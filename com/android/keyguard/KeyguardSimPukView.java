package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class KeyguardSimPukView extends KeyguardPinBasedInputView {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;

    public KeyguardSimPukView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final int getPasswordTextViewId() {
        return C1777R.C1779id.pukEntry;
    }

    public final int getPromptReasonStringRes(int i) {
        return 0;
    }

    public final String getPukPasswordErrorMessage(int i, boolean z, boolean z2) {
        String str;
        int i2;
        int i3;
        if (i == 0) {
            str = getContext().getString(C1777R.string.kg_password_wrong_puk_code_dead);
        } else if (i > 0) {
            if (z) {
                i3 = C1777R.plurals.kg_password_default_puk_message;
            } else {
                i3 = C1777R.plurals.kg_password_wrong_puk_code;
            }
            str = getContext().getResources().getQuantityString(i3, i, new Object[]{Integer.valueOf(i)});
        } else {
            if (z) {
                i2 = C1777R.string.kg_puk_enter_puk_hint;
            } else {
                i2 = C1777R.string.kg_password_puk_failed;
            }
            str = getContext().getString(i2);
        }
        if (z2) {
            str = getResources().getString(C1777R.string.kg_sim_lock_esim_instructions, new Object[]{str});
        }
        if (DEBUG) {
            Log.d("KeyguardSimPukView", "getPukPasswordErrorMessage: attemptsRemaining=" + i + " displayMessage=" + str);
        }
        return str;
    }

    public final void startAppearAnimation() {
    }

    public KeyguardSimPukView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final String getTitle() {
        return getContext().getString(17040506);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        View view = this.mEcaView;
        if (view instanceof EmergencyCarrierArea) {
            EmergencyCarrierArea emergencyCarrierArea = (EmergencyCarrierArea) view;
            Objects.requireNonNull(emergencyCarrierArea);
            emergencyCarrierArea.mCarrierText.setVisibility(0);
        }
    }
}
