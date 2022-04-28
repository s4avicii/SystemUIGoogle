package com.android.systemui.screenshot;

import android.view.ScrollCaptureResponse;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.ResolvableFuture;
import com.android.systemui.p006qs.tileimpl.QSTileImpl$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.tuner.NavBarTuner$$ExternalSyntheticLambda6;
import java.util.Objects;
import java.util.concurrent.Executor;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrollCaptureController$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ScrollCaptureController f$0;
    public final /* synthetic */ ScrollCaptureResponse f$1;

    public /* synthetic */ ScrollCaptureController$$ExternalSyntheticLambda0(ScrollCaptureController scrollCaptureController, ScrollCaptureResponse scrollCaptureResponse) {
        this.f$0 = scrollCaptureController;
        this.f$1 = scrollCaptureResponse;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        ScrollCaptureController scrollCaptureController = this.f$0;
        ScrollCaptureResponse scrollCaptureResponse = this.f$1;
        Objects.requireNonNull(scrollCaptureController);
        scrollCaptureController.mCaptureCompleter = completer;
        scrollCaptureController.mWindowOwner = scrollCaptureResponse.getPackageName();
        CallbackToFutureAdapter.Completer<ScrollCaptureController.LongScreenshot> completer2 = scrollCaptureController.mCaptureCompleter;
        QSTileImpl$$ExternalSyntheticLambda0 qSTileImpl$$ExternalSyntheticLambda0 = new QSTileImpl$$ExternalSyntheticLambda0(scrollCaptureController, 4);
        Executor executor = scrollCaptureController.mBgExecutor;
        Objects.requireNonNull(completer2);
        ResolvableFuture<Void> resolvableFuture = completer2.cancellationFuture;
        if (resolvableFuture != null) {
            resolvableFuture.addListener(qSTileImpl$$ExternalSyntheticLambda0, executor);
        }
        scrollCaptureController.mBgExecutor.execute(new NavBarTuner$$ExternalSyntheticLambda6(scrollCaptureController, scrollCaptureResponse, 3));
        return "<batch scroll capture>";
    }
}
