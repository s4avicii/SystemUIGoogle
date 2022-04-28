package com.android.systemui.assist;

import android.content.Context;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.assist.p003ui.DefaultUiController;
import com.android.systemui.model.SysUiState;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.Lazy;

public class AssistManager {
    public final AssistDisclosure mAssistDisclosure;
    public final AssistLogger mAssistLogger;
    public final AssistUtils mAssistUtils;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final PhoneStateMonitor mPhoneStateMonitor;
    public final Lazy<SysUiState> mSysUiState;
    public final DefaultUiController mUiController;

    public interface UiController {
        void hide();

        void onGestureCompletion(float f);

        void onInvocationProgress(int i, float f);
    }

    public final void hideAssist() {
        this.mAssistUtils.hideCurrentSession();
    }

    public void logStartAssistLegacy(int i, int i2) {
        MetricsLogger.action(new LogMaker(1716).setType(1).setSubtype((i << 1) | 0 | (i2 << 4)));
    }

    public void onGestureCompletion(float f) {
        this.mUiController.onGestureCompletion(f);
    }

    public void onInvocationProgress(int i, float f) {
        this.mUiController.onInvocationProgress(1, f);
    }

    public void registerVoiceInteractionSessionListener() {
        this.mAssistUtils.registerVoiceInteractionSessionListener(new IVoiceInteractionSessionListener.Stub() {
            public final void onSetUiHints(Bundle bundle) {
                if ("set_assist_gesture_constrained".equals(bundle.getString("action"))) {
                    SysUiState sysUiState = AssistManager.this.mSysUiState.get();
                    sysUiState.setFlag(8192, bundle.getBoolean("should_constrain", false));
                    sysUiState.commitUpdate(0);
                }
            }

            public final void onVoiceSessionHidden() throws RemoteException {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
            }

            public final void onVoiceSessionShown() throws RemoteException {
                AssistManager.this.mAssistLogger.reportAssistantSessionEvent(AssistantSessionEvent.ASSISTANT_SESSION_UPDATE);
            }
        });
    }

    public AssistManager(DeviceProvisionedController deviceProvisionedController, Context context, AssistUtils assistUtils, CommandQueue commandQueue, PhoneStateMonitor phoneStateMonitor, OverviewProxyService overviewProxyService, Lazy<SysUiState> lazy, DefaultUiController defaultUiController, AssistLogger assistLogger, Handler handler) {
        this.mContext = context;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mCommandQueue = commandQueue;
        this.mAssistUtils = assistUtils;
        this.mAssistDisclosure = new AssistDisclosure(context, handler);
        this.mPhoneStateMonitor = phoneStateMonitor;
        this.mAssistLogger = assistLogger;
        registerVoiceInteractionSessionListener();
        this.mUiController = defaultUiController;
        this.mSysUiState = lazy;
        overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) new OverviewProxyService.OverviewProxyListener() {
            public final void onAssistantGestureCompletion(float f) {
                AssistManager.this.onGestureCompletion(f);
            }

            public final void onAssistantProgress(float f) {
                AssistManager.this.onInvocationProgress(1, f);
            }
        });
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x007f, code lost:
        r1 = r1.getAssistIntent(r2);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void startAssist(android.os.Bundle r9) {
        /*
            r8 = this;
            int r0 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()
            com.android.internal.app.AssistUtils r1 = r8.mAssistUtils
            android.content.ComponentName r0 = r1.getAssistComponentForUser(r0)
            if (r0 != 0) goto L_0x000d
            return
        L_0x000d:
            com.android.internal.app.AssistUtils r1 = r8.mAssistUtils
            android.content.ComponentName r1 = r1.getActiveServiceComponentName()
            boolean r1 = r0.equals(r1)
            if (r9 != 0) goto L_0x001e
            android.os.Bundle r9 = new android.os.Bundle
            r9.<init>()
        L_0x001e:
            r2 = 0
            java.lang.String r3 = "invocation_type"
            int r3 = r9.getInt(r3, r2)
            com.android.systemui.assist.PhoneStateMonitor r4 = r8.mPhoneStateMonitor
            int r4 = r4.getPhoneState()
            java.lang.String r5 = "invocation_phone_state"
            r9.putInt(r5, r4)
            long r5 = android.os.SystemClock.elapsedRealtime()
            java.lang.String r7 = "invocation_time_ms"
            r9.putLong(r7, r5)
            com.android.systemui.assist.AssistLogger r5 = r8.mAssistLogger
            java.lang.Integer r6 = java.lang.Integer.valueOf(r4)
            r7 = 1
            r5.reportAssistantInvocationEventFromLegacy(r3, r7, r0, r6)
            r8.logStartAssistLegacy(r3, r4)
            if (r1 == 0) goto L_0x0051
            com.android.internal.app.AssistUtils r8 = r8.mAssistUtils
            r0 = 4
            r1 = 0
            r8.showSessionForActiveService(r9, r0, r1, r1)
            goto L_0x00d9
        L_0x0051:
            com.android.systemui.statusbar.policy.DeviceProvisionedController r1 = r8.mDeviceProvisionedController
            boolean r1 = r1.isDeviceProvisioned()
            if (r1 != 0) goto L_0x005b
            goto L_0x00d9
        L_0x005b:
            com.android.systemui.statusbar.CommandQueue r1 = r8.mCommandQueue
            r3 = 3
            r1.animateCollapsePanels(r3, r2)
            android.content.Context r1 = r8.mContext
            android.content.ContentResolver r1 = r1.getContentResolver()
            r3 = -2
            java.lang.String r4 = "assist_structure_enabled"
            int r1 = android.provider.Settings.Secure.getIntForUser(r1, r4, r7, r3)
            if (r1 == 0) goto L_0x0071
            r2 = r7
        L_0x0071:
            android.content.Context r1 = r8.mContext
            java.lang.String r3 = "search"
            java.lang.Object r1 = r1.getSystemService(r3)
            android.app.SearchManager r1 = (android.app.SearchManager) r1
            if (r1 != 0) goto L_0x007f
            goto L_0x00d9
        L_0x007f:
            android.content.Intent r1 = r1.getAssistIntent(r2)
            if (r1 != 0) goto L_0x0086
            goto L_0x00d9
        L_0x0086:
            r1.setComponent(r0)
            r1.putExtras(r9)
            if (r2 == 0) goto L_0x00a9
            android.content.Context r9 = r8.mContext
            boolean r9 = com.android.internal.app.AssistUtils.isDisclosureEnabled(r9)
            if (r9 == 0) goto L_0x00a9
            com.android.systemui.assist.AssistDisclosure r9 = r8.mAssistDisclosure
            java.util.Objects.requireNonNull(r9)
            android.os.Handler r0 = r9.mHandler
            com.android.systemui.assist.AssistDisclosure$1 r2 = r9.mShowRunnable
            r0.removeCallbacks(r2)
            android.os.Handler r0 = r9.mHandler
            com.android.systemui.assist.AssistDisclosure$1 r9 = r9.mShowRunnable
            r0.post(r9)
        L_0x00a9:
            android.content.Context r9 = r8.mContext     // Catch:{ ActivityNotFoundException -> 0x00c3 }
            r0 = 2130772554(0x7f01024a, float:1.714823E38)
            r2 = 2130772555(0x7f01024b, float:1.7148232E38)
            android.app.ActivityOptions r9 = android.app.ActivityOptions.makeCustomAnimation(r9, r0, r2)     // Catch:{ ActivityNotFoundException -> 0x00c3 }
            r0 = 268435456(0x10000000, float:2.5243549E-29)
            r1.addFlags(r0)     // Catch:{ ActivityNotFoundException -> 0x00c3 }
            com.android.systemui.assist.AssistManager$3 r0 = new com.android.systemui.assist.AssistManager$3     // Catch:{ ActivityNotFoundException -> 0x00c3 }
            r0.<init>(r1, r9)     // Catch:{ ActivityNotFoundException -> 0x00c3 }
            android.os.AsyncTask.execute(r0)     // Catch:{ ActivityNotFoundException -> 0x00c3 }
            goto L_0x00d9
        L_0x00c3:
            java.lang.String r8 = "Activity not found for "
            java.lang.StringBuilder r8 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r8)
            java.lang.String r9 = r1.getAction()
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            java.lang.String r9 = "AssistManager"
            android.util.Log.w(r9, r8)
        L_0x00d9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.assist.AssistManager.startAssist(android.os.Bundle):void");
    }
}
