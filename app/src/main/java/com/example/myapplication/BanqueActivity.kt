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
        //Initialisation de l'activité
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_banque)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Affichage de l'argent du joueur
        val player = Player.getInstance(context = this.applicationContext)
        val playerMoney = player.money
        val argentTextView: TextView = findViewById(R.id.argentTextView)
        argentTextView.text  = playerMoney.toString()

        //Fonctionnalité des boutons pour gagner de l'argent
        findViewById<Button>(R.id.Clicker).setOnClickListener {
            earnMoney(2)
        }
        findViewById<Button>(R.id.Watcher).setOnClickListener {
            earnMoney(1)
        }
    }

    fun earnMoney(id: Int){
        // Fonction appelée lorsque le joueur clique sur un bouton pour gagner de l'argent
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
        //met à jour l'affichage de l'argent du joueur
        val argentTextView: TextView = findViewById(R.id.argentTextView)
        argentTextView.text  = Player.getInstance(this.applicationContext).money.toString()
    }


    fun exit(view: View) {
        // Fonction appelée lorsque le joueur clique sur le bouton "Exit"
        onBackPressed()
    }
}