package com.android.settingslib.widget;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroupAdapter;
import androidx.preference.PreferenceViewHolder;
import com.android.p012wm.shell.C1777R;

public class FooterPreference extends Preference {
    public View.OnClickListener mLearnMoreListener;
    public FooterLearnMoreSpan mLearnMoreSpan;
    public CharSequence mLearnMoreText;

    public static class FooterLearnMoreSpan extends URLSpan {
        public final View.OnClickListener mClickListener;

        public FooterLearnMoreSpan(View.OnClickListener onClickListener) {
            super("");
            this.mClickListener = onClickListener;
        }

        public final void onClick(View view) {
            View.OnClickListener onClickListener = this.mClickListener;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    public CharSequence getContentDescription() {
        return null;
    }

    public CharSequence getLearnMoreContentDescription() {
        return null;
    }

    public FooterPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, C1777R.attr.footerPreferenceStyle);
        this.mLayoutResId = C1777R.layout.preference_footer;
        if (getIcon() == null) {
            setIcon(AppCompatResources.getDrawable(this.mContext, C1777R.C1778drawable.settingslib_ic_info_outline_24));
            this.mIconResId = C1777R.C1778drawable.settingslib_ic_info_outline_24;
        }
        if (2147483646 != this.mOrder) {
            this.mOrder = 2147483646;
            Preference.OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
            if (onPreferenceChangeInternalListener != null) {
                PreferenceGroupAdapter preferenceGroupAdapter = (PreferenceGroupAdapter) onPreferenceChangeInternalListener;
                preferenceGroupAdapter.mHandler.removeCallbacks(preferenceGroupAdapter.mSyncRunnable);
                preferenceGroupAdapter.mHandler.post(preferenceGroupAdapter.mSyncRunnable);
            }
        }
        if (TextUtils.isEmpty(this.mKey)) {
            setKey("footer_preference");
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.itemView.findViewById(16908310);
        textView.setMovementMethod(new LinkMovementMethod());
        textView.setClickable(false);
        textView.setLongClickable(false);
        textView.setFocusable(false);
        if (!TextUtils.isEmpty((CharSequence) null)) {
            textView.setContentDescription((CharSequence) null);
        }
        TextView textView2 = (TextView) preferenceViewHolder.itemView.findViewById(C1777R.C1779id.settingslib_learn_more);
        if (textView2 == null || this.mLearnMoreListener == null) {
            textView2.setVisibility(8);
            return;
        }
        textView2.setVisibility(0);
        if (TextUtils.isEmpty(this.mLearnMoreText)) {
            this.mLearnMoreText = textView2.getText();
        } else {
            textView2.setText(this.mLearnMoreText);
        }
        SpannableString spannableString = new SpannableString(this.mLearnMoreText);
        FooterLearnMoreSpan footerLearnMoreSpan = this.mLearnMoreSpan;
        if (footerLearnMoreSpan != null) {
            spannableString.removeSpan(footerLearnMoreSpan);
        }
        FooterLearnMoreSpan footerLearnMoreSpan2 = new FooterLearnMoreSpan(this.mLearnMoreListener);
        this.mLearnMoreSpan = footerLearnMoreSpan2;
        spannableString.setSpan(footerLearnMoreSpan2, 0, spannableString.length(), 0);
        textView2.setText(spannableString);
        if (!TextUtils.isEmpty((CharSequence) null)) {
            textView2.setContentDescription((CharSequence) null);
        }
        textView2.setFocusable(false);
    }

    public final void setSummary$1() {
        setTitle((int) C1777R.string.lockscreen_none);
    }

    public final void setSummary(CharSequence charSequence) {
        setTitle(charSequence);
    }

    public final CharSequence getSummary() {
        return this.mTitle;
    }
}
