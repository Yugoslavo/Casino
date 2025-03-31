package com.example.myapplication.slotmachine

import com.example.myapplication.R

class Grenade:Fruit {
    override var id:Int = 5
        get() {
            return id
        }

    override var icon: Int = R.drawable.grenade
        get() {
            return icon
        }

    override fun get_value(count: Int) : Float {
        /*
        Valeur ajoutée selon le nombre de grenades de ce type que tu obtiens
        Exemple:
            1 grenade -> pert un quart de ta mise
            2 grenades -> pert la moitié de la mise
            3 grenades -> pert toute la mise
         */
        var res:Float = when(count){
            1 -> - 0.25F
            2 -> - 0.5F
            3 -> - 1F
            else -> 0F
        }

        return res
    }
}