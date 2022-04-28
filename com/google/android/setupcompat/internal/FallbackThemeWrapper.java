package com.google.android.setupcompat.internal;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextThemeWrapper;

public final class FallbackThemeWrapper extends ContextThemeWrapper {
    public final void onApplyThemeResource(Resources.Theme theme, int i, boolean z) {
        theme.applyStyle(i, false);
    }

    public FallbackThemeWrapper(Context context, int i) {
        super(context, i);
    }
}
