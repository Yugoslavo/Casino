package com.example.myapplication

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi

class PyramideView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {
    private lateinit var thread: Thread
    private var drawing = false
    var canvas = Canvas()
    val paint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f
    var totalElapsedTime = 0.0
    
    var everyObjects : EveryObjects

    init {
        everyObjects = EveryObjects()

    }

    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
    }

    override fun run() {
        var previousFrameTime = System.currentTimeMillis()
        while (drawing) {
            val currentTime = System.currentTimeMillis()
            var elapsedTimeMS: Double = (currentTime - previousFrameTime).toDouble()
            totalElapsedTime += elapsedTimeMS / 1000.0
            updatePositions(elapsedTimeMS)
            previousFrameTime = currentTime


            updatePositions(elapsedTimeMS)
            draw()
        }
    }

    fun updatePositions(elapsedTimeMS: Double) {
        if (everyObjects.BallList.first().y > Constants.screenHeight - 60){
            everyObjects.BallList.removeAt(0)
        }
        for (ball in everyObjects.BallList){
            ball.move()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val activeBalls = everyObjects.BallList.count { it.isMoving }
            if (activeBalls < 5) {
                val offsetX = (-50..50).random()
                val startX = Constants.screenWidth / 2f + offsetX
                everyObjects.BallList.last().isMoving = true
                everyObjects.BallList.add(Ball())
            }
        }
        return true
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawColor(Color.BLACK)
            for (ball in everyObjects.BallList){
                ball.draw(canvas, paint)
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
