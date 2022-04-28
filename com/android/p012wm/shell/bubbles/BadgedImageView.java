package com.android.p012wm.shell.bubbles;

import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.PathParser;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.launcher3.icons.DotRenderer;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.Interpolators;
import java.util.EnumSet;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BadgedImageView */
public class BadgedImageView extends ConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public float mAnimatingToDotScale;
    public final ImageView mAppIcon;
    public BubbleViewProvider mBubble;
    public final ImageView mBubbleIcon;
    public int mDotColor;
    public boolean mDotIsAnimating;
    public DotRenderer mDotRenderer;
    public float mDotScale;
    public final EnumSet<SuppressionFlag> mDotSuppressionFlags;
    public DotRenderer.DrawParams mDrawParams;
    public boolean mOnLeft;
    public BubblePositioner mPositioner;
    public Rect mTempBounds;

    /* renamed from: com.android.wm.shell.bubbles.BadgedImageView$SuppressionFlag */
    public enum SuppressionFlag {
        FLYOUT_VISIBLE,
        BEHIND_STACK
    }

    public BadgedImageView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BadgedImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final float[] getDotCenter() {
        float[] fArr;
        if (this.mOnLeft) {
            DotRenderer dotRenderer = this.mDotRenderer;
            Objects.requireNonNull(dotRenderer);
            fArr = dotRenderer.mLeftDotPosition;
        } else {
            DotRenderer dotRenderer2 = this.mDotRenderer;
            Objects.requireNonNull(dotRenderer2);
            fArr = dotRenderer2.mRightDotPosition;
        }
        getDrawingRect(this.mTempBounds);
        return new float[]{((float) this.mTempBounds.width()) * fArr[0], ((float) this.mTempBounds.height()) * fArr[1]};
    }

    public final void hideDotAndBadge(boolean z) {
        if (this.mDotSuppressionFlags.add(SuppressionFlag.BEHIND_STACK)) {
            updateDotVisibility(true);
        }
        this.mOnLeft = z;
        this.mAppIcon.setVisibility(8);
    }

    public final void initialize(BubblePositioner bubblePositioner) {
        this.mPositioner = bubblePositioner;
        Path createPathFromPathData = PathParser.createPathFromPathData(getResources().getString(17039972));
        BubblePositioner bubblePositioner2 = this.mPositioner;
        Objects.requireNonNull(bubblePositioner2);
        this.mDotRenderer = new DotRenderer(bubblePositioner2.mBubbleSize, createPathFromPathData);
    }

    public final void removeDotSuppressionFlag(SuppressionFlag suppressionFlag) {
        boolean z;
        if (this.mDotSuppressionFlags.remove(suppressionFlag)) {
            if (suppressionFlag == SuppressionFlag.BEHIND_STACK) {
                z = true;
            } else {
                z = false;
            }
            updateDotVisibility(z);
        }
    }

    public final void setRenderedBubble(BubbleViewProvider bubbleViewProvider) {
        this.mBubble = bubbleViewProvider;
        this.mBubbleIcon.setImageBitmap(bubbleViewProvider.getBubbleIcon());
        this.mAppIcon.setImageBitmap(bubbleViewProvider.getAppBadge());
        if (this.mDotSuppressionFlags.contains(SuppressionFlag.BEHIND_STACK)) {
            this.mAppIcon.setVisibility(8);
        } else {
            showBadge();
        }
        this.mDotColor = bubbleViewProvider.getDotColor();
        Path dotPath = bubbleViewProvider.getDotPath();
        BubblePositioner bubblePositioner = this.mPositioner;
        Objects.requireNonNull(bubblePositioner);
        this.mDotRenderer = new DotRenderer(bubblePositioner.mBubbleSize, dotPath);
        invalidate();
    }

    public final boolean shouldDrawDot() {
        if (this.mDotIsAnimating || (this.mBubble.showDot() && this.mDotSuppressionFlags.isEmpty())) {
            return true;
        }
        return false;
    }

    public final void showBadge() {
        int i;
        if (this.mBubble.getAppBadge() == null) {
            this.mAppIcon.setVisibility(8);
            return;
        }
        if (this.mOnLeft) {
            i = -(this.mBubbleIcon.getWidth() - this.mAppIcon.getWidth());
        } else {
            i = 0;
        }
        this.mAppIcon.setTranslationX((float) i);
        this.mAppIcon.setVisibility(0);
    }

    public final void showDotAndBadge(boolean z) {
        removeDotSuppressionFlag(SuppressionFlag.BEHIND_STACK);
        this.mOnLeft = z;
        showBadge();
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BadgedImageView{");
        m.append(this.mBubble);
        m.append("}");
        return m.toString();
    }

    public BadgedImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final void onDraw(Canvas canvas) {
        float[] fArr;
        float f;
        super.onDraw(canvas);
        if (shouldDrawDot()) {
            getDrawingRect(this.mTempBounds);
            DotRenderer.DrawParams drawParams = this.mDrawParams;
            drawParams.color = this.mDotColor;
            drawParams.iconBounds = this.mTempBounds;
            drawParams.leftAlign = this.mOnLeft;
            drawParams.scale = this.mDotScale;
            DotRenderer dotRenderer = this.mDotRenderer;
            Objects.requireNonNull(dotRenderer);
            if (drawParams == null) {
                Log.e("DotRenderer", "Invalid null argument(s) passed in call to draw.");
                return;
            }
            canvas.save();
            Rect rect = drawParams.iconBounds;
            if (drawParams.leftAlign) {
                fArr = dotRenderer.mLeftDotPosition;
            } else {
                fArr = dotRenderer.mRightDotPosition;
            }
            float width = (((float) rect.width()) * fArr[0]) + ((float) rect.left);
            float height = (((float) rect.height()) * fArr[1]) + ((float) rect.top);
            Rect clipBounds = canvas.getClipBounds();
            if (drawParams.leftAlign) {
                f = Math.max(0.0f, ((float) clipBounds.left) - (dotRenderer.mBitmapOffset + width));
            } else {
                f = Math.min(0.0f, ((float) clipBounds.right) - (width - dotRenderer.mBitmapOffset));
            }
            canvas.translate(width + f, height + Math.max(0.0f, ((float) clipBounds.top) - (dotRenderer.mBitmapOffset + height)));
            float f2 = drawParams.scale;
            canvas.scale(f2, f2);
            dotRenderer.mCirclePaint.setColor(-16777216);
            Bitmap bitmap = dotRenderer.mBackgroundWithShadow;
            float f3 = dotRenderer.mBitmapOffset;
            canvas.drawBitmap(bitmap, f3, f3, dotRenderer.mCirclePaint);
            dotRenderer.mCirclePaint.setColor(drawParams.color);
            canvas.drawCircle(0.0f, 0.0f, dotRenderer.mCircleRadius, dotRenderer.mCirclePaint);
            canvas.restore();
        }
    }

    public final void updateDotVisibility(boolean z) {
        float f;
        if (shouldDrawDot()) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        if (z) {
            boolean z2 = true;
            this.mDotIsAnimating = true;
            if (this.mAnimatingToDotScale == f || !shouldDrawDot()) {
                this.mDotIsAnimating = false;
                return;
            }
            this.mAnimatingToDotScale = f;
            if (f <= 0.0f) {
                z2 = false;
            }
            clearAnimation();
            animate().setDuration(200).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setUpdateListener(new BadgedImageView$$ExternalSyntheticLambda0(this, z2)).withEndAction(new BadgedImageView$$ExternalSyntheticLambda1(this, z2)).start();
            return;
        }
        this.mDotScale = f;
        this.mAnimatingToDotScale = f;
        invalidate();
    }

    public BadgedImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDotSuppressionFlags = EnumSet.of(SuppressionFlag.FLYOUT_VISIBLE);
        this.mDotScale = 0.0f;
        this.mAnimatingToDotScale = 0.0f;
        this.mDotIsAnimating = false;
        this.mTempBounds = new Rect();
        setLayoutDirection(0);
        LayoutInflater.from(context).inflate(C1777R.layout.badged_image_view, this);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.icon_view);
        this.mBubbleIcon = imageView;
        this.mAppIcon = (ImageView) findViewById(C1777R.C1779id.app_icon_view);
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(attributeSet, new int[]{16843033}, i, i2);
        imageView.setImageResource(obtainStyledAttributes.getResourceId(0, 0));
        obtainStyledAttributes.recycle();
        this.mDrawParams = new DotRenderer.DrawParams();
        setFocusable(true);
        setClickable(true);
        setOutlineProvider(new ViewOutlineProvider() {
            public final void getOutline(View view, Outline outline) {
                BadgedImageView badgedImageView = BadgedImageView.this;
                int i = BadgedImageView.$r8$clinit;
                Objects.requireNonNull(badgedImageView);
                BubblePositioner bubblePositioner = badgedImageView.mPositioner;
                Objects.requireNonNull(bubblePositioner);
                int i2 = bubblePositioner.mBubbleSize;
                int round = (int) Math.round(Math.sqrt(((double) ((((float) (i2 * i2)) * 0.6597222f) * 4.0f)) / 3.141592653589793d));
                int i3 = (i2 - round) / 2;
                int i4 = round + i3;
                outline.setOval(i3, i3, i4, i4);
            }
        });
    }
}
