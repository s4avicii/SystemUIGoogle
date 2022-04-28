package com.google.android.systemui.dreamliner;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.service.dreams.IDreamManager;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda1;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptSuppressor;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.volume.CaptionsToggleImageButton$$ExternalSyntheticLambda0;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda3;
import com.google.android.systemui.dreamliner.WirelessCharger;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class DockObserver extends BroadcastReceiver implements DockManager {
    @VisibleForTesting
    public static final String ACTION_ALIGN_STATE_CHANGE = "com.google.android.systemui.dreamliner.ALIGNMENT_CHANGE";
    @VisibleForTesting
    public static final String ACTION_CHALLENGE = "com.google.android.systemui.dreamliner.ACTION_CHALLENGE";
    @VisibleForTesting
    public static final String ACTION_DOCK_UI_ACTIVE = "com.google.android.systemui.dreamliner.ACTION_DOCK_UI_ACTIVE";
    @VisibleForTesting
    public static final String ACTION_DOCK_UI_IDLE = "com.google.android.systemui.dreamliner.ACTION_DOCK_UI_IDLE";
    @VisibleForTesting
    public static final String ACTION_GET_DOCK_INFO = "com.google.android.systemui.dreamliner.ACTION_GET_DOCK_INFO";
    @VisibleForTesting
    public static final String ACTION_KEY_EXCHANGE = "com.google.android.systemui.dreamliner.ACTION_KEY_EXCHANGE";
    @VisibleForTesting
    public static final String ACTION_REBIND_DOCK_SERVICE = "com.google.android.systemui.dreamliner.ACTION_REBIND_DOCK_SERVICE";
    @VisibleForTesting
    public static final String ACTION_START_DREAMLINER_CONTROL_SERVICE = "com.google.android.apps.dreamliner.START";
    @VisibleForTesting
    public static final String COMPONENTNAME_DREAMLINER_CONTROL_SERVICE = "com.google.android.apps.dreamliner/.DreamlinerControlService";
    @VisibleForTesting
    public static final String EXTRA_ALIGN_STATE = "align_state";
    @VisibleForTesting
    public static final String EXTRA_CHALLENGE_DATA = "challenge_data";
    @VisibleForTesting
    public static final String EXTRA_CHALLENGE_DOCK_ID = "challenge_dock_id";
    @VisibleForTesting
    public static final String EXTRA_PUBLIC_KEY = "public_key";
    @VisibleForTesting
    public static final String KEY_SHOWING = "showing";
    @VisibleForTesting
    public static final String PERMISSION_WIRELESS_CHARGER_STATUS = "com.google.android.systemui.permission.WIRELESS_CHARGER_STATUS";
    @VisibleForTesting
    public static final int RESULT_NOT_FOUND = 1;
    @VisibleForTesting
    public static final int RESULT_OK = 0;
    @VisibleForTesting
    public static volatile ExecutorService mSingleThreadExecutor = null;
    public static boolean sIsDockingUiShowing = false;
    public final ArrayList mAlignmentStateListeners;
    public final ArrayList mClients;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final DockAlignmentController mDockAlignmentController;
    @VisibleForTesting
    public DockGestureController mDockGestureController;
    @VisibleForTesting
    public int mDockState = 0;
    public ImageView mDreamlinerGear;
    @VisibleForTesting
    public final DreamlinerBroadcastReceiver mDreamlinerReceiver = new DreamlinerBroadcastReceiver();
    @VisibleForTesting
    public DreamlinerServiceConn mDreamlinerServiceConn;
    public int mFanLevel = -1;
    public DockIndicationController mIndicationController;
    public final C22312 mInterruptSuppressor;
    @VisibleForTesting
    public int mLastAlignState = -1;
    public final DelayableExecutor mMainExecutor;
    public DockObserver$$ExternalSyntheticLambda0 mPhotoAction;
    public FrameLayout mPhotoPreview;
    public final StatusBarStateController mStatusBarStateController;
    public final C22301 mUserTracker;
    public final WirelessCharger mWirelessCharger;

    @VisibleForTesting
    public final class ChallengeCallback implements WirelessCharger.ChallengeCallback {
        public final ResultReceiver mResultReceiver;

        public ChallengeCallback(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }
    }

    public class ChallengeWithDock implements Runnable {
        public final byte[] challengeData;
        public final byte dockId;
        public final ResultReceiver resultReceiver;

        public ChallengeWithDock(ResultReceiver resultReceiver2, byte b, byte[] bArr) {
            this.dockId = b;
            this.challengeData = bArr;
            this.resultReceiver = resultReceiver2;
        }

        public final void run() {
            DockObserver dockObserver = DockObserver.this;
            WirelessCharger wirelessCharger = dockObserver.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.challenge(this.dockId, this.challengeData, new ChallengeCallback(this.resultReceiver));
            }
        }
    }

    @VisibleForTesting
    public class DreamlinerBroadcastReceiver extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mListening;

        public DreamlinerBroadcastReceiver() {
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onReceive(android.content.Context r13, android.content.Intent r14) {
            /*
                r12 = this;
                if (r14 != 0) goto L_0x0003
                return
            L_0x0003:
                java.lang.String r0 = "Dock Receiver.onReceive(): "
                java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
                java.lang.String r1 = r14.getAction()
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                java.lang.String r1 = "DLObserver"
                android.util.Log.d(r1, r0)
                java.lang.String r0 = r14.getAction()
                java.util.Objects.requireNonNull(r0)
                int r2 = r0.hashCode()
                r3 = 4
                r4 = 2
                r5 = -1
                r6 = 1
                r7 = 0
                switch(r2) {
                    case -2133451883: goto L_0x0123;
                    case -1627881412: goto L_0x0118;
                    case -1616532553: goto L_0x010d;
                    case -1598391011: goto L_0x0102;
                    case -1584152500: goto L_0x00f7;
                    case -1579804275: goto L_0x00ec;
                    case -1458969207: goto L_0x00e1;
                    case -1185055092: goto L_0x00d6;
                    case -686255721: goto L_0x00c8;
                    case -545730616: goto L_0x00ba;
                    case -484477188: goto L_0x00ac;
                    case -390730981: goto L_0x009e;
                    case 664552276: goto L_0x0090;
                    case 675144007: goto L_0x0082;
                    case 675346819: goto L_0x0074;
                    case 717413661: goto L_0x0066;
                    case 1954561023: goto L_0x0058;
                    case 1996802687: goto L_0x004a;
                    case 2009307741: goto L_0x003c;
                    case 2121889077: goto L_0x002e;
                    default: goto L_0x002c;
                }
            L_0x002c:
                goto L_0x012e
            L_0x002e:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_GET_WPC_CHALLENGE_RESPONSE"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0038
                goto L_0x012e
            L_0x0038:
                r0 = 19
                goto L_0x012f
            L_0x003c:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_GET_FAN_INFO"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0046
                goto L_0x012e
            L_0x0046:
                r0 = 18
                goto L_0x012f
            L_0x004a:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_DOCK_UI_ACTIVE"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0054
                goto L_0x012e
            L_0x0054:
                r0 = 17
                goto L_0x012f
            L_0x0058:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_GET_WPC_CERTIFICATE"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0062
                goto L_0x012e
            L_0x0062:
                r0 = 16
                goto L_0x012f
            L_0x0066:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.assistant_poodle"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0070
                goto L_0x012e
            L_0x0070:
                r0 = 15
                goto L_0x012f
            L_0x0074:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.photo"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x007e
                goto L_0x012e
            L_0x007e:
                r0 = 14
                goto L_0x012f
            L_0x0082:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.pause"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x008c
                goto L_0x012e
            L_0x008c:
                r0 = 13
                goto L_0x012f
            L_0x0090:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.dream"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x009a
                goto L_0x012e
            L_0x009a:
                r0 = 12
                goto L_0x012f
            L_0x009e:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.undock"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x00a8
                goto L_0x012e
            L_0x00a8:
                r0 = 11
                goto L_0x012f
            L_0x00ac:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.resume"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x00b6
                goto L_0x012e
            L_0x00b6:
                r0 = 10
                goto L_0x012f
            L_0x00ba:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.paired"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x00c4
                goto L_0x012e
            L_0x00c4:
                r0 = 9
                goto L_0x012f
            L_0x00c8:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_GET_WPC_DIGESTS"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x00d2
                goto L_0x012e
            L_0x00d2:
                r0 = 8
                goto L_0x012f
            L_0x00d6:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_GET_FAN_SIMPLE_INFO"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x00df
                goto L_0x012e
            L_0x00df:
                r0 = 7
                goto L_0x012f
            L_0x00e1:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_CHALLENGE"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x00ea
                goto L_0x012e
            L_0x00ea:
                r0 = 6
                goto L_0x012f
            L_0x00ec:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_DOCK_UI_IDLE"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x00f5
                goto L_0x012e
            L_0x00f5:
                r0 = 5
                goto L_0x012f
            L_0x00f7:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.photo_error"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0100
                goto L_0x012e
            L_0x0100:
                r0 = r3
                goto L_0x012f
            L_0x0102:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_KEY_EXCHANGE"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x010b
                goto L_0x012e
            L_0x010b:
                r0 = 3
                goto L_0x012f
            L_0x010d:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_GET_DOCK_INFO"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0116
                goto L_0x012e
            L_0x0116:
                r0 = r4
                goto L_0x012f
            L_0x0118:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_SET_FAN"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x0121
                goto L_0x012e
            L_0x0121:
                r0 = r6
                goto L_0x012f
            L_0x0123:
                java.lang.String r2 = "com.google.android.systemui.dreamliner.ACTION_GET_FAN_LEVEL"
                boolean r0 = r0.equals(r2)
                if (r0 != 0) goto L_0x012c
                goto L_0x012e
            L_0x012c:
                r0 = r7
                goto L_0x012f
            L_0x012e:
                r0 = r5
            L_0x012f:
                java.lang.String r2 = "slot_number"
                java.lang.String r8 = "fan_id"
                r9 = 0
                java.lang.String r10 = "android.intent.extra.RESULT_RECEIVER"
                r11 = 1073741824(0x40000000, float:2.0)
                switch(r0) {
                    case 0: goto L_0x044b;
                    case 1: goto L_0x03fa;
                    case 2: goto L_0x03e7;
                    case 3: goto L_0x03ba;
                    case 4: goto L_0x03a7;
                    case 5: goto L_0x038a;
                    case 6: goto L_0x0354;
                    case 7: goto L_0x0328;
                    case 8: goto L_0x02f2;
                    case 9: goto L_0x02a0;
                    case 10: goto L_0x02be;
                    case 11: goto L_0x0286;
                    case 12: goto L_0x026a;
                    case 13: goto L_0x0250;
                    case 14: goto L_0x0220;
                    case 15: goto L_0x020c;
                    case 16: goto L_0x01c5;
                    case 17: goto L_0x01a8;
                    case 18: goto L_0x017c;
                    case 19: goto L_0x013e;
                    default: goto L_0x013c;
                }
            L_0x013c:
                goto L_0x0455
            L_0x013e:
                byte r13 = r14.getByteExtra(r2, r5)
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r2 = "gWACR, num="
                r0.append(r2)
                r0.append(r13)
                java.lang.String r0 = r0.toString()
                android.util.Log.d(r1, r0)
                android.os.Parcelable r0 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                if (r0 == 0) goto L_0x0455
                java.lang.String r1 = "wpc_nonce"
                byte[] r14 = r14.getByteArrayExtra(r1)
                if (r14 == 0) goto L_0x0177
                int r1 = r14.length
                if (r1 > 0) goto L_0x016b
                goto L_0x0177
            L_0x016b:
                com.google.android.systemui.dreamliner.DockObserver$GetWpcAuthChallengeResponse r1 = new com.google.android.systemui.dreamliner.DockObserver$GetWpcAuthChallengeResponse
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                r1.<init>(r0, r13, r14)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r1)
                goto L_0x0455
            L_0x0177:
                r0.send(r6, r9)
                goto L_0x0455
            L_0x017c:
                java.lang.String r13 = "command=0, i="
                java.lang.StringBuilder r13 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r13)
                byte r0 = r14.getByteExtra(r8, r5)
                r13.append(r0)
                java.lang.String r13 = r13.toString()
                android.util.Log.d(r1, r13)
                android.os.Parcelable r13 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r13 = (android.os.ResultReceiver) r13
                if (r13 == 0) goto L_0x0455
                com.google.android.systemui.dreamliner.DockObserver$GetFanInformation r0 = new com.google.android.systemui.dreamliner.DockObserver$GetFanInformation
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                byte r14 = r14.getByteExtra(r8, r7)
                r0.<init>(r14, r13)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r0)
                goto L_0x0455
            L_0x01a8:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                java.util.Objects.requireNonNull(r12)
                java.lang.String r12 = "sendDockActiveIntent()"
                android.util.Log.d(r1, r12)
                android.content.Intent r12 = new android.content.Intent
                java.lang.String r14 = "android.intent.action.DOCK_ACTIVE"
                r12.<init>(r14)
                android.content.Intent r12 = r12.addFlags(r11)
                r13.sendBroadcast(r12)
                com.google.android.systemui.dreamliner.DockObserver.sIsDockingUiShowing = r7
                goto L_0x0455
            L_0x01c5:
                byte r3 = r14.getByteExtra(r2, r5)
                java.lang.String r13 = "cert_offset"
                short r4 = r14.getShortExtra(r13, r5)
                java.lang.String r13 = "cert_length"
                short r13 = r14.getShortExtra(r13, r5)
                java.lang.String r0 = "gWAC, num="
                java.lang.String r2 = ", offset="
                java.lang.String r7 = ", length="
                java.lang.StringBuilder r0 = androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0.m19m(r0, r3, r2, r4, r7)
                r0.append(r13)
                java.lang.String r0 = r0.toString()
                android.util.Log.d(r1, r0)
                android.os.Parcelable r14 = r14.getParcelableExtra(r10)
                r2 = r14
                android.os.ResultReceiver r2 = (android.os.ResultReceiver) r2
                if (r2 == 0) goto L_0x0455
                if (r3 == r5) goto L_0x0207
                if (r4 == r5) goto L_0x0207
                if (r13 != r5) goto L_0x01f9
                goto L_0x0207
            L_0x01f9:
                com.google.android.systemui.dreamliner.DockObserver$GetWpcAuthCertificate r14 = new com.google.android.systemui.dreamliner.DockObserver$GetWpcAuthCertificate
                com.google.android.systemui.dreamliner.DockObserver r1 = com.google.android.systemui.dreamliner.DockObserver.this
                r0 = r14
                r5 = r13
                r0.<init>(r2, r3, r4, r5)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r14)
                goto L_0x0455
            L_0x0207:
                r2.send(r6, r9)
                goto L_0x0455
            L_0x020c:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockIndicationController r12 = r12.mIndicationController
                if (r12 == 0) goto L_0x0455
                java.lang.String r13 = "showing"
                boolean r13 = r14.getBooleanExtra(r13, r7)
                r12.mTopIconShowing = r13
                r12.updateVisibility()
                goto L_0x0455
            L_0x0220:
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                java.util.Objects.requireNonNull(r13)
                android.os.Parcelable r0 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r0 = (android.os.ResultReceiver) r0
                java.lang.String r2 = "enabled"
                boolean r14 = r14.getBooleanExtra(r2, r7)
                java.lang.String r2 = "configPhotoAction, enabled="
                androidx.core.view.ViewCompat$$ExternalSyntheticLambda0.m12m(r2, r14, r1)
                com.google.android.systemui.dreamliner.DockGestureController r1 = r13.mDockGestureController
                if (r1 == 0) goto L_0x023c
                r1.mPhotoEnabled = r14
            L_0x023c:
                if (r0 == 0) goto L_0x0249
                com.google.android.systemui.dreamliner.DockIndicationController r14 = r13.mIndicationController
                if (r14 == 0) goto L_0x0249
                com.google.android.systemui.dreamliner.DockObserver$$ExternalSyntheticLambda0 r14 = new com.google.android.systemui.dreamliner.DockObserver$$ExternalSyntheticLambda0
                r14.<init>(r13, r0, r7)
                r13.mPhotoAction = r14
            L_0x0249:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                r12.runPhotoAction()
                goto L_0x0455
            L_0x0250:
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                r13.onDockStateChanged(r4)
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r13 = r13.mDockGestureController
                java.lang.Class<com.google.android.systemui.dreamliner.DockGestureController> r14 = com.google.android.systemui.dreamliner.DockGestureController.class
                boolean r13 = com.google.android.systemui.dreamliner.DockObserver.assertNotNull(r13)
                if (r13 == 0) goto L_0x0455
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r12 = r12.mDockGestureController
                r12.stopMonitoring()
                goto L_0x0455
            L_0x026a:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                java.util.Objects.requireNonNull(r12)
                java.lang.Class<android.os.PowerManager> r12 = android.os.PowerManager.class
                java.lang.Object r12 = r13.getSystemService(r12)
                android.os.PowerManager r12 = (android.os.PowerManager) r12
                boolean r13 = r12.isScreenOn()
                if (r13 == 0) goto L_0x0455
                long r13 = android.os.SystemClock.uptimeMillis()
                r12.goToSleep(r13)
                goto L_0x0455
            L_0x0286:
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                r13.onDockStateChanged(r7)
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r13 = r13.mDockGestureController
                java.lang.Class<com.google.android.systemui.dreamliner.DockGestureController> r14 = com.google.android.systemui.dreamliner.DockGestureController.class
                boolean r13 = com.google.android.systemui.dreamliner.DockObserver.assertNotNull(r13)
                if (r13 == 0) goto L_0x0455
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r12 = r12.mDockGestureController
                r12.stopMonitoring()
                goto L_0x0455
            L_0x02a0:
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r13 = r13.mDockGestureController
                java.lang.Class<com.google.android.systemui.dreamliner.DockGestureController> r0 = com.google.android.systemui.dreamliner.DockGestureController.class
                boolean r13 = com.google.android.systemui.dreamliner.DockObserver.assertNotNull(r13)
                if (r13 == 0) goto L_0x02be
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r13 = r13.mDockGestureController
                java.lang.String r0 = "single_tap_action"
                android.os.Parcelable r14 = r14.getParcelableExtra(r0)
                android.app.PendingIntent r14 = (android.app.PendingIntent) r14
                java.util.Objects.requireNonNull(r13)
                r13.mTapAction = r14
            L_0x02be:
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                r13.onDockStateChanged(r6)
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r13 = r13.mDockGestureController
                java.lang.Class<com.google.android.systemui.dreamliner.DockGestureController> r14 = com.google.android.systemui.dreamliner.DockGestureController.class
                boolean r13 = com.google.android.systemui.dreamliner.DockObserver.assertNotNull(r13)
                if (r13 == 0) goto L_0x0455
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockGestureController r12 = r12.mDockGestureController
                java.util.Objects.requireNonNull(r12)
                android.widget.ImageView r13 = r12.mSettingsGear
                r13.setVisibility(r3)
                com.android.systemui.plugins.statusbar.StatusBarStateController r13 = r12.mStatusBarStateController
                boolean r13 = r13.isDozing()
                r12.onDozingChanged(r13)
                com.android.systemui.plugins.statusbar.StatusBarStateController r13 = r12.mStatusBarStateController
                r13.addCallback(r12)
                com.android.systemui.statusbar.policy.KeyguardStateController r13 = r12.mKeyguardStateController
                com.google.android.systemui.dreamliner.DockGestureController$1 r12 = r12.mKeyguardMonitorCallback
                r13.addCallback(r12)
                goto L_0x0455
            L_0x02f2:
                java.lang.String r13 = "slot_mask"
                byte r13 = r14.getByteExtra(r13, r5)
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r2 = "gWAD, mask="
                r0.append(r2)
                r0.append(r13)
                java.lang.String r0 = r0.toString()
                android.util.Log.d(r1, r0)
                android.os.Parcelable r14 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r14 = (android.os.ResultReceiver) r14
                if (r14 == 0) goto L_0x0455
                if (r13 != r5) goto L_0x031c
                r14.send(r6, r9)
                goto L_0x0455
            L_0x031c:
                com.google.android.systemui.dreamliner.DockObserver$GetWpcAuthDigests r0 = new com.google.android.systemui.dreamliner.DockObserver$GetWpcAuthDigests
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                r0.<init>(r14, r13)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r0)
                goto L_0x0455
            L_0x0328:
                java.lang.String r13 = "command=3, i="
                java.lang.StringBuilder r13 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r13)
                byte r0 = r14.getByteExtra(r8, r5)
                r13.append(r0)
                java.lang.String r13 = r13.toString()
                android.util.Log.d(r1, r13)
                android.os.Parcelable r13 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r13 = (android.os.ResultReceiver) r13
                if (r13 == 0) goto L_0x0455
                com.google.android.systemui.dreamliner.DockObserver$GetFanSimpleInformation r0 = new com.google.android.systemui.dreamliner.DockObserver$GetFanSimpleInformation
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                byte r14 = r14.getByteExtra(r8, r7)
                r0.<init>(r14, r13)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r0)
                goto L_0x0455
            L_0x0354:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                java.util.Objects.requireNonNull(r12)
                java.lang.String r13 = "triggerChallengeWithDock"
                android.util.Log.d(r1, r13)
                android.os.Parcelable r13 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r13 = (android.os.ResultReceiver) r13
                if (r13 == 0) goto L_0x0455
                java.lang.String r0 = "challenge_dock_id"
                byte r0 = r14.getByteExtra(r0, r5)
                java.lang.String r1 = "challenge_data"
                byte[] r14 = r14.getByteArrayExtra(r1)
                if (r14 == 0) goto L_0x0385
                int r1 = r14.length
                if (r1 <= 0) goto L_0x0385
                if (r0 >= 0) goto L_0x037b
                goto L_0x0385
            L_0x037b:
                com.google.android.systemui.dreamliner.DockObserver$ChallengeWithDock r1 = new com.google.android.systemui.dreamliner.DockObserver$ChallengeWithDock
                r1.<init>(r13, r0, r14)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r1)
                goto L_0x0455
            L_0x0385:
                r13.send(r6, r9)
                goto L_0x0455
            L_0x038a:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                java.util.Objects.requireNonNull(r12)
                java.lang.String r12 = "sendDockIdleIntent()"
                android.util.Log.d(r1, r12)
                android.content.Intent r12 = new android.content.Intent
                java.lang.String r14 = "android.intent.action.DOCK_IDLE"
                r12.<init>(r14)
                android.content.Intent r12 = r12.addFlags(r11)
                r13.sendBroadcast(r12)
                com.google.android.systemui.dreamliner.DockObserver.sIsDockingUiShowing = r6
                goto L_0x0455
            L_0x03a7:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                java.util.Objects.requireNonNull(r12)
                java.lang.String r13 = "Fail to launch photo"
                android.util.Log.w(r1, r13)
                com.google.android.systemui.dreamliner.DockGestureController r12 = r12.mDockGestureController
                if (r12 == 0) goto L_0x0455
                r12.hidePhotoPreview(r7)
                goto L_0x0455
            L_0x03ba:
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                java.util.Objects.requireNonNull(r12)
                java.lang.String r13 = "triggerKeyExchangeWithDock"
                android.util.Log.d(r1, r13)
                android.os.Parcelable r13 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r13 = (android.os.ResultReceiver) r13
                if (r13 == 0) goto L_0x0455
                java.lang.String r0 = "public_key"
                byte[] r14 = r14.getByteArrayExtra(r0)
                if (r14 == 0) goto L_0x03e3
                int r0 = r14.length
                if (r0 > 0) goto L_0x03d9
                goto L_0x03e3
            L_0x03d9:
                com.google.android.systemui.dreamliner.DockObserver$KeyExchangeWithDock r0 = new com.google.android.systemui.dreamliner.DockObserver$KeyExchangeWithDock
                r0.<init>(r13, r14)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r0)
                goto L_0x0455
            L_0x03e3:
                r13.send(r6, r9)
                goto L_0x0455
            L_0x03e7:
                android.os.Parcelable r13 = r14.getParcelableExtra(r10)
                android.os.ResultReceiver r13 = (android.os.ResultReceiver) r13
                if (r13 == 0) goto L_0x0455
                com.google.android.systemui.dreamliner.DockObserver$GetDockInfo r14 = new com.google.android.systemui.dreamliner.DockObserver$GetDockInfo
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                r14.<init>(r13)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r14)
                goto L_0x0455
            L_0x03fa:
                java.lang.String r13 = "command=1, i="
                java.lang.StringBuilder r13 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r13)
                byte r0 = r14.getByteExtra(r8, r5)
                r13.append(r0)
                java.lang.String r0 = ", m="
                r13.append(r0)
                java.lang.String r0 = "fan_mode"
                byte r2 = r14.getByteExtra(r0, r5)
                r13.append(r2)
                java.lang.String r2 = ", r="
                r13.append(r2)
                java.lang.String r2 = "fan_rpm"
                int r3 = r14.getIntExtra(r2, r5)
                r13.append(r3)
                java.lang.String r13 = r13.toString()
                android.util.Log.d(r1, r13)
                byte r13 = r14.getByteExtra(r8, r7)
                byte r0 = r14.getByteExtra(r0, r7)
                int r14 = r14.getIntExtra(r2, r5)
                if (r0 != r6) goto L_0x0440
                if (r14 != r5) goto L_0x0440
                java.lang.String r12 = "Failed to get r."
                android.util.Log.e(r1, r12)
                goto L_0x0455
            L_0x0440:
                com.google.android.systemui.dreamliner.DockObserver$SetFan r1 = new com.google.android.systemui.dreamliner.DockObserver$SetFan
                com.google.android.systemui.dreamliner.DockObserver r12 = com.google.android.systemui.dreamliner.DockObserver.this
                r1.<init>(r13, r0, r14)
                com.google.android.systemui.dreamliner.DockObserver.runOnBackgroundThread(r1)
                goto L_0x0455
            L_0x044b:
                com.google.android.systemui.dreamliner.DockObserver r13 = com.google.android.systemui.dreamliner.DockObserver.this
                com.google.android.systemui.dreamliner.DockObserver$DreamlinerBroadcastReceiver$$ExternalSyntheticLambda0 r14 = new com.google.android.systemui.dreamliner.DockObserver$DreamlinerBroadcastReceiver$$ExternalSyntheticLambda0
                r14.<init>(r12)
                r13.refreshFanLevel(r14)
            L_0x0455:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.dreamliner.DockObserver.DreamlinerBroadcastReceiver.onReceive(android.content.Context, android.content.Intent):void");
        }

        public final void registerReceiver(Context context) {
            if (!this.mListening) {
                UserHandle userHandle = UserHandle.ALL;
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(DockObserver.ACTION_GET_DOCK_INFO);
                intentFilter.addAction(DockObserver.ACTION_DOCK_UI_IDLE);
                intentFilter.addAction(DockObserver.ACTION_DOCK_UI_ACTIVE);
                intentFilter.addAction(DockObserver.ACTION_KEY_EXCHANGE);
                intentFilter.addAction(DockObserver.ACTION_CHALLENGE);
                intentFilter.addAction("com.google.android.systemui.dreamliner.dream");
                intentFilter.addAction("com.google.android.systemui.dreamliner.paired");
                intentFilter.addAction("com.google.android.systemui.dreamliner.pause");
                intentFilter.addAction("com.google.android.systemui.dreamliner.resume");
                intentFilter.addAction("com.google.android.systemui.dreamliner.undock");
                intentFilter.addAction("com.google.android.systemui.dreamliner.assistant_poodle");
                intentFilter.addAction("com.google.android.systemui.dreamliner.photo");
                intentFilter.addAction("com.google.android.systemui.dreamliner.photo_error");
                intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_GET_FAN_INFO");
                intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_GET_FAN_SIMPLE_INFO");
                intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_SET_FAN");
                intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_GET_FAN_LEVEL");
                intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_GET_WPC_DIGESTS");
                intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_GET_WPC_CERTIFICATE");
                intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_GET_WPC_CHALLENGE_RESPONSE");
                context.registerReceiverAsUser(this, userHandle, intentFilter, DockObserver.PERMISSION_WIRELESS_CHARGER_STATUS, (Handler) null, 2);
                this.mListening = true;
            }
        }
    }

    @VisibleForTesting
    public final class DreamlinerServiceConn implements ServiceConnection {
        public final Context mContext;

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        }

        public DreamlinerServiceConn(Context context) {
            this.mContext = context;
        }

        public final void onBindingDied(ComponentName componentName) {
            DockObserver dockObserver = DockObserver.this;
            Context context = this.mContext;
            String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
            dockObserver.stopDreamlinerService(context);
            DockObserver.sIsDockingUiShowing = false;
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            DockObserver dockObserver = DockObserver.this;
            Context context = this.mContext;
            String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
            Objects.requireNonNull(dockObserver);
            Log.d("DLObserver", "sendDockActiveIntent()");
            context.sendBroadcast(new Intent("android.intent.action.DOCK_ACTIVE").addFlags(1073741824));
        }
    }

    public class GetDockInfo implements Runnable {
        public final ResultReceiver resultReceiver;

        public GetDockInfo(ResultReceiver resultReceiver2) {
            this.resultReceiver = resultReceiver2;
        }

        public final void run() {
            WirelessCharger wirelessCharger = DockObserver.this.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.getInformation(new GetInformationCallback(this.resultReceiver));
            }
        }
    }

    public class GetFanInformation implements Runnable {
        public final byte mFanId;
        public final ResultReceiver mResultReceiver;

        public GetFanInformation(byte b, ResultReceiver resultReceiver) {
            this.mFanId = b;
            this.mResultReceiver = resultReceiver;
        }

        public final void run() {
            WirelessCharger wirelessCharger = DockObserver.this.mWirelessCharger;
            if (wirelessCharger != null) {
                byte b = this.mFanId;
                wirelessCharger.getFanInformation(b, new GetFanInformationCallback(b, this.mResultReceiver));
            }
        }
    }

    public class GetFanSimpleInformation implements Runnable {
        public final byte mFanId;
        public final ResultReceiver mResultReceiver;

        public GetFanSimpleInformation(byte b, ResultReceiver resultReceiver) {
            this.mFanId = b;
            this.mResultReceiver = resultReceiver;
        }

        public final void run() {
            WirelessCharger wirelessCharger = DockObserver.this.mWirelessCharger;
            if (wirelessCharger != null) {
                byte b = this.mFanId;
                wirelessCharger.getFanSimpleInformation(b, new GetFanSimpleInformationCallback(b, this.mResultReceiver));
            }
        }
    }

    public class GetFeatures implements Runnable {
        public final long mChargerId;
        public final ResultReceiver mResultReceiver;

        public GetFeatures(ResultReceiver resultReceiver, long j) {
            this.mResultReceiver = resultReceiver;
            this.mChargerId = j;
        }

        public final void run() {
            DockObserver dockObserver = DockObserver.this;
            WirelessCharger wirelessCharger = dockObserver.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.getFeatures(this.mChargerId, new GetFeaturesCallback(this.mResultReceiver));
            }
        }
    }

    @VisibleForTesting
    public final class GetFeaturesCallback implements WirelessCharger.GetFeaturesCallback {
        public final ResultReceiver mResultReceiver;

        public GetFeaturesCallback(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }
    }

    public class GetWpcAuthCertificate implements Runnable {
        public final short mLength;
        public final short mOffset;
        public final ResultReceiver mResultReceiver;
        public final byte mSlotNum;

        public GetWpcAuthCertificate(ResultReceiver resultReceiver, byte b, short s, short s2) {
            this.mResultReceiver = resultReceiver;
            this.mSlotNum = b;
            this.mOffset = s;
            this.mLength = s2;
        }

        public final void run() {
            DockObserver dockObserver = DockObserver.this;
            WirelessCharger wirelessCharger = dockObserver.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.getWpcAuthCertificate(this.mSlotNum, this.mOffset, this.mLength, new GetWpcAuthCertificateCallback(this.mResultReceiver));
            }
        }
    }

    @VisibleForTesting
    public final class GetWpcAuthCertificateCallback implements WirelessCharger.GetWpcAuthCertificateCallback {
        public final ResultReceiver mResultReceiver;

        public GetWpcAuthCertificateCallback(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }
    }

    public class GetWpcAuthChallengeResponse implements Runnable {
        public final ResultReceiver mResultReceiver;
        public final byte mSlotNum;
        public final byte[] mWpcNonce;

        public GetWpcAuthChallengeResponse(ResultReceiver resultReceiver, byte b, byte[] bArr) {
            this.mResultReceiver = resultReceiver;
            this.mSlotNum = b;
            this.mWpcNonce = bArr;
        }

        public final void run() {
            DockObserver dockObserver = DockObserver.this;
            WirelessCharger wirelessCharger = dockObserver.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.getWpcAuthChallengeResponse(this.mSlotNum, this.mWpcNonce, new GetWpcAuthChallengeResponseCallback(this.mResultReceiver));
            }
        }
    }

    @VisibleForTesting
    public final class GetWpcAuthChallengeResponseCallback implements WirelessCharger.GetWpcAuthChallengeResponseCallback {
        public final ResultReceiver mResultReceiver;

        public GetWpcAuthChallengeResponseCallback(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }
    }

    public class GetWpcAuthDigests implements Runnable {
        public final ResultReceiver mResultReceiver;
        public final byte mSlotMask;

        public GetWpcAuthDigests(ResultReceiver resultReceiver, byte b) {
            this.mResultReceiver = resultReceiver;
            this.mSlotMask = b;
        }

        public final void run() {
            DockObserver dockObserver = DockObserver.this;
            WirelessCharger wirelessCharger = dockObserver.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.getWpcAuthDigests(this.mSlotMask, new GetWpcAuthDigestsCallback(this.mResultReceiver));
            }
        }
    }

    @VisibleForTesting
    public final class GetWpcAuthDigestsCallback implements WirelessCharger.GetWpcAuthDigestsCallback {
        public final ResultReceiver mResultReceiver;

        public GetWpcAuthDigestsCallback(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }
    }

    public class IsDockPresent implements Runnable {
        public final Context context;

        public IsDockPresent(Context context2) {
            this.context = context2;
        }

        public final void run() {
            DockObserver dockObserver = DockObserver.this;
            WirelessCharger wirelessCharger = dockObserver.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.asyncIsDockPresent(new IsDockPresentCallback(this.context));
            }
        }
    }

    @VisibleForTesting
    public final class IsDockPresentCallback implements WirelessCharger.IsDockPresentCallback {
        public final Context mContext;

        public IsDockPresentCallback(Context context) {
            this.mContext = context;
        }
    }

    @VisibleForTesting
    public final class KeyExchangeCallback implements WirelessCharger.KeyExchangeCallback {
        public final ResultReceiver mResultReceiver;

        public KeyExchangeCallback(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }

        public final void onCallback(int i, byte b, ArrayList<Byte> arrayList) {
            ExifInterface$$ExternalSyntheticOutline1.m14m("KE() Result: ", i, "DLObserver");
            Bundle bundle = null;
            if (i == 0) {
                Log.d("DLObserver", "KE() response: pk=" + arrayList);
                ResultReceiver resultReceiver = this.mResultReceiver;
                DockObserver dockObserver = DockObserver.this;
                String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
                Objects.requireNonNull(dockObserver);
                if (arrayList != null && !arrayList.isEmpty()) {
                    byte[] convertArrayListToPrimitiveArray = DockObserver.convertArrayListToPrimitiveArray(arrayList);
                    bundle = new Bundle();
                    bundle.putByte("dock_id", b);
                    bundle.putByteArray("dock_public_key", convertArrayListToPrimitiveArray);
                }
                resultReceiver.send(0, bundle);
                return;
            }
            this.mResultReceiver.send(1, (Bundle) null);
        }
    }

    public class KeyExchangeWithDock implements Runnable {
        public final byte[] publicKey;
        public final ResultReceiver resultReceiver;

        public KeyExchangeWithDock(ResultReceiver resultReceiver2, byte[] bArr) {
            this.publicKey = bArr;
            this.resultReceiver = resultReceiver2;
        }

        public final void run() {
            DockObserver dockObserver = DockObserver.this;
            WirelessCharger wirelessCharger = dockObserver.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.keyExchange(this.publicKey, new KeyExchangeCallback(this.resultReceiver));
            }
        }
    }

    public class SetFan implements Runnable {
        public final byte mFanId;
        public final byte mFanMode;
        public final int mFanRpm;

        public SetFan(byte b, byte b2, int i) {
            this.mFanId = b;
            this.mFanMode = b2;
            this.mFanRpm = i;
        }

        public final void run() {
            WirelessCharger wirelessCharger = DockObserver.this.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.setFan(this.mFanId, this.mFanMode, this.mFanRpm, new SetFanCallback());
            }
        }
    }

    @VisibleForTesting
    public static final class SetFanCallback implements WirelessCharger.SetFanCallback {
    }

    public class SetFeatures implements Runnable {
        public final long mChargerId;
        public final long mFeature;
        public final ResultReceiver mResultReceiver;

        public SetFeatures(ResultReceiver resultReceiver, long j, long j2) {
            this.mResultReceiver = resultReceiver;
            this.mChargerId = j;
            this.mFeature = j2;
        }

        public final void run() {
            WirelessCharger wirelessCharger = DockObserver.this.mWirelessCharger;
            if (wirelessCharger != null) {
                wirelessCharger.setFeatures(this.mChargerId, this.mFeature, new CaptionsToggleImageButton$$ExternalSyntheticLambda0(this));
            }
        }
    }

    public final void stopDreamlinerService(Context context) {
        notifyForceEnabledAmbientDisplay(false);
        onDockStateChanged(0);
        try {
            if (this.mDreamlinerServiceConn != null) {
                Class<DockGestureController> cls = DockGestureController.class;
                if (assertNotNull(this.mDockGestureController)) {
                    this.mConfigurationController.removeCallback(this.mDockGestureController);
                    this.mDockGestureController.stopMonitoring();
                    this.mDockGestureController = null;
                }
                this.mUserTracker.stopTracking();
                DreamlinerBroadcastReceiver dreamlinerBroadcastReceiver = this.mDreamlinerReceiver;
                Objects.requireNonNull(dreamlinerBroadcastReceiver);
                if (dreamlinerBroadcastReceiver.mListening) {
                    context.unregisterReceiver(dreamlinerBroadcastReceiver);
                    dreamlinerBroadcastReceiver.mListening = false;
                }
                context.unbindService(this.mDreamlinerServiceConn);
                this.mDreamlinerServiceConn = null;
            }
        } catch (IllegalArgumentException e) {
            Log.e("DLObserver", e.getMessage(), e);
        }
    }

    @VisibleForTesting
    public final void updateCurrentDockingStatus(Context context) {
        notifyForceEnabledAmbientDisplay(false);
        checkIsDockPresentIfNeeded(context);
    }

    @VisibleForTesting
    public static final class GetFanInformationCallback implements WirelessCharger.GetFanInformationCallback {
        public final byte mFanId;
        public final ResultReceiver mResultReceiver;

        public GetFanInformationCallback(byte b, ResultReceiver resultReceiver) {
            this.mFanId = b;
            this.mResultReceiver = resultReceiver;
        }
    }

    @VisibleForTesting
    public static final class GetFanSimpleInformationCallback implements WirelessCharger.GetFanSimpleInformationCallback {
        public final byte mFanId;
        public final ResultReceiver mResultReceiver;

        public GetFanSimpleInformationCallback(byte b, ResultReceiver resultReceiver) {
            this.mFanId = b;
            this.mResultReceiver = resultReceiver;
        }
    }

    @VisibleForTesting
    public final class GetInformationCallback implements WirelessCharger.GetInformationCallback {
        public final ResultReceiver mResultReceiver;

        public GetInformationCallback(ResultReceiver resultReceiver) {
            this.mResultReceiver = resultReceiver;
        }
    }

    public static boolean assertNotNull(DockGestureController dockGestureController) {
        if (dockGestureController != null) {
            return true;
        }
        Log.w("DLObserver", "DockGestureController is null");
        return false;
    }

    public static byte[] convertArrayListToPrimitiveArray(ArrayList arrayList) {
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        int size = arrayList.size();
        byte[] bArr = new byte[size];
        for (int i = 0; i < size; i++) {
            bArr[i] = ((Byte) arrayList.get(i)).byteValue();
        }
        return bArr;
    }

    public static void notifyForceEnabledAmbientDisplay(boolean z) {
        IDreamManager asInterface = IDreamManager.Stub.asInterface(ServiceManager.checkService("dreams"));
        if (asInterface != null) {
            try {
                asInterface.forceAmbientDisplayEnabled(z);
            } catch (RemoteException unused) {
            }
        } else {
            Log.e("DLObserver", "DreamManager not found");
        }
    }

    public static void runOnBackgroundThread(Runnable runnable) {
        if (mSingleThreadExecutor == null) {
            mSingleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        mSingleThreadExecutor.execute(runnable);
    }

    public final void addAlignmentStateListener(KeyguardIndicationController$$ExternalSyntheticLambda0 keyguardIndicationController$$ExternalSyntheticLambda0) {
        Log.d("DLObserver", "add alignment listener: " + keyguardIndicationController$$ExternalSyntheticLambda0);
        if (!this.mAlignmentStateListeners.contains(keyguardIndicationController$$ExternalSyntheticLambda0)) {
            this.mAlignmentStateListeners.add(keyguardIndicationController$$ExternalSyntheticLambda0);
        }
    }

    public final void addListener(DockManager.DockEventListener dockEventListener) {
        Log.d("DLObserver", "add listener: " + dockEventListener);
        if (!this.mClients.contains(dockEventListener)) {
            this.mClients.add(dockEventListener);
        }
        this.mMainExecutor.execute(new BubblesManager$5$$ExternalSyntheticLambda3(this, dockEventListener, 3));
    }

    public final void checkIsDockPresentIfNeeded(Context context) {
        if (this.mWirelessCharger != null) {
            Intent registerReceiver = context.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            boolean z = false;
            if (registerReceiver == null) {
                Log.i("DLObserver", "null battery intent when checking plugged status");
            } else {
                int intExtra = registerReceiver.getIntExtra("plugged", -1);
                ExifInterface$$ExternalSyntheticOutline1.m14m("plugged=", intExtra, "DLObserver");
                if (intExtra == 4) {
                    z = true;
                }
            }
            if (z) {
                runOnBackgroundThread(new IsDockPresent(context));
            }
        }
    }

    public final void dispatchDockEvent(DockManager.DockEventListener dockEventListener) {
        KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("onDockEvent mDockState = "), this.mDockState, "DLObserver");
        dockEventListener.onEvent(this.mDockState);
    }

    public final boolean isDocked() {
        int i = this.mDockState;
        if (i == 1 || i == 2) {
            return true;
        }
        return false;
    }

    public final boolean isHidden() {
        if (this.mDockState == 2) {
            return true;
        }
        return false;
    }

    public final void notifyDreamlinerLatestFanLevel() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("notify l=");
        m.append(this.mFanLevel);
        m.append(", isDocked=");
        m.append(isDocked());
        Log.d("DLObserver", m.toString());
        if (isDocked()) {
            this.mContext.sendBroadcastAsUser(new Intent("com.google.android.systemui.dreamliner.ACTION_UPDATE_FAN_LEVEL").putExtra("fan_level", this.mFanLevel).addFlags(1073741824), UserHandle.CURRENT);
        }
    }

    public final void onDockStateChanged(int i) {
        if (this.mDockState != i) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("dock state changed from ");
            m.append(this.mDockState);
            m.append(" to ");
            m.append(i);
            Log.d("DLObserver", m.toString());
            int i2 = this.mDockState;
            this.mDockState = i;
            for (int i3 = 0; i3 < this.mClients.size(); i3++) {
                dispatchDockEvent((DockManager.DockEventListener) this.mClients.get(i3));
            }
            DockIndicationController dockIndicationController = this.mIndicationController;
            if (dockIndicationController != null) {
                boolean isDocked = isDocked();
                dockIndicationController.mDocking = isDocked;
                if (!isDocked) {
                    dockIndicationController.mTopIconShowing = false;
                    dockIndicationController.mShowPromo = false;
                }
                dockIndicationController.updateVisibility();
                dockIndicationController.updateLiveRegionIfNeeded();
            }
            if (i2 == 0 && i == 1) {
                notifyDreamlinerAlignStateChanged(this.mLastAlignState);
                notifyDreamlinerLatestFanLevel();
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onReceive(); ");
            m.append(intent.getAction());
            Log.d("DLObserver", m.toString());
            String action = intent.getAction();
            Objects.requireNonNull(action);
            char c = 65535;
            switch (action.hashCode()) {
                case -1886648615:
                    if (action.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
                        c = 0;
                        break;
                    }
                    break;
                case -1863595884:
                    if (action.equals("com.google.android.systemui.dreamliner.ACTION_SET_FEATURES")) {
                        c = 1;
                        break;
                    }
                    break;
                case 798292259:
                    if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                        c = 2;
                        break;
                    }
                    break;
                case 882378784:
                    if (action.equals("com.google.android.systemui.dreamliner.ACTION_GET_FEATURES")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1019184907:
                    if (action.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1318602046:
                    if (action.equals(ACTION_REBIND_DOCK_SERVICE)) {
                        c = 5;
                        break;
                    }
                    break;
            }
            switch (c) {
                case 0:
                    stopDreamlinerService(context);
                    sIsDockingUiShowing = false;
                    return;
                case 1:
                    long longExtra = intent.getLongExtra("charger_id", -1);
                    long longExtra2 = intent.getLongExtra("charger_feature", -1);
                    ResultReceiver resultReceiver = (ResultReceiver) intent.getParcelableExtra("android.intent.extra.RESULT_RECEIVER");
                    Log.d("DLObserver", "sF, id=" + longExtra + ", feature=" + longExtra2);
                    if (resultReceiver == null) {
                        return;
                    }
                    if (longExtra == -1 || longExtra2 == -1) {
                        resultReceiver.send(1, (Bundle) null);
                        return;
                    } else {
                        runOnBackgroundThread(new SetFeatures(resultReceiver, longExtra, longExtra2));
                        return;
                    }
                case 2:
                case 5:
                    updateCurrentDockingStatus(context);
                    return;
                case 3:
                    long longExtra3 = intent.getLongExtra("charger_id", -1);
                    Log.d("DLObserver", "gF, id=" + longExtra3);
                    ResultReceiver resultReceiver2 = (ResultReceiver) intent.getParcelableExtra("android.intent.extra.RESULT_RECEIVER");
                    if (resultReceiver2 == null) {
                        return;
                    }
                    if (longExtra3 == -1) {
                        resultReceiver2.send(1, (Bundle) null);
                        return;
                    } else {
                        runOnBackgroundThread(new GetFeatures(resultReceiver2, longExtra3));
                        return;
                    }
                case 4:
                    checkIsDockPresentIfNeeded(context);
                    return;
                default:
                    return;
            }
        }
    }

    public final void refreshFanLevel(Runnable runnable) {
        Log.d("DLObserver", "command=2");
        runOnBackgroundThread(new ExecutorUtils$$ExternalSyntheticLambda1(this, runnable, 6));
    }

    public final void removeListener(DockManager.DockEventListener dockEventListener) {
        Log.d("DLObserver", "remove listener: " + dockEventListener);
        this.mClients.remove(dockEventListener);
    }

    public final void runPhotoAction() {
        boolean z;
        if (this.mLastAlignState == 0 && this.mPhotoAction != null) {
            DockIndicationController dockIndicationController = this.mIndicationController;
            Objects.requireNonNull(dockIndicationController);
            if (dockIndicationController.mDockPromo.getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                this.mMainExecutor.executeDelayed(this.mPhotoAction, Duration.ofSeconds(3).toMillis());
            }
        }
    }

    public DockObserver(final Context context, WirelessCharger wirelessCharger, BroadcastDispatcher broadcastDispatcher, StatusBarStateController statusBarStateController, NotificationInterruptStateProvider notificationInterruptStateProvider, ConfigurationController configurationController, DelayableExecutor delayableExecutor) {
        C22312 r0 = new NotificationInterruptSuppressor() {
            public final String getName() {
                return "DLObserver";
            }

            public final boolean suppressInterruptions() {
                return DockObserver.sIsDockingUiShowing;
            }
        };
        this.mInterruptSuppressor = r0;
        this.mMainExecutor = delayableExecutor;
        this.mContext = context;
        this.mClients = new ArrayList();
        this.mAlignmentStateListeners = new ArrayList();
        this.mUserTracker = new CurrentUserTracker(broadcastDispatcher) {
            public final void onUserSwitched(int i) {
                DockObserver dockObserver = DockObserver.this;
                Context context = context;
                String str = DockObserver.ACTION_START_DREAMLINER_CONTROL_SERVICE;
                dockObserver.stopDreamlinerService(context);
                DockObserver.this.updateCurrentDockingStatus(context);
            }
        };
        this.mWirelessCharger = wirelessCharger;
        if (wirelessCharger == null) {
            Log.i("DLObserver", "wireless charger is null, check dock component.");
        }
        this.mStatusBarStateController = statusBarStateController;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction(ACTION_REBIND_DOCK_SERVICE);
        intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_GET_FEATURES");
        intentFilter.addAction("com.google.android.systemui.dreamliner.ACTION_SET_FEATURES");
        intentFilter.setPriority(1000);
        context.registerReceiver(this, intentFilter, PERMISSION_WIRELESS_CHARGER_STATUS, (Handler) null, 2);
        this.mDockAlignmentController = new DockAlignmentController(wirelessCharger, this);
        notificationInterruptStateProvider.addSuppressor(r0);
        this.mConfigurationController = configurationController;
        refreshFanLevel((Runnable) null);
    }

    public final void notifyDreamlinerAlignStateChanged(int i) {
        if (isDocked()) {
            this.mContext.sendBroadcastAsUser(new Intent(ACTION_ALIGN_STATE_CHANGE).putExtra(EXTRA_ALIGN_STATE, i).addFlags(1073741824), UserHandle.CURRENT);
        }
    }
}
