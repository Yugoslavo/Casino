package com.example.myapplication

interface Banque {
    fun deposer(montant : Double)
}

class BanqueImp (private var solde : Double): Banque{
    override fun deposer(montant : Double){
        if (montant > 0){
            solde += montant
        }
    }
}