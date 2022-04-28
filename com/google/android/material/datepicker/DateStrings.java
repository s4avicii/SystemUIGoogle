package com.google.android.material.datepicker;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class DateStrings {
    public static String getMonthDay(long j, Locale locale) {
        return UtcDates.getAndroidFormat("MMMd", locale).format(new Date(j));
    }

    public static String getDateString$1(long j) {
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf((Calendar) null);
        utcCalendarOf.setTimeInMillis(j);
        if (todayCalendar.get(1) == utcCalendarOf.get(1)) {
            return getMonthDay(j, Locale.getDefault());
        }
        return getYearMonthDay(j, Locale.getDefault());
    }

    public static String getYearMonthDay(long j, Locale locale) {
        return UtcDates.getAndroidFormat("yMMMd", locale).format(new Date(j));
    }
}
