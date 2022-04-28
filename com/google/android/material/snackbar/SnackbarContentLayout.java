package com.google.android.material.snackbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import java.util.WeakHashMap;

public class SnackbarContentLayout extends LinearLayout {
    public TextView messageView;

    public SnackbarContentLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView) findViewById(C1777R.C1779id.snackbar_text);
        Button button = (Button) findViewById(C1777R.C1779id.snackbar_action);
    }

    public final void onMeasure(int i, int i2) {
        boolean z;
        super.onMeasure(i, i2);
        int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.design_snackbar_padding_vertical_2lines);
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(C1777R.dimen.design_snackbar_padding_vertical);
        boolean z2 = true;
        boolean z3 = false;
        if (this.messageView.getLayout().getLineCount() > 1) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            dimensionPixelSize = dimensionPixelSize2;
        }
        if (getOrientation() != 0) {
            setOrientation(0);
            z3 = true;
        }
        if (this.messageView.getPaddingTop() == dimensionPixelSize && this.messageView.getPaddingBottom() == dimensionPixelSize) {
            z2 = z3;
        } else {
            TextView textView = this.messageView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api17Impl.isPaddingRelative(textView)) {
                ViewCompat.Api17Impl.setPaddingRelative(textView, ViewCompat.Api17Impl.getPaddingStart(textView), dimensionPixelSize, ViewCompat.Api17Impl.getPaddingEnd(textView), dimensionPixelSize);
            } else {
                textView.setPadding(textView.getPaddingLeft(), dimensionPixelSize, textView.getPaddingRight(), dimensionPixelSize);
            }
        }
        if (z2) {
            super.onMeasure(i, i2);
        }
    }
}
