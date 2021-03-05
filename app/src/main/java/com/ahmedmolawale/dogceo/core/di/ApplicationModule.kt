package com.ahmedmolawale.dogceo.core.di

import com.ahmedmolawale.dogceo.BuildConfig
import com.ahmedmolawale.dogceo.features.dogs.data.remote.api.DogCeoApi
import com.ahmedmolawale.dogceo.features.dogs.data.repository.DogBreedImagesRepository
import com.ahmedmolawale.dogceo.features.dogs.data.repository.DogBreedsRepository
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedImagesRepository
import com.ahmedmolawale.dogceo.features.dogs.domain.repository.IDogBreedsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideDogCeoService(retrofit: Retrofit): DogCeoApi {
        return retrofit.create(DogCeoApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindDogBreedRepository(repo: DogBreedsRepository): IDogBreedsRepository

    @Binds
    abstract fun bindCharacterDetailsRepository(repo: DogBreedImagesRepository): IDogBreedImagesRepository
}
