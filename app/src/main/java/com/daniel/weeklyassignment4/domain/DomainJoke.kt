package com.daniel.weeklyassignment4.domain

import com.daniel.weeklyassignment4.model.Joke
import com.daniel.weeklyassignment4.model.RandomJoke
import com.daniel.weeklyassignment4.model.RandomJokes

data class DomainJoke(
    val id: Int,
    val joke: String,
    val categories: String
)

fun Joke.mapToDomainJoke(): DomainJoke {
    return DomainJoke(
        id = this.id ?: 0,
        joke = this.joke ?: "No found joke",
        categories = this.categories.toString() ?: "No found category"
    )
}

fun List<Joke>.mapToDomainJokes(): List<DomainJoke> {
    return this.map { joke ->
        DomainJoke(
            id = joke.id ?: 0,
            joke = joke.joke ?: "No found joke",
            categories = joke.categories.toString() ?: "No found category"
        )
    }
}