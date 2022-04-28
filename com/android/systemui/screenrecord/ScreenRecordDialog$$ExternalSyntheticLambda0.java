package com.android.systemui.screenrecord;

import android.view.View;
import android.widget.AdapterView;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScreenRecordDialog$$ExternalSyntheticLambda0 implements AdapterView.OnItemClickListener {
    public final /* synthetic */ ScreenRecordDialog f$0;

    public /* synthetic */ ScreenRecordDialog$$ExternalSyntheticLambda0(ScreenRecordDialog screenRecordDialog) {
        this.f$0 = screenRecordDialog;
    }

    public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
        ScreenRecordDialog screenRecordDialog = this.f$0;
        Objects.requireNonNull(screenRecordDialog);
        screenRecordDialog.mAudioSwitch.setChecked(true);
    }
}
