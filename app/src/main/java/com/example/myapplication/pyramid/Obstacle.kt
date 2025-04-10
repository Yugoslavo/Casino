package com.example.myapplication.pyramid

import android.graphics.Canvas
import android.graphics.Paint

abstract class Obstacle : Subject(), Figure {

    override fun draw(canvas: Canvas, paint: Paint) {
        // Dessin de l'obstacle
    }
}