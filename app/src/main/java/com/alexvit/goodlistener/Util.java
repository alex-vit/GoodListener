package com.alexvit.goodlistener;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by alexander.vitjukov on 13.09.2017.
 */

public class Util {

    private Util() {
    }

    public static String formatTimestamp(long millis) {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);

        int h = calendar.get(Calendar.HOUR_OF_DAY);
        int m = calendar.get(Calendar.MINUTE);
        int s = calendar.get(Calendar.SECOND);
        int ms = calendar.get(Calendar.MILLISECOND);

        return String.format(Locale.getDefault(), "%02d:%02d:%02d.%03d", h, m, s, ms);
    }

    public static String formatAction(String action) {
        return action.replace("android.", "");
    }
}
