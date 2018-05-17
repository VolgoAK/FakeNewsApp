package com.volgoak.fakenewsapp

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.volgoak.fakenewsapp.adapters.CommentViewHolder
import com.volgoak.fakenewsapp.beans.Comment
import com.volgoak.fakenewsapp.beans.Post
import kotlinx.android.synthetic.main.activity_post.*

class PostActivity : AppCompatActivity() {

    lateinit var viewModel: SinglePostViewModel

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

    private fun onPostReady(post : Post) {
        tvTitle.text = post.title
        tvContent.text = post.body
        tvDate.text = "12 jun 1984"
    }

    private fun onCommentsReady(comments : List<Comment>) {
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
