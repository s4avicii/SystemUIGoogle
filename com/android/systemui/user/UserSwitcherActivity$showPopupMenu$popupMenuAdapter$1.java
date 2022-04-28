package com.android.systemui.user;

import com.android.systemui.statusbar.policy.UserSwitcherController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: UserSwitcherActivity.kt */
final class UserSwitcherActivity$showPopupMenu$popupMenuAdapter$1 extends Lambda implements Function1<UserSwitcherController.UserRecord, String> {
    public final /* synthetic */ UserSwitcherActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UserSwitcherActivity$showPopupMenu$popupMenuAdapter$1(UserSwitcherActivity userSwitcherActivity) {
        super(1);
        this.this$0 = userSwitcherActivity;
    }

    public final Object invoke(Object obj) {
        UserSwitcherActivity userSwitcherActivity = this.this$0;
        return userSwitcherActivity.adapter.getName(userSwitcherActivity, (UserSwitcherController.UserRecord) obj);
    }
}
