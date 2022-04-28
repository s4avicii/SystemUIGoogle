package com.android.systemui.power;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Binder;
import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Temperature;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import android.view.WindowManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda6;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda10;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Future;

public class PowerUI extends CoreStartable implements CommandQueue.Callbacks {
    public static final boolean DEBUG = Log.isLoggable("PowerUI", 3);
    public static final long SIX_HOURS_MILLIS = Duration.ofHours(6).toMillis();
    @VisibleForTesting
    public int mBatteryLevel = 100;
    @VisibleForTesting
    public int mBatteryStatus = 1;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final CommandQueue mCommandQueue;
    @VisibleForTesting
    public BatteryStateSnapshot mCurrentBatteryStateSnapshot;
    public boolean mEnableSkinTemperatureWarning;
    public boolean mEnableUsbTemperatureAlarm;
    public final EnhancedEstimates mEnhancedEstimates;
    public final Handler mHandler = new Handler();
    public int mInvalidCharger = 0;
    @VisibleForTesting
    public BatteryStateSnapshot mLastBatteryStateSnapshot;
    public final Configuration mLastConfiguration = new Configuration();
    public Future mLastShowWarningTask;
    public int mLowBatteryAlertCloseLevel;
    public final int[] mLowBatteryReminderLevels = new int[2];
    @VisibleForTesting
    public boolean mLowWarningShownThisChargeCycle;
    public InattentiveSleepWarningView mOverlayView;
    public int mPlugType = 0;
    public final PowerManager mPowerManager;
    @VisibleForTesting
    public final Receiver mReceiver = new Receiver();
    public long mScreenOffTime = -1;
    @VisibleForTesting
    public boolean mSevereWarningShownThisChargeCycle;
    public IThermalEventListener mSkinThermalEventListener;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    @VisibleForTesting
    public IThermalService mThermalService;
    public IThermalEventListener mUsbThermalEventListener;
    public final WarningsUI mWarnings;

    @VisibleForTesting
    public final class Receiver extends BroadcastReceiver {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mHasReceivedBattery = false;

        public Receiver() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:31:0x019b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onReceive(android.content.Context r14, android.content.Intent r15) {
            /*
                r13 = this;
                java.lang.String r14 = r15.getAction()
                java.lang.String r0 = "android.os.action.POWER_SAVE_MODE_CHANGED"
                boolean r0 = r0.equals(r14)
                if (r0 == 0) goto L_0x0016
                com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda0 r14 = new com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda0
                r14.<init>(r13)
                androidx.recyclerview.R$dimen.postOnBackgroundThread(r14)
                goto L_0x01f8
            L_0x0016:
                java.lang.String r0 = "android.intent.action.BATTERY_CHANGED"
                boolean r0 = r0.equals(r14)
                java.lang.String r1 = "PowerUI"
                if (r0 == 0) goto L_0x01b3
                r14 = 1
                r13.mHasReceivedBattery = r14
                com.android.systemui.power.PowerUI r0 = com.android.systemui.power.PowerUI.this
                int r2 = r0.mBatteryLevel
                r3 = 100
                java.lang.String r4 = "level"
                int r3 = r15.getIntExtra(r4, r3)
                r0.mBatteryLevel = r3
                com.android.systemui.power.PowerUI r0 = com.android.systemui.power.PowerUI.this
                int r3 = r0.mBatteryStatus
                java.lang.String r4 = "status"
                int r4 = r15.getIntExtra(r4, r14)
                r0.mBatteryStatus = r4
                com.android.systemui.power.PowerUI r0 = com.android.systemui.power.PowerUI.this
                int r4 = r0.mPlugType
                java.lang.String r5 = "plugged"
                int r5 = r15.getIntExtra(r5, r14)
                r0.mPlugType = r5
                com.android.systemui.power.PowerUI r0 = com.android.systemui.power.PowerUI.this
                int r5 = r0.mInvalidCharger
                r6 = 0
                java.lang.String r7 = "invalid_charger"
                int r15 = r15.getIntExtra(r7, r6)
                r0.mInvalidCharger = r15
                com.android.systemui.power.PowerUI r15 = com.android.systemui.power.PowerUI.this
                com.android.systemui.power.BatteryStateSnapshot r0 = r15.mCurrentBatteryStateSnapshot
                r15.mLastBatteryStateSnapshot = r0
                int r0 = r15.mPlugType
                if (r0 == 0) goto L_0x0063
                r0 = r14
                goto L_0x0064
            L_0x0063:
                r0 = r6
            L_0x0064:
                if (r4 == 0) goto L_0x0068
                r7 = r14
                goto L_0x0069
            L_0x0068:
                r7 = r6
            L_0x0069:
                int r15 = r15.findBatteryLevelBucket(r2)
                com.android.systemui.power.PowerUI r8 = com.android.systemui.power.PowerUI.this
                int r9 = r8.mBatteryLevel
                int r8 = r8.findBatteryLevelBucket(r9)
                boolean r9 = com.android.systemui.power.PowerUI.DEBUG
                if (r9 == 0) goto L_0x0156
                java.lang.String r10 = "buckets   ....."
                java.lang.StringBuilder r10 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r10)
                com.android.systemui.power.PowerUI r11 = com.android.systemui.power.PowerUI.this
                int r11 = r11.mLowBatteryAlertCloseLevel
                r10.append(r11)
                java.lang.String r11 = " .. "
                r10.append(r11)
                com.android.systemui.power.PowerUI r12 = com.android.systemui.power.PowerUI.this
                int[] r12 = r12.mLowBatteryReminderLevels
                r6 = r12[r6]
                r10.append(r6)
                r10.append(r11)
                com.android.systemui.power.PowerUI r6 = com.android.systemui.power.PowerUI.this
                int[] r6 = r6.mLowBatteryReminderLevels
                r6 = r6[r14]
                r10.append(r6)
                java.lang.String r6 = r10.toString()
                android.util.Slog.d(r1, r6)
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r10 = "level          "
                r6.append(r10)
                r6.append(r2)
                java.lang.String r2 = " --> "
                r6.append(r2)
                com.android.systemui.power.PowerUI r10 = com.android.systemui.power.PowerUI.this
                int r10 = r10.mBatteryLevel
                r6.append(r10)
                java.lang.String r6 = r6.toString()
                android.util.Slog.d(r1, r6)
                java.lang.StringBuilder r6 = new java.lang.StringBuilder
                r6.<init>()
                java.lang.String r10 = "status         "
                r6.append(r10)
                r6.append(r3)
                r6.append(r2)
                com.android.systemui.power.PowerUI r3 = com.android.systemui.power.PowerUI.this
                int r3 = r3.mBatteryStatus
                r6.append(r3)
                java.lang.String r3 = r6.toString()
                android.util.Slog.d(r1, r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r6 = "plugType       "
                r3.append(r6)
                r3.append(r4)
                r3.append(r2)
                com.android.systemui.power.PowerUI r4 = com.android.systemui.power.PowerUI.this
                int r4 = r4.mPlugType
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                android.util.Slog.d(r1, r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "invalidCharger "
                r3.append(r4)
                r3.append(r5)
                r3.append(r2)
                com.android.systemui.power.PowerUI r4 = com.android.systemui.power.PowerUI.this
                int r4 = r4.mInvalidCharger
                r3.append(r4)
                java.lang.String r3 = r3.toString()
                android.util.Slog.d(r1, r3)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder
                r3.<init>()
                java.lang.String r4 = "bucket         "
                r3.append(r4)
                r3.append(r15)
                r3.append(r2)
                r3.append(r8)
                java.lang.String r15 = r3.toString()
                android.util.Slog.d(r1, r15)
                java.lang.StringBuilder r15 = new java.lang.StringBuilder
                r15.<init>()
                java.lang.String r3 = "plugged        "
                r15.append(r3)
                r15.append(r7)
                r15.append(r2)
                r15.append(r0)
                java.lang.String r15 = r15.toString()
                android.util.Slog.d(r1, r15)
            L_0x0156:
                com.android.systemui.power.PowerUI r15 = com.android.systemui.power.PowerUI.this
                com.android.systemui.power.PowerUI$WarningsUI r2 = r15.mWarnings
                int r15 = r15.mBatteryLevel
                r2.update(r15, r8)
                if (r5 != 0) goto L_0x0175
                com.android.systemui.power.PowerUI r15 = com.android.systemui.power.PowerUI.this
                int r15 = r15.mInvalidCharger
                if (r15 == 0) goto L_0x0175
                java.lang.String r14 = "showing invalid charger warning"
                android.util.Slog.d(r1, r14)
                com.android.systemui.power.PowerUI r13 = com.android.systemui.power.PowerUI.this
                com.android.systemui.power.PowerUI$WarningsUI r13 = r13.mWarnings
                r13.showInvalidChargerWarning()
                return
            L_0x0175:
                if (r5 == 0) goto L_0x0183
                com.android.systemui.power.PowerUI r15 = com.android.systemui.power.PowerUI.this
                int r2 = r15.mInvalidCharger
                if (r2 != 0) goto L_0x0183
                com.android.systemui.power.PowerUI$WarningsUI r15 = r15.mWarnings
                r15.dismissInvalidChargerWarning()
                goto L_0x0195
            L_0x0183:
                com.android.systemui.power.PowerUI r15 = com.android.systemui.power.PowerUI.this
                com.android.systemui.power.PowerUI$WarningsUI r15 = r15.mWarnings
                boolean r15 = r15.isInvalidChargerWarningShowing()
                if (r15 == 0) goto L_0x0195
                if (r9 == 0) goto L_0x0194
                java.lang.String r13 = "Bad Charger"
                android.util.Slog.d(r1, r13)
            L_0x0194:
                return
            L_0x0195:
                com.android.systemui.power.PowerUI r15 = com.android.systemui.power.PowerUI.this
                java.util.concurrent.Future r15 = r15.mLastShowWarningTask
                if (r15 == 0) goto L_0x01a5
                r15.cancel(r14)
                if (r9 == 0) goto L_0x01a5
                java.lang.String r14 = "cancelled task"
                android.util.Slog.d(r1, r14)
            L_0x01a5:
                com.android.systemui.power.PowerUI r14 = com.android.systemui.power.PowerUI.this
                com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda1 r15 = new com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda1
                r15.<init>(r13, r0, r8)
                java.util.concurrent.Future r13 = androidx.recyclerview.R$dimen.postOnBackgroundThread(r15)
                r14.mLastShowWarningTask = r13
                goto L_0x01f8
            L_0x01b3:
                java.lang.String r0 = "android.intent.action.SCREEN_OFF"
                boolean r0 = r0.equals(r14)
                if (r0 == 0) goto L_0x01c4
                com.android.systemui.power.PowerUI r13 = com.android.systemui.power.PowerUI.this
                long r14 = android.os.SystemClock.elapsedRealtime()
                r13.mScreenOffTime = r14
                goto L_0x01f8
            L_0x01c4:
                java.lang.String r0 = "android.intent.action.SCREEN_ON"
                boolean r0 = r0.equals(r14)
                if (r0 == 0) goto L_0x01d3
                com.android.systemui.power.PowerUI r13 = com.android.systemui.power.PowerUI.this
                r14 = -1
                r13.mScreenOffTime = r14
                goto L_0x01f8
            L_0x01d3:
                java.lang.String r0 = "android.intent.action.USER_SWITCHED"
                boolean r14 = r0.equals(r14)
                if (r14 == 0) goto L_0x01e3
                com.android.systemui.power.PowerUI r13 = com.android.systemui.power.PowerUI.this
                com.android.systemui.power.PowerUI$WarningsUI r13 = r13.mWarnings
                r13.userSwitched()
                goto L_0x01f8
            L_0x01e3:
                java.lang.StringBuilder r13 = new java.lang.StringBuilder
                r13.<init>()
                java.lang.String r14 = "unknown intent: "
                r13.append(r14)
                r13.append(r15)
                java.lang.String r13 = r13.toString()
                android.util.Slog.w(r1, r13)
            L_0x01f8:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.power.PowerUI.Receiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    @VisibleForTesting
    public final class SkinThermalEventListener extends IThermalEventListener.Stub {
        public SkinThermalEventListener() {
        }

        public final void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            if (status < 5) {
                PowerUI.this.mWarnings.dismissHighTemperatureWarning();
            } else if (!((Boolean) PowerUI.this.mStatusBarOptionalLazy.get().map(PeopleSpaceWidgetManager$$ExternalSyntheticLambda6.INSTANCE$1).orElse(Boolean.FALSE)).booleanValue()) {
                PowerUI.this.mWarnings.showHighTemperatureWarning();
                Slog.d("PowerUI", "SkinThermalEventListener: notifyThrottling was called , current skin status = " + status + ", temperature = " + temperature.getValue());
            }
        }
    }

    @VisibleForTesting
    public final class UsbThermalEventListener extends IThermalEventListener.Stub {
        public UsbThermalEventListener() {
        }

        public final void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            if (status >= 5) {
                PowerUI.this.mWarnings.showUsbHighTemperatureAlarm();
                Slog.d("PowerUI", "UsbThermalEventListener: notifyThrottling was called , current usb port status = " + status + ", temperature = " + temperature.getValue());
            }
        }
    }

    public interface WarningsUI {
        void dismissHighTemperatureWarning();

        void dismissInvalidChargerWarning();

        void dismissLowBatteryWarning();

        void dump(PrintWriter printWriter);

        boolean isInvalidChargerWarningShowing();

        void showHighTemperatureWarning();

        void showInvalidChargerWarning();

        void showLowBatteryWarning(boolean z);

        void showThermalShutdownWarning();

        void showUsbHighTemperatureAlarm();

        void update(int i, int i2);

        void updateLowBatteryWarning();

        void updateSnapshot(BatteryStateSnapshot batteryStateSnapshot);

        void userSwitched();
    }

    @VisibleForTesting
    public synchronized void doSkinThermalEventListenerRegistration() {
        boolean z;
        boolean z2;
        boolean z3 = this.mEnableSkinTemperatureWarning;
        boolean z4 = true;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "show_temperature_warning", this.mContext.getResources().getInteger(C1777R.integer.config_showTemperatureWarning)) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mEnableSkinTemperatureWarning = z;
        if (z != z3) {
            try {
                if (this.mSkinThermalEventListener == null) {
                    this.mSkinThermalEventListener = new SkinThermalEventListener();
                }
                if (this.mThermalService == null) {
                    this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                }
                if (this.mEnableSkinTemperatureWarning) {
                    z2 = this.mThermalService.registerThermalEventListenerWithType(this.mSkinThermalEventListener, 3);
                } else {
                    z2 = this.mThermalService.unregisterThermalEventListener(this.mSkinThermalEventListener);
                }
            } catch (RemoteException e) {
                Slog.e("PowerUI", "Exception while (un)registering skin thermal event listener.", e);
                z2 = false;
            }
            if (!z2) {
                if (this.mEnableSkinTemperatureWarning) {
                    z4 = false;
                }
                this.mEnableSkinTemperatureWarning = z4;
                Slog.e("PowerUI", "Failed to register or unregister skin thermal event listener.");
            }
        }
    }

    @VisibleForTesting
    public synchronized void doUsbThermalEventListenerRegistration() {
        boolean z;
        boolean z2;
        boolean z3 = this.mEnableUsbTemperatureAlarm;
        boolean z4 = true;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "show_usb_temperature_alarm", this.mContext.getResources().getInteger(C1777R.integer.config_showUsbPortAlarm)) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mEnableUsbTemperatureAlarm = z;
        if (z != z3) {
            try {
                if (this.mUsbThermalEventListener == null) {
                    this.mUsbThermalEventListener = new UsbThermalEventListener();
                }
                if (this.mThermalService == null) {
                    this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                }
                if (this.mEnableUsbTemperatureAlarm) {
                    z2 = this.mThermalService.registerThermalEventListenerWithType(this.mUsbThermalEventListener, 4);
                } else {
                    z2 = this.mThermalService.unregisterThermalEventListener(this.mUsbThermalEventListener);
                }
            } catch (RemoteException e) {
                Slog.e("PowerUI", "Exception while (un)registering usb thermal event listener.", e);
                z2 = false;
            }
            if (!z2) {
                if (this.mEnableUsbTemperatureAlarm) {
                    z4 = false;
                }
                this.mEnableUsbTemperatureAlarm = z4;
                Slog.e("PowerUI", "Failed to register or unregister usb thermal event listener.");
            }
        }
    }

    public final void dismissInattentiveSleepWarning(boolean z) {
        InattentiveSleepWarningView inattentiveSleepWarningView = this.mOverlayView;
        if (inattentiveSleepWarningView != null && inattentiveSleepWarningView.getParent() != null) {
            inattentiveSleepWarningView.mDismissing = true;
            if (z) {
                Animator animator = inattentiveSleepWarningView.mFadeOutAnimator;
                Objects.requireNonNull(animator);
                inattentiveSleepWarningView.postOnAnimation(new VolumeDialogImpl$$ExternalSyntheticLambda10(animator, 4));
                return;
            }
            inattentiveSleepWarningView.setVisibility(4);
            inattentiveSleepWarningView.mWindowManager.removeView(inattentiveSleepWarningView);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("mLowBatteryAlertCloseLevel=");
        printWriter.println(this.mLowBatteryAlertCloseLevel);
        printWriter.print("mLowBatteryReminderLevels=");
        printWriter.println(Arrays.toString(this.mLowBatteryReminderLevels));
        printWriter.print("mBatteryLevel=");
        printWriter.println(Integer.toString(this.mBatteryLevel));
        printWriter.print("mBatteryStatus=");
        printWriter.println(Integer.toString(this.mBatteryStatus));
        printWriter.print("mPlugType=");
        printWriter.println(Integer.toString(this.mPlugType));
        printWriter.print("mInvalidCharger=");
        printWriter.println(Integer.toString(this.mInvalidCharger));
        printWriter.print("mScreenOffTime=");
        printWriter.print(this.mScreenOffTime);
        if (this.mScreenOffTime >= 0) {
            printWriter.print(" (");
            printWriter.print(SystemClock.elapsedRealtime() - this.mScreenOffTime);
            printWriter.print(" ago)");
        }
        printWriter.println();
        printWriter.print("soundTimeout=");
        printWriter.println(Settings.Global.getInt(this.mContext.getContentResolver(), "low_battery_sound_timeout", 0));
        printWriter.print("bucket: ");
        printWriter.println(Integer.toString(findBatteryLevelBucket(this.mBatteryLevel)));
        printWriter.print("mEnableSkinTemperatureWarning=");
        printWriter.println(this.mEnableSkinTemperatureWarning);
        printWriter.print("mEnableUsbTemperatureAlarm=");
        printWriter.println(this.mEnableUsbTemperatureAlarm);
        this.mWarnings.dump(printWriter);
    }

    public final int findBatteryLevelBucket(int i) {
        if (i >= this.mLowBatteryAlertCloseLevel) {
            return 1;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        if (i > iArr[0]) {
            return 0;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (i <= this.mLowBatteryReminderLevels[length]) {
                return -1 - length;
            }
        }
        throw new RuntimeException("not possible!");
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if ((this.mLastConfiguration.updateFrom(configuration) & 3) != 0) {
            this.mHandler.post(new PowerUI$$ExternalSyntheticLambda0(this, 0));
        }
    }

    @VisibleForTesting
    public Estimate refreshEstimateIfNeeded() {
        BatteryStateSnapshot batteryStateSnapshot = this.mLastBatteryStateSnapshot;
        if (batteryStateSnapshot != null) {
            Objects.requireNonNull(batteryStateSnapshot);
            if (batteryStateSnapshot.timeRemainingMillis != -1) {
                int i = this.mBatteryLevel;
                BatteryStateSnapshot batteryStateSnapshot2 = this.mLastBatteryStateSnapshot;
                Objects.requireNonNull(batteryStateSnapshot2);
                if (i == batteryStateSnapshot2.batteryLevel) {
                    BatteryStateSnapshot batteryStateSnapshot3 = this.mLastBatteryStateSnapshot;
                    Objects.requireNonNull(batteryStateSnapshot3);
                    long j = batteryStateSnapshot3.timeRemainingMillis;
                    BatteryStateSnapshot batteryStateSnapshot4 = this.mLastBatteryStateSnapshot;
                    Objects.requireNonNull(batteryStateSnapshot4);
                    boolean z = batteryStateSnapshot4.isBasedOnUsage;
                    BatteryStateSnapshot batteryStateSnapshot5 = this.mLastBatteryStateSnapshot;
                    Objects.requireNonNull(batteryStateSnapshot5);
                    return new Estimate(j, z, batteryStateSnapshot5.averageTimeToDischargeMillis);
                }
            }
        }
        Estimate estimate = this.mEnhancedEstimates.getEstimate();
        if (DEBUG) {
            StringBuilder sb = new StringBuilder();
            sb.append("updated estimate: ");
            Objects.requireNonNull(estimate);
            sb.append(estimate.estimateMillis);
            Slog.d("PowerUI", sb.toString());
        }
        return estimate;
    }

    public final void showInattentiveSleepWarning() {
        if (this.mOverlayView == null) {
            this.mOverlayView = new InattentiveSleepWarningView(this.mContext);
        }
        InattentiveSleepWarningView inattentiveSleepWarningView = this.mOverlayView;
        Objects.requireNonNull(inattentiveSleepWarningView);
        if (inattentiveSleepWarningView.getParent() == null) {
            inattentiveSleepWarningView.setAlpha(1.0f);
            inattentiveSleepWarningView.setVisibility(0);
            WindowManager windowManager = inattentiveSleepWarningView.mWindowManager;
            Binder binder = inattentiveSleepWarningView.mWindowToken;
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2006, 256, -3);
            layoutParams.privateFlags |= 16;
            layoutParams.setTitle("InattentiveSleepWarning");
            layoutParams.token = binder;
            windowManager.addView(inattentiveSleepWarningView, layoutParams);
            inattentiveSleepWarningView.announceForAccessibility(inattentiveSleepWarningView.getContext().getString(C1777R.string.inattentive_sleep_warning_message));
        } else if (inattentiveSleepWarningView.mFadeOutAnimator.isStarted()) {
            inattentiveSleepWarningView.mFadeOutAnimator.cancel();
        }
    }

    public final void start() {
        long j;
        Intent registerReceiver;
        if (this.mPowerManager.isScreenOn()) {
            j = -1;
        } else {
            j = SystemClock.elapsedRealtime();
        }
        this.mScreenOffTime = j;
        this.mLastConfiguration.setTo(this.mContext.getResources().getConfiguration());
        C09661 r0 = new ContentObserver(this.mHandler) {
            public final void onChange(boolean z) {
                PowerUI.this.updateBatteryWarningLevels();
            }
        };
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i = -1;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, r0, -1);
        updateBatteryWarningLevels();
        Receiver receiver = this.mReceiver;
        Objects.requireNonNull(receiver);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        PowerUI powerUI = PowerUI.this;
        powerUI.mBroadcastDispatcher.registerReceiverWithHandler(receiver, intentFilter, powerUI.mHandler);
        if (!receiver.mHasReceivedBattery && (registerReceiver = PowerUI.this.mContext.registerReceiver((BroadcastReceiver) null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) != null) {
            receiver.onReceive(PowerUI.this.mContext, registerReceiver);
        }
        int i2 = this.mContext.getSharedPreferences("powerui_prefs", 0).getInt("boot_count", -1);
        try {
            i = Settings.Global.getInt(this.mContext.getContentResolver(), "boot_count");
        } catch (Settings.SettingNotFoundException unused) {
            Slog.e("PowerUI", "Failed to read system boot count from Settings.Global.BOOT_COUNT");
        }
        if (i > i2) {
            this.mContext.getSharedPreferences("powerui_prefs", 0).edit().putInt("boot_count", i).apply();
            if (this.mPowerManager.getLastShutdownReason() == 4) {
                this.mWarnings.showThermalShutdownWarning();
            }
        }
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_temperature_warning"), false, new ContentObserver(this.mHandler) {
            public final void onChange(boolean z) {
                PowerUI.this.doSkinThermalEventListenerRegistration();
            }
        });
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_usb_temperature_alarm"), false, new ContentObserver(this.mHandler) {
            public final void onChange(boolean z) {
                PowerUI.this.doUsbThermalEventListenerRegistration();
            }
        });
        doSkinThermalEventListenerRegistration();
        doUsbThermalEventListenerRegistration();
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
    }

    public final void updateBatteryWarningLevels() {
        int integer = this.mContext.getResources().getInteger(17694770);
        int integer2 = this.mContext.getResources().getInteger(17694857);
        if (integer2 < integer) {
            integer2 = integer;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        iArr[0] = integer2;
        iArr[1] = integer;
        this.mLowBatteryAlertCloseLevel = this.mContext.getResources().getInteger(17694856) + iArr[0];
    }

    public PowerUI(Context context, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, Lazy<Optional<StatusBar>> lazy, WarningsUI warningsUI, EnhancedEstimates enhancedEstimates, PowerManager powerManager) {
        super(context);
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mCommandQueue = commandQueue;
        this.mStatusBarOptionalLazy = lazy;
        this.mWarnings = warningsUI;
        this.mEnhancedEstimates = enhancedEstimates;
        this.mPowerManager = powerManager;
    }

    @VisibleForTesting
    public void maybeShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        Objects.requireNonNull(batteryStateSnapshot);
        long j = batteryStateSnapshot.timeRemainingMillis;
        boolean z = false;
        if (batteryStateSnapshot.batteryLevel >= 45 && (j > SIX_HOURS_MILLIS || j == -1)) {
            this.mLowWarningShownThisChargeCycle = false;
            this.mSevereWarningShownThisChargeCycle = false;
            if (DEBUG) {
                Slog.d("PowerUI", "Charge cycle reset! Can show warnings again");
            }
        }
        int i = batteryStateSnapshot.bucket;
        Objects.requireNonNull(batteryStateSnapshot2);
        if (i != batteryStateSnapshot2.bucket || batteryStateSnapshot2.plugged) {
            z = true;
        }
        if (shouldShowHybridWarning(batteryStateSnapshot)) {
            this.mWarnings.showLowBatteryWarning(z);
            if ((j == -1 || j > batteryStateSnapshot.severeThresholdMillis) && batteryStateSnapshot.batteryLevel > batteryStateSnapshot.severeLevelThreshold) {
                Slog.d("PowerUI", "Low warning marked as shown this cycle");
                this.mLowWarningShownThisChargeCycle = true;
                return;
            }
            this.mSevereWarningShownThisChargeCycle = true;
            this.mLowWarningShownThisChargeCycle = true;
            if (DEBUG) {
                Slog.d("PowerUI", "Severe warning marked as shown this cycle");
            }
        } else if (shouldDismissHybridWarning(batteryStateSnapshot)) {
            if (DEBUG) {
                Slog.d("PowerUI", "Dismissing warning");
            }
            this.mWarnings.dismissLowBatteryWarning();
        } else {
            if (DEBUG) {
                Slog.d("PowerUI", "Updating warning");
            }
            this.mWarnings.updateLowBatteryWarning();
        }
    }

    @VisibleForTesting
    public boolean shouldDismissHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        Objects.requireNonNull(batteryStateSnapshot);
        if (batteryStateSnapshot.plugged || batteryStateSnapshot.timeRemainingMillis > batteryStateSnapshot.lowThresholdMillis) {
            return true;
        }
        return false;
    }

    @VisibleForTesting
    public boolean shouldDismissLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        Objects.requireNonNull(batteryStateSnapshot);
        if (!batteryStateSnapshot.isPowerSaver && !batteryStateSnapshot.plugged) {
            int i = batteryStateSnapshot.bucket;
            Objects.requireNonNull(batteryStateSnapshot2);
            if (i <= batteryStateSnapshot2.bucket || batteryStateSnapshot.bucket <= 0) {
                return false;
            }
        }
        return true;
    }

    @VisibleForTesting
    public boolean shouldShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        boolean z;
        boolean z2;
        Objects.requireNonNull(batteryStateSnapshot);
        boolean z3 = false;
        boolean z4 = true;
        if (batteryStateSnapshot.plugged || batteryStateSnapshot.batteryStatus == 1) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("can't show warning due to - plugged: ");
            m.append(batteryStateSnapshot.plugged);
            m.append(" status unknown: ");
            if (batteryStateSnapshot.batteryStatus != 1) {
                z4 = false;
            }
            m.append(z4);
            Slog.d("PowerUI", m.toString());
            return false;
        }
        long j = batteryStateSnapshot.timeRemainingMillis;
        if (!batteryStateSnapshot.isLowWarningEnabled || this.mLowWarningShownThisChargeCycle || batteryStateSnapshot.isPowerSaver || ((j == -1 || j >= batteryStateSnapshot.lowThresholdMillis) && batteryStateSnapshot.batteryLevel > batteryStateSnapshot.lowLevelThreshold)) {
            z = false;
        } else {
            z = true;
        }
        if (this.mSevereWarningShownThisChargeCycle || ((j == -1 || j >= batteryStateSnapshot.severeThresholdMillis) && batteryStateSnapshot.batteryLevel > batteryStateSnapshot.severeLevelThreshold)) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (z || z2) {
            z3 = true;
        }
        if (DEBUG) {
            Slog.d("PowerUI", "Enhanced trigger is: " + z3 + "\nwith battery snapshot: mLowWarningShownThisChargeCycle: " + this.mLowWarningShownThisChargeCycle + " mSevereWarningShownThisChargeCycle: " + this.mSevereWarningShownThisChargeCycle + "\n" + batteryStateSnapshot.toString());
        }
        return z3;
    }

    @VisibleForTesting
    public boolean shouldShowLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        Objects.requireNonNull(batteryStateSnapshot);
        if (!batteryStateSnapshot.plugged && !batteryStateSnapshot.isPowerSaver) {
            int i = batteryStateSnapshot.bucket;
            Objects.requireNonNull(batteryStateSnapshot2);
            if ((i < batteryStateSnapshot2.bucket || batteryStateSnapshot2.plugged) && batteryStateSnapshot.bucket < 0 && batteryStateSnapshot.batteryStatus != 1) {
                return true;
            }
        }
        return false;
    }
}
