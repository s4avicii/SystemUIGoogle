package com.android.settingslib.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.preference.Preference;
import com.android.p012wm.shell.C1777R;
import java.util.regex.Pattern;

public class UsageProgressBarPreference extends Preference {
    public final Pattern mNumberPattern = Pattern.compile("[\\d]*[\\٫.,]?[\\d]+");
    public int mPercent = -1;

    public UsageProgressBarPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLayoutResId = C1777R.layout.preference_usage_progress_bar;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: android.text.SpannableString} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onBindViewHolder(androidx.preference.PreferenceViewHolder r10) {
        /*
            r9 = this;
            super.onBindViewHolder(r10)
            r0 = 0
            r10.mDividerAllowedAbove = r0
            r10.mDividerAllowedBelow = r0
            r1 = 2131429163(0x7f0b072b, float:1.847999E38)
            android.view.View r1 = r10.findViewById(r1)
            android.widget.TextView r1 = (android.widget.TextView) r1
            r2 = 0
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            r4 = 1
            if (r3 == 0) goto L_0x001c
            java.lang.String r3 = ""
            goto L_0x0044
        L_0x001c:
            java.util.regex.Pattern r3 = r9.mNumberPattern
            java.util.regex.Matcher r3 = r3.matcher(r2)
            boolean r5 = r3.find()
            if (r5 == 0) goto L_0x0043
            android.text.SpannableString r5 = new android.text.SpannableString
            r5.<init>(r2)
            android.text.style.AbsoluteSizeSpan r6 = new android.text.style.AbsoluteSizeSpan
            r7 = 64
            r6.<init>(r7, r4)
            int r7 = r3.start()
            int r3 = r3.end()
            r8 = 33
            r5.setSpan(r6, r7, r3, r8)
            r3 = r5
            goto L_0x0044
        L_0x0043:
            r3 = r2
        L_0x0044:
            r1.setText(r3)
            r1 = 2131429081(0x7f0b06d9, float:1.8479825E38)
            android.view.View r1 = r10.findViewById(r1)
            android.widget.TextView r1 = (android.widget.TextView) r1
            r1 = 2131427602(0x7f0b0112, float:1.8476825E38)
            android.view.View r1 = r10.findViewById(r1)
            android.widget.TextView r1 = (android.widget.TextView) r1
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            r5 = 8
            if (r3 == 0) goto L_0x0065
            r1.setVisibility(r5)
            goto L_0x006b
        L_0x0065:
            r1.setVisibility(r0)
            r1.setText(r2)
        L_0x006b:
            r1 = 16908301(0x102000d, float:2.3877265E-38)
            android.view.View r1 = r10.findViewById(r1)
            android.widget.ProgressBar r1 = (android.widget.ProgressBar) r1
            int r2 = r9.mPercent
            if (r2 >= 0) goto L_0x007c
            r1.setIndeterminate(r4)
            goto L_0x0084
        L_0x007c:
            r1.setIndeterminate(r0)
            int r9 = r9.mPercent
            r1.setProgress(r9)
        L_0x0084:
            r9 = 2131427792(0x7f0b01d0, float:1.847721E38)
            android.view.View r9 = r10.findViewById(r9)
            android.widget.FrameLayout r9 = (android.widget.FrameLayout) r9
            r9.removeAllViews()
            r9.setVisibility(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.widget.UsageProgressBarPreference.onBindViewHolder(androidx.preference.PreferenceViewHolder):void");
    }
}
