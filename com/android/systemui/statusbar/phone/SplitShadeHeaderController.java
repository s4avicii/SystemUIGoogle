package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.Dumpable;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p006qs.HeaderPrivacyIconsController;
import com.android.systemui.p006qs.carrier.QSCarrierGroup;
import com.android.systemui.p006qs.carrier.QSCarrierGroupController;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: SplitShadeHeaderController.kt */
public final class SplitShadeHeaderController implements Dumpable {
    public final List<String> carrierIconSlots;
    public final SplitShadeHeaderController$chipVisibilityListener$1 chipVisibilityListener;
    public final boolean combinedHeaders;
    public final StatusIconContainer iconContainer;
    public final StatusBarIconController.TintedIconManager iconManager;
    public final HeaderPrivacyIconsController privacyIconsController;
    public final QSCarrierGroupController qsCarrierGroupController;
    public float qsExpandedFraction = -1.0f;
    public int qsScrollY;
    public boolean shadeExpanded;
    public float shadeExpandedFraction = -1.0f;
    public boolean splitShadeMode;
    public final View statusBar;
    public final StatusBarIconController statusBarIconController;
    public boolean visible;

    public SplitShadeHeaderController(View view, StatusBarIconController statusBarIconController2, HeaderPrivacyIconsController headerPrivacyIconsController, QSCarrierGroupController.Builder builder, FeatureFlags featureFlags, BatteryMeterViewController batteryMeterViewController, DumpManager dumpManager) {
        List<String> list;
        ConstraintSet constraintSet;
        ConstraintSet constraintSet2;
        View view2 = view;
        HeaderPrivacyIconsController headerPrivacyIconsController2 = headerPrivacyIconsController;
        QSCarrierGroupController.Builder builder2 = builder;
        FeatureFlags featureFlags2 = featureFlags;
        BatteryMeterViewController batteryMeterViewController2 = batteryMeterViewController;
        this.statusBar = view2;
        this.statusBarIconController = statusBarIconController2;
        this.privacyIconsController = headerPrivacyIconsController2;
        this.combinedHeaders = featureFlags2.isEnabled(Flags.COMBINED_QS_HEADERS);
        SplitShadeHeaderController$chipVisibilityListener$1 splitShadeHeaderController$chipVisibilityListener$1 = new SplitShadeHeaderController$chipVisibilityListener$1(this);
        this.chipVisibilityListener = splitShadeHeaderController$chipVisibilityListener$1;
        if (view2 instanceof MotionLayout) {
            MotionLayout motionLayout = (MotionLayout) view2;
            Context context = motionLayout.getContext();
            Resources resources = motionLayout.getResources();
            Objects.requireNonNull(motionLayout);
            MotionScene motionScene = motionLayout.mScene;
            ConstraintSet constraintSet3 = null;
            if (motionScene == null) {
                constraintSet = null;
            } else {
                constraintSet = motionScene.getConstraintSet(C1777R.C1779id.qqs_header_constraint);
            }
            constraintSet.load(context, resources.getXml(C1777R.xml.qqs_header));
            Objects.requireNonNull(motionLayout);
            MotionScene motionScene2 = motionLayout.mScene;
            if (motionScene2 == null) {
                constraintSet2 = null;
            } else {
                constraintSet2 = motionScene2.getConstraintSet(C1777R.C1779id.qs_header_constraint);
            }
            constraintSet2.load(context, resources.getXml(C1777R.xml.qs_header));
            Objects.requireNonNull(motionLayout);
            MotionScene motionScene3 = motionLayout.mScene;
            (motionScene3 != null ? motionScene3.getConstraintSet(C1777R.C1779id.split_header_constraint) : constraintSet3).load(context, resources.getXml(C1777R.xml.split_header));
            Objects.requireNonNull(headerPrivacyIconsController);
            headerPrivacyIconsController2.chipVisibilityListener = splitShadeHeaderController$chipVisibilityListener$1;
        }
        batteryMeterViewController.init();
        BatteryMeterView batteryMeterView = (BatteryMeterView) view.findViewById(C1777R.C1779id.batteryRemainingIcon);
        batteryMeterViewController2.mIgnoreTunerUpdates = true;
        if (batteryMeterViewController2.mIsSubscribedForTunerUpdates) {
            batteryMeterViewController2.mTunerService.removeTunable(batteryMeterViewController2.mTunable);
            batteryMeterViewController2.mIsSubscribedForTunerUpdates = false;
        }
        batteryMeterView.setPercentShowMode(3);
        StatusIconContainer statusIconContainer = (StatusIconContainer) view.findViewById(C1777R.C1779id.statusIcons);
        this.iconContainer = statusIconContainer;
        StatusBarIconController.TintedIconManager tintedIconManager = new StatusBarIconController.TintedIconManager(statusIconContainer, featureFlags2);
        this.iconManager = tintedIconManager;
        tintedIconManager.setTint(Utils.getColorAttrDefaultColor(view.getContext(), 16842806));
        if (featureFlags2.isEnabled(Flags.COMBINED_STATUS_BAR_SIGNAL_ICONS)) {
            list = SetsKt__SetsKt.listOf(view.getContext().getString(17041554), view.getContext().getString(17041537));
        } else {
            list = Collections.singletonList(view.getContext().getString(17041551));
        }
        this.carrierIconSlots = list;
        QSCarrierGroup qSCarrierGroup = (QSCarrierGroup) view.findViewById(C1777R.C1779id.carrier_group);
        builder2.mView = qSCarrierGroup;
        this.qsCarrierGroupController = new QSCarrierGroupController(qSCarrierGroup, builder2.mActivityStarter, builder2.mHandler, builder2.mLooper, builder2.mNetworkController, builder2.mCarrierTextControllerBuilder, builder2.mContext, builder2.mCarrierConfigTracker, builder2.mFeatureFlags, builder2.mSlotIndexResolver);
        dumpManager.registerDumpable(this);
        updateVisibility();
        updateConstraints();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str;
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.visible, "visible: ", printWriter);
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.shadeExpanded, "shadeExpanded: ", printWriter);
        printWriter.println(Intrinsics.stringPlus("shadeExpandedFraction: ", Float.valueOf(this.shadeExpandedFraction)));
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(this.splitShadeMode, "splitShadeMode: ", printWriter);
        printWriter.println(Intrinsics.stringPlus("qsExpandedFraction: ", Float.valueOf(this.qsExpandedFraction)));
        printWriter.println(Intrinsics.stringPlus("qsScrollY: ", Integer.valueOf(this.qsScrollY)));
        if (this.combinedHeaders) {
            View view = this.statusBar;
            MotionLayout motionLayout = (MotionLayout) view;
            MotionLayout motionLayout2 = (MotionLayout) view;
            Objects.requireNonNull(motionLayout2);
            int i = motionLayout2.mCurrentState;
            if (i == C1777R.C1779id.qqs_header_constraint) {
                str = "QQS Header";
            } else if (i == C1777R.C1779id.qs_header_constraint) {
                str = "QS Header";
            } else if (i == C1777R.C1779id.split_header_constraint) {
                str = "Split Header";
            } else {
                str = "Unknown state";
            }
            printWriter.println(Intrinsics.stringPlus("currentState: ", str));
        }
    }

    public final void updateConstraints() {
        if (this.combinedHeaders) {
            MotionLayout motionLayout = (MotionLayout) this.statusBar;
            if (this.splitShadeMode) {
                motionLayout.setTransition((int) C1777R.C1779id.split_header_transition);
                return;
            }
            motionLayout.setTransition((int) C1777R.C1779id.header_transition);
            MotionLayout motionLayout2 = (MotionLayout) this.statusBar;
            Objects.requireNonNull(motionLayout2);
            motionLayout2.animateTo(0.0f);
            updatePosition$3();
            if (!this.splitShadeMode && this.combinedHeaders) {
                this.statusBar.setScrollY(this.qsScrollY);
            }
        }
    }

    public final void updatePosition$3() {
        View view = this.statusBar;
        if ((view instanceof MotionLayout) && !this.splitShadeMode && this.visible) {
            ((MotionLayout) view).setProgress(this.qsExpandedFraction);
        }
    }

    public final void updateVisibility() {
        int i;
        boolean z = false;
        if (!this.splitShadeMode && !this.combinedHeaders) {
            i = 8;
        } else if (this.shadeExpanded) {
            i = 0;
        } else {
            i = 4;
        }
        if (this.statusBar.getVisibility() != i) {
            this.statusBar.setVisibility(i);
            if (i == 0) {
                z = true;
            }
            if (this.visible != z) {
                this.visible = z;
                this.qsCarrierGroupController.setListening(z);
                if (this.visible) {
                    QSCarrierGroupController qSCarrierGroupController = this.qsCarrierGroupController;
                    Objects.requireNonNull(qSCarrierGroupController);
                    if (qSCarrierGroupController.mIsSingleCarrier) {
                        this.iconContainer.removeIgnoredSlots(this.carrierIconSlots);
                    } else {
                        this.iconContainer.addIgnoredSlots(this.carrierIconSlots);
                    }
                    QSCarrierGroupController qSCarrierGroupController2 = this.qsCarrierGroupController;
                    SplitShadeHeaderController$updateListeners$1 splitShadeHeaderController$updateListeners$1 = new SplitShadeHeaderController$updateListeners$1(this);
                    Objects.requireNonNull(qSCarrierGroupController2);
                    qSCarrierGroupController2.mOnSingleCarrierChangedListener = splitShadeHeaderController$updateListeners$1;
                    this.statusBarIconController.addIconGroup(this.iconManager);
                    return;
                }
                QSCarrierGroupController qSCarrierGroupController3 = this.qsCarrierGroupController;
                Objects.requireNonNull(qSCarrierGroupController3);
                qSCarrierGroupController3.mOnSingleCarrierChangedListener = null;
                this.statusBarIconController.removeIconGroup(this.iconManager);
            }
        }
    }
}
