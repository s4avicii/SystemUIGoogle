package com.android.systemui.p006qs;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.FooterActionsController$onClickListener$1 */
/* compiled from: FooterActionsController.kt */
public final class FooterActionsController$onClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ FooterActionsController this$0;

    public FooterActionsController$onClickListener$1(FooterActionsController footerActionsController) {
        this.this$0 = footerActionsController;
    }

    public final void onClick(View view) {
        FooterActionsController footerActionsController = this.this$0;
        Objects.requireNonNull(footerActionsController);
        if (footerActionsController.visible && !this.this$0.falsingManager.isFalseTap(1)) {
            FooterActionsController footerActionsController2 = this.this$0;
            if (view == footerActionsController2.settingsButton) {
                if (!footerActionsController2.deviceProvisionedController.isCurrentUserSetup()) {
                    this.this$0.activityStarter.postQSRunnableDismissingKeyguard(C09791.INSTANCE);
                    return;
                }
                this.this$0.metricsLogger.action(406);
                FooterActionsController footerActionsController3 = this.this$0;
                Objects.requireNonNull(footerActionsController3);
                View view2 = footerActionsController3.settingsButtonContainer;
                GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController = null;
                if (view2 != null) {
                    if (!(view2.getParent() instanceof ViewGroup)) {
                        Log.wtf("ActivityLaunchAnimator", "Skipping animation as view " + view2 + " is not attached to a ViewGroup", new Exception());
                    } else {
                        ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController(view2, (Integer) 33, 4);
                    }
                }
                footerActionsController3.activityStarter.startActivity(new Intent("android.settings.SETTINGS"), true, (ActivityLaunchAnimator.Controller) ghostedViewLaunchAnimatorController);
            } else if (view == footerActionsController2.powerMenuLite) {
                footerActionsController2.uiEventLogger.log(GlobalActionsDialogLite.GlobalActionsEvent.GA_OPEN_QS);
                this.this$0.globalActionsDialog.showOrHideDialog(false, true, view);
            }
        }
    }
}
