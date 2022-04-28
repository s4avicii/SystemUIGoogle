package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import com.android.p012wm.shell.C1777R;
import java.util.List;

public final class InfoMediaDevice extends MediaDevice {
    public final boolean isConnected() {
        return true;
    }

    public int getDrawableResId() {
        int type = this.mRouteInfo.getType();
        if (type == 1001) {
            return C1777R.C1778drawable.ic_media_display_device;
        }
        if (type != 2000) {
            return C1777R.C1778drawable.ic_media_speaker_device;
        }
        return C1777R.C1778drawable.ic_media_group_device;
    }

    public int getDrawableResIdByFeature() {
        List features = this.mRouteInfo.getFeatures();
        if (features.contains("android.media.route.feature.REMOTE_GROUP_PLAYBACK")) {
            return C1777R.C1778drawable.ic_media_group_device;
        }
        if (features.contains("android.media.route.feature.REMOTE_VIDEO_PLAYBACK")) {
            return C1777R.C1778drawable.ic_media_display_device;
        }
        return C1777R.C1778drawable.ic_media_speaker_device;
    }

    public final Drawable getIconWithoutBackground() {
        return this.mContext.getDrawable(getDrawableResIdByFeature());
    }

    public final String getId() {
        return this.mRouteInfo.getId();
    }

    public final String getName() {
        return this.mRouteInfo.getName().toString();
    }

    public InfoMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str);
        initDeviceRecord();
    }

    public final Drawable getIcon() {
        return getIconWithoutBackground();
    }
}
