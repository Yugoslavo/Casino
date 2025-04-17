package com.example.myapplication.pyramid

import com.example.myapplication.Singleton


class EveryObjects private constructor() {
    // Listes des objets utilisés dans le jeu
    var ballList = mutableListOf<Ball>().apply {
        add(Ball(0f))// balle de départ qui sert d'indicateur de position
    }
    var obstacleList = mutableListOf<Obstacle>().apply {
    }

    companion object : Singleton<EveryObjects>() {
        override fun createInstance(): EveryObjects {
            return EveryObjects()
        }
    }
}