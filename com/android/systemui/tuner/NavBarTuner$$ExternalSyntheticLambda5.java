package com.android.systemui.tuner;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import com.android.systemui.tuner.TunerService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda5 implements TunerService.Tunable {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ ListPreference f$2;
    public final /* synthetic */ ListPreference f$3;
    public final /* synthetic */ Preference f$4;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda5(NavBarTuner navBarTuner, String str, ListPreference listPreference, ListPreference listPreference2, Preference preference) {
        this.f$0 = navBarTuner;
        this.f$1 = str;
        this.f$2 = listPreference;
        this.f$3 = listPreference2;
        this.f$4 = preference;
    }

    public final void onTuningChanged(String str, String str2) {
        NavBarTuner navBarTuner = this.f$0;
        String str3 = this.f$1;
        ListPreference listPreference = this.f$2;
        ListPreference listPreference2 = this.f$3;
        Preference preference = this.f$4;
        int[][] iArr = NavBarTuner.ICONS;
        Objects.requireNonNull(navBarTuner);
        navBarTuner.mHandler.post(new NavBarTuner$$ExternalSyntheticLambda8(navBarTuner, str2, str3, listPreference, listPreference2, preference));
    }
}
