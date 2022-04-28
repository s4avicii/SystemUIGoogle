package com.android.systemui.tuner;

import androidx.preference.ListPreference;
import com.android.systemui.tuner.TunerService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda4 implements TunerService.Tunable {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ ListPreference f$1;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda4(NavBarTuner navBarTuner, ListPreference listPreference) {
        this.f$0 = navBarTuner;
        this.f$1 = listPreference;
    }

    public final void onTuningChanged(String str, String str2) {
        NavBarTuner navBarTuner = this.f$0;
        ListPreference listPreference = this.f$1;
        int[][] iArr = NavBarTuner.ICONS;
        Objects.requireNonNull(navBarTuner);
        navBarTuner.mHandler.post(new NavBarTuner$$ExternalSyntheticLambda6(str2, listPreference, 0));
    }
}
