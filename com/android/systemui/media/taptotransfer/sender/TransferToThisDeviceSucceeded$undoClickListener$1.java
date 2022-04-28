package com.android.systemui.media.taptotransfer.sender;

import android.view.View;
import java.util.Objects;

/* compiled from: ChipStateSender.kt */
public final class TransferToThisDeviceSucceeded$undoClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ MediaTttChipControllerSender $controllerSender;
    public final /* synthetic */ TransferToThisDeviceSucceeded this$0;

    public TransferToThisDeviceSucceeded$undoClickListener$1(TransferToThisDeviceSucceeded transferToThisDeviceSucceeded, MediaTttChipControllerSender mediaTttChipControllerSender) {
        this.this$0 = transferToThisDeviceSucceeded;
        this.$controllerSender = mediaTttChipControllerSender;
    }

    public final void onClick(View view) {
        TransferToThisDeviceSucceeded transferToThisDeviceSucceeded = this.this$0;
        Objects.requireNonNull(transferToThisDeviceSucceeded);
        transferToThisDeviceSucceeded.undoCallback.onUndoTriggered();
        MediaTttChipControllerSender mediaTttChipControllerSender = this.$controllerSender;
        TransferToThisDeviceSucceeded transferToThisDeviceSucceeded2 = this.this$0;
        Objects.requireNonNull(transferToThisDeviceSucceeded2);
        mediaTttChipControllerSender.displayChip(new TransferToReceiverTriggered(transferToThisDeviceSucceeded2.appPackageName, this.this$0.otherDeviceName));
    }
}
