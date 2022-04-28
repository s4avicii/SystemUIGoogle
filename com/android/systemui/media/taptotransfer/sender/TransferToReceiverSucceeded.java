package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.view.View;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.p012wm.shell.C1777R;

/* compiled from: ChipStateSender.kt */
public final class TransferToReceiverSucceeded extends ChipStateSender {
    public final String otherDeviceName;
    public final IUndoMediaTransferCallback undoCallback;

    public final String getChipTextString(Context context) {
        return context.getString(C1777R.string.media_transfer_playing_different_device, new Object[]{this.otherDeviceName});
    }

    public final View.OnClickListener undoClickListener(MediaTttChipControllerSender mediaTttChipControllerSender) {
        if (this.undoCallback == null) {
            return null;
        }
        return new TransferToReceiverSucceeded$undoClickListener$1(this, mediaTttChipControllerSender);
    }

    public TransferToReceiverSucceeded(String str, String str2, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        super(str);
        this.otherDeviceName = str2;
        this.undoCallback = iUndoMediaTransferCallback;
    }
}
