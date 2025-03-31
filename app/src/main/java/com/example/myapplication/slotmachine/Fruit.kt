package com.example.myapplication.slotmachine

interface Fruit {
    var id : Int
    /*
    l'id est un nombre pour savoir quel fruit on est, voici la liste des id avec le fruit correspondant
    1 -> fraise
    2 -> orange
    3 -> pomme
    4 -> banane
    5 -> Grenade
    6 -> Durian
    7 -> Dragon
     */
    var icon: Int // id vers l'image du fruit

    fun get_value(count: Int) : Float
}