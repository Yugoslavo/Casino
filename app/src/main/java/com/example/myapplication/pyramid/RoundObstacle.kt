package com.example.myapplication.pyramid

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.sqrt

class RoundObstacle(override var x: Float = Constants.screenWidth / 2f, override var y: Float = 400f): Obstacle() {
    // Cette classe représente les obstacles ronds qui constituent la pyramide
    override fun draw(canvas: Canvas, paint: Paint) {
        // Dessin de l'obstacle
        paint.color = Color.GRAY
        canvas.drawCircle(x, y , 10f, paint)
    }

    override fun createUpdateFunction(): (Float, Float) -> (Float, Float) -> Array<Float> {
        return {
                ballX: Float, ballY:Float ->
            var dx = this.x - ballX
            var dy = this.y - ballY
            var distance = sqrt(((dx * dx + dy * dy).toDouble())).toFloat()
            // On calcule la distance entre la balle et l'obstacle pour voir s'il y a collision
            if (distance <= 20f) {
                // il y a collision, on va donc changer la vitesse de la balle
                var update = {
                        speedX: Float, speedY: Float ->
                    // vitesse de la balle
                    var fullSpeed = sqrt((speedX * speedX + speedY * speedY).toDouble()).toFloat()
                    // perte d'énergie
//                    fullSpeed = fullSpeed * 0.5f
                    var newSpeedX: Float = -fullSpeed*dx / (distance)
                    var newSpeedY: Float = -fullSpeed*dy / (distance)
                    arrayOf(newSpeedX,newSpeedY)
                }
                update
            } else {
                // pas de collision, on ne change pas la vitesse
                var update = {speedX: Float, speedY: Float ->
                    var newSpeed = arrayOf(speedX, speedY)
                    newSpeed
                }
                update
            }
        }
    }
}