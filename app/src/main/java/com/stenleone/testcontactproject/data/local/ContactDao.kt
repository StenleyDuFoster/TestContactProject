package com.stenleone.testcontactproject.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.stenleone.testcontactproject.domain.entity.ContactEntity

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contacts: List<ContactEntity>)

    @Query("SELECT * FROM ContactEntity WHERE name LIKE :query")
    fun pagingSourceByQueryName(query: String): PagingSource<Int, ContactEntity>

    @Query("DELETE FROM ContactEntity")
    suspend fun clearAll()

    @Transaction
    suspend fun clearAndInsert(contacts: List<ContactEntity>) {
        clearAll()
        insertAll(contacts)
    }

}