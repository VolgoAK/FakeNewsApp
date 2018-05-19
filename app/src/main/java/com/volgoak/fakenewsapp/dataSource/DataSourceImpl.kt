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
 * Реализация интерфейса DataSource. Получает данные с сервера jsonplaceholder.typicode.com
 * и кэширует их в базу данных ObjectBox
 */
class DataSourceImpl(private val api: PlaceHolderApi, boxStore: BoxStore) : DataSource {

    private val postBox = boxStore.boxFor(Post::class.java)
    private val commentsBox = boxStore.boxFor(Comment::class.java)

    /**
     * Возвращает посты из базы данных в виде Observable
     * @param refresh загрузить новые данные с сервера
     */
    override fun getAllPosts(refresh: Boolean): Observable<List<Post>> {

        if(refresh) refreshPosts()

        val query = postBox.query().build()
        return RxQuery.observable(query)
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

    /**
     * Загружает все посты с сервера и добавляет их в базу данных
     */
    override fun refreshPosts() {
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

    /**
     * Загружает все комментарии с сервера и добавляет их в базу данных
     */
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

    /**
     * Загружает пост по id и обнавляет данные в базе
     * @param id id поста
     */
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

    /**
     * Загружает комментарии относящиеся к посту и обновляет данные в базе
     * @param postId id поста, комменты к которому нужно обновить
     */
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
}