package com.stenleone.testcontactproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
class ContactModel(
    @SerialName("company_name")
    val companyName: String?,
    val createdAt: String?,
    val department: String?,
    val email: String?,
    @PrimaryKey
    val id: String?,
    val name: String?,
    val number: Int?,
    val surname: String?,
)