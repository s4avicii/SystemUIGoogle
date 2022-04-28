package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;

public class KeyboardIconView extends FrameLayout {
    public final int COLOR_DARK_BACKGROUND;
    public final int COLOR_LIGHT_BACKGROUND;
    public ImageView mKeyboardIcon;

    public KeyboardIconView(Context context) {
        this(context, (AttributeSet) null);
    }

    public KeyboardIconView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyboardIconView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        this.mKeyboardIcon = (ImageView) findViewById(C1777R.C1779id.keyboard_icon_image);
    }

    public KeyboardIconView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.COLOR_DARK_BACKGROUND = getResources().getColor(C1777R.color.transcription_icon_dark);
        this.COLOR_LIGHT_BACKGROUND = getResources().getColor(C1777R.color.transcription_icon_light);
    }
}
