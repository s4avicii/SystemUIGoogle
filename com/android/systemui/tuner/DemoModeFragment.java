package com.android.systemui.tuner;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MenuItem;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.demomode.DemoModeAvailabilityTracker;
import com.android.systemui.demomode.DemoModeController;
import java.io.Serializable;
import java.util.Objects;

public class DemoModeFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
    public static final String[] STATUS_ICONS = {"volume", "bluetooth", "location", "alarm", "zen", "sync", "tty", "eri", "mute", "speakerphone", "managed_profile"};
    public DemoModeController mDemoModeController;
    public Tracker mDemoModeTracker;
    public SwitchPreference mEnabledSwitch;
    public SwitchPreference mOnSwitch;

    public class Tracker extends DemoModeAvailabilityTracker {
        public Tracker(Context context) {
            super(context);
        }

        public final void onDemoModeAvailabilityChanged() {
            DemoModeFragment demoModeFragment = DemoModeFragment.this;
            String[] strArr = DemoModeFragment.STATUS_ICONS;
            Objects.requireNonNull(demoModeFragment);
            SwitchPreference switchPreference = demoModeFragment.mEnabledSwitch;
            Tracker tracker = demoModeFragment.mDemoModeTracker;
            Objects.requireNonNull(tracker);
            switchPreference.setChecked(tracker.isDemoModeAvailable);
            SwitchPreference switchPreference2 = demoModeFragment.mOnSwitch;
            Tracker tracker2 = demoModeFragment.mDemoModeTracker;
            Objects.requireNonNull(tracker2);
            switchPreference2.setEnabled(tracker2.isDemoModeAvailable);
            DemoModeFragment.this.updateDemoModeOn();
        }

        public final void onDemoModeFinished() {
            DemoModeFragment demoModeFragment = DemoModeFragment.this;
            String[] strArr = DemoModeFragment.STATUS_ICONS;
            demoModeFragment.updateDemoModeOn();
        }

        public final void onDemoModeStarted() {
            DemoModeFragment demoModeFragment = DemoModeFragment.this;
            String[] strArr = DemoModeFragment.STATUS_ICONS;
            demoModeFragment.updateDemoModeOn();
        }
    }

    public final void onDestroy() {
        Tracker tracker = this.mDemoModeTracker;
        Objects.requireNonNull(tracker);
        ContentResolver contentResolver = tracker.context.getContentResolver();
        contentResolver.unregisterContentObserver(tracker.allowedObserver);
        contentResolver.unregisterContentObserver(tracker.onObserver);
        super.onDestroy();
    }

    public final boolean onPreferenceChange(Preference preference, Serializable serializable) {
        boolean z;
        String str;
        if (serializable == Boolean.TRUE) {
            z = true;
        } else {
            z = false;
        }
        if (preference == this.mEnabledSwitch) {
            if (!z) {
                this.mOnSwitch.setChecked(false);
                DemoModeController demoModeController = this.mDemoModeController;
                Objects.requireNonNull(demoModeController);
                demoModeController.globalSettings.putInt("sysui_tuner_demo_on", 0);
            }
            MetricsLogger.action(getContext(), 235, z);
            DemoModeController demoModeController2 = this.mDemoModeController;
            Objects.requireNonNull(demoModeController2);
            demoModeController2.globalSettings.putInt("sysui_demo_allowed", z ? 1 : 0);
        } else if (preference != this.mOnSwitch) {
            return false;
        } else {
            MetricsLogger.action(getContext(), 236, z);
            if (z) {
                Intent intent = new Intent("com.android.systemui.demo");
                DemoModeController demoModeController3 = this.mDemoModeController;
                Objects.requireNonNull(demoModeController3);
                demoModeController3.globalSettings.putInt("sysui_tuner_demo_on", 1);
                intent.putExtra("command", "clock");
                try {
                    str = String.format("%02d00", new Object[]{Integer.valueOf(Integer.valueOf(Build.VERSION.RELEASE_OR_CODENAME.split("\\.")[0]).intValue() % 24)});
                } catch (IllegalArgumentException unused) {
                    str = "1010";
                }
                intent.putExtra("hhmm", str);
                getContext().sendBroadcast(intent);
                intent.putExtra("command", "network");
                intent.putExtra("wifi", "show");
                intent.putExtra("mobile", "show");
                intent.putExtra("sims", "1");
                intent.putExtra("nosim", "false");
                intent.putExtra("level", "4");
                intent.putExtra("datatype", "lte");
                getContext().sendBroadcast(intent);
                intent.putExtra("fully", "true");
                getContext().sendBroadcast(intent);
                intent.putExtra("command", "battery");
                intent.putExtra("level", "100");
                intent.putExtra("plugged", "false");
                getContext().sendBroadcast(intent);
                intent.putExtra("command", "status");
                String[] strArr = STATUS_ICONS;
                for (int i = 0; i < 11; i++) {
                    intent.putExtra(strArr[i], "hide");
                }
                getContext().sendBroadcast(intent);
                intent.putExtra("command", "notifications");
                intent.putExtra("visible", "false");
                getContext().sendBroadcast(intent);
            } else {
                DemoModeController demoModeController4 = this.mDemoModeController;
                Objects.requireNonNull(demoModeController4);
                demoModeController4.globalSettings.putInt("sysui_tuner_demo_on", 0);
            }
        }
        return true;
    }

    public final void updateDemoModeOn() {
        SwitchPreference switchPreference = this.mOnSwitch;
        Tracker tracker = this.mDemoModeTracker;
        Objects.requireNonNull(tracker);
        switchPreference.setChecked(tracker.isInDemoMode);
    }

    @SuppressLint({"ValidFragment"})
    public DemoModeFragment(DemoModeController demoModeController) {
        this.mDemoModeController = demoModeController;
    }

    public final void onCreatePreferences(String str) {
        Context context = getContext();
        SwitchPreference switchPreference = new SwitchPreference(context);
        this.mEnabledSwitch = switchPreference;
        switchPreference.setTitle((int) C1777R.string.enable_demo_mode);
        SwitchPreference switchPreference2 = this.mEnabledSwitch;
        Objects.requireNonNull(switchPreference2);
        switchPreference2.mOnChangeListener = this;
        SwitchPreference switchPreference3 = new SwitchPreference(context);
        this.mOnSwitch = switchPreference3;
        switchPreference3.setTitle((int) C1777R.string.show_demo_mode);
        this.mOnSwitch.setEnabled(false);
        SwitchPreference switchPreference4 = this.mOnSwitch;
        Objects.requireNonNull(switchPreference4);
        switchPreference4.mOnChangeListener = this;
        PreferenceManager preferenceManager = this.mPreferenceManager;
        Objects.requireNonNull(preferenceManager);
        PreferenceScreen preferenceScreen = new PreferenceScreen(context, (AttributeSet) null);
        preferenceScreen.onAttachedToHierarchy(preferenceManager);
        preferenceScreen.addPreference(this.mEnabledSwitch);
        preferenceScreen.addPreference(this.mOnSwitch);
        setPreferenceScreen(preferenceScreen);
        Tracker tracker = new Tracker(context);
        this.mDemoModeTracker = tracker;
        tracker.startTracking();
        SwitchPreference switchPreference5 = this.mEnabledSwitch;
        Tracker tracker2 = this.mDemoModeTracker;
        Objects.requireNonNull(tracker2);
        switchPreference5.setChecked(tracker2.isDemoModeAvailable);
        SwitchPreference switchPreference6 = this.mOnSwitch;
        Tracker tracker3 = this.mDemoModeTracker;
        Objects.requireNonNull(tracker3);
        switchPreference6.setEnabled(tracker3.isDemoModeAvailable);
        updateDemoModeOn();
        setHasOptionsMenu(true);
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public final void onPause() {
        super.onPause();
        MetricsLogger.visibility(getContext(), 229, false);
    }

    public final void onResume() {
        super.onResume();
        MetricsLogger.visibility(getContext(), 229, true);
    }
}
