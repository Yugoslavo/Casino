package com.example.myapplication.blackjack

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.BanqueActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.Player
import com.example.myapplication.R

class Initblackjack : AppCompatActivity() {
    lateinit var player: Player
    object GameState {
        var betValue: Float = 0f
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_initblackjack)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        player = Player.getInstance(this)
        val currentMoney = player.money
        val moneyButton = findViewById<Button>(R.id.argent)
        moneyButton.text = "${currentMoney} €"

    }


    fun startBlackJack(view: View) {
        val intent = Intent(this, BlackjackActivity::class.java)
        startActivity(intent)
    }

    fun openBank(view: View) {
        val intent = Intent(this, BanqueActivity::class.java)
        startActivity(intent)
    }

    fun changeMise(view: View) {
        val changeMise = findViewById<Button>(R.id.Mise)
        val input = EditText(this)
        input.hint = "Mise"
        input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL

        AlertDialog.Builder(this)
            .setTitle("Enter your bet")
            .setView(input)
            .setPositiveButton("OK") { _, _ ->
                val newBet = input.text.toString()
                val value = newBet.toFloatOrNull()

                if (value != null) {
                    GameState.betValue = value //Mettre dans l'object de la variable classe
                    changeMise.text = value.toString()
                    player.money -= value

                } else {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun Back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}