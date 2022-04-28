package com.android.systemui.tuner;

import android.content.DialogInterface;
import android.widget.EditText;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda0 implements DialogInterface.OnClickListener {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ EditText f$1;
    public final /* synthetic */ Preference f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ ListPreference f$4;
    public final /* synthetic */ ListPreference f$5;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda0(NavBarTuner navBarTuner, EditText editText, Preference preference, String str, ListPreference listPreference, ListPreference listPreference2) {
        this.f$0 = navBarTuner;
        this.f$1 = editText;
        this.f$2 = preference;
        this.f$3 = str;
        this.f$4 = listPreference;
        this.f$5 = listPreference2;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2;
        NavBarTuner navBarTuner = this.f$0;
        EditText editText = this.f$1;
        Preference preference = this.f$2;
        String str = this.f$3;
        ListPreference listPreference = this.f$4;
        ListPreference listPreference2 = this.f$5;
        int[][] iArr = NavBarTuner.ICONS;
        Objects.requireNonNull(navBarTuner);
        try {
            i2 = Integer.parseInt(editText.getText().toString());
        } catch (Exception unused) {
            i2 = 66;
        }
        preference.setSummary(i2 + "");
        NavBarTuner.setValue(str, listPreference, preference, listPreference2);
    }
}
