package com.android.systemui.media;

import java.util.Objects;

/* compiled from: RecommendationViewHolder.kt */
public final class RecommendationViewHolder$marquee$1 implements Runnable {
    public final /* synthetic */ boolean $start;
    public final /* synthetic */ RecommendationViewHolder this$0;

    public RecommendationViewHolder$marquee$1(RecommendationViewHolder recommendationViewHolder, boolean z) {
        this.this$0 = recommendationViewHolder;
        this.$start = z;
    }

    public final void run() {
        RecommendationViewHolder recommendationViewHolder = this.this$0;
        Objects.requireNonNull(recommendationViewHolder);
        recommendationViewHolder.longPressText.setSelected(this.$start);
    }
}
