package com.android.keyguard;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.PasswordTextView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import java.util.Objects;

public abstract class KeyguardPinBasedInputView extends KeyguardAbsKeyInputView {
    public NumPadKey[] mButtons;
    public NumPadButton mDeleteButton;
    public NumPadButton mOkButton;
    public PasswordTextView mPasswordEntry;

    public KeyguardPinBasedInputView(Context context) {
        this(context, (AttributeSet) null);
    }

    public int getPromptReasonStringRes(int i) {
        if (i != 0) {
            return i != 1 ? i != 3 ? i != 4 ? C1777R.string.kg_prompt_reason_timeout_pin : C1777R.string.kg_prompt_reason_user_request : C1777R.string.kg_prompt_reason_device_admin : C1777R.string.kg_prompt_reason_restart_pin;
        }
        return 0;
    }

    public KeyguardPinBasedInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButtons = new NumPadKey[10];
    }

    public final LockscreenCredential getEnteredCredential() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        Objects.requireNonNull(passwordTextView);
        return LockscreenCredential.createPinOrNone(passwordTextView.mText);
    }

    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mPasswordEntry.requestFocus(i, rect);
    }

    public final void reloadColors() {
        for (NumPadKey numPadKey : this.mButtons) {
            Objects.requireNonNull(numPadKey);
            int defaultColor = Utils.getColorAttr(numPadKey.getContext(), 16842806).getDefaultColor();
            int defaultColor2 = Utils.getColorAttr(numPadKey.getContext(), 16842808).getDefaultColor();
            numPadKey.mDigitText.setTextColor(defaultColor);
            numPadKey.mKlondikeText.setTextColor(defaultColor2);
            NumPadAnimator numPadAnimator = numPadKey.mAnimator;
            if (numPadAnimator != null) {
                numPadAnimator.reloadColors(numPadKey.getContext());
            }
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        Objects.requireNonNull(passwordTextView);
        passwordTextView.mDrawPaint.setColor(Utils.getColorAttr(passwordTextView.getContext(), 16842806).getDefaultColor());
        this.mDeleteButton.reloadColors();
        this.mOkButton.reloadColors();
    }

    public final void resetPasswordText(boolean z, boolean z2) {
        int i;
        PasswordTextView passwordTextView = this.mPasswordEntry;
        Objects.requireNonNull(passwordTextView);
        StringBuilder transformedText = passwordTextView.getTransformedText();
        passwordTextView.mText = "";
        int size = passwordTextView.mTextChars.size();
        int i2 = size - 1;
        int i3 = i2 / 2;
        for (int i4 = 0; i4 < size; i4++) {
            PasswordTextView.CharState charState = passwordTextView.mTextChars.get(i4);
            if (z) {
                if (i4 <= i3) {
                    i = i4 * 2;
                } else {
                    i = i2 - (((i4 - i3) - 1) * 2);
                }
                charState.startRemoveAnimation(Math.min(((long) i) * 40, 200), Math.min(((long) i2) * 40, 200) + 160);
                PasswordTextView.this.removeCallbacks(charState.dotSwapperRunnable);
                charState.isDotSwapPending = false;
            } else {
                passwordTextView.mCharPool.push(charState);
            }
        }
        if (!z) {
            passwordTextView.mTextChars.clear();
        }
        if (z2) {
            passwordTextView.sendAccessibilityEventTypeViewTextChanged(transformedText, 0, transformedText.length(), 0);
        }
    }

    public final void setPasswordEntryEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
        this.mOkButton.setEnabled(z);
        if (z && !this.mPasswordEntry.hasFocus()) {
            this.mPasswordEntry.requestFocus();
        }
    }

    public final void setPasswordEntryInputEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
        this.mOkButton.setEnabled(z);
        if (z && !this.mPasswordEntry.hasFocus()) {
            this.mPasswordEntry.requestFocus();
        }
    }

    public String getTitle() {
        return getContext().getString(17040504);
    }

    public void onFinishInflate() {
        PasswordTextView passwordTextView = (PasswordTextView) findViewById(getPasswordTextViewId());
        this.mPasswordEntry = passwordTextView;
        passwordTextView.setSelected(true);
        this.mOkButton = (NumPadButton) findViewById(C1777R.C1779id.key_enter);
        NumPadButton numPadButton = (NumPadButton) findViewById(C1777R.C1779id.delete_button);
        this.mDeleteButton = numPadButton;
        numPadButton.setVisibility(0);
        this.mButtons[0] = (NumPadKey) findViewById(C1777R.C1779id.key0);
        this.mButtons[1] = (NumPadKey) findViewById(C1777R.C1779id.key1);
        this.mButtons[2] = (NumPadKey) findViewById(C1777R.C1779id.key2);
        this.mButtons[3] = (NumPadKey) findViewById(C1777R.C1779id.key3);
        this.mButtons[4] = (NumPadKey) findViewById(C1777R.C1779id.key4);
        this.mButtons[5] = (NumPadKey) findViewById(C1777R.C1779id.key5);
        this.mButtons[6] = (NumPadKey) findViewById(C1777R.C1779id.key6);
        this.mButtons[7] = (NumPadKey) findViewById(C1777R.C1779id.key7);
        this.mButtons[8] = (NumPadKey) findViewById(C1777R.C1779id.key8);
        this.mButtons[9] = (NumPadKey) findViewById(C1777R.C1779id.key9);
        this.mPasswordEntry.requestFocus();
        super.onFinishInflate();
        reloadColors();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(i)) {
            this.mOkButton.performClick();
            return true;
        } else if (i == 67) {
            this.mDeleteButton.performClick();
            return true;
        } else if (i >= 7 && i <= 16) {
            int i2 = i - 7;
            if (i2 >= 0 && i2 <= 9) {
                this.mButtons[i2].performClick();
            }
            return true;
        } else if (i < 144 || i > 153) {
            super.onKeyDown(i, keyEvent);
            return false;
        } else {
            int i3 = i - 144;
            if (i3 >= 0 && i3 <= 9) {
                this.mButtons[i3].performClick();
            }
            return true;
        }
    }
}
