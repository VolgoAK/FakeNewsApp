package com.volgoak.fakenewsapp

import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Post
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Api для связи с сервером jsonplaceholder.typicode.com
 */
interface PlaceHolderApi {

    @GET(value = "posts")
    fun getAllPosts(): Single<List<Post>>

    @GET(value = "posts/{id}")
    fun getPostById(@Path("id") id: Long): Single<Post>

    @GET(value = "comments")
    fun getComments(): Single<List<Comment>>

    @GET(value = "comments")
    fun getCommentsByPostId(@Query("postId") id: Long): Single<List<Comment>>
}