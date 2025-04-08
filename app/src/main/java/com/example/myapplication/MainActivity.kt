package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
        var slot_machine_button = findViewById<Button>(R.id.button)
        slot_machine_button.setOnClickListener{
            startActivity(Intent(this, SlotMachineActivity::class.java))
        }
        var jeux2 = findViewById<Button>(R.id.button2)
        jeux2.setOnClickListener{
            startActivity(Intent(this,black_jack::class.java))

        }

    }

}
