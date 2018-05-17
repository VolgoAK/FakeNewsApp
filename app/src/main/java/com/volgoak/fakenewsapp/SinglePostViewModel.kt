package com.volgoak.fakenewsapp

import android.app.Application
import android.arch.lifecycle.*
import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Comment_.postId
import com.volgoak.fakenewsapp.beans.Post
import com.volgoak.fakenewsapp.dataSource.DataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by alex on 5/17/18.
 */
class SinglePostViewModel(app: Application,val postId: Long) : AndroidViewModel(app) {

    @Inject
    lateinit var dataSource: DataSource

    private val postLiveData = MutableLiveData<Post>()
    private val commentsLiveData = MutableLiveData<List<Comment>>()

    private var postDisposable: Disposable? = null
    private var commentsDisposable: Disposable? = null

    init {
        (app as App).appComponent.inject(this)
    }

    fun getNote(): LiveData<Post> {
        if (postDisposable == null) {
            loadPost()
        }
        return postLiveData
    }

    fun getComments(): LiveData<List<Comment>> {
        if (commentsDisposable == null) {
            loadComments()
        }

        return commentsLiveData
    }

    private fun loadPost() {
        postDisposable = dataSource.getPost(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ post ->
                    postLiveData.value = post
                }, {error ->
                    Timber.e(error)
                })
    }

    private fun loadComments() {
        commentsDisposable = dataSource.getComments(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ comments ->
                    commentsLiveData.value = comments
                }, { error ->
                    Timber.e(error)
                })
    }

    override fun onCleared() {
        super.onCleared()
        postDisposable?.dispose()
        commentsDisposable?.dispose()
    }

    class Factory(val app: Application, val postId: Long) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SinglePostViewModel(app, postId) as T
        }
    }
}