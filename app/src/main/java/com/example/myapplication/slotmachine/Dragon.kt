package com.example.myapplication.slotmachine

import com.example.myapplication.R

class Dragon: Fruit {
    override var id:Int = 7
        get() = field

    override var icon: Int = R.drawable.dragon
        get() = field

    override fun getValue(count: Int) : Float {
        /*
        Valeur ajoutée selon le nombre de grenades de ce type que tu obtiens
        Exemple:
            1 dragon -> pert un quart de ta mise
            2 dragon -> pert la moitié de la mise
            3 grenades -> pert toute la mise
         */
        var res:Float = when(count){
            1 ->  1.25F
            2 ->  1.5F
            3 -> 3F
            else -> 0F
        }

        return res
    }
}