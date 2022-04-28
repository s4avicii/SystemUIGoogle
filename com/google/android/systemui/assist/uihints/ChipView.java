package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class ChipView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Drawable BACKGROUND_DARK;
    public final Drawable BACKGROUND_LIGHT;
    public final int TEXT_COLOR_DARK;
    public final int TEXT_COLOR_LIGHT;
    public LinearLayout mChip;
    public ImageView mIconView;
    public TextView mLabelView;
    public Space mSpaceView;

    public ChipView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ChipView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChipView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        LinearLayout linearLayout = (LinearLayout) findViewById(C1777R.C1779id.chip_background);
        Objects.requireNonNull(linearLayout);
        this.mChip = linearLayout;
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.chip_icon);
        Objects.requireNonNull(imageView);
        this.mIconView = imageView;
        TextView textView = (TextView) findViewById(C1777R.C1779id.chip_label);
        Objects.requireNonNull(textView);
        this.mLabelView = textView;
        Space space = (Space) findViewById(C1777R.C1779id.chip_element_padding);
        Objects.requireNonNull(space);
        this.mSpaceView = space;
    }

    public ChipView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.BACKGROUND_DARK = context.getDrawable(C1777R.C1778drawable.assist_chip_background_dark);
        this.BACKGROUND_LIGHT = context.getDrawable(C1777R.C1778drawable.assist_chip_background_light);
        this.TEXT_COLOR_DARK = context.getColor(C1777R.color.assist_chip_text_dark);
        this.TEXT_COLOR_LIGHT = context.getColor(C1777R.color.assist_chip_text_light);
    }
}
