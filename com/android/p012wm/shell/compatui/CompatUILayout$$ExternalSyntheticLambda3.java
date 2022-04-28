package com.android.p012wm.shell.compatui;

import android.util.Log;
import android.view.View;
import android.window.TaskAppearedInfo;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellTaskOrganizer;
import com.android.p012wm.shell.compatui.CompatUIController;
import java.util.Objects;

/* renamed from: com.android.wm.shell.compatui.CompatUILayout$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class CompatUILayout$$ExternalSyntheticLambda3 implements View.OnClickListener {
    public final /* synthetic */ CompatUILayout f$0;

    public /* synthetic */ CompatUILayout$$ExternalSyntheticLambda3(CompatUILayout compatUILayout) {
        this.f$0 = compatUILayout;
    }

    public final void onClick(View view) {
        TaskAppearedInfo taskAppearedInfo;
        CompatUILayout compatUILayout = this.f$0;
        int i = CompatUILayout.$r8$clinit;
        Objects.requireNonNull(compatUILayout);
        CompatUIWindowManager compatUIWindowManager = compatUILayout.mWindowManager;
        Objects.requireNonNull(compatUIWindowManager);
        if (!compatUIWindowManager.shouldShowCameraControl()) {
            Class<CompatUIWindowManager> cls = CompatUIWindowManager.class;
            Log.w("CompatUIWindowManager", "Camera compat shouldn't receive clicks in the hidden state.");
            return;
        }
        compatUIWindowManager.mCameraCompatControlState = 3;
        CompatUIController.CompatUICallback compatUICallback = compatUIWindowManager.mCallback;
        int i2 = compatUIWindowManager.mTaskId;
        ShellTaskOrganizer shellTaskOrganizer = (ShellTaskOrganizer) compatUICallback;
        Objects.requireNonNull(shellTaskOrganizer);
        synchronized (shellTaskOrganizer.mLock) {
            taskAppearedInfo = shellTaskOrganizer.mTasks.get(i2);
        }
        if (taskAppearedInfo != null) {
            shellTaskOrganizer.updateCameraCompatControlState(taskAppearedInfo.getTaskInfo().token, 3);
        }
        CompatUILayout compatUILayout2 = compatUIWindowManager.mLayout;
        Objects.requireNonNull(compatUILayout2);
        compatUILayout2.setViewVisibility(C1777R.C1779id.camera_compat_control, false);
        compatUILayout2.setViewVisibility(C1777R.C1779id.camera_compat_hint, false);
    }
}
