package com.android.p012wm.shell.bubbles;

import android.app.PendingIntent;
import com.android.p012wm.shell.bubbles.Bubbles;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.wm.shell.bubbles.Bubble$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Bubble$$ExternalSyntheticLambda0 implements PendingIntent.CancelListener {
    public final /* synthetic */ Bubble f$0;
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ Bubbles.PendingIntentCanceledListener f$2;

    public /* synthetic */ Bubble$$ExternalSyntheticLambda0(Bubble bubble, Executor executor, Bubbles.PendingIntentCanceledListener pendingIntentCanceledListener) {
        this.f$0 = bubble;
        this.f$1 = executor;
        this.f$2 = pendingIntentCanceledListener;
    }

    public final void onCanceled(PendingIntent pendingIntent) {
        Bubble bubble = this.f$0;
        Executor executor = this.f$1;
        Bubbles.PendingIntentCanceledListener pendingIntentCanceledListener = this.f$2;
        Objects.requireNonNull(bubble);
        PendingIntent pendingIntent2 = bubble.mIntent;
        if (pendingIntent2 != null) {
            pendingIntent2.unregisterCancelListener(bubble.mIntentCancelListener);
        }
        executor.execute(new Bubble$$ExternalSyntheticLambda1(bubble, pendingIntentCanceledListener, 0));
    }
}
