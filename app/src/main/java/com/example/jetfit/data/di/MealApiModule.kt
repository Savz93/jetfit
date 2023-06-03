package com.example.jetfit.data.di

import com.example.jetfit.data.mealapi.MealApi
import com.example.jetfit.data.mealapi.MealApiConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MealApiModule {

    @Provides
    @Singleton
    fun provideApi(builder: Retrofit.Builder): MealApi {
        return builder
            .build()
            .create(MealApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(MealApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
    }

}