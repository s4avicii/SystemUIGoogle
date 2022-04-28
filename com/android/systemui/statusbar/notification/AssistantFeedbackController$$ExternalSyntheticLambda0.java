package com.android.systemui.statusbar.notification;

import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AssistantFeedbackController$$ExternalSyntheticLambda0 implements Executor {
    public final /* synthetic */ AssistantFeedbackController f$0;

    public /* synthetic */ AssistantFeedbackController$$ExternalSyntheticLambda0(AssistantFeedbackController assistantFeedbackController) {
        this.f$0 = assistantFeedbackController;
    }

    public final void execute(Runnable runnable) {
        AssistantFeedbackController assistantFeedbackController = this.f$0;
        Objects.requireNonNull(assistantFeedbackController);
        assistantFeedbackController.mHandler.post(runnable);
    }
}
