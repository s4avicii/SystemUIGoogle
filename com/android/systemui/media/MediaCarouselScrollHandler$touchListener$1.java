package com.android.systemui.media;

import android.view.MotionEvent;
import android.view.ViewGroup;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.systemui.Gefingerpoken;
import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler$touchListener$1 implements Gefingerpoken {
    public final /* synthetic */ MediaCarouselScrollHandler this$0;

    public MediaCarouselScrollHandler$touchListener$1(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
        this.this$0 = mediaCarouselScrollHandler;
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
        Intrinsics.checkNotNull(motionEvent);
        Objects.requireNonNull(mediaCarouselScrollHandler);
        return mediaCarouselScrollHandler.gestureDetector.onTouchEvent(motionEvent);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int i;
        boolean z2;
        float f;
        boolean z3;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
        Intrinsics.checkNotNull(motionEvent);
        Objects.requireNonNull(mediaCarouselScrollHandler);
        boolean z4 = true;
        if (motionEvent.getAction() == 1) {
            z = true;
        } else {
            z = false;
        }
        if (z && mediaCarouselScrollHandler.falsingProtectionNeeded) {
            mediaCarouselScrollHandler.falsingCollector.onNotificationStopDismissing();
        }
        if (mediaCarouselScrollHandler.gestureDetector.onTouchEvent(motionEvent)) {
            if (z) {
                mediaCarouselScrollHandler.scrollView.cancelCurrentScroll();
                return true;
            }
        } else if (z || motionEvent.getAction() == 3) {
            MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
            Objects.requireNonNull(mediaScrollView);
            int scrollX = mediaScrollView.getScrollX();
            ViewGroup viewGroup = null;
            if (mediaScrollView.isLayoutRtl()) {
                ViewGroup viewGroup2 = mediaScrollView.contentContainer;
                if (viewGroup2 == null) {
                    viewGroup2 = null;
                }
                scrollX = (viewGroup2.getWidth() - mediaScrollView.getWidth()) - scrollX;
            }
            int i2 = mediaCarouselScrollHandler.playerWidthPlusPadding;
            int i3 = scrollX % i2;
            if (i3 > i2 / 2) {
                i = i2 - i3;
            } else {
                i = i3 * -1;
            }
            if (i != 0) {
                if (mediaCarouselScrollHandler.scrollView.isLayoutRtl()) {
                    i = -i;
                }
                MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
                Objects.requireNonNull(mediaScrollView2);
                int scrollX2 = mediaScrollView2.getScrollX();
                if (mediaScrollView2.isLayoutRtl()) {
                    ViewGroup viewGroup3 = mediaScrollView2.contentContainer;
                    if (viewGroup3 != null) {
                        viewGroup = viewGroup3;
                    }
                    scrollX2 = (viewGroup.getWidth() - mediaScrollView2.getWidth()) - scrollX2;
                }
                mediaCarouselScrollHandler.mainExecutor.execute(new MediaCarouselScrollHandler$onTouch$1(mediaCarouselScrollHandler, scrollX2 + i));
            }
            float contentTranslation = mediaCarouselScrollHandler.scrollView.getContentTranslation();
            if (contentTranslation == 0.0f) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (!z2) {
                if (Math.abs(contentTranslation) >= ((float) (mediaCarouselScrollHandler.getMaxTranslation() / 2))) {
                    if (!mediaCarouselScrollHandler.falsingProtectionNeeded || !mediaCarouselScrollHandler.falsingManager.isFalseTouch(1)) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (!z3) {
                        z4 = false;
                    }
                }
                if (z4) {
                    f = 0.0f;
                } else {
                    f = Math.signum(contentTranslation) * ((float) mediaCarouselScrollHandler.getMaxTranslation());
                    if (!mediaCarouselScrollHandler.showsSettingsButton) {
                        mediaCarouselScrollHandler.mainExecutor.executeDelayed(new MediaCarouselScrollHandler$onTouch$2(mediaCarouselScrollHandler), 100);
                    }
                }
                Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
                PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(mediaCarouselScrollHandler);
                instance.spring(MediaCarouselScrollHandler.CONTENT_TRANSLATION, f, 0.0f, MediaCarouselScrollHandlerKt.translationConfig);
                instance.start();
                MediaScrollView mediaScrollView3 = mediaCarouselScrollHandler.scrollView;
                Objects.requireNonNull(mediaScrollView3);
                mediaScrollView3.animationTargetX = f;
            }
        }
        return false;
    }
}
