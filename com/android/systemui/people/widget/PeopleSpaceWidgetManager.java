package com.android.systemui.people.widget;

import android.app.INotificationManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Person;
import android.app.backup.BackupManager;
import android.app.people.ConversationChannel;
import android.app.people.IPeopleManager;
import android.app.people.PeopleManager;
import android.app.people.PeopleSpaceTile;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import androidx.recyclerview.R$dimen;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.ArrayUtils;
import com.android.internal.widget.MessagingMessage;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.common.ExecutorUtils$$ExternalSyntheticLambda1;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.p006qs.tiles.ScreenRecordTile$$ExternalSyntheticLambda1;
import com.android.systemui.people.NotificationHelper;
import com.android.systemui.people.NotificationHelper$$ExternalSyntheticLambda1;
import com.android.systemui.people.PeopleSpaceUtils;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda0;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda4;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda5;
import com.android.systemui.people.PeopleSpaceUtils$$ExternalSyntheticLambda8;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.util.condition.Monitor$$ExternalSyntheticLambda4;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda11;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda7;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PeopleSpaceWidgetManager {
    @GuardedBy({"mLock"})
    public static HashMap mListeners = new HashMap();
    @GuardedBy({"mLock"})
    public static HashMap mTiles = new HashMap();
    public AppWidgetManager mAppWidgetManager;
    public BackupManager mBackupManager;
    public final C09602 mBaseBroadcastReceiver = new BroadcastReceiver() {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final void onReceive(Context context, Intent intent) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new ExecutorUtils$$ExternalSyntheticLambda0(this, intent, 2));
        }
    };
    public Executor mBgExecutor;
    public BroadcastDispatcher mBroadcastDispatcher;
    public Optional<Bubbles> mBubblesOptional;
    public final Context mContext;
    public INotificationManager mINotificationManager;
    public IPeopleManager mIPeopleManager;
    public LauncherApps mLauncherApps;
    public final C09591 mListener = new NotificationListener.NotificationHandler() {
        public final void onNotificationRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
        }

        public final void onNotificationsInitialized() {
        }

        public final void onNotificationPosted(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap) {
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            PeopleSpaceUtils.NotificationAction notificationAction = PeopleSpaceUtils.NotificationAction.POSTED;
            Objects.requireNonNull(peopleSpaceWidgetManager);
            peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(peopleSpaceWidgetManager, statusBarNotification, notificationAction, 0));
        }

        public final void onNotificationRemoved(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap, int i) {
            PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
            PeopleSpaceUtils.NotificationAction notificationAction = PeopleSpaceUtils.NotificationAction.REMOVED;
            Objects.requireNonNull(peopleSpaceWidgetManager);
            peopleSpaceWidgetManager.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda0(peopleSpaceWidgetManager, statusBarNotification, notificationAction, 0));
        }

        public final void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            if (notificationChannel.isConversation()) {
                PeopleSpaceWidgetManager peopleSpaceWidgetManager = PeopleSpaceWidgetManager.this;
                peopleSpaceWidgetManager.mBgExecutor.execute(new ScreenRecordTile$$ExternalSyntheticLambda1(peopleSpaceWidgetManager, peopleSpaceWidgetManager.mAppWidgetManager.getAppWidgetIds(new ComponentName(PeopleSpaceWidgetManager.this.mContext, PeopleSpaceWidgetProvider.class)), 1));
            }
        }
    };
    public final Object mLock = new Object();
    public PeopleSpaceWidgetManager mManager;
    public CommonNotifCollection mNotifCollection;
    @GuardedBy({"mLock"})
    public HashMap mNotificationKeyToWidgetIdsMatchedByUri = new HashMap();
    public NotificationManager mNotificationManager;
    public PackageManager mPackageManager;
    public PeopleManager mPeopleManager;
    public boolean mRegisteredReceivers;
    public SharedPreferences mSharedPrefs;
    public UiEventLoggerImpl mUiEventLogger = new UiEventLoggerImpl();
    public UserManager mUserManager;

    public class TileConversationListener implements PeopleManager.ConversationListener {
        public TileConversationListener() {
        }

        public final void onConversationUpdate(ConversationChannel conversationChannel) {
            PeopleSpaceWidgetManager.this.mBgExecutor.execute(new ExecutorUtils$$ExternalSyntheticLambda1(this, conversationChannel, 2));
        }
    }

    public PeopleSpaceWidgetManager(Context context, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional, UserManager userManager, NotificationManager notificationManager, BroadcastDispatcher broadcastDispatcher, Executor executor) {
        this.mContext = context;
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mLauncherApps = launcherApps;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mPeopleManager = (PeopleManager) context.getSystemService(PeopleManager.class);
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mINotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        this.mBubblesOptional = optional;
        this.mUserManager = userManager;
        this.mBackupManager = new BackupManager(context);
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mBgExecutor = executor;
    }

    public final void addNewWidget(int i, PeopleTileKey peopleTileKey) {
        PeopleTileKey keyFromStorageByWidgetId;
        try {
            PeopleSpaceTile tileFromPersistentStorage = getTileFromPersistentStorage(peopleTileKey, i, false);
            if (tileFromPersistentStorage != null) {
                PeopleSpaceTile augmentTileFromNotificationEntryManager = augmentTileFromNotificationEntryManager(tileFromPersistentStorage, Optional.of(Integer.valueOf(i)));
                synchronized (this.mLock) {
                    keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
                }
                if (PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
                    deleteWidgets(new int[]{i});
                } else {
                    this.mUiEventLogger.log(PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_ADDED);
                }
                synchronized (this.mLock) {
                    PeopleSpaceUtils.setSharedPreferencesStorageForTile(this.mContext, peopleTileKey, i, augmentTileFromNotificationEntryManager.getContactUri(), this.mBackupManager);
                }
                registerConversationListenerIfNeeded(peopleTileKey);
                try {
                    this.mLauncherApps.cacheShortcuts(augmentTileFromNotificationEntryManager.getPackageName(), Collections.singletonList(augmentTileFromNotificationEntryManager.getId()), augmentTileFromNotificationEntryManager.getUserHandle(), 2);
                } catch (Exception e) {
                    Log.w("PeopleSpaceWidgetMgr", "Exception caching shortcut:" + e);
                }
                this.mBgExecutor.execute(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda1(this, i, augmentTileFromNotificationEntryManager));
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("PeopleSpaceWidgetMgr", "Cannot add widget since app was uninstalled");
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x005d, code lost:
        r8 = r11.mLock;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x005f, code lost:
        monitor-enter(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        com.android.systemui.people.PeopleSpaceUtils.removeSharedPreferencesStorageForTile(r11.mContext, r6, r3, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0065, code lost:
        monitor-exit(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x006e, code lost:
        if (r5.contains(java.lang.String.valueOf(r3)) == false) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0075, code lost:
        if (r5.size() != 1) goto L_0x00c1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0077, code lost:
        r3 = mListeners;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0079, code lost:
        monitor-enter(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
        r4 = (com.android.systemui.people.widget.PeopleSpaceWidgetManager.TileConversationListener) mListeners.get(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0082, code lost:
        if (r4 != null) goto L_0x0086;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0084, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0086, code lost:
        mListeners.remove(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x008b, code lost:
        monitor-exit(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x008c, code lost:
        r11.mPeopleManager.unregisterConversationListener(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r11.mLauncherApps.uncacheShortcuts(r6.mPackageName, java.util.Collections.singletonList(r6.mShortcutId), android.os.UserHandle.of(r6.mUserId), 2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x00a6, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00a7, code lost:
        android.util.Log.d("PeopleSpaceWidgetMgr", "Exception uncaching shortcut:" + r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c1, code lost:
        continue;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void deleteWidgets(int[] r12) {
        /*
            r11 = this;
            int r0 = r12.length
            r1 = 0
            r2 = r1
        L_0x0003:
            if (r2 >= r0) goto L_0x00cb
            r3 = r12[r2]
            com.android.internal.logging.UiEventLoggerImpl r4 = r11.mUiEventLogger
            com.android.systemui.people.PeopleSpaceUtils$PeopleSpaceWidgetEvent r5 = com.android.systemui.people.PeopleSpaceUtils.PeopleSpaceWidgetEvent.PEOPLE_SPACE_WIDGET_DELETED
            r4.log(r5)
            java.lang.Object r4 = r11.mLock
            monitor-enter(r4)
            android.content.Context r5 = r11.mContext     // Catch:{ all -> 0x00c8 }
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x00c8 }
            android.content.SharedPreferences r5 = r5.getSharedPreferences(r6, r1)     // Catch:{ all -> 0x00c8 }
            com.android.systemui.people.widget.PeopleTileKey r6 = new com.android.systemui.people.widget.PeopleTileKey     // Catch:{ all -> 0x00c8 }
            java.lang.String r7 = "shortcut_id"
            r8 = 0
            java.lang.String r7 = r5.getString(r7, r8)     // Catch:{ all -> 0x00c8 }
            java.lang.String r9 = "user_id"
            r10 = -1
            int r9 = r5.getInt(r9, r10)     // Catch:{ all -> 0x00c8 }
            java.lang.String r10 = "package_name"
            java.lang.String r5 = r5.getString(r10, r8)     // Catch:{ all -> 0x00c8 }
            r6.<init>(r7, r9, r5)     // Catch:{ all -> 0x00c8 }
            boolean r5 = com.android.systemui.people.widget.PeopleTileKey.isValid(r6)     // Catch:{ all -> 0x00c8 }
            if (r5 != 0) goto L_0x003e
            monitor-exit(r4)     // Catch:{ all -> 0x00c8 }
            return
        L_0x003e:
            java.util.HashSet r5 = new java.util.HashSet     // Catch:{ all -> 0x00c8 }
            android.content.SharedPreferences r7 = r11.mSharedPrefs     // Catch:{ all -> 0x00c8 }
            java.lang.String r9 = r6.toString()     // Catch:{ all -> 0x00c8 }
            java.util.HashSet r10 = new java.util.HashSet     // Catch:{ all -> 0x00c8 }
            r10.<init>()     // Catch:{ all -> 0x00c8 }
            java.util.Set r7 = r7.getStringSet(r9, r10)     // Catch:{ all -> 0x00c8 }
            r5.<init>(r7)     // Catch:{ all -> 0x00c8 }
            android.content.SharedPreferences r7 = r11.mSharedPrefs     // Catch:{ all -> 0x00c8 }
            java.lang.String r9 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x00c8 }
            java.lang.String r7 = r7.getString(r9, r8)     // Catch:{ all -> 0x00c8 }
            monitor-exit(r4)     // Catch:{ all -> 0x00c8 }
            java.lang.Object r8 = r11.mLock
            monitor-enter(r8)
            android.content.Context r4 = r11.mContext     // Catch:{ all -> 0x00c5 }
            com.android.systemui.people.PeopleSpaceUtils.removeSharedPreferencesStorageForTile(r4, r6, r3, r7)     // Catch:{ all -> 0x00c5 }
            monitor-exit(r8)     // Catch:{ all -> 0x00c5 }
            java.lang.String r3 = java.lang.String.valueOf(r3)
            boolean r3 = r5.contains(r3)
            if (r3 == 0) goto L_0x00c1
            int r3 = r5.size()
            r4 = 1
            if (r3 != r4) goto L_0x00c1
            java.util.HashMap r3 = mListeners
            monitor-enter(r3)
            java.util.HashMap r4 = mListeners     // Catch:{ all -> 0x00be }
            java.lang.Object r4 = r4.get(r6)     // Catch:{ all -> 0x00be }
            com.android.systemui.people.widget.PeopleSpaceWidgetManager$TileConversationListener r4 = (com.android.systemui.people.widget.PeopleSpaceWidgetManager.TileConversationListener) r4     // Catch:{ all -> 0x00be }
            if (r4 != 0) goto L_0x0086
            monitor-exit(r3)     // Catch:{ all -> 0x00be }
            goto L_0x0091
        L_0x0086:
            java.util.HashMap r5 = mListeners     // Catch:{ all -> 0x00be }
            r5.remove(r6)     // Catch:{ all -> 0x00be }
            monitor-exit(r3)     // Catch:{ all -> 0x00be }
            android.app.people.PeopleManager r3 = r11.mPeopleManager
            r3.unregisterConversationListener(r4)
        L_0x0091:
            android.content.pm.LauncherApps r3 = r11.mLauncherApps     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r4 = r6.mPackageName     // Catch:{ Exception -> 0x00a6 }
            java.lang.String r5 = r6.mShortcutId     // Catch:{ Exception -> 0x00a6 }
            java.util.List r5 = java.util.Collections.singletonList(r5)     // Catch:{ Exception -> 0x00a6 }
            int r6 = r6.mUserId     // Catch:{ Exception -> 0x00a6 }
            android.os.UserHandle r6 = android.os.UserHandle.of(r6)     // Catch:{ Exception -> 0x00a6 }
            r7 = 2
            r3.uncacheShortcuts(r4, r5, r6, r7)     // Catch:{ Exception -> 0x00a6 }
            goto L_0x00c1
        L_0x00a6:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Exception uncaching shortcut:"
            r4.append(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            java.lang.String r4 = "PeopleSpaceWidgetMgr"
            android.util.Log.d(r4, r3)
            goto L_0x00c1
        L_0x00be:
            r11 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x00be }
            throw r11
        L_0x00c1:
            int r2 = r2 + 1
            goto L_0x0003
        L_0x00c5:
            r11 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00c5 }
            throw r11
        L_0x00c8:
            r11 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x00c8 }
            throw r11
        L_0x00cb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleSpaceWidgetManager.deleteWidgets(int[]):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x003d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.widget.RemoteViews getPreview(java.lang.String r4, android.os.UserHandle r5, java.lang.String r6, android.os.Bundle r7) {
        /*
            r3 = this;
            r0 = 0
            android.app.people.IPeopleManager r1 = r3.mIPeopleManager     // Catch:{ Exception -> 0x0051 }
            int r5 = r5.getIdentifier()     // Catch:{ Exception -> 0x0051 }
            android.app.people.ConversationChannel r4 = r1.getConversation(r6, r5, r4)     // Catch:{ Exception -> 0x0051 }
            android.content.pm.LauncherApps r5 = r3.mLauncherApps     // Catch:{ Exception -> 0x0051 }
            com.android.systemui.people.widget.PeopleTileKey r6 = com.android.systemui.people.PeopleSpaceUtils.EMPTY_KEY     // Catch:{ Exception -> 0x0051 }
            java.lang.String r6 = "PeopleSpaceUtils"
            r1 = 0
            if (r4 != 0) goto L_0x001a
            java.lang.String r4 = "ConversationChannel is null"
            android.util.Log.i(r6, r4)     // Catch:{ Exception -> 0x0051 }
            goto L_0x0039
        L_0x001a:
            android.app.people.PeopleSpaceTile$Builder r2 = new android.app.people.PeopleSpaceTile$Builder     // Catch:{ Exception -> 0x0051 }
            r2.<init>(r4, r5)     // Catch:{ Exception -> 0x0051 }
            android.app.people.PeopleSpaceTile r4 = r2.build()     // Catch:{ Exception -> 0x0051 }
            if (r4 == 0) goto L_0x0031
            java.lang.CharSequence r5 = r4.getUserName()     // Catch:{ Exception -> 0x0051 }
            boolean r5 = android.text.TextUtils.isEmpty(r5)     // Catch:{ Exception -> 0x0051 }
            if (r5 != 0) goto L_0x0031
            r5 = 1
            goto L_0x0032
        L_0x0031:
            r5 = r1
        L_0x0032:
            if (r5 != 0) goto L_0x003a
            java.lang.String r4 = "PeopleSpaceTile is not valid"
            android.util.Log.i(r6, r4)     // Catch:{ Exception -> 0x0051 }
        L_0x0039:
            r4 = r0
        L_0x003a:
            if (r4 != 0) goto L_0x003d
            return r0
        L_0x003d:
            java.util.Optional r5 = java.util.Optional.empty()
            android.app.people.PeopleSpaceTile r4 = r3.augmentTileFromNotificationEntryManager(r4, r5)
            android.content.Context r3 = r3.mContext
            com.android.systemui.people.widget.PeopleTileKey r5 = new com.android.systemui.people.widget.PeopleTileKey
            r5.<init>((android.app.people.PeopleSpaceTile) r4)
            android.widget.RemoteViews r3 = com.android.systemui.people.PeopleTileViewHelper.createRemoteViews(r3, r4, r1, r7, r5)
            return r3
        L_0x0051:
            r3 = move-exception
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Exception getting tiles: "
            r4.append(r5)
            r4.append(r3)
            java.lang.String r3 = r4.toString()
            java.lang.String r4 = "PeopleSpaceWidgetMgr"
            android.util.Log.w(r4, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleSpaceWidgetManager.getPreview(java.lang.String, android.os.UserHandle, java.lang.String, android.os.Bundle):android.widget.RemoteViews");
    }

    public final PeopleSpaceTile augmentTileFromNotificationEntryManager(PeopleSpaceTile peopleSpaceTile, Optional<Integer> optional) {
        String str;
        PeopleTileKey peopleTileKey = new PeopleTileKey(peopleSpaceTile);
        Map<PeopleTileKey, Set<NotificationEntry>> groupedConversationNotifications = getGroupedConversationNotifications();
        if (peopleSpaceTile.getContactUri() != null) {
            str = peopleSpaceTile.getContactUri().toString();
        } else {
            str = null;
        }
        return augmentTileFromNotifications(peopleSpaceTile, peopleTileKey, str, groupedConversationNotifications, optional);
    }

    public final PeopleSpaceTile augmentTileFromNotifications(PeopleSpaceTile peopleSpaceTile, PeopleTileKey peopleTileKey, String str, Map<PeopleTileKey, Set<NotificationEntry>> map, Optional<Integer> optional) {
        boolean z;
        NotificationEntry notificationEntry;
        Notification.MessagingStyle.Message message;
        CharSequence charSequence;
        Uri uri;
        Person senderPerson;
        List<Notification.MessagingStyle.Message> messagingStyleMessages;
        List list;
        boolean z2 = true;
        if (this.mPackageManager.checkPermission("android.permission.READ_CONTACTS", peopleSpaceTile.getPackageName()) == 0) {
            z = true;
        } else {
            z = false;
        }
        List arrayList = new ArrayList();
        if (z) {
            PackageManager packageManager = this.mPackageManager;
            PeopleTileKey peopleTileKey2 = PeopleSpaceUtils.EMPTY_KEY;
            if (TextUtils.isEmpty(str)) {
                list = new ArrayList();
            } else {
                list = (List) map.entrySet().stream().flatMap(PeopleSpaceUtils$$ExternalSyntheticLambda4.INSTANCE).filter(new PeopleSpaceUtils$$ExternalSyntheticLambda5(packageManager, str)).collect(Collectors.toList());
            }
            arrayList = list;
            arrayList.isEmpty();
        }
        Set<NotificationEntry> set = map.get(peopleTileKey);
        if (set == null) {
            set = new HashSet<>();
        }
        if (set.isEmpty() && arrayList.isEmpty()) {
            return PeopleSpaceUtils.removeNotificationFields(peopleSpaceTile);
        }
        set.addAll(arrayList);
        PeopleTileKey peopleTileKey3 = PeopleSpaceUtils.EMPTY_KEY;
        int i = 0;
        for (NotificationEntry notificationEntry2 : set) {
            Objects.requireNonNull(notificationEntry2);
            Notification notification = notificationEntry2.mSbn.getNotification();
            if (!NotificationHelper.isMissedCall(notification) && (messagingStyleMessages = NotificationHelper.getMessagingStyleMessages(notification)) != null) {
                i += messagingStyleMessages.size();
            }
        }
        CharSequence charSequence2 = null;
        if (set.isEmpty()) {
            notificationEntry = null;
        } else {
            notificationEntry = (NotificationEntry) set.stream().filter(NotificationHelper$$ExternalSyntheticLambda1.INSTANCE).sorted(NotificationHelper.notificationEntryComparator).findFirst().orElse((Object) null);
        }
        Context context = this.mContext;
        BackupManager backupManager = this.mBackupManager;
        if (notificationEntry == null || notificationEntry.mSbn.getNotification() == null) {
            return PeopleSpaceUtils.removeNotificationFields(peopleSpaceTile);
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Notification notification2 = statusBarNotification.getNotification();
        PeopleSpaceTile.Builder builder = peopleSpaceTile.toBuilder();
        String contactUri = NotificationHelper.getContactUri(statusBarNotification);
        if (optional.isPresent() && peopleSpaceTile.getContactUri() == null && !TextUtils.isEmpty(contactUri)) {
            Uri parse = Uri.parse(contactUri);
            PeopleSpaceUtils.setSharedPreferencesStorageForTile(context, new PeopleTileKey(peopleSpaceTile), optional.get().intValue(), parse, backupManager);
            builder.setContactUri(parse);
        }
        boolean isMissedCall = NotificationHelper.isMissedCall(notification2);
        List<Notification.MessagingStyle.Message> messagingStyleMessages2 = NotificationHelper.getMessagingStyleMessages(notification2);
        if (!isMissedCall && ArrayUtils.isEmpty(messagingStyleMessages2)) {
            return PeopleSpaceUtils.removeNotificationFields(builder.build());
        }
        if (messagingStyleMessages2 != null) {
            message = messagingStyleMessages2.get(0);
        } else {
            message = null;
        }
        if (message == null || TextUtils.isEmpty(message.getText())) {
            z2 = false;
        }
        if (!isMissedCall || z2) {
            charSequence = message.getText();
        } else {
            charSequence = context.getString(C1777R.string.missed_call);
        }
        if (message == null || !MessagingMessage.hasImage(message)) {
            uri = null;
        } else {
            uri = message.getDataUri();
        }
        if (notification2.extras.getBoolean("android.isGroupConversation", false) && (senderPerson = message.getSenderPerson()) != null) {
            charSequence2 = senderPerson.getName();
        }
        return builder.setLastInteractionTimestamp(statusBarNotification.getPostTime()).setNotificationKey(statusBarNotification.getKey()).setNotificationCategory(notification2.category).setNotificationContent(charSequence).setNotificationSender(charSequence2).setNotificationDataUri(uri).setMessagesCount(i).build();
    }

    public final Map<PeopleTileKey, Set<NotificationEntry>> getGroupedConversationNotifications() {
        return (Map) this.mNotifCollection.getAllNotifs().stream().filter(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda7(this)).collect(Collectors.groupingBy(PeopleSpaceWidgetManager$$ExternalSyntheticLambda6.INSTANCE, Collectors.mapping(Function.identity(), Collectors.toSet())));
    }

    public final PeopleTileKey getKeyFromStorageByWidgetId(int i) {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
        return new PeopleTileKey(sharedPreferences.getString("shortcut_id", ""), sharedPreferences.getInt("user_id", -1), sharedPreferences.getString("package_name", ""));
    }

    public final Set<String> getMatchingUriWidgetIds(StatusBarNotification statusBarNotification, PeopleSpaceUtils.NotificationAction notificationAction) {
        boolean z;
        String contactUri;
        if (notificationAction.equals(PeopleSpaceUtils.NotificationAction.POSTED)) {
            Notification notification = statusBarNotification.getNotification();
            if (notification == null) {
                z = false;
            } else {
                z = NotificationHelper.isMissedCall(notification);
            }
            HashSet hashSet = null;
            if (z && (contactUri = NotificationHelper.getContactUri(statusBarNotification)) != null) {
                HashSet hashSet2 = new HashSet(this.mSharedPrefs.getStringSet(contactUri, new HashSet()));
                if (!hashSet2.isEmpty()) {
                    hashSet = hashSet2;
                }
            }
            if (hashSet != null && !hashSet.isEmpty()) {
                this.mNotificationKeyToWidgetIdsMatchedByUri.put(statusBarNotification.getKey(), hashSet);
                return hashSet;
            }
        } else {
            Set<String> set = (Set) this.mNotificationKeyToWidgetIdsMatchedByUri.remove(statusBarNotification.getKey());
            if (set != null && !set.isEmpty()) {
                return set;
            }
        }
        return new HashSet();
    }

    public final int getNotificationPolicyState() {
        int currentInterruptionFilter;
        NotificationManager.Policy notificationPolicy = this.mNotificationManager.getNotificationPolicy();
        int i = 0;
        if (!NotificationManager.Policy.areAllVisualEffectsSuppressed(notificationPolicy.suppressedVisualEffects) || (currentInterruptionFilter = this.mNotificationManager.getCurrentInterruptionFilter()) == 1) {
            return 1;
        }
        if (currentInterruptionFilter == 2) {
            if (notificationPolicy.allowConversations()) {
                int i2 = notificationPolicy.priorityConversationSenders;
                if (i2 == 1) {
                    return 1;
                }
                if (i2 == 2) {
                    i = 4;
                }
            }
            if (notificationPolicy.allowMessages()) {
                int allowMessagesFrom = notificationPolicy.allowMessagesFrom();
                if (allowMessagesFrom == 1) {
                    return i | 16;
                }
                if (allowMessagesFrom != 2) {
                    return 1;
                }
                return i | 8;
            } else if (i != 0) {
                return i;
            }
        }
        return 2;
    }

    public final List<PeopleSpaceTile> getRecentTiles() throws Exception {
        return PeopleSpaceUtils.getSortedTiles(this.mIPeopleManager, this.mLauncherApps, this.mUserManager, Stream.concat(this.mINotificationManager.getConversations(false).getList().stream().filter(PeopleSpaceUtils$$ExternalSyntheticLambda8.INSTANCE$1).map(WifiPickerTracker$$ExternalSyntheticLambda7.INSTANCE$1), this.mIPeopleManager.getRecentConversations().getList().stream().map(PeopleSpaceWidgetManager$$ExternalSyntheticLambda5.INSTANCE)));
    }

    public final PeopleSpaceTile getTileForExistingWidgetThrowing(int i) throws PackageManager.NameNotFoundException {
        PeopleSpaceTile peopleSpaceTile;
        synchronized (mTiles) {
            peopleSpaceTile = (PeopleSpaceTile) mTiles.get(Integer.valueOf(i));
        }
        if (peopleSpaceTile != null) {
            return peopleSpaceTile;
        }
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(String.valueOf(i), 0);
        return getTileFromPersistentStorage(new PeopleTileKey(sharedPreferences.getString("shortcut_id", ""), sharedPreferences.getInt("user_id", -1), sharedPreferences.getString("package_name", "")), i, true);
    }

    public final void updateAppWidgetOptionsAndView(int i, PeopleSpaceTile peopleSpaceTile) {
        synchronized (mTiles) {
            mTiles.put(Integer.valueOf(i), peopleSpaceTile);
        }
        Bundle appWidgetOptions = this.mAppWidgetManager.getAppWidgetOptions(i);
        PeopleTileKey keyFromStorageByWidgetId = getKeyFromStorageByWidgetId(i);
        if (!PeopleTileKey.isValid(keyFromStorageByWidgetId)) {
            Log.e("PeopleSpaceWidgetMgr", "Cannot update invalid widget");
            return;
        }
        this.mAppWidgetManager.updateAppWidget(i, PeopleTileViewHelper.createRemoteViews(this.mContext, peopleSpaceTile, i, appWidgetOptions, keyFromStorageByWidgetId));
    }

    public final void updateSingleConversationWidgets(int[] iArr) {
        HashMap hashMap = new HashMap();
        for (int i : iArr) {
            PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
            if (tileForExistingWidget == null) {
                Log.e("PeopleSpaceWidgetMgr", "Matching conversation not found for shortcut ID");
            }
            updateAppWidgetOptionsAndView(i, tileForExistingWidget);
            hashMap.put(Integer.valueOf(i), tileForExistingWidget);
            if (tileForExistingWidget != null) {
                registerConversationListenerIfNeeded(new PeopleTileKey(tileForExistingWidget));
            }
        }
        Context context = this.mContext;
        PeopleSpaceWidgetManager peopleSpaceWidgetManager = this.mManager;
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        R$dimen.postOnBackgroundThread(new PeopleSpaceUtils$$ExternalSyntheticLambda0(context, peopleSpaceWidgetManager, hashMap, iArr));
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
        	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
        */
    @com.android.internal.annotations.VisibleForTesting
    public void updateWidgetsFromBroadcastInBackground(java.lang.String r11) {
        /*
            r10 = this;
            android.appwidget.AppWidgetManager r0 = r10.mAppWidgetManager
            android.content.ComponentName r1 = new android.content.ComponentName
            android.content.Context r2 = r10.mContext
            java.lang.Class<com.android.systemui.people.widget.PeopleSpaceWidgetProvider> r3 = com.android.systemui.people.widget.PeopleSpaceWidgetProvider.class
            r1.<init>(r2, r3)
            int[] r0 = r0.getAppWidgetIds(r1)
            if (r0 != 0) goto L_0x0012
            return
        L_0x0012:
            int r1 = r0.length
            r2 = 0
            r3 = r2
        L_0x0015:
            if (r3 >= r1) goto L_0x007a
            r4 = r0[r3]
            r5 = 0
            java.lang.Object r6 = r10.mLock     // Catch:{ NameNotFoundException -> 0x0038 }
            monitor-enter(r6)     // Catch:{ NameNotFoundException -> 0x0038 }
            android.app.people.PeopleSpaceTile r7 = r10.getTileForExistingWidgetThrowing(r4)     // Catch:{ all -> 0x0035 }
            if (r7 != 0) goto L_0x002c
            java.lang.String r7 = "PeopleSpaceWidgetMgr"
            java.lang.String r8 = "Matching conversation not found for shortcut ID"
            android.util.Log.e(r7, r8)     // Catch:{ all -> 0x0035 }
            monitor-exit(r6)     // Catch:{ all -> 0x0035 }
            goto L_0x0074
        L_0x002c:
            android.app.people.PeopleSpaceTile r5 = r10.getTileWithCurrentState(r7, r11)     // Catch:{ all -> 0x0035 }
            r10.updateAppWidgetOptionsAndView(r4, r5)     // Catch:{ all -> 0x0035 }
            monitor-exit(r6)     // Catch:{ all -> 0x0035 }
            goto L_0x0074
        L_0x0035:
            r7 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0035 }
            throw r7     // Catch:{ NameNotFoundException -> 0x0038 }
        L_0x0038:
            r6 = move-exception
            java.lang.String r7 = "PeopleSpaceWidgetMgr"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Package no longer found for tile: "
            r8.append(r9)
            r8.append(r6)
            java.lang.String r6 = r8.toString()
            android.util.Log.e(r7, r6)
            android.content.Context r6 = r10.mContext
            java.lang.Class<android.app.job.JobScheduler> r7 = android.app.job.JobScheduler.class
            java.lang.Object r6 = r6.getSystemService(r7)
            android.app.job.JobScheduler r6 = (android.app.job.JobScheduler) r6
            if (r6 == 0) goto L_0x0065
            r7 = 74823873(0x475b8c1, float:2.8884446E-36)
            android.app.job.JobInfo r6 = r6.getPendingJob(r7)
            if (r6 == 0) goto L_0x0065
            goto L_0x0074
        L_0x0065:
            java.lang.Object r6 = r10.mLock
            monitor-enter(r6)
            r10.updateAppWidgetOptionsAndView(r4, r5)     // Catch:{ all -> 0x0077 }
            monitor-exit(r6)     // Catch:{ all -> 0x0077 }
            r5 = 1
            int[] r5 = new int[r5]
            r5[r2] = r4
            r10.deleteWidgets(r5)
        L_0x0074:
            int r3 = r3 + 1
            goto L_0x0015
        L_0x0077:
            r10 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0077 }
            throw r10
        L_0x007a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleSpaceWidgetManager.updateWidgetsFromBroadcastInBackground(java.lang.String):void");
    }

    public static Set getNewWidgets(Set set, HashMap hashMap) {
        return (Set) set.stream().map(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda3(hashMap, 0)).filter(Monitor$$ExternalSyntheticLambda4.INSTANCE$1).collect(Collectors.toSet());
    }

    public final HashSet getMatchingKeyWidgetIds(PeopleTileKey peopleTileKey) {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            return new HashSet();
        }
        return new HashSet(this.mSharedPrefs.getStringSet(peopleTileKey.toString(), new HashSet()));
    }

    public final boolean getPackageSuspended(PeopleSpaceTile peopleSpaceTile) throws PackageManager.NameNotFoundException {
        boolean z;
        if (TextUtils.isEmpty(peopleSpaceTile.getPackageName()) || !this.mPackageManager.isPackageSuspended(peopleSpaceTile.getPackageName())) {
            z = false;
        } else {
            z = true;
        }
        PackageManager packageManager = this.mPackageManager;
        String packageName = peopleSpaceTile.getPackageName();
        PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
        packageManager.getApplicationInfoAsUser(packageName, 128, peopleSpaceTile.getUserHandle().getIdentifier());
        return z;
    }

    public final PeopleSpaceTile getTileForExistingWidget(int i) {
        try {
            return getTileForExistingWidgetThrowing(i);
        } catch (Exception e) {
            Log.e("PeopleSpaceWidgetMgr", "Failed to retrieve conversation for tile: " + e);
            return null;
        }
    }

    public final PeopleSpaceTile getTileFromPersistentStorage(PeopleTileKey peopleTileKey, int i, boolean z) throws PackageManager.NameNotFoundException {
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("PeopleTileKey invalid: ");
            m.append(peopleTileKey.toString());
            Log.e("PeopleSpaceWidgetMgr", m.toString());
            return null;
        }
        IPeopleManager iPeopleManager = this.mIPeopleManager;
        if (iPeopleManager == null || this.mLauncherApps == null) {
            Log.d("PeopleSpaceWidgetMgr", "System services are null");
            return null;
        }
        try {
            Objects.requireNonNull(peopleTileKey);
            ConversationChannel conversation = iPeopleManager.getConversation(peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileKey.mShortcutId);
            if (conversation == null) {
                return null;
            }
            PeopleSpaceTile.Builder builder = new PeopleSpaceTile.Builder(conversation, this.mLauncherApps);
            String string = this.mSharedPrefs.getString(String.valueOf(i), (String) null);
            if (z && string != null && builder.build().getContactUri() == null) {
                builder.setContactUri(Uri.parse(string));
            }
            return getTileWithCurrentState(builder.build(), "android.intent.action.BOOT_COMPLETED");
        } catch (RemoteException e) {
            Log.e("PeopleSpaceWidgetMgr", "Could not retrieve data: " + e);
            return null;
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.app.people.PeopleSpaceTile getTileWithCurrentState(android.app.people.PeopleSpaceTile r5, java.lang.String r6) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            r4 = this;
            android.app.people.PeopleSpaceTile$Builder r0 = r5.toBuilder()
            int r1 = r6.hashCode()
            r2 = 0
            r3 = 1
            switch(r1) {
                case -1238404651: goto L_0x0054;
                case -1001645458: goto L_0x004a;
                case -864107122: goto L_0x0040;
                case -19011148: goto L_0x0036;
                case 798292259: goto L_0x002c;
                case 833559602: goto L_0x0022;
                case 1290767157: goto L_0x0018;
                case 2106958107: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x005e
        L_0x000e:
            java.lang.String r1 = "android.app.action.INTERRUPTION_FILTER_CHANGED"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = r2
            goto L_0x005f
        L_0x0018:
            java.lang.String r1 = "android.intent.action.PACKAGES_UNSUSPENDED"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = 2
            goto L_0x005f
        L_0x0022:
            java.lang.String r1 = "android.intent.action.USER_UNLOCKED"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = 5
            goto L_0x005f
        L_0x002c:
            java.lang.String r1 = "android.intent.action.BOOT_COMPLETED"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = 7
            goto L_0x005f
        L_0x0036:
            java.lang.String r1 = "android.intent.action.LOCALE_CHANGED"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = 6
            goto L_0x005f
        L_0x0040:
            java.lang.String r1 = "android.intent.action.MANAGED_PROFILE_AVAILABLE"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = 3
            goto L_0x005f
        L_0x004a:
            java.lang.String r1 = "android.intent.action.PACKAGES_SUSPENDED"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = r3
            goto L_0x005f
        L_0x0054:
            java.lang.String r1 = "android.intent.action.MANAGED_PROFILE_UNAVAILABLE"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x005e
            r6 = 4
            goto L_0x005f
        L_0x005e:
            r6 = -1
        L_0x005f:
            switch(r6) {
                case 0: goto L_0x0082;
                case 1: goto L_0x007a;
                case 2: goto L_0x007a;
                case 3: goto L_0x0063;
                case 4: goto L_0x0063;
                case 5: goto L_0x0063;
                case 6: goto L_0x00b0;
                default: goto L_0x0062;
            }
        L_0x0062:
            goto L_0x008a
        L_0x0063:
            android.os.UserHandle r6 = r5.getUserHandle()
            if (r6 == 0) goto L_0x0076
            android.os.UserManager r4 = r4.mUserManager
            android.os.UserHandle r5 = r5.getUserHandle()
            boolean r4 = r4.isQuietModeEnabled(r5)
            if (r4 == 0) goto L_0x0076
            r2 = r3
        L_0x0076:
            r0.setIsUserQuieted(r2)
            goto L_0x00b0
        L_0x007a:
            boolean r4 = r4.getPackageSuspended(r5)
            r0.setIsPackageSuspended(r4)
            goto L_0x00b0
        L_0x0082:
            int r4 = r4.getNotificationPolicyState()
            r0.setNotificationPolicyState(r4)
            goto L_0x00b0
        L_0x008a:
            android.os.UserHandle r6 = r5.getUserHandle()
            if (r6 == 0) goto L_0x009d
            android.os.UserManager r6 = r4.mUserManager
            android.os.UserHandle r1 = r5.getUserHandle()
            boolean r6 = r6.isQuietModeEnabled(r1)
            if (r6 == 0) goto L_0x009d
            r2 = r3
        L_0x009d:
            android.app.people.PeopleSpaceTile$Builder r6 = r0.setIsUserQuieted(r2)
            boolean r5 = r4.getPackageSuspended(r5)
            android.app.people.PeopleSpaceTile$Builder r5 = r6.setIsPackageSuspended(r5)
            int r4 = r4.getNotificationPolicyState()
            r5.setNotificationPolicyState(r4)
        L_0x00b0:
            android.app.people.PeopleSpaceTile r4 = r0.build()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleSpaceWidgetManager.getTileWithCurrentState(android.app.people.PeopleSpaceTile, java.lang.String):android.app.people.PeopleSpaceTile");
    }

    public final void registerConversationListenerIfNeeded(PeopleTileKey peopleTileKey) {
        if (PeopleTileKey.isValid(peopleTileKey)) {
            TileConversationListener tileConversationListener = new TileConversationListener();
            synchronized (mListeners) {
                if (!mListeners.containsKey(peopleTileKey)) {
                    mListeners.put(peopleTileKey, tileConversationListener);
                    PeopleManager peopleManager = this.mPeopleManager;
                    Objects.requireNonNull(peopleTileKey);
                    peopleManager.registerConversationListener(peopleTileKey.mPackageName, peopleTileKey.mUserId, peopleTileKey.mShortcutId, tileConversationListener, this.mContext.getMainExecutor());
                }
            }
        }
    }

    public final void updateStorageAndViewWithConversationData(ConversationChannel conversationChannel, int i) {
        PeopleSpaceTile tileForExistingWidget = getTileForExistingWidget(i);
        if (tileForExistingWidget != null) {
            PeopleSpaceTile.Builder builder = tileForExistingWidget.toBuilder();
            ShortcutInfo shortcutInfo = conversationChannel.getShortcutInfo();
            Uri uri = null;
            if (shortcutInfo.getPersons() != null && shortcutInfo.getPersons().length > 0) {
                Person person = shortcutInfo.getPersons()[0];
                if (person.getUri() != null) {
                    uri = Uri.parse(person.getUri());
                }
            }
            CharSequence label = shortcutInfo.getLabel();
            if (label != null) {
                builder.setUserName(label);
            }
            Icon convertDrawableToIcon = PeopleSpaceTile.convertDrawableToIcon(this.mLauncherApps.getShortcutIconDrawable(shortcutInfo, 0));
            if (convertDrawableToIcon != null) {
                builder.setUserIcon(convertDrawableToIcon);
            }
            NotificationChannel notificationChannel = conversationChannel.getNotificationChannel();
            if (notificationChannel != null) {
                builder.setIsImportantConversation(notificationChannel.isImportantConversation());
            }
            builder.setContactUri(uri).setStatuses(conversationChannel.getStatuses()).setLastInteractionTimestamp(conversationChannel.getLastEventTimestamp());
            updateAppWidgetOptionsAndView(i, builder.build());
        }
    }

    public final void updateWidgetIdsBasedOnNotifications(HashSet hashSet) {
        if (!hashSet.isEmpty()) {
            try {
                ((Map) hashSet.stream().map(WifiPickerTracker$$ExternalSyntheticLambda11.INSTANCE$1).collect(Collectors.toMap(Function.identity(), new PeopleSpaceWidgetManager$$ExternalSyntheticLambda4(this, getGroupedConversationNotifications())))).forEach(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda2(this));
            } catch (Exception e) {
                Log.e("PeopleSpaceWidgetMgr", "Exception updating widgets: " + e);
            }
        }
    }

    @VisibleForTesting
    public PeopleSpaceWidgetManager(Context context, AppWidgetManager appWidgetManager, IPeopleManager iPeopleManager, PeopleManager peopleManager, LauncherApps launcherApps, CommonNotifCollection commonNotifCollection, PackageManager packageManager, Optional<Bubbles> optional, UserManager userManager, BackupManager backupManager, INotificationManager iNotificationManager, NotificationManager notificationManager, Executor executor) {
        this.mContext = context;
        this.mAppWidgetManager = appWidgetManager;
        this.mIPeopleManager = iPeopleManager;
        this.mPeopleManager = peopleManager;
        this.mLauncherApps = launcherApps;
        this.mNotifCollection = commonNotifCollection;
        this.mPackageManager = packageManager;
        this.mBubblesOptional = optional;
        this.mUserManager = userManager;
        this.mBackupManager = backupManager;
        this.mINotificationManager = iNotificationManager;
        this.mNotificationManager = notificationManager;
        this.mManager = this;
        this.mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.mBgExecutor = executor;
    }
}
