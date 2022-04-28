package com.android.systemui.media.dialog;

import android.widget.CompoundButton;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.dialog.MediaOutputAdapter;
import java.util.Objects;

/* renamed from: com.android.systemui.media.dialog.MediaOutputAdapter$MediaDeviceViewHolder$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0897xea444da2 implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ MediaOutputAdapter.MediaDeviceViewHolder f$0;
    public final /* synthetic */ MediaDevice f$1;

    public /* synthetic */ C0897xea444da2(MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder, MediaDevice mediaDevice) {
        this.f$0 = mediaDeviceViewHolder;
        this.f$1 = mediaDevice;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        MediaOutputAdapter.MediaDeviceViewHolder mediaDeviceViewHolder = this.f$0;
        MediaDevice mediaDevice = this.f$1;
        Objects.requireNonNull(mediaDeviceViewHolder);
        mediaDeviceViewHolder.onCheckBoxClicked(true, mediaDevice);
    }
}
