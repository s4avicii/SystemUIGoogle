package com.android.settingslib;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.RestrictedLockUtils;
import java.util.Objects;

public class RestrictedTopLevelPreference extends Preference {
    public RestrictedPreferenceHelper mHelper;

    public RestrictedTopLevelPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, 0);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet, 0);
    }

    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    public final void performClick() {
        boolean z;
        RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
        Objects.requireNonNull(restrictedPreferenceHelper);
        if (restrictedPreferenceHelper.mDisabledByAdmin) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(restrictedPreferenceHelper.mContext, restrictedPreferenceHelper.mEnforcedAdmin);
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            super.performClick();
        }
    }

    public final void setEnabled(boolean z) {
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
            Objects.requireNonNull(restrictedPreferenceHelper);
            if (restrictedPreferenceHelper.mDisabledByAdmin) {
                this.mHelper.setDisabledByAdmin((RestrictedLockUtils.EnforcedAdmin) null);
                return;
            }
        }
        super.setEnabled(z);
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    public RestrictedTopLevelPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, C1777R.attr.preferenceStyle, 16842894), 0);
    }
}
