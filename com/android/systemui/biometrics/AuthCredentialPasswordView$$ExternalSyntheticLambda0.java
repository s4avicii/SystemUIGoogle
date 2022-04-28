package com.android.systemui.biometrics;

import android.view.KeyEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthCredentialPasswordView$$ExternalSyntheticLambda0 implements View.OnKeyListener {
    public final /* synthetic */ AuthCredentialPasswordView f$0;

    public /* synthetic */ AuthCredentialPasswordView$$ExternalSyntheticLambda0(AuthCredentialPasswordView authCredentialPasswordView) {
        this.f$0 = authCredentialPasswordView;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        AuthCredentialPasswordView authCredentialPasswordView = this.f$0;
        int i2 = AuthCredentialPasswordView.$r8$clinit;
        Objects.requireNonNull(authCredentialPasswordView);
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            authCredentialPasswordView.mContainerView.sendEarlyUserCanceled();
            authCredentialPasswordView.mContainerView.animateAway(1);
        }
        return true;
    }
}
