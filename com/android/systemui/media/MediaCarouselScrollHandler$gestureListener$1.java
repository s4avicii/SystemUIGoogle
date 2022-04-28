package com.android.systemui.media;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler$gestureListener$1 extends GestureDetector.SimpleOnGestureListener {
    public final /* synthetic */ MediaCarouselScrollHandler this$0;

    public MediaCarouselScrollHandler$gestureListener$1(MediaCarouselScrollHandler mediaCarouselScrollHandler) {
        this.this$0 = mediaCarouselScrollHandler;
    }

    public final boolean onDown(MotionEvent motionEvent) {
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
        Objects.requireNonNull(mediaCarouselScrollHandler);
        if (!mediaCarouselScrollHandler.falsingProtectionNeeded) {
            return false;
        }
        this.this$0.falsingCollector.onNotificationStartDismissing();
        return false;
    }

    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        boolean z;
        int i;
        boolean z2;
        boolean z3;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
        Objects.requireNonNull(mediaCarouselScrollHandler);
        float f3 = f * f;
        double d = (double) f2;
        boolean z4 = false;
        if (((double) f3) < 0.5d * d * d || f3 < 1000000.0f) {
            return false;
        }
        float contentTranslation = mediaCarouselScrollHandler.scrollView.getContentTranslation();
        float f4 = 0.0f;
        if (contentTranslation == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            if (Math.signum(f) == Math.signum(contentTranslation)) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3) {
                if (mediaCarouselScrollHandler.falsingProtectionNeeded && mediaCarouselScrollHandler.falsingManager.isFalseTouch(1)) {
                    z4 = true;
                }
                if (!z4) {
                    f4 = ((float) mediaCarouselScrollHandler.getMaxTranslation()) * Math.signum(contentTranslation);
                    if (!mediaCarouselScrollHandler.showsSettingsButton) {
                        mediaCarouselScrollHandler.mainExecutor.executeDelayed(new MediaCarouselScrollHandler$onFling$1(mediaCarouselScrollHandler), 100);
                    }
                }
            }
            Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
            PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(mediaCarouselScrollHandler);
            instance.spring(MediaCarouselScrollHandler.CONTENT_TRANSLATION, f4, f, MediaCarouselScrollHandlerKt.translationConfig);
            instance.start();
            MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
            Objects.requireNonNull(mediaScrollView);
            mediaScrollView.animationTargetX = f4;
        } else {
            MediaScrollView mediaScrollView2 = mediaCarouselScrollHandler.scrollView;
            Objects.requireNonNull(mediaScrollView2);
            int scrollX = mediaScrollView2.getScrollX();
            if (mediaScrollView2.isLayoutRtl()) {
                ViewGroup viewGroup = mediaScrollView2.contentContainer;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                scrollX = (viewGroup.getWidth() - mediaScrollView2.getWidth()) - scrollX;
            }
            int i2 = mediaCarouselScrollHandler.playerWidthPlusPadding;
            if (i2 > 0) {
                i = scrollX / i2;
            } else {
                i = 0;
            }
            if (!mediaCarouselScrollHandler.scrollView.isLayoutRtl() ? f >= 0.0f : f <= 0.0f) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                i++;
            }
            mediaCarouselScrollHandler.mainExecutor.execute(new MediaCarouselScrollHandler$onFling$2(mediaCarouselScrollHandler, mediaCarouselScrollHandler.mediaContent.getChildAt(Math.min(mediaCarouselScrollHandler.mediaContent.getChildCount() - 1, Math.max(0, i)))));
        }
        return true;
    }

    public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        boolean z;
        boolean z2;
        boolean z3;
        MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
        Intrinsics.checkNotNull(motionEvent);
        Intrinsics.checkNotNull(motionEvent2);
        Objects.requireNonNull(mediaCarouselScrollHandler);
        float x = motionEvent2.getX() - motionEvent.getX();
        float contentTranslation = mediaCarouselScrollHandler.scrollView.getContentTranslation();
        int i = (contentTranslation > 0.0f ? 1 : (contentTranslation == 0.0f ? 0 : -1));
        boolean z4 = false;
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        if (z && mediaCarouselScrollHandler.scrollView.canScrollHorizontally((int) (-x))) {
            return false;
        }
        float f3 = contentTranslation - f;
        float abs = Math.abs(f3);
        if (abs > ((float) mediaCarouselScrollHandler.getMaxTranslation())) {
            if (Math.signum(f) == Math.signum(contentTranslation)) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (!z3) {
                if (Math.abs(contentTranslation) > ((float) mediaCarouselScrollHandler.getMaxTranslation())) {
                    f3 = contentTranslation - (f * 0.2f);
                } else {
                    f3 = Math.signum(f3) * (((abs - ((float) mediaCarouselScrollHandler.getMaxTranslation())) * 0.2f) + ((float) mediaCarouselScrollHandler.getMaxTranslation()));
                }
            }
        }
        if (Math.signum(f3) == Math.signum(contentTranslation)) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2) {
            if (i == 0) {
                z4 = true;
            }
            if (!z4 && mediaCarouselScrollHandler.scrollView.canScrollHorizontally(-((int) f3))) {
                f3 = 0.0f;
            }
        }
        Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
        PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(mediaCarouselScrollHandler);
        if (instance.isRunning()) {
            instance.spring(MediaCarouselScrollHandler.CONTENT_TRANSLATION, f3, 0.0f, MediaCarouselScrollHandlerKt.translationConfig);
            instance.start();
        } else {
            mediaCarouselScrollHandler.setContentTranslation(f3);
        }
        MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
        Objects.requireNonNull(mediaScrollView);
        mediaScrollView.animationTargetX = f3;
        return true;
    }
}
