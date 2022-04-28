package com.android.systemui.media;

import android.view.View;

/* compiled from: MediaHost.kt */
public final class MediaHost$init$1 implements View.OnAttachStateChangeListener {
    public final /* synthetic */ MediaHost this$0;

    public MediaHost$init$1(MediaHost mediaHost) {
        this.this$0 = mediaHost;
    }

    public final void onViewAttachedToWindow(View view) {
        this.this$0.setListeningToMediaData(true);
        this.this$0.updateViewVisibility();
    }

    public final void onViewDetachedFromWindow(View view) {
        this.this$0.setListeningToMediaData(false);
    }
}
