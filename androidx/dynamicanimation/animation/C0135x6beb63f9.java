package androidx.dynamicanimation.animation;

import android.os.SystemClock;
import androidx.dynamicanimation.animation.AnimationHandler;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import java.util.Objects;

/* renamed from: androidx.dynamicanimation.animation.AnimationHandler$FrameCallbackScheduler14$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0135x6beb63f9 implements Runnable {
    public final /* synthetic */ AnimationHandler.FrameCallbackScheduler14 f$0;
    public final /* synthetic */ Runnable f$1;

    public /* synthetic */ C0135x6beb63f9(AnimationHandler.FrameCallbackScheduler14 frameCallbackScheduler14, ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 imageWallpaper$GLEngine$$ExternalSyntheticLambda0) {
        this.f$0 = frameCallbackScheduler14;
        this.f$1 = imageWallpaper$GLEngine$$ExternalSyntheticLambda0;
    }

    public final void run() {
        AnimationHandler.FrameCallbackScheduler14 frameCallbackScheduler14 = this.f$0;
        Runnable runnable = this.f$1;
        Objects.requireNonNull(frameCallbackScheduler14);
        frameCallbackScheduler14.mLastFrameTime = SystemClock.uptimeMillis();
        runnable.run();
    }
}
