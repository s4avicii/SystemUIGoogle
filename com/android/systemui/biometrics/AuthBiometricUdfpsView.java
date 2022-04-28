package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.AuthDialog;
import java.util.Objects;

public class AuthBiometricUdfpsView extends AuthBiometricFingerprintView {
    public UdfpsDialogMeasureAdapter mMeasureAdapter;

    public AuthBiometricUdfpsView(Context context) {
        this(context, (AttributeSet) null);
    }

    public AuthBiometricUdfpsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onLayoutInternal() {
        super.onLayoutInternal();
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.mMeasureAdapter;
        Objects.requireNonNull(udfpsDialogMeasureAdapter);
        int i = udfpsDialogMeasureAdapter.mBottomSpacerHeight;
        GridLayoutManager$$ExternalSyntheticOutline1.m20m("bottomSpacerHeight: ", i, "AuthBiometricUdfpsView");
        if (i < 0) {
            float f = (float) (-i);
            ((FrameLayout) findViewById(C1777R.C1779id.biometric_icon_frame)).setTranslationY(f);
            ((TextView) findViewById(C1777R.C1779id.indicator)).setTranslationY(f);
        }
    }

    public final AuthDialog.LayoutParams onMeasureInternal(int i, int i2) {
        AuthDialog.LayoutParams onMeasureInternal = super.onMeasureInternal(i, i2);
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.mMeasureAdapter;
        if (udfpsDialogMeasureAdapter != null) {
            return udfpsDialogMeasureAdapter.onMeasureInternal(i, i2, onMeasureInternal);
        }
        return onMeasureInternal;
    }
}
