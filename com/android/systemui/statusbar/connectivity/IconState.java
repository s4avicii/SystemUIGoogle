package com.android.systemui.statusbar.connectivity;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SignalCallback.kt */
public final class IconState {
    public final String contentDescription;
    public final int icon;
    public final boolean visible;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IconState)) {
            return false;
        }
        IconState iconState = (IconState) obj;
        return this.visible == iconState.visible && this.icon == iconState.icon && Intrinsics.areEqual(this.contentDescription, iconState.contentDescription);
    }

    public final int hashCode() {
        boolean z = this.visible;
        if (z) {
            z = true;
        }
        return this.contentDescription.hashCode() + FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.icon, (z ? 1 : 0) * true, 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("[visible=");
        m.append(this.visible);
        m.append(',');
        m.append("icon=");
        m.append(this.icon);
        m.append(',');
        m.append("contentDescription=");
        m.append(this.contentDescription);
        m.append(']');
        return m.toString();
    }

    public IconState(boolean z, int i, String str) {
        this.visible = z;
        this.icon = i;
        this.contentDescription = str;
    }
}
