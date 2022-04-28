package com.android.systemui.controls.p004ui;

import android.content.Context;
import android.service.controls.Control;

/* renamed from: com.android.systemui.controls.ui.ControlActionCoordinator */
/* compiled from: ControlActionCoordinator.kt */
public interface ControlActionCoordinator {
    void closeDialogs();

    void drag(boolean z);

    void enableActionOnTouch(String str);

    void longPress(ControlViewHolder controlViewHolder);

    void runPendingAction(String str);

    void setActivityContext(Context context);

    void setValue(ControlViewHolder controlViewHolder, String str, float f);

    void toggle(ControlViewHolder controlViewHolder, String str, boolean z);

    void touch(ControlViewHolder controlViewHolder, String str, Control control);
}
