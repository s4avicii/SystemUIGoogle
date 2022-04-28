package com.android.systemui.recents;

import android.os.IBinder;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$$ExternalSyntheticLambda0 implements IBinder.DeathRecipient {
    public final /* synthetic */ OverviewProxyService f$0;

    public /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda0(OverviewProxyService overviewProxyService) {
        this.f$0 = overviewProxyService;
    }

    public final void binderDied() {
        OverviewProxyService overviewProxyService = this.f$0;
        Objects.requireNonNull(overviewProxyService);
        if (overviewProxyService.mInputFocusTransferStarted) {
            overviewProxyService.mHandler.post(new AccessPoint$$ExternalSyntheticLambda1(overviewProxyService, 2));
        }
        overviewProxyService.startConnectionToCurrentUser();
        overviewProxyService.mLegacySplitScreenOptional.ifPresent(OverviewProxyService$$ExternalSyntheticLambda6.INSTANCE);
    }
}
