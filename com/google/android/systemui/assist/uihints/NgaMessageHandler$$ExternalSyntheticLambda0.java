package com.google.android.systemui.assist.uihints;

import androidx.leanback.R$color;
import com.android.systemui.navigationbar.NavigationModeController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NgaMessageHandler$$ExternalSyntheticLambda0 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ NgaMessageHandler f$0;

    public /* synthetic */ NgaMessageHandler$$ExternalSyntheticLambda0(NgaMessageHandler ngaMessageHandler) {
        this.f$0 = ngaMessageHandler;
    }

    public final void onNavigationModeChanged(int i) {
        NgaMessageHandler ngaMessageHandler = this.f$0;
        Objects.requireNonNull(ngaMessageHandler);
        ngaMessageHandler.mIsGestureNav = R$color.isGesturalMode(i);
    }
}
