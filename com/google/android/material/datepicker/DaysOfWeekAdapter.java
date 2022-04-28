package com.google.android.material.datepicker;

import android.widget.BaseAdapter;
import java.util.Calendar;

public final class DaysOfWeekAdapter extends BaseAdapter {
    public final Calendar calendar;
    public final int daysInWeek;
    public final int firstDayOfWeek;

    public final long getItemId(int i) {
        return 0;
    }

    /* JADX WARNING: type inference failed for: r6v8, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    @android.annotation.SuppressLint({"WrongConstant"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View getView(int r5, android.view.View r6, android.view.ViewGroup r7) {
        /*
            r4 = this;
            r0 = r6
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 0
            if (r6 != 0) goto L_0x0018
            android.content.Context r6 = r7.getContext()
            android.view.LayoutInflater r6 = android.view.LayoutInflater.from(r6)
            r0 = 2131624293(0x7f0e0165, float:1.8875762E38)
            android.view.View r6 = r6.inflate(r0, r7, r1)
            r0 = r6
            android.widget.TextView r0 = (android.widget.TextView) r0
        L_0x0018:
            java.util.Calendar r6 = r4.calendar
            int r2 = r4.firstDayOfWeek
            int r5 = r5 + r2
            int r2 = r4.daysInWeek
            if (r5 <= r2) goto L_0x0022
            int r5 = r5 - r2
        L_0x0022:
            r2 = 7
            r6.set(r2, r5)
            android.content.res.Resources r5 = r0.getResources()
            android.content.res.Configuration r5 = r5.getConfiguration()
            java.util.Locale r5 = r5.locale
            java.util.Calendar r6 = r4.calendar
            r3 = 4
            java.lang.String r5 = r6.getDisplayName(r2, r3, r5)
            r0.setText(r5)
            android.content.Context r5 = r7.getContext()
            r6 = 2131952837(0x7f1304c5, float:1.9542128E38)
            java.lang.String r5 = r5.getString(r6)
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]
            java.util.Calendar r4 = r4.calendar
            r7 = 2
            java.util.Locale r3 = java.util.Locale.getDefault()
            java.lang.String r4 = r4.getDisplayName(r2, r7, r3)
            r6[r1] = r4
            java.lang.String r4 = java.lang.String.format(r5, r6)
            r0.setContentDescription(r4)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.datepicker.DaysOfWeekAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    public final Object getItem(int i) {
        int i2 = this.daysInWeek;
        if (i >= i2) {
            return null;
        }
        int i3 = i + this.firstDayOfWeek;
        if (i3 > i2) {
            i3 -= i2;
        }
        return Integer.valueOf(i3);
    }

    public DaysOfWeekAdapter() {
        Calendar utcCalendarOf = UtcDates.getUtcCalendarOf((Calendar) null);
        this.calendar = utcCalendarOf;
        this.daysInWeek = utcCalendarOf.getMaximum(7);
        this.firstDayOfWeek = utcCalendarOf.getFirstDayOfWeek();
    }

    public final int getCount() {
        return this.daysInWeek;
    }
}
