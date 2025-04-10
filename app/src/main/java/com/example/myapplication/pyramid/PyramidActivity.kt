package com.example.myapplication.pyramid


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.myapplication.BanqueActivity
import com.example.myapplication.Player
import com.example.myapplication.R

class PyramidActivity : AppCompatActivity() {
    private lateinit var pyramideView: PyramidView
    private lateinit var betInput: EditText
    private lateinit var playButton: Button
    private lateinit var bankButton: Button
    private lateinit var moneyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the theme to light mode
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        // Set the content view to the layout for this activity
        setContentView(R.layout.activity_pyramid)

        val player = Player.getInstance(context = this.applicationContext)
        // Initialize the PyramideView and other UI elements
        pyramideView = findViewById(R.id.pyramide_view)
        pyramideView.setActivity(this)
        betInput = findViewById(R.id.bet_input)
        betInput.setText(player.bet.toString())
        
        // Ajout du fonctionnement de l'input pour la mise
        betInput.setOnEditorActionListener { _, _, _ ->
            val bet = betInput.text.toString().toFloatOrNull()
            if (bet != null) {
                player.bet = bet
                betInput.setText(player.bet.toString())
            }
            false
        }
        
        // Initialize the TextView for displaying money
        moneyTextView = findViewById<TextView>(R.id.money_text)
        moneyTextView.text = player.money.toString()

        // Initialize the buttons
        playButton = findViewById(R.id.play_button)
        bankButton = findViewById(R.id.bank_button)
        // Fonctionnalité du bouton play
        playButton.setOnClickListener {
            if (player.bet == 0f){
                // Si la mise est nulle, on ne fait rien
                return@setOnClickListener
            }
            pyramideView.addBall(player.bet) // Ajout d'une balle qui contient une certaine mise
            player.addMoney(-1*player.bet) // On retire la mise du joueur
            moneyTextView.text = player.money.toString() // On met à jour l'affichage de l'argent
        }

        //functionality for the bank button 
        bankButton.setOnClickListener {
            // Ouverture de la banque
            val intent = Intent(this, BanqueActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPause() {
        super.onPause()
        pyramideView.pause()
        // Sauvegarde de la dernière activité dans les SharedPreferences afin que la banque puisse savoir où on était avant
        val sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)
        sharedPreferences.edit() {
            putString("last_activity", "PyramidActivity")
        }
    }

    override fun onResume() {
        super.onResume()
        pyramideView.resume()
    }

    fun ballHit(x:Float, ballBet: Float){
        // Fonction appelée lorsque la balle arrive en bas du jeu,
        // x est la position de la balle ce qui détermine le gain du joueur
        val player = Player.getInstance(context = this.applicationContext)
        val result = when {
            x < Constants.screenWidth/2 - 360f -> 3f*ballBet
            x < Constants.screenWidth/2 - 180f -> 1.5f*ballBet
            x < Constants.screenWidth/2 -> 0.5f
            x < Constants.screenWidth/2 + 180f -> 0.5f*ballBet
            x < Constants.screenWidth/2 + 360f -> 1.5f*ballBet
            else -> 3f*ballBet
        }
        // Ajout de l'argent chez le joueur
        player.addMoney(result)
        // On met à jour l'affichage de l'argent
        moneyTextView.text = player.money.toString()
    }
}