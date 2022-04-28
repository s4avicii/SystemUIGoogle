package com.android.systemui.media;

import android.content.res.Configuration;
import android.view.ViewGroup;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.Objects;

/* compiled from: MediaCarouselController.kt */
public final class MediaCarouselController$configListener$1 implements ConfigurationController.ConfigurationListener {
    public final /* synthetic */ MediaCarouselController this$0;

    public MediaCarouselController$configListener$1(MediaCarouselController mediaCarouselController) {
        this.this$0 = mediaCarouselController;
    }

    public final void onConfigChanged(Configuration configuration) {
        if (configuration != null) {
            MediaCarouselController mediaCarouselController = this.this$0;
            boolean z = true;
            int i = 0;
            if (configuration.getLayoutDirection() != 1) {
                z = false;
            }
            Objects.requireNonNull(mediaCarouselController);
            if (z != mediaCarouselController.isRtl) {
                mediaCarouselController.isRtl = z;
                mediaCarouselController.mediaFrame.setLayoutDirection(z ? 1 : 0);
                MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController.mediaCarouselScrollHandler;
                Objects.requireNonNull(mediaCarouselScrollHandler);
                MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
                Objects.requireNonNull(mediaScrollView);
                if (mediaScrollView.isLayoutRtl()) {
                    ViewGroup viewGroup = mediaScrollView.contentContainer;
                    if (viewGroup == null) {
                        viewGroup = null;
                    }
                    i = 0 + (viewGroup.getWidth() - mediaScrollView.getWidth());
                }
                mediaScrollView.setScrollX(i);
            }
        }
    }

    public final void onDensityOrFontScaleChanged() {
        MediaCarouselController.access$recreatePlayers(this.this$0);
        this.this$0.inflateSettingsButton();
    }

    public final void onThemeChanged() {
        MediaCarouselController.access$recreatePlayers(this.this$0);
        this.this$0.inflateSettingsButton();
    }

    public final void onUiModeChanged() {
        MediaCarouselController.access$recreatePlayers(this.this$0);
        this.this$0.inflateSettingsButton();
    }
}
