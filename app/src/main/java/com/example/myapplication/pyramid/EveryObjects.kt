package com.example.myapplication.pyramid


class EveryObjects private constructor() {
    // Listes des objets utilisés dans le jeu
    internal val ballList = mutableListOf<Ball>().apply {
        add(Ball(0f))// balle de départ qui sert d'indicateur de position
    }
    internal val obstacleList = mutableListOf<Obstacle>().apply {
    }

    // méthodes statiques pour implémenter le pattern Singleton
    companion object {
        @Volatile
        private var instance : EveryObjects? = null

        fun getInstance(): EveryObjects {
            return instance ?: synchronized(this) {
                instance ?: EveryObjects().also { instance = it }
            }
        }

        fun destroyInstance() {
            instance = null
        }
    }
}