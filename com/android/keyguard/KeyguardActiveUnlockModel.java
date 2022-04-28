package com.android.keyguard;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: KeyguardListenModel.kt */
public final class KeyguardActiveUnlockModel extends KeyguardListenModel {
    public final boolean authInterruptActive;
    public final boolean encryptedOrTimedOut;
    public final boolean fpLockout;
    public final boolean listening;
    public final boolean lockDown;
    public final boolean switchingUser;
    public final long timeMillis;
    public final boolean triggerActiveUnlockForAssistant;
    public final boolean userCanDismissLockScreen;
    public final int userId;

    public KeyguardActiveUnlockModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        super(0);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.authInterruptActive = z2;
        this.encryptedOrTimedOut = z3;
        this.fpLockout = z4;
        this.lockDown = z5;
        this.switchingUser = z6;
        this.triggerActiveUnlockForAssistant = z7;
        this.userCanDismissLockScreen = z8;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardActiveUnlockModel)) {
            return false;
        }
        KeyguardActiveUnlockModel keyguardActiveUnlockModel = (KeyguardActiveUnlockModel) obj;
        long j = this.timeMillis;
        Objects.requireNonNull(keyguardActiveUnlockModel);
        return j == keyguardActiveUnlockModel.timeMillis && this.userId == keyguardActiveUnlockModel.userId && this.listening == keyguardActiveUnlockModel.listening && this.authInterruptActive == keyguardActiveUnlockModel.authInterruptActive && this.encryptedOrTimedOut == keyguardActiveUnlockModel.encryptedOrTimedOut && this.fpLockout == keyguardActiveUnlockModel.fpLockout && this.lockDown == keyguardActiveUnlockModel.lockDown && this.switchingUser == keyguardActiveUnlockModel.switchingUser && this.triggerActiveUnlockForAssistant == keyguardActiveUnlockModel.triggerActiveUnlockForAssistant && this.userCanDismissLockScreen == keyguardActiveUnlockModel.userCanDismissLockScreen;
    }

    public final int hashCode() {
        int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.userId, Long.hashCode(this.timeMillis) * 31, 31);
        boolean z = this.listening;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (m + (z ? 1 : 0)) * 31;
        boolean z3 = this.authInterruptActive;
        if (z3) {
            z3 = true;
        }
        int i2 = (i + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.encryptedOrTimedOut;
        if (z4) {
            z4 = true;
        }
        int i3 = (i2 + (z4 ? 1 : 0)) * 31;
        boolean z5 = this.fpLockout;
        if (z5) {
            z5 = true;
        }
        int i4 = (i3 + (z5 ? 1 : 0)) * 31;
        boolean z6 = this.lockDown;
        if (z6) {
            z6 = true;
        }
        int i5 = (i4 + (z6 ? 1 : 0)) * 31;
        boolean z7 = this.switchingUser;
        if (z7) {
            z7 = true;
        }
        int i6 = (i5 + (z7 ? 1 : 0)) * 31;
        boolean z8 = this.triggerActiveUnlockForAssistant;
        if (z8) {
            z8 = true;
        }
        int i7 = (i6 + (z8 ? 1 : 0)) * 31;
        boolean z9 = this.userCanDismissLockScreen;
        if (!z9) {
            z2 = z9;
        }
        return i7 + (z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("KeyguardActiveUnlockModel(timeMillis=");
        m.append(this.timeMillis);
        m.append(", userId=");
        m.append(this.userId);
        m.append(", listening=");
        m.append(this.listening);
        m.append(", authInterruptActive=");
        m.append(this.authInterruptActive);
        m.append(", encryptedOrTimedOut=");
        m.append(this.encryptedOrTimedOut);
        m.append(", fpLockout=");
        m.append(this.fpLockout);
        m.append(", lockDown=");
        m.append(this.lockDown);
        m.append(", switchingUser=");
        m.append(this.switchingUser);
        m.append(", triggerActiveUnlockForAssistant=");
        m.append(this.triggerActiveUnlockForAssistant);
        m.append(", userCanDismissLockScreen=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.userCanDismissLockScreen, ')');
    }

    public final boolean getListening() {
        return this.listening;
    }

    public final long getTimeMillis() {
        return this.timeMillis;
    }
}
