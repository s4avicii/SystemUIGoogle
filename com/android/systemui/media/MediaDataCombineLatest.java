package com.android.systemui.media;

import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.MediaDeviceManager;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Pair;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaDataCombineLatest.kt */
public final class MediaDataCombineLatest implements MediaDataManager.Listener, MediaDeviceManager.Listener {
    public final LinkedHashMap entries = new LinkedHashMap();
    public final LinkedHashSet listeners = new LinkedHashSet();

    public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
        MediaDeviceData mediaDeviceData = null;
        if (str2 == null || Intrinsics.areEqual(str2, str) || !this.entries.containsKey(str2)) {
            LinkedHashMap linkedHashMap = this.entries;
            Pair pair = (Pair) linkedHashMap.get(str);
            if (pair != null) {
                mediaDeviceData = (MediaDeviceData) pair.getSecond();
            }
            linkedHashMap.put(str, new Pair(mediaData, mediaDeviceData));
            update(str, str);
            return;
        }
        LinkedHashMap linkedHashMap2 = this.entries;
        Pair pair2 = (Pair) linkedHashMap2.remove(str2);
        if (pair2 != null) {
            mediaDeviceData = (MediaDeviceData) pair2.getSecond();
        }
        linkedHashMap2.put(str, new Pair(mediaData, mediaDeviceData));
        update(str, str2);
    }

    public final void onMediaDeviceChanged(String str, String str2, MediaDeviceData mediaDeviceData) {
        MediaData mediaData = null;
        if (str2 == null || Intrinsics.areEqual(str2, str) || !this.entries.containsKey(str2)) {
            LinkedHashMap linkedHashMap = this.entries;
            Pair pair = (Pair) linkedHashMap.get(str);
            if (pair != null) {
                mediaData = (MediaData) pair.getFirst();
            }
            linkedHashMap.put(str, new Pair(mediaData, mediaDeviceData));
            update(str, str);
            return;
        }
        LinkedHashMap linkedHashMap2 = this.entries;
        Pair pair2 = (Pair) linkedHashMap2.remove(str2);
        if (pair2 != null) {
            mediaData = (MediaData) pair2.getFirst();
        }
        linkedHashMap2.put(str, new Pair(mediaData, mediaDeviceData));
        update(str, str2);
    }

    public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
        for (MediaDataManager.Listener onSmartspaceMediaDataLoaded$default : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
            onSmartspaceMediaDataLoaded$default.onSmartspaceMediaDataLoaded(str, smartspaceMediaData, false, false);
        }
    }

    public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
        for (MediaDataManager.Listener onSmartspaceMediaDataRemoved : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
            onSmartspaceMediaDataRemoved.onSmartspaceMediaDataRemoved(str, z);
        }
    }

    public final void remove(String str) {
        if (((Pair) this.entries.remove(str)) != null) {
            for (MediaDataManager.Listener onMediaDataRemoved : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
                onMediaDataRemoved.onMediaDataRemoved(str);
            }
        }
    }

    public final void update(String str, String str2) {
        Pair pair = (Pair) this.entries.get(str);
        if (pair == null) {
            pair = new Pair(null, null);
        }
        MediaData mediaData = (MediaData) pair.component1();
        MediaDeviceData mediaDeviceData = (MediaDeviceData) pair.component2();
        if (mediaData != null && mediaDeviceData != null) {
            MediaData copy$default = MediaData.copy$default(mediaData, (List) null, (List) null, (String) null, mediaDeviceData, false, (MediaResumeListener$getResumeAction$1) null, false, false, (Boolean) null, false, 16760831);
            for (MediaDataManager.Listener onMediaDataLoaded$default : CollectionsKt___CollectionsKt.toSet(this.listeners)) {
                MediaDataManager.Listener.DefaultImpls.onMediaDataLoaded$default(onMediaDataLoaded$default, str, str2, copy$default, false, 0, 24);
            }
        }
    }

    public final void onKeyRemoved(String str) {
        remove(str);
    }

    public final void onMediaDataRemoved(String str) {
        remove(str);
    }
}
