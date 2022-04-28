package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.viewpager.widget.ViewPager;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.WeakHashMap;

@ViewPager.DecorView
public class PagerTitleStrip extends ViewGroup {
    public static final int[] ATTRS = {16842804, 16842901, 16842904, 16842927};
    public static final int[] TEXT_ATTRS = {16843660};
    public TextView mCurrText;
    public int mGravity;
    public int mLastKnownCurrentPage = -1;
    public float mLastKnownPositionOffset = -1.0f;
    public TextView mNextText;
    public final PageListener mPageListener = new PageListener();
    public ViewPager mPager;
    public TextView mPrevText;
    public int mScaledTextSpacing;
    public int mTextColor;
    public boolean mUpdatingPositions;
    public boolean mUpdatingText;
    public WeakReference<PagerAdapter> mWatchingAdapter;

    public class PageListener extends DataSetObserver implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
        public int mScrollState;

        public PageListener() {
        }

        public final void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            PagerTitleStrip.this.updateAdapter(pagerAdapter, pagerAdapter2);
        }

        public final void onChanged() {
            PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
            ViewPager viewPager = pagerTitleStrip.mPager;
            Objects.requireNonNull(viewPager);
            int i = viewPager.mCurItem;
            ViewPager viewPager2 = PagerTitleStrip.this.mPager;
            Objects.requireNonNull(viewPager2);
            pagerTitleStrip.updateText(i, viewPager2.mAdapter);
            PagerTitleStrip pagerTitleStrip2 = PagerTitleStrip.this;
            float f = pagerTitleStrip2.mLastKnownPositionOffset;
            if (f < 0.0f) {
                f = 0.0f;
            }
            ViewPager viewPager3 = pagerTitleStrip2.mPager;
            Objects.requireNonNull(viewPager3);
            pagerTitleStrip2.updateTextPositions(viewPager3.mCurItem, f, true);
        }

        public final void onPageScrolled(int i, float f, int i2) {
            if (f > 0.5f) {
                i++;
            }
            PagerTitleStrip.this.updateTextPositions(i, f, false);
        }

        public final void onPageSelected(int i) {
            if (this.mScrollState == 0) {
                PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
                ViewPager viewPager = pagerTitleStrip.mPager;
                Objects.requireNonNull(viewPager);
                int i2 = viewPager.mCurItem;
                ViewPager viewPager2 = PagerTitleStrip.this.mPager;
                Objects.requireNonNull(viewPager2);
                pagerTitleStrip.updateText(i2, viewPager2.mAdapter);
                PagerTitleStrip pagerTitleStrip2 = PagerTitleStrip.this;
                float f = pagerTitleStrip2.mLastKnownPositionOffset;
                if (f < 0.0f) {
                    f = 0.0f;
                }
                ViewPager viewPager3 = pagerTitleStrip2.mPager;
                Objects.requireNonNull(viewPager3);
                pagerTitleStrip2.updateTextPositions(viewPager3.mCurItem, f, true);
            }
        }

        public final void onPageScrollStateChanged(int i) {
            this.mScrollState = i;
        }
    }

    public final void updateText(int i, PagerAdapter pagerAdapter) {
        if (pagerAdapter != null) {
            int count = pagerAdapter.getCount();
        }
        this.mUpdatingText = true;
        this.mPrevText.setText((CharSequence) null);
        this.mCurrText.setText((CharSequence) null);
        int i2 = i + 1;
        this.mNextText.setText((CharSequence) null);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * 0.8f)), Integer.MIN_VALUE);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.mPrevText.measure(makeMeasureSpec, makeMeasureSpec2);
        this.mCurrText.measure(makeMeasureSpec, makeMeasureSpec2);
        this.mNextText.measure(makeMeasureSpec, makeMeasureSpec2);
        this.mLastKnownCurrentPage = i;
        if (!this.mUpdatingPositions) {
            updateTextPositions(i, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    public static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        public Locale mLocale;

        public SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        public final CharSequence getTransformation(CharSequence charSequence, View view) {
            CharSequence transformation = super.getTransformation(charSequence, view);
            if (transformation != null) {
                return transformation.toString().toUpperCase(this.mLocale);
            }
            return null;
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mPager != null) {
            float f = this.mLastKnownPositionOffset;
            if (f < 0.0f) {
                f = 0.0f;
            }
            updateTextPositions(this.mLastKnownCurrentPage, f, true);
        }
    }

    public final void requestLayout() {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    public final void updateAdapter(PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
        if (pagerAdapter != null) {
            pagerAdapter.mObservable.unregisterObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (pagerAdapter2 != null) {
            pagerAdapter2.mObservable.registerObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<>(pagerAdapter2);
        }
        ViewPager viewPager = this.mPager;
        if (viewPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            updateText(viewPager.mCurItem, pagerAdapter2);
            requestLayout();
        }
    }

    public void updateTextPositions(int i, float f, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = i;
        float f2 = f;
        if (i6 != this.mLastKnownCurrentPage) {
            ViewPager viewPager = this.mPager;
            Objects.requireNonNull(viewPager);
            updateText(i6, viewPager.mAdapter);
        } else if (!z && f2 == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        int measuredWidth = this.mPrevText.getMeasuredWidth();
        int measuredWidth2 = this.mCurrText.getMeasuredWidth();
        int measuredWidth3 = this.mNextText.getMeasuredWidth();
        int i7 = measuredWidth2 / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i8 = paddingRight + i7;
        int i9 = (width - (paddingLeft + i7)) - i8;
        float f3 = 0.5f + f2;
        if (f3 > 1.0f) {
            f3 -= 1.0f;
        }
        int i10 = ((width - i8) - ((int) (((float) i9) * f3))) - i7;
        int i11 = measuredWidth2 + i10;
        int baseline = this.mPrevText.getBaseline();
        int baseline2 = this.mCurrText.getBaseline();
        int baseline3 = this.mNextText.getBaseline();
        int max = Math.max(Math.max(baseline, baseline2), baseline3);
        int i12 = max - baseline;
        int i13 = max - baseline2;
        int i14 = max - baseline3;
        int i15 = measuredWidth3;
        int max2 = Math.max(Math.max(this.mPrevText.getMeasuredHeight() + i12, this.mCurrText.getMeasuredHeight() + i13), this.mNextText.getMeasuredHeight() + i14);
        int i16 = this.mGravity & 112;
        if (i16 == 16) {
            i5 = (((height - paddingTop) - paddingBottom) - max2) / 2;
        } else if (i16 != 80) {
            i4 = i12 + paddingTop;
            i2 = i13 + paddingTop;
            i3 = paddingTop + i14;
            TextView textView = this.mCurrText;
            textView.layout(i10, i2, i11, textView.getMeasuredHeight() + i2);
            int min = Math.min(paddingLeft, (i10 - this.mScaledTextSpacing) - measuredWidth);
            TextView textView2 = this.mPrevText;
            textView2.layout(min, i4, measuredWidth + min, textView2.getMeasuredHeight() + i4);
            int max3 = Math.max((width - paddingRight) - i15, i11 + this.mScaledTextSpacing);
            TextView textView3 = this.mNextText;
            textView3.layout(max3, i3, max3 + i15, textView3.getMeasuredHeight() + i3);
            this.mLastKnownPositionOffset = f;
            this.mUpdatingPositions = false;
        } else {
            i5 = (height - paddingBottom) - max2;
        }
        i4 = i12 + i5;
        i2 = i13 + i5;
        i3 = i5 + i14;
        TextView textView4 = this.mCurrText;
        textView4.layout(i10, i2, i11, textView4.getMeasuredHeight() + i2);
        int min2 = Math.min(paddingLeft, (i10 - this.mScaledTextSpacing) - measuredWidth);
        TextView textView22 = this.mPrevText;
        textView22.layout(min2, i4, measuredWidth + min2, textView22.getMeasuredHeight() + i4);
        int max32 = Math.max((width - paddingRight) - i15, i11 + this.mScaledTextSpacing);
        TextView textView32 = this.mNextText;
        textView32.layout(max32, i3, max32 + i15, textView32.getMeasuredHeight() + i3);
        this.mLastKnownPositionOffset = f;
        this.mUpdatingPositions = false;
    }

    public PagerTitleStrip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TextView textView = new TextView(context);
        this.mPrevText = textView;
        addView(textView);
        TextView textView2 = new TextView(context);
        this.mCurrText = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context);
        this.mNextText = textView3;
        addView(textView3);
        int[] iArr = ATTRS;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        boolean z = false;
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId != 0) {
            this.mPrevText.setTextAppearance(resourceId);
            this.mCurrText.setTextAppearance(resourceId);
            this.mNextText.setTextAppearance(resourceId);
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(1, 0);
        if (dimensionPixelSize != 0) {
            float f = (float) dimensionPixelSize;
            this.mPrevText.setTextSize(0, f);
            this.mCurrText.setTextSize(0, f);
            this.mNextText.setTextSize(0, f);
        }
        if (obtainStyledAttributes.hasValue(2)) {
            int color = obtainStyledAttributes.getColor(2, 0);
            this.mPrevText.setTextColor(color);
            this.mCurrText.setTextColor(color);
            this.mNextText.setTextColor(color);
        }
        this.mGravity = obtainStyledAttributes.getInteger(3, 80);
        obtainStyledAttributes.recycle();
        int defaultColor = this.mCurrText.getTextColors().getDefaultColor();
        this.mTextColor = defaultColor;
        int i = (defaultColor & 16777215) | ((((int) 153.0f) & 255) << 24);
        this.mPrevText.setTextColor(i);
        this.mNextText.setTextColor(i);
        this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
        this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
        this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
        if (resourceId != 0) {
            TypedArray obtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, TEXT_ATTRS);
            z = obtainStyledAttributes2.getBoolean(0, false);
            obtainStyledAttributes2.recycle();
        }
        if (z) {
            TextView textView4 = this.mPrevText;
            textView4.setTransformationMethod(new SingleLineAllCapsTransform(textView4.getContext()));
            TextView textView5 = this.mCurrText;
            textView5.setTransformationMethod(new SingleLineAllCapsTransform(textView5.getContext()));
            TextView textView6 = this.mNextText;
            textView6.setTransformationMethod(new SingleLineAllCapsTransform(textView6.getContext()));
        } else {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int) (context.getResources().getDisplayMetrics().density * 16.0f);
    }

    public int getMinHeight() {
        Drawable background = getBackground();
        if (background != null) {
            return background.getIntrinsicHeight();
        }
        return 0;
    }

    public final void onAttachedToWindow() {
        PagerAdapter pagerAdapter;
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof ViewPager) {
            ViewPager viewPager = (ViewPager) parent;
            Objects.requireNonNull(viewPager);
            PagerAdapter pagerAdapter2 = viewPager.mAdapter;
            PageListener pageListener = this.mPageListener;
            viewPager.mInternalPageChangeListener = pageListener;
            if (viewPager.mAdapterChangeListeners == null) {
                viewPager.mAdapterChangeListeners = new ArrayList();
            }
            viewPager.mAdapterChangeListeners.add(pageListener);
            this.mPager = viewPager;
            WeakReference<PagerAdapter> weakReference = this.mWatchingAdapter;
            if (weakReference != null) {
                pagerAdapter = weakReference.get();
            } else {
                pagerAdapter = null;
            }
            updateAdapter(pagerAdapter, pagerAdapter2);
            return;
        }
        throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.mPager;
        if (viewPager != null) {
            updateAdapter(viewPager.mAdapter, (PagerAdapter) null);
            ViewPager viewPager2 = this.mPager;
            Objects.requireNonNull(viewPager2);
            viewPager2.mInternalPageChangeListener = null;
            ViewPager viewPager3 = this.mPager;
            PageListener pageListener = this.mPageListener;
            Objects.requireNonNull(viewPager3);
            ArrayList arrayList = viewPager3.mAdapterChangeListeners;
            if (arrayList != null) {
                arrayList.remove(pageListener);
            }
            this.mPager = null;
        }
    }

    public final void onMeasure(int i, int i2) {
        int i3;
        if (View.MeasureSpec.getMode(i) == 1073741824) {
            int paddingBottom = getPaddingBottom() + getPaddingTop();
            int childMeasureSpec = ViewGroup.getChildMeasureSpec(i2, paddingBottom, -2);
            int size = View.MeasureSpec.getSize(i);
            int childMeasureSpec2 = ViewGroup.getChildMeasureSpec(i, (int) (((float) size) * 0.2f), -2);
            this.mPrevText.measure(childMeasureSpec2, childMeasureSpec);
            this.mCurrText.measure(childMeasureSpec2, childMeasureSpec);
            this.mNextText.measure(childMeasureSpec2, childMeasureSpec);
            if (View.MeasureSpec.getMode(i2) == 1073741824) {
                i3 = View.MeasureSpec.getSize(i2);
            } else {
                i3 = Math.max(getMinHeight(), this.mCurrText.getMeasuredHeight() + paddingBottom);
            }
            setMeasuredDimension(size, View.resolveSizeAndState(i3, i2, this.mCurrText.getMeasuredState() << 16));
            return;
        }
        throw new IllegalStateException("Must measure with an exact width");
    }
}
