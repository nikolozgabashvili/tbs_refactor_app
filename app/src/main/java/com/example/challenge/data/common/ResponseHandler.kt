package com.example.challenge.data.common

import com.example.challenge.domain.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class ResponseHandler {
    fun <T : Any> safeApiCall(call: suspend () -> Response<T>) = flow {
        emit(Resource.Loading(loading = true))
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                emit(Resource.Success(data = body))
            } else {
                emit(Resource.Error(errorMessage = response.errorBody()?.string() ?: ""))
            }
        } catch (e: Throwable) {
            emit(Resource.Error(errorMessage = e.message ?: ""))
        }
        emit(Resource.Loading(loading = false))
    }.flowOn(Dispatchers.IO)
}
