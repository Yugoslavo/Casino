package com.example.myapplication.pyramid


abstract class Obstacle : Subject(), Figure {
    // Template method for calling observers
    override fun callObservers() {
        val updateSpeed = createUpdateFunction()
        try {
            for (observer in observers) {
                observer.update(updateSpeed)
            }
        } catch (e: Exception) {
            // Ignore exceptions
        }
    }

    // Abstract method to be implemented by subclasses
    protected abstract fun createUpdateFunction(): (Float, Float) -> (Float, Float) -> Array<Float>

}