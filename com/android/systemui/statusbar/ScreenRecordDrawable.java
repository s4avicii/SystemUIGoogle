package com.android.systemui.statusbar;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import com.android.p012wm.shell.C1777R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ScreenRecordDrawable extends DrawableWrapper {
    public Drawable mFillDrawable;
    public int mHeightPx;
    public int mHorizontalPadding;
    public Drawable mIconDrawable;
    public int mIconRadius;
    public int mLevel;
    public Paint mPaint;
    public float mTextSize;
    public int mWidthPx;

    public ScreenRecordDrawable() {
        super((Drawable) null);
    }

    public final boolean canApplyTheme() {
        if (this.mFillDrawable.canApplyTheme() || super.canApplyTheme()) {
            return true;
        }
        return false;
    }

    public final boolean getPadding(Rect rect) {
        int i = rect.left;
        int i2 = this.mHorizontalPadding;
        rect.left = i + i2;
        rect.right += i2;
        return true;
    }

    public final Drawable mutate() {
        this.mFillDrawable.mutate();
        return super.mutate();
    }

    public final boolean onLayoutDirectionChanged(int i) {
        this.mFillDrawable.setLayoutDirection(i);
        return super.onLayoutDirectionChanged(i);
    }

    public final boolean setVisible(boolean z, boolean z2) {
        this.mFillDrawable.setVisible(z, z2);
        return super.setVisible(z, z2);
    }

    public final void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        this.mFillDrawable.applyTheme(theme);
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
        this.mFillDrawable.draw(canvas);
        Rect bounds = this.mFillDrawable.getBounds();
        int i = this.mLevel;
        if (i > 0) {
            String valueOf = String.valueOf(i);
            Rect rect = new Rect();
            this.mPaint.getTextBounds(valueOf, 0, valueOf.length(), rect);
            canvas.drawText(valueOf, (float) bounds.centerX(), (float) ((rect.height() / 2) + bounds.centerY()), this.mPaint);
            return;
        }
        this.mIconDrawable.setBounds(new Rect(bounds.centerX() - this.mIconRadius, bounds.centerY() - this.mIconRadius, bounds.centerX() + this.mIconRadius, bounds.centerY() + this.mIconRadius));
        this.mIconDrawable.draw(canvas);
    }

    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        setDrawable(resources.getDrawable(C1777R.C1778drawable.ic_screen_record_background, theme).mutate());
        this.mFillDrawable = resources.getDrawable(C1777R.C1778drawable.ic_screen_record_background, theme).mutate();
        this.mIconDrawable = resources.getDrawable(C1777R.C1778drawable.ic_screenrecord, theme).mutate();
        this.mHorizontalPadding = resources.getDimensionPixelSize(C1777R.dimen.status_bar_horizontal_padding);
        this.mTextSize = (float) resources.getDimensionPixelSize(C1777R.dimen.screenrecord_status_text_size);
        this.mIconRadius = resources.getDimensionPixelSize(C1777R.dimen.screenrecord_status_icon_radius);
        this.mLevel = attributeSet.getAttributeIntValue((String) null, "level", 0);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setTextAlign(Paint.Align.CENTER);
        this.mPaint.setColor(-1);
        this.mPaint.setTextSize(this.mTextSize);
        this.mPaint.setFakeBoldText(true);
        this.mWidthPx = resources.getDimensionPixelSize(C1777R.dimen.screenrecord_status_icon_width);
        this.mHeightPx = resources.getDimensionPixelSize(C1777R.dimen.screenrecord_status_icon_height);
    }

    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mFillDrawable.setBounds(rect);
    }

    public final void setAlpha(int i) {
        super.setAlpha(i);
        this.mFillDrawable.setAlpha(i);
    }

    public final int getIntrinsicHeight() {
        return this.mHeightPx;
    }

    public final int getIntrinsicWidth() {
        return this.mWidthPx;
    }
}
