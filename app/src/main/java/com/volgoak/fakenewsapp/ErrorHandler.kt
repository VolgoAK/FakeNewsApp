package com.volgoak.fakenewsapp

/**
 * Created by alex on 5/19/18.
 */
interface ErrorHandler {
    fun onPostsUpdateError(throwable: Throwable)

    fun onCommentsUpdateError(throwable: Throwable)

    fun onDbError(throwable: Throwable)
}