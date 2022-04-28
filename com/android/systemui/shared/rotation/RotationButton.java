package com.android.systemui.shared.rotation;

import android.graphics.drawable.Drawable;
import android.view.View;

public interface RotationButton {

    public interface RotationButtonUpdatesCallback {
    }

    View getCurrentView() {
        return null;
    }

    Drawable getImageDrawable() {
        return null;
    }

    boolean hide() {
        return false;
    }

    boolean isVisible() {
        return false;
    }

    void setCanShowRotationButton(boolean z) {
    }

    void setDarkIntensity(float f) {
    }

    void setOnClickListener(View.OnClickListener onClickListener) {
    }

    void setOnHoverListener(RotationButtonController$$ExternalSyntheticLambda0 rotationButtonController$$ExternalSyntheticLambda0) {
    }

    void setRotationButtonController(RotationButtonController rotationButtonController) {
    }

    void setUpdatesCallback(RotationButtonUpdatesCallback rotationButtonUpdatesCallback) {
    }

    boolean show() {
        return false;
    }

    void updateIcon(int i, int i2) {
    }

    boolean acceptRotationProposal() {
        if (getCurrentView() != null) {
            return true;
        }
        return false;
    }
}
