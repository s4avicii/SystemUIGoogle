package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;

public class TwoTargetPreference extends Preference {
    public TwoTargetPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i, 0);
        init(context);
    }

    public int getSecondTargetResId() {
        return 0;
    }

    public TwoTargetPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public final void init(Context context) {
        this.mLayoutResId = C1777R.layout.preference_two_target;
        context.getResources().getDimensionPixelSize(C1777R.dimen.two_target_pref_small_icon_size);
        context.getResources().getDimensionPixelSize(C1777R.dimen.two_target_pref_medium_icon_size);
        int secondTargetResId = getSecondTargetResId();
        if (secondTargetResId != 0) {
            this.mWidgetLayoutResId = secondTargetResId;
        }
    }

    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        int i;
        super.onBindViewHolder(preferenceViewHolder);
        ImageView imageView = (ImageView) preferenceViewHolder.itemView.findViewById(16908294);
        View findViewById = preferenceViewHolder.findViewById(C1777R.C1779id.two_target_divider);
        View findViewById2 = preferenceViewHolder.findViewById(16908312);
        boolean shouldHideSecondTarget = shouldHideSecondTarget();
        int i2 = 8;
        if (findViewById != null) {
            if (shouldHideSecondTarget) {
                i = 8;
            } else {
                i = 0;
            }
            findViewById.setVisibility(i);
        }
        if (findViewById2 != null) {
            if (!shouldHideSecondTarget) {
                i2 = 0;
            }
            findViewById2.setVisibility(i2);
        }
    }

    public boolean shouldHideSecondTarget() {
        if (getSecondTargetResId() == 0) {
            return true;
        }
        return false;
    }
}
