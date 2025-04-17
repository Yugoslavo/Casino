package com.example.myapplication

abstract class Singleton<T> {
    @Volatile
    protected var instance: T? = null

    protected abstract fun createInstance(): T

    fun getInstance(): T {
        return instance ?: synchronized(this) {
            instance ?: createInstance().also { instance = it }
        }
    }

    fun destroyInstance() {
        instance = null
    }
}