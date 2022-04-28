package com.android.p012wm.shell.pip.p013tv;

import android.view.animation.PathInterpolator;

/* renamed from: com.android.wm.shell.pip.tv.TvPipInterpolators */
public final class TvPipInterpolators {
    public static final PathInterpolator ENTER = new PathInterpolator(0.12f, 1.0f, 0.4f, 1.0f);
    public static final PathInterpolator EXIT = new PathInterpolator(0.4f, 1.0f, 0.12f, 1.0f);

    static {
        new PathInterpolator(0.2f, 0.1f, 0.0f, 1.0f);
        new PathInterpolator(0.18f, 1.0f, 0.22f, 1.0f);
    }
}
