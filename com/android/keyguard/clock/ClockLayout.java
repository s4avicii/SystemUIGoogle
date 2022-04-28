package com.android.keyguard.clock;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$anim;

public class ClockLayout extends FrameLayout {
    public View mAnalogClock;
    public int mBurnInPreventionOffsetX;
    public int mBurnInPreventionOffsetY;

    public ClockLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public ClockLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClockLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mAnalogClock = findViewById(C1777R.C1779id.analog_clock);
        Resources resources = getResources();
        this.mBurnInPreventionOffsetX = resources.getDimensionPixelSize(C1777R.dimen.burn_in_prevention_offset_x);
        this.mBurnInPreventionOffsetY = resources.getDimensionPixelSize(C1777R.dimen.burn_in_prevention_offset_y);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        float lerp = MathUtils.lerp(0.0f, (float) (R$anim.getBurnInOffset(this.mBurnInPreventionOffsetX * 2, true) - this.mBurnInPreventionOffsetX), 0.0f);
        float lerp2 = MathUtils.lerp(0.0f, ((float) R$anim.getBurnInOffset(this.mBurnInPreventionOffsetY * 2, false)) - (((float) this.mBurnInPreventionOffsetY) * 0.5f), 0.0f);
        View view = this.mAnalogClock;
        if (view != null) {
            view.setX((lerp * 3.0f) + Math.max(0.0f, ((float) (getWidth() - this.mAnalogClock.getWidth())) * 0.5f));
            this.mAnalogClock.setY((lerp2 * 3.0f) + Math.max(0.0f, ((float) (getHeight() - this.mAnalogClock.getHeight())) * 0.5f));
        }
    }
}
