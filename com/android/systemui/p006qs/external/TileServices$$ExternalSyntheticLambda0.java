package com.android.systemui.p006qs.external;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreenController;
import com.android.systemui.p006qs.QSTileHost;
import com.google.android.systemui.assist.uihints.ChipsContainer;
import com.google.android.systemui.assist.uihints.TranscriptionController;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.external.TileServices$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class TileServices$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ TileServices$$ExternalSyntheticLambda0(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                TileServices tileServices = (TileServices) this.f$0;
                Objects.requireNonNull(tileServices);
                QSTileHost qSTileHost = tileServices.mHost;
                Objects.requireNonNull(qSTileHost);
                qSTileHost.mIconController.removeAllIconsForSlot((String) this.f$1);
                return;
            case 1:
                LegacySplitScreenController.SplitScreenImpl splitScreenImpl = (LegacySplitScreenController.SplitScreenImpl) this.f$0;
                Objects.requireNonNull(splitScreenImpl);
                ((boolean[]) this.f$1)[0] = LegacySplitScreenController.this.isDividerVisible();
                return;
            default:
                TranscriptionController transcriptionController = (TranscriptionController) this.f$0;
                Objects.requireNonNull(transcriptionController);
                ChipsContainer chipsContainer = (ChipsContainer) transcriptionController.mViewMap.get(TranscriptionController.State.CHIPS);
                Objects.requireNonNull(chipsContainer);
                chipsContainer.mChips = (List) this.f$1;
                chipsContainer.setChipsInternal();
                if (chipsContainer.mAnimator.isRunning()) {
                    Log.w("ChipsContainer", "Already animating in chips view; ignoring");
                    return;
                }
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(ObjectAnimator.ofFloat(chipsContainer, View.SCALE_X, new float[]{0.8f, 1.0f})).with(ObjectAnimator.ofFloat(chipsContainer, View.SCALE_Y, new float[]{0.8f, 1.0f})).with(ObjectAnimator.ofFloat(chipsContainer, View.ALPHA, new float[]{0.0f, 1.0f}));
                animatorSet.setDuration(200);
                chipsContainer.setVisibility(0);
                animatorSet.start();
                return;
        }
    }
}
