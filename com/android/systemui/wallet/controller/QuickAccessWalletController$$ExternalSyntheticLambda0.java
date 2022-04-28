package com.android.systemui.wallet.controller;

import android.app.PendingIntent;
import android.content.Intent;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.wallet.p011ui.WalletActivity;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QuickAccessWalletController$$ExternalSyntheticLambda0 implements QuickAccessWalletClient.WalletPendingIntentCallback {
    public final /* synthetic */ QuickAccessWalletController f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ ActivityStarter f$2;
    public final /* synthetic */ ActivityLaunchAnimator.Controller f$3;

    public /* synthetic */ QuickAccessWalletController$$ExternalSyntheticLambda0(QuickAccessWalletController quickAccessWalletController, boolean z, ActivityStarter activityStarter, ActivityLaunchAnimator.Controller controller) {
        this.f$0 = quickAccessWalletController;
        this.f$1 = z;
        this.f$2 = activityStarter;
        this.f$3 = controller;
    }

    public final void onWalletPendingIntentRetrieved(PendingIntent pendingIntent) {
        QuickAccessWalletController quickAccessWalletController = this.f$0;
        boolean z = this.f$1;
        ActivityStarter activityStarter = this.f$2;
        ActivityLaunchAnimator.Controller controller = this.f$3;
        if (pendingIntent == null) {
            Intent createWalletIntent = quickAccessWalletController.mQuickAccessWalletClient.createWalletIntent();
            if (createWalletIntent == null) {
                createWalletIntent = new Intent(quickAccessWalletController.mContext, WalletActivity.class).setAction("android.intent.action.VIEW");
            }
            if (z) {
                activityStarter.startActivity(createWalletIntent, true, controller, true);
            } else {
                activityStarter.postStartActivityDismissingKeyguard(createWalletIntent, 0, controller);
            }
        } else {
            Objects.requireNonNull(quickAccessWalletController);
            activityStarter.postStartActivityDismissingKeyguard(pendingIntent, controller);
        }
    }
}
