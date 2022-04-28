package com.android.systemui.volume;

import android.view.View;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda5 implements View.OnClickListener {
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda5(VolumeDialogImpl volumeDialogImpl) {
        this.f$0 = volumeDialogImpl;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0028, code lost:
        if (r2 != false) goto L_0x0039;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onClick(android.view.View r6) {
        /*
            r5 = this;
            com.android.systemui.volume.VolumeDialogImpl r5 = r5.f$0
            java.util.Objects.requireNonNull(r5)
            android.view.ContextThemeWrapper r6 = r5.mContext
            java.lang.String r0 = "TouchedRingerToggle"
            r1 = 1
            com.android.systemui.Prefs.putBoolean(r6, r0, r1)
            com.android.systemui.plugins.VolumeDialogController$State r6 = r5.mState
            android.util.SparseArray<com.android.systemui.plugins.VolumeDialogController$StreamState> r6 = r6.states
            r0 = 2
            java.lang.Object r6 = r6.get(r0)
            com.android.systemui.plugins.VolumeDialogController$StreamState r6 = (com.android.systemui.plugins.VolumeDialogController.StreamState) r6
            if (r6 != 0) goto L_0x001b
            goto L_0x003c
        L_0x001b:
            com.android.systemui.plugins.VolumeDialogController r2 = r5.mController
            boolean r2 = r2.hasVibrator()
            com.android.systemui.plugins.VolumeDialogController$State r3 = r5.mState
            int r3 = r3.ringerModeInternal
            r4 = 0
            if (r3 != r0) goto L_0x002b
            if (r2 == 0) goto L_0x002d
            goto L_0x0039
        L_0x002b:
            if (r3 != r1) goto L_0x002f
        L_0x002d:
            r1 = r4
            goto L_0x0039
        L_0x002f:
            int r6 = r6.level
            if (r6 != 0) goto L_0x0038
            com.android.systemui.plugins.VolumeDialogController r6 = r5.mController
            r6.setStreamVolume(r0, r1)
        L_0x0038:
            r1 = r0
        L_0x0039:
            r5.setRingerMode(r1)
        L_0x003c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda5.onClick(android.view.View):void");
    }
}
