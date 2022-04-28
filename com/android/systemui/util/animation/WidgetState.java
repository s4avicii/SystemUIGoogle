package com.android.systemui.util.animation;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: TransitionLayout.kt */
public final class WidgetState {
    public float alpha;
    public boolean gone;
    public int height;
    public int measureHeight;
    public int measureWidth;
    public float scale;
    public int width;

    /* renamed from: x */
    public float f84x;

    /* renamed from: y */
    public float f85y;

    public WidgetState() {
        this(511);
    }

    public WidgetState(float f, float f2, int i, int i2, int i3, int i4, float f3, float f4, boolean z) {
        this.f84x = f;
        this.f85y = f2;
        this.width = i;
        this.height = i2;
        this.measureWidth = i3;
        this.measureHeight = i4;
        this.alpha = f3;
        this.scale = f4;
        this.gone = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WidgetState)) {
            return false;
        }
        WidgetState widgetState = (WidgetState) obj;
        return Intrinsics.areEqual(Float.valueOf(this.f84x), Float.valueOf(widgetState.f84x)) && Intrinsics.areEqual(Float.valueOf(this.f85y), Float.valueOf(widgetState.f85y)) && this.width == widgetState.width && this.height == widgetState.height && this.measureWidth == widgetState.measureWidth && this.measureHeight == widgetState.measureHeight && Intrinsics.areEqual(Float.valueOf(this.alpha), Float.valueOf(widgetState.alpha)) && Intrinsics.areEqual(Float.valueOf(this.scale), Float.valueOf(widgetState.scale)) && this.gone == widgetState.gone;
    }

    public final int hashCode() {
        int hashCode = Float.hashCode(this.f85y);
        int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.measureHeight, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.measureWidth, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.height, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.width, (hashCode + (Float.hashCode(this.f84x) * 31)) * 31, 31), 31), 31), 31);
        int hashCode2 = (Float.hashCode(this.scale) + ((Float.hashCode(this.alpha) + m) * 31)) * 31;
        boolean z = this.gone;
        if (z) {
            z = true;
        }
        return hashCode2 + (z ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("WidgetState(x=");
        m.append(this.f84x);
        m.append(", y=");
        m.append(this.f85y);
        m.append(", width=");
        m.append(this.width);
        m.append(", height=");
        m.append(this.height);
        m.append(", measureWidth=");
        m.append(this.measureWidth);
        m.append(", measureHeight=");
        m.append(this.measureHeight);
        m.append(", alpha=");
        m.append(this.alpha);
        m.append(", scale=");
        m.append(this.scale);
        m.append(", gone=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.gone, ')');
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ WidgetState(int i) {
        this(0.0f, 0.0f, 0, 0, 0, 0, (i & 64) != 0 ? 1.0f : 0.0f, (i & 128) != 0 ? 1.0f : 0.0f, false);
    }
}
