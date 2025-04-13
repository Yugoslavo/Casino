package com.example.myapplication.blackjack

data class Card(
    val suit: String,    // ♠, ♥, ♦, ♣
    val rank: String,    // A, 2, ..., K
    val value: Int,      // 11, 10, ...
    val imageResId: Int  // R.drawable.nom_image
)