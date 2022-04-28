package com.android.settingslib;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.res.TypedArrayUtils;
import androidx.leanback.R$string;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreference;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.RestrictedLockUtils;
import java.util.Objects;

public class RestrictedSwitchPreference extends SwitchPreference {
    public RestrictedPreferenceHelper mHelper;
    public CharSequence mRestrictedSwitchSummary;
    public boolean mUseAdditionalSummary;

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, 0);
        this.mUseAdditionalSummary = false;
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet, 0);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$string.RestrictedSwitchPreference);
            boolean z = true;
            TypedValue peekValue = obtainStyledAttributes.peekValue(1);
            if (peekValue != null) {
                this.mUseAdditionalSummary = (peekValue.type != 18 || peekValue.data == 0) ? false : z;
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(0);
            obtainStyledAttributes.recycle();
            if (peekValue2 != null && peekValue2.type == 3) {
                int i3 = peekValue2.resourceId;
                if (i3 != 0) {
                    this.mRestrictedSwitchSummary = context.getText(i3);
                } else {
                    this.mRestrictedSwitchSummary = peekValue2.string;
                }
            }
        }
        if (this.mUseAdditionalSummary) {
            this.mLayoutResId = C1777R.layout.restricted_switch_preference;
            RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
            Objects.requireNonNull(restrictedPreferenceHelper);
            restrictedPreferenceHelper.mDisabledSummary = false;
        }
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
        int i;
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
        CharSequence charSequence = this.mRestrictedSwitchSummary;
        if (charSequence == null) {
            Context context = this.mContext;
            if (this.mChecked) {
                i = C1777R.string.enabled_by_admin;
            } else {
                i = C1777R.string.disabled_by_admin;
            }
            charSequence = context.getText(i);
        }
        ImageView imageView = (ImageView) preferenceViewHolder.itemView.findViewById(16908294);
        if (this.mUseAdditionalSummary) {
            TextView textView = (TextView) preferenceViewHolder.findViewById(C1777R.C1779id.additional_summary);
            if (textView != null) {
                RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
                Objects.requireNonNull(restrictedPreferenceHelper);
                if (restrictedPreferenceHelper.mDisabledByAdmin) {
                    textView.setText(charSequence);
                    textView.setVisibility(0);
                    return;
                }
                textView.setVisibility(8);
                return;
            }
            return;
        }
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(16908304);
        if (textView2 != null) {
            RestrictedPreferenceHelper restrictedPreferenceHelper2 = this.mHelper;
            Objects.requireNonNull(restrictedPreferenceHelper2);
            if (restrictedPreferenceHelper2.mDisabledByAdmin) {
                textView2.setText(charSequence);
                textView2.setVisibility(0);
            }
        }
    }

    public RestrictedSwitchPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, TypedArrayUtils.getAttr(context, C1777R.attr.switchPreferenceStyle, 16843629), 0);
    }
}
