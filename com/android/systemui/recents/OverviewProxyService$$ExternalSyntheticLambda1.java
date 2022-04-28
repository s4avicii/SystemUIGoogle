package com.android.systemui.recents;

import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shared.recents.IOverviewProxy;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$$ExternalSyntheticLambda1 implements SysUiState.SysUiStateCallback {
    public final /* synthetic */ OverviewProxyService f$0;

    public /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda1(OverviewProxyService overviewProxyService) {
        this.f$0 = overviewProxyService;
    }

    public final void onSystemUiStateChanged(int i) {
        OverviewProxyService overviewProxyService = this.f$0;
        Objects.requireNonNull(overviewProxyService);
        try {
            IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onSystemUiStateChanged(i);
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to notify sysui state change", e);
        }
    }
}
