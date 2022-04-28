package com.android.systemui.statusbar.phone;

import android.os.RemoteException;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.systemui.ScreenDecorations$$ExternalSyntheticLambda1;
import com.android.systemui.screenshot.ScrollCaptureClient;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.util.Assert;
import com.google.android.systemui.elmyra.sensors.JNIGestureSensor;
import com.google.android.systemui.elmyra.sensors.config.GestureConfiguration;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda2 implements CallbackToFutureAdapter.Resolver, Pluggable.PluggableListener, Bubbles.BubbleExpandListener, GestureConfiguration.Listener {
    public final /* synthetic */ Object f$0;

    public final void onGestureConfigurationChanged(GestureConfiguration gestureConfiguration) {
        ((JNIGestureSensor) this.f$0).lambda$new$0(gestureConfiguration);
    }

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda2(Object obj) {
        this.f$0 = obj;
    }

    public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
        ScrollCaptureClient.SessionWrapper sessionWrapper = (ScrollCaptureClient.SessionWrapper) this.f$0;
        int i = ScrollCaptureClient.SessionWrapper.$r8$clinit;
        Objects.requireNonNull(sessionWrapper);
        if (!sessionWrapper.mStarted) {
            try {
                sessionWrapper.mConnection.asBinder().unlinkToDeath(sessionWrapper, 0);
                sessionWrapper.mConnection.close();
            } catch (RemoteException unused) {
            }
            sessionWrapper.mConnection = null;
            completer.set(null);
            return "";
        }
        sessionWrapper.mEndCompleter = completer;
        try {
            sessionWrapper.mConnection.endCapture();
        } catch (RemoteException e) {
            completer.setException(e);
        }
        return "IScrollCaptureCallbacks#onCaptureEnded";
    }

    public final void onBubbleExpandChanged(boolean z, String str) {
        StatusBar statusBar = (StatusBar) this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.mContext.getMainExecutor().execute(new ScreenDecorations$$ExternalSyntheticLambda1(statusBar, 6));
    }

    public final void onPluggableInvalidated(Object obj) {
        ShadeListBuilder shadeListBuilder = (ShadeListBuilder) this.f$0;
        NotifFilter notifFilter = (NotifFilter) obj;
        Objects.requireNonNull(shadeListBuilder);
        Assert.isMainThread();
        ShadeListBuilderLogger shadeListBuilderLogger = shadeListBuilder.mLogger;
        Objects.requireNonNull(notifFilter);
        String str = notifFilter.mName;
        PipelineState pipelineState = shadeListBuilder.mPipelineState;
        Objects.requireNonNull(pipelineState);
        shadeListBuilderLogger.logFinalizeFilterInvalidated(str, pipelineState.mState);
        shadeListBuilder.rebuildListIfBefore(8);
    }
}
