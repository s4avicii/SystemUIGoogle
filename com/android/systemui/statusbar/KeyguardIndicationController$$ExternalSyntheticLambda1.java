package com.android.systemui.statusbar;

import android.util.Slog;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.DisplayController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardIndicationController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ KeyguardIndicationController$$ExternalSyntheticLambda1(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    public final void run() {
        String str;
        switch (this.$r8$classId) {
            case 0:
                KeyguardIndicationController keyguardIndicationController = (KeyguardIndicationController) this.f$0;
                int i = this.f$1;
                Objects.requireNonNull(keyguardIndicationController);
                if (i == 1) {
                    str = keyguardIndicationController.mContext.getResources().getString(C1777R.string.dock_alignment_slow_charging);
                } else if (i == 2) {
                    str = keyguardIndicationController.mContext.getResources().getString(C1777R.string.dock_alignment_not_charging);
                } else {
                    str = "";
                }
                if (!str.equals(keyguardIndicationController.mAlignmentIndication)) {
                    keyguardIndicationController.mAlignmentIndication = str;
                    keyguardIndicationController.updateIndication(false);
                    return;
                }
                return;
            default:
                DisplayController.DisplayWindowListenerImpl displayWindowListenerImpl = (DisplayController.DisplayWindowListenerImpl) this.f$0;
                int i2 = this.f$1;
                int i3 = DisplayController.DisplayWindowListenerImpl.$r8$clinit;
                Objects.requireNonNull(displayWindowListenerImpl);
                DisplayController displayController = DisplayController.this;
                Objects.requireNonNull(displayController);
                synchronized (displayController.mDisplays) {
                    if (displayController.mDisplays.get(i2) != null) {
                        if (displayController.getDisplay(i2) != null) {
                            int size = displayController.mDisplayChangedListeners.size();
                            while (true) {
                                size--;
                                if (size >= 0) {
                                    displayController.mDisplayChangedListeners.get(size).onFixedRotationFinished();
                                } else {
                                    return;
                                }
                            }
                        }
                    }
                    Slog.w("DisplayController", "Skipping onFixedRotationFinished on unknown display, displayId=" + i2);
                    return;
                }
        }
    }
}
