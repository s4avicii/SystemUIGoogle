package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.MediaSession;
import java.util.Objects;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$dismissMediaData$1 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$dismissMediaData$1(MediaDataManager mediaDataManager, String str) {
        this.this$0 = mediaDataManager;
        this.$key = str;
    }

    public final void run() {
        boolean z;
        MediaSession.Token token;
        MediaData mediaData = this.this$0.mediaEntries.get(this.$key);
        if (mediaData != null) {
            MediaDataManager mediaDataManager = this.this$0;
            if (mediaData.playbackLocation == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z && (token = mediaData.token) != null) {
                MediaControllerFactory mediaControllerFactory = mediaDataManager.mediaControllerFactory;
                Objects.requireNonNull(mediaControllerFactory);
                new MediaController(mediaControllerFactory.mContext, token).getTransportControls().stop();
            }
        }
    }
}
