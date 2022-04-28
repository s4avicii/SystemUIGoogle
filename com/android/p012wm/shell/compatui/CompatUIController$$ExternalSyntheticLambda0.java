package com.android.p012wm.shell.compatui;

import android.app.ActivityManager;
import com.android.systemui.shared.rotation.RotationButtonController;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUIController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ CompatUIController$$ExternalSyntheticLambda0(Object obj, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = obj;
        this.f$1 = i;
    }

    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                CompatUIController compatUIController = (CompatUIController) this.f$0;
                int i = this.f$1;
                Objects.requireNonNull(compatUIController);
                ((CompatUIWindowManagerAbstract) obj).updateVisibility(compatUIController.showOnDisplay(i));
                return;
            default:
                RotationButtonController.TaskStackListenerImpl taskStackListenerImpl = (RotationButtonController.TaskStackListenerImpl) this.f$0;
                int i2 = this.f$1;
                Objects.requireNonNull(taskStackListenerImpl);
                if (((ActivityManager.RunningTaskInfo) obj).id == i2) {
                    RotationButtonController rotationButtonController = RotationButtonController.this;
                    Objects.requireNonNull(rotationButtonController);
                    rotationButtonController.setRotateSuggestionButtonState(false, false);
                    return;
                }
                return;
        }
    }
}
