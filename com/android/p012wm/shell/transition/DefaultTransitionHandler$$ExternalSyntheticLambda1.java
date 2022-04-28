package com.android.p012wm.shell.transition;

import android.os.IBinder;
import android.util.IndentingPrintWriter;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.p012wm.shell.transition.Transitions;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Objects;

/* renamed from: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;
    public final /* synthetic */ Object f$3;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda1(Object obj, Object obj2, Object obj3, Object obj4, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
        this.f$3 = obj4;
    }

    public final void run() {
        ExpandableView expandableView = null;
        switch (this.$r8$classId) {
            case 0:
                DefaultTransitionHandler defaultTransitionHandler = (DefaultTransitionHandler) this.f$0;
                IBinder iBinder = (IBinder) this.f$2;
                Transitions.TransitionFinishCallback transitionFinishCallback = (Transitions.TransitionFinishCallback) this.f$3;
                Objects.requireNonNull(defaultTransitionHandler);
                if (((ArrayList) this.f$1).isEmpty()) {
                    ScreenRotationAnimation screenRotationAnimation = defaultTransitionHandler.mRotationAnimation;
                    if (screenRotationAnimation != null) {
                        SurfaceControl.Transaction transaction = screenRotationAnimation.mTransaction;
                        if (transaction == null) {
                            transaction = screenRotationAnimation.mTransactionPool.acquire();
                        }
                        if (screenRotationAnimation.mAnimLeash.isValid()) {
                            transaction.remove(screenRotationAnimation.mAnimLeash);
                        }
                        SurfaceControl surfaceControl = screenRotationAnimation.mScreenshotLayer;
                        if (surfaceControl != null) {
                            if (surfaceControl.isValid()) {
                                transaction.remove(screenRotationAnimation.mScreenshotLayer);
                            }
                            screenRotationAnimation.mScreenshotLayer = null;
                        }
                        SurfaceControl surfaceControl2 = screenRotationAnimation.mBackColorSurface;
                        if (surfaceControl2 != null) {
                            if (surfaceControl2.isValid()) {
                                transaction.remove(screenRotationAnimation.mBackColorSurface);
                            }
                            screenRotationAnimation.mBackColorSurface = null;
                        }
                        transaction.apply();
                        screenRotationAnimation.mTransactionPool.release(transaction);
                        defaultTransitionHandler.mRotationAnimation = null;
                    }
                    defaultTransitionHandler.mAnimations.remove(iBinder);
                    transitionFinishCallback.onTransitionFinished((WindowContainerTransaction) null);
                    return;
                }
                return;
            default:
                NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) this.f$0;
                IndentingPrintWriter indentingPrintWriter = (IndentingPrintWriter) this.f$1;
                FileDescriptor fileDescriptor = (FileDescriptor) this.f$2;
                String[] strArr = (String[]) this.f$3;
                boolean z = NotificationStackScrollLayout.SPEW;
                Objects.requireNonNull(notificationStackScrollLayout);
                int childCount = notificationStackScrollLayout.getChildCount();
                indentingPrintWriter.println("Number of children: " + childCount);
                indentingPrintWriter.println();
                for (int i = 0; i < childCount; i++) {
                    ((ExpandableView) notificationStackScrollLayout.getChildAt(i)).dump(fileDescriptor, indentingPrintWriter, strArr);
                    indentingPrintWriter.println();
                }
                int transientViewCount = notificationStackScrollLayout.getTransientViewCount();
                indentingPrintWriter.println("Transient Views: " + transientViewCount);
                for (int i2 = 0; i2 < transientViewCount; i2++) {
                    ((ExpandableView) notificationStackScrollLayout.getTransientView(i2)).dump(fileDescriptor, indentingPrintWriter, strArr);
                }
                NotificationSwipeHelper notificationSwipeHelper = notificationStackScrollLayout.mSwipeHelper;
                Objects.requireNonNull(notificationSwipeHelper);
                if (notificationSwipeHelper.mIsSwiping) {
                    expandableView = notificationSwipeHelper.mTouchedView;
                }
                indentingPrintWriter.println("Swiped view: " + expandableView);
                if (expandableView instanceof ExpandableView) {
                    expandableView.dump(fileDescriptor, indentingPrintWriter, strArr);
                    return;
                }
                return;
        }
    }
}
