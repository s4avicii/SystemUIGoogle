package com.android.systemui.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.collections.ArrayAsCollection;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;

/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter implements MediaDataManager.Listener {
    public final Executor backgroundExecutor;
    public final Executor foregroundExecutor;
    public final LinkedHashMap keyedTokens = new LinkedHashMap();
    public final LinkedHashSet listeners = new LinkedHashSet();
    public final LinkedHashMap<String, List<MediaController>> packageControllers = new LinkedHashMap<>();
    public final MediaSessionBasedFilter$sessionListener$1 sessionListener = new MediaSessionBasedFilter$sessionListener$1(this);
    public final MediaSessionManager sessionManager;
    public final LinkedHashSet tokensWithNotifications = new LinkedHashSet();

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onMediaDataLoaded$1(mediaData, str2, str, this, z));
    }

    public final void onMediaDataRemoved(String str) {
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onMediaDataRemoved$1(this, str));
    }

    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onSmartspaceMediaDataLoaded$1(this, str, smartspaceMediaData));
    }

    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        this.backgroundExecutor.execute(new MediaSessionBasedFilter$onSmartspaceMediaDataRemoved$1(this, str, z));
    }

    public MediaSessionBasedFilter(final Context context, MediaSessionManager mediaSessionManager, Executor executor, Executor executor2) {
        this.sessionManager = mediaSessionManager;
        this.foregroundExecutor = executor;
        this.backgroundExecutor = executor2;
        executor2.execute(new Runnable() {
            public final void run() {
                ComponentName componentName = new ComponentName(context, NotificationListenerWithPlugins.class);
                MediaSessionBasedFilter mediaSessionBasedFilter = this;
                mediaSessionBasedFilter.sessionManager.addOnActiveSessionsChangedListener(mediaSessionBasedFilter.sessionListener, componentName);
                MediaSessionBasedFilter mediaSessionBasedFilter2 = this;
                MediaSessionBasedFilter.access$handleControllersChanged(mediaSessionBasedFilter2, mediaSessionBasedFilter2.sessionManager.getActiveSessions(componentName));
            }
        });
    }

    public static final void access$handleControllersChanged(MediaSessionBasedFilter mediaSessionBasedFilter, List list) {
        Boolean bool;
        Objects.requireNonNull(mediaSessionBasedFilter);
        mediaSessionBasedFilter.packageControllers.clear();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MediaController mediaController = (MediaController) it.next();
            List list2 = mediaSessionBasedFilter.packageControllers.get(mediaController.getPackageName());
            if (list2 == null) {
                bool = null;
            } else {
                bool = Boolean.valueOf(list2.add(mediaController));
            }
            if (bool == null) {
                List put = mediaSessionBasedFilter.packageControllers.put(mediaController.getPackageName(), new ArrayList(new ArrayAsCollection(new MediaController[]{mediaController}, true)));
            }
        }
        LinkedHashSet linkedHashSet = mediaSessionBasedFilter.tokensWithNotifications;
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
        Iterator it2 = list.iterator();
        while (it2.hasNext()) {
            arrayList.add(((MediaController) it2.next()).getSessionToken());
        }
        linkedHashSet.retainAll(arrayList);
    }
}
