package com.android.settingslib.inputmethod;

import android.content.Context;
import androidx.preference.SwitchPreference;

public class SwitchWithNoTextPreference extends SwitchPreference {
    public SwitchWithNoTextPreference(Context context) {
        super(context);
        this.mSwitchOn = "";
        notifyChanged();
        this.mSwitchOff = "";
        notifyChanged();
    }
}
