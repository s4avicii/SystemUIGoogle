package com.android.systemui.statusbar.phone;

import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.ChipVisibilityListener;
import java.util.Objects;

/* compiled from: SplitShadeHeaderController.kt */
public final class SplitShadeHeaderController$chipVisibilityListener$1 implements ChipVisibilityListener {
    public final /* synthetic */ SplitShadeHeaderController this$0;

    public SplitShadeHeaderController$chipVisibilityListener$1(SplitShadeHeaderController splitShadeHeaderController) {
        this.this$0 = splitShadeHeaderController;
    }

    public final void onChipVisibilityRefreshed(boolean z) {
        ConstraintSet constraintSet;
        float f;
        View view = this.this$0.statusBar;
        if (view instanceof MotionLayout) {
            MotionLayout motionLayout = (MotionLayout) view;
            Objects.requireNonNull(motionLayout);
            MotionScene motionScene = motionLayout.mScene;
            if (motionScene == null) {
                constraintSet = null;
            } else {
                constraintSet = motionScene.getConstraintSet(C1777R.C1779id.qqs_header_constraint);
            }
            float f2 = 0.0f;
            if (z) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            Objects.requireNonNull(constraintSet);
            constraintSet.get(C1777R.C1779id.statusIcons).propertySet.alpha = f;
            if (!z) {
                f2 = 1.0f;
            }
            constraintSet.get(C1777R.C1779id.batteryRemainingIcon).propertySet.alpha = f2;
            MotionLayout motionLayout2 = (MotionLayout) this.this$0.statusBar;
            Objects.requireNonNull(motionLayout2);
            MotionScene motionScene2 = motionLayout2.mScene;
            if (motionScene2 != null) {
                motionScene2.mConstraintSetMap.put(C1777R.C1779id.qqs_header_constraint, constraintSet);
            }
            motionLayout2.mModel.initFrom(motionLayout2.mScene.getConstraintSet(motionLayout2.mBeginState), motionLayout2.mScene.getConstraintSet(motionLayout2.mEndState));
            motionLayout2.rebuildScene();
            if (motionLayout2.mCurrentState == C1777R.C1779id.qqs_header_constraint) {
                constraintSet.applyTo(motionLayout2);
            }
        }
    }
}
