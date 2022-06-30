package com.daniel.weeklyassignment4.model


import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("categories")
    val categories: List<Any?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("joke")
    val joke: String?
)