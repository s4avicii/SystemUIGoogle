package com.android.systemui.media;

import android.app.PendingIntent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import kotlin.collections.EmptyList;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaCarouselController.kt */
public final class MediaPlayerData {
    public static final MediaData EMPTY;
    public static final MediaPlayerData INSTANCE = new MediaPlayerData();
    public static final MediaPlayerData$special$$inlined$thenByDescending$6 comparator;
    public static final LinkedHashMap mediaData = new LinkedHashMap();
    public static final TreeMap<MediaSortKey, MediaControlPanel> mediaPlayers;
    public static boolean shouldPrioritizeSs;
    public static SmartspaceMediaData smartspaceMediaData;

    /* compiled from: MediaCarouselController.kt */
    public static final class MediaSortKey {
        public final MediaData data;
        public final boolean isSsMediaRec;
        public final long updateTime;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaSortKey)) {
                return false;
            }
            MediaSortKey mediaSortKey = (MediaSortKey) obj;
            return this.isSsMediaRec == mediaSortKey.isSsMediaRec && Intrinsics.areEqual(this.data, mediaSortKey.data) && this.updateTime == mediaSortKey.updateTime;
        }

        public final int hashCode() {
            boolean z = this.isSsMediaRec;
            if (z) {
                z = true;
            }
            int hashCode = this.data.hashCode();
            return Long.hashCode(this.updateTime) + ((hashCode + ((z ? 1 : 0) * true)) * 31);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("MediaSortKey(isSsMediaRec=");
            m.append(this.isSsMediaRec);
            m.append(", data=");
            m.append(this.data);
            m.append(", updateTime=");
            m.append(this.updateTime);
            m.append(')');
            return m.toString();
        }

        public MediaSortKey(boolean z, MediaData mediaData, long j) {
            this.isSsMediaRec = z;
            this.data = mediaData;
            this.updateTime = j;
        }
    }

    static {
        EmptyList emptyList = EmptyList.INSTANCE;
        EMPTY = new MediaData(-1, false, 0, (String) null, (Icon) null, (CharSequence) null, (CharSequence) null, (Icon) null, emptyList, emptyList, (MediaButton) null, "INVALID", (MediaSession.Token) null, (PendingIntent) null, (MediaDeviceData) null, true, (Runnable) null, 0, false, (String) null, false, (Boolean) null, false, 0, 16647168);
        MediaPlayerData$special$$inlined$thenByDescending$6 mediaPlayerData$special$$inlined$thenByDescending$6 = new MediaPlayerData$special$$inlined$thenByDescending$6(new MediaPlayerData$special$$inlined$thenByDescending$5(new MediaPlayerData$special$$inlined$thenByDescending$4(new MediaPlayerData$special$$inlined$thenByDescending$3(new MediaPlayerData$special$$inlined$thenByDescending$2(new MediaPlayerData$special$$inlined$thenByDescending$1(new MediaPlayerData$special$$inlined$compareByDescending$1()))))));
        comparator = mediaPlayerData$special$$inlined$thenByDescending$6;
        mediaPlayers = new TreeMap<>(mediaPlayerData$special$$inlined$thenByDescending$6);
    }

    public static int firstActiveMediaIndex() {
        int i = 0;
        for (T next : mediaPlayers.entrySet()) {
            int i2 = i + 1;
            if (i >= 0) {
                Map.Entry entry = (Map.Entry) next;
                MediaSortKey mediaSortKey = (MediaSortKey) entry.getKey();
                Objects.requireNonNull(mediaSortKey);
                if (!mediaSortKey.isSsMediaRec) {
                    MediaSortKey mediaSortKey2 = (MediaSortKey) entry.getKey();
                    Objects.requireNonNull(mediaSortKey2);
                    MediaData mediaData2 = mediaSortKey2.data;
                    Objects.requireNonNull(mediaData2);
                    if (mediaData2.active) {
                        return i;
                    }
                }
                i = i2;
            } else {
                SetsKt__SetsKt.throwIndexOverflow();
                throw null;
            }
        }
        return -1;
    }

    public static MediaControlPanel getMediaPlayer(String str) {
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.get(str);
        if (mediaSortKey == null) {
            return null;
        }
        return mediaPlayers.get(mediaSortKey);
    }

    public static int getMediaPlayerIndex(String str) {
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.get(str);
        int i = 0;
        for (T next : mediaPlayers.entrySet()) {
            int i2 = i + 1;
            if (i < 0) {
                SetsKt__SetsKt.throwIndexOverflow();
                throw null;
            } else if (Intrinsics.areEqual(((Map.Entry) next).getKey(), mediaSortKey)) {
                return i;
            } else {
                i = i2;
            }
        }
        return -1;
    }

    public static Collection players() {
        return mediaPlayers.values();
    }

    public static MediaControlPanel removeMediaPlayer(String str) {
        MediaSortKey mediaSortKey = (MediaSortKey) mediaData.remove(str);
        if (mediaSortKey == null) {
            return null;
        }
        if (mediaSortKey.isSsMediaRec) {
            smartspaceMediaData = null;
        }
        return mediaPlayers.remove(mediaSortKey);
    }

    public final void clear() {
        mediaData.clear();
        mediaPlayers.clear();
    }

    private MediaPlayerData() {
    }
}
