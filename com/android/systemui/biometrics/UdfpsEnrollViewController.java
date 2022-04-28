package com.android.systemui.biometrics;

import android.graphics.PointF;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.biometrics.UdfpsEnrollHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import java.util.Objects;

public final class UdfpsEnrollViewController extends UdfpsAnimationViewController<UdfpsEnrollView> {
    public final UdfpsEnrollHelper mEnrollHelper;
    public final C07041 mEnrollHelperListener = new UdfpsEnrollHelper.Listener() {
    };
    public final int mEnrollProgressBarRadius = getContext().getResources().getInteger(C1777R.integer.config_udfpsEnrollProgressBar);

    public UdfpsEnrollViewController(UdfpsEnrollView udfpsEnrollView, UdfpsEnrollHelper udfpsEnrollHelper, StatusBarStateController statusBarStateController, PanelExpansionStateManager panelExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager) {
        super(udfpsEnrollView, statusBarStateController, panelExpansionStateManager, systemUIDialogManager, dumpManager);
        this.mEnrollHelper = udfpsEnrollHelper;
        UdfpsEnrollDrawable udfpsEnrollDrawable = udfpsEnrollView.mFingerprintDrawable;
        Objects.requireNonNull(udfpsEnrollDrawable);
        udfpsEnrollDrawable.mEnrollHelper = udfpsEnrollHelper;
    }

    public final String getTag() {
        return "UdfpsEnrollViewController";
    }

    public final PointF getTouchTranslation() {
        if (!this.mEnrollHelper.isGuidedEnrollmentStage()) {
            return new PointF(0.0f, 0.0f);
        }
        return this.mEnrollHelper.getNextGuidedEnrollmentPoint();
    }

    public final void onViewAttached() {
        boolean z;
        int i;
        super.onViewAttached();
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        Objects.requireNonNull(udfpsEnrollHelper);
        if (udfpsEnrollHelper.mEnrollReason == 2) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            UdfpsEnrollHelper udfpsEnrollHelper2 = this.mEnrollHelper;
            C07041 r4 = this.mEnrollHelperListener;
            Objects.requireNonNull(udfpsEnrollHelper2);
            udfpsEnrollHelper2.mListener = r4;
            if (r4 != null && (i = udfpsEnrollHelper2.mTotalSteps) != -1) {
                int i2 = udfpsEnrollHelper2.mRemainingSteps;
                UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) UdfpsEnrollViewController.this.mView;
                Objects.requireNonNull(udfpsEnrollView);
                udfpsEnrollView.mHandler.post(new UdfpsEnrollView$$ExternalSyntheticLambda1(udfpsEnrollView, i2, i));
            }
        }
    }

    public final int getPaddingX() {
        return this.mEnrollProgressBarRadius;
    }

    public final int getPaddingY() {
        return this.mEnrollProgressBarRadius;
    }
}
