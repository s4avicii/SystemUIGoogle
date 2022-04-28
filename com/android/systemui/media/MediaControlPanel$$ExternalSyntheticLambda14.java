package com.android.systemui.media;

import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda14 implements Function0 {
    public final /* synthetic */ MediaControlPanel f$0;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda14(MediaControlPanel mediaControlPanel) {
        this.f$0 = mediaControlPanel;
    }

    public final Object invoke() {
        MediaControlPanel mediaControlPanel = this.f$0;
        Objects.requireNonNull(mediaControlPanel);
        mediaControlPanel.logSmartspaceCardReported(760, false, 0, 0);
        return Unit.INSTANCE;
    }
}
