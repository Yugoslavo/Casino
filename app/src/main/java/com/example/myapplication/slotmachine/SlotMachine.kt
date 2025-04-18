package com.example.myapplication.slotmachine

import kotlin.math.atan
import kotlin.math.max
import kotlin.random.Random

class SlotMachine (var luck: Float) {
    // Cette classe représente une machine à sous et gère la logique de jeu
    var fruits: Array<Fruit> = arrayOf()
        get() = field
        set(value){
            field = value
        }


    fun play(mise: Float): Float{
        // Luck est un nombre entre - inf et + inf qui représente à quel points le joueur doit être chanceux
        var normalized_luck = atan(luck.toDouble()) /3.15 +0.5 // on normalise la chance entre 0 et 1 (0 = pas de chance, 1 = chance max)
        this.fruits = arrayOf()
        //Distribution normal: luck/3, bon: 2*luck/3, mauvais: 1 - luck
        (1..3).forEach { i ->
            var result = Random.Default.nextFloat()
            var fruit: Fruit = when {
                result< (1 - normalized_luck)/2 -> Grenade()
                result < (1 - normalized_luck) -> Durian()
                (result > 1 - normalized_luck*2/3) -> Dragon()
                else -> FruitNormal()
            }
            fruits += fruit
        }
        return this.getResult(mise)
    }

    private fun getResult(mise:Float): Float{
        if (fruits.size < 3){
            return this.play(mise)
        }

        //calcule le gain en fonction des fruits obtenus et de la mise
        var foundIds: Array<Int> = arrayOf()
        var multiplier = 0F
        for (fruit in fruits) {
            if (fruit.id in foundIds){
                continue
            }
            var count = fruits.count { x -> x.id == fruit.id }
            foundIds += fruit.id
            multiplier += fruit.getValue(count)
        }
        multiplier = max(0F, multiplier)
        return multiplier*mise
    }
}