package com.volgoak.fakenewsapp.viewModel

import android.app.Application
import android.arch.lifecycle.*
import com.volgoak.fakenewsapp.App
import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Post
import com.volgoak.fakenewsapp.dataSource.DataSource
import com.volgoak.fakenewsapp.utils.ErrorType
import com.volgoak.fakenewsapp.utils.showErrorToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by alex on 5/17/18.
 */
class SinglePostViewModel(app: Application, val postId: Long) : AndroidViewModel(app) {

    @Inject
    lateinit var dataSource: DataSource

    private val postLiveData = MutableLiveData<Post>()
    private val commentsLiveData = MutableLiveData<List<Comment>>()

    private var postDisposable: Disposable? = null
    private var commentsDisposable: Disposable? = null

    private val errorObserver = Observer<ErrorType>{
        it?.let {
            showErrorToast(it, app)
        }
    }

    init {
        (app as App).appComponent.inject(this)
        dataSource.getErrorLiveData()
                .observeForever(errorObserver)
    }

    /**
     * Возвращает LiveData c постом.
     * При первом вызове вызывает загрузку поста из DataSource
     */
    fun getNote(): LiveData<Post> {
        if (postDisposable == null) {
            loadPost()
        }
        return postLiveData
    }

    /**
     * Возвращает LiveData с комментариями к текущему посту
     */
    fun getComments(): LiveData<List<Comment>> {
        if (commentsDisposable == null) {
            loadComments()
        }

        return commentsLiveData
    }

    /**
     * Подписывается на обновления поста от DataSource
     */
    private fun loadPost() {
        postDisposable = dataSource.getPost(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ post ->
                    postLiveData.value = post
                }, { error ->
                    Timber.e(error)
                })
    }

    /**
     * Подписывается на обновления комментариев к посту
     */
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

        dataSource.getErrorLiveData()
                .removeObserver(errorObserver)

    }

    /**
     * Фабрика для создания ViewModel
     */
    class Factory(val app: Application, val postId: Long) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SinglePostViewModel(app, postId) as T
        }
    }
}