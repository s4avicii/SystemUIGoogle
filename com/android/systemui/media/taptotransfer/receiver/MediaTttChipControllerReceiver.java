package com.android.systemui.media.taptotransfer.receiver;

import android.content.Context;
import android.os.Handler;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.media.taptotransfer.common.MediaTttChipControllerCommon;
import com.android.systemui.media.taptotransfer.common.MediaTttChipState;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: MediaTttChipControllerReceiver.kt */
public final class MediaTttChipControllerReceiver extends MediaTttChipControllerCommon<ChipStateReceiver> {
    public final Handler mainHandler;

    public final void updateChipView(MediaTttChipState mediaTttChipState, ViewGroup viewGroup) {
        mo9152x42993fc1((ChipStateReceiver) mediaTttChipState, viewGroup);
    }

    public MediaTttChipControllerReceiver(CommandQueue commandQueue, Context context, WindowManager windowManager, DelayableExecutor delayableExecutor, Handler handler) {
        super(context, windowManager, delayableExecutor, C1777R.layout.media_ttt_chip_receiver);
        this.mainHandler = handler;
        commandQueue.addCallback((CommandQueue.Callbacks) new MediaTttChipControllerReceiver$commandQueueCallbacks$1(this));
    }
}
