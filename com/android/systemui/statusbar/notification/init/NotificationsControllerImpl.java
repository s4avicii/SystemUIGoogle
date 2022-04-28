package com.android.systemui.statusbar.notification.init;

import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.plugins.statusbar.NotificationSwipeActionHelper;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$1;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$2;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager$bind$3;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.NotificationListController;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStore;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.NotificationRankingManager;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver$initialize$1;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManager;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl$attachToLegacyPipeline$1;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager$attach$1;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.interruption.HeadsUpController;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.NotificationGroupAlertTransferHelper;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.util.Assert;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda1;
import dagger.Lazy;
import java.util.Objects;
import java.util.Optional;

/* compiled from: NotificationsControllerImpl.kt */
public final class NotificationsControllerImpl implements NotificationsController {
    public final AnimatedImageNotificationManager animatedImageNotificationManager;
    public final BindEventManagerImpl bindEventManagerImpl;
    public final NotificationClicker.Builder clickerBuilder;
    public final Lazy<CommonNotifCollection> commonNotifCollection;
    public final DebugModeFilterProvider debugModeFilterProvider;
    public final DeviceProvisionedController deviceProvisionedController;
    public final NotificationEntryManager entryManager;
    public final NotificationGroupAlertTransferHelper groupAlertTransferHelper;
    public final Lazy<NotificationGroupManagerLegacy> groupManagerLegacy;
    public final HeadsUpController headsUpController;
    public final HeadsUpManager headsUpManager;
    public final HeadsUpViewBinder headsUpViewBinder;
    public final NotificationRankingManager legacyRanker;
    public final Lazy<NotifPipelineInitializer> newNotifPipelineInitializer;
    public final NotifBindPipelineInitializer notifBindPipelineInitializer;
    public final NotifLiveDataStore notifLiveDataStore;
    public final Lazy<NotifPipeline> notifPipeline;
    public final NotifPipelineFlags notifPipelineFlags;
    public final NotificationListener notificationListener;
    public final NotificationRowBinderImpl notificationRowBinder;
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;
    public final RemoteInputUriController remoteInputUriController;
    public final TargetSdkResolver targetSdkResolver;

    public NotificationsControllerImpl(NotifPipelineFlags notifPipelineFlags2, NotificationListener notificationListener2, NotificationEntryManager notificationEntryManager, DebugModeFilterProvider debugModeFilterProvider2, NotificationRankingManager notificationRankingManager, Lazy<CommonNotifCollection> lazy, Lazy<NotifPipeline> lazy2, NotifLiveDataStore notifLiveDataStore2, TargetSdkResolver targetSdkResolver2, Lazy<NotifPipelineInitializer> lazy3, NotifBindPipelineInitializer notifBindPipelineInitializer2, DeviceProvisionedController deviceProvisionedController2, NotificationRowBinderImpl notificationRowBinderImpl, BindEventManagerImpl bindEventManagerImpl2, RemoteInputUriController remoteInputUriController2, Lazy<NotificationGroupManagerLegacy> lazy4, NotificationGroupAlertTransferHelper notificationGroupAlertTransferHelper, HeadsUpManager headsUpManager2, HeadsUpController headsUpController2, HeadsUpViewBinder headsUpViewBinder2, NotificationClicker.Builder builder, AnimatedImageNotificationManager animatedImageNotificationManager2, PeopleSpaceWidgetManager peopleSpaceWidgetManager2) {
        this.notifPipelineFlags = notifPipelineFlags2;
        this.notificationListener = notificationListener2;
        this.entryManager = notificationEntryManager;
        this.debugModeFilterProvider = debugModeFilterProvider2;
        this.legacyRanker = notificationRankingManager;
        this.commonNotifCollection = lazy;
        this.notifPipeline = lazy2;
        this.notifLiveDataStore = notifLiveDataStore2;
        this.targetSdkResolver = targetSdkResolver2;
        this.newNotifPipelineInitializer = lazy3;
        this.notifBindPipelineInitializer = notifBindPipelineInitializer2;
        this.deviceProvisionedController = deviceProvisionedController2;
        this.notificationRowBinder = notificationRowBinderImpl;
        this.bindEventManagerImpl = bindEventManagerImpl2;
        this.remoteInputUriController = remoteInputUriController2;
        this.groupManagerLegacy = lazy4;
        this.groupAlertTransferHelper = notificationGroupAlertTransferHelper;
        this.headsUpManager = headsUpManager2;
        this.headsUpController = headsUpController2;
        this.headsUpViewBinder = headsUpViewBinder2;
        this.clickerBuilder = builder;
        this.animatedImageNotificationManager = animatedImageNotificationManager2;
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager2;
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        NotificationEntryManager notificationEntryManager = this.entryManager;
        Objects.requireNonNull(notificationEntryManager);
        indentingPrintWriter.println("NotificationEntryManager (Legacy)");
        int size = notificationEntryManager.mSortedAndFiltered.size();
        indentingPrintWriter.print("  ");
        indentingPrintWriter.println("active notifications: " + size);
        int i = 0;
        while (i < size) {
            NotificationEntryManager.dumpEntry(indentingPrintWriter, i, notificationEntryManager.mSortedAndFiltered.get(i));
            i++;
        }
        synchronized (notificationEntryManager.mActiveNotifications) {
            int size2 = notificationEntryManager.mActiveNotifications.size();
            indentingPrintWriter.print("  ");
            indentingPrintWriter.println("inactive notifications: " + (size2 - i));
            int i2 = 0;
            for (int i3 = 0; i3 < size2; i3++) {
                NotificationEntry valueAt = notificationEntryManager.mActiveNotifications.valueAt(i3);
                if (!notificationEntryManager.mSortedAndFiltered.contains(valueAt)) {
                    NotificationEntryManager.dumpEntry(indentingPrintWriter, i2, valueAt);
                    i2++;
                }
            }
        }
    }

    public final int getActiveNotificationsCount() {
        return ((Number) this.notifLiveDataStore.getActiveNotifCount().getValue()).intValue();
    }

    public final void initialize(StatusBar statusBar, Optional optional, StatusBarNotificationPresenter statusBarNotificationPresenter, NotificationStackScrollLayoutController.NotificationListContainerImpl notificationListContainerImpl, NotificationStackScrollLayoutController.NotifStackControllerImpl notifStackControllerImpl, NotificationActivityStarter notificationActivityStarter, StatusBarNotificationPresenter statusBarNotificationPresenter2) {
        this.notificationListener.registerAsSystemService();
        NotificationListController notificationListController = new NotificationListController(this.entryManager, notificationListContainerImpl, this.deviceProvisionedController);
        notificationListController.mEntryManager.addNotificationEntryListener(notificationListController.mEntryListener);
        notificationListController.mDeviceProvisionedController.addCallback(notificationListController.mDeviceProvisionedListener);
        NotificationRowBinderImpl notificationRowBinderImpl = this.notificationRowBinder;
        NotificationClicker.Builder builder = this.clickerBuilder;
        Optional of = Optional.of(statusBar);
        Objects.requireNonNull(builder);
        NotificationClicker notificationClicker = new NotificationClicker(builder.mLogger, of, optional, notificationActivityStarter);
        Objects.requireNonNull(notificationRowBinderImpl);
        notificationRowBinderImpl.mNotificationClicker = notificationClicker;
        NotificationRowBinderImpl notificationRowBinderImpl2 = this.notificationRowBinder;
        Objects.requireNonNull(notificationRowBinderImpl2);
        notificationRowBinderImpl2.mPresenter = statusBarNotificationPresenter;
        notificationRowBinderImpl2.mListContainer = notificationListContainerImpl;
        notificationRowBinderImpl2.mBindRowCallback = statusBarNotificationPresenter2;
        IconManager iconManager = notificationRowBinderImpl2.mIconManager;
        Objects.requireNonNull(iconManager);
        iconManager.notifCollection.addCollectionListener(iconManager.entryListener);
        HeadsUpViewBinder headsUpViewBinder2 = this.headsUpViewBinder;
        Objects.requireNonNull(headsUpViewBinder2);
        headsUpViewBinder2.mNotificationPresenter = statusBarNotificationPresenter;
        this.notifBindPipelineInitializer.initialize();
        AnimatedImageNotificationManager animatedImageNotificationManager2 = this.animatedImageNotificationManager;
        Objects.requireNonNull(animatedImageNotificationManager2);
        animatedImageNotificationManager2.headsUpManager.addListener(new AnimatedImageNotificationManager$bind$1(animatedImageNotificationManager2));
        animatedImageNotificationManager2.statusBarStateController.addCallback(new AnimatedImageNotificationManager$bind$2(animatedImageNotificationManager2));
        BindEventManager bindEventManager = animatedImageNotificationManager2.bindEventManager;
        AnimatedImageNotificationManager$bind$3 animatedImageNotificationManager$bind$3 = new AnimatedImageNotificationManager$bind$3(animatedImageNotificationManager2);
        Objects.requireNonNull(bindEventManager);
        bindEventManager.listeners.addIfAbsent(animatedImageNotificationManager$bind$3);
        NotifPipelineInitializer notifPipelineInitializer = this.newNotifPipelineInitializer.get();
        NotificationListener notificationListener2 = this.notificationListener;
        NotificationRowBinderImpl notificationRowBinderImpl3 = this.notificationRowBinder;
        Objects.requireNonNull(notifPipelineInitializer);
        notifPipelineInitializer.mDumpManager.registerDumpable("NotifPipeline", notifPipelineInitializer);
        if (notifPipelineInitializer.mNotifPipelineFlags.isNewPipelineEnabled()) {
            NotifInflaterImpl notifInflaterImpl = notifPipelineInitializer.mNotifInflater;
            Objects.requireNonNull(notifInflaterImpl);
            notifInflaterImpl.mNotificationRowBinder = notificationRowBinderImpl3;
        }
        notifPipelineInitializer.mNotifPluggableCoordinators.attach(notifPipelineInitializer.mPipelineWrapper);
        if (notifPipelineInitializer.mNotifPipelineFlags.isNewPipelineEnabled()) {
            ShadeViewManager create = notifPipelineInitializer.mShadeViewManagerFactory.create(notificationListContainerImpl, notifStackControllerImpl);
            RenderStageManager renderStageManager = notifPipelineInitializer.mRenderStageManager;
            Objects.requireNonNull(create);
            renderStageManager.viewRenderer = create.viewRenderer;
        }
        RenderStageManager renderStageManager2 = notifPipelineInitializer.mRenderStageManager;
        ShadeListBuilder shadeListBuilder = notifPipelineInitializer.mListBuilder;
        Objects.requireNonNull(renderStageManager2);
        RenderStageManager$attach$1 renderStageManager$attach$1 = new RenderStageManager$attach$1(renderStageManager2);
        Assert.isMainThread();
        shadeListBuilder.mPipelineState.requireState();
        shadeListBuilder.mOnRenderListListener = renderStageManager$attach$1;
        ShadeListBuilder shadeListBuilder2 = notifPipelineInitializer.mListBuilder;
        NotifCollection notifCollection = notifPipelineInitializer.mNotifCollection;
        Objects.requireNonNull(shadeListBuilder2);
        Assert.isMainThread();
        NotificationInteractionTracker notificationInteractionTracker = shadeListBuilder2.mInteractionTracker;
        Objects.requireNonNull(notifCollection);
        Assert.isMainThread();
        notifCollection.mNotifCollectionListeners.add(notificationInteractionTracker);
        ShadeListBuilder.C12471 r8 = shadeListBuilder2.mReadyForBuildListener;
        Assert.isMainThread();
        notifCollection.mBuildListener = r8;
        shadeListBuilder2.mChoreographer.addOnEvalListener(new WMShell$7$$ExternalSyntheticLambda1(shadeListBuilder2, 4));
        NotifCollection notifCollection2 = notifPipelineInitializer.mNotifCollection;
        GroupCoalescer groupCoalescer = notifPipelineInitializer.mGroupCoalescer;
        Objects.requireNonNull(notifCollection2);
        Assert.isMainThread();
        if (!notifCollection2.mAttached) {
            notifCollection2.mAttached = true;
            NotifCollection.C12441 r6 = notifCollection2.mNotifHandler;
            Objects.requireNonNull(groupCoalescer);
            groupCoalescer.mHandler = r6;
            GroupCoalescer groupCoalescer2 = notifPipelineInitializer.mGroupCoalescer;
            Objects.requireNonNull(groupCoalescer2);
            notificationListener2.addNotificationHandler(groupCoalescer2.mListener);
            Log.d("NotifPipeline", "Notif pipeline initialized. rendering=" + notifPipelineInitializer.mNotifPipelineFlags.isNewPipelineEnabled());
            if (this.notifPipelineFlags.isNewPipelineEnabled()) {
                TargetSdkResolver targetSdkResolver2 = this.targetSdkResolver;
                Objects.requireNonNull(targetSdkResolver2);
                this.notifPipeline.get().addCollectionListener(new TargetSdkResolver$initialize$1(targetSdkResolver2));
            } else {
                TargetSdkResolver targetSdkResolver3 = this.targetSdkResolver;
                NotificationEntryManager notificationEntryManager = this.entryManager;
                Objects.requireNonNull(targetSdkResolver3);
                notificationEntryManager.addCollectionListener(new TargetSdkResolver$initialize$1(targetSdkResolver3));
                RemoteInputUriController remoteInputUriController2 = this.remoteInputUriController;
                NotificationEntryManager notificationEntryManager2 = this.entryManager;
                Objects.requireNonNull(remoteInputUriController2);
                notificationEntryManager2.addCollectionListener(remoteInputUriController2.mInlineUriListener);
                NotificationGroupAlertTransferHelper notificationGroupAlertTransferHelper = this.groupAlertTransferHelper;
                NotificationEntryManager notificationEntryManager3 = this.entryManager;
                NotificationGroupManagerLegacy notificationGroupManagerLegacy = this.groupManagerLegacy.get();
                Objects.requireNonNull(notificationGroupAlertTransferHelper);
                if (notificationGroupAlertTransferHelper.mEntryManager == null) {
                    notificationGroupAlertTransferHelper.mEntryManager = notificationEntryManager3;
                    notificationEntryManager3.addNotificationEntryListener(notificationGroupAlertTransferHelper.mNotificationEntryListener);
                    NotificationGroupAlertTransferHelper.C14641 r4 = notificationGroupAlertTransferHelper.mOnGroupChangeListener;
                    Objects.requireNonNull(notificationGroupManagerLegacy);
                    NotificationGroupManagerLegacy.GroupEventDispatcher groupEventDispatcher = notificationGroupManagerLegacy.mEventDispatcher;
                    Objects.requireNonNull(groupEventDispatcher);
                    groupEventDispatcher.mGroupChangeListeners.add(r4);
                    BindEventManagerImpl bindEventManagerImpl2 = this.bindEventManagerImpl;
                    NotificationEntryManager notificationEntryManager4 = this.entryManager;
                    Objects.requireNonNull(bindEventManagerImpl2);
                    notificationEntryManager4.addNotificationEntryListener(new BindEventManagerImpl$attachToLegacyPipeline$1(bindEventManagerImpl2));
                    this.headsUpManager.addListener(this.groupManagerLegacy.get());
                    this.headsUpManager.addListener(this.groupAlertTransferHelper);
                    HeadsUpController headsUpController2 = this.headsUpController;
                    NotificationEntryManager notificationEntryManager5 = this.entryManager;
                    HeadsUpManager headsUpManager2 = this.headsUpManager;
                    Objects.requireNonNull(headsUpController2);
                    notificationEntryManager5.addCollectionListener(headsUpController2.mCollectionListener);
                    headsUpManager2.addListener(headsUpController2.mOnHeadsUpChangedListener);
                    NotificationGroupManagerLegacy notificationGroupManagerLegacy2 = this.groupManagerLegacy.get();
                    HeadsUpManager headsUpManager3 = this.headsUpManager;
                    Objects.requireNonNull(notificationGroupManagerLegacy2);
                    notificationGroupManagerLegacy2.mHeadsUpManager = headsUpManager3;
                    NotificationGroupAlertTransferHelper notificationGroupAlertTransferHelper2 = this.groupAlertTransferHelper;
                    HeadsUpManager headsUpManager4 = this.headsUpManager;
                    Objects.requireNonNull(notificationGroupAlertTransferHelper2);
                    notificationGroupAlertTransferHelper2.mHeadsUpManager = headsUpManager4;
                    this.debugModeFilterProvider.registerInvalidationListener(new NotificationsControllerImpl$initialize$1(this));
                    NotificationEntryManager notificationEntryManager6 = this.entryManager;
                    NotificationListener notificationListener3 = this.notificationListener;
                    NotificationRankingManager notificationRankingManager = this.legacyRanker;
                    Objects.requireNonNull(notificationEntryManager6);
                    notificationEntryManager6.mRanker = notificationRankingManager;
                    notificationListener3.addNotificationHandler(notificationEntryManager6.mNotifListener);
                    notificationEntryManager6.mDumpManager.registerDumpable(notificationEntryManager6);
                } else {
                    throw new IllegalStateException("Already bound.");
                }
            }
            PeopleSpaceWidgetManager peopleSpaceWidgetManager2 = this.peopleSpaceWidgetManager;
            NotificationListener notificationListener4 = this.notificationListener;
            Objects.requireNonNull(peopleSpaceWidgetManager2);
            notificationListener4.addNotificationHandler(peopleSpaceWidgetManager2.mListener);
            return;
        }
        throw new RuntimeException("attach() called twice");
    }

    public final void requestNotificationUpdate(String str) {
        this.entryManager.updateNotifications(str);
    }

    public final void resetUserExpandedStates() {
        for (NotificationEntry next : this.commonNotifCollection.get().getAllNotifs()) {
            Objects.requireNonNull(next);
            ExpandableNotificationRow expandableNotificationRow = next.row;
            if (expandableNotificationRow != null) {
                boolean isExpanded = expandableNotificationRow.isExpanded(false);
                expandableNotificationRow.mHasUserChangedExpansion = false;
                expandableNotificationRow.mUserExpanded = false;
                if (isExpanded != expandableNotificationRow.isExpanded(false)) {
                    if (expandableNotificationRow.mIsSummaryWithChildren) {
                        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
                        Objects.requireNonNull(notificationChildrenContainer);
                        if (notificationChildrenContainer.mIsLowPriority) {
                            boolean z = notificationChildrenContainer.mUserLocked;
                            if (z) {
                                notificationChildrenContainer.setUserLocked(z);
                            }
                            notificationChildrenContainer.updateHeaderVisibility(true);
                        }
                    }
                    expandableNotificationRow.notifyHeightChanged(false);
                }
                expandableNotificationRow.updateShelfIconColor();
            }
        }
    }

    public final void setNotificationSnoozed(StatusBarNotification statusBarNotification, NotificationSwipeActionHelper.SnoozeOption snoozeOption) {
        if (snoozeOption.getSnoozeCriterion() != null) {
            this.notificationListener.snoozeNotification(statusBarNotification.getKey(), snoozeOption.getSnoozeCriterion().getId());
        } else {
            this.notificationListener.snoozeNotification(statusBarNotification.getKey(), ((long) (snoozeOption.getMinutesToSnoozeFor() * 60)) * 1000);
        }
    }
}
