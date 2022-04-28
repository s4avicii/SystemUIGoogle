package com.android.systemui.media;

import android.view.View;
import android.view.ViewGroup;
import java.util.Objects;

/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler$scrollChangedListener$1 implements View.OnScrollChangeListener {
    public final /* synthetic */ MediaCarouselScrollHandler this$0;

    public MediaCarouselScrollHandler$scrollChangedListener$1(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
        this.this$0 = mediaCarouselScrollHandler;
    }

    public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
        boolean z;
        boolean z2;
        float f;
        boolean z3;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
        Objects.requireNonNull(mediaCarouselScrollHandler);
        if (mediaCarouselScrollHandler.playerWidthPlusPadding != 0) {
            MediaScrollView mediaScrollView = this.this$0.scrollView;
            Objects.requireNonNull(mediaScrollView);
            int scrollX = mediaScrollView.getScrollX();
            if (mediaScrollView.isLayoutRtl()) {
                ViewGroup viewGroup = mediaScrollView.contentContainer;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                scrollX = (viewGroup.getWidth() - mediaScrollView.getWidth()) - scrollX;
            }
            MediaCarouselScrollHandler mediaCarouselScrollHandler2 = this.this$0;
            Objects.requireNonNull(mediaCarouselScrollHandler2);
            int i5 = scrollX / mediaCarouselScrollHandler2.playerWidthPlusPadding;
            MediaCarouselScrollHandler mediaCarouselScrollHandler3 = this.this$0;
            Objects.requireNonNull(mediaCarouselScrollHandler3);
            int i6 = scrollX % mediaCarouselScrollHandler3.playerWidthPlusPadding;
            boolean z4 = true;
            if (mediaCarouselScrollHandler2.scrollIntoCurrentMedia != 0) {
                z = true;
            } else {
                z = false;
            }
            mediaCarouselScrollHandler2.scrollIntoCurrentMedia = i6;
            if (i6 != 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            int i7 = mediaCarouselScrollHandler2.visibleMediaIndex;
            if (!(i5 == i7 && z == z2)) {
                mediaCarouselScrollHandler2.visibleMediaIndex = i5;
                if (i7 != i5 && mediaCarouselScrollHandler2.visibleToUser) {
                    mediaCarouselScrollHandler2.logSmartspaceImpression.invoke(Boolean.valueOf(mediaCarouselScrollHandler2.qsExpanded));
                }
                mediaCarouselScrollHandler2.closeGuts.invoke(Boolean.FALSE);
                mediaCarouselScrollHandler2.updatePlayerVisibilities();
            }
            float f2 = (float) mediaCarouselScrollHandler2.visibleMediaIndex;
            int i8 = mediaCarouselScrollHandler2.playerWidthPlusPadding;
            if (i8 > 0) {
                f = ((float) i6) / ((float) i8);
            } else {
                f = 0.0f;
            }
            float f3 = f2 + f;
            if (mediaCarouselScrollHandler2.scrollView.isLayoutRtl()) {
                f3 = (((float) mediaCarouselScrollHandler2.mediaContent.getChildCount()) - f3) - ((float) 1);
            }
            mediaCarouselScrollHandler2.pageIndicator.setLocation(f3);
            if (mediaCarouselScrollHandler2.contentTranslation == 0.0f) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3 && mediaCarouselScrollHandler2.scrollIntoCurrentMedia == 0) {
                z4 = false;
            }
            mediaCarouselScrollHandler2.scrollView.setClipToOutline(z4);
        }
    }
}
