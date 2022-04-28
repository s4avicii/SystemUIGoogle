package com.android.systemui.statusbar.phone.userswitcher;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.user.UserSwitcherActivity;

/* compiled from: StatusBarUserSwitcherController.kt */
public final class StatusBarUserSwitcherControllerImpl$onViewAttached$1 implements View.OnClickListener {
    public final /* synthetic */ StatusBarUserSwitcherControllerImpl this$0;

    public StatusBarUserSwitcherControllerImpl$onViewAttached$1(StatusBarUserSwitcherControllerImpl statusBarUserSwitcherControllerImpl) {
        this.this$0 = statusBarUserSwitcherControllerImpl;
    }

    public final void onClick(View view) {
        if (this.this$0.featureFlags.isEnabled(Flags.FULL_SCREEN_USER_SWITCHER)) {
            Intent intent = new Intent(this.this$0.getContext(), UserSwitcherActivity.class);
            intent.addFlags(335544320);
            ActivityStarter activityStarter = this.this$0.activityStarter;
            GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController = null;
            if (!(view.getParent() instanceof ViewGroup)) {
                Log.wtf("ActivityLaunchAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
            } else {
                ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController(view, (Integer) null, 4);
            }
            activityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) ghostedViewLaunchAnimatorController, true);
            return;
        }
        this.this$0.userSwitcherDialogController.showDialog(view);
    }
}
