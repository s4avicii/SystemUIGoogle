package com.android.systemui.p006qs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;

/* renamed from: com.android.systemui.qs.DataUsageGraph */
public class DataUsageGraph extends View {
    public final int mMarkerWidth;
    public final Paint mTmpPaint = new Paint();
    public final RectF mTmpRect = new RectF();
    public final int mTrackColor;
    public final int mUsageColor;
    public final int mWarningColor;

    public DataUsageGraph(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Resources resources = context.getResources();
        this.mTrackColor = Utils.getColorStateListDefaultColor(context, C1777R.color.data_usage_graph_track);
        this.mWarningColor = Utils.getColorStateListDefaultColor(context, C1777R.color.data_usage_graph_warning);
        this.mUsageColor = Utils.getColorAttrDefaultColor(context, 16843829);
        Utils.getColorAttrDefaultColor(context, 16844099);
        this.mMarkerWidth = resources.getDimensionPixelSize(C1777R.dimen.data_usage_graph_marker_width);
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF rectF = this.mTmpRect;
        Paint paint = this.mTmpPaint;
        int width = getWidth();
        int height = getHeight();
        float f = (float) width;
        float f2 = (float) 0;
        float f3 = (f2 / f2) * f;
        rectF.set(0.0f, 0.0f, f, (float) height);
        paint.setColor(this.mTrackColor);
        canvas.drawRect(rectF, paint);
        float f4 = (float) height;
        rectF.set(0.0f, 0.0f, f3, f4);
        paint.setColor(this.mUsageColor);
        canvas.drawRect(rectF, paint);
        float min = Math.min(Math.max(f3 - ((float) (this.mMarkerWidth / 2)), 0.0f), (float) (width - this.mMarkerWidth));
        rectF.set(min, 0.0f, ((float) this.mMarkerWidth) + min, f4);
        paint.setColor(this.mWarningColor);
        canvas.drawRect(rectF, paint);
    }
}
