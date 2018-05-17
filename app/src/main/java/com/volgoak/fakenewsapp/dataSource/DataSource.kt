package com.volgoak.fakenewsapp.dataSource

import android.arch.lifecycle.LiveData
import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Post
import io.reactivex.Observable


/**
 * Created by alex on 5/16/18.
 */
interface DataSource {
    fun getAllPosts(refresh : Boolean) : Observable<List<Post>>

    fun getPost(id : Long) : Observable<Post>

    fun getComments(postId : Long) : Observable<List<Comment>>

    fun refreshComments()
}