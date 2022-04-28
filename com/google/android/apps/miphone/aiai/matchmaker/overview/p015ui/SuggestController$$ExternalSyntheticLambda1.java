package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import com.android.p012wm.shell.compatui.CompatUIController;
import com.android.p012wm.shell.onehanded.OneHandedTimeoutHandler;
import com.android.p012wm.shell.pip.phone.PipInputConsumer;
import com.android.p012wm.shell.pip.phone.PipTouchHandler;
import com.android.systemui.Prefs;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.p006qs.QSPanelController;
import com.android.systemui.p006qs.QSTileRevealController;
import com.android.systemui.p006qs.tiles.DndTile$$ExternalSyntheticLambda0;
import com.android.systemui.p006qs.tiles.dialog.InternetDialog;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0;
import com.android.systemui.screenshot.ScreenshotEvent;
import com.android.systemui.screenshot.ScreenshotView;
import com.android.systemui.screenshot.SwipeDismissHandler;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBar$2$Callback$$ExternalSyntheticLambda0;
import com.android.systemui.volume.C1716D;
import com.android.systemui.volume.CaptionsToggleImageButton;
import com.android.systemui.volume.VolumeDialogImpl;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.InteractionContextParcelables$InteractionContext;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$InteractionType;
import com.google.android.apps.miphone.aiai.matchmaker.overview.common.BundleUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.LogUtils;
import java.util.Objects;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.SuggestController$$ExternalSyntheticLambda1 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SuggestController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SuggestController$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    public final void run() {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                SuggestController suggestController = (SuggestController) this.f$0;
                Objects.requireNonNull(suggestController);
                try {
                    Log.i("AiAiSuggestUi", "Connecting to system intelligence module.");
                    InteractionContextParcelables$InteractionContext interactionContextParcelables$InteractionContext = new InteractionContextParcelables$InteractionContext();
                    interactionContextParcelables$InteractionContext.interactionType = SuggestParcelables$InteractionType.SETUP;
                    ContentSuggestionsServiceWrapperImpl contentSuggestionsServiceWrapperImpl = suggestController.wrapper;
                    BundleUtils bundleUtils = suggestController.bundleUtils;
                    String packageName = suggestController.uiContext.getPackageName();
                    Objects.requireNonNull(bundleUtils);
                    contentSuggestionsServiceWrapperImpl.suggestContentSelections(-1, BundleUtils.createSelectionsRequest(packageName, "", -1, -1, interactionContextParcelables$InteractionContext, (Bundle) null, (LockFreeLinkedListKt) null), new SuggestController$$ExternalSyntheticLambda0(suggestController));
                    return;
                } catch (Exception e) {
                    LogUtils.m81e("Error while connecting to system intelligence module.", e);
                    return;
                }
            case 1:
                QSTileRevealController.C10021 r11 = (QSTileRevealController.C10021) this.f$0;
                Objects.requireNonNull(r11);
                QSPanelController qSPanelController = QSTileRevealController.this.mQSPanelController;
                Objects.requireNonNull(qSPanelController);
                QSPanel qSPanel = (QSPanel) qSPanelController.mView;
                Objects.requireNonNull(qSPanel);
                if (qSPanel.mExpanded) {
                    QSTileRevealController qSTileRevealController = QSTileRevealController.this;
                    qSTileRevealController.addTileSpecsToRevealed(qSTileRevealController.mTilesToReveal);
                    QSTileRevealController.this.mTilesToReveal.clear();
                    return;
                }
                return;
            case 2:
                InternetDialog internetDialog = (InternetDialog) this.f$0;
                boolean z2 = InternetDialog.DEBUG;
                Objects.requireNonNull(internetDialog);
                internetDialog.updateDialog(true);
                return;
            case 3:
                ScreenshotView screenshotView = (ScreenshotView) this.f$0;
                int i = ScreenshotView.$r8$clinit;
                Objects.requireNonNull(screenshotView);
                screenshotView.mUiEventLogger.log(ScreenshotEvent.SCREENSHOT_SMART_ACTION_TAPPED, 0, screenshotView.mPackageName);
                SwipeDismissHandler swipeDismissHandler = screenshotView.mSwipeDismissHandler;
                Objects.requireNonNull(swipeDismissHandler);
                swipeDismissHandler.dismiss(1.0f);
                return;
            case 4:
                RotationButtonController rotationButtonController = (RotationButtonController) this.f$0;
                LinearInterpolator linearInterpolator = RotationButtonController.LINEAR_INTERPOLATOR;
                Objects.requireNonNull(rotationButtonController);
                rotationButtonController.setRotateSuggestionButtonState(false, false);
                return;
            case 5:
                NotificationPanelViewController.KeyguardAffordanceHelperCallback keyguardAffordanceHelperCallback = (NotificationPanelViewController.KeyguardAffordanceHelperCallback) this.f$0;
                Objects.requireNonNull(keyguardAffordanceHelperCallback);
                NotificationPanelViewController.this.mKeyguardBottomArea.launchLeftAffordance();
                return;
            case FalsingManager.VERSION:
                StatusBar.C15432.Callback callback = (StatusBar.C15432.Callback) this.f$0;
                Objects.requireNonNull(callback);
                StatusBar.this.mNotificationShadeWindowController.setStateListener(new StatusBar$2$Callback$$ExternalSyntheticLambda0(callback));
                StatusBar.C15432 r0 = StatusBar.C15432.this;
                NotificationShadeWindowController notificationShadeWindowController = StatusBar.this.mNotificationShadeWindowController;
                if (r0.mOverlays.size() != 0) {
                    z = true;
                }
                notificationShadeWindowController.setForcePluginOpen(z, callback);
                return;
            case 7:
                VolumeDialogImpl volumeDialogImpl = (VolumeDialogImpl) this.f$0;
                String str = VolumeDialogImpl.TAG;
                Objects.requireNonNull(volumeDialogImpl);
                if (C1716D.BUG) {
                    Log.d(VolumeDialogImpl.TAG, "tool:checkODICaptionsTooltip() putBoolean true");
                }
                Prefs.putBoolean(volumeDialogImpl.mContext, "HasSeenODICaptionsTooltip", true);
                volumeDialogImpl.mHasSeenODICaptionsTooltip = true;
                CaptionsToggleImageButton captionsToggleImageButton = volumeDialogImpl.mODICaptionsIcon;
                if (captionsToggleImageButton != null) {
                    captionsToggleImageButton.postOnAnimation(new DndTile$$ExternalSyntheticLambda0(volumeDialogImpl, captionsToggleImageButton, 2));
                    return;
                }
                return;
            case 8:
                CompatUIController compatUIController = (CompatUIController) this.f$0;
                Objects.requireNonNull(compatUIController);
                compatUIController.mActiveLetterboxEduLayout = null;
                return;
            case 9:
                OneHandedTimeoutHandler oneHandedTimeoutHandler = (OneHandedTimeoutHandler) this.f$0;
                Objects.requireNonNull(oneHandedTimeoutHandler);
                int size = oneHandedTimeoutHandler.mListeners.size();
                while (true) {
                    size--;
                    if (size >= 0) {
                        ((OneHandedTimeoutHandler.TimeoutListener) oneHandedTimeoutHandler.mListeners.get(size)).onTimeout();
                    } else {
                        return;
                    }
                }
            default:
                PipInputConsumer pipInputConsumer = (PipInputConsumer) this.f$0;
                Objects.requireNonNull(pipInputConsumer);
                PipInputConsumer.RegistrationListener registrationListener = pipInputConsumer.mRegistrationListener;
                if (registrationListener != null) {
                    if (pipInputConsumer.mInputEventReceiver != null) {
                        z = true;
                    }
                    ((PipTouchHandler) ((ImageLoader$$ExternalSyntheticLambda0) registrationListener).f$0).onRegistrationChanged(z);
                    return;
                }
                return;
        }
    }
}
