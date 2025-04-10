package com.example.myapplication.pyramid

class EveryObjects() {
    // Listes des objets utilisés dans le jeu
    var ballList = mutableListOf<Ball>().apply {
        add(Ball(0f))// balle de départ qui sert d'indicateur de position
    }
    var obstacleList = mutableListOf<Obstacle>().apply {
    }
}