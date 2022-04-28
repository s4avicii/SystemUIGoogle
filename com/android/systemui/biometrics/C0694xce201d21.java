package com.android.systemui.biometrics;

import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.systemui.biometrics.AuthCredentialPatternView;
import java.util.Objects;

/* renamed from: com.android.systemui.biometrics.AuthCredentialPatternView$UnlockPatternListener$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0694xce201d21 implements LockPatternChecker.OnVerifyCallback {
    public final /* synthetic */ AuthCredentialPatternView.UnlockPatternListener f$0;

    public /* synthetic */ C0694xce201d21(AuthCredentialPatternView.UnlockPatternListener unlockPatternListener) {
        this.f$0 = unlockPatternListener;
    }

    public final void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        AuthCredentialPatternView.UnlockPatternListener unlockPatternListener = this.f$0;
        Objects.requireNonNull(unlockPatternListener);
        AuthCredentialPatternView.this.onCredentialVerified(verifyCredentialResponse, i);
        if (i > 0) {
            AuthCredentialPatternView.this.mLockPatternView.setEnabled(false);
        } else {
            AuthCredentialPatternView.this.mLockPatternView.setEnabled(true);
        }
    }
}
