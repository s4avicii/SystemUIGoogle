package com.android.systemui.biometrics;

import android.content.Context;
import android.os.UserHandle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImeAwareEditText;
import android.widget.TextView;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.p012wm.shell.C1777R;

public class AuthCredentialPasswordView extends AuthCredentialView implements TextView.OnEditorActionListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final InputMethodManager mImm = ((InputMethodManager) this.mContext.getSystemService(InputMethodManager.class));
    public ImeAwareEditText mPasswordField;

    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        LockscreenCredential lockscreenCredential;
        if (keyEvent == null && (i == 0 || i == 6 || i == 5)) {
            z = true;
        } else {
            z = false;
        }
        if (keyEvent == null || !KeyEvent.isConfirmKey(keyEvent.getKeyCode()) || keyEvent.getAction() != 0) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (!z && !z2) {
            return false;
        }
        if (this.mCredentialType == 1) {
            lockscreenCredential = LockscreenCredential.createPinOrNone(this.mPasswordField.getText());
        } else {
            lockscreenCredential = LockscreenCredential.createPasswordOrNone(this.mPasswordField.getText());
        }
        try {
            if (!lockscreenCredential.isNone()) {
                this.mPendingLockCheck = LockPatternChecker.verifyCredential(this.mLockPatternUtils, lockscreenCredential, this.mEffectiveUserId, 1, new AuthCredentialPasswordView$$ExternalSyntheticLambda1(this));
            }
            lockscreenCredential.close();
            return true;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    public AuthCredentialPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPasswordField.setTextOperationUser(UserHandle.of(this.mUserId));
        if (this.mCredentialType == 1) {
            this.mPasswordField.setInputType(18);
        }
        this.mPasswordField.requestFocus();
        this.mPasswordField.scheduleShowSoftInput();
    }

    public final void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        super.onCredentialVerified(verifyCredentialResponse, i);
        if (verifyCredentialResponse.isMatched()) {
            this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            this.mPasswordField.setText("");
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        ImeAwareEditText findViewById = findViewById(C1777R.C1779id.lockPassword);
        this.mPasswordField = findViewById;
        findViewById.setOnEditorActionListener(this);
        this.mPasswordField.setOnKeyListener(new AuthCredentialPasswordView$$ExternalSyntheticLambda0(this));
    }
}
