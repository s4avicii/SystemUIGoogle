package com.android.systemui.media;

import android.app.PendingIntent;
import android.app.smartspace.SmartspaceAction;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import com.android.internal.annotations.VisibleForTesting;
import kotlin.collections.EmptyList;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManagerKt {
    public static final String[] ART_URIS = {"android.media.metadata.ALBUM_ART_URI", "android.media.metadata.ART_URI", "android.media.metadata.DISPLAY_ICON_URI"};
    public static final SmartspaceMediaData EMPTY_SMARTSPACE_MEDIA_DATA;
    public static final MediaData LOADING;

    @VisibleForTesting
    public static /* synthetic */ void getEMPTY_SMARTSPACE_MEDIA_DATA$annotations() {
    }

    static {
        EmptyList emptyList = EmptyList.INSTANCE;
        LOADING = new MediaData(-1, false, 0, (String) null, (Icon) null, (CharSequence) null, (CharSequence) null, (Icon) null, emptyList, emptyList, (MediaButton) null, "INVALID", (MediaSession.Token) null, (PendingIntent) null, (MediaDeviceData) null, true, (Runnable) null, 0, false, (String) null, false, (Boolean) null, false, 0, 16647168);
        EMPTY_SMARTSPACE_MEDIA_DATA = new SmartspaceMediaData("INVALID", false, false, "INVALID", (SmartspaceAction) null, emptyList, (Intent) null, 0, 0);
    }
}
