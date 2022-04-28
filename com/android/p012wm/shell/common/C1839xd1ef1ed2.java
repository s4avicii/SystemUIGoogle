package com.android.p012wm.shell.common;

import android.util.Slog;
import com.android.p012wm.shell.common.DisplayController;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1839xd1ef1ed2 implements Runnable {
    public final /* synthetic */ DisplayController.DisplayWindowListenerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ List f$3;

    public /* synthetic */ C1839xd1ef1ed2(DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl, int i, List list, List list2) {
        this.f$0 = displayWindowListenerImpl;
        this.f$1 = i;
        this.f$2 = list;
        this.f$3 = list2;
    }

    public final void run() {
        DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = this.f$0;
        int i = this.f$1;
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
                            Objects.requireNonNull(displayController.mDisplayChangedListeners.get(size));
                        } else {
                            return;
                        }
                    }
                }
            }
            Slog.w("DisplayController", "Skipping onKeepClearAreasChanged on unknown display, displayId=" + i);
        }
    }
}
