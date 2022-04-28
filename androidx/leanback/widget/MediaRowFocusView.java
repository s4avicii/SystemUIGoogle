package androidx.leanback.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.android.p012wm.shell.C1777R;

class MediaRowFocusView extends View {
    public final Paint mPaint;
    public final RectF mRoundRectF = new RectF();
    public int mRoundRectRadius;

    public MediaRowFocusView(Context context) {
        super(context);
        this.mPaint = createPaint(context);
    }

    public static Paint createPaint(Context context) {
        Paint paint = new Paint();
        paint.setColor(context.getResources().getColor(C1777R.color.lb_playback_media_row_highlight_color));
        return paint;
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight() / 2;
        this.mRoundRectRadius = height;
        int height2 = ((height * 2) - getHeight()) / 2;
        this.mRoundRectF.set(0.0f, (float) (-height2), (float) getWidth(), (float) (getHeight() + height2));
        RectF rectF = this.mRoundRectF;
        int i = this.mRoundRectRadius;
        canvas.drawRoundRect(rectF, (float) i, (float) i, this.mPaint);
    }

    public MediaRowFocusView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = createPaint(context);
    }

    public MediaRowFocusView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPaint = createPaint(context);
    }
}
