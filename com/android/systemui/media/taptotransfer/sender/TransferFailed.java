package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import com.android.p012wm.shell.C1777R;

/* compiled from: ChipStateSender.kt */
public final class TransferFailed extends ChipStateSender {
    public final String getChipTextString(Context context) {
        return context.getString(C1777R.string.media_transfer_failed);
    }

    public TransferFailed(String str) {
        super(str);
    }
}
