package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Switch;
import android.widget.TextView;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;
import androidx.preference.TwoStatePreference;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class MainSwitchPreference extends TwoStatePreference implements OnMainSwitchChangeListener {
    public MainSwitchBar mMainSwitchBar;
    public final ArrayList mSwitchChangeListeners;
    public CharSequence mTitle;

    public final void setTitle(CharSequence charSequence) {
        TextView textView;
        this.mTitle = charSequence;
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null && (textView = mainSwitchBar.mTextView) != null) {
            textView.setText(charSequence);
        }
    }

    public MainSwitchPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ArrayList arrayList = new ArrayList();
        this.mSwitchChangeListeners = arrayList;
        this.mLayoutResId = C1777R.layout.settingslib_main_switch_layout;
        arrayList.add(this);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, 0, 0);
            setTitle(obtainStyledAttributes.getText(4));
            obtainStyledAttributes.recycle();
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
        this.mMainSwitchBar = (MainSwitchBar) preferenceViewHolder.findViewById(C1777R.C1779id.settingslib_main_switch_bar);
        setChecked(this.mChecked);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null) {
            CharSequence charSequence = this.mTitle;
            TextView textView = mainSwitchBar.mTextView;
            if (textView != null) {
                textView.setText(charSequence);
            }
            MainSwitchBar mainSwitchBar2 = this.mMainSwitchBar;
            Objects.requireNonNull(mainSwitchBar2);
            mainSwitchBar2.setVisibility(0);
            mainSwitchBar2.mSwitch.setOnCheckedChangeListener(mainSwitchBar2);
        }
        Iterator it = this.mSwitchChangeListeners.iterator();
        while (it.hasNext()) {
            OnMainSwitchChangeListener onMainSwitchChangeListener = (OnMainSwitchChangeListener) it.next();
            MainSwitchBar mainSwitchBar3 = this.mMainSwitchBar;
            Objects.requireNonNull(mainSwitchBar3);
            if (!mainSwitchBar3.mSwitchChangeListeners.contains(onMainSwitchChangeListener)) {
                mainSwitchBar3.mSwitchChangeListeners.add(onMainSwitchChangeListener);
            }
        }
        this.mSwitchChangeListeners.clear();
    }

    public final void setChecked(boolean z) {
        super.setChecked(z);
        MainSwitchBar mainSwitchBar = this.mMainSwitchBar;
        if (mainSwitchBar != null && mainSwitchBar.mSwitch.isChecked() != z) {
            MainSwitchBar mainSwitchBar2 = this.mMainSwitchBar;
            Objects.requireNonNull(mainSwitchBar2);
            Switch switchR = mainSwitchBar2.mSwitch;
            if (switchR != null) {
                switchR.setChecked(z);
            }
            mainSwitchBar2.setBackground(z);
        }
    }

    public final void onSwitchChanged(boolean z) {
        super.setChecked(z);
    }
}
