package com.android.systemui.biometrics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.p012wm.shell.C1777R;
import java.util.List;

public class AuthCredentialPatternView extends AuthCredentialView {
    public LockPatternView mLockPatternView;

    public class UnlockPatternListener implements LockPatternView.OnPatternListener {
        public final void onPatternCellAdded(List<LockPatternView.Cell> list) {
        }

        public final void onPatternCleared() {
        }

        public final void onPatternStart() {
        }

        public UnlockPatternListener() {
        }

        public final void onPatternDetected(List<LockPatternView.Cell> list) {
            AsyncTask<?, ?, ?> asyncTask = AuthCredentialPatternView.this.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
            }
            AuthCredentialPatternView.this.mLockPatternView.setEnabled(false);
            if (list.size() < 4) {
                AuthCredentialPatternView.this.onCredentialVerified(VerifyCredentialResponse.ERROR, 0);
                AuthCredentialPatternView.this.mLockPatternView.setEnabled(true);
                return;
            }
            LockscreenCredential createPattern = LockscreenCredential.createPattern(list);
            try {
                AuthCredentialPatternView authCredentialPatternView = AuthCredentialPatternView.this;
                authCredentialPatternView.mPendingLockCheck = LockPatternChecker.verifyCredential(authCredentialPatternView.mLockPatternUtils, createPattern, authCredentialPatternView.mEffectiveUserId, 1, new C0694xce201d21(this));
                if (createPattern != null) {
                    createPattern.close();
                    return;
                }
                return;
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
            throw th;
        }
    }

    public final void onErrorTimeoutFinish() {
        this.mLockPatternView.setEnabled(true);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        LockPatternView findViewById = findViewById(C1777R.C1779id.lockPattern);
        this.mLockPatternView = findViewById;
        findViewById.setOnPatternListener(new UnlockPatternListener());
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(this.mUserId));
    }

    public AuthCredentialPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
