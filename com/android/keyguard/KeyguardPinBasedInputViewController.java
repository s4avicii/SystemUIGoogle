package com.android.keyguard;

import android.view.View;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardAbsKeyInputViewController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardPinBasedInputView;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda5;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda0;
import java.util.Objects;

public abstract class KeyguardPinBasedInputViewController<T extends KeyguardPinBasedInputView> extends KeyguardAbsKeyInputViewController<T> {
    public final KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3 mActionButtonTouchListener = new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3(this);
    public final FalsingCollector mFalsingCollector;
    public final LiftToActivateListener mLiftToActivateListener;
    public final KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0 mOnKeyListener = new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0(this);
    public PasswordTextView mPasswordEntry;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardPinBasedInputViewController(T t, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector) {
        super(t, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController);
        this.mLiftToActivateListener = liftToActivateListener;
        this.mFalsingCollector = falsingCollector;
        this.mPasswordEntry = (PasswordTextView) t.findViewById(t.getPasswordTextViewId());
    }

    public void onResume(int i) {
        this.mResumed = true;
        this.mPasswordEntry.requestFocus();
    }

    public void onViewAttached() {
        KeyguardAbsKeyInputView keyguardAbsKeyInputView = (KeyguardAbsKeyInputView) this.mView;
        KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0 = this.mKeyDownListener;
        Objects.requireNonNull(keyguardAbsKeyInputView);
        keyguardAbsKeyInputView.mKeyDownListener = keyguardAbsKeyInputViewController$$ExternalSyntheticLambda0;
        EmergencyButtonController emergencyButtonController = this.mEmergencyButtonController;
        KeyguardAbsKeyInputViewController.C04861 r1 = this.mEmergencyButtonCallback;
        Objects.requireNonNull(emergencyButtonController);
        emergencyButtonController.mEmergencyButtonCallback = r1;
        KeyguardPinBasedInputView keyguardPinBasedInputView = (KeyguardPinBasedInputView) this.mView;
        Objects.requireNonNull(keyguardPinBasedInputView);
        for (NumPadKey onTouchListener : keyguardPinBasedInputView.mButtons) {
            onTouchListener.setOnTouchListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda2(this));
        }
        this.mPasswordEntry.setOnKeyListener(this.mOnKeyListener);
        PasswordTextView passwordTextView = this.mPasswordEntry;
        DozeTriggers$$ExternalSyntheticLambda0 dozeTriggers$$ExternalSyntheticLambda0 = new DozeTriggers$$ExternalSyntheticLambda0(this);
        Objects.requireNonNull(passwordTextView);
        passwordTextView.mUserActivityListener = dozeTriggers$$ExternalSyntheticLambda0;
        View findViewById = ((KeyguardPinBasedInputView) this.mView).findViewById(C1777R.C1779id.delete_button);
        findViewById.setOnTouchListener(this.mActionButtonTouchListener);
        findViewById.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda5(this, 1));
        findViewById.setOnLongClickListener(new KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1(this));
        View findViewById2 = ((KeyguardPinBasedInputView) this.mView).findViewById(C1777R.C1779id.key_enter);
        if (findViewById2 != null) {
            findViewById2.setOnTouchListener(this.mActionButtonTouchListener);
            findViewById2.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    if (KeyguardPinBasedInputViewController.this.mPasswordEntry.isEnabled()) {
                        KeyguardPinBasedInputViewController.this.verifyPasswordAndUnlock();
                    }
                }
            });
            findViewById2.setOnHoverListener(this.mLiftToActivateListener);
        }
    }

    public void onViewDetached() {
        KeyguardPinBasedInputView keyguardPinBasedInputView = (KeyguardPinBasedInputView) this.mView;
        Objects.requireNonNull(keyguardPinBasedInputView);
        for (NumPadKey onTouchListener : keyguardPinBasedInputView.mButtons) {
            onTouchListener.setOnTouchListener((View.OnTouchListener) null);
        }
    }
}
