package com.android.systemui.media;

import androidx.fragment.R$styleable;
import com.android.systemui.media.MediaPlayerData;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$thenByDescending$4<T> implements Comparator {
    public final /* synthetic */ Comparator $this_thenByDescending;

    public MediaPlayerData$special$$inlined$thenByDescending$4(MediaPlayerData$special$$inlined$thenByDescending$3 mediaPlayerData$special$$inlined$thenByDescending$3) {
        this.$this_thenByDescending = mediaPlayerData$special$$inlined$thenByDescending$3;
    }

    public final int compare(T t, T t2) {
        boolean z;
        int compare = this.$this_thenByDescending.compare(t, t2);
        if (compare != 0) {
            return compare;
        }
        MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) t2;
        Objects.requireNonNull(mediaSortKey);
        MediaData mediaData = mediaSortKey.data;
        Objects.requireNonNull(mediaData);
        boolean z2 = true;
        if (mediaData.playbackLocation != 2) {
            z = true;
        } else {
            z = false;
        }
        Boolean valueOf = Boolean.valueOf(z);
        MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) t;
        Objects.requireNonNull(mediaSortKey2);
        MediaData mediaData2 = mediaSortKey2.data;
        Objects.requireNonNull(mediaData2);
        if (mediaData2.playbackLocation == 2) {
            z2 = false;
        }
        return R$styleable.compareValues(valueOf, Boolean.valueOf(z2));
    }
}
