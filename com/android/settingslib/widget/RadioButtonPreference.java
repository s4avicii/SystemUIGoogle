package com.android.settingslib.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.preference.CheckBoxPreference;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;

public class RadioButtonPreference extends CheckBoxPreference {
    public View mAppendix;
    public int mAppendixVisibility = -1;
    public ImageView mExtraWidget;
    public View mExtraWidgetContainer;
    public View.OnClickListener mExtraWidgetOnClickListener;

    public final void onClick() {
    }

    public RadioButtonPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mWidgetLayoutResId = C1777R.layout.preference_widget_radiobutton;
        this.mLayoutResId = C1777R.layout.preference_radio;
        if (this.mIconSpaceReserved) {
            this.mIconSpaceReserved = false;
            notifyChanged();
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        int i2;
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(C1777R.C1779id.summary_container);
        int i3 = 0;
        if (findViewById != null) {
            if (TextUtils.isEmpty(getSummary())) {
                i = 8;
            } else {
                i = 0;
            }
            findViewById.setVisibility(i);
            View findViewById2 = preferenceViewHolder.findViewById(C1777R.C1779id.appendix);
            this.mAppendix = findViewById2;
            if (!(findViewById2 == null || (i2 = this.mAppendixVisibility) == -1)) {
                findViewById2.setVisibility(i2);
            }
        }
        this.mExtraWidget = (ImageView) preferenceViewHolder.findViewById(C1777R.C1779id.radio_extra_widget);
        View findViewById3 = preferenceViewHolder.findViewById(C1777R.C1779id.radio_extra_widget_container);
        this.mExtraWidgetContainer = findViewById3;
        View.OnClickListener onClickListener = this.mExtraWidgetOnClickListener;
        this.mExtraWidgetOnClickListener = onClickListener;
        ImageView imageView = this.mExtraWidget;
        if (imageView != null && findViewById3 != null) {
            imageView.setOnClickListener(onClickListener);
            View view = this.mExtraWidgetContainer;
            if (this.mExtraWidgetOnClickListener == null) {
                i3 = 8;
            }
            view.setVisibility(i3);
        }
    }
}
