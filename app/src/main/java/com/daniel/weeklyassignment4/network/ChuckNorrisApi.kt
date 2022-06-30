package com.daniel.weeklyassignment4.network

import com.daniel.weeklyassignment4.domain.DomainJoke
import com.daniel.weeklyassignment4.model.RandomJoke
import com.daniel.weeklyassignment4.model.RandomJokes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChuckNorrisApi {


    @GET(RANDOM_JOKE)
    suspend fun getRandomJoke(
    ): Response<RandomJoke>

    @GET(RANDOM_JOKE)
    suspend fun changeName(
        @Query("firstName") firstName: String,
        @Query("lastName") lastName: String
    ): Response<RandomJoke>

    @GET(RANDOM_LIST)
    suspend fun getRandomJokes(
        @Path("limit") limit: Int = 5
    ): Response<RandomJokes>

    companion object{
    //http://api.icndb.com/jokes/random?firstName=xxx&lastName=xxx for input name
        const val BASE_URL = "http://api.icndb.com/jokes/"
        private const val RANDOM_JOKE = "random"
        private const val RANDOM_LIST = "random/{limit}"
    }
}