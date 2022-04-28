package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import java.io.PrintWriter;
import java.util.Objects;

public final class DozeDockHandler implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public final AmbientDisplayConfiguration mConfig;
    public final DockEventListener mDockEventListener;
    public final DockManager mDockManager;
    public int mDockState = 0;
    public DozeMachine mMachine;

    public class DockEventListener implements DockManager.DockEventListener {
        public boolean mRegistered;

        public DockEventListener() {
        }

        public final void onEvent(int i) {
            boolean z;
            DozeMachine.State state = DozeMachine.State.DOZE;
            if (DozeDockHandler.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("dock event = ", i, "DozeDockHandler");
            }
            DozeDockHandler dozeDockHandler = DozeDockHandler.this;
            if (dozeDockHandler.mDockState != i) {
                dozeDockHandler.mDockState = i;
                DozeMachine.State state2 = dozeDockHandler.mMachine.getState();
                if (state2 == DozeMachine.State.DOZE_REQUEST_PULSE || state2 == DozeMachine.State.DOZE_PULSING || state2 == DozeMachine.State.DOZE_PULSING_BRIGHT) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    DozeDockHandler dozeDockHandler2 = DozeDockHandler.this;
                    int i2 = dozeDockHandler2.mDockState;
                    if (i2 != 0) {
                        if (i2 == 1) {
                            state = DozeMachine.State.DOZE_AOD_DOCKED;
                        } else if (i2 != 2) {
                            return;
                        }
                    } else if (dozeDockHandler2.mConfig.alwaysOnEnabled(-2)) {
                        state = DozeMachine.State.DOZE_AOD;
                    }
                    DozeDockHandler.this.mMachine.requestState(state);
                }
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "DozeDockHandler:", " dockState=");
        m.append(this.mDockState);
        printWriter.println(m.toString());
    }

    public DozeDockHandler(AmbientDisplayConfiguration ambientDisplayConfiguration, DockManager dockManager) {
        this.mConfig = ambientDisplayConfiguration;
        this.mDockManager = dockManager;
        this.mDockEventListener = new DockEventListener();
    }

    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int ordinal = state2.ordinal();
        if (ordinal == 1) {
            DockEventListener dockEventListener = this.mDockEventListener;
            Objects.requireNonNull(dockEventListener);
            if (!dockEventListener.mRegistered) {
                DockManager dockManager = DozeDockHandler.this.mDockManager;
                if (dockManager != null) {
                    dockManager.addListener(dockEventListener);
                }
                dockEventListener.mRegistered = true;
            }
        } else if (ordinal == 8) {
            DockEventListener dockEventListener2 = this.mDockEventListener;
            Objects.requireNonNull(dockEventListener2);
            if (dockEventListener2.mRegistered) {
                DockManager dockManager2 = DozeDockHandler.this.mDockManager;
                if (dockManager2 != null) {
                    dockManager2.removeListener(dockEventListener2);
                }
                dockEventListener2.mRegistered = false;
            }
        }
    }

    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }
}
