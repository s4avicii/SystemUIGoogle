package com.android.p012wm.shell.transition;

import com.android.p012wm.shell.transition.Transitions;
import java.util.function.Function;

/* renamed from: com.android.wm.shell.transition.Transitions$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Transitions$$ExternalSyntheticLambda2 implements Function {
    public static final /* synthetic */ Transitions$$ExternalSyntheticLambda2 INSTANCE = new Transitions$$ExternalSyntheticLambda2();

    public final Object apply(Object obj) {
        boolean z = Transitions.ENABLE_SHELL_TRANSITIONS;
        return ((Transitions.ActiveTransition) obj).mToken;
    }
}
