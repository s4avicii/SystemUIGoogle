package com.android.systemui.statusbar.policy;

import android.icu.text.DateFormat;
import android.icu.text.DisplayContext;
import android.text.TextUtils;
import java.util.Date;
import java.util.Locale;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewControllerKt {
    public static final VariableDateViewControllerKt$EMPTY_FORMAT$1 EMPTY_FORMAT = new VariableDateViewControllerKt$EMPTY_FORMAT$1();

    public static final DateFormat getFormatFromPattern(String str) {
        if (TextUtils.equals(str, "")) {
            return EMPTY_FORMAT;
        }
        DateFormat instanceForSkeleton = DateFormat.getInstanceForSkeleton(str, Locale.getDefault());
        instanceForSkeleton.setContext(DisplayContext.CAPITALIZATION_FOR_STANDALONE);
        return instanceForSkeleton;
    }

    public static final String getTextForFormat(Date date, DateFormat dateFormat) {
        if (dateFormat == EMPTY_FORMAT) {
            return "";
        }
        return dateFormat.format(date);
    }
}
