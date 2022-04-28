package com.android.systemui.statusbar.policy;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.util.ArraySet;
import android.util.SparseBooleanArray;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: DeviceProvisionedControllerImpl.kt */
public final class DeviceProvisionedControllerImpl implements DeviceProvisionedController, DeviceProvisionedController.DeviceProvisionedListener, Dumpable {
    public final HandlerExecutor backgroundExecutor;
    public final AtomicBoolean deviceProvisioned = new AtomicBoolean(false);
    public final Uri deviceProvisionedUri;
    public final DumpManager dumpManager;
    public final GlobalSettings globalSettings;
    public final AtomicBoolean initted;
    public final ArraySet<DeviceProvisionedController.DeviceProvisionedListener> listeners;
    public final Object lock;
    public final Executor mainExecutor;
    public final DeviceProvisionedControllerImpl$observer$1 observer;
    public final SecureSettings secureSettings;
    public final DeviceProvisionedControllerImpl$userChangedCallback$1 userChangedCallback;
    public final SparseBooleanArray userSetupComplete;
    public final Uri userSetupUri;
    public final UserTracker userTracker;

    public final void updateValues(boolean z, int i) {
        boolean z2;
        boolean z3;
        boolean z4 = true;
        if (z) {
            AtomicBoolean atomicBoolean = this.deviceProvisioned;
            if (this.globalSettings.getInt("device_provisioned", 0) != 0) {
                z3 = true;
            } else {
                z3 = false;
            }
            atomicBoolean.set(z3);
        }
        synchronized (this.lock) {
            if (i == -1) {
                try {
                    int size = this.userSetupComplete.size();
                    int i2 = 0;
                    while (i2 < size) {
                        int i3 = i2 + 1;
                        int keyAt = this.userSetupComplete.keyAt(i2);
                        if (this.secureSettings.getIntForUser("user_setup_complete", 0, keyAt) != 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        this.userSetupComplete.put(keyAt, z2);
                        i2 = i3;
                    }
                } finally {
                }
            } else if (i != -2) {
                if (this.secureSettings.getIntForUser("user_setup_complete", 0, i) == 0) {
                    z4 = false;
                }
                this.userSetupComplete.put(i, z4);
            }
        }
    }

    public final void addCallback(Object obj) {
        DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener = (DeviceProvisionedController.DeviceProvisionedListener) obj;
        synchronized (this.lock) {
            this.listeners.add(deviceProvisionedListener);
        }
    }

    public final void dispatchChange(Function1<? super DeviceProvisionedController.DeviceProvisionedListener, Unit> function1) {
        ArrayList arrayList;
        synchronized (this.lock) {
            arrayList = new ArrayList(this.listeners);
        }
        this.mainExecutor.execute(new DeviceProvisionedControllerImpl$dispatchChange$1(arrayList, function1));
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("Device provisioned: ", Boolean.valueOf(this.deviceProvisioned.get())));
        synchronized (this.lock) {
            printWriter.println(Intrinsics.stringPlus("User setup complete: ", this.userSetupComplete));
            printWriter.println(Intrinsics.stringPlus("Listeners: ", this.listeners));
        }
    }

    public final void init() {
        if (this.initted.compareAndSet(false, true)) {
            this.dumpManager.registerDumpable(this);
            updateValues(true, -1);
            this.userTracker.addCallback(this.userChangedCallback, this.backgroundExecutor);
            this.globalSettings.registerContentObserver(this.deviceProvisionedUri, this.observer);
            this.secureSettings.registerContentObserverForUser(this.userSetupUri, (ContentObserver) this.observer, -1);
        }
    }

    public final boolean isCurrentUserSetup() {
        return isUserSetup(this.userTracker.getUserId());
    }

    public final boolean isDeviceProvisioned() {
        return this.deviceProvisioned.get();
    }

    public final boolean isUserSetup(int i) {
        int indexOfKey;
        synchronized (this.lock) {
            indexOfKey = this.userSetupComplete.indexOfKey(i);
        }
        boolean z = false;
        if (indexOfKey < 0) {
            if (this.secureSettings.getIntForUser("user_setup_complete", 0, i) != 0) {
                z = true;
            }
            synchronized (this.lock) {
                this.userSetupComplete.put(i, z);
            }
        } else {
            synchronized (this.lock) {
                z = this.userSetupComplete.get(i, false);
            }
        }
        return z;
    }

    public final void onDeviceProvisionedChanged() {
        dispatchChange(DeviceProvisionedControllerImpl$onDeviceProvisionedChanged$1.INSTANCE);
    }

    public final void onUserSetupChanged() {
        dispatchChange(DeviceProvisionedControllerImpl$onUserSetupChanged$1.INSTANCE);
    }

    public final void onUserSwitched() {
        dispatchChange(DeviceProvisionedControllerImpl$onUserSwitched$1.INSTANCE);
    }

    public final void removeCallback(Object obj) {
        DeviceProvisionedController.DeviceProvisionedListener deviceProvisionedListener = (DeviceProvisionedController.DeviceProvisionedListener) obj;
        synchronized (this.lock) {
            this.listeners.remove(deviceProvisionedListener);
        }
    }

    public DeviceProvisionedControllerImpl(SecureSettings secureSettings2, GlobalSettings globalSettings2, UserTracker userTracker2, DumpManager dumpManager2, Handler handler, Executor executor) {
        this.secureSettings = secureSettings2;
        this.globalSettings = globalSettings2;
        this.userTracker = userTracker2;
        this.dumpManager = dumpManager2;
        this.mainExecutor = executor;
        this.deviceProvisionedUri = globalSettings2.getUriFor("device_provisioned");
        this.userSetupUri = secureSettings2.getUriFor("user_setup_complete");
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        this.userSetupComplete = sparseBooleanArray;
        this.listeners = new ArraySet<>();
        this.lock = new Object();
        this.backgroundExecutor = new HandlerExecutor(handler);
        this.initted = new AtomicBoolean(false);
        this.observer = new DeviceProvisionedControllerImpl$observer$1(this, handler);
        this.userChangedCallback = new DeviceProvisionedControllerImpl$userChangedCallback$1(this);
        sparseBooleanArray.put(userTracker2.getUserId(), false);
    }
}
