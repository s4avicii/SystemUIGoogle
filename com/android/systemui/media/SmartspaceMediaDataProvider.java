package com.android.systemui.media;

import android.app.smartspace.SmartspaceTarget;
import android.util.Log;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SmartspaceMediaDataProvider.kt */
public final class SmartspaceMediaDataProvider implements BcSmartspaceDataPlugin {
    public final ArrayList smartspaceMediaTargetListeners = new ArrayList();
    public List<SmartspaceTarget> smartspaceMediaTargets = EmptyList.INSTANCE;

    public final void onTargetsAvailable(List<SmartspaceTarget> list) {
        ArrayList arrayList = new ArrayList();
        for (SmartspaceTarget next : list) {
            if (next.getFeatureType() == 15) {
                arrayList.add(next);
            }
        }
        if (!arrayList.isEmpty()) {
            Log.d("SsMediaDataProvider", Intrinsics.stringPlus("Forwarding Smartspace media updates ", arrayList));
        }
        this.smartspaceMediaTargets = arrayList;
        Iterator it = this.smartspaceMediaTargetListeners.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceTargetListener) it.next()).onSmartspaceTargetsUpdated(this.smartspaceMediaTargets);
        }
    }

    public final void registerListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        this.smartspaceMediaTargetListeners.add(smartspaceTargetListener);
    }

    public final void unregisterListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        this.smartspaceMediaTargetListeners.remove(smartspaceTargetListener);
    }
}
