package com.android.p012wm.shell.common.magnetictarget;

import com.android.p012wm.shell.common.magnetictarget.MagnetizedObject;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.wm.shell.common.magnetictarget.MagnetizedObject$maybeConsumeMotionEvent$1 */
/* compiled from: MagnetizedObject.kt */
public final class MagnetizedObject$maybeConsumeMotionEvent$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ MagnetizedObject.MagneticTarget $flungToTarget;
    public final /* synthetic */ MagnetizedObject<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MagnetizedObject$maybeConsumeMotionEvent$1(MagnetizedObject<T> magnetizedObject, MagnetizedObject.MagneticTarget magneticTarget) {
        super(0);
        this.this$0 = magnetizedObject;
        this.$flungToTarget = magneticTarget;
    }

    public final Object invoke() {
        this.this$0.getMagnetListener().onReleasedInTarget();
        MagnetizedObject<T> magnetizedObject = this.this$0;
        magnetizedObject.targetObjectIsStuckTo = null;
        magnetizedObject.vibrateIfEnabled(5);
        return Unit.INSTANCE;
    }
}
