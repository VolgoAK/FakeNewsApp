package com.volgoak.fakenewsapp.adapters

import android.view.View
import android.widget.TextView
import com.volgoak.fakenewsapp.R
import com.volgoak.fakenewsapp.beans.Comment

/**
 * Created by alex on 5/17/18.
 */
class CommentViewHolder(val view : View) {
    val tvAuthor : TextView = view.findViewById(R.id.tvCommentAuthor)
    val tvDate : TextView = view.findViewById(R.id.tvCommentDate)
    val tvContent : TextView = view.findViewById(R.id.tvCommentContent)

    fun bind(comment: Comment) {
        tvAuthor.text = comment.name
        tvContent.text = comment.body

    }
}