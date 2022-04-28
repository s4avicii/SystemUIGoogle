package com.google.android.systemui.face;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import java.util.Objects;

public final class FaceNotificationService {
    public FaceNotificationBroadcastReceiver mBroadcastReceiver;
    public Context mContext;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final C22681 mKeyguardUpdateMonitorCallback;
    public boolean mNotificationQueued;

    public FaceNotificationService(Context context) {
        C22681 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
                if (i == 1004) {
                    Settings.Secure.putIntForUser(FaceNotificationService.this.mContext.getContentResolver(), "face_unlock_re_enroll", 3, -2);
                }
            }

            public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
                if (i == 13) {
                    Settings.Secure.putIntForUser(FaceNotificationService.this.mContext.getContentResolver(), "face_unlock_re_enroll", 1, -2);
                }
            }

            public final void onUserUnlocked() {
                FaceNotificationService faceNotificationService = FaceNotificationService.this;
                if (faceNotificationService.mNotificationQueued) {
                    Log.d("FaceNotificationService", "Not showing notification; already queued.");
                    return;
                }
                boolean z = false;
                if (Settings.Secure.getIntForUser(faceNotificationService.mContext.getContentResolver(), "face_unlock_re_enroll", 0, -2) == 3) {
                    z = true;
                }
                if (z) {
                    FaceNotificationService faceNotificationService2 = FaceNotificationService.this;
                    Objects.requireNonNull(faceNotificationService2);
                    faceNotificationService2.mNotificationQueued = true;
                    faceNotificationService2.mHandler.postDelayed(new FaceNotificationService$$ExternalSyntheticLambda0(faceNotificationService2, faceNotificationService2.mContext.getString(C1777R.string.face_reenroll_notification_title), faceNotificationService2.mContext.getString(C1777R.string.face_reenroll_notification_content)), 10000);
                }
            }
        };
        this.mKeyguardUpdateMonitorCallback = r0;
        this.mContext = context;
        ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(r0);
        this.mBroadcastReceiver = new FaceNotificationBroadcastReceiver(this.mContext);
    }
}
