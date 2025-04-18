package com.example.myapplication

import android.content.Context
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class ClickButton : MoneyEarningStrategy {
    // Classe qui implémente la stratégie de gagner de l'argent en cliquant n fois sur un bouton
    override fun earnMoney(context: Context){
        val player = Player.getInstance(context = context.applicationContext)
        val button = Button(context) // Création du bouton
        val n = (5..50).random() // Nombre aléatoire de clics requis
        button.text = n.toString()

        // Création de la boîte de dialogue avec le bouton
        val alert = AlertDialog.Builder(context, R.style.Theme_MyApplication)
            .setTitle("Click $n times")
            .setView(button).setOnDismissListener {
                (context as BanqueActivity).updateMoney()
            }.show()

        // Gestion de l'événement de clic sur le bouton
        button.setOnClickListener {
            var k = button.text.toString().toInt()
            if (k == 0){
                player.addMoney(100f) // Ajout de 100 euros à l'argent du joueur
                Toast.makeText(context, "You earned 100 money!", Toast.LENGTH_SHORT).show() // notification à l'utilisateur du gain
                alert.dismiss()//Ferme la boîte de dialogue -> retour à l'écran banque
            } else {
                k -= 1 // Comptage à rebours du nombre de clics restants
                button.text = k.toString()
            }
        }
    }
}