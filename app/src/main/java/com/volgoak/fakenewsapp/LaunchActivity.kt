package com.volgoak.fakenewsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
