package com.android.systemui.privacy;

import android.content.IntentFilter;
import android.provider.DeviceConfig;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController implements Dumpable {
    public static final Companion Companion = new Companion(0);
    public static final int[] OPS;
    public static final int[] OPS_LOCATION;
    public boolean allIndicatorsAvailable;
    public final AppOpsController appOpsController;
    public final DelayableExecutor bgExecutor;
    public final ArrayList callbacks = new ArrayList();

    /* renamed from: cb */
    public final PrivacyItemController$cb$1 f67cb;
    public List<Integer> currentUserIds;
    public Runnable holdingRunnableCanceler;
    public final MyExecutor internalUiExecutor;
    public boolean listening;
    public boolean locationAvailable;
    public final PrivacyLogger logger;
    public boolean micCameraAvailable;
    public final PrivacyItemController$notifyChanges$1 notifyChanges;
    public List<PrivacyItem> privacyList;
    public final SystemClock systemClock;
    public final PrivacyItemController$updateListAndNotifyChanges$1 updateListAndNotifyChanges;
    public final UserTracker userTracker;
    public PrivacyItemController$userTrackerCallback$1 userTrackerCallback;

    /* compiled from: PrivacyItemController.kt */
    public interface Callback {
        void onFlagLocationChanged(boolean z) {
        }

        void onFlagMicCameraChanged(boolean z) {
        }

        void onPrivacyItemsChanged(List<PrivacyItem> list);
    }

    @VisibleForTesting
    /* compiled from: PrivacyItemController.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(int i) {
            this();
        }

        @VisibleForTesting
        public static /* synthetic */ void getTIME_TO_HOLD_INDICATORS$annotations() {
        }
    }

    /* compiled from: PrivacyItemController.kt */
    public final class MyExecutor implements Executor {
        public final DelayableExecutor delegate;
        public Runnable listeningCanceller;

        public MyExecutor(DelayableExecutor delayableExecutor) {
            this.delegate = delayableExecutor;
        }

        public final void execute(Runnable runnable) {
            this.delegate.execute(runnable);
        }

        public final void updateListeningState() {
            Runnable runnable = this.listeningCanceller;
            if (runnable != null) {
                runnable.run();
            }
            this.listeningCanceller = this.delegate.executeDelayed(new PrivacyItemController$MyExecutor$updateListeningState$1(PrivacyItemController.this), 0);
        }
    }

    /* compiled from: PrivacyItemController.kt */
    public static final class NotifyChangesToCallback implements Runnable {
        public final Callback callback;
        public final List<PrivacyItem> list;

        public final void run() {
            Callback callback2 = this.callback;
            if (callback2 != null) {
                callback2.onPrivacyItemsChanged(this.list);
            }
        }

        public NotifyChangesToCallback(Callback callback2, List<PrivacyItem> list2) {
            this.callback = callback2;
            this.list = list2;
        }
    }

    @VisibleForTesting
    /* renamed from: getPrivacyList$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m62xaaa48fd6() {
    }

    @VisibleForTesting
    /* renamed from: getUserTrackerCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations */
    public static /* synthetic */ void m63x9bde9e2() {
    }

    static {
        int[] iArr = {0, 1};
        OPS_LOCATION = iArr;
        int[] copyOf = Arrays.copyOf(new int[]{26, 101, 27, 100}, 6);
        System.arraycopy(iArr, 0, copyOf, 4, 2);
        OPS = copyOf;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_AVAILABLE");
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_UNAVAILABLE");
    }

    public final void addCallback(Callback callback) {
        List<T> list;
        WeakReference weakReference = new WeakReference(callback);
        this.callbacks.add(weakReference);
        if ((!this.callbacks.isEmpty()) && !this.listening) {
            this.internalUiExecutor.updateListeningState();
        } else if (this.listening) {
            MyExecutor myExecutor = this.internalUiExecutor;
            Callback callback2 = (Callback) weakReference.get();
            synchronized (this) {
                list = CollectionsKt___CollectionsKt.toList(this.privacyList);
            }
            myExecutor.execute(new NotifyChangesToCallback(callback2, list));
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        List<T> list;
        printWriter.println("PrivacyItemController state:");
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.listening, "  Listening: ", printWriter);
        printWriter.println(Intrinsics.stringPlus("  Current user ids: ", this.currentUserIds));
        printWriter.println("  Privacy Items:");
        synchronized (this) {
            list = CollectionsKt___CollectionsKt.toList(this.privacyList);
        }
        for (T privacyItem : list) {
            printWriter.print("    ");
            printWriter.println(privacyItem.toString());
        }
        printWriter.println("  Callbacks:");
        Iterator it = this.callbacks.iterator();
        while (it.hasNext()) {
            Callback callback = (Callback) ((WeakReference) it.next()).get();
            if (callback != null) {
                printWriter.print("    ");
                printWriter.println(callback.toString());
            }
        }
    }

    public final void removeCallback(Callback callback) {
        this.callbacks.removeIf(new PrivacyItemController$removeCallback$1(new WeakReference(callback)));
        if (this.callbacks.isEmpty()) {
            this.internalUiExecutor.updateListeningState();
        }
    }

    public final void update(boolean z) {
        this.bgExecutor.execute(new PrivacyItemController$update$1(z, this));
    }

    public PrivacyItemController(AppOpsController appOpsController2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, DeviceConfigProxy deviceConfigProxy, UserTracker userTracker2, PrivacyLogger privacyLogger, SystemClock systemClock2, DumpManager dumpManager) {
        this.appOpsController = appOpsController2;
        this.bgExecutor = delayableExecutor2;
        this.userTracker = userTracker2;
        this.logger = privacyLogger;
        this.systemClock = systemClock2;
        EmptyList emptyList = EmptyList.INSTANCE;
        this.privacyList = emptyList;
        this.currentUserIds = emptyList;
        this.internalUiExecutor = new MyExecutor(delayableExecutor);
        this.notifyChanges = new PrivacyItemController$notifyChanges$1(this);
        this.updateListAndNotifyChanges = new PrivacyItemController$updateListAndNotifyChanges$1(this, delayableExecutor);
        Objects.requireNonNull(deviceConfigProxy);
        boolean z = true;
        this.micCameraAvailable = DeviceConfig.getBoolean("privacy", "camera_mic_icons_enabled", true);
        boolean z2 = DeviceConfig.getBoolean("privacy", "location_indicators_enabled", false);
        this.locationAvailable = z2;
        this.allIndicatorsAvailable = (!this.micCameraAvailable || !z2) ? false : z;
        PrivacyItemController$devicePropertiesChangedListener$1 privacyItemController$devicePropertiesChangedListener$1 = new PrivacyItemController$devicePropertiesChangedListener$1(this);
        this.f67cb = new PrivacyItemController$cb$1(this);
        this.userTrackerCallback = new PrivacyItemController$userTrackerCallback$1(this);
        DeviceConfig.addOnPropertiesChangedListener("privacy", delayableExecutor, privacyItemController$devicePropertiesChangedListener$1);
        dumpManager.registerDumpable("PrivacyItemController", this);
    }
}
