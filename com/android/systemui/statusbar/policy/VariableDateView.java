package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$styleable;

/* compiled from: VariableDateView.kt */
public final class VariableDateView extends TextView {
    public boolean freezeSwitching;
    public final String longerPattern;
    public OnMeasureListener onMeasureListener;
    public final String shorterPattern;

    /* compiled from: VariableDateView.kt */
    public interface OnMeasureListener {
        void onMeasureAction(int i);
    }

    public VariableDateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.VariableDateView, 0, 0);
        String string = obtainStyledAttributes.getString(0);
        this.longerPattern = string == null ? context.getString(C1777R.string.system_ui_date_pattern) : string;
        String string2 = obtainStyledAttributes.getString(1);
        this.shorterPattern = string2 == null ? context.getString(C1777R.string.abbrev_month_day_no_year) : string2;
        obtainStyledAttributes.recycle();
    }

    public final void onMeasure(int i, int i2) {
        OnMeasureListener onMeasureListener2;
        int size = (View.MeasureSpec.getSize(i) - getPaddingStart()) - getPaddingEnd();
        if (!(View.MeasureSpec.getMode(i) == 0 || this.freezeSwitching || (onMeasureListener2 = this.onMeasureListener) == null)) {
            onMeasureListener2.onMeasureAction(size);
        }
        super.onMeasure(i, i2);
    }
}
