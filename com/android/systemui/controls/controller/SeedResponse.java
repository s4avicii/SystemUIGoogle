package com.android.systemui.controls.controller;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsController.kt */
public final class SeedResponse {
    public final boolean accepted;
    public final String packageName;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeedResponse)) {
            return false;
        }
        SeedResponse seedResponse = (SeedResponse) obj;
        return Intrinsics.areEqual(this.packageName, seedResponse.packageName) && this.accepted == seedResponse.accepted;
    }

    public final int hashCode() {
        int hashCode = this.packageName.hashCode() * 31;
        boolean z = this.accepted;
        if (z) {
            z = true;
        }
        return hashCode + (z ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SeedResponse(packageName=");
        m.append(this.packageName);
        m.append(", accepted=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.accepted, ')');
    }

    public SeedResponse(String str, boolean z) {
        this.packageName = str;
        this.accepted = z;
    }
}
