package com.android.keyguard;

import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.log.SessionTracker;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

/* compiled from: KeyguardBiometricLockoutLogger.kt */
public final class KeyguardBiometricLockoutLogger extends CoreStartable {
    public boolean encryptedOrLockdown;
    public boolean faceLockedOut;
    public boolean fingerprintLockedOut;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1 mKeyguardUpdateMonitorCallback = new KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1(this);
    public final SessionTracker sessionTracker;
    public boolean timeout;
    public final UiEventLogger uiEventLogger;
    public boolean unattendedUpdate;

    @VisibleForTesting
    /* compiled from: KeyguardBiometricLockoutLogger.kt */
    public enum PrimaryAuthRequiredEvent implements UiEventLogger.UiEventEnum {
        PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT(924),
        PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET(925),
        PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT(926),
        PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET(927),
        PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN(928),
        PRIMARY_AUTH_REQUIRED_TIMEOUT(929),
        PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE(931);
        
        private final int mId;

        /* access modifiers changed from: public */
        PrimaryAuthRequiredEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.fingerprintLockedOut, "  mFingerprintLockedOut=", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.faceLockedOut, "  mFaceLockedOut=", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.encryptedOrLockdown, "  mIsEncryptedOrLockdown=", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.unattendedUpdate, "  mIsUnattendedUpdate=", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.timeout, "  mIsTimeout=", printWriter);
    }

    public final void start() {
        this.mKeyguardUpdateMonitorCallback.onStrongAuthStateChanged(KeyguardUpdateMonitor.getCurrentUser());
        this.keyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }

    public KeyguardBiometricLockoutLogger(Context context, UiEventLogger uiEventLogger2, KeyguardUpdateMonitor keyguardUpdateMonitor2, SessionTracker sessionTracker2) {
        super(context);
        this.uiEventLogger = uiEventLogger2;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor2;
        this.sessionTracker = sessionTracker2;
    }

    public static final void access$log(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, PrimaryAuthRequiredEvent primaryAuthRequiredEvent) {
        Objects.requireNonNull(keyguardBiometricLockoutLogger);
        UiEventLogger uiEventLogger2 = keyguardBiometricLockoutLogger.uiEventLogger;
        SessionTracker sessionTracker2 = keyguardBiometricLockoutLogger.sessionTracker;
        Objects.requireNonNull(sessionTracker2);
        uiEventLogger2.log(primaryAuthRequiredEvent, (InstanceId) sessionTracker2.mSessionToInstanceId.getOrDefault(1, (Object) null));
    }
}
