package com.android.p012wm.shell.splitscreen;

import android.view.RemoteAnimationTarget;
import java.util.Comparator;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$$ExternalSyntheticLambda0 implements Comparator {
    public static final /* synthetic */ SplitScreenController$$ExternalSyntheticLambda0 INSTANCE = new SplitScreenController$$ExternalSyntheticLambda0();

    public final int compare(Object obj, Object obj2) {
        return ((RemoteAnimationTarget) obj).prefixOrderIndex - ((RemoteAnimationTarget) obj2).prefixOrderIndex;
    }
}
