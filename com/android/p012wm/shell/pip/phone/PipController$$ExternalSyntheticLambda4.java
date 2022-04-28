package com.android.p012wm.shell.pip.phone;

import android.graphics.Rect;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.p012wm.shell.onehanded.OneHandedController;
import com.android.p012wm.shell.onehanded.OneHandedTransitionCallback;
import com.android.p012wm.shell.pip.Pip;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda4 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void accept(Object obj) {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                PipController pipController = (PipController) this.f$0;
                OneHandedController oneHandedController = (OneHandedController) obj;
                Objects.requireNonNull(pipController);
                Objects.requireNonNull(oneHandedController);
                oneHandedController.mImpl.registerTransitionCallback(new OneHandedTransitionCallback() {
                    public final void onStartFinished(Rect rect) {
                        PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
                        int i = rect.top;
                        Objects.requireNonNull(pipTouchHandler);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        Objects.requireNonNull(pipResizeGestureHandler);
                        pipResizeGestureHandler.mOhmOffset = i;
                    }

                    public final void onStopFinished(Rect rect) {
                        PipTouchHandler pipTouchHandler = PipController.this.mTouchHandler;
                        int i = rect.top;
                        Objects.requireNonNull(pipTouchHandler);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        Objects.requireNonNull(pipResizeGestureHandler);
                        pipResizeGestureHandler.mOhmOffset = i;
                    }
                });
                return;
            case 1:
                NavigationBarView navigationBarView = (NavigationBarView) this.f$0;
                Objects.requireNonNull(navigationBarView);
                ((Pip) obj).addPipExclusionBoundsChangeListener(navigationBarView.mPipListener);
                return;
            default:
                StatusBar statusBar = (StatusBar) this.f$0;
                Bubbles bubbles = (Bubbles) obj;
                long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
                Objects.requireNonNull(statusBar);
                int i = statusBar.mStatusBarMode;
                if (i == 3 || i == 6) {
                    z = false;
                } else {
                    z = true;
                }
                bubbles.onStatusBarVisibilityChanged(z);
                return;
        }
    }
}
