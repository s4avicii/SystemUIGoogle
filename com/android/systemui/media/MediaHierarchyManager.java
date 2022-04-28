package com.android.systemui.media;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import com.android.keyguard.KeyguardViewController;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda20;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.Utils;
import java.util.Objects;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaHierarchyManager.kt */
public final class MediaHierarchyManager {
    public float animationCrossFadeProgress;
    public boolean animationPending;
    public float animationStartAlpha;
    public Rect animationStartBounds = new Rect();
    public float animationStartCrossFadeProgress;
    public ValueAnimator animator;
    public final KeyguardBypassController bypassController;
    public float carouselAlpha;
    public boolean collapsingShadeFromQS;
    public final Context context;
    public int crossFadeAnimationEndLocation = -1;
    public int crossFadeAnimationStartLocation = -1;
    public int currentAttachmentLocation;
    public Rect currentBounds = new Rect();
    public int desiredLocation;
    public int distanceForFullShadeTransition;
    public boolean dozeAnimationRunning;
    public boolean dreamOverlayActive;
    public final DreamOverlayStateController dreamOverlayStateController;
    public float fullShadeTransitionProgress;
    public boolean fullyAwake;
    public boolean goingToSleep;
    public boolean inSplitShade;
    public boolean isCrossFadeAnimatorRunning;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final MediaCarouselController mediaCarouselController;
    public final MediaHost[] mediaHosts;
    public final NotificationLockscreenUserManager notifLockscreenUserManager;
    public int previousLocation;
    public boolean qsExpanded;
    public float qsExpansion;
    public ViewGroupOverlay rootOverlay;
    public View rootView;
    public final MediaHierarchyManager$startAnimation$1 startAnimation;
    public final SysuiStatusBarStateController statusBarStateController;
    public int statusbarState;
    public Rect targetBounds = new Rect();

    public final Pair<Long, Long> getAnimationParams(int i, int i2) {
        long j;
        long j2 = 0;
        if (i == 2 && i2 == 1) {
            if (this.statusbarState == 0 && this.keyguardStateController.isKeyguardFadingAway()) {
                j2 = this.keyguardStateController.getKeyguardFadingAwayDelay();
            }
            j = 224;
        } else if (i == 1 && i2 == 2) {
            j = 464;
        } else {
            j = 200;
        }
        return new Pair<>(Long.valueOf(j), Long.valueOf(j2));
    }

    public static Rect interpolateBounds(Rect rect, Rect rect2, float f, Rect rect3) {
        int lerp = (int) MathUtils.lerp((float) rect.left, (float) rect2.left, f);
        int lerp2 = (int) MathUtils.lerp((float) rect.top, (float) rect2.top, f);
        int lerp3 = (int) MathUtils.lerp((float) rect.right, (float) rect2.right, f);
        int lerp4 = (int) MathUtils.lerp((float) rect.bottom, (float) rect2.bottom, f);
        if (rect3 == null) {
            rect3 = new Rect();
        }
        rect3.set(lerp, lerp2, lerp3, lerp4);
        return rect3;
    }

    public static /* synthetic */ void updateDesiredLocation$default(MediaHierarchyManager mediaHierarchyManager, boolean z, int i) {
        if ((i & 1) != 0) {
            z = false;
        }
        mediaHierarchyManager.updateDesiredLocation(z, false);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:104:0x015d, code lost:
        if (r13 != false) goto L_0x015f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x0077, code lost:
        if (r13 == false) goto L_0x00fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00ef, code lost:
        if (r11 != r13) goto L_0x00f1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0152  */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x017d  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x017f  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0184  */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x01ee  */
    /* JADX WARNING: Removed duplicated region for block: B:136:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x00ff  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0111  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x011c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void applyState(android.graphics.Rect r11, float r12, boolean r13) {
        /*
            r10 = this;
            android.graphics.Rect r0 = r10.currentBounds
            r0.set(r11)
            boolean r11 = r10.isCurrentlyFading()
            r0 = 1065353216(0x3f800000, float:1.0)
            if (r11 == 0) goto L_0x000e
            goto L_0x000f
        L_0x000e:
            r12 = r0
        L_0x000f:
            float r11 = r10.carouselAlpha
            int r11 = (r11 > r12 ? 1 : (r11 == r12 ? 0 : -1))
            r1 = 1
            r2 = 0
            if (r11 != 0) goto L_0x0019
            r11 = r1
            goto L_0x001a
        L_0x0019:
            r11 = r2
        L_0x001a:
            if (r11 == 0) goto L_0x001d
            goto L_0x0026
        L_0x001d:
            r10.carouselAlpha = r12
            android.view.ViewGroup r11 = r10.getMediaFrame()
            androidx.leanback.R$raw.fadeIn((android.view.View) r11, (float) r12, (boolean) r2)
        L_0x0026:
            boolean r11 = r10.isCurrentlyInGuidedTransformation()
            if (r11 == 0) goto L_0x0035
            boolean r11 = r10.isCurrentlyFading()
            if (r11 == 0) goto L_0x0033
            goto L_0x0035
        L_0x0033:
            r11 = r2
            goto L_0x0036
        L_0x0035:
            r11 = r1
        L_0x0036:
            r12 = -1
            if (r11 == 0) goto L_0x003b
            r3 = r12
            goto L_0x003d
        L_0x003b:
            int r3 = r10.previousLocation
        L_0x003d:
            if (r11 == 0) goto L_0x0041
            r11 = r0
            goto L_0x0045
        L_0x0041:
            float r11 = r10.getTransformationProgress()
        L_0x0045:
            boolean r4 = r10.isCrossFadeAnimatorRunning
            r5 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            if (r4 == 0) goto L_0x005d
            float r4 = r10.animationCrossFadeProgress
            double r7 = (double) r4
            int r4 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r4 > 0) goto L_0x005a
            int r4 = r10.previousLocation
            if (r4 != r12) goto L_0x0057
            goto L_0x005a
        L_0x0057:
            int r4 = r10.crossFadeAnimationStartLocation
            goto L_0x005f
        L_0x005a:
            int r4 = r10.crossFadeAnimationEndLocation
            goto L_0x005f
        L_0x005d:
            int r4 = r10.desiredLocation
        L_0x005f:
            com.android.systemui.media.MediaCarouselController r7 = r10.mediaCarouselController
            java.util.Objects.requireNonNull(r7)
            int r8 = r7.currentStartLocation
            if (r3 != r8) goto L_0x0079
            int r8 = r7.currentEndLocation
            if (r4 != r8) goto L_0x0079
            float r8 = r7.currentTransitionProgress
            int r8 = (r11 > r8 ? 1 : (r11 == r8 ? 0 : -1))
            if (r8 != 0) goto L_0x0074
            r8 = r1
            goto L_0x0075
        L_0x0074:
            r8 = r2
        L_0x0075:
            if (r8 == 0) goto L_0x0079
            if (r13 == 0) goto L_0x00fb
        L_0x0079:
            r7.currentStartLocation = r3
            r7.currentEndLocation = r4
            r7.currentTransitionProgress = r11
            com.android.systemui.media.MediaPlayerData r11 = com.android.systemui.media.MediaPlayerData.INSTANCE
            java.util.Objects.requireNonNull(r11)
            java.util.Collection r11 = com.android.systemui.media.MediaPlayerData.players()
            java.util.Iterator r11 = r11.iterator()
        L_0x008c:
            boolean r3 = r11.hasNext()
            if (r3 == 0) goto L_0x00a7
            java.lang.Object r3 = r11.next()
            com.android.systemui.media.MediaControlPanel r3 = (com.android.systemui.media.MediaControlPanel) r3
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.media.MediaViewController r3 = r3.mMediaViewController
            int r4 = r7.currentStartLocation
            int r8 = r7.currentEndLocation
            float r9 = r7.currentTransitionProgress
            r3.setCurrentState(r4, r8, r9, r13)
            goto L_0x008c
        L_0x00a7:
            com.android.systemui.media.MediaHostStatesManager r11 = r7.mediaHostStatesManager
            java.util.Objects.requireNonNull(r11)
            java.util.LinkedHashMap r11 = r11.mediaHostStates
            int r13 = r7.currentEndLocation
            java.lang.Integer r13 = java.lang.Integer.valueOf(r13)
            java.lang.Object r13 = r11.get(r13)
            com.android.systemui.media.MediaHostState r13 = (com.android.systemui.media.MediaHostState) r13
            if (r13 != 0) goto L_0x00be
            r13 = r1
            goto L_0x00c2
        L_0x00be:
            boolean r13 = r13.getShowsOnlyActiveMedia()
        L_0x00c2:
            int r3 = r7.currentStartLocation
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.lang.Object r11 = r11.get(r3)
            com.android.systemui.media.MediaHostState r11 = (com.android.systemui.media.MediaHostState) r11
            if (r11 != 0) goto L_0x00d2
            r11 = r13
            goto L_0x00d6
        L_0x00d2:
            boolean r11 = r11.getShowsOnlyActiveMedia()
        L_0x00d6:
            boolean r3 = r7.currentlyShowingOnlyActive
            if (r3 != r13) goto L_0x00f1
            float r3 = r7.currentTransitionProgress
            int r4 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
            if (r4 != 0) goto L_0x00e2
            r4 = r1
            goto L_0x00e3
        L_0x00e2:
            r4 = r2
        L_0x00e3:
            if (r4 != 0) goto L_0x00f8
            r4 = 0
            int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
            if (r3 != 0) goto L_0x00ec
            r3 = r1
            goto L_0x00ed
        L_0x00ec:
            r3 = r2
        L_0x00ed:
            if (r3 != 0) goto L_0x00f8
            if (r11 == r13) goto L_0x00f8
        L_0x00f1:
            r7.currentlyShowingOnlyActive = r13
            com.android.systemui.media.MediaCarouselScrollHandler r11 = r7.mediaCarouselScrollHandler
            r11.resetTranslation(r1)
        L_0x00f8:
            r7.updatePageIndicatorAlpha()
        L_0x00fb:
            boolean r11 = r10.isCrossFadeAnimatorRunning
            if (r11 == 0) goto L_0x0111
            float r11 = r10.animationCrossFadeProgress
            double r3 = (double) r11
            int r11 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r11 > 0) goto L_0x010e
            int r11 = r10.previousLocation
            if (r11 != r12) goto L_0x010b
            goto L_0x010e
        L_0x010b:
            int r11 = r10.crossFadeAnimationStartLocation
            goto L_0x0113
        L_0x010e:
            int r11 = r10.crossFadeAnimationEndLocation
            goto L_0x0113
        L_0x0111:
            int r11 = r10.desiredLocation
        L_0x0113:
            boolean r12 = r10.isCurrentlyFading()
            r12 = r12 ^ r1
            boolean r13 = r10.isCrossFadeAnimatorRunning
            if (r13 == 0) goto L_0x014c
            com.android.systemui.media.MediaHost r13 = r10.getHost(r11)
            if (r13 != 0) goto L_0x0123
            goto L_0x012b
        L_0x0123:
            boolean r13 = r13.getVisible()
            if (r13 != r1) goto L_0x012b
            r13 = r1
            goto L_0x012c
        L_0x012b:
            r13 = r2
        L_0x012c:
            if (r13 == 0) goto L_0x014c
            com.android.systemui.media.MediaHost r13 = r10.getHost(r11)
            if (r13 != 0) goto L_0x0135
            goto L_0x0144
        L_0x0135:
            com.android.systemui.util.animation.UniqueObjectHostView r13 = r13.getHostView()
            if (r13 != 0) goto L_0x013c
            goto L_0x0144
        L_0x013c:
            boolean r13 = r13.isShown()
            if (r13 != 0) goto L_0x0144
            r13 = r1
            goto L_0x0145
        L_0x0144:
            r13 = r2
        L_0x0145:
            if (r13 == 0) goto L_0x014c
            int r13 = r10.desiredLocation
            if (r11 == r13) goto L_0x014c
            r12 = r1
        L_0x014c:
            boolean r13 = r10.isCurrentlyInGuidedTransformation()
            if (r13 == 0) goto L_0x015f
            float r13 = r10.getTransformationProgress()
            int r13 = (r13 > r0 ? 1 : (r13 == r0 ? 0 : -1))
            if (r13 != 0) goto L_0x015c
            r13 = r1
            goto L_0x015d
        L_0x015c:
            r13 = r2
        L_0x015d:
            if (r13 == 0) goto L_0x016e
        L_0x015f:
            android.animation.ValueAnimator r13 = r10.animator
            boolean r13 = r13.isRunning()
            if (r13 != 0) goto L_0x016e
            boolean r13 = r10.animationPending
            if (r13 == 0) goto L_0x016c
            goto L_0x016e
        L_0x016c:
            r13 = r2
            goto L_0x016f
        L_0x016e:
            r13 = r1
        L_0x016f:
            if (r13 == 0) goto L_0x0178
            android.view.ViewGroupOverlay r13 = r10.rootOverlay
            if (r13 == 0) goto L_0x0178
            if (r12 == 0) goto L_0x0178
            goto L_0x0179
        L_0x0178:
            r1 = r2
        L_0x0179:
            r12 = -1000(0xfffffffffffffc18, float:NaN)
            if (r1 == 0) goto L_0x017f
            r3 = r12
            goto L_0x0180
        L_0x017f:
            r3 = r11
        L_0x0180:
            int r11 = r10.currentAttachmentLocation
            if (r11 == r3) goto L_0x01ea
            r10.currentAttachmentLocation = r3
            android.view.ViewGroup r11 = r10.getMediaFrame()
            android.view.ViewParent r11 = r11.getParent()
            android.view.ViewGroup r11 = (android.view.ViewGroup) r11
            if (r11 != 0) goto L_0x0193
            goto L_0x019a
        L_0x0193:
            android.view.ViewGroup r13 = r10.getMediaFrame()
            r11.removeView(r13)
        L_0x019a:
            if (r1 == 0) goto L_0x01a9
            android.view.ViewGroupOverlay r11 = r10.rootOverlay
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11)
            android.view.ViewGroup r13 = r10.getMediaFrame()
            r11.add(r13)
            goto L_0x01d8
        L_0x01a9:
            com.android.systemui.media.MediaHost r11 = r10.getHost(r3)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11)
            com.android.systemui.util.animation.UniqueObjectHostView r11 = r11.getHostView()
            android.view.ViewGroup r13 = r10.getMediaFrame()
            r11.addView(r13)
            int r13 = r11.getPaddingLeft()
            int r11 = r11.getPaddingTop()
            android.view.ViewGroup r0 = r10.getMediaFrame()
            android.graphics.Rect r1 = r10.currentBounds
            int r1 = r1.width()
            int r1 = r1 + r13
            android.graphics.Rect r2 = r10.currentBounds
            int r2 = r2.height()
            int r2 = r2 + r11
            r0.setLeftTopRightBottom(r13, r11, r1, r2)
        L_0x01d8:
            boolean r11 = r10.isCrossFadeAnimatorRunning
            if (r11 == 0) goto L_0x01ea
            com.android.systemui.media.MediaCarouselController r2 = r10.mediaCarouselController
            com.android.systemui.media.MediaHost r4 = r10.getHost(r3)
            r6 = 200(0xc8, double:9.9E-322)
            r8 = 0
            r5 = 0
            r2.onDesiredLocationChanged(r3, r4, r5, r6, r8)
        L_0x01ea:
            int r11 = r10.currentAttachmentLocation
            if (r11 != r12) goto L_0x01ff
            android.view.ViewGroup r11 = r10.getMediaFrame()
            android.graphics.Rect r10 = r10.currentBounds
            int r12 = r10.left
            int r13 = r10.top
            int r0 = r10.right
            int r10 = r10.bottom
            r11.setLeftTopRightBottom(r12, r13, r0, r10)
        L_0x01ff:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaHierarchyManager.applyState(android.graphics.Rect, float, boolean):void");
    }

    public final void applyTargetStateIfNotAnimating() {
        if (!this.animator.isRunning()) {
            applyState(this.targetBounds, this.carouselAlpha, false);
        }
    }

    public final void cancelAnimationAndApplyDesiredState() {
        this.animator.cancel();
        MediaHost host = getHost(this.desiredLocation);
        if (host != null) {
            applyState(host.getCurrentBounds(), 1.0f, true);
        }
    }

    public final MediaHost getHost(int i) {
        if (i < 0) {
            return null;
        }
        return this.mediaHosts[i];
    }

    public final ViewGroup getMediaFrame() {
        MediaCarouselController mediaCarouselController2 = this.mediaCarouselController;
        Objects.requireNonNull(mediaCarouselController2);
        return mediaCarouselController2.mediaFrame;
    }

    public final float getQSTransformationProgress() {
        boolean z;
        boolean z2;
        MediaHost host = getHost(this.desiredLocation);
        MediaHost host2 = getHost(this.previousLocation);
        MediaHost mediaHost = this.mediaHosts[1];
        boolean z3 = false;
        if (mediaHost != null && mediaHost.getVisible()) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            return -1.0f;
        }
        if (host != null && host.location == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z2 || this.inSplitShade) {
            return -1.0f;
        }
        if (host2 != null && host2.location == 1) {
            z3 = true;
        }
        if (!z3) {
            return -1.0f;
        }
        if (host2.getVisible() || this.statusbarState != 1) {
            return this.qsExpansion;
        }
        return -1.0f;
    }

    public final boolean isHomeScreenShadeVisibleToUser() {
        if (this.statusBarStateController.isDozing() || this.statusBarStateController.getState() != 0 || !this.statusBarStateController.isExpanded()) {
            return false;
        }
        return true;
    }

    public final boolean isLockScreenShadeVisibleToUser() {
        if (!this.statusBarStateController.isDozing() && !this.keyguardViewController.isBouncerShowing()) {
            if (this.statusBarStateController.getState() == 2) {
                return true;
            }
            if (this.statusBarStateController.getState() != 1 || !this.qsExpanded) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final boolean isLockScreenVisibleToUser() {
        if (this.statusBarStateController.isDozing() || this.keyguardViewController.isBouncerShowing() || this.statusBarStateController.getState() != 1 || !this.notifLockscreenUserManager.shouldShowLockscreenNotifications() || !this.statusBarStateController.isExpanded() || this.qsExpanded) {
            return false;
        }
        return true;
    }

    public final boolean isTransitioningToFullShade() {
        boolean z;
        if (this.fullShadeTransitionProgress == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z || this.bypassController.getBypassEnabled() || this.statusbarState != 1) {
            return false;
        }
        return true;
    }

    public final void setQsExpanded(boolean z) {
        if (this.qsExpanded != z) {
            this.qsExpanded = z;
            MediaCarouselController mediaCarouselController2 = this.mediaCarouselController;
            Objects.requireNonNull(mediaCarouselController2);
            MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController2.mediaCarouselScrollHandler;
            Objects.requireNonNull(mediaCarouselScrollHandler);
            mediaCarouselScrollHandler.qsExpanded = z;
        }
        if (z && (isLockScreenShadeVisibleToUser() || isHomeScreenShadeVisibleToUser())) {
            this.mediaCarouselController.logSmartspaceImpression(z);
        }
        MediaCarouselController mediaCarouselController3 = this.mediaCarouselController;
        Objects.requireNonNull(mediaCarouselController3);
        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = mediaCarouselController3.mediaCarouselScrollHandler;
        boolean isVisibleToUser = isVisibleToUser();
        Objects.requireNonNull(mediaCarouselScrollHandler2);
        mediaCarouselScrollHandler2.visibleToUser = isVisibleToUser;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0145, code lost:
        if (r13.animationPending == false) goto L_0x0148;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x0070, code lost:
        if (r6 != false) goto L_0x0079;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x0094, code lost:
        if (r13.statusBarStateController.isDozing() == false) goto L_0x00a1;
     */
    /* JADX WARNING: Removed duplicated region for block: B:132:0x014d  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:138:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x018e A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:180:0x0244  */
    /* JADX WARNING: Removed duplicated region for block: B:195:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00b4 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x00b8 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x00bf  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x00dd  */
    /* JADX WARNING: Removed duplicated region for block: B:90:0x00df  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x00e4  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateDesiredLocation(boolean r14, boolean r15) {
        /*
            r13 = this;
            boolean r0 = r13.goingToSleep
            r1 = 1
            r2 = 0
            if (r0 != 0) goto L_0x000d
            boolean r0 = r13.dozeAnimationRunning
            if (r0 == 0) goto L_0x000b
            goto L_0x000d
        L_0x000b:
            r0 = r2
            goto L_0x000e
        L_0x000d:
            r0 = r1
        L_0x000e:
            r3 = 0
            r4 = 2
            if (r0 == 0) goto L_0x0016
            int r0 = r13.desiredLocation
            goto L_0x00af
        L_0x0016:
            com.android.systemui.statusbar.phone.KeyguardBypassController r0 = r13.bypassController
            boolean r0 = r0.getBypassEnabled()
            if (r0 != 0) goto L_0x0024
            int r0 = r13.statusbarState
            if (r0 != r1) goto L_0x0024
            r0 = r1
            goto L_0x0025
        L_0x0024:
            r0 = r2
        L_0x0025:
            com.android.systemui.statusbar.NotificationLockscreenUserManager r5 = r13.notifLockscreenUserManager
            boolean r5 = r5.shouldShowLockscreenNotifications()
            boolean r6 = r13.dreamOverlayActive
            if (r6 == 0) goto L_0x0031
            r0 = 3
            goto L_0x007a
        L_0x0031:
            float r6 = r13.qsExpansion
            int r7 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r7 > 0) goto L_0x003b
            boolean r7 = r13.inSplitShade
            if (r7 == 0) goto L_0x003e
        L_0x003b:
            if (r0 != 0) goto L_0x003e
            goto L_0x005a
        L_0x003e:
            r7 = 1053609165(0x3ecccccd, float:0.4)
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L_0x0048
            if (r0 == 0) goto L_0x0048
            goto L_0x005a
        L_0x0048:
            com.android.systemui.media.MediaHost[] r6 = r13.mediaHosts
            r6 = r6[r1]
            if (r6 != 0) goto L_0x004f
            goto L_0x0057
        L_0x004f:
            boolean r6 = r6.getVisible()
            if (r6 != r1) goto L_0x0057
            r6 = r1
            goto L_0x0058
        L_0x0057:
            r6 = r2
        L_0x0058:
            if (r6 != 0) goto L_0x005c
        L_0x005a:
            r0 = r2
            goto L_0x007a
        L_0x005c:
            if (r0 == 0) goto L_0x0073
            boolean r6 = r13.isTransitioningToFullShade()
            if (r6 != 0) goto L_0x0065
            goto L_0x006f
        L_0x0065:
            float r6 = r13.fullShadeTransitionProgress
            r7 = 1056964608(0x3f000000, float:0.5)
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 <= 0) goto L_0x006f
            r6 = r1
            goto L_0x0070
        L_0x006f:
            r6 = r2
        L_0x0070:
            if (r6 == 0) goto L_0x0073
            goto L_0x0079
        L_0x0073:
            if (r0 == 0) goto L_0x0079
            if (r5 == 0) goto L_0x0079
            r0 = r4
            goto L_0x007a
        L_0x0079:
            r0 = r1
        L_0x007a:
            if (r0 != r4) goto L_0x0097
            com.android.systemui.media.MediaHost r5 = r13.getHost(r0)
            if (r5 != 0) goto L_0x0083
            goto L_0x008b
        L_0x0083:
            boolean r5 = r5.getVisible()
            if (r5 != r1) goto L_0x008b
            r5 = r1
            goto L_0x008c
        L_0x008b:
            r5 = r2
        L_0x008c:
            if (r5 != 0) goto L_0x0097
            com.android.systemui.statusbar.SysuiStatusBarStateController r5 = r13.statusBarStateController
            boolean r5 = r5.isDozing()
            if (r5 != 0) goto L_0x0097
            goto L_0x00a1
        L_0x0097:
            if (r0 != r4) goto L_0x00a3
            int r5 = r13.desiredLocation
            if (r5 != 0) goto L_0x00a3
            boolean r5 = r13.collapsingShadeFromQS
            if (r5 == 0) goto L_0x00a3
        L_0x00a1:
            r6 = r2
            goto L_0x00b0
        L_0x00a3:
            if (r0 == r4) goto L_0x00af
            int r5 = r13.desiredLocation
            if (r5 != r4) goto L_0x00af
            boolean r5 = r13.fullyAwake
            if (r5 != 0) goto L_0x00af
            r6 = r4
            goto L_0x00b0
        L_0x00af:
            r6 = r0
        L_0x00b0:
            int r0 = r13.desiredLocation
            if (r6 != r0) goto L_0x00b6
            if (r15 == 0) goto L_0x0257
        L_0x00b6:
            if (r0 < 0) goto L_0x00bd
            if (r6 == r0) goto L_0x00bd
            r13.previousLocation = r0
            goto L_0x00d8
        L_0x00bd:
            if (r15 == 0) goto L_0x00d8
            com.android.systemui.statusbar.phone.KeyguardBypassController r15 = r13.bypassController
            boolean r15 = r15.getBypassEnabled()
            if (r15 != 0) goto L_0x00cd
            int r15 = r13.statusbarState
            if (r15 != r1) goto L_0x00cd
            r15 = r1
            goto L_0x00ce
        L_0x00cd:
            r15 = r2
        L_0x00ce:
            if (r6 != 0) goto L_0x00d8
            int r0 = r13.previousLocation
            if (r0 != r4) goto L_0x00d8
            if (r15 != 0) goto L_0x00d8
            r13.previousLocation = r1
        L_0x00d8:
            int r15 = r13.desiredLocation
            r0 = -1
            if (r15 != r0) goto L_0x00df
            r15 = r1
            goto L_0x00e0
        L_0x00df:
            r15 = r2
        L_0x00e0:
            r13.desiredLocation = r6
            if (r14 != 0) goto L_0x0152
            int r14 = r13.previousLocation
            boolean r0 = r13.isCurrentlyInGuidedTransformation()
            if (r0 == 0) goto L_0x00ee
            goto L_0x0148
        L_0x00ee:
            if (r14 != r4) goto L_0x00f9
            int r0 = r13.desiredLocation
            if (r0 != r1) goto L_0x00f9
            int r0 = r13.statusbarState
            if (r0 != 0) goto L_0x00f9
            goto L_0x0148
        L_0x00f9:
            if (r6 != r1) goto L_0x010a
            if (r14 != r4) goto L_0x010a
            com.android.systemui.statusbar.SysuiStatusBarStateController r0 = r13.statusBarStateController
            boolean r0 = r0.leaveOpenOnKeyguardHide()
            if (r0 != 0) goto L_0x014a
            int r0 = r13.statusbarState
            if (r0 != r4) goto L_0x010a
            goto L_0x014a
        L_0x010a:
            int r0 = r13.statusbarState
            if (r0 != r1) goto L_0x0113
            if (r6 == r4) goto L_0x0148
            if (r14 != r4) goto L_0x0113
            goto L_0x0148
        L_0x0113:
            android.view.ViewGroup r14 = r13.getMediaFrame()
        L_0x0117:
            int r0 = r14.getVisibility()
            if (r0 == 0) goto L_0x011e
            goto L_0x0132
        L_0x011e:
            float r0 = r14.getAlpha()
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x0128
            r0 = r1
            goto L_0x0129
        L_0x0128:
            r0 = r2
        L_0x0129:
            if (r0 == 0) goto L_0x012c
            goto L_0x0132
        L_0x012c:
            android.view.ViewParent r14 = r14.getParent()
            if (r14 != 0) goto L_0x0134
        L_0x0132:
            r14 = r2
            goto L_0x0139
        L_0x0134:
            boolean r0 = r14 instanceof android.view.View
            if (r0 != 0) goto L_0x014f
            r14 = r1
        L_0x0139:
            if (r14 != 0) goto L_0x014a
            android.animation.ValueAnimator r14 = r13.animator
            boolean r14 = r14.isRunning()
            if (r14 != 0) goto L_0x014a
            boolean r14 = r13.animationPending
            if (r14 == 0) goto L_0x0148
            goto L_0x014a
        L_0x0148:
            r14 = r2
            goto L_0x014b
        L_0x014a:
            r14 = r1
        L_0x014b:
            if (r14 == 0) goto L_0x0152
            r14 = r1
            goto L_0x0153
        L_0x014f:
            android.view.View r14 = (android.view.View) r14
            goto L_0x0117
        L_0x0152:
            r14 = r2
        L_0x0153:
            int r0 = r13.previousLocation
            kotlin.Pair r0 = r13.getAnimationParams(r0, r6)
            java.lang.Object r4 = r0.component1()
            java.lang.Number r4 = (java.lang.Number) r4
            long r9 = r4.longValue()
            java.lang.Object r0 = r0.component2()
            java.lang.Number r0 = (java.lang.Number) r0
            long r11 = r0.longValue()
            com.android.systemui.media.MediaHost r7 = r13.getHost(r6)
            int r0 = r13.calculateTransformationType()
            if (r0 != r1) goto L_0x0179
            r0 = r1
            goto L_0x017a
        L_0x0179:
            r0 = r2
        L_0x017a:
            if (r0 == 0) goto L_0x0184
            boolean r0 = r13.isCurrentlyInGuidedTransformation()
            if (r0 != 0) goto L_0x0184
            if (r14 != 0) goto L_0x018a
        L_0x0184:
            com.android.systemui.media.MediaCarouselController r5 = r13.mediaCarouselController
            r8 = r14
            r5.onDesiredLocationChanged(r6, r7, r8, r9, r11)
        L_0x018a:
            int r0 = r13.previousLocation
            if (r0 < 0) goto L_0x0254
            if (r15 == 0) goto L_0x0192
            goto L_0x0254
        L_0x0192:
            int r15 = r13.desiredLocation
            com.android.systemui.media.MediaHost r15 = r13.getHost(r15)
            int r0 = r13.previousLocation
            com.android.systemui.media.MediaHost r0 = r13.getHost(r0)
            if (r15 == 0) goto L_0x0250
            if (r0 != 0) goto L_0x01a4
            goto L_0x0250
        L_0x01a4:
            r13.updateTargetState()
            boolean r15 = r13.isCurrentlyInGuidedTransformation()
            if (r15 == 0) goto L_0x01b2
            r13.applyTargetStateIfNotAnimating()
            goto L_0x0257
        L_0x01b2:
            if (r14 == 0) goto L_0x024c
            boolean r14 = r13.isCrossFadeAnimatorRunning
            float r15 = r13.animationCrossFadeProgress
            android.animation.ValueAnimator r4 = r13.animator
            r4.cancel()
            int r4 = r13.currentAttachmentLocation
            int r5 = r13.previousLocation
            if (r4 != r5) goto L_0x01d8
            com.android.systemui.util.animation.UniqueObjectHostView r4 = r0.getHostView()
            boolean r4 = r4.isAttachedToWindow()
            if (r4 != 0) goto L_0x01ce
            goto L_0x01d8
        L_0x01ce:
            android.graphics.Rect r4 = r13.animationStartBounds
            android.graphics.Rect r0 = r0.getCurrentBounds()
            r4.set(r0)
            goto L_0x01df
        L_0x01d8:
            android.graphics.Rect r0 = r13.animationStartBounds
            android.graphics.Rect r4 = r13.currentBounds
            r0.set(r4)
        L_0x01df:
            int r0 = r13.calculateTransformationType()
            if (r0 != r1) goto L_0x01e6
            r2 = r1
        L_0x01e6:
            int r0 = r13.previousLocation
            r4 = 1065353216(0x3f800000, float:1.0)
            if (r14 == 0) goto L_0x0202
            int r14 = r13.currentAttachmentLocation
            int r5 = r13.crossFadeAnimationEndLocation
            if (r14 != r5) goto L_0x01f7
            if (r2 == 0) goto L_0x020b
            float r3 = r4 - r15
            goto L_0x020b
        L_0x01f7:
            int r14 = r13.crossFadeAnimationStartLocation
            int r3 = r13.desiredLocation
            if (r14 != r3) goto L_0x0200
            float r15 = r4 - r15
            goto L_0x020d
        L_0x0200:
            r2 = r1
            goto L_0x020d
        L_0x0202:
            if (r2 == 0) goto L_0x020b
            float r14 = r13.carouselAlpha
            float r4 = r4 - r14
            r14 = 1073741824(0x40000000, float:2.0)
            float r3 = r4 / r14
        L_0x020b:
            r14 = r0
            r15 = r3
        L_0x020d:
            r13.isCrossFadeAnimatorRunning = r2
            r13.crossFadeAnimationStartLocation = r14
            int r14 = r13.desiredLocation
            r13.crossFadeAnimationEndLocation = r14
            float r2 = r13.carouselAlpha
            r13.animationStartAlpha = r2
            r13.animationStartCrossFadeProgress = r15
            kotlin.Pair r14 = r13.getAnimationParams(r0, r14)
            java.lang.Object r15 = r14.component1()
            java.lang.Number r15 = (java.lang.Number) r15
            long r2 = r15.longValue()
            java.lang.Object r14 = r14.component2()
            java.lang.Number r14 = (java.lang.Number) r14
            long r14 = r14.longValue()
            android.animation.ValueAnimator r0 = r13.animator
            r0.setDuration(r2)
            r0.setStartDelay(r14)
            boolean r14 = r13.animationPending
            if (r14 != 0) goto L_0x0257
            android.view.View r14 = r13.rootView
            if (r14 != 0) goto L_0x0244
            goto L_0x0257
        L_0x0244:
            r13.animationPending = r1
            com.android.systemui.media.MediaHierarchyManager$startAnimation$1 r13 = r13.startAnimation
            r14.postOnAnimation(r13)
            goto L_0x0257
        L_0x024c:
            r13.cancelAnimationAndApplyDesiredState()
            goto L_0x0257
        L_0x0250:
            r13.cancelAnimationAndApplyDesiredState()
            goto L_0x0257
        L_0x0254:
            r13.cancelAnimationAndApplyDesiredState()
        L_0x0257:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaHierarchyManager.updateDesiredLocation(boolean, boolean):void");
    }

    public MediaHierarchyManager(Context context2, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController2, KeyguardBypassController keyguardBypassController, MediaCarouselController mediaCarouselController2, NotificationLockscreenUserManager notificationLockscreenUserManager, ConfigurationController configurationController, WakefulnessLifecycle wakefulnessLifecycle, KeyguardViewController keyguardViewController2, DreamOverlayStateController dreamOverlayStateController2) {
        this.context = context2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.keyguardStateController = keyguardStateController2;
        this.bypassController = keyguardBypassController;
        this.mediaCarouselController = mediaCarouselController2;
        this.notifLockscreenUserManager = notificationLockscreenUserManager;
        this.keyguardViewController = keyguardViewController2;
        this.dreamOverlayStateController = dreamOverlayStateController2;
        this.statusbarState = sysuiStatusBarStateController.getState();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        ofFloat.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat.addUpdateListener(new MediaHierarchyManager$animator$1$1(this, ofFloat));
        ofFloat.addListener(new MediaHierarchyManager$animator$1$2(this));
        this.animator = ofFloat;
        this.mediaHosts = new MediaHost[4];
        this.previousLocation = -1;
        this.desiredLocation = -1;
        this.currentAttachmentLocation = -1;
        this.startAnimation = new MediaHierarchyManager$startAnimation$1(this);
        this.animationCrossFadeProgress = 1.0f;
        this.carouselAlpha = 1.0f;
        this.distanceForFullShadeTransition = context2.getResources().getDimensionPixelSize(C1777R.dimen.lockscreen_shade_media_transition_distance);
        this.inSplitShade = Utils.shouldUseSplitNotificationShade(context2.getResources());
        configurationController.addCallback(new ConfigurationController.ConfigurationListener(this) {
            public final /* synthetic */ MediaHierarchyManager this$0;

            {
                this.this$0 = r1;
            }

            public final void onConfigChanged(Configuration configuration) {
                MediaHierarchyManager mediaHierarchyManager = this.this$0;
                Objects.requireNonNull(mediaHierarchyManager);
                mediaHierarchyManager.distanceForFullShadeTransition = mediaHierarchyManager.context.getResources().getDimensionPixelSize(C1777R.dimen.lockscreen_shade_media_transition_distance);
                mediaHierarchyManager.inSplitShade = Utils.shouldUseSplitNotificationShade(mediaHierarchyManager.context.getResources());
                this.this$0.updateDesiredLocation(true, true);
            }
        });
        sysuiStatusBarStateController.addCallback(new StatusBarStateController.StateListener(this) {
            public final /* synthetic */ MediaHierarchyManager this$0;

            public final void onDozingChanged(boolean z) {
                if (!z) {
                    MediaHierarchyManager mediaHierarchyManager = this.this$0;
                    Objects.requireNonNull(mediaHierarchyManager);
                    if (mediaHierarchyManager.dozeAnimationRunning) {
                        mediaHierarchyManager.dozeAnimationRunning = false;
                        MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
                    }
                    if (this.this$0.isLockScreenVisibleToUser()) {
                        MediaHierarchyManager mediaHierarchyManager2 = this.this$0;
                        mediaHierarchyManager2.mediaCarouselController.logSmartspaceImpression(mediaHierarchyManager2.qsExpanded);
                    }
                } else {
                    MediaHierarchyManager.updateDesiredLocation$default(this.this$0, false, 3);
                    this.this$0.setQsExpanded(false);
                    MediaHierarchyManager mediaHierarchyManager3 = this.this$0;
                    Objects.requireNonNull(mediaHierarchyManager3);
                    Objects.requireNonNull(mediaHierarchyManager3.mediaCarouselController);
                    Objects.requireNonNull(MediaPlayerData.INSTANCE);
                    for (MediaControlPanel closeGuts : MediaPlayerData.players()) {
                        closeGuts.closeGuts(true);
                    }
                }
                MediaCarouselController mediaCarouselController = this.this$0.mediaCarouselController;
                Objects.requireNonNull(mediaCarouselController);
                MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController.mediaCarouselScrollHandler;
                boolean isVisibleToUser = this.this$0.isVisibleToUser();
                Objects.requireNonNull(mediaCarouselScrollHandler);
                mediaCarouselScrollHandler.visibleToUser = isVisibleToUser;
            }

            {
                this.this$0 = r1;
            }

            /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
                if (r3 == false) goto L_0x001b;
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onDozeAmountChanged(float r3, float r4) {
                /*
                    r2 = this;
                    com.android.systemui.media.MediaHierarchyManager r2 = r2.this$0
                    r4 = 0
                    int r4 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                    r0 = 0
                    r1 = 1
                    if (r4 != 0) goto L_0x000b
                    r4 = r1
                    goto L_0x000c
                L_0x000b:
                    r4 = r0
                L_0x000c:
                    if (r4 != 0) goto L_0x001a
                    r4 = 1065353216(0x3f800000, float:1.0)
                    int r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1))
                    if (r3 != 0) goto L_0x0016
                    r3 = r1
                    goto L_0x0017
                L_0x0016:
                    r3 = r0
                L_0x0017:
                    if (r3 != 0) goto L_0x001a
                    goto L_0x001b
                L_0x001a:
                    r1 = r0
                L_0x001b:
                    java.util.Objects.requireNonNull(r2)
                    boolean r3 = r2.dozeAnimationRunning
                    if (r3 == r1) goto L_0x002a
                    r2.dozeAnimationRunning = r1
                    if (r1 != 0) goto L_0x002a
                    r3 = 3
                    com.android.systemui.media.MediaHierarchyManager.updateDesiredLocation$default(r2, r0, r3)
                L_0x002a:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaHierarchyManager.C08852.onDozeAmountChanged(float, float):void");
            }

            public final void onExpandedChanged(boolean z) {
                if (this.this$0.isHomeScreenShadeVisibleToUser()) {
                    MediaHierarchyManager mediaHierarchyManager = this.this$0;
                    MediaCarouselController mediaCarouselController = mediaHierarchyManager.mediaCarouselController;
                    Objects.requireNonNull(mediaHierarchyManager);
                    mediaCarouselController.logSmartspaceImpression(mediaHierarchyManager.qsExpanded);
                }
                MediaCarouselController mediaCarouselController2 = this.this$0.mediaCarouselController;
                Objects.requireNonNull(mediaCarouselController2);
                MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController2.mediaCarouselScrollHandler;
                boolean isVisibleToUser = this.this$0.isVisibleToUser();
                Objects.requireNonNull(mediaCarouselScrollHandler);
                mediaCarouselScrollHandler.visibleToUser = isVisibleToUser;
            }

            public final void onStateChanged(int i) {
                this.this$0.updateTargetState();
                if (i == 2 && this.this$0.isLockScreenShadeVisibleToUser()) {
                    MediaHierarchyManager mediaHierarchyManager = this.this$0;
                    MediaCarouselController mediaCarouselController = mediaHierarchyManager.mediaCarouselController;
                    Objects.requireNonNull(mediaHierarchyManager);
                    mediaCarouselController.logSmartspaceImpression(mediaHierarchyManager.qsExpanded);
                }
                MediaCarouselController mediaCarouselController2 = this.this$0.mediaCarouselController;
                Objects.requireNonNull(mediaCarouselController2);
                MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController2.mediaCarouselScrollHandler;
                boolean isVisibleToUser = this.this$0.isVisibleToUser();
                Objects.requireNonNull(mediaCarouselScrollHandler);
                mediaCarouselScrollHandler.visibleToUser = isVisibleToUser;
            }

            public final void onStatePreChange(int i, int i2) {
                MediaHierarchyManager mediaHierarchyManager = this.this$0;
                mediaHierarchyManager.statusbarState = i2;
                MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, false, 3);
            }
        });
        C08863 r1 = new DreamOverlayStateController.Callback(this) {
            public final /* synthetic */ MediaHierarchyManager this$0;

            {
                this.this$0 = r1;
            }

            public final void onStateChanged() {
                boolean z;
                DreamOverlayStateController dreamOverlayStateController = this.this$0.dreamOverlayStateController;
                Objects.requireNonNull(dreamOverlayStateController);
                if ((dreamOverlayStateController.mState & 1) != 0) {
                    z = true;
                } else {
                    z = false;
                }
                MediaHierarchyManager mediaHierarchyManager = this.this$0;
                Objects.requireNonNull(mediaHierarchyManager);
                if (mediaHierarchyManager.dreamOverlayActive != z) {
                    mediaHierarchyManager.dreamOverlayActive = z;
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
                }
            }
        };
        Objects.requireNonNull(dreamOverlayStateController2);
        dreamOverlayStateController2.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda20(dreamOverlayStateController2, r1, 2));
        wakefulnessLifecycle.mObservers.add(new WakefulnessLifecycle.Observer(this) {
            public final /* synthetic */ MediaHierarchyManager this$0;

            {
                this.this$0 = r1;
            }

            public final void onFinishedGoingToSleep() {
                MediaHierarchyManager.access$setGoingToSleep(this.this$0, false);
            }

            public final void onFinishedWakingUp() {
                MediaHierarchyManager.access$setGoingToSleep(this.this$0, false);
                MediaHierarchyManager mediaHierarchyManager = this.this$0;
                Objects.requireNonNull(mediaHierarchyManager);
                if (!mediaHierarchyManager.fullyAwake) {
                    mediaHierarchyManager.fullyAwake = true;
                    MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager, true, 2);
                }
            }

            public final void onStartedGoingToSleep() {
                MediaHierarchyManager.access$setGoingToSleep(this.this$0, true);
                MediaHierarchyManager mediaHierarchyManager = this.this$0;
                Objects.requireNonNull(mediaHierarchyManager);
                if (mediaHierarchyManager.fullyAwake) {
                    mediaHierarchyManager.fullyAwake = false;
                }
            }

            public final void onStartedWakingUp() {
                MediaHierarchyManager.access$setGoingToSleep(this.this$0, false);
            }
        });
        C08885 r12 = new Function0<Unit>(this) {
            public final /* synthetic */ MediaHierarchyManager this$0;

            {
                this.this$0 = r1;
            }

            public final Object invoke() {
                MediaCarouselController mediaCarouselController = this.this$0.mediaCarouselController;
                Objects.requireNonNull(mediaCarouselController);
                MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController.mediaCarouselScrollHandler;
                boolean isVisibleToUser = this.this$0.isVisibleToUser();
                Objects.requireNonNull(mediaCarouselScrollHandler);
                mediaCarouselScrollHandler.visibleToUser = isVisibleToUser;
                return Unit.INSTANCE;
            }
        };
        Objects.requireNonNull(mediaCarouselController2);
        mediaCarouselController2.updateUserVisibility = r12;
    }

    public static final void access$setGoingToSleep(MediaHierarchyManager mediaHierarchyManager, boolean z) {
        Objects.requireNonNull(mediaHierarchyManager);
        if (mediaHierarchyManager.goingToSleep != z) {
            mediaHierarchyManager.goingToSleep = z;
            if (!z) {
                updateDesiredLocation$default(mediaHierarchyManager, false, 3);
            }
        }
    }

    public final int calculateTransformationType() {
        if (isTransitioningToFullShade()) {
            return 1;
        }
        int i = this.previousLocation;
        if ((i == 2 && this.desiredLocation == 0) || (i == 0 && this.desiredLocation == 2)) {
            return 1;
        }
        if (i == 2 && this.desiredLocation == 1) {
            return 1;
        }
        return 0;
    }

    public final float getTransformationProgress() {
        float qSTransformationProgress = getQSTransformationProgress();
        if (this.statusbarState != 1 && qSTransformationProgress >= 0.0f) {
            return qSTransformationProgress;
        }
        if (isTransitioningToFullShade()) {
            return this.fullShadeTransitionProgress;
        }
        return -1.0f;
    }

    public final boolean isCurrentlyFading() {
        if (isTransitioningToFullShade()) {
            return true;
        }
        return this.isCrossFadeAnimatorRunning;
    }

    public final boolean isCurrentlyInGuidedTransformation() {
        if (getTransformationProgress() >= 0.0f) {
            return true;
        }
        return false;
    }

    public final boolean isVisibleToUser() {
        if (isLockScreenVisibleToUser() || isLockScreenShadeVisibleToUser() || isHomeScreenShadeVisibleToUser()) {
            return true;
        }
        return false;
    }

    public final void updateTargetState() {
        Rect rect = null;
        if (!isCurrentlyInGuidedTransformation() || isCurrentlyFading()) {
            MediaHost host = getHost(this.desiredLocation);
            if (host != null) {
                rect = host.getCurrentBounds();
            }
            if (rect != null) {
                this.targetBounds.set(rect);
                return;
            }
            return;
        }
        float transformationProgress = getTransformationProgress();
        MediaHost host2 = getHost(this.desiredLocation);
        Intrinsics.checkNotNull(host2);
        MediaHost host3 = getHost(this.previousLocation);
        Intrinsics.checkNotNull(host3);
        if (!host2.getVisible()) {
            host2 = host3;
        } else if (!host3.getVisible()) {
            host3 = host2;
        }
        this.targetBounds = interpolateBounds(host3.getCurrentBounds(), host2.getCurrentBounds(), transformationProgress, (Rect) null);
    }
}
