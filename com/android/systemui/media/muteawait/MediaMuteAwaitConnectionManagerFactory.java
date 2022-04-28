package com.android.systemui.media.muteawait;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;
import com.android.systemui.media.MediaFlags;
import java.util.concurrent.Executor;

/* compiled from: MediaMuteAwaitConnectionManagerFactory.kt */
public final class MediaMuteAwaitConnectionManagerFactory {
    public final Context context;
    public final DeviceIconUtil deviceIconUtil = new DeviceIconUtil();
    public final Executor mainExecutor;
    public final MediaFlags mediaFlags;

    public MediaMuteAwaitConnectionManagerFactory(MediaFlags mediaFlags2, Context context2, Executor executor) {
        this.mediaFlags = mediaFlags2;
        this.context = context2;
        this.mainExecutor = executor;
    }
}
