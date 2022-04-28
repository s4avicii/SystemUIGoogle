package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.os.CancellationSignal;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.TextViewInputDisabler;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import java.util.Objects;

public class KeyguardPasswordView extends KeyguardAbsKeyInputView {
    public TextView mPasswordEntry;
    public TextViewInputDisabler mPasswordEntryDisabler;

    public KeyguardPasswordView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final int getPasswordTextViewId() {
        return C1777R.C1779id.passwordEntry;
    }

    public final int getPromptReasonStringRes(int i) {
        if (i != 0) {
            return i != 1 ? i != 3 ? i != 4 ? C1777R.string.kg_prompt_reason_timeout_password : C1777R.string.kg_prompt_reason_user_request : C1777R.string.kg_prompt_reason_device_admin : C1777R.string.kg_prompt_reason_restart_password;
        }
        return 0;
    }

    public final int getWrongPasswordStringId() {
        return C1777R.string.kg_wrong_password;
    }

    public final void startAppearAnimation() {
        setAlpha(0.0f);
        animate().alpha(1.0f).setDuration(300).start();
        setTranslationY(0.0f);
    }

    public KeyguardPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getResources().getDimensionPixelSize(C1777R.dimen.disappear_y_translation);
        AnimationUtils.loadInterpolator(context, 17563662);
        AnimationUtils.loadInterpolator(context, 17563663);
    }

    public final LockscreenCredential getEnteredCredential() {
        return LockscreenCredential.createPasswordOrNone(this.mPasswordEntry.getText());
    }

    public final boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mPasswordEntry.requestFocus(i, rect);
    }

    public final void resetPasswordText(boolean z, boolean z2) {
        this.mPasswordEntry.setText("");
    }

    public final void setPasswordEntryEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
    }

    public final void setPasswordEntryInputEnabled(boolean z) {
        this.mPasswordEntryDisabler.setInputEnabled(z);
    }

    public final String getTitle() {
        return getResources().getString(17040501);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPasswordEntry = (TextView) findViewById(C1777R.C1779id.passwordEntry);
        this.mPasswordEntryDisabler = new TextViewInputDisabler(this.mPasswordEntry);
    }

    public final boolean startDisappearAnimation(final QSTileImpl$$ExternalSyntheticLambda0 qSTileImpl$$ExternalSyntheticLambda0) {
        getWindowInsetsController().controlWindowInsetsAnimation(WindowInsets.Type.ime(), 100, Interpolators.LINEAR, (CancellationSignal) null, new WindowInsetsAnimationControlListener() {
            public final void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
            }

            public final void onReady(final WindowInsetsAnimationController windowInsetsAnimationController, int i) {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{1.0f, 0.0f});
                ofFloat.addUpdateListener(new KeyguardPasswordView$1$$ExternalSyntheticLambda0(windowInsetsAnimationController, ofFloat));
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationStart(Animator animator) {
                    }

                    public final void onAnimationEnd(Animator animator) {
                        windowInsetsAnimationController.finish(false);
                        KeyguardPasswordView keyguardPasswordView = KeyguardPasswordView.this;
                        Objects.requireNonNull(keyguardPasswordView);
                        Runnable runnable = keyguardPasswordView.mOnFinishImeAnimationRunnable;
                        if (runnable != null) {
                            runnable.run();
                            keyguardPasswordView.mOnFinishImeAnimationRunnable = null;
                        }
                        qSTileImpl$$ExternalSyntheticLambda0.run();
                    }
                });
                ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
                ofFloat.start();
            }

            public final void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
                KeyguardPasswordView keyguardPasswordView = KeyguardPasswordView.this;
                Objects.requireNonNull(keyguardPasswordView);
                Runnable runnable = keyguardPasswordView.mOnFinishImeAnimationRunnable;
                if (runnable != null) {
                    runnable.run();
                    keyguardPasswordView.mOnFinishImeAnimationRunnable = null;
                }
                qSTileImpl$$ExternalSyntheticLambda0.run();
            }
        });
        return true;
    }
}
