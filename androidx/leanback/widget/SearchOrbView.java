package androidx.leanback.widget;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.leanback.R$styleable;
import com.android.p012wm.shell.C1777R;
import java.util.WeakHashMap;

public class SearchOrbView extends FrameLayout implements View.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mAttachedToWindow;
    public boolean mColorAnimationEnabled;
    public ValueAnimator mColorAnimator;
    public final ArgbEvaluator mColorEvaluator;
    public Colors mColors;
    public final SearchOrbView$$ExternalSyntheticLambda0 mFocusUpdateListener;
    public final float mFocusedZ;
    public final float mFocusedZoom;
    public final ImageView mIcon;
    public View.OnClickListener mListener;
    public final int mPulseDurationMs;
    public final View mRootView;
    public final int mScaleDurationMs;
    public final View mSearchOrbView;
    public ValueAnimator mShadowFocusAnimator;
    public final float mUnfocusedZ;
    public final SearchOrbView$$ExternalSyntheticLambda1 mUpdateListener;

    public SearchOrbView(Context context) {
        this(context, (AttributeSet) null);
    }

    public int getLayoutResourceId() {
        return C1777R.layout.lb_search_orb;
    }

    public final void onDetachedFromWindow() {
        this.mAttachedToWindow = false;
        updateColorAnimator();
        super.onDetachedFromWindow();
    }

    public static class Colors {
        public int brightColor;
        public int color;
        public int iconColor;

        public Colors(int i, int i2, int i3) {
            this.color = i;
            if (i2 == i) {
                i2 = Color.argb((int) ((((float) Color.alpha(i)) * 0.85f) + 38.25f), (int) ((((float) Color.red(i)) * 0.85f) + 38.25f), (int) ((((float) Color.green(i)) * 0.85f) + 38.25f), (int) ((((float) Color.blue(i)) * 0.85f) + 38.25f));
            }
            this.brightColor = i2;
            this.iconColor = i3;
        }
    }

    public SearchOrbView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.searchOrbViewStyle);
    }

    public final void animateOnFocus(boolean z) {
        float f;
        if (z) {
            f = this.mFocusedZoom;
        } else {
            f = 1.0f;
        }
        this.mRootView.animate().scaleX(f).scaleY(f).setDuration((long) this.mScaleDurationMs).start();
        int i = this.mScaleDurationMs;
        if (this.mShadowFocusAnimator == null) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mShadowFocusAnimator = ofFloat;
            ofFloat.addUpdateListener(this.mFocusUpdateListener);
        }
        if (z) {
            this.mShadowFocusAnimator.start();
        } else {
            this.mShadowFocusAnimator.reverse();
        }
        this.mShadowFocusAnimator.setDuration((long) i);
        this.mColorAnimationEnabled = z;
        updateColorAnimator();
    }

    public final void onClick(View view) {
        View.OnClickListener onClickListener = this.mListener;
        if (onClickListener != null) {
            onClickListener.onClick(view);
        }
    }

    public final void updateColorAnimator() {
        ValueAnimator valueAnimator = this.mColorAnimator;
        if (valueAnimator != null) {
            valueAnimator.end();
            this.mColorAnimator = null;
        }
        if (this.mColorAnimationEnabled && this.mAttachedToWindow) {
            ValueAnimator ofObject = ValueAnimator.ofObject(this.mColorEvaluator, new Object[]{Integer.valueOf(this.mColors.color), Integer.valueOf(this.mColors.brightColor), Integer.valueOf(this.mColors.color)});
            this.mColorAnimator = ofObject;
            ofObject.setRepeatCount(-1);
            this.mColorAnimator.setDuration((long) (this.mPulseDurationMs * 2));
            this.mColorAnimator.addUpdateListener(this.mUpdateListener);
            this.mColorAnimator.start();
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @SuppressLint({"CustomViewStyleable"})
    public SearchOrbView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Context context2 = context;
        this.mColorEvaluator = new ArgbEvaluator();
        this.mUpdateListener = new SearchOrbView$$ExternalSyntheticLambda1(this);
        this.mFocusUpdateListener = new SearchOrbView$$ExternalSyntheticLambda0(this, 0);
        Resources resources = context.getResources();
        View inflate = ((LayoutInflater) context2.getSystemService("layout_inflater")).inflate(getLayoutResourceId(), this, true);
        this.mRootView = inflate;
        View findViewById = inflate.findViewById(C1777R.C1779id.search_orb);
        this.mSearchOrbView = findViewById;
        ImageView imageView = (ImageView) inflate.findViewById(C1777R.C1779id.icon);
        this.mIcon = imageView;
        this.mFocusedZoom = context.getResources().getFraction(C1777R.fraction.lb_search_orb_focused_zoom, 1, 1);
        this.mPulseDurationMs = context.getResources().getInteger(C1777R.integer.lb_search_orb_pulse_duration_ms);
        this.mScaleDurationMs = context.getResources().getInteger(C1777R.integer.lb_search_orb_scale_duration_ms);
        float dimensionPixelSize = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.lb_search_orb_focused_z);
        this.mFocusedZ = dimensionPixelSize;
        float dimensionPixelSize2 = (float) context.getResources().getDimensionPixelSize(C1777R.dimen.lb_search_orb_unfocused_z);
        this.mUnfocusedZ = dimensionPixelSize2;
        int[] iArr = R$styleable.lbSearchOrbView;
        AttributeSet attributeSet2 = attributeSet;
        int i2 = i;
        TypedArray obtainStyledAttributes = context2.obtainStyledAttributes(attributeSet2, iArr, i2, 0);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context2, iArr, attributeSet2, obtainStyledAttributes, i2, 0);
        Drawable drawable = obtainStyledAttributes.getDrawable(2);
        imageView.setImageDrawable(drawable == null ? resources.getDrawable(C1777R.C1778drawable.lb_ic_in_app_search) : drawable);
        int color = obtainStyledAttributes.getColor(1, resources.getColor(C1777R.color.lb_default_search_color));
        Colors colors = new Colors(color, obtainStyledAttributes.getColor(0, color), obtainStyledAttributes.getColor(3, 0));
        this.mColors = colors;
        imageView.setColorFilter(colors.iconColor);
        if (this.mColorAnimator == null) {
            int i3 = this.mColors.color;
            if (findViewById.getBackground() instanceof GradientDrawable) {
                ((GradientDrawable) findViewById.getBackground()).setColor(i3);
            }
        } else {
            this.mColorAnimationEnabled = true;
            updateColorAnimator();
        }
        obtainStyledAttributes.recycle();
        setFocusable(true);
        setClipChildren(false);
        setOnClickListener(this);
        setSoundEffectsEnabled(false);
        ViewCompat.Api21Impl.setZ(findViewById, ((dimensionPixelSize - dimensionPixelSize2) * 0.0f) + dimensionPixelSize2);
        ViewCompat.Api21Impl.setZ(imageView, dimensionPixelSize);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        updateColorAnimator();
    }

    public final void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        animateOnFocus(z);
    }
}
