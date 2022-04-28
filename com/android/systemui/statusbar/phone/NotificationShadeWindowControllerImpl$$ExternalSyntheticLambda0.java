package com.android.systemui.statusbar.phone;

import java.lang.ref.WeakReference;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 implements Function {
    public static final /* synthetic */ NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0 INSTANCE = new NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda0();

    public final Object apply(Object obj) {
        return (StatusBarWindowCallback) ((WeakReference) obj).get();
    }
}
