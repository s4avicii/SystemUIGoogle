package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import android.util.MathUtils;
import android.view.View;
import com.android.internal.graphics.ColorUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrimController$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScrimController f$0;
    public final /* synthetic */ View f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ScrimController$$ExternalSyntheticLambda0(ScrimController scrimController, View view, int i) {
        this.f$0 = scrimController;
        this.f$1 = view;
        this.f$2 = i;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScrimController scrimController = this.f$0;
        View view = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(scrimController);
        float floatValue = ((Float) view.getTag(ScrimController.TAG_START_ALPHA)).floatValue();
        float floatValue2 = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        scrimController.updateScrimColor(view, MathUtils.constrain(MathUtils.lerp(floatValue, scrimController.getCurrentScrimAlpha(view), floatValue2), 0.0f, 1.0f), ColorUtils.blendARGB(i, scrimController.getCurrentScrimTint(view), floatValue2));
        scrimController.dispatchScrimsVisible();
    }
}
