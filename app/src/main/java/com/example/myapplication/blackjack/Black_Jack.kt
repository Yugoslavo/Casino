package com.example.myapplication.blackjack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.MainActivity
import com.example.myapplication.Player
import com.example.myapplication.R

class Black_Jack : AppCompatActivity() {
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

        val rankToValueMap = mapOf(

            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9,
            "ten" to 10,

            )

        val suits = listOf("spades", "hearts", "diamonds", "clubs")
        val ranks = listOf("ace", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king")
        for (suit in suits) {
            for (rank in ranks) {
                val value = when (rank) {
                    "ace" -> 11
                    "king", "queen", "jack" -> 10
                    else -> rankToValueMap[rank]?: 0

                }
                deck.add(Card(suit, rank, value))
            }
        }
        deck.shuffle()
        repeat(2){
            val carte = deck.random().also { deck.remove(it) }
            playerHand.add(carte)
            aiHand.add(deck.random().also { deck.remove(it) })
        }
        //montrer les cartes du joueur
        val carte1 = playerHand[0]
        val carte2 = playerHand[1]
        val imageView1: ImageView = findViewById(R.id.imageView17)
        val cardName1 = "${carte1.rank.lowercase()}_of_${carte1.suit.lowercase()}"
        val resourceId1 = resources.getIdentifier(cardName1, "drawable", packageName)
        imageView1.setImageResource(resourceId1)
        val imageView2: ImageView = findViewById(R.id.imageView16)
        val cardName2 = "${carte2.rank.lowercase()}_of_${carte2.suit.lowercase()}"
        val resourceId2 = resources.getIdentifier(cardName2, "drawable", packageName)
        imageView2.setImageResource(resourceId2)


        // rank_of_suit le nom de tout les fichier avec les cartes
    }


    // Data Class for cards
    data class Card (val suit: String, val rank : String, val value : Int)

    //Put the card nbr 1 into your hand
    fun CardReveal(view: View) {
        if (deck.isNotEmpty()) {
            val firstCard = deck[0]
            deck.removeAt(0)
            playerHand.add(firstCard)


            // Making a list of image views
            val imageViews = listOf(
                findViewById<ImageView>(R.id.imageView19),
                findViewById<ImageView>(R.id.imageView18),
                findViewById<ImageView>(R.id.imageView20),
                findViewById<ImageView>(R.id.imageView21),
                findViewById<ImageView>(R.id.imageView22),
                findViewById<ImageView>(R.id.imageView23)

            )
            for (imageView in imageViews){
                if(imageView.visibility != View.VISIBLE){
                    imageView.visibility = View.VISIBLE
                    val cardName = "${firstCard.rank.lowercase()}_of_${firstCard.suit.lowercase()}"
                    val resourceId1 = resources.getIdentifier(cardName, "drawable", packageName)
                    imageView.setImageResource(resourceId1)

                    break //Sinon ça va tout changer
                }
            }
            aiPlayTurn()

        }
    }
    //Each time the player plays the AI plays next

    fun aiPlayTurn() {
        if (calculatePoints(aiHand) < 17 && deck.isNotEmpty()) {
            val card = deck.removeAt(0)
            aiHand.add(card)

// @drawable/card_back

            val imageViews = listOf(
                findViewById<ImageView>(R.id.imageView27),
                findViewById<ImageView>(R.id.imageView24),
                findViewById<ImageView>(R.id.imageView9),
                findViewById<ImageView>(R.id.imageView28),
                findViewById<ImageView>(R.id.imageView11)
            )
            for(imageView in imageViews){
                if(imageView.visibility != View.VISIBLE){
                    imageView.visibility = View.VISIBLE

                    break //Sinon ça crash
                }
            }
        }
        else{
            aiHasStopped = true
            checkIfGameShouldEnd()
        }

    }
    private fun checkIfGameShouldEnd() {
        if (playerHasStopped && aiHasStopped) {
            val mainIA = aiHand.toMutableList()
            val imageViews = listOf(
                findViewById<ImageView>(R.id.imageView27),
                findViewById<ImageView>(R.id.imageView24),
                findViewById<ImageView>(R.id.imageView9),
                findViewById<ImageView>(R.id.imageView28),
                findViewById<ImageView>(R.id.imageView25),
                findViewById<ImageView>(R.id.imageView26),
                findViewById<ImageView>(R.id.imageView11)

            )
            val cardsIterator = mainIA.iterator()

            for (imageView in imageViews) {
                if (imageView.visibility == View.VISIBLE && cardsIterator.hasNext()) {
                    val card = cardsIterator.next()
                    val cardName = "${card.rank.lowercase()}_of_${card.suit.lowercase()}"
                    val resourceId = resources.getIdentifier(cardName, "drawable", packageName)
                    imageView.setImageResource(resourceId)
                    cardsIterator.remove()
                }
            }

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

            val player = Player(this)
            val bet = Initblackjack.GameState.betValue
            if(aiPoints > 21 && playerPoints < 22 || playerPoints > aiPoints){
                player.money += 2*bet
            }
            if(playerPoints > 21 && aiPoints < 22 || aiPoints > playerPoints ){
                player.money -= bet
            }

            val resultTextView = findViewById<TextView>(R.id.myTextView)
            resultTextView.text = result

            val returnButton = findViewById<Button>(R.id.button4)
            returnButton.visibility = View.VISIBLE


        }
    }

    // J'ai prit en compte les Aces en fesant en mode -10 si le total depasse 21

    private fun calculatePoints(hand: List<Card>): Int {
        var total = 0
        var aceCount = 0
        for (card in hand){
            total += card.value
            if(card.rank == "ace") aceCount++
        }
        while(total > 21 && aceCount > 0){
            total -= 10
            aceCount -= 1
        }
        return total
    }

    fun playerStop(view: View) {
        playerHasStopped = true
        checkIfGameShouldEnd()
        // Desactiver les autres boutons
        findViewById<Button>(R.id.deckButton).isEnabled = false
        findViewById<Button>(R.id.playerStop).isEnabled = false

        while(aiHasStopped == false){
            aiPlayTurn()
        }


    }

    fun returnToHome(view: View) {
        val intent = Intent(this, Initblackjack::class.java)
        startActivity(intent)
        finish() // Optionnel, pour éviter que l'utilisateur revienne ici avec "back"
    }

}