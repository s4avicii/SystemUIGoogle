package com.android.systemui.wmshell;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.IStatusBarService;
import com.android.p012wm.shell.bubbles.BubbleEntry;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationChannelHelper;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.ZenModeController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Executor;

public final class BubblesManager implements Dumpable {
    public final IStatusBarService mBarService;
    public final Bubbles mBubbles;
    public final ArrayList mCallbacks;
    public final CommonNotifCollection mCommonNotifCollection;
    public final Context mContext;
    public final NotificationLockscreenUserManager mNotifUserManager;
    public final NotificationEntryManager mNotificationEntryManager;
    public final NotificationGroupManagerLegacy mNotificationGroupManager;
    public final NotificationInterruptStateProvider mNotificationInterruptStateProvider;
    public final INotificationManager mNotificationManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final ShadeController mShadeController;
    public final Executor mSysuiMainExecutor;
    public final C17525 mSysuiProxy;
    public final NotificationVisibilityProvider mVisibilityProvider;

    public interface NotifCallback {
        void invalidateNotifications(String str);

        void maybeCancelSummary(NotificationEntry notificationEntry);

        void removeNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats, int i);
    }

    @VisibleForTesting
    public BubblesManager(Context context, final Bubbles bubbles, NotificationShadeWindowController notificationShadeWindowController, StatusBarStateController statusBarStateController, ShadeController shadeController, ConfigurationController configurationController, IStatusBarService iStatusBarService, INotificationManager iNotificationManager, NotificationVisibilityProvider notificationVisibilityProvider, NotificationInterruptStateProvider notificationInterruptStateProvider, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationGroupManagerLegacy notificationGroupManagerLegacy, NotificationEntryManager notificationEntryManager, CommonNotifCollection commonNotifCollection, NotifPipeline notifPipeline, SysUiState sysUiState, NotifPipelineFlags notifPipelineFlags, DumpManager dumpManager, Executor executor) {
        IStatusBarService iStatusBarService2;
        NotificationLockscreenUserManager notificationLockscreenUserManager2 = notificationLockscreenUserManager;
        NotificationGroupManagerLegacy notificationGroupManagerLegacy2 = notificationGroupManagerLegacy;
        NotificationEntryManager notificationEntryManager2 = notificationEntryManager;
        final Executor executor2 = executor;
        ArrayList arrayList = new ArrayList();
        this.mCallbacks = arrayList;
        this.mContext = context;
        this.mBubbles = bubbles;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mShadeController = shadeController;
        this.mNotificationManager = iNotificationManager;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mNotificationInterruptStateProvider = notificationInterruptStateProvider;
        this.mNotifUserManager = notificationLockscreenUserManager2;
        this.mNotificationGroupManager = notificationGroupManagerLegacy2;
        this.mNotificationEntryManager = notificationEntryManager2;
        this.mCommonNotifCollection = commonNotifCollection;
        this.mSysuiMainExecutor = executor2;
        if (iStatusBarService == null) {
            iStatusBarService2 = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        } else {
            iStatusBarService2 = iStatusBarService;
        }
        this.mBarService = iStatusBarService2;
        if (notifPipelineFlags.isNewPipelineEnabled()) {
            notifPipeline.addCollectionListener(new NotifCollectionListener() {
                public final void onEntryAdded(NotificationEntry notificationEntry) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    if (bubblesManager.mNotificationInterruptStateProvider.shouldBubbleUp(notificationEntry) && notificationEntry.isBubble()) {
                        bubblesManager.mBubbles.onEntryAdded(BubblesManager.notifToBubbleEntry(notificationEntry));
                    }
                }

                public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    bubblesManager.mBubbles.onEntryRemoved(BubblesManager.notifToBubbleEntry(notificationEntry));
                }

                public final void onEntryUpdated(NotificationEntry notificationEntry) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    bubblesManager.mBubbles.onEntryUpdated(BubblesManager.notifToBubbleEntry(notificationEntry), bubblesManager.mNotificationInterruptStateProvider.shouldBubbleUp(notificationEntry));
                }

                public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    bubblesManager.mBubbles.onNotificationChannelModified(str, userHandle, notificationChannel, i);
                }

                public final void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                    BubblesManager.this.onRankingUpdate(rankingMap);
                }
            });
        } else {
            notificationEntryManager2.addNotificationEntryListener(new NotificationEntryListener() {
                public final void onEntryRemoved(NotificationEntry notificationEntry, boolean z) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    bubblesManager.mBubbles.onEntryRemoved(BubblesManager.notifToBubbleEntry(notificationEntry));
                }

                public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    bubblesManager.mBubbles.onNotificationChannelModified(str, userHandle, notificationChannel, i);
                }

                public final void onNotificationRankingUpdated(NotificationListenerService.RankingMap rankingMap) {
                    BubblesManager.this.onRankingUpdate(rankingMap);
                }

                public final void onPendingEntryAdded(NotificationEntry notificationEntry) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    if (bubblesManager.mNotificationInterruptStateProvider.shouldBubbleUp(notificationEntry) && notificationEntry.isBubble()) {
                        bubblesManager.mBubbles.onEntryAdded(BubblesManager.notifToBubbleEntry(notificationEntry));
                    }
                }

                public final void onPreEntryUpdated(NotificationEntry notificationEntry) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    Objects.requireNonNull(bubblesManager);
                    bubblesManager.mBubbles.onEntryUpdated(BubblesManager.notifToBubbleEntry(notificationEntry), bubblesManager.mNotificationInterruptStateProvider.shouldBubbleUp(notificationEntry));
                }
            });
            notificationEntryManager2.mRemoveInterceptors.add(new BubblesManager$$ExternalSyntheticLambda0(this));
            C17547 r4 = new NotificationGroupManagerLegacy.OnGroupChangeListener() {
                public final void onGroupSuppressionChanged(NotificationGroupManagerLegacy.NotificationGroup notificationGroup, boolean z) {
                    String str;
                    NotificationEntry notificationEntry = notificationGroup.summary;
                    if (notificationEntry != null) {
                        str = notificationEntry.mSbn.getGroupKey();
                    } else {
                        str = null;
                    }
                    if (!z && str != null) {
                        BubblesManager.this.mBubbles.removeSuppressedSummaryIfNecessary(str, (BubblesManager$8$$ExternalSyntheticLambda0) null, (Executor) null);
                    }
                }
            };
            Objects.requireNonNull(notificationGroupManagerLegacy);
            NotificationGroupManagerLegacy.GroupEventDispatcher groupEventDispatcher = notificationGroupManagerLegacy2.mEventDispatcher;
            Objects.requireNonNull(groupEventDispatcher);
            groupEventDispatcher.mGroupChangeListeners.add(r4);
            arrayList.add(new NotifCallback() {
                public final void invalidateNotifications(String str) {
                    BubblesManager.this.mNotificationEntryManager.updateNotifications(str);
                }

                public final void maybeCancelSummary(NotificationEntry notificationEntry) {
                    String groupKey = notificationEntry.mSbn.getGroupKey();
                    BubblesManager bubblesManager = BubblesManager.this;
                    bubblesManager.mBubbles.removeSuppressedSummaryIfNecessary(groupKey, new BubblesManager$8$$ExternalSyntheticLambda0(this), bubblesManager.mSysuiMainExecutor);
                    NotificationEntry logicalGroupSummary = BubblesManager.this.mNotificationGroupManager.getLogicalGroupSummary(notificationEntry);
                    if (logicalGroupSummary != null) {
                        ArrayList<NotificationEntry> logicalChildren = BubblesManager.this.mNotificationGroupManager.getLogicalChildren(logicalGroupSummary.mSbn);
                        if (logicalGroupSummary.mKey.equals(notificationEntry.mKey)) {
                            return;
                        }
                        if (logicalChildren == null || logicalChildren.isEmpty()) {
                            BubblesManager bubblesManager2 = BubblesManager.this;
                            bubblesManager2.mNotificationEntryManager.performRemoveNotification(logicalGroupSummary.mSbn, bubblesManager2.getDismissedByUserStats(logicalGroupSummary, false), 0);
                        }
                    }
                }

                public final void removeNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats, int i) {
                    NotificationEntryManager notificationEntryManager = BubblesManager.this.mNotificationEntryManager;
                    Objects.requireNonNull(notificationEntry);
                    notificationEntryManager.performRemoveNotification(notificationEntry.mSbn, dismissedByUserStats, i);
                }
            });
        }
        dumpManager.registerDumpable("Bubbles", this);
        StatusBarStateController statusBarStateController2 = statusBarStateController;
        statusBarStateController.addCallback(new StatusBarStateController.StateListener() {
            public final void onStateChanged(int i) {
                boolean z;
                if (i == 0) {
                    z = true;
                } else {
                    z = false;
                }
                Bubbles.this.onStatusBarStateChanged(z);
            }
        });
        ConfigurationController configurationController2 = configurationController;
        configurationController.addCallback(new ConfigurationController.ConfigurationListener() {
            public final void onConfigChanged(Configuration configuration) {
                BubblesManager.this.mBubbles.onConfigChanged(configuration);
            }

            public final void onThemeChanged() {
                BubblesManager.this.mBubbles.updateForThemeChanges();
            }

            public final void onUiModeChanged() {
                BubblesManager.this.mBubbles.updateForThemeChanges();
            }
        });
        zenModeController.addCallback(new ZenModeController.Callback() {
            public final void onConfigChanged() {
                BubblesManager.this.mBubbles.onZenStateChanged();
            }

            public final void onZenChanged(int i) {
                BubblesManager.this.mBubbles.onZenStateChanged();
            }
        });
        notificationLockscreenUserManager2.addUserChangedListener(new NotificationLockscreenUserManager.UserChangedListener() {
            public final void onCurrentProfilesChanged(SparseArray<UserInfo> sparseArray) {
                BubblesManager.this.mBubbles.onCurrentProfilesChanged(sparseArray);
            }

            public final void onUserChanged(int i) {
                BubblesManager.this.mBubbles.onUserChanged(i);
            }
        });
        final SysUiState sysUiState2 = sysUiState;
        C17525 r2 = new Bubbles.SysuiProxy() {
        };
        this.mSysuiProxy = r2;
        bubbles.setSysuiProxy(r2);
    }

    public final boolean handleDismissalInterception(NotificationEntry notificationEntry) {
        if (notificationEntry == null) {
            return false;
        }
        ArrayList attachedNotifChildren = notificationEntry.getAttachedNotifChildren();
        ArrayList arrayList = null;
        if (attachedNotifChildren != null) {
            arrayList = new ArrayList();
            for (int i = 0; i < attachedNotifChildren.size(); i++) {
                arrayList.add(notifToBubbleEntry((NotificationEntry) attachedNotifChildren.get(i)));
            }
        }
        return this.mBubbles.handleDismissalInterception(notifToBubbleEntry(notificationEntry), arrayList, new BubblesManager$$ExternalSyntheticLambda1(this, attachedNotifChildren, notificationEntry), this.mSysuiMainExecutor);
    }

    public static BubbleEntry notifToBubbleEntry(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        return new BubbleEntry(notificationEntry.mSbn, notificationEntry.mRanking, notificationEntry.isDismissable(), notificationEntry.shouldSuppressVisualEffect(64), notificationEntry.shouldSuppressVisualEffect(256), notificationEntry.shouldSuppressVisualEffect(16));
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        this.mBubbles.dump(fileDescriptor, printWriter, strArr);
    }

    public final DismissedByUserStats getDismissedByUserStats(NotificationEntry notificationEntry, boolean z) {
        return new DismissedByUserStats(3, this.mVisibilityProvider.obtain(notificationEntry, z));
    }

    public static boolean areBubblesEnabled(Context context, UserHandle userHandle) {
        if (userHandle.getIdentifier() < 0) {
            if (Settings.Secure.getInt(context.getContentResolver(), "notification_bubbles", 0) == 1) {
                return true;
            }
            return false;
        } else if (Settings.Secure.getIntForUser(context.getContentResolver(), "notification_bubbles", 0, userHandle.getIdentifier()) == 1) {
            return true;
        } else {
            return false;
        }
    }

    public final void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        BubbleEntry bubbleEntry;
        boolean z;
        String[] orderedKeys = rankingMap.getOrderedKeys();
        HashMap hashMap = new HashMap();
        for (String str : orderedKeys) {
            NotificationEntry entry = this.mCommonNotifCollection.getEntry(str);
            if (entry != null) {
                bubbleEntry = notifToBubbleEntry(entry);
            } else {
                bubbleEntry = null;
            }
            if (entry != null) {
                z = this.mNotificationInterruptStateProvider.shouldBubbleUp(entry);
            } else {
                z = false;
            }
            hashMap.put(str, new Pair(bubbleEntry, Boolean.valueOf(z)));
        }
        this.mBubbles.onRankingUpdated(rankingMap, hashMap);
    }

    public final void onUserChangedBubble(NotificationEntry notificationEntry, boolean z) {
        NotificationChannel channel = notificationEntry.getChannel();
        String packageName = notificationEntry.mSbn.getPackageName();
        int uid = notificationEntry.mSbn.getUid();
        if (channel != null && packageName != null) {
            try {
                this.mBarService.onNotificationBubbleChanged(notificationEntry.mKey, z, 3);
            } catch (RemoteException unused) {
            }
            NotificationChannel createConversationChannelIfNeeded = NotificationChannelHelper.createConversationChannelIfNeeded(this.mContext, this.mNotificationManager, notificationEntry, channel);
            createConversationChannelIfNeeded.setAllowBubbles(z);
            try {
                int bubblePreferenceForPackage = this.mNotificationManager.getBubblePreferenceForPackage(packageName, uid);
                if (z && bubblePreferenceForPackage == 0) {
                    this.mNotificationManager.setBubblesAllowed(packageName, uid, 2);
                }
                this.mNotificationManager.updateNotificationChannelForPackage(packageName, uid, createConversationChannelIfNeeded);
            } catch (RemoteException e) {
                Log.e("Bubbles", e.getMessage());
            }
            if (z) {
                this.mShadeController.collapsePanel(true);
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.updateBubbleButton();
                }
            }
        }
    }
}
