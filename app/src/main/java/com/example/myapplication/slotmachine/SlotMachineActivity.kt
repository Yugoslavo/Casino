package com.example.myapplication.slotmachine

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.BanqueActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.Player
import com.example.myapplication.R

class SlotMachineActivity : AppCompatActivity() {
    lateinit var player: Player
    lateinit var machine: SlotMachine
    lateinit var argent_button: Button
    lateinit var mise_view: EditText

    /*
    après quelques tests on a remarqué que la chance à 0.3 possède une espérance légèrement positive
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = Player.getInstance(context = this.applicationContext)
        machine = SlotMachine(luck = player.luck)

        enableEdgeToEdge()
        setContentView(R.layout.activity_slot_machine)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        argent_button = findViewById<Button>(R.id.argent)
        // mise en place du bouton pour ouvrir la banque
        argent_button.setOnClickListener {
            openBank()
        }

        // affichage de la mise du joueur
        mise_view = findViewById<EditText>(R.id.mise_input)
        mise_view.setText(player.bet.toString())

        // Sauvegarde de la mise du joueur lorsque l'utilisateur la modifie
        mise_view.setOnEditorActionListener { _, _, _ ->
            val bet = mise_view.text.toString().toFloatOrNull()
            if (bet != null) {
                player.bet = bet
                mise_view.setText(player.bet.toString())
            }
            false
        }


        val manivelle_button: ImageButton = findViewById<ImageButton>(R.id.manivelle)
        var fruit1: ImageView = findViewById<ImageView>(R.id.fruit1)
        var fruit2: ImageView = findViewById<ImageView>(R.id.fruit2)
        var fruit3: ImageView = findViewById<ImageView>(R.id.fruit3)

        // Fonctionnalité de la manivelle de la machine à sous
        manivelle_button.setOnClickListener {
            play()
        }
        updateMoneyValue()
        findViewById<Button>(R.id.return_button).setOnClickListener {
            back()
        }
    }

    fun play(){
        var argent = argent_button.text.toString().toFloat()
        var mise_text = mise_view.text.toString()
        var mise = when(mise_text){
            "" -> 0F
            else -> mise_text.toFloat()
        }
        if (mise == 0f) {
            return
        }
        // lancer la machine à sous
        val result = machine.play(mise)
        // affichage du résultat
        argent -= mise
        argent += result
        player.addMoney(result - mise)
        argent_button.text = argent.toString()
        mise_view.setText(player.bet.toString())
        // affichage des fruits
        val fruits: Array<Fruit> = machine.fruits
        val fruit1: ImageView = findViewById<ImageView>(R.id.fruit1)
        val fruit2: ImageView = findViewById<ImageView>(R.id.fruit2)
        val fruit3: ImageView = findViewById<ImageView>(R.id.fruit3)
        fruit1.setImageResource(fruits[0].icon)
        fruit2.setImageResource(fruits[1].icon)
        fruit3.setImageResource(fruits[2].icon)
    }


    fun back(){ // Retour à l'écran principal
        startActivity(Intent(this, MainActivity::class.java))
    }



    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        updateMoneyValue()
    }

    fun openBank() {// Ouverture de la banque
        val intent = Intent(this, BanqueActivity::class.java)
        startActivity(intent)
    }

    private fun updateMoneyValue() {
        // sauvegarde bet
        val playerMoney = player.money // Default to 0
        argent_button.text = playerMoney.toString()  // Update the button
    }

}
