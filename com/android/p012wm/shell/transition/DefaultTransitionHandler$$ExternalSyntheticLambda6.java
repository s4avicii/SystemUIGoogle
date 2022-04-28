package com.android.p012wm.shell.transition;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda6 implements Consumer {
    public final /* synthetic */ DefaultTransitionHandler f$0;
    public final /* synthetic */ ArrayList f$1;
    public final /* synthetic */ Animation f$2;
    public final /* synthetic */ TransitionInfo.Change f$3;
    public final /* synthetic */ Runnable f$4;
    public final /* synthetic */ float f$5;
    public final /* synthetic */ Rect f$6;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda6(DefaultTransitionHandler defaultTransitionHandler, ArrayList arrayList, Animation animation, TransitionInfo.Change change, DefaultTransitionHandler$$ExternalSyntheticLambda1 defaultTransitionHandler$$ExternalSyntheticLambda1, float f, Rect rect) {
        this.f$0 = defaultTransitionHandler;
        this.f$1 = arrayList;
        this.f$2 = animation;
        this.f$3 = change;
        this.f$4 = defaultTransitionHandler$$ExternalSyntheticLambda1;
        this.f$5 = f;
        this.f$6 = rect;
    }

    public final void accept(Object obj) {
        DefaultTransitionHandler defaultTransitionHandler = this.f$0;
        ArrayList arrayList = this.f$1;
        Animation animation = this.f$2;
        TransitionInfo.Change change = this.f$3;
        Runnable runnable = this.f$4;
        float f = this.f$5;
        Rect rect = this.f$6;
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) obj;
        Objects.requireNonNull(defaultTransitionHandler);
        DefaultTransitionHandler.startSurfaceAnimation(arrayList, animation, change.getLeash(), runnable, defaultTransitionHandler.mTransactionPool, defaultTransitionHandler.mMainExecutor, defaultTransitionHandler.mAnimExecutor, (Point) null, f, rect);
    }
}
