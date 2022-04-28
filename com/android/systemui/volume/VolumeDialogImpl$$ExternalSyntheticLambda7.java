package com.android.systemui.volume;

import android.view.View;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.VolumeDialogImpl;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda7 implements View.OnClickListener {
    public final /* synthetic */ VolumeDialogImpl f$0;
    public final /* synthetic */ VolumeDialogImpl.VolumeRow f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda7(VolumeDialogImpl volumeDialogImpl, VolumeDialogImpl.VolumeRow volumeRow, int i) {
        this.f$0 = volumeDialogImpl;
        this.f$1 = volumeRow;
        this.f$2 = i;
    }

    public final void onClick(View view) {
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        VolumeDialogImpl.VolumeRow volumeRow = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(volumeDialogImpl);
        int i2 = 0;
        boolean z = true;
        Events.writeEvent(7, Integer.valueOf(volumeRow.stream), Integer.valueOf(volumeRow.iconState));
        volumeDialogImpl.mController.setActiveStream(volumeRow.stream);
        if (volumeRow.stream == 2) {
            boolean hasVibrator = volumeDialogImpl.mController.hasVibrator();
            if (volumeDialogImpl.mState.ringerModeInternal != 2) {
                volumeDialogImpl.mController.setRingerMode(2, false);
                if (volumeRow.f88ss.level == 0) {
                    volumeDialogImpl.mController.setStreamVolume(i, 1);
                }
            } else if (hasVibrator) {
                volumeDialogImpl.mController.setRingerMode(1, false);
            } else {
                if (volumeRow.f88ss.level != 0) {
                    z = false;
                }
                VolumeDialogController volumeDialogController = volumeDialogImpl.mController;
                if (z) {
                    i2 = volumeRow.lastAudibleLevel;
                }
                volumeDialogController.setStreamVolume(i, i2);
            }
        } else {
            VolumeDialogController.StreamState streamState = volumeRow.f88ss;
            int i3 = streamState.level;
            int i4 = streamState.levelMin;
            if (i3 == i4) {
                i2 = 1;
            }
            VolumeDialogController volumeDialogController2 = volumeDialogImpl.mController;
            if (i2 != 0) {
                i4 = volumeRow.lastAudibleLevel;
            }
            volumeDialogController2.setStreamVolume(i, i4);
        }
        volumeRow.userAttempt = 0;
    }
}
