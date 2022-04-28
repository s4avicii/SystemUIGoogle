package com.android.systemui.battery;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.graph.ThemedBatteryDrawable;
import com.android.settingslib.graph.ThemedBatteryDrawable$sam$java_lang_Runnable$0;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ViewController;
import java.util.Objects;

public final class BatteryMeterViewController extends ViewController<BatteryMeterView> {
    public final BatteryController mBatteryController;
    public final C06843 mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() {
        public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
            boolean z3;
            BatteryMeterView batteryMeterView = (BatteryMeterView) BatteryMeterViewController.this.mView;
            Objects.requireNonNull(batteryMeterView);
            ThemedBatteryDrawable themedBatteryDrawable = batteryMeterView.mDrawable;
            Objects.requireNonNull(themedBatteryDrawable);
            themedBatteryDrawable.charging = z;
            themedBatteryDrawable.unscheduleSelf(new ThemedBatteryDrawable$sam$java_lang_Runnable$0(themedBatteryDrawable.invalidateRunnable));
            themedBatteryDrawable.scheduleSelf(new ThemedBatteryDrawable$sam$java_lang_Runnable$0(themedBatteryDrawable.invalidateRunnable), 0);
            ThemedBatteryDrawable themedBatteryDrawable2 = batteryMeterView.mDrawable;
            Objects.requireNonNull(themedBatteryDrawable2);
            if (i >= 67) {
                z3 = true;
            } else if (i <= 33) {
                z3 = false;
            } else {
                z3 = themedBatteryDrawable2.invertFillIcon;
            }
            themedBatteryDrawable2.invertFillIcon = z3;
            themedBatteryDrawable2.batteryLevel = i;
            themedBatteryDrawable2.levelColor = themedBatteryDrawable2.batteryColorForLevel(i);
            themedBatteryDrawable2.invalidateSelf();
            batteryMeterView.mCharging = z;
            batteryMeterView.mLevel = i;
            batteryMeterView.updatePercentText();
        }

        public final void onBatteryUnknownStateChanged(boolean z) {
            ((BatteryMeterView) BatteryMeterViewController.this.mView).onBatteryUnknownStateChanged(z);
        }

        public final void onPowerSaveChanged(boolean z) {
            BatteryMeterView batteryMeterView = (BatteryMeterView) BatteryMeterViewController.this.mView;
            Objects.requireNonNull(batteryMeterView);
            ThemedBatteryDrawable themedBatteryDrawable = batteryMeterView.mDrawable;
            Objects.requireNonNull(themedBatteryDrawable);
            themedBatteryDrawable.powerSaveEnabled = z;
            themedBatteryDrawable.unscheduleSelf(new ThemedBatteryDrawable$sam$java_lang_Runnable$0(themedBatteryDrawable.invalidateRunnable));
            themedBatteryDrawable.scheduleSelf(new ThemedBatteryDrawable$sam$java_lang_Runnable$0(themedBatteryDrawable.invalidateRunnable), 0);
        }
    };
    public final ConfigurationController mConfigurationController;
    public final C06821 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onDensityOrFontScaleChanged() {
            BatteryMeterView batteryMeterView = (BatteryMeterView) BatteryMeterViewController.this.mView;
            Objects.requireNonNull(batteryMeterView);
            Resources resources = batteryMeterView.getContext().getResources();
            TypedValue typedValue = new TypedValue();
            resources.getValue(C1777R.dimen.status_bar_icon_scale_factor, typedValue, true);
            float f = typedValue.getFloat();
            int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.status_bar_battery_icon_height);
            int dimensionPixelSize2 = resources.getDimensionPixelSize(C1777R.dimen.status_bar_battery_icon_width);
            int dimensionPixelSize3 = resources.getDimensionPixelSize(C1777R.dimen.battery_margin_bottom);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (((float) dimensionPixelSize2) * f), (int) (((float) dimensionPixelSize) * f));
            layoutParams.setMargins(0, 0, 0, dimensionPixelSize3);
            batteryMeterView.mBatteryIconView.setLayoutParams(layoutParams);
        }
    };
    public final ContentResolver mContentResolver;
    public final C06854 mCurrentUserTracker;
    public boolean mIgnoreTunerUpdates;
    public boolean mIsSubscribedForTunerUpdates;
    public final SettingObserver mSettingObserver;
    public final String mSlotBattery;
    public final C06832 mTunable = new TunerService.Tunable() {
        public final void onTuningChanged(String str, String str2) {
            int i;
            if ("icon_blacklist".equals(str)) {
                ArraySet<String> iconHideList = StatusBarIconController.getIconHideList(BatteryMeterViewController.this.getContext(), str2);
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                BatteryMeterView batteryMeterView = (BatteryMeterView) batteryMeterViewController.mView;
                if (iconHideList.contains(batteryMeterViewController.mSlotBattery)) {
                    i = 8;
                } else {
                    i = 0;
                }
                batteryMeterView.setVisibility(i);
            }
        }
    };
    public final TunerService mTunerService;

    public final class SettingObserver extends ContentObserver {
        public SettingObserver(Handler handler) {
            super(handler);
        }

        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            ((BatteryMeterView) BatteryMeterViewController.this.mView).updateShowPercent();
            if (TextUtils.equals(uri.getLastPathSegment(), "battery_estimates_last_update_time")) {
                ((BatteryMeterView) BatteryMeterViewController.this.mView).updatePercentText();
            }
        }
    }

    public final void onViewAttached() {
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        if (!this.mIsSubscribedForTunerUpdates && !this.mIgnoreTunerUpdates) {
            this.mTunerService.addTunable(this.mTunable, "icon_blacklist");
            this.mIsSubscribedForTunerUpdates = true;
        }
        this.mBatteryController.addCallback(this.mBatteryStateChangeCallback);
        this.mContentResolver.registerContentObserver(Settings.System.getUriFor("status_bar_show_battery_percent"), false, this.mSettingObserver, ActivityManager.getCurrentUser());
        this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("battery_estimates_last_update_time"), false, this.mSettingObserver);
        this.mCurrentUserTracker.startTracking();
        ((BatteryMeterView) this.mView).updateShowPercent();
    }

    public final void onViewDetached() {
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        if (this.mIsSubscribedForTunerUpdates) {
            this.mTunerService.removeTunable(this.mTunable);
            this.mIsSubscribedForTunerUpdates = false;
        }
        this.mBatteryController.removeCallback(this.mBatteryStateChangeCallback);
        this.mCurrentUserTracker.stopTracking();
        this.mContentResolver.unregisterContentObserver(this.mSettingObserver);
    }

    public BatteryMeterViewController(BatteryMeterView batteryMeterView, ConfigurationController configurationController, TunerService tunerService, BroadcastDispatcher broadcastDispatcher, Handler handler, final ContentResolver contentResolver, BatteryController batteryController) {
        super(batteryMeterView);
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mContentResolver = contentResolver;
        this.mBatteryController = batteryController;
        Objects.requireNonNull(batteryController);
        BatteryMeterViewController$$ExternalSyntheticLambda0 batteryMeterViewController$$ExternalSyntheticLambda0 = new BatteryMeterViewController$$ExternalSyntheticLambda0(batteryController);
        Objects.requireNonNull(batteryMeterView);
        batteryMeterView.mBatteryEstimateFetcher = batteryMeterViewController$$ExternalSyntheticLambda0;
        this.mSlotBattery = getResources().getString(17041535);
        this.mSettingObserver = new SettingObserver(handler);
        this.mCurrentUserTracker = new CurrentUserTracker(broadcastDispatcher) {
            public final void onUserSwitched(int i) {
                contentResolver.unregisterContentObserver(BatteryMeterViewController.this.mSettingObserver);
                BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                Objects.requireNonNull(batteryMeterViewController);
                batteryMeterViewController.mContentResolver.registerContentObserver(Settings.System.getUriFor("status_bar_show_battery_percent"), false, batteryMeterViewController.mSettingObserver, i);
                ((BatteryMeterView) BatteryMeterViewController.this.mView).updateShowPercent();
            }
        };
    }
}
