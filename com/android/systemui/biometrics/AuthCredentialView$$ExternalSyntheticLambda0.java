package com.android.systemui.biometrics;

import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthCredentialView$$ExternalSyntheticLambda0 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ AuthCredentialView f$0;

    public /* synthetic */ AuthCredentialView$$ExternalSyntheticLambda0(AuthCredentialView authCredentialView) {
        this.f$0 = authCredentialView;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        AuthCredentialView authCredentialView = this.f$0;
        int i = AuthCredentialView.$r8$clinit;
        Objects.requireNonNull(authCredentialView);
        authCredentialView.mContainerView.animateAway(5);
    }
}
