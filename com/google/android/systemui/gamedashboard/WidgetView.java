package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;

public class WidgetView extends FrameLayout {
    public LinearLayout mContentView;
    public final int mDefaultBackgroundColor;
    public TextView mDescription;
    public ImageView mIcon;
    public boolean mLoading;
    public LinearLayout mLoadingView;
    public final Drawable mOvalBackgroundDrawable;
    public TextView mTipText;
    public TextView mTitle;

    public final void setLoading(boolean z) {
        if (z != this.mLoading) {
            this.mLoading = z;
            if (z) {
                this.mLoadingView.setVisibility(0);
                this.mContentView.setVisibility(8);
                return;
            }
            this.mLoadingView.setVisibility(8);
            this.mContentView.setVisibility(0);
        }
    }

    public final void update(Drawable drawable, int i, int i2, View.OnClickListener onClickListener) {
        int i3 = this.mDefaultBackgroundColor;
        this.mIcon.setImageDrawable(drawable);
        this.mOvalBackgroundDrawable.setColorFilter(new PorterDuffColorFilter(i3, PorterDuff.Mode.SRC_ATOP));
        this.mIcon.setBackground(this.mOvalBackgroundDrawable);
        this.mTitle.setText(i);
        this.mDescription.setText(i2);
        setOnClickListener(onClickListener);
    }

    public WidgetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOvalBackgroundDrawable = context.getDrawable(C1777R.C1778drawable.game_dashboard_button_background);
        this.mDefaultBackgroundColor = context.getColor(C1777R.color.game_dashboard_color_surface);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mIcon = (ImageView) findViewById(C1777R.C1779id.icon);
        this.mTipText = (TextView) findViewById(C1777R.C1779id.tip_text);
        this.mTitle = (TextView) findViewById(C1777R.C1779id.title);
        this.mDescription = (TextView) findViewById(C1777R.C1779id.description);
        this.mContentView = (LinearLayout) findViewById(C1777R.C1779id.content_view);
        this.mLoadingView = (LinearLayout) findViewById(C1777R.C1779id.loading_view);
        this.mLoading = false;
    }

    public final void setEnabled(boolean z) {
        float f;
        super.setEnabled(z);
        if (isEnabled()) {
            f = 1.0f;
        } else {
            f = 0.5f;
        }
        setAlpha(f);
    }
}
