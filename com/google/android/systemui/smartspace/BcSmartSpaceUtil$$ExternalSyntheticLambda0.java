package com.google.android.systemui.smartspace;

import android.app.smartspace.SmartspaceAction;
import android.app.smartspace.SmartspaceTarget;
import android.app.smartspace.SmartspaceTargetEvent;
import android.util.Log;
import android.view.View;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.FalsingManager;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BcSmartSpaceUtil$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ BcSmartspaceCardLoggingInfo f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ BcSmartspaceDataPlugin.IntentStarter f$2;
    public final /* synthetic */ SmartspaceAction f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ View.OnClickListener f$5 = null;
    public final /* synthetic */ BcSmartspaceDataPlugin.SmartspaceEventNotifier f$6;
    public final /* synthetic */ String f$7;
    public final /* synthetic */ SmartspaceTarget f$8;

    public /* synthetic */ BcSmartSpaceUtil$$ExternalSyntheticLambda0(BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo, boolean z, BcSmartspaceDataPlugin.IntentStarter intentStarter, SmartspaceAction smartspaceAction, boolean z2, CardPagerAdapter$$ExternalSyntheticLambda0 cardPagerAdapter$$ExternalSyntheticLambda0, String str, SmartspaceTarget smartspaceTarget) {
        this.f$0 = bcSmartspaceCardLoggingInfo;
        this.f$1 = z;
        this.f$2 = intentStarter;
        this.f$3 = smartspaceAction;
        this.f$4 = z2;
        this.f$6 = cardPagerAdapter$$ExternalSyntheticLambda0;
        this.f$7 = str;
        this.f$8 = smartspaceTarget;
    }

    public final void onClick(View view) {
        BcSmartspaceCardLoggingInfo bcSmartspaceCardLoggingInfo = this.f$0;
        boolean z = this.f$1;
        BcSmartspaceDataPlugin.IntentStarter intentStarter = this.f$2;
        SmartspaceAction smartspaceAction = this.f$3;
        boolean z2 = this.f$4;
        View.OnClickListener onClickListener = this.f$5;
        BcSmartspaceDataPlugin.SmartspaceEventNotifier smartspaceEventNotifier = this.f$6;
        String str = this.f$7;
        SmartspaceTarget smartspaceTarget = this.f$8;
        FalsingManager falsingManager = BcSmartSpaceUtil.sFalsingManager;
        if (falsingManager == null || !falsingManager.isFalseTap(1)) {
            if (bcSmartspaceCardLoggingInfo != null) {
                BcSmartspaceLogger.log(BcSmartspaceEvent.SMARTSPACE_CARD_CLICK, bcSmartspaceCardLoggingInfo);
            }
            if (!z) {
                intentStarter.startFromAction(smartspaceAction, view, z2);
            }
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
            if (smartspaceEventNotifier == null) {
                Log.w(str, "Cannot notify target interaction smartspace event: event notifier null.");
            } else {
                smartspaceEventNotifier.notifySmartspaceEvent(new SmartspaceTargetEvent.Builder(1).setSmartspaceTarget(smartspaceTarget).setSmartspaceActionId(smartspaceAction.getId()).build());
            }
        }
    }
}
