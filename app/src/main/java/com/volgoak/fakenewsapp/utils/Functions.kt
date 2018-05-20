package com.volgoak.fakenewsapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.volgoak.fakenewsapp.R

/**
 * Created by alex on 5/20/18.
 */


fun showErrorToast(errorType: ErrorType, context: Context) {
    val resId = when (errorType) {
        ErrorType.POSTS_UPDATING_ERROR,
        ErrorType.POST_UPDATING_ERROR -> R.string.error_updating_posts
        ErrorType.COMMENTS_UPDATING_ERROR -> R.string.error_updating_comments
        ErrorType.DB_ERROR -> R.string.db_error
    }

    Toast.makeText(context, resId, Toast.LENGTH_LONG).show()
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}
