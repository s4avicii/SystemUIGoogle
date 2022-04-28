package com.android.keyguard;

import android.util.MathUtils;
import java.util.Objects;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: FontInterpolator.kt */
public final class FontInterpolator$lerp$newAxes$1 extends Lambda implements Function3<String, Float, Float, Float> {
    public final /* synthetic */ float $progress;
    public final /* synthetic */ FontInterpolator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FontInterpolator$lerp$newAxes$1(FontInterpolator fontInterpolator, float f) {
        super(3);
        this.this$0 = fontInterpolator;
        this.$progress = f;
    }

    public final Object invoke(Object obj, Object obj2, Object obj3) {
        float f;
        boolean z;
        float f2;
        float f3;
        String str = (String) obj;
        Float f4 = (Float) obj2;
        Float f5 = (Float) obj3;
        if (Intrinsics.areEqual(str, "wght")) {
            FontInterpolator fontInterpolator = this.this$0;
            float f6 = 400.0f;
            if (f4 == null) {
                f3 = 400.0f;
            } else {
                f3 = f4.floatValue();
            }
            if (f5 != null) {
                f6 = f5.floatValue();
            }
            float lerp = MathUtils.lerp(f3, f6, this.$progress);
            Objects.requireNonNull(fontInterpolator);
            f = FontInterpolator.coerceInWithStep(lerp, 1000.0f, 10.0f);
        } else if (Intrinsics.areEqual(str, "ital")) {
            FontInterpolator fontInterpolator2 = this.this$0;
            float f7 = 0.0f;
            if (f4 == null) {
                f2 = 0.0f;
            } else {
                f2 = f4.floatValue();
            }
            if (f5 != null) {
                f7 = f5.floatValue();
            }
            float lerp2 = MathUtils.lerp(f2, f7, this.$progress);
            Objects.requireNonNull(fontInterpolator2);
            f = FontInterpolator.coerceInWithStep(lerp2, 1.0f, 0.1f);
        } else {
            if (f4 == null || f5 == null) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                f = MathUtils.lerp(f4.floatValue(), f5.floatValue(), this.$progress);
            } else {
                throw new IllegalArgumentException(Intrinsics.stringPlus("Unable to interpolate due to unknown default axes value : ", str).toString());
            }
        }
        return Float.valueOf(f);
    }
}
