package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.HighlightView */
public final class HighlightView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Paint backgroundPaint;
    public final float highlightCornerRadius;
    public final Paint highlightPaint;
    public float highlightProgress = 0.0f;
    public final ArrayList<RectF> highlights = new ArrayList<>();
    public final ArrayList listeners = new ArrayList();
    public float pathChangeProgress = 0.0f;
    public final Rect tmpRect = new Rect();
    public final RectF tmpRectF = new RectF();

    /* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.HighlightView$TapListener */
    public interface TapListener {
        void onTap();
    }

    public HighlightView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        Paint paint = new Paint();
        this.backgroundPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getColor(C1777R.color.default_gleam_background_color));
        float dimensionPixelSize = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.selection_padding);
        this.highlightCornerRadius = dimensionPixelSize;
        Paint paint2 = new Paint();
        Paint paint3 = new Paint();
        this.highlightPaint = paint3;
        paint3.setColor(context.getColor(C1777R.color.default_gleam_highlight_color));
        paint2.setBlendMode(BlendMode.PLUS);
        paint2.setColor(paint3.getColor());
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeWidth(dimensionPixelSize * 2.0f);
        setWillNotDraw(false);
    }

    static {
        new FloatProperty<HighlightView>() {
            public final Object get(Object obj) {
                HighlightView highlightView = (HighlightView) obj;
                Objects.requireNonNull(highlightView);
                return Float.valueOf(highlightView.highlightProgress);
            }

            public final void setValue(Object obj, float f) {
                HighlightView highlightView = (HighlightView) obj;
                Objects.requireNonNull(highlightView);
                highlightView.highlightProgress = f;
                highlightView.invalidate();
            }
        };
        new FloatProperty<HighlightView>() {
            public final Object get(Object obj) {
                HighlightView highlightView = (HighlightView) obj;
                int i = HighlightView.$r8$clinit;
                Objects.requireNonNull(highlightView);
                return Float.valueOf(highlightView.pathChangeProgress);
            }

            public final void setValue(Object obj, float f) {
                HighlightView highlightView = (HighlightView) obj;
                int i = HighlightView.$r8$clinit;
                Objects.requireNonNull(highlightView);
                highlightView.pathChangeProgress = f;
                highlightView.invalidate();
            }
        };
        new PathInterpolator(0.71f, 0.0f, 0.13f, 1.0f);
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        return super.dispatchHoverEvent(motionEvent);
    }

    public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnTouchListener(new HighlightView$$ExternalSyntheticLambda0(this));
        setAlpha(0.0f);
        animate().alpha(0.2f);
    }

    public final void onDraw(Canvas canvas) {
        float f;
        super.onDraw(canvas);
        if (getHeight() != 0) {
            getDrawingRect(this.tmpRect);
            this.tmpRectF.set(this.tmpRect);
            canvas.drawRoundRect(this.tmpRectF, 0.0f, 0.0f, this.backgroundPaint);
            float f2 = this.highlightProgress * 1.1f;
            for (int i = 0; i < this.highlights.size(); i++) {
                float height = (f2 - (this.highlights.get(i).top / ((float) getHeight()))) * 10.0f;
                if (height < 0.0f) {
                    f = 0.0f;
                } else {
                    f = Math.min(1.0f, height);
                }
                this.highlightPaint.setAlpha(Math.round(f * 255.0f));
                float f3 = this.highlightCornerRadius;
                canvas.drawRoundRect(this.highlights.get(i), f3, f3, this.highlightPaint);
            }
        }
    }

    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
    }
}
