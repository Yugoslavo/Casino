package com.example.myapplication.blackjack

enum class Rank ( val displayName: String, val value : Int){
    ACE("ace", 11),
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9),
    TEN("ten", 10),
    JACK("jack", 10),
    QUEEN("queen", 10),
    KING("king", 10)
}