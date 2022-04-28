package com.android.p012wm.shell.common;

import com.android.p012wm.shell.common.DisplayController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1836xd1ef1ecf implements Runnable {
    public final /* synthetic */ DisplayController.DisplayWindowListenerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ C1836xd1ef1ecf(DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl, int i) {
        this.f$0 = displayWindowListenerImpl;
        this.f$1 = i;
    }

    public final void run() {
        DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(displayWindowListenerImpl);
        DisplayController displayController = DisplayController.this;
        Objects.requireNonNull(displayController);
        synchronized (displayController.mDisplays) {
            if (displayController.mDisplays.get(i) != null) {
                int size = displayController.mDisplayChangedListeners.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        displayController.mDisplayChangedListeners.get(size).onDisplayRemoved(i);
                    } else {
                        displayController.mDisplays.remove(i);
                        return;
                    }
                }
            }
        }
    }
}
