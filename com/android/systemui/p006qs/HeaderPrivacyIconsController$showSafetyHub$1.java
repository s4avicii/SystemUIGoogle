package com.android.systemui.p006qs;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.HeaderPrivacyIconsController$showSafetyHub$1 */
/* compiled from: HeaderPrivacyIconsController.kt */
public final class HeaderPrivacyIconsController$showSafetyHub$1 implements Runnable {
    public final /* synthetic */ HeaderPrivacyIconsController this$0;

    public HeaderPrivacyIconsController$showSafetyHub$1(HeaderPrivacyIconsController headerPrivacyIconsController) {
        this.this$0 = headerPrivacyIconsController;
    }

    public final void run() {
        HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
        Objects.requireNonNull(headerPrivacyIconsController);
        ArrayList arrayList = new ArrayList(headerPrivacyIconsController.permissionManager.getIndicatorAppOpUsageData(headerPrivacyIconsController.appOpsController.isMicMuted()));
        this.this$0.privacyLogger.logUnfilteredPermGroupUsage(arrayList);
        final Intent intent = new Intent("android.intent.action.VIEW_SAFETY_HUB");
        intent.putParcelableArrayListExtra("android.permission.extra.PERMISSION_USAGES", arrayList);
        intent.setFlags(268435456);
        final HeaderPrivacyIconsController headerPrivacyIconsController2 = this.this$0;
        headerPrivacyIconsController2.uiExecutor.execute(new Runnable() {
            public final void run() {
                HeaderPrivacyIconsController headerPrivacyIconsController = headerPrivacyIconsController2;
                ActivityStarter activityStarter = headerPrivacyIconsController.activityStarter;
                Intent intent = intent;
                OngoingPrivacyChip ongoingPrivacyChip = headerPrivacyIconsController.privacyChip;
                GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController = null;
                if (!(ongoingPrivacyChip.getParent() instanceof ViewGroup)) {
                    Log.wtf("ActivityLaunchAnimator", "Skipping animation as view " + ongoingPrivacyChip + " is not attached to a ViewGroup", new Exception());
                } else {
                    ghostedViewLaunchAnimatorController = new GhostedViewLaunchAnimatorController((View) ongoingPrivacyChip, (Integer) null, 4);
                }
                activityStarter.startActivity(intent, true, (ActivityLaunchAnimator.Controller) ghostedViewLaunchAnimatorController);
            }
        });
    }
}
