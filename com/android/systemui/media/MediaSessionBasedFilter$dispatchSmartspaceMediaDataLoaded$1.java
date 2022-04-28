package com.android.systemui.media;

import com.android.systemui.media.MediaDataManager;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter$dispatchSmartspaceMediaDataLoaded$1 implements Runnable {
    public final /* synthetic */ SmartspaceMediaData $info;
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaSessionBasedFilter this$0;

    public MediaSessionBasedFilter$dispatchSmartspaceMediaDataLoaded$1(MediaSessionBasedFilter mediaSessionBasedFilter, String str, SmartspaceMediaData smartspaceMediaData) {
        this.this$0 = mediaSessionBasedFilter;
        this.$key = str;
        this.$info = smartspaceMediaData;
    }

    public final void run() {
        Set<MediaDataManager.Listener> set = CollectionsKt___CollectionsKt.toSet(this.this$0.listeners);
        String str = this.$key;
        SmartspaceMediaData smartspaceMediaData = this.$info;
        for (MediaDataManager.Listener onSmartspaceMediaDataLoaded : set) {
            onSmartspaceMediaDataLoaded.onSmartspaceMediaDataLoaded(str, smartspaceMediaData, false, false);
        }
    }
}
