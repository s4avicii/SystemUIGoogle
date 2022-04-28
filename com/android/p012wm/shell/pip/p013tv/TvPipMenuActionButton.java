package com.android.p012wm.shell.pip.p013tv;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.android.wm.shell.pip.tv.TvPipMenuActionButton */
public class TvPipMenuActionButton extends RelativeLayout implements View.OnClickListener {
    public final View mButtonView;
    public final ImageView mIconImageView;
    public View.OnClickListener mOnClickListener;

    public TvPipMenuActionButton(Context context) {
        this(context, (AttributeSet) null, 0, 0);
    }

    public TvPipMenuActionButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0);
    }

    public final boolean isEnabled() {
        return this.mButtonView.isEnabled();
    }

    public final void onClick(View view) {
        View.OnClickListener onClickListener = this.mOnClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    public final void setEnabled(boolean z) {
        this.mButtonView.setEnabled(z);
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        View view = this.mButtonView;
        if (onClickListener == null) {
            this = null;
        }
        view.setOnClickListener(this);
    }

    public TvPipMenuActionButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public TvPipMenuActionButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1777R.layout.tv_pip_menu_action_button, this);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.icon);
        this.mIconImageView = imageView;
        View findViewById = findViewById(C1777R.C1779id.button);
        this.mButtonView = findViewById;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16843033, 16843087}, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            imageView.setImageResource(resourceId);
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        if (resourceId2 != 0) {
            findViewById.setContentDescription(getContext().getString(resourceId2));
        }
        obtainStyledAttributes.recycle();
    }
}
