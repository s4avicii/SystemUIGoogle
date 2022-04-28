package com.android.systemui.media;

import java.util.Objects;

/* compiled from: MediaViewHolder.kt */
public final class MediaViewHolder$marquee$1 implements Runnable {
    public final /* synthetic */ boolean $start;
    public final /* synthetic */ MediaViewHolder this$0;

    public MediaViewHolder$marquee$1(MediaViewHolder mediaViewHolder, boolean z) {
        this.this$0 = mediaViewHolder;
        this.$start = z;
    }

    public final void run() {
        MediaViewHolder mediaViewHolder = this.this$0;
        Objects.requireNonNull(mediaViewHolder);
        mediaViewHolder.longPressText.setSelected(this.$start);
    }
}
