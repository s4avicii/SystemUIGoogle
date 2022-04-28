package com.android.systemui.statusbar.notification.collection.inflation;

import android.view.View;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.row.RowInflaterTask;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationRowBinderImpl$$ExternalSyntheticLambda1 implements RowInflaterTask.RowInflationFinishedListener {
    public final /* synthetic */ NotificationRowBinderImpl f$0;
    public final /* synthetic */ NotificationEntry f$1;
    public final /* synthetic */ NotifInflater.Params f$2;
    public final /* synthetic */ NotificationRowContentBinder.InflationCallback f$3;

    public /* synthetic */ NotificationRowBinderImpl$$ExternalSyntheticLambda1(NotificationRowBinderImpl notificationRowBinderImpl, NotificationEntry notificationEntry, NotifInflater.Params params, NotificationRowContentBinder.InflationCallback inflationCallback) {
        this.f$0 = notificationRowBinderImpl;
        this.f$1 = notificationEntry;
        this.f$2 = params;
        this.f$3 = inflationCallback;
    }

    public final void onInflationFinished(ExpandableNotificationRow expandableNotificationRow) {
        NotificationRowBinderImpl notificationRowBinderImpl = this.f$0;
        NotificationEntry notificationEntry = this.f$1;
        NotifInflater.Params params = this.f$2;
        NotificationRowContentBinder.InflationCallback inflationCallback = this.f$3;
        Objects.requireNonNull(notificationRowBinderImpl);
        ExpandableNotificationRowController expandableNotificationRowController = notificationRowBinderImpl.mExpandableNotificationRowComponentBuilder.expandableNotificationRow(expandableNotificationRow).notificationEntry(notificationEntry).onExpandClickListener(notificationRowBinderImpl.mPresenter).listContainer(notificationRowBinderImpl.mListContainer).build().getExpandableNotificationRowController();
        Objects.requireNonNull(expandableNotificationRowController);
        expandableNotificationRowController.mActivatableNotificationViewController.init();
        NotificationRowContentBinder.InflationCallback inflationCallback2 = inflationCallback;
        ExpandableNotificationRowController expandableNotificationRowController2 = expandableNotificationRowController;
        NotifInflater.Params params2 = params;
        NotificationEntry notificationEntry2 = notificationEntry;
        expandableNotificationRowController.mView.initialize(notificationEntry, expandableNotificationRowController.mRemoteInputViewSubcomponentFactory, expandableNotificationRowController.mAppName, expandableNotificationRowController.mNotificationKey, expandableNotificationRowController.mExpansionLogger, expandableNotificationRowController.mKeyguardBypassController, expandableNotificationRowController.mGroupMembershipManager, expandableNotificationRowController.mGroupExpansionManager, expandableNotificationRowController.mHeadsUpManager, expandableNotificationRowController.mRowContentBindStage, expandableNotificationRowController2.mOnExpandClickListener, expandableNotificationRowController2.mOnFeedbackClickListener, expandableNotificationRowController2.mFalsingManager, expandableNotificationRowController2.mFalsingCollector, expandableNotificationRowController2.mStatusBarStateController, expandableNotificationRowController2.mPeopleNotificationIdentifier, expandableNotificationRowController2.mOnUserInteractionCallback, expandableNotificationRowController2.mBubblesManagerOptional, expandableNotificationRowController2.mNotificationGutsManager);
        expandableNotificationRowController2.mView.setDescendantFocusability(393216);
        if (expandableNotificationRowController2.mAllowLongPress) {
            if (expandableNotificationRowController2.mFeatureFlags.isEnabled(Flags.NOTIFICATION_DRAG_TO_CONTENTS)) {
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRowController2.mView;
                ExpandableNotificationRowDragController expandableNotificationRowDragController = expandableNotificationRowController2.mDragController;
                Objects.requireNonNull(expandableNotificationRow2);
                expandableNotificationRow2.mDragController = expandableNotificationRowDragController;
            }
            ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRowController2.mView;
            ExpandableNotificationRowController$$ExternalSyntheticLambda0 expandableNotificationRowController$$ExternalSyntheticLambda0 = new ExpandableNotificationRowController$$ExternalSyntheticLambda0(expandableNotificationRowController2);
            Objects.requireNonNull(expandableNotificationRow3);
            expandableNotificationRow3.mLongPressListener = expandableNotificationRowController$$ExternalSyntheticLambda0;
        }
        if (NotificationRemoteInputManager.ENABLE_REMOTE_INPUT) {
            expandableNotificationRowController2.mView.setDescendantFocusability(131072);
        }
        expandableNotificationRowController2.mView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            public final void onViewAttachedToWindow(View view) {
                ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRowController.this.mView;
                Objects.requireNonNull(expandableNotificationRow);
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                long elapsedRealtime = ExpandableNotificationRowController.this.mClock.elapsedRealtime();
                Objects.requireNonNull(notificationEntry);
                if (notificationEntry.initializationTime == -1) {
                    notificationEntry.initializationTime = elapsedRealtime;
                }
                ExpandableNotificationRowController expandableNotificationRowController = ExpandableNotificationRowController.this;
                boolean z = false;
                expandableNotificationRowController.mPluginManager.addPluginListener(expandableNotificationRowController.mView, NotificationMenuRowPlugin.class, false);
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRowController2.mView;
                if (expandableNotificationRowController2.mStatusBarStateController.getState() == 1) {
                    z = true;
                }
                expandableNotificationRow2.setOnKeyguard(z);
                ExpandableNotificationRowController expandableNotificationRowController3 = ExpandableNotificationRowController.this;
                expandableNotificationRowController3.mStatusBarStateController.addCallback(expandableNotificationRowController3.mStatusBarStateListener);
            }

            public final void onViewDetachedFromWindow(View view) {
                ExpandableNotificationRowController expandableNotificationRowController = ExpandableNotificationRowController.this;
                expandableNotificationRowController.mPluginManager.removePluginListener(expandableNotificationRowController.mView);
                ExpandableNotificationRowController expandableNotificationRowController2 = ExpandableNotificationRowController.this;
                expandableNotificationRowController2.mStatusBarStateController.removeCallback(expandableNotificationRowController2.mStatusBarStateListener);
            }
        });
        Objects.requireNonNull(notificationEntry2);
        NotificationEntry notificationEntry3 = notificationEntry2;
        notificationEntry3.mRowController = expandableNotificationRowController2;
        ExpandableNotificationRow expandableNotificationRow4 = expandableNotificationRow;
        notificationRowBinderImpl.mListContainer.bindRow(expandableNotificationRow4);
        NotificationRemoteInputManager notificationRemoteInputManager = notificationRowBinderImpl.mNotificationRemoteInputManager;
        Objects.requireNonNull(notificationRemoteInputManager);
        RemoteInputController remoteInputController = notificationRemoteInputManager.mRemoteInputController;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationContentView notificationContentView = expandableNotificationRow4.mPrivateLayout;
        Objects.requireNonNull(notificationContentView);
        notificationContentView.mRemoteInputController = remoteInputController;
        expandableNotificationRow4.mOnActivatedListener = notificationRowBinderImpl.mPresenter;
        notificationEntry3.row = expandableNotificationRow4;
        notificationRowBinderImpl.mNotifBindPipeline.manageRow(notificationEntry3, expandableNotificationRow4);
        StatusBarNotificationPresenter statusBarNotificationPresenter = (StatusBarNotificationPresenter) notificationRowBinderImpl.mBindRowCallback;
        Objects.requireNonNull(statusBarNotificationPresenter);
        expandableNotificationRow4.mAboveShelfChangedListener = statusBarNotificationPresenter.mAboveShelfObserver;
        KeyguardStateController keyguardStateController = statusBarNotificationPresenter.mKeyguardStateController;
        Objects.requireNonNull(keyguardStateController);
        expandableNotificationRow4.mSecureStateProvider = new StatusBarNotificationPresenter$$ExternalSyntheticLambda2(keyguardStateController);
        notificationRowBinderImpl.updateRow(notificationEntry3, expandableNotificationRow4);
        notificationRowBinderImpl.inflateContentViews(notificationEntry3, params2, expandableNotificationRow4, inflationCallback2);
    }
}
