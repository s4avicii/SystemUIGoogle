package com.android.settingslib.graph;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.PathParser;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: ThemedBatteryDrawable.kt */
public final class ThemedBatteryDrawable extends Drawable {
    public int batteryLevel;
    public final Path boltPath;
    public boolean charging;
    public int[] colorLevels;
    public final Context context;
    public boolean dualTone;
    public final Paint dualToneBackgroundFill;
    public final Paint errorPaint;
    public final Path errorPerimeterPath = new Path();
    public int fillColor;
    public final Paint fillColorStrokePaint;
    public final Paint fillColorStrokeProtection;
    public final Path fillMask = new Path();
    public final Paint fillPaint;
    public final RectF fillRect = new RectF();
    public int intrinsicHeight;
    public int intrinsicWidth;
    public final Function0<Unit> invalidateRunnable;
    public boolean invertFillIcon;
    public int levelColor;
    public final Path levelPath = new Path();
    public final RectF levelRect = new RectF();
    public final Path perimeterPath = new Path();
    public final Path plusPath;
    public boolean powerSaveEnabled;
    public final Matrix scaleMatrix = new Matrix();
    public final Path scaledBolt;
    public final Path scaledErrorPerimeter = new Path();
    public final Path scaledFill = new Path();
    public final Path scaledPerimeter = new Path();
    public final Path scaledPlus;
    public final Path unifiedPath;

    public final void draw(Canvas canvas) {
        float f;
        canvas.saveLayer((RectF) null, (Paint) null);
        this.unifiedPath.reset();
        this.levelPath.reset();
        this.levelRect.set(this.fillRect);
        int i = this.batteryLevel;
        float f2 = ((float) i) / 100.0f;
        if (i >= 95) {
            f = this.fillRect.top;
        } else {
            RectF rectF = this.fillRect;
            float f3 = (float) 1;
            f = MotionController$$ExternalSyntheticOutline0.m7m(f3, f2, rectF.height(), rectF.top);
        }
        this.levelRect.top = (float) Math.floor((double) f);
        this.levelPath.addRect(this.levelRect, Path.Direction.CCW);
        this.unifiedPath.addPath(this.scaledPerimeter);
        if (!this.dualTone) {
            this.unifiedPath.op(this.levelPath, Path.Op.UNION);
        }
        this.fillPaint.setColor(this.levelColor);
        if (this.charging) {
            this.unifiedPath.op(this.scaledBolt, Path.Op.DIFFERENCE);
            if (!this.invertFillIcon) {
                canvas.drawPath(this.scaledBolt, this.fillPaint);
            }
        }
        if (this.dualTone) {
            canvas.drawPath(this.unifiedPath, this.dualToneBackgroundFill);
            canvas.save();
            canvas.clipRect(0.0f, ((float) getBounds().bottom) - (((float) getBounds().height()) * f2), (float) getBounds().right, (float) getBounds().bottom);
            canvas.drawPath(this.unifiedPath, this.fillPaint);
            canvas.restore();
        } else {
            this.fillPaint.setColor(this.fillColor);
            canvas.drawPath(this.unifiedPath, this.fillPaint);
            this.fillPaint.setColor(this.levelColor);
            if (this.batteryLevel <= 15 && !this.charging) {
                canvas.save();
                canvas.clipPath(this.scaledFill);
                canvas.drawPath(this.levelPath, this.fillPaint);
                canvas.restore();
            }
        }
        if (this.charging) {
            canvas.clipOutPath(this.scaledBolt);
            if (this.invertFillIcon) {
                canvas.drawPath(this.scaledBolt, this.fillColorStrokePaint);
            } else {
                canvas.drawPath(this.scaledBolt, this.fillColorStrokeProtection);
            }
        } else if (this.powerSaveEnabled) {
            canvas.drawPath(this.scaledErrorPerimeter, this.errorPaint);
            canvas.drawPath(this.scaledPlus, this.errorPaint);
        }
        canvas.restore();
    }

    public final int getOpacity() {
        return -1;
    }

    public final void setAlpha(int i) {
    }

    public final int batteryColorForLevel(int i) {
        if (this.charging || this.powerSaveEnabled) {
            return this.fillColor;
        }
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.colorLevels;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            int i5 = iArr[i2 + 1];
            if (i > i4) {
                i2 += 2;
                i3 = i5;
            } else if (i2 == iArr.length - 2) {
                return this.fillColor;
            } else {
                return i5;
            }
        }
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.fillPaint.setColorFilter(colorFilter);
        this.fillColorStrokePaint.setColorFilter(colorFilter);
        this.dualToneBackgroundFill.setColorFilter(colorFilter);
    }

    public ThemedBatteryDrawable(Context context2, int i) {
        this.context = context2;
        new Rect();
        this.unifiedPath = new Path();
        this.boltPath = new Path();
        this.scaledBolt = new Path();
        this.plusPath = new Path();
        this.scaledPlus = new Path();
        this.fillColor = -65281;
        this.levelColor = -65281;
        this.invalidateRunnable = new ThemedBatteryDrawable$invalidateRunnable$1(this);
        context2.getResources().getInteger(17694770);
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setAlpha(255);
        paint.setDither(true);
        paint.setStrokeWidth(5.0f);
        paint.setStyle(Paint.Style.STROKE);
        paint.setBlendMode(BlendMode.SRC);
        paint.setStrokeMiter(5.0f);
        paint.setStrokeJoin(Paint.Join.ROUND);
        this.fillColorStrokePaint = paint;
        Paint paint2 = new Paint(1);
        paint2.setDither(true);
        paint2.setStrokeWidth(5.0f);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setBlendMode(BlendMode.CLEAR);
        paint2.setStrokeMiter(5.0f);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        this.fillColorStrokeProtection = paint2;
        Paint paint3 = new Paint(1);
        paint3.setColor(i);
        paint3.setAlpha(255);
        paint3.setDither(true);
        paint3.setStrokeWidth(0.0f);
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        this.fillPaint = paint3;
        Paint paint4 = new Paint(1);
        paint4.setColor(Utils.getColorStateListDefaultColor(context2, C1777R.color.batterymeter_plus_color));
        paint4.setAlpha(255);
        paint4.setDither(true);
        paint4.setStrokeWidth(0.0f);
        paint4.setStyle(Paint.Style.FILL_AND_STROKE);
        paint4.setBlendMode(BlendMode.SRC);
        this.errorPaint = paint4;
        Paint paint5 = new Paint(1);
        paint5.setColor(i);
        paint5.setAlpha(85);
        paint5.setDither(true);
        paint5.setStrokeWidth(0.0f);
        paint5.setStyle(Paint.Style.FILL_AND_STROKE);
        this.dualToneBackgroundFill = paint5;
        float f = context2.getResources().getDisplayMetrics().density;
        this.intrinsicHeight = (int) (20.0f * f);
        this.intrinsicWidth = (int) (f * 12.0f);
        Resources resources = context2.getResources();
        TypedArray obtainTypedArray = resources.obtainTypedArray(C1777R.array.batterymeter_color_levels);
        TypedArray obtainTypedArray2 = resources.obtainTypedArray(C1777R.array.batterymeter_color_values);
        int length = obtainTypedArray.length();
        this.colorLevels = new int[(length * 2)];
        int i2 = 0;
        while (i2 < length) {
            int i3 = i2 + 1;
            int i4 = i2 * 2;
            this.colorLevels[i4] = obtainTypedArray.getInt(i2, 0);
            if (obtainTypedArray2.getType(i2) == 2) {
                this.colorLevels[i4 + 1] = Utils.getColorAttrDefaultColor(this.context, obtainTypedArray2.getThemeAttributeId(i2, 0));
            } else {
                this.colorLevels[i4 + 1] = obtainTypedArray2.getColor(i2, 0);
            }
            i2 = i3;
        }
        obtainTypedArray.recycle();
        obtainTypedArray2.recycle();
        this.perimeterPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039877)));
        this.perimeterPath.computeBounds(new RectF(), true);
        this.errorPerimeterPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039875)));
        this.errorPerimeterPath.computeBounds(new RectF(), true);
        this.fillMask.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039876)));
        this.fillMask.computeBounds(this.fillRect, true);
        this.boltPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039874)));
        this.plusPath.set(PathParser.createPathFromPathData(this.context.getResources().getString(17039878)));
        this.dualTone = this.context.getResources().getBoolean(17891384);
    }

    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        Rect bounds = getBounds();
        if (bounds.isEmpty()) {
            this.scaleMatrix.setScale(1.0f, 1.0f);
        } else {
            this.scaleMatrix.setScale(((float) bounds.right) / 12.0f, ((float) bounds.bottom) / 20.0f);
        }
        this.perimeterPath.transform(this.scaleMatrix, this.scaledPerimeter);
        this.errorPerimeterPath.transform(this.scaleMatrix, this.scaledErrorPerimeter);
        this.fillMask.transform(this.scaleMatrix, this.scaledFill);
        this.scaledFill.computeBounds(this.fillRect, true);
        this.boltPath.transform(this.scaleMatrix, this.scaledBolt);
        this.plusPath.transform(this.scaleMatrix, this.scaledPlus);
        float max = Math.max((((float) bounds.right) / 12.0f) * 3.0f, 6.0f);
        this.fillColorStrokePaint.setStrokeWidth(max);
        this.fillColorStrokeProtection.setStrokeWidth(max);
    }

    public final int getIntrinsicHeight() {
        return this.intrinsicHeight;
    }

    public final int getIntrinsicWidth() {
        return this.intrinsicWidth;
    }
}
