package com.android.systemui.p006qs.user;

import android.content.DialogInterface;
import com.android.systemui.p006qs.QSUserSwitcherEvent;

/* renamed from: com.android.systemui.qs.user.UserSwitchDialogController$showDialog$1$1 */
/* compiled from: UserSwitchDialogController.kt */
public final class UserSwitchDialogController$showDialog$1$1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ UserSwitchDialogController this$0;

    public UserSwitchDialogController$showDialog$1$1(UserSwitchDialogController userSwitchDialogController) {
        this.this$0 = userSwitchDialogController;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_DETAIL_CLOSE);
    }
}
