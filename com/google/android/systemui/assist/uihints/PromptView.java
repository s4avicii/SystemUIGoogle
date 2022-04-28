package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.policy.ConfigurationController;

public class PromptView extends TextView implements ConfigurationController.ConfigurationListener {
    public final DecelerateInterpolator mDecelerateInterpolator;
    public boolean mEnabled;
    public String mHandleString;
    public boolean mHasDarkBackground;
    public int mLastInvocationType;
    public final float mRiseDistance;
    public String mSqueezeString;
    public final int mTextColorDark;
    public final int mTextColorLight;

    public PromptView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void disable$1() {
        this.mEnabled = false;
        setVisibility(8);
    }

    public PromptView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void onDensityOrFontScaleChanged() {
        setTextSize(0, this.mContext.getResources().getDimension(C1777R.dimen.transcription_text_size));
        updateViewHeight();
    }

    public PromptView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onConfigChanged(Configuration configuration) {
        this.mHandleString = getResources().getString(C1777R.string.handle_invocation_prompt);
        this.mSqueezeString = getResources().getString(C1777R.string.squeeze_invocation_prompt);
    }

    public final void updateViewHeight() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = (int) (this.mContext.getResources().getDimension(C1777R.dimen.transcription_text_size) + getResources().getDimension(C1777R.dimen.assist_prompt_start_height) + this.mRiseDistance);
        }
        requestLayout();
    }

    public PromptView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDecelerateInterpolator = new DecelerateInterpolator(2.0f);
        this.mHasDarkBackground = false;
        this.mEnabled = false;
        this.mLastInvocationType = 0;
        int color = getContext().getColor(C1777R.color.transcription_text_dark);
        this.mTextColorDark = color;
        int color2 = getContext().getColor(C1777R.color.transcription_text_light);
        this.mTextColorLight = color2;
        this.mRiseDistance = getResources().getDimension(C1777R.dimen.assist_prompt_rise_distance);
        this.mHandleString = getResources().getString(C1777R.string.handle_invocation_prompt);
        this.mSqueezeString = getResources().getString(C1777R.string.squeeze_invocation_prompt);
        ((ConfigurationController) Dependency.get(ConfigurationController.class)).addCallback(this);
        boolean z = this.mHasDarkBackground;
        boolean z2 = !z;
        if (z2 != z) {
            setTextColor(!z2 ? color2 : color);
            this.mHasDarkBackground = z2;
        }
    }

    public final void onFinishInflate() {
        updateViewHeight();
    }
}
