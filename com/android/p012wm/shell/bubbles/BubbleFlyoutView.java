package com.android.p012wm.shell.bubbles;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewPropertyAnimator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.Interpolators;
import com.android.p012wm.shell.bubbles.Bubble;
import com.android.p012wm.shell.common.TriangleShape;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleFlyoutView */
public final class BubbleFlyoutView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();
    public boolean mArrowPointingLeft = true;
    public final Paint mBgPaint;
    public final RectF mBgRect = new RectF();
    public float mBgTranslationX;
    public float mBgTranslationY;
    public final int mBubbleElevation;
    public int mBubbleSize;
    public final float mCornerRadius;
    public float[] mDotCenter;
    public int mDotColor;
    public final int mFloatingBackgroundColor;
    public final int mFlyoutElevation;
    public final int mFlyoutPadding;
    public final int mFlyoutSpaceFromBubble;
    public final ViewGroup mFlyoutTextContainer;
    public float mFlyoutToDotHeightDelta = 0.0f;
    public float mFlyoutToDotWidthDelta = 0.0f;
    public float mFlyoutY = 0.0f;
    public final TextView mMessageText;
    public float mNewDotRadius;
    public float mNewDotSize;
    public Runnable mOnHide;
    public float mOriginalDotSize;
    public float mPercentStillFlyout = 0.0f;
    public float mPercentTransitionedToDot = 1.0f;
    public BubblePositioner mPositioner;
    public float mRestingTranslationX = 0.0f;
    public final ImageView mSenderAvatar;
    public final TextView mSenderText;
    public float mTranslationXWhenDot = 0.0f;
    public float mTranslationYWhenDot = 0.0f;
    public final Outline mTriangleOutline = new Outline();

    public final void updateDot(PointF pointF, boolean z) {
        float f;
        float f2 = 0.0f;
        if (z) {
            f = 0.0f;
        } else {
            f = this.mNewDotSize;
        }
        this.mFlyoutToDotWidthDelta = ((float) getWidth()) - f;
        this.mFlyoutToDotHeightDelta = ((float) getHeight()) - f;
        if (!z) {
            f2 = this.mOriginalDotSize / 2.0f;
        }
        float f3 = pointF.x;
        float[] fArr = this.mDotCenter;
        float f4 = (f3 + fArr[0]) - f2;
        float f5 = (pointF.y + fArr[1]) - f2;
        float f6 = this.mRestingTranslationX - f4;
        float f7 = this.mFlyoutY - f5;
        this.mTranslationXWhenDot = -f6;
        this.mTranslationYWhenDot = -f7;
    }

    public final void fade(boolean z, PointF pointF, boolean z2, Runnable runnable) {
        float f;
        float f2;
        long j;
        PathInterpolator pathInterpolator;
        PathInterpolator pathInterpolator2;
        this.mFlyoutY = (((float) (this.mBubbleSize - this.mFlyoutTextContainer.getHeight())) / 2.0f) + pointF.y;
        float f3 = 0.0f;
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        setAlpha(f);
        float f4 = this.mFlyoutY;
        if (z) {
            f4 += 40.0f;
        }
        setTranslationY(f4);
        float f5 = pointF.x;
        if (this.mArrowPointingLeft) {
            f2 = f5 + ((float) this.mBubbleSize) + ((float) this.mFlyoutSpaceFromBubble);
        } else {
            f2 = (f5 - ((float) getWidth())) - ((float) this.mFlyoutSpaceFromBubble);
        }
        this.mRestingTranslationX = f2;
        setTranslationX(f2);
        updateDot(pointF, z2);
        ViewPropertyAnimator animate = animate();
        if (z) {
            f3 = 1.0f;
        }
        ViewPropertyAnimator alpha = animate.alpha(f3);
        long j2 = 250;
        if (z) {
            j = 250;
        } else {
            j = 150;
        }
        ViewPropertyAnimator duration = alpha.setDuration(j);
        if (z) {
            pathInterpolator = Interpolators.ALPHA_IN;
        } else {
            pathInterpolator = Interpolators.ALPHA_OUT;
        }
        duration.setInterpolator(pathInterpolator);
        ViewPropertyAnimator animate2 = animate();
        float f6 = this.mFlyoutY;
        if (!z) {
            f6 -= 40.0f;
        }
        ViewPropertyAnimator translationY = animate2.translationY(f6);
        if (!z) {
            j2 = 150;
        }
        ViewPropertyAnimator duration2 = translationY.setDuration(j2);
        if (z) {
            pathInterpolator2 = Interpolators.ALPHA_IN;
        } else {
            pathInterpolator2 = Interpolators.ALPHA_OUT;
        }
        duration2.setInterpolator(pathInterpolator2).withEndAction(runnable);
    }

    public final void updateFlyoutMessage(Bubble.FlyoutMessage flyoutMessage) {
        float f;
        Drawable drawable = flyoutMessage.senderAvatar;
        if (drawable == null || !flyoutMessage.isGroupChat) {
            this.mSenderAvatar.setVisibility(8);
            this.mSenderAvatar.setTranslationX(0.0f);
            this.mMessageText.setTranslationX(0.0f);
            this.mSenderText.setTranslationX(0.0f);
        } else {
            this.mSenderAvatar.setVisibility(0);
            this.mSenderAvatar.setImageDrawable(drawable);
        }
        BubblePositioner bubblePositioner = this.mPositioner;
        Objects.requireNonNull(bubblePositioner);
        if (bubblePositioner.mIsLargeScreen) {
            f = Math.max(((float) bubblePositioner.mScreenRect.width()) * 0.3f, (float) bubblePositioner.mMinimumFlyoutWidthLargeScreen);
        } else {
            f = ((float) bubblePositioner.mScreenRect.width()) * 0.6f;
        }
        int i = ((int) f) - (this.mFlyoutPadding * 2);
        if (!TextUtils.isEmpty(flyoutMessage.senderName)) {
            this.mSenderText.setMaxWidth(i);
            this.mSenderText.setText(flyoutMessage.senderName);
            this.mSenderText.setVisibility(0);
        } else {
            this.mSenderText.setVisibility(8);
        }
        this.mMessageText.setMaxWidth(i);
        this.mMessageText.setText(flyoutMessage.message);
    }

    public final void updateFontSize() {
        float dimensionPixelSize = (float) this.mContext.getResources().getDimensionPixelSize(17105569);
        this.mMessageText.setTextSize(0, dimensionPixelSize);
        this.mSenderText.setTextSize(0, dimensionPixelSize);
    }

    public BubbleFlyoutView(Context context, BubblePositioner bubblePositioner) {
        super(context);
        Paint paint = new Paint(3);
        this.mBgPaint = paint;
        this.mPositioner = bubblePositioner;
        LayoutInflater.from(context).inflate(C1777R.layout.bubble_flyout, this, true);
        ViewGroup viewGroup = (ViewGroup) findViewById(C1777R.C1779id.bubble_flyout_text_container);
        this.mFlyoutTextContainer = viewGroup;
        this.mSenderText = (TextView) findViewById(C1777R.C1779id.bubble_flyout_name);
        this.mSenderAvatar = (ImageView) findViewById(C1777R.C1779id.bubble_flyout_avatar);
        this.mMessageText = (TextView) viewGroup.findViewById(C1777R.C1779id.bubble_flyout_text);
        Resources resources = getResources();
        this.mFlyoutPadding = resources.getDimensionPixelSize(C1777R.dimen.bubble_flyout_padding_x);
        this.mFlyoutSpaceFromBubble = resources.getDimensionPixelSize(C1777R.dimen.bubble_flyout_space_from_bubble);
        this.mBubbleElevation = resources.getDimensionPixelSize(C1777R.dimen.bubble_elevation);
        int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.bubble_flyout_elevation);
        this.mFlyoutElevation = dimensionPixelSize;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{17956909, 16844145});
        int color = obtainStyledAttributes.getColor(0, -1);
        this.mFloatingBackgroundColor = color;
        this.mCornerRadius = (float) obtainStyledAttributes.getDimensionPixelSize(1, 0);
        obtainStyledAttributes.recycle();
        setPadding(0, 0, 0, 0);
        setWillNotDraw(false);
        setClipChildren(true);
        setTranslationZ((float) dimensionPixelSize);
        setOutlineProvider(new ViewOutlineProvider() {
            public final void getOutline(View view, Outline outline) {
                BubbleFlyoutView bubbleFlyoutView = BubbleFlyoutView.this;
                Objects.requireNonNull(bubbleFlyoutView);
                bubbleFlyoutView.mTriangleOutline.isEmpty();
                Path path = new Path();
                float f = bubbleFlyoutView.mNewDotRadius;
                float f2 = bubbleFlyoutView.mPercentTransitionedToDot;
                float m = MotionController$$ExternalSyntheticOutline0.m7m(1.0f, f2, bubbleFlyoutView.mCornerRadius, f * f2);
                path.addRoundRect(bubbleFlyoutView.mBgRect, m, m, Path.Direction.CW);
                outline.setPath(path);
                Matrix matrix = new Matrix();
                matrix.postTranslate(((float) bubbleFlyoutView.getLeft()) + bubbleFlyoutView.mBgTranslationX, ((float) bubbleFlyoutView.getTop()) + bubbleFlyoutView.mBgTranslationY);
                float f3 = bubbleFlyoutView.mPercentTransitionedToDot;
                if (f3 > 0.98f) {
                    float f4 = (f3 - 0.98f) / 0.02f;
                    float f5 = 1.0f - f4;
                    float f6 = bubbleFlyoutView.mNewDotRadius * f4;
                    matrix.postTranslate(f6, f6);
                    matrix.preScale(f5, f5);
                }
                outline.mPath.transform(matrix);
            }
        });
        setLayoutDirection(3);
        paint.setColor(color);
        float f = (float) 0;
        ShapeDrawable shapeDrawable = new ShapeDrawable(TriangleShape.createHorizontal(f, f, true));
        shapeDrawable.setBounds(0, 0, 0, 0);
        shapeDrawable.getPaint().setColor(color);
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(TriangleShape.createHorizontal(f, f, false));
        shapeDrawable2.setBounds(0, 0, 0, 0);
        shapeDrawable2.getPaint().setColor(color);
    }

    public final void onDraw(Canvas canvas) {
        float width = ((float) getWidth()) - (this.mFlyoutToDotWidthDelta * this.mPercentTransitionedToDot);
        float f = this.mFlyoutToDotHeightDelta;
        float f2 = this.mPercentTransitionedToDot;
        float height = ((float) getHeight()) - (f * f2);
        float m = MotionController$$ExternalSyntheticOutline0.m7m(1.0f, f2, this.mCornerRadius, this.mNewDotRadius * f2);
        this.mBgTranslationX = this.mTranslationXWhenDot * f2;
        this.mBgTranslationY = this.mTranslationYWhenDot * f2;
        RectF rectF = this.mBgRect;
        float f3 = ((float) 0) * this.mPercentStillFlyout;
        rectF.set(f3, 0.0f, width - f3, height);
        this.mBgPaint.setColor(((Integer) this.mArgbEvaluator.evaluate(this.mPercentTransitionedToDot, Integer.valueOf(this.mFloatingBackgroundColor), Integer.valueOf(this.mDotColor))).intValue());
        canvas.save();
        canvas.translate(this.mBgTranslationX, this.mBgTranslationY);
        canvas.drawRoundRect(this.mBgRect, m, m, this.mBgPaint);
        canvas.restore();
        invalidateOutline();
        super.onDraw(canvas);
    }

    public final void setCollapsePercent(float f) {
        int i;
        if (!Float.isNaN(f)) {
            float max = Math.max(0.0f, Math.min(f, 1.0f));
            this.mPercentTransitionedToDot = max;
            this.mPercentStillFlyout = 1.0f - max;
            if (this.mArrowPointingLeft) {
                i = -getWidth();
            } else {
                i = getWidth();
            }
            float f2 = max * ((float) i);
            float min = Math.min(1.0f, Math.max(0.0f, (this.mPercentStillFlyout - 0.75f) / 0.25f));
            this.mMessageText.setTranslationX(f2);
            this.mMessageText.setAlpha(min);
            this.mSenderText.setTranslationX(f2);
            this.mSenderText.setAlpha(min);
            this.mSenderAvatar.setTranslationX(f2);
            this.mSenderAvatar.setAlpha(min);
            int i2 = this.mFlyoutElevation;
            setTranslationZ(((float) i2) - (((float) (i2 - this.mBubbleElevation)) * this.mPercentTransitionedToDot));
            invalidate();
        }
    }
}
