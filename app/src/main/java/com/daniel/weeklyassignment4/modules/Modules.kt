package com.daniel.weeklyassignment4.DI

import androidx.lifecycle.ViewModel
import com.daniel.weeklyassignment4.network.ChuckNorrisApi
import com.daniel.weeklyassignment4.network.JokesRepository
import com.daniel.weeklyassignment4.network.JokesRepositoryImpl
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesGson(): Gson = Gson()

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30,TimeUnit.SECONDS)
            .writeTimeout(30,TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit =
        Retrofit.Builder()
            .baseUrl(ChuckNorrisApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    @Provides
    fun providesChuckNorrisApi(retrofit: Retrofit): ChuckNorrisApi =
        retrofit.create(ChuckNorrisApi::class.java)

    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun bindsJokesRepository(
        jokesRepositoryImpl: JokesRepositoryImpl
    ): JokesRepository

}

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {


}