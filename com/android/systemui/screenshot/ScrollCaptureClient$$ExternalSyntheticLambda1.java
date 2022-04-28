package com.android.systemui.screenshot;

import android.media.ImageReader;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.view.IScrollCaptureConnection;
import android.view.ScrollCaptureResponse;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.concurrent.futures.ResolvableFuture;
import com.android.p012wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda7;
import com.android.systemui.screenshot.ScrollCaptureClient;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrollCaptureClient$$ExternalSyntheticLambda1 implements CallbackToFutureAdapter.Resolver {
    public final /* synthetic */ ScrollCaptureClient f$0;
    public final /* synthetic */ IScrollCaptureConnection f$1;
    public final /* synthetic */ ScrollCaptureResponse f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ ScrollCaptureClient$$ExternalSyntheticLambda1(ScrollCaptureClient scrollCaptureClient, IScrollCaptureConnection iScrollCaptureConnection, ScrollCaptureResponse scrollCaptureResponse, float f) {
        this.f$0 = scrollCaptureClient;
        this.f$1 = iScrollCaptureConnection;
        this.f$2 = scrollCaptureResponse;
        this.f$3 = f;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        ScrollCaptureClient scrollCaptureClient = this.f$0;
        IScrollCaptureConnection iScrollCaptureConnection = this.f$1;
        ScrollCaptureResponse scrollCaptureResponse = this.f$2;
        float f = this.f$3;
        Objects.requireNonNull(scrollCaptureClient);
        if (iScrollCaptureConnection == null || !iScrollCaptureConnection.asBinder().isBinderAlive()) {
            completer.setException(new DeadObjectException("No active connection!"));
            return "";
        }
        ScrollCaptureClient.SessionWrapper sessionWrapper = new ScrollCaptureClient.SessionWrapper(iScrollCaptureConnection, scrollCaptureResponse.getWindowBounds(), scrollCaptureResponse.getBoundsInWindow(), f, scrollCaptureClient.mBgExecutor);
        ImageReader newInstance = ImageReader.newInstance(sessionWrapper.mTileWidth, sessionWrapper.mTileHeight, 1, 30, 256);
        sessionWrapper.mReader = newInstance;
        sessionWrapper.mStartCompleter = completer;
        newInstance.setOnImageAvailableListenerWithExecutor(sessionWrapper, sessionWrapper.mBgExecutor);
        try {
            sessionWrapper.mCancellationSignal = sessionWrapper.mConnection.startCapture(sessionWrapper.mReader.getSurface(), sessionWrapper);
            PipMenuView$$ExternalSyntheticLambda7 pipMenuView$$ExternalSyntheticLambda7 = new PipMenuView$$ExternalSyntheticLambda7(sessionWrapper, 2);
            SaveImageInBackgroundTask$$ExternalSyntheticLambda0 saveImageInBackgroundTask$$ExternalSyntheticLambda0 = SaveImageInBackgroundTask$$ExternalSyntheticLambda0.INSTANCE;
            ResolvableFuture<Void> resolvableFuture = completer.cancellationFuture;
            if (resolvableFuture != null) {
                resolvableFuture.addListener(pipMenuView$$ExternalSyntheticLambda7, saveImageInBackgroundTask$$ExternalSyntheticLambda0);
            }
            sessionWrapper.mStarted = true;
        } catch (RemoteException e) {
            sessionWrapper.mReader.close();
            completer.setException(e);
        }
        return "IScrollCaptureCallbacks#onCaptureStarted";
    }
}
