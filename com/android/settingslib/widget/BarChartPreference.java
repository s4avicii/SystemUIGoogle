package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;

public class BarChartPreference extends Preference {
    public BarChartPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        if (this.mSelectable) {
            this.mSelectable = false;
            notifyChanged();
        }
        this.mLayoutResId = C1777R.layout.settings_bar_chart;
        this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.settings_bar_view_max_height);
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        preferenceViewHolder.mDividerAllowedAbove = true;
        preferenceViewHolder.mDividerAllowedBelow = true;
        TextView textView = (TextView) preferenceViewHolder.findViewById(C1777R.C1779id.bar_chart_title);
        throw null;
    }
}
