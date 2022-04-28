package com.android.p012wm.shell.common;

import android.util.Slog;
import com.android.p012wm.shell.common.DisplayController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1837xd1ef1ed0 implements Runnable {
    public final /* synthetic */ DisplayController.DisplayWindowListenerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ C1837xd1ef1ed0(DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl, int i, int i2) {
        this.f$0 = displayWindowListenerImpl;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void run() {
        DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(displayWindowListenerImpl);
        DisplayController displayController = DisplayController.this;
        Objects.requireNonNull(displayController);
        synchronized (displayController.mDisplays) {
            if (displayController.mDisplays.get(i) != null) {
                if (displayController.getDisplay(i) != null) {
                    int size = displayController.mDisplayChangedListeners.size();
                    while (true) {
                        size--;
                        if (size >= 0) {
                            displayController.mDisplayChangedListeners.get(size).onFixedRotationStarted(i2);
                        } else {
                            return;
                        }
                    }
                }
            }
            Slog.w("DisplayController", "Skipping onFixedRotationStarted on unknown display, displayId=" + i);
        }
    }
}
