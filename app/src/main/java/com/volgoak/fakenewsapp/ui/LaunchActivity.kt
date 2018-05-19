package com.volgoak.fakenewsapp.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

/**
 * Стартовое активити
 */
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
