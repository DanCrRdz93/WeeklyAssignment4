package com.daniel.weeklyassignment4.network

import com.daniel.weeklyassignment4.domain.mapToDomainJoke
import com.daniel.weeklyassignment4.domain.mapToDomainJokes
import com.daniel.weeklyassignment4.model.RandomJoke
import com.daniel.weeklyassignment4.model.RandomJokes
import com.daniel.weeklyassignment4.utils.ResponseState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

interface JokesRepository{
    fun getRandomJoke(): Flow<ResponseState>
    fun getRandomJokes(): Flow<ResponseState>
    fun getCustomJoke(firstName: String, lastName: String): Flow<ResponseState>
}

class JokesRepositoryImpl @Inject constructor(
    private val chuckNorrisApi: ChuckNorrisApi
) : JokesRepository {
    override fun getRandomJoke(): Flow<ResponseState> =
        flow {
            try {
                val response = chuckNorrisApi.getRandomJoke()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResponseState.SUCCESS(it.value?.mapToDomainJoke()))
                    } ?: throw Exception("RESPONSE IS NULL")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (error: Exception) {
                emit(ResponseState.ERROR(error))
            }
        }

    override fun getCustomJoke(firstName: String, lastName: String): Flow<ResponseState> =
        flow {
            try {
                val response = chuckNorrisApi.changeName(firstName,lastName)
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResponseState.SUCCESS(it.value?.mapToDomainJoke()))
                    } ?: throw Exception("RESPONSE IS NULL")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (error: Exception) {
                emit(ResponseState.ERROR(error))
            }
        }

    override fun getRandomJokes(): Flow<ResponseState> =
        flow {
            try {
                val response = chuckNorrisApi.getRandomJokes()
                if (response.isSuccessful) {
                    response.body()?.let {
                        emit(ResponseState.SUCCESS(it.value?.mapToDomainJokes()))
                    } ?: throw Exception("RESPONSE IS NULL")
                } else {
                    throw Exception(response.errorBody()?.string())
                }
            } catch (error: Exception) {
                emit(ResponseState.ERROR(error))
            }
        }
}