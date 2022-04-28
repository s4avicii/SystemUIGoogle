package com.android.systemui.media;

import java.util.Objects;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$dismissSmartspaceRecommendation$1 implements Runnable {
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$dismissSmartspaceRecommendation$1(MediaDataManager mediaDataManager) {
        this.this$0 = mediaDataManager;
    }

    public final void run() {
        MediaDataManager mediaDataManager = this.this$0;
        Objects.requireNonNull(mediaDataManager);
        SmartspaceMediaData smartspaceMediaData = mediaDataManager.smartspaceMediaData;
        Objects.requireNonNull(smartspaceMediaData);
        mediaDataManager.notifySmartspaceMediaDataRemoved(smartspaceMediaData.targetId, true);
    }
}
