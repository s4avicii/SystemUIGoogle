package com.android.keyguard;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: KeyguardListenModel.kt */
public final class KeyguardFingerprintListenModel extends KeyguardListenModel {
    public final boolean biometricEnabledForUser;
    public final boolean bouncer;
    public final boolean canSkipBouncer;
    public final boolean credentialAttempted;
    public final boolean deviceInteractive;
    public final boolean dreaming;
    public final boolean encryptedOrLockdown;
    public final boolean fingerprintDisabled;
    public final boolean fingerprintLockedOut;
    public final boolean goingToSleep;
    public final boolean keyguardGoingAway;
    public final boolean keyguardIsVisible;
    public final boolean keyguardOccluded;
    public final boolean listening;
    public final boolean occludingAppRequestingFp;
    public final boolean primaryUser;
    public final boolean shouldListenForFingerprintAssistant;
    public final boolean switchingUser;
    public final long timeMillis;
    public final boolean udfps;
    public final boolean userDoesNotHaveTrust;
    public final int userId;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardFingerprintListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20) {
        super(0);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.biometricEnabledForUser = z2;
        this.bouncer = z3;
        this.canSkipBouncer = z4;
        this.credentialAttempted = z5;
        this.deviceInteractive = z6;
        this.dreaming = z7;
        this.encryptedOrLockdown = z8;
        this.fingerprintDisabled = z9;
        this.fingerprintLockedOut = z10;
        this.goingToSleep = z11;
        this.keyguardGoingAway = z12;
        this.keyguardIsVisible = z13;
        this.keyguardOccluded = z14;
        this.occludingAppRequestingFp = z15;
        this.primaryUser = z16;
        this.shouldListenForFingerprintAssistant = z17;
        this.switchingUser = z18;
        this.udfps = z19;
        this.userDoesNotHaveTrust = z20;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof KeyguardFingerprintListenModel)) {
            return false;
        }
        KeyguardFingerprintListenModel keyguardFingerprintListenModel = (KeyguardFingerprintListenModel) obj;
        long j = this.timeMillis;
        Objects.requireNonNull(keyguardFingerprintListenModel);
        return j == keyguardFingerprintListenModel.timeMillis && this.userId == keyguardFingerprintListenModel.userId && this.listening == keyguardFingerprintListenModel.listening && this.biometricEnabledForUser == keyguardFingerprintListenModel.biometricEnabledForUser && this.bouncer == keyguardFingerprintListenModel.bouncer && this.canSkipBouncer == keyguardFingerprintListenModel.canSkipBouncer && this.credentialAttempted == keyguardFingerprintListenModel.credentialAttempted && this.deviceInteractive == keyguardFingerprintListenModel.deviceInteractive && this.dreaming == keyguardFingerprintListenModel.dreaming && this.encryptedOrLockdown == keyguardFingerprintListenModel.encryptedOrLockdown && this.fingerprintDisabled == keyguardFingerprintListenModel.fingerprintDisabled && this.fingerprintLockedOut == keyguardFingerprintListenModel.fingerprintLockedOut && this.goingToSleep == keyguardFingerprintListenModel.goingToSleep && this.keyguardGoingAway == keyguardFingerprintListenModel.keyguardGoingAway && this.keyguardIsVisible == keyguardFingerprintListenModel.keyguardIsVisible && this.keyguardOccluded == keyguardFingerprintListenModel.keyguardOccluded && this.occludingAppRequestingFp == keyguardFingerprintListenModel.occludingAppRequestingFp && this.primaryUser == keyguardFingerprintListenModel.primaryUser && this.shouldListenForFingerprintAssistant == keyguardFingerprintListenModel.shouldListenForFingerprintAssistant && this.switchingUser == keyguardFingerprintListenModel.switchingUser && this.udfps == keyguardFingerprintListenModel.udfps && this.userDoesNotHaveTrust == keyguardFingerprintListenModel.userDoesNotHaveTrust;
    }

    public final int hashCode() {
        int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.userId, Long.hashCode(this.timeMillis) * 31, 31);
        boolean z = this.listening;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (m + (z ? 1 : 0)) * 31;
        boolean z3 = this.biometricEnabledForUser;
        if (z3) {
            z3 = true;
        }
        int i2 = (i + (z3 ? 1 : 0)) * 31;
        boolean z4 = this.bouncer;
        if (z4) {
            z4 = true;
        }
        int i3 = (i2 + (z4 ? 1 : 0)) * 31;
        boolean z5 = this.canSkipBouncer;
        if (z5) {
            z5 = true;
        }
        int i4 = (i3 + (z5 ? 1 : 0)) * 31;
        boolean z6 = this.credentialAttempted;
        if (z6) {
            z6 = true;
        }
        int i5 = (i4 + (z6 ? 1 : 0)) * 31;
        boolean z7 = this.deviceInteractive;
        if (z7) {
            z7 = true;
        }
        int i6 = (i5 + (z7 ? 1 : 0)) * 31;
        boolean z8 = this.dreaming;
        if (z8) {
            z8 = true;
        }
        int i7 = (i6 + (z8 ? 1 : 0)) * 31;
        boolean z9 = this.encryptedOrLockdown;
        if (z9) {
            z9 = true;
        }
        int i8 = (i7 + (z9 ? 1 : 0)) * 31;
        boolean z10 = this.fingerprintDisabled;
        if (z10) {
            z10 = true;
        }
        int i9 = (i8 + (z10 ? 1 : 0)) * 31;
        boolean z11 = this.fingerprintLockedOut;
        if (z11) {
            z11 = true;
        }
        int i10 = (i9 + (z11 ? 1 : 0)) * 31;
        boolean z12 = this.goingToSleep;
        if (z12) {
            z12 = true;
        }
        int i11 = (i10 + (z12 ? 1 : 0)) * 31;
        boolean z13 = this.keyguardGoingAway;
        if (z13) {
            z13 = true;
        }
        int i12 = (i11 + (z13 ? 1 : 0)) * 31;
        boolean z14 = this.keyguardIsVisible;
        if (z14) {
            z14 = true;
        }
        int i13 = (i12 + (z14 ? 1 : 0)) * 31;
        boolean z15 = this.keyguardOccluded;
        if (z15) {
            z15 = true;
        }
        int i14 = (i13 + (z15 ? 1 : 0)) * 31;
        boolean z16 = this.occludingAppRequestingFp;
        if (z16) {
            z16 = true;
        }
        int i15 = (i14 + (z16 ? 1 : 0)) * 31;
        boolean z17 = this.primaryUser;
        if (z17) {
            z17 = true;
        }
        int i16 = (i15 + (z17 ? 1 : 0)) * 31;
        boolean z18 = this.shouldListenForFingerprintAssistant;
        if (z18) {
            z18 = true;
        }
        int i17 = (i16 + (z18 ? 1 : 0)) * 31;
        boolean z19 = this.switchingUser;
        if (z19) {
            z19 = true;
        }
        int i18 = (i17 + (z19 ? 1 : 0)) * 31;
        boolean z20 = this.udfps;
        if (z20) {
            z20 = true;
        }
        int i19 = (i18 + (z20 ? 1 : 0)) * 31;
        boolean z21 = this.userDoesNotHaveTrust;
        if (!z21) {
            z2 = z21;
        }
        return i19 + (z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("KeyguardFingerprintListenModel(timeMillis=");
        m.append(this.timeMillis);
        m.append(", userId=");
        m.append(this.userId);
        m.append(", listening=");
        m.append(this.listening);
        m.append(", biometricEnabledForUser=");
        m.append(this.biometricEnabledForUser);
        m.append(", bouncer=");
        m.append(this.bouncer);
        m.append(", canSkipBouncer=");
        m.append(this.canSkipBouncer);
        m.append(", credentialAttempted=");
        m.append(this.credentialAttempted);
        m.append(", deviceInteractive=");
        m.append(this.deviceInteractive);
        m.append(", dreaming=");
        m.append(this.dreaming);
        m.append(", encryptedOrLockdown=");
        m.append(this.encryptedOrLockdown);
        m.append(", fingerprintDisabled=");
        m.append(this.fingerprintDisabled);
        m.append(", fingerprintLockedOut=");
        m.append(this.fingerprintLockedOut);
        m.append(", goingToSleep=");
        m.append(this.goingToSleep);
        m.append(", keyguardGoingAway=");
        m.append(this.keyguardGoingAway);
        m.append(", keyguardIsVisible=");
        m.append(this.keyguardIsVisible);
        m.append(", keyguardOccluded=");
        m.append(this.keyguardOccluded);
        m.append(", occludingAppRequestingFp=");
        m.append(this.occludingAppRequestingFp);
        m.append(", primaryUser=");
        m.append(this.primaryUser);
        m.append(", shouldListenForFingerprintAssistant=");
        m.append(this.shouldListenForFingerprintAssistant);
        m.append(", switchingUser=");
        m.append(this.switchingUser);
        m.append(", udfps=");
        m.append(this.udfps);
        m.append(", userDoesNotHaveTrust=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.userDoesNotHaveTrust, ')');
    }

    public final boolean getListening() {
        return this.listening;
    }

    public final long getTimeMillis() {
        return this.timeMillis;
    }
}
