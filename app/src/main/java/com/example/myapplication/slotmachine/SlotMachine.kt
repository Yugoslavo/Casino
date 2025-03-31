package com.example.myapplication.slotmachine

import kotlin.math.atan
import kotlin.math.max
import kotlin.random.Random

class SlotMachine (var luck: Int) {
    var fruits: Array<Fruit>
        get() {
            return fruits
        }
        set(value){
            fruits = value
        }

    init {
        this.fruits = arrayOf()
    }

    fun play(mise: Float): Float{
        var normalized_luck = atan(luck.toDouble()) /3.15 +0.5 // Luck est un nombre entre - inf et + inf qui représente à quel points le joueur doit être chanceux
        this.fruits = arrayOf()
        //Distribution normal: 1 - luck, bon: luck/3, mauvais: 2*luck/3
        for (i in 1..3){
            var result = Random.Default.nextFloat()
            var fruit: Fruit = when {
                result< (1 - normalized_luck)/2 -> Grenade()
                result < (1 - normalized_luck) -> Durian()
                (result > 1 - normalized_luck*2/3) -> Dragon()
                else -> FruitNormal()
            }
            fruits[i] = fruit
        }
        return get_result(mise)
    }

    fun get_result(mise:Float): Float{

        if (fruits.size < 3){
            return play(mise)
        }

        var found_id: Array<Int> = arrayOf()
        var multiplier: Float = 0F;
        for (fruit in fruits) {
            if (fruit.id in found_id){
                continue
            }
            var count = fruits.count { x -> x.id == fruit.id }
            found_id += fruit.id
            multiplier += fruit.get_value(count)
        }
        multiplier = max(0F, multiplier)
        return multiplier*mise
    }
}