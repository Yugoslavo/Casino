package com.example.myapplication.pyramid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView

class PyramidView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    // C'est une vue qu'on va pouvoir mettre dans le layout d'activity_pyramid.xml
    // et qui va servir à toute la partie du jeu avec les balles

    private lateinit var thread: Thread
    private var drawing = false
    var canvas = Canvas()
    val paint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f
    var totalElapsedTime = 0.0
    private lateinit var activity: PyramidActivity

    // Position des différents obstacles
    val obstaclePosX = mutableListOf<Int>(
        0, -80, 80, -160, 0, 160, -240, -80, 80, 240, -320, -160, 0, 160, 320
    )
    val obstaclePosY = mutableListOf<Int>(
        0, 80, 80, 160, 160, 160, 240, 240, 240, 240, 320, 320, 320, 320, 320
    )


    var everyObjects : EveryObjects


    init {
        everyObjects = EveryObjects()

        // Création des obstacles
        for (i in 0 until obstaclePosX.size){
            var obstacle = RoundObstacle(x = Constants.screenWidth / 2f + obstaclePosX[i], y = Constants.screenHeight / 4f + obstaclePosY[i])
            everyObjects.obstacleList.add(obstacle)
        }
        var wall1 = Wall(x = 0f, y = Constants.screenHeight / 2f, width = 5f, height = Constants.screenHeight.toFloat())
        var wall2 = Wall(x = Constants.screenWidth.toFloat() , y = Constants.screenHeight / 2f, width = 5f, height = Constants.screenHeight.toFloat())
        everyObjects.obstacleList.add(wall1)
        everyObjects.obstacleList.add(wall2)

    }

    // On doit implémenter la méthode setActivity pour pouvoir appeler la méthode ballHit de l'activité
    fun setActivity(activity: PyramidActivity) {
        this.activity = activity
    }

    // Si la taille de la surface change, on met à jour la taille de l'écran
    // Je suis pas sur que ce soit très utile
    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            // Opérations effectuées à chaque "tick" de la boucle de jeu
            val currentTime = System.currentTimeMillis()
            var elapsedTimeMS: Double = (currentTime - previousFrameTime).toDouble()
            totalElapsedTime += elapsedTimeMS / 1000.0
            updatePositions(elapsedTimeMS)
            previousFrameTime = currentTime

            // On met à jour la position des objets (balles) et on les dessine
            updatePositions(elapsedTimeMS)
            draw()
        }
    }

    fun updatePositions(elapsedTimeMS: Double) {
        everyObjects.ballList.filter { ball -> ball.y > Constants.screenHeight / 4f + 360 }.forEach {
                ball ->
                // Si les balles sont en dessous de la limite, on envoie le résultat à l'activité
                activity.ballHit(ball.x,ball.bet)
                everyObjects.obstacleList.forEach { obstacle -> obstacle.detach(ball) }

        }
        everyObjects.ballList.removeIf {
            // Suppression des balles qui sont arrivées en bas de l'écran
            ball -> ball.y > Constants.screenHeight / 4f + 360
        }
        for (ball in everyObjects.ballList){
            // On met à jour la position de chaque balle
            ball.move(elapsedTimeMS)
        }
        for (obstacle in everyObjects.obstacleList){
            // On fait en sorte que les obstacles vérifient si les balles sont en contact avec eux
            obstacle.callObservers()
        }
    }

    // Ajoute une balle sur l'écran
    fun addBall(bet:Float) {
        //compte des balles qui peut être utilisé pour limiter le nombre de balles en même temps
//        val activeBalls = everyObjects.ballList.count {
//            it.isMoving
//        }

        // On crée une nouvelle balle avec la mise du joueur
        // On lui donne une position aléatoire sur l'axe des abscisses
        // afin d'éviter que toutes les balles soient au même endroit
        val offsetX = (-50..50).random()
        val startX = Constants.screenWidth / 2f +offsetX
        var newBall = Ball(bet, x = startX)
        everyObjects.obstacleList.forEach {
            it.attach(newBall)// Ajout de la balle à la liste des observateurs des obstacles
        }
        everyObjects.ballList.add(newBall) // Ajout de la balle à la liste des balles
        everyObjects.ballList.last().isMoving = true // mise en mouvement de la balle
    }

    private fun draw() {
        // Dessin de toutes les balles et des obstacles
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            for (figure in everyObjects.ballList + everyObjects.obstacleList) {
                figure.draw(canvas, paint)
            }
            holder.unlockCanvasAndPost(canvas)
        }
    }

    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                width: Int, height: Int) {}

    override fun surfaceCreated(holder: SurfaceHolder) {
        resume()  // Lance le thread quand la surface est prête
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        pause()  // Arrête le thread lorsque la surface est détruite
    }

}