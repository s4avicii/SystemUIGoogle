package com.android.systemui.accessibility.floatingmenu;

import android.graphics.Rect;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AccessibilityFloatingMenuView$$ExternalSyntheticLambda2 implements View.OnApplyWindowInsetsListener {
    public final /* synthetic */ AccessibilityFloatingMenuView f$0;

    public /* synthetic */ AccessibilityFloatingMenuView$$ExternalSyntheticLambda2(AccessibilityFloatingMenuView accessibilityFloatingMenuView) {
        this.f$0 = accessibilityFloatingMenuView;
    }

    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        boolean z;
        AccessibilityFloatingMenuView accessibilityFloatingMenuView = this.f$0;
        Objects.requireNonNull(accessibilityFloatingMenuView);
        WindowMetrics currentWindowMetrics = accessibilityFloatingMenuView.mWindowManager.getCurrentWindowMetrics();
        if (!currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout()).toRect().equals(accessibilityFloatingMenuView.mDisplayInsetsRect)) {
            accessibilityFloatingMenuView.updateDisplaySizeWith(currentWindowMetrics);
            accessibilityFloatingMenuView.updateLocationWith(accessibilityFloatingMenuView.mPosition);
        }
        Rect rect = currentWindowMetrics.getWindowInsets().getInsets(WindowInsets.Type.ime()).toRect();
        if (!rect.equals(accessibilityFloatingMenuView.mImeInsetsRect)) {
            if (rect.left == 0 && rect.top == 0 && rect.right == 0 && rect.bottom == 0) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                accessibilityFloatingMenuView.mImeInsetsRect.set(rect);
            } else {
                accessibilityFloatingMenuView.mImeInsetsRect.setEmpty();
            }
            accessibilityFloatingMenuView.updateLocationWith(accessibilityFloatingMenuView.mPosition);
        }
        return windowInsets;
    }
}
