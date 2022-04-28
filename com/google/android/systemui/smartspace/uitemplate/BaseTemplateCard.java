package com.google.android.systemui.smartspace.uitemplate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.launcher3.icons.GraphicsUtils;
import com.android.p012wm.shell.C1777R;
import com.google.android.systemui.smartspace.DoubleShadowTextView;
import com.google.android.systemui.smartspace.IcuDateTextView;

public class BaseTemplateCard extends LinearLayout {
    public IcuDateTextView mDateView;
    public ImageView mDndImageView;
    public float mDozeAmount;
    public ViewGroup mExtrasGroup;
    public int mIconTintColor;
    public ImageView mNextAlarmImageView;
    public DoubleShadowTextView mNextAlarmTextView;
    public DoubleShadowTextView mSubtitleSupplementalView;
    public DoubleShadowTextView mSubtitleTextView;
    public DoubleShadowTextView mTitleTextView;
    public int mTopPadding;

    public BaseTemplateCard(Context context) {
        this(context, (AttributeSet) null);
    }

    public BaseTemplateCard(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIconTintColor = GraphicsUtils.getAttrColor(getContext(), 16842806);
        this.mDateView = null;
        this.mTitleTextView = null;
        this.mSubtitleTextView = null;
        this.mSubtitleSupplementalView = null;
        this.mExtrasGroup = null;
        this.mDndImageView = null;
        this.mNextAlarmImageView = null;
        this.mNextAlarmTextView = null;
    }

    public final AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityNodeInfo createAccessibilityNodeInfo = super.createAccessibilityNodeInfo();
        createAccessibilityNodeInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", " ");
        return createAccessibilityNodeInfo;
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDateView = (IcuDateTextView) findViewById(C1777R.C1779id.date);
        this.mTitleTextView = (DoubleShadowTextView) findViewById(C1777R.C1779id.title_text);
        this.mSubtitleTextView = (DoubleShadowTextView) findViewById(C1777R.C1779id.subtitle_text);
        this.mSubtitleSupplementalView = (DoubleShadowTextView) findViewById(C1777R.C1779id.base_action_icon_subtitle);
        this.mExtrasGroup = (ViewGroup) findViewById(C1777R.C1779id.smartspace_extras_group);
        this.mTopPadding = getPaddingTop();
        ViewGroup viewGroup = this.mExtrasGroup;
        if (viewGroup != null) {
            this.mDndImageView = (ImageView) viewGroup.findViewById(C1777R.C1779id.dnd_icon);
            this.mNextAlarmImageView = (ImageView) this.mExtrasGroup.findViewById(C1777R.C1779id.alarm_icon);
            this.mNextAlarmTextView = (DoubleShadowTextView) this.mExtrasGroup.findViewById(C1777R.C1779id.alarm_text);
        }
    }
}
