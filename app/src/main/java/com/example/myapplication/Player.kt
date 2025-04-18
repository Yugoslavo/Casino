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

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val nameKey = stringPreferencesKey("name")
    val moneyKey = floatPreferencesKey("money")
    val betKey = floatPreferencesKey("bet")
    val luckKey = floatPreferencesKey("luck")
    var name: String = ""
        set(value) {
            field = value
            runBlocking {
                updateValues()
            }
        }

    var money: Float = 0F
        set(value) {
            if (value < 0) {
                field = 0F
            }else {
                field = value
            }
            if (bet > field) {
                bet = field
            }
            runBlocking {
                updateValues()
            }
        }

    var bet: Float = 0F
        set(value) {
            if (value > money) {
                field = money
            } else {
                field = value
            }
            runBlocking {
                updateValues()
            }
        }

    var luck: Float = 0.3F
        set(value) {
            field = value
            runBlocking {
                updateValues()
            }
        }

    suspend fun updateValues() {
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
        }
    }

    private suspend fun getValues(): Preferences {
        return context.applicationContext.dataStore.data.first()
    }

    fun addMoney(money: Float) {
        this.money += money
    }

    companion object {
        @Volatile
        private var instance : Player? = null

        fun getInstance(context: Context): Player {
            return instance ?: synchronized(this) {
                instance ?: Player(context.applicationContext).also { instance = it }
            }
        }

        fun destroyInstance() {
            instance = null
        }
    }
}