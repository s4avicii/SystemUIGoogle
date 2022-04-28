package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Build;
import android.os.SystemProperties;
import android.os.Trace;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda10;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.classifier.FalsingDataProvider$$ExternalSyntheticLambda1;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.TunerActivity$$ExternalSyntheticLambda0;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public final class KeyguardStateControllerImpl implements KeyguardStateController, Dumpable {
    public boolean mBypassFadingAnimation;
    public final ArrayList<KeyguardStateController.Callback> mCallbacks = new ArrayList<>();
    public boolean mCanDismissLockScreen;
    public final Context mContext;
    public float mDismissAmount;
    public boolean mDismissingFromTouch;
    public boolean mFaceAuthEnabled;
    public boolean mFlingingToDismissKeyguard;
    public boolean mFlingingToDismissKeyguardDuringSwipeGesture;
    public boolean mKeyguardFadingAway;
    public long mKeyguardFadingAwayDelay;
    public long mKeyguardFadingAwayDuration;
    public boolean mKeyguardGoingAway;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final UpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public boolean mLaunchTransitionFadingAway;
    public final LockPatternUtils mLockPatternUtils;
    public boolean mOccluded;
    public boolean mSecure;
    public boolean mShowing;
    public boolean mSnappingKeyguardBackAfterSwipe;
    public boolean mTrustManaged;
    public boolean mTrusted;
    public final Lazy<KeyguardUnlockAnimationController> mUnlockAnimationControllerLazy;

    public class UpdateMonitorCallback extends KeyguardUpdateMonitorCallback {
        public UpdateMonitorCallback() {
        }

        public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            Trace.beginSection("KeyguardUpdateMonitorCallback#onBiometricAuthenticated");
            if (KeyguardStateControllerImpl.this.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z)) {
                KeyguardStateControllerImpl.this.update(false);
            }
            Trace.endSection();
        }

        public final void onBiometricsCleared() {
            KeyguardStateControllerImpl.this.update(false);
        }

        public final void onFaceUnlockStateChanged() {
            KeyguardStateControllerImpl.this.update(false);
        }

        public final void onKeyguardVisibilityChanged(boolean z) {
            KeyguardStateControllerImpl.this.update(false);
        }

        public final void onStartedWakingUp() {
            KeyguardStateControllerImpl.this.update(false);
        }

        public final void onStrongAuthStateChanged(int i) {
            KeyguardStateControllerImpl.this.update(false);
        }

        public final void onTrustChanged(int i) {
            KeyguardStateControllerImpl.this.update(false);
            KeyguardStateControllerImpl keyguardStateControllerImpl = KeyguardStateControllerImpl.this;
            Objects.requireNonNull(keyguardStateControllerImpl);
            Trace.beginSection("KeyguardStateController#notifyKeyguardChanged");
            new ArrayList(keyguardStateControllerImpl.mCallbacks).forEach(KeyguardStateControllerImpl$$ExternalSyntheticLambda0.INSTANCE);
            Trace.endSection();
        }

        public final void onTrustManagedChanged() {
            KeyguardStateControllerImpl.this.update(false);
        }

        public final void onUserSwitchComplete(int i) {
            KeyguardStateControllerImpl.this.update(false);
        }
    }

    public final void notifyKeyguardDoneFading() {
        this.mKeyguardGoingAway = false;
        setKeyguardFadingAway(false);
    }

    public final void notifyKeyguardGoingAway() {
        this.mKeyguardGoingAway = true;
    }

    public final void notifyPanelFlingEnd() {
        this.mFlingingToDismissKeyguard = false;
        this.mFlingingToDismissKeyguardDuringSwipeGesture = false;
        this.mSnappingKeyguardBackAfterSwipe = false;
    }

    public final void addCallback(Object obj) {
        KeyguardStateController.Callback callback = (KeyguardStateController.Callback) obj;
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        if (!this.mCallbacks.contains(callback)) {
            this.mCallbacks.add(callback);
        }
    }

    public final long calculateGoingToFullShadeDelay() {
        return this.mKeyguardFadingAwayDelay + this.mKeyguardFadingAwayDuration;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "KeyguardStateController:", "  mSecure: "), this.mSecure, printWriter, "  mCanDismissLockScreen: "), this.mCanDismissLockScreen, printWriter, "  mTrustManaged: "), this.mTrustManaged, printWriter, "  mTrusted: ");
        m.append(this.mTrusted);
        printWriter.println(m.toString());
        printWriter.println("  mDebugUnlocked: false");
        printWriter.println("  mFaceAuthEnabled: " + this.mFaceAuthEnabled);
    }

    public final boolean isKeyguardScreenRotationAllowed() {
        if (SystemProperties.getBoolean("lockscreen.rot_override", false) || this.mContext.getResources().getBoolean(C1777R.bool.config_enableLockScreenRotation)) {
            return true;
        }
        return false;
    }

    public final void notifyKeyguardDismissAmountChanged(float f, boolean z) {
        this.mDismissAmount = f;
        this.mDismissingFromTouch = z;
        new ArrayList(this.mCallbacks).forEach(TunerActivity$$ExternalSyntheticLambda0.INSTANCE$1);
    }

    public final void notifyKeyguardFadingAway(long j, long j2, boolean z) {
        this.mKeyguardFadingAwayDelay = j;
        this.mKeyguardFadingAwayDuration = j2;
        this.mBypassFadingAnimation = z;
        setKeyguardFadingAway(true);
    }

    public final void notifyKeyguardState(boolean z, boolean z2) {
        float f;
        if (this.mShowing != z || this.mOccluded != z2) {
            this.mShowing = z;
            this.mOccluded = z2;
            Trace.instantForTrack(4096, "UI Events", "Keyguard showing: " + z + " occluded: " + z2);
            Trace.beginSection("KeyguardStateController#notifyKeyguardChanged");
            new ArrayList(this.mCallbacks).forEach(KeyguardStateControllerImpl$$ExternalSyntheticLambda0.INSTANCE);
            Trace.endSection();
            if (z) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            notifyKeyguardDismissAmountChanged(f, false);
        }
    }

    public final void notifyPanelFlingStart(boolean z) {
        boolean z2;
        this.mFlingingToDismissKeyguard = z;
        if (!z || !this.mDismissingFromTouch) {
            z2 = false;
        } else {
            z2 = true;
        }
        this.mFlingingToDismissKeyguardDuringSwipeGesture = z2;
        this.mSnappingKeyguardBackAfterSwipe = !z;
    }

    public final void removeCallback(Object obj) {
        KeyguardStateController.Callback callback = (KeyguardStateController.Callback) obj;
        Objects.requireNonNull(callback, "Callback must not be null. b/128895449");
        this.mCallbacks.remove(callback);
    }

    public final void setKeyguardFadingAway(boolean z) {
        if (this.mKeyguardFadingAway != z) {
            this.mKeyguardFadingAway = z;
            ArrayList arrayList = new ArrayList(this.mCallbacks);
            for (int i = 0; i < arrayList.size(); i++) {
                ((KeyguardStateController.Callback) arrayList.get(i)).onKeyguardFadingAwayChanged();
            }
        }
    }

    public final void setLaunchTransitionFadingAway(boolean z) {
        this.mLaunchTransitionFadingAway = z;
        new ArrayList(this.mCallbacks).forEach(FalsingDataProvider$$ExternalSyntheticLambda1.INSTANCE$1);
    }

    public void update(boolean z) {
        boolean z2;
        Trace.beginSection("KeyguardStateController#update");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        boolean isSecure = this.mLockPatternUtils.isSecure(currentUser);
        boolean z3 = false;
        if (!isSecure || this.mKeyguardUpdateMonitor.getUserCanSkipBouncer(currentUser)) {
            z2 = true;
        } else {
            boolean z4 = Build.IS_DEBUGGABLE;
            z2 = false;
        }
        boolean userTrustIsManaged = this.mKeyguardUpdateMonitor.getUserTrustIsManaged(currentUser);
        boolean userHasTrust = this.mKeyguardUpdateMonitor.getUserHasTrust(currentUser);
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        Objects.requireNonNull(keyguardUpdateMonitor);
        boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardUpdateMonitor$$ExternalSyntheticLambda10(keyguardUpdateMonitor, currentUser))).booleanValue();
        keyguardUpdateMonitor.mIsFaceEnrolled = booleanValue;
        if (!(isSecure == this.mSecure && z2 == this.mCanDismissLockScreen && userTrustIsManaged == this.mTrustManaged && this.mTrusted == userHasTrust && this.mFaceAuthEnabled == booleanValue)) {
            z3 = true;
        }
        if (z3 || z) {
            this.mSecure = isSecure;
            this.mCanDismissLockScreen = z2;
            this.mTrusted = userHasTrust;
            this.mTrustManaged = userTrustIsManaged;
            this.mFaceAuthEnabled = booleanValue;
            Trace.beginSection("KeyguardStateController#notifyUnlockedChanged");
            new ArrayList(this.mCallbacks).forEach(KeyguardStateControllerImpl$$ExternalSyntheticLambda1.INSTANCE);
            Trace.endSection();
        }
        Trace.endSection();
    }

    public KeyguardStateControllerImpl(Context context, KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils, Lazy<KeyguardUnlockAnimationController> lazy, DumpManager dumpManager) {
        UpdateMonitorCallback updateMonitorCallback = new UpdateMonitorCallback();
        this.mKeyguardUpdateMonitorCallback = updateMonitorCallback;
        this.mDismissAmount = 0.0f;
        this.mDismissingFromTouch = false;
        this.mFlingingToDismissKeyguard = false;
        this.mFlingingToDismissKeyguardDuringSwipeGesture = false;
        this.mSnappingKeyguardBackAfterSwipe = false;
        this.mContext = context;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockPatternUtils = lockPatternUtils;
        keyguardUpdateMonitor.registerCallback(updateMonitorCallback);
        this.mUnlockAnimationControllerLazy = lazy;
        dumpManager.registerDumpable("KeyguardStateControllerImpl", this);
        update(true);
        boolean z = Build.IS_DEBUGGABLE;
    }

    public final boolean canDismissLockScreen() {
        return this.mCanDismissLockScreen;
    }

    public final float getDismissAmount() {
        return this.mDismissAmount;
    }

    public final long getKeyguardFadingAwayDelay() {
        return this.mKeyguardFadingAwayDelay;
    }

    public final long getKeyguardFadingAwayDuration() {
        return this.mKeyguardFadingAwayDuration;
    }

    public final boolean isBypassFadingAnimation() {
        return this.mBypassFadingAnimation;
    }

    public final boolean isDismissingFromSwipe() {
        return this.mDismissingFromTouch;
    }

    public final boolean isFaceAuthEnabled() {
        return this.mFaceAuthEnabled;
    }

    public final boolean isFlingingToDismissKeyguard() {
        return this.mFlingingToDismissKeyguard;
    }

    public final boolean isFlingingToDismissKeyguardDuringSwipeGesture() {
        return this.mFlingingToDismissKeyguardDuringSwipeGesture;
    }

    public final boolean isKeyguardFadingAway() {
        return this.mKeyguardFadingAway;
    }

    public final boolean isKeyguardGoingAway() {
        return this.mKeyguardGoingAway;
    }

    public final boolean isLaunchTransitionFadingAway() {
        return this.mLaunchTransitionFadingAway;
    }

    public final boolean isMethodSecure() {
        return this.mSecure;
    }

    public final boolean isOccluded() {
        return this.mOccluded;
    }

    public final boolean isShowing() {
        return this.mShowing;
    }

    public final boolean isSnappingKeyguardBackAfterSwipe() {
        return this.mSnappingKeyguardBackAfterSwipe;
    }
}
