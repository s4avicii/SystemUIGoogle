package com.android.systemui.navigationbar;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import java.util.function.Consumer;

public class NavigationBarOverlayController {
    public final Context mContext;

    public View getCurrentView() {
        return null;
    }

    public void init(Consumer<Boolean> consumer, Consumer<Rect> consumer2) {
    }

    public boolean isNavigationBarOverlayEnabled() {
        return false;
    }

    public boolean isVisible() {
        return false;
    }

    public void registerListeners() {
    }

    public void setButtonState(boolean z, boolean z2) {
    }

    public void setCanShow(boolean z) {
    }

    public void unregisterListeners() {
    }

    public NavigationBarOverlayController(Context context) {
        this.mContext = context;
    }
}
