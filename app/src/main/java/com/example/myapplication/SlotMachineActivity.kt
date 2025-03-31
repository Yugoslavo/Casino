package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SlotMachineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_slot_machine)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        updateBetValue()
    }

    override fun onResume() {
        super.onResume()
        updateBetValue()
    }

    fun openBank(view: View) {
        //val mise: Button = findViewById(R.id.mise)
        val intent = Intent(this, BanqueActivity::class.java)
        startActivity(intent)
        //val newValue = nouvelleMise()
        //mise.text = "$newValue"
    }
    //fun nouvelleMise(): Int {
    //    return 12
    // }


    private fun updateBetValue() {
        val mise: Button = findViewById(R.id.mise)
        // sauvegarde bet
        val sharedPref = getSharedPreferences("BetPrefs", MODE_PRIVATE)
        val savedBet = sharedPref.getInt("bet_value", 0) // Default to 0
        mise.text = savedBet.toString()  // Update the button
    }
}