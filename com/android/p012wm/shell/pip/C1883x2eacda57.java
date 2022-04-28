package com.android.p012wm.shell.pip;

import com.android.p012wm.shell.pip.PinnedStackListenerForwarder;
import java.util.Iterator;
import java.util.Objects;

/* renamed from: com.android.wm.shell.pip.PinnedStackListenerForwarder$PinnedTaskListenerImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1883x2eacda57 implements Runnable {
    public final /* synthetic */ PinnedStackListenerForwarder.PinnedTaskListenerImpl f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ C1883x2eacda57(PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl, boolean z) {
        this.f$0 = pinnedTaskListenerImpl;
        this.f$1 = z;
    }

    public final void run() {
        PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(pinnedTaskListenerImpl);
        PinnedStackListenerForwarder pinnedStackListenerForwarder = PinnedStackListenerForwarder.this;
        Objects.requireNonNull(pinnedStackListenerForwarder);
        Iterator<PinnedStackListenerForwarder.PinnedTaskListener> it = pinnedStackListenerForwarder.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onMovementBoundsChanged(z);
        }
    }
}
