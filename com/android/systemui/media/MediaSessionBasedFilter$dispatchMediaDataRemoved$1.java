package com.android.systemui.media;

import com.android.systemui.media.MediaDataManager;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter$dispatchMediaDataRemoved$1 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaSessionBasedFilter this$0;

    public MediaSessionBasedFilter$dispatchMediaDataRemoved$1(MediaSessionBasedFilter mediaSessionBasedFilter, String str) {
        this.this$0 = mediaSessionBasedFilter;
        this.$key = str;
    }

    public final void run() {
        Set<MediaDataManager.Listener> set = CollectionsKt___CollectionsKt.toSet(this.this$0.listeners);
        String str = this.$key;
        for (MediaDataManager.Listener onMediaDataRemoved : set) {
            onMediaDataRemoved.onMediaDataRemoved(str);
        }
    }
}
