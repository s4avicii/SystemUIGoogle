package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.util.LinkedHashMap;
import java.util.Objects;

/* compiled from: SmartspaceDedupingCoordinator.kt */
public final class SmartspaceDedupingCoordinator implements Coordinator {
    public final SystemClock clock;
    public final SmartspaceDedupingCoordinator$collectionListener$1 collectionListener = new SmartspaceDedupingCoordinator$collectionListener$1(this);
    public final DelayableExecutor executor;
    public final SmartspaceDedupingCoordinator$filter$1 filter = new SmartspaceDedupingCoordinator$filter$1(this);
    public boolean isOnLockscreen;
    public final NotifPipeline notifPipeline;
    public final NotificationEntryManager notificationEntryManager;
    public final NotificationLockscreenUserManager notificationLockscreenUserManager;
    public final LockscreenSmartspaceController smartspaceController;
    public final SysuiStatusBarStateController statusBarStateController;
    public final SmartspaceDedupingCoordinator$statusBarStateListener$1 statusBarStateListener = new SmartspaceDedupingCoordinator$statusBarStateListener$1(this);
    public LinkedHashMap trackedSmartspaceTargets = new LinkedHashMap();

    public final void attach(NotifPipeline notifPipeline2) {
        notifPipeline2.addPreGroupFilter(this.filter);
        notifPipeline2.addCollectionListener(this.collectionListener);
        this.statusBarStateController.addCallback(this.statusBarStateListener);
        LockscreenSmartspaceController lockscreenSmartspaceController = this.smartspaceController;
        SmartspaceDedupingCoordinator$attach$1 smartspaceDedupingCoordinator$attach$1 = new SmartspaceDedupingCoordinator$attach$1(this);
        Objects.requireNonNull(lockscreenSmartspaceController);
        lockscreenSmartspaceController.execution.assertIsMainThread();
        BcSmartspaceDataPlugin bcSmartspaceDataPlugin = lockscreenSmartspaceController.plugin;
        if (bcSmartspaceDataPlugin != null) {
            bcSmartspaceDataPlugin.registerListener(smartspaceDedupingCoordinator$attach$1);
        }
        if (!notifPipeline2.isNewPipelineEnabled) {
            this.notificationLockscreenUserManager.addKeyguardNotificationSuppressor(new SmartspaceDedupingCoordinator$attach$2(this));
        }
        int state = this.statusBarStateController.getState();
        boolean z = this.isOnLockscreen;
        boolean z2 = true;
        if (state != 1) {
            z2 = false;
        }
        this.isOnLockscreen = z2;
        if (z2 != z) {
            this.filter.invalidateList();
        }
    }

    public final boolean updateFilterStatus(TrackedSmartspaceTarget trackedSmartspaceTarget) {
        boolean z;
        boolean z2 = trackedSmartspaceTarget.shouldFilter;
        NotificationEntry entry = this.notifPipeline.getEntry(trackedSmartspaceTarget.key);
        if (entry != null) {
            long currentTimeMillis = this.clock.currentTimeMillis();
            long lastAudiblyAlertedMillis = entry.mRanking.getLastAudiblyAlertedMillis();
            long j = SmartspaceDedupingCoordinatorKt.ALERT_WINDOW;
            long j2 = lastAudiblyAlertedMillis + j;
            if (j2 != trackedSmartspaceTarget.alertExceptionExpires && j2 > currentTimeMillis) {
                Runnable runnable = trackedSmartspaceTarget.cancelTimeoutRunnable;
                if (runnable != null) {
                    runnable.run();
                }
                trackedSmartspaceTarget.alertExceptionExpires = j2;
                trackedSmartspaceTarget.cancelTimeoutRunnable = this.executor.executeDelayed(new SmartspaceDedupingCoordinator$updateAlertException$1(trackedSmartspaceTarget, this), j2 - currentTimeMillis);
            }
            if (this.clock.currentTimeMillis() - entry.mRanking.getLastAudiblyAlertedMillis() <= j) {
                z = true;
            } else {
                z = false;
            }
            trackedSmartspaceTarget.shouldFilter = !z;
        }
        if (trackedSmartspaceTarget.shouldFilter == z2 || !this.isOnLockscreen) {
            return false;
        }
        return true;
    }

    public SmartspaceDedupingCoordinator(SysuiStatusBarStateController sysuiStatusBarStateController, LockscreenSmartspaceController lockscreenSmartspaceController, NotificationEntryManager notificationEntryManager2, NotificationLockscreenUserManager notificationLockscreenUserManager2, NotifPipeline notifPipeline2, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.smartspaceController = lockscreenSmartspaceController;
        this.notificationEntryManager = notificationEntryManager2;
        this.notificationLockscreenUserManager = notificationLockscreenUserManager2;
        this.notifPipeline = notifPipeline2;
        this.executor = delayableExecutor;
        this.clock = systemClock;
    }
}
