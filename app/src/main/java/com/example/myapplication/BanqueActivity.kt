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
        val player = Player.getInstance(context = this.applicationContext)
        val playerMoney = player.money
        println("Player money: $playerMoney")
        val myTextView: TextView = findViewById(R.id.myTextView)
        myTextView.text  = playerMoney.toString()  // Update the button text
    }

    private var decreaseValue = 50f
    fun Diminue(view: View) {
        val myTextView: TextView = findViewById(R.id.myTextView)
        // Get bet
        val currentBet = myTextView.text.toString().toFloatOrNull() ?: 0f
        // Increase bet by 100
        if (currentBet - decreaseValue <= 0f) return

        val newBet =  currentBet - decreaseValue
        // Update the TextView
        myTextView.text = newBet.toString()

        // Save the new value to Player
        val player = Player.getInstance(context = this.applicationContext)
        player.addMoney(-1*decreaseValue.toFloat())
    }

    fun Augmente(view: View) {
        val myTextView: TextView = findViewById(R.id.myTextView)
        // Get the current bet
        val currentBet = myTextView.text.toString().toFloatOrNull() ?: 0f
        // Increase the bet by 100
        val newBet = currentBet + decreaseValue
        // Update the TextView
        myTextView.text = newBet.toString()

        // Save the new value to player
        val player = Player.getInstance(context = this.applicationContext)
        player.addMoney(decreaseValue.toFloat())
    }
    fun ChangeValue50(view: View) {
        decreaseValue = 50f
    }

    fun ChangeValue25(view: View) {
        decreaseValue = 25f
    }

    fun ChangeValue15(view: View) {
        decreaseValue = 15f
    }

    fun getTextFromEditText(view: View) {
        val editText: EditText = findViewById(R.id.myEditText)
        val userInput = editText.text.toString()
        val intValue = userInput.toFloatOrNull()
        if (intValue == null) {
            Toast.makeText(this, "Integer please!", Toast.LENGTH_SHORT).show()
            decreaseValue = 0f
        } else {
            decreaseValue = intValue
        }
    }

    fun exit(view: View) {
        onBackPressed()
    }
}