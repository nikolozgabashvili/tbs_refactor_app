package com.example.challenge.data

import com.example.challenge.domain.repository.datastore.DataStoreManager
import com.example.challenge.domain.user_data_key.PreferenceKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AppInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    private var authToken: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {

        val token = authToken ?: run {
            runBlocking {
                authToken =
                    dataStoreManager.getValue(key = PreferenceKeys.ACCESS_TOKEN, defaultValue = "")
                        .first()
                authToken
            }
        }
        val newRequest = if (!token.isNullOrBlank()) {
            chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(newRequest)

    }
}