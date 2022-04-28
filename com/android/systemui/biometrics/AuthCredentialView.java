package com.android.systemui.biometrics;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.PromptInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import java.util.Objects;

public abstract class AuthCredentialView extends LinearLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AccessibilityManager mAccessibilityManager = ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class));
    public Callback mCallback;
    public final C06951 mClearErrorRunnable = new Runnable() {
        public final void run() {
            TextView textView = AuthCredentialView.this.mErrorView;
            if (textView != null) {
                textView.setText("");
            }
        }
    };
    public AuthContainerView mContainerView;
    public int mCredentialType;
    public TextView mDescriptionView;
    public final DevicePolicyManager mDevicePolicyManager = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class));
    public int mEffectiveUserId;
    public C06962 mErrorTimer;
    public TextView mErrorView;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public ImageView mIconView;
    public final LockPatternUtils mLockPatternUtils = new LockPatternUtils(this.mContext);
    public long mOperationId;
    public AuthPanelController mPanelController;
    public AsyncTask<?, ?, ?> mPendingLockCheck;
    public PromptInfo mPromptInfo;
    public boolean mShouldAnimateContents;
    public boolean mShouldAnimatePanel;
    public TextView mSubtitleView;
    public TextView mTitleView;
    public int mUserId;
    public final UserManager mUserManager = ((UserManager) this.mContext.getSystemService(UserManager.class));

    public interface Callback {
    }

    public static class ErrorTimer extends CountDownTimer {
        public final Context mContext;
        public final TextView mErrorView;

        public ErrorTimer(Context context, long j, TextView textView) {
            super(j, 1000);
            this.mErrorView = textView;
            this.mContext = context;
        }

        public final void onTick(long j) {
            this.mErrorView.setText(this.mContext.getString(C1777R.string.biometric_dialog_credential_too_many_attempts, new Object[]{Integer.valueOf((int) (j / 1000))}));
        }
    }

    /* renamed from: $r8$lambda$Gx6uVWVgpTY74-hc4P38xpLn6pE  reason: not valid java name */
    public static String m169$r8$lambda$Gx6uVWVgpTY74hc4P38xpLn6pE(AuthCredentialView authCredentialView, int i) {
        int i2;
        if (i == 1) {
            i2 = C1777R.string.biometric_dialog_failed_attempts_now_wiping_device;
        } else if (i == 2) {
            i2 = C1777R.string.biometric_dialog_failed_attempts_now_wiping_profile;
        } else if (i == 3) {
            i2 = C1777R.string.biometric_dialog_failed_attempts_now_wiping_user;
        } else {
            Objects.requireNonNull(authCredentialView);
            throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Unrecognized user type:", i));
        }
        return authCredentialView.mContext.getString(i2);
    }

    public void onErrorTimeoutFinish() {
    }

    public final int getUserTypeForWipe() {
        UserInfo userInfo = this.mUserManager.getUserInfo(this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(this.mEffectiveUserId));
        if (userInfo == null || userInfo.isPrimary()) {
            return 1;
        }
        if (userInfo.isManagedProfile()) {
            return 2;
        }
        return 3;
    }

    public static String $r8$lambda$eIDslPxXQOSGDO4GIa22aIM6I0o(AuthCredentialView authCredentialView, int i) {
        int i2;
        Objects.requireNonNull(authCredentialView);
        if (i == 1) {
            i2 = C1777R.string.biometric_dialog_last_pin_attempt_before_wipe_profile;
        } else if (i != 2) {
            i2 = C1777R.string.biometric_dialog_last_password_attempt_before_wipe_profile;
        } else {
            i2 = C1777R.string.biometric_dialog_last_pattern_attempt_before_wipe_profile;
        }
        return authCredentialView.mContext.getString(i2);
    }

    public AuthCredentialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void onAttachedToWindow() {
        Drawable drawable;
        super.onAttachedToWindow();
        PromptInfo promptInfo = this.mPromptInfo;
        CharSequence deviceCredentialTitle = promptInfo.getDeviceCredentialTitle();
        if (deviceCredentialTitle == null) {
            deviceCredentialTitle = promptInfo.getTitle();
        }
        this.mTitleView.setText(deviceCredentialTitle);
        TextView textView = this.mSubtitleView;
        PromptInfo promptInfo2 = this.mPromptInfo;
        CharSequence deviceCredentialSubtitle = promptInfo2.getDeviceCredentialSubtitle();
        if (deviceCredentialSubtitle == null) {
            deviceCredentialSubtitle = promptInfo2.getSubtitle();
        }
        if (TextUtils.isEmpty(deviceCredentialSubtitle)) {
            textView.setVisibility(8);
        } else {
            textView.setText(deviceCredentialSubtitle);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        TextView textView2 = this.mDescriptionView;
        PromptInfo promptInfo3 = this.mPromptInfo;
        CharSequence deviceCredentialDescription = promptInfo3.getDeviceCredentialDescription();
        if (deviceCredentialDescription == null) {
            deviceCredentialDescription = promptInfo3.getDescription();
        }
        if (TextUtils.isEmpty(deviceCredentialDescription)) {
            textView2.setVisibility(8);
        } else {
            textView2.setText(deviceCredentialDescription);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        announceForAccessibility(deviceCredentialTitle);
        if (this.mIconView != null) {
            Context context = this.mContext;
            if (((UserManager) context.getSystemService(UserManager.class)).isManagedProfile(this.mEffectiveUserId)) {
                drawable = getResources().getDrawable(C1777R.C1778drawable.auth_dialog_enterprise, this.mContext.getTheme());
            } else {
                drawable = getResources().getDrawable(C1777R.C1778drawable.auth_dialog_lock, this.mContext.getTheme());
            }
            this.mIconView.setImageDrawable(drawable);
        }
        if (this.mShouldAnimateContents) {
            setTranslationY(getResources().getDimension(C1777R.dimen.biometric_dialog_credential_translation_offset));
            setAlpha(0.0f);
            postOnAnimation(new StatusBar$$ExternalSyntheticLambda18(this, 2));
        }
    }

    public void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        int i2;
        String str;
        String str2;
        String str3;
        int i3;
        if (verifyCredentialResponse.isMatched()) {
            this.mClearErrorRunnable.run();
            this.mLockPatternUtils.userPresent(this.mEffectiveUserId);
            long gatekeeperPasswordHandle = verifyCredentialResponse.getGatekeeperPasswordHandle();
            VerifyCredentialResponse verifyGatekeeperPasswordHandle = this.mLockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, this.mOperationId, this.mEffectiveUserId);
            Callback callback = this.mCallback;
            byte[] gatekeeperHAT = verifyGatekeeperPasswordHandle.getGatekeeperHAT();
            AuthContainerView.CredentialCallback credentialCallback = (AuthContainerView.CredentialCallback) callback;
            Objects.requireNonNull(credentialCallback);
            AuthContainerView authContainerView = AuthContainerView.this;
            authContainerView.mCredentialAttestation = gatekeeperHAT;
            authContainerView.animateAway(7);
            this.mLockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
        } else if (i > 0) {
            this.mHandler.removeCallbacks(this.mClearErrorRunnable);
            C06962 r0 = new ErrorTimer(this.mContext, this.mLockPatternUtils.setLockoutAttemptDeadline(this.mEffectiveUserId, i) - SystemClock.elapsedRealtime(), this.mErrorView) {
                public final void onFinish() {
                    AuthCredentialView.this.onErrorTimeoutFinish();
                    AuthCredentialView.this.mClearErrorRunnable.run();
                }
            };
            this.mErrorTimer = r0;
            r0.start();
        } else {
            int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId) + 1;
            int maximumFailedPasswordsForWipe = this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(this.mEffectiveUserId);
            boolean z = false;
            if (maximumFailedPasswordsForWipe > 0 && currentFailedPasswordAttempts > 0) {
                if (this.mErrorView != null) {
                    String string = getResources().getString(C1777R.string.biometric_dialog_credential_attempts_before_wipe, new Object[]{Integer.valueOf(currentFailedPasswordAttempts), Integer.valueOf(maximumFailedPasswordsForWipe)});
                    Handler handler = this.mHandler;
                    if (handler != null) {
                        handler.removeCallbacks(this.mClearErrorRunnable);
                        this.mHandler.postDelayed(this.mClearErrorRunnable, 3000);
                    }
                    TextView textView = this.mErrorView;
                    if (textView != null) {
                        textView.setText(string);
                    }
                }
                int i4 = maximumFailedPasswordsForWipe - currentFailedPasswordAttempts;
                if (i4 == 1) {
                    AlertDialog.Builder title = new AlertDialog.Builder(this.mContext).setTitle(C1777R.string.biometric_dialog_last_attempt_before_wipe_dialog_title);
                    int userTypeForWipe = getUserTypeForWipe();
                    int i5 = this.mCredentialType;
                    if (userTypeForWipe != 1) {
                        if (userTypeForWipe == 2) {
                            DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
                            if (i5 == 1) {
                                str3 = "SystemUi.BIOMETRIC_DIALOG_WORK_PIN_LAST_ATTEMPT";
                            } else if (i5 != 2) {
                                str3 = "SystemUi.BIOMETRIC_DIALOG_WORK_PASSWORD_LAST_ATTEMPT";
                            } else {
                                str3 = "SystemUi.BIOMETRIC_DIALOG_WORK_PATTERN_LAST_ATTEMPT";
                            }
                            str2 = devicePolicyManager.getString(str3, new AuthCredentialView$$ExternalSyntheticLambda2(this, i5));
                        } else if (userTypeForWipe == 3) {
                            if (i5 == 1) {
                                i3 = C1777R.string.biometric_dialog_last_pin_attempt_before_wipe_user;
                            } else if (i5 != 2) {
                                i3 = C1777R.string.biometric_dialog_last_password_attempt_before_wipe_user;
                            } else {
                                i3 = C1777R.string.biometric_dialog_last_pattern_attempt_before_wipe_user;
                            }
                            str2 = this.mContext.getString(i3);
                        } else {
                            throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Unrecognized user type:", userTypeForWipe));
                        }
                    } else if (i5 == 1) {
                        str2 = this.mContext.getString(C1777R.string.biometric_dialog_last_pin_attempt_before_wipe_device);
                    } else if (i5 != 2) {
                        str2 = this.mContext.getString(C1777R.string.biometric_dialog_last_password_attempt_before_wipe_device);
                    } else {
                        str2 = this.mContext.getString(C1777R.string.biometric_dialog_last_pattern_attempt_before_wipe_device);
                    }
                    AlertDialog create = title.setMessage(str2).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
                    create.getWindow().setType(2017);
                    create.show();
                } else if (i4 <= 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                    int userTypeForWipe2 = getUserTypeForWipe();
                    DevicePolicyManager devicePolicyManager2 = this.mDevicePolicyManager;
                    if (userTypeForWipe2 != 2) {
                        str = "UNDEFINED";
                    } else {
                        str = "SystemUi.BIOMETRIC_DIALOG_WORK_LOCK_FAILED_ATTEMPTS";
                    }
                    AlertDialog create2 = builder.setMessage(devicePolicyManager2.getString(str, new AuthCredentialView$$ExternalSyntheticLambda1(this, userTypeForWipe2))).setPositiveButton(C1777R.string.biometric_dialog_now_wiping_dialog_dismiss, (DialogInterface.OnClickListener) null).setOnDismissListener(new AuthCredentialView$$ExternalSyntheticLambda0(this)).create();
                    create2.getWindow().setType(2017);
                    create2.show();
                }
                z = true;
            }
            this.mLockPatternUtils.reportFailedPasswordAttempt(this.mEffectiveUserId);
            if (!z) {
                int i6 = this.mCredentialType;
                if (i6 == 1) {
                    i2 = C1777R.string.biometric_dialog_wrong_pin;
                } else if (i6 != 2) {
                    i2 = C1777R.string.biometric_dialog_wrong_password;
                } else {
                    i2 = C1777R.string.biometric_dialog_wrong_pattern;
                }
                String string2 = getResources().getString(i2);
                Handler handler2 = this.mHandler;
                if (handler2 != null) {
                    handler2.removeCallbacks(this.mClearErrorRunnable);
                    this.mHandler.postDelayed(this.mClearErrorRunnable, 3000);
                }
                TextView textView2 = this.mErrorView;
                if (textView2 != null) {
                    textView2.setText(string2);
                }
            }
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        C06962 r0 = this.mErrorTimer;
        if (r0 != null) {
            r0.cancel();
        }
    }

    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(C1777R.C1779id.title);
        this.mSubtitleView = (TextView) findViewById(C1777R.C1779id.subtitle);
        this.mDescriptionView = (TextView) findViewById(C1777R.C1779id.description);
        this.mIconView = (ImageView) findViewById(C1777R.C1779id.icon);
        this.mErrorView = (TextView) findViewById(C1777R.C1779id.error);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mShouldAnimatePanel) {
            AuthPanelController authPanelController = this.mPanelController;
            Objects.requireNonNull(authPanelController);
            authPanelController.mUseFullScreen = true;
            AuthPanelController authPanelController2 = this.mPanelController;
            Objects.requireNonNull(authPanelController2);
            int i5 = authPanelController2.mContainerWidth;
            AuthPanelController authPanelController3 = this.mPanelController;
            Objects.requireNonNull(authPanelController3);
            authPanelController2.updateForContentDimensions(i5, authPanelController3.mContainerHeight, 0);
            this.mShouldAnimatePanel = false;
        }
    }
}
