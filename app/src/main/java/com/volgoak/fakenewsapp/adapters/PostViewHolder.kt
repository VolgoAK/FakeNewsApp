package com.volgoak.fakenewsapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.volgoak.fakenewsapp.R
import com.volgoak.fakenewsapp.beans.Post

/**
 * Created by alex on 5/17/18.
 */
class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTitle: TextView = view.findViewById(R.id.tvPostTitle)
    private val tvDate: TextView = view.findViewById(R.id.tvPostDate)
    private val tvContent: TextView = view.findViewById(R.id.tvPostContent)
    private val tvCommentsCount: TextView = view.findViewById(R.id.tvPostCommentsCount)

    var listener : ((Post) -> Unit)? = null

    fun bind(post: Post) {
        tvTitle.text = post.title
        tvContent.text = post.body
        tvCommentsCount.text = post.getCommentsCount().toString()



        itemView.setOnClickListener {
            listener?.invoke(post)
        }
    }
}