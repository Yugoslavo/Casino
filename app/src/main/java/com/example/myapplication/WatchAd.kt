package com.example.myapplication


import android.content.Context
import android.net.Uri
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri

class WatchAd : MoneyEarningStrategy {
    // Classe qui implémente la stratégie de gagner de l'argent en regardant une publicité
    override fun earnMoney(context: Context) {
        val player = Player.getInstance(context = context.applicationContext)
        println(context.toString())
        // Création d'une boîte de dialogue avec une vidéo
        val video = VideoView(context)
        val videoUri = "android.resource://${context.packageName}/raw/${mutableListOf<String>("godet", "bouillard").random<String>()}".toUri()
        video.setVideoURI(videoUri)
        val alert = AlertDialog.Builder(context, R.style.Theme_MyApplication)
            .setTitle("Regarde cette vidéo jusqu'au bout pour gagner 1000 €")
            .setView(video)
            .setNegativeButton ("abandonner", null)
            // Permet de fermer la boîte de dialogue sans regarder la vidéo mais sans gagner d'argent
            .show()
        video.start()

        // Gestion de l'événement de fin de vidéo
        video.setOnCompletionListener {
            //Gain d'argent après avoir regardé la vidéo
            player.addMoney(1000f)
            Toast.makeText(context, "You earned 1000 money!", Toast.LENGTH_SHORT).show()
            alert.dismiss() // Ferme la boîte de dialogue
            (context as BanqueActivity).updateMoney() // Met à jour l'affichage de l'argent du joueur
        }
    }
}