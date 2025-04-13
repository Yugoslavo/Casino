package com.example.myapplication.blackjack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.BanqueActivity
import com.example.myapplication.R

class Initblackjack : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_initblackjack)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun openBank(view: View) {
        val intent = Intent(this, BanqueActivity::class.java)
        startActivity(intent)
    }

    fun startBlackJack(view: View) {
        val intent = Intent(this, Black_Jack::class.java)
        startActivity(intent)
    }

}