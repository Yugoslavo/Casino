package com.example.myapplication.pyramid


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.myapplication.BanqueActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.Player
import com.example.myapplication.R

class PyramidActivity : AppCompatActivity() {
    private lateinit var pyramideView: PyramidView
    private lateinit var betInput: EditText
    private lateinit var playButton: Button
    private lateinit var bankButton: Button
    private lateinit var moneyTextView: TextView
//    private var results = arrayOf<Float>(0f, 0f, 0f, 0f, 0f, 0f, 0f) //Utilisé pour les tests


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
//            for (i in 1..50) pyramideView.addBall(5f) //Utilisé pour faire des tests
            pyramideView.addBall(player.bet) // Ajout d'une balle qui contient une certaine mise
            player.addMoney(-1*player.bet) // On retire la mise du joueur
            moneyTextView.text = player.money.toString() // On met à jour l'affichage de l'argent
            betInput.setText(player.bet.toString())
        }

        //functionality for the bank button 
        bankButton.setOnClickListener {
            // Ouverture de la banque
            val intent = Intent(this, BanqueActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.return_button).setOnClickListener {
            back()
        }
    }

    fun back(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onPause() {
        super.onPause()
        pyramideView.pause()
    }

    override fun onResume() {
        super.onResume()
        pyramideView.resume()
        updateMoneyValue()
    }

    private fun updateMoneyValue() {
        // sauvegarde bet
        val player = Player.getInstance(context = this.applicationContext)
        val playerMoney = player.money // Default to 0
        moneyTextView.text = playerMoney.toString()  // Update the button
    }

    fun ballHit(ballResult: Float){
        // Fonction appelée lorsque la balle arrive en bas du jeu,
        // x est la position de la balle ce qui détermine le gain du joueur
        val player = Player.getInstance(context = this.applicationContext)
//        var result = 3f;
        //résultats de
        // 4.0, 50.0, 111.0, 107.0, 29.0, 9.0
//        println("Resulst: ${results[0]}, ${results[1]}, ${results[2]}, ${results[3]}, ${results[4]}, ${results[5]}")

        // Ajout de l'argent chez le joueur
        player.addMoney(ballResult)
        // On met à jour l'affichage de l'argent
        moneyTextView.text = player.money.toString()
    }
}