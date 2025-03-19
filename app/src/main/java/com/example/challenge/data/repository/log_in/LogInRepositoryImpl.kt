package com.example.challenge.data.repository.log_in

import com.example.challenge.data.common.ResponseHandler
import com.example.challenge.data.mapper.log_in.toDomain
import com.example.challenge.data.service.log_in.LogInService
import com.example.challenge.domain.common.Resource
import com.example.challenge.domain.common.map
import com.example.challenge.domain.model.log_in.GetToken
import com.example.challenge.domain.repository.datastore.DataStoreManager
import com.example.challenge.domain.repository.log_in.LogInRepository
import com.example.challenge.domain.user_data_key.PreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val logInService: LogInService,
    private val dataStoreManager: DataStoreManager,
    private val responseHandler: ResponseHandler,
) : LogInRepository {
    override suspend fun logIn(email: String, password: String): Flow<Resource<GetToken>> {
        return responseHandler.safeApiCall {
            logInService.logIn(email = email, password = password)
        }.map { resource ->
            if (resource is Resource.Success) {
                resource.data.accessToken?.let {
                    dataStoreManager.saveValue(key = PreferenceKeys.ACCESS_TOKEN, it)
                }
                resource.data.refreshToken?.let {
                    dataStoreManager.saveValue(PreferenceKeys.REFRESH_TOKEN, it)
                }
            }
            resource.map { dto ->
                dto.toDomain()
            }
        }
    }
}