package com.stenleone.testcontactproject.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ContactModel(
    @SerialName("company_name")
    val companyName: String?,
    val createdAt: String?,
    val department: String?,
    val email: String?,
    val id: String?,
    val name: String?,
    val number: Int?,
    val surname: String?,
)