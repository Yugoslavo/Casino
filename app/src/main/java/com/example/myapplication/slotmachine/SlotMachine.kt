package com.example.myapplication.slotmachine

import kotlin.math.atan
import kotlin.math.max
import kotlin.random.Random

class SlotMachine (var luck: Float) {
    var fruits: Array<Fruit> = arrayOf()
        get() = field
        set(value){
            field = value
        }


    fun play(mise: Float): Float{
        var normalized_luck = atan(luck.toDouble()) /3.15 +0.5 // Luck est un nombre entre - inf et + inf qui représente à quel points le joueur doit être chanceux
        this.fruits = arrayOf()
        //Distribution normal: 1 - luck, bon: luck/3, mauvais: 2*luck/3
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

        var found_id: Array<Int> = arrayOf()
        var multiplier = 0F
        for (fruit in fruits) {
            if (fruit.id in found_id){
                continue
            }
            var count = fruits.count { x -> x.id == fruit.id }
            found_id += fruit.id
            multiplier += fruit.getValue(count)
        }
        multiplier = max(0F, multiplier)
        return multiplier*mise
    }
}