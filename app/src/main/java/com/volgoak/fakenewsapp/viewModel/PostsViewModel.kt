package com.volgoak.fakenewsapp.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.volgoak.fakenewsapp.App
import com.volgoak.fakenewsapp.beans.Post
import com.volgoak.fakenewsapp.dataSource.DataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


import javax.inject.Inject

/**
 * Created by alex on 5/16/18.
 */
class PostsViewModel(app: Application) : AndroidViewModel(app) {

    @Inject
    lateinit var dataSource: DataSource

    private val postsLiveData = MutableLiveData<List<Post>>()

    private var postsDisposable: Disposable? = null

    init {
        (app as App).appComponent.inject(this)
    }

    /**
     * Возвращает LiveData c постами, подписывается на обновления
     * от DataSource, если подписка не была создана ранее
     */
    fun getPosts(): LiveData<List<Post>> {
        if (postsDisposable == null) {
            subscribeToPosts()
        }

        return postsLiveData
    }

    /**
     * Подписывается на обновления от DataSource,
     * вызывает обновление постов и комментариев
     */
    private fun subscribeToPosts() {
        postsDisposable = dataSource.getAllPosts(true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    Timber.d("posts obtained size ${list.size}")
                    postsLiveData.value = list
                }, { error ->
                    Timber.e(error)
                })

        dataSource.refreshComments()
    }

    override fun onCleared() {
        super.onCleared()
        //очищаем подписку
        postsDisposable?.dispose()
    }
}