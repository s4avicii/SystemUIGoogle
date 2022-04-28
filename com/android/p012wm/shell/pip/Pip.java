package com.android.p012wm.shell.pip;

import android.content.res.Configuration;
import android.graphics.Rect;
import java.io.PrintWriter;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.Pip */
public interface Pip {
    void addPipExclusionBoundsChangeListener(Consumer<Rect> consumer) {
    }

    IPip createExternalInterface() {
        return null;
    }

    void dump(PrintWriter printWriter) {
    }

    void hidePipMenu() {
    }

    void onConfigurationChanged(Configuration configuration) {
    }

    void onDensityOrFontScaleChanged() {
    }

    void onOverlayChanged() {
    }

    void onSystemUiStateChanged(boolean z, int i) {
    }

    void registerSessionListenerForCurrentUser() {
    }

    void removePipExclusionBoundsChangeListener(Consumer<Rect> consumer) {
    }

    void setPinnedStackAnimationType() {
    }

    void showPictureInPictureMenu() {
    }
}
