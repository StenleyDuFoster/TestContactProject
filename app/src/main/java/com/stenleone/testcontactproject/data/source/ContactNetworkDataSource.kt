package com.stenleone.testcontactproject.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.stenleone.testcontactproject.data.network.ContactsApi
import com.stenleone.testcontactproject.domain.entity.ContactEntity
import com.stenleone.testcontactproject.domain.mapper.ContactMapper
import com.stenleone.testcontactproject.domain.model.ContactModel
import com.stenleone.testcontactproject.util.exception.ConnectionException
import com.stenleone.testcontactproject.util.exception.ConnectivityManager
import com.stenleone.testcontactproject.util.exception.SomethingWrongException
import com.stenleone.testcontactproject.util.extencion.mapToArrayList
import io.ktor.http.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class ContactNetworkDataSource @Inject constructor(private val service: ContactsApi, private val contactMapper: ContactMapper) :
    PagingSource<Int, ContactEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContactEntity> {
        val pageNumber = params.key ?: 1
        return try {
            if (!ConnectivityManager.networkState) {
                throw ConnectionException()
            }
            val response = service.getContacts(pageNumber)

            if (!response.status.isSuccess()) {
                throw SomethingWrongException()
            }

            val data = Json.decodeFromStream<ArrayList<ContactModel>>(response.content.toInputStream()).mapToArrayList {
                contactMapper.toEntity(it)
            }

            var nextPageNumber: Int? = null
            if (data.size > 0) {
                nextPageNumber = pageNumber + 1
            }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            if (!ConnectivityManager.networkState) {
                return LoadResult.Error(ConnectionException())
            }
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ContactEntity>): Int? = null

}