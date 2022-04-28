package com.android.p012wm.shell.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/* renamed from: com.android.wm.shell.common.AlphaOptimizedButton */
public class AlphaOptimizedButton extends Button {
    public AlphaOptimizedButton(Context context) {
        super(context);
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public AlphaOptimizedButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
