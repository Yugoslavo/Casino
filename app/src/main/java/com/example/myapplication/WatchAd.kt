package com.example.myapplication


import android.content.Context
import android.net.Uri
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri

class WatchAd : MoneyEarningStrategy {
    override fun earnMoney(context: Context) {
        val player = Player.getInstance(context = context.applicationContext)
        println(context.toString())
        val video = VideoView(context)
        val videoUri = "android.resource://${context.packageName}/raw/ad_video".toUri()
        video.setVideoURI(videoUri)
        val alert = AlertDialog.Builder(context, R.style.Theme_MyApplication)
            .setTitle("Regarde cette vidéo jusqu'au bout pour gagner 1000 €")
            .setView(video)
            .setNegativeButton ("abandonner", null)
            .show()
        video.start()
        video.setOnCompletionListener {
            player.addMoney(1000f)
            Toast.makeText(context, "You earned 1000 money!", Toast.LENGTH_SHORT).show()
            alert.dismiss()
            (context as BanqueActivity).updateMoney()
        }
    }
}