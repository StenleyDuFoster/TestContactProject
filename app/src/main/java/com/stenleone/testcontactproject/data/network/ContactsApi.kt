package com.stenleone.testcontactproject.data.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import javax.inject.Inject

class ContactsApi @Inject constructor(private val client: HttpClient) {

    suspend fun getContacts(page: Int) = client.get<HttpResponse>(NetworkUrl.CONTACTS.url) {
        parameter("page", page)
        parameter("limit", 10)
    }

}