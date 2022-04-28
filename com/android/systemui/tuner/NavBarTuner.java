package com.android.systemui.tuner;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.tuner.TunerService;
import java.util.ArrayList;
import java.util.Objects;

@Deprecated
public class NavBarTuner extends TunerPreferenceFragment {
    public static final int[][] ICONS = {new int[]{C1777R.C1778drawable.ic_qs_circle, C1777R.string.tuner_circle}, new int[]{C1777R.C1778drawable.ic_add, C1777R.string.tuner_plus}, new int[]{C1777R.C1778drawable.ic_remove, C1777R.string.tuner_minus}, new int[]{C1777R.C1778drawable.ic_left, C1777R.string.tuner_left}, new int[]{C1777R.C1778drawable.ic_right, C1777R.string.tuner_right}, new int[]{C1777R.C1778drawable.ic_menu, C1777R.string.tuner_menu}};
    public Handler mHandler;
    public final ArrayList<TunerService.Tunable> mTunables = new ArrayList<>();

    public final void bindButton(String str, String str2, String str3) {
        String str4 = str3;
        ListPreference listPreference = (ListPreference) findPreference("type_" + str4);
        Preference findPreference = findPreference("keycode_" + str4);
        ListPreference listPreference2 = (ListPreference) findPreference("icon_" + str4);
        CharSequence[] charSequenceArr = new CharSequence[6];
        CharSequence[] charSequenceArr2 = new CharSequence[6];
        int applyDimension = (int) TypedValue.applyDimension(1, 14.0f, getContext().getResources().getDisplayMetrics());
        int i = 0;
        while (true) {
            int[][] iArr = ICONS;
            if (i < 6) {
                SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
                Drawable loadDrawable = Icon.createWithResource(getContext().getPackageName(), iArr[i][0]).loadDrawable(getContext());
                loadDrawable.setTint(-16777216);
                loadDrawable.setBounds(0, 0, applyDimension, applyDimension);
                spannableStringBuilder.append("  ", new ImageSpan(loadDrawable, 1), 0);
                spannableStringBuilder.append(" ");
                spannableStringBuilder.append(getString(iArr[i][1]));
                charSequenceArr[i] = spannableStringBuilder;
                charSequenceArr2[i] = getContext().getPackageName() + "/" + iArr[i][0];
                i++;
            } else {
                listPreference2.setEntries(charSequenceArr);
                listPreference2.mEntryValues = charSequenceArr2;
                NavBarTuner$$ExternalSyntheticLambda5 navBarTuner$$ExternalSyntheticLambda5 = new NavBarTuner$$ExternalSyntheticLambda5(this, str2, listPreference, listPreference2, findPreference);
                this.mTunables.add(navBarTuner$$ExternalSyntheticLambda5);
                ((TunerService) Dependency.get(TunerService.class)).addTunable(navBarTuner$$ExternalSyntheticLambda5, str);
                ListPreference listPreference3 = listPreference2;
                NavBarTuner$$ExternalSyntheticLambda1 navBarTuner$$ExternalSyntheticLambda1 = new NavBarTuner$$ExternalSyntheticLambda1(this, str, listPreference, findPreference, listPreference3);
                Objects.requireNonNull(listPreference);
                listPreference.mOnChangeListener = navBarTuner$$ExternalSyntheticLambda1;
                listPreference2.mOnChangeListener = navBarTuner$$ExternalSyntheticLambda1;
                NavBarTuner$$ExternalSyntheticLambda3 navBarTuner$$ExternalSyntheticLambda3 = new NavBarTuner$$ExternalSyntheticLambda3(this, findPreference, str, listPreference, listPreference3);
                Objects.requireNonNull(findPreference);
                findPreference.mOnClickListener = navBarTuner$$ExternalSyntheticLambda3;
                return;
            }
        }
    }

    public final void onCreate(Bundle bundle) {
        this.mHandler = new Handler();
        super.onCreate(bundle);
    }

    public final void updateSummary(ListPreference listPreference) {
        try {
            int applyDimension = (int) TypedValue.applyDimension(1, 14.0f, getContext().getResources().getDisplayMetrics());
            Objects.requireNonNull(listPreference);
            String str = listPreference.mValue.split("/")[0];
            int parseInt = Integer.parseInt(listPreference.mValue.split("/")[1]);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            Drawable loadDrawable = Icon.createWithResource(str, parseInt).loadDrawable(getContext());
            loadDrawable.setTint(-16777216);
            loadDrawable.setBounds(0, 0, applyDimension, applyDimension);
            spannableStringBuilder.append("  ", new ImageSpan(loadDrawable, 1), 0);
            spannableStringBuilder.append(" ");
            int i = 0;
            while (true) {
                int[][] iArr = ICONS;
                if (i < 6) {
                    if (iArr[i][0] == parseInt) {
                        spannableStringBuilder.append(getString(iArr[i][1]));
                    }
                    i++;
                } else {
                    listPreference.setSummary(spannableStringBuilder);
                    return;
                }
            }
        } catch (Exception e) {
            Log.d("NavButton", "Problem with summary", e);
            listPreference.setSummary((CharSequence) null);
        }
    }

    public static void setValue(String str, ListPreference listPreference, Preference preference, ListPreference listPreference2) {
        Objects.requireNonNull(listPreference);
        String str2 = listPreference.mValue;
        if ("key".equals(str2)) {
            Objects.requireNonNull(listPreference2);
            String str3 = listPreference2.mValue;
            int i = 66;
            try {
                i = Integer.parseInt(preference.getSummary().toString());
            } catch (Exception unused) {
            }
            str2 = str2 + "(" + i + ":" + str3 + ")";
        }
        ((TunerService) Dependency.get(TunerService.class)).setValue(str, str2);
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public final void onCreatePreferences(String str) {
        addPreferencesFromResource(C1777R.xml.nav_bar_tuner);
        ListPreference listPreference = (ListPreference) findPreference("layout");
        NavBarTuner$$ExternalSyntheticLambda4 navBarTuner$$ExternalSyntheticLambda4 = new NavBarTuner$$ExternalSyntheticLambda4(this, listPreference);
        this.mTunables.add(navBarTuner$$ExternalSyntheticLambda4);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(navBarTuner$$ExternalSyntheticLambda4, "sysui_nav_bar");
        NavBarTuner$$ExternalSyntheticLambda2 navBarTuner$$ExternalSyntheticLambda2 = NavBarTuner$$ExternalSyntheticLambda2.INSTANCE;
        Objects.requireNonNull(listPreference);
        listPreference.mOnChangeListener = navBarTuner$$ExternalSyntheticLambda2;
        bindButton("sysui_nav_bar_left", "space", "left");
        bindButton("sysui_nav_bar_right", "menu_ime", "right");
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mTunables.forEach(NavBarTuner$$ExternalSyntheticLambda9.INSTANCE);
    }
}
