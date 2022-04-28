package com.android.systemui.media;

/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler$onTouch$1 implements Runnable {
    public final /* synthetic */ int $newScrollX;
    public final /* synthetic */ MediaCarouselScrollHandler this$0;

    public MediaCarouselScrollHandler$onTouch$1(MediaCarouselScrollHandler mediaCarouselScrollHandler, int i) {
        this.this$0 = mediaCarouselScrollHandler;
        this.$newScrollX = i;
    }

    public final void run() {
        MediaScrollView mediaScrollView = this.this$0.scrollView;
        mediaScrollView.smoothScrollTo(this.$newScrollX, mediaScrollView.getScrollY());
    }
}
