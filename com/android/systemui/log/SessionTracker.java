package com.android.systemui.log;

import android.app.StatusBarManager;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;

public class SessionTracker extends CoreStartable {
    public final AuthController mAuthController;
    public C08663 mAuthControllerCallback = new AuthController.Callback() {
        public final void onBiometricPromptDismissed() {
            SessionTracker.m208$$Nest$mendSession(SessionTracker.this, 2);
        }

        public final void onBiometricPromptShown() {
            SessionTracker.this.startSession(2);
        }
    };
    public final InstanceIdSequence mInstanceIdGenerator = new InstanceIdSequence(1048576);
    public boolean mKeyguardSessionStarted;
    public C08652 mKeyguardStateCallback = new KeyguardStateController.Callback() {
        public final void onKeyguardShowingChanged() {
            SessionTracker sessionTracker = SessionTracker.this;
            boolean z = sessionTracker.mKeyguardSessionStarted;
            boolean isShowing = sessionTracker.mKeyguardStateController.isShowing();
            if (isShowing && !z) {
                SessionTracker sessionTracker2 = SessionTracker.this;
                sessionTracker2.mKeyguardSessionStarted = true;
                sessionTracker2.startSession(1);
            } else if (!isShowing && z) {
                SessionTracker sessionTracker3 = SessionTracker.this;
                sessionTracker3.mKeyguardSessionStarted = false;
                SessionTracker.m208$$Nest$mendSession(sessionTracker3, 1);
            }
        }
    };
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public C08641 mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        public final void onStartedGoingToSleep$1() {
            SessionTracker sessionTracker = SessionTracker.this;
            if (!sessionTracker.mKeyguardSessionStarted) {
                sessionTracker.mKeyguardSessionStarted = true;
                sessionTracker.startSession(1);
            }
        }
    };
    public final HashMap mSessionToInstanceId = new HashMap();
    public final IStatusBarService mStatusBarManagerService;

    public static String getString(int i) {
        if (i == 1) {
            return "KEYGUARD";
        }
        if (i == 2) {
            return "BIOMETRIC_PROMPT";
        }
        return VendorAtomValue$$ExternalSyntheticOutline0.m0m("unknownType=", i);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (Integer intValue : StatusBarManager.ALL_SESSIONS) {
            int intValue2 = intValue.intValue();
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  ");
            m.append(getString(intValue2));
            m.append(" instanceId=");
            m.append(this.mSessionToInstanceId.get(Integer.valueOf(intValue2)));
            printWriter.println(m.toString());
        }
    }

    public final void start() {
        this.mAuthController.addCallback(this.mAuthControllerCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mKeyguardStateController.addCallback(this.mKeyguardStateCallback);
        boolean isShowing = this.mKeyguardStateController.isShowing();
        this.mKeyguardSessionStarted = isShowing;
        if (isShowing) {
            startSession(1);
        }
    }

    public final void startSession(int i) {
        if (this.mSessionToInstanceId.getOrDefault(Integer.valueOf(i), (Object) null) != null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("session [");
            m.append(getString(i));
            m.append("] was already started");
            Log.e("SessionTracker", m.toString());
            return;
        }
        InstanceId newInstanceId = this.mInstanceIdGenerator.newInstanceId();
        this.mSessionToInstanceId.put(Integer.valueOf(i), newInstanceId);
        try {
            this.mStatusBarManagerService.onSessionStarted(i, newInstanceId);
        } catch (RemoteException e) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to send onSessionStarted for session=[");
            m2.append(getString(i));
            m2.append("]");
            Log.e("SessionTracker", m2.toString(), e);
        }
    }

    /* renamed from: -$$Nest$mendSession  reason: not valid java name */
    public static void m208$$Nest$mendSession(SessionTracker sessionTracker, int i) {
        Objects.requireNonNull(sessionTracker);
        if (sessionTracker.mSessionToInstanceId.getOrDefault(Integer.valueOf(i), (Object) null) == null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("session [");
            m.append(getString(i));
            m.append("] was not started");
            Log.e("SessionTracker", m.toString());
            return;
        }
        InstanceId instanceId = (InstanceId) sessionTracker.mSessionToInstanceId.get(Integer.valueOf(i));
        sessionTracker.mSessionToInstanceId.put(Integer.valueOf(i), (Object) null);
        try {
            sessionTracker.mStatusBarManagerService.onSessionEnded(i, instanceId);
        } catch (RemoteException e) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unable to send onSessionEnded for session=[");
            m2.append(getString(i));
            m2.append("]");
            Log.e("SessionTracker", m2.toString(), e);
        }
    }

    public SessionTracker(Context context, IStatusBarService iStatusBarService, AuthController authController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController) {
        super(context);
        this.mStatusBarManagerService = iStatusBarService;
        this.mAuthController = authController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
    }
}
