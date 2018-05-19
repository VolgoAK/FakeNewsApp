package com.volgoak.fakenewsapp.dataSource

import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Post
import io.reactivex.Observable


/**
 * Интерфейс взаимодействия с базой данных
 */
interface DataSource {
    fun getAllPosts(refresh: Boolean): Observable<List<Post>>

    fun getPost(id: Long): Observable<Post>

    fun getComments(postId: Long): Observable<List<Comment>>

    fun refreshComments()

    fun refreshPosts()
}