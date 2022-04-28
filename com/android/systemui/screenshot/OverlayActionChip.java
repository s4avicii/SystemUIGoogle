package com.android.systemui.screenshot;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Icon;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class OverlayActionChip extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageView mIconView;
    public boolean mIsPending;
    public TextView mTextView;

    public OverlayActionChip(Context context) {
        this(context, (AttributeSet) null);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void setIcon(Icon icon, boolean z) {
        this.mIconView.setImageIcon(icon);
        if (!z) {
            this.mIconView.setImageTintList((ColorStateList) null);
        }
    }

    public final void setIsPending(boolean z) {
        this.mIsPending = z;
        setPressed(z);
    }

    public final void setPressed(boolean z) {
        boolean z2;
        if (this.mIsPending || z) {
            z2 = true;
        } else {
            z2 = false;
        }
        super.setPressed(z2);
    }

    public final void setText(CharSequence charSequence) {
        boolean z;
        this.mTextView.setText(charSequence);
        if (charSequence.length() > 0) {
            z = true;
        } else {
            z = false;
        }
        updatePadding(z);
    }

    public final void updatePadding(boolean z) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mTextView.getLayoutParams();
        if (z) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.overlay_action_chip_padding_horizontal);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.overlay_action_chip_spacing);
            layoutParams.setMarginStart(dimensionPixelSize);
            layoutParams.setMarginEnd(dimensionPixelSize2);
            layoutParams2.setMarginEnd(dimensionPixelSize);
        } else {
            int dimensionPixelSize3 = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.overlay_action_chip_icon_only_padding_horizontal);
            layoutParams.setMarginStart(dimensionPixelSize3);
            layoutParams.setMarginEnd(dimensionPixelSize3);
        }
        this.mIconView.setLayoutParams(layoutParams);
        this.mTextView.setLayoutParams(layoutParams2);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        boolean z;
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.overlay_action_chip_icon);
        Objects.requireNonNull(imageView);
        ImageView imageView2 = imageView;
        this.mIconView = imageView;
        TextView textView = (TextView) findViewById(C1777R.C1779id.overlay_action_chip_text);
        Objects.requireNonNull(textView);
        TextView textView2 = textView;
        this.mTextView = textView;
        if (textView.getText().length() > 0) {
            z = true;
        } else {
            z = false;
        }
        updatePadding(z);
    }

    public OverlayActionChip(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIsPending = false;
    }
}
