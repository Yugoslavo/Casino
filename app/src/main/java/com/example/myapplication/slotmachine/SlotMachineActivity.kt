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
import com.example.myapplication.R

class SlotMachineActivity : AppCompatActivity() {
    val machine: SlotMachine = SlotMachine(luck = 0.3F)
    //
    /*
    après quelques essais, la chance à 0.3 possède une espérance légèrement positive
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val manivelle_button: ImageButton = findViewById<ImageButton>(R.id.manivelle)
        var fruit1: ImageView = findViewById<ImageView>(R.id.fruit1)
        var fruit2: ImageView = findViewById<ImageView>(R.id.fruit2)
        var fruit3: ImageView = findViewById<ImageView>(R.id.fruit3)
        manivelle_button.setOnClickListener {
            val argent_button: Button = openBankButton
            var argent = argent_button.text.toString().toFloat()
            val mise_view: EditText = findViewById<EditText>(R.id.mise_input)
            var mise_text = mise_view.text.toString()
            var mise = when(mise_text){
                "" -> 0F
                else -> mise_text.toFloat()
            }
            if (argent < mise){
                mise = argent
                mise_view.setText(mise.toString())
            }
            var result = machine.play(mise)
            argent -= mise
            argent += result
            argent_button.text = argent.toString()
            println("Tu as comme argent : $argent")
            var fruits: Array<Fruit> = machine.fruits
            fruit1.setImageResource(fruits[0].icon)
            fruit2.setImageResource(fruits[1].icon)
            fruit3.setImageResource(fruits[2].icon)
        }
        updateBetValue()
    }
    override fun onResume() {
        super.onResume()
        updateBetValue()
    }

    fun openBank() {
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
        val mise: Button = findViewById(R.id.argent)
        // sauvegarde bet
        val sharedPref = getSharedPreferences("BetPrefs", MODE_PRIVATE)
        val savedBet = sharedPref.getInt("bet_value", 0) // Default to 0
        mise.text = savedBet.toString()  // Update the button
    }

}