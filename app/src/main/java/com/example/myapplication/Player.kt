package com.example.myapplication

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class Player private constructor(private val context: Context) {
    // Classe qui sert à gérer les données du joueur
    // Utilise DataStore pour stocker les données afin de les conserver entre les sessions
    // Utilise un singleton pattern pour éviter de créer plusieurs instances de la classe
    // (Le DataStore force cet aspect)

    // Clés pour stocker les données
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val nameKey = stringPreferencesKey("name")
    val moneyKey = floatPreferencesKey("money")
    val betKey = floatPreferencesKey("bet")
    val luckKey = floatPreferencesKey("luck")

    var name: String = "" //nom du joueur, pas utilisé pour l'instant
        set(value) {
            field = value
            runBlocking {
                updateValues()
            }
        }

    var money: Float = 0F // argent du joueur
        set(value) {
            if (value < 0) {
                field = 0F
            }else {
                field = value
            }
            if (bet > field) { // vérifie que la mise ne dépasse pas l'argent du joueur
                bet = field
            }
            runBlocking {// stockage des données
                updateValues()
            }
        }

    var bet: Float = 0F
        set(value) {
            if (value > money) { // vérifie que la mise ne dépasse pas l'argent du joueur
                field = money
            } else {
                field = value
            }
            runBlocking { // stockage des données
                updateValues()
            }
        }

    var luck: Float = 0.3F // chance du joueur,
        // utilisée seulement dans la machine à sous et n'est jamais modifiée
        // Une chance de 0.3 signifie un avantage léger dans le jeu de machine à sous (d'après les tests)
        set(value) {
            field = value
            runBlocking {
                updateValues()
            }
        }

    suspend fun updateValues() {
        // Fonction qui met à jour les valeurs du joueur dans le DataStore
        context.applicationContext.dataStore.edit { settings ->
            settings[nameKey] = this@Player.name
            settings[moneyKey] = this@Player.money
            settings[betKey] = this@Player.bet
            settings[luckKey] = this@Player.luck
        }
    }

    init {
        runBlocking {
            val preferences = getValues()
            this@Player.name = preferences[nameKey] ?: ""
            this@Player.money = preferences[moneyKey] ?: 0F
            this@Player.bet = preferences[betKey] ?: 0F
            this@Player.luck = preferences[luckKey] ?: 0.3F
            // Initialisation des valeurs du joueur à partir du DataStore
        }
    }

    private suspend fun getValues(): Preferences {
        return context.applicationContext.dataStore.data.first()
    }

    fun addMoney(money: Float) {
        // Fonction qui ajoute de l'argent au joueur,
        //on pourrait en pratique directement manipuler le champ étant donné qu'il est public
        this.money += money
    }

    // Méthodes statiques qui servent à l'implémentation du singleton
    companion object { // La classe pyramid.EveryObjects est aussi un singleton
        @Volatile
        private var instance : Player? = null

        fun getInstance(context: Context): Player {
            // Récupère ou crée l'instance de la classe Player
            return instance ?: synchronized(this) {
                instance ?: Player(context.applicationContext).also { instance = it }
            }
        }

        fun destroyInstance() {
            // Détruit l'instance de la classe Player
            // Pas utilisé pour l'instant mais pratique à avoir sous la main
            instance = null
        }
    }
}