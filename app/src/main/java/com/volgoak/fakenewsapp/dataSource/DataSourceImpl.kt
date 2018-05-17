package com.volgoak.fakenewsapp.dataSource

import android.app.DownloadManager
import com.volgoak.fakenewsapp.PlaceHolderApi
import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Comment_
import com.volgoak.fakenewsapp.beans.Post
import com.volgoak.fakenewsapp.beans.Post_
import io.objectbox.BoxStore
import io.objectbox.query.Query
import io.objectbox.rx.RxQuery
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * Created by alex on 5/16/18.
 */
class DataSourceImpl(val api: PlaceHolderApi, val boxStore: BoxStore) : DataSource {

    private val postBox = boxStore.boxFor(Post::class.java)
    private val commentsBox = boxStore.boxFor(Comment::class.java)

    override fun getAllPosts(refresh: Boolean): Observable<List<Post>> {

        if(refresh) refreshPosts()

        val query = postBox.query().build()
        return RxQuery.observable(query).map {
            Timber.d("on posts ${it.size}")
            it
        }
    }

    override fun getPost(id: Long): Observable<Post> {
        val query = postBox.query().equal(Post_.id, id).build()
        return RxQuery.observable(query).map { it.first()}
    }

    override fun getComments(postId: Long): Observable<List<Comment>> {
        refreshComments(postId)

        val query = commentsBox.query().equal(Comment_.postId, postId).build()
        return RxQuery.observable(query)
    }

    //todo delete
    fun instertTestPost() {
        Timber.d("New post added")
        val post = Post(id = 1000, title = "new post")
        postBox.put(post)
    }

    fun refreshPosts() {
        api.getAllPosts()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({ list ->
                    Timber.d("Posts from server. Size ${list.size}")
                    postBox.put(list)
                }, { error ->
                    //todo handle errors
                    Timber.e(error)
                })
    }

    fun refreshPost(id: Long) {
        api.getPostById(id)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({ post ->
                    postBox.put(post)
                }, { error ->
                    //todo handle errors
                    Timber.e(error)
                })
    }

    fun refreshComments(postId: Long) {
        api.getCommentsByPostId(postId)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({ comments ->
                    commentsBox.put(comments)
                }, { error ->
                    //todo handle errors
                    Timber.e(error)
                })
    }

    override fun refreshComments() {
        api.getComments()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribe({ comments ->
                    commentsBox.put(comments)
                }, { error ->
                    Timber.e(error)
                })
    }
}