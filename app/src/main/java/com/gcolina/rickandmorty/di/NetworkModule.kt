package com.gcolina.rickandmorty.di

import com.gcolina.rickandmorty.data.CharactersApi
import com.gcolina.rickandmorty.data.repository.CharacterDataSource
import com.gcolina.rickandmorty.data.repository.CharacterRepository
import com.gcolina.rickandmorty.data.repository.CharacterRepositoryImpl
import com.gcolina.rickandmorty.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CharactersApi {
        return retrofit.create(CharactersApi::class.java)
    }

    @Provides
    @Singleton
    fun providesHeroesRepository(characterDataSource: CharacterDataSource): CharacterRepository {
        return CharacterRepositoryImpl(characterDataSource)
    }

}
