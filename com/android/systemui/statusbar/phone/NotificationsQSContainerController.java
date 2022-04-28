package com.android.systemui.statusbar.phone;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.R$color;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.android.systemui.plugins.p005qs.QSContainerController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda15;
import com.android.systemui.util.ViewController;
import com.google.android.systemui.elmyra.actions.UnpinNotifications$$ExternalSyntheticLambda1;
import java.util.Objects;

/* compiled from: NotificationsQSContainerController.kt */
public final class NotificationsQSContainerController extends ViewController<NotificationsQuickSettingsContainer> implements QSContainerController {
    public int bottomCutoutInsets;
    public int bottomStableInsets;
    public final FeatureFlags featureFlags;
    public boolean isGestureNavigation = true;
    public boolean isQSCustomizerAnimating;
    public boolean isQSCustomizing;
    public boolean isQSDetailShowing;
    public final NavigationModeController navigationModeController;
    public int notificationsBottomMargin;
    public final OverviewProxyService overviewProxyService;
    public boolean qsExpanded;
    public boolean splitShadeEnabled;
    public final NotificationsQSContainerController$taskbarVisibilityListener$1 taskbarVisibilityListener = new NotificationsQSContainerController$taskbarVisibilityListener$1(this);
    public boolean taskbarVisible;
    public final NotificationsQSContainerController$windowInsetsListener$1 windowInsetsListener = new NotificationsQSContainerController$windowInsetsListener$1(this);

    public final void onInit() {
        this.isGestureNavigation = R$color.isGesturalMode(this.navigationModeController.addListener(new NotificationsQSContainerController$onInit$currentMode$1(this)));
    }

    public final void onViewDetached() {
        this.overviewProxyService.removeCallback((OverviewProxyService.OverviewProxyListener) this.taskbarVisibilityListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        Objects.requireNonNull(notificationsQuickSettingsContainer);
        notificationsQuickSettingsContainer.mInsetsChangedListener = UnpinNotifications$$ExternalSyntheticLambda1.INSTANCE$1;
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer2 = (NotificationsQuickSettingsContainer) this.mView;
        Objects.requireNonNull(notificationsQuickSettingsContainer2);
        notificationsQuickSettingsContainer2.mQSFragmentAttachedListener = OverviewProxyService$1$$ExternalSyntheticLambda15.INSTANCE$1;
    }

    public final void setCustomizerAnimating(boolean z) {
        if (this.isQSCustomizerAnimating != z) {
            this.isQSCustomizerAnimating = z;
            ((NotificationsQuickSettingsContainer) this.mView).invalidate();
        }
    }

    public final void setCustomizerShowing(boolean z) {
        this.isQSCustomizing = z;
        updateBottomSpacing();
    }

    public final void setDetailShowing(boolean z) {
        this.isQSDetailShowing = z;
        updateBottomSpacing();
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0075  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a2  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00c1  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateBottomSpacing() {
        /*
            r6 = this;
            int r0 = r6.notificationsBottomMargin
            boolean r1 = r6.splitShadeEnabled
            r2 = 0
            if (r1 == 0) goto L_0x001a
            boolean r1 = r6.isGestureNavigation
            if (r1 == 0) goto L_0x000e
            int r1 = r6.bottomCutoutInsets
            goto L_0x0033
        L_0x000e:
            boolean r1 = r6.taskbarVisible
            if (r1 == 0) goto L_0x0015
            int r1 = r6.bottomStableInsets
            goto L_0x0033
        L_0x0015:
            int r1 = r6.bottomStableInsets
            int r0 = r0 + r1
        L_0x0018:
            r1 = r2
            goto L_0x0033
        L_0x001a:
            boolean r1 = r6.isQSCustomizing
            if (r1 != 0) goto L_0x0031
            boolean r1 = r6.isQSDetailShowing
            if (r1 == 0) goto L_0x0023
            goto L_0x0031
        L_0x0023:
            boolean r1 = r6.isGestureNavigation
            if (r1 == 0) goto L_0x002a
            int r1 = r6.bottomCutoutInsets
            goto L_0x0033
        L_0x002a:
            boolean r1 = r6.taskbarVisible
            if (r1 == 0) goto L_0x0018
            int r1 = r6.bottomStableInsets
            goto L_0x0033
        L_0x0031:
            r0 = r2
            r1 = r0
        L_0x0033:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            kotlin.Pair r3 = new kotlin.Pair
            r3.<init>(r1, r0)
            java.lang.Object r0 = r3.component1()
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            java.lang.Object r1 = r3.component2()
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            com.android.systemui.flags.FeatureFlags r3 = r6.featureFlags
            com.android.systemui.flags.BooleanFlag r4 = com.android.systemui.flags.Flags.NEW_FOOTER
            boolean r3 = r3.isEnabled((com.android.systemui.flags.BooleanFlag) r4)
            if (r3 != 0) goto L_0x0075
            boolean r4 = r6.splitShadeEnabled
            if (r4 != 0) goto L_0x0075
            boolean r4 = r6.isQSCustomizing
            if (r4 != 0) goto L_0x0075
            boolean r4 = r6.isQSDetailShowing
            if (r4 != 0) goto L_0x0075
            boolean r4 = r6.isGestureNavigation
            if (r4 != 0) goto L_0x0075
            boolean r4 = r6.taskbarVisible
            if (r4 != 0) goto L_0x0075
            int r4 = r6.bottomStableInsets
            goto L_0x0083
        L_0x0075:
            if (r3 == 0) goto L_0x0082
            boolean r4 = r6.isQSCustomizing
            if (r4 != 0) goto L_0x0082
            boolean r4 = r6.isQSDetailShowing
            if (r4 != 0) goto L_0x0082
            int r4 = r6.bottomStableInsets
            goto L_0x0083
        L_0x0082:
            r4 = r2
        L_0x0083:
            T r5 = r6.mView
            com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer r5 = (com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer) r5
            r5.setPadding(r2, r2, r2, r0)
            T r0 = r6.mView
            com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer r0 = (com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer) r0
            java.util.Objects.requireNonNull(r0)
            android.view.View r2 = r0.mStackScroller
            android.view.ViewGroup$LayoutParams r2 = r2.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r2 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r2
            r2.bottomMargin = r1
            android.view.View r0 = r0.mStackScroller
            r0.setLayoutParams(r2)
            if (r3 == 0) goto L_0x00c1
            T r6 = r6.mView
            com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer r6 = (com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer) r6
            java.util.Objects.requireNonNull(r6)
            android.view.View r0 = r6.mQSContainer
            if (r0 == 0) goto L_0x00df
            int r1 = r0.getPaddingLeft()
            android.view.View r2 = r6.mQSContainer
            int r2 = r2.getPaddingTop()
            android.view.View r6 = r6.mQSContainer
            int r6 = r6.getPaddingRight()
            r0.setPadding(r1, r2, r6, r4)
            goto L_0x00df
        L_0x00c1:
            T r6 = r6.mView
            com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer r6 = (com.android.systemui.statusbar.phone.NotificationsQuickSettingsContainer) r6
            java.util.Objects.requireNonNull(r6)
            android.view.View r0 = r6.mQSScrollView
            if (r0 == 0) goto L_0x00df
            int r1 = r0.getPaddingLeft()
            android.view.View r2 = r6.mQSScrollView
            int r2 = r2.getPaddingTop()
            android.view.View r6 = r6.mQSScrollView
            int r6 = r6.getPaddingRight()
            r0.setPaddingRelative(r1, r2, r6, r4)
        L_0x00df:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.NotificationsQSContainerController.updateBottomSpacing():void");
    }

    public final void updateMargins$2() {
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        Objects.requireNonNull(notificationsQuickSettingsContainer);
        this.notificationsBottomMargin = ((ConstraintLayout.LayoutParams) notificationsQuickSettingsContainer.mStackScroller.getLayoutParams()).bottomMargin;
    }

    public NotificationsQSContainerController(NotificationsQuickSettingsContainer notificationsQuickSettingsContainer, NavigationModeController navigationModeController2, OverviewProxyService overviewProxyService2, FeatureFlags featureFlags2) {
        super(notificationsQuickSettingsContainer);
        this.navigationModeController = navigationModeController2;
        this.overviewProxyService = overviewProxyService2;
        this.featureFlags = featureFlags2;
    }

    public final void onViewAttached() {
        updateMargins$2();
        this.overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this.taskbarVisibilityListener);
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer = (NotificationsQuickSettingsContainer) this.mView;
        NotificationsQSContainerController$windowInsetsListener$1 notificationsQSContainerController$windowInsetsListener$1 = this.windowInsetsListener;
        Objects.requireNonNull(notificationsQuickSettingsContainer);
        notificationsQuickSettingsContainer.mInsetsChangedListener = notificationsQSContainerController$windowInsetsListener$1;
        NotificationsQuickSettingsContainer notificationsQuickSettingsContainer2 = (NotificationsQuickSettingsContainer) this.mView;
        NotificationsQSContainerController$onViewAttached$1 notificationsQSContainerController$onViewAttached$1 = new NotificationsQSContainerController$onViewAttached$1(this);
        Objects.requireNonNull(notificationsQuickSettingsContainer2);
        notificationsQuickSettingsContainer2.mQSFragmentAttachedListener = notificationsQSContainerController$onViewAttached$1;
        C0961QS qs = notificationsQuickSettingsContainer2.mQs;
        if (qs != null) {
            notificationsQSContainerController$onViewAttached$1.accept(qs);
        }
    }
}
