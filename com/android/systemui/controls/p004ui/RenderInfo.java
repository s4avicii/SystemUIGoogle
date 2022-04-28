package com.android.systemui.controls.p004ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.util.ArrayMap;
import android.util.SparseArray;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import kotlin.Pair;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.RenderInfo */
/* compiled from: RenderInfo.kt */
public final class RenderInfo {
    public static final ArrayMap<ComponentName, Drawable> appIconMap = new ArrayMap<>();
    public static final SparseArray<Drawable> iconMap = new SparseArray<>();
    public final int enabledBackground;
    public final int foreground;
    public final Drawable icon;

    /* renamed from: com.android.systemui.controls.ui.RenderInfo$Companion */
    /* compiled from: RenderInfo.kt */
    public static final class Companion {
        public static RenderInfo lookup(Context context, ComponentName componentName, int i, int i2) {
            Drawable drawable;
            if (i2 > 0) {
                i = (i * 1000) + i2;
            }
            Pair pair = (Pair) MapsKt___MapsKt.getValue(RenderInfoKt.deviceColorMap, Integer.valueOf(i));
            int intValue = ((Number) pair.component1()).intValue();
            int intValue2 = ((Number) pair.component2()).intValue();
            int intValue3 = ((Number) MapsKt___MapsKt.getValue(RenderInfoKt.deviceIconMap, Integer.valueOf(i))).intValue();
            if (intValue3 == -1) {
                ArrayMap<ComponentName, Drawable> arrayMap = RenderInfo.appIconMap;
                drawable = arrayMap.get(componentName);
                if (drawable == null) {
                    drawable = context.getResources().getDrawable(C1777R.C1778drawable.ic_device_unknown_on, (Resources.Theme) null);
                    arrayMap.put(componentName, drawable);
                }
            } else {
                SparseArray<Drawable> sparseArray = RenderInfo.iconMap;
                drawable = sparseArray.get(intValue3);
                if (drawable == null) {
                    drawable = context.getResources().getDrawable(intValue3, (Resources.Theme) null);
                    sparseArray.put(intValue3, drawable);
                }
            }
            Intrinsics.checkNotNull(drawable);
            return new RenderInfo(drawable.getConstantState().newDrawable(context.getResources()), intValue, intValue2);
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RenderInfo)) {
            return false;
        }
        RenderInfo renderInfo = (RenderInfo) obj;
        return Intrinsics.areEqual(this.icon, renderInfo.icon) && this.foreground == renderInfo.foreground && this.enabledBackground == renderInfo.enabledBackground;
    }

    public final int hashCode() {
        return Integer.hashCode(this.enabledBackground) + FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.foreground, this.icon.hashCode() * 31, 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("RenderInfo(icon=");
        m.append(this.icon);
        m.append(", foreground=");
        m.append(this.foreground);
        m.append(", enabledBackground=");
        return Insets$$ExternalSyntheticOutline0.m11m(m, this.enabledBackground, ')');
    }

    public RenderInfo(Drawable drawable, int i, int i2) {
        this.icon = drawable;
        this.foreground = i;
        this.enabledBackground = i2;
    }
}
