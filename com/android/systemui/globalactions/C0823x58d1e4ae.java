package com.android.systemui.globalactions;

import android.animation.ValueAnimator;
import android.view.Window;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0823x58d1e4ae implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ Window f$2;
    public final /* synthetic */ float f$3;
    public final /* synthetic */ int f$4;

    public /* synthetic */ C0823x58d1e4ae(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite, boolean z, Window window, float f, int i) {
        this.f$0 = actionsDialogLite;
        this.f$1 = z;
        this.f$2 = window;
        this.f$3 = f;
        this.f$4 = i;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f;
        float f2;
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        boolean z = this.f$1;
        Window window = this.f$2;
        float f3 = this.f$3;
        int i = this.f$4;
        int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(actionsDialogLite);
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        if (z) {
            f = floatValue;
        } else {
            f = 1.0f - floatValue;
        }
        actionsDialogLite.mGlobalActionsLayout.setAlpha(f);
        window.setDimAmount(actionsDialogLite.mWindowDimAmount * f);
        if (z) {
            f2 = (1.0f - floatValue) * f3;
        } else {
            f2 = f3 * floatValue;
        }
        if (i == 0) {
            actionsDialogLite.mGlobalActionsLayout.setTranslationX(f2);
        } else if (i == 1) {
            actionsDialogLite.mGlobalActionsLayout.setTranslationY(-f2);
        } else if (i == 2) {
            actionsDialogLite.mGlobalActionsLayout.setTranslationX(-f2);
        } else if (i == 3) {
            actionsDialogLite.mGlobalActionsLayout.setTranslationY(f2);
        }
    }
}
