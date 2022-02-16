package com.stenleone.testcontactproject.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactEntity(
    val companyName: String,
    val createdAt: String,
    val department: String,
    val email: String,
    @PrimaryKey
    val id: String,
    val name: String,
    val number: Int,
    val surname: String,
)