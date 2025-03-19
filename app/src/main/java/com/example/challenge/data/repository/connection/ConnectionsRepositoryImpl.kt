package com.example.challenge.data.repository.connection

import com.example.challenge.data.common.ResponseHandler
import com.example.challenge.data.mapper.connection.toDomain
import com.example.challenge.data.service.connection.ConnectionsService
import com.example.challenge.domain.common.Resource
import com.example.challenge.domain.common.mapResource
import com.example.challenge.domain.model.connection.GetConnection
import com.example.challenge.domain.repository.connection.ConnectionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConnectionsRepositoryImpl @Inject constructor(
    private val connectionsService: ConnectionsService,
    private val responseHandler: ResponseHandler,
) : ConnectionsRepository {

    override suspend fun getConnections(): Flow<Resource<List<GetConnection>>> {
        return responseHandler.safeApiCall {
            connectionsService.getConnections()
        }.mapResource {list->
            list.map {dto->
                dto.toDomain()
            }
        }
    }
}