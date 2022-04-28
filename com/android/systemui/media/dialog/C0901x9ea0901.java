package com.android.systemui.media.dialog;

import android.graphics.drawable.Icon;
import android.text.TextUtils;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.MediaOutputBaseAdapter;
import java.util.Objects;

/* renamed from: com.android.systemui.media.dialog.MediaOutputBaseAdapter$MediaDeviceBaseViewHolder$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0901x9ea0901 implements Runnable {
    public final /* synthetic */ MediaOutputBaseAdapter.MediaDeviceBaseViewHolder f$0;
    public final /* synthetic */ MediaDevice f$1;
    public final /* synthetic */ Icon f$2;

    public /* synthetic */ C0901x9ea0901(MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder, MediaDevice mediaDevice, Icon icon) {
        this.f$0 = mediaDeviceBaseViewHolder;
        this.f$1 = mediaDevice;
        this.f$2 = icon;
    }

    public final void run() {
        MediaOutputBaseAdapter.MediaDeviceBaseViewHolder mediaDeviceBaseViewHolder = this.f$0;
        MediaDevice mediaDevice = this.f$1;
        Icon icon = this.f$2;
        Objects.requireNonNull(mediaDeviceBaseViewHolder);
        if (TextUtils.equals(mediaDeviceBaseViewHolder.mDeviceId, mediaDevice.getId())) {
            mediaDeviceBaseViewHolder.mTitleIcon.setImageIcon(icon);
        }
    }
}
