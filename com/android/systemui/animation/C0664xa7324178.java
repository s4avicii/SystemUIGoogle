package com.android.systemui.animation;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.View;
import androidx.leanback.R$drawable;

/* renamed from: com.android.systemui.animation.AnimatedDialog$AnimatedBoundsLayoutListener$onLayoutChange$animator$1$2 */
/* compiled from: DialogLaunchAnimator.kt */
public final class C0664xa7324178 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $bottom;
    public final /* synthetic */ Rect $bounds;
    public final /* synthetic */ int $left;
    public final /* synthetic */ int $right;
    public final /* synthetic */ int $startBottom;
    public final /* synthetic */ int $startLeft;
    public final /* synthetic */ int $startRight;
    public final /* synthetic */ int $startTop;
    public final /* synthetic */ int $top;
    public final /* synthetic */ View $view;

    public C0664xa7324178(Rect rect, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, View view) {
        this.$bounds = rect;
        this.$startLeft = i;
        this.$left = i2;
        this.$startTop = i3;
        this.$top = i4;
        this.$startRight = i5;
        this.$right = i6;
        this.$startBottom = i7;
        this.$bottom = i8;
        this.$view = view;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        float animatedFraction = valueAnimator.getAnimatedFraction();
        this.$bounds.left = R$drawable.roundToInt(MathUtils.lerp(this.$startLeft, this.$left, animatedFraction));
        this.$bounds.top = R$drawable.roundToInt(MathUtils.lerp(this.$startTop, this.$top, animatedFraction));
        this.$bounds.right = R$drawable.roundToInt(MathUtils.lerp(this.$startRight, this.$right, animatedFraction));
        this.$bounds.bottom = R$drawable.roundToInt(MathUtils.lerp(this.$startBottom, this.$bottom, animatedFraction));
        this.$view.setLeft(this.$bounds.left);
        this.$view.setTop(this.$bounds.top);
        this.$view.setRight(this.$bounds.right);
        this.$view.setBottom(this.$bounds.bottom);
    }
}
