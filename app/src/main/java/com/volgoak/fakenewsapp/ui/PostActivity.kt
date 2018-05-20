package com.volgoak.fakenewsapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import com.volgoak.fakenewsapp.R
import com.volgoak.fakenewsapp.adapters.CommentViewHolder
import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Post
import com.volgoak.fakenewsapp.viewModel.SinglePostViewModel
import kotlinx.android.synthetic.main.activity_post.*

/**
 * Activity отображает содержимое конкретного поста
 * и комментарии к нему
 */
class PostActivity : AppCompatActivity() {

    private lateinit var viewModel: SinglePostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val postId = intent.getLongExtra(EXTRA_POST_ID, 0)

        viewModel = ViewModelProviders.of(this, SinglePostViewModel.Factory(application, postId))
                .get(SinglePostViewModel::class.java)

        viewModel.getNote().observe(this, Observer { post ->
            post?.let { onPostReady(it) }
        })

        viewModel.getComments().observe(this, Observer { comments ->
            comments?.let { onCommentsReady(it) }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            super.onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun onPostReady(post: Post) {
        tvTitle.text = post.title
        tvContent.text = post.body
        //Используем placeHolder для даты
        tvDate.setText(R.string.date_placeholder)
    }

    /**
     * Для каждого комментария создает CommentHolder
     * и добавляет его вьюху в LinearLayout
     */
    private fun onCommentsReady(comments: List<Comment>) {
        llComments.removeAllViews()
        comments.forEach {
            val view = LayoutInflater.from(this)
                    .inflate(R.layout.comment_holder, llComments, false)

            val holder = CommentViewHolder(view)
            holder.bind(it)

            llComments.addView(view)
        }
    }

    companion object {
        const val EXTRA_POST_ID = "extra_post_id"
    }
}
