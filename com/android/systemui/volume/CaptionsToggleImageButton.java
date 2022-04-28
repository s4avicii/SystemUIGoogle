package com.android.systemui.volume;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.android.keyguard.AlphaOptimizedImageButton;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class CaptionsToggleImageButton extends AlphaOptimizedImageButton {
    public static final int[] OPTED_OUT_STATE = {C1777R.attr.optedOut};
    public boolean mCaptionsEnabled = false;
    public ConfirmedTapListener mConfirmedTapListener;
    public GestureDetector mGestureDetector;
    public C17131 mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            CaptionsToggleImageButton captionsToggleImageButton = CaptionsToggleImageButton.this;
            int[] iArr = CaptionsToggleImageButton.OPTED_OUT_STATE;
            return captionsToggleImageButton.tryToSendTapConfirmedEvent();
        }
    };
    public boolean mOptedOut = false;

    public interface ConfirmedTapListener {
    }

    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.mOptedOut) {
            View.mergeDrawableStates(onCreateDrawableState, OPTED_OUT_STATE);
        }
        return onCreateDrawableState;
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        return super.onTouchEvent(motionEvent);
    }

    public final boolean tryToSendTapConfirmedEvent() {
        ConfirmedTapListener confirmedTapListener = this.mConfirmedTapListener;
        if (confirmedTapListener == null) {
            return false;
        }
        VolumeDialogImpl$$ExternalSyntheticLambda9 volumeDialogImpl$$ExternalSyntheticLambda9 = (VolumeDialogImpl$$ExternalSyntheticLambda9) confirmedTapListener;
        Objects.requireNonNull(volumeDialogImpl$$ExternalSyntheticLambda9);
        VolumeDialogImpl volumeDialogImpl = (VolumeDialogImpl) volumeDialogImpl$$ExternalSyntheticLambda9.f$0;
        Objects.requireNonNull(volumeDialogImpl);
        volumeDialogImpl.mController.setCaptionsEnabled(!volumeDialogImpl.mController.areCaptionsEnabled());
        volumeDialogImpl.updateCaptionsIcon();
        Events.writeEvent(21, new Object[0]);
        return true;
    }

    public CaptionsToggleImageButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setContentDescription(getContext().getString(C1777R.string.volume_odi_captions_content_description));
    }
}
