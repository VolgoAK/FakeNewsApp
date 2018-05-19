package com.volgoak.fakenewsapp

import android.app.Application
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import timber.log.Timber

/**
 * Created by alex on 5/19/18.
 */
class ErrorHandlerImpl(val app: Application) : ErrorHandler{

    override fun onPostsUpdateError(throwable: Throwable) {
        Timber.e(throwable)
        showToast(R.string.error_updating_posts)
    }

    override fun onCommentsUpdateError(throwable: Throwable) {
        Timber.e(throwable)
        showToast(R.string.error_updating_comments)
    }

    override fun onDbError(throwable: Throwable) {
        Timber.e(throwable)
        showToast(R.string.db_error)
    }

    private fun showToast(stringId : Int) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(app, stringId, Toast.LENGTH_LONG).show()
        }
    }
}