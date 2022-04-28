package com.android.systemui.dreams.touch;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.listbuilder.PipelineState;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifStabilityManager;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.Pluggable;
import com.android.systemui.util.Assert;
import com.google.android.systemui.smartspace.InterceptingViewPager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2 implements DreamOverlayTouchMonitor.Evaluator, Pluggable.PluggableListener, InterceptingViewPager.EventProxy {
    public final /* synthetic */ Object f$0;

    public final boolean delegateEvent(MotionEvent motionEvent) {
        return Objects.requireNonNull((InterceptingViewPager) this.f$0);
    }

    public /* synthetic */ DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2(Object obj) {
        this.f$0 = obj;
    }

    public final boolean evaluate(GestureDetector.OnGestureListener onGestureListener) {
        return onGestureListener.onDown((MotionEvent) this.f$0);
    }

    public final void onPluggableInvalidated(Object obj) {
        ShadeListBuilder shadeListBuilder = (ShadeListBuilder) this.f$0;
        NotifStabilityManager notifStabilityManager = (NotifStabilityManager) obj;
        Objects.requireNonNull(shadeListBuilder);
        Assert.isMainThread();
        ShadeListBuilderLogger shadeListBuilderLogger = shadeListBuilder.mLogger;
        Objects.requireNonNull(notifStabilityManager);
        String str = notifStabilityManager.mName;
        PipelineState pipelineState = shadeListBuilder.mPipelineState;
        Objects.requireNonNull(pipelineState);
        shadeListBuilderLogger.logReorderingAllowedInvalidated(str, pipelineState.mState);
        shadeListBuilder.rebuildListIfBefore(4);
    }
}
