package com.android.p012wm.shell.onehanded;

import android.view.SurfaceControl;
import android.window.WindowContainerToken;
import com.android.p012wm.shell.onehanded.OneHandedAnimationController;
import java.util.Objects;
import java.util.function.BiConsumer;

/* renamed from: com.android.wm.shell.onehanded.OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda2 implements BiConsumer {
    public final /* synthetic */ OneHandedDisplayAreaOrganizer f$0;
    public final /* synthetic */ SurfaceControl.Transaction f$1;

    public /* synthetic */ OneHandedDisplayAreaOrganizer$$ExternalSyntheticLambda2(OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer, SurfaceControl.Transaction transaction) {
        this.f$0 = oneHandedDisplayAreaOrganizer;
        this.f$1 = transaction;
    }

    public final void accept(Object obj, Object obj2) {
        OneHandedDisplayAreaOrganizer oneHandedDisplayAreaOrganizer = this.f$0;
        SurfaceControl.Transaction transaction = this.f$1;
        SurfaceControl surfaceControl = (SurfaceControl) obj2;
        Objects.requireNonNull(oneHandedDisplayAreaOrganizer);
        OneHandedAnimationController oneHandedAnimationController = oneHandedDisplayAreaOrganizer.mAnimationController;
        Objects.requireNonNull(oneHandedAnimationController);
        OneHandedAnimationController.OneHandedTransitionAnimator remove = oneHandedAnimationController.mAnimatorMap.remove((WindowContainerToken) obj);
        if (remove != null && remove.isRunning()) {
            remove.cancel();
        }
        transaction.setPosition(surfaceControl, 0.0f, 0.0f).setWindowCrop(surfaceControl, -1, -1).setCornerRadius(surfaceControl, -1.0f);
    }
}
