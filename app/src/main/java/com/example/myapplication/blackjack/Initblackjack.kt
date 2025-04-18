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
        val changeMise = findViewById<EditText>(R.id.Mise)
        changeMise.setText(player.bet.toString())

        // Ajout du fonctionnement de l'input pour la mise
        changeMise.setOnEditorActionListener { _, _, _ ->
            val bet = changeMise.text.toString().toFloatOrNull()
            if (bet != null) {
                player.bet = bet
                changeMise.setText(player.bet.toString())
            }
            false
        }
    }


    fun startBlackJack(view: View) {
        val intent = Intent(this, BlackjackActivity::class.java)
        startActivity(intent)
    }

    fun openBank(view: View) {
        val intent = Intent(this, BanqueActivity::class.java)
        startActivity(intent)
    }

    fun Back(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


}