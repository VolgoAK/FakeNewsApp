package com.volgoak.fakenewsapp.di

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.volgoak.fakenewsapp.PlaceHolderApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by alex on 5/16/18.
 */
@Module
@ApplicationScope
class HttpModule {

    @Provides
    @ApplicationScope
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    @ApplicationScope
    fun providePlaceHolderApi(retrofit: Retrofit): PlaceHolderApi {
        return retrofit.create(PlaceHolderApi::class.java)
    }
}