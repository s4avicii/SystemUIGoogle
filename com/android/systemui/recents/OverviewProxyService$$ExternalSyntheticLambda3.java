package com.android.systemui.recents;

import android.graphics.Rect;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.shared.recents.IOverviewProxy;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$$ExternalSyntheticLambda3 implements BiConsumer {
    public final /* synthetic */ OverviewProxyService f$0;

    public /* synthetic */ OverviewProxyService$$ExternalSyntheticLambda3(OverviewProxyService overviewProxyService) {
        this.f$0 = overviewProxyService;
    }

    public final void accept(Object obj, Object obj2) {
        OverviewProxyService overviewProxyService = this.f$0;
        Rect rect = (Rect) obj;
        Rect rect2 = (Rect) obj2;
        Objects.requireNonNull(overviewProxyService);
        try {
            IOverviewProxy iOverviewProxy = overviewProxyService.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onSplitScreenSecondaryBoundsChanged(rect, rect2);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy for split screen bounds.");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onSplitScreenSecondaryBoundsChanged()", e);
        }
    }
}
