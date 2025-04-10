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

        val suits = listOf("♠", "♥", "♦", "♣")
        val ranks = listOf("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K")
        for (suit in suits) {
            for (rank in ranks) {
                val value = when (rank) {
                    "A" -> 11
                    "K", "Q", "J" -> 10
                    else -> rank.toInt()
                }
                deck.add(Card(suit, rank, value))
            }
        }
        deck.shuffle()
        repeat(2){
        playerHand.add(deck.random().also { deck.remove(it) })
        aiHand.add(deck.random().also { deck.remove(it) })
        }
    }


    // Data Class for cards
    data class Card (val suit: String, val rank : String, val value : Int)

    //Put the card nbr 1 into your hand

    fun CardReveal(view: View) {
        if (deck.isNotEmpty()) {
            val firstCard = deck[0]
            deck.removeAt(0)
            playerHand.add(firstCard)
            aiPlayTurn()
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