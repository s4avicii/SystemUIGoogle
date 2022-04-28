package com.android.systemui.media;

import androidx.fragment.R$styleable;
import com.android.systemui.media.MediaPlayerData;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$thenByDescending$2<T> implements Comparator {
    public final /* synthetic */ Comparator $this_thenByDescending;

    public MediaPlayerData$special$$inlined$thenByDescending$2(MediaPlayerData$special$$inlined$thenByDescending$1 mediaPlayerData$special$$inlined$thenByDescending$1) {
        this.$this_thenByDescending = mediaPlayerData$special$$inlined$thenByDescending$1;
    }

    public final int compare(T t, T t2) {
        boolean z;
        boolean z2;
        int compare = this.$this_thenByDescending.compare(t, t2);
        if (compare != 0) {
            return compare;
        }
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) t2;
        MediaPlayerData mediaPlayerData = MediaPlayerData.INSTANCE;
        Objects.requireNonNull(mediaPlayerData);
        boolean z3 = MediaPlayerData.shouldPrioritizeSs;
        Objects.requireNonNull(mediaSortKey);
        if (z3) {
            z = mediaSortKey.isSsMediaRec;
        } else {
            z = !mediaSortKey.isSsMediaRec;
        }
        Boolean valueOf = Boolean.valueOf(z);
        MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) t;
        Objects.requireNonNull(mediaPlayerData);
        boolean z4 = MediaPlayerData.shouldPrioritizeSs;
        Objects.requireNonNull(mediaSortKey2);
        if (z4) {
            z2 = mediaSortKey2.isSsMediaRec;
        } else {
            z2 = !mediaSortKey2.isSsMediaRec;
        }
        return R$styleable.compareValues(valueOf, Boolean.valueOf(z2));
    }
}
