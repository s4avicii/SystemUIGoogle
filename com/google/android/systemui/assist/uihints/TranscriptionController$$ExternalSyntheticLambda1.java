package com.google.android.systemui.assist.uihints;

import com.google.android.systemui.assist.uihints.TranscriptionController;
import java.util.Objects;
import java.util.Optional;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TranscriptionController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ TranscriptionController f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Optional f$2;

    public /* synthetic */ TranscriptionController$$ExternalSyntheticLambda1(TranscriptionController transcriptionController, String str, Optional optional) {
        this.f$0 = transcriptionController;
        this.f$1 = str;
        this.f$2 = optional;
    }

    public final void run() {
        TranscriptionController transcriptionController = this.f$0;
        String str = this.f$1;
        Optional optional = this.f$2;
        Objects.requireNonNull(transcriptionController);
        ((GreetingView) transcriptionController.mViewMap.get(TranscriptionController.State.GREETING)).setGreetingAnimated(str, ((Float) optional.get()).floatValue());
    }
}
