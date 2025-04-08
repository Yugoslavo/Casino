package com.example.myapplication

import android.app.Activity
import android.os.Bundle


class GameActivity : Activity() {
    private lateinit var pyramideView: PyramideView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pyramideView = PyramideView(this)
        setContentView(pyramideView)
    }

    override fun onResume() {
        super.onResume()
        pyramideView.resume()
    }

    override fun onPause() {
        super.onPause()
        pyramideView.pause()

    }
}
