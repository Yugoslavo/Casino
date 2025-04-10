package com.example.myapplication.pyramid

import android.graphics.Canvas
import android.graphics.Paint

interface Figure {
    var x: Float // Position X
    var y: Float // Position Y
    fun draw(canvas: Canvas, paint: Paint) // Dessine la figure sur le canvas
}