package com.android.systemui.p006qs;

import android.permission.PermissionManager;
import android.provider.DeviceConfig;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.qs.HeaderPrivacyIconsController */
/* compiled from: HeaderPrivacyIconsController.kt */
public final class HeaderPrivacyIconsController {
    public final ActivityStarter activityStarter;
    public final AppOpsController appOpsController;
    public final Executor backgroundExecutor;
    public final String cameraSlot;
    public ChipVisibilityListener chipVisibilityListener;
    public final StatusIconContainer iconContainer;
    public boolean listening;
    public boolean locationIndicatorsEnabled;
    public final String locationSlot;
    public boolean micCameraIndicatorsEnabled;
    public final String micSlot;
    public final PermissionManager permissionManager;
    public final HeaderPrivacyIconsController$picCallback$1 picCallback;
    public final OngoingPrivacyChip privacyChip;
    public boolean privacyChipLogged;
    public final PrivacyDialogController privacyDialogController;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public boolean safetyCenterEnabled = DeviceConfig.getBoolean("privacy", "safety_center_is_enabled", false);
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0033  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x003d  */
    /* JADX WARNING: Removed duplicated region for block: B:22:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setChipVisibility(boolean r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 == 0) goto L_0x0029
            boolean r1 = r3.micCameraIndicatorsEnabled
            r2 = 1
            if (r1 != 0) goto L_0x000f
            boolean r1 = r3.locationIndicatorsEnabled
            if (r1 == 0) goto L_0x000d
            goto L_0x000f
        L_0x000d:
            r1 = r0
            goto L_0x0010
        L_0x000f:
            r1 = r2
        L_0x0010:
            if (r1 == 0) goto L_0x0029
            com.android.systemui.privacy.logging.PrivacyLogger r1 = r3.privacyLogger
            r1.logChipVisible(r2)
            boolean r1 = r3.privacyChipLogged
            if (r1 != 0) goto L_0x002e
            boolean r1 = r3.listening
            if (r1 == 0) goto L_0x002e
            r3.privacyChipLogged = r2
            com.android.internal.logging.UiEventLogger r1 = r3.uiEventLogger
            com.android.systemui.privacy.PrivacyChipEvent r2 = com.android.systemui.privacy.PrivacyChipEvent.ONGOING_INDICATORS_CHIP_VIEW
            r1.log(r2)
            goto L_0x002e
        L_0x0029:
            com.android.systemui.privacy.logging.PrivacyLogger r1 = r3.privacyLogger
            r1.logChipVisible(r0)
        L_0x002e:
            com.android.systemui.privacy.OngoingPrivacyChip r1 = r3.privacyChip
            if (r4 == 0) goto L_0x0033
            goto L_0x0035
        L_0x0033:
            r0 = 8
        L_0x0035:
            r1.setVisibility(r0)
            com.android.systemui.qs.ChipVisibilityListener r3 = r3.chipVisibilityListener
            if (r3 != 0) goto L_0x003d
            goto L_0x0040
        L_0x003d:
            r3.onChipVisibilityRefreshed(r4)
        L_0x0040:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.HeaderPrivacyIconsController.setChipVisibility(boolean):void");
    }

    public final void startListening() {
        this.listening = true;
        PrivacyItemController privacyItemController2 = this.privacyItemController;
        Objects.requireNonNull(privacyItemController2);
        this.micCameraIndicatorsEnabled = privacyItemController2.micCameraAvailable;
        PrivacyItemController privacyItemController3 = this.privacyItemController;
        Objects.requireNonNull(privacyItemController3);
        this.locationIndicatorsEnabled = privacyItemController3.locationAvailable;
        this.privacyItemController.addCallback(this.picCallback);
    }

    public final void onParentVisible() {
        boolean z;
        this.privacyChip.setOnClickListener(new HeaderPrivacyIconsController$onParentVisible$1(this));
        if (this.privacyChip.getVisibility() == 0) {
            z = true;
        } else {
            z = false;
        }
        setChipVisibility(z);
        PrivacyItemController privacyItemController2 = this.privacyItemController;
        Objects.requireNonNull(privacyItemController2);
        this.micCameraIndicatorsEnabled = privacyItemController2.micCameraAvailable;
        PrivacyItemController privacyItemController3 = this.privacyItemController;
        Objects.requireNonNull(privacyItemController3);
        this.locationIndicatorsEnabled = privacyItemController3.locationAvailable;
        updatePrivacyIconSlots();
    }

    public final void updatePrivacyIconSlots() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4 = this.micCameraIndicatorsEnabled;
        boolean z5 = false;
        if (z4 || this.locationIndicatorsEnabled) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            if (z4) {
                StatusIconContainer statusIconContainer = this.iconContainer;
                String str = this.cameraSlot;
                Objects.requireNonNull(statusIconContainer);
                if (statusIconContainer.mIgnoredSlots.contains(str)) {
                    z2 = false;
                } else {
                    statusIconContainer.mIgnoredSlots.add(str);
                    z2 = true;
                }
                if (z2) {
                    statusIconContainer.requestLayout();
                }
                StatusIconContainer statusIconContainer2 = this.iconContainer;
                String str2 = this.micSlot;
                Objects.requireNonNull(statusIconContainer2);
                if (statusIconContainer2.mIgnoredSlots.contains(str2)) {
                    z3 = false;
                } else {
                    statusIconContainer2.mIgnoredSlots.add(str2);
                    z3 = true;
                }
                if (z3) {
                    statusIconContainer2.requestLayout();
                }
            } else {
                this.iconContainer.removeIgnoredSlot(this.cameraSlot);
                this.iconContainer.removeIgnoredSlot(this.micSlot);
            }
            if (this.locationIndicatorsEnabled) {
                StatusIconContainer statusIconContainer3 = this.iconContainer;
                String str3 = this.locationSlot;
                Objects.requireNonNull(statusIconContainer3);
                if (!statusIconContainer3.mIgnoredSlots.contains(str3)) {
                    statusIconContainer3.mIgnoredSlots.add(str3);
                    z5 = true;
                }
                if (z5) {
                    statusIconContainer3.requestLayout();
                    return;
                }
                return;
            }
            this.iconContainer.removeIgnoredSlot(this.locationSlot);
            return;
        }
        this.iconContainer.removeIgnoredSlot(this.cameraSlot);
        this.iconContainer.removeIgnoredSlot(this.micSlot);
        this.iconContainer.removeIgnoredSlot(this.locationSlot);
    }

    public HeaderPrivacyIconsController(PrivacyItemController privacyItemController2, UiEventLogger uiEventLogger2, OngoingPrivacyChip ongoingPrivacyChip, PrivacyDialogController privacyDialogController2, PrivacyLogger privacyLogger2, StatusIconContainer statusIconContainer, PermissionManager permissionManager2, Executor executor, Executor executor2, ActivityStarter activityStarter2, AppOpsController appOpsController2, DeviceConfigProxy deviceConfigProxy) {
        this.privacyItemController = privacyItemController2;
        this.uiEventLogger = uiEventLogger2;
        this.privacyChip = ongoingPrivacyChip;
        this.privacyDialogController = privacyDialogController2;
        this.privacyLogger = privacyLogger2;
        this.iconContainer = statusIconContainer;
        this.permissionManager = permissionManager2;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.activityStarter = activityStarter2;
        this.appOpsController = appOpsController2;
        this.cameraSlot = ongoingPrivacyChip.getResources().getString(17041538);
        this.micSlot = ongoingPrivacyChip.getResources().getString(17041550);
        this.locationSlot = ongoingPrivacyChip.getResources().getString(17041548);
        HeaderPrivacyIconsController$devicePropertiesChangedListener$1 headerPrivacyIconsController$devicePropertiesChangedListener$1 = new HeaderPrivacyIconsController$devicePropertiesChangedListener$1(this);
        Objects.requireNonNull(deviceConfigProxy);
        DeviceConfig.addOnPropertiesChangedListener("privacy", executor2, headerPrivacyIconsController$devicePropertiesChangedListener$1);
        this.picCallback = new HeaderPrivacyIconsController$picCallback$1(this);
    }
}
