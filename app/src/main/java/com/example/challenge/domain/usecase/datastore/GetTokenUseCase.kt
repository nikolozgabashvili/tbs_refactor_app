package com.example.challenge.domain.usecase.datastore

import com.example.challenge.domain.repository.datastore.DataStoreManager
import com.example.challenge.domain.user_data_key.PreferenceKeys
import javax.inject.Inject

class GetTokenUseCase @Inject constructor(private val dataStoreManager: DataStoreManager) {
    operator fun invoke() = dataStoreManager.getValue(key = PreferenceKeys.ACCESS_TOKEN, "")
}