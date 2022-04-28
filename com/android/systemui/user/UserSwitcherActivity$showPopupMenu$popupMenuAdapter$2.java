package com.android.systemui.user;

import android.graphics.drawable.Drawable;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: UserSwitcherActivity.kt */
final class UserSwitcherActivity$showPopupMenu$popupMenuAdapter$2 extends Lambda implements Function1<UserSwitcherController.UserRecord, Drawable> {
    public final /* synthetic */ UserSwitcherActivity this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public UserSwitcherActivity$showPopupMenu$popupMenuAdapter$2(UserSwitcherActivity userSwitcherActivity) {
        super(1);
        this.this$0 = userSwitcherActivity;
    }

    public final Object invoke(Object obj) {
        Drawable mutate = this.this$0.adapter.findUserIcon((UserSwitcherController.UserRecord) obj).mutate();
        UserSwitcherActivity userSwitcherActivity = this.this$0;
        mutate.setTint(userSwitcherActivity.getResources().getColor(C1777R.color.user_switcher_fullscreen_popup_item_tint, userSwitcherActivity.getTheme()));
        return mutate;
    }
}
