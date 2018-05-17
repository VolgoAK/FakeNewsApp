package com.volgoak.fakenewsapp.di

import com.volgoak.fakenewsapp.MainActivity
import com.volgoak.fakenewsapp.PostsViewModel
import com.volgoak.fakenewsapp.SinglePostViewModel
import dagger.Component

/**
 * Created by alex on 5/16/18.
 */

@ApplicationScope
@Component(modules = [AppModule::class, HttpModule::class])
interface AppComponent {

    fun inject(target: MainActivity)
    fun inject(target: PostsViewModel)
    fun inject(target: SinglePostViewModel)
}