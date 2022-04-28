package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.TouchDelegate;
import android.view.View;
import android.widget.LinearLayout;
import com.android.p012wm.shell.C1777R;

public class BannerMessageView extends LinearLayout {
    public Rect mTouchTargetForDismissButton;

    public BannerMessageView(Context context) {
        super(context);
    }

    public BannerMessageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BannerMessageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        super.onLayout(z, i, i2, i3, i4);
        if (this.mTouchTargetForDismissButton == null) {
            View findViewById = findViewById(C1777R.C1779id.top_row);
            View findViewById2 = findViewById(C1777R.C1779id.banner_dismiss_btn);
            if (findViewById != null && findViewById2 != null && findViewById2.getVisibility() == 0) {
                int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.settingslib_preferred_minimum_touch_target);
                int width = findViewById2.getWidth();
                int height = findViewById2.getHeight();
                int i8 = 0;
                if (width < dimensionPixelSize) {
                    i5 = dimensionPixelSize - width;
                } else {
                    i5 = 0;
                }
                if (height < dimensionPixelSize) {
                    i8 = dimensionPixelSize - height;
                }
                Rect rect = new Rect();
                findViewById2.getHitRect(rect);
                Rect rect2 = new Rect();
                findViewById.getHitRect(rect2);
                Rect rect3 = new Rect();
                this.mTouchTargetForDismissButton = rect3;
                int i9 = rect2.left + rect.left;
                rect3.left = i9;
                int i10 = rect2.left + rect.right;
                rect3.right = i10;
                int i11 = rect2.top + rect.top;
                rect3.top = i11;
                int i12 = rect2.top + rect.bottom;
                rect3.bottom = i12;
                if (i5 % 2 == 1) {
                    i6 = (i5 / 2) + 1;
                } else {
                    i6 = i5 / 2;
                }
                rect3.left = i9 - i6;
                if (i8 % 2 == 1) {
                    i7 = (i8 / 2) + 1;
                } else {
                    i7 = i8 / 2;
                }
                rect3.top = i11 - i7;
                rect3.right = (i5 / 2) + i10;
                rect3.bottom = (i8 / 2) + i12;
                setTouchDelegate(new TouchDelegate(this.mTouchTargetForDismissButton, findViewById2));
            }
        }
    }

    public BannerMessageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
