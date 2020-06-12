package com.demo.firebase

import java.util.*

object BiasedRandom {

    /**
     * Draws random number between 1 and limit. Biased on a day of week:
     * - on the first day of week biased towards first 1/7th of numbers,
     * - on the second day of week biased towards second 1/7th of numbers
     * - etc.
     */
    @JvmStatic
    fun dateBiasedUserId(limit: Int): Int {
        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val start = limit / 7 * (day - 1)
        val end = limit / 7 * day
        val biasedRange = start..end
        var random = Random().nextInt(limit)
        (0..2).forEach {
            if (random !in biasedRange) {
                random = Random().nextInt(limit)
            }
        }
        return random
    }
}