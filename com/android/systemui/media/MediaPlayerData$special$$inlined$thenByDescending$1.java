package com.android.systemui.media;

import java.util.Comparator;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$thenByDescending$1<T> implements Comparator {
    public final /* synthetic */ Comparator $this_thenByDescending;

    public MediaPlayerData$special$$inlined$thenByDescending$1(MediaPlayerData$special$$inlined$compareByDescending$1 mediaPlayerData$special$$inlined$compareByDescending$1) {
        this.$this_thenByDescending = mediaPlayerData$special$$inlined$compareByDescending$1;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0041  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int compare(T r4, T r5) {
        /*
            r3 = this;
            java.util.Comparator r3 = r3.$this_thenByDescending
            int r3 = r3.compare(r4, r5)
            if (r3 == 0) goto L_0x0009
            goto L_0x0053
        L_0x0009:
            com.android.systemui.media.MediaPlayerData$MediaSortKey r5 = (com.android.systemui.media.MediaPlayerData.MediaSortKey) r5
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.media.MediaData r3 = r5.data
            java.util.Objects.requireNonNull(r3)
            java.lang.Boolean r3 = r3.isPlaying
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r0)
            r1 = 0
            r2 = 1
            if (r3 == 0) goto L_0x002a
            com.android.systemui.media.MediaData r3 = r5.data
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.playbackLocation
            if (r3 != r2) goto L_0x002a
            r3 = r2
            goto L_0x002b
        L_0x002a:
            r3 = r1
        L_0x002b:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            com.android.systemui.media.MediaPlayerData$MediaSortKey r4 = (com.android.systemui.media.MediaPlayerData.MediaSortKey) r4
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.media.MediaData r5 = r4.data
            java.util.Objects.requireNonNull(r5)
            java.lang.Boolean r5 = r5.isPlaying
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            if (r5 == 0) goto L_0x004b
            com.android.systemui.media.MediaData r4 = r4.data
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.playbackLocation
            if (r4 != r2) goto L_0x004b
            r1 = r2
        L_0x004b:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r1)
            int r3 = androidx.fragment.R$styleable.compareValues(r3, r4)
        L_0x0053:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaPlayerData$special$$inlined$thenByDescending$1.compare(java.lang.Object, java.lang.Object):int");
    }
}
