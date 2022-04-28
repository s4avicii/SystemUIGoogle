package com.android.systemui.tuner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarTuner$$ExternalSyntheticLambda3 implements Preference.OnPreferenceClickListener {
    public final /* synthetic */ NavBarTuner f$0;
    public final /* synthetic */ Preference f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ ListPreference f$3;
    public final /* synthetic */ ListPreference f$4;

    public /* synthetic */ NavBarTuner$$ExternalSyntheticLambda3(NavBarTuner navBarTuner, Preference preference, String str, ListPreference listPreference, ListPreference listPreference2) {
        this.f$0 = navBarTuner;
        this.f$1 = preference;
        this.f$2 = str;
        this.f$3 = listPreference;
        this.f$4 = listPreference2;
    }

    public final void onPreferenceClick(Preference preference) {
        NavBarTuner navBarTuner = this.f$0;
        Preference preference2 = this.f$1;
        String str = this.f$2;
        ListPreference listPreference = this.f$3;
        ListPreference listPreference2 = this.f$4;
        int[][] iArr = NavBarTuner.ICONS;
        Objects.requireNonNull(navBarTuner);
        EditText editText = new EditText(navBarTuner.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(navBarTuner.getContext());
        Objects.requireNonNull(preference);
        builder.setTitle(preference.mTitle).setView(editText).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).setPositiveButton(17039370, new NavBarTuner$$ExternalSyntheticLambda0(navBarTuner, editText, preference2, str, listPreference, listPreference2)).show();
    }
}
