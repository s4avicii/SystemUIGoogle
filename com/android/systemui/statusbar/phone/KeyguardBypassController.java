package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.biometrics.BiometricSourceType;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.stack.StackScrollAlgorithm;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.TunerService;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: KeyguardBypassController.kt */
public final class KeyguardBypassController implements Dumpable, StackScrollAlgorithm.BypassController {
    public boolean altBouncerShowing;
    public boolean bouncerShowing;
    public boolean bypassEnabled;
    public final int bypassOverride;
    public final KeyguardBypassController$faceAuthEnabledChangedCallback$1 faceAuthEnabledChangedCallback = new KeyguardBypassController$faceAuthEnabledChangedCallback$1();
    public boolean hasFaceFeature;
    public boolean isPulseExpanding;
    public boolean launchingAffordance;
    public final ArrayList listeners = new ArrayList();
    public final KeyguardStateController mKeyguardStateController;
    public PendingUnlock pendingUnlock;
    public boolean qSExpanded;
    public final StatusBarStateController statusBarStateController;
    public BiometricUnlockController unlockController;
    public boolean userHasDeviceEntryIntent;

    /* compiled from: KeyguardBypassController.kt */
    public interface OnBypassStateChangedListener {
        void onBypassStateChanged(boolean z);
    }

    /* compiled from: KeyguardBypassController.kt */
    public static final class PendingUnlock {
        public final boolean isStrongBiometric;
        public final BiometricSourceType pendingUnlockType;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PendingUnlock)) {
                return false;
            }
            PendingUnlock pendingUnlock = (PendingUnlock) obj;
            return this.pendingUnlockType == pendingUnlock.pendingUnlockType && this.isStrongBiometric == pendingUnlock.isStrongBiometric;
        }

        public final int hashCode() {
            int hashCode = this.pendingUnlockType.hashCode() * 31;
            boolean z = this.isStrongBiometric;
            if (z) {
                z = true;
            }
            return hashCode + (z ? 1 : 0);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("PendingUnlock(pendingUnlockType=");
            m.append(this.pendingUnlockType);
            m.append(", isStrongBiometric=");
            return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.isStrongBiometric, ')');
        }

        public PendingUnlock(BiometricSourceType biometricSourceType, boolean z) {
            this.pendingUnlockType = biometricSourceType;
            this.isStrongBiometric = z;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardBypassController:");
        PendingUnlock pendingUnlock2 = this.pendingUnlock;
        if (pendingUnlock2 != null) {
            Intrinsics.checkNotNull(pendingUnlock2);
            printWriter.println(Intrinsics.stringPlus("  mPendingUnlock.pendingUnlockType: ", pendingUnlock2.pendingUnlockType));
            PendingUnlock pendingUnlock3 = this.pendingUnlock;
            Intrinsics.checkNotNull(pendingUnlock3);
            KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(pendingUnlock3.isStrongBiometric, "  mPendingUnlock.isStrongBiometric: ", printWriter);
        } else {
            printWriter.println(Intrinsics.stringPlus("  mPendingUnlock: ", pendingUnlock2));
        }
        printWriter.println(Intrinsics.stringPlus("  bypassEnabled: ", Boolean.valueOf(getBypassEnabled())));
        printWriter.println(Intrinsics.stringPlus("  canBypass: ", Boolean.valueOf(canBypass())));
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.bouncerShowing, "  bouncerShowing: ", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.altBouncerShowing, "  altBouncerShowing: ", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.isPulseExpanding, "  isPulseExpanding: ", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.launchingAffordance, "  launchingAffordance: ", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.qSExpanded, "  qSExpanded: ", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.hasFaceFeature, "  hasFaceFeature: ", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.userHasDeviceEntryIntent, "  userHasDeviceEntryIntent: ", printWriter);
    }

    public final boolean getBypassEnabled() {
        boolean z;
        int i = this.bypassOverride;
        if (i == 1) {
            z = true;
        } else if (i != 2) {
            z = this.bypassEnabled;
        } else {
            z = false;
        }
        if (!z || !this.mKeyguardStateController.isFaceAuthEnabled()) {
            return false;
        }
        return true;
    }

    public final void maybePerformPendingUnlock() {
        PendingUnlock pendingUnlock2 = this.pendingUnlock;
        if (pendingUnlock2 != null) {
            Intrinsics.checkNotNull(pendingUnlock2);
            BiometricSourceType biometricSourceType = pendingUnlock2.pendingUnlockType;
            PendingUnlock pendingUnlock3 = this.pendingUnlock;
            Intrinsics.checkNotNull(pendingUnlock3);
            if (onBiometricAuthenticated(biometricSourceType, pendingUnlock3.isStrongBiometric)) {
                BiometricUnlockController biometricUnlockController = this.unlockController;
                if (biometricUnlockController == null) {
                    biometricUnlockController = null;
                }
                PendingUnlock pendingUnlock4 = this.pendingUnlock;
                Intrinsics.checkNotNull(pendingUnlock4);
                BiometricSourceType biometricSourceType2 = pendingUnlock4.pendingUnlockType;
                PendingUnlock pendingUnlock5 = this.pendingUnlock;
                Intrinsics.checkNotNull(pendingUnlock5);
                biometricUnlockController.startWakeAndUnlock(biometricSourceType2, pendingUnlock5.isStrongBiometric);
                this.pendingUnlock = null;
            }
        }
    }

    public final boolean onBiometricAuthenticated(BiometricSourceType biometricSourceType, boolean z) {
        if (biometricSourceType != BiometricSourceType.FACE || !getBypassEnabled()) {
            return true;
        }
        boolean canBypass = canBypass();
        if (!canBypass && (this.isPulseExpanding || this.qSExpanded)) {
            this.pendingUnlock = new PendingUnlock(biometricSourceType, z);
        }
        return canBypass;
    }

    public KeyguardBypassController(Context context, final TunerService tunerService, StatusBarStateController statusBarStateController2, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardStateController keyguardStateController, DumpManager dumpManager) {
        this.mKeyguardStateController = keyguardStateController;
        this.statusBarStateController = statusBarStateController2;
        this.bypassOverride = context.getResources().getInteger(C1777R.integer.config_face_unlock_bypass_override);
        boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("android.hardware.biometrics.face");
        this.hasFaceFeature = hasSystemFeature;
        if (hasSystemFeature) {
            dumpManager.registerDumpable("KeyguardBypassController", this);
            statusBarStateController2.addCallback(new StatusBarStateController.StateListener(this) {
                public final /* synthetic */ KeyguardBypassController this$0;

                public final void onStateChanged(int i) {
                    if (i != 1) {
                        this.this$0.pendingUnlock = null;
                    }
                }

                {
                    this.this$0 = r1;
                }
            });
            final int i = context.getResources().getBoolean(17891652) ? 1 : 0;
            tunerService.addTunable(new TunerService.Tunable(this) {
                public final /* synthetic */ KeyguardBypassController this$0;

                {
                    this.this$0 = r1;
                }

                public final void onTuningChanged(String str, String str2) {
                    boolean z;
                    KeyguardBypassController keyguardBypassController = this.this$0;
                    if (tunerService.getValue(str, i) != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    Objects.requireNonNull(keyguardBypassController);
                    keyguardBypassController.bypassEnabled = z;
                    Iterator it = keyguardBypassController.listeners.iterator();
                    while (it.hasNext()) {
                        ((OnBypassStateChangedListener) it.next()).onBypassStateChanged(keyguardBypassController.getBypassEnabled());
                    }
                }
            }, "face_unlock_dismisses_keyguard");
            notificationLockscreenUserManager.addUserChangedListener(new NotificationLockscreenUserManager.UserChangedListener(this) {
                public final /* synthetic */ KeyguardBypassController this$0;

                {
                    this.this$0 = r1;
                }

                public final void onUserChanged(int i) {
                    this.this$0.pendingUnlock = null;
                }
            });
        }
    }

    public final boolean canBypass() {
        if (!getBypassEnabled()) {
            return false;
        }
        if (!this.bouncerShowing && !this.altBouncerShowing && (this.statusBarStateController.getState() != 1 || this.launchingAffordance || this.isPulseExpanding || this.qSExpanded)) {
            return false;
        }
        return true;
    }

    public final boolean isBypassEnabled() {
        return getBypassEnabled();
    }
}
