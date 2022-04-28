package com.google.android.systemui.coversheet;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import java.util.Objects;

public final class CoversheetService {
    public static final boolean DEBUG = Log.isLoggable("Coversheet", 3);
    public final String mBuildId;
    public final C22081 mCallback;
    public final Context mContext;
    public boolean mKeyguardShowing;
    public boolean mUserUnlocked;

    /* renamed from: -$$Nest$mstartCoversheetIfNeeded  reason: not valid java name */
    public static void m306$$Nest$mstartCoversheetIfNeeded(CoversheetService coversheetService) {
        boolean z;
        boolean z2 = DEBUG;
        if (z2) {
            Objects.requireNonNull(coversheetService);
            StringBuilder sb = new StringBuilder();
            sb.append("mKeyguardShowing: ");
            sb.append(coversheetService.mKeyguardShowing);
            sb.append(", mUserUnlocked: ");
            KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(sb, coversheetService.mUserUnlocked, "Coversheet");
        }
        if (!coversheetService.mKeyguardShowing && coversheetService.mUserUnlocked) {
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.sInstance.getRunningTask();
            if (runningTask == null) {
                Log.w("Coversheet", "Not able to get any running task");
                return;
            }
            if (runningTask.configuration.windowConfiguration.getActivityType() == 2) {
                z = true;
            } else {
                z = false;
            }
            if (z2) {
                ViewCompat$$ExternalSyntheticLambda0.m12m("Going to home now? ", z, "Coversheet");
            }
            if (z) {
                Intent intent = new Intent("com.google.android.apps.tips.action.COVERSHEET");
                intent.setPackage("com.google.android.apps.tips");
                intent.setFlags(335544320);
                try {
                    coversheetService.mContext.startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    Log.w("Coversheet", "Coversheet was not found");
                }
                Settings.System.putString(coversheetService.mContext.getContentResolver(), "coversheet_id", coversheetService.mBuildId);
                ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).removeCallback(coversheetService.mCallback);
            }
        }
    }

    public CoversheetService(Context context) {
        C22081 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onKeyguardVisibilityChanged(boolean z) {
                if (CoversheetService.DEBUG) {
                    Log.d("Coversheet", "onKeyguardVisibilityChanged");
                }
                CoversheetService coversheetService = CoversheetService.this;
                coversheetService.mKeyguardShowing = z;
                CoversheetService.m306$$Nest$mstartCoversheetIfNeeded(coversheetService);
            }

            public final void onUserUnlocked() {
                if (CoversheetService.DEBUG) {
                    Log.d("Coversheet", "onUserUnlocked");
                }
                CoversheetService coversheetService = CoversheetService.this;
                coversheetService.mUserUnlocked = true;
                CoversheetService.m306$$Nest$mstartCoversheetIfNeeded(coversheetService);
            }
        };
        this.mCallback = r0;
        String str = Build.ID.split("\\.")[0];
        this.mBuildId = str;
        this.mContext = context;
        if (!((DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class)).isDeviceProvisioned()) {
            if (DEBUG) {
                DialogFragment$$ExternalSyntheticOutline0.m17m("Store initial ID: ", str, "Coversheet");
            }
            Settings.System.putString(context.getContentResolver(), "coversheet_id", str);
        } else if (!TextUtils.equals(str, Settings.System.getString(context.getContentResolver(), "coversheet_id"))) {
            if (DEBUG) {
                Log.d("Coversheet", "Register callback.");
            }
            ((KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class)).registerCallback(r0);
        }
    }
}
