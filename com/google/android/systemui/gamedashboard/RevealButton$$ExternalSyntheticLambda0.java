package com.google.android.systemui.gamedashboard;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.google.android.systemui.gamedashboard.RevealButton;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class RevealButton$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ RevealButton f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Rect f$2;
    public final /* synthetic */ int f$3;

    public /* synthetic */ RevealButton$$ExternalSyntheticLambda0(RevealButton revealButton, int i, Rect rect, int i2) {
        this.f$0 = revealButton;
        this.f$1 = i;
        this.f$2 = rect;
        this.f$3 = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        RevealButton revealButton = this.f$0;
        int i = this.f$1;
        Rect rect = this.f$2;
        int i2 = this.f$3;
        RevealButton.C22821 r3 = RevealButton.BACKGROUND_WIDTH;
        Objects.requireNonNull(revealButton);
        float floatValue = (((Float) valueAnimator.getAnimatedValue()).floatValue() * ((float) i)) / 2.0f;
        int centerX = (int) (((float) rect.centerX()) + floatValue);
        float f = ((float) i2) / 2.0f;
        revealButton.mBackground.setBounds((int) (((float) rect.centerX()) - floatValue), (int) (((float) rect.centerY()) - f), centerX, (int) (((float) rect.centerY()) + f));
    }
}
