package com.google.android.systemui.elmyra.gates;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.TaskStackListener;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import com.google.android.systemui.elmyra.actions.Action;
import com.google.android.systemui.elmyra.actions.CameraAction;
import com.google.android.systemui.elmyra.gates.Gate;
import java.util.List;

public final class CameraVisibility extends Gate {
    public final IActivityManager mActivityManager;
    public final CameraAction mCameraAction;
    public final String mCameraPackageName;
    public boolean mCameraShowing;
    public final List<Action> mExceptions;
    public final C22472 mGateListener;
    public final KeyguardVisibility mKeyguardGate;
    public final PackageManager mPackageManager;
    public final PowerState mPowerState;
    public final C22461 mTaskStackListener = new TaskStackListener() {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final void onTaskStackChanged() {
            CameraVisibility cameraVisibility = CameraVisibility.this;
            cameraVisibility.mUpdateHandler.post(new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(cameraVisibility, 10));
        }
    };
    public final Handler mUpdateHandler;

    public final boolean isBlocked() {
        for (int i = 0; i < this.mExceptions.size(); i++) {
            if (this.mExceptions.get(i).isAvailable()) {
                return false;
            }
        }
        return this.mCameraShowing && !this.mCameraAction.isAvailable();
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0030 A[SYNTHETIC, Splitter:B:10:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:40:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isCameraShowing() {
        /*
            r9 = this;
            java.lang.String r0 = "Elmyra/CameraVisibility"
            r1 = 0
            r2 = 1
            android.app.IActivityManager r3 = android.app.ActivityManager.getService()     // Catch:{ RemoteException -> 0x0026 }
            java.util.List r3 = r3.getTasks(r2)     // Catch:{ RemoteException -> 0x0026 }
            boolean r4 = r3.isEmpty()     // Catch:{ RemoteException -> 0x0026 }
            if (r4 == 0) goto L_0x0013
            goto L_0x002d
        L_0x0013:
            java.lang.Object r3 = r3.get(r1)     // Catch:{ RemoteException -> 0x0026 }
            android.app.ActivityManager$RunningTaskInfo r3 = (android.app.ActivityManager.RunningTaskInfo) r3     // Catch:{ RemoteException -> 0x0026 }
            android.content.ComponentName r3 = r3.topActivity     // Catch:{ RemoteException -> 0x0026 }
            java.lang.String r3 = r3.getPackageName()     // Catch:{ RemoteException -> 0x0026 }
            java.lang.String r4 = r9.mCameraPackageName     // Catch:{ RemoteException -> 0x0026 }
            boolean r3 = r3.equalsIgnoreCase(r4)     // Catch:{ RemoteException -> 0x0026 }
            goto L_0x002e
        L_0x0026:
            r3 = move-exception
            java.lang.String r4 = "unable to check task stack"
            android.util.Log.e(r0, r4, r3)
        L_0x002d:
            r3 = r1
        L_0x002e:
            if (r3 == 0) goto L_0x0086
            android.app.IActivityManager r3 = r9.mActivityManager     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            android.content.pm.UserInfo r3 = r3.getCurrentUser()     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            android.content.pm.PackageManager r4 = r9.mPackageManager     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            java.lang.String r5 = r9.mCameraPackageName     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            if (r3 == 0) goto L_0x003f
            int r3 = r3.id     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            goto L_0x0040
        L_0x003f:
            r3 = r1
        L_0x0040:
            android.content.pm.ApplicationInfo r3 = r4.getApplicationInfoAsUser(r5, r1, r3)     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            int r3 = r3.uid     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            android.app.IActivityManager r4 = r9.mActivityManager     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            java.util.List r4 = r4.getRunningAppProcesses()     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            r5 = r1
        L_0x004d:
            int r6 = r4.size()     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            if (r5 >= r6) goto L_0x006f
            java.lang.Object r6 = r4.get(r5)     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            android.app.ActivityManager$RunningAppProcessInfo r6 = (android.app.ActivityManager.RunningAppProcessInfo) r6     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            int r7 = r6.uid     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            if (r7 != r3) goto L_0x0071
            java.lang.String r7 = r6.processName     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            java.lang.String r8 = r9.mCameraPackageName     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            boolean r7 = r7.equalsIgnoreCase(r8)     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            if (r7 == 0) goto L_0x0071
            int r0 = r6.importance     // Catch:{ NameNotFoundException -> 0x006f, RemoteException -> 0x0074 }
            r3 = 100
            if (r0 != r3) goto L_0x006f
            r0 = r2
            goto L_0x007b
        L_0x006f:
            r0 = r1
            goto L_0x007b
        L_0x0071:
            int r5 = r5 + 1
            goto L_0x004d
        L_0x0074:
            r3 = move-exception
            java.lang.String r4 = "Could not check camera foreground status"
            android.util.Log.e(r0, r4, r3)
            goto L_0x006f
        L_0x007b:
            if (r0 == 0) goto L_0x0086
            com.google.android.systemui.elmyra.gates.PowerState r9 = r9.mPowerState
            boolean r9 = r9.isBlocking()
            if (r9 != 0) goto L_0x0086
            r1 = r2
        L_0x0086:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.elmyra.gates.CameraVisibility.isCameraShowing():boolean");
    }

    public final void onActivate() {
        this.mKeyguardGate.activate();
        this.mPowerState.activate();
        this.mCameraShowing = isCameraShowing();
        try {
            this.mActivityManager.registerTaskStackListener(this.mTaskStackListener);
        } catch (RemoteException e) {
            Log.e("Elmyra/CameraVisibility", "Could not register task stack listener", e);
        }
    }

    public final void onDeactivate() {
        this.mKeyguardGate.deactivate();
        this.mPowerState.deactivate();
        try {
            this.mActivityManager.unregisterTaskStackListener(this.mTaskStackListener);
        } catch (RemoteException e) {
            Log.e("Elmyra/CameraVisibility", "Could not unregister task stack listener", e);
        }
    }

    public CameraVisibility(Context context, CameraAction cameraAction, List<Action> list) {
        super(context);
        C22472 r0 = new Gate.Listener() {
            public final void onGateChanged(Gate gate) {
                CameraVisibility cameraVisibility = CameraVisibility.this;
                cameraVisibility.mUpdateHandler.post(new BubbleStackView$$ExternalSyntheticLambda15(cameraVisibility, 12));
            }
        };
        this.mGateListener = r0;
        this.mCameraAction = cameraAction;
        this.mExceptions = list;
        this.mPackageManager = context.getPackageManager();
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        this.mActivityManager = ActivityManager.getService();
        KeyguardVisibility keyguardVisibility = new KeyguardVisibility(context);
        this.mKeyguardGate = keyguardVisibility;
        PowerState powerState = new PowerState(context);
        this.mPowerState = powerState;
        keyguardVisibility.mListener = r0;
        powerState.mListener = r0;
        this.mCameraPackageName = context.getResources().getString(C1777R.string.google_camera_app_package_name);
        this.mUpdateHandler = new Handler(context.getMainLooper());
    }
}
