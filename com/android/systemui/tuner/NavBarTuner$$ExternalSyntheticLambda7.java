package com.android.systemui.tuner;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ ListPreference f$2;
    public final /* synthetic */ Preference f$3;
    public final /* synthetic */ ListPreference f$4;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda7(NavBarTuner navBarTuner, String str, ListPreference listPreference, Preference preference, ListPreference listPreference2) {
        this.f$0 = navBarTuner;
        this.f$1 = str;
        this.f$2 = listPreference;
        this.f$3 = preference;
        this.f$4 = listPreference2;
    }

    public final void run() {
        NavBarTuner navBarTuner = this.f$0;
        String str = this.f$1;
        ListPreference listPreference = this.f$2;
        Preference preference = this.f$3;
        ListPreference listPreference2 = this.f$4;
        int[][] iArr = NavBarTuner.ICONS;
        Objects.requireNonNull(navBarTuner);
        NavBarTuner.setValue(str, listPreference, preference, listPreference2);
        navBarTuner.updateSummary(listPreference2);
    }
}
