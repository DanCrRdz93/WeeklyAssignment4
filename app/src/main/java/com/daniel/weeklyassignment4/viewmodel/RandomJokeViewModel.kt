package com.daniel.weeklyassignment4.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daniel.weeklyassignment4.network.JokesRepository
import com.daniel.weeklyassignment4.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RandomJokeViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val ioDispatcher: CoroutineDispatcher
): ViewModel() {

    private val _randomJoke: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.LOADING)
    val joke: LiveData<ResponseState> get() = _randomJoke

    private val _customJoke: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.LOADING)
    val customJoke: LiveData<ResponseState> get() = _customJoke

    private val _randomJokes: MutableLiveData<ResponseState> = MutableLiveData(ResponseState.LOADING)
    val jokes: LiveData<ResponseState> get() = _randomJokes

    private var _firstName: String = ""
    private var _lastName: String = ""

    fun setName(firstName: String, lastName: String){
        _firstName = firstName
        _lastName = lastName
    }

    fun getRandomJoke(){
        getJokes(jokesRepository.getRandomJoke(),_randomJoke)
    }

    fun getCustomJoke(){
        getJokes(jokesRepository.getCustomJoke(_firstName,_lastName),_customJoke)
    }

    fun getRandomJokes(){
        getJokes(jokesRepository.getRandomJokes(),_randomJokes)

    }

    private fun getJokes(flow: Flow<ResponseState>, joke: MutableLiveData<ResponseState>){
        viewModelScope.launch {
            try {
                flow
                    .flowOn(ioDispatcher)
                    .collect{
                        when (it) {
                            is ResponseState.LOADING -> {
                                joke.postValue(it)
                            }
                            is ResponseState.SUCCESS<*> ->{
                                joke.postValue(it)
                            }
                            is ResponseState.ERROR -> {
                                joke.postValue(it)
                            }
                        }
                    }
            } catch (error: Exception) {
                withContext(Dispatchers.Main){
                    joke.postValue(ResponseState.ERROR(error))
                }
            }
        }
    }

    fun resetState() {
        _randomJoke.value = ResponseState.LOADING
        _customJoke.value = ResponseState.LOADING
        _randomJokes.value = ResponseState.LOADING
    }
}