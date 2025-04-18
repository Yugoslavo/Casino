package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BanqueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_banque)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val player = Player.getInstance(context = this.applicationContext)
        val playerMoney = player.money
        println("Player money: $playerMoney")
        val argentTextView: TextView = findViewById(R.id.argentTextView)
        argentTextView.text  = playerMoney.toString()  // Update the button text
        findViewById<Button>(R.id.Clicker).setOnClickListener {
            earnMoney(2)
        }
        findViewById<Button>(R.id.Watcher).setOnClickListener {
            earnMoney(1)
        }
    }

    fun earnMoney(id: Int){
        val strategy: MoneyEarningStrategy = when (id) {
            1 -> WatchAd()
            2 -> ClickButton()
            else -> WatchAd() // should not happen
        }
        strategy.earnMoney(this)
    }

    override fun onResume() {
        super.onResume()
        updateMoney()
    }

    fun updateMoney() {
        val argentTextView: TextView = findViewById(R.id.argentTextView)
        argentTextView.text  = Player.getInstance(this.applicationContext).money.toString()
    }


    fun exit(view: View) {
        onBackPressed()
    }
}