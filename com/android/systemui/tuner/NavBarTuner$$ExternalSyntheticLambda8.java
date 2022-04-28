package com.android.systemui.tuner;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import com.android.systemui.navigationbar.NavigationBarInflaterView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda8 implements Runnable {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ ListPreference f$3;
    public final /* synthetic */ ListPreference f$4;
    public final /* synthetic */ Preference f$5;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda8(NavBarTuner navBarTuner, String str, String str2, ListPreference listPreference, ListPreference listPreference2, Preference preference) {
        this.f$0 = navBarTuner;
        this.f$1 = str;
        this.f$2 = str2;
        this.f$3 = listPreference;
        this.f$4 = listPreference2;
        this.f$5 = preference;
    }

    public final void run() {
        String str;
        NavBarTuner navBarTuner = this.f$0;
        String str2 = this.f$1;
        String str3 = this.f$2;
        ListPreference listPreference = this.f$3;
        ListPreference listPreference2 = this.f$4;
        Preference preference = this.f$5;
        int[][] iArr = NavBarTuner.ICONS;
        Objects.requireNonNull(navBarTuner);
        if (str2 == null) {
            str2 = str3;
        }
        String extractButton = NavigationBarInflaterView.extractButton(str2);
        if (extractButton.startsWith("key")) {
            listPreference.setValue("key");
            if (!extractButton.contains(":")) {
                str = null;
            } else {
                str = extractButton.substring(extractButton.indexOf(":") + 1, extractButton.indexOf(")"));
            }
            int extractKeycode = NavigationBarInflaterView.extractKeycode(extractButton);
            listPreference2.setValue(str);
            navBarTuner.updateSummary(listPreference2);
            preference.setSummary(extractKeycode + "");
            preference.setVisible(true);
            listPreference2.setVisible(true);
            return;
        }
        listPreference.setValue(extractButton);
        preference.setVisible(false);
        listPreference2.setVisible(false);
    }
}
