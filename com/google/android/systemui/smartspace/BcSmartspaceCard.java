package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceTarget;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.p012wm.shell.C1777R;

public class BcSmartspaceCard extends LinearLayout {
    public DoubleShadowTextView mBaseActionIconSubtitleView;
    public IcuDateTextView mDateView;
    public ImageView mDndImageView;
    public float mDozeAmount;
    public ViewGroup mExtrasGroup;
    public DoubleShadowIconDrawable mIconDrawable;
    public int mIconTintColor;
    public ImageView mNextAlarmImageView;
    public TextView mNextAlarmTextView;
    public BcSmartspaceCardSecondary mSecondaryCard;
    public TextView mSubtitleTextView;
    public SmartspaceTarget mTarget;
    public TextView mTitleTextView;
    public int mTopPadding;
    public boolean mUsePageIndicatorUi;

    public BcSmartspaceCard(Context context) {
        this(context, (AttributeSet) null);
    }

    public BcSmartspaceCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSecondaryCard = null;
        this.mIconTintColor = GraphicsUtils.getAttrColor(getContext(), 16842806);
        this.mDateView = null;
        this.mTitleTextView = null;
        this.mSubtitleTextView = null;
        this.mBaseActionIconSubtitleView = null;
        this.mExtrasGroup = null;
        this.mDndImageView = null;
        this.mNextAlarmImageView = null;
        this.mNextAlarmTextView = null;
    }

    public final void setPrimaryTextColor(int i) {
        TextView textView = this.mTitleTextView;
        if (textView != null) {
            textView.setTextColor(i);
        }
        IcuDateTextView icuDateTextView = this.mDateView;
        if (icuDateTextView != null) {
            icuDateTextView.setTextColor(i);
        }
        TextView textView2 = this.mSubtitleTextView;
        if (textView2 != null) {
            textView2.setTextColor(i);
        }
        DoubleShadowTextView doubleShadowTextView = this.mBaseActionIconSubtitleView;
        if (doubleShadowTextView != null) {
            doubleShadowTextView.setTextColor(i);
        }
        this.mIconTintColor = i;
        TextView textView3 = this.mNextAlarmTextView;
        if (textView3 != null) {
            textView3.setTextColor(i);
        }
        ImageView imageView = this.mNextAlarmImageView;
        if (!(imageView == null || imageView.getDrawable() == null)) {
            imageView.getDrawable().setTint(this.mIconTintColor);
        }
        ImageView imageView2 = this.mDndImageView;
        if (!(imageView2 == null || imageView2.getDrawable() == null)) {
            imageView2.getDrawable().setTint(this.mIconTintColor);
        }
        updateIconTint();
    }

    /* JADX WARNING: Removed duplicated region for block: B:73:0x0176  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01e1  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01e3  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01f5 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setSmartspaceTarget(android.app.smartspace.SmartspaceTarget r17, com.google.android.systemui.smartspace.CardPagerAdapter$$ExternalSyntheticLambda0 r18, com.google.android.systemui.smartspace.BcSmartspaceCardLoggingInfo r19, boolean r20) {
        /*
            r16 = this;
            r0 = r16
            r7 = r17
            r0.mTarget = r7
            android.app.smartspace.SmartspaceAction r8 = r17.getHeaderAction()
            android.app.smartspace.SmartspaceAction r9 = r17.getBaseAction()
            r1 = r20
            r0.mUsePageIndicatorUi = r1
            r10 = 1
            r1 = 0
            if (r8 == 0) goto L_0x010e
            com.google.android.systemui.smartspace.BcSmartspaceCardSecondary r2 = r0.mSecondaryCard
            r13 = r18
            r12 = r19
            if (r2 == 0) goto L_0x002d
            boolean r2 = r2.setSmartspaceActions(r7, r13, r12)
            com.google.android.systemui.smartspace.BcSmartspaceCardSecondary r3 = r0.mSecondaryCard
            if (r2 == 0) goto L_0x0028
            r2 = 0
            goto L_0x002a
        L_0x0028:
            r2 = 8
        L_0x002a:
            r3.setVisibility(r2)
        L_0x002d:
            android.graphics.drawable.Icon r2 = r8.getIcon()
            android.content.Context r3 = r16.getContext()
            android.graphics.drawable.Drawable r2 = com.google.android.systemui.smartspace.BcSmartSpaceUtil.getIconDrawable(r2, r3)
            if (r2 != 0) goto L_0x003d
            r3 = r1
            goto L_0x0046
        L_0x003d:
            com.google.android.systemui.smartspace.DoubleShadowIconDrawable r3 = new com.google.android.systemui.smartspace.DoubleShadowIconDrawable
            android.content.Context r4 = r16.getContext()
            r3.<init>(r2, r4)
        L_0x0046:
            r0.mIconDrawable = r3
            java.lang.CharSequence r2 = r8.getTitle()
            java.lang.CharSequence r3 = r8.getSubtitle()
            int r4 = r17.getFeatureType()
            if (r4 == r10) goto L_0x005f
            boolean r4 = android.text.TextUtils.isEmpty(r2)
            if (r4 != 0) goto L_0x005d
            goto L_0x005f
        L_0x005d:
            r4 = 0
            goto L_0x0060
        L_0x005f:
            r4 = r10
        L_0x0060:
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            r5 = r5 ^ r10
            r16.updateZenVisibility()
            if (r4 == 0) goto L_0x006b
            goto L_0x006c
        L_0x006b:
            r2 = r3
        L_0x006c:
            java.lang.CharSequence r6 = r8.getContentDescription()
            if (r4 == r5) goto L_0x0074
            r14 = r10
            goto L_0x0075
        L_0x0074:
            r14 = 0
        L_0x0075:
            android.widget.TextView r15 = r0.mTitleTextView
            java.lang.String r10 = "BcSmartspaceCard"
            if (r15 != 0) goto L_0x0081
            java.lang.String r2 = "No title view to update"
            android.util.Log.w(r10, r2)
            goto L_0x00ca
        L_0x0081:
            r15.setText(r2)
            android.widget.TextView r15 = r0.mTitleTextView
            if (r14 == 0) goto L_0x008b
            com.google.android.systemui.smartspace.DoubleShadowIconDrawable r11 = r0.mIconDrawable
            goto L_0x008c
        L_0x008b:
            r11 = r1
        L_0x008c:
            r15.setCompoundDrawablesRelative(r11, r1, r1, r1)
            android.app.smartspace.SmartspaceTarget r11 = r0.mTarget
            int r11 = r11.getFeatureType()
            r15 = 2
            if (r11 != r15) goto L_0x00bc
            java.util.Locale r11 = java.util.Locale.ENGLISH
            java.lang.String r11 = r11.getLanguage()
            android.content.Context r15 = r0.mContext
            android.content.res.Resources r15 = r15.getResources()
            android.content.res.Configuration r15 = r15.getConfiguration()
            java.util.Locale r15 = r15.locale
            java.lang.String r15 = r15.getLanguage()
            boolean r11 = r11.equals(r15)
            if (r11 == 0) goto L_0x00bc
            android.widget.TextView r11 = r0.mTitleTextView
            android.text.TextUtils$TruncateAt r15 = android.text.TextUtils.TruncateAt.MIDDLE
            r11.setEllipsize(r15)
            goto L_0x00c3
        L_0x00bc:
            android.widget.TextView r11 = r0.mTitleTextView
            android.text.TextUtils$TruncateAt r15 = android.text.TextUtils.TruncateAt.END
            r11.setEllipsize(r15)
        L_0x00c3:
            if (r14 == 0) goto L_0x00ca
            android.widget.TextView r11 = r0.mTitleTextView
            r0.setFormattedContentDescription(r11, r2, r6)
        L_0x00ca:
            if (r4 == 0) goto L_0x00cf
            if (r5 == 0) goto L_0x00cf
            goto L_0x00d0
        L_0x00cf:
            r3 = r1
        L_0x00d0:
            java.lang.CharSequence r2 = r8.getContentDescription()
            android.widget.TextView r4 = r0.mSubtitleTextView
            if (r4 != 0) goto L_0x00de
            java.lang.String r2 = "No subtitle view to update"
            android.util.Log.w(r10, r2)
            goto L_0x010a
        L_0x00de:
            r4.setText(r3)
            android.widget.TextView r4 = r0.mSubtitleTextView
            boolean r5 = android.text.TextUtils.isEmpty(r3)
            if (r5 == 0) goto L_0x00eb
            r5 = r1
            goto L_0x00ed
        L_0x00eb:
            com.google.android.systemui.smartspace.DoubleShadowIconDrawable r5 = r0.mIconDrawable
        L_0x00ed:
            r4.setCompoundDrawablesRelative(r5, r1, r1, r1)
            android.widget.TextView r4 = r0.mSubtitleTextView
            android.app.smartspace.SmartspaceTarget r5 = r0.mTarget
            int r5 = r5.getFeatureType()
            r6 = 5
            if (r5 != r6) goto L_0x0101
            boolean r5 = r0.mUsePageIndicatorUi
            if (r5 != 0) goto L_0x0101
            r11 = 2
            goto L_0x0102
        L_0x0101:
            r11 = 1
        L_0x0102:
            r4.setMaxLines(r11)
            android.widget.TextView r4 = r0.mSubtitleTextView
            r0.setFormattedContentDescription(r4, r3, r2)
        L_0x010a:
            r16.updateIconTint()
            goto L_0x0112
        L_0x010e:
            r13 = r18
            r12 = r19
        L_0x0112:
            if (r9 == 0) goto L_0x0171
            com.google.android.systemui.smartspace.DoubleShadowTextView r2 = r0.mBaseActionIconSubtitleView
            if (r2 == 0) goto L_0x0171
            android.graphics.drawable.Icon r2 = r9.getIcon()
            if (r2 != 0) goto L_0x0120
            r2 = r1
            goto L_0x012c
        L_0x0120:
            android.graphics.drawable.Icon r2 = r9.getIcon()
            android.content.Context r3 = r16.getContext()
            android.graphics.drawable.Drawable r2 = com.google.android.systemui.smartspace.BcSmartSpaceUtil.getIconDrawable(r2, r3)
        L_0x012c:
            if (r2 != 0) goto L_0x013f
            com.google.android.systemui.smartspace.DoubleShadowTextView r2 = r0.mBaseActionIconSubtitleView
            r3 = 4
            r2.setVisibility(r3)
            com.google.android.systemui.smartspace.DoubleShadowTextView r2 = r0.mBaseActionIconSubtitleView
            r2.setOnClickListener(r1)
            com.google.android.systemui.smartspace.DoubleShadowTextView r2 = r0.mBaseActionIconSubtitleView
            r2.setContentDescription(r1)
            goto L_0x0171
        L_0x013f:
            r2.setTintList(r1)
            com.google.android.systemui.smartspace.DoubleShadowTextView r3 = r0.mBaseActionIconSubtitleView
            java.lang.CharSequence r4 = r9.getSubtitle()
            r3.setText(r4)
            com.google.android.systemui.smartspace.DoubleShadowTextView r3 = r0.mBaseActionIconSubtitleView
            r3.setCompoundDrawablesRelative(r2, r1, r1, r1)
            com.google.android.systemui.smartspace.DoubleShadowTextView r1 = r0.mBaseActionIconSubtitleView
            r10 = 0
            r1.setVisibility(r10)
            com.google.android.systemui.smartspace.DoubleShadowTextView r1 = r0.mBaseActionIconSubtitleView
            r6 = 0
            java.lang.String r4 = "BcSmartspaceCard"
            r2 = r17
            r3 = r9
            r5 = r18
            com.google.android.systemui.smartspace.BcSmartSpaceUtil.setOnClickListener(r1, r2, r3, r4, r5, r6)
            com.google.android.systemui.smartspace.DoubleShadowTextView r1 = r0.mBaseActionIconSubtitleView
            java.lang.CharSequence r2 = r9.getSubtitle()
            java.lang.CharSequence r3 = r9.getContentDescription()
            r0.setFormattedContentDescription(r1, r2, r3)
            goto L_0x0172
        L_0x0171:
            r10 = 0
        L_0x0172:
            com.google.android.systemui.smartspace.IcuDateTextView r1 = r0.mDateView
            if (r1 == 0) goto L_0x01d3
            if (r8 == 0) goto L_0x017d
            java.lang.String r1 = r8.getId()
            goto L_0x018c
        L_0x017d:
            if (r9 == 0) goto L_0x0184
            java.lang.String r1 = r9.getId()
            goto L_0x018c
        L_0x0184:
            java.util.UUID r1 = java.util.UUID.randomUUID()
            java.lang.String r1 = r1.toString()
        L_0x018c:
            android.app.smartspace.SmartspaceAction$Builder r2 = new android.app.smartspace.SmartspaceAction$Builder
            java.lang.String r3 = "unusedTitle"
            r2.<init>(r1, r3)
            android.net.Uri r1 = android.provider.CalendarContract.CONTENT_URI
            android.net.Uri$Builder r1 = r1.buildUpon()
            java.lang.String r3 = "time"
            android.net.Uri$Builder r1 = r1.appendPath(r3)
            long r3 = java.lang.System.currentTimeMillis()
            android.net.Uri$Builder r1 = android.content.ContentUris.appendId(r1, r3)
            android.net.Uri r1 = r1.build()
            android.content.Intent r3 = new android.content.Intent
            java.lang.String r4 = "android.intent.action.VIEW"
            r3.<init>(r4)
            android.content.Intent r1 = r3.setData(r1)
            r3 = 270532608(0x10200000, float:3.1554436E-29)
            android.content.Intent r1 = r1.addFlags(r3)
            android.app.smartspace.SmartspaceAction$Builder r1 = r2.setIntent(r1)
            android.app.smartspace.SmartspaceAction r3 = r1.build()
            com.google.android.systemui.smartspace.IcuDateTextView r1 = r0.mDateView
            java.lang.String r4 = "BcSmartspaceCard"
            r2 = r17
            r5 = r18
            r6 = r19
            com.google.android.systemui.smartspace.BcSmartSpaceUtil.setOnClickListener(r1, r2, r3, r4, r5, r6)
        L_0x01d3:
            if (r8 == 0) goto L_0x01e3
            android.content.Intent r1 = r8.getIntent()
            if (r1 != 0) goto L_0x01e1
            android.app.PendingIntent r1 = r8.getPendingIntent()
            if (r1 == 0) goto L_0x01e3
        L_0x01e1:
            r1 = 1
            goto L_0x01e4
        L_0x01e3:
            r1 = r10
        L_0x01e4:
            if (r1 == 0) goto L_0x01f5
            java.lang.String r3 = "BcSmartspaceCard"
            r0 = r16
            r1 = r17
            r2 = r8
            r4 = r18
            r5 = r19
            com.google.android.systemui.smartspace.BcSmartSpaceUtil.setOnClickListener(r0, r1, r2, r3, r4, r5)
            goto L_0x0223
        L_0x01f5:
            if (r9 == 0) goto L_0x0204
            android.content.Intent r1 = r9.getIntent()
            if (r1 != 0) goto L_0x0203
            android.app.PendingIntent r1 = r9.getPendingIntent()
            if (r1 == 0) goto L_0x0204
        L_0x0203:
            r10 = 1
        L_0x0204:
            if (r10 == 0) goto L_0x0215
            java.lang.String r3 = "BcSmartspaceCard"
            r0 = r16
            r1 = r17
            r2 = r9
            r4 = r18
            r5 = r19
            com.google.android.systemui.smartspace.BcSmartSpaceUtil.setOnClickListener(r0, r1, r2, r3, r4, r5)
            goto L_0x0223
        L_0x0215:
            java.lang.String r3 = "BcSmartspaceCard"
            r0 = r16
            r1 = r17
            r2 = r8
            r4 = r18
            r5 = r19
            com.google.android.systemui.smartspace.BcSmartSpaceUtil.setOnClickListener(r0, r1, r2, r3, r4, r5)
        L_0x0223:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.BcSmartspaceCard.setSmartspaceTarget(android.app.smartspace.SmartspaceTarget, com.google.android.systemui.smartspace.CardPagerAdapter$$ExternalSyntheticLambda0, com.google.android.systemui.smartspace.BcSmartspaceCardLoggingInfo, boolean):void");
    }

    public final void updateIconTint() {
        SmartspaceTarget smartspaceTarget = this.mTarget;
        if (smartspaceTarget != null && this.mIconDrawable != null) {
            boolean z = true;
            if (smartspaceTarget.getFeatureType() == 1) {
                z = false;
            }
            if (z) {
                this.mIconDrawable.setTint(this.mIconTintColor);
            } else {
                this.mIconDrawable.setTintList((ColorStateList) null);
            }
        }
    }

    public final void updateZenVisibility() {
        boolean z;
        boolean z2;
        if (this.mExtrasGroup != null) {
            ImageView imageView = this.mDndImageView;
            boolean z3 = true;
            int i = 0;
            if (imageView == null || imageView.getVisibility() != 0) {
                z = false;
            } else {
                z = true;
            }
            ImageView imageView2 = this.mNextAlarmImageView;
            if (imageView2 == null || imageView2.getVisibility() != 0) {
                z2 = false;
            } else {
                z2 = true;
            }
            if ((!z && !z2) || (this.mUsePageIndicatorUi && this.mTarget.getFeatureType() != 1)) {
                z3 = false;
            }
            int i2 = this.mTopPadding;
            if (!z3) {
                this.mExtrasGroup.setVisibility(4);
                i = i2;
            } else {
                this.mExtrasGroup.setVisibility(0);
                TextView textView = this.mNextAlarmTextView;
                if (textView != null) {
                    textView.setTextColor(this.mIconTintColor);
                }
                ImageView imageView3 = this.mNextAlarmImageView;
                if (!(imageView3 == null || imageView3.getDrawable() == null)) {
                    imageView3.getDrawable().setTint(this.mIconTintColor);
                }
                ImageView imageView4 = this.mDndImageView;
                if (!(imageView4 == null || imageView4.getDrawable() == null)) {
                    imageView4.getDrawable().setTint(this.mIconTintColor);
                }
            }
            setPadding(getPaddingLeft(), i, getPaddingRight(), getPaddingBottom());
        }
    }

    public final AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        createAccessibilityNodeInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", " ");
        return createAccessibilityNodeInfo;
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDateView = (IcuDateTextView) findViewById(C1777R.C1779id.date);
        this.mTitleTextView = (TextView) findViewById(C1777R.C1779id.title_text);
        this.mSubtitleTextView = (TextView) findViewById(C1777R.C1779id.subtitle_text);
        this.mBaseActionIconSubtitleView = (DoubleShadowTextView) findViewById(C1777R.C1779id.base_action_icon_subtitle);
        this.mExtrasGroup = (ViewGroup) findViewById(C1777R.C1779id.smartspace_extras_group);
        this.mTopPadding = getPaddingTop();
        ViewGroup viewGroup = this.mExtrasGroup;
        if (viewGroup != null) {
            this.mDndImageView = (ImageView) viewGroup.findViewById(C1777R.C1779id.dnd_icon);
            this.mNextAlarmImageView = (ImageView) this.mExtrasGroup.findViewById(C1777R.C1779id.alarm_icon);
            this.mNextAlarmTextView = (TextView) this.mExtrasGroup.findViewById(C1777R.C1779id.alarm_text);
        }
    }

    public final void setFormattedContentDescription(TextView textView, CharSequence charSequence, CharSequence charSequence2) {
        if (TextUtils.isEmpty(charSequence)) {
            charSequence = charSequence2;
        } else if (!TextUtils.isEmpty(charSequence2)) {
            charSequence = this.mContext.getString(C1777R.string.generic_smartspace_concatenated_desc, new Object[]{charSequence2, charSequence});
        }
        textView.setContentDescription(charSequence);
    }
}
