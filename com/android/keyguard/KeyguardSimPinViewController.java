package com.android.keyguard;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.telephony.PinResult;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ImageView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda20;
import com.android.systemui.classifier.FalsingCollector;
import java.util.Objects;

public final class KeyguardSimPinViewController extends KeyguardPinBasedInputViewController<KeyguardSimPinView> {
    public CheckSimPin mCheckSimPinThread;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public int mRemainingAttempts;
    public AlertDialog mRemainingAttemptsDialog;
    public boolean mShowDefaultMessage;
    public ImageView mSimImageView;
    public ProgressDialog mSimUnlockProgressDialog;
    public int mSubId = -1;
    public final TelephonyManager mTelephonyManager;
    public C05241 mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
        public final void onSimStateChanged(int i, int i2, int i3) {
            Log.v("KeyguardSimPinView", "onSimStateChanged(subId=" + i + ",state=" + i3 + ")");
            if (i3 == 5) {
                KeyguardSimPinViewController keyguardSimPinViewController = KeyguardSimPinViewController.this;
                keyguardSimPinViewController.mRemainingAttempts = -1;
                keyguardSimPinViewController.resetState();
                return;
            }
            KeyguardSimPinViewController.this.resetState();
        }
    };

    public abstract class CheckSimPin extends Thread {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final String mPin;
        public int mSubId;

        public abstract void onSimCheckResponse(PinResult pinResult);

        public CheckSimPin(String str, int i) {
            this.mPin = str;
            this.mSubId = i;
        }

        public final void run() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("call supplyIccLockPin(subid=");
            m.append(this.mSubId);
            m.append(")");
            Log.v("KeyguardSimPinView", m.toString());
            PinResult supplyIccLockPin = KeyguardSimPinViewController.this.mTelephonyManager.createForSubscriptionId(this.mSubId).supplyIccLockPin(this.mPin);
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("supplyIccLockPin returned: ");
            m2.append(supplyIccLockPin.toString());
            Log.v("KeyguardSimPinView", m2.toString());
            ((KeyguardSimPinView) KeyguardSimPinViewController.this.mView).post(new BubbleController$BubblesImpl$$ExternalSyntheticLambda0(this, supplyIccLockPin, 1));
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public KeyguardSimPinViewController(KeyguardSimPinView keyguardSimPinView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController) {
        super(keyguardSimPinView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mSimImageView = (ImageView) keyguardSimPinView.findViewById(C1777R.C1779id.keyguard_sim);
    }

    public final String getPinPasswordErrorMessage(int i) {
        String str;
        if (i == 0) {
            str = ((KeyguardSimPinView) this.mView).getResources().getString(C1777R.string.kg_password_wrong_pin_code_pukked);
        } else if (i > 0) {
            str = ((KeyguardSimPinView) this.mView).getResources().getQuantityString(C1777R.plurals.kg_password_wrong_pin_code, i, new Object[]{Integer.valueOf(i)});
        } else {
            str = ((KeyguardSimPinView) this.mView).getResources().getString(C1777R.string.kg_password_pin_failed);
        }
        if (KeyguardEsimArea.isEsimLocked(((KeyguardSimPinView) this.mView).getContext(), this.mSubId)) {
            str = ((KeyguardSimPinView) this.mView).getResources().getString(C1777R.string.kg_sim_lock_esim_instructions, new Object[]{str});
        }
        Log.d("KeyguardSimPinView", "getPinPasswordErrorMessage: attemptsRemaining=" + i + " displayMessage=" + str);
        return str;
    }

    public final boolean startDisappearAnimation(Runnable runnable) {
        return false;
    }

    public final void resetState() {
        int i;
        ((KeyguardPinBasedInputView) this.mView).setPasswordEntryEnabled(true);
        Log.v("KeyguardSimPinView", "Resetting state");
        int nextSubIdForState = this.mKeyguardUpdateMonitor.getNextSubIdForState(2);
        if (nextSubIdForState != this.mSubId && SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
            this.mSubId = nextSubIdForState;
            this.mShowDefaultMessage = true;
            this.mRemainingAttempts = -1;
        }
        this.mMessageAreaController.setMessage((CharSequence) "");
        if (this.mShowDefaultMessage) {
            setLockedSimMessage();
            if (this.mRemainingAttempts < 0) {
                new CheckSimPin(this.mSubId) {
                    public final void onSimCheckResponse(PinResult pinResult) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onSimCheckResponse  empty One result ");
                        m.append(pinResult.toString());
                        Log.d("KeyguardSimPinView", m.toString());
                        if (pinResult.getAttemptsRemaining() >= 0) {
                            KeyguardSimPinViewController.this.mRemainingAttempts = pinResult.getAttemptsRemaining();
                            KeyguardSimPinViewController.this.setLockedSimMessage();
                        }
                    }
                }.start();
            }
        }
        KeyguardSimPinView keyguardSimPinView = (KeyguardSimPinView) this.mView;
        boolean isEsimLocked = KeyguardEsimArea.isEsimLocked(keyguardSimPinView.getContext(), this.mSubId);
        int i2 = this.mSubId;
        Objects.requireNonNull(keyguardSimPinView);
        KeyguardEsimArea keyguardEsimArea = (KeyguardEsimArea) keyguardSimPinView.findViewById(C1777R.C1779id.keyguard_esim_area);
        Objects.requireNonNull(keyguardEsimArea);
        keyguardEsimArea.mSubscriptionId = i2;
        if (isEsimLocked) {
            i = 0;
        } else {
            i = 8;
        }
        keyguardEsimArea.setVisibility(i);
    }

    public final void setLockedSimMessage() {
        int i;
        String str;
        Object obj;
        boolean isEsimLocked = KeyguardEsimArea.isEsimLocked(((KeyguardSimPinView) this.mView).getContext(), this.mSubId);
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager != null) {
            i = telephonyManager.getActiveModemCount();
        } else {
            i = 1;
        }
        Resources resources = ((KeyguardSimPinView) this.mView).getResources();
        TypedArray obtainStyledAttributes = ((KeyguardSimPinView) this.mView).getContext().obtainStyledAttributes(new int[]{16842904});
        int color = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        if (i < 2) {
            str = resources.getString(C1777R.string.kg_sim_pin_instructions);
        } else {
            SubscriptionInfo subscriptionInfoForSubId = this.mKeyguardUpdateMonitor.getSubscriptionInfoForSubId(this.mSubId);
            if (subscriptionInfoForSubId != null) {
                obj = subscriptionInfoForSubId.getDisplayName();
            } else {
                obj = "";
            }
            String string = resources.getString(C1777R.string.kg_sim_pin_instructions_multi, new Object[]{obj});
            if (subscriptionInfoForSubId != null) {
                color = subscriptionInfoForSubId.getIconTint();
            }
            str = string;
        }
        if (isEsimLocked) {
            str = resources.getString(C1777R.string.kg_sim_lock_esim_instructions, new Object[]{str});
        }
        if (((KeyguardSimPinView) this.mView).getVisibility() == 0) {
            this.mMessageAreaController.setMessage((CharSequence) str);
        }
        this.mSimImageView.setImageTintList(ColorStateList.valueOf(color));
    }

    public final void verifyPasswordAndUnlock() {
        PasswordTextView passwordTextView = this.mPasswordEntry;
        Objects.requireNonNull(passwordTextView);
        if (passwordTextView.mText.length() < 4) {
            this.mMessageAreaController.setMessage((int) C1777R.string.kg_invalid_sim_pin_hint);
            ((KeyguardSimPinView) this.mView).resetPasswordText(true, true);
            getKeyguardSecurityCallback().userActivity();
            return;
        }
        if (this.mSimUnlockProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(((KeyguardSimPinView) this.mView).getContext());
            this.mSimUnlockProgressDialog = progressDialog;
            progressDialog.setMessage(((KeyguardSimPinView) this.mView).getResources().getString(C1777R.string.kg_sim_unlock_progress_dialog_message));
            this.mSimUnlockProgressDialog.setIndeterminate(true);
            this.mSimUnlockProgressDialog.setCancelable(false);
            this.mSimUnlockProgressDialog.getWindow().setType(2009);
        }
        this.mSimUnlockProgressDialog.show();
        if (this.mCheckSimPinThread == null) {
            PasswordTextView passwordTextView2 = this.mPasswordEntry;
            Objects.requireNonNull(passwordTextView2);
            C05252 r0 = new CheckSimPin(passwordTextView2.mText, this.mSubId) {
                public static final /* synthetic */ int $r8$clinit = 0;

                public final void onSimCheckResponse(PinResult pinResult) {
                    ((KeyguardSimPinView) KeyguardSimPinViewController.this.mView).post(new BubbleStackView$$ExternalSyntheticLambda20(this, pinResult, 1));
                }
            };
            this.mCheckSimPinThread = r0;
            r0.start();
        }
    }

    public final void onPause() {
        super.onPause();
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        ProgressDialog progressDialog = this.mSimUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
    }

    public final void onResume(int i) {
        super.onResume(i);
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        Objects.requireNonNull((KeyguardSimPinView) this.mView);
    }

    public final void onViewAttached() {
        super.onViewAttached();
    }
}
