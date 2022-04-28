package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternUtils;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda5;
import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda17;
import com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthDialog;
import com.android.systemui.p006qs.QSFooterView$$ExternalSyntheticLambda0;
import com.android.systemui.tuner.RadioListPreference$$ExternalSyntheticLambda0;
import com.android.systemui.util.Utils;
import com.android.systemui.wallet.p011ui.WalletView$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Objects;

public abstract class AuthBiometricView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager;
    public final AuthBiometricView$$ExternalSyntheticLambda6 mBackgroundClickListener;
    public Callback mCallback;
    @VisibleForTesting
    public Button mCancelButton;
    @VisibleForTesting
    public Button mConfirmButton;
    public TextView mDescriptionView;
    public boolean mDialogSizeAnimating;
    public int mEffectiveUserId;
    public final Handler mHandler;
    public View mIconHolderView;
    public float mIconOriginalY;
    public ImageView mIconView;
    public TextView mIndicatorView;
    public final Injector mInjector;
    @VisibleForTesting
    public AuthDialog.LayoutParams mLayoutParams;
    @VisibleForTesting
    public Button mNegativeButton;
    public AuthPanelController mPanelController;
    public PromptInfo mPromptInfo;
    public boolean mRequireConfirmation;
    public final QSFooterView$$ExternalSyntheticLambda0 mResetErrorRunnable;
    public final BubbleStackView$$ExternalSyntheticLambda17 mResetHelpRunnable;
    public Bundle mSavedState;
    public int mSize;
    public int mState;
    public TextView mSubtitleView;
    public final int mTextColorError;
    public final int mTextColorHint;
    public TextView mTitleView;
    @VisibleForTesting
    public Button mTryAgainButton;
    @VisibleForTesting
    public Button mUseCredentialButton;

    public interface Callback {
    }

    @VisibleForTesting
    public static class Injector {
        public AuthBiometricView mBiometricView;
    }

    public AuthBiometricView(Context context) {
        this(context, (AttributeSet) null);
    }

    public abstract int getDelayAfterAuthenticatedDurationMs();

    public abstract int getStateForAfterError();

    public abstract void handleResetAfterError();

    public abstract void handleResetAfterHelp();

    public final void startTransitionToCredentialUI() {
        updateSize(3);
        ((AuthContainerView.BiometricCallback) this.mCallback).onAction(6);
    }

    public boolean supportsManualRetry() {
        return false;
    }

    public abstract boolean supportsSmallDialog();

    public AuthBiometricView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, new Injector());
    }

    @VisibleForTesting
    public void onAttachedToWindowInternal() {
        boolean z;
        boolean z2;
        String str;
        this.mTitleView.setText(this.mPromptInfo.getTitle());
        if ((this.mPromptInfo.getAuthenticators() & 32768) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            int keyguardStoredPasswordQuality = new LockPatternUtils(this.mContext).getKeyguardStoredPasswordQuality(this.mEffectiveUserId);
            if (keyguardStoredPasswordQuality == 65536) {
                z2 = true;
            } else if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
                z2 = true;
            } else {
                z2 = true;
            }
            if (z2) {
                str = getResources().getString(C1777R.string.biometric_dialog_use_pin);
            } else if (z2) {
                str = getResources().getString(C1777R.string.biometric_dialog_use_pattern);
            } else if (!z2) {
                str = getResources().getString(C1777R.string.biometric_dialog_use_password);
            } else {
                str = getResources().getString(C1777R.string.biometric_dialog_use_password);
            }
            this.mNegativeButton.setVisibility(8);
            this.mUseCredentialButton.setText(str);
            this.mUseCredentialButton.setVisibility(0);
        } else {
            this.mNegativeButton.setText(this.mPromptInfo.getNegativeButtonText());
        }
        TextView textView = this.mSubtitleView;
        CharSequence subtitle = this.mPromptInfo.getSubtitle();
        if (TextUtils.isEmpty(subtitle)) {
            textView.setVisibility(8);
        } else {
            textView.setText(subtitle);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        TextView textView2 = this.mDescriptionView;
        CharSequence description = this.mPromptInfo.getDescription();
        if (TextUtils.isEmpty(description)) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(description);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        Bundle bundle = this.mSavedState;
        if (bundle == null) {
            updateState(1);
            return;
        }
        updateState(bundle.getInt("state"));
        this.mConfirmButton.setVisibility(this.mSavedState.getInt("confirm_visibility"));
        if (this.mConfirmButton.getVisibility() == 8) {
            this.mRequireConfirmation = false;
        }
        this.mTryAgainButton.setVisibility(this.mSavedState.getInt("try_agian_visibility"));
    }

    public void onAuthenticationFailed(int i, String str) {
        showTemporaryMessage(str, this.mResetErrorRunnable);
        updateState(4);
    }

    public void onError(int i, String str) {
        showTemporaryMessage(str, this.mResetErrorRunnable);
        updateState(4);
        Handler handler = this.mHandler;
        TaskView$$ExternalSyntheticLambda5 taskView$$ExternalSyntheticLambda5 = new TaskView$$ExternalSyntheticLambda5(this, 2);
        Objects.requireNonNull(this.mInjector);
        handler.postDelayed(taskView$$ExternalSyntheticLambda5, (long) 2000);
    }

    @VisibleForTesting
    public void onFinishInflateInternal() {
        Injector injector = this.mInjector;
        Objects.requireNonNull(injector);
        this.mTitleView = (TextView) injector.mBiometricView.findViewById(C1777R.C1779id.title);
        Injector injector2 = this.mInjector;
        Objects.requireNonNull(injector2);
        this.mSubtitleView = (TextView) injector2.mBiometricView.findViewById(C1777R.C1779id.subtitle);
        Injector injector3 = this.mInjector;
        Objects.requireNonNull(injector3);
        this.mDescriptionView = (TextView) injector3.mBiometricView.findViewById(C1777R.C1779id.description);
        Injector injector4 = this.mInjector;
        Objects.requireNonNull(injector4);
        this.mIconView = (ImageView) injector4.mBiometricView.findViewById(C1777R.C1779id.biometric_icon);
        Injector injector5 = this.mInjector;
        Objects.requireNonNull(injector5);
        this.mIconHolderView = injector5.mBiometricView.findViewById(C1777R.C1779id.biometric_icon_frame);
        Injector injector6 = this.mInjector;
        Objects.requireNonNull(injector6);
        this.mIndicatorView = (TextView) injector6.mBiometricView.findViewById(C1777R.C1779id.indicator);
        Injector injector7 = this.mInjector;
        Objects.requireNonNull(injector7);
        this.mNegativeButton = (Button) injector7.mBiometricView.findViewById(C1777R.C1779id.button_negative);
        Injector injector8 = this.mInjector;
        Objects.requireNonNull(injector8);
        this.mCancelButton = (Button) injector8.mBiometricView.findViewById(C1777R.C1779id.button_cancel);
        Injector injector9 = this.mInjector;
        Objects.requireNonNull(injector9);
        this.mUseCredentialButton = (Button) injector9.mBiometricView.findViewById(C1777R.C1779id.button_use_credential);
        Injector injector10 = this.mInjector;
        Objects.requireNonNull(injector10);
        this.mConfirmButton = (Button) injector10.mBiometricView.findViewById(C1777R.C1779id.button_confirm);
        Injector injector11 = this.mInjector;
        Objects.requireNonNull(injector11);
        this.mTryAgainButton = (Button) injector11.mBiometricView.findViewById(C1777R.C1779id.button_try_again);
        this.mNegativeButton.setOnClickListener(new RadioListPreference$$ExternalSyntheticLambda0(this, 1));
        this.mCancelButton.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda4(this, 0));
        this.mUseCredentialButton.setOnClickListener(new WalletView$$ExternalSyntheticLambda0(this, 1));
        this.mConfirmButton.setOnClickListener(new AuthBiometricView$$ExternalSyntheticLambda5(this, 0));
        this.mTryAgainButton.setOnClickListener(new LayoutPreference$$ExternalSyntheticLambda0(this, 1));
    }

    public final void onHelp(String str) {
        if (this.mSize != 2) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Help received in size: ");
            m.append(this.mSize);
            Log.w("BiometricPrompt/AuthBiometricView", m.toString());
        } else if (TextUtils.isEmpty(str)) {
            Log.w("BiometricPrompt/AuthBiometricView", "Ignoring blank help message");
        } else {
            showTemporaryMessage(str, this.mResetHelpRunnable);
            updateState(3);
        }
    }

    @VisibleForTesting
    public void onLayoutInternal() {
        int i;
        if (this.mIconOriginalY == 0.0f) {
            this.mIconOriginalY = this.mIconHolderView.getY();
            Bundle bundle = this.mSavedState;
            if (bundle == null) {
                if (this.mRequireConfirmation || !supportsSmallDialog()) {
                    i = 2;
                } else {
                    i = 1;
                }
                updateSize(i);
                return;
            }
            updateSize(bundle.getInt("size"));
            String string = this.mSavedState.getString("indicator_string");
            if (this.mSavedState.getBoolean("hint_is_temporary")) {
                onHelp(string);
            } else if (this.mSavedState.getBoolean("error_is_temporary")) {
                onAuthenticationFailed(0, string);
            }
        }
    }

    public void onSaveState(Bundle bundle) {
        String str;
        bundle.putInt("confirm_visibility", this.mConfirmButton.getVisibility());
        bundle.putInt("try_agian_visibility", this.mTryAgainButton.getVisibility());
        bundle.putInt("state", this.mState);
        if (this.mIndicatorView.getText() != null) {
            str = this.mIndicatorView.getText().toString();
        } else {
            str = "";
        }
        bundle.putString("indicator_string", str);
        bundle.putBoolean("error_is_temporary", this.mHandler.hasCallbacks(this.mResetErrorRunnable));
        bundle.putBoolean("hint_is_temporary", this.mHandler.hasCallbacks(this.mResetHelpRunnable));
        bundle.putInt("size", this.mSize);
    }

    public final void removePendingAnimations() {
        this.mHandler.removeCallbacks(this.mResetHelpRunnable);
        this.mHandler.removeCallbacks(this.mResetErrorRunnable);
    }

    @VisibleForTesting
    public void updateSize(final int i) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Current size: ");
        m.append(this.mSize);
        m.append(" New size: ");
        m.append(i);
        Log.v("BiometricPrompt/AuthBiometricView", m.toString());
        if (i == 1) {
            this.mTitleView.setVisibility(8);
            this.mSubtitleView.setVisibility(8);
            this.mDescriptionView.setVisibility(8);
            this.mIndicatorView.setVisibility(8);
            this.mNegativeButton.setVisibility(8);
            this.mUseCredentialButton.setVisibility(8);
            float dimension = getResources().getDimension(C1777R.dimen.biometric_dialog_icon_padding);
            this.mIconHolderView.setY(((float) (getHeight() - this.mIconHolderView.getHeight())) - dimension);
            this.mPanelController.updateForContentDimensions(this.mLayoutParams.mMediumWidth, (((((int) dimension) * 2) + this.mIconHolderView.getHeight()) - this.mIconHolderView.getPaddingTop()) - this.mIconHolderView.getPaddingBottom(), 0);
            this.mSize = i;
        } else if (this.mSize == 1 && i == 2) {
            if (!this.mDialogSizeAnimating) {
                this.mDialogSizeAnimating = true;
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{this.mIconHolderView.getY(), this.mIconOriginalY});
                ofFloat.addUpdateListener(new AuthBiometricView$$ExternalSyntheticLambda0(this, 0));
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat2.addUpdateListener(new AuthBiometricView$$ExternalSyntheticLambda1(this, 0));
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.setDuration(150);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        AuthBiometricView authBiometricView = AuthBiometricView.this;
                        authBiometricView.mSize = i;
                        authBiometricView.mDialogSizeAnimating = false;
                        Utils.notifyAccessibilityContentChanged(authBiometricView.mAccessibilityManager, authBiometricView);
                    }

                    public final void onAnimationStart(Animator animator) {
                        boolean z;
                        super.onAnimationStart(animator);
                        AuthBiometricView.this.mTitleView.setVisibility(0);
                        AuthBiometricView.this.mIndicatorView.setVisibility(0);
                        AuthBiometricView authBiometricView = AuthBiometricView.this;
                        Objects.requireNonNull(authBiometricView);
                        if ((authBiometricView.mPromptInfo.getAuthenticators() & 32768) != 0) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (z) {
                            AuthBiometricView.this.mUseCredentialButton.setVisibility(0);
                        } else {
                            AuthBiometricView.this.mNegativeButton.setVisibility(0);
                        }
                        if (AuthBiometricView.this.supportsManualRetry()) {
                            AuthBiometricView.this.mTryAgainButton.setVisibility(0);
                        }
                        if (!TextUtils.isEmpty(AuthBiometricView.this.mSubtitleView.getText())) {
                            AuthBiometricView.this.mSubtitleView.setVisibility(0);
                        }
                        if (!TextUtils.isEmpty(AuthBiometricView.this.mDescriptionView.getText())) {
                            AuthBiometricView.this.mDescriptionView.setVisibility(0);
                        }
                    }
                });
                animatorSet.play(ofFloat).with(ofFloat2);
                animatorSet.start();
                AuthPanelController authPanelController = this.mPanelController;
                AuthDialog.LayoutParams layoutParams = this.mLayoutParams;
                authPanelController.updateForContentDimensions(layoutParams.mMediumWidth, layoutParams.mMediumHeight, 150);
            } else {
                return;
            }
        } else if (i == 2) {
            AuthPanelController authPanelController2 = this.mPanelController;
            AuthDialog.LayoutParams layoutParams2 = this.mLayoutParams;
            authPanelController2.updateForContentDimensions(layoutParams2.mMediumWidth, layoutParams2.mMediumHeight, 0);
            this.mSize = i;
        } else if (i == 3) {
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(new float[]{getY(), getY() - getResources().getDimension(C1777R.dimen.biometric_dialog_medium_to_large_translation_offset)});
            Objects.requireNonNull(this.mInjector);
            ofFloat3.setDuration((long) 450);
            ofFloat3.addUpdateListener(new AuthBiometricView$$ExternalSyntheticLambda2(this));
            ofFloat3.addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (this.getParent() != null) {
                        ((ViewGroup) this.getParent()).removeView(this);
                    }
                    AuthBiometricView.this.mSize = i;
                }
            });
            ValueAnimator ofFloat4 = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
            Objects.requireNonNull(this.mInjector);
            ofFloat4.setDuration((long) 225);
            ofFloat4.addUpdateListener(new AuthBiometricView$$ExternalSyntheticLambda3(this));
            AuthPanelController authPanelController3 = this.mPanelController;
            Objects.requireNonNull(authPanelController3);
            authPanelController3.mUseFullScreen = true;
            AuthPanelController authPanelController4 = this.mPanelController;
            Objects.requireNonNull(authPanelController4);
            int i2 = authPanelController4.mContainerWidth;
            AuthPanelController authPanelController5 = this.mPanelController;
            Objects.requireNonNull(authPanelController5);
            int i3 = authPanelController5.mContainerHeight;
            Objects.requireNonNull(this.mInjector);
            authPanelController4.updateForContentDimensions(i2, i3, 450);
            AnimatorSet animatorSet2 = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ofFloat3);
            arrayList.add(ofFloat4);
            animatorSet2.playTogether(arrayList);
            Objects.requireNonNull(this.mInjector);
            animatorSet2.setDuration((long) 300);
            animatorSet2.start();
        } else {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown transition from: ");
            m2.append(this.mSize);
            m2.append(" to: ");
            m2.append(i);
            Log.e("BiometricPrompt/AuthBiometricView", m2.toString());
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public void updateState(int i) {
        Log.v("BiometricPrompt/AuthBiometricView", "newState: " + i);
        if (i == 1 || i == 2) {
            removePendingAnimations();
            if (this.mRequireConfirmation) {
                this.mConfirmButton.setEnabled(false);
                this.mConfirmButton.setVisibility(0);
            }
        } else if (i != 4) {
            if (i == 5) {
                removePendingAnimations();
                this.mNegativeButton.setVisibility(8);
                this.mCancelButton.setVisibility(0);
                this.mUseCredentialButton.setVisibility(8);
                this.mConfirmButton.setEnabled(true);
                this.mConfirmButton.setVisibility(0);
                this.mIndicatorView.setTextColor(this.mTextColorHint);
                this.mIndicatorView.setText(C1777R.string.biometric_dialog_tap_confirm);
                this.mIndicatorView.setVisibility(0);
            } else if (i != 6) {
                GridLayoutManager$$ExternalSyntheticOutline1.m20m("Unhandled state: ", i, "BiometricPrompt/AuthBiometricView");
            } else {
                if (this.mSize != 1) {
                    this.mConfirmButton.setVisibility(8);
                    this.mNegativeButton.setVisibility(8);
                    this.mUseCredentialButton.setVisibility(8);
                    this.mCancelButton.setVisibility(8);
                    this.mIndicatorView.setVisibility(4);
                }
                announceForAccessibility(getResources().getString(C1777R.string.biometric_dialog_authenticated));
                this.mHandler.postDelayed(new TaskView$$ExternalSyntheticLambda6(this, 1), (long) getDelayAfterAuthenticatedDurationMs());
            }
        } else if (this.mSize == 1) {
            updateSize(2);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        this.mState = i;
    }

    /* renamed from: $r8$lambda$-2-rwDSgFbOdOVjO-mncQYw0Cq8  reason: not valid java name */
    public static /* synthetic */ void m167$r8$lambda$2rwDSgFbOdOVjOmncQYw0Cq8(AuthBiometricView authBiometricView) {
        Objects.requireNonNull(authBiometricView);
        ((AuthContainerView.BiometricCallback) authBiometricView.mCallback).onAction(1);
    }

    /* renamed from: $r8$lambda$321443sPT2-_vmgCOTtrDZ3nF1M  reason: not valid java name */
    public static /* synthetic */ void m168$r8$lambda$321443sPT2_vmgCOTtrDZ3nF1M(AuthBiometricView authBiometricView) {
        Objects.requireNonNull(authBiometricView);
        authBiometricView.updateState(2);
        ((AuthContainerView.BiometricCallback) authBiometricView.mCallback).onAction(4);
        authBiometricView.mTryAgainButton.setVisibility(8);
        Utils.notifyAccessibilityContentChanged(authBiometricView.mAccessibilityManager, authBiometricView);
    }

    public static /* synthetic */ void $r8$lambda$VegE5yOsiHWU0vJWzcmzXwUISuc(AuthBiometricView authBiometricView) {
        Objects.requireNonNull(authBiometricView);
        ((AuthContainerView.BiometricCallback) authBiometricView.mCallback).onAction(5);
    }

    public static /* synthetic */ void $r8$lambda$WF3RgVIOlK7RYqAvt14uiLwyRTM(AuthBiometricView authBiometricView) {
        Objects.requireNonNull(authBiometricView);
        ((AuthContainerView.BiometricCallback) authBiometricView.mCallback).onAction(3);
    }

    @VisibleForTesting
    public AuthBiometricView(Context context, AttributeSet attributeSet, Injector injector) {
        super(context, attributeSet);
        this.mSize = 0;
        this.mBackgroundClickListener = new AuthBiometricView$$ExternalSyntheticLambda6(this);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mTextColorError = getResources().getColor(C1777R.color.biometric_dialog_error, context.getTheme());
        this.mTextColorHint = getResources().getColor(C1777R.color.biometric_dialog_gray, context.getTheme());
        this.mInjector = injector;
        injector.mBiometricView = this;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mResetErrorRunnable = new QSFooterView$$ExternalSyntheticLambda0(this, 2);
        this.mResetHelpRunnable = new BubbleStackView$$ExternalSyntheticLambda17(this, 2);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttachedToWindowInternal();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mHandler.removeCallbacksAndMessages((Object) null);
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        onFinishInflateInternal();
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        onLayoutInternal();
    }

    public final void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (Utils.shouldUseSplitNotificationShade(getResources())) {
            i3 = (Math.min(size, size2) * 2) / 3;
        } else {
            i3 = Math.min(size, size2);
        }
        AuthDialog.LayoutParams onMeasureInternal = onMeasureInternal(i3, size2);
        this.mLayoutParams = onMeasureInternal;
        setMeasuredDimension(onMeasureInternal.mMediumWidth, onMeasureInternal.mMediumHeight);
    }

    public AuthDialog.LayoutParams onMeasureInternal(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getId() == C1777R.C1779id.space_above_icon || childAt.getId() == C1777R.C1779id.space_below_icon || childAt.getId() == C1777R.C1779id.button_bar) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == C1777R.C1779id.biometric_icon_frame) {
                View findViewById = findViewById(C1777R.C1779id.biometric_icon);
                childAt.measure(View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().width, 1073741824), View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == C1777R.C1779id.biometric_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            } else {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            }
            if (childAt.getVisibility() != 8) {
                i3 = childAt.getMeasuredHeight() + i3;
            }
        }
        return new AuthDialog.LayoutParams(i, i3);
    }

    public final void showTemporaryMessage(String str, Runnable runnable) {
        removePendingAnimations();
        this.mIndicatorView.setText(str);
        this.mIndicatorView.setTextColor(this.mTextColorError);
        boolean z = false;
        this.mIndicatorView.setVisibility(0);
        TextView textView = this.mIndicatorView;
        if (!this.mAccessibilityManager.isEnabled() || !this.mAccessibilityManager.isTouchExplorationEnabled()) {
            z = true;
        }
        textView.setSelected(z);
        Handler handler = this.mHandler;
        Objects.requireNonNull(this.mInjector);
        handler.postDelayed(runnable, (long) 2000);
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public void restoreState(Bundle bundle) {
        this.mSavedState = bundle;
    }
}
