package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.assist.p003ui.EdgeLight;
import com.android.systemui.p006qs.QSAnimator$$ExternalSyntheticLambda0;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.google.android.systemui.assist.uihints.BlurProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public final class GlowView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageView mBlueGlow;
    public BlurProvider mBlurProvider;
    public int mBlurRadius;
    public EdgeLight[] mEdgeLights;
    public RectF mGlowImageCropRegion;
    public final Matrix mGlowImageMatrix;
    public ArrayList<ImageView> mGlowImageViews;
    public float mGlowWidthRatio;
    public ImageView mGreenGlow;
    public int mMinimumHeightPx;
    public final Paint mPaint;
    public ImageView mRedGlow;
    public int mTranslationY;
    public ImageView mYellowGlow;

    public GlowView(Context context) {
        this(context, (AttributeSet) null);
    }

    public GlowView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void distributeEvenly() {
        int i;
        Context context = this.mContext;
        int identifier = context.getResources().getIdentifier("rounded_corner_radius_bottom", "dimen", ThemeOverlayApplier.ANDROID_PACKAGE);
        int i2 = 0;
        if (identifier > 0) {
            i = context.getResources().getDimensionPixelSize(identifier);
        } else {
            i = 0;
        }
        if (i == 0) {
            int identifier2 = context.getResources().getIdentifier("rounded_corner_radius", "dimen", ThemeOverlayApplier.ANDROID_PACKAGE);
            if (identifier2 > 0) {
                i2 = context.getResources().getDimensionPixelSize(identifier2);
            }
            i = i2;
        }
        float f = (float) i;
        float width = (float) getWidth();
        float f2 = this.mGlowWidthRatio / 2.0f;
        float f3 = 0.96f * f2;
        float f4 = (f / width) - f2;
        float f5 = f4 + f3;
        float f6 = f5 + f3;
        this.mBlueGlow.setX(f4 * width);
        this.mRedGlow.setX(f5 * width);
        this.mYellowGlow.setX(f6 * width);
        this.mGreenGlow.setX(width * (f3 + f6));
    }

    public final int getGlowHeight() {
        float ceil = (float) ((int) Math.ceil((double) (this.mGlowWidthRatio * ((float) getWidth()))));
        float f = 0.0f;
        if (this.mGlowImageCropRegion.width() != 0.0f) {
            f = this.mGlowImageCropRegion.height() / this.mGlowImageCropRegion.width();
        }
        return (int) Math.ceil((double) (ceil * f));
    }

    public final void setBlurredImageOnViews(int i) {
        BlurProvider.BlurResult blurResult;
        this.mBlurRadius = i;
        BlurProvider blurProvider = this.mBlurProvider;
        Objects.requireNonNull(blurProvider);
        if (blurProvider.mBuffer == null) {
            BlurProvider.SourceDownsampler sourceDownsampler = blurProvider.mImageSource;
            Objects.requireNonNull(sourceDownsampler);
            float f = 1.0f / ((float) 1);
            blurProvider.mBuffer = Bitmap.createBitmap((int) Math.ceil((double) ((((float) sourceDownsampler.mDrawable.getIntrinsicWidth()) * f) + 50.0f)), (int) Math.ceil((double) ((f * ((float) sourceDownsampler.mDrawable.getIntrinsicHeight())) + 50.0f)), Bitmap.Config.ARGB_8888);
        }
        Bitmap bitmap = blurProvider.mBuffer;
        float constrain = MathUtils.constrain((float) i, 0.0f, 1000.0f);
        if (constrain <= 1.0f) {
            BlurProvider.SourceDownsampler sourceDownsampler2 = blurProvider.mImageSource;
            Objects.requireNonNull(sourceDownsampler2);
            Drawable drawable = sourceDownsampler2.mDrawable;
            blurResult = new BlurProvider.BlurResult(drawable, new RectF(0.0f, 0.0f, (float) drawable.getIntrinsicWidth(), (float) drawable.getIntrinsicHeight()));
        } else {
            int i2 = 0;
            int i3 = 0;
            for (int i4 = 25; ((float) i4) < constrain; i4 *= 2) {
                i3++;
            }
            BlurProvider.SourceDownsampler sourceDownsampler3 = blurProvider.mImageSource;
            Objects.requireNonNull(sourceDownsampler3);
            Canvas canvas = new Canvas(bitmap);
            float f2 = 1.0f / ((float) (1 << i3));
            canvas.clipRect(0, 0, ((int) Math.ceil((double) ((((float) sourceDownsampler3.mDrawable.getIntrinsicWidth()) * f2) + 50.0f))) + 25, ((int) Math.ceil((double) ((((float) sourceDownsampler3.mDrawable.getIntrinsicHeight()) * f2) + 50.0f))) + 25);
            canvas.drawColor(0, BlendMode.CLEAR);
            float f3 = (float) 25;
            canvas.translate(f3, f3);
            canvas.scale(f2, f2);
            Drawable drawable2 = sourceDownsampler3.mDrawable;
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), sourceDownsampler3.mDrawable.getIntrinsicHeight());
            sourceDownsampler3.mDrawable.draw(canvas);
            BlurProvider.BlurKernel blurKernel = blurProvider.mBlurKernel;
            Objects.requireNonNull(blurKernel);
            blurKernel.prepareInputAllocation(bitmap);
            blurKernel.prepareOutputAllocation(bitmap);
            blurKernel.mBlurScript.setRadius(MathUtils.constrain(constrain * f2, 0.0f, 25.0f));
            blurKernel.mBlurScript.setInput(blurKernel.mLastInputAllocation);
            blurKernel.mBlurScript.forEach(blurKernel.mLastOutputAllocation);
            blurKernel.mLastOutputAllocation.copyTo(bitmap);
            BlurProvider.SourceDownsampler sourceDownsampler4 = blurProvider.mImageSource;
            Objects.requireNonNull(sourceDownsampler4);
            int ceil = (int) Math.ceil((double) ((((float) sourceDownsampler4.mDrawable.getIntrinsicWidth()) * f2) + 50.0f));
            BlurProvider.SourceDownsampler sourceDownsampler5 = blurProvider.mImageSource;
            Objects.requireNonNull(sourceDownsampler5);
            int ceil2 = (int) Math.ceil((double) ((f2 * ((float) sourceDownsampler5.mDrawable.getIntrinsicHeight())) + 50.0f));
            int i5 = 0;
            loop1:
            while (true) {
                if (i5 >= ceil) {
                    i5 = 0;
                    break;
                }
                for (int i6 = 0; i6 < ceil2; i6++) {
                    if (bitmap.getPixel(i5, i6) != 0) {
                        break loop1;
                    }
                }
                i5++;
            }
            int i7 = 0;
            loop3:
            while (true) {
                if (i7 >= ceil2) {
                    break;
                }
                for (int i8 = i5; i8 < ceil; i8++) {
                    if (bitmap.getPixel(i8, i7) != 0) {
                        i2 = i7;
                        break loop3;
                    }
                }
                i7++;
            }
            int i9 = ceil - 1;
            loop5:
            while (true) {
                if (i9 < i5) {
                    break;
                }
                for (int i10 = ceil2 - 1; i10 >= i2; i10--) {
                    if (bitmap.getPixel(i9, i10) != 0) {
                        ceil = i9;
                        break loop5;
                    }
                }
                i9--;
            }
            int i11 = ceil2 - 1;
            loop7:
            while (true) {
                if (i11 < i2) {
                    break;
                }
                for (int i12 = ceil - 1; i12 >= i5; i12--) {
                    if (bitmap.getPixel(i12, i11) != 0) {
                        ceil2 = i11;
                        break loop7;
                    }
                }
                i11--;
            }
            blurResult = new BlurProvider.BlurResult(new BitmapDrawable(blurProvider.mResources, bitmap), new RectF((float) i5, (float) i2, (float) ceil, (float) ceil2));
        }
        this.mGlowImageCropRegion = blurResult.cropRegion;
        this.mGlowImageMatrix.setRectToRect(this.mGlowImageCropRegion, new RectF(0.0f, 0.0f, (float) ((int) Math.ceil((double) (this.mGlowWidthRatio * ((float) getWidth())))), (float) getGlowHeight()), Matrix.ScaleToFit.FILL);
        this.mGlowImageViews.forEach(new GlowView$$ExternalSyntheticLambda0(this, blurResult));
    }

    public final void setGlowsY(int i, int i2, EdgeLight[] edgeLightArr) {
        this.mTranslationY = i;
        this.mMinimumHeightPx = i2;
        this.mEdgeLights = edgeLightArr;
        int i3 = 0;
        if (edgeLightArr == null || edgeLightArr.length != 4) {
            while (i3 < 4) {
                this.mGlowImageViews.get(i3).setTranslationY((float) (getHeight() - i));
                i3++;
            }
            return;
        }
        int i4 = (i - i2) * 4;
        EdgeLight edgeLight = edgeLightArr[0];
        Objects.requireNonNull(edgeLight);
        float f = edgeLight.mLength;
        EdgeLight edgeLight2 = edgeLightArr[1];
        Objects.requireNonNull(edgeLight2);
        float f2 = f + edgeLight2.mLength;
        EdgeLight edgeLight3 = edgeLightArr[2];
        Objects.requireNonNull(edgeLight3);
        float f3 = f2 + edgeLight3.mLength;
        EdgeLight edgeLight4 = edgeLightArr[3];
        Objects.requireNonNull(edgeLight4);
        float f4 = f3 + edgeLight4.mLength;
        while (i3 < 4) {
            EdgeLight edgeLight5 = edgeLightArr[i3];
            Objects.requireNonNull(edgeLight5);
            this.mGlowImageViews.get(i3).setTranslationY((float) (getHeight() - (((int) ((edgeLight5.mLength * ((float) i4)) / f4)) + i2)));
            i3++;
        }
    }

    public final void updateGlowSizes() {
        int ceil = (int) Math.ceil((double) (this.mGlowWidthRatio * ((float) getWidth())));
        int glowHeight = getGlowHeight();
        this.mGlowImageMatrix.setRectToRect(this.mGlowImageCropRegion, new RectF(0.0f, 0.0f, (float) ((int) Math.ceil((double) (this.mGlowWidthRatio * ((float) getWidth())))), (float) getGlowHeight()), Matrix.ScaleToFit.FILL);
        Iterator<ImageView> it = this.mGlowImageViews.iterator();
        while (it.hasNext()) {
            ImageView next = it.next();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) next.getLayoutParams();
            layoutParams.width = ceil;
            layoutParams.height = glowHeight;
            next.setLayoutParams(layoutParams);
            next.setImageMatrix(this.mGlowImageMatrix);
        }
        distributeEvenly();
    }

    public GlowView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onFinishInflate() {
        this.mBlueGlow = (ImageView) findViewById(C1777R.C1779id.blue_glow);
        this.mRedGlow = (ImageView) findViewById(C1777R.C1779id.red_glow);
        this.mYellowGlow = (ImageView) findViewById(C1777R.C1779id.yellow_glow);
        this.mGreenGlow = (ImageView) findViewById(C1777R.C1779id.green_glow);
        this.mGlowImageViews = new ArrayList<>(Arrays.asList(new ImageView[]{this.mBlueGlow, this.mRedGlow, this.mYellowGlow, this.mGreenGlow}));
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        post(new QSAnimator$$ExternalSyntheticLambda0(this, 7));
    }

    public final void setVisibility(int i) {
        int visibility = getVisibility();
        if (visibility != i) {
            super.setVisibility(i);
            if (visibility == 8) {
                setBlurredImageOnViews(this.mBlurRadius);
            }
        }
    }

    public GlowView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mGlowImageCropRegion = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        this.mGlowImageMatrix = new Matrix();
        this.mBlurRadius = 0;
        this.mPaint = new Paint();
        this.mBlurProvider = new BlurProvider(context, context.getResources().getDrawable(C1777R.C1778drawable.glow_vector, (Resources.Theme) null));
    }
}
