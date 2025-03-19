package com.example.challenge.di

import com.example.challenge.data.repository.connection.ConnectionsRepositoryImpl
import com.example.challenge.data.repository.datastore.DataStoreManagerImpl
import com.example.challenge.data.repository.log_in.LogInRepositoryImpl
import com.example.challenge.domain.repository.connection.ConnectionsRepository
import com.example.challenge.domain.repository.datastore.DataStoreManager
import com.example.challenge.domain.repository.log_in.LogInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsDataStoreRepository(impl: DataStoreManagerImpl): DataStoreManager

    @Singleton
    @Binds
    abstract fun bindsConnectionsRepository(impl: ConnectionsRepositoryImpl): ConnectionsRepository

    @Singleton
    @Binds
    abstract fun bindsLoginRepository(impl: LogInRepositoryImpl): LogInRepository



}