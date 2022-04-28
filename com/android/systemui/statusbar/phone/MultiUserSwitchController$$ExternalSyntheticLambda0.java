package com.android.systemui.statusbar.phone;

import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MultiUserSwitchController$$ExternalSyntheticLambda0 implements Supplier {
    public final /* synthetic */ MultiUserSwitchController f$0;

    public /* synthetic */ MultiUserSwitchController$$ExternalSyntheticLambda0(MultiUserSwitchController multiUserSwitchController) {
        this.f$0 = multiUserSwitchController;
    }

    public final Object get() {
        MultiUserSwitchController multiUserSwitchController = this.f$0;
        Objects.requireNonNull(multiUserSwitchController);
        return Boolean.valueOf(multiUserSwitchController.mUserManager.isUserSwitcherEnabled());
    }
}
