package com.google.android.material.internal;

import android.view.ViewGroup;
import android.view.ViewOverlay;

public final class ViewOverlayApi18 {
    public final ViewOverlay viewOverlay;

    public ViewOverlayApi18(ViewGroup viewGroup) {
        this.viewOverlay = viewGroup.getOverlay();
    }
}
