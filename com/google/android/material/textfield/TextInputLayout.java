package com.google.android.material.textfield;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.core.content.ContextCompat;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.resources.CancelableFontCallback;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.ShapeAppearanceModel;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.WeakHashMap;

public class TextInputLayout extends LinearLayout {
    public ValueAnimator animator;
    public MaterialShapeDrawable boxBackground;
    public int boxBackgroundColor;
    public int boxBackgroundMode;
    public int boxCollapsedPaddingTopPx;
    public final int boxLabelCutoutPaddingPx;
    public int boxStrokeColor;
    public int boxStrokeWidthDefaultPx;
    public int boxStrokeWidthFocusedPx;
    public int boxStrokeWidthPx;
    public MaterialShapeDrawable boxUnderline;
    public final CollapsingTextHelper collapsingTextHelper;
    public boolean counterEnabled;
    public int counterMaxLength;
    public int counterOverflowTextAppearance;
    public ColorStateList counterOverflowTextColor;
    public boolean counterOverflowed;
    public int counterTextAppearance;
    public ColorStateList counterTextColor;
    public AppCompatTextView counterView;
    public int defaultFilledBackgroundColor;
    public ColorStateList defaultHintTextColor;
    public int defaultStrokeColor;
    public int disabledColor;
    public int disabledFilledBackgroundColor;
    public EditText editText;
    public final LinkedHashSet<OnEditTextAttachedListener> editTextAttachedListeners;
    public ColorDrawable endDummyDrawable;
    public int endDummyDrawableWidth;
    public final LinkedHashSet<OnEndIconChangedListener> endIconChangedListeners;
    public final SparseArray<EndIconDelegate> endIconDelegates;
    public final FrameLayout endIconFrame;
    public int endIconMode;
    public View.OnLongClickListener endIconOnLongClickListener;
    public ColorStateList endIconTintList;
    public PorterDuff.Mode endIconTintMode;
    public final CheckableImageButton endIconView;
    public final LinearLayout endLayout;
    public ColorStateList errorIconTintList;
    public final CheckableImageButton errorIconView;
    public boolean expandedHintEnabled;
    public int focusedFilledBackgroundColor;
    public int focusedStrokeColor;
    public ColorStateList focusedTextColor;
    public boolean hasEndIconTintList;
    public boolean hasEndIconTintMode;
    public boolean hasStartIconTintList;
    public boolean hasStartIconTintMode;
    public CharSequence hint;
    public boolean hintAnimationEnabled;
    public boolean hintEnabled;
    public boolean hintExpanded;
    public int hoveredFilledBackgroundColor;
    public int hoveredStrokeColor;
    public boolean inDrawableStateChanged;
    public final IndicatorViewController indicatorViewController;
    public final FrameLayout inputFrame;
    public boolean isProvidingHint;
    public int maxWidth;
    public int minWidth;
    public Drawable originalEditTextEndDrawable;
    public CharSequence originalHint;
    public boolean placeholderEnabled;
    public Fade placeholderFadeIn;
    public Fade placeholderFadeOut;
    public CharSequence placeholderText;
    public int placeholderTextAppearance;
    public ColorStateList placeholderTextColor;
    public AppCompatTextView placeholderTextView;
    public CharSequence prefixText;
    public final AppCompatTextView prefixTextView;
    public boolean restoringSavedState;
    public ShapeAppearanceModel shapeAppearanceModel;
    public ColorDrawable startDummyDrawable;
    public int startDummyDrawableWidth;
    public View.OnLongClickListener startIconOnLongClickListener;
    public ColorStateList startIconTintList;
    public PorterDuff.Mode startIconTintMode;
    public final CheckableImageButton startIconView;
    public final LinearLayout startLayout;
    public ColorStateList strokeErrorColor;
    public CharSequence suffixText;
    public final AppCompatTextView suffixTextView;
    public final Rect tmpBoundsRect;
    public final Rect tmpRect;
    public final RectF tmpRectF;

    public interface OnEditTextAttachedListener {
        void onEditTextAttached(TextInputLayout textInputLayout);
    }

    public interface OnEndIconChangedListener {
        void onEndIconChanged(TextInputLayout textInputLayout, int i);
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public CharSequence error;
        public CharSequence helperText;
        public CharSequence hintText;
        public boolean isEndIconChecked;
        public CharSequence placeholderText;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.isEndIconChecked = parcel.readInt() != 1 ? false : true;
            this.hintText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.helperText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.placeholderText = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("TextInputLayout.SavedState{");
            m.append(Integer.toHexString(System.identityHashCode(this)));
            m.append(" error=");
            m.append(this.error);
            m.append(" hint=");
            m.append(this.hintText);
            m.append(" helperText=");
            m.append(this.helperText);
            m.append(" placeholderText=");
            m.append(this.placeholderText);
            m.append("}");
            return m.toString();
        }

        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            TextUtils.writeToParcel(this.error, parcel, i);
            parcel.writeInt(this.isEndIconChecked ? 1 : 0);
            TextUtils.writeToParcel(this.hintText, parcel, i);
            TextUtils.writeToParcel(this.helperText, parcel, i);
            TextUtils.writeToParcel(this.placeholderText, parcel, i);
        }
    }

    public TextInputLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public final void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.restoringSavedState = false;
    }

    public final void setTextAppearanceCompatWithErrorFallback(TextView textView, int i) {
        boolean z = true;
        try {
            textView.setTextAppearance(i);
            if (textView.getTextColors().getDefaultColor() != -65281) {
                z = false;
            }
        } catch (Exception unused) {
        }
        if (z) {
            textView.setTextAppearance(2132017816);
            Context context = getContext();
            Object obj = ContextCompat.sLock;
            textView.setTextColor(context.getColor(C1777R.color.design_error));
        }
    }

    public static class AccessibilityDelegate extends AccessibilityDelegateCompat {
        public final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout textInputLayout) {
            this.layout = textInputLayout;
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            Editable editable;
            CharSequence charSequence;
            boolean z;
            String str;
            AppCompatTextView appCompatTextView;
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            TextInputLayout textInputLayout = this.layout;
            Objects.requireNonNull(textInputLayout);
            EditText editText = textInputLayout.editText;
            CharSequence charSequence2 = null;
            if (editText != null) {
                editable = editText.getText();
            } else {
                editable = null;
            }
            CharSequence hint = this.layout.getHint();
            CharSequence error = this.layout.getError();
            TextInputLayout textInputLayout2 = this.layout;
            Objects.requireNonNull(textInputLayout2);
            if (textInputLayout2.placeholderEnabled) {
                charSequence = textInputLayout2.placeholderText;
            } else {
                charSequence = null;
            }
            TextInputLayout textInputLayout3 = this.layout;
            Objects.requireNonNull(textInputLayout3);
            int i = textInputLayout3.counterMaxLength;
            TextInputLayout textInputLayout4 = this.layout;
            Objects.requireNonNull(textInputLayout4);
            if (textInputLayout4.counterEnabled && textInputLayout4.counterOverflowed && (appCompatTextView = textInputLayout4.counterView) != null) {
                charSequence2 = appCompatTextView.getContentDescription();
            }
            boolean z2 = !TextUtils.isEmpty(editable);
            boolean z3 = !TextUtils.isEmpty(hint);
            boolean z4 = !this.layout.isHintExpanded();
            boolean z5 = !TextUtils.isEmpty(error);
            if (z5 || !TextUtils.isEmpty(charSequence2)) {
                z = true;
            } else {
                z = false;
            }
            if (z3) {
                str = hint.toString();
            } else {
                str = "";
            }
            if (z2) {
                accessibilityNodeInfoCompat.mInfo.setText(editable);
            } else if (!TextUtils.isEmpty(str)) {
                accessibilityNodeInfoCompat.mInfo.setText(str);
                if (z4 && charSequence != null) {
                    accessibilityNodeInfoCompat.mInfo.setText(str + ", " + charSequence);
                }
            } else if (charSequence != null) {
                accessibilityNodeInfoCompat.mInfo.setText(charSequence);
            }
            if (!TextUtils.isEmpty(str)) {
                accessibilityNodeInfoCompat.mInfo.setHintText(str);
                accessibilityNodeInfoCompat.mInfo.setShowingHintText(!z2);
            }
            if (editable == null || editable.length() != i) {
                i = -1;
            }
            accessibilityNodeInfoCompat.mInfo.setMaxTextLength(i);
            if (z) {
                if (!z5) {
                    error = charSequence2;
                }
                accessibilityNodeInfoCompat.mInfo.setError(error);
            }
            if (editText != null) {
                editText.setLabelFor(C1777R.C1779id.textinput_helper_text);
            }
        }
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.textInputStyle);
    }

    public static void setIconClickable(CheckableImageButton checkableImageButton, View.OnLongClickListener onLongClickListener) {
        boolean z;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean hasOnClickListeners = ViewCompat.Api15Impl.hasOnClickListeners(checkableImageButton);
        boolean z2 = false;
        int i = 1;
        if (onLongClickListener != null) {
            z = true;
        } else {
            z = false;
        }
        if (hasOnClickListeners || z) {
            z2 = true;
        }
        checkableImageButton.setFocusable(z2);
        checkableImageButton.setClickable(hasOnClickListeners);
        checkableImageButton.pressable = hasOnClickListeners;
        checkableImageButton.setLongClickable(z);
        if (!z2) {
            i = 2;
        }
        ViewCompat.Api16Impl.setImportantForAccessibility(checkableImageButton, i);
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        boolean z;
        boolean z2;
        if (view instanceof EditText) {
            FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
            layoutParams2.gravity = (layoutParams2.gravity & -113) | 16;
            this.inputFrame.addView(view, layoutParams2);
            this.inputFrame.setLayoutParams(layoutParams);
            updateInputLayoutMargins();
            EditText editText2 = (EditText) view;
            if (this.editText == null) {
                if (this.endIconMode != 3 && !(editText2 instanceof TextInputEditText)) {
                    Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
                }
                this.editText = editText2;
                int i2 = this.minWidth;
                this.minWidth = i2;
                if (!(editText2 == null || i2 == -1)) {
                    editText2.setMinWidth(i2);
                }
                int i3 = this.maxWidth;
                this.maxWidth = i3;
                EditText editText3 = this.editText;
                if (!(editText3 == null || i3 == -1)) {
                    editText3.setMaxWidth(i3);
                }
                onApplyBoxBackgroundMode();
                AccessibilityDelegate accessibilityDelegate = new AccessibilityDelegate(this);
                EditText editText4 = this.editText;
                if (editText4 != null) {
                    ViewCompat.setAccessibilityDelegate(editText4, accessibilityDelegate);
                }
                CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
                Typeface typeface = this.editText.getTypeface();
                Objects.requireNonNull(collapsingTextHelper2);
                CancelableFontCallback cancelableFontCallback = collapsingTextHelper2.collapsedFontCallback;
                if (cancelableFontCallback != null) {
                    cancelableFontCallback.cancelled = true;
                }
                if (collapsingTextHelper2.collapsedTypeface != typeface) {
                    collapsingTextHelper2.collapsedTypeface = typeface;
                    z = true;
                } else {
                    z = false;
                }
                CancelableFontCallback cancelableFontCallback2 = collapsingTextHelper2.expandedFontCallback;
                if (cancelableFontCallback2 != null) {
                    cancelableFontCallback2.cancelled = true;
                }
                if (collapsingTextHelper2.expandedTypeface != typeface) {
                    collapsingTextHelper2.expandedTypeface = typeface;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (z || z2) {
                    collapsingTextHelper2.recalculate(false);
                }
                CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
                float textSize = this.editText.getTextSize();
                Objects.requireNonNull(collapsingTextHelper3);
                if (collapsingTextHelper3.expandedTextSize != textSize) {
                    collapsingTextHelper3.expandedTextSize = textSize;
                    collapsingTextHelper3.recalculate(false);
                }
                int gravity = this.editText.getGravity();
                CollapsingTextHelper collapsingTextHelper4 = this.collapsingTextHelper;
                int i4 = (gravity & -113) | 48;
                Objects.requireNonNull(collapsingTextHelper4);
                if (collapsingTextHelper4.collapsedTextGravity != i4) {
                    collapsingTextHelper4.collapsedTextGravity = i4;
                    collapsingTextHelper4.recalculate(false);
                }
                CollapsingTextHelper collapsingTextHelper5 = this.collapsingTextHelper;
                Objects.requireNonNull(collapsingTextHelper5);
                if (collapsingTextHelper5.expandedTextGravity != gravity) {
                    collapsingTextHelper5.expandedTextGravity = gravity;
                    collapsingTextHelper5.recalculate(false);
                }
                this.editText.addTextChangedListener(new TextWatcher() {
                    public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    }

                    public final void afterTextChanged(Editable editable) {
                        TextInputLayout textInputLayout = TextInputLayout.this;
                        Objects.requireNonNull(textInputLayout);
                        textInputLayout.updateLabelState(!textInputLayout.restoringSavedState, false);
                        TextInputLayout textInputLayout2 = TextInputLayout.this;
                        if (textInputLayout2.counterEnabled) {
                            textInputLayout2.updateCounter(editable.length());
                        }
                        TextInputLayout textInputLayout3 = TextInputLayout.this;
                        if (textInputLayout3.placeholderEnabled) {
                            textInputLayout3.updatePlaceholderText(editable.length());
                        }
                    }
                });
                if (this.defaultHintTextColor == null) {
                    this.defaultHintTextColor = this.editText.getHintTextColors();
                }
                if (this.hintEnabled) {
                    if (TextUtils.isEmpty(this.hint)) {
                        CharSequence hint2 = this.editText.getHint();
                        this.originalHint = hint2;
                        setHint(hint2);
                        this.editText.setHint((CharSequence) null);
                    }
                    this.isProvidingHint = true;
                }
                if (this.counterView != null) {
                    updateCounter(this.editText.getText().length());
                }
                updateEditTextBackground();
                this.indicatorViewController.adjustIndicatorPadding();
                this.startLayout.bringToFront();
                this.endLayout.bringToFront();
                this.endIconFrame.bringToFront();
                this.errorIconView.bringToFront();
                Iterator<OnEditTextAttachedListener> it = this.editTextAttachedListeners.iterator();
                while (it.hasNext()) {
                    it.next().onEditTextAttached(this);
                }
                updatePrefixTextViewPadding();
                updateSuffixTextViewPadding();
                if (!isEnabled()) {
                    editText2.setEnabled(false);
                }
                updateLabelState(false, true);
                return;
            }
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        super.addView(view, i, layoutParams);
    }

    public void animateToExpansionFraction(float f) {
        CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
        Objects.requireNonNull(collapsingTextHelper2);
        if (collapsingTextHelper2.expandedFraction != f) {
            if (this.animator == null) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.animator = valueAnimator;
                valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                this.animator.setDuration(167);
                this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    }
                });
            }
            ValueAnimator valueAnimator2 = this.animator;
            CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
            Objects.requireNonNull(collapsingTextHelper3);
            valueAnimator2.setFloatValues(new float[]{collapsingTextHelper3.expandedFraction, f});
            this.animator.start();
        }
    }

    public final void applyEndIconTint() {
        applyIconTint(this.endIconView, this.hasEndIconTintList, this.endIconTintList, this.hasEndIconTintMode, this.endIconTintMode);
    }

    public final int calculateLabelMarginTop() {
        float collapsedTextHeight;
        if (!this.hintEnabled) {
            return 0;
        }
        int i = this.boxBackgroundMode;
        if (i == 0 || i == 1) {
            collapsedTextHeight = this.collapsingTextHelper.getCollapsedTextHeight();
        } else if (i != 2) {
            return 0;
        } else {
            collapsedTextHeight = this.collapsingTextHelper.getCollapsedTextHeight() / 2.0f;
        }
        return (int) collapsedTextHeight;
    }

    public final boolean cutoutEnabled() {
        if (!this.hintEnabled || TextUtils.isEmpty(this.hint) || !(this.boxBackground instanceof CutoutDrawable)) {
            return false;
        }
        return true;
    }

    @TargetApi(26)
    public final void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        EditText editText2 = this.editText;
        if (editText2 == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i);
            return;
        }
        if (this.originalHint != null) {
            boolean z = this.isProvidingHint;
            this.isProvidingHint = false;
            CharSequence hint2 = editText2.getHint();
            this.editText.setHint(this.originalHint);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, i);
            } finally {
                this.editText.setHint(hint2);
                this.isProvidingHint = z;
            }
        } else {
            viewStructure.setAutofillId(getAutofillId());
            onProvideAutofillStructure(viewStructure, i);
            onProvideAutofillVirtualStructure(viewStructure, i);
            viewStructure.setChildCount(this.inputFrame.getChildCount());
            for (int i2 = 0; i2 < this.inputFrame.getChildCount(); i2++) {
                View childAt = this.inputFrame.getChildAt(i2);
                ViewStructure newChild = viewStructure.newChild(i2);
                childAt.dispatchProvideAutofillStructure(newChild, i);
                if (childAt == this.editText) {
                    newChild.setHint(getHint());
                }
            }
        }
    }

    public final void drawableStateChanged() {
        boolean z;
        if (!this.inDrawableStateChanged) {
            boolean z2 = true;
            this.inDrawableStateChanged = true;
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            if (collapsingTextHelper2 != null) {
                z = collapsingTextHelper2.setState(drawableState) | false;
            } else {
                z = false;
            }
            if (this.editText != null) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (!ViewCompat.Api19Impl.isLaidOut(this) || !isEnabled()) {
                    z2 = false;
                }
                updateLabelState(z2, false);
            }
            updateEditTextBackground();
            updateTextInputBoxState();
            if (z) {
                invalidate();
            }
            this.inDrawableStateChanged = false;
        }
    }

    public final int getBaseline() {
        EditText editText2 = this.editText;
        if (editText2 == null) {
            return super.getBaseline();
        }
        int baseline = editText2.getBaseline();
        return calculateLabelMarginTop() + getPaddingTop() + baseline;
    }

    public final EndIconDelegate getEndIconDelegate() {
        EndIconDelegate endIconDelegate = this.endIconDelegates.get(this.endIconMode);
        if (endIconDelegate != null) {
            return endIconDelegate;
        }
        return this.endIconDelegates.get(0);
    }

    public final CharSequence getError() {
        IndicatorViewController indicatorViewController2 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController2);
        if (!indicatorViewController2.errorEnabled) {
            return null;
        }
        IndicatorViewController indicatorViewController3 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController3);
        return indicatorViewController3.errorText;
    }

    public final int getErrorTextCurrentColor() {
        IndicatorViewController indicatorViewController2 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController2);
        AppCompatTextView appCompatTextView = indicatorViewController2.errorView;
        if (appCompatTextView != null) {
            return appCompatTextView.getCurrentTextColor();
        }
        return -1;
    }

    public final CharSequence getHint() {
        if (this.hintEnabled) {
            return this.hint;
        }
        return null;
    }

    public final float getHintCollapsedTextHeight() {
        return this.collapsingTextHelper.getCollapsedTextHeight();
    }

    public final int getHintCurrentCollapsedTextColor() {
        CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
        Objects.requireNonNull(collapsingTextHelper2);
        return collapsingTextHelper2.getCurrentColor(collapsingTextHelper2.collapsedTextColor);
    }

    public final boolean isEndIconVisible() {
        if (this.endIconFrame.getVisibility() == 0 && this.endIconView.getVisibility() == 0) {
            return true;
        }
        return false;
    }

    public final boolean isHelperTextDisplayed() {
        IndicatorViewController indicatorViewController2 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController2);
        if (indicatorViewController2.captionDisplayed != 2 || indicatorViewController2.helperTextView == null || TextUtils.isEmpty(indicatorViewController2.helperText)) {
            return false;
        }
        return true;
    }

    public final void onApplyBoxBackgroundMode() {
        boolean z;
        boolean z2;
        int i = this.boxBackgroundMode;
        boolean z3 = true;
        if (i == 0) {
            this.boxBackground = null;
            this.boxUnderline = null;
        } else if (i == 1) {
            this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.boxUnderline = new MaterialShapeDrawable();
        } else if (i == 2) {
            if (!this.hintEnabled || (this.boxBackground instanceof CutoutDrawable)) {
                this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            } else {
                this.boxBackground = new CutoutDrawable(this.shapeAppearanceModel);
            }
            this.boxUnderline = null;
        } else {
            throw new IllegalArgumentException(this.boxBackgroundMode + " is illegal; only @BoxBackgroundMode constants are supported.");
        }
        EditText editText2 = this.editText;
        if (editText2 == null || this.boxBackground == null || editText2.getBackground() != null || this.boxBackgroundMode == 0) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            EditText editText3 = this.editText;
            MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(editText3, materialShapeDrawable);
        }
        updateTextInputBoxState();
        if (this.boxBackgroundMode == 1) {
            if (getContext().getResources().getConfiguration().fontScale >= 2.0f) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(C1777R.dimen.material_font_2_0_box_collapsed_padding_top);
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(C1777R.dimen.material_font_1_3_box_collapsed_padding_top);
            }
        }
        if (this.editText != null && this.boxBackgroundMode == 1) {
            if (getContext().getResources().getConfiguration().fontScale < 2.0f) {
                z3 = false;
            }
            if (z3) {
                EditText editText4 = this.editText;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(editText4, ViewCompat.Api17Impl.getPaddingStart(editText4), getResources().getDimensionPixelSize(C1777R.dimen.material_filled_edittext_font_2_0_padding_top), ViewCompat.Api17Impl.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(C1777R.dimen.material_filled_edittext_font_2_0_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                EditText editText5 = this.editText;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(editText5, ViewCompat.Api17Impl.getPaddingStart(editText5), getResources().getDimensionPixelSize(C1777R.dimen.material_filled_edittext_font_1_3_padding_top), ViewCompat.Api17Impl.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(C1777R.dimen.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        setError(savedState.error);
        if (savedState.isEndIconChecked) {
            this.endIconView.post(new Runnable() {
                public final void run() {
                    TextInputLayout.this.endIconView.performClick();
                    TextInputLayout.this.endIconView.jumpDrawablesToCurrentState();
                }
            });
        }
        setHint(savedState.hintText);
        setHelperText(savedState.helperText);
        setPlaceholderText(savedState.placeholderText);
        requestLayout();
    }

    public final void setEndIconContentDescription(CharSequence charSequence) {
        if (this.endIconView.getContentDescription() != charSequence) {
            this.endIconView.setContentDescription(charSequence);
        }
    }

    public final void setEndIconDrawable(int i) {
        Drawable drawable;
        if (i != 0) {
            drawable = AppCompatResources.getDrawable(getContext(), i);
        } else {
            drawable = null;
        }
        this.endIconView.setImageDrawable(drawable);
        if (drawable != null) {
            applyEndIconTint();
            refreshIconDrawableState(this.endIconView, this.endIconTintList);
        }
    }

    public final void setEndIconMode(int i) {
        boolean z;
        int i2 = this.endIconMode;
        this.endIconMode = i;
        Iterator<OnEndIconChangedListener> it = this.endIconChangedListeners.iterator();
        while (it.hasNext()) {
            it.next().onEndIconChanged(this, i2);
        }
        if (i != 0) {
            z = true;
        } else {
            z = false;
        }
        setEndIconVisible(z);
        if (getEndIconDelegate().isBoxBackgroundModeSupported(this.boxBackgroundMode)) {
            getEndIconDelegate().initialize();
            applyEndIconTint();
            return;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("The current box background mode ");
        m.append(this.boxBackgroundMode);
        m.append(" is not supported by the end icon mode ");
        m.append(i);
        throw new IllegalStateException(m.toString());
    }

    public final void setError(CharSequence charSequence) {
        IndicatorViewController indicatorViewController2 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController2);
        if (!indicatorViewController2.errorEnabled) {
            if (!TextUtils.isEmpty(charSequence)) {
                setErrorEnabled(true);
            } else {
                return;
            }
        }
        if (!TextUtils.isEmpty(charSequence)) {
            IndicatorViewController indicatorViewController3 = this.indicatorViewController;
            Objects.requireNonNull(indicatorViewController3);
            indicatorViewController3.cancelCaptionAnimator();
            indicatorViewController3.errorText = charSequence;
            indicatorViewController3.errorView.setText(charSequence);
            int i = indicatorViewController3.captionDisplayed;
            if (i != 1) {
                indicatorViewController3.captionToShow = 1;
            }
            indicatorViewController3.updateCaptionViewsVisibility(i, indicatorViewController3.captionToShow, indicatorViewController3.shouldAnimateCaptionView(indicatorViewController3.errorView, charSequence));
            return;
        }
        this.indicatorViewController.hideError();
    }

    public final void setErrorEnabled(boolean z) {
        IndicatorViewController indicatorViewController2 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController2);
        if (indicatorViewController2.errorEnabled != z) {
            indicatorViewController2.cancelCaptionAnimator();
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(indicatorViewController2.context);
                indicatorViewController2.errorView = appCompatTextView;
                appCompatTextView.setId(C1777R.C1779id.textinput_error);
                indicatorViewController2.errorView.setTextAlignment(5);
                int i = indicatorViewController2.errorTextAppearance;
                indicatorViewController2.errorTextAppearance = i;
                AppCompatTextView appCompatTextView2 = indicatorViewController2.errorView;
                if (appCompatTextView2 != null) {
                    indicatorViewController2.textInputView.setTextAppearanceCompatWithErrorFallback(appCompatTextView2, i);
                }
                ColorStateList colorStateList = indicatorViewController2.errorViewTextColor;
                indicatorViewController2.errorViewTextColor = colorStateList;
                AppCompatTextView appCompatTextView3 = indicatorViewController2.errorView;
                if (!(appCompatTextView3 == null || colorStateList == null)) {
                    appCompatTextView3.setTextColor(colorStateList);
                }
                CharSequence charSequence = indicatorViewController2.errorViewContentDescription;
                indicatorViewController2.errorViewContentDescription = charSequence;
                AppCompatTextView appCompatTextView4 = indicatorViewController2.errorView;
                if (appCompatTextView4 != null) {
                    appCompatTextView4.setContentDescription(charSequence);
                }
                indicatorViewController2.errorView.setVisibility(4);
                AppCompatTextView appCompatTextView5 = indicatorViewController2.errorView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api19Impl.setAccessibilityLiveRegion(appCompatTextView5, 1);
                indicatorViewController2.addIndicator(indicatorViewController2.errorView, 0);
            } else {
                indicatorViewController2.hideError();
                indicatorViewController2.removeIndicator(indicatorViewController2.errorView, 0);
                indicatorViewController2.errorView = null;
                indicatorViewController2.textInputView.updateEditTextBackground();
                indicatorViewController2.textInputView.updateTextInputBoxState();
            }
            indicatorViewController2.errorEnabled = z;
        }
    }

    public final void setErrorIconVisible(boolean z) {
        int i;
        CheckableImageButton checkableImageButton = this.errorIconView;
        boolean z2 = false;
        int i2 = 8;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        checkableImageButton.setVisibility(i);
        FrameLayout frameLayout = this.endIconFrame;
        if (!z) {
            i2 = 0;
        }
        frameLayout.setVisibility(i2);
        updateSuffixTextViewPadding();
        if (this.endIconMode != 0) {
            z2 = true;
        }
        if (!z2) {
            updateDummyDrawables();
        }
    }

    public final void setHelperTextEnabled(boolean z) {
        IndicatorViewController indicatorViewController2 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController2);
        if (indicatorViewController2.helperTextEnabled != z) {
            indicatorViewController2.cancelCaptionAnimator();
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(indicatorViewController2.context);
                indicatorViewController2.helperTextView = appCompatTextView;
                appCompatTextView.setId(C1777R.C1779id.textinput_helper_text);
                indicatorViewController2.helperTextView.setTextAlignment(5);
                indicatorViewController2.helperTextView.setVisibility(4);
                AppCompatTextView appCompatTextView2 = indicatorViewController2.helperTextView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api19Impl.setAccessibilityLiveRegion(appCompatTextView2, 1);
                int i = indicatorViewController2.helperTextTextAppearance;
                indicatorViewController2.helperTextTextAppearance = i;
                AppCompatTextView appCompatTextView3 = indicatorViewController2.helperTextView;
                if (appCompatTextView3 != null) {
                    appCompatTextView3.setTextAppearance(i);
                }
                ColorStateList colorStateList = indicatorViewController2.helperTextViewTextColor;
                indicatorViewController2.helperTextViewTextColor = colorStateList;
                AppCompatTextView appCompatTextView4 = indicatorViewController2.helperTextView;
                if (!(appCompatTextView4 == null || colorStateList == null)) {
                    appCompatTextView4.setTextColor(colorStateList);
                }
                indicatorViewController2.addIndicator(indicatorViewController2.helperTextView, 1);
            } else {
                indicatorViewController2.cancelCaptionAnimator();
                int i2 = indicatorViewController2.captionDisplayed;
                if (i2 == 2) {
                    indicatorViewController2.captionToShow = 0;
                }
                indicatorViewController2.updateCaptionViewsVisibility(i2, indicatorViewController2.captionToShow, indicatorViewController2.shouldAnimateCaptionView(indicatorViewController2.helperTextView, (CharSequence) null));
                indicatorViewController2.removeIndicator(indicatorViewController2.helperTextView, 1);
                indicatorViewController2.helperTextView = null;
                indicatorViewController2.textInputView.updateEditTextBackground();
                indicatorViewController2.textInputView.updateTextInputBoxState();
            }
            indicatorViewController2.helperTextEnabled = z;
        }
    }

    public final void setHint(CharSequence charSequence) {
        if (this.hintEnabled) {
            if (!TextUtils.equals(charSequence, this.hint)) {
                this.hint = charSequence;
                CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
                Objects.requireNonNull(collapsingTextHelper2);
                if (charSequence == null || !TextUtils.equals(collapsingTextHelper2.text, charSequence)) {
                    collapsingTextHelper2.text = charSequence;
                    collapsingTextHelper2.textToDraw = null;
                    Bitmap bitmap = collapsingTextHelper2.expandedTitleTexture;
                    if (bitmap != null) {
                        bitmap.recycle();
                        collapsingTextHelper2.expandedTitleTexture = null;
                    }
                    collapsingTextHelper2.recalculate(false);
                }
                if (!this.hintExpanded) {
                    openCutout();
                }
            }
            sendAccessibilityEvent(2048);
        }
    }

    public final void setPlaceholderText(CharSequence charSequence) {
        int i = 0;
        if (!this.placeholderEnabled || !TextUtils.isEmpty(charSequence)) {
            if (!this.placeholderEnabled) {
                setPlaceholderTextEnabled(true);
            }
            this.placeholderText = charSequence;
        } else {
            setPlaceholderTextEnabled(false);
        }
        EditText editText2 = this.editText;
        if (editText2 != null) {
            i = editText2.getText().length();
        }
        updatePlaceholderText(i);
    }

    public final void setPlaceholderTextEnabled(boolean z) {
        if (this.placeholderEnabled != z) {
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.placeholderTextView = appCompatTextView;
                appCompatTextView.setId(C1777R.C1779id.textinput_placeholder);
                Fade fade = new Fade();
                fade.mDuration = 87;
                LinearInterpolator linearInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
                fade.mInterpolator = linearInterpolator;
                this.placeholderFadeIn = fade;
                fade.mStartDelay = 67;
                Fade fade2 = new Fade();
                fade2.mDuration = 87;
                fade2.mInterpolator = linearInterpolator;
                this.placeholderFadeOut = fade2;
                AppCompatTextView appCompatTextView2 = this.placeholderTextView;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api19Impl.setAccessibilityLiveRegion(appCompatTextView2, 1);
                int i = this.placeholderTextAppearance;
                this.placeholderTextAppearance = i;
                AppCompatTextView appCompatTextView3 = this.placeholderTextView;
                if (appCompatTextView3 != null) {
                    appCompatTextView3.setTextAppearance(i);
                }
                AppCompatTextView appCompatTextView4 = this.placeholderTextView;
                if (appCompatTextView4 != null) {
                    this.inputFrame.addView(appCompatTextView4);
                    this.placeholderTextView.setVisibility(0);
                }
            } else {
                AppCompatTextView appCompatTextView5 = this.placeholderTextView;
                if (appCompatTextView5 != null) {
                    appCompatTextView5.setVisibility(8);
                }
                this.placeholderTextView = null;
            }
            this.placeholderEnabled = z;
        }
    }

    public final void updateCounter(int i) {
        boolean z;
        int i2;
        boolean z2 = this.counterOverflowed;
        int i3 = this.counterMaxLength;
        if (i3 == -1) {
            this.counterView.setText(String.valueOf(i));
            this.counterView.setContentDescription((CharSequence) null);
            this.counterOverflowed = false;
        } else {
            if (i > i3) {
                z = true;
            } else {
                z = false;
            }
            this.counterOverflowed = z;
            Context context = getContext();
            AppCompatTextView appCompatTextView = this.counterView;
            int i4 = this.counterMaxLength;
            if (this.counterOverflowed) {
                i2 = C1777R.string.character_counter_overflowed_content_description;
            } else {
                i2 = C1777R.string.character_counter_content_description;
            }
            appCompatTextView.setContentDescription(context.getString(i2, new Object[]{Integer.valueOf(i), Integer.valueOf(i4)}));
            if (z2 != this.counterOverflowed) {
                updateCounterTextAppearanceAndColor();
            }
            this.counterView.setText(BidiFormatter.getInstance().unicodeWrap(getContext().getString(C1777R.string.character_counter_pattern, new Object[]{Integer.valueOf(i), Integer.valueOf(this.counterMaxLength)})));
        }
        if (this.editText != null && z2 != this.counterOverflowed) {
            updateLabelState(false, false);
            updateTextInputBoxState();
            updateEditTextBackground();
        }
    }

    public final void updateCounterTextAppearanceAndColor() {
        int i;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        AppCompatTextView appCompatTextView = this.counterView;
        if (appCompatTextView != null) {
            if (this.counterOverflowed) {
                i = this.counterOverflowTextAppearance;
            } else {
                i = this.counterTextAppearance;
            }
            setTextAppearanceCompatWithErrorFallback(appCompatTextView, i);
            if (!this.counterOverflowed && (colorStateList2 = this.counterTextColor) != null) {
                this.counterView.setTextColor(colorStateList2);
            }
            if (this.counterOverflowed && (colorStateList = this.counterOverflowTextColor) != null) {
                this.counterView.setTextColor(colorStateList);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008b, code lost:
        if (isEndIconVisible() != false) goto L_0x0091;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x008f, code lost:
        if (r10.suffixText != null) goto L_0x0091;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0099  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x0121  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean updateDummyDrawables() {
        /*
            r10 = this;
            android.widget.EditText r0 = r10.editText
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            com.google.android.material.internal.CheckableImageButton r0 = r10.startIconView
            android.graphics.drawable.Drawable r0 = r0.getDrawable()
            r2 = 1
            if (r0 != 0) goto L_0x0013
            java.lang.CharSequence r0 = r10.prefixText
            if (r0 == 0) goto L_0x001d
        L_0x0013:
            android.widget.LinearLayout r0 = r10.startLayout
            int r0 = r0.getMeasuredWidth()
            if (r0 <= 0) goto L_0x001d
            r0 = r2
            goto L_0x001e
        L_0x001d:
            r0 = r1
        L_0x001e:
            r3 = 0
            r4 = 3
            r5 = 2
            if (r0 == 0) goto L_0x005c
            android.widget.LinearLayout r0 = r10.startLayout
            int r0 = r0.getMeasuredWidth()
            android.widget.EditText r6 = r10.editText
            int r6 = r6.getPaddingLeft()
            int r0 = r0 - r6
            android.graphics.drawable.ColorDrawable r6 = r10.startDummyDrawable
            if (r6 == 0) goto L_0x0038
            int r6 = r10.startDummyDrawableWidth
            if (r6 == r0) goto L_0x0044
        L_0x0038:
            android.graphics.drawable.ColorDrawable r6 = new android.graphics.drawable.ColorDrawable
            r6.<init>()
            r10.startDummyDrawable = r6
            r10.startDummyDrawableWidth = r0
            r6.setBounds(r1, r1, r0, r2)
        L_0x0044:
            android.widget.EditText r0 = r10.editText
            android.graphics.drawable.Drawable[] r0 = r0.getCompoundDrawablesRelative()
            r6 = r0[r1]
            android.graphics.drawable.ColorDrawable r7 = r10.startDummyDrawable
            if (r6 == r7) goto L_0x0075
            android.widget.EditText r6 = r10.editText
            r8 = r0[r2]
            r9 = r0[r5]
            r0 = r0[r4]
            r6.setCompoundDrawablesRelative(r7, r8, r9, r0)
            goto L_0x0073
        L_0x005c:
            android.graphics.drawable.ColorDrawable r0 = r10.startDummyDrawable
            if (r0 == 0) goto L_0x0075
            android.widget.EditText r0 = r10.editText
            android.graphics.drawable.Drawable[] r0 = r0.getCompoundDrawablesRelative()
            android.widget.EditText r6 = r10.editText
            r7 = r0[r2]
            r8 = r0[r5]
            r0 = r0[r4]
            r6.setCompoundDrawablesRelative(r3, r7, r8, r0)
            r10.startDummyDrawable = r3
        L_0x0073:
            r0 = r2
            goto L_0x0076
        L_0x0075:
            r0 = r1
        L_0x0076:
            com.google.android.material.internal.CheckableImageButton r6 = r10.errorIconView
            int r6 = r6.getVisibility()
            if (r6 == 0) goto L_0x0091
            int r6 = r10.endIconMode
            if (r6 == 0) goto L_0x0084
            r6 = r2
            goto L_0x0085
        L_0x0084:
            r6 = r1
        L_0x0085:
            if (r6 == 0) goto L_0x008d
            boolean r6 = r10.isEndIconVisible()
            if (r6 != 0) goto L_0x0091
        L_0x008d:
            java.lang.CharSequence r6 = r10.suffixText
            if (r6 == 0) goto L_0x009b
        L_0x0091:
            android.widget.LinearLayout r6 = r10.endLayout
            int r6 = r6.getMeasuredWidth()
            if (r6 <= 0) goto L_0x009b
            r6 = r2
            goto L_0x009c
        L_0x009b:
            r6 = r1
        L_0x009c:
            if (r6 == 0) goto L_0x0121
            androidx.appcompat.widget.AppCompatTextView r6 = r10.suffixTextView
            int r6 = r6.getMeasuredWidth()
            android.widget.EditText r7 = r10.editText
            int r7 = r7.getPaddingRight()
            int r6 = r6 - r7
            com.google.android.material.internal.CheckableImageButton r7 = r10.errorIconView
            int r7 = r7.getVisibility()
            if (r7 != 0) goto L_0x00b6
            com.google.android.material.internal.CheckableImageButton r3 = r10.errorIconView
            goto L_0x00c7
        L_0x00b6:
            int r7 = r10.endIconMode
            if (r7 == 0) goto L_0x00bc
            r7 = r2
            goto L_0x00bd
        L_0x00bc:
            r7 = r1
        L_0x00bd:
            if (r7 == 0) goto L_0x00c7
            boolean r7 = r10.isEndIconVisible()
            if (r7 == 0) goto L_0x00c7
            com.google.android.material.internal.CheckableImageButton r3 = r10.endIconView
        L_0x00c7:
            if (r3 == 0) goto L_0x00da
            int r7 = r3.getMeasuredWidth()
            int r7 = r7 + r6
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r3 = (android.view.ViewGroup.MarginLayoutParams) r3
            int r3 = r3.getMarginStart()
            int r6 = r3 + r7
        L_0x00da:
            android.widget.EditText r3 = r10.editText
            android.graphics.drawable.Drawable[] r3 = r3.getCompoundDrawablesRelative()
            android.graphics.drawable.ColorDrawable r7 = r10.endDummyDrawable
            if (r7 == 0) goto L_0x00fb
            int r8 = r10.endDummyDrawableWidth
            if (r8 == r6) goto L_0x00fb
            r10.endDummyDrawableWidth = r6
            r7.setBounds(r1, r1, r6, r2)
            android.widget.EditText r0 = r10.editText
            r1 = r3[r1]
            r5 = r3[r2]
            android.graphics.drawable.ColorDrawable r10 = r10.endDummyDrawable
            r3 = r3[r4]
            r0.setCompoundDrawablesRelative(r1, r5, r10, r3)
            goto L_0x0142
        L_0x00fb:
            if (r7 != 0) goto L_0x0109
            android.graphics.drawable.ColorDrawable r7 = new android.graphics.drawable.ColorDrawable
            r7.<init>()
            r10.endDummyDrawable = r7
            r10.endDummyDrawableWidth = r6
            r7.setBounds(r1, r1, r6, r2)
        L_0x0109:
            r6 = r3[r5]
            android.graphics.drawable.ColorDrawable r7 = r10.endDummyDrawable
            if (r6 == r7) goto L_0x011f
            r0 = r3[r5]
            r10.originalEditTextEndDrawable = r0
            android.widget.EditText r10 = r10.editText
            r0 = r3[r1]
            r1 = r3[r2]
            r3 = r3[r4]
            r10.setCompoundDrawablesRelative(r0, r1, r7, r3)
            goto L_0x0142
        L_0x011f:
            r2 = r0
            goto L_0x0142
        L_0x0121:
            android.graphics.drawable.ColorDrawable r6 = r10.endDummyDrawable
            if (r6 == 0) goto L_0x0143
            android.widget.EditText r6 = r10.editText
            android.graphics.drawable.Drawable[] r6 = r6.getCompoundDrawablesRelative()
            r5 = r6[r5]
            android.graphics.drawable.ColorDrawable r7 = r10.endDummyDrawable
            if (r5 != r7) goto L_0x013f
            android.widget.EditText r0 = r10.editText
            r1 = r6[r1]
            r5 = r6[r2]
            android.graphics.drawable.Drawable r7 = r10.originalEditTextEndDrawable
            r4 = r6[r4]
            r0.setCompoundDrawablesRelative(r1, r5, r7, r4)
            goto L_0x0140
        L_0x013f:
            r2 = r0
        L_0x0140:
            r10.endDummyDrawable = r3
        L_0x0142:
            r0 = r2
        L_0x0143:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateDummyDrawables():boolean");
    }

    public final void updateEditTextBackground() {
        Drawable background;
        AppCompatTextView appCompatTextView;
        int i;
        EditText editText2 = this.editText;
        if (editText2 != null && this.boxBackgroundMode == 0 && (background = editText2.getBackground()) != null) {
            if (DrawableUtils.canSafelyMutateDrawable(background)) {
                background = background.mutate();
            }
            if (this.indicatorViewController.errorShouldBeShown()) {
                IndicatorViewController indicatorViewController2 = this.indicatorViewController;
                Objects.requireNonNull(indicatorViewController2);
                AppCompatTextView appCompatTextView2 = indicatorViewController2.errorView;
                if (appCompatTextView2 != null) {
                    i = appCompatTextView2.getCurrentTextColor();
                } else {
                    i = -1;
                }
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(i, PorterDuff.Mode.SRC_IN));
            } else if (!this.counterOverflowed || (appCompatTextView = this.counterView) == null) {
                background.clearColorFilter();
                this.editText.refreshDrawableState();
            } else {
                background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(appCompatTextView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
            }
        }
    }

    public final void updateInputLayoutMargins() {
        if (this.boxBackgroundMode != 1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.inputFrame.getLayoutParams();
            int calculateLabelMarginTop = calculateLabelMarginTop();
            if (calculateLabelMarginTop != layoutParams.topMargin) {
                layoutParams.topMargin = calculateLabelMarginTop;
                this.inputFrame.requestLayout();
            }
        }
    }

    public final void updatePlaceholderText(int i) {
        if (i != 0 || this.hintExpanded) {
            AppCompatTextView appCompatTextView = this.placeholderTextView;
            if (appCompatTextView != null && this.placeholderEnabled) {
                appCompatTextView.setText((CharSequence) null);
                TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeOut);
                this.placeholderTextView.setVisibility(4);
                return;
            }
            return;
        }
        AppCompatTextView appCompatTextView2 = this.placeholderTextView;
        if (appCompatTextView2 != null && this.placeholderEnabled) {
            appCompatTextView2.setText(this.placeholderText);
            TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeIn);
            this.placeholderTextView.setVisibility(0);
            this.placeholderTextView.bringToFront();
        }
    }

    public final void updatePrefixTextViewPadding() {
        boolean z;
        if (this.editText != null) {
            int i = 0;
            if (this.startIconView.getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                EditText editText2 = this.editText;
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                i = ViewCompat.Api17Impl.getPaddingStart(editText2);
            }
            AppCompatTextView appCompatTextView = this.prefixTextView;
            int compoundPaddingTop = this.editText.getCompoundPaddingTop();
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.material_input_text_to_prefix_suffix_padding);
            int compoundPaddingBottom = this.editText.getCompoundPaddingBottom();
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api17Impl.setPaddingRelative(appCompatTextView, i, compoundPaddingTop, dimensionPixelSize, compoundPaddingBottom);
        }
    }

    public final void updatePrefixTextVisibility() {
        int i;
        AppCompatTextView appCompatTextView = this.prefixTextView;
        if (this.prefixText == null || isHintExpanded()) {
            i = 8;
        } else {
            i = 0;
        }
        appCompatTextView.setVisibility(i);
        updateDummyDrawables();
    }

    public final void updateStrokeErrorColor(boolean z, boolean z2) {
        int defaultColor = this.strokeErrorColor.getDefaultColor();
        int colorForState = this.strokeErrorColor.getColorForState(new int[]{16843623, 16842910}, defaultColor);
        int colorForState2 = this.strokeErrorColor.getColorForState(new int[]{16843518, 16842910}, defaultColor);
        if (z) {
            this.boxStrokeColor = colorForState2;
        } else if (z2) {
            this.boxStrokeColor = colorForState;
        } else {
            this.boxStrokeColor = defaultColor;
        }
    }

    public final void updateSuffixTextViewPadding() {
        boolean z;
        if (this.editText != null) {
            int i = 0;
            if (!isEndIconVisible()) {
                if (this.errorIconView.getVisibility() == 0) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    EditText editText2 = this.editText;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    i = ViewCompat.Api17Impl.getPaddingEnd(editText2);
                }
            }
            AppCompatTextView appCompatTextView = this.suffixTextView;
            int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(C1777R.dimen.material_input_text_to_prefix_suffix_padding);
            int paddingTop = this.editText.getPaddingTop();
            int paddingBottom = this.editText.getPaddingBottom();
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api17Impl.setPaddingRelative(appCompatTextView, dimensionPixelSize, paddingTop, i, paddingBottom);
        }
    }

    public final void updateSuffixTextVisibility() {
        boolean z;
        int visibility = this.suffixTextView.getVisibility();
        int i = 0;
        if (this.suffixText == null || isHintExpanded()) {
            z = false;
        } else {
            z = true;
        }
        AppCompatTextView appCompatTextView = this.suffixTextView;
        if (!z) {
            i = 8;
        }
        appCompatTextView.setVisibility(i);
        if (visibility != this.suffixTextView.getVisibility()) {
            getEndIconDelegate().onSuffixVisibilityChanged(z);
        }
        updateDummyDrawables();
    }

    /* JADX WARNING: Removed duplicated region for block: B:109:0x017d  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x019b  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x01c2  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:134:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0109  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x013d  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x0162  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateTextInputBoxState() {
        /*
            r8 = this;
            com.google.android.material.shape.MaterialShapeDrawable r0 = r8.boxBackground
            if (r0 == 0) goto L_0x01eb
            int r0 = r8.boxBackgroundMode
            if (r0 != 0) goto L_0x000a
            goto L_0x01eb
        L_0x000a:
            boolean r0 = r8.isFocused()
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x001f
            android.widget.EditText r0 = r8.editText
            if (r0 == 0) goto L_0x001d
            boolean r0 = r0.hasFocus()
            if (r0 == 0) goto L_0x001d
            goto L_0x001f
        L_0x001d:
            r0 = r2
            goto L_0x0020
        L_0x001f:
            r0 = r1
        L_0x0020:
            boolean r3 = r8.isHovered()
            if (r3 != 0) goto L_0x0033
            android.widget.EditText r3 = r8.editText
            if (r3 == 0) goto L_0x0031
            boolean r3 = r3.isHovered()
            if (r3 == 0) goto L_0x0031
            goto L_0x0033
        L_0x0031:
            r3 = r2
            goto L_0x0034
        L_0x0033:
            r3 = r1
        L_0x0034:
            boolean r4 = r8.isEnabled()
            r5 = -1
            if (r4 != 0) goto L_0x0040
            int r4 = r8.disabledColor
            r8.boxStrokeColor = r4
            goto L_0x008b
        L_0x0040:
            com.google.android.material.textfield.IndicatorViewController r4 = r8.indicatorViewController
            boolean r4 = r4.errorShouldBeShown()
            if (r4 == 0) goto L_0x0062
            android.content.res.ColorStateList r4 = r8.strokeErrorColor
            if (r4 == 0) goto L_0x0050
            r8.updateStrokeErrorColor(r0, r3)
            goto L_0x008b
        L_0x0050:
            com.google.android.material.textfield.IndicatorViewController r4 = r8.indicatorViewController
            java.util.Objects.requireNonNull(r4)
            androidx.appcompat.widget.AppCompatTextView r4 = r4.errorView
            if (r4 == 0) goto L_0x005e
            int r4 = r4.getCurrentTextColor()
            goto L_0x005f
        L_0x005e:
            r4 = r5
        L_0x005f:
            r8.boxStrokeColor = r4
            goto L_0x008b
        L_0x0062:
            boolean r4 = r8.counterOverflowed
            if (r4 == 0) goto L_0x0079
            androidx.appcompat.widget.AppCompatTextView r4 = r8.counterView
            if (r4 == 0) goto L_0x0079
            android.content.res.ColorStateList r6 = r8.strokeErrorColor
            if (r6 == 0) goto L_0x0072
            r8.updateStrokeErrorColor(r0, r3)
            goto L_0x008b
        L_0x0072:
            int r4 = r4.getCurrentTextColor()
            r8.boxStrokeColor = r4
            goto L_0x008b
        L_0x0079:
            if (r0 == 0) goto L_0x0080
            int r4 = r8.focusedStrokeColor
            r8.boxStrokeColor = r4
            goto L_0x008b
        L_0x0080:
            if (r3 == 0) goto L_0x0087
            int r4 = r8.hoveredStrokeColor
            r8.boxStrokeColor = r4
            goto L_0x008b
        L_0x0087:
            int r4 = r8.defaultStrokeColor
            r8.boxStrokeColor = r4
        L_0x008b:
            com.google.android.material.internal.CheckableImageButton r4 = r8.errorIconView
            android.graphics.drawable.Drawable r4 = r4.getDrawable()
            if (r4 == 0) goto L_0x00a6
            com.google.android.material.textfield.IndicatorViewController r4 = r8.indicatorViewController
            java.util.Objects.requireNonNull(r4)
            boolean r4 = r4.errorEnabled
            if (r4 == 0) goto L_0x00a6
            com.google.android.material.textfield.IndicatorViewController r4 = r8.indicatorViewController
            boolean r4 = r4.errorShouldBeShown()
            if (r4 == 0) goto L_0x00a6
            r4 = r1
            goto L_0x00a7
        L_0x00a6:
            r4 = r2
        L_0x00a7:
            r8.setErrorIconVisible(r4)
            com.google.android.material.internal.CheckableImageButton r4 = r8.errorIconView
            android.content.res.ColorStateList r6 = r8.errorIconTintList
            r8.refreshIconDrawableState(r4, r6)
            com.google.android.material.internal.CheckableImageButton r4 = r8.startIconView
            android.content.res.ColorStateList r6 = r8.startIconTintList
            r8.refreshIconDrawableState(r4, r6)
            com.google.android.material.internal.CheckableImageButton r4 = r8.endIconView
            android.content.res.ColorStateList r6 = r8.endIconTintList
            r8.refreshIconDrawableState(r4, r6)
            com.google.android.material.textfield.EndIconDelegate r4 = r8.getEndIconDelegate()
            java.util.Objects.requireNonNull(r4)
            boolean r4 = r4 instanceof com.google.android.material.textfield.DropdownMenuEndIconDelegate
            if (r4 == 0) goto L_0x00ff
            com.google.android.material.textfield.IndicatorViewController r4 = r8.indicatorViewController
            boolean r4 = r4.errorShouldBeShown()
            if (r4 == 0) goto L_0x00fc
            com.google.android.material.internal.CheckableImageButton r4 = r8.endIconView
            android.graphics.drawable.Drawable r4 = r4.getDrawable()
            if (r4 == 0) goto L_0x00fc
            com.google.android.material.internal.CheckableImageButton r4 = r8.endIconView
            android.graphics.drawable.Drawable r4 = r4.getDrawable()
            android.graphics.drawable.Drawable r4 = r4.mutate()
            com.google.android.material.textfield.IndicatorViewController r6 = r8.indicatorViewController
            java.util.Objects.requireNonNull(r6)
            androidx.appcompat.widget.AppCompatTextView r6 = r6.errorView
            if (r6 == 0) goto L_0x00f2
            int r6 = r6.getCurrentTextColor()
            goto L_0x00f3
        L_0x00f2:
            r6 = r5
        L_0x00f3:
            r4.setTint(r6)
            com.google.android.material.internal.CheckableImageButton r6 = r8.endIconView
            r6.setImageDrawable(r4)
            goto L_0x00ff
        L_0x00fc:
            r8.applyEndIconTint()
        L_0x00ff:
            int r4 = r8.boxStrokeWidthPx
            if (r0 == 0) goto L_0x010e
            boolean r6 = r8.isEnabled()
            if (r6 == 0) goto L_0x010e
            int r6 = r8.boxStrokeWidthFocusedPx
            r8.boxStrokeWidthPx = r6
            goto L_0x0112
        L_0x010e:
            int r6 = r8.boxStrokeWidthDefaultPx
            r8.boxStrokeWidthPx = r6
        L_0x0112:
            int r6 = r8.boxStrokeWidthPx
            r7 = 2
            if (r6 == r4) goto L_0x0139
            int r4 = r8.boxBackgroundMode
            if (r4 != r7) goto L_0x0139
            boolean r4 = r8.cutoutEnabled()
            if (r4 == 0) goto L_0x0139
            boolean r4 = r8.hintExpanded
            if (r4 != 0) goto L_0x0139
            boolean r4 = r8.cutoutEnabled()
            if (r4 == 0) goto L_0x0136
            com.google.android.material.shape.MaterialShapeDrawable r4 = r8.boxBackground
            com.google.android.material.textfield.CutoutDrawable r4 = (com.google.android.material.textfield.CutoutDrawable) r4
            java.util.Objects.requireNonNull(r4)
            r6 = 0
            r4.setCutout(r6, r6, r6, r6)
        L_0x0136:
            r8.openCutout()
        L_0x0139:
            int r4 = r8.boxBackgroundMode
            if (r4 != r1) goto L_0x015c
            boolean r4 = r8.isEnabled()
            if (r4 != 0) goto L_0x0148
            int r0 = r8.disabledFilledBackgroundColor
            r8.boxBackgroundColor = r0
            goto L_0x015c
        L_0x0148:
            if (r3 == 0) goto L_0x0151
            if (r0 != 0) goto L_0x0151
            int r0 = r8.hoveredFilledBackgroundColor
            r8.boxBackgroundColor = r0
            goto L_0x015c
        L_0x0151:
            if (r0 == 0) goto L_0x0158
            int r0 = r8.focusedFilledBackgroundColor
            r8.boxBackgroundColor = r0
            goto L_0x015c
        L_0x0158:
            int r0 = r8.defaultFilledBackgroundColor
            r8.boxBackgroundColor = r0
        L_0x015c:
            com.google.android.material.shape.MaterialShapeDrawable r0 = r8.boxBackground
            if (r0 != 0) goto L_0x0162
            goto L_0x01eb
        L_0x0162:
            com.google.android.material.shape.ShapeAppearanceModel r3 = r8.shapeAppearanceModel
            r0.setShapeAppearanceModel(r3)
            int r0 = r8.boxBackgroundMode
            if (r0 != r7) goto L_0x017a
            int r0 = r8.boxStrokeWidthPx
            if (r0 <= r5) goto L_0x0175
            int r0 = r8.boxStrokeColor
            if (r0 == 0) goto L_0x0175
            r0 = r1
            goto L_0x0176
        L_0x0175:
            r0 = r2
        L_0x0176:
            if (r0 == 0) goto L_0x017a
            r0 = r1
            goto L_0x017b
        L_0x017a:
            r0 = r2
        L_0x017b:
            if (r0 == 0) goto L_0x0195
            com.google.android.material.shape.MaterialShapeDrawable r0 = r8.boxBackground
            int r3 = r8.boxStrokeWidthPx
            float r3 = (float) r3
            int r4 = r8.boxStrokeColor
            java.util.Objects.requireNonNull(r0)
            com.google.android.material.shape.MaterialShapeDrawable$MaterialShapeDrawableState r6 = r0.drawableState
            r6.strokeWidth = r3
            r0.invalidateSelf()
            android.content.res.ColorStateList r3 = android.content.res.ColorStateList.valueOf(r4)
            r0.setStrokeColor(r3)
        L_0x0195:
            int r0 = r8.boxBackgroundColor
            int r3 = r8.boxBackgroundMode
            if (r3 != r1) goto L_0x01b2
            r0 = 2130968847(0x7f04010f, float:1.754636E38)
            android.content.Context r3 = r8.getContext()
            android.util.TypedValue r0 = com.google.android.material.resources.MaterialAttributes.resolve(r3, r0)
            if (r0 == 0) goto L_0x01ab
            int r0 = r0.data
            goto L_0x01ac
        L_0x01ab:
            r0 = r2
        L_0x01ac:
            int r3 = r8.boxBackgroundColor
            int r0 = androidx.core.graphics.ColorUtils.compositeColors(r3, r0)
        L_0x01b2:
            r8.boxBackgroundColor = r0
            com.google.android.material.shape.MaterialShapeDrawable r3 = r8.boxBackground
            android.content.res.ColorStateList r0 = android.content.res.ColorStateList.valueOf(r0)
            r3.setFillColor(r0)
            int r0 = r8.endIconMode
            r3 = 3
            if (r0 != r3) goto L_0x01cb
            android.widget.EditText r0 = r8.editText
            android.graphics.drawable.Drawable r0 = r0.getBackground()
            r0.invalidateSelf()
        L_0x01cb:
            com.google.android.material.shape.MaterialShapeDrawable r0 = r8.boxUnderline
            if (r0 != 0) goto L_0x01d0
            goto L_0x01e8
        L_0x01d0:
            int r3 = r8.boxStrokeWidthPx
            if (r3 <= r5) goto L_0x01d9
            int r3 = r8.boxStrokeColor
            if (r3 == 0) goto L_0x01d9
            goto L_0x01da
        L_0x01d9:
            r1 = r2
        L_0x01da:
            if (r1 == 0) goto L_0x01e5
            int r1 = r8.boxStrokeColor
            android.content.res.ColorStateList r1 = android.content.res.ColorStateList.valueOf(r1)
            r0.setFillColor(r1)
        L_0x01e5:
            r8.invalidate()
        L_0x01e8:
            r8.invalidate()
        L_0x01eb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateTextInputBoxState():void");
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TextInputLayout(android.content.Context r33, android.util.AttributeSet r34, int r35) {
        /*
            r32 = this;
            r0 = r32
            r7 = r34
            r8 = r35
            r9 = 2132018393(0x7f1404d9, float:1.9675091E38)
            r1 = r33
            android.content.Context r1 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r1, r7, r8)
            r10 = -1
            r0.minWidth = r10
            r0.maxWidth = r10
            com.google.android.material.textfield.IndicatorViewController r11 = new com.google.android.material.textfield.IndicatorViewController
            r11.<init>(r0)
            r0.indicatorViewController = r11
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.tmpRect = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r0.tmpBoundsRect = r1
            android.graphics.RectF r1 = new android.graphics.RectF
            r1.<init>()
            r0.tmpRectF = r1
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            r0.editTextAttachedListeners = r1
            r12 = 0
            r0.endIconMode = r12
            android.util.SparseArray r13 = new android.util.SparseArray
            r13.<init>()
            r0.endIconDelegates = r13
            java.util.LinkedHashSet r1 = new java.util.LinkedHashSet
            r1.<init>()
            r0.endIconChangedListeners = r1
            com.google.android.material.internal.CollapsingTextHelper r14 = new com.google.android.material.internal.CollapsingTextHelper
            r14.<init>(r0)
            r0.collapsingTextHelper = r14
            android.content.Context r15 = r32.getContext()
            r6 = 1
            r0.setOrientation(r6)
            r0.setWillNotDraw(r12)
            r0.setAddStatesFromChildren(r6)
            android.widget.FrameLayout r1 = new android.widget.FrameLayout
            r1.<init>(r15)
            r0.inputFrame = r1
            r1.setAddStatesFromChildren(r6)
            r0.addView(r1)
            android.widget.LinearLayout r5 = new android.widget.LinearLayout
            r5.<init>(r15)
            r0.startLayout = r5
            r5.setOrientation(r12)
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r4 = -2
            r3 = 8388611(0x800003, float:1.1754948E-38)
            r2.<init>(r4, r10, r3)
            r5.setLayoutParams(r2)
            r1.addView(r5)
            android.widget.LinearLayout r3 = new android.widget.LinearLayout
            r3.<init>(r15)
            r0.endLayout = r3
            r3.setOrientation(r12)
            android.widget.FrameLayout$LayoutParams r2 = new android.widget.FrameLayout$LayoutParams
            r6 = 8388613(0x800005, float:1.175495E-38)
            r2.<init>(r4, r10, r6)
            r3.setLayoutParams(r2)
            r1.addView(r3)
            android.widget.FrameLayout r6 = new android.widget.FrameLayout
            r6.<init>(r15)
            r0.endIconFrame = r6
            android.widget.FrameLayout$LayoutParams r1 = new android.widget.FrameLayout$LayoutParams
            r1.<init>(r4, r10)
            r6.setLayoutParams(r1)
            android.view.animation.LinearInterpolator r1 = com.google.android.material.animation.AnimationUtils.LINEAR_INTERPOLATOR
            r14.textSizeInterpolator = r1
            r14.recalculate(r12)
            r14.positionInterpolator = r1
            r14.recalculate(r12)
            r1 = 8388659(0x800033, float:1.1755015E-38)
            int r2 = r14.collapsedTextGravity
            if (r2 == r1) goto L_0x00c5
            r14.collapsedTextGravity = r1
            r14.recalculate(r12)
        L_0x00c5:
            int[] r16 = com.google.android.material.R$styleable.TextInputLayout
            r2 = 5
            int[] r1 = new int[r2]
            r1 = {20, 18, 33, 38, 42} // fill-array
            r17 = 2132018393(0x7f1404d9, float:1.9675091E38)
            r18 = r1
            r1 = r15
            r2 = r34
            r19 = r3
            r3 = r16
            r4 = r35
            r20 = r5
            r5 = r17
            r21 = r6
            r12 = 1
            r6 = r18
            androidx.appcompat.widget.TintTypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            r2 = 41
            boolean r2 = r1.getBoolean(r2, r12)
            r0.hintEnabled = r2
            r2 = 4
            java.lang.CharSequence r2 = r1.getText(r2)
            r0.setHint(r2)
            r2 = 40
            boolean r2 = r1.getBoolean(r2, r12)
            r0.hintAnimationEnabled = r2
            r2 = 35
            boolean r2 = r1.getBoolean(r2, r12)
            r0.expandedHintEnabled = r2
            r2 = 3
            boolean r3 = r1.hasValue(r2)
            if (r3 == 0) goto L_0x011e
            int r3 = r1.getDimensionPixelSize(r2, r10)
            r0.minWidth = r3
            android.widget.EditText r4 = r0.editText
            if (r4 == 0) goto L_0x011e
            if (r3 == r10) goto L_0x011e
            r4.setMinWidth(r3)
        L_0x011e:
            r3 = 2
            boolean r4 = r1.hasValue(r3)
            if (r4 == 0) goto L_0x0134
            int r4 = r1.getDimensionPixelSize(r3, r10)
            r0.maxWidth = r4
            android.widget.EditText r5 = r0.editText
            if (r5 == 0) goto L_0x0134
            if (r4 == r10) goto L_0x0134
            r5.setMaxWidth(r4)
        L_0x0134:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r4 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r15, (android.util.AttributeSet) r7, (int) r8, (int) r9)
            com.google.android.material.shape.ShapeAppearanceModel r5 = new com.google.android.material.shape.ShapeAppearanceModel
            r5.<init>(r4)
            r0.shapeAppearanceModel = r5
            android.content.res.Resources r4 = r15.getResources()
            r5 = 2131166566(0x7f070566, float:1.794738E38)
            int r4 = r4.getDimensionPixelOffset(r5)
            r0.boxLabelCutoutPaddingPx = r4
            r4 = 7
            r5 = 0
            int r4 = r1.getDimensionPixelOffset(r4, r5)
            r0.boxCollapsedPaddingTopPx = r4
            r4 = 14
            android.content.res.Resources r5 = r15.getResources()
            r6 = 2131166567(0x7f070567, float:1.7947383E38)
            int r5 = r5.getDimensionPixelSize(r6)
            int r4 = r1.getDimensionPixelSize(r4, r5)
            r0.boxStrokeWidthDefaultPx = r4
            r4 = 15
            android.content.res.Resources r5 = r15.getResources()
            r6 = 2131166568(0x7f070568, float:1.7947385E38)
            int r5 = r5.getDimensionPixelSize(r6)
            int r4 = r1.getDimensionPixelSize(r4, r5)
            r0.boxStrokeWidthFocusedPx = r4
            int r4 = r0.boxStrokeWidthDefaultPx
            r0.boxStrokeWidthPx = r4
            r4 = 11
            android.content.res.TypedArray r5 = r1.mWrapped
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r4 = r5.getDimension(r4, r6)
            r5 = 10
            android.content.res.TypedArray r7 = r1.mWrapped
            float r5 = r7.getDimension(r5, r6)
            android.content.res.TypedArray r7 = r1.mWrapped
            r8 = 8
            float r7 = r7.getDimension(r8, r6)
            r9 = 9
            android.content.res.TypedArray r2 = r1.mWrapped
            float r2 = r2.getDimension(r9, r6)
            com.google.android.material.shape.ShapeAppearanceModel r6 = r0.shapeAppearanceModel
            java.util.Objects.requireNonNull(r6)
            com.google.android.material.shape.ShapeAppearanceModel$Builder r9 = new com.google.android.material.shape.ShapeAppearanceModel$Builder
            r9.<init>(r6)
            r6 = 0
            int r17 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r17 < 0) goto L_0x01b6
            com.google.android.material.shape.AbsoluteCornerSize r8 = new com.google.android.material.shape.AbsoluteCornerSize
            r8.<init>(r4)
            r9.topLeftCornerSize = r8
        L_0x01b6:
            int r4 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x01c1
            com.google.android.material.shape.AbsoluteCornerSize r4 = new com.google.android.material.shape.AbsoluteCornerSize
            r4.<init>(r5)
            r9.topRightCornerSize = r4
        L_0x01c1:
            int r4 = (r7 > r6 ? 1 : (r7 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x01cc
            com.google.android.material.shape.AbsoluteCornerSize r4 = new com.google.android.material.shape.AbsoluteCornerSize
            r4.<init>(r7)
            r9.bottomRightCornerSize = r4
        L_0x01cc:
            int r4 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1))
            if (r4 < 0) goto L_0x01d7
            com.google.android.material.shape.AbsoluteCornerSize r4 = new com.google.android.material.shape.AbsoluteCornerSize
            r4.<init>(r2)
            r9.bottomLeftCornerSize = r4
        L_0x01d7:
            com.google.android.material.shape.ShapeAppearanceModel r2 = new com.google.android.material.shape.ShapeAppearanceModel
            r2.<init>(r9)
            r0.shapeAppearanceModel = r2
            r2 = 5
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r15, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            r4 = -16842910(0xfffffffffefeff62, float:-1.6947497E38)
            if (r2 == 0) goto L_0x023c
            int r5 = r2.getDefaultColor()
            r0.defaultFilledBackgroundColor = r5
            r0.boxBackgroundColor = r5
            boolean r5 = r2.isStateful()
            if (r5 == 0) goto L_0x0218
            int[] r5 = new int[r12]
            r6 = 0
            r5[r6] = r4
            int r5 = r2.getColorForState(r5, r10)
            r0.disabledFilledBackgroundColor = r5
            int[] r5 = new int[r3]
            r5 = {16842908, 16842910} // fill-array
            int r5 = r2.getColorForState(r5, r10)
            r0.focusedFilledBackgroundColor = r5
            int[] r5 = new int[r3]
            r5 = {16843623, 16842910} // fill-array
            int r2 = r2.getColorForState(r5, r10)
            r0.hoveredFilledBackgroundColor = r2
            goto L_0x0247
        L_0x0218:
            int r2 = r0.defaultFilledBackgroundColor
            r0.focusedFilledBackgroundColor = r2
            r2 = 2131100436(0x7f060314, float:1.7813253E38)
            android.content.res.ColorStateList r2 = androidx.appcompat.content.res.AppCompatResources.getColorStateList(r15, r2)
            int[] r5 = new int[r12]
            r6 = 0
            r5[r6] = r4
            int r5 = r2.getColorForState(r5, r10)
            r0.disabledFilledBackgroundColor = r5
            int[] r5 = new int[r12]
            r7 = 16843623(0x1010367, float:2.3696E-38)
            r5[r6] = r7
            int r2 = r2.getColorForState(r5, r10)
            r0.hoveredFilledBackgroundColor = r2
            goto L_0x0247
        L_0x023c:
            r6 = 0
            r0.boxBackgroundColor = r6
            r0.defaultFilledBackgroundColor = r6
            r0.disabledFilledBackgroundColor = r6
            r0.focusedFilledBackgroundColor = r6
            r0.hoveredFilledBackgroundColor = r6
        L_0x0247:
            boolean r2 = r1.hasValue(r12)
            if (r2 == 0) goto L_0x0255
            android.content.res.ColorStateList r2 = r1.getColorStateList(r12)
            r0.focusedTextColor = r2
            r0.defaultHintTextColor = r2
        L_0x0255:
            r2 = 12
            android.content.res.ColorStateList r5 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r15, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            android.content.res.TypedArray r6 = r1.mWrapped
            r7 = 0
            int r2 = r6.getColor(r2, r7)
            r0.focusedStrokeColor = r2
            r2 = 2131100459(0x7f06032b, float:1.78133E38)
            java.lang.Object r6 = androidx.core.content.ContextCompat.sLock
            int r2 = r15.getColor(r2)
            r0.defaultStrokeColor = r2
            r2 = 2131100460(0x7f06032c, float:1.7813302E38)
            int r2 = r15.getColor(r2)
            r0.disabledColor = r2
            r2 = 2131100463(0x7f06032f, float:1.7813308E38)
            int r2 = r15.getColor(r2)
            r0.hoveredStrokeColor = r2
            if (r5 == 0) goto L_0x02c2
            boolean r2 = r5.isStateful()
            if (r2 == 0) goto L_0x02b1
            int r2 = r5.getDefaultColor()
            r0.defaultStrokeColor = r2
            int[] r2 = new int[r12]
            r6 = 0
            r2[r6] = r4
            int r2 = r5.getColorForState(r2, r10)
            r0.disabledColor = r2
            int[] r2 = new int[r3]
            r2 = {16843623, 16842910} // fill-array
            int r2 = r5.getColorForState(r2, r10)
            r0.hoveredStrokeColor = r2
            int[] r2 = new int[r3]
            r2 = {16842908, 16842910} // fill-array
            int r2 = r5.getColorForState(r2, r10)
            r0.focusedStrokeColor = r2
            goto L_0x02bf
        L_0x02b1:
            int r2 = r0.focusedStrokeColor
            int r4 = r5.getDefaultColor()
            if (r2 == r4) goto L_0x02bf
            int r2 = r5.getDefaultColor()
            r0.focusedStrokeColor = r2
        L_0x02bf:
            r32.updateTextInputBoxState()
        L_0x02c2:
            r2 = 13
            boolean r4 = r1.hasValue(r2)
            if (r4 == 0) goto L_0x02d7
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r15, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            android.content.res.ColorStateList r4 = r0.strokeErrorColor
            if (r4 == r2) goto L_0x02d7
            r0.strokeErrorColor = r2
            r32.updateTextInputBoxState()
        L_0x02d7:
            r2 = 42
            int r4 = r1.getResourceId(r2, r10)
            if (r4 == r10) goto L_0x02f6
            r4 = 0
            int r2 = r1.getResourceId(r2, r4)
            r14.setCollapsedTextAppearance(r2)
            android.content.res.ColorStateList r2 = r14.collapsedTextColor
            r0.focusedTextColor = r2
            android.widget.EditText r2 = r0.editText
            if (r2 == 0) goto L_0x02f7
            r0.updateLabelState(r4, r4)
            r32.updateInputLayoutMargins()
            goto L_0x02f7
        L_0x02f6:
            r4 = 0
        L_0x02f7:
            r2 = 33
            int r2 = r1.getResourceId(r2, r4)
            r5 = 28
            java.lang.CharSequence r5 = r1.getText(r5)
            r6 = 29
            boolean r6 = r1.getBoolean(r6, r4)
            android.content.Context r7 = r32.getContext()
            android.view.LayoutInflater r7 = android.view.LayoutInflater.from(r7)
            r8 = 2131624080(0x7f0e0090, float:1.887533E38)
            r9 = r19
            android.view.View r7 = r7.inflate(r8, r9, r4)
            com.google.android.material.internal.CheckableImageButton r7 = (com.google.android.material.internal.CheckableImageButton) r7
            r0.errorIconView = r7
            r8 = 2131429034(0x7f0b06aa, float:1.847973E38)
            r7.setId(r8)
            r8 = 8
            r7.setVisibility(r8)
            boolean r8 = com.google.android.material.resources.MaterialResources.isFontScaleAtLeast1_3(r15)
            if (r8 == 0) goto L_0x0338
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r8 = (android.view.ViewGroup.MarginLayoutParams) r8
            r8.setMarginStart(r4)
        L_0x0338:
            r4 = 30
            boolean r8 = r1.hasValue(r4)
            if (r8 == 0) goto L_0x0356
            android.graphics.drawable.Drawable r4 = r1.getDrawable(r4)
            r7.setImageDrawable(r4)
            if (r4 == 0) goto L_0x0352
            java.util.Objects.requireNonNull(r11)
            boolean r4 = r11.errorEnabled
            if (r4 == 0) goto L_0x0352
            r4 = r12
            goto L_0x0353
        L_0x0352:
            r4 = 0
        L_0x0353:
            r0.setErrorIconVisible(r4)
        L_0x0356:
            r4 = 31
            boolean r8 = r1.hasValue(r4)
            if (r8 == 0) goto L_0x037a
            android.content.res.ColorStateList r4 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r15, (androidx.appcompat.widget.TintTypedArray) r1, (int) r4)
            r0.errorIconTintList = r4
            android.graphics.drawable.Drawable r8 = r7.getDrawable()
            if (r8 == 0) goto L_0x0371
            android.graphics.drawable.Drawable r8 = r8.mutate()
            r8.setTintList(r4)
        L_0x0371:
            android.graphics.drawable.Drawable r4 = r7.getDrawable()
            if (r4 == r8) goto L_0x037a
            r7.setImageDrawable(r8)
        L_0x037a:
            r4 = 32
            boolean r8 = r1.hasValue(r4)
            r12 = 0
            if (r8 == 0) goto L_0x03a1
            int r4 = r1.getInt(r4, r10)
            android.graphics.PorterDuff$Mode r4 = com.google.android.material.internal.ViewUtils.parseTintMode(r4, r12)
            android.graphics.drawable.Drawable r8 = r7.getDrawable()
            if (r8 == 0) goto L_0x0398
            android.graphics.drawable.Drawable r8 = r8.mutate()
            r8.setTintMode(r4)
        L_0x0398:
            android.graphics.drawable.Drawable r4 = r7.getDrawable()
            if (r4 == r8) goto L_0x03a1
            r7.setImageDrawable(r8)
        L_0x03a1:
            android.content.res.Resources r4 = r32.getResources()
            r8 = 2131952336(0x7f1302d0, float:1.9541112E38)
            java.lang.CharSequence r4 = r4.getText(r8)
            r7.setContentDescription(r4)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r4 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.setImportantForAccessibility(r7, r3)
            r4 = 0
            r7.setClickable(r4)
            r7.pressable = r4
            r7.setFocusable(r4)
            r8 = 38
            int r8 = r1.getResourceId(r8, r4)
            r3 = 37
            boolean r3 = r1.getBoolean(r3, r4)
            r12 = 36
            java.lang.CharSequence r12 = r1.getText(r12)
            r10 = 50
            int r10 = r1.getResourceId(r10, r4)
            r4 = 49
            java.lang.CharSequence r4 = r1.getText(r4)
            r22 = r14
            r14 = 53
            r23 = r10
            r10 = 0
            int r14 = r1.getResourceId(r14, r10)
            r10 = 52
            java.lang.CharSequence r10 = r1.getText(r10)
            r24 = r14
            r14 = 63
            r25 = r10
            r10 = 0
            int r14 = r1.getResourceId(r14, r10)
            r10 = 62
            java.lang.CharSequence r10 = r1.getText(r10)
            r26 = r14
            r14 = 16
            r27 = r10
            r10 = 0
            boolean r14 = r1.getBoolean(r14, r10)
            r10 = 17
            r28 = r14
            r14 = -1
            int r10 = r1.getInt(r10, r14)
            int r14 = r0.counterMaxLength
            if (r14 == r10) goto L_0x0436
            if (r10 <= 0) goto L_0x041a
            r0.counterMaxLength = r10
            goto L_0x041d
        L_0x041a:
            r10 = -1
            r0.counterMaxLength = r10
        L_0x041d:
            boolean r10 = r0.counterEnabled
            if (r10 == 0) goto L_0x0436
            androidx.appcompat.widget.AppCompatTextView r10 = r0.counterView
            if (r10 == 0) goto L_0x0436
            android.widget.EditText r10 = r0.editText
            if (r10 != 0) goto L_0x042b
            r10 = 0
            goto L_0x0433
        L_0x042b:
            android.text.Editable r10 = r10.getText()
            int r10 = r10.length()
        L_0x0433:
            r0.updateCounter(r10)
        L_0x0436:
            r10 = 20
            r14 = 0
            int r10 = r1.getResourceId(r10, r14)
            r0.counterTextAppearance = r10
            r10 = 18
            int r10 = r1.getResourceId(r10, r14)
            r0.counterOverflowTextAppearance = r10
            android.content.Context r10 = r32.getContext()
            android.view.LayoutInflater r10 = android.view.LayoutInflater.from(r10)
            r29 = r4
            r4 = 2131624081(0x7f0e0091, float:1.8875332E38)
            r30 = r5
            r5 = r20
            android.view.View r4 = r10.inflate(r4, r5, r14)
            com.google.android.material.internal.CheckableImageButton r4 = (com.google.android.material.internal.CheckableImageButton) r4
            r0.startIconView = r4
            r10 = 8
            r4.setVisibility(r10)
            boolean r10 = com.google.android.material.resources.MaterialResources.isFontScaleAtLeast1_3(r15)
            if (r10 == 0) goto L_0x0474
            android.view.ViewGroup$LayoutParams r10 = r4.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r10 = (android.view.ViewGroup.MarginLayoutParams) r10
            r10.setMarginEnd(r14)
        L_0x0474:
            android.view.View$OnLongClickListener r10 = r0.startIconOnLongClickListener
            r14 = 0
            r4.setOnClickListener(r14)
            setIconClickable(r4, r10)
            r0.startIconOnLongClickListener = r14
            r4.setOnLongClickListener(r14)
            setIconClickable(r4, r14)
            r10 = 59
            boolean r14 = r1.hasValue(r10)
            if (r14 == 0) goto L_0x051d
            android.graphics.drawable.Drawable r10 = r1.getDrawable(r10)
            r4.setImageDrawable(r10)
            if (r10 == 0) goto L_0x04c2
            boolean r10 = r0.hasStartIconTintList
            android.content.res.ColorStateList r14 = r0.startIconTintList
            r20 = r2
            boolean r2 = r0.hasStartIconTintMode
            r31 = r6
            android.graphics.PorterDuff$Mode r6 = r0.startIconTintMode
            applyIconTint(r4, r10, r14, r2, r6)
            int r2 = r4.getVisibility()
            if (r2 != 0) goto L_0x04ae
            r2 = 1
            r6 = 1
            goto L_0x04b0
        L_0x04ae:
            r2 = 1
            r6 = 0
        L_0x04b0:
            if (r6 == r2) goto L_0x04bc
            r2 = 0
            r4.setVisibility(r2)
            r32.updatePrefixTextViewPadding()
            r32.updateDummyDrawables()
        L_0x04bc:
            android.content.res.ColorStateList r2 = r0.startIconTintList
            r0.refreshIconDrawableState(r4, r2)
            goto L_0x04f6
        L_0x04c2:
            r20 = r2
            r31 = r6
            int r2 = r4.getVisibility()
            if (r2 != 0) goto L_0x04ce
            r6 = 1
            goto L_0x04cf
        L_0x04ce:
            r6 = 0
        L_0x04cf:
            if (r6 == 0) goto L_0x04dc
            r2 = 8
            r4.setVisibility(r2)
            r32.updatePrefixTextViewPadding()
            r32.updateDummyDrawables()
        L_0x04dc:
            android.view.View$OnLongClickListener r2 = r0.startIconOnLongClickListener
            r6 = 0
            r4.setOnClickListener(r6)
            setIconClickable(r4, r2)
            r0.startIconOnLongClickListener = r6
            r4.setOnLongClickListener(r6)
            setIconClickable(r4, r6)
            java.lang.CharSequence r2 = r4.getContentDescription()
            if (r2 == 0) goto L_0x04f6
            r4.setContentDescription(r6)
        L_0x04f6:
            r2 = 58
            boolean r6 = r1.hasValue(r2)
            if (r6 == 0) goto L_0x050b
            java.lang.CharSequence r2 = r1.getText(r2)
            java.lang.CharSequence r6 = r4.getContentDescription()
            if (r6 == r2) goto L_0x050b
            r4.setContentDescription(r2)
        L_0x050b:
            r2 = 57
            r6 = 1
            boolean r2 = r1.getBoolean(r2, r6)
            boolean r6 = r4.checkable
            if (r6 == r2) goto L_0x0521
            r4.checkable = r2
            r2 = 0
            r4.sendAccessibilityEvent(r2)
            goto L_0x0521
        L_0x051d:
            r20 = r2
            r31 = r6
        L_0x0521:
            r2 = 60
            boolean r6 = r1.hasValue(r2)
            if (r6 == 0) goto L_0x053d
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r15, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            android.content.res.ColorStateList r6 = r0.startIconTintList
            if (r6 == r2) goto L_0x053d
            r0.startIconTintList = r2
            r6 = 1
            r0.hasStartIconTintList = r6
            boolean r10 = r0.hasStartIconTintMode
            android.graphics.PorterDuff$Mode r14 = r0.startIconTintMode
            applyIconTint(r4, r6, r2, r10, r14)
        L_0x053d:
            r2 = 61
            boolean r6 = r1.hasValue(r2)
            if (r6 == 0) goto L_0x055f
            r6 = -1
            int r2 = r1.getInt(r2, r6)
            r6 = 0
            android.graphics.PorterDuff$Mode r2 = com.google.android.material.internal.ViewUtils.parseTintMode(r2, r6)
            android.graphics.PorterDuff$Mode r6 = r0.startIconTintMode
            if (r6 == r2) goto L_0x055f
            r0.startIconTintMode = r2
            r6 = 1
            r0.hasStartIconTintMode = r6
            boolean r10 = r0.hasStartIconTintList
            android.content.res.ColorStateList r14 = r0.startIconTintList
            applyIconTint(r4, r10, r14, r6, r2)
        L_0x055f:
            r2 = 6
            r6 = 0
            int r2 = r1.getInt(r2, r6)
            int r6 = r0.boxBackgroundMode
            if (r2 != r6) goto L_0x056a
            goto L_0x0573
        L_0x056a:
            r0.boxBackgroundMode = r2
            android.widget.EditText r2 = r0.editText
            if (r2 == 0) goto L_0x0573
            r32.onApplyBoxBackgroundMode()
        L_0x0573:
            android.content.Context r2 = r32.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            r6 = r21
            r10 = 2131624080(0x7f0e0090, float:1.887533E38)
            r14 = 0
            android.view.View r2 = r2.inflate(r10, r6, r14)
            com.google.android.material.internal.CheckableImageButton r2 = (com.google.android.material.internal.CheckableImageButton) r2
            r0.endIconView = r2
            r6.addView(r2)
            r10 = 8
            r2.setVisibility(r10)
            boolean r10 = com.google.android.material.resources.MaterialResources.isFontScaleAtLeast1_3(r15)
            if (r10 == 0) goto L_0x05a0
            android.view.ViewGroup$LayoutParams r10 = r2.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r10 = (android.view.ViewGroup.MarginLayoutParams) r10
            r10.setMarginStart(r14)
        L_0x05a0:
            r10 = 24
            int r10 = r1.getResourceId(r10, r14)
            com.google.android.material.textfield.CustomEndIconDelegate r14 = new com.google.android.material.textfield.CustomEndIconDelegate
            r14.<init>(r0, r10)
            r34 = r8
            r8 = -1
            r13.append(r8, r14)
            com.google.android.material.textfield.NoEndIconDelegate r8 = new com.google.android.material.textfield.NoEndIconDelegate
            r8.<init>(r0)
            r14 = 0
            r13.append(r14, r8)
            com.google.android.material.textfield.PasswordToggleEndIconDelegate r8 = new com.google.android.material.textfield.PasswordToggleEndIconDelegate
            if (r10 != 0) goto L_0x05c7
            r21 = r11
            r11 = 45
            int r11 = r1.getResourceId(r11, r14)
            goto L_0x05ca
        L_0x05c7:
            r21 = r11
            r11 = r10
        L_0x05ca:
            r8.<init>(r0, r11)
            r11 = 1
            r13.append(r11, r8)
            com.google.android.material.textfield.ClearTextEndIconDelegate r8 = new com.google.android.material.textfield.ClearTextEndIconDelegate
            r8.<init>(r0, r10)
            r11 = 2
            r13.append(r11, r8)
            com.google.android.material.textfield.DropdownMenuEndIconDelegate r8 = new com.google.android.material.textfield.DropdownMenuEndIconDelegate
            r8.<init>(r0, r10)
            r10 = 3
            r13.append(r10, r8)
            r8 = 25
            boolean r10 = r1.hasValue(r8)
            r11 = 46
            if (r10 == 0) goto L_0x061b
            r10 = 0
            int r8 = r1.getInt(r8, r10)
            r0.setEndIconMode(r8)
            r8 = 23
            boolean r10 = r1.hasValue(r8)
            if (r10 == 0) goto L_0x0604
            java.lang.CharSequence r8 = r1.getText(r8)
            r0.setEndIconContentDescription(r8)
        L_0x0604:
            r8 = 22
            r10 = 1
            boolean r8 = r1.getBoolean(r8, r10)
            java.util.Objects.requireNonNull(r2)
            boolean r10 = r2.checkable
            if (r10 == r8) goto L_0x0619
            r2.checkable = r8
            r8 = 0
            r2.sendAccessibilityEvent(r8)
            goto L_0x0668
        L_0x0619:
            r8 = 0
            goto L_0x0668
        L_0x061b:
            r8 = 0
            boolean r2 = r1.hasValue(r11)
            if (r2 == 0) goto L_0x0668
            boolean r2 = r1.getBoolean(r11, r8)
            r0.setEndIconMode(r2)
            r2 = 44
            java.lang.CharSequence r2 = r1.getText(r2)
            r0.setEndIconContentDescription(r2)
            r2 = 47
            boolean r8 = r1.hasValue(r2)
            if (r8 == 0) goto L_0x064a
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r15, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            android.content.res.ColorStateList r8 = r0.endIconTintList
            if (r8 == r2) goto L_0x064a
            r0.endIconTintList = r2
            r2 = 1
            r0.hasEndIconTintList = r2
            r32.applyEndIconTint()
        L_0x064a:
            r2 = 48
            boolean r8 = r1.hasValue(r2)
            if (r8 == 0) goto L_0x0668
            r8 = -1
            int r2 = r1.getInt(r2, r8)
            r8 = 0
            android.graphics.PorterDuff$Mode r2 = com.google.android.material.internal.ViewUtils.parseTintMode(r2, r8)
            android.graphics.PorterDuff$Mode r8 = r0.endIconTintMode
            if (r8 == r2) goto L_0x0668
            r0.endIconTintMode = r2
            r2 = 1
            r0.hasEndIconTintMode = r2
            r32.applyEndIconTint()
        L_0x0668:
            boolean r2 = r1.hasValue(r11)
            if (r2 != 0) goto L_0x06a4
            r2 = 26
            boolean r8 = r1.hasValue(r2)
            if (r8 == 0) goto L_0x0686
            android.content.res.ColorStateList r2 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r15, (androidx.appcompat.widget.TintTypedArray) r1, (int) r2)
            android.content.res.ColorStateList r8 = r0.endIconTintList
            if (r8 == r2) goto L_0x0686
            r0.endIconTintList = r2
            r2 = 1
            r0.hasEndIconTintList = r2
            r32.applyEndIconTint()
        L_0x0686:
            r2 = 27
            boolean r8 = r1.hasValue(r2)
            if (r8 == 0) goto L_0x06a4
            r8 = -1
            int r2 = r1.getInt(r2, r8)
            r8 = 0
            android.graphics.PorterDuff$Mode r2 = com.google.android.material.internal.ViewUtils.parseTintMode(r2, r8)
            android.graphics.PorterDuff$Mode r8 = r0.endIconTintMode
            if (r8 == r2) goto L_0x06a4
            r0.endIconTintMode = r2
            r2 = 1
            r0.hasEndIconTintMode = r2
            r32.applyEndIconTint()
        L_0x06a4:
            androidx.appcompat.widget.AppCompatTextView r2 = new androidx.appcompat.widget.AppCompatTextView
            r2.<init>(r15)
            r0.prefixTextView = r2
            r8 = 2131429044(0x7f0b06b4, float:1.847975E38)
            r2.setId(r8)
            android.widget.FrameLayout$LayoutParams r8 = new android.widget.FrameLayout$LayoutParams
            r10 = -2
            r8.<init>(r10, r10)
            r2.setLayoutParams(r8)
            r8 = 1
            androidx.core.view.ViewCompat.Api19Impl.setAccessibilityLiveRegion(r2, r8)
            r5.addView(r4)
            r5.addView(r2)
            androidx.appcompat.widget.AppCompatTextView r4 = new androidx.appcompat.widget.AppCompatTextView
            r4.<init>(r15)
            r0.suffixTextView = r4
            r5 = 2131429045(0x7f0b06b5, float:1.8479752E38)
            r4.setId(r5)
            android.widget.FrameLayout$LayoutParams r5 = new android.widget.FrameLayout$LayoutParams
            r8 = 80
            r5.<init>(r10, r10, r8)
            r4.setLayoutParams(r5)
            r5 = 1
            androidx.core.view.ViewCompat.Api19Impl.setAccessibilityLiveRegion(r4, r5)
            r9.addView(r4)
            r9.addView(r7)
            r9.addView(r6)
            r0.setHelperTextEnabled(r3)
            r0.setHelperText(r12)
            r5 = r34
            r3 = r21
            r3.helperTextTextAppearance = r5
            androidx.appcompat.widget.AppCompatTextView r6 = r3.helperTextView
            if (r6 == 0) goto L_0x06fb
            r6.setTextAppearance(r5)
        L_0x06fb:
            r5 = r31
            r0.setErrorEnabled(r5)
            r5 = r20
            r3.errorTextAppearance = r5
            androidx.appcompat.widget.AppCompatTextView r6 = r3.errorView
            if (r6 == 0) goto L_0x070d
            com.google.android.material.textfield.TextInputLayout r7 = r3.textInputView
            r7.setTextAppearanceCompatWithErrorFallback(r6, r5)
        L_0x070d:
            r5 = r30
            r3.errorViewContentDescription = r5
            androidx.appcompat.widget.AppCompatTextView r6 = r3.errorView
            if (r6 == 0) goto L_0x0718
            r6.setContentDescription(r5)
        L_0x0718:
            r5 = r29
            r0.setPlaceholderText(r5)
            r5 = r23
            r0.placeholderTextAppearance = r5
            androidx.appcompat.widget.AppCompatTextView r6 = r0.placeholderTextView
            if (r6 == 0) goto L_0x0728
            r6.setTextAppearance(r5)
        L_0x0728:
            boolean r5 = android.text.TextUtils.isEmpty(r25)
            if (r5 == 0) goto L_0x0730
            r14 = 0
            goto L_0x0732
        L_0x0730:
            r14 = r25
        L_0x0732:
            r0.prefixText = r14
            r5 = r25
            r2.setText(r5)
            r32.updatePrefixTextVisibility()
            r5 = r24
            r2.setTextAppearance(r5)
            boolean r5 = android.text.TextUtils.isEmpty(r27)
            if (r5 == 0) goto L_0x0749
            r14 = 0
            goto L_0x074b
        L_0x0749:
            r14 = r27
        L_0x074b:
            r0.suffixText = r14
            r5 = r27
            r4.setText(r5)
            r32.updateSuffixTextVisibility()
            r5 = r26
            r4.setTextAppearance(r5)
            r5 = 34
            boolean r6 = r1.hasValue(r5)
            if (r6 == 0) goto L_0x0771
            android.content.res.ColorStateList r5 = r1.getColorStateList(r5)
            r3.errorViewTextColor = r5
            androidx.appcompat.widget.AppCompatTextView r6 = r3.errorView
            if (r6 == 0) goto L_0x0771
            if (r5 == 0) goto L_0x0771
            r6.setTextColor(r5)
        L_0x0771:
            r5 = 39
            boolean r5 = r1.hasValue(r5)
            if (r5 == 0) goto L_0x078a
            r5 = 39
            android.content.res.ColorStateList r5 = r1.getColorStateList(r5)
            r3.helperTextViewTextColor = r5
            androidx.appcompat.widget.AppCompatTextView r6 = r3.helperTextView
            if (r6 == 0) goto L_0x078a
            if (r5 == 0) goto L_0x078a
            r6.setTextColor(r5)
        L_0x078a:
            r5 = 43
            boolean r5 = r1.hasValue(r5)
            if (r5 == 0) goto L_0x07af
            r5 = 43
            android.content.res.ColorStateList r5 = r1.getColorStateList(r5)
            android.content.res.ColorStateList r6 = r0.focusedTextColor
            if (r6 == r5) goto L_0x07af
            android.content.res.ColorStateList r6 = r0.defaultHintTextColor
            if (r6 != 0) goto L_0x07a5
            r6 = r22
            r6.setCollapsedTextColor(r5)
        L_0x07a5:
            r0.focusedTextColor = r5
            android.widget.EditText r5 = r0.editText
            if (r5 == 0) goto L_0x07af
            r5 = 0
            r0.updateLabelState(r5, r5)
        L_0x07af:
            r5 = 21
            boolean r5 = r1.hasValue(r5)
            if (r5 == 0) goto L_0x07c6
            r5 = 21
            android.content.res.ColorStateList r5 = r1.getColorStateList(r5)
            android.content.res.ColorStateList r6 = r0.counterTextColor
            if (r6 == r5) goto L_0x07c6
            r0.counterTextColor = r5
            r32.updateCounterTextAppearanceAndColor()
        L_0x07c6:
            r5 = 19
            boolean r5 = r1.hasValue(r5)
            if (r5 == 0) goto L_0x07dd
            r5 = 19
            android.content.res.ColorStateList r5 = r1.getColorStateList(r5)
            android.content.res.ColorStateList r6 = r0.counterOverflowTextColor
            if (r6 == r5) goto L_0x07dd
            r0.counterOverflowTextColor = r5
            r32.updateCounterTextAppearanceAndColor()
        L_0x07dd:
            r5 = 51
            boolean r5 = r1.hasValue(r5)
            if (r5 == 0) goto L_0x07fa
            r5 = 51
            android.content.res.ColorStateList r5 = r1.getColorStateList(r5)
            android.content.res.ColorStateList r6 = r0.placeholderTextColor
            if (r6 == r5) goto L_0x07fa
            r0.placeholderTextColor = r5
            androidx.appcompat.widget.AppCompatTextView r6 = r0.placeholderTextView
            if (r6 == 0) goto L_0x07fa
            if (r5 == 0) goto L_0x07fa
            r6.setTextColor(r5)
        L_0x07fa:
            r5 = 54
            boolean r5 = r1.hasValue(r5)
            if (r5 == 0) goto L_0x080b
            r5 = 54
            android.content.res.ColorStateList r5 = r1.getColorStateList(r5)
            r2.setTextColor(r5)
        L_0x080b:
            r2 = 64
            boolean r2 = r1.hasValue(r2)
            if (r2 == 0) goto L_0x081c
            r2 = 64
            android.content.res.ColorStateList r2 = r1.getColorStateList(r2)
            r4.setTextColor(r2)
        L_0x081c:
            boolean r2 = r0.counterEnabled
            r4 = r28
            if (r2 == r4) goto L_0x087d
            if (r4 == 0) goto L_0x0871
            androidx.appcompat.widget.AppCompatTextView r2 = new androidx.appcompat.widget.AppCompatTextView
            android.content.Context r5 = r32.getContext()
            r2.<init>(r5)
            r0.counterView = r2
            r5 = 2131429040(0x7f0b06b0, float:1.8479741E38)
            r2.setId(r5)
            androidx.appcompat.widget.AppCompatTextView r2 = r0.counterView
            r5 = 1
            r2.setMaxLines(r5)
            androidx.appcompat.widget.AppCompatTextView r2 = r0.counterView
            r5 = 2
            r3.addIndicator(r2, r5)
            androidx.appcompat.widget.AppCompatTextView r2 = r0.counterView
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r2 = (android.view.ViewGroup.MarginLayoutParams) r2
            android.content.res.Resources r3 = r32.getResources()
            r5 = 2131166569(0x7f070569, float:1.7947387E38)
            int r3 = r3.getDimensionPixelOffset(r5)
            r2.setMarginStart(r3)
            r32.updateCounterTextAppearanceAndColor()
            androidx.appcompat.widget.AppCompatTextView r2 = r0.counterView
            if (r2 == 0) goto L_0x086f
            android.widget.EditText r2 = r0.editText
            if (r2 != 0) goto L_0x0864
            r5 = 0
            goto L_0x086c
        L_0x0864:
            android.text.Editable r2 = r2.getText()
            int r5 = r2.length()
        L_0x086c:
            r0.updateCounter(r5)
        L_0x086f:
            r5 = 2
            goto L_0x087a
        L_0x0871:
            androidx.appcompat.widget.AppCompatTextView r2 = r0.counterView
            r5 = 2
            r3.removeIndicator(r2, r5)
            r2 = 0
            r0.counterView = r2
        L_0x087a:
            r0.counterEnabled = r4
            goto L_0x087e
        L_0x087d:
            r5 = 2
        L_0x087e:
            r2 = 1
            r3 = 0
            boolean r3 = r1.getBoolean(r3, r2)
            r0.setEnabled(r3)
            r1.recycle()
            androidx.core.view.ViewCompat.Api16Impl.setImportantForAccessibility(r0, r5)
            androidx.core.view.ViewCompat.Api26Impl.setImportantForAutofill(r0, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public static void applyIconTint(CheckableImageButton checkableImageButton, boolean z, ColorStateList colorStateList, boolean z2, PorterDuff.Mode mode) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (drawable != null && (z || z2)) {
            drawable = drawable.mutate();
            if (z) {
                drawable.setTintList(colorStateList);
            }
            if (z2) {
                drawable.setTintMode(mode);
            }
        }
        if (checkableImageButton.getDrawable() != drawable) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    public static void recursiveSetEnabled(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) childAt, z);
            }
        }
    }

    public boolean cutoutIsOpen() {
        if (cutoutEnabled()) {
            CutoutDrawable cutoutDrawable = (CutoutDrawable) this.boxBackground;
            Objects.requireNonNull(cutoutDrawable);
            if (!cutoutDrawable.cutoutBounds.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.hintEnabled) {
            this.collapsingTextHelper.draw(canvas);
        }
        MaterialShapeDrawable materialShapeDrawable = this.boxUnderline;
        if (materialShapeDrawable != null) {
            Rect bounds = materialShapeDrawable.getBounds();
            bounds.top = bounds.bottom - this.boxStrokeWidthPx;
            this.boxUnderline.draw(canvas);
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        boolean z3;
        boolean z4;
        int i5;
        boolean z5;
        int i6;
        boolean z6;
        super.onLayout(z, i, i2, i3, i4);
        EditText editText2 = this.editText;
        if (editText2 != null) {
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(this, editText2, rect);
            MaterialShapeDrawable materialShapeDrawable = this.boxUnderline;
            if (materialShapeDrawable != null) {
                int i7 = rect.bottom;
                materialShapeDrawable.setBounds(rect.left, i7 - this.boxStrokeWidthFocusedPx, rect.right, i7);
            }
            if (this.hintEnabled) {
                CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
                float textSize = this.editText.getTextSize();
                Objects.requireNonNull(collapsingTextHelper2);
                if (collapsingTextHelper2.expandedTextSize != textSize) {
                    collapsingTextHelper2.expandedTextSize = textSize;
                    collapsingTextHelper2.recalculate(false);
                }
                int gravity = this.editText.getGravity();
                CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
                int i8 = (gravity & -113) | 48;
                Objects.requireNonNull(collapsingTextHelper3);
                if (collapsingTextHelper3.collapsedTextGravity != i8) {
                    collapsingTextHelper3.collapsedTextGravity = i8;
                    collapsingTextHelper3.recalculate(false);
                }
                CollapsingTextHelper collapsingTextHelper4 = this.collapsingTextHelper;
                Objects.requireNonNull(collapsingTextHelper4);
                if (collapsingTextHelper4.expandedTextGravity != gravity) {
                    collapsingTextHelper4.expandedTextGravity = gravity;
                    collapsingTextHelper4.recalculate(false);
                }
                CollapsingTextHelper collapsingTextHelper5 = this.collapsingTextHelper;
                if (this.editText != null) {
                    Rect rect2 = this.tmpBoundsRect;
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api17Impl.getLayoutDirection(this) == 1) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    rect2.bottom = rect.bottom;
                    int i9 = this.boxBackgroundMode;
                    if (i9 == 1) {
                        int compoundPaddingLeft = this.editText.getCompoundPaddingLeft() + rect.left;
                        if (this.prefixText != null && !z2) {
                            compoundPaddingLeft = (compoundPaddingLeft - this.prefixTextView.getMeasuredWidth()) + this.prefixTextView.getPaddingLeft();
                        }
                        rect2.left = compoundPaddingLeft;
                        rect2.top = rect.top + this.boxCollapsedPaddingTopPx;
                        int compoundPaddingRight = rect.right - this.editText.getCompoundPaddingRight();
                        if (this.prefixText != null && z2) {
                            compoundPaddingRight += this.prefixTextView.getMeasuredWidth() - this.prefixTextView.getPaddingRight();
                        }
                        rect2.right = compoundPaddingRight;
                    } else if (i9 != 2) {
                        int compoundPaddingLeft2 = this.editText.getCompoundPaddingLeft() + rect.left;
                        if (this.prefixText != null && !z2) {
                            compoundPaddingLeft2 = (compoundPaddingLeft2 - this.prefixTextView.getMeasuredWidth()) + this.prefixTextView.getPaddingLeft();
                        }
                        rect2.left = compoundPaddingLeft2;
                        rect2.top = getPaddingTop();
                        int compoundPaddingRight2 = rect.right - this.editText.getCompoundPaddingRight();
                        if (this.prefixText != null && z2) {
                            compoundPaddingRight2 += this.prefixTextView.getMeasuredWidth() - this.prefixTextView.getPaddingRight();
                        }
                        rect2.right = compoundPaddingRight2;
                    } else {
                        rect2.left = this.editText.getPaddingLeft() + rect.left;
                        rect2.top = rect.top - calculateLabelMarginTop();
                        rect2.right = rect.right - this.editText.getPaddingRight();
                    }
                    Objects.requireNonNull(collapsingTextHelper5);
                    int i10 = rect2.left;
                    int i11 = rect2.top;
                    int i12 = rect2.right;
                    int i13 = rect2.bottom;
                    Rect rect3 = collapsingTextHelper5.collapsedBounds;
                    if (rect3.left == i10 && rect3.top == i11 && rect3.right == i12 && rect3.bottom == i13) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (!z3) {
                        rect3.set(i10, i11, i12, i13);
                        collapsingTextHelper5.boundsChanged = true;
                        collapsingTextHelper5.onBoundsChanged();
                    }
                    CollapsingTextHelper collapsingTextHelper6 = this.collapsingTextHelper;
                    if (this.editText != null) {
                        Rect rect4 = this.tmpBoundsRect;
                        Objects.requireNonNull(collapsingTextHelper6);
                        TextPaint textPaint = collapsingTextHelper6.tmpPaint;
                        textPaint.setTextSize(collapsingTextHelper6.expandedTextSize);
                        textPaint.setTypeface(collapsingTextHelper6.expandedTypeface);
                        textPaint.setLetterSpacing(collapsingTextHelper6.expandedLetterSpacing);
                        float f = -collapsingTextHelper6.tmpPaint.ascent();
                        rect4.left = this.editText.getCompoundPaddingLeft() + rect.left;
                        if (this.boxBackgroundMode != 1 || this.editText.getMinLines() > 1) {
                            z4 = false;
                        } else {
                            z4 = true;
                        }
                        if (z4) {
                            i5 = (int) (((float) rect.centerY()) - (f / 2.0f));
                        } else {
                            i5 = rect.top + this.editText.getCompoundPaddingTop();
                        }
                        rect4.top = i5;
                        rect4.right = rect.right - this.editText.getCompoundPaddingRight();
                        if (this.boxBackgroundMode != 1 || this.editText.getMinLines() > 1) {
                            z5 = false;
                        } else {
                            z5 = true;
                        }
                        if (z5) {
                            i6 = (int) (((float) rect4.top) + f);
                        } else {
                            i6 = rect.bottom - this.editText.getCompoundPaddingBottom();
                        }
                        rect4.bottom = i6;
                        int i14 = rect4.left;
                        int i15 = rect4.top;
                        int i16 = rect4.right;
                        Rect rect5 = collapsingTextHelper6.expandedBounds;
                        if (rect5.left == i14 && rect5.top == i15 && rect5.right == i16 && rect5.bottom == i6) {
                            z6 = true;
                        } else {
                            z6 = false;
                        }
                        if (!z6) {
                            rect5.set(i14, i15, i16, i6);
                            collapsingTextHelper6.boundsChanged = true;
                            collapsingTextHelper6.onBoundsChanged();
                        }
                        CollapsingTextHelper collapsingTextHelper7 = this.collapsingTextHelper;
                        Objects.requireNonNull(collapsingTextHelper7);
                        collapsingTextHelper7.recalculate(false);
                        if (cutoutEnabled() && !this.hintExpanded) {
                            openCutout();
                            return;
                        }
                        return;
                    }
                    throw new IllegalStateException();
                }
                throw new IllegalStateException();
            }
        }
    }

    public final void onMeasure(int i, int i2) {
        boolean z;
        EditText editText2;
        int max;
        super.onMeasure(i, i2);
        if (this.editText != null && this.editText.getMeasuredHeight() < (max = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight()))) {
            this.editText.setMinimumHeight(max);
            z = true;
        } else {
            z = false;
        }
        boolean updateDummyDrawables = updateDummyDrawables();
        if (z || updateDummyDrawables) {
            this.editText.post(new Runnable() {
                public final void run() {
                    TextInputLayout.this.editText.requestLayout();
                }
            });
        }
        if (!(this.placeholderTextView == null || (editText2 = this.editText) == null)) {
            this.placeholderTextView.setGravity(editText2.getGravity());
            this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
        }
        updatePrefixTextViewPadding();
        updateSuffixTextViewPadding();
    }

    public final Parcelable onSaveInstanceState() {
        boolean z;
        CharSequence charSequence;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.indicatorViewController.errorShouldBeShown()) {
            savedState.error = getError();
        }
        boolean z2 = true;
        if (this.endIconMode != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z || !this.endIconView.isChecked()) {
            z2 = false;
        }
        savedState.isEndIconChecked = z2;
        savedState.hintText = getHint();
        IndicatorViewController indicatorViewController2 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController2);
        CharSequence charSequence2 = null;
        if (indicatorViewController2.helperTextEnabled) {
            IndicatorViewController indicatorViewController3 = this.indicatorViewController;
            Objects.requireNonNull(indicatorViewController3);
            charSequence = indicatorViewController3.helperText;
        } else {
            charSequence = null;
        }
        savedState.helperText = charSequence;
        if (this.placeholderEnabled) {
            charSequence2 = this.placeholderText;
        }
        savedState.placeholderText = charSequence2;
        return savedState;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0088  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void openCutout() {
        /*
            r13 = this;
            boolean r0 = r13.cutoutEnabled()
            if (r0 != 0) goto L_0x0007
            return
        L_0x0007:
            android.graphics.RectF r0 = r13.tmpRectF
            com.google.android.material.internal.CollapsingTextHelper r1 = r13.collapsingTextHelper
            android.widget.EditText r2 = r13.editText
            int r2 = r2.getWidth()
            android.widget.EditText r3 = r13.editText
            int r3 = r3.getGravity()
            java.util.Objects.requireNonNull(r1)
            java.lang.CharSequence r4 = r1.text
            boolean r4 = r1.calculateIsRtl(r4)
            r1.isRtl = r4
            r5 = 8388613(0x800005, float:1.175495E-38)
            r6 = 1
            r7 = 17
            r8 = 1073741824(0x40000000, float:2.0)
            r9 = 5
            if (r3 == r7) goto L_0x0056
            r10 = r3 & 7
            if (r10 != r6) goto L_0x0032
            goto L_0x0056
        L_0x0032:
            r10 = r3 & r5
            if (r10 == r5) goto L_0x0048
            r10 = r3 & 5
            if (r10 != r9) goto L_0x003b
            goto L_0x0048
        L_0x003b:
            android.graphics.Rect r10 = r1.collapsedBounds
            if (r4 == 0) goto L_0x0045
            int r10 = r10.right
            float r10 = (float) r10
            float r11 = r1.collapsedTextWidth
            goto L_0x005b
        L_0x0045:
            int r10 = r10.left
            goto L_0x004e
        L_0x0048:
            android.graphics.Rect r10 = r1.collapsedBounds
            if (r4 == 0) goto L_0x0050
            int r10 = r10.left
        L_0x004e:
            float r10 = (float) r10
            goto L_0x005c
        L_0x0050:
            int r10 = r10.right
            float r10 = (float) r10
            float r11 = r1.collapsedTextWidth
            goto L_0x005b
        L_0x0056:
            float r10 = (float) r2
            float r10 = r10 / r8
            float r11 = r1.collapsedTextWidth
            float r11 = r11 / r8
        L_0x005b:
            float r10 = r10 - r11
        L_0x005c:
            r0.left = r10
            android.graphics.Rect r11 = r1.collapsedBounds
            int r12 = r11.top
            float r12 = (float) r12
            r0.top = r12
            if (r3 == r7) goto L_0x0088
            r7 = r3 & 7
            if (r7 != r6) goto L_0x006c
            goto L_0x0088
        L_0x006c:
            r2 = r3 & r5
            if (r2 == r5) goto L_0x007e
            r2 = r3 & 5
            if (r2 != r9) goto L_0x0075
            goto L_0x007e
        L_0x0075:
            if (r4 == 0) goto L_0x007a
            int r2 = r11.right
            goto L_0x0086
        L_0x007a:
            float r2 = r1.collapsedTextWidth
            float r2 = r2 + r10
            goto L_0x008e
        L_0x007e:
            if (r4 == 0) goto L_0x0084
            float r2 = r1.collapsedTextWidth
            float r2 = r2 + r10
            goto L_0x008e
        L_0x0084:
            int r2 = r11.right
        L_0x0086:
            float r2 = (float) r2
            goto L_0x008e
        L_0x0088:
            float r2 = (float) r2
            float r2 = r2 / r8
            float r3 = r1.collapsedTextWidth
            float r3 = r3 / r8
            float r2 = r2 + r3
        L_0x008e:
            r0.right = r2
            float r1 = r1.getCollapsedTextHeight()
            float r1 = r1 + r12
            r0.bottom = r1
            float r1 = r0.left
            int r2 = r13.boxLabelCutoutPaddingPx
            float r2 = (float) r2
            float r1 = r1 - r2
            r0.left = r1
            float r1 = r0.right
            float r1 = r1 + r2
            r0.right = r1
            int r1 = r13.getPaddingLeft()
            int r1 = -r1
            float r1 = (float) r1
            int r2 = r13.getPaddingTop()
            int r2 = -r2
            float r2 = (float) r2
            float r3 = r0.height()
            float r3 = r3 / r8
            float r2 = r2 - r3
            int r3 = r13.boxStrokeWidthPx
            float r3 = (float) r3
            float r2 = r2 + r3
            r0.offset(r1, r2)
            com.google.android.material.shape.MaterialShapeDrawable r13 = r13.boxBackground
            com.google.android.material.textfield.CutoutDrawable r13 = (com.google.android.material.textfield.CutoutDrawable) r13
            java.util.Objects.requireNonNull(r13)
            float r1 = r0.left
            float r2 = r0.top
            float r3 = r0.right
            float r0 = r0.bottom
            r13.setCutout(r1, r2, r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.openCutout():void");
    }

    public final void refreshIconDrawableState(CheckableImageButton checkableImageButton, ColorStateList colorStateList) {
        Drawable drawable = checkableImageButton.getDrawable();
        if (checkableImageButton.getDrawable() != null && colorStateList != null && colorStateList.isStateful()) {
            int[] drawableState = getDrawableState();
            int[] drawableState2 = checkableImageButton.getDrawableState();
            int length = drawableState.length;
            int[] copyOf = Arrays.copyOf(drawableState, drawableState.length + drawableState2.length);
            System.arraycopy(drawableState2, 0, copyOf, length, drawableState2.length);
            int colorForState = colorStateList.getColorForState(copyOf, colorStateList.getDefaultColor());
            Drawable mutate = drawable.mutate();
            mutate.setTintList(ColorStateList.valueOf(colorForState));
            checkableImageButton.setImageDrawable(mutate);
        }
    }

    public final void setEnabled(boolean z) {
        recursiveSetEnabled(this, z);
        super.setEnabled(z);
    }

    public final void setEndIconVisible(boolean z) {
        int i;
        if (isEndIconVisible() != z) {
            CheckableImageButton checkableImageButton = this.endIconView;
            if (z) {
                i = 0;
            } else {
                i = 8;
            }
            checkableImageButton.setVisibility(i);
            updateSuffixTextViewPadding();
            updateDummyDrawables();
        }
    }

    public final void setHelperText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            IndicatorViewController indicatorViewController2 = this.indicatorViewController;
            Objects.requireNonNull(indicatorViewController2);
            if (indicatorViewController2.helperTextEnabled) {
                setHelperTextEnabled(false);
                return;
            }
            return;
        }
        IndicatorViewController indicatorViewController3 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController3);
        if (!indicatorViewController3.helperTextEnabled) {
            setHelperTextEnabled(true);
        }
        IndicatorViewController indicatorViewController4 = this.indicatorViewController;
        Objects.requireNonNull(indicatorViewController4);
        indicatorViewController4.cancelCaptionAnimator();
        indicatorViewController4.helperText = charSequence;
        indicatorViewController4.helperTextView.setText(charSequence);
        int i = indicatorViewController4.captionDisplayed;
        if (i != 2) {
            indicatorViewController4.captionToShow = 2;
        }
        indicatorViewController4.updateCaptionViewsVisibility(i, indicatorViewController4.captionToShow, indicatorViewController4.shouldAnimateCaptionView(indicatorViewController4.helperTextView, charSequence));
    }

    public final void updateLabelState(boolean z, boolean z2) {
        boolean z3;
        boolean z4;
        ColorStateList colorStateList;
        AppCompatTextView appCompatTextView;
        ColorStateList colorStateList2;
        int i;
        boolean isEnabled = isEnabled();
        EditText editText2 = this.editText;
        int i2 = 0;
        if (editText2 == null || TextUtils.isEmpty(editText2.getText())) {
            z3 = false;
        } else {
            z3 = true;
        }
        EditText editText3 = this.editText;
        if (editText3 == null || !editText3.hasFocus()) {
            z4 = false;
        } else {
            z4 = true;
        }
        boolean errorShouldBeShown = this.indicatorViewController.errorShouldBeShown();
        ColorStateList colorStateList3 = this.defaultHintTextColor;
        if (colorStateList3 != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList3);
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            ColorStateList colorStateList4 = this.defaultHintTextColor;
            Objects.requireNonNull(collapsingTextHelper2);
            if (collapsingTextHelper2.expandedTextColor != colorStateList4) {
                collapsingTextHelper2.expandedTextColor = colorStateList4;
                collapsingTextHelper2.recalculate(false);
            }
        }
        if (!isEnabled) {
            ColorStateList colorStateList5 = this.defaultHintTextColor;
            if (colorStateList5 != null) {
                i = colorStateList5.getColorForState(new int[]{-16842910}, this.disabledColor);
            } else {
                i = this.disabledColor;
            }
            this.collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf(i));
            CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
            ColorStateList valueOf = ColorStateList.valueOf(i);
            Objects.requireNonNull(collapsingTextHelper3);
            if (collapsingTextHelper3.expandedTextColor != valueOf) {
                collapsingTextHelper3.expandedTextColor = valueOf;
                collapsingTextHelper3.recalculate(false);
            }
        } else if (errorShouldBeShown) {
            CollapsingTextHelper collapsingTextHelper4 = this.collapsingTextHelper;
            IndicatorViewController indicatorViewController2 = this.indicatorViewController;
            Objects.requireNonNull(indicatorViewController2);
            AppCompatTextView appCompatTextView2 = indicatorViewController2.errorView;
            if (appCompatTextView2 != null) {
                colorStateList2 = appCompatTextView2.getTextColors();
            } else {
                colorStateList2 = null;
            }
            collapsingTextHelper4.setCollapsedTextColor(colorStateList2);
        } else if (this.counterOverflowed && (appCompatTextView = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(appCompatTextView.getTextColors());
        } else if (z4 && (colorStateList = this.focusedTextColor) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        if (z3 || !this.expandedHintEnabled || (isEnabled() && z4)) {
            if (z2 || this.hintExpanded) {
                ValueAnimator valueAnimator = this.animator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.animator.cancel();
                }
                if (!z || !this.hintAnimationEnabled) {
                    this.collapsingTextHelper.setExpansionFraction(1.0f);
                } else {
                    animateToExpansionFraction(1.0f);
                }
                this.hintExpanded = false;
                if (cutoutEnabled()) {
                    openCutout();
                }
                EditText editText4 = this.editText;
                if (editText4 != null) {
                    i2 = editText4.getText().length();
                }
                updatePlaceholderText(i2);
                updatePrefixTextVisibility();
                updateSuffixTextVisibility();
            }
        } else if (z2 || !this.hintExpanded) {
            ValueAnimator valueAnimator2 = this.animator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.animator.cancel();
            }
            if (!z || !this.hintAnimationEnabled) {
                this.collapsingTextHelper.setExpansionFraction(0.0f);
            } else {
                animateToExpansionFraction(0.0f);
            }
            if (cutoutEnabled()) {
                CutoutDrawable cutoutDrawable = (CutoutDrawable) this.boxBackground;
                Objects.requireNonNull(cutoutDrawable);
                if ((!cutoutDrawable.cutoutBounds.isEmpty()) && cutoutEnabled()) {
                    CutoutDrawable cutoutDrawable2 = (CutoutDrawable) this.boxBackground;
                    Objects.requireNonNull(cutoutDrawable2);
                    cutoutDrawable2.setCutout(0.0f, 0.0f, 0.0f, 0.0f);
                }
            }
            this.hintExpanded = true;
            AppCompatTextView appCompatTextView3 = this.placeholderTextView;
            if (appCompatTextView3 != null && this.placeholderEnabled) {
                appCompatTextView3.setText((CharSequence) null);
                TransitionManager.beginDelayedTransition(this.inputFrame, this.placeholderFadeOut);
                this.placeholderTextView.setVisibility(4);
            }
            updatePrefixTextVisibility();
            updateSuffixTextVisibility();
        }
    }

    public final boolean isHintExpanded() {
        return this.hintExpanded;
    }
}
