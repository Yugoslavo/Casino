package com.example.myapplication.pyramid


interface Observer {
    fun update(updateSpeed:(Float, Float) -> ((Float, Float) -> Array<Float>)){
        // Notifie l'observateur d'une mise à jour à faire
    }
    fun update() {

    }
}