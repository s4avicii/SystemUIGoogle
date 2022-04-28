package com.android.launcher3.icons;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.graphics.drawable.ColorDrawable;
import androidx.core.graphics.ColorUtils;
import com.android.p012wm.shell.C1777R;

public final class PlaceHolderIconDrawable extends FastBitmapDrawable {
    public final Path mProgressPath;

    public PlaceHolderIconDrawable(BitmapInfo bitmapInfo, Context context) {
        super(bitmapInfo.icon, bitmapInfo.color);
        int i = GraphicsUtils.$r8$clinit;
        AdaptiveIconDrawable adaptiveIconDrawable = new AdaptiveIconDrawable(new ColorDrawable(-16777216), new ColorDrawable(-16777216));
        adaptiveIconDrawable.setBounds(0, 0, 100, 100);
        this.mProgressPath = new Path(adaptiveIconDrawable.getIconMask());
        this.mPaint.setColor(ColorUtils.compositeColors(GraphicsUtils.getAttrColor(context, C1777R.attr.loadingIconColor), bitmapInfo.color));
    }

    public final void drawInternal(Canvas canvas, Rect rect) {
        int save = canvas.save();
        canvas.translate((float) rect.left, (float) rect.top);
        canvas.scale(((float) rect.width()) / 100.0f, ((float) rect.height()) / 100.0f);
        canvas.drawPath(this.mProgressPath, this.mPaint);
        canvas.restoreToCount(save);
    }
}
