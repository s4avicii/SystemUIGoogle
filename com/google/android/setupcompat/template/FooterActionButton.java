package com.google.android.setupcompat.template;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import java.util.Objects;

public class FooterActionButton extends Button {
    public FooterButton footerButton;
    public boolean isPrimaryButtonStyle = false;

    public FooterActionButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        FooterButton footerButton2;
        if (motionEvent.getAction() == 0 && (footerButton2 = this.footerButton) != null && !footerButton2.enabled) {
            Objects.requireNonNull(footerButton2);
            Objects.requireNonNull(this.footerButton);
        }
        return super.onTouchEvent(motionEvent);
    }
}
