package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.display.AmbientDisplayConfiguration;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.ArrayList;

public final class DozeMachine {
    public static final boolean DEBUG = DozeService.DEBUG;
    public final BatteryController mBatteryController;
    public final AmbientDisplayConfiguration mConfig;
    public DockManager mDockManager;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final Service mDozeService;
    public Part[] mParts;
    public int mPulseReason;
    public final ArrayList<State> mQueuedRequests = new ArrayList<>();
    public State mState = State.UNINITIALIZED;
    public final WakeLock mWakeLock;
    public boolean mWakeLockHeldForCurrentState;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    public interface Part {
        void destroy() {
        }

        void dump(PrintWriter printWriter) {
        }

        void onScreenState(int i) {
        }

        void setDozeMachine(DozeMachine dozeMachine) {
        }

        void transitionTo(State state, State state2);
    }

    public interface Service {

        public static class Delegate implements Service {
            public final Service mDelegate;

            public final void finish() {
                this.mDelegate.finish();
            }

            public final void requestWakeUp() {
                this.mDelegate.requestWakeUp();
            }

            public void setDozeScreenBrightness(int i) {
                this.mDelegate.setDozeScreenBrightness(i);
            }

            public void setDozeScreenState(int i) {
                this.mDelegate.setDozeScreenState(i);
            }

            public Delegate(Service service) {
                this.mDelegate = service;
            }
        }

        void finish();

        void requestWakeUp();

        void setDozeScreenBrightness(int i);

        void setDozeScreenState(int i);
    }

    public enum State {
        UNINITIALIZED,
        INITIALIZED,
        DOZE,
        DOZE_AOD,
        DOZE_REQUEST_PULSE,
        DOZE_PULSING,
        DOZE_PULSING_BRIGHT,
        DOZE_PULSE_DONE,
        FINISH,
        DOZE_AOD_PAUSED,
        DOZE_AOD_PAUSING,
        DOZE_AOD_DOCKED;

        public final boolean isAlwaysOn() {
            if (this == DOZE_AOD || this == DOZE_AOD_DOCKED) {
                return true;
            }
            return false;
        }
    }

    public final void requestState(State state) {
        Preconditions.checkArgument(state != State.DOZE_REQUEST_PULSE);
        requestState(state, -1);
    }

    public final boolean isExecutingTransition() {
        return !this.mQueuedRequests.isEmpty();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:125:0x0212, code lost:
        if (r1.mDockManager.isHidden() != false) goto L_0x0220;
     */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x01f4  */
    /* JADX WARNING: Removed duplicated region for block: B:139:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void transitionTo(com.android.systemui.doze.DozeMachine.State r17, int r18) {
        /*
            r16 = this;
            r1 = r16
            r0 = r17
            com.android.systemui.doze.DozeMachine$State r2 = com.android.systemui.doze.DozeMachine.State.INITIALIZED
            com.android.systemui.doze.DozeMachine$State r3 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD_DOCKED
            com.android.systemui.doze.DozeMachine$State r4 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD
            com.android.systemui.doze.DozeMachine$State r5 = com.android.systemui.doze.DozeMachine.State.DOZE_REQUEST_PULSE
            com.android.systemui.doze.DozeMachine$State r6 = com.android.systemui.doze.DozeMachine.State.DOZE_PULSE_DONE
            com.android.systemui.doze.DozeMachine$State r7 = com.android.systemui.doze.DozeMachine.State.FINISH
            com.android.systemui.doze.DozeMachine$State r8 = com.android.systemui.doze.DozeMachine.State.DOZE
            com.android.systemui.log.LogLevel r9 = com.android.systemui.log.LogLevel.INFO
            com.android.systemui.doze.DozeMachine$State r10 = r1.mState
            r11 = 2
            java.lang.String r12 = "DozeMachine"
            java.lang.String r13 = "DozeLog"
            if (r10 != r7) goto L_0x0020
            r10 = r7
            goto L_0x00b9
        L_0x0020:
            com.android.systemui.doze.DozeHost r10 = r1.mDozeHost
            boolean r10 = r10.isDozeSuppressed()
            if (r10 == 0) goto L_0x0066
            boolean r10 = r17.isAlwaysOn()
            if (r10 == 0) goto L_0x0066
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Doze is suppressed. Suppressing state: "
            r10.append(r11)
            r10.append(r0)
            java.lang.String r10 = r10.toString()
            android.util.Log.i(r12, r10)
            com.android.systemui.doze.DozeLog r10 = r1.mDozeLog
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.doze.DozeLogger r10 = r10.mLogger
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.log.LogBuffer r10 = r10.buffer
            com.android.systemui.doze.DozeLogger$logDozeSuppressed$2 r11 = com.android.systemui.doze.DozeLogger$logDozeSuppressed$2.INSTANCE
            java.util.Objects.requireNonNull(r10)
            boolean r14 = r10.frozen
            if (r14 != 0) goto L_0x0064
            com.android.systemui.log.LogMessageImpl r11 = r10.obtain(r13, r9, r11)
            java.lang.String r14 = r17.name()
            r11.str1 = r14
            r10.push(r11)
        L_0x0064:
            r10 = r8
            goto L_0x00b9
        L_0x0066:
            com.android.systemui.doze.DozeMachine$State r10 = r1.mState
            com.android.systemui.doze.DozeMachine$State r14 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD_PAUSED
            if (r10 == r14) goto L_0x0076
            com.android.systemui.doze.DozeMachine$State r14 = com.android.systemui.doze.DozeMachine.State.DOZE_AOD_PAUSING
            if (r10 == r14) goto L_0x0076
            if (r10 == r4) goto L_0x0076
            if (r10 == r8) goto L_0x0076
            if (r10 != r3) goto L_0x008d
        L_0x0076:
            if (r0 != r6) goto L_0x008d
            java.lang.String r10 = "Dropping pulse done because current state is already done: "
            java.lang.StringBuilder r10 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
            com.android.systemui.doze.DozeMachine$State r11 = r1.mState
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.i(r12, r10)
            com.android.systemui.doze.DozeMachine$State r10 = r1.mState
            goto L_0x00b9
        L_0x008d:
            if (r0 != r5) goto L_0x00b8
            java.util.Objects.requireNonNull(r10)
            int r10 = r10.ordinal()
            if (r10 == r11) goto L_0x00a0
            r11 = 3
            if (r10 == r11) goto L_0x00a0
            switch(r10) {
                case 9: goto L_0x00a0;
                case 10: goto L_0x00a0;
                case 11: goto L_0x00a0;
                default: goto L_0x009e;
            }
        L_0x009e:
            r10 = 0
            goto L_0x00a1
        L_0x00a0:
            r10 = 1
        L_0x00a1:
            if (r10 != 0) goto L_0x00b8
            java.lang.String r10 = "Dropping pulse request because current state can't pulse: "
            java.lang.StringBuilder r10 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
            com.android.systemui.doze.DozeMachine$State r11 = r1.mState
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            android.util.Log.i(r12, r10)
            com.android.systemui.doze.DozeMachine$State r10 = r1.mState
            goto L_0x00b9
        L_0x00b8:
            r10 = r0
        L_0x00b9:
            boolean r11 = DEBUG
            if (r11 == 0) goto L_0x00e0
            java.lang.String r11 = "transition: old="
            java.lang.StringBuilder r11 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r11)
            com.android.systemui.doze.DozeMachine$State r14 = r1.mState
            r11.append(r14)
            java.lang.String r14 = " req="
            r11.append(r14)
            r11.append(r0)
            java.lang.String r0 = " new="
            r11.append(r0)
            r11.append(r10)
            java.lang.String r0 = r11.toString()
            android.util.Log.i(r12, r0)
        L_0x00e0:
            com.android.systemui.doze.DozeMachine$State r0 = r1.mState
            if (r10 != r0) goto L_0x00e5
            return
        L_0x00e5:
            int r0 = r0.ordinal()     // Catch:{ RuntimeException -> 0x022d }
            r11 = 8
            if (r0 == 0) goto L_0x00f9
            if (r0 == r11) goto L_0x00f0
            goto L_0x0101
        L_0x00f0:
            if (r10 != r7) goto L_0x00f4
            r0 = 1
            goto L_0x00f5
        L_0x00f4:
            r0 = 0
        L_0x00f5:
            com.android.internal.util.Preconditions.checkState(r0)     // Catch:{ RuntimeException -> 0x022d }
            goto L_0x0101
        L_0x00f9:
            if (r10 != r2) goto L_0x00fd
            r0 = 1
            goto L_0x00fe
        L_0x00fd:
            r0 = 0
        L_0x00fe:
            com.android.internal.util.Preconditions.checkState(r0)     // Catch:{ RuntimeException -> 0x022d }
        L_0x0101:
            int r0 = r10.ordinal()     // Catch:{ RuntimeException -> 0x022d }
            if (r0 == 0) goto L_0x0225
            r11 = 5
            r12 = 7
            r14 = 1
            if (r0 == r14) goto L_0x0130
            if (r0 == r11) goto L_0x0125
            if (r0 == r12) goto L_0x0111
            goto L_0x013c
        L_0x0111:
            com.android.systemui.doze.DozeMachine$State r0 = r1.mState     // Catch:{ RuntimeException -> 0x022d }
            if (r0 == r5) goto L_0x0120
            com.android.systemui.doze.DozeMachine$State r11 = com.android.systemui.doze.DozeMachine.State.DOZE_PULSING     // Catch:{ RuntimeException -> 0x022d }
            if (r0 == r11) goto L_0x0120
            com.android.systemui.doze.DozeMachine$State r11 = com.android.systemui.doze.DozeMachine.State.DOZE_PULSING_BRIGHT     // Catch:{ RuntimeException -> 0x022d }
            if (r0 != r11) goto L_0x011e
            goto L_0x0120
        L_0x011e:
            r0 = 0
            goto L_0x0121
        L_0x0120:
            r0 = 1
        L_0x0121:
            com.android.internal.util.Preconditions.checkState(r0)     // Catch:{ RuntimeException -> 0x022d }
            goto L_0x013c
        L_0x0125:
            com.android.systemui.doze.DozeMachine$State r0 = r1.mState     // Catch:{ RuntimeException -> 0x022d }
            if (r0 != r5) goto L_0x012b
            r0 = 1
            goto L_0x012c
        L_0x012b:
            r0 = 0
        L_0x012c:
            com.android.internal.util.Preconditions.checkState(r0)     // Catch:{ RuntimeException -> 0x022d }
            goto L_0x013c
        L_0x0130:
            com.android.systemui.doze.DozeMachine$State r0 = r1.mState     // Catch:{ RuntimeException -> 0x022d }
            com.android.systemui.doze.DozeMachine$State r11 = com.android.systemui.doze.DozeMachine.State.UNINITIALIZED     // Catch:{ RuntimeException -> 0x022d }
            if (r0 != r11) goto L_0x0138
            r0 = 1
            goto L_0x0139
        L_0x0138:
            r0 = 0
        L_0x0139:
            com.android.internal.util.Preconditions.checkState(r0)     // Catch:{ RuntimeException -> 0x022d }
        L_0x013c:
            com.android.systemui.doze.DozeMachine$State r0 = r1.mState
            r1.mState = r10
            com.android.systemui.doze.DozeLog r11 = r1.mDozeLog
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.doze.DozeLogger r11 = r11.mLogger
            java.util.Objects.requireNonNull(r11)
            com.android.systemui.log.LogBuffer r11 = r11.buffer
            com.android.systemui.doze.DozeLogger$logDozeStateChanged$2 r12 = com.android.systemui.doze.DozeLogger$logDozeStateChanged$2.INSTANCE
            java.util.Objects.requireNonNull(r11)
            boolean r14 = r11.frozen
            if (r14 != 0) goto L_0x0162
            com.android.systemui.log.LogMessageImpl r12 = r11.obtain(r13, r9, r12)
            java.lang.String r14 = r10.name()
            r12.str1 = r14
            r11.push(r12)
        L_0x0162:
            r11 = 4096(0x1000, double:2.0237E-320)
            int r14 = r10.ordinal()
            java.lang.String r15 = "doze_machine_state"
            android.os.Trace.traceCounter(r11, r15, r14)
            r11 = -1
            if (r10 != r5) goto L_0x0175
            r5 = r18
            r1.mPulseReason = r5
            goto L_0x0179
        L_0x0175:
            if (r0 != r6) goto L_0x0179
            r1.mPulseReason = r11
        L_0x0179:
            com.android.systemui.doze.DozeMachine$Part[] r5 = r1.mParts
            int r6 = r5.length
            r12 = 0
        L_0x017d:
            if (r12 >= r6) goto L_0x0187
            r14 = r5[r12]
            r14.transitionTo(r0, r10)
            int r12 = r12 + 1
            goto L_0x017d
        L_0x0187:
            com.android.systemui.doze.DozeLog r0 = r1.mDozeLog
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.doze.DozeLogger r0 = r0.mLogger
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.log.LogBuffer r0 = r0.buffer
            com.android.systemui.doze.DozeLogger$logStateChangedSent$2 r5 = com.android.systemui.doze.DozeLogger$logStateChangedSent$2.INSTANCE
            java.util.Objects.requireNonNull(r0)
            boolean r6 = r0.frozen
            if (r6 != 0) goto L_0x01a9
            com.android.systemui.log.LogMessageImpl r5 = r0.obtain(r13, r9, r5)
            java.lang.String r6 = r10.name()
            r5.str1 = r6
            r0.push(r5)
        L_0x01a9:
            int r0 = r10.ordinal()
            r5 = 8
            if (r0 == r5) goto L_0x01b2
            goto L_0x01b7
        L_0x01b2:
            com.android.systemui.doze.DozeMachine$Service r0 = r1.mDozeService
            r0.finish()
        L_0x01b7:
            int r0 = r10.ordinal()
            r5 = 4
            if (r0 == r5) goto L_0x01ca
            r5 = 5
            if (r0 == r5) goto L_0x01ca
            r5 = 6
            if (r0 == r5) goto L_0x01ca
            r5 = 11
            if (r0 == r5) goto L_0x01ca
            r0 = 0
            goto L_0x01cb
        L_0x01ca:
            r0 = 1
        L_0x01cb:
            boolean r5 = r1.mWakeLockHeldForCurrentState
            java.lang.String r6 = "DozeMachine#heldForState"
            if (r5 == 0) goto L_0x01dc
            if (r0 != 0) goto L_0x01dc
            com.android.systemui.util.wakelock.WakeLock r0 = r1.mWakeLock
            r0.release(r6)
            r0 = 0
            r1.mWakeLockHeldForCurrentState = r0
            goto L_0x01e9
        L_0x01dc:
            if (r5 != 0) goto L_0x01e9
            if (r0 == 0) goto L_0x01e9
            com.android.systemui.util.wakelock.WakeLock r0 = r1.mWakeLock
            r0.acquire(r6)
            r0 = 1
            r1.mWakeLockHeldForCurrentState = r0
            goto L_0x01ea
        L_0x01e9:
            r0 = 1
        L_0x01ea:
            int r5 = r10.ordinal()
            if (r5 == r0) goto L_0x01f4
            r6 = 7
            if (r5 == r6) goto L_0x01f4
            goto L_0x0224
        L_0x01f4:
            com.android.systemui.keyguard.WakefulnessLifecycle r5 = r1.mWakefulnessLifecycle
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mWakefulness
            if (r10 == r2) goto L_0x0204
            r2 = 2
            if (r5 == r2) goto L_0x0202
            if (r5 != r0) goto L_0x0204
        L_0x0202:
            r3 = r7
            goto L_0x0221
        L_0x0204:
            com.android.systemui.dock.DockManager r0 = r1.mDockManager
            boolean r0 = r0.isDocked()
            if (r0 == 0) goto L_0x0215
            com.android.systemui.dock.DockManager r0 = r1.mDockManager
            boolean r0 = r0.isHidden()
            if (r0 == 0) goto L_0x0221
            goto L_0x0220
        L_0x0215:
            android.hardware.display.AmbientDisplayConfiguration r0 = r1.mConfig
            r2 = -2
            boolean r0 = r0.alwaysOnEnabled(r2)
            if (r0 == 0) goto L_0x0220
            r3 = r4
            goto L_0x0221
        L_0x0220:
            r3 = r8
        L_0x0221:
            r1.transitionTo(r3, r11)
        L_0x0224:
            return
        L_0x0225:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException     // Catch:{ RuntimeException -> 0x022d }
            java.lang.String r2 = "can't transition to UNINITIALIZED"
            r0.<init>(r2)     // Catch:{ RuntimeException -> 0x022d }
            throw r0     // Catch:{ RuntimeException -> 0x022d }
        L_0x022d:
            r0 = move-exception
            java.lang.IllegalStateException r2 = new java.lang.IllegalStateException
            java.lang.String r3 = "Illegal Transition: "
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            com.android.systemui.doze.DozeMachine$State r1 = r1.mState
            r3.append(r1)
            java.lang.String r1 = " -> "
            r3.append(r1)
            r3.append(r10)
            java.lang.String r1 = r3.toString()
            r2.<init>(r1, r0)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.doze.DozeMachine.transitionTo(com.android.systemui.doze.DozeMachine$State, int):void");
    }

    public DozeMachine(Service service, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, WakefulnessLifecycle wakefulnessLifecycle, BatteryController batteryController, DozeLog dozeLog, DockManager dockManager, DozeHost dozeHost, Part[] partArr) {
        this.mWakeLockHeldForCurrentState = false;
        this.mDozeService = service;
        this.mConfig = ambientDisplayConfiguration;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mWakeLock = wakeLock;
        this.mBatteryController = batteryController;
        this.mDozeLog = dozeLog;
        this.mDockManager = dockManager;
        this.mDozeHost = dozeHost;
        this.mParts = partArr;
        for (Part dozeMachine : partArr) {
            dozeMachine.setDozeMachine(this);
        }
    }

    public final State getState() {
        Assert.isMainThread();
        if (!isExecutingTransition()) {
            return this.mState;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot get state because there were pending transitions: ");
        m.append(this.mQueuedRequests.toString());
        throw new IllegalStateException(m.toString());
    }

    public final void requestState(State state, int i) {
        Assert.isMainThread();
        if (DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("request: current=");
            m.append(this.mState);
            m.append(" req=");
            m.append(state);
            Log.i("DozeMachine", m.toString(), new Throwable("here"));
        }
        boolean z = !isExecutingTransition();
        this.mQueuedRequests.add(state);
        if (z) {
            this.mWakeLock.acquire("DozeMachine#requestState");
            for (int i2 = 0; i2 < this.mQueuedRequests.size(); i2++) {
                transitionTo(this.mQueuedRequests.get(i2), i);
            }
            this.mQueuedRequests.clear();
            this.mWakeLock.release("DozeMachine#requestState");
        }
    }
}
