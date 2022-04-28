package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import com.android.internal.widget.LockscreenCredential;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public abstract class KeyguardAbsKeyInputView extends KeyguardInputView {
    public View mEcaView;
    public KeyDownListener mKeyDownListener;

    public interface KeyDownListener {
    }

    public KeyguardAbsKeyInputView(Context context) {
        this(context, (AttributeSet) null);
    }

    public abstract LockscreenCredential getEnteredCredential();

    public abstract int getPasswordTextViewId();

    public abstract int getPromptReasonStringRes(int i);

    public int getWrongPasswordStringId() {
        return C1777R.string.kg_wrong_password;
    }

    public abstract void resetPasswordText(boolean z, boolean z2);

    public abstract void setPasswordEntryEnabled(boolean z);

    public abstract void setPasswordEntryInputEnabled(boolean z);

    public KeyguardAbsKeyInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        KeyDownListener keyDownListener = this.mKeyDownListener;
        if (keyDownListener == null) {
            return false;
        }
        KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 = (KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0) keyDownListener;
        Objects.requireNonNull(keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0);
        KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0.f$0;
        if (i != 0) {
            keyguardAbsKeyInputViewController.onUserInput();
            return false;
        }
        Objects.requireNonNull(keyguardAbsKeyInputViewController);
        return false;
    }

    public void onFinishInflate() {
        this.mEcaView = findViewById(C1777R.C1779id.keyguard_selector_fade_container);
    }
}
