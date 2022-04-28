package com.android.systemui.p006qs.tileimpl;

import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tileimpl.QSTileViewImpl$singleAnimator$1$1 */
/* compiled from: QSTileViewImpl.kt */
public final class QSTileViewImpl$singleAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ QSTileViewImpl this$0;

    public QSTileViewImpl$singleAnimator$1$1(QSTileViewImpl qSTileViewImpl) {
        this.this$0 = qSTileViewImpl;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        QSTileViewImpl qSTileViewImpl = this.this$0;
        Object animatedValue = valueAnimator.getAnimatedValue("background");
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Int");
        int intValue = ((Integer) animatedValue).intValue();
        Object animatedValue2 = valueAnimator.getAnimatedValue("label");
        Objects.requireNonNull(animatedValue2, "null cannot be cast to non-null type kotlin.Int");
        int intValue2 = ((Integer) animatedValue2).intValue();
        Object animatedValue3 = valueAnimator.getAnimatedValue("secondaryLabel");
        Objects.requireNonNull(animatedValue3, "null cannot be cast to non-null type kotlin.Int");
        int intValue3 = ((Integer) animatedValue3).intValue();
        Object animatedValue4 = valueAnimator.getAnimatedValue("chevron");
        Objects.requireNonNull(animatedValue4, "null cannot be cast to non-null type kotlin.Int");
        int intValue4 = ((Integer) animatedValue4).intValue();
        Objects.requireNonNull(qSTileViewImpl);
        Drawable drawable = qSTileViewImpl.colorBackgroundDrawable;
        ImageView imageView = null;
        if (drawable == null) {
            drawable = null;
        }
        drawable.mutate().setTint(intValue);
        qSTileViewImpl.paintColor = intValue;
        TextView textView = qSTileViewImpl.label;
        if (textView == null) {
            textView = null;
        }
        textView.setTextColor(intValue2);
        qSTileViewImpl.getSecondaryLabel().setTextColor(intValue3);
        ImageView imageView2 = qSTileViewImpl.chevronView;
        if (imageView2 != null) {
            imageView = imageView2;
        }
        imageView.setImageTintList(ColorStateList.valueOf(intValue4));
    }
}
