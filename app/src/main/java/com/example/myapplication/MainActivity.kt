package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.blackjack.Initblackjack
import com.example.myapplication.pyramid.PyramidActivity
import com.example.myapplication.slotmachine.SlotMachineActivity

// Testing

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // création des boutons pour accéder aux différents jeux
        var slotMachineButton = findViewById<Button>(R.id.button)
        slotMachineButton.setOnClickListener{
            startActivity(Intent(this, SlotMachineActivity::class.java))
        }
        var blackJackButton = findViewById<Button>(R.id.button2)
        blackJackButton.setOnClickListener{
            startActivity(Intent(this, Initblackjack::class.java))
        }

        var pyramideButton = findViewById<Button>(R.id.button3)
        pyramideButton.setOnClickListener{
            startActivity(Intent(this, PyramidActivity::class.java))
        }
    }

}
