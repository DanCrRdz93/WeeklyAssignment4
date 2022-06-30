package com.daniel.weeklyassignment4.model


import com.google.gson.annotations.SerializedName

data class RandomJokes(
    @SerializedName("type")
    val type: String?,
    @SerializedName("value")
    val value: List<Joke>
)