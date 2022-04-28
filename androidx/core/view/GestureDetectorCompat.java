package androidx.core.view;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import java.util.Objects;

public final class GestureDetectorCompat {
    public final GestureDetectorCompatImplJellybeanMr2 mImpl;

    public static class GestureDetectorCompatImplJellybeanMr2 {
        public final GestureDetector mDetector;

        public GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener) {
            this.mDetector = new GestureDetector(context, onGestureListener, (Handler) null);
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        GestureDetectorCompatImplJellybeanMr2 gestureDetectorCompatImplJellybeanMr2 = this.mImpl;
        Objects.requireNonNull(gestureDetectorCompatImplJellybeanMr2);
        return gestureDetectorCompatImplJellybeanMr2.mDetector.onTouchEvent(motionEvent);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener);
    }
}
