package com.android.systemui.keyguard;

import android.app.ActivityTaskManager;
import android.app.Service;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.Log;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManagerPolicyConstants;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardService;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.transition.ShellTransitions;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimation;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public class KeyguardService extends Service {
    public static boolean sEnableRemoteKeyguardGoingAwayAnimation;
    public static boolean sEnableRemoteKeyguardOccludeAnimation;
    public final C08465 mBinder = new IKeyguardService.Stub() {
        public final void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            synchronized (keyguardViewMediator) {
                keyguardViewMediator.mKeyguardStateCallbacks.add(iKeyguardStateCallback);
                try {
                    iKeyguardStateCallback.onSimSecureStateChanged(keyguardViewMediator.mUpdateMonitor.isSimPinSecure());
                    iKeyguardStateCallback.onShowingStateChanged(keyguardViewMediator.mShowing, KeyguardUpdateMonitor.getCurrentUser());
                    iKeyguardStateCallback.onInputRestrictedStateChanged(keyguardViewMediator.mInputRestricted);
                    iKeyguardStateCallback.onTrustedChanged(keyguardViewMediator.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()));
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call to IKeyguardStateCallback", e);
                }
            }
        }

        public final void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.dismiss(iKeyguardDismissCallback, charSequence);
        }

        public final void dismissKeyguardToLaunch(Intent intent) {
            KeyguardService.this.checkPermission();
            Objects.requireNonNull(KeyguardService.this.mKeyguardViewMediator);
        }

        public final void doKeyguardTimeout(Bundle bundle) {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            keyguardViewMediator.mHandler.removeMessages(10);
            keyguardViewMediator.mHandler.sendMessageAtFrontOfQueue(keyguardViewMediator.mHandler.obtainMessage(10, bundle));
        }

        public final void onBootCompleted() {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onBootCompleted();
        }

        public final void onDreamingStarted() {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            KeyguardUpdateMonitor.C054014 r0 = keyguardUpdateMonitor.mHandler;
            r0.sendMessage(r0.obtainMessage(333, 1, 0));
            synchronized (keyguardViewMediator) {
                if (keyguardViewMediator.mDeviceInteractive && keyguardViewMediator.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser())) {
                    long lockTimeout = keyguardViewMediator.getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                    if (lockTimeout == 0) {
                        keyguardViewMediator.doKeyguardLocked((Bundle) null);
                    } else {
                        keyguardViewMediator.doKeyguardLaterLocked(lockTimeout);
                    }
                }
            }
        }

        public final void onDreamingStopped() {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            KeyguardUpdateMonitor.C054014 r0 = keyguardUpdateMonitor.mHandler;
            r0.sendMessage(r0.obtainMessage(333, 0, 0));
            synchronized (keyguardViewMediator) {
                if (keyguardViewMediator.mDeviceInteractive) {
                    keyguardViewMediator.mDelayedShowingSequence++;
                }
            }
        }

        public final void onFinishedGoingToSleep(int i, boolean z) {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            int translateSleepReasonToOffReason = WindowManagerPolicyConstants.translateSleepReasonToOffReason(i);
            if (KeyguardViewMediator.DEBUG) {
                Objects.requireNonNull(keyguardViewMediator);
                Log.d("KeyguardViewMediator", "onFinishedGoingToSleep(" + translateSleepReasonToOffReason + ")");
            }
            synchronized (keyguardViewMediator) {
                try {
                    keyguardViewMediator.mDeviceInteractive = false;
                    keyguardViewMediator.mGoingToSleep = false;
                    keyguardViewMediator.mScreenOnCoordinator.setWakeAndUnlocking(false);
                    DozeParameters dozeParameters = keyguardViewMediator.mDozeParameters;
                    Objects.requireNonNull(dozeParameters);
                    ScreenOffAnimationController screenOffAnimationController = dozeParameters.mScreenOffAnimationController;
                    Objects.requireNonNull(screenOffAnimationController);
                    ArrayList arrayList = screenOffAnimationController.animations;
                    boolean z2 = true;
                    if (!(arrayList instanceof Collection) || !arrayList.isEmpty()) {
                        Iterator it = arrayList.iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (!((ScreenOffAnimation) it.next()).shouldAnimateDozingChange()) {
                                    z2 = false;
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    keyguardViewMediator.mAnimatingScreenOff = z2;
                    keyguardViewMediator.resetKeyguardDonePendingLocked();
                    keyguardViewMediator.mHideAnimationRun = false;
                    if (KeyguardViewMediator.DEBUG) {
                        Log.d("KeyguardViewMediator", "notifyFinishedGoingToSleep");
                    }
                    keyguardViewMediator.mHandler.sendEmptyMessage(5);
                    if (z) {
                        ((PowerManager) keyguardViewMediator.mContext.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), 5, "com.android.systemui:CAMERA_GESTURE_PREVENT_LOCK");
                        keyguardViewMediator.mPendingLock = false;
                        keyguardViewMediator.mPendingReset = false;
                    }
                    if (keyguardViewMediator.mPendingReset) {
                        keyguardViewMediator.resetStateLocked();
                        keyguardViewMediator.mPendingReset = false;
                    }
                    if (keyguardViewMediator.mPendingLock && !keyguardViewMediator.mScreenOffAnimationController.isKeyguardShowDelayed()) {
                        keyguardViewMediator.doKeyguardLocked((Bundle) null);
                        keyguardViewMediator.mPendingLock = false;
                    }
                    if (!keyguardViewMediator.mLockLater && !z) {
                        keyguardViewMediator.doKeyguardForChildProfilesLocked();
                    }
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            synchronized (keyguardUpdateMonitor) {
                keyguardUpdateMonitor.mDeviceInteractive = false;
            }
            KeyguardUpdateMonitor.C054014 r9 = keyguardUpdateMonitor.mHandler;
            r9.sendMessage(r9.obtainMessage(320, translateSleepReasonToOffReason, 0));
            KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardService.this.mKeyguardLifecyclesDispatcher;
            Objects.requireNonNull(keyguardLifecyclesDispatcher);
            keyguardLifecyclesDispatcher.mHandler.obtainMessage(7).sendToTarget();
        }

        public final void onFinishedWakingUp() {
            Trace.beginSection("KeyguardService.mBinder#onFinishedWakingUp");
            KeyguardService.this.checkPermission();
            KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardService.this.mKeyguardLifecyclesDispatcher;
            Objects.requireNonNull(keyguardLifecyclesDispatcher);
            keyguardLifecyclesDispatcher.mHandler.obtainMessage(5).sendToTarget();
            Trace.endSection();
        }

        public final void onScreenTurnedOff() {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            keyguardUpdateMonitor.mHandler.sendEmptyMessage(332);
            KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardService.this.mKeyguardLifecyclesDispatcher;
            Objects.requireNonNull(keyguardLifecyclesDispatcher);
            keyguardLifecyclesDispatcher.mHandler.obtainMessage(3).sendToTarget();
        }

        public final void onScreenTurnedOn() {
            Trace.beginSection("KeyguardService.mBinder#onScreenTurnedOn");
            KeyguardService.this.checkPermission();
            KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardService.this.mKeyguardLifecyclesDispatcher;
            Objects.requireNonNull(keyguardLifecyclesDispatcher);
            keyguardLifecyclesDispatcher.mHandler.obtainMessage(1).sendToTarget();
            Trace.endSection();
        }

        public final void onScreenTurningOff() {
            KeyguardService.this.checkPermission();
            KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardService.this.mKeyguardLifecyclesDispatcher;
            Objects.requireNonNull(keyguardLifecyclesDispatcher);
            keyguardLifecyclesDispatcher.mHandler.obtainMessage(2).sendToTarget();
        }

        public final void onScreenTurningOn(IKeyguardDrawnCallback iKeyguardDrawnCallback) {
            Trace.beginSection("KeyguardService.mBinder#onScreenTurningOn");
            KeyguardService.this.checkPermission();
            KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardService.this.mKeyguardLifecyclesDispatcher;
            Objects.requireNonNull(keyguardLifecyclesDispatcher);
            keyguardLifecyclesDispatcher.mHandler.obtainMessage(0, iKeyguardDrawnCallback).sendToTarget();
            Trace.endSection();
        }

        public final void onShortPowerPressedGoHome() {
            KeyguardService.this.checkPermission();
            Objects.requireNonNull(KeyguardService.this.mKeyguardViewMediator);
        }

        /* JADX WARNING: Removed duplicated region for block: B:17:0x0064 A[Catch:{ all -> 0x008f }] */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x0067 A[Catch:{ all -> 0x008f }] */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x0089 A[Catch:{ all -> 0x008f }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onStartedGoingToSleep(int r12) {
            /*
                r11 = this;
                com.android.systemui.keyguard.KeyguardService r0 = com.android.systemui.keyguard.KeyguardService.this
                r0.checkPermission()
                com.android.systemui.keyguard.KeyguardService r0 = com.android.systemui.keyguard.KeyguardService.this
                com.android.systemui.keyguard.KeyguardViewMediator r0 = r0.mKeyguardViewMediator
                int r1 = android.view.WindowManagerPolicyConstants.translateSleepReasonToOffReason(r12)
                boolean r2 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG
                if (r2 == 0) goto L_0x002f
                java.util.Objects.requireNonNull(r0)
                java.lang.String r3 = "KeyguardViewMediator"
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                java.lang.String r5 = "onStartedGoingToSleep("
                r4.append(r5)
                r4.append(r1)
                java.lang.String r5 = ")"
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                android.util.Log.d(r3, r4)
            L_0x002f:
                monitor-enter(r0)
                r3 = 0
                r0.mDeviceInteractive = r3     // Catch:{ all -> 0x008f }
                r4 = 1
                r0.mGoingToSleep = r4     // Catch:{ all -> 0x008f }
                int r5 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()     // Catch:{ all -> 0x008f }
                com.android.internal.widget.LockPatternUtils r6 = r0.mLockPatternUtils     // Catch:{ all -> 0x008f }
                boolean r6 = r6.getPowerButtonInstantlyLocks(r5)     // Catch:{ all -> 0x008f }
                if (r6 != 0) goto L_0x004d
                com.android.internal.widget.LockPatternUtils r6 = r0.mLockPatternUtils     // Catch:{ all -> 0x008f }
                boolean r6 = r6.isSecure(r5)     // Catch:{ all -> 0x008f }
                if (r6 != 0) goto L_0x004b
                goto L_0x004d
            L_0x004b:
                r6 = r3
                goto L_0x004e
            L_0x004d:
                r6 = r4
            L_0x004e:
                int r7 = com.android.keyguard.KeyguardUpdateMonitor.getCurrentUser()     // Catch:{ all -> 0x008f }
                long r7 = r0.getLockTimeout(r7)     // Catch:{ all -> 0x008f }
                r0.mLockLater = r3     // Catch:{ all -> 0x008f }
                boolean r9 = r0.mShowing     // Catch:{ all -> 0x008f }
                if (r9 == 0) goto L_0x0067
                com.android.systemui.statusbar.policy.KeyguardStateController r9 = r0.mKeyguardStateController     // Catch:{ all -> 0x008f }
                boolean r9 = r9.isKeyguardGoingAway()     // Catch:{ all -> 0x008f }
                if (r9 != 0) goto L_0x0067
                r0.mPendingReset = r4     // Catch:{ all -> 0x008f }
                goto L_0x0085
            L_0x0067:
                r9 = 3
                if (r1 != r9) goto L_0x0070
                r9 = 0
                int r9 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
                if (r9 > 0) goto L_0x0075
            L_0x0070:
                r9 = 2
                if (r1 != r9) goto L_0x007b
                if (r6 != 0) goto L_0x007b
            L_0x0075:
                r0.doKeyguardLaterLocked(r7)     // Catch:{ all -> 0x008f }
                r0.mLockLater = r4     // Catch:{ all -> 0x008f }
                goto L_0x0085
            L_0x007b:
                com.android.internal.widget.LockPatternUtils r6 = r0.mLockPatternUtils     // Catch:{ all -> 0x008f }
                boolean r5 = r6.isLockScreenDisabled(r5)     // Catch:{ all -> 0x008f }
                if (r5 != 0) goto L_0x0085
                r0.mPendingLock = r4     // Catch:{ all -> 0x008f }
            L_0x0085:
                boolean r4 = r0.mPendingLock     // Catch:{ all -> 0x008f }
                if (r4 == 0) goto L_0x0091
                int r4 = r0.mLockSoundId     // Catch:{ all -> 0x008f }
                r0.playSound(r4)     // Catch:{ all -> 0x008f }
                goto L_0x0091
            L_0x008f:
                r11 = move-exception
                goto L_0x00d8
            L_0x0091:
                monitor-exit(r0)     // Catch:{ all -> 0x008f }
                com.android.keyguard.KeyguardUpdateMonitor r4 = r0.mUpdateMonitor
                java.util.Objects.requireNonNull(r4)
                com.android.keyguard.KeyguardUpdateMonitor$14 r4 = r4.mHandler
                r5 = 321(0x141, float:4.5E-43)
                android.os.Message r1 = r4.obtainMessage(r5, r1, r3)
                r4.sendMessage(r1)
                com.android.keyguard.KeyguardUpdateMonitor r1 = r0.mUpdateMonitor
                java.util.Objects.requireNonNull(r1)
                com.android.keyguard.KeyguardUpdateMonitor$14 r1 = r1.mHandler
                java.lang.Boolean r3 = java.lang.Boolean.FALSE
                r4 = 342(0x156, float:4.79E-43)
                android.os.Message r3 = r1.obtainMessage(r4, r3)
                r1.sendMessage(r3)
                if (r2 == 0) goto L_0x00bd
                java.lang.String r1 = "KeyguardViewMediator"
                java.lang.String r2 = "notifyStartedGoingToSleep"
                android.util.Log.d(r1, r2)
            L_0x00bd:
                com.android.systemui.keyguard.KeyguardViewMediator$9 r0 = r0.mHandler
                r1 = 17
                r0.sendEmptyMessage(r1)
                com.android.systemui.keyguard.KeyguardService r11 = com.android.systemui.keyguard.KeyguardService.this
                com.android.systemui.keyguard.KeyguardLifecyclesDispatcher r11 = r11.mKeyguardLifecyclesDispatcher
                r0 = 6
                java.util.Objects.requireNonNull(r11)
                com.android.systemui.keyguard.KeyguardLifecyclesDispatcher$1 r11 = r11.mHandler
                android.os.Message r11 = r11.obtainMessage(r0)
                r11.arg1 = r12
                r11.sendToTarget()
                return
            L_0x00d8:
                monitor-exit(r0)     // Catch:{ all -> 0x008f }
                throw r11
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardService.C08465.onStartedGoingToSleep(int):void");
        }

        public final void onStartedWakingUp(int i, boolean z) {
            Trace.beginSection("KeyguardService.mBinder#onStartedWakingUp");
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
            synchronized (keyguardViewMediator) {
                keyguardViewMediator.mDeviceInteractive = true;
                if (keyguardViewMediator.mPendingLock && !z) {
                    keyguardViewMediator.doKeyguardLocked((Bundle) null);
                }
                keyguardViewMediator.mAnimatingScreenOff = false;
                keyguardViewMediator.mDelayedShowingSequence++;
                keyguardViewMediator.mDelayedProfileShowingSequence++;
                boolean z2 = KeyguardViewMediator.DEBUG;
                if (z2) {
                    Log.d("KeyguardViewMediator", "onStartedWakingUp, seq = " + keyguardViewMediator.mDelayedShowingSequence);
                }
                if (z2) {
                    Log.d("KeyguardViewMediator", "notifyStartedWakingUp");
                }
                keyguardViewMediator.mHandler.sendEmptyMessage(14);
            }
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            synchronized (keyguardUpdateMonitor) {
                keyguardUpdateMonitor.mDeviceInteractive = true;
            }
            keyguardUpdateMonitor.mHandler.sendEmptyMessage(319);
            keyguardViewMediator.maybeSendUserPresentBroadcast();
            Trace.endSection();
            KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher = KeyguardService.this.mKeyguardLifecyclesDispatcher;
            Objects.requireNonNull(keyguardLifecyclesDispatcher);
            Message obtainMessage = keyguardLifecyclesDispatcher.mHandler.obtainMessage(4);
            obtainMessage.arg1 = i;
            obtainMessage.sendToTarget();
            Trace.endSection();
        }

        public final void onSystemKeyPressed(int i) {
            KeyguardService.this.checkPermission();
            Objects.requireNonNull(KeyguardService.this.mKeyguardViewMediator);
        }

        public final void onSystemReady() {
            Trace.beginSection("KeyguardService.mBinder#onSystemReady");
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            keyguardViewMediator.mHandler.obtainMessage(18).sendToTarget();
            Trace.endSection();
        }

        public final void setCurrentUser(int i) {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            boolean z = KeyguardUpdateMonitor.DEBUG;
            synchronized (KeyguardUpdateMonitor.class) {
                KeyguardUpdateMonitor.sCurrentUser = i;
            }
            synchronized (keyguardViewMediator) {
                keyguardViewMediator.notifyTrustedChangedLocked(keyguardViewMediator.mUpdateMonitor.getUserHasTrust(i));
            }
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(7:23|24|25|26|38|35|21) */
        /* JADX WARNING: Code restructure failed: missing block: B:37:0x0077, code lost:
            continue;
         */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x007f */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void setKeyguardEnabled(boolean r5) {
            /*
                r4 = this;
                com.android.systemui.keyguard.KeyguardService r0 = com.android.systemui.keyguard.KeyguardService.this
                r0.checkPermission()
                com.android.systemui.keyguard.KeyguardService r4 = com.android.systemui.keyguard.KeyguardService.this
                com.android.systemui.keyguard.KeyguardViewMediator r4 = r4.mKeyguardViewMediator
                java.util.Objects.requireNonNull(r4)
                monitor-enter(r4)
                boolean r0 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x0094 }
                if (r0 == 0) goto L_0x002d
                java.lang.String r1 = "KeyguardViewMediator"
                java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x0094 }
                r2.<init>()     // Catch:{ all -> 0x0094 }
                java.lang.String r3 = "setKeyguardEnabled("
                r2.append(r3)     // Catch:{ all -> 0x0094 }
                r2.append(r5)     // Catch:{ all -> 0x0094 }
                java.lang.String r3 = ")"
                r2.append(r3)     // Catch:{ all -> 0x0094 }
                java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x0094 }
                android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0094 }
            L_0x002d:
                r4.mExternallyEnabled = r5     // Catch:{ all -> 0x0094 }
                r1 = 1
                if (r5 != 0) goto L_0x0049
                boolean r2 = r4.mShowing     // Catch:{ all -> 0x0094 }
                if (r2 == 0) goto L_0x0049
                if (r0 == 0) goto L_0x0040
                java.lang.String r5 = "KeyguardViewMediator"
                java.lang.String r0 = "remembering to reshow, hiding keyguard, disabling status bar expansion"
                android.util.Log.d(r5, r0)     // Catch:{ all -> 0x0094 }
            L_0x0040:
                r4.mNeedToReshowWhenReenabled = r1     // Catch:{ all -> 0x0094 }
                r4.updateInputRestrictedLocked()     // Catch:{ all -> 0x0094 }
                r4.hideLocked()     // Catch:{ all -> 0x0094 }
                goto L_0x0092
            L_0x0049:
                if (r5 == 0) goto L_0x0092
                boolean r5 = r4.mNeedToReshowWhenReenabled     // Catch:{ all -> 0x0094 }
                if (r5 == 0) goto L_0x0092
                if (r0 == 0) goto L_0x0058
                java.lang.String r5 = "KeyguardViewMediator"
                java.lang.String r2 = "previously hidden, reshowing, reenabling status bar expansion"
                android.util.Log.d(r5, r2)     // Catch:{ all -> 0x0094 }
            L_0x0058:
                r5 = 0
                r4.mNeedToReshowWhenReenabled = r5     // Catch:{ all -> 0x0094 }
                r4.updateInputRestrictedLocked()     // Catch:{ all -> 0x0094 }
                r5 = 0
                r4.showLocked(r5)     // Catch:{ all -> 0x0094 }
                r4.mWaitingUntilKeyguardVisible = r1     // Catch:{ all -> 0x0094 }
                com.android.systemui.keyguard.KeyguardViewMediator$9 r5 = r4.mHandler     // Catch:{ all -> 0x0094 }
                r1 = 8
                r2 = 2000(0x7d0, double:9.88E-321)
                r5.sendEmptyMessageDelayed(r1, r2)     // Catch:{ all -> 0x0094 }
                if (r0 == 0) goto L_0x0077
                java.lang.String r5 = "KeyguardViewMediator"
                java.lang.String r0 = "waiting until mWaitingUntilKeyguardVisible is false"
                android.util.Log.d(r5, r0)     // Catch:{ all -> 0x0094 }
            L_0x0077:
                boolean r5 = r4.mWaitingUntilKeyguardVisible     // Catch:{ all -> 0x0094 }
                if (r5 == 0) goto L_0x0087
                r4.wait()     // Catch:{ InterruptedException -> 0x007f }
                goto L_0x0077
            L_0x007f:
                java.lang.Thread r5 = java.lang.Thread.currentThread()     // Catch:{ all -> 0x0094 }
                r5.interrupt()     // Catch:{ all -> 0x0094 }
                goto L_0x0077
            L_0x0087:
                boolean r5 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x0094 }
                if (r5 == 0) goto L_0x0092
                java.lang.String r5 = "KeyguardViewMediator"
                java.lang.String r0 = "done waiting for mWaitingUntilKeyguardVisible"
                android.util.Log.d(r5, r0)     // Catch:{ all -> 0x0094 }
            L_0x0092:
                monitor-exit(r4)     // Catch:{ all -> 0x0094 }
                return
            L_0x0094:
                r5 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x0094 }
                throw r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardService.C08465.setKeyguardEnabled(boolean):void");
        }

        public final void setOccluded(boolean z, boolean z2) {
            Trace.beginSection("KeyguardService.mBinder#setOccluded");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setOccluded(z, z2);
            Trace.endSection();
        }

        public final void setSwitchingUser(boolean z) {
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardViewMediator.mUpdateMonitor;
            Objects.requireNonNull(keyguardUpdateMonitor);
            keyguardUpdateMonitor.mSwitchingUser = z;
            keyguardUpdateMonitor.mHandler.post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(keyguardUpdateMonitor, 0));
        }

        @Deprecated
        public final void startKeyguardExitAnimation(long j, long j2) {
            Trace.beginSection("KeyguardService.mBinder#startKeyguardExitAnimation");
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            keyguardViewMediator.startKeyguardExitAnimation(0, j, j2, (RemoteAnimationTarget[]) null, (RemoteAnimationTarget[]) null, (RemoteAnimationTarget[]) null, (IRemoteAnimationFinishedCallback) null);
            Trace.endSection();
        }

        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        public final void verifyUnlock(com.android.internal.policy.IKeyguardExitCallback r4) {
            /*
                r3 = this;
                java.lang.String r0 = "KeyguardService.mBinder#verifyUnlock"
                android.os.Trace.beginSection(r0)
                com.android.systemui.keyguard.KeyguardService r0 = com.android.systemui.keyguard.KeyguardService.this
                r0.checkPermission()
                com.android.systemui.keyguard.KeyguardService r3 = com.android.systemui.keyguard.KeyguardService.this
                com.android.systemui.keyguard.KeyguardViewMediator r3 = r3.mKeyguardViewMediator
                java.util.Objects.requireNonNull(r3)
                java.lang.String r0 = "KeyguardViewMediator#verifyUnlock"
                android.os.Trace.beginSection(r0)
                monitor-enter(r3)
                boolean r0 = com.android.systemui.keyguard.KeyguardViewMediator.DEBUG     // Catch:{ all -> 0x0079 }
                if (r0 == 0) goto L_0x0023
                java.lang.String r1 = "KeyguardViewMediator"
                java.lang.String r2 = "verifyUnlock"
                android.util.Log.d(r1, r2)     // Catch:{ all -> 0x0079 }
            L_0x0023:
                boolean r1 = r3.shouldWaitForProvisioning()     // Catch:{ all -> 0x0079 }
                r2 = 0
                if (r1 == 0) goto L_0x0040
                if (r0 == 0) goto L_0x0033
                java.lang.String r0 = "KeyguardViewMediator"
                java.lang.String r1 = "ignoring because device isn't provisioned"
                android.util.Log.d(r0, r1)     // Catch:{ all -> 0x0079 }
            L_0x0033:
                r4.onKeyguardExitResult(r2)     // Catch:{ RemoteException -> 0x0037 }
                goto L_0x0087
            L_0x0037:
                r4 = move-exception
                java.lang.String r0 = "KeyguardViewMediator"
                java.lang.String r1 = "Failed to call onKeyguardExitResult(false)"
                android.util.Slog.w(r0, r1, r4)     // Catch:{ all -> 0x0079 }
                goto L_0x0087
            L_0x0040:
                boolean r0 = r3.mExternallyEnabled     // Catch:{ all -> 0x0079 }
                if (r0 == 0) goto L_0x0059
                java.lang.String r0 = "KeyguardViewMediator"
                java.lang.String r1 = "verifyUnlock called when not externally disabled"
                android.util.Log.w(r0, r1)     // Catch:{ all -> 0x0079 }
                r4.onKeyguardExitResult(r2)     // Catch:{ RemoteException -> 0x0050 }
                goto L_0x0087
            L_0x0050:
                r4 = move-exception
                java.lang.String r0 = "KeyguardViewMediator"
                java.lang.String r1 = "Failed to call onKeyguardExitResult(false)"
                android.util.Slog.w(r0, r1, r4)     // Catch:{ all -> 0x0079 }
                goto L_0x0087
            L_0x0059:
                boolean r0 = r3.isSecure()     // Catch:{ all -> 0x0079 }
                if (r0 != 0) goto L_0x007b
                r0 = 1
                r3.mExternallyEnabled = r0     // Catch:{ all -> 0x0079 }
                r3.mNeedToReshowWhenReenabled = r2     // Catch:{ all -> 0x0079 }
                monitor-enter(r3)     // Catch:{ all -> 0x0079 }
                r3.updateInputRestrictedLocked()     // Catch:{ all -> 0x0076 }
                monitor-exit(r3)     // Catch:{ all -> 0x0076 }
                r4.onKeyguardExitResult(r0)     // Catch:{ RemoteException -> 0x006d }
                goto L_0x0087
            L_0x006d:
                r4 = move-exception
                java.lang.String r0 = "KeyguardViewMediator"
                java.lang.String r1 = "Failed to call onKeyguardExitResult(false)"
                android.util.Slog.w(r0, r1, r4)     // Catch:{ all -> 0x0079 }
                goto L_0x0087
            L_0x0076:
                r4 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0076 }
                throw r4     // Catch:{ all -> 0x0079 }
            L_0x0079:
                r4 = move-exception
                goto L_0x008f
            L_0x007b:
                r4.onKeyguardExitResult(r2)     // Catch:{ RemoteException -> 0x007f }
                goto L_0x0087
            L_0x007f:
                r4 = move-exception
                java.lang.String r0 = "KeyguardViewMediator"
                java.lang.String r1 = "Failed to call onKeyguardExitResult(false)"
                android.util.Slog.w(r0, r1, r4)     // Catch:{ all -> 0x0079 }
            L_0x0087:
                monitor-exit(r3)     // Catch:{ all -> 0x0079 }
                android.os.Trace.endSection()
                android.os.Trace.endSection()
                return
            L_0x008f:
                monitor-exit(r3)     // Catch:{ all -> 0x0079 }
                throw r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardService.C08465.verifyUnlock(com.android.internal.policy.IKeyguardExitCallback):void");
        }
    };
    public final C08432 mExitAnimationRunner = new IRemoteAnimationRunner.Stub() {
        public final void onAnimationCancelled() {
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            Trace.beginSection("KeyguardViewMediator#cancelKeyguardExitAnimation");
            keyguardViewMediator.mHandler.sendMessage(keyguardViewMediator.mHandler.obtainMessage(19));
            Trace.endSection();
        }

        public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            Trace.beginSection("mExitAnimationRunner.onAnimationStart#startKeyguardExitAnimation");
            KeyguardService.this.checkPermission();
            KeyguardViewMediator keyguardViewMediator = KeyguardService.this.mKeyguardViewMediator;
            Objects.requireNonNull(keyguardViewMediator);
            keyguardViewMediator.startKeyguardExitAnimation(i, 0, 0, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            Trace.endSection();
        }
    };
    public final KeyguardLifecyclesDispatcher mKeyguardLifecyclesDispatcher;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final C08443 mOccludeAnimation = new IRemoteTransition.Stub() {
        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
        }

        public final void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
            transaction.apply();
            KeyguardService.this.mBinder.setOccluded(true, true);
            iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
        }
    };
    public final ShellTransitions mShellTransitions;
    public final C08454 mUnoccludeAnimation = new IRemoteTransition.Stub() {
        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
        }

        public final void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
            transaction.apply();
            KeyguardService.this.mBinder.setOccluded(false, true);
            iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
        }
    };

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0091, code lost:
        if (r7 != 4) goto L_0x0097;
     */
    /* renamed from: -$$Nest$smwrap  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.view.RemoteAnimationTarget[] m201$$Nest$smwrap(android.window.TransitionInfo r24, boolean r25) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = r1
        L_0x0007:
            java.util.List r3 = r24.getChanges()
            int r3 = r3.size()
            if (r2 >= r3) goto L_0x00e8
            java.util.List r3 = r24.getChanges()
            java.lang.Object r3 = r3.get(r2)
            android.window.TransitionInfo$Change r3 = (android.window.TransitionInfo.Change) r3
            int r3 = r3.getFlags()
            r4 = 2
            r3 = r3 & r4
            r5 = 1
            if (r3 == 0) goto L_0x0028
            r3 = r25
            r6 = r5
            goto L_0x002b
        L_0x0028:
            r3 = r25
            r6 = r1
        L_0x002b:
            if (r3 == r6) goto L_0x002f
            goto L_0x00e4
        L_0x002f:
            java.util.List r6 = r24.getChanges()
            java.lang.Object r6 = r6.get(r2)
            android.window.TransitionInfo$Change r6 = (android.window.TransitionInfo.Change) r6
            android.app.ActivityManager$RunningTaskInfo r22 = r6.getTaskInfo()
            if (r22 == 0) goto L_0x0046
            android.app.ActivityManager$RunningTaskInfo r7 = r6.getTaskInfo()
            int r7 = r7.taskId
            goto L_0x0047
        L_0x0046:
            r7 = -1
        L_0x0047:
            r8 = r7
            r7 = 0
            if (r22 == 0) goto L_0x0067
            android.content.res.Configuration r9 = r22.getConfiguration()
            if (r9 == 0) goto L_0x005b
            android.app.ActivityManager$RunningTaskInfo r7 = r6.getTaskInfo()
            android.content.res.Configuration r7 = r7.getConfiguration()
            android.app.WindowConfiguration r7 = r7.windowConfiguration
        L_0x005b:
            android.app.ActivityManager$RunningTaskInfo r9 = r6.getTaskInfo()
            boolean r9 = r9.isRunning
            r9 = r9 ^ r5
            r18 = r7
            r19 = r9
            goto L_0x006b
        L_0x0067:
            r19 = r5
            r18 = r7
        L_0x006b:
            android.graphics.Rect r11 = new android.graphics.Rect
            android.graphics.Rect r7 = r6.getEndAbsBounds()
            r11.<init>(r7)
            android.graphics.Point r7 = r6.getEndRelOffset()
            int r7 = r7.x
            android.graphics.Point r9 = r6.getEndRelOffset()
            int r9 = r9.y
            r11.offsetTo(r7, r9)
            android.view.RemoteAnimationTarget r10 = new android.view.RemoteAnimationTarget
            int r7 = r6.getMode()
            r9 = 4
            if (r7 == r5) goto L_0x0096
            if (r7 == r4) goto L_0x0094
            r12 = 3
            if (r7 == r12) goto L_0x0096
            if (r7 == r9) goto L_0x0094
            goto L_0x0097
        L_0x0094:
            r4 = r5
            goto L_0x0097
        L_0x0096:
            r4 = r1
        L_0x0097:
            android.view.SurfaceControl r16 = r6.getLeash()
            int r7 = r6.getFlags()
            r7 = r7 & r9
            if (r7 != 0) goto L_0x00ab
            int r7 = r6.getFlags()
            r7 = r7 & r5
            if (r7 == 0) goto L_0x00aa
            goto L_0x00ab
        L_0x00aa:
            r5 = r1
        L_0x00ab:
            r12 = 0
            android.graphics.Rect r7 = new android.graphics.Rect
            r13 = r7
            r7.<init>(r1, r1, r1, r1)
            java.util.List r7 = r24.getChanges()
            int r7 = r7.size()
            int r14 = r7 - r2
            android.graphics.Point r7 = new android.graphics.Point
            r15 = r7
            r7.<init>()
            android.graphics.Rect r7 = new android.graphics.Rect
            r17 = r7
            android.graphics.Rect r9 = r6.getEndAbsBounds()
            r7.<init>(r9)
            r20 = 0
            android.graphics.Rect r21 = r6.getStartAbsBounds()
            r23 = 0
            r7 = r10
            r9 = r4
            r4 = r10
            r10 = r16
            r6 = r11
            r11 = r5
            r16 = r6
            r7.<init>(r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)
            r0.add(r4)
        L_0x00e4:
            int r2 = r2 + 1
            goto L_0x0007
        L_0x00e8:
            int r1 = r0.size()
            android.view.RemoteAnimationTarget[] r1 = new android.view.RemoteAnimationTarget[r1]
            java.lang.Object[] r0 = r0.toArray(r1)
            android.view.RemoteAnimationTarget[] r0 = (android.view.RemoteAnimationTarget[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardService.m201$$Nest$smwrap(android.window.TransitionInfo, boolean):android.view.RemoteAnimationTarget[]");
    }

    static {
        boolean z;
        boolean z2 = true;
        int i = SystemProperties.getInt("persist.wm.enable_remote_keyguard_animation", 1);
        if (i >= 1) {
            z = true;
        } else {
            z = false;
        }
        sEnableRemoteKeyguardGoingAwayAnimation = z;
        if (i < 2) {
            z2 = false;
        }
        sEnableRemoteKeyguardOccludeAnimation = z2;
    }

    public KeyguardService(KeyguardViewMediator keyguardViewMediator, KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher, ShellTransitions shellTransitions) {
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardLifecyclesDispatcher = keyguardLifecyclesDispatcher;
        this.mShellTransitions = shellTransitions;
    }

    public final void checkPermission() {
        if (Binder.getCallingUid() != 1000 && getBaseContext().checkCallingOrSelfPermission("android.permission.CONTROL_KEYGUARD") != 0) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Caller needs permission 'android.permission.CONTROL_KEYGUARD' to call ");
            m.append(Debug.getCaller());
            Log.w("KeyguardService", m.toString());
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Access denied to process: ");
            m2.append(Binder.getCallingPid());
            m2.append(", must have permission ");
            m2.append("android.permission.CONTROL_KEYGUARD");
            throw new SecurityException(m2.toString());
        }
    }

    public final void onCreate() {
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
        if (this.mShellTransitions == null || !Transitions.ENABLE_SHELL_TRANSITIONS) {
            RemoteAnimationDefinition remoteAnimationDefinition = new RemoteAnimationDefinition();
            if (sEnableRemoteKeyguardGoingAwayAnimation) {
                RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(this.mExitAnimationRunner, 0, 0);
                remoteAnimationDefinition.addRemoteAnimation(20, remoteAnimationAdapter);
                remoteAnimationDefinition.addRemoteAnimation(21, remoteAnimationAdapter);
            }
            if (sEnableRemoteKeyguardOccludeAnimation) {
                KeyguardViewMediator keyguardViewMediator = this.mKeyguardViewMediator;
                Objects.requireNonNull(keyguardViewMediator);
                remoteAnimationDefinition.addRemoteAnimation(22, new RemoteAnimationAdapter(keyguardViewMediator.mOccludeAnimationRunner, 0, 0));
                KeyguardViewMediator keyguardViewMediator2 = this.mKeyguardViewMediator;
                Objects.requireNonNull(keyguardViewMediator2);
                remoteAnimationDefinition.addRemoteAnimation(23, new RemoteAnimationAdapter(keyguardViewMediator2.mUnoccludeAnimationRunner, 0, 0));
            }
            ActivityTaskManager.getInstance().registerRemoteAnimationsForDisplay(0, remoteAnimationDefinition);
            return;
        }
        if (sEnableRemoteKeyguardGoingAwayAnimation) {
            Slog.d("KeyguardService", "KeyguardService registerRemote: TRANSIT_KEYGUARD_GOING_AWAY");
            TransitionFilter transitionFilter = new TransitionFilter();
            transitionFilter.mFlags = 256;
            ShellTransitions shellTransitions = this.mShellTransitions;
            final C08432 r5 = this.mExitAnimationRunner;
            shellTransitions.registerRemote(transitionFilter, new RemoteTransition(new IRemoteTransition.Stub() {
                public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
                }

                public final void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
                    Slog.d("KeyguardService", "Starts IRemoteAnimationRunner: info=" + transitionInfo);
                    int i = 0;
                    RemoteAnimationTarget[] r3 = KeyguardService.m201$$Nest$smwrap(transitionInfo, false);
                    RemoteAnimationTarget[] r4 = KeyguardService.m201$$Nest$smwrap(transitionInfo, true);
                    RemoteAnimationTarget[] remoteAnimationTargetArr = new RemoteAnimationTarget[0];
                    for (TransitionInfo.Change leash : transitionInfo.getChanges()) {
                        transaction.setAlpha(leash.getLeash(), 1.0f);
                    }
                    transaction.apply();
                    IRemoteAnimationRunner iRemoteAnimationRunner = r5;
                    int type = transitionInfo.getType();
                    int flags = transitionInfo.getFlags();
                    if (type == 7 || (flags & 256) != 0) {
                        if (r3.length == 0) {
                            i = 21;
                        } else {
                            i = 20;
                        }
                    } else if (type == 8) {
                        i = 22;
                    } else if (type == 9) {
                        i = 23;
                    } else {
                        Slog.d("KeyguardService", "Unexpected transit type: " + type);
                    }
                    iRemoteAnimationRunner.onAnimationStart(i, r3, r4, remoteAnimationTargetArr, new IRemoteAnimationFinishedCallback.Stub() {
                        public final void onAnimationFinished() throws RemoteException {
                            Slog.d("KeyguardService", "Finish IRemoteAnimationRunner.");
                            iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
                        }
                    });
                }
            }, getIApplicationThread()));
        }
        if (sEnableRemoteKeyguardOccludeAnimation) {
            Slog.d("KeyguardService", "KeyguardService registerRemote: TRANSIT_KEYGUARD_(UN)OCCLUDE");
            TransitionFilter transitionFilter2 = new TransitionFilter();
            transitionFilter2.mFlags = 64;
            TransitionFilter.Requirement[] requirementArr = {new TransitionFilter.Requirement(), new TransitionFilter.Requirement()};
            transitionFilter2.mRequirements = requirementArr;
            requirementArr[0].mMustBeIndependent = false;
            requirementArr[0].mFlags = 64;
            requirementArr[0].mModes = new int[]{1, 3};
            requirementArr[1].mNot = true;
            requirementArr[1].mMustBeIndependent = false;
            requirementArr[1].mFlags = 64;
            requirementArr[1].mModes = new int[]{2, 4};
            this.mShellTransitions.registerRemote(transitionFilter2, new RemoteTransition(this.mOccludeAnimation, getIApplicationThread()));
            TransitionFilter transitionFilter3 = new TransitionFilter();
            transitionFilter3.mFlags = 64;
            TransitionFilter.Requirement[] requirementArr2 = {new TransitionFilter.Requirement(), new TransitionFilter.Requirement()};
            transitionFilter3.mRequirements = requirementArr2;
            requirementArr2[1].mMustBeIndependent = false;
            requirementArr2[1].mModes = new int[]{2, 4};
            requirementArr2[1].mMustBeTask = true;
            requirementArr2[0].mNot = true;
            requirementArr2[0].mMustBeIndependent = false;
            requirementArr2[0].mFlags = 64;
            requirementArr2[0].mModes = new int[]{1, 3};
            this.mShellTransitions.registerRemote(transitionFilter3, new RemoteTransition(this.mUnoccludeAnimation, getIApplicationThread()));
        }
    }

    /* JADX WARNING: type inference failed for: r0v1, types: [com.android.systemui.keyguard.KeyguardService$5, android.os.IBinder] */
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }
}
