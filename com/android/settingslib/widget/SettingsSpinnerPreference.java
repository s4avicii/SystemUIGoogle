package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class SettingsSpinnerPreference extends Preference implements Preference.OnPreferenceClickListener {
    public final C06141 mOnSelectedListener = new AdapterView.OnItemSelectedListener() {
        public final void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
            SettingsSpinnerPreference settingsSpinnerPreference = SettingsSpinnerPreference.this;
            if (settingsSpinnerPreference.mPosition != i) {
                settingsSpinnerPreference.mPosition = i;
                Objects.requireNonNull(settingsSpinnerPreference);
            }
        }

        public final void onNothingSelected(AdapterView<?> adapterView) {
            Objects.requireNonNull(SettingsSpinnerPreference.this);
        }
    };
    public int mPosition;
    public boolean mShouldPerformClick;

    public final void onPreferenceClick(Preference preference) {
        this.mShouldPerformClick = true;
        notifyChanged();
    }

    public SettingsSpinnerPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutResId = C1777R.layout.settings_spinner_preference;
        this.mOnClickListener = this;
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Spinner spinner = (Spinner) preferenceViewHolder.findViewById(C1777R.C1779id.spinner);
        spinner.setAdapter((SpinnerAdapter) null);
        spinner.setSelection(this.mPosition);
        spinner.setOnItemSelectedListener(this.mOnSelectedListener);
        if (this.mShouldPerformClick) {
            this.mShouldPerformClick = false;
            spinner.performClick();
        }
    }
}
