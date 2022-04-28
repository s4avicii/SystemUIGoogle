package com.android.systemui.biometrics;

import android.view.KeyEvent;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AuthContainerView$$ExternalSyntheticLambda0 implements View.OnKeyListener {
    public final /* synthetic */ AuthContainerView f$0;

    public /* synthetic */ AuthContainerView$$ExternalSyntheticLambda0(AuthContainerView authContainerView) {
        this.f$0 = authContainerView;
    }

    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
        AuthContainerView authContainerView = this.f$0;
        Objects.requireNonNull(authContainerView);
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            authContainerView.sendEarlyUserCanceled();
            authContainerView.animateAway(1);
        }
        return true;
    }
}
