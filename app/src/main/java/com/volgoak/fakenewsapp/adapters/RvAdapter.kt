package com.volgoak.fakenewsapp.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.volgoak.fakenewsapp.R
import com.volgoak.fakenewsapp.beans.Post

/**
 * Created by alex on 5/17/18.
 */
class RvAdapter : RecyclerView.Adapter<PostViewHolder>() {

    private var data: List<Post>? = null

    var listener: ((Post) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_holder, parent, false)

        val holder = PostViewHolder(view)
        holder.listener = listener

        return holder
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        data?.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    fun changeData(newData: List<Post>?) {
        data = newData
        notifyDataSetChanged()
    }
}