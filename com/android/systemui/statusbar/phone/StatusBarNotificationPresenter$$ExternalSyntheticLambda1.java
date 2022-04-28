package com.android.systemui.statusbar.phone;

import com.android.systemui.Dependency;
import com.android.systemui.ForegroundServiceNotificationListener;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3;
import com.android.systemui.statusbar.NotificationLifetimeExtender;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationViewHierarchyManager;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2;
import com.android.systemui.wmshell.WMShell$8$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBarNotificationPresenter$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ StatusBarNotificationPresenter f$0;
    public final /* synthetic */ NotificationStackScrollLayoutController f$1;
    public final /* synthetic */ NotificationRemoteInputManager f$2;
    public final /* synthetic */ NotificationInterruptStateProvider f$3;

    public /* synthetic */ StatusBarNotificationPresenter$$ExternalSyntheticLambda1(StatusBarNotificationPresenter statusBarNotificationPresenter, NotificationStackScrollLayoutController notificationStackScrollLayoutController, NotificationRemoteInputManager notificationRemoteInputManager, NotificationInterruptStateProvider notificationInterruptStateProvider) {
        this.f$0 = statusBarNotificationPresenter;
        this.f$1 = notificationStackScrollLayoutController;
        this.f$2 = notificationRemoteInputManager;
        this.f$3 = notificationInterruptStateProvider;
    }

    public final void run() {
        StatusBarNotificationPresenter statusBarNotificationPresenter = this.f$0;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$1;
        NotificationRemoteInputManager notificationRemoteInputManager = this.f$2;
        NotificationInterruptStateProvider notificationInterruptStateProvider = this.f$3;
        Objects.requireNonNull(statusBarNotificationPresenter);
        statusBarNotificationPresenter.mKeyguardIndicationController.init();
        NotificationViewHierarchyManager notificationViewHierarchyManager = statusBarNotificationPresenter.mViewHierarchyManager;
        Objects.requireNonNull(notificationStackScrollLayoutController);
        NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl = notificationStackScrollLayoutController.mNotifStackController;
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl = notificationStackScrollLayoutController.mNotificationListContainer;
        Objects.requireNonNull(notificationViewHierarchyManager);
        notificationViewHierarchyManager.mPresenter = statusBarNotificationPresenter;
        notificationViewHierarchyManager.mStackController = notifStackControllerImpl;
        notificationViewHierarchyManager.mListContainer = notificationListContainerImpl;
        if (!notificationViewHierarchyManager.mNotifPipelineFlags.isNewPipelineEnabled()) {
            DynamicPrivacyController dynamicPrivacyController = notificationViewHierarchyManager.mDynamicPrivacyController;
            Objects.requireNonNull(dynamicPrivacyController);
            dynamicPrivacyController.mListeners.add(notificationViewHierarchyManager);
        }
        statusBarNotificationPresenter.mNotifShadeEventSource.setShadeEmptiedCallback(new WMShell$7$$ExternalSyntheticLambda2(statusBarNotificationPresenter, 7));
        statusBarNotificationPresenter.mNotifShadeEventSource.setNotifRemovedByUserCallback(new WMShell$8$$ExternalSyntheticLambda0(statusBarNotificationPresenter, 4));
        if (!statusBarNotificationPresenter.mNotifPipelineFlags.isNewPipelineEnabled()) {
            NotificationEntryManager notificationEntryManager = statusBarNotificationPresenter.mEntryManager;
            Objects.requireNonNull(notificationEntryManager);
            notificationEntryManager.mPresenter = statusBarNotificationPresenter;
            NotificationEntryManager notificationEntryManager2 = statusBarNotificationPresenter.mEntryManager;
            HeadsUpManagerPhone headsUpManagerPhone = statusBarNotificationPresenter.mHeadsUpManager;
            Objects.requireNonNull(notificationEntryManager2);
            notificationEntryManager2.mNotificationLifetimeExtenders.add(headsUpManagerPhone);
            headsUpManagerPhone.setCallback(new ScreenshotController$$ExternalSyntheticLambda3(notificationEntryManager2));
            NotificationEntryManager notificationEntryManager3 = statusBarNotificationPresenter.mEntryManager;
            NotificationGutsManager notificationGutsManager = statusBarNotificationPresenter.mGutsManager;
            Objects.requireNonNull(notificationEntryManager3);
            notificationEntryManager3.mNotificationLifetimeExtenders.add(notificationGutsManager);
            notificationGutsManager.setCallback(new ScreenshotController$$ExternalSyntheticLambda3(notificationEntryManager3));
            NotificationEntryManager notificationEntryManager4 = statusBarNotificationPresenter.mEntryManager;
            Objects.requireNonNull(notificationRemoteInputManager);
            ArrayList<NotificationLifetimeExtender> arrayList = ((NotificationRemoteInputManager.LegacyRemoteInputLifetimeExtender) notificationRemoteInputManager.mRemoteInputListener).mLifetimeExtenders;
            Objects.requireNonNull(notificationEntryManager4);
            Iterator<NotificationLifetimeExtender> it = arrayList.iterator();
            while (it.hasNext()) {
                NotificationLifetimeExtender next = it.next();
                notificationEntryManager4.mNotificationLifetimeExtenders.add(next);
                next.setCallback(new ScreenshotController$$ExternalSyntheticLambda3(notificationEntryManager4));
            }
        }
        notificationInterruptStateProvider.addSuppressor(statusBarNotificationPresenter.mInterruptSuppressor);
        statusBarNotificationPresenter.mLockscreenUserManager.setUpWithPresenter(statusBarNotificationPresenter);
        NotificationMediaManager notificationMediaManager = statusBarNotificationPresenter.mMediaManager;
        Objects.requireNonNull(notificationMediaManager);
        notificationMediaManager.mPresenter = statusBarNotificationPresenter;
        NotificationGutsManager notificationGutsManager2 = statusBarNotificationPresenter.mGutsManager;
        NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl2 = notificationStackScrollLayoutController.mNotificationListContainer;
        StatusBarNotificationPresenter.C15813 r2 = statusBarNotificationPresenter.mOnSettingsClickListener;
        Objects.requireNonNull(notificationGutsManager2);
        notificationGutsManager2.mPresenter = statusBarNotificationPresenter;
        notificationGutsManager2.mListContainer = notificationListContainerImpl2;
        notificationGutsManager2.mOnSettingsClickListener = r2;
        Dependency.get(ForegroundServiceNotificationListener.class);
        statusBarNotificationPresenter.onUserSwitched(statusBarNotificationPresenter.mLockscreenUserManager.getCurrentUserId());
    }
}
