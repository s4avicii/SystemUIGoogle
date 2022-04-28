package com.android.p012wm.shell.draganddrop;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.Interpolators;
import java.util.Objects;

/* renamed from: com.android.wm.shell.draganddrop.DropZoneView */
public class DropZoneView extends FrameLayout {
    public static final C18611 INSETS = new FloatProperty<DropZoneView>() {
        public final Object get(Object obj) {
            DropZoneView dropZoneView = (DropZoneView) obj;
            C18611 r0 = DropZoneView.INSETS;
            Objects.requireNonNull(dropZoneView);
            return Float.valueOf(dropZoneView.mMarginPercent);
        }

        public final void setValue(Object obj, float f) {
            DropZoneView dropZoneView = (DropZoneView) obj;
            C18611 r0 = DropZoneView.INSETS;
            Objects.requireNonNull(dropZoneView);
            if (f != dropZoneView.mMarginPercent) {
                dropZoneView.mMarginPercent = f;
                dropZoneView.mMarginView.invalidate();
            }
        }
    };
    public ObjectAnimator mBackgroundAnimator;
    public float mBottomInset;
    public ColorDrawable mColorDrawable;
    public final float[] mContainerMargin = new float[4];
    public float mCornerRadius;
    public int mHighlightColor;
    public ObjectAnimator mMarginAnimator;
    public int mMarginColor;
    public float mMarginPercent;
    public MarginView mMarginView;
    public final Path mPath = new Path();
    public boolean mShowingHighlight;
    public boolean mShowingMargin;
    public boolean mShowingSplash;
    public int mSplashScreenColor;
    public ImageView mSplashScreenView;

    /* renamed from: com.android.wm.shell.draganddrop.DropZoneView$MarginView */
    public class MarginView extends View {
        public MarginView(Context context) {
            super(context);
        }

        public final void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            DropZoneView.this.mPath.reset();
            DropZoneView dropZoneView = DropZoneView.this;
            Path path = dropZoneView.mPath;
            float[] fArr = dropZoneView.mContainerMargin;
            float f = fArr[0];
            float f2 = dropZoneView.mMarginPercent;
            float f3 = f * f2;
            float f4 = f2 * fArr[1];
            DropZoneView dropZoneView2 = DropZoneView.this;
            float width = ((float) getWidth()) - (dropZoneView2.mContainerMargin[2] * dropZoneView2.mMarginPercent);
            DropZoneView dropZoneView3 = DropZoneView.this;
            float f5 = dropZoneView3.mContainerMargin[3];
            float f6 = dropZoneView3.mMarginPercent;
            float height = (((float) getHeight()) - (f5 * f6)) - dropZoneView3.mBottomInset;
            float f7 = dropZoneView3.mCornerRadius;
            path.addRoundRect(f3, f4, width, height, f7 * f6, f6 * f7, Path.Direction.CW);
            DropZoneView.this.mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
            canvas.clipPath(DropZoneView.this.mPath);
            canvas.drawColor(DropZoneView.this.mMarginColor);
        }
    }

    public DropZoneView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0, 0);
        setContainerMargin(0.0f, 0.0f, 0.0f, 0.0f);
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mMarginColor = getResources().getColor(C1777R.color.taskbar_background);
        int color = getResources().getColor(17170494);
        this.mHighlightColor = Color.argb(1.0f, (float) Color.red(color), (float) Color.green(color), (float) Color.blue(color));
        this.mSplashScreenColor = Color.argb(0.9f, 0.0f, 0.0f, 0.0f);
        ColorDrawable colorDrawable = new ColorDrawable();
        this.mColorDrawable = colorDrawable;
        setBackgroundDrawable(colorDrawable);
        ImageView imageView = new ImageView(context);
        this.mSplashScreenView = imageView;
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        addView(this.mSplashScreenView, new FrameLayout.LayoutParams(-1, -1));
        this.mSplashScreenView.setAlpha(0.0f);
        MarginView marginView = new MarginView(context);
        this.mMarginView = marginView;
        addView(marginView, new FrameLayout.LayoutParams(-1, -1));
    }

    public final void animateBackground(int i, int i2) {
        ObjectAnimator objectAnimator = this.mBackgroundAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        ObjectAnimator ofArgb = ObjectAnimator.ofArgb(this.mColorDrawable, "color", new int[]{i, i2});
        this.mBackgroundAnimator = ofArgb;
        if (!this.mShowingSplash && !this.mShowingHighlight) {
            ofArgb.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        }
        this.mBackgroundAnimator.start();
    }

    public final void animateSplashScreenIcon() {
        float f;
        ViewPropertyAnimator animate = this.mSplashScreenView.animate();
        if (this.mShowingSplash) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        animate.alpha(f).start();
    }

    public final void animateSwitch() {
        int i;
        boolean z = !this.mShowingHighlight;
        this.mShowingHighlight = z;
        this.mShowingSplash = !z;
        if (z) {
            i = this.mHighlightColor;
        } else {
            i = this.mSplashScreenColor;
        }
        animateBackground(this.mColorDrawable.getColor(), i);
        animateSplashScreenIcon();
    }

    public final void setBottomInset(float f) {
        this.mBottomInset = f;
        ((FrameLayout.LayoutParams) this.mSplashScreenView.getLayoutParams()).bottomMargin = (int) f;
        if (this.mMarginPercent > 0.0f) {
            this.mMarginView.invalidate();
        }
    }

    public final void setContainerMargin(float f, float f2, float f3, float f4) {
        float[] fArr = this.mContainerMargin;
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        fArr[3] = f4;
        if (this.mMarginPercent > 0.0f) {
            this.mMarginView.invalidate();
        }
    }

    public final void setShowingHighlight(boolean z) {
        int i;
        this.mShowingHighlight = z;
        this.mShowingSplash = !z;
        if (z) {
            i = this.mHighlightColor;
        } else {
            i = this.mSplashScreenColor;
        }
        animateBackground(0, i);
        animateSplashScreenIcon();
    }

    public final void setShowingMargin(boolean z) {
        float f;
        long j;
        if (this.mShowingMargin != z) {
            this.mShowingMargin = z;
            ObjectAnimator objectAnimator = this.mMarginAnimator;
            if (objectAnimator != null) {
                objectAnimator.cancel();
            }
            C18611 r5 = INSETS;
            float[] fArr = new float[2];
            fArr[0] = this.mMarginPercent;
            if (this.mShowingMargin) {
                f = 1.0f;
            } else {
                f = 0.0f;
            }
            fArr[1] = f;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, r5, fArr);
            this.mMarginAnimator = ofFloat;
            ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
            ObjectAnimator objectAnimator2 = this.mMarginAnimator;
            if (this.mShowingMargin) {
                j = 400;
            } else {
                j = 250;
            }
            objectAnimator2.setDuration(j);
            this.mMarginAnimator.start();
        }
        if (!this.mShowingMargin) {
            this.mShowingHighlight = false;
            this.mShowingSplash = false;
            animateBackground(this.mColorDrawable.getColor(), 0);
            animateSplashScreenIcon();
        }
    }

    public final void onThemeChange() {
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(getContext());
        this.mMarginColor = getResources().getColor(C1777R.color.taskbar_background);
        this.mHighlightColor = getResources().getColor(17170494);
        if (this.mMarginPercent > 0.0f) {
            this.mMarginView.invalidate();
        }
    }

    public final void setAppInfo(int i, Drawable drawable) {
        Color valueOf = Color.valueOf(i);
        this.mSplashScreenColor = Color.argb(0.9f, valueOf.red(), valueOf.green(), valueOf.blue());
        this.mSplashScreenView.setImageDrawable(drawable);
    }
}
