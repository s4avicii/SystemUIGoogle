package androidx.dynamicanimation.animation;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.Choreographer;
import androidx.collection.SimpleArrayMap;
import com.android.systemui.ImageWallpaper$GLEngine$$ExternalSyntheticLambda0;
import java.util.ArrayList;

public final class AnimationHandler {
    public static final ThreadLocal<AnimationHandler> sAnimatorHandler = new ThreadLocal<>();
    public final ArrayList<AnimationFrameCallback> mAnimationCallbacks = new ArrayList<>();
    public final AnimationCallbackDispatcher mCallbackDispatcher = new AnimationCallbackDispatcher();
    public long mCurrentFrameTime = 0;
    public final SimpleArrayMap<AnimationFrameCallback, Long> mDelayedCallbackStartTime = new SimpleArrayMap<>();
    public boolean mListDirty = false;
    public final ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 mRunnable = new ImageWallpaper$GLEngine$$ExternalSyntheticLambda0(this, 1);
    public FrameCallbackScheduler mScheduler;

    public class AnimationCallbackDispatcher {
        public AnimationCallbackDispatcher() {
        }
    }

    public interface AnimationFrameCallback {
        boolean doAnimationFrame(long j);
    }

    public interface FrameCallbackScheduler {
        boolean isCurrentThread();

        void postFrameCallback(ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 imageWallpaper$GLEngine$$ExternalSyntheticLambda0);
    }

    public static final class FrameCallbackScheduler16 implements FrameCallbackScheduler {
        public final Choreographer mChoreographer = Choreographer.getInstance();
        public final Looper mLooper = Looper.myLooper();

        public final void postFrameCallback(ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 imageWallpaper$GLEngine$$ExternalSyntheticLambda0) {
            this.mChoreographer.postFrameCallback(new C0136xde8d857b(imageWallpaper$GLEngine$$ExternalSyntheticLambda0));
        }

        public final boolean isCurrentThread() {
            if (Thread.currentThread() == this.mLooper.getThread()) {
                return true;
            }
            return false;
        }
    }

    public static class FrameCallbackScheduler14 implements FrameCallbackScheduler {
        public final Handler mHandler = new Handler(Looper.myLooper());
        public long mLastFrameTime;

        public final boolean isCurrentThread() {
            if (Thread.currentThread() == this.mHandler.getLooper().getThread()) {
                return true;
            }
            return false;
        }

        public final void postFrameCallback(ImageWallpaper$GLEngine$$ExternalSyntheticLambda0 imageWallpaper$GLEngine$$ExternalSyntheticLambda0) {
            this.mHandler.postDelayed(new C0135x6beb63f9(this, imageWallpaper$GLEngine$$ExternalSyntheticLambda0), Math.max(10 - (SystemClock.uptimeMillis() - this.mLastFrameTime), 0));
        }
    }

    public AnimationHandler(FrameCallbackScheduler frameCallbackScheduler) {
        this.mScheduler = frameCallbackScheduler;
    }

    public void setScheduler(FrameCallbackScheduler frameCallbackScheduler) {
        this.mScheduler = frameCallbackScheduler;
    }

    public FrameCallbackScheduler getScheduler() {
        return this.mScheduler;
    }
}
