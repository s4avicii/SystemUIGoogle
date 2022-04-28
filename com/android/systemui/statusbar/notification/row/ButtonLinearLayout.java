package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;

public class ButtonLinearLayout extends LinearLayout {
    public final CharSequence getAccessibilityClassName() {
        return Button.class.getName();
    }

    public ButtonLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
