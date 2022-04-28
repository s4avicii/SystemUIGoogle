package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;

public class ZeroStateIconView extends FrameLayout {
    public final int COLOR_DARK_BACKGROUND;
    public final int COLOR_LIGHT_BACKGROUND;
    public ImageView mZeroStateIcon;

    public ZeroStateIconView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ZeroStateIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ZeroStateIconView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        this.mZeroStateIcon = (ImageView) findViewById(C1777R.C1779id.zerostate_icon_image);
    }

    public ZeroStateIconView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.COLOR_DARK_BACKGROUND = getResources().getColor(C1777R.color.transcription_icon_dark);
        this.COLOR_LIGHT_BACKGROUND = getResources().getColor(C1777R.color.transcription_icon_light);
    }
}
