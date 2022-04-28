package com.android.systemui.media;

import java.util.Comparator;

/* compiled from: Comparisons.kt */
public final class MediaPlayerData$special$$inlined$compareByDescending$1<T> implements Comparator {
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003f, code lost:
        if (r4.playbackLocation == 0) goto L_0x0043;
     */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int compare(T r4, T r5) {
        /*
            r3 = this;
            com.android.systemui.media.MediaPlayerData$MediaSortKey r5 = (com.android.systemui.media.MediaPlayerData.MediaSortKey) r5
            java.util.Objects.requireNonNull(r5)
            com.android.systemui.media.MediaData r3 = r5.data
            java.util.Objects.requireNonNull(r3)
            java.lang.Boolean r3 = r3.isPlaying
            java.lang.Boolean r0 = java.lang.Boolean.TRUE
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r0)
            r1 = 1
            r2 = 0
            if (r3 == 0) goto L_0x0021
            com.android.systemui.media.MediaData r3 = r5.data
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.playbackLocation
            if (r3 != 0) goto L_0x0021
            r3 = r1
            goto L_0x0022
        L_0x0021:
            r3 = r2
        L_0x0022:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
            com.android.systemui.media.MediaPlayerData$MediaSortKey r4 = (com.android.systemui.media.MediaPlayerData.MediaSortKey) r4
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.media.MediaData r5 = r4.data
            java.util.Objects.requireNonNull(r5)
            java.lang.Boolean r5 = r5.isPlaying
            boolean r5 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r0)
            if (r5 == 0) goto L_0x0042
            com.android.systemui.media.MediaData r4 = r4.data
            java.util.Objects.requireNonNull(r4)
            int r4 = r4.playbackLocation
            if (r4 != 0) goto L_0x0042
            goto L_0x0043
        L_0x0042:
            r1 = r2
        L_0x0043:
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r1)
            int r3 = androidx.fragment.R$styleable.compareValues(r3, r4)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaPlayerData$special$$inlined$compareByDescending$1.compare(java.lang.Object, java.lang.Object):int");
    }
}
