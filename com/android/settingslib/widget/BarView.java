package com.android.settingslib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

public class BarView extends LinearLayout {
    public TextView mBarSummary;
    public TextView mBarTitle;
    public View mBarView;

    public BarView(Context context) {
        super(context);
        init();
    }

    public CharSequence getSummary() {
        return this.mBarSummary.getText();
    }

    public CharSequence getTitle() {
        return this.mBarTitle.getText();
    }

    public BarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
        int color = context.obtainStyledAttributes(new int[]{16843829}).getColor(0, 0);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SettingsBarView);
        int color2 = obtainStyledAttributes.getColor(0, color);
        obtainStyledAttributes.recycle();
        this.mBarView.setBackgroundColor(color2);
    }

    public final void init() {
        LayoutInflater.from(getContext()).inflate(C1777R.layout.settings_bar_view, this);
        setOrientation(1);
        setGravity(81);
        this.mBarView = findViewById(C1777R.C1779id.bar_view);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.icon_view);
        this.mBarTitle = (TextView) findViewById(C1777R.C1779id.bar_title);
        this.mBarSummary = (TextView) findViewById(C1777R.C1779id.bar_summary);
    }
}
