package com.android.systemui.media;

import androidx.fragment.R$styleable;
import com.android.systemui.media.MediaPlayerData;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$thenByDescending$5<T> implements Comparator {
    public final /* synthetic */ Comparator $this_thenByDescending;

    public MediaPlayerData$special$$inlined$thenByDescending$5(MediaPlayerData$special$$inlined$thenByDescending$4 mediaPlayerData$special$$inlined$thenByDescending$4) {
        this.$this_thenByDescending = mediaPlayerData$special$$inlined$thenByDescending$4;
    }

    public final int compare(T t, T t2) {
        int compare = this.$this_thenByDescending.compare(t, t2);
        if (compare != 0) {
            return compare;
        }
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) t2;
        Objects.requireNonNull(mediaSortKey);
        Long valueOf = Long.valueOf(mediaSortKey.updateTime);
        MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) t;
        Objects.requireNonNull(mediaSortKey2);
        return R$styleable.compareValues(valueOf, Long.valueOf(mediaSortKey2.updateTime));
    }
}
