package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log

class black_jack : AppCompatActivity() {
    private val deck = mutableListOf<Card>()
    private val playerHand = mutableListOf<Card>()
    private val aiHand = mutableListOf<Card>()
    private var playerHasStopped = false
    private var aiHasStopped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_black_jack)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        deckButton.visibility = View.GONE

        // Liste des couleurs (spades = ♠, hearts = ♥, etc.)
        val suits = listOf("spades", "hearts", "diamonds", "clubs")
        val suitSymbols = mapOf(
            "spades" to "♠",
            "hearts" to "♥",
            "diamonds" to "♦",
            "clubs" to "♣"
        )

        // Liste des rangs + leurs valeurs
        val rankMap = mapOf(
            "ace" to Pair("A", 11),
            "two" to Pair("2", 2),
            "three" to Pair("3", 3),
            "four" to Pair("4", 4),
            "five" to Pair("5", 5),
            "six" to Pair("6", 6),
            "seven" to Pair("7", 7),
            "eight" to Pair("8", 8),
            "nine" to Pair("9", 9),
            "ten" to Pair("10", 10),
            "jack" to Pair("J", 10),
            "queen" to Pair("Q", 10),
            "king" to Pair("K", 10)
        )

        // Construction du deck (52 cartes)
        for (suit in suits) {
            for ((rankName, rankInfo) in rankMap) {
                val (rankSymbol, value) = rankInfo
                val resName = "${rankName}_of_${suit}" // ex: "jack_of_hearts"
                val resId = resources.getIdentifier(resName, "drawable", packageName)

                if (resId != 0) {
                    val card = Card(
                        suit = suitSymbols[suit] ?: "?",
                        rank = rankSymbol,
                        value = value,
                        imageResId = resId
                    )
                    deck.add(card)
                } else {
                    Log.e("CardLoader", "Image $resName not found in drawable")
                }
            }
        }


        deck.shuffle()
        repeat(2){
        playerHand.add(deck.random().also { deck.remove(it) })
        aiHand.add(deck.random().also { deck.remove(it) })
        }
    }



    //Each time the player plays the AI plays next

        fun aiPlayTurn() {
            while (calculatePoints(aiHand) < 17 && deck.isNotEmpty()) {
                val card = deck.removeAt(0)
                aiHand.add(card)
            }

            aiHasStopped = true
        checkIfGameShouldEnd()
    }
    private fun checkIfGameShouldEnd() {
        if (playerHasStopped && aiHasStopped) {
            val playerPoints = calculatePoints(playerHand)
            val aiPoints = calculatePoints(aiHand)

            val result = when {
                playerPoints > 21 && aiPoints > 21 -> "Both busted!"
                playerPoints > 21 -> "AI wins! You busted!"
                aiPoints > 21 -> "You win! AI busted!"
                playerPoints > aiPoints -> "You win with $playerPoints vs $aiPoints!"
                aiPoints > playerPoints -> "AI wins with $aiPoints vs $playerPoints!"
                else -> "It's a tie at $playerPoints!"
            }

            val resultTextView = findViewById<TextView>(R.id.myTextView)
            resultTextView.text = result
        }
    }

    // J'ai prit en compte les Aces en fesant en mode -10 si le total depasse 21

    private fun calculatePoints(hand: List<Card>): Int {
        var total = 0
        var aceCount = 0
        for (card in hand){
            total += card.value
            if(card.rank == "A") aceCount++
        }
        while(total > 21 && aceCount > 0){
            total -= 10
            aceCount -= 1
        }
        return total
    }

    fun playerStop(view: View) {
        playerHasStopped = true
    }

}