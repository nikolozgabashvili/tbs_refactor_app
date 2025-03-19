package com.example.challenge.domain.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

sealed class Resource<out D> {
    data class Success<out D>(val data: D) : Resource<D>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Loading(val loading: Boolean) : Resource<Nothing>()
}

suspend fun <Dto, Domain> Resource<Dto>.map(
    transform: suspend (Dto) -> Domain
): Resource<Domain> {
    return when (this) {
        is Resource.Success -> Resource.Success(data = transform(data))
        is Resource.Error -> Resource.Error(errorMessage = errorMessage)
        is Resource.Loading -> Resource.Loading(loading = loading)
    }
}

suspend fun <Dto, Domain> Flow<Resource<Dto>>.mapResource(
    transform: suspend (Dto) -> Domain,
): Flow<Resource<Domain>> {
    return this.map { it.map(transform) }
}
