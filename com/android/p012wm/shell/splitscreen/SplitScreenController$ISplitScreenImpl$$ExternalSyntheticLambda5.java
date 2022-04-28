package com.android.p012wm.shell.splitscreen;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.RemoteAnimationAdapter;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.splitscreen.SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda5 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda5 implements Consumer {
    public final /* synthetic */ PendingIntent f$0;
    public final /* synthetic */ Intent f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Bundle f$3;
    public final /* synthetic */ Bundle f$4;
    public final /* synthetic */ int f$5;
    public final /* synthetic */ float f$6;
    public final /* synthetic */ RemoteAnimationAdapter f$7;

    public /* synthetic */ SplitScreenController$ISplitScreenImpl$$ExternalSyntheticLambda5(PendingIntent pendingIntent, Intent intent, int i, Bundle bundle, Bundle bundle2, int i2, float f, RemoteAnimationAdapter remoteAnimationAdapter) {
        this.f$0 = pendingIntent;
        this.f$1 = intent;
        this.f$2 = i;
        this.f$3 = bundle;
        this.f$4 = bundle2;
        this.f$5 = i2;
        this.f$6 = f;
        this.f$7 = remoteAnimationAdapter;
    }

    public final void accept(Object obj) {
        PendingIntent pendingIntent = this.f$0;
        Intent intent = this.f$1;
        int i = this.f$2;
        Bundle bundle = this.f$3;
        Bundle bundle2 = this.f$4;
        int i2 = this.f$5;
        float f = this.f$6;
        RemoteAnimationAdapter remoteAnimationAdapter = this.f$7;
        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
        Objects.requireNonNull(stageCoordinator);
        stageCoordinator.startWithLegacyTransition(i, -1, pendingIntent, intent, bundle, bundle2, i2, f, remoteAnimationAdapter);
    }
}
