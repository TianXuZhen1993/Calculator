package com.example.calculator.activity

import android.content.Context

object SPUtils {
    fun spToPx(context: Context, sp: Int): Float {
        val density = context.resources.displayMetrics.scaledDensity
        return density * sp + 0.5f
    }
}