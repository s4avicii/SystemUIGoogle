package com.google.android.setupdesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import com.android.p012wm.shell.C1777R;

public class IconUniformityAppImageView extends ImageView {
    public static final boolean ON_L_PLUS = true;
    public int backdropColorResId = 0;
    public final GradientDrawable backdropDrawable = new GradientDrawable();

    static {
        Float.valueOf(0.75f).floatValue();
    }

    public IconUniformityAppImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean z = ON_L_PLUS;
        super.onDraw(canvas);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.backdropColorResId = C1777R.color.sud_uniformity_backdrop_color;
        GradientDrawable gradientDrawable = this.backdropDrawable;
        Context context = getContext();
        int i = this.backdropColorResId;
        Object obj = ContextCompat.sLock;
        gradientDrawable.setColor(context.getColor(i));
    }
}
