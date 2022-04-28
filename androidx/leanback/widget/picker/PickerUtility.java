package androidx.leanback.widget.picker;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public final class PickerUtility {

    public static class DateConstant {
        public final Locale locale;
        public final String[] months;

        public DateConstant(Locale locale2) {
            this.locale = locale2;
            this.months = DateFormatSymbols.getInstance(locale2).getShortMonths();
            Calendar instance = Calendar.getInstance(locale2);
            PickerUtility.createStringIntArrays(instance.getMinimum(5), instance.getMaximum(5));
        }
    }

    public static class TimeConstant {
        public final String[] ampm;
        public final String[] hours24 = PickerUtility.createStringIntArrays(0, 23);
        public final Locale locale;
        public final String[] minutes = PickerUtility.createStringIntArrays(0, 59);

        public TimeConstant(Locale locale2) {
            this.locale = locale2;
            DateFormatSymbols instance = DateFormatSymbols.getInstance(locale2);
            PickerUtility.createStringIntArrays(1, 12);
            this.ampm = instance.getAmPmStrings();
        }
    }

    public static String[] createStringIntArrays(int i, int i2) {
        String[] strArr = new String[((i2 - i) + 1)];
        for (int i3 = i; i3 <= i2; i3++) {
            strArr[i3 - i] = String.format("%02d", new Object[]{Integer.valueOf(i3)});
        }
        return strArr;
    }

    public static Calendar getCalendarForLocale(Calendar calendar, Locale locale) {
        if (calendar == null) {
            return Calendar.getInstance(locale);
        }
        long timeInMillis = calendar.getTimeInMillis();
        Calendar instance = Calendar.getInstance(locale);
        instance.setTimeInMillis(timeInMillis);
        return instance;
    }
}
