package com.android.keyguard;

public interface KeyguardSecurityCallback {
    void dismiss(int i);

    void dismiss(int i, boolean z);

    void onCancelClicked() {
    }

    void onUserInput();

    void reportUnlockAttempt(int i, boolean z, int i2);

    void reset();

    void userActivity();
}
