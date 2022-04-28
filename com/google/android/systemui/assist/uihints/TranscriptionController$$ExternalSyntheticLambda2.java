package com.google.android.systemui.assist.uihints;

import android.os.Bundle;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TranscriptionController$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ TranscriptionController f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ TranscriptionController$$ExternalSyntheticLambda2(TranscriptionController transcriptionController, ArrayList arrayList) {
        this.f$0 = transcriptionController;
        this.f$1 = arrayList;
    }

    public final void run() {
        TranscriptionController transcriptionController = this.f$0;
        List<Bundle> list = this.f$1;
        Objects.requireNonNull(transcriptionController);
        ChipsContainer chipsContainer = (ChipsContainer) transcriptionController.mViewMap.get(TranscriptionController.State.CHIPS);
        Objects.requireNonNull(chipsContainer);
        chipsContainer.mChips = list;
        chipsContainer.setChipsInternal();
        chipsContainer.setVisibility(0);
    }
}
