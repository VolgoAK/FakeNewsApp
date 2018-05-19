package com.volgoak.fakenewsapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.volgoak.fakenewsapp.viewModel.PostsViewModel
import com.volgoak.fakenewsapp.R
import com.volgoak.fakenewsapp.adapters.RvAdapter
import com.volgoak.fakenewsapp.beans.Post
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Activity отображает список постов
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PostsViewModel
    private lateinit var adapter: RvAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(PostsViewModel::class.java)

        //Подписываемся на список постов
        viewModel.getPosts().observe(this, Observer { list ->
            onPostsReady(list)
        })

        //Инициализация RecyclerView
        val llm = LinearLayoutManager(this)
        rvMain.layoutManager = llm

        adapter = RvAdapter()
        adapter.listener = this::onPostClicked
        rvMain.adapter = adapter
    }

    private fun onPostsReady(posts: List<Post>?) {
        adapter.changeData(posts)
    }

    /**
     * При клике на пост, открывает PostActivity с выбранным постом
     */
    private fun onPostClicked(post : Post) {
        val intent = Intent(this, PostActivity::class.java)
        intent.putExtra(PostActivity.EXTRA_POST_ID, post.id)

        startActivity(intent)
    }
}