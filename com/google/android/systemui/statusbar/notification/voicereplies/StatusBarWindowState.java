package com.google.android.systemui.statusbar.notification.voicereplies;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;

/* compiled from: NotificationVoiceReplyManager.kt */
public final class StatusBarWindowState {
    public final boolean bouncerShowing;
    public final boolean keyguardOccluded;
    public final boolean keyguardShowing;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StatusBarWindowState)) {
            return false;
        }
        StatusBarWindowState statusBarWindowState = (StatusBarWindowState) obj;
        return this.keyguardShowing == statusBarWindowState.keyguardShowing && this.keyguardOccluded == statusBarWindowState.keyguardOccluded && this.bouncerShowing == statusBarWindowState.bouncerShowing;
    }

    public final int hashCode() {
        boolean z = this.keyguardShowing;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (z ? 1 : 0) * true;
        boolean z3 = this.keyguardOccluded;
        if (z3) {
            z3 = true;
        }
        int i2 = (i + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.bouncerShowing;
        if (!z4) {
            z2 = z4;
        }
        return i2 + (z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("StatusBarWindowState(keyguardShowing=");
        m.append(this.keyguardShowing);
        m.append(", keyguardOccluded=");
        m.append(this.keyguardOccluded);
        m.append(", bouncerShowing=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.bouncerShowing, ')');
    }

    public StatusBarWindowState(boolean z, boolean z2, boolean z3) {
        this.keyguardShowing = z;
        this.keyguardOccluded = z2;
        this.bouncerShowing = z3;
    }
}
