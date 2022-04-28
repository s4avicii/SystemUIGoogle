package com.android.systemui.tuner;

import androidx.preference.PreferenceFragment;
import com.android.p012wm.shell.C1777R;

public class OtherPrefs extends PreferenceFragment {
    public final void onCreatePreferences(String str) {
        addPreferencesFromResource(C1777R.xml.other_settings);
    }
}
