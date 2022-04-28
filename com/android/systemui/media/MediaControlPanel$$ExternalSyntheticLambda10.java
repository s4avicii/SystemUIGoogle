package com.android.systemui.media;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda10 implements View.OnClickListener {
    public final /* synthetic */ MediaControlPanel f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda10(MediaControlPanel mediaControlPanel, Runnable runnable) {
        this.f$0 = mediaControlPanel;
        this.f$1 = runnable;
    }

    public final void onClick(View view) {
        MediaControlPanel mediaControlPanel = this.f$0;
        Runnable runnable = this.f$1;
        Objects.requireNonNull(mediaControlPanel);
        if (!mediaControlPanel.mFalsingManager.isFalseTap(1)) {
            mediaControlPanel.logSmartspaceCardReported(760, false, 0, 0);
            runnable.run();
        }
    }
}
