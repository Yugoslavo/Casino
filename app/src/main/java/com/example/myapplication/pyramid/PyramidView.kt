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

    private var moneyGave = 0f
    private var moneyGained = 0f
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
        -40, 40, -80, 0, 80, -120, -40, 40,120, -160, -80, 0, 80, 160, -200, -120, - 40, 40, 120, 200, -240, - 160, - 80, 0, 80, 160, 240, -280, -200, -120, -40, 40, 120, 200, 280
    )
    val lastRowObstacle = obstaclePosX.subList(27, 34)
    val obstaclePosY = mutableListOf<Int>(
        0, 0, 50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 150, 200, 200, 200, 200, 200, 200, 250, 250, 250, 250, 250, 250, 250,  300, 300, 300, 300, 300, 300, 300, 300
    )

    val textPosx = mutableListOf<Int>(
        -320, -240, -160,-80,  0, 80, 160, 240, 320
    )

    val textPosy = 350f
    //Espérence de ~0.7
    val multipliers = mutableListOf<Float>(
        3f,1.5f,1f,0.5f, 0.3f, 0.5f, 1f, 1.5f, 3f
    )

    var everyObjects : EveryObjects

    init {
        println(lastRowObstacle)
        everyObjects = EveryObjects.getInstance()

        // Création des obstacles
        for (i in 0 until obstaclePosX.size){
            var obstacle = RoundObstacle(x = Constants.screenWidth / 2f + obstaclePosX[i], y = Constants.screenHeight / 2f + obstaclePosY[i])
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
        everyObjects.ballList.filter { ball -> ball.y > Constants.screenHeight / 2f + 320 }.forEach {
                ball ->
                // Si les balles sont en dessous de la limite, on envoie le résultat à l'activité
                moneyGave += ball.bet
                val res = result(ball.x)*ball.bet
                moneyGained += res
                activity.ballHit(res)
                val esp = moneyGained/moneyGave
                println("espérence : $esp")
                everyObjects.obstacleList.forEach { obstacle -> obstacle.detach(ball) }
        }

        everyObjects.ballList.removeAll {
            // Suppression des balles qui sont arrivées en bas de l'écran
            ball -> ball.y > Constants.screenHeight / 2f + 320
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

    fun result(posx: Float): Float{
        for (i  in 0 until lastRowObstacle.size - 1){
            if (posx < Constants.screenWidth/2 + lastRowObstacle[i]){
                return multipliers[i]
            }
        }
        return multipliers[0]
    }

    // Ajoute une balle sur l'écran
    fun addBall(bet:Float) {

        // On crée une nouvelle balle avec la mise du joueur
        // On lui donne une position aléatoire sur l'axe des abscisses
        // afin d'éviter que toutes les balles soient au même endroit
        var offsetX = (-39..39).random()
        if (offsetX == 0){
            offsetX = 1
        }
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
            canvas.drawColor(Color.BLUE)
            for (figure in everyObjects.ballList + everyObjects.obstacleList) {
                figure.draw(canvas, paint)
            }

            paint.textSize = 20f
            for (i in 0 until multipliers.size){
                paint.color = Color.MAGENTA
                canvas.drawRect(Constants.screenWidth/2f + textPosx[i].toFloat() - paint.textSize, Constants.screenHeight/2f + textPosy - 30f, Constants.screenWidth/2f + textPosx[i].toFloat() + paint.textSize, Constants.screenHeight/2f + textPosy + 10f, paint)
                paint.color = Color.WHITE
                canvas.drawText(multipliers[i].toString(), Constants.screenWidth/2f + textPosx[i].toFloat() - paint.textSize, Constants.screenHeight/2f + textPosy, paint)
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