package com.android.systemui.p006qs;

import android.os.Bundle;
import android.view.View;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p006qs.QSPanelControllerBase;
import com.android.systemui.p006qs.carrier.QSCarrierGroup;
import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.VariableDateView;
import com.android.systemui.statusbar.policy.VariableDateViewController;
import com.android.systemui.util.ViewController;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QuickStatusBarHeaderController */
public final class QuickStatusBarHeaderController extends ViewController<QuickStatusBarHeader> implements ChipVisibilityListener {
    public final BatteryMeterViewController mBatteryMeterViewController;
    public final Clock mClockView;
    public SysuiColorExtractor mColorExtractor;
    public final DemoModeController mDemoModeController;
    public final ClockDemoModeReceiver mDemoModeReceiver;
    public final FeatureFlags mFeatureFlags;
    public final StatusIconContainer mIconContainer;
    public final StatusBarIconController.TintedIconManager mIconManager;
    public final StatusBarContentInsetsProvider mInsetsProvider;
    public boolean mListening;
    public QuickStatusBarHeaderController$$ExternalSyntheticLambda0 mOnColorsChangedListener;
    public final HeaderPrivacyIconsController mPrivacyIconsController;
    public final QSCarrierGroupController mQSCarrierGroupController;
    public final QSExpansionPathInterpolator mQSExpansionPathInterpolator;
    public final QuickQSPanelController mQuickQSPanelController;
    public final StatusBarIconController mStatusBarIconController;
    public final VariableDateViewController mVariableDateViewControllerClockDateView;
    public final VariableDateViewController mVariableDateViewControllerDateView;

    /* renamed from: com.android.systemui.qs.QuickStatusBarHeaderController$ClockDemoModeReceiver */
    public static class ClockDemoModeReceiver implements DemoMode {
        public Clock mClockView;

        public final List<String> demoCommands() {
            return List.of("clock");
        }

        public final void dispatchDemoCommand(String str, Bundle bundle) {
            this.mClockView.dispatchDemoCommand(str, bundle);
        }

        public final void onDemoModeFinished() {
            this.mClockView.onDemoModeFinished();
        }

        public final void onDemoModeStarted() {
            Clock clock = this.mClockView;
            Objects.requireNonNull(clock);
            clock.mDemoMode = true;
        }

        public ClockDemoModeReceiver(Clock clock) {
            this.mClockView = clock;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public QuickStatusBarHeaderController(QuickStatusBarHeader quickStatusBarHeader, HeaderPrivacyIconsController headerPrivacyIconsController, StatusBarIconController statusBarIconController, DemoModeController demoModeController, QuickQSPanelController quickQSPanelController, QSCarrierGroupController.Builder builder, SysuiColorExtractor sysuiColorExtractor, QSExpansionPathInterpolator qSExpansionPathInterpolator, FeatureFlags featureFlags, VariableDateViewController.Factory factory, BatteryMeterViewController batteryMeterViewController, StatusBarContentInsetsProvider statusBarContentInsetsProvider) {
        super(quickStatusBarHeader);
        QuickStatusBarHeader quickStatusBarHeader2 = quickStatusBarHeader;
        QSCarrierGroupController.Builder builder2 = builder;
        SysuiColorExtractor sysuiColorExtractor2 = sysuiColorExtractor;
        FeatureFlags featureFlags2 = featureFlags;
        VariableDateViewController.Factory factory2 = factory;
        this.mPrivacyIconsController = headerPrivacyIconsController;
        this.mStatusBarIconController = statusBarIconController;
        this.mDemoModeController = demoModeController;
        this.mQuickQSPanelController = quickQSPanelController;
        this.mQSExpansionPathInterpolator = qSExpansionPathInterpolator;
        this.mFeatureFlags = featureFlags2;
        this.mBatteryMeterViewController = batteryMeterViewController;
        this.mInsetsProvider = statusBarContentInsetsProvider;
        QSCarrierGroup qSCarrierGroup = (QSCarrierGroup) quickStatusBarHeader2.findViewById(C1777R.C1779id.carrier_group);
        Objects.requireNonNull(builder);
        builder2.mView = qSCarrierGroup;
        this.mQSCarrierGroupController = new QSCarrierGroupController(qSCarrierGroup, builder2.mActivityStarter, builder2.mHandler, builder2.mLooper, builder2.mNetworkController, builder2.mCarrierTextControllerBuilder, builder2.mContext, builder2.mCarrierConfigTracker, builder2.mFeatureFlags, builder2.mSlotIndexResolver);
        Clock clock = (Clock) quickStatusBarHeader2.findViewById(C1777R.C1779id.clock);
        this.mClockView = clock;
        StatusIconContainer statusIconContainer = (StatusIconContainer) quickStatusBarHeader2.findViewById(C1777R.C1779id.statusIcons);
        this.mIconContainer = statusIconContainer;
        Objects.requireNonNull(factory);
        this.mVariableDateViewControllerDateView = new VariableDateViewController(factory2.systemClock, factory2.broadcastDispatcher, factory2.handler, (VariableDateView) quickStatusBarHeader2.requireViewById(C1777R.C1779id.date));
        this.mVariableDateViewControllerClockDateView = new VariableDateViewController(factory2.systemClock, factory2.broadcastDispatcher, factory2.handler, (VariableDateView) quickStatusBarHeader2.requireViewById(C1777R.C1779id.date_clock));
        this.mIconManager = new StatusBarIconController.TintedIconManager(statusIconContainer, featureFlags2);
        this.mDemoModeReceiver = new ClockDemoModeReceiver(clock);
        this.mColorExtractor = sysuiColorExtractor2;
        QuickStatusBarHeaderController$$ExternalSyntheticLambda0 quickStatusBarHeaderController$$ExternalSyntheticLambda0 = new QuickStatusBarHeaderController$$ExternalSyntheticLambda0(this);
        this.mOnColorsChangedListener = quickStatusBarHeaderController$$ExternalSyntheticLambda0;
        sysuiColorExtractor2.addOnColorsChangedListener(quickStatusBarHeaderController$$ExternalSyntheticLambda0);
        Objects.requireNonNull(batteryMeterViewController);
        BatteryMeterViewController batteryMeterViewController2 = batteryMeterViewController;
        batteryMeterViewController2.mIgnoreTunerUpdates = true;
        if (batteryMeterViewController2.mIsSubscribedForTunerUpdates) {
            batteryMeterViewController2.mTunerService.removeTunable(batteryMeterViewController2.mTunable);
            batteryMeterViewController2.mIsSubscribedForTunerUpdates = false;
        }
    }

    public final void onChipVisibilityRefreshed(boolean z) {
        QuickStatusBarHeader quickStatusBarHeader = (QuickStatusBarHeader) this.mView;
        Objects.requireNonNull(quickStatusBarHeader);
        if (z) {
            TouchAnimator touchAnimator = quickStatusBarHeader.mIconsAlphaAnimatorFixed;
            quickStatusBarHeader.mIconsAlphaAnimator = touchAnimator;
            touchAnimator.setPosition(quickStatusBarHeader.mKeyguardExpansionFraction);
            return;
        }
        quickStatusBarHeader.mIconsAlphaAnimator = null;
        quickStatusBarHeader.mIconContainer.setAlpha(1.0f);
        quickStatusBarHeader.mBatteryRemainingIcon.setAlpha(1.0f);
    }

    public final void onInit() {
        this.mBatteryMeterViewController.init();
    }

    public final void onViewAttached() {
        boolean z;
        List<String> list;
        this.mPrivacyIconsController.onParentVisible();
        HeaderPrivacyIconsController headerPrivacyIconsController = this.mPrivacyIconsController;
        Objects.requireNonNull(headerPrivacyIconsController);
        headerPrivacyIconsController.chipVisibilityListener = this;
        StatusIconContainer statusIconContainer = this.mIconContainer;
        String string = getResources().getString(17041549);
        Objects.requireNonNull(statusIconContainer);
        if (statusIconContainer.mIgnoredSlots.contains(string)) {
            z = false;
        } else {
            statusIconContainer.mIgnoredSlots.add(string);
            z = true;
        }
        if (z) {
            statusIconContainer.requestLayout();
        }
        StatusIconContainer statusIconContainer2 = this.mIconContainer;
        Objects.requireNonNull(statusIconContainer2);
        statusIconContainer2.mShouldRestrictIcons = false;
        this.mStatusBarIconController.addIconGroup(this.mIconManager);
        QuickStatusBarHeader quickStatusBarHeader = (QuickStatusBarHeader) this.mView;
        QSCarrierGroupController qSCarrierGroupController = this.mQSCarrierGroupController;
        Objects.requireNonNull(qSCarrierGroupController);
        boolean z2 = qSCarrierGroupController.mIsSingleCarrier;
        Objects.requireNonNull(quickStatusBarHeader);
        quickStatusBarHeader.mIsSingleCarrier = z2;
        quickStatusBarHeader.updateAlphaAnimator();
        QSCarrierGroupController qSCarrierGroupController2 = this.mQSCarrierGroupController;
        QuickStatusBarHeader quickStatusBarHeader2 = (QuickStatusBarHeader) this.mView;
        Objects.requireNonNull(quickStatusBarHeader2);
        QuickStatusBarHeaderController$$ExternalSyntheticLambda1 quickStatusBarHeaderController$$ExternalSyntheticLambda1 = new QuickStatusBarHeaderController$$ExternalSyntheticLambda1(quickStatusBarHeader2);
        Objects.requireNonNull(qSCarrierGroupController2);
        qSCarrierGroupController2.mOnSingleCarrierChangedListener = quickStatusBarHeaderController$$ExternalSyntheticLambda1;
        if (this.mFeatureFlags.isEnabled(Flags.COMBINED_STATUS_BAR_SIGNAL_ICONS)) {
            list = List.of(getResources().getString(17041554), getResources().getString(17041537));
        } else {
            list = List.of(getResources().getString(17041551));
        }
        QuickStatusBarHeader quickStatusBarHeader3 = (QuickStatusBarHeader) this.mView;
        StatusBarIconController.TintedIconManager tintedIconManager = this.mIconManager;
        QSExpansionPathInterpolator qSExpansionPathInterpolator = this.mQSExpansionPathInterpolator;
        StatusBarContentInsetsProvider statusBarContentInsetsProvider = this.mInsetsProvider;
        boolean isEnabled = this.mFeatureFlags.isEnabled(Flags.COMBINED_QS_HEADERS);
        Objects.requireNonNull(quickStatusBarHeader3);
        quickStatusBarHeader3.mUseCombinedQSHeader = isEnabled;
        quickStatusBarHeader3.mTintedIconManager = tintedIconManager;
        quickStatusBarHeader3.mRssiIgnoredSlots = list;
        quickStatusBarHeader3.mInsetsProvider = statusBarContentInsetsProvider;
        tintedIconManager.setTint(Utils.getColorAttrDefaultColor(quickStatusBarHeader3.getContext(), 16842806));
        quickStatusBarHeader3.mQSExpansionPathInterpolator = qSExpansionPathInterpolator;
        quickStatusBarHeader3.updateAnimators();
        this.mDemoModeController.addCallback((DemoMode) this.mDemoModeReceiver);
        this.mVariableDateViewControllerDateView.init();
        this.mVariableDateViewControllerClockDateView.init();
    }

    public final void onViewDetached() {
        this.mColorExtractor.removeOnColorsChangedListener(this.mOnColorsChangedListener);
        HeaderPrivacyIconsController headerPrivacyIconsController = this.mPrivacyIconsController;
        Objects.requireNonNull(headerPrivacyIconsController);
        headerPrivacyIconsController.chipVisibilityListener = null;
        headerPrivacyIconsController.privacyChip.setOnClickListener((View.OnClickListener) null);
        this.mStatusBarIconController.removeIconGroup(this.mIconManager);
        QSCarrierGroupController qSCarrierGroupController = this.mQSCarrierGroupController;
        Objects.requireNonNull(qSCarrierGroupController);
        qSCarrierGroupController.mOnSingleCarrierChangedListener = null;
        this.mDemoModeController.removeCallback((DemoMode) this.mDemoModeReceiver);
        setListening(false);
    }

    public final void setListening(boolean z) {
        this.mQSCarrierGroupController.setListening(z);
        if (z != this.mListening) {
            this.mListening = z;
            this.mQuickQSPanelController.setListening(z);
            QuickQSPanelController quickQSPanelController = this.mQuickQSPanelController;
            Objects.requireNonNull(quickQSPanelController);
            QuickQSPanel quickQSPanel = (QuickQSPanel) quickQSPanelController.mView;
            Objects.requireNonNull(quickQSPanel);
            if (quickQSPanel.mListening) {
                QuickQSPanelController quickQSPanelController2 = this.mQuickQSPanelController;
                Objects.requireNonNull(quickQSPanelController2);
                Iterator<QSPanelControllerBase.TileRecord> it = quickQSPanelController2.mRecords.iterator();
                while (it.hasNext()) {
                    it.next().tile.refreshState();
                }
            }
            if (this.mQuickQSPanelController.switchTileLayout(false)) {
                ((QuickStatusBarHeader) this.mView).updateResources();
            }
            if (z) {
                this.mPrivacyIconsController.startListening();
                return;
            }
            HeaderPrivacyIconsController headerPrivacyIconsController = this.mPrivacyIconsController;
            Objects.requireNonNull(headerPrivacyIconsController);
            headerPrivacyIconsController.listening = false;
            headerPrivacyIconsController.privacyItemController.removeCallback(headerPrivacyIconsController.picCallback);
            headerPrivacyIconsController.privacyChipLogged = false;
        }
    }
}
