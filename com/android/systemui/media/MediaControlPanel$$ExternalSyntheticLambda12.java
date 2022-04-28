package com.android.systemui.media;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda12 implements View.OnLongClickListener {
    public final /* synthetic */ MediaControlPanel f$0;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda12(MediaControlPanel mediaControlPanel) {
        this.f$0 = mediaControlPanel;
    }

    public final boolean onLongClick(View view) {
        MediaControlPanel mediaControlPanel = this.f$0;
        Objects.requireNonNull(mediaControlPanel);
        MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
        Objects.requireNonNull(mediaViewController);
        if (!mediaViewController.isGutsVisible) {
            mediaControlPanel.openGuts();
            return true;
        }
        mediaControlPanel.closeGuts(false);
        return true;
    }
}
