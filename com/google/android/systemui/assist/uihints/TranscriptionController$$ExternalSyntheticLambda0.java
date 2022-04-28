package com.google.android.systemui.assist.uihints;

import com.google.common.base.Function;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TranscriptionController$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ TranscriptionController f$0;

    public /* synthetic */ TranscriptionController$$ExternalSyntheticLambda0(TranscriptionController transcriptionController) {
        this.f$0 = transcriptionController;
    }

    public final Object apply(Object obj) {
        TranscriptionController transcriptionController = this.f$0;
        Void voidR = (Void) obj;
        Objects.requireNonNull(transcriptionController);
        transcriptionController.mCurrentState = transcriptionController.mQueuedState;
        Runnable runnable = transcriptionController.mQueuedCompletion;
        if (runnable == null) {
            return null;
        }
        runnable.run();
        return null;
    }
}
