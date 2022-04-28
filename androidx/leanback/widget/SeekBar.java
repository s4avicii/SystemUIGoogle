package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import com.android.p012wm.shell.C1777R;

public final class SeekBar extends View {
    public int mActiveBarHeight;
    public int mActiveRadius;
    public final Paint mBackgroundPaint;
    public final RectF mBackgroundRect = new RectF();
    public int mBarHeight;
    public final Paint mKnobPaint;
    public int mKnobx;
    public final Paint mProgressPaint;
    public final RectF mProgressRect = new RectF();
    public final Paint mSecondProgressPaint;
    public final RectF mSecondProgressRect = new RectF();

    public final CharSequence getAccessibilityClassName() {
        return android.widget.SeekBar.class.getName();
    }

    public SeekBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint(1);
        this.mSecondProgressPaint = paint;
        Paint paint2 = new Paint(1);
        this.mProgressPaint = paint2;
        Paint paint3 = new Paint(1);
        this.mBackgroundPaint = paint3;
        Paint paint4 = new Paint(1);
        this.mKnobPaint = paint4;
        setWillNotDraw(false);
        paint3.setColor(-7829368);
        paint.setColor(-3355444);
        paint2.setColor(-65536);
        paint4.setColor(-1);
        this.mBarHeight = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_progressbar_bar_height);
        this.mActiveBarHeight = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_progressbar_active_bar_height);
        this.mActiveRadius = context.getResources().getDimensionPixelSize(C1777R.dimen.lb_playback_transport_progressbar_active_radius);
    }

    public final void calculate() {
        int i;
        int i2;
        if (isFocused()) {
            i = this.mActiveBarHeight;
        } else {
            i = this.mBarHeight;
        }
        int width = getWidth();
        int height = getHeight();
        int i3 = (height - i) / 2;
        RectF rectF = this.mBackgroundRect;
        int i4 = this.mBarHeight;
        float f = (float) i3;
        float f2 = (float) (height - i3);
        rectF.set((float) (i4 / 2), f, (float) (width - (i4 / 2)), f2);
        if (isFocused()) {
            i2 = this.mActiveRadius;
        } else {
            i2 = this.mBarHeight / 2;
        }
        float f3 = (float) 0;
        float f4 = (f3 / f3) * ((float) (width - (i2 * 2)));
        RectF rectF2 = this.mProgressRect;
        int i5 = this.mBarHeight;
        rectF2.set((float) (i5 / 2), f, ((float) (i5 / 2)) + f4, f2);
        this.mSecondProgressRect.set(this.mProgressRect.right, f, ((float) (this.mBarHeight / 2)) + f4, f2);
        this.mKnobx = i2 + ((int) f4);
        invalidate();
    }

    public final void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        if (isFocused()) {
            i = this.mActiveRadius;
        } else {
            i = this.mBarHeight / 2;
        }
        float f = (float) i;
        canvas.drawRoundRect(this.mBackgroundRect, f, f, this.mBackgroundPaint);
        RectF rectF = this.mSecondProgressRect;
        if (rectF.right > rectF.left) {
            canvas.drawRoundRect(rectF, f, f, this.mSecondProgressPaint);
        }
        canvas.drawRoundRect(this.mProgressRect, f, f, this.mProgressPaint);
        canvas.drawCircle((float) this.mKnobx, (float) (getHeight() / 2), f, this.mKnobPaint);
    }

    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        calculate();
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        calculate();
    }

    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        return super.performAccessibilityAction(i, bundle);
    }
}
