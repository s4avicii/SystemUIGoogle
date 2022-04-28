package com.google.android.setupdesign.util;

import android.content.Context;
import com.google.android.setupcompat.util.Logger;

public final class ThemeHelper {
    public static final Logger LOG = new Logger("ThemeHelper");

    public static String colorIntToHex(Context context, int i) {
        return String.format("#%06X", new Object[]{Integer.valueOf(context.getResources().getColor(i) & 16777215)});
    }
}
