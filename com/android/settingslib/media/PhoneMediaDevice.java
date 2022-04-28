package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.media.DeviceIconUtil;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.p005qs.C0961QS;
import java.util.Objects;

public final class PhoneMediaDevice extends MediaDevice {
    public final DeviceIconUtil mDeviceIconUtil = new DeviceIconUtil();

    public final boolean isConnected() {
        return true;
    }

    public int getDrawableResId() {
        DeviceIconUtil deviceIconUtil = this.mDeviceIconUtil;
        int type = this.mRouteInfo.getType();
        Objects.requireNonNull(deviceIconUtil);
        if (deviceIconUtil.mMediaRouteTypeToIconMap.containsKey(Integer.valueOf(type))) {
            return ((DeviceIconUtil.Device) deviceIconUtil.mMediaRouteTypeToIconMap.get(Integer.valueOf(type))).mIconDrawableRes;
        }
        return C1777R.C1778drawable.ic_smartphone;
    }

    public final Drawable getIconWithoutBackground() {
        return this.mContext.getDrawable(getDrawableResId());
    }

    public final String getId() {
        int type = this.mRouteInfo.getType();
        if (type == 3 || type == 4) {
            return "wired_headset_media_device_id";
        }
        if (!(type == 9 || type == 22)) {
            switch (type) {
                case QSTileImpl.C1034H.STALE /*11*/:
                case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
                case C0961QS.VERSION /*13*/:
                    break;
                default:
                    return "phone_media_device_id";
            }
        }
        return "usb_headset_media_device_id";
    }

    public final String getName() {
        CharSequence charSequence;
        int type = this.mRouteInfo.getType();
        if (!(type == 3 || type == 4)) {
            if (type != 9) {
                if (type != 22) {
                    switch (type) {
                        case QSTileImpl.C1034H.STALE /*11*/:
                        case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
                            break;
                        case C0961QS.VERSION /*13*/:
                            break;
                        default:
                            charSequence = this.mContext.getString(C1777R.string.media_transfer_this_device_name);
                            break;
                    }
                }
            }
            charSequence = this.mRouteInfo.getName();
            return charSequence.toString();
        }
        charSequence = this.mContext.getString(C1777R.string.media_transfer_wired_usb_device_name);
        return charSequence.toString();
    }

    public PhoneMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str);
        initDeviceRecord();
    }

    public final Drawable getIcon() {
        return getIconWithoutBackground();
    }
}
