package com.stenleone.testcontactproject.data.repository

import com.stenleone.testcontactproject.data.network.ContactsApi
import com.stenleone.testcontactproject.domain.DataState
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

class ContactRepository @Inject constructor(
    private val contactsApi: ContactsApi,
    private val contactMapper: ContactMapper
) {

    suspend fun getContacts(): DataState<ArrayList<ContactEntity>> {
        if (!ConnectivityManager.networkState) {
            return DataState.Error(ConnectionException())
        }

        return try {
            val result = contactsApi.getContacts()

            if (!result.status.isSuccess()) {
                return DataState.Error(SomethingWrongException())
            }

            val data = Json.decodeFromStream<ArrayList<ContactModel>>(result.content.toInputStream())

            DataState.Success(data.mapToArrayList { contactMapper.toEntity(it) })
        } catch (e: Exception) {
            if (!ConnectivityManager.networkState) {
                return DataState.Error(ConnectionException())
            }
            DataState.Error(e)
        }
    }

}