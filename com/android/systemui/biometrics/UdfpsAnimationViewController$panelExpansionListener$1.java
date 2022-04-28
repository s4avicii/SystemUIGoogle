package com.android.systemui.biometrics;

import com.android.systemui.statusbar.phone.panelstate.PanelExpansionListener;
import java.util.Objects;

/* compiled from: UdfpsAnimationViewController.kt */
public final class UdfpsAnimationViewController$panelExpansionListener$1 implements PanelExpansionListener {
    public final /* synthetic */ T $view;
    public final /* synthetic */ UdfpsAnimationViewController<T> this$0;

    public UdfpsAnimationViewController$panelExpansionListener$1(UdfpsAnimationViewController<T> udfpsAnimationViewController, T t) {
        this.this$0 = udfpsAnimationViewController;
        this.$view = t;
    }

    public final void onPanelExpansionChanged(float f, boolean z, boolean z2) {
        boolean z3;
        UdfpsAnimationViewController<T> udfpsAnimationViewController = this.this$0;
        int i = 0;
        if (!z || f <= 0.0f) {
            z3 = false;
        } else {
            z3 = true;
        }
        Objects.requireNonNull(udfpsAnimationViewController);
        udfpsAnimationViewController.notificationShadeVisible = z3;
        T t = this.$view;
        Objects.requireNonNull(t);
        if (f < 0.4f) {
            i = (int) ((1.0f - (f / 0.4f)) * 255.0f);
        }
        t.mAlpha = i;
        t.updateAlpha();
        this.this$0.updatePauseAuth();
    }
}
