package com.android.systemui.media.taptotransfer.receiver;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.MediaRoute2Info;
import android.util.Log;
import android.view.ViewGroup;
import com.android.systemui.statusbar.CommandQueue;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaTttChipControllerReceiver.kt */
public final class MediaTttChipControllerReceiver$commandQueueCallbacks$1 implements CommandQueue.Callbacks {
    public final /* synthetic */ MediaTttChipControllerReceiver this$0;

    public MediaTttChipControllerReceiver$commandQueueCallbacks$1(MediaTttChipControllerReceiver mediaTttChipControllerReceiver) {
        this.this$0 = mediaTttChipControllerReceiver;
    }

    public final void updateMediaTapToTransferReceiverDisplay(int i, MediaRoute2Info mediaRoute2Info, Icon icon, CharSequence charSequence) {
        MediaTttChipControllerReceiver mediaTttChipControllerReceiver = this.this$0;
        Objects.requireNonNull(mediaTttChipControllerReceiver);
        if (i == 0) {
            String packageName = mediaRoute2Info.getPackageName();
            if (icon == null) {
                mediaTttChipControllerReceiver.displayChip(new ChipStateReceiver(packageName, (Drawable) null, charSequence));
            } else {
                icon.loadDrawableAsync(mediaTttChipControllerReceiver.context, new C0911xb0b664f5(mediaTttChipControllerReceiver, packageName, charSequence), mediaTttChipControllerReceiver.mainHandler);
            }
        } else if (i != 1) {
            Log.e("MediaTapToTransferRcvr", Intrinsics.stringPlus("Unhandled MediaTransferReceiverState ", Integer.valueOf(i)));
        } else {
            ViewGroup viewGroup = mediaTttChipControllerReceiver.chipView;
            if (viewGroup != null) {
                mediaTttChipControllerReceiver.windowManager.removeView(viewGroup);
                mediaTttChipControllerReceiver.chipView = null;
            }
        }
    }
}
