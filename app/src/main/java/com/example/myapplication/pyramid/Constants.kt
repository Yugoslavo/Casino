package com.example.myapplication.pyramid

import android.content.res.Resources

object Constants {
    // Constantes de l'écra
    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels
}