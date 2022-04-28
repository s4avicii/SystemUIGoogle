package com.android.systemui.media;

import android.graphics.Outline;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import androidx.core.view.GestureDetectorCompat;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.p006qs.PageIndicator;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: MediaCarouselScrollHandler.kt */
public final class MediaCarouselScrollHandler {
    public static final MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1 CONTENT_TRANSLATION = new MediaCarouselScrollHandler$Companion$CONTENT_TRANSLATION$1();
    public int carouselHeight;
    public int carouselWidth;
    public final Function1<Boolean, Unit> closeGuts;
    public float contentTranslation;
    public int cornerRadius;
    public final Function0<Unit> dismissCallback;
    public final FalsingCollector falsingCollector;
    public final FalsingManager falsingManager;
    public boolean falsingProtectionNeeded;
    public final GestureDetectorCompat gestureDetector;
    public final Function1<Boolean, Unit> logSmartspaceImpression;
    public final DelayableExecutor mainExecutor;
    public ViewGroup mediaContent;
    public final PageIndicator pageIndicator;
    public int playerWidthPlusPadding;
    public boolean qsExpanded;
    public int scrollIntoCurrentMedia;
    public final MediaScrollView scrollView;
    public View settingsButton;
    public boolean showsSettingsButton;
    public Function0<Unit> translationChangedListener;
    public int visibleMediaIndex;
    public boolean visibleToUser;

    public final int getMaxTranslation() {
        if (!this.showsSettingsButton) {
            return this.playerWidthPlusPadding;
        }
        View view = this.settingsButton;
        if (view == null) {
            view = null;
        }
        return view.getWidth();
    }

    public final void resetTranslation(boolean z) {
        boolean z2;
        if (this.scrollView.getContentTranslation() == 0.0f) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2) {
            return;
        }
        if (z) {
            Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
            PhysicsAnimator instance = PhysicsAnimator.Companion.getInstance(this);
            instance.spring(CONTENT_TRANSLATION, 0.0f, 0.0f, MediaCarouselScrollHandlerKt.translationConfig);
            instance.start();
            MediaScrollView mediaScrollView = this.scrollView;
            Objects.requireNonNull(mediaScrollView);
            mediaScrollView.animationTargetX = 0.0f;
            return;
        }
        Function1<Object, ? extends PhysicsAnimator<?>> function12 = PhysicsAnimator.instanceConstructor;
        PhysicsAnimator.Companion.getInstance(this).cancel();
        setContentTranslation(0.0f);
    }

    public final void scrollToPlayer(int i, int i2) {
        if (i >= 0 && i < this.mediaContent.getChildCount()) {
            MediaScrollView mediaScrollView = this.scrollView;
            int i3 = i * this.playerWidthPlusPadding;
            Objects.requireNonNull(mediaScrollView);
            if (mediaScrollView.isLayoutRtl()) {
                ViewGroup viewGroup = mediaScrollView.contentContainer;
                if (viewGroup == null) {
                    viewGroup = null;
                }
                i3 = (viewGroup.getWidth() - mediaScrollView.getWidth()) - i3;
            }
            mediaScrollView.setScrollX(i3);
        }
        this.mainExecutor.executeDelayed(new MediaCarouselScrollHandler$scrollToPlayer$1(this, this.mediaContent.getChildAt(Math.min(this.mediaContent.getChildCount() - 1, i2))), 100);
    }

    public final void setContentTranslation(float f) {
        boolean z;
        this.contentTranslation = f;
        this.mediaContent.setTranslationX(f);
        updateSettingsPresentation();
        this.translationChangedListener.invoke();
        boolean z2 = true;
        if (this.contentTranslation == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z && this.scrollIntoCurrentMedia == 0) {
            z2 = false;
        }
        this.scrollView.setClipToOutline(z2);
    }

    public final void updatePlayerVisibilities() {
        boolean z;
        boolean z2;
        int i;
        if (this.scrollIntoCurrentMedia != 0) {
            z = true;
        } else {
            z = false;
        }
        int childCount = this.mediaContent.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            int i3 = i2 + 1;
            View childAt = this.mediaContent.getChildAt(i2);
            int i4 = this.visibleMediaIndex;
            if (i2 == i4 || (i2 == i4 + 1 && z)) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                i = 0;
            } else {
                i = 4;
            }
            childAt.setVisibility(i);
            i2 = i3;
        }
    }

    public final void updateSettingsPresentation() {
        boolean z;
        int i = 4;
        View view = null;
        if (this.showsSettingsButton) {
            View view2 = this.settingsButton;
            if (view2 == null) {
                view2 = null;
            }
            if (view2.getWidth() > 0) {
                float map = MathUtils.map(0.0f, (float) getMaxTranslation(), 0.0f, 1.0f, Math.abs(this.contentTranslation));
                float f = 1.0f - map;
                View view3 = this.settingsButton;
                if (view3 == null) {
                    view3 = null;
                }
                float f2 = ((float) (-view3.getWidth())) * f * 0.3f;
                if (this.scrollView.isLayoutRtl()) {
                    if (this.contentTranslation > 0.0f) {
                        float width = ((float) this.scrollView.getWidth()) - f2;
                        View view4 = this.settingsButton;
                        if (view4 == null) {
                            view4 = null;
                        }
                        f2 = -(width - ((float) view4.getWidth()));
                    } else {
                        f2 = -f2;
                    }
                } else if (this.contentTranslation <= 0.0f) {
                    float width2 = ((float) this.scrollView.getWidth()) - f2;
                    View view5 = this.settingsButton;
                    if (view5 == null) {
                        view5 = null;
                    }
                    f2 = width2 - ((float) view5.getWidth());
                }
                float f3 = f * ((float) 50);
                View view6 = this.settingsButton;
                if (view6 == null) {
                    view6 = null;
                }
                view6.setRotation(f3 * (-Math.signum(this.contentTranslation)));
                float saturate = MathUtils.saturate(MathUtils.map(0.5f, 1.0f, 0.0f, 1.0f, map));
                View view7 = this.settingsButton;
                if (view7 == null) {
                    view7 = null;
                }
                view7.setAlpha(saturate);
                View view8 = this.settingsButton;
                if (view8 == null) {
                    view8 = null;
                }
                if (saturate == 0.0f) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    i = 0;
                }
                view8.setVisibility(i);
                View view9 = this.settingsButton;
                if (view9 == null) {
                    view9 = null;
                }
                view9.setTranslationX(f2);
                View view10 = this.settingsButton;
                if (view10 == null) {
                    view10 = null;
                }
                int height = this.scrollView.getHeight();
                View view11 = this.settingsButton;
                if (view11 != null) {
                    view = view11;
                }
                view10.setTranslationY(((float) (height - view.getHeight())) / 2.0f);
                return;
            }
        }
        View view12 = this.settingsButton;
        if (view12 != null) {
            view = view12;
        }
        view.setVisibility(4);
    }

    public MediaCarouselScrollHandler(MediaScrollView mediaScrollView, PageIndicator pageIndicator2, DelayableExecutor delayableExecutor, Function0<Unit> function0, Function0<Unit> function02, Function1<? super Boolean, Unit> function1, FalsingCollector falsingCollector2, FalsingManager falsingManager2, Function1<? super Boolean, Unit> function12) {
        this.scrollView = mediaScrollView;
        this.pageIndicator = pageIndicator2;
        this.mainExecutor = delayableExecutor;
        this.dismissCallback = function0;
        this.translationChangedListener = function02;
        this.closeGuts = function1;
        this.falsingCollector = falsingCollector2;
        this.falsingManager = falsingManager2;
        this.logSmartspaceImpression = function12;
        MediaCarouselScrollHandler$gestureListener$1 mediaCarouselScrollHandler$gestureListener$1 = new MediaCarouselScrollHandler$gestureListener$1(this);
        MediaCarouselScrollHandler$touchListener$1 mediaCarouselScrollHandler$touchListener$1 = new MediaCarouselScrollHandler$touchListener$1(this);
        MediaCarouselScrollHandler$scrollChangedListener$1 mediaCarouselScrollHandler$scrollChangedListener$1 = new MediaCarouselScrollHandler$scrollChangedListener$1(this);
        this.gestureDetector = new GestureDetectorCompat(mediaScrollView.getContext(), mediaCarouselScrollHandler$gestureListener$1);
        mediaScrollView.touchListener = mediaCarouselScrollHandler$touchListener$1;
        mediaScrollView.setOverScrollMode(2);
        ViewGroup viewGroup = mediaScrollView.contentContainer;
        this.mediaContent = viewGroup == null ? null : viewGroup;
        mediaScrollView.setOnScrollChangeListener(mediaCarouselScrollHandler$scrollChangedListener$1);
        mediaScrollView.setOutlineProvider(new ViewOutlineProvider(this) {
            public final /* synthetic */ MediaCarouselScrollHandler this$0;

            {
                this.this$0 = r1;
            }

            public final void getOutline(View view, Outline outline) {
                if (outline != null) {
                    MediaCarouselScrollHandler mediaCarouselScrollHandler = this.this$0;
                    outline.setRoundRect(0, 0, mediaCarouselScrollHandler.carouselWidth, mediaCarouselScrollHandler.carouselHeight, (float) mediaCarouselScrollHandler.cornerRadius);
                }
            }
        });
    }

    public final void onPlayersChanged() {
        int i;
        updatePlayerVisibilities();
        int dimensionPixelSize = this.scrollView.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_media_padding);
        int childCount = this.mediaContent.getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            int i3 = i2 + 1;
            View childAt = this.mediaContent.getChildAt(i2);
            if (i2 == childCount - 1) {
                i = 0;
            } else {
                i = dimensionPixelSize;
            }
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            if (marginLayoutParams.getMarginEnd() != i) {
                marginLayoutParams.setMarginEnd(i);
                childAt.setLayoutParams(marginLayoutParams);
            }
            i2 = i3;
        }
    }
}
