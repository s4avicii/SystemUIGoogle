package com.android.systemui.recents;

import com.android.systemui.model.SysUiState;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$$ExternalSyntheticLambda2 implements StatusBarWindowCallback {
    public final /* synthetic */ OverviewProxyService f$0;

    public /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda2(OverviewProxyService overviewProxyService) {
        this.f$0 = overviewProxyService;
    }

    public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4) {
        boolean z5;
        OverviewProxyService overviewProxyService = this.f$0;
        Objects.requireNonNull(overviewProxyService);
        SysUiState sysUiState = overviewProxyService.mSysUiState;
        boolean z6 = true;
        if (!z || z2) {
            z5 = false;
        } else {
            z5 = true;
        }
        sysUiState.setFlag(64, z5);
        if (!z || !z2) {
            z6 = false;
        }
        sysUiState.setFlag(512, z6);
        sysUiState.setFlag(8, z3);
        sysUiState.setFlag(2097152, z4);
        sysUiState.commitUpdate(overviewProxyService.mContext.getDisplayId());
    }
}
