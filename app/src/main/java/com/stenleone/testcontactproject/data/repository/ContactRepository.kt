package com.stenleone.testcontactproject.data.repository

import androidx.paging.*
import com.stenleone.testcontactproject.data.local.ContactDb
import com.stenleone.testcontactproject.data.mediator.ContactRemoteMediator
import com.stenleone.testcontactproject.data.network.ContactsApi
import com.stenleone.testcontactproject.data.source.ContactNetworkDataSource
import com.stenleone.testcontactproject.domain.entity.ContactEntity
import com.stenleone.testcontactproject.domain.mapper.ContactMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactApi: ContactsApi,
    private val remoteDb: ContactDb,
    private val contactMapper: ContactMapper
) {

    @ExperimentalPagingApi
    fun getContacts(): Flow<PagingData<ContactEntity>> {
        return Pager(
            config = PagingConfig(10, 8),
            remoteMediator = ContactRemoteMediator(remoteDb, contactApi, contactMapper),
            pagingSourceFactory = { ContactNetworkDataSource(contactApi, contactMapper) }).flow
    }

}