package com.android.systemui.volume;

import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;
import com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda6 implements View.OnClickListener {
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda6(VolumeDialogImpl volumeDialogImpl) {
        this.f$0 = volumeDialogImpl;
    }

    public final void onClick(View view) {
        int i;
        int i2;
        int i3;
        long j;
        int i4;
        VolumeDialogImpl volumeDialogImpl = this.f$0;
        Objects.requireNonNull(volumeDialogImpl);
        boolean z = volumeDialogImpl.mIsRingerDrawerOpen;
        if (z) {
            volumeDialogImpl.hideRingerDrawer();
        } else if (!z) {
            ImageView imageView = volumeDialogImpl.mRingerDrawerVibrateIcon;
            int i5 = 4;
            if (volumeDialogImpl.mState.ringerModeInternal == 1) {
                i = 4;
            } else {
                i = 0;
            }
            imageView.setVisibility(i);
            ImageView imageView2 = volumeDialogImpl.mRingerDrawerMuteIcon;
            if (volumeDialogImpl.mState.ringerModeInternal == 0) {
                i2 = 4;
            } else {
                i2 = 0;
            }
            imageView2.setVisibility(i2);
            ImageView imageView3 = volumeDialogImpl.mRingerDrawerNormalIcon;
            if (volumeDialogImpl.mState.ringerModeInternal != 2) {
                i5 = 0;
            }
            imageView3.setVisibility(i5);
            volumeDialogImpl.mRingerDrawerNewSelectionBg.setAlpha(0.0f);
            if (!volumeDialogImpl.isLandscape()) {
                volumeDialogImpl.mRingerDrawerNewSelectionBg.setTranslationY(volumeDialogImpl.getTranslationInDrawerForRingerMode(volumeDialogImpl.mState.ringerModeInternal));
            } else {
                volumeDialogImpl.mRingerDrawerNewSelectionBg.setTranslationX(volumeDialogImpl.getTranslationInDrawerForRingerMode(volumeDialogImpl.mState.ringerModeInternal));
            }
            if (!volumeDialogImpl.isLandscape()) {
                volumeDialogImpl.mRingerDrawerContainer.setTranslationY((float) ((volumeDialogImpl.mRingerCount - 1) * volumeDialogImpl.mRingerDrawerItemSize));
            } else {
                volumeDialogImpl.mRingerDrawerContainer.setTranslationX((float) ((volumeDialogImpl.mRingerCount - 1) * volumeDialogImpl.mRingerDrawerItemSize));
            }
            volumeDialogImpl.mRingerDrawerContainer.setAlpha(0.0f);
            volumeDialogImpl.mRingerDrawerContainer.setVisibility(0);
            if (volumeDialogImpl.mState.ringerModeInternal == 1) {
                i3 = 175;
            } else {
                i3 = 250;
            }
            ViewPropertyAnimator animate = volumeDialogImpl.mRingerDrawerContainer.animate();
            PathInterpolator pathInterpolator = Interpolators.FAST_OUT_SLOW_IN;
            long j2 = (long) i3;
            ViewPropertyAnimator duration = animate.setInterpolator(pathInterpolator).setDuration(j2);
            if (volumeDialogImpl.mState.ringerModeInternal == 1) {
                j = 75;
            } else {
                j = 0;
            }
            duration.setStartDelay(j).alpha(1.0f).translationX(0.0f).translationY(0.0f).start();
            volumeDialogImpl.mSelectedRingerContainer.animate().setInterpolator(pathInterpolator).setDuration(250).withEndAction(new LockIconViewController$$ExternalSyntheticLambda2(volumeDialogImpl, 6));
            volumeDialogImpl.mAnimateUpBackgroundToMatchDrawer.setDuration(j2);
            volumeDialogImpl.mAnimateUpBackgroundToMatchDrawer.setInterpolator(pathInterpolator);
            volumeDialogImpl.mAnimateUpBackgroundToMatchDrawer.start();
            if (!volumeDialogImpl.isLandscape()) {
                volumeDialogImpl.mSelectedRingerContainer.animate().translationY(volumeDialogImpl.getTranslationInDrawerForRingerMode(volumeDialogImpl.mState.ringerModeInternal)).start();
            } else {
                volumeDialogImpl.mSelectedRingerContainer.animate().translationX(volumeDialogImpl.getTranslationInDrawerForRingerMode(volumeDialogImpl.mState.ringerModeInternal)).start();
            }
            ViewGroup viewGroup = volumeDialogImpl.mSelectedRingerContainer;
            ContextThemeWrapper contextThemeWrapper = volumeDialogImpl.mContext;
            int i6 = volumeDialogImpl.mState.ringerModeInternal;
            if (i6 == 0) {
                i4 = C1777R.string.volume_ringer_status_silent;
            } else if (i6 != 1) {
                i4 = C1777R.string.volume_ringer_status_normal;
            } else {
                i4 = C1777R.string.volume_ringer_status_vibrate;
            }
            viewGroup.setContentDescription(contextThemeWrapper.getString(i4));
            volumeDialogImpl.mIsRingerDrawerOpen = true;
        }
    }
}
