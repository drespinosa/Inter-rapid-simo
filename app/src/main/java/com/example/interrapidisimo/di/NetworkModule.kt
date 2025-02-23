package com.example.interrapidisimo.di

import android.content.Context
import androidx.room.Room
import com.example.interrapidisimo.data.database.TablesDatabase
import com.example.interrapidisimo.data.database.daos.LocalityDao
import com.example.interrapidisimo.data.database.daos.SchemaDao
import com.example.interrapidisimo.data.database.daos.UserDao
import com.example.interrapidisimo.data.network.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val DATABASE_NAME = "app_database"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://testing.interrapidisimo.co:8088/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideRoom(
        @ApplicationContext context: Context
    ): TablesDatabase {
        return Room.databaseBuilder(
            context,
            TablesDatabase::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries()
            .addMigrations(TablesDatabase.MIGRATION_1_2)
            .addMigrations(TablesDatabase.MIGRATION_2_3)
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: TablesDatabase): UserDao {
        return db.getUserDao()
    }

    @Singleton
    @Provides
    fun provideSchemaDao(db: TablesDatabase): SchemaDao {
        return db.getSchemaDao()
    }

    @Singleton
    @Provides
    fun provideLocalityDao(db: TablesDatabase): LocalityDao {
        return db.getLocalityDao()
    }

}