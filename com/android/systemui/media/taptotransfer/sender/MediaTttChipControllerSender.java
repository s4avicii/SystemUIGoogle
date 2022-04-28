package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.media.taptotransfer.common.MediaTttChipControllerCommon;
import com.android.systemui.media.taptotransfer.common.MediaTttChipState;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* compiled from: MediaTttChipControllerSender.kt */
public final class MediaTttChipControllerSender extends MediaTttChipControllerCommon<ChipStateSender> {
    public final DelayableExecutor mainExecutor;

    public final void updateChipView(MediaTttChipState mediaTttChipState, ViewGroup viewGroup) {
        int i;
        int i2;
        ChipStateSender chipStateSender = (ChipStateSender) mediaTttChipState;
        mo9152x42993fc1(chipStateSender, viewGroup);
        TextView textView = (TextView) viewGroup.requireViewById(C1777R.C1779id.text);
        textView.setText(chipStateSender.getChipTextString(textView.getContext()));
        View requireViewById = viewGroup.requireViewById(C1777R.C1779id.loading);
        int i3 = 0;
        if (chipStateSender.showLoading()) {
            i = 0;
        } else {
            i = 8;
        }
        requireViewById.setVisibility(i);
        View requireViewById2 = viewGroup.requireViewById(C1777R.C1779id.undo);
        View.OnClickListener undoClickListener = chipStateSender.undoClickListener(this);
        requireViewById2.setOnClickListener(undoClickListener);
        if (undoClickListener != null) {
            i2 = 0;
        } else {
            i2 = 8;
        }
        requireViewById2.setVisibility(i2);
        boolean z = chipStateSender instanceof TransferFailed;
        View requireViewById3 = viewGroup.requireViewById(C1777R.C1779id.failure_icon);
        if (!z) {
            i3 = 8;
        }
        requireViewById3.setVisibility(i3);
    }

    public MediaTttChipControllerSender(CommandQueue commandQueue, Context context, WindowManager windowManager, DelayableExecutor delayableExecutor) {
        super(context, windowManager, delayableExecutor, C1777R.layout.media_ttt_chip);
        this.mainExecutor = delayableExecutor;
        commandQueue.addCallback((CommandQueue.Callbacks) new MediaTttChipControllerSender$commandQueueCallbacks$1(this));
    }
}
