package com.android.systemui.media;

import android.app.PendingIntent;
import android.view.View;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class MediaControlPanel$$ExternalSyntheticLambda8 implements View.OnClickListener {
    public final /* synthetic */ MediaControlPanel f$0;
    public final /* synthetic */ PendingIntent f$1;

    public /* synthetic */ MediaControlPanel$$ExternalSyntheticLambda8(MediaControlPanel mediaControlPanel, PendingIntent pendingIntent) {
        this.f$0 = mediaControlPanel;
        this.f$1 = pendingIntent;
    }

    public final void onClick(View view) {
        MediaControlPanel mediaControlPanel = this.f$0;
        PendingIntent pendingIntent = this.f$1;
        Objects.requireNonNull(mediaControlPanel);
        if (!mediaControlPanel.mFalsingManager.isFalseTap(1)) {
            MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
            Objects.requireNonNull(mediaViewController);
            if (!mediaViewController.isGutsVisible) {
                mediaControlPanel.logSmartspaceCardReported(760, false, 0, 0);
                ActivityStarter activityStarter = mediaControlPanel.mActivityStarter;
                MediaViewHolder mediaViewHolder = mediaControlPanel.mMediaViewHolder;
                Objects.requireNonNull(mediaViewHolder);
                activityStarter.postStartActivityDismissingKeyguard(pendingIntent, (ActivityLaunchAnimator.Controller) MediaControlPanel.buildLaunchAnimatorController(mediaViewHolder.player));
            }
        }
    }
}
