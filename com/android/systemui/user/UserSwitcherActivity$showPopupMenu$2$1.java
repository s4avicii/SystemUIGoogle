package com.android.systemui.user;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.UserSwitcherActivity;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: UserSwitcherActivity.kt */
public final class UserSwitcherActivity$showPopupMenu$2$1 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ Ref$ObjectRef<UserSwitcherActivity.ItemAdapter> $popupMenuAdapter;
    public final /* synthetic */ UserSwitcherPopupMenu $this_apply;
    public final /* synthetic */ UserSwitcherActivity this$0;

    public UserSwitcherActivity$showPopupMenu$2$1(UserSwitcherActivity userSwitcherActivity, Ref$ObjectRef<UserSwitcherActivity.ItemAdapter> ref$ObjectRef, UserSwitcherPopupMenu userSwitcherPopupMenu) {
        this.this$0 = userSwitcherActivity;
        this.$popupMenuAdapter = ref$ObjectRef;
        this.$this_apply = userSwitcherPopupMenu;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (!this.this$0.falsingManager.isFalseTap(1) && view.isEnabled()) {
            UserSwitcherController.UserRecord userRecord = (UserSwitcherController.UserRecord) ((UserSwitcherActivity.ItemAdapter) this.$popupMenuAdapter.element).getItem(i - 1);
            if (Intrinsics.areEqual(userRecord, this.this$0.manageUserRecord)) {
                this.this$0.startActivity(new Intent().setAction("android.settings.USER_SETTINGS"));
            } else {
                UserSwitcherActivity$adapter$1 userSwitcherActivity$adapter$1 = this.this$0.adapter;
                Objects.requireNonNull(userSwitcherActivity$adapter$1);
                userSwitcherActivity$adapter$1.onUserListItemClicked(userRecord, (UserSwitchDialogController.DialogShower) null);
            }
            this.$this_apply.dismiss();
            Objects.requireNonNull(this.this$0);
            if (!userRecord.isAddUser) {
                this.this$0.finish();
            }
        }
    }
}
