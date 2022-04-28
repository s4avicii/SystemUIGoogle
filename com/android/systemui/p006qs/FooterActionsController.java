package com.android.systemui.p006qs;

import android.os.Handler;
import android.os.UserManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.DejankUtils;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.p006qs.TouchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.MultiUserSwitch;
import com.android.systemui.statusbar.phone.MultiUserSwitchController;
import com.android.systemui.statusbar.phone.MultiUserSwitchController$$ExternalSyntheticLambda1;
import com.android.systemui.statusbar.phone.SettingsButton;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.util.DualHeightHorizontalLinearLayout;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.FooterActionsController */
/* compiled from: FooterActionsController.kt */
public final class FooterActionsController extends ViewController<FooterActionsView> {
    public final ActivityStarter activityStarter;
    public final TouchAnimator alphaAnimator;
    public final DeviceProvisionedController deviceProvisionedController;
    public final FalsingManager falsingManager;
    public final FeatureFlags featureFlags;
    public final QSFgsManagerFooter fgsManagerFooterController;
    public final GlobalActionsDialogLite globalActionsDialog;
    public final GlobalSettings globalSetting;
    public float lastExpansion = -1.0f;
    public boolean listening;
    public final MetricsLogger metricsLogger;
    public final FooterActionsController$multiUserSetting$1 multiUserSetting;
    public final MultiUserSwitchController multiUserSwitchController;
    public final FooterActionsController$onClickListener$1 onClickListener;
    public final FooterActionsController$onUserInfoChangedListener$1 onUserInfoChangedListener;
    public final View powerMenuLite;
    public final QSSecurityFooter securityFooterController;
    public final ViewGroup securityFootersContainer;
    public final View securityFootersSeparator;
    public final SettingsButton settingsButton;
    public final View settingsButtonContainer;
    public final boolean showPMLiteButton;
    public final UiEventLogger uiEventLogger;
    public final UserInfoController userInfoController;
    public final UserManager userManager;
    public final UserTracker userTracker;
    public boolean visible;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FooterActionsController(FooterActionsView footerActionsView, MultiUserSwitchController.Factory factory, ActivityStarter activityStarter2, UserManager userManager2, UserTracker userTracker2, UserInfoController userInfoController2, DeviceProvisionedController deviceProvisionedController2, QSSecurityFooter qSSecurityFooter, QSFgsManagerFooter qSFgsManagerFooter, FalsingManager falsingManager2, MetricsLogger metricsLogger2, GlobalActionsDialogLite globalActionsDialogLite, UiEventLogger uiEventLogger2, boolean z, GlobalSettings globalSettings, Handler handler, FeatureFlags featureFlags2) {
        super(footerActionsView);
        FooterActionsView footerActionsView2 = footerActionsView;
        MultiUserSwitchController.Factory factory2 = factory;
        GlobalSettings globalSettings2 = globalSettings;
        this.activityStarter = activityStarter2;
        this.userManager = userManager2;
        this.userTracker = userTracker2;
        this.userInfoController = userInfoController2;
        this.deviceProvisionedController = deviceProvisionedController2;
        this.securityFooterController = qSSecurityFooter;
        this.fgsManagerFooterController = qSFgsManagerFooter;
        this.falsingManager = falsingManager2;
        this.metricsLogger = metricsLogger2;
        this.globalActionsDialog = globalActionsDialogLite;
        this.uiEventLogger = uiEventLogger2;
        this.showPMLiteButton = z;
        this.globalSetting = globalSettings2;
        this.featureFlags = featureFlags2;
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(footerActionsView, "alpha", 0.0f, 1.0f);
        builder.mStartDelay = 0.9f;
        this.alphaAnimator = builder.build();
        this.visible = true;
        this.settingsButton = (SettingsButton) footerActionsView.findViewById(C1777R.C1779id.settings_button);
        this.settingsButtonContainer = footerActionsView.findViewById(C1777R.C1779id.settings_button_container);
        this.securityFootersContainer = (ViewGroup) footerActionsView.findViewById(C1777R.C1779id.security_footers_container);
        this.powerMenuLite = footerActionsView.findViewById(C1777R.C1779id.pm_lite);
        this.multiUserSwitchController = new MultiUserSwitchController((MultiUserSwitch) footerActionsView.findViewById(C1777R.C1779id.multi_user_switch), factory2.mUserManager, factory2.mUserSwitcherController, factory2.mFalsingManager, factory2.mUserSwitchDialogController, factory2.mFeatureFlags, factory2.mActivityStarter);
        View view = new View(getContext());
        view.setVisibility(8);
        this.securityFootersSeparator = view;
        this.onUserInfoChangedListener = new FooterActionsController$onUserInfoChangedListener$1(this);
        this.multiUserSetting = new FooterActionsController$multiUserSetting$1(this, globalSettings2, handler, userTracker2.getUserId());
        this.onClickListener = new FooterActionsController$onClickListener$1(this);
    }

    public final void onViewDetached() {
        setListening(false);
    }

    public final void onInit() {
        this.multiUserSwitchController.init();
        this.fgsManagerFooterController.init();
    }

    public void onViewAttached() {
        if (this.showPMLiteButton) {
            this.powerMenuLite.setVisibility(0);
            this.powerMenuLite.setOnClickListener(this.onClickListener);
        } else {
            this.powerMenuLite.setVisibility(8);
        }
        this.settingsButton.setOnClickListener(this.onClickListener);
        if (this.featureFlags.isEnabled(Flags.NEW_FOOTER)) {
            QSSecurityFooter qSSecurityFooter = this.securityFooterController;
            Objects.requireNonNull(qSSecurityFooter);
            View view = qSSecurityFooter.mRootView;
            Objects.requireNonNull(view, "null cannot be cast to non-null type com.android.systemui.util.DualHeightHorizontalLinearLayout");
            DualHeightHorizontalLinearLayout dualHeightHorizontalLinearLayout = (DualHeightHorizontalLinearLayout) view;
            ViewGroup viewGroup = this.securityFootersContainer;
            if (viewGroup != null) {
                viewGroup.addView(dualHeightHorizontalLinearLayout);
            }
            int dimensionPixelSize = getResources().getDimensionPixelSize(C1777R.dimen.new_qs_footer_action_inset);
            ViewGroup viewGroup2 = this.securityFootersContainer;
            if (viewGroup2 != null) {
                viewGroup2.addView(this.securityFootersSeparator, dimensionPixelSize, 1);
            }
            ViewGroup.LayoutParams layoutParams = dualHeightHorizontalLinearLayout.getLayoutParams();
            Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) layoutParams;
            layoutParams2.bottomMargin = 0;
            layoutParams2.width = 0;
            layoutParams2.weight = 1.0f;
            layoutParams2.setMarginEnd(getResources().getDimensionPixelSize(C1777R.dimen.new_qs_footer_action_inset));
            dualHeightHorizontalLinearLayout.alwaysSingleLine = true;
            TextView textView = dualHeightHorizontalLinearLayout.textView;
            if (textView != null) {
                textView.setSingleLine();
            }
            QSFgsManagerFooter qSFgsManagerFooter = this.fgsManagerFooterController;
            Objects.requireNonNull(qSFgsManagerFooter);
            View view2 = qSFgsManagerFooter.mRootView;
            ViewGroup viewGroup3 = this.securityFootersContainer;
            if (viewGroup3 != null) {
                viewGroup3.addView(view2);
            }
            ViewGroup.LayoutParams layoutParams3 = view2.getLayoutParams();
            Objects.requireNonNull(layoutParams3, "null cannot be cast to non-null type android.widget.LinearLayout.LayoutParams");
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) layoutParams3;
            layoutParams4.width = 0;
            layoutParams4.weight = 1.0f;
            FooterActionsController$onViewAttached$visibilityListener$1 footerActionsController$onViewAttached$visibilityListener$1 = new FooterActionsController$onViewAttached$visibilityListener$1(this, dualHeightHorizontalLinearLayout, view2);
            QSSecurityFooter qSSecurityFooter2 = this.securityFooterController;
            Objects.requireNonNull(qSSecurityFooter2);
            qSSecurityFooter2.mVisibilityChangedListener = footerActionsController$onViewAttached$visibilityListener$1;
            QSFgsManagerFooter qSFgsManagerFooter2 = this.fgsManagerFooterController;
            Objects.requireNonNull(qSFgsManagerFooter2);
            qSFgsManagerFooter2.mVisibilityChangedListener = footerActionsController$onViewAttached$visibilityListener$1;
        }
        updateView();
    }

    public final void setExpansion(float f) {
        boolean z;
        if (this.featureFlags.isEnabled(Flags.NEW_FOOTER)) {
            float f2 = this.lastExpansion;
            if (f == f2) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                if (f >= 1.0f) {
                    ((FooterActionsView) this.mView).animate().alpha(1.0f).setDuration(500).start();
                } else if (f2 >= 1.0f && f < 1.0f) {
                    ((FooterActionsView) this.mView).animate().alpha(0.0f).setDuration(250).start();
                }
                this.lastExpansion = f;
                return;
            }
            return;
        }
        this.alphaAnimator.setPosition(f);
    }

    public final void setListening(boolean z) {
        if (this.listening != z) {
            this.listening = z;
            this.multiUserSetting.setListening(z);
            if (this.listening) {
                this.userInfoController.addCallback(this.onUserInfoChangedListener);
                updateView();
            } else {
                this.userInfoController.removeCallback(this.onUserInfoChangedListener);
            }
            if (this.featureFlags.isEnabled(Flags.NEW_FOOTER)) {
                this.fgsManagerFooterController.setListening(z);
                QSSecurityFooter qSSecurityFooter = this.securityFooterController;
                Objects.requireNonNull(qSSecurityFooter);
                if (z) {
                    qSSecurityFooter.mSecurityController.addCallback(qSSecurityFooter.mCallback);
                    qSSecurityFooter.mHandler.sendEmptyMessage(1);
                    return;
                }
                qSSecurityFooter.mSecurityController.removeCallback(qSSecurityFooter.mCallback);
            }
        }
    }

    public final void setVisible(boolean z) {
        int i;
        this.visible = z;
        int visibility = ((FooterActionsView) this.mView).getVisibility();
        FooterActionsView footerActionsView = (FooterActionsView) this.mView;
        if (this.visible) {
            i = 0;
        } else {
            i = 4;
        }
        footerActionsView.setVisibility(i);
        if (visibility != ((FooterActionsView) this.mView).getVisibility()) {
            updateView();
        }
    }

    public final void updateView() {
        FooterActionsView footerActionsView = (FooterActionsView) this.mView;
        MultiUserSwitchController multiUserSwitchController2 = this.multiUserSwitchController;
        Objects.requireNonNull(multiUserSwitchController2);
        boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new MultiUserSwitchController$$ExternalSyntheticLambda1(multiUserSwitchController2))).booleanValue();
        Objects.requireNonNull(footerActionsView);
        footerActionsView.post(new FooterActionsView$updateEverything$1(footerActionsView, booleanValue));
    }
}
