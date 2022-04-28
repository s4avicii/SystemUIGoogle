package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.R$styleable;
import com.android.p012wm.shell.C1777R;

public class ButtonPreference extends Preference {
    public Button mButton;
    public View.OnClickListener mClickListener;
    public int mGravity;
    public Drawable mIcon;
    public CharSequence mTitle;

    public ButtonPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, 0);
        this.mLayoutResId = C1777R.layout.settingslib_button_layout;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.Preference, 0, 0);
            this.mTitle = obtainStyledAttributes.getText(4);
            this.mIcon = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R$styleable.ButtonPreference, 0, 0);
            this.mGravity = obtainStyledAttributes2.getInt(0, 8388611);
            obtainStyledAttributes2.recycle();
        }
    }

    public final void setIcon(Drawable drawable) {
        this.mIcon = drawable;
        if (this.mButton != null && drawable != null) {
            int applyDimension = (int) TypedValue.applyDimension(1, 24.0f, this.mContext.getResources().getDisplayMetrics());
            drawable.setBounds(0, 0, applyDimension, applyDimension);
            this.mButton.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
        }
    }

    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        Button button = this.mButton;
        if (button != null) {
            button.setText(charSequence);
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        this.mButton = (Button) preferenceViewHolder.findViewById(C1777R.C1779id.settingslib_button);
        setTitle(this.mTitle);
        setIcon(this.mIcon);
        int i = this.mGravity;
        if (i == 1 || i == 16 || i == 17) {
            this.mGravity = 1;
        } else {
            this.mGravity = 8388611;
        }
        Button button = this.mButton;
        if (button != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
            layoutParams.gravity = this.mGravity;
            this.mButton.setLayoutParams(layoutParams);
        }
        View.OnClickListener onClickListener = this.mClickListener;
        this.mClickListener = onClickListener;
        Button button2 = this.mButton;
        if (button2 != null) {
            button2.setOnClickListener(onClickListener);
        }
        Button button3 = this.mButton;
        if (button3 != null) {
            boolean z = this.mSelectable;
            button3.setFocusable(z);
            this.mButton.setClickable(z);
            this.mButton.setEnabled(isEnabled());
        }
        preferenceViewHolder.mDividerAllowedAbove = false;
        preferenceViewHolder.mDividerAllowedBelow = false;
    }

    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        Button button = this.mButton;
        if (button != null) {
            button.setEnabled(z);
        }
    }

    public ButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }
}
