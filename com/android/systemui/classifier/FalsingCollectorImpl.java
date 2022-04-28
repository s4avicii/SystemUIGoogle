package com.android.systemui.classifier;

import android.hardware.biometrics.BiometricSourceType;
import android.view.MotionEvent;
import androidx.preference.R$id;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda5;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import com.android.systemui.util.time.SystemClock;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public final class FalsingCollectorImpl implements FalsingCollector {
    public boolean mAvoidGesture;
    public final BatteryController mBatteryController;
    public final C07213 mBatteryListener;
    public final C07224 mDockEventListener;
    public final DockManager mDockManager;
    public final FalsingDataProvider mFalsingDataProvider;
    public final FalsingManager mFalsingManager;
    public final HistoryTracker mHistoryTracker;
    public final KeyguardStateController mKeyguardStateController;
    public final C07202 mKeyguardUpdateCallback;
    public final DelayableExecutor mMainExecutor;
    public MotionEvent mPendingDownEvent;
    public final ProximitySensor mProximitySensor;
    public boolean mScreenOn;
    public final FalsingCollectorImpl$$ExternalSyntheticLambda0 mSensorEventListener = new FalsingCollectorImpl$$ExternalSyntheticLambda0(this);
    public boolean mSessionStarted;
    public boolean mShowingAod;
    public int mState;
    public final StatusBarStateController mStatusBarStateController;
    public final C07191 mStatusBarStateListener;
    public final SystemClock mSystemClock;

    public static class ProximityEventImpl implements FalsingManager.ProximityEvent {
        public ThresholdSensorEvent mThresholdSensorEvent;

        public final boolean getCovered() {
            ThresholdSensorEvent thresholdSensorEvent = this.mThresholdSensorEvent;
            Objects.requireNonNull(thresholdSensorEvent);
            return thresholdSensorEvent.mBelow;
        }

        public final long getTimestampNs() {
            ThresholdSensorEvent thresholdSensorEvent = this.mThresholdSensorEvent;
            Objects.requireNonNull(thresholdSensorEvent);
            return thresholdSensorEvent.mTimestampNs;
        }

        public ProximityEventImpl(ThresholdSensorEvent thresholdSensorEvent) {
            this.mThresholdSensorEvent = thresholdSensorEvent;
        }
    }

    public final void avoidGesture() {
        this.mAvoidGesture = true;
        MotionEvent motionEvent = this.mPendingDownEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mPendingDownEvent = null;
        }
    }

    public final void isReportingEnabled() {
    }

    public final void onAffordanceSwipingAborted() {
    }

    public final void onAffordanceSwipingStarted() {
    }

    public final void onCameraHintStarted() {
    }

    public final void onCameraOn() {
    }

    public final void onExpansionFromPulseStopped() {
    }

    public final void onLeftAffordanceHintStarted() {
    }

    public final void onLeftAffordanceOn() {
    }

    public final void onNotificationDismissed() {
    }

    public final void onNotificationStartDismissing() {
    }

    public final void onNotificationStartDraggingDown() {
    }

    public final void onNotificationStopDismissing() {
    }

    public final void onNotificationStopDraggingDown() {
    }

    public final void onQsDown() {
    }

    public final void onScreenOff() {
        this.mScreenOn = false;
        updateSessionActive();
    }

    public final void onScreenTurningOn() {
        this.mScreenOn = true;
        updateSessionActive();
    }

    public final void onStartExpandingFromPulse() {
    }

    public final void onTrackingStarted() {
    }

    public final void onTrackingStopped() {
    }

    public final void onUnlockHintStarted() {
    }

    public final void setNotificationExpanded() {
    }

    public final void shouldEnforceBouncer() {
    }

    public final void onBouncerHidden() {
        if (this.mSessionStarted) {
            this.mProximitySensor.register(this.mSensorEventListener);
        }
    }

    public final void onBouncerShown() {
        this.mProximitySensor.unregister(this.mSensorEventListener);
    }

    public final void onMotionEventComplete() {
        DelayableExecutor delayableExecutor = this.mMainExecutor;
        FalsingDataProvider falsingDataProvider = this.mFalsingDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        delayableExecutor.executeDelayed(new TaskView$$ExternalSyntheticLambda5(falsingDataProvider, 3), 100);
    }

    public final void onSuccessfulUnlock() {
        this.mFalsingManager.onSuccessfulUnlock();
        sessionEnd();
    }

    public final void onTouchEvent(MotionEvent motionEvent) {
        if (!this.mKeyguardStateController.isShowing() || (this.mStatusBarStateController.isDozing() && !this.mStatusBarStateController.isPulsing())) {
            avoidGesture();
        } else if (motionEvent.getActionMasked() == 0) {
            this.mPendingDownEvent = MotionEvent.obtain(motionEvent);
            this.mAvoidGesture = false;
        } else if (!this.mAvoidGesture) {
            MotionEvent motionEvent2 = this.mPendingDownEvent;
            if (motionEvent2 != null) {
                this.mFalsingDataProvider.onMotionEvent(motionEvent2);
                this.mPendingDownEvent.recycle();
                this.mPendingDownEvent = null;
            }
            this.mFalsingDataProvider.onMotionEvent(motionEvent);
        }
    }

    public final void sessionEnd() {
        if (this.mSessionStarted) {
            this.mSessionStarted = false;
            this.mProximitySensor.unregister(this.mSensorEventListener);
            FalsingDataProvider falsingDataProvider = this.mFalsingDataProvider;
            Objects.requireNonNull(falsingDataProvider);
            Iterator<MotionEvent> it = falsingDataProvider.mRecentMotionEvents.iterator();
            while (it.hasNext()) {
                it.next().recycle();
            }
            falsingDataProvider.mRecentMotionEvents.clear();
            falsingDataProvider.mDirty = true;
            falsingDataProvider.mSessionListeners.forEach(FalsingDataProvider$$ExternalSyntheticLambda1.INSTANCE);
        }
    }

    public final void setQsExpanded(boolean z) {
        if (z) {
            this.mProximitySensor.unregister(this.mSensorEventListener);
        } else if (this.mSessionStarted) {
            this.mProximitySensor.register(this.mSensorEventListener);
        }
    }

    public final void setShowingAod(boolean z) {
        this.mShowingAod = z;
        updateSessionActive();
    }

    public final void updateFalseConfidence(FalsingClassifier.Result result) {
        this.mHistoryTracker.addResults(Collections.singleton(result), this.mSystemClock.uptimeMillis());
    }

    public final void updateSessionActive() {
        boolean z;
        boolean z2;
        boolean z3 = this.mScreenOn;
        if (!z3 || this.mState != 1 || this.mShowingAod) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            sessionEnd();
        } else if (!this.mSessionStarted) {
            if (!z3 || this.mState != 1 || this.mShowingAod) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                this.mSessionStarted = true;
                FalsingDataProvider falsingDataProvider = this.mFalsingDataProvider;
                Objects.requireNonNull(falsingDataProvider);
                falsingDataProvider.mJustUnlockedWithFace = false;
                this.mProximitySensor.register(this.mSensorEventListener);
                FalsingDataProvider falsingDataProvider2 = this.mFalsingDataProvider;
                Objects.requireNonNull(falsingDataProvider2);
                falsingDataProvider2.mSessionListeners.forEach(FalsingDataProvider$$ExternalSyntheticLambda0.INSTANCE);
            }
        }
    }

    public FalsingCollectorImpl(FalsingDataProvider falsingDataProvider, FalsingManager falsingManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HistoryTracker historyTracker, ProximitySensor proximitySensor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, BatteryController batteryController, DockManager dockManager, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        C07191 r0 = new StatusBarStateController.StateListener() {
            public final void onStateChanged(int i) {
                R$id.toShortString(i);
                FalsingCollectorImpl falsingCollectorImpl = FalsingCollectorImpl.this;
                falsingCollectorImpl.mState = i;
                falsingCollectorImpl.updateSessionActive();
            }
        };
        this.mStatusBarStateListener = r0;
        C07202 r1 = new KeyguardUpdateMonitorCallback() {
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (i == KeyguardUpdateMonitor.getCurrentUser() && biometricSourceType == BiometricSourceType.FACE) {
                    FalsingDataProvider falsingDataProvider = FalsingCollectorImpl.this.mFalsingDataProvider;
                    Objects.requireNonNull(falsingDataProvider);
                    falsingDataProvider.mJustUnlockedWithFace = true;
                }
            }
        };
        this.mKeyguardUpdateCallback = r1;
        C07213 r2 = new BatteryController.BatteryStateChangeCallback() {
            public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
            }

            public final void onWirelessChargingChanged(boolean z) {
                if (z || FalsingCollectorImpl.this.mDockManager.isDocked()) {
                    FalsingCollectorImpl.this.mProximitySensor.pause();
                } else {
                    FalsingCollectorImpl.this.mProximitySensor.resume();
                }
            }
        };
        this.mBatteryListener = r2;
        C07224 r3 = new DockManager.DockEventListener() {
            public final void onEvent(int i) {
                if (i != 0 || FalsingCollectorImpl.this.mBatteryController.isWirelessCharging()) {
                    FalsingCollectorImpl.this.mProximitySensor.pause();
                } else {
                    FalsingCollectorImpl.this.mProximitySensor.resume();
                }
            }
        };
        this.mDockEventListener = r3;
        this.mFalsingDataProvider = falsingDataProvider;
        this.mFalsingManager = falsingManager;
        this.mHistoryTracker = historyTracker;
        this.mProximitySensor = proximitySensor;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mBatteryController = batteryController;
        this.mDockManager = dockManager;
        this.mMainExecutor = delayableExecutor;
        this.mSystemClock = systemClock;
        proximitySensor.setTag("FalsingManager");
        proximitySensor.setDelay();
        statusBarStateController.addCallback(r0);
        this.mState = statusBarStateController.getState();
        keyguardUpdateMonitor.registerCallback(r1);
        batteryController.addCallback(r2);
        dockManager.addListener(r3);
    }

    public final void onScreenOnFromTouch() {
        onScreenTurningOn();
    }
}
