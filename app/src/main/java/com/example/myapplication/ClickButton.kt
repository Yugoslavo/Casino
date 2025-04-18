package com.example.myapplication

import android.content.Context
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ClickButton : MoneyEarningStrategy {
    override fun earnMoney(context: Context){
        val player = Player.getInstance(context = context.applicationContext)
        println(context.toString())
        val button = Button(context)
        val n = (5..50).random()
        button.text = n.toString()
        val alert = AlertDialog.Builder(context, R.style.Theme_MyApplication)
            .setTitle("Click $n times")
            .setView(button).show()
        button.setOnClickListener {
            var k = button.text.toString().toInt()
            if (k == 0){
                player.addMoney(100f)
                Toast.makeText(context, "You earned 100 money!", Toast.LENGTH_SHORT).show()
                alert.dismiss()
            } else {
                k -= 1
                button.text = k.toString()
            }
        }
    }
}