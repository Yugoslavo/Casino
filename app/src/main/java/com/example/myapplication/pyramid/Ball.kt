package com.example.myapplication.pyramid

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Ball (var bet:Float, override var x: Float = Constants.screenWidth / 2f, override var y: Float = Constants.screenHeight/2f - 100): Figure, Observer {
    var isMoving = false
    private var radius = 10f // rayon de la balle
    private var gravity = 80f // gravité -> à régler comme on veut
    private var vSpeed = 0f // vitesse verticale
    private var hSpeed = 0f // vitesse horizontale mise à 0.1 afin que la balle ne reste pas coincée

    override fun draw (canvas: Canvas, paint: Paint){
        // Dessing de la balle
        paint.color = Color.WHITE
        canvas.drawCircle(x, y, radius + 4f, paint)
        paint.color = Color.RED
        canvas.drawCircle(x, y, radius, paint)
    }

    fun move(elapsedTimeMS: Double) {
        if (isMoving){
            // Met à jour la position de la balle

            y += vSpeed*elapsedTimeMS.toFloat()/1000f + gravity*elapsedTimeMS.toFloat()*elapsedTimeMS.toFloat()/2000000f
            vSpeed += gravity*elapsedTimeMS.toFloat()/1000f
            x += hSpeed*elapsedTimeMS.toFloat()/1000f
            //"frottement dans l'air"
            vSpeed = 0.99f*vSpeed
            hSpeed = 0.99f *hSpeed
        }
    }

    override fun update(updateSpeed:(Float,Float) -> ((Float, Float) -> Array<Float>)) {
        // Met à jour la vitesse de la balle si elle touche un obstacle
        var newSpeed = updateSpeed(x, y)(hSpeed, vSpeed)
        this.hSpeed = newSpeed[0]
        this.vSpeed = newSpeed[1]
    }
}