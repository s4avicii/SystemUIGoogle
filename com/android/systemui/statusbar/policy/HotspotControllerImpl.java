package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.content.Context;
import android.net.TetheringManager;
import android.net.wifi.WifiClient;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.UserManager;
import android.util.Log;
import com.android.internal.util.ConcurrentUtils;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.statusbar.policy.HotspotController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class HotspotControllerImpl implements HotspotController, WifiManager.SoftApCallback {
    public static final boolean DEBUG = Log.isLoggable("HotspotController", 3);
    public final ArrayList<HotspotController.Callback> mCallbacks = new ArrayList<>();
    public final Context mContext;
    public volatile boolean mHasTetherableWifiRegexs = true;
    public int mHotspotState;
    public volatile boolean mIsTetheringSupported = true;
    public final Handler mMainHandler;
    public volatile int mNumConnectedDevices;
    public C16191 mTetheringCallback = new TetheringManager.TetheringEventCallback() {
        public final void onTetheringSupported(boolean z) {
            if (HotspotControllerImpl.this.mIsTetheringSupported != z) {
                HotspotControllerImpl.this.mIsTetheringSupported = z;
                HotspotControllerImpl.m253$$Nest$mfireHotspotAvailabilityChanged(HotspotControllerImpl.this);
            }
        }

        public final void onTetherableInterfaceRegexpsChanged(TetheringManager.TetheringInterfaceRegexps tetheringInterfaceRegexps) {
            boolean z;
            if (tetheringInterfaceRegexps.getTetherableWifiRegexs().size() != 0) {
                z = true;
            } else {
                z = false;
            }
            if (HotspotControllerImpl.this.mHasTetherableWifiRegexs != z) {
                HotspotControllerImpl.this.mHasTetherableWifiRegexs = z;
                HotspotControllerImpl.m253$$Nest$mfireHotspotAvailabilityChanged(HotspotControllerImpl.this);
            }
        }
    };
    public final TetheringManager mTetheringManager;
    public boolean mWaitingForTerminalState;
    public final WifiManager mWifiManager;

    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void addCallback(java.lang.Object r5) {
        /*
            r4 = this;
            com.android.systemui.statusbar.policy.HotspotController$Callback r5 = (com.android.systemui.statusbar.policy.HotspotController.Callback) r5
            java.util.ArrayList<com.android.systemui.statusbar.policy.HotspotController$Callback> r0 = r4.mCallbacks
            monitor-enter(r0)
            if (r5 == 0) goto L_0x0056
            java.util.ArrayList<com.android.systemui.statusbar.policy.HotspotController$Callback> r1 = r4.mCallbacks     // Catch:{ all -> 0x0058 }
            boolean r1 = r1.contains(r5)     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x0010
            goto L_0x0056
        L_0x0010:
            boolean r1 = DEBUG     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x002a
            java.lang.String r1 = "HotspotController"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0058 }
            r2.<init>()     // Catch:{ all -> 0x0058 }
            java.lang.String r3 = "addCallback "
            r2.append(r3)     // Catch:{ all -> 0x0058 }
            r2.append(r5)     // Catch:{ all -> 0x0058 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0058 }
            android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0058 }
        L_0x002a:
            java.util.ArrayList<com.android.systemui.statusbar.policy.HotspotController$Callback> r1 = r4.mCallbacks     // Catch:{ all -> 0x0058 }
            r1.add(r5)     // Catch:{ all -> 0x0058 }
            android.net.wifi.WifiManager r1 = r4.mWifiManager     // Catch:{ all -> 0x0058 }
            if (r1 == 0) goto L_0x0054
            java.util.ArrayList<com.android.systemui.statusbar.policy.HotspotController$Callback> r1 = r4.mCallbacks     // Catch:{ all -> 0x0058 }
            int r1 = r1.size()     // Catch:{ all -> 0x0058 }
            r2 = 1
            if (r1 != r2) goto L_0x0049
            android.net.wifi.WifiManager r5 = r4.mWifiManager     // Catch:{ all -> 0x0058 }
            android.os.HandlerExecutor r1 = new android.os.HandlerExecutor     // Catch:{ all -> 0x0058 }
            android.os.Handler r2 = r4.mMainHandler     // Catch:{ all -> 0x0058 }
            r1.<init>(r2)     // Catch:{ all -> 0x0058 }
            r5.registerSoftApCallback(r1, r4)     // Catch:{ all -> 0x0058 }
            goto L_0x0054
        L_0x0049:
            android.os.Handler r1 = r4.mMainHandler     // Catch:{ all -> 0x0058 }
            com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda6 r2 = new com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda6     // Catch:{ all -> 0x0058 }
            r3 = 2
            r2.<init>(r4, r5, r3)     // Catch:{ all -> 0x0058 }
            r1.post(r2)     // Catch:{ all -> 0x0058 }
        L_0x0054:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            goto L_0x0057
        L_0x0056:
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
        L_0x0057:
            return
        L_0x0058:
            r4 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0058 }
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.HotspotControllerImpl.addCallback(java.lang.Object):void");
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        printWriter.println("HotspotController state:");
        printWriter.print("  available=");
        printWriter.println(isHotspotSupported());
        printWriter.print("  mHotspotState=");
        switch (this.mHotspotState) {
            case 10:
                str = "DISABLING";
                break;
            case QSTileImpl.C1034H.STALE:
                str = "DISABLED";
                break;
            case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                str = "ENABLING";
                break;
            case C0961QS.VERSION:
                str = "ENABLED";
                break;
            case 14:
                str = "FAILED";
                break;
            default:
                str = null;
                break;
        }
        printWriter.println(str);
        printWriter.print("  mNumConnectedDevices=");
        printWriter.println(this.mNumConnectedDevices);
        printWriter.print("  mWaitingForTerminalState=");
        printWriter.println(this.mWaitingForTerminalState);
    }

    public final void fireHotspotChangedCallback() {
        ArrayList arrayList;
        synchronized (this.mCallbacks) {
            arrayList = new ArrayList(this.mCallbacks);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((HotspotController.Callback) it.next()).onHotspotChanged(isHotspotEnabled(), this.mNumConnectedDevices);
        }
    }

    public final boolean isHotspotEnabled() {
        if (this.mHotspotState == 13) {
            return true;
        }
        return false;
    }

    public final boolean isHotspotSupported() {
        if (!this.mIsTetheringSupported || !this.mHasTetherableWifiRegexs || !UserManager.get(this.mContext).isUserAdmin(ActivityManager.getCurrentUser())) {
            return false;
        }
        return true;
    }

    public final boolean isHotspotTransient() {
        if (this.mWaitingForTerminalState || this.mHotspotState == 12) {
            return true;
        }
        return false;
    }

    public final void maybeResetSoftApState() {
        if (this.mWaitingForTerminalState) {
            int i = this.mHotspotState;
            if (!(i == 11 || i == 13)) {
                if (i == 14) {
                    this.mTetheringManager.stopTethering(0);
                } else {
                    return;
                }
            }
            this.mWaitingForTerminalState = false;
        }
    }

    public final void onStateChanged(int i, int i2) {
        this.mHotspotState = i;
        maybeResetSoftApState();
        if (!isHotspotEnabled()) {
            this.mNumConnectedDevices = 0;
        }
        fireHotspotChangedCallback();
    }

    public final void removeCallback(Object obj) {
        WifiManager wifiManager;
        HotspotController.Callback callback = (HotspotController.Callback) obj;
        if (callback != null) {
            if (DEBUG) {
                Log.d("HotspotController", "removeCallback " + callback);
            }
            synchronized (this.mCallbacks) {
                this.mCallbacks.remove(callback);
                if (this.mCallbacks.isEmpty() && (wifiManager = this.mWifiManager) != null) {
                    wifiManager.unregisterSoftApCallback(this);
                }
            }
        }
    }

    public final void setHotspotEnabled(boolean z) {
        if (this.mWaitingForTerminalState) {
            if (DEBUG) {
                Log.d("HotspotController", "Ignoring setHotspotEnabled; waiting for terminal state.");
            }
        } else if (z) {
            this.mWaitingForTerminalState = true;
            if (DEBUG) {
                Log.d("HotspotController", "Starting tethering");
            }
            this.mTetheringManager.startTethering(new TetheringManager.TetheringRequest.Builder(0).setShouldShowEntitlementUi(false).build(), ConcurrentUtils.DIRECT_EXECUTOR, new TetheringManager.StartTetheringCallback() {
                public final void onTetheringFailed(int i) {
                    if (HotspotControllerImpl.DEBUG) {
                        Log.d("HotspotController", "onTetheringFailed");
                    }
                    HotspotControllerImpl.this.maybeResetSoftApState();
                    HotspotControllerImpl.this.fireHotspotChangedCallback();
                }
            });
        } else {
            this.mTetheringManager.stopTethering(0);
        }
    }

    /* renamed from: -$$Nest$mfireHotspotAvailabilityChanged  reason: not valid java name */
    public static void m253$$Nest$mfireHotspotAvailabilityChanged(HotspotControllerImpl hotspotControllerImpl) {
        ArrayList arrayList;
        Objects.requireNonNull(hotspotControllerImpl);
        synchronized (hotspotControllerImpl.mCallbacks) {
            arrayList = new ArrayList(hotspotControllerImpl.mCallbacks);
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((HotspotController.Callback) it.next()).onHotspotAvailabilityChanged(hotspotControllerImpl.isHotspotSupported());
        }
    }

    public HotspotControllerImpl(Context context, Handler handler, Handler handler2, DumpManager dumpManager) {
        this.mContext = context;
        TetheringManager tetheringManager = (TetheringManager) context.getSystemService(TetheringManager.class);
        this.mTetheringManager = tetheringManager;
        this.mWifiManager = (WifiManager) context.getSystemService("wifi");
        this.mMainHandler = handler;
        tetheringManager.registerTetheringEventCallback(new HandlerExecutor(handler2), this.mTetheringCallback);
        Class<HotspotControllerImpl> cls = HotspotControllerImpl.class;
        dumpManager.registerDumpable("HotspotControllerImpl", this);
    }

    public final void onConnectedClientsChanged(List<WifiClient> list) {
        this.mNumConnectedDevices = list.size();
        fireHotspotChangedCallback();
    }

    public final int getNumConnectedDevices() {
        return this.mNumConnectedDevices;
    }
}
