package com.android.systemui.media;

import android.media.session.MediaController;
import android.media.session.PlaybackState;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$getCustomAction$1 implements Runnable {
    public final /* synthetic */ MediaController $controller;
    public final /* synthetic */ PlaybackState.CustomAction $it;

    public MediaDataManager$getCustomAction$1(MediaController mediaController, PlaybackState.CustomAction customAction) {
        this.$controller = mediaController;
        this.$it = customAction;
    }

    public final void run() {
        MediaController.TransportControls transportControls = this.$controller.getTransportControls();
        PlaybackState.CustomAction customAction = this.$it;
        transportControls.sendCustomAction(customAction, customAction.getExtras());
    }
}
