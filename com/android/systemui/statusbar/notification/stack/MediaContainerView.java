package com.android.systemui.statusbar.notification.stack;

import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.notification.row.ExpandableView;

/* compiled from: MediaContainerView.kt */
public final class MediaContainerView extends ExpandableView {
    public int clipHeight;
    public Path clipPath = new Path();
    public RectF clipRect = new RectF();
    public float cornerRadius;

    public final void performAddAnimation(long j, long j2, boolean z) {
    }

    public final long performRemoveAnimation(long j, long j2, float f, boolean z, float f2, Runnable runnable, AnimatorListenerAdapter animatorListenerAdapter) {
        return 0;
    }

    public final void updateClipping() {
        int i = this.clipHeight;
        int i2 = this.mActualHeight;
        if (i != i2) {
            this.clipHeight = i2;
        }
        invalidate();
    }

    public MediaContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setWillNotDraw(false);
        this.cornerRadius = (float) getContext().getResources().getDimensionPixelSize(C1777R.dimen.notification_corner_radius);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.cornerRadius = (float) getContext().getResources().getDimensionPixelSize(C1777R.dimen.notification_corner_radius);
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect clipBounds = canvas.getClipBounds();
        clipBounds.bottom = this.clipHeight;
        this.clipRect.set(clipBounds);
        this.clipPath.reset();
        Path path = this.clipPath;
        RectF rectF = this.clipRect;
        float f = this.cornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        canvas.clipPath(this.clipPath);
    }
}
