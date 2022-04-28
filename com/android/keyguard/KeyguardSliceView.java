package com.android.keyguard;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
import android.os.Trace;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import androidx.slice.SliceItem;
import androidx.slice.core.SliceQuery;
import androidx.slice.widget.RowContent;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.ColorUtils;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.util.wakelock.KeepAwakeAnimationListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class KeyguardSliceView extends LinearLayout {
    public Runnable mContentChangeListener;
    public float mDarkAmount = 0.0f;
    public boolean mHasHeader;
    public int mIconSize;
    public int mIconSizeWithHeader;
    public final LayoutTransition mLayoutTransition;
    public View.OnClickListener mOnClickListener;
    public Row mRow;
    public int mTextColor;
    @VisibleForTesting
    public TextView mTitle;

    public static class Row extends LinearLayout {
        public float mDarkAmount;
        public final KeepAwakeAnimationListener mKeepAwakeListener;
        public HashSet mKeyguardSliceTextViewSet;
        public LayoutTransition mLayoutTransition;

        public Row(Context context) {
            this(context, (AttributeSet) null);
        }

        public final boolean hasOverlappingRendering() {
            return false;
        }

        public Row(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public final void onFinishInflate() {
            LayoutTransition layoutTransition = new LayoutTransition();
            this.mLayoutTransition = layoutTransition;
            layoutTransition.setDuration(550);
            ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder((Object) null, new PropertyValuesHolder[]{PropertyValuesHolder.ofInt("left", new int[]{0, 1}), PropertyValuesHolder.ofInt("right", new int[]{0, 1})});
            this.mLayoutTransition.setAnimator(0, ofPropertyValuesHolder);
            this.mLayoutTransition.setAnimator(1, ofPropertyValuesHolder);
            LayoutTransition layoutTransition2 = this.mLayoutTransition;
            AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = Interpolators.ACCELERATE_DECELERATE;
            layoutTransition2.setInterpolator(0, accelerateDecelerateInterpolator);
            this.mLayoutTransition.setInterpolator(1, accelerateDecelerateInterpolator);
            this.mLayoutTransition.setStartDelay(0, 550);
            this.mLayoutTransition.setStartDelay(1, 550);
            this.mLayoutTransition.setAnimator(2, ObjectAnimator.ofFloat((Object) null, "alpha", new float[]{0.0f, 1.0f}));
            this.mLayoutTransition.setInterpolator(2, Interpolators.ALPHA_IN);
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", new float[]{1.0f, 0.0f});
            this.mLayoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
            this.mLayoutTransition.setDuration(3, 137);
            this.mLayoutTransition.setAnimator(3, ofFloat);
            this.mLayoutTransition.setAnimateParentHierarchy(false);
        }

        public Row(Context context, AttributeSet attributeSet, int i) {
            this(context, attributeSet, i, 0);
        }

        public final void addView(View view, int i) {
            super.addView(view, i);
            if (view instanceof KeyguardSliceTextView) {
                this.mKeyguardSliceTextViewSet.add((KeyguardSliceTextView) view);
            }
        }

        public final void onMeasure(int i, int i2) {
            View.MeasureSpec.getSize(i);
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt instanceof KeyguardSliceTextView) {
                    ((KeyguardSliceTextView) childAt).setMaxWidth(Integer.MAX_VALUE);
                }
            }
            super.onMeasure(i, i2);
        }

        public final void onVisibilityAggregated(boolean z) {
            LayoutTransition layoutTransition;
            super.onVisibilityAggregated(z);
            if (z) {
                layoutTransition = this.mLayoutTransition;
            } else {
                layoutTransition = null;
            }
            setLayoutTransition(layoutTransition);
        }

        public final void removeView(View view) {
            super.removeView(view);
            if (view instanceof KeyguardSliceTextView) {
                this.mKeyguardSliceTextViewSet.remove((KeyguardSliceTextView) view);
            }
        }

        public Row(Context context, AttributeSet attributeSet, int i, int i2) {
            super(context, attributeSet, i, i2);
            this.mKeyguardSliceTextViewSet = new HashSet();
            this.mKeepAwakeListener = new KeepAwakeAnimationListener(this.mContext);
        }
    }

    @VisibleForTesting
    public static class KeyguardSliceTextView extends TextView {
        public KeyguardSliceTextView(Context context) {
            super(context, (AttributeSet) null, 0, 2132017919);
            updatePadding();
            setEllipsize(TextUtils.TruncateAt.END);
        }

        public final void setCompoundDrawablesRelative(Drawable drawable, Drawable drawable2, Drawable drawable3, Drawable drawable4) {
            super.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
            int currentTextColor = getCurrentTextColor();
            for (Drawable drawable5 : getCompoundDrawables()) {
                if (drawable5 != null) {
                    drawable5.setTint(currentTextColor);
                }
            }
            updatePadding();
        }

        public final void setText(CharSequence charSequence, TextView.BufferType bufferType) {
            super.setText(charSequence, bufferType);
            updatePadding();
        }

        public final void setTextColor(int i) {
            super.setTextColor(i);
            int currentTextColor = getCurrentTextColor();
            for (Drawable drawable : getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.setTint(currentTextColor);
                }
            }
        }

        public final void updatePadding() {
            int i;
            boolean z = !TextUtils.isEmpty(getText());
            int dimension = ((int) getContext().getResources().getDimension(C1777R.dimen.widget_horizontal_padding)) / 2;
            if (z) {
                i = dimension;
            } else {
                i = 0;
            }
            setPadding(0, dimension, 0, i);
            setCompoundDrawablePadding((int) this.mContext.getResources().getDimension(C1777R.dimen.widget_icon_padding));
        }
    }

    @VisibleForTesting
    public int getTextColor() {
        return ColorUtils.blendARGB(this.mTextColor, -1, this.mDarkAmount);
    }

    public final void onDensityOrFontScaleChanged() {
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(C1777R.dimen.widget_icon_size);
        this.mIconSizeWithHeader = (int) this.mContext.getResources().getDimension(C1777R.dimen.header_icon_size);
        for (int i = 0; i < this.mRow.getChildCount(); i++) {
            View childAt = this.mRow.getChildAt(i);
            if (childAt instanceof KeyguardSliceTextView) {
                KeyguardSliceTextView keyguardSliceTextView = (KeyguardSliceTextView) childAt;
                Objects.requireNonNull(keyguardSliceTextView);
                keyguardSliceTextView.updatePadding();
            }
        }
    }

    public final void setOnClickListener(View.OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
        this.mTitle.setOnClickListener(onClickListener);
    }

    @VisibleForTesting
    public void setTextColor(int i) {
        this.mTextColor = i;
        updateTextColors();
    }

    public final HashMap showSlice(RowContent rowContent, List list) {
        boolean z;
        PendingIntent pendingIntent;
        CharSequence charSequence;
        CharSequence charSequence2;
        Drawable drawable;
        boolean z2;
        int i;
        CharSequence charSequence3;
        Trace.beginSection("KeyguardSliceView#showSlice");
        int i2 = 0;
        if (rowContent != null) {
            z = true;
        } else {
            z = false;
        }
        this.mHasHeader = z;
        HashMap hashMap = new HashMap();
        int i3 = 8;
        if (!this.mHasHeader) {
            this.mTitle.setVisibility(8);
        } else {
            this.mTitle.setVisibility(0);
            Objects.requireNonNull(rowContent);
            SliceItem sliceItem = rowContent.mTitleItem;
            if (sliceItem != null) {
                charSequence3 = (CharSequence) sliceItem.mObj;
            } else {
                charSequence3 = null;
            }
            this.mTitle.setText(charSequence3);
            SliceItem sliceItem2 = rowContent.mPrimaryAction;
            if (!(sliceItem2 == null || sliceItem2.getAction() == null)) {
                hashMap.put(this.mTitle, rowContent.mPrimaryAction.getAction());
            }
        }
        int size = list.size();
        int textColor = getTextColor();
        Row row = this.mRow;
        if (size > 0) {
            i3 = 0;
        }
        row.setVisibility(i3);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mRow.getLayoutParams();
        layoutParams.gravity = 8388611;
        this.mRow.setLayoutParams(layoutParams);
        for (int i4 = this.mHasHeader; i4 < size; i4++) {
            RowContent rowContent2 = (RowContent) list.get(i4);
            Objects.requireNonNull(rowContent2);
            SliceItem sliceItem3 = rowContent2.mSliceItem;
            Uri uri = sliceItem3.getSlice().getUri();
            KeyguardSliceTextView keyguardSliceTextView = (KeyguardSliceTextView) this.mRow.findViewWithTag(uri);
            if (keyguardSliceTextView == null) {
                keyguardSliceTextView = new KeyguardSliceTextView(this.mContext);
                keyguardSliceTextView.setTextColor(textColor);
                keyguardSliceTextView.setTag(uri);
                this.mRow.addView(keyguardSliceTextView, i4 - (this.mHasHeader ? 1 : 0));
            }
            SliceItem sliceItem4 = rowContent2.mPrimaryAction;
            if (sliceItem4 != null) {
                pendingIntent = sliceItem4.getAction();
            } else {
                pendingIntent = null;
            }
            hashMap.put(keyguardSliceTextView, pendingIntent);
            SliceItem sliceItem5 = rowContent2.mTitleItem;
            if (sliceItem5 == null) {
                charSequence = null;
            } else {
                charSequence = (CharSequence) sliceItem5.mObj;
            }
            keyguardSliceTextView.setText(charSequence);
            SliceItem sliceItem6 = rowContent2.mContentDescr;
            if (sliceItem6 != null) {
                charSequence2 = (CharSequence) sliceItem6.mObj;
            } else {
                charSequence2 = null;
            }
            keyguardSliceTextView.setContentDescription(charSequence2);
            SliceItem find = SliceQuery.find(sliceItem3.getSlice(), "image", (String[]) null, (String[]) null);
            if (find != null) {
                if (this.mHasHeader) {
                    i = this.mIconSizeWithHeader;
                } else {
                    i = this.mIconSize;
                }
                drawable = ((IconCompat) find.mObj).loadDrawable(this.mContext);
                if (drawable != null) {
                    if (drawable instanceof InsetDrawable) {
                        drawable = ((InsetDrawable) drawable).getDrawable();
                    }
                    drawable.setBounds(0, 0, Math.max((int) ((((float) drawable.getIntrinsicWidth()) / ((float) drawable.getIntrinsicHeight())) * ((float) i)), 1), i);
                }
            } else {
                drawable = null;
            }
            keyguardSliceTextView.setCompoundDrawablesRelative(drawable, (Drawable) null, (Drawable) null, (Drawable) null);
            keyguardSliceTextView.setOnClickListener(this.mOnClickListener);
            if (pendingIntent != null) {
                z2 = true;
            } else {
                z2 = false;
            }
            keyguardSliceTextView.setClickable(z2);
        }
        while (i2 < this.mRow.getChildCount()) {
            View childAt = this.mRow.getChildAt(i2);
            if (!hashMap.containsKey(childAt)) {
                this.mRow.removeView(childAt);
                i2--;
            }
            i2++;
        }
        Runnable runnable = this.mContentChangeListener;
        if (runnable != null) {
            runnable.run();
        }
        Trace.endSection();
        return hashMap;
    }

    public KeyguardSliceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        context.getResources();
        LayoutTransition layoutTransition = new LayoutTransition();
        this.mLayoutTransition = layoutTransition;
        layoutTransition.setStagger(0, 275);
        layoutTransition.setDuration(2, 550);
        layoutTransition.setDuration(3, 275);
        layoutTransition.disableTransitionType(0);
        layoutTransition.disableTransitionType(1);
        layoutTransition.setInterpolator(2, Interpolators.FAST_OUT_SLOW_IN);
        layoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
        layoutTransition.setAnimateParentHierarchy(false);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mTitle = (TextView) findViewById(C1777R.C1779id.title);
        this.mRow = (Row) findViewById(C1777R.C1779id.row);
        this.mTextColor = Utils.getColorAttrDefaultColor(this.mContext, C1777R.attr.wallpaperTextColor);
        this.mIconSize = (int) this.mContext.getResources().getDimension(C1777R.dimen.widget_icon_size);
        this.mIconSizeWithHeader = (int) this.mContext.getResources().getDimension(C1777R.dimen.header_icon_size);
        this.mTitle.setBreakStrategy(2);
    }

    public final void onVisibilityAggregated(boolean z) {
        LayoutTransition layoutTransition;
        super.onVisibilityAggregated(z);
        if (z) {
            layoutTransition = this.mLayoutTransition;
        } else {
            layoutTransition = null;
        }
        setLayoutTransition(layoutTransition);
    }

    public final void updateTextColors() {
        int textColor = getTextColor();
        this.mTitle.setTextColor(textColor);
        int childCount = this.mRow.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mRow.getChildAt(i);
            if (childAt instanceof TextView) {
                ((TextView) childAt).setTextColor(textColor);
            }
        }
    }
}
