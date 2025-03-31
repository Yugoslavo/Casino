package com.example.myapplication.slotmachine

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R

class SlotMachineActivity : AppCompatActivity() {
    var machine: SlotMachine = SlotMachine(luck = 1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_slot_machine)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val manivelle_button: ImageButton = findViewById<ImageButton>(R.id.manivelle)
        var fruit1: ImageView = findViewById<ImageView>(R.id.fruit1)
        var fruit2: ImageView = findViewById<ImageView>(R.id.fruit2)
        var fruit3: ImageView = findViewById<ImageView>(R.id.fruit3)
        manivelle_button.setOnClickListener {
            machine.play(10F)
            var fruits: Array<Fruit> = machine.fruits
            fruit1.setImageResource(fruits[0].icon)
            fruit2.setImageResource(fruits[1].icon)
            fruit3.setImageResource(fruits[2].icon)
        }

    }
}