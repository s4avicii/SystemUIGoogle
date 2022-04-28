package com.google.android.systemui.gamedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.IntProperty;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

@SuppressLint({"AppCompatCustomView"})
public class RevealButton extends View {
    public static final C22832 BACKGROUND_HEIGHT = new IntProperty<RevealButton>() {
        public final Object get(Object obj) {
            RevealButton revealButton = (RevealButton) obj;
            C22821 r0 = RevealButton.BACKGROUND_WIDTH;
            Objects.requireNonNull(revealButton);
            return Integer.valueOf(revealButton.mBackground.getBounds().height());
        }

        public final void setValue(Object obj, int i) {
            RevealButton revealButton = (RevealButton) obj;
            C22821 r2 = RevealButton.BACKGROUND_WIDTH;
            Objects.requireNonNull(revealButton);
            Rect bounds = revealButton.mBackground.getBounds();
            int i2 = i / 2;
            revealButton.mBackground.setBounds(bounds.left, bounds.centerY() - i2, bounds.right, bounds.centerY() + i2);
        }
    };
    public static final C22821 BACKGROUND_WIDTH = new IntProperty<RevealButton>() {
        public final Object get(Object obj) {
            RevealButton revealButton = (RevealButton) obj;
            C22821 r0 = RevealButton.BACKGROUND_WIDTH;
            Objects.requireNonNull(revealButton);
            return Integer.valueOf(revealButton.mBackground.getBounds().width());
        }

        public final void setValue(Object obj, int i) {
            RevealButton revealButton = (RevealButton) obj;
            C22821 r2 = RevealButton.BACKGROUND_WIDTH;
            Objects.requireNonNull(revealButton);
            Rect bounds = revealButton.mBackground.getBounds();
            RevealButtonBackground revealButtonBackground = revealButton.mBackground;
            int i2 = bounds.left;
            revealButtonBackground.setBounds(i2, bounds.top, i + i2, bounds.bottom);
        }
    };
    public static final C22843 ICON_ALPHA = new IntProperty<RevealButton>() {
        public final Object get(Object obj) {
            RevealButton revealButton = (RevealButton) obj;
            C22821 r0 = RevealButton.BACKGROUND_WIDTH;
            Objects.requireNonNull(revealButton);
            return Integer.valueOf(revealButton.mIconAlpha);
        }

        public final void setValue(Object obj, int i) {
            RevealButton revealButton = (RevealButton) obj;
            C22821 r0 = RevealButton.BACKGROUND_WIDTH;
            Objects.requireNonNull(revealButton);
            revealButton.mIconAlpha = i;
            revealButton.invalidate();
        }
    };
    public final RevealButtonBackground mBackground;
    public int mIconAlpha = 255;
    public boolean mIsRecording = false;
    public final Drawable mLeftArrow;
    public final Drawable mLeftRecordingDot;
    public final Drawable mRightArrow;
    public final Drawable mRightRecordingDot;
    public boolean mRightSide = true;
    public ValueAnimator mValueAnimator;

    public final void bounce(boolean z) {
        float f;
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        final Rect copyBounds = this.mBackground.copyBounds();
        int width = copyBounds.width();
        int height = copyBounds.height();
        float[] fArr = new float[3];
        fArr[0] = 1.0f;
        if (z) {
            f = 1.25f;
        } else {
            f = 0.75f;
        }
        fArr[1] = f;
        fArr[2] = 1.0f;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(fArr);
        this.mValueAnimator = ofFloat;
        ofFloat.addUpdateListener(new RevealButton$$ExternalSyntheticLambda0(this, width, copyBounds, height));
        this.mValueAnimator.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                RevealButton.this.mBackground.setBounds(copyBounds);
            }
        });
        this.mValueAnimator.setDuration(300);
        this.mValueAnimator.start();
    }

    public final void drawDrawable(Drawable drawable, Canvas canvas, int i, int i2, int i3, int i4) {
        int i5 = i / 2;
        drawable.setBounds(0, 0, i5, i5);
        canvas.translate((float) i3, (float) (((i2 - drawable.getBounds().width()) / 2) + i4));
        drawable.setAlpha(this.mIconAlpha);
        drawable.draw(canvas);
    }

    public RevealButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getContext();
        RevealButtonBackground revealButtonBackground = new RevealButtonBackground();
        this.mBackground = revealButtonBackground;
        setWillNotDraw(false);
        Resources resources = getResources();
        Resources.Theme theme = context.getTheme();
        this.mLeftArrow = resources.getDrawable(C1777R.C1778drawable.ic_wide_arrow_left, theme);
        this.mRightArrow = resources.getDrawable(C1777R.C1778drawable.ic_wide_arrow_right, theme);
        this.mLeftRecordingDot = resources.getDrawable(C1777R.C1778drawable.ic_recording_dot_left, theme);
        this.mRightRecordingDot = resources.getDrawable(C1777R.C1778drawable.ic_recording_dot_right, theme);
        setBackground(revealButtonBackground);
    }

    public final void onDraw(Canvas canvas) {
        Drawable drawable;
        Drawable drawable2;
        super.onDraw(canvas);
        canvas.save();
        Rect bounds = this.mBackground.getBounds();
        int width = bounds.width();
        int height = bounds.height();
        if (this.mRightSide) {
            if (this.mIsRecording) {
                drawable2 = this.mLeftRecordingDot;
            } else {
                drawable2 = this.mLeftArrow;
            }
            drawDrawable(drawable2, canvas, width, height, bounds.left, bounds.top);
        } else {
            if (this.mIsRecording) {
                drawable = this.mRightRecordingDot;
            } else {
                drawable = this.mRightArrow;
            }
            Canvas canvas2 = canvas;
            drawDrawable(drawable, canvas2, width, height, (width / 2) + bounds.left, bounds.top);
        }
        canvas.restore();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mBackground.setBounds(0, 0, getWidth(), getHeight());
    }

    public final boolean performClick() {
        super.performClick();
        return true;
    }
}
