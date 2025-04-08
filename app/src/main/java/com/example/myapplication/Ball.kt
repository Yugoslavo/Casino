package com.example.myapplication

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.provider.SyncStateContract

class Ball {
    private var x : Float = Constants.screenWidth / 2f
    var y = 300f
    var isMoving = false
    private var radius = 20f
    private var gravity = 2f

    fun draw (canvas: Canvas, paint: Paint){
        paint.color = Color.WHITE
        canvas.drawCircle(x, y, radius + 4f, paint)
        paint.color = Color.RED
        canvas.drawCircle(x, y, radius, paint)
    }

    fun move(){
        if (isMoving){
            y += gravity
        }
    }
}
