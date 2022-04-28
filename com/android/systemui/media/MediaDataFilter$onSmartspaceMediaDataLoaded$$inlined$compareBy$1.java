package com.android.systemui.media;

import androidx.fragment.R$styleable;
import java.util.Comparator;

/* compiled from: Comparisons.kt */
public final class MediaDataFilter$onSmartspaceMediaDataLoaded$$inlined$compareBy$1<T> implements Comparator {
    public final /* synthetic */ MediaDataFilter this$0;

    public MediaDataFilter$onSmartspaceMediaDataLoaded$$inlined$compareBy$1(MediaDataFilter mediaDataFilter) {
        this.this$0 = mediaDataFilter;
    }

    public final int compare(T t, T t2) {
        int i;
        int i2;
        MediaData mediaData = this.this$0.userEntries.get((String) t);
        if (mediaData == null) {
            i = -1;
        } else {
            i = Long.valueOf(mediaData.lastActive);
        }
        MediaData mediaData2 = this.this$0.userEntries.get((String) t2);
        if (mediaData2 == null) {
            i2 = -1;
        } else {
            i2 = Long.valueOf(mediaData2.lastActive);
        }
        return R$styleable.compareValues(i, i2);
    }
}
