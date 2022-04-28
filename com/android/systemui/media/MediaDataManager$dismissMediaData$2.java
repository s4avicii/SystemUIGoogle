package com.android.systemui.media;

import java.util.Objects;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$dismissMediaData$2 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$dismissMediaData$2(MediaDataManager mediaDataManager, String str) {
        this.this$0 = mediaDataManager;
        this.$key = str;
    }

    public final void run() {
        MediaDataManager mediaDataManager = this.this$0;
        String str = this.$key;
        Objects.requireNonNull(mediaDataManager);
        mediaDataManager.mediaEntries.remove(str);
        mediaDataManager.notifyMediaDataRemoved(str);
    }
}
