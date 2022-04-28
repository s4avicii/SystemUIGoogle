package com.google.android.material.shape;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import com.google.android.material.elevation.ElevationOverlayProvider;
import com.google.android.material.shadow.ShadowRenderer;
import com.google.android.material.shape.ShapeAppearancePathProvider;
import com.google.android.material.shape.ShapePath;
import java.util.BitSet;
import java.util.Objects;

public class MaterialShapeDrawable extends Drawable implements Shapeable {
    public static final Paint clearPaint;
    public final BitSet containsIncompatibleShadowOp;
    public final ShapePath.ShadowCompatOperation[] cornerShadowOperation;
    public MaterialShapeDrawableState drawableState;
    public final ShapePath.ShadowCompatOperation[] edgeShadowOperation;
    public final Paint fillPaint;
    public final RectF insetRectF;
    public final Matrix matrix;
    public final Path path;
    public final RectF pathBounds;
    public boolean pathDirty;
    public final Path pathInsetByStroke;
    public final ShapeAppearancePathProvider pathProvider;
    public final C20781 pathShadowListener;
    public final RectF rectF;
    public int resolvedTintColor;
    public final Region scratchRegion;
    public boolean shadowBitmapDrawingEnable;
    public final ShadowRenderer shadowRenderer;
    public final Paint strokePaint;
    public ShapeAppearanceModel strokeShapeAppearance;
    public PorterDuffColorFilter strokeTintFilter;
    public PorterDuffColorFilter tintFilter;
    public final Region transparentRegion;

    public static final class MaterialShapeDrawableState extends Drawable.ConstantState {
        public int alpha = 255;
        public float elevation = 0.0f;
        public ElevationOverlayProvider elevationOverlayProvider;
        public ColorStateList fillColor = null;
        public float interpolation = 1.0f;
        public Rect padding = null;
        public Paint.Style paintStyle = Paint.Style.FILL_AND_STROKE;
        public float parentAbsoluteElevation = 0.0f;
        public float scale = 1.0f;
        public int shadowCompatMode = 0;
        public int shadowCompatOffset = 0;
        public int shadowCompatRadius = 0;
        public int shadowCompatRotation = 0;
        public ShapeAppearanceModel shapeAppearanceModel;
        public ColorStateList strokeColor = null;
        public ColorStateList strokeTintList = null;
        public float strokeWidth;
        public ColorStateList tintList = null;
        public PorterDuff.Mode tintMode = PorterDuff.Mode.SRC_IN;
        public float translationZ = 0.0f;
        public boolean useTintColorForShadow = false;

        public MaterialShapeDrawableState(ShapeAppearanceModel shapeAppearanceModel2) {
            this.shapeAppearanceModel = shapeAppearanceModel2;
            this.elevationOverlayProvider = null;
        }

        public final int getChangingConfigurations() {
            return 0;
        }

        public final Drawable newDrawable() {
            MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(this);
            materialShapeDrawable.pathDirty = true;
            return materialShapeDrawable;
        }

        public MaterialShapeDrawableState(MaterialShapeDrawableState materialShapeDrawableState) {
            this.shapeAppearanceModel = materialShapeDrawableState.shapeAppearanceModel;
            this.elevationOverlayProvider = materialShapeDrawableState.elevationOverlayProvider;
            this.strokeWidth = materialShapeDrawableState.strokeWidth;
            this.fillColor = materialShapeDrawableState.fillColor;
            this.strokeColor = materialShapeDrawableState.strokeColor;
            this.tintMode = materialShapeDrawableState.tintMode;
            this.tintList = materialShapeDrawableState.tintList;
            this.alpha = materialShapeDrawableState.alpha;
            this.scale = materialShapeDrawableState.scale;
            this.shadowCompatOffset = materialShapeDrawableState.shadowCompatOffset;
            this.shadowCompatMode = materialShapeDrawableState.shadowCompatMode;
            this.useTintColorForShadow = materialShapeDrawableState.useTintColorForShadow;
            this.interpolation = materialShapeDrawableState.interpolation;
            this.parentAbsoluteElevation = materialShapeDrawableState.parentAbsoluteElevation;
            this.elevation = materialShapeDrawableState.elevation;
            this.translationZ = materialShapeDrawableState.translationZ;
            this.shadowCompatRadius = materialShapeDrawableState.shadowCompatRadius;
            this.shadowCompatRotation = materialShapeDrawableState.shadowCompatRotation;
            this.strokeTintList = materialShapeDrawableState.strokeTintList;
            this.paintStyle = materialShapeDrawableState.paintStyle;
            if (materialShapeDrawableState.padding != null) {
                this.padding = new Rect(materialShapeDrawableState.padding);
            }
        }
    }

    public MaterialShapeDrawable() {
        this(new ShapeAppearanceModel());
    }

    public int getOpacity() {
        return -3;
    }

    public final void invalidateSelf() {
        this.pathDirty = true;
        super.invalidateSelf();
    }

    public void onBoundsChange(Rect rect) {
        this.pathDirty = true;
        super.onBoundsChange(rect);
    }

    static {
        Class<MaterialShapeDrawable> cls = MaterialShapeDrawable.class;
        Paint paint = new Paint(1);
        clearPaint = paint;
        paint.setColor(-1);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    }

    public MaterialShapeDrawable(Context context, AttributeSet attributeSet, int i, int i2) {
        this(ShapeAppearanceModel.builder(context, attributeSet, i, i2).build());
    }

    public final void calculatePath(RectF rectF2, Path path2) {
        ShapeAppearancePathProvider shapeAppearancePathProvider = this.pathProvider;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawableState.shapeAppearanceModel;
        float f = materialShapeDrawableState.interpolation;
        shapeAppearancePathProvider.calculatePath(shapeAppearanceModel, f, rectF2, this.pathShadowListener, path2);
        if (this.drawableState.scale != 1.0f) {
            this.matrix.reset();
            Matrix matrix2 = this.matrix;
            float f2 = this.drawableState.scale;
            matrix2.setScale(f2, f2, rectF2.width() / 2.0f, rectF2.height() / 2.0f);
            path2.transform(this.matrix);
        }
        path2.computeBounds(this.pathBounds, true);
    }

    public final PorterDuffColorFilter calculateTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, Paint paint, boolean z) {
        if (colorStateList == null || mode == null) {
            if (z) {
                int color = paint.getColor();
                int compositeElevationOverlayIfNeeded = compositeElevationOverlayIfNeeded(color);
                this.resolvedTintColor = compositeElevationOverlayIfNeeded;
                if (compositeElevationOverlayIfNeeded != color) {
                    return new PorterDuffColorFilter(compositeElevationOverlayIfNeeded, PorterDuff.Mode.SRC_IN);
                }
            }
            return null;
        }
        int colorForState = colorStateList.getColorForState(getState(), 0);
        if (z) {
            colorForState = compositeElevationOverlayIfNeeded(colorForState);
        }
        this.resolvedTintColor = colorForState;
        return new PorterDuffColorFilter(colorForState, mode);
    }

    public final int compositeElevationOverlayIfNeeded(int i) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        float f = materialShapeDrawableState.elevation + materialShapeDrawableState.translationZ + materialShapeDrawableState.parentAbsoluteElevation;
        ElevationOverlayProvider elevationOverlayProvider = materialShapeDrawableState.elevationOverlayProvider;
        if (elevationOverlayProvider != null) {
            return elevationOverlayProvider.compositeOverlayIfNeeded(i, f);
        }
        return i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:49:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01e4  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01e6  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0214  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void draw(android.graphics.Canvas r20) {
        /*
            r19 = this;
            r6 = r19
            r7 = r20
            android.graphics.Paint r0 = r6.fillPaint
            android.graphics.PorterDuffColorFilter r1 = r6.tintFilter
            r0.setColorFilter(r1)
            android.graphics.Paint r0 = r6.fillPaint
            int r8 = r0.getAlpha()
            android.graphics.Paint r0 = r6.fillPaint
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r1 = r6.drawableState
            int r1 = r1.alpha
            int r2 = r1 >>> 7
            int r1 = r1 + r2
            int r1 = r1 * r8
            int r1 = r1 >>> 8
            r0.setAlpha(r1)
            android.graphics.Paint r0 = r6.strokePaint
            android.graphics.PorterDuffColorFilter r1 = r6.strokeTintFilter
            r0.setColorFilter(r1)
            android.graphics.Paint r0 = r6.strokePaint
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r1 = r6.drawableState
            float r1 = r1.strokeWidth
            r0.setStrokeWidth(r1)
            android.graphics.Paint r0 = r6.strokePaint
            int r9 = r0.getAlpha()
            android.graphics.Paint r0 = r6.strokePaint
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r1 = r6.drawableState
            int r1 = r1.alpha
            int r2 = r1 >>> 7
            int r1 = r1 + r2
            int r1 = r1 * r9
            int r1 = r1 >>> 8
            r0.setAlpha(r1)
            boolean r0 = r6.pathDirty
            r10 = 0
            r11 = 1
            r12 = 0
            if (r0 == 0) goto L_0x0115
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r6.drawableState
            android.graphics.Paint$Style r0 = r0.paintStyle
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.FILL_AND_STROKE
            if (r0 == r1) goto L_0x0058
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            if (r0 != r1) goto L_0x0064
        L_0x0058:
            android.graphics.Paint r0 = r6.strokePaint
            float r0 = r0.getStrokeWidth()
            int r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r0 <= 0) goto L_0x0064
            r0 = r11
            goto L_0x0065
        L_0x0064:
            r0 = r12
        L_0x0065:
            r1 = 1073741824(0x40000000, float:2.0)
            if (r0 == 0) goto L_0x0071
            android.graphics.Paint r0 = r6.strokePaint
            float r0 = r0.getStrokeWidth()
            float r0 = r0 / r1
            goto L_0x0072
        L_0x0071:
            r0 = r10
        L_0x0072:
            float r0 = -r0
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r2 = r6.drawableState
            com.google.android.material.shape.ShapeAppearanceModel r2 = r2.shapeAppearanceModel
            java.util.Objects.requireNonNull(r2)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r3 = new com.google.android.material.shape.ShapeAppearanceModel$Builder
            r3.<init>(r2)
            com.google.android.material.shape.CornerSize r4 = r2.topLeftCornerSize
            boolean r5 = r4 instanceof com.google.android.material.shape.RelativeCornerSize
            if (r5 == 0) goto L_0x0086
            goto L_0x008c
        L_0x0086:
            com.google.android.material.shape.AdjustedCornerSize r5 = new com.google.android.material.shape.AdjustedCornerSize
            r5.<init>(r0, r4)
            r4 = r5
        L_0x008c:
            r3.topLeftCornerSize = r4
            com.google.android.material.shape.CornerSize r4 = r2.topRightCornerSize
            boolean r5 = r4 instanceof com.google.android.material.shape.RelativeCornerSize
            if (r5 == 0) goto L_0x0095
            goto L_0x009b
        L_0x0095:
            com.google.android.material.shape.AdjustedCornerSize r5 = new com.google.android.material.shape.AdjustedCornerSize
            r5.<init>(r0, r4)
            r4 = r5
        L_0x009b:
            r3.topRightCornerSize = r4
            com.google.android.material.shape.CornerSize r4 = r2.bottomLeftCornerSize
            boolean r5 = r4 instanceof com.google.android.material.shape.RelativeCornerSize
            if (r5 == 0) goto L_0x00a4
            goto L_0x00aa
        L_0x00a4:
            com.google.android.material.shape.AdjustedCornerSize r5 = new com.google.android.material.shape.AdjustedCornerSize
            r5.<init>(r0, r4)
            r4 = r5
        L_0x00aa:
            r3.bottomLeftCornerSize = r4
            com.google.android.material.shape.CornerSize r2 = r2.bottomRightCornerSize
            boolean r4 = r2 instanceof com.google.android.material.shape.RelativeCornerSize
            if (r4 == 0) goto L_0x00b3
            goto L_0x00b9
        L_0x00b3:
            com.google.android.material.shape.AdjustedCornerSize r4 = new com.google.android.material.shape.AdjustedCornerSize
            r4.<init>(r0, r2)
            r2 = r4
        L_0x00b9:
            r3.bottomRightCornerSize = r2
            com.google.android.material.shape.ShapeAppearanceModel r14 = new com.google.android.material.shape.ShapeAppearanceModel
            r14.<init>(r3)
            r6.strokeShapeAppearance = r14
            com.google.android.material.shape.ShapeAppearancePathProvider r13 = r6.pathProvider
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r6.drawableState
            float r15 = r0.interpolation
            android.graphics.RectF r0 = r6.insetRectF
            android.graphics.RectF r2 = r19.getBoundsAsRectF()
            r0.set(r2)
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r6.drawableState
            android.graphics.Paint$Style r0 = r0.paintStyle
            android.graphics.Paint$Style r2 = android.graphics.Paint.Style.FILL_AND_STROKE
            if (r0 == r2) goto L_0x00dd
            android.graphics.Paint$Style r2 = android.graphics.Paint.Style.STROKE
            if (r0 != r2) goto L_0x00e9
        L_0x00dd:
            android.graphics.Paint r0 = r6.strokePaint
            float r0 = r0.getStrokeWidth()
            int r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r0 <= 0) goto L_0x00e9
            r0 = r11
            goto L_0x00ea
        L_0x00e9:
            r0 = r12
        L_0x00ea:
            if (r0 == 0) goto L_0x00f4
            android.graphics.Paint r0 = r6.strokePaint
            float r0 = r0.getStrokeWidth()
            float r0 = r0 / r1
            goto L_0x00f5
        L_0x00f4:
            r0 = r10
        L_0x00f5:
            android.graphics.RectF r1 = r6.insetRectF
            r1.inset(r0, r0)
            android.graphics.RectF r0 = r6.insetRectF
            android.graphics.Path r1 = r6.pathInsetByStroke
            java.util.Objects.requireNonNull(r13)
            r17 = 0
            r16 = r0
            r18 = r1
            r13.calculatePath(r14, r15, r16, r17, r18)
            android.graphics.RectF r0 = r19.getBoundsAsRectF()
            android.graphics.Path r1 = r6.path
            r6.calculatePath(r0, r1)
            r6.pathDirty = r12
        L_0x0115:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r6.drawableState
            int r1 = r0.shadowCompatMode
            r2 = 2
            if (r1 == r11) goto L_0x0130
            int r0 = r0.shadowCompatRadius
            if (r0 <= 0) goto L_0x0130
            if (r1 == r2) goto L_0x012e
            boolean r0 = r19.isRoundRect()
            if (r0 != 0) goto L_0x0130
            android.graphics.Path r0 = r6.path
            r0.isConvex()
            goto L_0x0130
        L_0x012e:
            r0 = r11
            goto L_0x0131
        L_0x0130:
            r0 = r12
        L_0x0131:
            if (r0 != 0) goto L_0x0135
            goto L_0x01d7
        L_0x0135:
            r20.save()
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r6.drawableState
            int r1 = r0.shadowCompatOffset
            double r3 = (double) r1
            int r0 = r0.shadowCompatRotation
            double r0 = (double) r0
            double r0 = java.lang.Math.toRadians(r0)
            double r0 = java.lang.Math.sin(r0)
            double r0 = r0 * r3
            int r0 = (int) r0
            int r1 = r19.getShadowOffsetY()
            float r0 = (float) r0
            float r1 = (float) r1
            r7.translate(r0, r1)
            boolean r0 = r6.shadowBitmapDrawingEnable
            if (r0 != 0) goto L_0x015e
            r19.drawCompatShadow(r20)
            r20.restore()
            goto L_0x01d7
        L_0x015e:
            android.graphics.RectF r0 = r6.pathBounds
            float r0 = r0.width()
            android.graphics.Rect r1 = r19.getBounds()
            int r1 = r1.width()
            float r1 = (float) r1
            float r0 = r0 - r1
            int r0 = (int) r0
            android.graphics.RectF r1 = r6.pathBounds
            float r1 = r1.height()
            android.graphics.Rect r3 = r19.getBounds()
            int r3 = r3.height()
            float r3 = (float) r3
            float r1 = r1 - r3
            int r1 = (int) r1
            if (r0 < 0) goto L_0x0222
            if (r1 < 0) goto L_0x0222
            android.graphics.RectF r3 = r6.pathBounds
            float r3 = r3.width()
            int r3 = (int) r3
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r4 = r6.drawableState
            int r4 = r4.shadowCompatRadius
            int r4 = r4 * r2
            int r4 = r4 + r3
            int r4 = r4 + r0
            android.graphics.RectF r3 = r6.pathBounds
            float r3 = r3.height()
            int r3 = (int) r3
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r5 = r6.drawableState
            int r5 = r5.shadowCompatRadius
            int r5 = r5 * r2
            int r5 = r5 + r3
            int r5 = r5 + r1
            android.graphics.Bitmap$Config r2 = android.graphics.Bitmap.Config.ARGB_8888
            android.graphics.Bitmap r2 = android.graphics.Bitmap.createBitmap(r4, r5, r2)
            android.graphics.Canvas r3 = new android.graphics.Canvas
            r3.<init>(r2)
            android.graphics.Rect r4 = r19.getBounds()
            int r4 = r4.left
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r5 = r6.drawableState
            int r5 = r5.shadowCompatRadius
            int r4 = r4 - r5
            int r4 = r4 - r0
            float r0 = (float) r4
            android.graphics.Rect r4 = r19.getBounds()
            int r4 = r4.top
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r5 = r6.drawableState
            int r5 = r5.shadowCompatRadius
            int r4 = r4 - r5
            int r4 = r4 - r1
            float r1 = (float) r4
            float r4 = -r0
            float r5 = -r1
            r3.translate(r4, r5)
            r6.drawCompatShadow(r3)
            r3 = 0
            r7.drawBitmap(r2, r0, r1, r3)
            r2.recycle()
            r20.restore()
        L_0x01d7:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r6.drawableState
            android.graphics.Paint$Style r1 = r0.paintStyle
            android.graphics.Paint$Style r2 = android.graphics.Paint.Style.FILL_AND_STROKE
            if (r1 == r2) goto L_0x01e6
            android.graphics.Paint$Style r2 = android.graphics.Paint.Style.FILL
            if (r1 != r2) goto L_0x01e4
            goto L_0x01e6
        L_0x01e4:
            r1 = r12
            goto L_0x01e7
        L_0x01e6:
            r1 = r11
        L_0x01e7:
            if (r1 == 0) goto L_0x01fa
            android.graphics.Paint r2 = r6.fillPaint
            android.graphics.Path r3 = r6.path
            com.google.android.material.shape.ShapeAppearanceModel r4 = r0.shapeAppearanceModel
            android.graphics.RectF r5 = r19.getBoundsAsRectF()
            r0 = r19
            r1 = r20
            r0.drawShape(r1, r2, r3, r4, r5)
        L_0x01fa:
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r0 = r6.drawableState
            android.graphics.Paint$Style r0 = r0.paintStyle
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.FILL_AND_STROKE
            if (r0 == r1) goto L_0x0206
            android.graphics.Paint$Style r1 = android.graphics.Paint.Style.STROKE
            if (r0 != r1) goto L_0x0211
        L_0x0206:
            android.graphics.Paint r0 = r6.strokePaint
            float r0 = r0.getStrokeWidth()
            int r0 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1))
            if (r0 <= 0) goto L_0x0211
            goto L_0x0212
        L_0x0211:
            r11 = r12
        L_0x0212:
            if (r11 == 0) goto L_0x0217
            r19.drawStrokeShape(r20)
        L_0x0217:
            android.graphics.Paint r0 = r6.fillPaint
            r0.setAlpha(r8)
            android.graphics.Paint r0 = r6.strokePaint
            r0.setAlpha(r9)
            return
        L_0x0222:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Invalid shadow bounds. Check that the treatments result in a valid path."
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.shape.MaterialShapeDrawable.draw(android.graphics.Canvas):void");
    }

    public final void drawCompatShadow(Canvas canvas) {
        if (this.containsIncompatibleShadowOp.cardinality() > 0) {
            Log.w("MaterialShapeDrawable", "Compatibility shadow requested but can't be drawn for all operations in this shape.");
        }
        if (this.drawableState.shadowCompatOffset != 0) {
            Path path2 = this.path;
            ShadowRenderer shadowRenderer2 = this.shadowRenderer;
            Objects.requireNonNull(shadowRenderer2);
            canvas.drawPath(path2, shadowRenderer2.shadowPaint);
        }
        for (int i = 0; i < 4; i++) {
            ShapePath.ShadowCompatOperation shadowCompatOperation = this.cornerShadowOperation[i];
            ShadowRenderer shadowRenderer3 = this.shadowRenderer;
            int i2 = this.drawableState.shadowCompatRadius;
            Objects.requireNonNull(shadowCompatOperation);
            Matrix matrix2 = ShapePath.ShadowCompatOperation.IDENTITY_MATRIX;
            shadowCompatOperation.draw(matrix2, shadowRenderer3, i2, canvas);
            ShapePath.ShadowCompatOperation shadowCompatOperation2 = this.edgeShadowOperation[i];
            ShadowRenderer shadowRenderer4 = this.shadowRenderer;
            int i3 = this.drawableState.shadowCompatRadius;
            Objects.requireNonNull(shadowCompatOperation2);
            shadowCompatOperation2.draw(matrix2, shadowRenderer4, i3, canvas);
        }
        if (this.shadowBitmapDrawingEnable) {
            MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
            int sin = (int) (Math.sin(Math.toRadians((double) materialShapeDrawableState.shadowCompatRotation)) * ((double) materialShapeDrawableState.shadowCompatOffset));
            int shadowOffsetY = getShadowOffsetY();
            canvas.translate((float) (-sin), (float) (-shadowOffsetY));
            canvas.drawPath(this.path, clearPaint);
            canvas.translate((float) sin, (float) shadowOffsetY);
        }
    }

    public void drawStrokeShape(Canvas canvas) {
        boolean z;
        Paint paint = this.strokePaint;
        Path path2 = this.pathInsetByStroke;
        ShapeAppearanceModel shapeAppearanceModel = this.strokeShapeAppearance;
        this.insetRectF.set(getBoundsAsRectF());
        Paint.Style style = this.drawableState.paintStyle;
        float f = 0.0f;
        if ((style == Paint.Style.FILL_AND_STROKE || style == Paint.Style.STROKE) && this.strokePaint.getStrokeWidth() > 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            f = this.strokePaint.getStrokeWidth() / 2.0f;
        }
        this.insetRectF.inset(f, f);
        drawShape(canvas, paint, path2, shapeAppearanceModel, this.insetRectF);
    }

    public final RectF getBoundsAsRectF() {
        this.rectF.set(getBounds());
        return this.rectF;
    }

    @TargetApi(21)
    public void getOutline(Outline outline) {
        if (this.drawableState.shadowCompatMode != 2) {
            if (isRoundRect()) {
                outline.setRoundRect(getBounds(), getTopLeftCornerResolvedSize() * this.drawableState.interpolation);
                return;
            }
            calculatePath(getBoundsAsRectF(), this.path);
            boolean isConvex = this.path.isConvex();
            try {
                outline.setConvexPath(this.path);
            } catch (IllegalArgumentException unused) {
            }
        }
    }

    public final boolean getPadding(Rect rect) {
        Rect rect2 = this.drawableState.padding;
        if (rect2 == null) {
            return super.getPadding(rect);
        }
        rect.set(rect2);
        return true;
    }

    public final int getShadowOffsetY() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        return (int) (Math.cos(Math.toRadians((double) materialShapeDrawableState.shadowCompatRotation)) * ((double) materialShapeDrawableState.shadowCompatOffset));
    }

    public final float getTopLeftCornerResolvedSize() {
        ShapeAppearanceModel shapeAppearanceModel = this.drawableState.shapeAppearanceModel;
        Objects.requireNonNull(shapeAppearanceModel);
        return shapeAppearanceModel.topLeftCornerSize.getCornerSize(getBoundsAsRectF());
    }

    public final void initializeElevationOverlay(Context context) {
        this.drawableState.elevationOverlayProvider = new ElevationOverlayProvider(context);
        updateZ();
    }

    public final boolean isRoundRect() {
        return this.drawableState.shapeAppearanceModel.isRoundRect(getBoundsAsRectF());
    }

    public final Drawable mutate() {
        this.drawableState = new MaterialShapeDrawableState(this.drawableState);
        return this;
    }

    public void setAlpha(int i) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.alpha != i) {
            materialShapeDrawableState.alpha = i;
            super.invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter colorFilter) {
        Objects.requireNonNull(this.drawableState);
        super.invalidateSelf();
    }

    public final void setElevation(float f) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.elevation != f) {
            materialShapeDrawableState.elevation = f;
            updateZ();
        }
    }

    public final void setFillColor(ColorStateList colorStateList) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.fillColor != colorStateList) {
            materialShapeDrawableState.fillColor = colorStateList;
            onStateChange(getState());
        }
    }

    public final void setInterpolation(float f) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.interpolation != f) {
            materialShapeDrawableState.interpolation = f;
            this.pathDirty = true;
            invalidateSelf();
        }
    }

    public final void setPaintStyle(Paint.Style style) {
        this.drawableState.paintStyle = style;
        super.invalidateSelf();
    }

    public final void setShadowColor() {
        this.shadowRenderer.setShadowColor(-12303292);
        this.drawableState.useTintColorForShadow = false;
        super.invalidateSelf();
    }

    public final void setShadowCompatibilityMode() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.shadowCompatMode != 2) {
            materialShapeDrawableState.shadowCompatMode = 2;
            super.invalidateSelf();
        }
    }

    public final void setShapeAppearanceModel(ShapeAppearanceModel shapeAppearanceModel) {
        this.drawableState.shapeAppearanceModel = shapeAppearanceModel;
        invalidateSelf();
    }

    public final void setStrokeColor(ColorStateList colorStateList) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.strokeColor != colorStateList) {
            materialShapeDrawableState.strokeColor = colorStateList;
            onStateChange(getState());
        }
    }

    public void setTintList(ColorStateList colorStateList) {
        this.drawableState.tintList = colorStateList;
        updateTintFilter();
        super.invalidateSelf();
    }

    public void setTintMode(PorterDuff.Mode mode) {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        if (materialShapeDrawableState.tintMode != mode) {
            materialShapeDrawableState.tintMode = mode;
            updateTintFilter();
            super.invalidateSelf();
        }
    }

    public final boolean updateColorsForState(int[] iArr) {
        boolean z;
        int color;
        int colorForState;
        int color2;
        int colorForState2;
        if (this.drawableState.fillColor == null || (color2 = this.fillPaint.getColor()) == (colorForState2 = this.drawableState.fillColor.getColorForState(iArr, color2))) {
            z = false;
        } else {
            this.fillPaint.setColor(colorForState2);
            z = true;
        }
        if (this.drawableState.strokeColor == null || (color = this.strokePaint.getColor()) == (colorForState = this.drawableState.strokeColor.getColorForState(iArr, color))) {
            return z;
        }
        this.strokePaint.setColor(colorForState);
        return true;
    }

    public final boolean updateTintFilter() {
        PorterDuffColorFilter porterDuffColorFilter = this.tintFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = this.strokeTintFilter;
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        this.tintFilter = calculateTintFilter(materialShapeDrawableState.tintList, materialShapeDrawableState.tintMode, this.fillPaint, true);
        MaterialShapeDrawableState materialShapeDrawableState2 = this.drawableState;
        this.strokeTintFilter = calculateTintFilter(materialShapeDrawableState2.strokeTintList, materialShapeDrawableState2.tintMode, this.strokePaint, false);
        MaterialShapeDrawableState materialShapeDrawableState3 = this.drawableState;
        if (materialShapeDrawableState3.useTintColorForShadow) {
            this.shadowRenderer.setShadowColor(materialShapeDrawableState3.tintList.getColorForState(getState(), 0));
        }
        if (!Objects.equals(porterDuffColorFilter, this.tintFilter) || !Objects.equals(porterDuffColorFilter2, this.strokeTintFilter)) {
            return true;
        }
        return false;
    }

    public final void updateZ() {
        MaterialShapeDrawableState materialShapeDrawableState = this.drawableState;
        float f = materialShapeDrawableState.elevation + materialShapeDrawableState.translationZ;
        materialShapeDrawableState.shadowCompatRadius = (int) Math.ceil((double) (0.75f * f));
        this.drawableState.shadowCompatOffset = (int) Math.ceil((double) (f * 0.25f));
        updateTintFilter();
        super.invalidateSelf();
    }

    public MaterialShapeDrawable(ShapeAppearanceModel shapeAppearanceModel) {
        this(new MaterialShapeDrawableState(shapeAppearanceModel));
    }

    public final void drawShape(Canvas canvas, Paint paint, Path path2, ShapeAppearanceModel shapeAppearanceModel, RectF rectF2) {
        if (shapeAppearanceModel.isRoundRect(rectF2)) {
            float cornerSize = shapeAppearanceModel.topRightCornerSize.getCornerSize(rectF2) * this.drawableState.interpolation;
            canvas.drawRoundRect(rectF2, cornerSize, cornerSize, paint);
            return;
        }
        canvas.drawPath(path2, paint);
    }

    public final Region getTransparentRegion() {
        this.transparentRegion.set(getBounds());
        calculatePath(getBoundsAsRectF(), this.path);
        this.scratchRegion.setPath(this.path, this.transparentRegion);
        this.transparentRegion.op(this.scratchRegion, Region.Op.DIFFERENCE);
        return this.transparentRegion;
    }

    public boolean isStateful() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        ColorStateList colorStateList4;
        if (super.isStateful() || (((colorStateList = this.drawableState.tintList) != null && colorStateList.isStateful()) || (((colorStateList2 = this.drawableState.strokeTintList) != null && colorStateList2.isStateful()) || (((colorStateList3 = this.drawableState.strokeColor) != null && colorStateList3.isStateful()) || ((colorStateList4 = this.drawableState.fillColor) != null && colorStateList4.isStateful()))))) {
            return true;
        }
        return false;
    }

    public boolean onStateChange(int[] iArr) {
        boolean z;
        boolean updateColorsForState = updateColorsForState(iArr);
        boolean updateTintFilter = updateTintFilter();
        if (updateColorsForState || updateTintFilter) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            invalidateSelf();
        }
        return z;
    }

    public final void setTint(int i) {
        setTintList(ColorStateList.valueOf(i));
    }

    public MaterialShapeDrawable(MaterialShapeDrawableState materialShapeDrawableState) {
        ShapeAppearancePathProvider shapeAppearancePathProvider;
        this.cornerShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.edgeShadowOperation = new ShapePath.ShadowCompatOperation[4];
        this.containsIncompatibleShadowOp = new BitSet(8);
        this.matrix = new Matrix();
        this.path = new Path();
        this.pathInsetByStroke = new Path();
        this.rectF = new RectF();
        this.insetRectF = new RectF();
        this.transparentRegion = new Region();
        this.scratchRegion = new Region();
        Paint paint = new Paint(1);
        this.fillPaint = paint;
        Paint paint2 = new Paint(1);
        this.strokePaint = paint2;
        this.shadowRenderer = new ShadowRenderer();
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            shapeAppearancePathProvider = ShapeAppearancePathProvider.Lazy.INSTANCE;
        } else {
            shapeAppearancePathProvider = new ShapeAppearancePathProvider();
        }
        this.pathProvider = shapeAppearancePathProvider;
        this.pathBounds = new RectF();
        this.shadowBitmapDrawingEnable = true;
        this.drawableState = materialShapeDrawableState;
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        updateTintFilter();
        updateColorsForState(getState());
        this.pathShadowListener = new Object() {
        };
    }

    public final Drawable.ConstantState getConstantState() {
        return this.drawableState;
    }
}
