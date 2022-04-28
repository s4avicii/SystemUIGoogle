package com.android.systemui.p006qs.user;

import android.content.DialogInterface;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator$createActivityLaunchController$1;
import com.android.systemui.p006qs.QSUserSwitcherEvent;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* renamed from: com.android.systemui.qs.user.UserSwitchDialogController$showDialog$1$2 */
/* compiled from: UserSwitchDialogController.kt */
public final class UserSwitchDialogController$showDialog$1$2 implements DialogInterface.OnClickListener {
    public final /* synthetic */ SystemUIDialog $this_with;
    public final /* synthetic */ UserSwitchDialogController this$0;

    public UserSwitchDialogController$showDialog$1$2(UserSwitchDialogController userSwitchDialogController, SystemUIDialog systemUIDialog) {
        this.this$0 = userSwitchDialogController;
        this.$this_with = systemUIDialog;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (!this.this$0.falsingManager.isFalseTap(1)) {
            this.this$0.uiEventLogger.log(QSUserSwitcherEvent.QS_USER_MORE_SETTINGS);
            DialogLaunchAnimator$createActivityLaunchController$1 createActivityLaunchController$default = DialogLaunchAnimator.createActivityLaunchController$default(this.this$0.dialogLaunchAnimator, this.$this_with.getButton(-3));
            if (createActivityLaunchController$default == null) {
                this.$this_with.dismiss();
            }
            this.this$0.activityStarter.postStartActivityDismissingKeyguard(UserSwitchDialogController.USER_SETTINGS_INTENT, 0, createActivityLaunchController$default);
        }
    }
}
