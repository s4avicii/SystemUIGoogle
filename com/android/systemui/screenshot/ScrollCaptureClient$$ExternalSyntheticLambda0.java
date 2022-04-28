package com.android.systemui.screenshot;

import android.os.RemoteException;
import android.view.IScrollCaptureResponseListener;
import android.view.ScrollCaptureResponse;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrollCaptureClient$$ExternalSyntheticLambda0 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ScrollCaptureClient f$0;
    public final /* synthetic */ int f$1 = 0;
    public final /* synthetic */ int f$2 = -1;

    public /* synthetic */ ScrollCaptureClient$$ExternalSyntheticLambda0(ScrollCaptureClient scrollCaptureClient) {
        this.f$0 = scrollCaptureClient;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        ScrollCaptureClient scrollCaptureClient = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(scrollCaptureClient);
        try {
            scrollCaptureClient.mWindowManagerService.requestScrollCapture(i, scrollCaptureClient.mHostWindowToken, i2, new IScrollCaptureResponseListener.Stub() {
                public final void onScrollCaptureResponse(ScrollCaptureResponse scrollCaptureResponse) {
                    CallbackToFutureAdapter.Completer.this.set(scrollCaptureResponse);
                }
            });
        } catch (RemoteException e) {
            completer.setException(e);
        }
        return "ScrollCaptureClient#request(displayId=" + i + ", taskId=" + i2 + ")";
    }
}
