package com.android.p012wm.shell.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.Slog;
import android.view.Display;
import com.android.p012wm.shell.common.DisplayController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.common.DisplayController$DisplayWindowListenerImpl$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C1838xd1ef1ed1 implements Runnable {
    public final /* synthetic */ DisplayController.DisplayWindowListenerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Configuration f$2;

    public /* synthetic */ C1838xd1ef1ed1(DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl, int i, Configuration configuration) {
        this.f$0 = displayWindowListenerImpl;
        this.f$1 = i;
        this.f$2 = configuration;
    }

    public final void run() {
        Context context;
        DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = this.f$0;
        int i = this.f$1;
        Configuration configuration = this.f$2;
        Objects.requireNonNull(displayWindowListenerImpl);
        DisplayController displayController = DisplayController.this;
        Objects.requireNonNull(displayController);
        synchronized (displayController.mDisplays) {
            DisplayController.DisplayRecord displayRecord = displayController.mDisplays.get(i);
            if (displayRecord == null) {
                Slog.w("DisplayController", "Skipping Display Configuration change on non-added display.");
                return;
            }
            Display display = displayController.getDisplay(i);
            if (display == null) {
                Slog.w("DisplayController", "Skipping Display Configuration change on invalid display. It may have been removed.");
                return;
            }
            if (i == 0) {
                context = displayController.mContext;
            } else {
                context = displayController.mContext.createDisplayContext(display);
            }
            Context createConfigurationContext = context.createConfigurationContext(configuration);
            DisplayLayout displayLayout = new DisplayLayout(createConfigurationContext, display);
            displayRecord.mContext = createConfigurationContext;
            displayRecord.mDisplayLayout = displayLayout;
            Resources resources = createConfigurationContext.getResources();
            displayLayout.mInsetsState = displayRecord.mInsetsState;
            displayLayout.recalcInsets(resources);
            for (int i2 = 0; i2 < displayController.mDisplayChangedListeners.size(); i2++) {
                displayController.mDisplayChangedListeners.get(i2).onDisplayConfigurationChanged(i, configuration);
            }
        }
    }
}
