package com.example.myapplication.slotmachine

import com.example.myapplication.R

class Durian: Fruit {
    override var id:Int = 5
        get() = field

    override var icon: Int = R.drawable.grenade
        get() = field

    override fun get_value(count: Int) : Float {
        /*
        Valeur ajoutée selon le nombre de grenades de ce type que tu obtiens
        Exemple:
            1 durian -> pert un quart de ta mise
            2 durian -> pert un quart
            3 grenades -> pert un quart
         */
        return -0.25F
    }
}