package com.google.android.systemui.columbus.actions;

import com.android.systemui.tuner.TunerService;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.assist.OpaEnabledReceiver;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: LaunchOpa.kt */
public final class LaunchOpa$tunable$1 implements TunerService.Tunable {
    public final /* synthetic */ LaunchOpa this$0;

    public LaunchOpa$tunable$1(LaunchOpa launchOpa) {
        this.this$0 = launchOpa;
    }

    public final void onTuningChanged(String str, String str2) {
        if (Intrinsics.areEqual("assist_gesture_any_assistant", str)) {
            this.this$0.enableForAnyAssistant = Intrinsics.areEqual("1", str2);
            AssistManagerGoogle assistManagerGoogle = this.this$0.assistManager;
            if (assistManagerGoogle != null) {
                OpaEnabledReceiver opaEnabledReceiver = assistManagerGoogle.mOpaEnabledReceiver;
                Objects.requireNonNull(opaEnabledReceiver);
                opaEnabledReceiver.dispatchOpaEnabledState(opaEnabledReceiver.mContext);
            }
        }
    }
}
