package com.android.systemui.util;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R$styleable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: DualHeightHorizontalLinearLayout.kt */
public final class DualHeightHorizontalLinearLayout extends LinearLayout {
    public boolean alwaysSingleLine;
    public int initialPadding;
    public int singleLineHeightPx;
    public final TypedValue singleLineHeightValue;
    public int singleLineVerticalPaddingPx;
    public final TypedValue singleLineVerticalPaddingValue;
    public TextView textView;
    public final int textViewId;

    public DualHeightHorizontalLinearLayout(Context context) {
        this(context, (AttributeSet) null, 0, 0, 14, (DefaultConstructorMarker) null);
    }

    public DualHeightHorizontalLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, (DefaultConstructorMarker) null);
    }

    public DualHeightHorizontalLinearLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, (DefaultConstructorMarker) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DualHeightHorizontalLinearLayout(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    public final void setOrientation(int i) {
        if (i != 1) {
            super.setOrientation(i);
            return;
        }
        throw new IllegalStateException("This view should always have horizontal orientation");
    }

    public DualHeightHorizontalLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.initialPadding = this.mPaddingTop;
        if (getOrientation() == 0) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.DualHeightHorizontalLinearLayout, i, i2);
            TypedValue typedValue = new TypedValue();
            TypedValue typedValue2 = null;
            if (obtainStyledAttributes.hasValue(0)) {
                obtainStyledAttributes.getValue(0, typedValue);
            } else {
                typedValue = null;
            }
            this.singleLineHeightValue = typedValue;
            TypedValue typedValue3 = new TypedValue();
            if (obtainStyledAttributes.hasValue(1)) {
                obtainStyledAttributes.getValue(1, typedValue3);
                typedValue2 = typedValue3;
            }
            this.singleLineVerticalPaddingValue = typedValue2;
            this.textViewId = obtainStyledAttributes.getResourceId(2, 0);
            obtainStyledAttributes.recycle();
            updateResources();
            return;
        }
        throw new IllegalStateException("This view should always have horizontal orientation");
    }

    public final void updateResources() {
        int i;
        int i2;
        TypedValue typedValue = this.singleLineHeightValue;
        int minimumHeight = getMinimumHeight();
        DualHeightHorizontalLinearLayout$updateResources$2 dualHeightHorizontalLinearLayout$updateResources$2 = new DualHeightHorizontalLinearLayout$updateResources$2(this);
        if (typedValue != null) {
            if (typedValue.resourceId != 0) {
                i2 = getContext().getResources().getDimensionPixelSize(typedValue.resourceId);
            } else {
                i2 = (int) typedValue.getDimension(getContext().getResources().getDisplayMetrics());
            }
            minimumHeight = i2;
        }
        dualHeightHorizontalLinearLayout$updateResources$2.set(Integer.valueOf(minimumHeight));
        TypedValue typedValue2 = this.singleLineVerticalPaddingValue;
        int i3 = 0;
        DualHeightHorizontalLinearLayout$updateResources$4 dualHeightHorizontalLinearLayout$updateResources$4 = new DualHeightHorizontalLinearLayout$updateResources$4(this);
        if (typedValue2 != null) {
            if (typedValue2.resourceId != 0) {
                i = getContext().getResources().getDimensionPixelSize(typedValue2.resourceId);
            } else {
                i = (int) typedValue2.getDimension(getContext().getResources().getDisplayMetrics());
            }
            i3 = i;
        }
        dualHeightHorizontalLinearLayout$updateResources$4.set(Integer.valueOf(i3));
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        TextView textView2 = (TextView) findViewById(this.textViewId);
        if (textView2 == null) {
            textView2 = null;
        } else {
            textView2.getMaxLines();
        }
        this.textView = textView2;
    }

    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        TextView textView2 = this.textView;
        if (textView2 != null) {
            if (textView2.getLineCount() < 2 || this.alwaysSingleLine) {
                setMeasuredDimension(getMeasuredWidth(), this.singleLineHeightPx);
                this.mPaddingBottom = 0;
                this.mPaddingTop = 0;
                return;
            }
            int i3 = this.initialPadding;
            this.mPaddingBottom = i3;
            this.mPaddingTop = i3;
        }
    }

    public final void setPadding(int i, int i2, int i3, int i4) {
        super.setPadding(i, i2, i3, i4);
        this.initialPadding = i2;
    }

    public final void setPaddingRelative(int i, int i2, int i3, int i4) {
        super.setPaddingRelative(i, i2, i3, i4);
        this.initialPadding = i2;
    }
}
