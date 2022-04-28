package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

/* compiled from: ChipStateSender.kt */
public final class TransferToThisDeviceTriggered extends ChipStateSender {
    public final boolean showLoading() {
        return true;
    }

    public final String getChipTextString(Context context) {
        return context.getString(C1777R.string.media_transfer_playing_this_device);
    }

    public TransferToThisDeviceTriggered(String str) {
        super(str);
    }
}
