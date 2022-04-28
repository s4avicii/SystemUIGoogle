package com.android.systemui.unfold.updates;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import android.os.Handler;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* compiled from: DeviceFoldStateProvider.kt */
public final class DeviceFoldStateProvider implements FoldStateProvider {
    public final DeviceStateManager deviceStateManager;
    public final FoldStateListener foldStateListener;
    public final Handler handler;
    public final HingeAngleListener hingeAngleListener = new HingeAngleListener();
    public final HingeAngleProvider hingeAngleProvider;
    public boolean isFolded;
    public boolean isUnfoldHandled;
    public Integer lastFoldUpdate;
    public float lastHingeAngle;
    public final Executor mainExecutor;
    public final ArrayList outputListeners = new ArrayList();
    public final ScreenStatusListener screenListener = new ScreenStatusListener();
    public final ScreenStatusProvider screenStatusProvider;
    public final TimeoutRunnable timeoutRunnable;

    /* compiled from: DeviceFoldStateProvider.kt */
    public final class FoldStateListener extends DeviceStateManager.FoldStateListener {
        public FoldStateListener(final DeviceFoldStateProvider deviceFoldStateProvider, Context context) {
            super(context, new Consumer() {
                public final void accept(Object obj) {
                    Integer num;
                    boolean booleanValue = ((Boolean) obj).booleanValue();
                    DeviceFoldStateProvider deviceFoldStateProvider = deviceFoldStateProvider;
                    deviceFoldStateProvider.isFolded = booleanValue;
                    deviceFoldStateProvider.lastHingeAngle = 0.0f;
                    boolean z = false;
                    if (booleanValue) {
                        deviceFoldStateProvider.hingeAngleProvider.stop();
                        deviceFoldStateProvider.notifyFoldUpdate(5);
                        DeviceFoldStateProvider deviceFoldStateProvider2 = deviceFoldStateProvider;
                        Objects.requireNonNull(deviceFoldStateProvider2);
                        deviceFoldStateProvider2.handler.removeCallbacks(deviceFoldStateProvider2.timeoutRunnable);
                        deviceFoldStateProvider.isUnfoldHandled = false;
                        return;
                    }
                    deviceFoldStateProvider.notifyFoldUpdate(0);
                    DeviceFoldStateProvider deviceFoldStateProvider3 = deviceFoldStateProvider;
                    Objects.requireNonNull(deviceFoldStateProvider3);
                    Integer num2 = deviceFoldStateProvider3.lastFoldUpdate;
                    if ((num2 != null && num2.intValue() == 0) || ((num = deviceFoldStateProvider3.lastFoldUpdate) != null && num.intValue() == 1)) {
                        z = true;
                    }
                    if (z) {
                        deviceFoldStateProvider3.handler.removeCallbacks(deviceFoldStateProvider3.timeoutRunnable);
                    }
                    deviceFoldStateProvider3.handler.postDelayed(deviceFoldStateProvider3.timeoutRunnable, 1000);
                    deviceFoldStateProvider.hingeAngleProvider.start();
                }
            });
        }
    }

    /* compiled from: DeviceFoldStateProvider.kt */
    public final class HingeAngleListener implements androidx.core.util.Consumer<Float> {
        public HingeAngleListener() {
        }

        public final void accept(Object obj) {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            Integer num;
            Integer num2;
            float floatValue = ((Number) obj).floatValue();
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            Objects.requireNonNull(deviceFoldStateProvider);
            boolean z5 = true;
            if (floatValue < deviceFoldStateProvider.lastHingeAngle) {
                z = true;
            } else {
                z = false;
            }
            if (180.0f - floatValue < 15.0f) {
                z2 = true;
            } else {
                z2 = false;
            }
            Integer num3 = deviceFoldStateProvider.lastFoldUpdate;
            if (num3 != null && num3.intValue() == 1) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z && !z3 && !z2) {
                deviceFoldStateProvider.notifyFoldUpdate(1);
            }
            Integer num4 = deviceFoldStateProvider.lastFoldUpdate;
            if ((num4 != null && num4.intValue() == 0) || ((num2 = deviceFoldStateProvider.lastFoldUpdate) != null && num2.intValue() == 1)) {
                z4 = true;
            } else {
                z4 = false;
            }
            if (z4) {
                if (z2) {
                    deviceFoldStateProvider.notifyFoldUpdate(4);
                    deviceFoldStateProvider.handler.removeCallbacks(deviceFoldStateProvider.timeoutRunnable);
                } else {
                    Integer num5 = deviceFoldStateProvider.lastFoldUpdate;
                    if ((num5 == null || num5.intValue() != 0) && ((num = deviceFoldStateProvider.lastFoldUpdate) == null || num.intValue() != 1)) {
                        z5 = false;
                    }
                    if (z5) {
                        deviceFoldStateProvider.handler.removeCallbacks(deviceFoldStateProvider.timeoutRunnable);
                    }
                    deviceFoldStateProvider.handler.postDelayed(deviceFoldStateProvider.timeoutRunnable, 1000);
                }
            }
            deviceFoldStateProvider.lastHingeAngle = floatValue;
            Iterator it = deviceFoldStateProvider.outputListeners.iterator();
            while (it.hasNext()) {
                ((FoldStateProvider.FoldUpdatesListener) it.next()).onHingeAngleUpdate(floatValue);
            }
        }
    }

    /* compiled from: DeviceFoldStateProvider.kt */
    public final class ScreenStatusListener implements ScreenStatusProvider.ScreenListener {
        public ScreenStatusListener() {
        }

        public final void onScreenTurnedOn() {
            DeviceFoldStateProvider deviceFoldStateProvider = DeviceFoldStateProvider.this;
            if (!deviceFoldStateProvider.isFolded && !deviceFoldStateProvider.isUnfoldHandled) {
                Iterator it = deviceFoldStateProvider.outputListeners.iterator();
                while (it.hasNext()) {
                    ((FoldStateProvider.FoldUpdatesListener) it.next()).onFoldUpdate(2);
                }
                DeviceFoldStateProvider.this.isUnfoldHandled = true;
            }
        }
    }

    /* compiled from: DeviceFoldStateProvider.kt */
    public final class TimeoutRunnable implements Runnable {
        public TimeoutRunnable() {
        }

        public final void run() {
            DeviceFoldStateProvider.this.notifyFoldUpdate(3);
        }
    }

    public final void addCallback(Object obj) {
        this.outputListeners.add((FoldStateProvider.FoldUpdatesListener) obj);
    }

    public final boolean isFullyOpened() {
        Integer num;
        if (this.isFolded || (num = this.lastFoldUpdate) == null || num.intValue() != 4) {
            return false;
        }
        return true;
    }

    public final void notifyFoldUpdate(int i) {
        Iterator it = this.outputListeners.iterator();
        while (it.hasNext()) {
            ((FoldStateProvider.FoldUpdatesListener) it.next()).onFoldUpdate(i);
        }
        this.lastFoldUpdate = Integer.valueOf(i);
    }

    public final void removeCallback(Object obj) {
        this.outputListeners.remove((FoldStateProvider.FoldUpdatesListener) obj);
    }

    public final void start() {
        this.deviceStateManager.registerCallback(this.mainExecutor, this.foldStateListener);
        this.screenStatusProvider.addCallback(this.screenListener);
        this.hingeAngleProvider.addCallback(this.hingeAngleListener);
    }

    public DeviceFoldStateProvider(Context context, HingeAngleProvider hingeAngleProvider2, ScreenStatusProvider screenStatusProvider2, DeviceStateManager deviceStateManager2, Executor executor, Handler handler2) {
        this.hingeAngleProvider = hingeAngleProvider2;
        this.screenStatusProvider = screenStatusProvider2;
        this.deviceStateManager = deviceStateManager2;
        this.mainExecutor = executor;
        this.handler = handler2;
        this.foldStateListener = new FoldStateListener(this, context);
        this.timeoutRunnable = new TimeoutRunnable();
        this.isUnfoldHandled = true;
    }
}
