package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.AuthBiometricFaceView;
import com.android.systemui.biometrics.AuthBiometricView;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthDialog;
import com.android.systemui.plugins.FalsingManager;
import java.util.Objects;

public class AuthBiometricFaceToFingerprintView extends AuthBiometricFaceView {
    public int mActiveSensorType = 8;
    public FingerprintSensorPropertiesInternal mFingerprintSensorProps;
    public ModalityListener mModalityListener;
    @VisibleForTesting
    public UdfpsIconController mUdfpsIconController;
    public UdfpsDialogMeasureAdapter mUdfpsMeasureAdapter;

    public AuthBiometricFaceToFingerprintView(Context context) {
        super(context);
    }

    public static class UdfpsIconController extends AuthBiometricFaceView.IconController {
        public int mIconState = 0;

        public final void updateState(int i, int i2) {
            boolean z;
            if (i == 4 || i == 3) {
                z = true;
            } else {
                z = false;
            }
            switch (i2) {
                case 0:
                case 1:
                case 2:
                case 5:
                case FalsingManager.VERSION /*6*/:
                    if (z) {
                        animateIcon(C1777R.C1778drawable.fingerprint_dialog_error_to_fp, false);
                    } else {
                        showStaticDrawable(C1777R.C1778drawable.fingerprint_dialog_fp_to_error);
                    }
                    this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.accessibility_fingerprint_dialog_fingerprint_icon));
                    break;
                case 3:
                case 4:
                    if (!z) {
                        animateIcon(C1777R.C1778drawable.fingerprint_dialog_fp_to_error, false);
                    } else {
                        showStaticDrawable(C1777R.C1778drawable.fingerprint_dialog_error_to_fp);
                    }
                    this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_try_again));
                    break;
                default:
                    KeyguardUpdateMonitor$$ExternalSyntheticOutline1.m27m("Unknown biometric dialog state: ", i2, "BiometricPrompt/AuthBiometricFaceToFingerprintView");
                    break;
            }
            this.mState = i2;
            this.mIconState = i2;
        }

        public UdfpsIconController(Context context, ImageView imageView) {
            super(context, imageView);
        }
    }

    public final String checkErrorForFallback(String str) {
        if (this.mActiveSensorType != 8) {
            return str;
        }
        DialogFragment$$ExternalSyntheticOutline0.m17m("Falling back to fingerprint: ", str, "BiometricPrompt/AuthBiometricFaceToFingerprintView");
        ((AuthContainerView.BiometricCallback) this.mCallback).onAction(7);
        return this.mContext.getString(C1777R.string.fingerprint_dialog_use_fingerprint_instead);
    }

    public final int getDelayAfterAuthenticatedDurationMs() {
        if (this.mActiveSensorType == 2) {
            return 0;
        }
        return 500;
    }

    public final int getStateForAfterError() {
        if (this.mActiveSensorType == 8) {
            return 2;
        }
        return 0;
    }

    public final void restoreState(Bundle bundle) {
        this.mSavedState = bundle;
        if (bundle != null) {
            this.mActiveSensorType = bundle.getInt("sensor_type", 8);
            this.mFingerprintSensorProps = bundle.getParcelable("sensor_props");
        }
    }

    public final void updateState(int i) {
        if (this.mActiveSensorType != 8) {
            UdfpsIconController udfpsIconController = this.mUdfpsIconController;
            Objects.requireNonNull(udfpsIconController);
            udfpsIconController.updateState(udfpsIconController.mIconState, i);
        } else if (i == 3 || i == 4) {
            this.mActiveSensorType = 2;
            this.mRequireConfirmation = false;
            this.mConfirmButton.setEnabled(false);
            this.mConfirmButton.setVisibility(8);
            ModalityListener modalityListener = this.mModalityListener;
            if (modalityListener != null) {
                AuthContainerView.this.maybeUpdatePositionForUdfps(true);
            }
            AuthBiometricFaceView.IconController iconController = this.mFaceIconController;
            Objects.requireNonNull(iconController);
            iconController.mDeactivated = true;
            UdfpsIconController udfpsIconController2 = this.mUdfpsIconController;
            Objects.requireNonNull(udfpsIconController2);
            udfpsIconController2.updateState(udfpsIconController2.mIconState, 0);
        }
        super.updateState(i);
    }

    public AuthBiometricFaceToFingerprintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onAuthenticationFailed(int i, String str) {
        super.onAuthenticationFailed(i, checkErrorForFallback(str));
    }

    public final void onError(int i, String str) {
        super.onError(i, checkErrorForFallback(str));
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mUdfpsIconController = new UdfpsIconController(this.mContext, this.mIconView);
    }

    public final AuthDialog.LayoutParams onMeasureInternal(int i, int i2) {
        AuthDialog.LayoutParams onMeasureInternal = super.onMeasureInternal(i, i2);
        if (!this.mFingerprintSensorProps.isAnyUdfpsType()) {
            return onMeasureInternal;
        }
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.mUdfpsMeasureAdapter;
        if (udfpsDialogMeasureAdapter == null || udfpsDialogMeasureAdapter.mSensorProps != this.mFingerprintSensorProps) {
            this.mUdfpsMeasureAdapter = new UdfpsDialogMeasureAdapter(this, this.mFingerprintSensorProps);
        }
        return this.mUdfpsMeasureAdapter.onMeasureInternal(i, i2, onMeasureInternal);
    }

    public final void onSaveState(Bundle bundle) {
        super.onSaveState(bundle);
        bundle.putInt("sensor_type", this.mActiveSensorType);
        bundle.putParcelable("sensor_props", this.mFingerprintSensorProps);
    }

    @VisibleForTesting
    public AuthBiometricFaceToFingerprintView(Context context, AttributeSet attributeSet, AuthBiometricView.Injector injector) {
        super(context, attributeSet, injector);
    }
}
