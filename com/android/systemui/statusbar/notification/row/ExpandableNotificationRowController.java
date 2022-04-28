package com.android.systemui.statusbar.notification.row;

import android.util.Log;
import android.view.View;
import com.android.p012wm.shell.pip.phone.PipController$$ExternalSyntheticLambda1;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.notification.FeedbackIcon;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NotifViewController;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public final class ExpandableNotificationRowController implements NotifViewController {
    public final ActivatableNotificationViewController mActivatableNotificationViewController;
    public final boolean mAllowLongPress;
    public final String mAppName;
    public final Optional<BubblesManager> mBubblesManagerOptional;
    public final SystemClock mClock;
    public final ExpandableNotificationRowDragController mDragController;
    public final PipController$$ExternalSyntheticLambda1 mExpansionLogger = new PipController$$ExternalSyntheticLambda1(this);
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final GroupExpansionManager mGroupExpansionManager;
    public final GroupMembershipManager mGroupMembershipManager;
    public final HeadsUpManager mHeadsUpManager;
    public final KeyguardBypassController mKeyguardBypassController;
    public final NotificationListContainer mListContainer;
    public final NotificationMediaManager mMediaManager;
    public final NotificationGutsManager mNotificationGutsManager;
    public final String mNotificationKey;
    public final NotificationLogger mNotificationLogger;
    public final ExpandableNotificationRow.OnExpandClickListener mOnExpandClickListener;
    public final ImageLoader$$ExternalSyntheticLambda0 mOnFeedbackClickListener;
    public final OnUserInteractionCallback mOnUserInteractionCallback;
    public final PeopleNotificationIdentifier mPeopleNotificationIdentifier;
    public final PluginManager mPluginManager;
    public final RemoteInputViewSubcomponent.Factory mRemoteInputViewSubcomponentFactory;
    public final RowContentBindStage mRowContentBindStage;
    public final StatusBarStateController mStatusBarStateController;
    public final C13132 mStatusBarStateListener = new StatusBarStateController.StateListener() {
        public final void onStateChanged(int i) {
            ExpandableNotificationRow expandableNotificationRow = ExpandableNotificationRowController.this.mView;
            boolean z = true;
            if (i != 1) {
                z = false;
            }
            expandableNotificationRow.setOnKeyguard(z);
        }
    };
    public final ExpandableNotificationRow mView;

    public ExpandableNotificationRowController(ExpandableNotificationRow expandableNotificationRow, NotificationListContainer notificationListContainer, RemoteInputViewSubcomponent.Factory factory, ActivatableNotificationViewController activatableNotificationViewController, NotificationMediaManager notificationMediaManager, PluginManager pluginManager, SystemClock systemClock, String str, String str2, KeyguardBypassController keyguardBypassController, GroupMembershipManager groupMembershipManager, GroupExpansionManager groupExpansionManager, RowContentBindStage rowContentBindStage, NotificationLogger notificationLogger, HeadsUpManager headsUpManager, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, StatusBarStateController statusBarStateController, NotificationGutsManager notificationGutsManager, boolean z, OnUserInteractionCallback onUserInteractionCallback, FalsingManager falsingManager, FalsingCollector falsingCollector, FeatureFlags featureFlags, PeopleNotificationIdentifier peopleNotificationIdentifier, Optional<BubblesManager> optional, ExpandableNotificationRowDragController expandableNotificationRowDragController) {
        NotificationGutsManager notificationGutsManager2 = notificationGutsManager;
        this.mView = expandableNotificationRow;
        this.mListContainer = notificationListContainer;
        this.mRemoteInputViewSubcomponentFactory = factory;
        this.mActivatableNotificationViewController = activatableNotificationViewController;
        this.mMediaManager = notificationMediaManager;
        this.mPluginManager = pluginManager;
        this.mClock = systemClock;
        this.mAppName = str;
        this.mNotificationKey = str2;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mGroupMembershipManager = groupMembershipManager;
        this.mGroupExpansionManager = groupExpansionManager;
        this.mRowContentBindStage = rowContentBindStage;
        this.mNotificationLogger = notificationLogger;
        this.mHeadsUpManager = headsUpManager;
        this.mOnExpandClickListener = onExpandClickListener;
        this.mStatusBarStateController = statusBarStateController;
        this.mNotificationGutsManager = notificationGutsManager2;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mFalsingManager = falsingManager;
        Objects.requireNonNull(notificationGutsManager);
        this.mOnFeedbackClickListener = new ImageLoader$$ExternalSyntheticLambda0(notificationGutsManager2);
        this.mAllowLongPress = z;
        this.mFalsingCollector = falsingCollector;
        this.mFeatureFlags = featureFlags;
        this.mPeopleNotificationIdentifier = peopleNotificationIdentifier;
        this.mBubblesManagerOptional = optional;
        this.mDragController = expandableNotificationRowDragController;
    }

    public final void onViewAdded() {
    }

    public final void onViewMoved() {
    }

    public final void onViewRemoved() {
    }

    public final View getChildAt(int i) {
        ExpandableNotificationRow expandableNotificationRow = this.mView;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer == null || notificationChildrenContainer.mAttachedChildren.size() <= i) {
            return null;
        }
        NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
        Objects.requireNonNull(notificationChildrenContainer2);
        return (ExpandableNotificationRow) notificationChildrenContainer2.mAttachedChildren.get(i);
    }

    public final int getChildCount() {
        ArrayList attachedChildren = this.mView.getAttachedChildren();
        if (attachedChildren != null) {
            return attachedChildren.size();
        }
        return 0;
    }

    public final String getNodeLabel() {
        ExpandableNotificationRow expandableNotificationRow = this.mView;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        return notificationEntry.mKey;
    }

    public final void setFeedbackIcon(FeedbackIcon feedbackIcon) {
        this.mView.setFeedbackIcon(feedbackIcon);
    }

    public final void setLastAudiblyAlertedMs(long j) {
        this.mView.setLastAudiblyAlertedMs(j);
    }

    public final void setSystemExpanded(boolean z) {
        this.mView.setSystemExpanded(z);
    }

    public final void setUntruncatedChildCount(int i) {
        ExpandableNotificationRow expandableNotificationRow = this.mView;
        Objects.requireNonNull(expandableNotificationRow);
        if (expandableNotificationRow.mIsSummaryWithChildren) {
            ExpandableNotificationRow expandableNotificationRow2 = this.mView;
            Objects.requireNonNull(expandableNotificationRow2);
            if (expandableNotificationRow2.mChildrenContainer == null) {
                expandableNotificationRow2.mChildrenContainerStub.inflate();
            }
            NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow2.mChildrenContainer;
            Objects.requireNonNull(notificationChildrenContainer);
            notificationChildrenContainer.mUntruncatedChildCount = i;
            notificationChildrenContainer.updateGroupOverflow();
            return;
        }
        Log.w("NotifRowController", "Called setUntruncatedChildCount(" + i + ") on a leaf row");
    }

    public final void addChildAt(NodeController nodeController, int i) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        this.mView.addChildNotification((ExpandableNotificationRow) nodeController.getView(), i);
        this.mListContainer.notifyGroupChildAdded(expandableNotificationRow);
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mChangingPosition = false;
    }

    public final void moveChildTo(NodeController nodeController, int i) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mChangingPosition = true;
        this.mView.removeChildNotification(expandableNotificationRow);
        this.mView.addChildNotification(expandableNotificationRow, i);
        expandableNotificationRow.mChangingPosition = false;
    }

    public final void removeChild(NodeController nodeController, boolean z) {
        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) nodeController.getView();
        if (z) {
            Objects.requireNonNull(expandableNotificationRow);
            expandableNotificationRow.mChangingPosition = true;
        }
        this.mView.removeChildNotification(expandableNotificationRow);
        if (!z) {
            this.mListContainer.notifyGroupChildRemoved(expandableNotificationRow, this.mView);
        }
    }

    public final View getView() {
        return this.mView;
    }
}
