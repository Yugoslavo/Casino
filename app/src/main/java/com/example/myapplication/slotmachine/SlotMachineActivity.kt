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
import com.example.myapplication.Player
import com.example.myapplication.R

class SlotMachineActivity : AppCompatActivity() {
    lateinit var player: Player
    lateinit var machine: SlotMachine

    /*
    après quelques essais, la chance à 0.3 possède une espérance légèrement positive
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
        val openBankButton: Button = findViewById<Button>(R.id.argent)
        openBankButton.setOnClickListener {
            openBank()
        }

        val mise_view: EditText = findViewById<EditText>(R.id.mise_input)
        mise_view.setText(player.bet.toString())

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

        manivelle_button.setOnClickListener {
            val argent_button: Button = openBankButton
            var argent = argent_button.text.toString().toFloat()
            var mise_text = mise_view.text.toString()
            var mise = when(mise_text){
                "" -> 0F
                else -> mise_text.toFloat()
            }
            if (mise == 0f) {
                return@setOnClickListener
            }
            var result = machine.play(mise)
            argent -= mise
            argent += result
            player.addMoney(result - mise)
            argent_button.text = argent.toString()
            mise_view.setText(player.bet.toString())
            var fruits: Array<Fruit> = machine.fruits
            fruit1.setImageResource(fruits[0].icon)
            fruit2.setImageResource(fruits[1].icon)
            fruit3.setImageResource(fruits[2].icon)
        }
        updateMoneyValue()
    }



    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        updateMoneyValue()
    }

    fun openBank() {
        //val mise: Button = findViewById(R.id.mise)
        val intent = Intent(this, BanqueActivity::class.java)
        startActivity(intent)
        //val newValue = nouvelleMise()
        //mise.text = "$newValue"
    }

    private fun updateMoneyValue() {
        val bank: Button = findViewById(R.id.argent)
        // sauvegarde bet
        val playerMoney = player.money // Default to 0
        bank.text = playerMoney.toString()  // Update the button
    }

}
