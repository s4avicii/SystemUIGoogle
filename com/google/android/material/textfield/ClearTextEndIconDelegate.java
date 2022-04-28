package com.google.android.material.textfield;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Objects;

public final class ClearTextEndIconDelegate extends EndIconDelegate {
    public final C20921 clearTextEndIconTextWatcher = new TextWatcher() {
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void afterTextChanged(Editable editable) {
            TextInputLayout textInputLayout = ClearTextEndIconDelegate.this.textInputLayout;
            Objects.requireNonNull(textInputLayout);
            if (textInputLayout.suffixText == null) {
                ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
                clearTextEndIconDelegate.animateIcon(ClearTextEndIconDelegate.access$000(clearTextEndIconDelegate));
            }
        }
    };
    public final C20943 clearTextOnEditTextAttachedListener = new TextInputLayout.OnEditTextAttachedListener() {
        public final void onEditTextAttached(TextInputLayout textInputLayout) {
            Objects.requireNonNull(textInputLayout);
            EditText editText = textInputLayout.editText;
            textInputLayout.setEndIconVisible(ClearTextEndIconDelegate.access$000(ClearTextEndIconDelegate.this));
            CheckableImageButton checkableImageButton = textInputLayout.endIconView;
            Objects.requireNonNull(checkableImageButton);
            if (checkableImageButton.checkable) {
                checkableImageButton.checkable = false;
                checkableImageButton.sendAccessibilityEvent(0);
            }
            editText.setOnFocusChangeListener(ClearTextEndIconDelegate.this.onFocusChangeListener);
            ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
            clearTextEndIconDelegate.endIconView.setOnFocusChangeListener(clearTextEndIconDelegate.onFocusChangeListener);
            editText.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
            editText.addTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
        }
    };
    public final C20954 endIconChangedListener = new TextInputLayout.OnEndIconChangedListener() {
        public final void onEndIconChanged(TextInputLayout textInputLayout, int i) {
            Objects.requireNonNull(textInputLayout);
            final EditText editText = textInputLayout.editText;
            if (editText != null && i == 2) {
                editText.post(new Runnable() {
                    public final void run() {
                        editText.removeTextChangedListener(ClearTextEndIconDelegate.this.clearTextEndIconTextWatcher);
                    }
                });
                if (editText.getOnFocusChangeListener() == ClearTextEndIconDelegate.this.onFocusChangeListener) {
                    editText.setOnFocusChangeListener((View.OnFocusChangeListener) null);
                }
                View.OnFocusChangeListener onFocusChangeListener = ClearTextEndIconDelegate.this.endIconView.getOnFocusChangeListener();
                ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
                if (onFocusChangeListener == clearTextEndIconDelegate.onFocusChangeListener) {
                    clearTextEndIconDelegate.endIconView.setOnFocusChangeListener((View.OnFocusChangeListener) null);
                }
            }
        }
    };
    public AnimatorSet iconInAnim;
    public ValueAnimator iconOutAnim;
    public final C20932 onFocusChangeListener = new View.OnFocusChangeListener() {
        public final void onFocusChange(View view, boolean z) {
            ClearTextEndIconDelegate clearTextEndIconDelegate = ClearTextEndIconDelegate.this;
            clearTextEndIconDelegate.animateIcon(ClearTextEndIconDelegate.access$000(clearTextEndIconDelegate));
        }
    };

    public final void animateIcon(boolean z) {
        boolean z2;
        if (this.textInputLayout.isEndIconVisible() == z) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z && !this.iconInAnim.isRunning()) {
            this.iconOutAnim.cancel();
            this.iconInAnim.start();
            if (z2) {
                this.iconInAnim.end();
            }
        } else if (!z) {
            this.iconInAnim.cancel();
            this.iconOutAnim.start();
            if (z2) {
                this.iconOutAnim.end();
            }
        }
    }

    public final void initialize() {
        TextInputLayout textInputLayout = this.textInputLayout;
        int i = this.customEndIcon;
        if (i == 0) {
            i = C1777R.C1778drawable.mtrl_ic_cancel;
        }
        textInputLayout.setEndIconDrawable(i);
        TextInputLayout textInputLayout2 = this.textInputLayout;
        textInputLayout2.setEndIconContentDescription(textInputLayout2.getResources().getText(C1777R.string.clear_text_end_icon_content_description));
        TextInputLayout textInputLayout3 = this.textInputLayout;
        C20975 r1 = new View.OnClickListener() {
            public final void onClick(View view) {
                TextInputLayout textInputLayout = ClearTextEndIconDelegate.this.textInputLayout;
                Objects.requireNonNull(textInputLayout);
                Editable text = textInputLayout.editText.getText();
                if (text != null) {
                    text.clear();
                }
                TextInputLayout textInputLayout2 = ClearTextEndIconDelegate.this.textInputLayout;
                Objects.requireNonNull(textInputLayout2);
                textInputLayout2.refreshIconDrawableState(textInputLayout2.endIconView, textInputLayout2.endIconTintList);
            }
        };
        Objects.requireNonNull(textInputLayout3);
        CheckableImageButton checkableImageButton = textInputLayout3.endIconView;
        View.OnLongClickListener onLongClickListener = textInputLayout3.endIconOnLongClickListener;
        checkableImageButton.setOnClickListener(r1);
        TextInputLayout.setIconClickable(checkableImageButton, onLongClickListener);
        TextInputLayout textInputLayout4 = this.textInputLayout;
        C20943 r12 = this.clearTextOnEditTextAttachedListener;
        Objects.requireNonNull(textInputLayout4);
        textInputLayout4.editTextAttachedListeners.add(r12);
        if (textInputLayout4.editText != null) {
            r12.onEditTextAttached(textInputLayout4);
        }
        TextInputLayout textInputLayout5 = this.textInputLayout;
        C20954 r13 = this.endIconChangedListener;
        Objects.requireNonNull(textInputLayout5);
        textInputLayout5.endIconChangedListeners.add(r13);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.8f, 1.0f});
        ofFloat.setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR);
        ofFloat.setDuration(150);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                ClearTextEndIconDelegate.this.endIconView.setScaleX(floatValue);
                ClearTextEndIconDelegate.this.endIconView.setScaleY(floatValue);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        LinearInterpolator linearInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        ofFloat2.setInterpolator(linearInterpolator);
        ofFloat2.setDuration(100);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClearTextEndIconDelegate.this.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        this.iconInAnim = animatorSet;
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        this.iconInAnim.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationStart(Animator animator) {
                ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(true);
            }
        });
        ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
        ofFloat3.setInterpolator(linearInterpolator);
        ofFloat3.setDuration(100);
        ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                ClearTextEndIconDelegate.this.endIconView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        this.iconOutAnim = ofFloat3;
        ofFloat3.addListener(new AnimatorListenerAdapter() {
            public final void onAnimationEnd(Animator animator) {
                ClearTextEndIconDelegate.this.textInputLayout.setEndIconVisible(false);
            }
        });
    }

    public final void onSuffixVisibilityChanged(boolean z) {
        TextInputLayout textInputLayout = this.textInputLayout;
        Objects.requireNonNull(textInputLayout);
        if (textInputLayout.suffixText != null) {
            animateIcon(z);
        }
    }

    public ClearTextEndIconDelegate(TextInputLayout textInputLayout, int i) {
        super(textInputLayout, i);
    }

    public static boolean access$000(ClearTextEndIconDelegate clearTextEndIconDelegate) {
        Objects.requireNonNull(clearTextEndIconDelegate);
        TextInputLayout textInputLayout = clearTextEndIconDelegate.textInputLayout;
        Objects.requireNonNull(textInputLayout);
        EditText editText = textInputLayout.editText;
        if (editText == null || ((!editText.hasFocus() && !clearTextEndIconDelegate.endIconView.hasFocus()) || editText.getText().length() <= 0)) {
            return false;
        }
        return true;
    }
}
