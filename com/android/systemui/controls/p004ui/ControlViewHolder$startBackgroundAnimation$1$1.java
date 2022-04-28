package com.android.systemui.controls.p004ui;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.MathUtils;
import com.android.internal.graphics.ColorUtils;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ControlViewHolder$startBackgroundAnimation$1$1 */
/* compiled from: ControlViewHolder.kt */
public final class ControlViewHolder$startBackgroundAnimation$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ Drawable $clipDrawable;
    public final /* synthetic */ int $newBaseColor;
    public final /* synthetic */ int $newClipColor;
    public final /* synthetic */ float $oldAlpha;
    public final /* synthetic */ int $oldBaseColor;
    public final /* synthetic */ int $oldClipColor;
    public final /* synthetic */ ControlViewHolder this$0;

    public ControlViewHolder$startBackgroundAnimation$1$1(int i, int i2, int i3, int i4, float f, ControlViewHolder controlViewHolder, Drawable drawable) {
        this.$oldClipColor = i;
        this.$newClipColor = i2;
        this.$oldBaseColor = i3;
        this.$newBaseColor = i4;
        this.$oldAlpha = f;
        this.this$0 = controlViewHolder;
        this.$clipDrawable = drawable;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int intValue = ((Integer) animatedValue).intValue();
        int blendARGB = ColorUtils.blendARGB(this.$oldClipColor, this.$newClipColor, valueAnimator.getAnimatedFraction());
        int blendARGB2 = ColorUtils.blendARGB(this.$oldBaseColor, this.$newBaseColor, valueAnimator.getAnimatedFraction());
        float lerp = MathUtils.lerp(this.$oldAlpha, 1.0f, valueAnimator.getAnimatedFraction());
        ControlViewHolder controlViewHolder = this.this$0;
        Drawable drawable = this.$clipDrawable;
        Objects.requireNonNull(controlViewHolder);
        drawable.setAlpha(intValue);
        if (drawable instanceof GradientDrawable) {
            ((GradientDrawable) drawable).setColor(blendARGB);
        }
        controlViewHolder.baseLayer.setColor(blendARGB2);
        controlViewHolder.layout.setAlpha(lerp);
    }
}
