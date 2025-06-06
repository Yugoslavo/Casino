package com.example.myapplication.pyramid

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.abs
import kotlin.math.sign

class Wall(override var x: Float = Constants.screenWidth.toFloat(), override var y: Float = 400f, var width: Float = 1f, var height: Float = Constants.screenHeight/2f): Obstacle() {



    override fun draw(canvas: Canvas, paint: Paint) {
        // Draw the wall
        paint.color = Color.BLACK
        canvas.drawRect(x - this.width/2, y - this.height / 2, x + this.width/2, y + this.height/2, paint)
    }

    override fun createUpdateFunction(): (Float, Float) -> (Float, Float) -> Array<Float> {
        return {
                ballX: Float, ballY:Float ->
            var dx = this.x - ballX // distance entre la balle et le mur
            if (abs(dx) <= 10f+ this.width/2) {
                var update = {
                        speedX: Float, speedY: Float ->
                    // S'il y a collision on change la vitesse horizontalle de la balle
                    var newSpeedX: Float = -1*abs(speedX)*sign(dx)
                    var newSpeedY: Float = speedY

                    arrayOf(newSpeedX,newSpeedY)
                }
                update
            } else {
                var update = {speedX: Float, speedY: Float ->
                    var newSpeed = arrayOf(speedX, speedY)
                    newSpeed

                }
                update
            }
        }
    }
}