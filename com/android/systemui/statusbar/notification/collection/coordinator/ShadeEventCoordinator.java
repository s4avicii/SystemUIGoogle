package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.render.NotifShadeEventSource;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2;
import com.android.systemui.wmshell.WMShell$8$$ExternalSyntheticLambda0;
import java.util.concurrent.Executor;

/* compiled from: ShadeEventCoordinator.kt */
public final class ShadeEventCoordinator implements Coordinator, NotifShadeEventSource {
    public boolean mEntryRemoved;
    public boolean mEntryRemovedByUser;
    public final ShadeEventCoordinatorLogger mLogger;
    public final Executor mMainExecutor;
    public final ShadeEventCoordinator$mNotifCollectionListener$1 mNotifCollectionListener = new ShadeEventCoordinator$mNotifCollectionListener$1(this);
    public Runnable mNotifRemovedByUserCallback;
    public Runnable mShadeEmptiedCallback;

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeRenderListListener(new ShadeEventCoordinator$attach$1(this));
    }

    public final void setNotifRemovedByUserCallback(WMShell$8$$ExternalSyntheticLambda0 wMShell$8$$ExternalSyntheticLambda0) {
        boolean z;
        if (this.mNotifRemovedByUserCallback == null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.mNotifRemovedByUserCallback = wMShell$8$$ExternalSyntheticLambda0;
            return;
        }
        throw new IllegalStateException("mNotifRemovedByUserCallback already set".toString());
    }

    public final void setShadeEmptiedCallback(WMShell$7$$ExternalSyntheticLambda2 wMShell$7$$ExternalSyntheticLambda2) {
        boolean z;
        if (this.mShadeEmptiedCallback == null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.mShadeEmptiedCallback = wMShell$7$$ExternalSyntheticLambda2;
            return;
        }
        throw new IllegalStateException("mShadeEmptiedCallback already set".toString());
    }

    public ShadeEventCoordinator(Executor executor, ShadeEventCoordinatorLogger shadeEventCoordinatorLogger) {
        this.mMainExecutor = executor;
        this.mLogger = shadeEventCoordinatorLogger;
    }
}
