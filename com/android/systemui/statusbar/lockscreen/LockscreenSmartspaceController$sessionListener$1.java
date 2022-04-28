package com.android.systemui.statusbar.lockscreen;

import android.app.smartspace.SmartspaceSession;
import android.app.smartspace.SmartspaceTarget;
import android.os.UserHandle;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LockscreenSmartspaceController.kt */
public final class LockscreenSmartspaceController$sessionListener$1 implements SmartspaceSession.OnTargetsAvailableListener {
    public final /* synthetic */ LockscreenSmartspaceController this$0;

    public LockscreenSmartspaceController$sessionListener$1(LockscreenSmartspaceController lockscreenSmartspaceController) {
        this.this$0 = lockscreenSmartspaceController;
    }

    public final void onTargetsAvailable(List<SmartspaceTarget> list) {
        this.this$0.execution.assertIsMainThread();
        LockscreenSmartspaceController lockscreenSmartspaceController = this.this$0;
        ArrayList arrayList = new ArrayList();
        for (T next : list) {
            SmartspaceTarget smartspaceTarget = (SmartspaceTarget) next;
            Objects.requireNonNull(lockscreenSmartspaceController);
            UserHandle userHandle = smartspaceTarget.getUserHandle();
            boolean z = true;
            if (!Intrinsics.areEqual(userHandle, lockscreenSmartspaceController.userTracker.getUserHandle()) ? !Intrinsics.areEqual(userHandle, lockscreenSmartspaceController.managedUserHandle) || lockscreenSmartspaceController.userTracker.getUserHandle().getIdentifier() != 0 || (smartspaceTarget.isSensitive() && !lockscreenSmartspaceController.showSensitiveContentForManagedUser) : smartspaceTarget.isSensitive() && !lockscreenSmartspaceController.showSensitiveContentForCurrentUser) {
                z = false;
            }
            if (z) {
                arrayList.add(next);
            }
        }
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = this.this$0.plugin;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.onTargetsAvailable(arrayList);
        }
    }
}
