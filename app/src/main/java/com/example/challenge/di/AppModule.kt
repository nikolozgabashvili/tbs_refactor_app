package com.example.challenge.di

import com.example.challenge.BuildConfig
import com.example.challenge.data.AppInterceptor
import com.example.challenge.data.common.ResponseHandler
import com.example.challenge.data.service.connection.ConnectionsService
import com.example.challenge.data.service.log_in.LogInService
import com.example.challenge.domain.repository.datastore.DataStoreManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        appInterceptor: AppInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(appInterceptor)
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(loggingInterceptor)
        }
        return clientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideAppInterceptor(
        dataStoreManagerImpl: DataStoreManager
    ): AppInterceptor = AppInterceptor(dataStoreManagerImpl)


    @Singleton
    @Provides
    fun provideHandleResponse(): ResponseHandler {
        return ResponseHandler()
    }

    @Singleton
    @Provides
    fun provideLogInService(retrofit: Retrofit): LogInService {
        return retrofit.create(LogInService::class.java)
    }

    @Singleton
    @Provides
    fun provideConnectionsService(retrofit: Retrofit): ConnectionsService {
        return retrofit.create(ConnectionsService::class.java)
    }
}