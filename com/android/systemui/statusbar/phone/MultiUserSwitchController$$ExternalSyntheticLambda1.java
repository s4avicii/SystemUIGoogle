package com.android.systemui.statusbar.phone;

import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MultiUserSwitchController$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ MultiUserSwitchController f$0;

    public /* synthetic */ MultiUserSwitchController$$ExternalSyntheticLambda1(MultiUserSwitchController multiUserSwitchController) {
        this.f$0 = multiUserSwitchController;
    }

    public final Object get() {
        MultiUserSwitchController multiUserSwitchController = this.f$0;
        Objects.requireNonNull(multiUserSwitchController);
        return Boolean.valueOf(multiUserSwitchController.mUserManager.isUserSwitcherEnabled(multiUserSwitchController.getResources().getBoolean(C1777R.bool.qs_show_user_switcher_for_single_user)));
    }
}
