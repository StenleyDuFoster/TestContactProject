package com.stenleone.testcontactproject.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import coil.network.HttpException
import com.stenleone.testcontactproject.data.local.ContactDb
import com.stenleone.testcontactproject.data.network.ContactsApi
import com.stenleone.testcontactproject.domain.entity.ContactEntity
import com.stenleone.testcontactproject.domain.mapper.ContactMapper
import com.stenleone.testcontactproject.domain.model.ContactModel
import com.stenleone.testcontactproject.util.extencion.mapToArrayList
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.IOException
import java.util.concurrent.TimeUnit

@ExperimentalPagingApi
class ContactRemoteMediator(
    private val database: ContactDb,
    private val networkService: ContactsApi,
    private val contactMapper: ContactMapper
): RemoteMediator<Int, ContactEntity>() {

    val dao = database.dao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ContactEntity>): MediatorResult {
        val pageNumber = state.anchorPosition ?: 1
        return try {
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (lastItem == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    lastItem.id
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = networkService.getContacts(pageNumber)
            val data = Json.decodeFromStream<ArrayList<ContactModel>>(response.content.toInputStream()).mapToArrayList { contactMapper.toEntity(it) }

            dao.insertAll(data)

            MediatorResult.Success(
                endOfPaginationReached = data.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        return if (System.currentTimeMillis() /*- db.lastUpdated()*/ >= cacheTimeout)
        {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

}