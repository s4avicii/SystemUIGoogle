package com.android.keyguard;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class KeyguardSimPinView extends KeyguardPinBasedInputView {
    public KeyguardSimPinView(Context context) {
        this(context, (AttributeSet) null);
    }

    public final int getPasswordTextViewId() {
        return C1777R.C1779id.simPinEntry;
    }

    public final int getPromptReasonStringRes(int i) {
        return 0;
    }

    public final void startAppearAnimation() {
    }

    public KeyguardSimPinView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final String getTitle() {
        return getContext().getString(17040505);
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

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }
}
