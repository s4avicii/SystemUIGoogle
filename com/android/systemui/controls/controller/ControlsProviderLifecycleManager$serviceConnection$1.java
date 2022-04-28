package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.controls.IControlsProvider;
import android.util.ArraySet;
import android.util.Log;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsProviderLifecycleManager.kt */
public final class ControlsProviderLifecycleManager$serviceConnection$1 implements ServiceConnection {
    public final /* synthetic */ ControlsProviderLifecycleManager this$0;

    public ControlsProviderLifecycleManager$serviceConnection$1(ControlsProviderLifecycleManager controlsProviderLifecycleManager) {
        this.this$0 = controlsProviderLifecycleManager;
    }

    public final void onNullBinding(ComponentName componentName) {
        Log.d(this.this$0.TAG, Intrinsics.stringPlus("onNullBinding ", componentName));
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.this$0;
        controlsProviderLifecycleManager.wrapper = null;
        controlsProviderLifecycleManager.context.unbindService(this);
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        ArraySet arraySet;
        Log.d(this.this$0.TAG, Intrinsics.stringPlus("onServiceConnected ", componentName));
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.this$0;
        controlsProviderLifecycleManager.bindTryCount = 0;
        controlsProviderLifecycleManager.wrapper = new ServiceWrapper(IControlsProvider.Stub.asInterface(iBinder));
        try {
            iBinder.linkToDeath(this.this$0, 0);
        } catch (RemoteException unused) {
        }
        ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = this.this$0;
        Objects.requireNonNull(controlsProviderLifecycleManager2);
        synchronized (controlsProviderLifecycleManager2.queuedServiceMethods) {
            arraySet = new ArraySet(controlsProviderLifecycleManager2.queuedServiceMethods);
            controlsProviderLifecycleManager2.queuedServiceMethods.clear();
        }
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            ControlsProviderLifecycleManager.ServiceMethod serviceMethod = (ControlsProviderLifecycleManager.ServiceMethod) it.next();
            Objects.requireNonNull(serviceMethod);
            if (!serviceMethod.mo7670x93b7231b()) {
                ControlsProviderLifecycleManager controlsProviderLifecycleManager3 = ControlsProviderLifecycleManager.this;
                Objects.requireNonNull(controlsProviderLifecycleManager3);
                synchronized (controlsProviderLifecycleManager3.queuedServiceMethods) {
                    controlsProviderLifecycleManager3.queuedServiceMethods.add(serviceMethod);
                }
                ControlsProviderLifecycleManager.this.binderDied();
            }
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        Log.d(this.this$0.TAG, Intrinsics.stringPlus("onServiceDisconnected ", componentName));
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.this$0;
        controlsProviderLifecycleManager.wrapper = null;
        controlsProviderLifecycleManager.executor.execute(new ControlsProviderLifecycleManager$bindService$1(controlsProviderLifecycleManager, false));
    }
}
