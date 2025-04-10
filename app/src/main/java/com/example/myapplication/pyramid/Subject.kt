package com.example.myapplication.pyramid

open class Subject {
    // Classe de base pour les objets observables
    protected var observers = mutableListOf<Observer>()

    open fun callObservers() {
        for(obs in observers)
            obs.update()
    }

    fun attach(obs : Observer) {
        observers.add(obs)
    }

    fun detach(obs : Observer) {
        observers.remove(obs)
    }
}