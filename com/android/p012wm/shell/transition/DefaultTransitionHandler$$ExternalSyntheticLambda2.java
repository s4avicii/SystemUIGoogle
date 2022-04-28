package com.android.p012wm.shell.transition;

import android.animation.ValueAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.common.TransactionPool;
import com.android.p012wm.shell.draganddrop.DragAndDropController$$ExternalSyntheticLambda0;
import java.util.ArrayList;

/* renamed from: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ ValueAnimator f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;
    public final /* synthetic */ ShellExecutor f$10;
    public final /* synthetic */ ArrayList f$11;
    public final /* synthetic */ Runnable f$12;
    public final /* synthetic */ SurfaceControl f$2;
    public final /* synthetic */ Animation f$3;
    public final /* synthetic */ Transformation f$4;
    public final /* synthetic */ float[] f$5;
    public final /* synthetic */ Point f$6;
    public final /* synthetic */ float f$7;
    public final /* synthetic */ Rect f$8;
    public final /* synthetic */ TransactionPool f$9;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda2(ValueAnimator valueAnimator, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Animation animation, Transformation transformation, float[] fArr, Point point, float f, Rect rect, TransactionPool transactionPool, ShellExecutor shellExecutor, ArrayList arrayList, Runnable runnable) {
        this.f$0 = valueAnimator;
        this.f$1 = transaction;
        this.f$2 = surfaceControl;
        this.f$3 = animation;
        this.f$4 = transformation;
        this.f$5 = fArr;
        this.f$6 = point;
        this.f$7 = f;
        this.f$8 = rect;
        this.f$9 = transactionPool;
        this.f$10 = shellExecutor;
        this.f$11 = arrayList;
        this.f$12 = runnable;
    }

    public final void run() {
        ValueAnimator valueAnimator = this.f$0;
        SurfaceControl.Transaction transaction = this.f$1;
        SurfaceControl surfaceControl = this.f$2;
        Animation animation = this.f$3;
        Transformation transformation = this.f$4;
        float[] fArr = this.f$5;
        Point point = this.f$6;
        float f = this.f$7;
        Rect rect = this.f$8;
        TransactionPool transactionPool = this.f$9;
        ShellExecutor shellExecutor = this.f$10;
        ArrayList arrayList = this.f$11;
        Runnable runnable = this.f$12;
        DefaultTransitionHandler.applyTransformation(valueAnimator.getDuration(), transaction, surfaceControl, animation, transformation, fArr, point, f, rect);
        transactionPool.release(transaction);
        shellExecutor.execute(new DragAndDropController$$ExternalSyntheticLambda0(arrayList, valueAnimator, runnable, 1));
    }
}
