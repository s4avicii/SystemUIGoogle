package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Calendar;
import java.util.Objects;

public final class DozeUi implements DozeMachine.Part {
    public final boolean mCanAnimateTransition;
    public final Context mContext;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final Handler mHandler;
    public final DozeHost mHost;
    public final C07811 mKeyguardVisibilityCallback;
    public long mLastTimeTickElapsed = 0;
    public DozeMachine mMachine;
    public final StatusBarStateController mStatusBarStateController;
    public final AlarmTimeout mTimeTicker;
    public final WakeLock mWakeLock;

    public final void scheduleTimeTick() {
        AlarmTimeout alarmTimeout = this.mTimeTicker;
        Objects.requireNonNull(alarmTimeout);
        if (!alarmTimeout.mScheduled) {
            long currentTimeMillis = System.currentTimeMillis();
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(currentTimeMillis);
            instance.set(14, 0);
            instance.set(13, 0);
            instance.add(12, 1);
            long timeInMillis = instance.getTimeInMillis() - System.currentTimeMillis();
            if (this.mTimeTicker.schedule(timeInMillis)) {
                DozeLog dozeLog = this.mDozeLog;
                long j = timeInMillis + currentTimeMillis;
                Objects.requireNonNull(dozeLog);
                DozeLogger dozeLogger = dozeLog.mLogger;
                Objects.requireNonNull(dozeLogger);
                LogBuffer logBuffer = dozeLogger.buffer;
                LogLevel logLevel = LogLevel.DEBUG;
                DozeLogger$logTimeTickScheduled$2 dozeLogger$logTimeTickScheduled$2 = DozeLogger$logTimeTickScheduled$2.INSTANCE;
                Objects.requireNonNull(logBuffer);
                if (!logBuffer.frozen) {
                    LogMessageImpl obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logTimeTickScheduled$2);
                    obtain.long1 = currentTimeMillis;
                    obtain.long2 = j;
                    logBuffer.push(obtain);
                }
            }
            this.mLastTimeTickElapsed = SystemClock.elapsedRealtime();
        }
    }

    public DozeUi(Context context, AlarmManager alarmManager, WakeLock wakeLock, DozeHost dozeHost, Handler handler, DozeParameters dozeParameters, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarStateController statusBarStateController, DozeLog dozeLog) {
        C07811 r0 = new KeyguardUpdateMonitorCallback() {
            public final void onTimeChanged() {
            }
        };
        this.mKeyguardVisibilityCallback = r0;
        this.mContext = context;
        this.mWakeLock = wakeLock;
        this.mHost = dozeHost;
        this.mHandler = handler;
        this.mCanAnimateTransition = !dozeParameters.getDisplayNeedsBlanking();
        this.mDozeParameters = dozeParameters;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new DozeUi$$ExternalSyntheticLambda0(this), "doze_time_tick", handler);
        keyguardUpdateMonitor.registerCallback(r0);
        this.mDozeLog = dozeLog;
        this.mStatusBarStateController = statusBarStateController;
    }

    public final void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        boolean z;
        int ordinal = state2.ordinal();
        boolean z2 = false;
        if (ordinal != 1) {
            if (ordinal != 2) {
                if (ordinal != 3) {
                    if (ordinal != 4) {
                        switch (ordinal) {
                            case 8:
                                this.mHost.stopDozing();
                                AlarmTimeout alarmTimeout = this.mTimeTicker;
                                Objects.requireNonNull(alarmTimeout);
                                if (alarmTimeout.mScheduled) {
                                    verifyLastTimeTick();
                                    this.mTimeTicker.cancel();
                                    break;
                                }
                                break;
                            case 9:
                                break;
                            case 10:
                                scheduleTimeTick();
                                break;
                            case QSTileImpl.C1034H.STALE /*11*/:
                                break;
                        }
                    } else {
                        scheduleTimeTick();
                        DozeMachine dozeMachine = this.mMachine;
                        Objects.requireNonNull(dozeMachine);
                        Assert.isMainThread();
                        DozeMachine.State state3 = dozeMachine.mState;
                        if (state3 == DozeMachine.State.DOZE_REQUEST_PULSE || state3 == DozeMachine.State.DOZE_PULSING || state3 == DozeMachine.State.DOZE_PULSING_BRIGHT || state3 == DozeMachine.State.DOZE_PULSE_DONE) {
                            z = true;
                        } else {
                            z = false;
                        }
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("must be in pulsing state, but is ");
                        m.append(dozeMachine.mState);
                        Preconditions.checkState(z, m.toString());
                        final int i = dozeMachine.mPulseReason;
                        this.mHost.pulseWhileDozing(new DozeHost.PulseCallback() {
                            public final void onPulseFinished() {
                                DozeUi.this.mMachine.requestState(DozeMachine.State.DOZE_PULSE_DONE);
                            }

                            public final void onPulseStarted() {
                                DozeMachine.State state;
                                try {
                                    DozeMachine dozeMachine = DozeUi.this.mMachine;
                                    if (i == 8) {
                                        state = DozeMachine.State.DOZE_PULSING_BRIGHT;
                                    } else {
                                        state = DozeMachine.State.DOZE_PULSING;
                                    }
                                    dozeMachine.requestState(state);
                                } catch (IllegalStateException unused) {
                                }
                            }
                        }, i);
                    }
                }
                if (state == DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE) {
                    this.mHost.dozeTimeTick();
                    Handler handler = this.mHandler;
                    WakeLock wakeLock = this.mWakeLock;
                    DozeHost dozeHost = this.mHost;
                    Objects.requireNonNull(dozeHost);
                    handler.postDelayed(wakeLock.wrap(new DozeUi$$ExternalSyntheticLambda1(dozeHost, 0)), 500);
                }
                scheduleTimeTick();
            }
            AlarmTimeout alarmTimeout2 = this.mTimeTicker;
            Objects.requireNonNull(alarmTimeout2);
            if (alarmTimeout2.mScheduled) {
                verifyLastTimeTick();
                this.mTimeTicker.cancel();
            }
        } else {
            this.mHost.startDozing();
        }
        switch (state2.ordinal()) {
            case 4:
            case 5:
            case FalsingManager.VERSION /*6*/:
            case 7:
                this.mHost.setAnimateWakeup(true);
                return;
            case 8:
                return;
            default:
                DozeHost dozeHost2 = this.mHost;
                if (this.mCanAnimateTransition && this.mDozeParameters.getAlwaysOn()) {
                    z2 = true;
                }
                dozeHost2.setAnimateWakeup(z2);
                return;
        }
    }

    public final void verifyLastTimeTick() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mLastTimeTickElapsed;
        if (elapsedRealtime > 90000) {
            String formatShortElapsedTime = Formatter.formatShortElapsedTime(this.mContext, elapsedRealtime);
            DozeLog dozeLog = this.mDozeLog;
            Objects.requireNonNull(dozeLog);
            DozeLogger dozeLogger = dozeLog.mLogger;
            Objects.requireNonNull(dozeLogger);
            LogBuffer logBuffer = dozeLogger.buffer;
            LogLevel logLevel = LogLevel.ERROR;
            DozeLogger$logMissedTick$2 dozeLogger$logMissedTick$2 = DozeLogger$logMissedTick$2.INSTANCE;
            Objects.requireNonNull(logBuffer);
            if (!logBuffer.frozen) {
                LogMessageImpl obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logMissedTick$2);
                obtain.str1 = formatShortElapsedTime;
                logBuffer.push(obtain);
            }
            Log.e("DozeMachine", "Missed AOD time tick by " + formatShortElapsedTime);
        }
    }

    public final void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }
}
