package androidx.leanback.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.leanback.R$styleable;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.WeakHashMap;

public class PagingIndicator extends View {
    public static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final C02261 DOT_ALPHA = new Property<Dot, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            Dot dot = (Dot) obj;
            Objects.requireNonNull(dot);
            return Float.valueOf(dot.mAlpha);
        }

        public final void set(Object obj, Object obj2) {
            Dot dot = (Dot) obj;
            float floatValue = ((Float) obj2).floatValue();
            Objects.requireNonNull(dot);
            dot.mAlpha = floatValue;
            Math.round(floatValue * 255.0f);
            throw null;
        }
    };
    public static final C02272 DOT_DIAMETER = new Property<Dot, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            Dot dot = (Dot) obj;
            Objects.requireNonNull(dot);
            return Float.valueOf(dot.mDiameter);
        }

        public final void set(Object obj, Object obj2) {
            Dot dot = (Dot) obj;
            float floatValue = ((Float) obj2).floatValue();
            Objects.requireNonNull(dot);
            dot.mDiameter = floatValue;
            throw null;
        }
    };
    public static final C02283 DOT_TRANSLATION_X = new Property<Dot, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            Dot dot = (Dot) obj;
            Objects.requireNonNull(dot);
            return Float.valueOf(dot.mTranslationX);
        }

        public final void set(Object obj, Object obj2) {
            Dot dot = (Dot) obj;
            float floatValue = ((Float) obj2).floatValue();
            Objects.requireNonNull(dot);
            dot.mTranslationX = floatValue * 0.0f * 0.0f;
            throw null;
        }
    };
    public Bitmap mArrow;
    public final int mArrowDiameter;
    public final int mArrowGap;
    public Paint mArrowPaint;
    public final int mArrowRadius;
    public final int mDotGap;
    public final int mDotRadius;
    public int[] mDotSelectedNextX;
    public int[] mDotSelectedPrevX;
    public int[] mDotSelectedX;
    public boolean mIsLtr;
    public final int mShadowRadius;

    public class Dot {
        public float mAlpha;
        public float mDiameter;
        public float mTranslationX;
    }

    public PagingIndicator(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public int getPageCount() {
        return 0;
    }

    public final void onDraw(Canvas canvas) {
    }

    public PagingIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final ObjectAnimator createDotTranslationXAnimator() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, DOT_TRANSLATION_X, new float[]{(float) ((-this.mArrowGap) + this.mDotGap), 0.0f});
        ofFloat.setDuration(417);
        ofFloat.setInterpolator(DECELERATE_INTERPOLATOR);
        return ofFloat;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public PagingIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        AnimatorSet animatorSet = new AnimatorSet();
        Resources resources = getResources();
        int[] iArr = R$styleable.PagingIndicator;
        Context context2 = context;
        AttributeSet attributeSet2 = attributeSet;
        int i2 = i;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet2, iArr, i2, 0);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context2, iArr, attributeSet2, obtainStyledAttributes, i2, 0);
        int dimensionFromTypedArray = getDimensionFromTypedArray(obtainStyledAttributes, 6, C1777R.dimen.lb_page_indicator_dot_radius);
        this.mDotRadius = dimensionFromTypedArray;
        int i3 = dimensionFromTypedArray * 2;
        int dimensionFromTypedArray2 = getDimensionFromTypedArray(obtainStyledAttributes, 2, C1777R.dimen.lb_page_indicator_arrow_radius);
        this.mArrowRadius = dimensionFromTypedArray2;
        int i4 = dimensionFromTypedArray2 * 2;
        this.mArrowDiameter = i4;
        this.mDotGap = getDimensionFromTypedArray(obtainStyledAttributes, 5, C1777R.dimen.lb_page_indicator_dot_gap);
        this.mArrowGap = getDimensionFromTypedArray(obtainStyledAttributes, 4, C1777R.dimen.lb_page_indicator_arrow_gap);
        new Paint(1).setColor(obtainStyledAttributes.getColor(3, getResources().getColor(C1777R.color.lb_page_indicator_dot)));
        obtainStyledAttributes.getColor(0, getResources().getColor(C1777R.color.lb_page_indicator_arrow_background));
        if (this.mArrowPaint == null && obtainStyledAttributes.hasValue(1)) {
            int color = obtainStyledAttributes.getColor(1, 0);
            if (this.mArrowPaint == null) {
                this.mArrowPaint = new Paint();
            }
            this.mArrowPaint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        }
        obtainStyledAttributes.recycle();
        this.mIsLtr = resources.getConfiguration().getLayoutDirection() == 0;
        int color2 = resources.getColor(C1777R.color.lb_page_indicator_arrow_shadow);
        int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.lb_page_indicator_arrow_shadow_radius);
        this.mShadowRadius = dimensionPixelSize;
        Paint paint = new Paint(1);
        float dimensionPixelSize2 = (float) resources.getDimensionPixelSize(C1777R.dimen.lb_page_indicator_arrow_shadow_offset);
        paint.setShadowLayer((float) dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, color2);
        this.mArrow = loadArrow();
        new Rect(0, 0, this.mArrow.getWidth(), this.mArrow.getHeight());
        this.mArrow.getWidth();
        float f = (float) i4;
        AnimatorSet animatorSet2 = new AnimatorSet();
        C02261 r9 = DOT_ALPHA;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, r9, new float[]{0.0f, 1.0f});
        ofFloat.setDuration(167);
        DecelerateInterpolator decelerateInterpolator = DECELERATE_INTERPOLATOR;
        ofFloat.setInterpolator(decelerateInterpolator);
        float f2 = (float) i3;
        C02272 r11 = DOT_DIAMETER;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat((Object) null, r11, new float[]{f2, f});
        ofFloat2.setDuration(417);
        ofFloat2.setInterpolator(decelerateInterpolator);
        animatorSet2.playTogether(new Animator[]{ofFloat, ofFloat2, createDotTranslationXAnimator()});
        AnimatorSet animatorSet3 = new AnimatorSet();
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat((Object) null, r9, new float[]{1.0f, 0.0f});
        ofFloat3.setDuration(167);
        ofFloat3.setInterpolator(decelerateInterpolator);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat((Object) null, r11, new float[]{f, f2});
        ofFloat4.setDuration(417);
        ofFloat4.setInterpolator(decelerateInterpolator);
        animatorSet3.playTogether(new Animator[]{ofFloat3, ofFloat4, createDotTranslationXAnimator()});
        animatorSet.playTogether(new Animator[]{animatorSet2, animatorSet3});
        setLayerType(1, (Paint) null);
    }

    public final void calculateDotPositions() {
        int paddingLeft = getPaddingLeft();
        getPaddingTop();
        int width = getWidth() - getPaddingRight();
        int i = this.mDotRadius;
        int i2 = this.mArrowGap;
        int i3 = this.mDotGap;
        int i4 = (i3 * -3) + (i2 * 2) + (i * 2);
        int i5 = (paddingLeft + width) / 2;
        int[] iArr = new int[0];
        this.mDotSelectedX = iArr;
        int[] iArr2 = new int[0];
        this.mDotSelectedPrevX = iArr2;
        int[] iArr3 = new int[0];
        this.mDotSelectedNextX = iArr3;
        if (this.mIsLtr) {
            int i6 = (i5 - (i4 / 2)) + i;
            iArr[0] = (i6 - i3) + i2;
            iArr2[0] = i6;
            iArr3[0] = (i2 * 2) + (i6 - (i3 * 2));
        } else {
            int i7 = ((i4 / 2) + i5) - i;
            iArr[0] = (i7 + i3) - i2;
            iArr2[0] = i7;
            iArr3[0] = ((i3 * 2) + i7) - (i2 * 2);
        }
        throw null;
    }

    public final int getDimensionFromTypedArray(TypedArray typedArray, int i, int i2) {
        return typedArray.getDimensionPixelOffset(i, getResources().getDimensionPixelOffset(i2));
    }

    public final Bitmap loadArrow() {
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), C1777R.C1778drawable.lb_ic_nav_arrow);
        if (this.mIsLtr) {
            return decodeResource;
        }
        Matrix matrix = new Matrix();
        matrix.preScale(-1.0f, 1.0f);
        return Bitmap.createBitmap(decodeResource, 0, 0, decodeResource.getWidth(), decodeResource.getHeight(), matrix, false);
    }

    public final void onMeasure(int i, int i2) {
        int paddingBottom = getPaddingBottom() + getPaddingTop() + this.mArrowDiameter + this.mShadowRadius;
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            paddingBottom = Math.min(paddingBottom, View.MeasureSpec.getSize(i2));
        } else if (mode == 1073741824) {
            paddingBottom = View.MeasureSpec.getSize(i2);
        }
        int paddingRight = getPaddingRight() + (this.mDotGap * -3) + (this.mArrowGap * 2) + (this.mDotRadius * 2) + getPaddingLeft();
        int mode2 = View.MeasureSpec.getMode(i);
        if (mode2 == Integer.MIN_VALUE) {
            paddingRight = Math.min(paddingRight, View.MeasureSpec.getSize(i));
        } else if (mode2 == 1073741824) {
            paddingRight = View.MeasureSpec.getSize(i);
        }
        setMeasuredDimension(paddingRight, paddingBottom);
    }

    public final void onRtlPropertiesChanged(int i) {
        boolean z;
        super.onRtlPropertiesChanged(i);
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        if (this.mIsLtr != z) {
            this.mIsLtr = z;
            this.mArrow = loadArrow();
            calculateDotPositions();
            throw null;
        }
    }

    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        setMeasuredDimension(i, i2);
        calculateDotPositions();
        throw null;
    }

    public int[] getDotSelectedLeftX() {
        return this.mDotSelectedPrevX;
    }

    public int[] getDotSelectedRightX() {
        return this.mDotSelectedNextX;
    }

    public int[] getDotSelectedX() {
        return this.mDotSelectedX;
    }
}
