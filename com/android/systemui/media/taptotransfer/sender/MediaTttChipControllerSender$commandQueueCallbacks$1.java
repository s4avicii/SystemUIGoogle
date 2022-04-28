package com.android.systemui.media.taptotransfer.sender;

import android.media.MediaRoute2Info;
import android.util.Log;
import android.view.ViewGroup;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.systemui.media.taptotransfer.common.MediaTttChipState;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.CommandQueue;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaTttChipControllerSender.kt */
public final class MediaTttChipControllerSender$commandQueueCallbacks$1 implements CommandQueue.Callbacks {
    public final /* synthetic */ MediaTttChipControllerSender this$0;

    public MediaTttChipControllerSender$commandQueueCallbacks$1(MediaTttChipControllerSender mediaTttChipControllerSender) {
        this.this$0 = mediaTttChipControllerSender;
    }

    public final void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        MediaTttChipControllerSender mediaTttChipControllerSender = this.this$0;
        Objects.requireNonNull(mediaTttChipControllerSender);
        String packageName = mediaRoute2Info.getPackageName();
        String obj = mediaRoute2Info.getName().toString();
        MediaTttChipState mediaTttChipState = null;
        switch (i) {
            case 0:
                mediaTttChipState = new AlmostCloseToStartCast(packageName, obj);
                break;
            case 1:
                mediaTttChipState = new AlmostCloseToEndCast(packageName, obj);
                break;
            case 2:
                mediaTttChipState = new TransferToReceiverTriggered(packageName, obj);
                break;
            case 3:
                mediaTttChipState = new TransferToThisDeviceTriggered(packageName);
                break;
            case 4:
                mediaTttChipState = new TransferToReceiverSucceeded(packageName, obj, iUndoMediaTransferCallback);
                break;
            case 5:
                mediaTttChipState = new TransferToThisDeviceSucceeded(packageName, obj, iUndoMediaTransferCallback);
                break;
            case FalsingManager.VERSION /*6*/:
            case 7:
                mediaTttChipState = new TransferFailed(packageName);
                break;
            case 8:
                ViewGroup viewGroup = mediaTttChipControllerSender.chipView;
                if (viewGroup != null) {
                    mediaTttChipControllerSender.windowManager.removeView(viewGroup);
                    mediaTttChipControllerSender.chipView = null;
                    break;
                }
                break;
            default:
                Log.e("MediaTapToTransferSender", Intrinsics.stringPlus("Unhandled MediaTransferSenderState ", Integer.valueOf(i)));
                break;
        }
        if (mediaTttChipState != null) {
            mediaTttChipControllerSender.displayChip(mediaTttChipState);
        }
    }
}
