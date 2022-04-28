package com.android.systemui.media;

import java.util.Objects;

/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter$onSmartspaceMediaDataLoaded$1 implements Runnable {
    public final /* synthetic */ SmartspaceMediaData $data;
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaSessionBasedFilter this$0;

    public MediaSessionBasedFilter$onSmartspaceMediaDataLoaded$1(MediaSessionBasedFilter mediaSessionBasedFilter, String str, SmartspaceMediaData smartspaceMediaData) {
        this.this$0 = mediaSessionBasedFilter;
        this.$key = str;
        this.$data = smartspaceMediaData;
    }

    public final void run() {
        MediaSessionBasedFilter mediaSessionBasedFilter = this.this$0;
        String str = this.$key;
        SmartspaceMediaData smartspaceMediaData = this.$data;
        Objects.requireNonNull(mediaSessionBasedFilter);
        mediaSessionBasedFilter.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchSmartspaceMediaDataLoaded$1(mediaSessionBasedFilter, str, smartspaceMediaData));
    }
}
