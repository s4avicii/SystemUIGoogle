package com.google.android.systemui.lowlightclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;
import android.util.Log;
import com.android.p012wm.shell.bubbles.Bubble$$ExternalSyntheticLambda1;
import com.android.systemui.dock.DockManager;
import com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class LowLightDockManager implements DockManager {
    public static final boolean DEBUG = Log.isLoggable("LowLightDockManager", 3);
    public final AmbientLightModeMonitor mAmbientLightModeMonitor;
    public final ArrayList mClients = new ArrayList();
    public int mDockState = 0;
    public boolean mIsLowLight = false;
    public final Executor mMainExecutor;
    public final PowerManager mPowerManager;

    public final void addAlignmentStateListener(KeyguardIndicationController$$ExternalSyntheticLambda0 keyguardIndicationController$$ExternalSyntheticLambda0) {
    }

    public final void addListener(DockManager.DockEventListener dockEventListener) {
        if (!this.mClients.contains(dockEventListener)) {
            this.mClients.add(dockEventListener);
        }
        this.mMainExecutor.execute(new Bubble$$ExternalSyntheticLambda1(this, dockEventListener, 4));
    }

    public final boolean isDocked() {
        if (this.mDockState == 1) {
            return true;
        }
        return false;
    }

    public final boolean isHidden() {
        if (!this.mIsLowLight || this.mDockState == 2) {
            return true;
        }
        return false;
    }

    public final void processDockEvent(Intent intent) {
        int i;
        if (intent != null && "android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
            boolean z = false;
            int intExtra = intent.getIntExtra("android.intent.extra.DOCK_STATE", 0);
            if (intExtra == 1 || intExtra == 3 || intExtra == 4) {
                z = true;
            }
            if (z) {
                i = 1;
            } else {
                i = 2;
            }
            if (this.mDockState != i) {
                this.mDockState = i;
                if (i == 1) {
                    AmbientLightModeMonitor ambientLightModeMonitor = this.mAmbientLightModeMonitor;
                    LowLightDockManager$$ExternalSyntheticLambda0 lowLightDockManager$$ExternalSyntheticLambda0 = new LowLightDockManager$$ExternalSyntheticLambda0(this);
                    boolean z2 = AmbientLightModeMonitor.DEBUG;
                    if (z2) {
                        Objects.requireNonNull(ambientLightModeMonitor);
                        Log.d("AmbientLightModeMonitor", "start monitoring ambient light mode");
                    }
                    if (ambientLightModeMonitor.lightSensor != null) {
                        ambientLightModeMonitor.algorithm.start(lowLightDockManager$$ExternalSyntheticLambda0);
                        ambientLightModeMonitor.sensorManager.registerListener(ambientLightModeMonitor.mSensorEventListener, ambientLightModeMonitor.lightSensor, 3);
                    } else if (z2) {
                        Log.w("AmbientLightModeMonitor", "light sensor not available");
                    }
                } else {
                    AmbientLightModeMonitor ambientLightModeMonitor2 = this.mAmbientLightModeMonitor;
                    if (AmbientLightModeMonitor.DEBUG) {
                        Objects.requireNonNull(ambientLightModeMonitor2);
                        Log.d("AmbientLightModeMonitor", "stop monitoring ambient light mode");
                    }
                    ambientLightModeMonitor2.algorithm.stop();
                    ambientLightModeMonitor2.sensorManager.unregisterListener(ambientLightModeMonitor2.mSensorEventListener);
                }
                Iterator it = this.mClients.iterator();
                while (it.hasNext()) {
                    ((DockManager.DockEventListener) it.next()).onEvent(this.mDockState);
                }
            }
        }
    }

    public final void removeListener(DockManager.DockEventListener dockEventListener) {
        this.mClients.remove(dockEventListener);
    }

    public LowLightDockManager(Context context, AmbientLightModeMonitor ambientLightModeMonitor, Executor executor, PowerManager powerManager) {
        this.mAmbientLightModeMonitor = ambientLightModeMonitor;
        this.mMainExecutor = executor;
        this.mPowerManager = powerManager;
        processDockEvent(context.registerReceiver(new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                LowLightDockManager.this.processDockEvent(intent);
            }
        }, new IntentFilter("android.intent.action.DOCK_EVENT")));
    }
}
