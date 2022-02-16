package com.stenleone.testcontactproject.di

import android.content.Context
import androidx.room.Room
import com.stenleone.testcontactproject.data.local.ContactDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ContactDb::class.java,
        "contact_db"
    ).build()

    @Singleton
    @Provides
    fun provideYourDao(db: ContactDb) = db.dao()

}