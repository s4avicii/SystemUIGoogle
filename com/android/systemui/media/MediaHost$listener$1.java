package com.android.systemui.media;

import com.android.systemui.media.MediaDataManager;

/* compiled from: MediaHost.kt */
public final class MediaHost$listener$1 implements MediaDataManager.Listener {
    public final /* synthetic */ MediaHost this$0;

    public MediaHost$listener$1(MediaHost mediaHost) {
        this.this$0 = mediaHost;
    }

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
        if (z) {
            this.this$0.updateViewVisibility();
        }
    }

    public final void onMediaDataRemoved(String str) {
        this.this$0.updateViewVisibility();
    }

    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
        this.this$0.updateViewVisibility();
    }

    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        if (z) {
            this.this$0.updateViewVisibility();
        }
    }
}
