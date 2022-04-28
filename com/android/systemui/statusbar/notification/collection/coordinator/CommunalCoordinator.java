package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.p012wm.shell.TaskView$$ExternalSyntheticLambda2;
import com.android.systemui.communal.CommunalStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class CommunalCoordinator implements Coordinator {
    public final CommunalStateController mCommunalStateController;
    public final Executor mExecutor;
    public final C12551 mFilter = new NotifFilter() {
        public final boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
            CommunalStateController communalStateController = CommunalCoordinator.this.mCommunalStateController;
            Objects.requireNonNull(communalStateController);
            return communalStateController.mCommunalViewShowing;
        }
    };
    public final NotificationEntryManager mNotificationEntryManager;
    public final NotificationLockscreenUserManager mNotificationLockscreenUserManager;
    public final C12562 mStateCallback = new CommunalStateController.Callback() {
        public final void onCommunalViewShowingChanged() {
            CommunalCoordinator.this.mExecutor.execute(new TaskView$$ExternalSyntheticLambda2(this, 4));
        }
    };

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addPreGroupFilter(this.mFilter);
        this.mCommunalStateController.addCallback((CommunalStateController.Callback) this.mStateCallback);
        if (!notifPipeline.isNewPipelineEnabled) {
            this.mNotificationLockscreenUserManager.addKeyguardNotificationSuppressor(new CommunalCoordinator$$ExternalSyntheticLambda0(this));
        }
    }

    public CommunalCoordinator(Executor executor, NotificationEntryManager notificationEntryManager, NotificationLockscreenUserManager notificationLockscreenUserManager, CommunalStateController communalStateController) {
        this.mExecutor = executor;
        this.mNotificationEntryManager = notificationEntryManager;
        this.mNotificationLockscreenUserManager = notificationLockscreenUserManager;
        this.mCommunalStateController = communalStateController;
    }
}
