package com.android.systemui.p006qs.carrier;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.carrier.CellSignalState */
/* compiled from: CellSignalState.kt */
public final class CellSignalState {
    public final String contentDescription;
    public final int mobileSignalIconId;
    public final boolean providerModelBehavior;
    public final boolean roaming;
    public final String typeContentDescription;
    public final boolean visible;

    public CellSignalState() {
        this(false, 0, (String) null, (String) null, false, false);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CellSignalState)) {
            return false;
        }
        CellSignalState cellSignalState = (CellSignalState) obj;
        return this.visible == cellSignalState.visible && this.mobileSignalIconId == cellSignalState.mobileSignalIconId && Intrinsics.areEqual(this.contentDescription, cellSignalState.contentDescription) && Intrinsics.areEqual(this.typeContentDescription, cellSignalState.typeContentDescription) && this.roaming == cellSignalState.roaming && this.providerModelBehavior == cellSignalState.providerModelBehavior;
    }

    public CellSignalState(boolean z, int i, String str, String str2, boolean z2, boolean z3) {
        this.visible = z;
        this.mobileSignalIconId = i;
        this.contentDescription = str;
        this.typeContentDescription = str2;
        this.roaming = z2;
        this.providerModelBehavior = z3;
    }

    public final CellSignalState changeVisibility(boolean z) {
        if (this.visible == z) {
            return this;
        }
        return new CellSignalState(z, this.mobileSignalIconId, this.contentDescription, this.typeContentDescription, this.roaming, this.providerModelBehavior);
    }

    public final int hashCode() {
        int i;
        boolean z = this.visible;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.mobileSignalIconId, (z ? 1 : 0) * true, 31);
        String str = this.contentDescription;
        int i2 = 0;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        int i3 = (m + i) * 31;
        String str2 = this.typeContentDescription;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        int i4 = (i3 + i2) * 31;
        boolean z3 = this.roaming;
        if (z3) {
            z3 = true;
        }
        int i5 = (i4 + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.providerModelBehavior;
        if (!z4) {
            z2 = z4;
        }
        return i5 + (z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CellSignalState(visible=");
        m.append(this.visible);
        m.append(", mobileSignalIconId=");
        m.append(this.mobileSignalIconId);
        m.append(", contentDescription=");
        m.append(this.contentDescription);
        m.append(", typeContentDescription=");
        m.append(this.typeContentDescription);
        m.append(", roaming=");
        m.append(this.roaming);
        m.append(", providerModelBehavior=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.providerModelBehavior, ')');
    }
}
