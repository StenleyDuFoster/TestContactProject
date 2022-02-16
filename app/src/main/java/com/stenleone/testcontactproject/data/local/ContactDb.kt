package com.stenleone.testcontactproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stenleone.testcontactproject.domain.entity.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1
)
abstract class ContactDb : RoomDatabase() {

    abstract fun dao(): ContactDao

}