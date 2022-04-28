package com.android.systemui.statusbar.notification.row;

import android.animation.Animator;
import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.metrics.LogMaker;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.service.notification.SnoozeCriterion;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda3;
import com.android.settingslib.users.EditUserInfoController$$ExternalSyntheticLambda3;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.NotificationMenuRowPlugin;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.screenshot.ScreenshotController$$ExternalSyntheticLambda3;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.NotificationLifetimeExtender;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator$mGutsListener$1;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewListener;
import com.android.systemui.statusbar.notification.collection.render.NotifGutsViewManager;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationSnooze;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda18;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda9;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.wmshell.BubblesManager;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class NotificationGutsManager implements Dumpable, NotificationLifetimeExtender, NotifGutsViewManager {
    public final AccessibilityManager mAccessibilityManager;
    public final AssistantFeedbackController mAssistantFeedbackController;
    public final Handler mBgHandler;
    public final Optional<BubblesManager> mBubblesManagerOptional;
    public final ChannelEditorDialogController mChannelEditorDialogController;
    public final Context mContext;
    public final UserContextProvider mContextTracker;
    public final DeviceProvisionedController mDeviceProvisionedController = ((DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class));
    public NotifGutsViewListener mGutsListener;
    public NotificationMenuRowPlugin.MenuItem mGutsMenuItem;
    public final HighPriorityProvider mHighPriorityProvider;
    @VisibleForTesting
    public String mKeyToRemoveOnGutsClosed;
    public final LauncherApps mLauncherApps;
    public NotificationListContainer mListContainer;
    public final NotificationLockscreenUserManager mLockscreenUserManager = ((NotificationLockscreenUserManager) Dependency.get(NotificationLockscreenUserManager.class));
    public final Handler mMainHandler;
    public final MetricsLogger mMetricsLogger = ((MetricsLogger) Dependency.get(MetricsLogger.class));
    public NotificationActivityStarter mNotificationActivityStarter;
    public NotificationGuts mNotificationGutsExposed;
    public NotificationLifetimeExtender.NotificationSafeToRemoveCallback mNotificationLifetimeFinishedCallback;
    public final INotificationManager mNotificationManager;
    public OnSettingsClickListener mOnSettingsClickListener;
    public final OnUserInteractionCallback mOnUserInteractionCallback;
    public C13291 mOpenRunnable;
    public final PeopleSpaceWidgetManager mPeopleSpaceWidgetManager;
    public NotificationPresenter mPresenter;
    public final ShadeController mShadeController;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final StatusBarStateController mStatusBarStateController = ((StatusBarStateController) Dependency.get(StatusBarStateController.class));
    public final UiEventLogger mUiEventLogger;

    public interface OnSettingsClickListener {
    }

    public NotificationGutsManager(Context context, Lazy lazy, Handler handler, Handler handler2, AccessibilityManager accessibilityManager, HighPriorityProvider highPriorityProvider, INotificationManager iNotificationManager, PeopleSpaceWidgetManager peopleSpaceWidgetManager, LauncherApps launcherApps, ShortcutManager shortcutManager, ChannelEditorDialogController channelEditorDialogController, UserContextProvider userContextProvider, AssistantFeedbackController assistantFeedbackController, Optional optional, UiEventLogger uiEventLogger, OnUserInteractionCallback onUserInteractionCallback, ShadeController shadeController, DumpManager dumpManager) {
        this.mContext = context;
        this.mStatusBarOptionalLazy = lazy;
        this.mMainHandler = handler;
        this.mBgHandler = handler2;
        this.mAccessibilityManager = accessibilityManager;
        this.mHighPriorityProvider = highPriorityProvider;
        this.mNotificationManager = iNotificationManager;
        this.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
        this.mLauncherApps = launcherApps;
        this.mContextTracker = userContextProvider;
        this.mChannelEditorDialogController = channelEditorDialogController;
        this.mAssistantFeedbackController = assistantFeedbackController;
        this.mBubblesManagerOptional = optional;
        this.mUiEventLogger = uiEventLogger;
        this.mOnUserInteractionCallback = onUserInteractionCallback;
        this.mShadeController = shadeController;
        AppWidgetManager.getInstance(context);
        dumpManager.registerDumpable(this);
    }

    public final void setShouldManageLifetime(NotificationEntry notificationEntry, boolean z) {
        if (z) {
            this.mKeyToRemoveOnGutsClosed = notificationEntry.mKey;
            if (Log.isLoggable("NotificationGutsManager", 3)) {
                ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("Keeping notification because it's showing guts. "), notificationEntry.mKey, "NotificationGutsManager");
                return;
            }
            return;
        }
        String str = this.mKeyToRemoveOnGutsClosed;
        if (str != null && str.equals(notificationEntry.mKey)) {
            this.mKeyToRemoveOnGutsClosed = null;
            if (Log.isLoggable("NotificationGutsManager", 3)) {
                ExifInterface$$ExternalSyntheticOutline2.m15m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("Notification that was kept for guts was updated. "), notificationEntry.mKey, "NotificationGutsManager");
            }
        }
    }

    public final void closeAndSaveGuts(boolean z, boolean z2, boolean z3, boolean z4) {
        NotificationGuts notificationGuts = this.mNotificationGutsExposed;
        if (notificationGuts != null) {
            notificationGuts.removeCallbacks(this.mOpenRunnable);
            NotificationGuts notificationGuts2 = this.mNotificationGutsExposed;
            Objects.requireNonNull(notificationGuts2);
            NotificationGuts.GutsContent gutsContent = notificationGuts2.mGutsContent;
            if (gutsContent != null && ((gutsContent.isLeavebehind() && z) || (!notificationGuts2.mGutsContent.isLeavebehind() && z3))) {
                notificationGuts2.closeControls(-1, -1, notificationGuts2.mGutsContent.shouldBeSaved(), z2);
            }
        }
        if (z4) {
            this.mListContainer.resetExposedMenuView();
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("NotificationGutsManager state:");
        printWriter.print("  mKeyToRemoveOnGutsClosed (legacy): ");
        printWriter.println(this.mKeyToRemoveOnGutsClosed);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x013e  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x014e A[Catch:{ NameNotFoundException -> 0x015a }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0185 A[SYNTHETIC, Splitter:B:35:0x0185] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x019a  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x01a9  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x01b2 A[SYNTHETIC, Splitter:B:48:0x01b2] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01f0  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x024c  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0252  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x025c  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0265  */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void initializeConversationNotificationInfo(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r20, com.android.systemui.statusbar.notification.row.NotificationConversationInfo r21) throws java.lang.Exception {
        /*
            r19 = this;
            r0 = r19
            r6 = r20
            r7 = r21
            java.lang.String r8 = "ConversationGuts"
            java.util.Objects.requireNonNull(r20)
            com.android.systemui.statusbar.notification.row.NotificationGuts r3 = r6.mGuts
            com.android.systemui.statusbar.notification.collection.NotificationEntry r9 = r6.mEntry
            java.util.Objects.requireNonNull(r9)
            android.service.notification.StatusBarNotification r4 = r9.mSbn
            java.lang.String r10 = r4.getPackageName()
            android.os.UserHandle r1 = r4.getUser()
            android.content.Context r2 = r0.mContext
            int r5 = r1.getIdentifier()
            android.content.pm.PackageManager r11 = com.android.systemui.statusbar.phone.StatusBar.getPackageManagerForUser(r2, r5)
            android.os.UserHandle r2 = android.os.UserHandle.ALL
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x0039
            com.android.systemui.statusbar.NotificationLockscreenUserManager r1 = r0.mLockscreenUserManager
            int r1 = r1.getCurrentUserId()
            if (r1 != 0) goto L_0x0037
            goto L_0x0039
        L_0x0037:
            r13 = 0
            goto L_0x0044
        L_0x0039:
            com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda0 r13 = new com.android.systemui.statusbar.notification.row.NotificationGutsManager$$ExternalSyntheticLambda0
            r1 = r13
            r2 = r19
            r5 = r10
            r6 = r20
            r1.<init>(r2, r3, r4, r5, r6)
        L_0x0044:
            com.android.settingslib.notification.ConversationIconFactory r1 = new com.android.settingslib.notification.ConversationIconFactory
            android.content.Context r2 = r0.mContext
            android.content.pm.LauncherApps r3 = r0.mLauncherApps
            r4 = 0
            android.util.IconDrawableFactory.newInstance(r2, r4)
            android.content.Context r5 = r0.mContext
            android.content.res.Resources r5 = r5.getResources()
            r6 = 2131166631(0x7f0705a7, float:1.7947513E38)
            int r5 = r5.getDimensionPixelSize(r6)
            r1.<init>(r2, r3, r11, r5)
            java.util.Objects.requireNonNull(r21)
            int r2 = r7.mSelectedAction
            com.android.systemui.people.widget.PeopleSpaceWidgetManager r3 = r0.mPeopleSpaceWidgetManager
            android.app.INotificationManager r5 = r0.mNotificationManager
            com.android.systemui.statusbar.notification.row.OnUserInteractionCallback r6 = r0.mOnUserInteractionCallback
            android.app.NotificationChannel r14 = r9.getChannel()
            android.app.Notification$BubbleMetadata r15 = r9.mBubbleMetadata
            com.android.systemui.settings.UserContextProvider r12 = r0.mContextTracker
            r12.getUserContext()
            com.android.systemui.statusbar.policy.DeviceProvisionedController r12 = r0.mDeviceProvisionedController
            boolean r12 = r12.isDeviceProvisioned()
            android.os.Handler r4 = r0.mMainHandler
            r16 = r8
            android.os.Handler r8 = r0.mBgHandler
            r17 = r8
            java.util.Optional<com.android.systemui.wmshell.BubblesManager> r8 = r0.mBubblesManagerOptional
            com.android.systemui.statusbar.phone.ShadeController r0 = r0.mShadeController
            r18 = r4
            r4 = 0
            r7.mPressedApply = r4
            r7.mSelectedAction = r2
            r7.mINotificationManager = r5
            r7.mPeopleSpaceWidgetManager = r3
            r7.mOnUserInteractionCallback = r6
            r7.mPackageName = r10
            r7.mEntry = r9
            android.service.notification.StatusBarNotification r2 = r9.mSbn
            r7.mSbn = r2
            r7.mPm = r11
            r7.mAppName = r10
            r7.mOnSettingsClickListener = r13
            r7.mNotificationChannel = r14
            int r2 = r2.getUid()
            r7.mAppUid = r2
            android.service.notification.StatusBarNotification r2 = r7.mSbn
            java.lang.String r2 = r2.getOpPkg()
            r7.mDelegatePkg = r2
            r7.mIsDeviceProvisioned = r12
            r7.mIconFactory = r1
            r7.mBubbleMetadata = r15
            r7.mBubblesManagerOptional = r8
            r7.mShadeController = r0
            r0 = r18
            r7.mMainHandler = r0
            r0 = r17
            r7.mBgHandler = r0
            android.service.notification.NotificationListenerService$Ranking r0 = r9.mRanking
            android.content.pm.ShortcutInfo r0 = r0.getConversationShortcutInfo()
            r7.mShortcutInfo = r0
            if (r0 == 0) goto L_0x0283
            android.content.Context r0 = r21.getContext()
            android.app.INotificationManager r1 = r7.mINotificationManager
            android.app.NotificationChannel r2 = r7.mNotificationChannel
            android.app.NotificationChannel r0 = com.android.systemui.statusbar.notification.NotificationChannelHelper.createConversationChannelIfNeeded(r0, r1, r9, r2)
            r7.mNotificationChannel = r0
            r1 = 2
            android.app.INotificationManager r0 = r7.mINotificationManager     // Catch:{ RemoteException -> 0x00eb }
            java.lang.String r2 = r7.mPackageName     // Catch:{ RemoteException -> 0x00eb }
            int r3 = r7.mAppUid     // Catch:{ RemoteException -> 0x00eb }
            int r0 = r0.getBubblePreferenceForPackage(r2, r3)     // Catch:{ RemoteException -> 0x00eb }
            r7.mAppBubble = r0     // Catch:{ RemoteException -> 0x00eb }
            r3 = r16
            goto L_0x00f5
        L_0x00eb:
            r0 = move-exception
            java.lang.String r2 = "can't reach OS"
            r3 = r16
            android.util.Log.e(r3, r2, r0)
            r7.mAppBubble = r1
        L_0x00f5:
            r0 = 2131428564(0x7f0b04d4, float:1.8478776E38)
            android.view.View r0 = r7.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.app.NotificationChannel r2 = r7.mNotificationChannel
            java.lang.CharSequence r2 = r2.getName()
            r0.setText(r2)
            android.app.NotificationChannel r0 = r7.mNotificationChannel
            if (r0 == 0) goto L_0x0128
            java.lang.String r0 = r0.getGroup()
            if (r0 == 0) goto L_0x0128
            android.app.INotificationManager r0 = r7.mINotificationManager     // Catch:{ RemoteException -> 0x0128 }
            android.app.NotificationChannel r2 = r7.mNotificationChannel     // Catch:{ RemoteException -> 0x0128 }
            java.lang.String r2 = r2.getGroup()     // Catch:{ RemoteException -> 0x0128 }
            java.lang.String r4 = r7.mPackageName     // Catch:{ RemoteException -> 0x0128 }
            int r5 = r7.mAppUid     // Catch:{ RemoteException -> 0x0128 }
            android.app.NotificationChannelGroup r0 = r0.getNotificationChannelGroupForPackage(r2, r4, r5)     // Catch:{ RemoteException -> 0x0128 }
            if (r0 == 0) goto L_0x0128
            java.lang.CharSequence r0 = r0.getName()     // Catch:{ RemoteException -> 0x0128 }
            goto L_0x0129
        L_0x0128:
            r0 = 0
        L_0x0129:
            r2 = 2131428044(0x7f0b02cc, float:1.8477721E38)
            android.view.View r2 = r7.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r4 = 8
            if (r0 == 0) goto L_0x013e
            r2.setText(r0)
            r5 = 0
            r2.setVisibility(r5)
            goto L_0x0141
        L_0x013e:
            r2.setVisibility(r4)
        L_0x0141:
            android.content.pm.PackageManager r0 = r7.mPm     // Catch:{ NameNotFoundException -> 0x015a }
            java.lang.String r2 = r7.mPackageName     // Catch:{ NameNotFoundException -> 0x015a }
            r5 = 795136(0xc2200, float:1.114223E-39)
            android.content.pm.ApplicationInfo r0 = r0.getApplicationInfo(r2, r5)     // Catch:{ NameNotFoundException -> 0x015a }
            if (r0 == 0) goto L_0x015a
            android.content.pm.PackageManager r2 = r7.mPm     // Catch:{ NameNotFoundException -> 0x015a }
            java.lang.CharSequence r0 = r2.getApplicationLabel(r0)     // Catch:{ NameNotFoundException -> 0x015a }
            java.lang.String r0 = java.lang.String.valueOf(r0)     // Catch:{ NameNotFoundException -> 0x015a }
            r7.mAppName = r0     // Catch:{ NameNotFoundException -> 0x015a }
        L_0x015a:
            r0 = 2131428593(0x7f0b04f1, float:1.8478835E38)
            android.view.View r0 = r7.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            java.lang.String r2 = r7.mAppName
            r0.setText(r2)
            android.app.NotificationChannel r0 = r7.mNotificationChannel
            boolean r0 = r0.isImportantConversation()
            r7.bindIcon(r0)
            r0 = 2131428615(0x7f0b0507, float:1.847888E38)
            android.view.View r0 = r7.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            r7.mPriorityDescriptionView = r0
            boolean r0 = r21.willShowAsBubble()
            java.lang.String r2 = "Could not check conversation senders"
            r5 = 1
            if (r0 == 0) goto L_0x01a3
            android.app.INotificationManager r0 = r7.mINotificationManager     // Catch:{ RemoteException -> 0x0193 }
            android.app.NotificationManager$Policy r0 = r0.getConsolidatedNotificationPolicy()     // Catch:{ RemoteException -> 0x0193 }
            int r0 = r0.priorityConversationSenders     // Catch:{ RemoteException -> 0x0193 }
            if (r0 == r1) goto L_0x0191
            if (r0 != r5) goto L_0x0197
        L_0x0191:
            r0 = r5
            goto L_0x0198
        L_0x0193:
            r0 = move-exception
            android.util.Log.e(r3, r2, r0)
        L_0x0197:
            r0 = 0
        L_0x0198:
            if (r0 == 0) goto L_0x01a3
            android.widget.TextView r0 = r7.mPriorityDescriptionView
            r1 = 2131952901(0x7f130505, float:1.9542258E38)
            r0.setText(r1)
            goto L_0x01d8
        L_0x01a3:
            boolean r0 = r21.willShowAsBubble()
            if (r0 == 0) goto L_0x01b2
            android.widget.TextView r0 = r7.mPriorityDescriptionView
            r1 = 2131952903(0x7f130507, float:1.9542262E38)
            r0.setText(r1)
            goto L_0x01d8
        L_0x01b2:
            android.app.INotificationManager r0 = r7.mINotificationManager     // Catch:{ RemoteException -> 0x01c0 }
            android.app.NotificationManager$Policy r0 = r0.getConsolidatedNotificationPolicy()     // Catch:{ RemoteException -> 0x01c0 }
            int r0 = r0.priorityConversationSenders     // Catch:{ RemoteException -> 0x01c0 }
            if (r0 == r1) goto L_0x01be
            if (r0 != r5) goto L_0x01c4
        L_0x01be:
            r0 = r5
            goto L_0x01c5
        L_0x01c0:
            r0 = move-exception
            android.util.Log.e(r3, r2, r0)
        L_0x01c4:
            r0 = 0
        L_0x01c5:
            if (r0 == 0) goto L_0x01d0
            android.widget.TextView r0 = r7.mPriorityDescriptionView
            r1 = 2131952904(0x7f130508, float:1.9542264E38)
            r0.setText(r1)
            goto L_0x01d8
        L_0x01d0:
            android.widget.TextView r0 = r7.mPriorityDescriptionView
            r1 = 2131952902(0x7f130506, float:1.954226E38)
            r0.setText(r1)
        L_0x01d8:
            r0 = 2131427812(0x7f0b01e4, float:1.847725E38)
            android.view.View r0 = r7.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            java.lang.String r1 = r7.mPackageName
            java.lang.String r2 = r7.mDelegatePkg
            boolean r1 = android.text.TextUtils.equals(r1, r2)
            if (r1 != 0) goto L_0x01f0
            r1 = 0
            r0.setVisibility(r1)
            goto L_0x01f3
        L_0x01f0:
            r0.setVisibility(r4)
        L_0x01f3:
            int r0 = r7.mAppBubble
            if (r0 != r5) goto L_0x0215
            r0 = 2131427811(0x7f0b01e3, float:1.8477249E38)
            android.view.View r0 = r7.findViewById(r0)
            android.widget.TextView r0 = (android.widget.TextView) r0
            android.content.res.Resources r1 = r21.getResources()
            r2 = 2131952899(0x7f130503, float:1.9542254E38)
            java.lang.Object[] r3 = new java.lang.Object[r5]
            java.lang.String r5 = r7.mAppName
            r6 = 0
            r3[r6] = r5
            java.lang.String r1 = r1.getString(r2, r3)
            r0.setText(r1)
        L_0x0215:
            r0 = 2131428611(0x7f0b0503, float:1.8478871E38)
            android.view.View r0 = r7.findViewById(r0)
            com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda5 r1 = r7.mOnFavoriteClick
            r0.setOnClickListener(r1)
            r0 = 2131427807(0x7f0b01df, float:1.847724E38)
            android.view.View r0 = r7.findViewById(r0)
            com.android.settingslib.widget.LayoutPreference$$ExternalSyntheticLambda0 r1 = r7.mOnDefaultClick
            r0.setOnClickListener(r1)
            r0 = 2131428861(0x7f0b05fd, float:1.8479378E38)
            android.view.View r0 = r7.findViewById(r0)
            com.android.systemui.screenshot.LongScreenshotActivity$$ExternalSyntheticLambda0 r1 = r7.mOnMuteClick
            r0.setOnClickListener(r1)
            r0 = 2131428121(0x7f0b0319, float:1.8477878E38)
            android.view.View r0 = r7.findViewById(r0)
            int r1 = r7.mAppUid
            if (r1 < 0) goto L_0x0252
            com.android.systemui.statusbar.notification.row.NotificationConversationInfo$OnSettingsClickListener r2 = r7.mOnSettingsClickListener
            if (r2 == 0) goto L_0x0252
            boolean r2 = r7.mIsDeviceProvisioned
            if (r2 == 0) goto L_0x0252
            com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda1 r12 = new com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda1
            r12.<init>(r7, r1)
            goto L_0x0253
        L_0x0252:
            r12 = 0
        L_0x0253:
            r0.setOnClickListener(r12)
            boolean r1 = r0.hasOnClickListeners()
            if (r1 == 0) goto L_0x025d
            r4 = 0
        L_0x025d:
            r0.setVisibility(r4)
            int r0 = r7.mSelectedAction
            r1 = -1
            if (r0 != r1) goto L_0x0269
            int r0 = r21.getPriority()
        L_0x0269:
            r1 = 0
            r7.updateToggleActions(r0, r1)
            r0 = 2131427865(0x7f0b0219, float:1.8477358E38)
            android.view.View r0 = r7.findViewById(r0)
            com.android.systemui.statusbar.notification.row.NotificationConversationInfo$$ExternalSyntheticLambda0 r1 = r7.mOnDone
            r0.setOnClickListener(r1)
            com.android.systemui.statusbar.notification.row.NotificationGuts r1 = r7.mGutsContainer
            android.view.View$AccessibilityDelegate r1 = r1.getAccessibilityDelegate()
            r0.setAccessibilityDelegate(r1)
            return
        L_0x0283:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Does not have required information"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.row.NotificationGutsManager.initializeConversationNotificationInfo(com.android.systemui.statusbar.notification.row.ExpandableNotificationRow, com.android.systemui.statusbar.notification.row.NotificationConversationInfo):void");
    }

    @VisibleForTesting
    public void initializeNotificationInfo(ExpandableNotificationRow expandableNotificationRow, NotificationInfo notificationInfo) throws Exception {
        NotificationGutsManager$$ExternalSyntheticLambda3 notificationGutsManager$$ExternalSyntheticLambda3;
        boolean z;
        boolean z2;
        NotificationInfo$$ExternalSyntheticLambda0 notificationInfo$$ExternalSyntheticLambda0;
        int i;
        CharSequence charSequence;
        LogMaker logMaker;
        NotificationChannelGroup notificationChannelGroupForPackage;
        ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
        NotificationInfo notificationInfo2 = notificationInfo;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationGuts notificationGuts = expandableNotificationRow2.mGuts;
        NotificationEntry notificationEntry = expandableNotificationRow2.mEntry;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = StatusBar.getPackageManagerForUser(this.mContext, user.getIdentifier());
        NotificationGutsManager$$ExternalSyntheticLambda2 notificationGutsManager$$ExternalSyntheticLambda2 = new NotificationGutsManager$$ExternalSyntheticLambda2(this, notificationGuts, statusBarNotification, expandableNotificationRow2);
        if (!user.equals(UserHandle.ALL) || this.mLockscreenUserManager.getCurrentUserId() == 0) {
            notificationGutsManager$$ExternalSyntheticLambda3 = new NotificationGutsManager$$ExternalSyntheticLambda3(this, notificationGuts, statusBarNotification, packageName, expandableNotificationRow);
        } else {
            notificationGutsManager$$ExternalSyntheticLambda3 = null;
        }
        INotificationManager iNotificationManager = this.mNotificationManager;
        OnUserInteractionCallback onUserInteractionCallback = this.mOnUserInteractionCallback;
        ChannelEditorDialogController channelEditorDialogController = this.mChannelEditorDialogController;
        NotificationChannel channel = expandableNotificationRow2.mEntry.getChannel();
        ArraySet<NotificationChannel> uniqueChannels = expandableNotificationRow.getUniqueChannels();
        NotificationEntry notificationEntry2 = expandableNotificationRow2.mEntry;
        UiEventLogger uiEventLogger = this.mUiEventLogger;
        boolean isDeviceProvisioned = this.mDeviceProvisionedController.isDeviceProvisioned();
        boolean isNonblockable = expandableNotificationRow.getIsNonblockable();
        boolean isHighPriority = this.mHighPriorityProvider.isHighPriority(expandableNotificationRow2.mEntry);
        AssistantFeedbackController assistantFeedbackController = this.mAssistantFeedbackController;
        Objects.requireNonNull(notificationInfo);
        notificationInfo2.mINotificationManager = iNotificationManager;
        notificationInfo2.mMetricsLogger = (MetricsLogger) Dependency.get(MetricsLogger.class);
        notificationInfo2.mOnUserInteractionCallback = onUserInteractionCallback;
        notificationInfo2.mChannelEditorDialogController = channelEditorDialogController;
        notificationInfo2.mAssistantFeedbackController = assistantFeedbackController;
        notificationInfo2.mPackageName = packageName;
        notificationInfo2.mUniqueChannelsInRow = uniqueChannels;
        notificationInfo2.mNumUniqueChannelsInRow = uniqueChannels.size();
        notificationInfo2.mEntry = notificationEntry2;
        Objects.requireNonNull(notificationEntry2);
        notificationInfo2.mSbn = notificationEntry2.mSbn;
        notificationInfo2.mPm = packageManagerForUser;
        notificationInfo2.mAppSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda2;
        notificationInfo2.mAppName = notificationInfo2.mPackageName;
        notificationInfo2.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda3;
        notificationInfo2.mSingleNotificationChannel = channel;
        notificationInfo2.mStartingChannelImportance = channel.getImportance();
        notificationInfo2.mWasShownHighPriority = isHighPriority;
        notificationInfo2.mIsNonblockable = isNonblockable;
        notificationInfo2.mAppUid = notificationInfo2.mSbn.getUid();
        notificationInfo2.mDelegatePkg = notificationInfo2.mSbn.getOpPkg();
        notificationInfo2.mIsDeviceProvisioned = isDeviceProvisioned;
        AssistantFeedbackController assistantFeedbackController2 = notificationInfo2.mAssistantFeedbackController;
        Objects.requireNonNull(assistantFeedbackController2);
        notificationInfo2.mShowAutomaticSetting = assistantFeedbackController2.mFeedbackEnabled;
        notificationInfo2.mUiEventLogger = uiEventLogger;
        int numNotificationChannelsForPackage = notificationInfo2.mINotificationManager.getNumNotificationChannelsForPackage(packageName, notificationInfo2.mAppUid, false);
        int i2 = notificationInfo2.mNumUniqueChannelsInRow;
        if (i2 != 0) {
            if (i2 == 1 && notificationInfo2.mSingleNotificationChannel.getId().equals("miscellaneous") && numNotificationChannelsForPackage == 1) {
                z = true;
            } else {
                z = false;
            }
            notificationInfo2.mIsSingleDefaultChannel = z;
            if (notificationInfo.getAlertingBehavior() == 2) {
                z2 = true;
            } else {
                z2 = false;
            }
            notificationInfo2.mIsAutomaticChosen = z2;
            notificationInfo2.mPkgIcon = null;
            try {
                ApplicationInfo applicationInfo = notificationInfo2.mPm.getApplicationInfo(notificationInfo2.mPackageName, 795136);
                if (applicationInfo != null) {
                    notificationInfo2.mAppName = String.valueOf(notificationInfo2.mPm.getApplicationLabel(applicationInfo));
                    notificationInfo2.mPkgIcon = notificationInfo2.mPm.getApplicationIcon(applicationInfo);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                notificationInfo2.mPkgIcon = notificationInfo2.mPm.getDefaultActivityIcon();
            }
            ((ImageView) notificationInfo2.findViewById(C1777R.C1779id.pkg_icon)).setImageDrawable(notificationInfo2.mPkgIcon);
            ((TextView) notificationInfo2.findViewById(C1777R.C1779id.pkg_name)).setText(notificationInfo2.mAppName);
            TextView textView = (TextView) notificationInfo2.findViewById(C1777R.C1779id.delegate_name);
            if (!TextUtils.equals(notificationInfo2.mPackageName, notificationInfo2.mDelegatePkg)) {
                textView.setVisibility(0);
            } else {
                textView.setVisibility(8);
            }
            View findViewById = notificationInfo2.findViewById(C1777R.C1779id.app_settings);
            PackageManager packageManager = notificationInfo2.mPm;
            String str = notificationInfo2.mPackageName;
            NotificationChannel notificationChannel = notificationInfo2.mSingleNotificationChannel;
            int id = notificationInfo2.mSbn.getId();
            String tag = notificationInfo2.mSbn.getTag();
            Intent intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.NOTIFICATION_PREFERENCES").setPackage(str);
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
            if (queryIntentActivities == null || queryIntentActivities.size() == 0 || queryIntentActivities.get(0) == null) {
                intent = null;
            } else {
                ActivityInfo activityInfo = queryIntentActivities.get(0).activityInfo;
                intent.setClassName(activityInfo.packageName, activityInfo.name);
                if (notificationChannel != null) {
                    intent.putExtra("android.intent.extra.CHANNEL_ID", notificationChannel.getId());
                }
                intent.putExtra("android.intent.extra.NOTIFICATION_ID", id);
                intent.putExtra("android.intent.extra.NOTIFICATION_TAG", tag);
            }
            if (intent == null || TextUtils.isEmpty(notificationInfo2.mSbn.getNotification().getSettingsText())) {
                findViewById.setVisibility(8);
            } else {
                findViewById.setVisibility(0);
                findViewById.setOnClickListener(new EditUserInfoController$$ExternalSyntheticLambda3(notificationInfo2, intent, 1));
            }
            View findViewById2 = notificationInfo2.findViewById(C1777R.C1779id.info);
            int i3 = notificationInfo2.mAppUid;
            if (i3 < 0 || notificationInfo2.mOnSettingsClickListener == null || !notificationInfo2.mIsDeviceProvisioned) {
                notificationInfo$$ExternalSyntheticLambda0 = null;
            } else {
                notificationInfo$$ExternalSyntheticLambda0 = new NotificationInfo$$ExternalSyntheticLambda0(notificationInfo2, i3);
            }
            findViewById2.setOnClickListener(notificationInfo$$ExternalSyntheticLambda0);
            if (findViewById2.hasOnClickListeners()) {
                i = 0;
            } else {
                i = 8;
            }
            findViewById2.setVisibility(i);
            TextView textView2 = (TextView) notificationInfo2.findViewById(C1777R.C1779id.channel_name);
            if (notificationInfo2.mIsSingleDefaultChannel || notificationInfo2.mNumUniqueChannelsInRow > 1) {
                textView2.setVisibility(8);
            } else {
                textView2.setText(notificationInfo2.mSingleNotificationChannel.getName());
            }
            NotificationChannel notificationChannel2 = notificationInfo2.mSingleNotificationChannel;
            if (notificationChannel2 == null || notificationChannel2.getGroup() == null || (notificationChannelGroupForPackage = notificationInfo2.mINotificationManager.getNotificationChannelGroupForPackage(notificationInfo2.mSingleNotificationChannel.getGroup(), notificationInfo2.mPackageName, notificationInfo2.mAppUid)) == null) {
                charSequence = null;
            } else {
                charSequence = notificationChannelGroupForPackage.getName();
            }
            TextView textView3 = (TextView) notificationInfo2.findViewById(C1777R.C1779id.group_name);
            if (charSequence != null) {
                textView3.setText(charSequence);
                textView3.setVisibility(0);
            } else {
                textView3.setVisibility(8);
            }
            notificationInfo.bindInlineControls();
            notificationInfo2.logUiEvent(NotificationControlsEvent.NOTIFICATION_CONTROLS_OPEN);
            MetricsLogger metricsLogger = notificationInfo2.mMetricsLogger;
            StatusBarNotification statusBarNotification2 = notificationInfo2.mSbn;
            if (statusBarNotification2 == null) {
                logMaker = new LogMaker(1621);
            } else {
                logMaker = statusBarNotification2.getLogMaker().setCategory(1621);
            }
            metricsLogger.write(logMaker.setCategory(204).setType(1).setSubtype(0));
            return;
        }
        throw new IllegalArgumentException("bindNotification requires at least one channel");
    }

    @VisibleForTesting
    public boolean openGutsInternal(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
        if (!(view instanceof ExpandableNotificationRow)) {
            return false;
        }
        if (view.getWindowToken() == null) {
            Log.e("NotificationGutsManager", "Trying to show notification guts, but not attached to window");
            return false;
        }
        final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) view;
        view.performHapticFeedback(0);
        if (expandableNotificationRow.areGutsExposed()) {
            closeAndSaveGuts(false, false, true, true);
            return false;
        }
        if (expandableNotificationRow.mGuts == null) {
            expandableNotificationRow.mGutsStub.inflate();
        }
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        this.mNotificationGutsExposed = notificationGuts;
        if (!bindGuts(expandableNotificationRow, menuItem) || notificationGuts == null) {
            return false;
        }
        notificationGuts.setVisibility(4);
        final NotificationGuts notificationGuts2 = notificationGuts;
        final int i3 = i;
        final int i4 = i2;
        final NotificationMenuRowPlugin.MenuItem menuItem2 = menuItem;
        C13291 r2 = new Runnable() {
            public final void run() {
                if (expandableNotificationRow.getWindowToken() == null) {
                    Log.e("NotificationGutsManager", "Trying to show notification guts in post(), but not attached to window");
                    return;
                }
                boolean z = false;
                notificationGuts2.setVisibility(0);
                if (NotificationGutsManager.this.mStatusBarStateController.getState() == 1 && !NotificationGutsManager.this.mAccessibilityManager.isTouchExplorationEnabled()) {
                    z = true;
                }
                NotificationGuts notificationGuts = notificationGuts2;
                Objects.requireNonNull(expandableNotificationRow);
                int i = i3;
                int i2 = i4;
                ExpandableNotificationRow expandableNotificationRow = expandableNotificationRow;
                Objects.requireNonNull(expandableNotificationRow);
                StatusBar$$ExternalSyntheticLambda18 statusBar$$ExternalSyntheticLambda18 = new StatusBar$$ExternalSyntheticLambda18(expandableNotificationRow, 9);
                Objects.requireNonNull(notificationGuts);
                if (notificationGuts.isAttachedToWindow()) {
                    notificationGuts.setAlpha(1.0f);
                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(notificationGuts, i, i2, 0.0f, (float) Math.hypot((double) Math.max(notificationGuts.getWidth() - i, i), (double) Math.max(notificationGuts.getHeight() - i2, i2)));
                    createCircularReveal.setDuration(360);
                    createCircularReveal.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                    createCircularReveal.addListener(new NotificationGuts.AnimateOpenListener(statusBar$$ExternalSyntheticLambda18));
                    createCircularReveal.start();
                } else {
                    Log.w("NotificationGuts", "Failed to animate guts open");
                }
                notificationGuts.setExposed(true, z);
                NotifGutsViewListener notifGutsViewListener = NotificationGutsManager.this.mGutsListener;
                if (notifGutsViewListener != null) {
                    ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                    Objects.requireNonNull(expandableNotificationRow2);
                    notifGutsViewListener.onGutsOpen(expandableNotificationRow2.mEntry, notificationGuts2);
                }
                expandableNotificationRow.closeRemoteInput();
                NotificationGutsManager.this.mListContainer.onHeightChanged(expandableNotificationRow, true);
                NotificationGutsManager.this.mGutsMenuItem = menuItem2;
            }
        };
        this.mOpenRunnable = r2;
        notificationGuts.post(r2);
        return true;
    }

    public final boolean shouldExtendLifetime(NotificationEntry notificationEntry) {
        NotificationGuts notificationGuts;
        boolean z;
        NotificationGuts notificationGuts2 = this.mNotificationGutsExposed;
        if (notificationGuts2 != null) {
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            NotificationGuts notificationGuts3 = null;
            if (expandableNotificationRow != null) {
                notificationGuts = expandableNotificationRow.mGuts;
            } else {
                notificationGuts = null;
            }
            if (notificationGuts != null) {
                if (expandableNotificationRow != null) {
                    notificationGuts3 = expandableNotificationRow.mGuts;
                }
                if (notificationGuts2 == notificationGuts3) {
                    Objects.requireNonNull(notificationGuts2);
                    NotificationGuts.GutsContent gutsContent = notificationGuts2.mGutsContent;
                    if (gutsContent == null || !gutsContent.isLeavebehind()) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (!z) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void startAppNotificationSettingsActivity(String str, int i, NotificationChannel notificationChannel, ExpandableNotificationRow expandableNotificationRow) {
        Intent intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
        intent.putExtra("android.provider.extra.APP_PACKAGE", str);
        intent.putExtra("app_uid", i);
        if (notificationChannel != null) {
            Bundle bundle = new Bundle();
            intent.putExtra(":settings:fragment_args_key", notificationChannel.getId());
            bundle.putString(":settings:fragment_args_key", notificationChannel.getId());
            intent.putExtra(":settings:show_fragment_args", bundle);
        }
        StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) this.mNotificationActivityStarter;
        Objects.requireNonNull(statusBarNotificationActivityStarter);
        StatusBar statusBar = statusBarNotificationActivityStarter.mStatusBar;
        Objects.requireNonNull(statusBar);
        statusBarNotificationActivityStarter.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction(expandableNotificationRow, statusBar.shouldAnimateLaunch(true, false), intent, i) {
            public final /* synthetic */ boolean val$animate;
            public final /* synthetic */ int val$appUid;
            public final /* synthetic */ Intent val$intent;
            public final /* synthetic */ ExpandableNotificationRow val$row;

            {
                this.val$row = r2;
                this.val$animate = r3;
                this.val$intent = r4;
                this.val$appUid = r5;
            }

            public final boolean onDismiss() {
                AsyncTask.execute(new StatusBarNotificationActivityStarter$4$$ExternalSyntheticLambda0(this, this.val$row, this.val$animate, this.val$intent, this.val$appUid));
                return true;
            }

            public final boolean willRunAnimationOnKeyguard() {
                return this.val$animate;
            }
        }, (Runnable) null, false);
    }

    @VisibleForTesting
    public boolean bindGuts(ExpandableNotificationRow expandableNotificationRow, NotificationMenuRowPlugin.MenuItem menuItem) {
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        if (expandableNotificationRow.mGuts != null && (menuItem.getGutsView() instanceof NotificationGuts.GutsContent)) {
            NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
            NotificationGuts.GutsContent gutsContent = (NotificationGuts.GutsContent) menuItem.getGutsView();
            Objects.requireNonNull(notificationGuts);
            gutsContent.setGutsParent(notificationGuts);
            gutsContent.setAccessibilityDelegate(notificationGuts.mGutsContentAccessibilityDelegate);
            notificationGuts.mGutsContent = gutsContent;
            notificationGuts.removeAllViews();
            notificationGuts.addView(notificationGuts.mGutsContent.getContentView());
        }
        Objects.requireNonNull(notificationEntry);
        expandableNotificationRow.setTag(notificationEntry.mSbn.getPackageName());
        NotificationGuts notificationGuts2 = expandableNotificationRow.mGuts;
        NotificationGutsManager$$ExternalSyntheticLambda1 notificationGutsManager$$ExternalSyntheticLambda1 = new NotificationGutsManager$$ExternalSyntheticLambda1(this, expandableNotificationRow, notificationEntry);
        Objects.requireNonNull(notificationGuts2);
        notificationGuts2.mClosedListener = notificationGutsManager$$ExternalSyntheticLambda1;
        View gutsView = menuItem.getGutsView();
        try {
            if (gutsView instanceof NotificationSnooze) {
                initializeSnoozeView(expandableNotificationRow, (NotificationSnooze) gutsView);
                return true;
            } else if (gutsView instanceof NotificationInfo) {
                initializeNotificationInfo(expandableNotificationRow, (NotificationInfo) gutsView);
                return true;
            } else if (gutsView instanceof NotificationConversationInfo) {
                initializeConversationNotificationInfo(expandableNotificationRow, (NotificationConversationInfo) gutsView);
                return true;
            } else if (gutsView instanceof PartialConversationInfo) {
                initializePartialConversationNotificationInfo(expandableNotificationRow, (PartialConversationInfo) gutsView);
                return true;
            } else if (!(gutsView instanceof FeedbackInfo)) {
                return true;
            } else {
                FeedbackInfo feedbackInfo = (FeedbackInfo) gutsView;
                AssistantFeedbackController assistantFeedbackController = this.mAssistantFeedbackController;
                NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
                Objects.requireNonNull(assistantFeedbackController);
                if (assistantFeedbackController.mIcons.get(assistantFeedbackController.getFeedbackStatus(notificationEntry2)) == null) {
                    return true;
                }
                NotificationEntry notificationEntry3 = expandableNotificationRow.mEntry;
                Objects.requireNonNull(notificationEntry3);
                StatusBarNotification statusBarNotification = notificationEntry3.mSbn;
                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                feedbackInfo.bindGuts(StatusBar.getPackageManagerForUser(this.mContext, statusBarNotification.getUser().getIdentifier()), statusBarNotification, expandableNotificationRow.mEntry, expandableNotificationRow2, this.mAssistantFeedbackController);
                return true;
            }
        } catch (Exception e) {
            Log.e("NotificationGutsManager", "error binding guts", e);
            return false;
        }
    }

    @VisibleForTesting
    public void initializePartialConversationNotificationInfo(ExpandableNotificationRow expandableNotificationRow, PartialConversationInfo partialConversationInfo) throws Exception {
        NotificationGutsManager$$ExternalSyntheticLambda4 notificationGutsManager$$ExternalSyntheticLambda4;
        int i;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        String packageName = statusBarNotification.getPackageName();
        UserHandle user = statusBarNotification.getUser();
        PackageManager packageManagerForUser = StatusBar.getPackageManagerForUser(this.mContext, user.getIdentifier());
        PartialConversationInfo$$ExternalSyntheticLambda0 partialConversationInfo$$ExternalSyntheticLambda0 = null;
        if (!user.equals(UserHandle.ALL) || this.mLockscreenUserManager.getCurrentUserId() == 0) {
            notificationGutsManager$$ExternalSyntheticLambda4 = new NotificationGutsManager$$ExternalSyntheticLambda4(this, notificationGuts, statusBarNotification, packageName, expandableNotificationRow);
        } else {
            notificationGutsManager$$ExternalSyntheticLambda4 = null;
        }
        ChannelEditorDialogController channelEditorDialogController = this.mChannelEditorDialogController;
        NotificationChannel channel = expandableNotificationRow.mEntry.getChannel();
        ArraySet<NotificationChannel> uniqueChannels = expandableNotificationRow.getUniqueChannels();
        NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
        boolean isDeviceProvisioned = this.mDeviceProvisionedController.isDeviceProvisioned();
        boolean isNonblockable = expandableNotificationRow.getIsNonblockable();
        Objects.requireNonNull(partialConversationInfo);
        partialConversationInfo.mPackageName = packageName;
        Objects.requireNonNull(notificationEntry2);
        StatusBarNotification statusBarNotification2 = notificationEntry2.mSbn;
        partialConversationInfo.mSbn = statusBarNotification2;
        partialConversationInfo.mPm = packageManagerForUser;
        partialConversationInfo.mAppName = partialConversationInfo.mPackageName;
        partialConversationInfo.mOnSettingsClickListener = notificationGutsManager$$ExternalSyntheticLambda4;
        partialConversationInfo.mNotificationChannel = channel;
        partialConversationInfo.mAppUid = statusBarNotification2.getUid();
        partialConversationInfo.mDelegatePkg = partialConversationInfo.mSbn.getOpPkg();
        partialConversationInfo.mIsDeviceProvisioned = isDeviceProvisioned;
        partialConversationInfo.mIsNonBlockable = isNonblockable;
        partialConversationInfo.mChannelEditorDialogController = channelEditorDialogController;
        partialConversationInfo.mUniqueChannelsInRow = uniqueChannels;
        try {
            ApplicationInfo applicationInfo = partialConversationInfo.mPm.getApplicationInfo(partialConversationInfo.mPackageName, 795136);
            if (applicationInfo != null) {
                partialConversationInfo.mAppName = String.valueOf(partialConversationInfo.mPm.getApplicationLabel(applicationInfo));
                partialConversationInfo.mPkgIcon = partialConversationInfo.mPm.getApplicationIcon(applicationInfo);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            partialConversationInfo.mPkgIcon = partialConversationInfo.mPm.getDefaultActivityIcon();
        }
        ((TextView) partialConversationInfo.findViewById(C1777R.C1779id.name)).setText(partialConversationInfo.mAppName);
        ((ImageView) partialConversationInfo.findViewById(C1777R.C1779id.icon)).setImageDrawable(partialConversationInfo.mPkgIcon);
        TextView textView = (TextView) partialConversationInfo.findViewById(C1777R.C1779id.delegate_name);
        int i2 = 0;
        if (!TextUtils.equals(partialConversationInfo.mPackageName, partialConversationInfo.mDelegatePkg)) {
            textView.setVisibility(0);
        } else {
            textView.setVisibility(8);
        }
        int i3 = partialConversationInfo.mAppUid;
        if (i3 >= 0 && partialConversationInfo.mOnSettingsClickListener != null && partialConversationInfo.mIsDeviceProvisioned) {
            partialConversationInfo$$ExternalSyntheticLambda0 = new PartialConversationInfo$$ExternalSyntheticLambda0(partialConversationInfo, i3);
        }
        View findViewById = partialConversationInfo.findViewById(C1777R.C1779id.info);
        findViewById.setOnClickListener(partialConversationInfo$$ExternalSyntheticLambda0);
        if (findViewById.hasOnClickListeners()) {
            i = 0;
        } else {
            i = 8;
        }
        findViewById.setVisibility(i);
        partialConversationInfo.findViewById(C1777R.C1779id.settings_link).setOnClickListener(partialConversationInfo$$ExternalSyntheticLambda0);
        ((TextView) partialConversationInfo.findViewById(C1777R.C1779id.non_configurable_text)).setText(partialConversationInfo.getResources().getString(C1777R.string.no_shortcut, new Object[]{partialConversationInfo.mAppName}));
        View findViewById2 = partialConversationInfo.findViewById(C1777R.C1779id.turn_off_notifications);
        findViewById2.setOnClickListener(new BubbleStackView$$ExternalSyntheticLambda3(partialConversationInfo, 1));
        if (!findViewById2.hasOnClickListeners() || partialConversationInfo.mIsNonBlockable) {
            i2 = 8;
        }
        findViewById2.setVisibility(i2);
        View findViewById3 = partialConversationInfo.findViewById(C1777R.C1779id.done);
        findViewById3.setOnClickListener(partialConversationInfo.mOnDone);
        findViewById3.setAccessibilityDelegate(partialConversationInfo.mGutsContainer.getAccessibilityDelegate());
    }

    public final void initializeSnoozeView(ExpandableNotificationRow expandableNotificationRow, NotificationSnooze notificationSnooze) {
        Objects.requireNonNull(expandableNotificationRow);
        NotificationGuts notificationGuts = expandableNotificationRow.mGuts;
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        NotificationSwipeHelper swipeActionHelper = this.mListContainer.getSwipeActionHelper();
        Objects.requireNonNull(notificationSnooze);
        notificationSnooze.mSnoozeListener = swipeActionHelper;
        notificationSnooze.mSbn = statusBarNotification;
        NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry2);
        List snoozeCriteria = notificationEntry2.mRanking.getSnoozeCriteria();
        if (snoozeCriteria != null) {
            notificationSnooze.mSnoozeOptions.clear();
            notificationSnooze.mSnoozeOptions = notificationSnooze.getDefaultSnoozeOptions();
            int min = Math.min(1, snoozeCriteria.size());
            for (int i = 0; i < min; i++) {
                SnoozeCriterion snoozeCriterion = (SnoozeCriterion) snoozeCriteria.get(i);
                notificationSnooze.mSnoozeOptions.add(new NotificationSnooze.NotificationSnoozeOption(snoozeCriterion, 0, snoozeCriterion.getExplanation(), snoozeCriterion.getConfirmation(), new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_snooze_assistant_suggestion_1, snoozeCriterion.getExplanation())));
            }
            notificationSnooze.createOptionViews();
        }
        StatusBar$$ExternalSyntheticLambda9 statusBar$$ExternalSyntheticLambda9 = new StatusBar$$ExternalSyntheticLambda9(this, expandableNotificationRow);
        Objects.requireNonNull(notificationGuts);
        notificationGuts.mHeightListener = statusBar$$ExternalSyntheticLambda9;
    }

    public final boolean openGuts(View view, int i, int i2, NotificationMenuRowPlugin.MenuItem menuItem) {
        if ((menuItem.getGutsView() instanceof NotificationGuts.GutsContent) && ((NotificationGuts.GutsContent) menuItem.getGutsView()).needsFalsingProtection()) {
            StatusBarStateController statusBarStateController = this.mStatusBarStateController;
            if (statusBarStateController instanceof StatusBarStateControllerImpl) {
                StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) statusBarStateController;
                Objects.requireNonNull(statusBarStateControllerImpl);
                statusBarStateControllerImpl.mLeaveOpenOnKeyguardHide = true;
            }
            Optional optional = this.mStatusBarOptionalLazy.get();
            if (optional.isPresent()) {
                ((StatusBar) optional.get()).executeRunnableDismissingKeyguard(new NotificationGutsManager$$ExternalSyntheticLambda6(this, view, i, i2, menuItem), false, true, true);
                return true;
            }
        }
        return openGutsInternal(view, i, i2, menuItem);
    }

    public final void setCallback(ScreenshotController$$ExternalSyntheticLambda3 screenshotController$$ExternalSyntheticLambda3) {
        this.mNotificationLifetimeFinishedCallback = screenshotController$$ExternalSyntheticLambda3;
    }

    public final void setGutsListener(GutsCoordinator$mGutsListener$1 gutsCoordinator$mGutsListener$1) {
        this.mGutsListener = gutsCoordinator$mGutsListener$1;
    }
}
