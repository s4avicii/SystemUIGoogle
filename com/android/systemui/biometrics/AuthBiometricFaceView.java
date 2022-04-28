package com.android.systemui.biometrics;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.AuthBiometricView;
import java.util.Objects;

public class AuthBiometricFaceView extends AuthBiometricView {
    @VisibleForTesting
    public IconController mFaceIconController;
    public final C06861 mOnAttachStateChangeListener;

    public static class IconController extends Animatable2.AnimationCallback {
        public Context mContext;
        public boolean mDeactivated;
        public ImageView mIconView;
        public boolean mLastPulseLightToDark;
        public int mState;

        public final void animateIcon(int i, boolean z) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("animateIcon, state: ");
            m.append(this.mState);
            m.append(", deactivated: ");
            KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(m, this.mDeactivated, "BiometricPrompt/AuthBiometricFaceView");
            if (!this.mDeactivated) {
                AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) this.mContext.getDrawable(i);
                this.mIconView.setImageDrawable(animatedVectorDrawable);
                animatedVectorDrawable.forceAnimationOnUI();
                if (z) {
                    animatedVectorDrawable.registerAnimationCallback(this);
                }
                animatedVectorDrawable.start();
            }
        }

        public final void showStaticDrawable(int i) {
            this.mIconView.setImageDrawable(this.mContext.getDrawable(i));
        }

        public void updateState(int i, int i2) {
            boolean z;
            if (this.mDeactivated) {
                GridLayoutManager$$ExternalSyntheticOutline1.m20m("Ignoring updateState when deactivated: ", i2, "BiometricPrompt/AuthBiometricFaceView");
                return;
            }
            if (i == 4 || i == 3) {
                z = true;
            } else {
                z = false;
            }
            if (i2 == 1) {
                showStaticDrawable(C1777R.C1778drawable.face_dialog_pulse_dark_to_light);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_authenticating));
            } else if (i2 == 2) {
                this.mLastPulseLightToDark = false;
                animateIcon(C1777R.C1778drawable.face_dialog_pulse_dark_to_light, true);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_authenticating));
            } else if (i == 5 && i2 == 6) {
                animateIcon(C1777R.C1778drawable.face_dialog_dark_to_checkmark, false);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_confirmed));
            } else if (z && i2 == 0) {
                animateIcon(C1777R.C1778drawable.face_dialog_error_to_idle, false);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_idle));
            } else if (z && i2 == 6) {
                animateIcon(C1777R.C1778drawable.face_dialog_dark_to_checkmark, false);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_authenticated));
            } else if (i2 == 4 && i != 4) {
                animateIcon(C1777R.C1778drawable.face_dialog_dark_to_error, false);
            } else if (i == 2 && i2 == 6) {
                animateIcon(C1777R.C1778drawable.face_dialog_dark_to_checkmark, false);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_authenticated));
            } else if (i2 == 5) {
                animateIcon(C1777R.C1778drawable.face_dialog_wink_from_dark, false);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_authenticated));
            } else if (i2 == 0) {
                showStaticDrawable(C1777R.C1778drawable.face_dialog_idle_static);
                this.mIconView.setContentDescription(this.mContext.getString(C1777R.string.biometric_dialog_face_icon_description_idle));
            } else {
                GridLayoutManager$$ExternalSyntheticOutline1.m20m("Unhandled state: ", i2, "BiometricPrompt/AuthBiometricFaceView");
            }
            this.mState = i2;
        }

        public IconController(Context context, ImageView imageView) {
            this.mContext = context;
            this.mIconView = imageView;
            new Handler(Looper.getMainLooper());
            showStaticDrawable(C1777R.C1778drawable.face_dialog_pulse_dark_to_light);
        }

        public final void onAnimationEnd(Drawable drawable) {
            int i;
            super.onAnimationEnd(drawable);
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onAnimationEnd, mState: ");
            m.append(this.mState);
            m.append(", deactivated: ");
            KeyguardUpdateMonitor$18$$ExternalSyntheticOutline0.m29m(m, this.mDeactivated, "BiometricPrompt/AuthBiometricFaceView");
            if (!this.mDeactivated) {
                int i2 = this.mState;
                if (i2 == 2 || i2 == 3) {
                    if (this.mLastPulseLightToDark) {
                        i = C1777R.C1778drawable.face_dialog_pulse_dark_to_light;
                    } else {
                        i = C1777R.C1778drawable.face_dialog_pulse_light_to_dark;
                    }
                    animateIcon(i, true);
                    this.mLastPulseLightToDark = !this.mLastPulseLightToDark;
                }
            }
        }
    }

    public AuthBiometricFaceView(Context context) {
        this(context, (AttributeSet) null);
    }

    public int getDelayAfterAuthenticatedDurationMs() {
        return 500;
    }

    public int getStateForAfterError() {
        return 0;
    }

    public boolean supportsManualRetry() {
        return !(this instanceof AuthBiometricFaceToFingerprintView);
    }

    public final boolean supportsSmallDialog() {
        return true;
    }

    public AuthBiometricFaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
            public final void onViewAttachedToWindow(View view) {
            }

            public final void onViewDetachedFromWindow(View view) {
                IconController iconController = AuthBiometricFaceView.this.mFaceIconController;
                Objects.requireNonNull(iconController);
                iconController.mDeactivated = true;
            }
        };
    }

    public final void handleResetAfterError() {
        this.mIndicatorView.setTextColor(this.mTextColorHint);
        this.mIndicatorView.setVisibility(4);
    }

    public final void handleResetAfterHelp() {
        this.mIndicatorView.setTextColor(this.mTextColorHint);
        this.mIndicatorView.setVisibility(4);
    }

    public void onAuthenticationFailed(int i, String str) {
        if (this.mSize == 2 && (!(this instanceof AuthBiometricFaceToFingerprintView))) {
            this.mTryAgainButton.setVisibility(0);
            this.mConfirmButton.setVisibility(8);
        }
        super.onAuthenticationFailed(i, str);
    }

    public void updateState(int i) {
        this.mFaceIconController.updateState(this.mState, i);
        if (i == 1 || (i == 2 && this.mSize == 2)) {
            this.mIndicatorView.setTextColor(this.mTextColorHint);
            this.mIndicatorView.setVisibility(4);
        }
        super.updateState(i);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mFaceIconController = new IconController(this.mContext, this.mIconView);
        addOnAttachStateChangeListener(this.mOnAttachStateChangeListener);
    }

    @VisibleForTesting
    public AuthBiometricFaceView(Context context, AttributeSet attributeSet, AuthBiometricView.Injector injector) {
        super(context, attributeSet, injector);
        this.mOnAttachStateChangeListener = new View.OnAttachStateChangeListener() {
            public final void onViewAttachedToWindow(View view) {
            }

            public final void onViewDetachedFromWindow(View view) {
                IconController iconController = AuthBiometricFaceView.this.mFaceIconController;
                Objects.requireNonNull(iconController);
                iconController.mDeactivated = true;
            }
        };
    }
}
