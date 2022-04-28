package com.android.systemui.appops;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.permission.PermissionManager;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.PrivacyItemController$cb$1;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class AppOpsControllerImpl extends BroadcastReceiver implements AppOpsController, AppOpsManager.OnOpActiveChangedListener, AppOpsManager.OnOpNotedListener, IndividualSensorPrivacyController.Callback, Dumpable {
    public static final int[] OPS = {42, 26, 101, 24, 27, 100, 0, 1};
    @GuardedBy({"mActiveItems"})
    public final ArrayList mActiveItems = new ArrayList();
    public final AppOpsManager mAppOps;
    public final AudioManager mAudioManager;
    public C06701 mAudioRecordingCallback = new AudioManager.AudioRecordingCallback() {
        public final void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
            synchronized (AppOpsControllerImpl.this.mActiveItems) {
                AppOpsControllerImpl.this.mRecordingsByUid.clear();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    AudioRecordingConfiguration audioRecordingConfiguration = list.get(i);
                    ArrayList arrayList = AppOpsControllerImpl.this.mRecordingsByUid.get(audioRecordingConfiguration.getClientUid());
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        AppOpsControllerImpl.this.mRecordingsByUid.put(audioRecordingConfiguration.getClientUid(), arrayList);
                    }
                    arrayList.add(audioRecordingConfiguration);
                }
            }
            AppOpsControllerImpl.this.updateSensorDisabledStatus();
        }
    };
    public C0671H mBGHandler;
    public final ArrayList mCallbacks = new ArrayList();
    public final SparseArray<Set<AppOpsController.Callback>> mCallbacksByCode = new SparseArray<>();
    public boolean mCameraDisabled;
    public final SystemClock mClock;
    public final Context mContext;
    public final BroadcastDispatcher mDispatcher;
    public boolean mListening;
    public boolean mMicMuted;
    @GuardedBy({"mNotedItems"})
    public final ArrayList mNotedItems = new ArrayList();
    @GuardedBy({"mActiveItems"})
    public final SparseArray<ArrayList<AudioRecordingConfiguration>> mRecordingsByUid = new SparseArray<>();
    public final IndividualSensorPrivacyController mSensorPrivacyController;

    /* renamed from: com.android.systemui.appops.AppOpsControllerImpl$H */
    public class C0671H extends Handler {
        public C0671H(Looper looper) {
            super(looper);
        }
    }

    public final void addCallback(int[] iArr, AppOpsController.Callback callback) {
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (this.mCallbacksByCode.contains(iArr[i])) {
                this.mCallbacksByCode.get(iArr[i]).add(callback);
                z = true;
            }
        }
        if (z) {
            this.mCallbacks.add(callback);
        }
        if (!this.mCallbacks.isEmpty()) {
            setListening(true);
        }
    }

    public final ArrayList getActiveAppOps() {
        return getActiveAppOps(false);
    }

    public final void onOpActiveChanged(String str, int i, String str2, boolean z) {
        onOpActiveChanged(str, i, str2, (String) null, z, 0, -1);
    }

    public final void removeCallback(int[] iArr, PrivacyItemController$cb$1 privacyItemController$cb$1) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (this.mCallbacksByCode.contains(iArr[i])) {
                this.mCallbacksByCode.get(iArr[i]).remove(privacyItemController$cb$1);
            }
        }
        this.mCallbacks.remove(privacyItemController$cb$1);
        if (this.mCallbacks.isEmpty()) {
            setListening(false);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "AppOpsController state:", "  Listening: ");
        m.append(this.mListening);
        printWriter.println(m.toString());
        printWriter.println("  Active Items:");
        for (int i = 0; i < this.mActiveItems.size(); i++) {
            printWriter.print("    ");
            printWriter.println(((AppOpItem) this.mActiveItems.get(i)).toString());
        }
        printWriter.println("  Noted Items:");
        for (int i2 = 0; i2 < this.mNotedItems.size(); i2++) {
            printWriter.print("    ");
            printWriter.println(((AppOpItem) this.mNotedItems.get(i2)).toString());
        }
    }

    public final ArrayList getActiveAppOps(boolean z) {
        int i;
        Assert.isNotMainThread();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mActiveItems) {
            try {
                int size = this.mActiveItems.size();
                for (int i2 = 0; i2 < size; i2++) {
                    AppOpItem appOpItem = (AppOpItem) this.mActiveItems.get(i2);
                    Objects.requireNonNull(appOpItem);
                    if (PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, appOpItem.mPackageName) && (z || !appOpItem.mIsDisabled)) {
                        arrayList.add(appOpItem);
                    }
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        synchronized (this.mNotedItems) {
            int size2 = this.mNotedItems.size();
            for (i = 0; i < size2; i++) {
                AppOpItem appOpItem2 = (AppOpItem) this.mNotedItems.get(i);
                Objects.requireNonNull(appOpItem2);
                if (PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, appOpItem2.mPackageName)) {
                    arrayList.add(appOpItem2);
                }
            }
        }
        return arrayList;
    }

    public final boolean isAnyRecordingPausedLocked(int i) {
        if (this.mMicMuted) {
            return true;
        }
        List list = this.mRecordingsByUid.get(i);
        if (list == null) {
            return false;
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (((AudioRecordingConfiguration) list.get(i2)).isClientSilenced()) {
                return true;
            }
        }
        return false;
    }

    public final void notifySuscribers(int i, int i2, String str, boolean z) {
        this.mBGHandler.post(new AppOpsControllerImpl$$ExternalSyntheticLambda0(this, i, i2, str, z));
    }

    public final void notifySuscribersWorker(int i, int i2, String str, boolean z) {
        if (this.mCallbacksByCode.contains(i) && PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, str)) {
            for (AppOpsController.Callback onActiveStateChanged : this.mCallbacksByCode.get(i)) {
                onActiveStateChanged.onActiveStateChanged(i, i2, str, z);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0062  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onOpActiveChanged(java.lang.String r8, int r9, java.lang.String r10, java.lang.String r11, boolean r12, int r13, int r14) {
        /*
            r7 = this;
            int r8 = android.app.AppOpsManager.strOpToOp(r8)
            r11 = -1
            if (r14 == r11) goto L_0x0012
            if (r13 == 0) goto L_0x0012
            r11 = r13 & 1
            if (r11 != 0) goto L_0x0012
            r11 = r13 & 8
            if (r11 != 0) goto L_0x0012
            return
        L_0x0012:
            java.util.ArrayList r11 = r7.mActiveItems
            monitor-enter(r11)
            java.util.ArrayList r13 = r7.mActiveItems     // Catch:{ all -> 0x008c }
            com.android.systemui.appops.AppOpItem r13 = getAppOpItemLocked(r13, r8, r9, r10)     // Catch:{ all -> 0x008c }
            r14 = 0
            r6 = 1
            if (r13 != 0) goto L_0x0065
            if (r12 == 0) goto L_0x0065
            com.android.systemui.appops.AppOpItem r13 = new com.android.systemui.appops.AppOpItem     // Catch:{ all -> 0x008c }
            com.android.systemui.util.time.SystemClock r0 = r7.mClock     // Catch:{ all -> 0x008c }
            long r4 = r0.elapsedRealtime()     // Catch:{ all -> 0x008c }
            r0 = r13
            r1 = r8
            r2 = r9
            r3 = r10
            r0.<init>(r1, r2, r3, r4)     // Catch:{ all -> 0x008c }
            r0 = 27
            if (r8 == r0) goto L_0x003b
            r0 = 100
            if (r8 != r0) goto L_0x0039
            goto L_0x003b
        L_0x0039:
            r0 = r14
            goto L_0x003c
        L_0x003b:
            r0 = r6
        L_0x003c:
            if (r0 == 0) goto L_0x0045
            boolean r0 = r7.isAnyRecordingPausedLocked(r9)     // Catch:{ all -> 0x008c }
            r13.mIsDisabled = r0     // Catch:{ all -> 0x008c }
            goto L_0x0057
        L_0x0045:
            r0 = 26
            if (r8 == r0) goto L_0x0050
            r0 = 101(0x65, float:1.42E-43)
            if (r8 != r0) goto L_0x004e
            goto L_0x0050
        L_0x004e:
            r0 = r14
            goto L_0x0051
        L_0x0050:
            r0 = r6
        L_0x0051:
            if (r0 == 0) goto L_0x0057
            boolean r0 = r7.mCameraDisabled     // Catch:{ all -> 0x008c }
            r13.mIsDisabled = r0     // Catch:{ all -> 0x008c }
        L_0x0057:
            java.util.ArrayList r0 = r7.mActiveItems     // Catch:{ all -> 0x008c }
            r0.add(r13)     // Catch:{ all -> 0x008c }
            boolean r13 = r13.mIsDisabled     // Catch:{ all -> 0x008c }
            if (r13 != 0) goto L_0x0062
            r13 = r6
            goto L_0x0063
        L_0x0062:
            r13 = r14
        L_0x0063:
            monitor-exit(r11)     // Catch:{ all -> 0x008c }
            goto L_0x0073
        L_0x0065:
            if (r13 == 0) goto L_0x0071
            if (r12 != 0) goto L_0x0071
            java.util.ArrayList r0 = r7.mActiveItems     // Catch:{ all -> 0x008c }
            r0.remove(r13)     // Catch:{ all -> 0x008c }
            monitor-exit(r11)     // Catch:{ all -> 0x008c }
            r13 = r6
            goto L_0x0073
        L_0x0071:
            monitor-exit(r11)     // Catch:{ all -> 0x008c }
            r13 = r14
        L_0x0073:
            if (r13 != 0) goto L_0x0076
            return
        L_0x0076:
            java.util.ArrayList r13 = r7.mNotedItems
            monitor-enter(r13)
            java.util.ArrayList r11 = r7.mNotedItems     // Catch:{ all -> 0x0089 }
            com.android.systemui.appops.AppOpItem r11 = getAppOpItemLocked(r11, r8, r9, r10)     // Catch:{ all -> 0x0089 }
            if (r11 == 0) goto L_0x0082
            r14 = r6
        L_0x0082:
            monitor-exit(r13)     // Catch:{ all -> 0x0089 }
            if (r14 != 0) goto L_0x0088
            r7.notifySuscribers(r8, r9, r10, r12)
        L_0x0088:
            return
        L_0x0089:
            r7 = move-exception
            monitor-exit(r13)     // Catch:{ all -> 0x0089 }
            throw r7
        L_0x008c:
            r7 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x008c }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.appops.AppOpsControllerImpl.onOpActiveChanged(java.lang.String, int, java.lang.String, java.lang.String, boolean, int, int):void");
    }

    public final void onOpNoted(int i, int i2, String str, String str2, int i3, int i4) {
        AppOpItem appOpItemLocked;
        boolean z;
        boolean z2;
        if (i4 == 0) {
            synchronized (this.mNotedItems) {
                appOpItemLocked = getAppOpItemLocked(this.mNotedItems, i, i2, str);
                z = false;
                if (appOpItemLocked == null) {
                    appOpItemLocked = new AppOpItem(i, i2, str, this.mClock.elapsedRealtime());
                    this.mNotedItems.add(appOpItemLocked);
                    z2 = true;
                } else {
                    z2 = false;
                }
            }
            this.mBGHandler.removeCallbacksAndMessages(appOpItemLocked);
            C0671H h = this.mBGHandler;
            Objects.requireNonNull(h);
            h.removeCallbacksAndMessages(appOpItemLocked);
            h.postDelayed(new Runnable(appOpItemLocked) {
                public final /* synthetic */ AppOpItem val$item;

                {
                    this.val$item = r2;
                }

                /* JADX WARNING: Code restructure failed: missing block: B:10:0x0033, code lost:
                    monitor-enter(r4);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:13:0x003b, code lost:
                    if (com.android.systemui.appops.AppOpsControllerImpl.getAppOpItemLocked(r0.mActiveItems, r1, r2, r6) == null) goto L_0x003f;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:14:0x003d, code lost:
                    r3 = true;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:15:0x003f, code lost:
                    r3 = false;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:16:0x0040, code lost:
                    monitor-exit(r4);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
                    if (r3 != false) goto L_?;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:18:0x0043, code lost:
                    r0.notifySuscribersWorker(r1, r2, r6, false);
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
                    return;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:9:0x0031, code lost:
                    r4 = r0.mActiveItems;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public final void run() {
                    /*
                        r6 = this;
                        com.android.systemui.appops.AppOpsControllerImpl$H r0 = com.android.systemui.appops.AppOpsControllerImpl.C0671H.this
                        com.android.systemui.appops.AppOpsControllerImpl r0 = com.android.systemui.appops.AppOpsControllerImpl.this
                        com.android.systemui.appops.AppOpItem r1 = r6.val$item
                        java.util.Objects.requireNonNull(r1)
                        int r1 = r1.mCode
                        com.android.systemui.appops.AppOpItem r2 = r6.val$item
                        java.util.Objects.requireNonNull(r2)
                        int r2 = r2.mUid
                        com.android.systemui.appops.AppOpItem r6 = r6.val$item
                        java.util.Objects.requireNonNull(r6)
                        java.lang.String r6 = r6.mPackageName
                        int[] r3 = com.android.systemui.appops.AppOpsControllerImpl.OPS
                        java.util.Objects.requireNonNull(r0)
                        java.util.ArrayList r3 = r0.mNotedItems
                        monitor-enter(r3)
                        java.util.ArrayList r4 = r0.mNotedItems     // Catch:{ all -> 0x004a }
                        com.android.systemui.appops.AppOpItem r4 = com.android.systemui.appops.AppOpsControllerImpl.getAppOpItemLocked(r4, r1, r2, r6)     // Catch:{ all -> 0x004a }
                        if (r4 != 0) goto L_0x002b
                        monitor-exit(r3)     // Catch:{ all -> 0x004a }
                        goto L_0x0046
                    L_0x002b:
                        java.util.ArrayList r5 = r0.mNotedItems     // Catch:{ all -> 0x004a }
                        r5.remove(r4)     // Catch:{ all -> 0x004a }
                        monitor-exit(r3)     // Catch:{ all -> 0x004a }
                        java.util.ArrayList r4 = r0.mActiveItems
                        monitor-enter(r4)
                        java.util.ArrayList r3 = r0.mActiveItems     // Catch:{ all -> 0x0047 }
                        com.android.systemui.appops.AppOpItem r3 = com.android.systemui.appops.AppOpsControllerImpl.getAppOpItemLocked(r3, r1, r2, r6)     // Catch:{ all -> 0x0047 }
                        r5 = 0
                        if (r3 == 0) goto L_0x003f
                        r3 = 1
                        goto L_0x0040
                    L_0x003f:
                        r3 = r5
                    L_0x0040:
                        monitor-exit(r4)     // Catch:{ all -> 0x0047 }
                        if (r3 != 0) goto L_0x0046
                        r0.notifySuscribersWorker(r1, r2, r6, r5)
                    L_0x0046:
                        return
                    L_0x0047:
                        r6 = move-exception
                        monitor-exit(r4)     // Catch:{ all -> 0x0047 }
                        throw r6
                    L_0x004a:
                        r6 = move-exception
                        monitor-exit(r3)     // Catch:{ all -> 0x004a }
                        throw r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.appops.AppOpsControllerImpl.C0671H.C06721.run():void");
                }
            }, appOpItemLocked, 5000);
            if (z2) {
                synchronized (this.mActiveItems) {
                    if (getAppOpItemLocked(this.mActiveItems, i, i2, str) != null) {
                        z = true;
                    }
                }
                if (!z) {
                    notifySuscribers(i, i2, str, true);
                }
            }
        }
    }

    public final void onReceive(Context context, Intent intent) {
        boolean z = true;
        if (!this.mAudioManager.isMicrophoneMute() && !this.mSensorPrivacyController.isSensorBlocked(1)) {
            z = false;
        }
        this.mMicMuted = z;
        updateSensorDisabledStatus();
    }

    public final void onSensorBlockedChanged(int i, boolean z) {
        this.mBGHandler.post(new AppOpsControllerImpl$$ExternalSyntheticLambda1(this, i, z));
    }

    @VisibleForTesting
    public void setListening(boolean z) {
        this.mListening = z;
        if (z) {
            AppOpsManager appOpsManager = this.mAppOps;
            int[] iArr = OPS;
            appOpsManager.startWatchingActive(iArr, this);
            this.mAppOps.startWatchingNoted(iArr, this);
            this.mAudioManager.registerAudioRecordingCallback(this.mAudioRecordingCallback, this.mBGHandler);
            this.mSensorPrivacyController.addCallback(this);
            boolean z2 = true;
            if (!this.mAudioManager.isMicrophoneMute() && !this.mSensorPrivacyController.isSensorBlocked(1)) {
                z2 = false;
            }
            this.mMicMuted = z2;
            this.mCameraDisabled = this.mSensorPrivacyController.isSensorBlocked(2);
            this.mBGHandler.post(new CarrierTextManager$$ExternalSyntheticLambda0(this, 2));
            this.mDispatcher.registerReceiverWithHandler(this, new IntentFilter("android.media.action.MICROPHONE_MUTE_CHANGED"), this.mBGHandler);
            return;
        }
        this.mAppOps.stopWatchingActive(this);
        this.mAppOps.stopWatchingNoted(this);
        this.mAudioManager.unregisterAudioRecordingCallback(this.mAudioRecordingCallback);
        this.mSensorPrivacyController.removeCallback(this);
        this.mBGHandler.removeCallbacksAndMessages((Object) null);
        this.mDispatcher.unregisterReceiver(this);
        synchronized (this.mActiveItems) {
            this.mActiveItems.clear();
            this.mRecordingsByUid.clear();
        }
        synchronized (this.mNotedItems) {
            this.mNotedItems.clear();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0055 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateSensorDisabledStatus() {
        /*
            r9 = this;
            java.util.ArrayList r0 = r9.mActiveItems
            monitor-enter(r0)
            java.util.ArrayList r1 = r9.mActiveItems     // Catch:{ all -> 0x005a }
            int r1 = r1.size()     // Catch:{ all -> 0x005a }
            r2 = 0
            r3 = r2
        L_0x000b:
            if (r3 >= r1) goto L_0x0058
            java.util.ArrayList r4 = r9.mActiveItems     // Catch:{ all -> 0x005a }
            java.lang.Object r4 = r4.get(r3)     // Catch:{ all -> 0x005a }
            com.android.systemui.appops.AppOpItem r4 = (com.android.systemui.appops.AppOpItem) r4     // Catch:{ all -> 0x005a }
            java.util.Objects.requireNonNull(r4)     // Catch:{ all -> 0x005a }
            int r5 = r4.mCode     // Catch:{ all -> 0x005a }
            r6 = 27
            r7 = 1
            if (r5 == r6) goto L_0x0026
            r6 = 100
            if (r5 != r6) goto L_0x0024
            goto L_0x0026
        L_0x0024:
            r6 = r2
            goto L_0x0027
        L_0x0026:
            r6 = r7
        L_0x0027:
            if (r6 == 0) goto L_0x0030
            int r5 = r4.mUid     // Catch:{ all -> 0x005a }
            boolean r5 = r9.isAnyRecordingPausedLocked(r5)     // Catch:{ all -> 0x005a }
            goto L_0x0042
        L_0x0030:
            r6 = 26
            if (r5 == r6) goto L_0x003b
            r6 = 101(0x65, float:1.42E-43)
            if (r5 != r6) goto L_0x0039
            goto L_0x003b
        L_0x0039:
            r5 = r2
            goto L_0x003c
        L_0x003b:
            r5 = r7
        L_0x003c:
            if (r5 == 0) goto L_0x0041
            boolean r5 = r9.mCameraDisabled     // Catch:{ all -> 0x005a }
            goto L_0x0042
        L_0x0041:
            r5 = r2
        L_0x0042:
            boolean r6 = r4.mIsDisabled     // Catch:{ all -> 0x005a }
            if (r6 == r5) goto L_0x0055
            r4.mIsDisabled = r5     // Catch:{ all -> 0x005a }
            int r6 = r4.mCode     // Catch:{ all -> 0x005a }
            int r8 = r4.mUid     // Catch:{ all -> 0x005a }
            java.lang.String r4 = r4.mPackageName     // Catch:{ all -> 0x005a }
            if (r5 != 0) goto L_0x0051
            goto L_0x0052
        L_0x0051:
            r7 = r2
        L_0x0052:
            r9.notifySuscribers(r6, r8, r4, r7)     // Catch:{ all -> 0x005a }
        L_0x0055:
            int r3 = r3 + 1
            goto L_0x000b
        L_0x0058:
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            return
        L_0x005a:
            r9 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x005a }
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.appops.AppOpsControllerImpl.updateSensorDisabledStatus():void");
    }

    public AppOpsControllerImpl(Context context, Looper looper, DumpManager dumpManager, AudioManager audioManager, IndividualSensorPrivacyController individualSensorPrivacyController, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock) {
        this.mDispatcher = broadcastDispatcher;
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        this.mBGHandler = new C0671H(looper);
        boolean z = false;
        for (int i = 0; i < 8; i++) {
            this.mCallbacksByCode.put(OPS[i], new ArraySet());
        }
        this.mAudioManager = audioManager;
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mMicMuted = (audioManager.isMicrophoneMute() || individualSensorPrivacyController.isSensorBlocked(1)) ? true : z;
        this.mCameraDisabled = individualSensorPrivacyController.isSensorBlocked(2);
        this.mContext = context;
        this.mClock = systemClock;
        dumpManager.registerDumpable("AppOpsControllerImpl", this);
    }

    public static AppOpItem getAppOpItemLocked(ArrayList arrayList, int i, int i2, String str) {
        int size = arrayList.size();
        for (int i3 = 0; i3 < size; i3++) {
            AppOpItem appOpItem = (AppOpItem) arrayList.get(i3);
            Objects.requireNonNull(appOpItem);
            if (appOpItem.mCode == i && appOpItem.mUid == i2 && appOpItem.mPackageName.equals(str)) {
                return appOpItem;
            }
        }
        return null;
    }

    @VisibleForTesting
    public void setBGHandler(C0671H h) {
        this.mBGHandler = h;
    }

    public final boolean isMicMuted() {
        return this.mMicMuted;
    }
}
