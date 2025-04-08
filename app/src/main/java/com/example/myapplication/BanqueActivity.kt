package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.slotmachine.SlotMachineActivity

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
        val savedBet = sharedPref.getInt("bet_value", 0)
        val myTextView: TextView = findViewById(R.id.myTextView)
        myTextView.text  = savedBet.toString()  // Update the button text
    }
    private var decreaseValue = 50
    fun Diminue(view: View) {
        val myTextView: TextView = findViewById(R.id.myTextView)
        // Get bet
        val currentBet = myTextView.text.toString().toIntOrNull() ?: 0
        // Increase bet by 100
        if (currentBet <= 0) return

        val newBet =  currentBet - decreaseValue
        // Update the TextView
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
        // Get the current bet
        val currentBet = myTextView.text.toString().toIntOrNull() ?: 0
        // Increase the bet by 100
        val newBet = currentBet + decreaseValue
        // Update the TextView
        myTextView.text = newBet.toString()

        // Save the new value to SharedPreferences
        val sharedPref = getSharedPreferences("BetPrefs", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("bet_value", newBet)
            apply()
        }
    }
    fun ChangeValue50(view: View) {
        decreaseValue = 50
    }

    fun ChangeValue25(view: View) {
        decreaseValue = 25
    }

    fun ChangeValue15(view: View) {
        decreaseValue = 15
    }
    fun getTextFromEditText(view: View) {
        val editText: EditText = findViewById(R.id.myEditText)
        val userInput = editText.text.toString()
        val intValue = userInput.toIntOrNull()
        if (intValue == null) {
            Toast.makeText(this, "Integer please!", Toast.LENGTH_SHORT).show()
            decreaseValue = 0
        } else {
            decreaseValue = intValue }

    }
    fun exit(view: View) {
        val intent = Intent(this, SlotMachineActivity::class.java)
        startActivity(intent)
    }
}