package com.example.myapplication.pyramid

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import kotlin.math.abs
import kotlin.math.sign
import kotlin.math.sqrt

class RoundObstacle(override var x: Float = Constants.screenWidth / 2f, override var y: Float = 400f): Obstacle() {
    // Cette classe représente les obstacles ronds qui constituent la pyramide

    override fun draw(canvas: Canvas, paint: Paint) {
        // Dessin de l'obstacle
        paint.color = Color.GRAY
        canvas.drawCircle(x, y , 20f, paint)
    }

    override fun callObservers() {
        // Appelle les observateurs pour mettre à jour la vitesse de la balle
        // On crée donc une fonction qui va à partir de la position de la balle et de sa vitesse
        // déterminer la nouvelle vitesse de la balle après collision
        var updateSpeed = {
            ballX: Float, ballY:Float ->
            var dx = this.x - ballX
            var dy = this.y - ballY
            var distance = sqrt(((dx * dx + dy * dy).toDouble())).toFloat()
            // On calcule la distance entre la balle et l'obstacle pour voir s'il y a collision
            if (distance <= 40f) {
                // il y a collision, on va donc changer la vitesse de la balle
                var update = {
                    speedX: Float, speedY: Float ->
                    // vitesse de la balle
                    var fullSpeed = sqrt((speedX * speedX + speedY * speedY).toDouble()).toFloat()
                    // perte d'énergie
                    fullSpeed = fullSpeed * 0.3f
                    // changement de la vitesse pour qu'elle rebondisse
                    var newSpeedX: Float = -fullSpeed*dx / (distance)
                    var newSpeedY: Float = -fullSpeed*dy / (distance)
                    if (fullSpeed < 1f) {
                        //débloque la balle si elle est coincée
                        if (abs(dx*-2f) < 10f) {
                            newSpeedX = 10f
                        }else {
                            newSpeedX = dx*-2f
                        }
                        newSpeedY = -40f
                    }
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
        try {
            for (i in observers.size - 1 downTo 0) {
                // On appelle la méthode update de chaque observateur
                // pour mettre à jour la vitesse des balles
                observers[i].update(updateSpeed)
            }
        }catch (e: Exception){
            // On ignore les exceptions
            // Cela arrive quand on retire une balle de la liste des observateurs
            // et qu'on essaie de l'appeler
        }
    }

}