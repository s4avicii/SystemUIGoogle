package com.android.systemui.unfold;

import android.content.res.Configuration;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.view.WindowlessWindowManager;
import java.util.function.Consumer;

/* compiled from: UnfoldLightRevealOverlayAnimation.kt */
public final class UnfoldLightRevealOverlayAnimation$init$1<T> implements Consumer {
    public final /* synthetic */ UnfoldLightRevealOverlayAnimation this$0;

    public UnfoldLightRevealOverlayAnimation$init$1(UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation) {
        this.this$0 = unfoldLightRevealOverlayAnimation;
    }

    public final void accept(Object obj) {
        final SurfaceControl.Builder builder = (SurfaceControl.Builder) obj;
        final UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = this.this$0;
        unfoldLightRevealOverlayAnimation.executor.execute(new Runnable() {
            public final void run() {
                UnfoldLightRevealOverlayAnimation.this.overlayContainer = builder.build();
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                SurfaceControl surfaceControl = UnfoldLightRevealOverlayAnimation.this.overlayContainer;
                if (surfaceControl == null) {
                    surfaceControl = null;
                }
                SurfaceControl.Transaction layer = transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
                SurfaceControl surfaceControl2 = UnfoldLightRevealOverlayAnimation.this.overlayContainer;
                if (surfaceControl2 == null) {
                    surfaceControl2 = null;
                }
                layer.show(surfaceControl2).apply();
                UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = UnfoldLightRevealOverlayAnimation.this;
                Configuration configuration = UnfoldLightRevealOverlayAnimation.this.context.getResources().getConfiguration();
                SurfaceControl surfaceControl3 = UnfoldLightRevealOverlayAnimation.this.overlayContainer;
                if (surfaceControl3 == null) {
                    surfaceControl3 = null;
                }
                unfoldLightRevealOverlayAnimation.wwm = new WindowlessWindowManager(configuration, surfaceControl3, (IBinder) null);
            }
        });
    }
}
