package com.android.systemui.media.taptotransfer.common;

import android.view.ViewGroup;
import java.util.Objects;

/* compiled from: MediaTttChipControllerCommon.kt */
public /* synthetic */ class MediaTttChipControllerCommon$displayChip$1 implements Runnable {
    public final /* synthetic */ MediaTttChipControllerCommon<T> $tmp0;

    public MediaTttChipControllerCommon$displayChip$1(MediaTttChipControllerCommon<T> mediaTttChipControllerCommon) {
        this.$tmp0 = mediaTttChipControllerCommon;
    }

    public final void run() {
        MediaTttChipControllerCommon<T> mediaTttChipControllerCommon = this.$tmp0;
        Objects.requireNonNull(mediaTttChipControllerCommon);
        ViewGroup viewGroup = mediaTttChipControllerCommon.chipView;
        if (viewGroup != null) {
            mediaTttChipControllerCommon.windowManager.removeView(viewGroup);
            mediaTttChipControllerCommon.chipView = null;
        }
    }
}
