package androidx.dynamicanimation.animation;

import android.view.Choreographer;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;

/* renamed from: androidx.dynamicanimation.animation.AnimationHandler$FrameCallbackScheduler16$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0136xde8d857b implements Choreographer.FrameCallback {
    public final /* synthetic */ Runnable f$0;

    public /* synthetic */ C0136xde8d857b(ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 imageWallpaper$GLEngine$$ExternalSyntheticLambda0) {
        this.f$0 = imageWallpaper$GLEngine$$ExternalSyntheticLambda0;
    }

    public final void doFrame(long j) {
        this.f$0.run();
    }
}
