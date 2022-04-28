package com.android.systemui.animation;

import android.view.SurfaceControl;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: ViewRootSync.kt */
public final /* synthetic */ class ViewRootSync$synchronizeNextDraw$consumeNextDraw$1 implements Consumer {
    public final /* synthetic */ SurfaceControl.Transaction $mergedTransactions;
    public final /* synthetic */ Ref$IntRef $remainingTransactions;
    public final /* synthetic */ Function0<Unit> $then;

    public ViewRootSync$synchronizeNextDraw$consumeNextDraw$1(Ref$IntRef ref$IntRef, SurfaceControl.Transaction transaction, Function0<Unit> function0) {
        this.$remainingTransactions = ref$IntRef;
        this.$mergedTransactions = transaction;
        this.$then = function0;
    }

    public final void accept(Object obj) {
        SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) obj;
        Ref$IntRef ref$IntRef = this.$remainingTransactions;
        SurfaceControl.Transaction transaction2 = this.$mergedTransactions;
        Function0<Unit> function0 = this.$then;
        ref$IntRef.element--;
        if (transaction != null) {
            transaction2.merge(transaction);
        }
        if (ref$IntRef.element == 0) {
            transaction2.apply();
            function0.invoke();
        }
    }
}
