package com.example.myapplication

import android.content.res.Resources

object Constants {     val screenWidth: Int
    get() = Resources.getSystem().displayMetrics.widthPixels
    val screenHeight: Int
        get() = Resources.getSystem().displayMetrics.heightPixels
}
