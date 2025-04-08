package com.example.myapplication.slotmachine

import kotlin.random.Random
import com.example.myapplication.R

class FruitNormal() : Fruit {
    override var id:Int = 1
        get() = field
        set(value) {
            if (1 <= this.id && this.id <= 4) {
                field = value
                this.icon = when (this.id){
                    1 -> R.drawable.fraise
                    2 -> R.drawable.orange
                    3 -> R.drawable.pomme
                    4 -> R.drawable.banane
                    else -> {
                        println("Erreur, l'id d'un fruit normal n'est pas entre 1 et 4")
                        R.drawable.fraise
                    }
                }
            }

        }

    override var icon: Int = R.drawable.fraise
        get() = field
        set(value) {
            field = value
        }

    init {
        id = Random.nextInt(1,5)
    }

    override fun getValue(count: Int) : Float {
        /*
        Valeur ajoutée selon le nombre de fruits de ce type que tu obtiens
        Exemple:
            1 banane -> un quart de ta mise
            2 bananes -> ta mise de départ
            3 bananes -> 2.25 fois ta mise de départ -> on travaille avec des entiers et ça fera donc surement plus
         */
        return (count.toFloat() * count.toFloat())/4
    }
}