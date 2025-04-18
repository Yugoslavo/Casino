package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AlertDialog

interface MoneyEarningStrategy {
    fun earnMoney(context: Context)
}