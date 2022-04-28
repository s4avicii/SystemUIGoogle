package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.time.SystemClock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDataFilter.kt */
public final class MediaDataFilter implements MediaDataManager.Listener {
    public final LinkedHashSet _listeners = new LinkedHashSet();
    public final LinkedHashMap<String, MediaData> allEntries = new LinkedHashMap<>();
    public final Context context;
    public final Executor executor;
    public final NotificationLockscreenUserManager lockscreenUserManager;
    public MediaDataManager mediaDataManager;
    public String reactivatedKey;
    public SmartspaceMediaData smartspaceMediaData = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
    public final SystemClock systemClock;
    public final LinkedHashMap<String, MediaData> userEntries = new LinkedHashMap<>();
    public final C08791 userTracker;

    /* renamed from: getListeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final Set<MediaDataManager.Listener> mo8888xef59304f() {
        return CollectionsKt___CollectionsKt.toSet(this._listeners);
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            this.allEntries.remove(str2);
        }
        this.allEntries.put(str, mediaData);
        if (this.lockscreenUserManager.isCurrentProfile(mediaData.userId)) {
            if (str2 != null && !Intrinsics.areEqual(str2, str)) {
                this.userEntries.remove(str2);
            }
            this.userEntries.put(str, mediaData);
            for (MediaDataManager.Listener onMediaDataLoaded$default : mo8888xef59304f()) {
                MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(onMediaDataLoaded$default, str, str2, mediaData, false, 0, 24);
            }
        }
    }

    public final void onMediaDataRemoved(String str) {
        this.allEntries.remove(str);
        if (this.userEntries.remove(str) != null) {
            for (MediaDataManager.Listener onMediaDataRemoved : mo8888xef59304f()) {
                onMediaDataRemoved.onMediaDataRemoved(str);
            }
        }
    }

    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData2, boolean z, boolean z2) {
        boolean z3;
        SmartspaceMediaData smartspaceMediaData3 = smartspaceMediaData2;
        if (!smartspaceMediaData3.isActive) {
            Log.d("MediaDataFilter", "Inactive recommendation data. Skip triggering.");
            return;
        }
        this.smartspaceMediaData = smartspaceMediaData3;
        LinkedHashMap<String, MediaData> linkedHashMap = this.userEntries;
        TreeMap treeMap = new TreeMap(new MediaDataFilter$onSmartspaceMediaDataLoaded$$inlined$compareBy$1(this));
        treeMap.putAll(linkedHashMap);
        long j = Long.MAX_VALUE;
        if (!treeMap.isEmpty()) {
            long elapsedRealtime = this.systemClock.elapsedRealtime();
            MediaData mediaData = (MediaData) treeMap.get((String) treeMap.lastKey());
            if (mediaData != null) {
                j = elapsedRealtime - mediaData.lastActive;
            }
        }
        long j2 = MediaDataFilterKt.SMARTSPACE_MAX_AGE;
        SmartspaceAction smartspaceAction = smartspaceMediaData3.cardAction;
        if (smartspaceAction != null) {
            long j3 = smartspaceAction.getExtras().getLong("resumable_media_max_age_seconds", 0);
            if (j3 > 0) {
                j2 = TimeUnit.SECONDS.toMillis(j3);
            }
        }
        LinkedHashMap<String, MediaData> linkedHashMap2 = this.userEntries;
        LinkedHashMap linkedHashMap3 = new LinkedHashMap();
        for (Map.Entry next : linkedHashMap2.entrySet()) {
            String str2 = (String) next.getKey();
            MediaData mediaData2 = (MediaData) next.getValue();
            Objects.requireNonNull(mediaData2);
            if (mediaData2.active) {
                linkedHashMap3.put(next.getKey(), next.getValue());
            }
        }
        boolean isEmpty = linkedHashMap3.isEmpty();
        boolean z4 = false;
        if (!isEmpty || !(!this.userEntries.isEmpty())) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (j >= j2) {
            z4 = true;
        } else if (z3) {
            String str3 = (String) treeMap.lastKey();
            Log.d("MediaDataFilter", "reactivating " + str3 + " instead of smartspace");
            this.reactivatedKey = str3;
            Object obj = treeMap.get(str3);
            Intrinsics.checkNotNull(obj);
            MediaData copy$default = MediaData.copy$default((MediaData) obj, (List) null, (List) null, (String) null, (MediaDeviceData) null, true, (MediaResumeListener$getResumeAction$1) null, false, false, (Boolean) null, false, 16744447);
            for (MediaDataManager.Listener onMediaDataLoaded$default : mo8888xef59304f()) {
                MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(onMediaDataLoaded$default, str3, str3, copy$default, false, (int) (this.systemClock.currentTimeMillis() - smartspaceMediaData3.headphoneConnectionTimeMillis), 8);
            }
        }
        if (!smartspaceMediaData3.isValid) {
            Log.d("MediaDataFilter", "Invalid recommendation data. Skip showing the rec card");
            return;
        }
        for (MediaDataManager.Listener onSmartspaceMediaDataLoaded : mo8888xef59304f()) {
            onSmartspaceMediaDataLoaded.onSmartspaceMediaDataLoaded(str, smartspaceMediaData3, z4, z3);
        }
    }

    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        String str2 = this.reactivatedKey;
        if (str2 != null) {
            this.reactivatedKey = null;
            Log.d("MediaDataFilter", Intrinsics.stringPlus("expiring reactivated key ", str2));
            MediaData mediaData = this.userEntries.get(str2);
            if (mediaData != null) {
                for (MediaDataManager.Listener onMediaDataLoaded$default : mo8888xef59304f()) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(onMediaDataLoaded$default, str2, str2, mediaData, z, 0, 16);
                }
            }
        }
        SmartspaceMediaData smartspaceMediaData2 = this.smartspaceMediaData;
        Objects.requireNonNull(smartspaceMediaData2);
        if (smartspaceMediaData2.isActive) {
            SmartspaceMediaData smartspaceMediaData3 = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
            SmartspaceMediaData smartspaceMediaData4 = this.smartspaceMediaData;
            Objects.requireNonNull(smartspaceMediaData4);
            String str3 = smartspaceMediaData4.targetId;
            SmartspaceMediaData smartspaceMediaData5 = this.smartspaceMediaData;
            Objects.requireNonNull(smartspaceMediaData5);
            this.smartspaceMediaData = SmartspaceMediaData.copy$default(smartspaceMediaData3, str3, false, smartspaceMediaData5.isValid, (Intent) null, 0, 0, 506);
        }
        for (MediaDataManager.Listener onSmartspaceMediaDataRemoved : mo8888xef59304f()) {
            onSmartspaceMediaDataRemoved.onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public MediaDataFilter(Context context2, BroadcastDispatcher broadcastDispatcher, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor2, SystemClock systemClock2) {
        this.context = context2;
        this.lockscreenUserManager = notificationLockscreenUserManager;
        this.executor = executor2;
        this.systemClock = systemClock2;
        C08791 r1 = new CurrentUserTracker(this, broadcastDispatcher) {
            public final /* synthetic */ MediaDataFilter this$0;

            {
                this.this$0 = r1;
            }

            public final void onUserSwitched(int i) {
                MediaDataFilter mediaDataFilter = this.this$0;
                mediaDataFilter.executor.execute(new MediaDataFilter$1$onUserSwitched$1(mediaDataFilter, i));
            }
        };
        this.userTracker = r1;
        r1.startTracking();
    }

    @VisibleForTesting
    /* renamed from: handleUserSwitched$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo8889xa9aae04c(int i) {
        Set<MediaDataManager.Listener> listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core = mo8888xef59304f();
        ArrayList arrayList = new ArrayList(this.userEntries.keySet());
        this.userEntries.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Log.d("MediaDataFilter", "Removing " + str + " after user change");
            for (MediaDataManager.Listener onMediaDataRemoved : listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                onMediaDataRemoved.onMediaDataRemoved(str);
            }
        }
        for (Map.Entry next : this.allEntries.entrySet()) {
            String str2 = (String) next.getKey();
            MediaData mediaData = (MediaData) next.getValue();
            NotificationLockscreenUserManager notificationLockscreenUserManager = this.lockscreenUserManager;
            Objects.requireNonNull(mediaData);
            if (notificationLockscreenUserManager.isCurrentProfile(mediaData.userId)) {
                Log.d("MediaDataFilter", "Re-adding " + str2 + " after user change");
                this.userEntries.put(str2, mediaData);
                for (MediaDataManager.Listener onMediaDataLoaded$default : listeners$frameworks__base__packages__SystemUI__android_common__SystemUI_core) {
                    MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(onMediaDataLoaded$default, str2, (String) null, mediaData, false, 0, 24);
                }
            }
        }
    }
}
