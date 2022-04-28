package com.android.systemui.doze;

import android.os.Handler;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Objects;
import javax.inject.Provider;

public final class DozeScreenState implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public final DozeScreenState$$ExternalSyntheticLambda0 mApplyPendingScreenState = new DozeScreenState$$ExternalSyntheticLambda0(this, 0);
    public final AuthController mAuthController;
    public final C07771 mAuthControllerCallback;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeScreenBrightness mDozeScreenBrightness;
    public final DozeMachine.Service mDozeService;
    public final Handler mHandler;
    public final DozeParameters mParameters;
    public int mPendingScreenState = 0;
    public UdfpsController mUdfpsController;
    public final Provider<UdfpsController> mUdfpsControllerProvider;
    public SettableWakeLock mWakeLock;

    public final void applyScreenState(int i) {
        if (i != 0) {
            if (DEBUG) {
                Log.d("DozeScreenState", "setDozeScreenState(" + i + ")");
            }
            this.mDozeService.setDozeScreenState(i);
            if (i == 3) {
                this.mDozeScreenBrightness.updateBrightnessAndReady(false);
            }
            this.mPendingScreenState = 0;
            this.mWakeLock.setAcquired(false);
        }
    }

    public final void destroy() {
        AuthController authController = this.mAuthController;
        C07771 r1 = this.mAuthControllerCallback;
        Objects.requireNonNull(authController);
        authController.mCallbacks.remove(r1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:100:0x0146, code lost:
        if (r1 != false) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x0148, code lost:
        if (r5 == false) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x014a, code lost:
        r11.mWakeLock.setAcquired(true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0025, code lost:
        r11.mDozeHost.cancelGentleSleep();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002c, code lost:
        if (r13 != com.android.systemui.doze.DozeMachine.State.FINISH) goto L_0x0040;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002e, code lost:
        r11.mPendingScreenState = 0;
        r11.mHandler.removeCallbacks(r11.mApplyPendingScreenState);
        applyScreenState(r2);
        r11.mWakeLock.setAcquired(false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x003f, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0040, code lost:
        if (r2 != 0) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0042, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0043, code lost:
        r3 = r11.mHandler.hasCallbacks(r11.mApplyPendingScreenState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x004d, code lost:
        if (r12 != com.android.systemui.doze.DozeMachine.State.DOZE_PULSE_DONE) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0053, code lost:
        if (r13.isAlwaysOn() == false) goto L_0x0057;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0055, code lost:
        r7 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0057, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0058, code lost:
        r8 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD_PAUSED;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x005a, code lost:
        if (r12 == r8) goto L_0x005e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x005c, code lost:
        if (r12 != r1) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0062, code lost:
        if (r13.isAlwaysOn() == false) goto L_0x0066;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0064, code lost:
        r9 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0066, code lost:
        r9 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x006b, code lost:
        if (r12.isAlwaysOn() == false) goto L_0x006f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006d, code lost:
        if (r13 == r1) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0071, code lost:
        if (r12 != com.android.systemui.doze.DozeMachine.State.DOZE_AOD_PAUSING) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0073, code lost:
        if (r13 != r8) goto L_0x0077;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0075, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0077, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x007a, code lost:
        if (r12 != com.android.systemui.doze.DozeMachine.State.INITIALIZED) goto L_0x007e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x007c, code lost:
        r12 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x007e, code lost:
        r12 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x007f, code lost:
        if (r3 != false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0081, code lost:
        if (r12 != false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0083, code lost:
        if (r7 != false) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x0085, code lost:
        if (r9 == false) goto L_0x0088;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0088, code lost:
        if (r1 == false) goto L_0x0096;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x008a, code lost:
        r11.mDozeHost.prepareForGentleSleep(new com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda1(r11, r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x0096, code lost:
        applyScreenState(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x009b, code lost:
        r11.mPendingScreenState = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x009d, code lost:
        if (r13 != r0) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0016, code lost:
        if (r2.getDisplayNeedsBlanking() != false) goto L_0x0024;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x009f, code lost:
        r1 = r11.mParameters;
        java.util.Objects.requireNonNull(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00a8, code lost:
        if (r1.getAlwaysOn() == false) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00ac, code lost:
        if (r1.mKeyguardShowing == false) goto L_0x00b0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ae, code lost:
        r7 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00b0, code lost:
        r7 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00b1, code lost:
        if (r7 != false) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00b3, code lost:
        r1 = r1.mScreenOffAnimationController;
        java.util.Objects.requireNonNull(r1);
        r1 = r1.animations;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x00bc, code lost:
        if ((r1 instanceof java.util.Collection) == false) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00c2, code lost:
        if (r1.isEmpty() == false) goto L_0x00c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x00c5, code lost:
        r1 = r1.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00cd, code lost:
        if (r1.hasNext() == false) goto L_0x00dd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00d9, code lost:
        if (((com.android.systemui.statusbar.phone.ScreenOffAnimation) r1.next()).shouldDelayDisplayDozeTransition() == false) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00db, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00dd, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00de, code lost:
        if (r1 == false) goto L_0x00e1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x00e1, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00e3, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00e4, code lost:
        if (r1 == false) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00e6, code lost:
        if (r9 != false) goto L_0x00ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00e8, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00ea, code lost:
        r1 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00eb, code lost:
        if (r13 != r0) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00ed, code lost:
        r13 = r11.mUdfpsController;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00ef, code lost:
        if (r13 == null) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x00f3, code lost:
        if (r13.mOnFingerDown == false) goto L_0x00f6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0020, code lost:
        if (r2.mControlScreenOffAnimation != false) goto L_0x0022;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x00f5, code lost:
        r5 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x00f8, code lost:
        if (r3 != false) goto L_0x013d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x00fc, code lost:
        if (DEBUG == false) goto L_0x010f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x00fe, code lost:
        r0 = androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0.m13m("Display state changed to ", r2, " delayed by ");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x0106, code lost:
        if (r1 == false) goto L_0x010b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x0108, code lost:
        r3 = 4000;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x010b, code lost:
        r3 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x010c, code lost:
        com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(r0, r3, "DozeScreenState");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0022, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:90:0x010f, code lost:
        if (r1 == false) goto L_0x0122;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0111, code lost:
        if (r12 == false) goto L_0x0118;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x0113, code lost:
        applyScreenState(2);
        r11.mPendingScreenState = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x0118, code lost:
        r11.mHandler.postDelayed(r11.mApplyPendingScreenState, 4000);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x0122, code lost:
        if (r5 == false) goto L_0x0135;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x0124, code lost:
        r11.mDozeLog.traceDisplayStateDelayedByUdfps(r11.mPendingScreenState);
        r11.mHandler.postDelayed(r11.mApplyPendingScreenState, 1200);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x0135, code lost:
        r11.mHandler.post(r11.mApplyPendingScreenState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x013f, code lost:
        if (DEBUG == false) goto L_0x0146;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x0141, code lost:
        androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m("Pending display state change to ", r2, "DozeScreenState");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0024, code lost:
        r2 = 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void transitionTo(com.android.systemui.doze.DozeMachine.State r12, com.android.systemui.doze.DozeMachine.State r13) {
        /*
            r11 = this;
            com.android.systemui.doze.DozeMachine$State r0 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD
            com.android.systemui.doze.DozeMachine$State r1 = com.android.systemui.doze.DozeMachine.State.DOZE
            com.android.systemui.statusbar.phone.DozeParameters r2 = r11.mParameters
            int r3 = r13.ordinal()
            r4 = 2
            r5 = 0
            r6 = 1
            switch(r3) {
                case 0: goto L_0x001b;
                case 1: goto L_0x001b;
                case 2: goto L_0x0024;
                case 3: goto L_0x0019;
                case 4: goto L_0x0012;
                case 5: goto L_0x0022;
                case 6: goto L_0x0022;
                case 7: goto L_0x0010;
                case 8: goto L_0x0010;
                case 9: goto L_0x0024;
                case 10: goto L_0x0019;
                case 11: goto L_0x0022;
                default: goto L_0x0010;
            }
        L_0x0010:
            r2 = r5
            goto L_0x0025
        L_0x0012:
            boolean r2 = r2.getDisplayNeedsBlanking()
            if (r2 == 0) goto L_0x0022
            goto L_0x0024
        L_0x0019:
            r2 = 4
            goto L_0x0025
        L_0x001b:
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mControlScreenOffAnimation
            if (r2 == 0) goto L_0x0024
        L_0x0022:
            r2 = r4
            goto L_0x0025
        L_0x0024:
            r2 = r6
        L_0x0025:
            com.android.systemui.doze.DozeHost r3 = r11.mDozeHost
            r3.cancelGentleSleep()
            com.android.systemui.doze.DozeMachine$State r3 = com.android.systemui.doze.DozeMachine.State.FINISH
            if (r13 != r3) goto L_0x0040
            r11.mPendingScreenState = r5
            android.os.Handler r12 = r11.mHandler
            com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0 r13 = r11.mApplyPendingScreenState
            r12.removeCallbacks(r13)
            r11.applyScreenState(r2)
            com.android.systemui.util.wakelock.SettableWakeLock r11 = r11.mWakeLock
            r11.setAcquired(r5)
            return
        L_0x0040:
            if (r2 != 0) goto L_0x0043
            return
        L_0x0043:
            android.os.Handler r3 = r11.mHandler
            com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0 r7 = r11.mApplyPendingScreenState
            boolean r3 = r3.hasCallbacks(r7)
            com.android.systemui.doze.DozeMachine$State r7 = com.android.systemui.doze.DozeMachine.State.DOZE_PULSE_DONE
            if (r12 != r7) goto L_0x0057
            boolean r7 = r13.isAlwaysOn()
            if (r7 == 0) goto L_0x0057
            r7 = r6
            goto L_0x0058
        L_0x0057:
            r7 = r5
        L_0x0058:
            com.android.systemui.doze.DozeMachine$State r8 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD_PAUSED
            if (r12 == r8) goto L_0x005e
            if (r12 != r1) goto L_0x0066
        L_0x005e:
            boolean r9 = r13.isAlwaysOn()
            if (r9 == 0) goto L_0x0066
            r9 = r6
            goto L_0x0067
        L_0x0066:
            r9 = r5
        L_0x0067:
            boolean r10 = r12.isAlwaysOn()
            if (r10 == 0) goto L_0x006f
            if (r13 == r1) goto L_0x0075
        L_0x006f:
            com.android.systemui.doze.DozeMachine$State r1 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD_PAUSING
            if (r12 != r1) goto L_0x0077
            if (r13 != r8) goto L_0x0077
        L_0x0075:
            r1 = r6
            goto L_0x0078
        L_0x0077:
            r1 = r5
        L_0x0078:
            com.android.systemui.doze.DozeMachine$State r8 = com.android.systemui.doze.DozeMachine.State.INITIALIZED
            if (r12 != r8) goto L_0x007e
            r12 = r6
            goto L_0x007f
        L_0x007e:
            r12 = r5
        L_0x007f:
            if (r3 != 0) goto L_0x009b
            if (r12 != 0) goto L_0x009b
            if (r7 != 0) goto L_0x009b
            if (r9 == 0) goto L_0x0088
            goto L_0x009b
        L_0x0088:
            if (r1 == 0) goto L_0x0096
            com.android.systemui.doze.DozeHost r12 = r11.mDozeHost
            com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda1 r13 = new com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda1
            r13.<init>(r11, r2)
            r12.prepareForGentleSleep(r13)
            goto L_0x014f
        L_0x0096:
            r11.applyScreenState(r2)
            goto L_0x014f
        L_0x009b:
            r11.mPendingScreenState = r2
            if (r13 != r0) goto L_0x00ea
            com.android.systemui.statusbar.phone.DozeParameters r1 = r11.mParameters
            java.util.Objects.requireNonNull(r1)
            boolean r7 = r1.getAlwaysOn()
            if (r7 == 0) goto L_0x00b0
            boolean r7 = r1.mKeyguardShowing
            if (r7 == 0) goto L_0x00b0
            r7 = r6
            goto L_0x00b1
        L_0x00b0:
            r7 = r5
        L_0x00b1:
            if (r7 != 0) goto L_0x00e3
            com.android.systemui.statusbar.phone.ScreenOffAnimationController r1 = r1.mScreenOffAnimationController
            java.util.Objects.requireNonNull(r1)
            java.util.ArrayList r1 = r1.animations
            boolean r7 = r1 instanceof java.util.Collection
            if (r7 == 0) goto L_0x00c5
            boolean r7 = r1.isEmpty()
            if (r7 == 0) goto L_0x00c5
            goto L_0x00dd
        L_0x00c5:
            java.util.Iterator r1 = r1.iterator()
        L_0x00c9:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L_0x00dd
            java.lang.Object r7 = r1.next()
            com.android.systemui.statusbar.phone.ScreenOffAnimation r7 = (com.android.systemui.statusbar.phone.ScreenOffAnimation) r7
            boolean r7 = r7.shouldDelayDisplayDozeTransition()
            if (r7 == 0) goto L_0x00c9
            r1 = r6
            goto L_0x00de
        L_0x00dd:
            r1 = r5
        L_0x00de:
            if (r1 == 0) goto L_0x00e1
            goto L_0x00e3
        L_0x00e1:
            r1 = r5
            goto L_0x00e4
        L_0x00e3:
            r1 = r6
        L_0x00e4:
            if (r1 == 0) goto L_0x00ea
            if (r9 != 0) goto L_0x00ea
            r1 = r6
            goto L_0x00eb
        L_0x00ea:
            r1 = r5
        L_0x00eb:
            if (r13 != r0) goto L_0x00f6
            com.android.systemui.biometrics.UdfpsController r13 = r11.mUdfpsController
            if (r13 == 0) goto L_0x00f6
            boolean r13 = r13.mOnFingerDown
            if (r13 == 0) goto L_0x00f6
            r5 = r6
        L_0x00f6:
            java.lang.String r13 = "DozeScreenState"
            if (r3 != 0) goto L_0x013d
            boolean r0 = DEBUG
            if (r0 == 0) goto L_0x010f
            java.lang.String r0 = "Display state changed to "
            java.lang.String r3 = " delayed by "
            java.lang.StringBuilder r0 = androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0.m13m(r0, r2, r3)
            if (r1 == 0) goto L_0x010b
            r3 = 4000(0xfa0, float:5.605E-42)
            goto L_0x010c
        L_0x010b:
            r3 = r6
        L_0x010c:
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(r0, r3, r13)
        L_0x010f:
            if (r1 == 0) goto L_0x0122
            if (r12 == 0) goto L_0x0118
            r11.applyScreenState(r4)
            r11.mPendingScreenState = r2
        L_0x0118:
            android.os.Handler r12 = r11.mHandler
            com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0 r13 = r11.mApplyPendingScreenState
            r2 = 4000(0xfa0, double:1.9763E-320)
            r12.postDelayed(r13, r2)
            goto L_0x0146
        L_0x0122:
            if (r5 == 0) goto L_0x0135
            com.android.systemui.doze.DozeLog r12 = r11.mDozeLog
            int r13 = r11.mPendingScreenState
            r12.traceDisplayStateDelayedByUdfps(r13)
            android.os.Handler r12 = r11.mHandler
            com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0 r13 = r11.mApplyPendingScreenState
            r2 = 1200(0x4b0, double:5.93E-321)
            r12.postDelayed(r13, r2)
            goto L_0x0146
        L_0x0135:
            android.os.Handler r12 = r11.mHandler
            com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0 r13 = r11.mApplyPendingScreenState
            r12.post(r13)
            goto L_0x0146
        L_0x013d:
            boolean r12 = DEBUG
            if (r12 == 0) goto L_0x0146
            java.lang.String r12 = "Pending display state change to "
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1.m14m(r12, r2, r13)
        L_0x0146:
            if (r1 != 0) goto L_0x014a
            if (r5 == 0) goto L_0x014f
        L_0x014a:
            com.android.systemui.util.wakelock.SettableWakeLock r11 = r11.mWakeLock
            r11.setAcquired(r6)
        L_0x014f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeScreenState.transitionTo(com.android.systemui.doze.DozeMachine$State, com.android.systemui.doze.DozeMachine$State):void");
    }

    public final void updateUdfpsController() {
        if (this.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser())) {
            this.mUdfpsController = this.mUdfpsControllerProvider.get();
        } else {
            this.mUdfpsController = null;
        }
    }

    public DozeScreenState(DozeMachine.Service service, Handler handler, DozeHost dozeHost, DozeParameters dozeParameters, WakeLock wakeLock, AuthController authController, Provider<UdfpsController> provider, DozeLog dozeLog, DozeScreenBrightness dozeScreenBrightness) {
        C07771 r0 = new AuthController.Callback() {
            public final void onAllAuthenticatorsRegistered() {
                DozeScreenState.this.updateUdfpsController();
            }

            public final void onEnrollmentsChanged() {
                DozeScreenState.this.updateUdfpsController();
            }
        };
        this.mAuthControllerCallback = r0;
        this.mDozeService = service;
        this.mHandler = handler;
        this.mParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mWakeLock = new SettableWakeLock(wakeLock, "DozeScreenState");
        this.mAuthController = authController;
        this.mUdfpsControllerProvider = provider;
        this.mDozeLog = dozeLog;
        this.mDozeScreenBrightness = dozeScreenBrightness;
        updateUdfpsController();
        if (this.mUdfpsController == null) {
            authController.addCallback(r0);
        }
    }
}
