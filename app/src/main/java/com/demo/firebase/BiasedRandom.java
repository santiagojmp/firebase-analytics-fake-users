package com.demo.firebase;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class BiasedRandom {

    /**
     * Draws random number between 1 and limit. Biased on a day of week:
     * - on the first day of week biased towards first 1/7th of numbers,
     * - on the second day of week biased towards second 1/7th of numbers
     * - etc.
     */
    public static int dateBiasedUserId(int limit) {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        // TODO: make it actually biased
        return new Random().nextInt(limit);
    }
}
