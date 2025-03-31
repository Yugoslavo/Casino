package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
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

        val sharedPref = getSharedPreferences("BetPrefs", MODE_PRIVATE)
        val savedBet = sharedPref.getInt("bet_value", 0) // Default to 0 if not found
        val myTextView: TextView = findViewById(R.id.myTextView)
        myTextView.text  = savedBet.toString()  // Update the button text
    }

    fun Diminue(view: View) {
        val myTextView: TextView = findViewById(R.id.myTextView)
        // Get the current bet amount from the TextView
        val currentBet = myTextView.text.toString().toIntOrNull() ?: 0
        // Increase the bet by 100
        if (currentBet <= 0) return

        val newBet =  currentBet - 100
        // Update the TextView with the new bet amount
        myTextView.text = newBet.toString()

        // Save the new value to SharedPreferences
        val sharedPref = getSharedPreferences("BetPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("bet_value", newBet)
            apply()
        }
    }

    fun Augmente(view: View) {
        val myTextView: TextView = findViewById(R.id.myTextView)
        // Get the current bet amount from the TextView
        val currentBet = myTextView.text.toString().toIntOrNull() ?: 0
        // Increase the bet by 100
        val newBet = currentBet + 100
        // Update the TextView with the new bet amount
        myTextView.text = newBet.toString()

        // Save the new value to SharedPreferences
        val sharedPref = getSharedPreferences("BetPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("bet_value", newBet)
            apply()
        }
    }

    fun exit(view: View) {
        val intent = Intent(this, SlotMachineActivity::class.java)
        startActivity(intent)
    }
}