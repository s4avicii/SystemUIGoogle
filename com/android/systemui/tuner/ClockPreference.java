package com.android.systemui.tuner;

import android.content.Context;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import androidx.preference.DropDownPreference;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.tuner.TunerService;

public class ClockPreference extends DropDownPreference implements TunerService.Tunable {
    public final String mClock;
    public boolean mClockEnabled;
    public boolean mHasSeconds;
    public boolean mHasSetValue;
    public ArraySet<String> mHideList;
    public boolean mReceivedClock;
    public boolean mReceivedSeconds;

    public final void onDetached() {
        ((TunerService) Dependency.get(TunerService.class)).removeTunable(this);
        unregisterDependency();
    }

    public final void onTuningChanged(String str, String str2) {
        boolean z;
        if ("icon_blacklist".equals(str)) {
            this.mReceivedClock = true;
            ArraySet<String> iconHideList = StatusBarIconController.getIconHideList(this.mContext, str2);
            this.mHideList = iconHideList;
            this.mClockEnabled = !iconHideList.contains(this.mClock);
        } else if ("clock_seconds".equals(str)) {
            this.mReceivedSeconds = true;
            if (str2 == null || Integer.parseInt(str2) == 0) {
                z = false;
            } else {
                z = true;
            }
            this.mHasSeconds = z;
        }
        if (!this.mHasSetValue && this.mReceivedClock && this.mReceivedSeconds) {
            this.mHasSetValue = true;
            boolean z2 = this.mClockEnabled;
            if (z2 && this.mHasSeconds) {
                setValue("seconds");
            } else if (z2) {
                setValue("default");
            } else {
                setValue("disabled");
            }
        }
    }

    public final boolean persistString(String str) {
        Class cls = TunerService.class;
        ((TunerService) Dependency.get(cls)).setValue("clock_seconds", "seconds".equals(str) ? 1 : 0);
        if ("disabled".equals(str)) {
            this.mHideList.add(this.mClock);
        } else {
            this.mHideList.remove(this.mClock);
        }
        ((TunerService) Dependency.get(cls)).setValue("icon_blacklist", TextUtils.join(",", this.mHideList));
        return true;
    }

    public ClockPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClock = context.getString(17041541);
        this.mEntryValues = new CharSequence[]{"seconds", "default", "disabled"};
    }

    public final void onAttached() {
        super.onAttached();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, "icon_blacklist", "clock_seconds");
    }
}
