package com.android.p012wm.shell.pip.phone;

import android.app.PictureInPictureParams;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.p012wm.shell.pip.PipTransitionState;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PipController$IPipImpl$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ Rect[] f$0;
    public final /* synthetic */ ComponentName f$1;
    public final /* synthetic */ ActivityInfo f$2;
    public final /* synthetic */ PictureInPictureParams f$3;
    public final /* synthetic */ int f$4;
    public final /* synthetic */ int f$5;

    public /* synthetic */ PipController$IPipImpl$$ExternalSyntheticLambda2(Rect[] rectArr, ComponentName componentName, ActivityInfo activityInfo, PictureInPictureParams pictureInPictureParams, int i, int i2) {
        this.f$0 = rectArr;
        this.f$1 = componentName;
        this.f$2 = activityInfo;
        this.f$3 = pictureInPictureParams;
        this.f$4 = i;
        this.f$5 = i2;
    }

    public final void accept(Object obj) {
        boolean z;
        Rect[] rectArr = this.f$0;
        ComponentName componentName = this.f$1;
        ActivityInfo activityInfo = this.f$2;
        PictureInPictureParams pictureInPictureParams = this.f$3;
        int i = this.f$4;
        int i2 = this.f$5;
        PipController pipController = (PipController) obj;
        Objects.requireNonNull(pipController);
        if (i2 > 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            i2 = 0;
        }
        PipBoundsState pipBoundsState = pipController.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState);
        pipBoundsState.setShelfVisibility(z, i2, true);
        Context context = pipController.mContext;
        PipBoundsState pipBoundsState2 = pipController.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState2);
        pipBoundsState2.mDisplayLayout.rotateTo(context.getResources(), i);
        PipTaskOrganizer pipTaskOrganizer = pipController.mPipTaskOrganizer;
        Objects.requireNonNull(pipTaskOrganizer);
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState);
        pipTransitionState.mInSwipePipToHomeTransition = true;
        PipTransitionState pipTransitionState2 = pipTaskOrganizer.mPipTransitionState;
        Objects.requireNonNull(pipTransitionState2);
        pipTransitionState2.mState = 3;
        pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionStarted(2);
        pipTaskOrganizer.mPipBoundsState.setBoundsStateForEntry(componentName, activityInfo, pictureInPictureParams, pipTaskOrganizer.mPipBoundsAlgorithm);
        Rect entryDestinationBounds = pipTaskOrganizer.mPipBoundsAlgorithm.getEntryDestinationBounds();
        PipBoundsState pipBoundsState3 = pipController.mPipBoundsState;
        Objects.requireNonNull(pipBoundsState3);
        pipBoundsState3.mNormalBounds.set(entryDestinationBounds);
        rectArr[0] = entryDestinationBounds;
    }
}
