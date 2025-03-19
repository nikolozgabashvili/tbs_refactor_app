package com.example.challenge.domain.usecase.datastore

import com.example.challenge.domain.repository.datastore.DataStoreManager
import javax.inject.Inject

class ClearDataStoreUseCase @Inject constructor(private val dataStoreManager: DataStoreManager) {
    suspend operator fun invoke() {
        dataStoreManager.clear()
    }
}