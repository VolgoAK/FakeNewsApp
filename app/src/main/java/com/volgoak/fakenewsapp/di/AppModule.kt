package com.volgoak.fakenewsapp.di

import com.volgoak.fakenewsapp.App
import com.volgoak.fakenewsapp.PlaceHolderApi
import com.volgoak.fakenewsapp.beans.MyObjectBox
import com.volgoak.fakenewsapp.dataSource.DataSource
import com.volgoak.fakenewsapp.dataSource.DataSourceImpl
import dagger.Module
import dagger.Provides
import io.objectbox.BoxStore

/**
 * Created by alex on 5/16/18.
 */

@Module
class AppModule(val app: App) {

    @ApplicationScope
    @Provides
    fun provideApp(): App = app

    @ApplicationScope
    @Provides
    fun provideDataSource(api: PlaceHolderApi, boxStore: BoxStore): DataSource {
        return DataSourceImpl(api, boxStore)
    }

    @ApplicationScope
    @Provides
    fun provideBoxStore(app: App): BoxStore {
        return MyObjectBox.builder().androidContext(app).build()
    }
}