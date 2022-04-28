package com.android.p012wm.shell.bubbles;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.os.RemoteException;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.widget.Toast;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.p012wm.shell.legacysplitscreen.WindowManagerProxy;
import com.android.p012wm.shell.pip.PinnedStackListenerForwarder;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda22 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda22 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda22(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        boolean z;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        int activityType;
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                Consumer consumer = (Consumer) this.f$1;
                Objects.requireNonNull(bubbleStackView);
                SurfaceControl.ScreenshotHardwareBuffer screenshotHardwareBuffer = bubbleStackView.mAnimatingOutBubbleBuffer;
                if (screenshotHardwareBuffer == null || screenshotHardwareBuffer.getHardwareBuffer() == null || bubbleStackView.mAnimatingOutBubbleBuffer.getHardwareBuffer().isClosed()) {
                    consumer.accept(Boolean.FALSE);
                    return;
                } else if (!bubbleStackView.mIsExpanded || !bubbleStackView.mAnimatingOutSurfaceReady) {
                    consumer.accept(Boolean.FALSE);
                    return;
                } else {
                    bubbleStackView.mAnimatingOutSurfaceView.getHolder().getSurface().attachAndQueueBufferWithColorSpace(bubbleStackView.mAnimatingOutBubbleBuffer.getHardwareBuffer(), bubbleStackView.mAnimatingOutBubbleBuffer.getColorSpace());
                    bubbleStackView.mAnimatingOutSurfaceView.setAlpha(1.0f);
                    bubbleStackView.mExpandedViewContainer.setVisibility(8);
                    BubbleStackView.SurfaceSynchronizer surfaceSynchronizer = bubbleStackView.mSurfaceSynchronizer;
                    BubbleStackView$$ExternalSyntheticLambda21 bubbleStackView$$ExternalSyntheticLambda21 = new BubbleStackView$$ExternalSyntheticLambda21(bubbleStackView, consumer, 0);
                    Objects.requireNonNull((BubbleStackView.C18031) surfaceSynchronizer);
                    Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback(bubbleStackView$$ExternalSyntheticLambda21) {
                        public int mFrameWait = 2;
                        public final /* synthetic */ Runnable val$callback;

                        {
                            this.val$callback = r1;
                        }

                        public final void doFrame(long j) {
                            int i = this.mFrameWait - 1;
                            this.mFrameWait = i;
                            if (i > 0) {
                                Choreographer.getInstance().postFrameCallback(this);
                            } else {
                                this.val$callback.run();
                            }
                        }
                    });
                    return;
                }
            case 1:
                QuickAccessWalletController.C17421 r0 = (QuickAccessWalletController.C17421) this.f$0;
                int i = QuickAccessWalletController.C17421.$r8$clinit;
                Objects.requireNonNull(r0);
                QuickAccessWalletController.this.reCreateWalletClient();
                QuickAccessWalletController.this.updateWalletPreference();
                QuickAccessWalletController.this.queryWalletCards((QuickAccessWalletClient.OnWalletCardsRetrievedCallback) this.f$1);
                return;
            case 2:
                LegacySplitScreenController.SplitScreenImpl splitScreenImpl = (LegacySplitScreenController.SplitScreenImpl) this.f$0;
                boolean[] zArr = (boolean[]) this.f$1;
                Objects.requireNonNull(splitScreenImpl);
                LegacySplitScreenController legacySplitScreenController = LegacySplitScreenController.this;
                Objects.requireNonNull(legacySplitScreenController);
                try {
                    if (!(ActivityTaskManager.getService().getLockTaskModeState() == 2 || legacySplitScreenController.isSplitActive() || legacySplitScreenController.mSplits.mPrimary == null)) {
                        z = true;
                        List tasks = ActivityTaskManager.getInstance().getTasks(1);
                        if (!(tasks == null || tasks.isEmpty() || (activityType = runningTaskInfo.getActivityType()) == 2 || activityType == 3)) {
                            if (!(runningTaskInfo = (ActivityManager.RunningTaskInfo) tasks.get(0)).supportsSplitScreenMultiWindow) {
                                Toast.makeText(legacySplitScreenController.mContext, C1777R.string.dock_non_resizeble_failed_to_dock_text, 0).show();
                            } else {
                                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                windowContainerTransaction.setWindowingMode(runningTaskInfo.token, 0);
                                windowContainerTransaction.reparent(runningTaskInfo.token, legacySplitScreenController.mSplits.mPrimary.token, true);
                                WindowManagerProxy windowManagerProxy = legacySplitScreenController.mWindowManagerProxy;
                                Objects.requireNonNull(windowManagerProxy);
                                windowManagerProxy.mSyncTransactionQueue.queue(windowContainerTransaction);
                                zArr[0] = z;
                                return;
                            }
                        }
                    }
                } catch (RemoteException unused) {
                }
                z = false;
                zArr[0] = z;
                return;
            default:
                PinnedStackListenerForwarder.PinnedTaskListenerImpl pinnedTaskListenerImpl = (PinnedStackListenerForwarder.PinnedTaskListenerImpl) this.f$0;
                ComponentName componentName = (ComponentName) this.f$1;
                int i2 = PinnedStackListenerForwarder.PinnedTaskListenerImpl.$r8$clinit;
                Objects.requireNonNull(pinnedTaskListenerImpl);
                PinnedStackListenerForwarder pinnedStackListenerForwarder = PinnedStackListenerForwarder.this;
                Objects.requireNonNull(pinnedStackListenerForwarder);
                Iterator<PinnedStackListenerForwarder.PinnedTaskListener> it = pinnedStackListenerForwarder.mListeners.iterator();
                while (it.hasNext()) {
                    it.next().onActivityHidden(componentName);
                }
                return;
        }
    }
}
