package com.android.systemui.tuner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.preference.CheckBoxPreference;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.ScalingDrawableWrapper;

public class SelectablePreference extends CheckBoxPreference {
    public final int mSize;

    public SelectablePreference(Context context) {
        super(context, (AttributeSet) null);
        this.mWidgetLayoutResId = C1777R.layout.preference_widget_radiobutton;
        if (!this.mSelectable) {
            this.mSelectable = true;
            notifyChanged();
        }
        this.mSize = (int) TypedValue.applyDimension(1, 32.0f, context.getResources().getDisplayMetrics());
    }

    public String toString() {
        return "";
    }

    public final void setIcon(Drawable drawable) {
        super.setIcon(new ScalingDrawableWrapper(drawable, ((float) this.mSize) / ((float) drawable.getIntrinsicWidth())));
    }
}
