package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;
import androidx.annotation.Keep;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;

public class PrimarySwitchPreference extends RestrictedPreference {
    public boolean mChecked;
    public boolean mCheckedSet;
    public boolean mEnableSwitch = true;
    public Switch mSwitch;

    public PrimarySwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final int getSecondTargetResId() {
        return C1777R.layout.preference_widget_primary_switch;
    }

    public final boolean shouldHideSecondTarget() {
        return false;
    }

    @Keep
    public Boolean getCheckedState() {
        if (this.mCheckedSet) {
            return Boolean.valueOf(this.mChecked);
        }
        return null;
    }

    public final void setChecked(boolean z) {
        boolean z2;
        if (this.mChecked != z) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 || !this.mCheckedSet) {
            this.mChecked = z;
            this.mCheckedSet = true;
            Switch switchR = this.mSwitch;
            if (switchR != null) {
                switchR.setChecked(z);
            }
        }
    }

    public PrimarySwitchPreference(Context context) {
        super(context, (AttributeSet) null);
    }

    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(C1777R.C1779id.switchWidget);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    Switch switchR = PrimarySwitchPreference.this.mSwitch;
                    if (switchR == null || switchR.isEnabled()) {
                        PrimarySwitchPreference primarySwitchPreference = PrimarySwitchPreference.this;
                        primarySwitchPreference.setChecked(!primarySwitchPreference.mChecked);
                        PrimarySwitchPreference primarySwitchPreference2 = PrimarySwitchPreference.this;
                        if (!primarySwitchPreference2.callChangeListener(Boolean.valueOf(primarySwitchPreference2.mChecked))) {
                            PrimarySwitchPreference primarySwitchPreference3 = PrimarySwitchPreference.this;
                            primarySwitchPreference3.setChecked(!primarySwitchPreference3.mChecked);
                            return;
                        }
                        PrimarySwitchPreference primarySwitchPreference4 = PrimarySwitchPreference.this;
                        primarySwitchPreference4.persistBoolean(primarySwitchPreference4.mChecked);
                    }
                }
            });
            findViewById.setOnTouchListener(PrimarySwitchPreference$$ExternalSyntheticLambda0.INSTANCE);
        }
        Switch switchR = (Switch) preferenceViewHolder.findViewById(C1777R.C1779id.switchWidget);
        this.mSwitch = switchR;
        if (switchR != null) {
            switchR.setContentDescription(this.mTitle);
            this.mSwitch.setChecked(this.mChecked);
            this.mSwitch.setEnabled(this.mEnableSwitch);
        }
    }
}
