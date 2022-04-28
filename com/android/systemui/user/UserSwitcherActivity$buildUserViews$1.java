package com.android.systemui.user;

import android.view.View;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import java.util.Objects;

/* compiled from: UserSwitcherActivity.kt */
public final class UserSwitcherActivity$buildUserViews$1 implements View.OnClickListener {
    public final /* synthetic */ UserSwitcherController.UserRecord $item;
    public final /* synthetic */ UserSwitcherActivity this$0;

    public UserSwitcherActivity$buildUserViews$1(UserSwitcherActivity userSwitcherActivity, UserSwitcherController.UserRecord userRecord) {
        this.this$0 = userSwitcherActivity;
        this.$item = userRecord;
    }

    public final void onClick(View view) {
        if (!this.this$0.falsingManager.isFalseTap(1) && view.isEnabled()) {
            UserSwitcherController.UserRecord userRecord = this.$item;
            if (!userRecord.isCurrent || userRecord.isGuest) {
                UserSwitcherActivity$adapter$1 userSwitcherActivity$adapter$1 = this.this$0.adapter;
                Objects.requireNonNull(userSwitcherActivity$adapter$1);
                userSwitcherActivity$adapter$1.onUserListItemClicked(userRecord, (UserSwitchDialogController.DialogShower) null);
            }
        }
    }
}
