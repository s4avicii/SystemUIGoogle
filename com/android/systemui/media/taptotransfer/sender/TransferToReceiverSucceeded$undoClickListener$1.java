package com.android.systemui.media.taptotransfer.sender;

import android.view.View;
import java.util.Objects;

/* compiled from: ChipStateSender.kt */
public final class TransferToReceiverSucceeded$undoClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ MediaTttChipControllerSender $controllerSender;
    public final /* synthetic */ TransferToReceiverSucceeded this$0;

    public TransferToReceiverSucceeded$undoClickListener$1(TransferToReceiverSucceeded transferToReceiverSucceeded, MediaTttChipControllerSender mediaTttChipControllerSender) {
        this.this$0 = transferToReceiverSucceeded;
        this.$controllerSender = mediaTttChipControllerSender;
    }

    public final void onClick(View view) {
        TransferToReceiverSucceeded transferToReceiverSucceeded = this.this$0;
        Objects.requireNonNull(transferToReceiverSucceeded);
        transferToReceiverSucceeded.undoCallback.onUndoTriggered();
        MediaTttChipControllerSender mediaTttChipControllerSender = this.$controllerSender;
        TransferToReceiverSucceeded transferToReceiverSucceeded2 = this.this$0;
        Objects.requireNonNull(transferToReceiverSucceeded2);
        mediaTttChipControllerSender.displayChip(new TransferToThisDeviceTriggered(transferToReceiverSucceeded2.appPackageName));
    }
}
