package com.android.systemui.media;

import java.util.Objects;

/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter$onSmartspaceMediaDataRemoved$1 implements Runnable {
    public final /* synthetic */ boolean $immediately;
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaSessionBasedFilter this$0;

    public MediaSessionBasedFilter$onSmartspaceMediaDataRemoved$1(MediaSessionBasedFilter mediaSessionBasedFilter, String str, boolean z) {
        this.this$0 = mediaSessionBasedFilter;
        this.$key = str;
        this.$immediately = z;
    }

    public final void run() {
        MediaSessionBasedFilter mediaSessionBasedFilter = this.this$0;
        String str = this.$key;
        boolean z = this.$immediately;
        Objects.requireNonNull(mediaSessionBasedFilter);
        mediaSessionBasedFilter.foregroundExecutor.execute(new MediaSessionBasedFilter$dispatchSmartspaceMediaDataRemoved$1(mediaSessionBasedFilter, str, z));
    }
}
