package com.android.systemui.media;

import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/* compiled from: MediaResumeListener.kt */
public final class MediaResumeListener$setManager$1 implements TunerService.Tunable {
    public final /* synthetic */ MediaResumeListener this$0;

    public MediaResumeListener$setManager$1(MediaResumeListener mediaResumeListener) {
        this.this$0 = mediaResumeListener;
    }

    public final void onTuningChanged(String str, String str2) {
        MediaResumeListener mediaResumeListener = this.this$0;
        mediaResumeListener.useMediaResumption = Utils.useMediaResumption(mediaResumeListener.context);
        MediaResumeListener mediaResumeListener2 = this.this$0;
        MediaDataManager mediaDataManager = mediaResumeListener2.mediaDataManager;
        if (mediaDataManager == null) {
            mediaDataManager = null;
        }
        boolean z = mediaResumeListener2.useMediaResumption;
        Objects.requireNonNull(mediaDataManager);
        if (mediaDataManager.useMediaResumption != z) {
            mediaDataManager.useMediaResumption = z;
            if (!z) {
                LinkedHashMap<String, MediaData> linkedHashMap = mediaDataManager.mediaEntries;
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                for (Map.Entry next : linkedHashMap.entrySet()) {
                    MediaData mediaData = (MediaData) next.getValue();
                    Objects.requireNonNull(mediaData);
                    if (!mediaData.active) {
                        linkedHashMap2.put(next.getKey(), next.getValue());
                    }
                }
                for (Map.Entry entry : linkedHashMap2.entrySet()) {
                    mediaDataManager.mediaEntries.remove(entry.getKey());
                    mediaDataManager.notifyMediaDataRemoved((String) entry.getKey());
                }
            }
        }
    }
}
