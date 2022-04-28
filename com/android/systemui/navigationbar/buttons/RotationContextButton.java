package com.android.systemui.navigationbar.buttons;

import android.content.Context;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.shared.rotation.RotationButton;
import com.android.systemui.shared.rotation.RotationButtonController;
import java.util.Objects;

public final class RotationContextButton extends ContextualButton implements RotationButton {
    public RotationButtonController mRotationButtonController;

    public final boolean acceptRotationProposal() {
        View view = this.mCurrentView;
        if (view == null || !view.isAttachedToWindow()) {
            return false;
        }
        return true;
    }

    public final KeyButtonDrawable getNewDrawable(int i, int i2) {
        RotationButtonController rotationButtonController = this.mRotationButtonController;
        Objects.requireNonNull(rotationButtonController);
        Context context = rotationButtonController.mContext;
        RotationButtonController rotationButtonController2 = this.mRotationButtonController;
        Objects.requireNonNull(rotationButtonController2);
        return KeyButtonDrawable.create(context, i, i2, rotationButtonController2.mIconResId, false);
    }

    public final void setUpdatesCallback(RotationButton.RotationButtonUpdatesCallback rotationButtonUpdatesCallback) {
        this.mListener = new RotationContextButton$$ExternalSyntheticLambda0(rotationButtonUpdatesCallback);
    }

    public RotationContextButton(Context context) {
        super(C1777R.C1779id.rotate_suggestion, context, C1777R.C1778drawable.ic_sysbar_rotate_button_ccw_start_0);
    }

    public final void setVisibility(int i) {
        super.setVisibility(i);
        KeyButtonDrawable keyButtonDrawable = this.mImageDrawable;
        if (i == 0 && keyButtonDrawable != null) {
            AnimatedVectorDrawable animatedVectorDrawable = keyButtonDrawable.mAnimatedDrawable;
            if (animatedVectorDrawable != null) {
                animatedVectorDrawable.reset();
            }
            AnimatedVectorDrawable animatedVectorDrawable2 = keyButtonDrawable.mAnimatedDrawable;
            if (animatedVectorDrawable2 != null) {
                animatedVectorDrawable2.start();
            }
        }
    }

    public final void setRotationButtonController(RotationButtonController rotationButtonController) {
        this.mRotationButtonController = rotationButtonController;
    }

    public final Drawable getImageDrawable() {
        return this.mImageDrawable;
    }
}
