package com.android.systemui.decor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import java.util.HashMap;
import java.util.Objects;

/* compiled from: OverlayWindow.kt */
public final class OverlayWindow {
    public final LayoutInflater layoutInflater;
    public final ViewGroup rootView;
    public final HashMap viewProviderMap;

    public OverlayWindow(LayoutInflater layoutInflater2, int i) {
        int i2;
        this.layoutInflater = layoutInflater2;
        if (i == 0 || i == 1) {
            i2 = C1777R.layout.rounded_corners_top;
        } else {
            i2 = C1777R.layout.rounded_corners_bottom;
        }
        View inflate = layoutInflater2.inflate(i2, (ViewGroup) null);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        this.rootView = (ViewGroup) inflate;
        this.viewProviderMap = new HashMap();
    }
}
