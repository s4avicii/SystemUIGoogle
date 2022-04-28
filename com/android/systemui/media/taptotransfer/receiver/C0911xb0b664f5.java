package com.android.systemui.media.taptotransfer.receiver;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;

/* renamed from: com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver$updateMediaTapToTransferReceiverDisplay$1 */
/* compiled from: MediaTttChipControllerReceiver.kt */
public final class C0911xb0b664f5 implements Icon.OnDrawableLoadedListener {
    public final /* synthetic */ CharSequence $appName;
    public final /* synthetic */ String $packageName;
    public final /* synthetic */ MediaTttChipControllerReceiver this$0;

    public C0911xb0b664f5(MediaTttChipControllerReceiver mediaTttChipControllerReceiver, String str, CharSequence charSequence) {
        this.this$0 = mediaTttChipControllerReceiver;
        this.$packageName = str;
        this.$appName = charSequence;
    }

    public final void onDrawableLoaded(Drawable drawable) {
        this.this$0.displayChip(new ChipStateReceiver(this.$packageName, drawable, this.$appName));
    }
}
