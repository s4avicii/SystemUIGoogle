package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

/* compiled from: ChipStateSender.kt */
public final class TransferToReceiverTriggered extends ChipStateSender {
    public final String otherDeviceName;

    public final String getChipTextString(Context context) {
        return context.getString(C1777R.string.media_transfer_playing_different_device, new Object[]{this.otherDeviceName});
    }

    public TransferToReceiverTriggered(String str, String str2) {
        super(str);
        this.otherDeviceName = str2;
    }
}
