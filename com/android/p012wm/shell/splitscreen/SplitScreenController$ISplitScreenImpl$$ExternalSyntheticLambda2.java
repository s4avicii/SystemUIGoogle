package com.android.p012wm.shell.splitscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.RemoteAnimationAdapter;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int f$0;
    public final /* synthetic */ Bundle f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Bundle f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ float f$5;
    public final /* synthetic */ RemoteAnimationAdapter f$6;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda2(int i, Bundle bundle, int i2, Bundle bundle2, int i3, float f, RemoteAnimationAdapter remoteAnimationAdapter) {
        this.f$0 = i;
        this.f$1 = bundle;
        this.f$2 = i2;
        this.f$3 = bundle2;
        this.f$4 = i3;
        this.f$5 = f;
        this.f$6 = remoteAnimationAdapter;
    }

    public final void accept(Object obj) {
        int i = this.f$0;
        Bundle bundle = this.f$1;
        int i2 = this.f$2;
        Bundle bundle2 = this.f$3;
        int i3 = this.f$4;
        float f = this.f$5;
        RemoteAnimationAdapter remoteAnimationAdapter = this.f$6;
        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        stageCoordinator.startWithLegacyTransition(i, i2, (PendingIntent) null, (Intent) null, bundle, bundle2, i3, f, remoteAnimationAdapter);
    }
}
