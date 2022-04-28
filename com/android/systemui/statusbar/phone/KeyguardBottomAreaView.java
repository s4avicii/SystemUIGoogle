package com.android.systemui.statusbar.phone;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.quickaccesswallet.GetWalletCardsError;
import android.service.quickaccesswallet.GetWalletCardsResponse;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardPINView$$ExternalSyntheticLambda0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2;
import com.android.settingslib.Utils;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.R$anim;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.GhostedViewLaunchAnimatorController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistManager$$ExternalSyntheticLambda0;
import com.android.systemui.camera.CameraIntents;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.p004ui.ControlsActivity;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda0;
import com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda3;
import com.android.systemui.p006qs.QSTileHost$$ExternalSyntheticLambda1;
import com.android.systemui.p006qs.external.CustomTile$$ExternalSyntheticLambda1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.IntentButtonProvider;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda10;
import com.android.systemui.settings.CurrentUserTracker$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.KeyguardAffordanceView;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.PreviewInflater;
import com.android.systemui.tuner.LockscreenFragment;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

public class KeyguardBottomAreaView extends FrameLayout implements View.OnClickListener, KeyguardStateController.Callback, AccessibilityController.AccessibilityStateChangedCallback {
    public static final Intent PHONE_INTENT = new Intent("android.intent.action.DIAL");
    public AccessibilityController mAccessibilityController;
    public C14293 mAccessibilityDelegate;
    public ActivityIntentHelper mActivityIntentHelper;
    public ActivityStarter mActivityStarter;
    public KeyguardAffordanceHelper mAffordanceHelper;
    public View mAmbientIndicationArea;
    public int mBurnInXOffset;
    public int mBurnInYOffset;
    public KeyguardPreviewContainer mCameraPreview;
    public WalletCardRetriever mCardRetriever;
    public boolean mControlServicesAvailable;
    public ImageView mControlsButton;
    public ControlsComponent mControlsComponent;
    public float mDarkAmount;
    public final C14358 mDevicePolicyReceiver;
    public boolean mDozing;
    public FalsingManager mFalsingManager;
    public FlashlightController mFlashlightController;
    public boolean mHasCard;
    public ViewGroup mIndicationArea;
    public int mIndicationBottomMargin;
    public int mIndicationPadding;
    public TextView mIndicationText;
    public TextView mIndicationTextBottom;
    public KeyguardStateController mKeyguardStateController;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public KeyguardAffordanceView mLeftAffordanceView;
    public IntentButtonProvider.IntentButton mLeftButton;
    public ExtensionControllerImpl.ExtensionImpl mLeftExtension;
    public boolean mLeftIsVoiceAssist;
    public KeyguardPreviewContainer mLeftPreview;
    public C14282 mListingCallback;
    public ViewGroup mOverlayContainer;
    public ViewGroup mPreviewContainer;
    public PreviewInflater mPreviewInflater;
    public boolean mPrewarmBound;
    public final C14271 mPrewarmConnection;
    public Messenger mPrewarmMessenger;
    public ImageView mQRCodeScannerButton;
    public QRCodeScannerController mQRCodeScannerController;
    public QuickAccessWalletController mQuickAccessWalletController;
    public KeyguardAffordanceView mRightAffordanceView;
    public IntentButtonProvider.IntentButton mRightButton;
    public ExtensionControllerImpl.ExtensionImpl mRightExtension;
    public final boolean mShowCameraAffordance;
    public final boolean mShowLeftAffordance;
    public StatusBar mStatusBar;
    public final C14379 mUpdateMonitorCallback;
    public boolean mUserSetupComplete;
    public ImageView mWalletButton;

    public class DefaultLeftButton implements IntentButtonProvider.IntentButton {
        public IntentButtonProvider.IntentButton.IconState mIconState = new IntentButtonProvider.IntentButton.IconState();

        public DefaultLeftButton() {
        }

        public final IntentButtonProvider.IntentButton.IconState getIcon() {
            KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
            Intent intent = KeyguardBottomAreaView.PHONE_INTENT;
            Objects.requireNonNull(keyguardBottomAreaView);
            AssistManager assistManager = (AssistManager) Dependency.get(AssistManager.class);
            Objects.requireNonNull(assistManager);
            keyguardBottomAreaView.mLeftIsVoiceAssist = ((Boolean) DejankUtils.whitelistIpcs(new AssistManager$$ExternalSyntheticLambda0(assistManager))).booleanValue();
            KeyguardBottomAreaView keyguardBottomAreaView2 = KeyguardBottomAreaView.this;
            boolean z = true;
            if (keyguardBottomAreaView2.mLeftIsVoiceAssist) {
                IntentButtonProvider.IntentButton.IconState iconState = this.mIconState;
                if (!keyguardBottomAreaView2.mUserSetupComplete || !keyguardBottomAreaView2.mShowLeftAffordance) {
                    z = false;
                }
                iconState.isVisible = z;
                Objects.requireNonNull(keyguardBottomAreaView2);
                this.mIconState.drawable = KeyguardBottomAreaView.this.mContext.getDrawable(C1777R.C1778drawable.ic_mic_26dp);
                this.mIconState.contentDescription = KeyguardBottomAreaView.this.mContext.getString(C1777R.string.accessibility_voice_assist_button);
            } else {
                IntentButtonProvider.IntentButton.IconState iconState2 = this.mIconState;
                if (!keyguardBottomAreaView2.mUserSetupComplete || !keyguardBottomAreaView2.mShowLeftAffordance || !KeyguardBottomAreaView.m237$$Nest$misPhoneVisible(keyguardBottomAreaView2)) {
                    z = false;
                }
                iconState2.isVisible = z;
                this.mIconState.drawable = KeyguardBottomAreaView.this.mContext.getDrawable(17302810);
                this.mIconState.contentDescription = KeyguardBottomAreaView.this.mContext.getString(C1777R.string.accessibility_phone_button);
            }
            return this.mIconState;
        }

        public final Intent getIntent() {
            return KeyguardBottomAreaView.PHONE_INTENT;
        }
    }

    public class DefaultRightButton implements IntentButtonProvider.IntentButton {
        public IntentButtonProvider.IntentButton.IconState mIconState = new IntentButtonProvider.IntentButton.IconState();

        public DefaultRightButton() {
        }

        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0023, code lost:
            if (r0.resolveCameraIntent() != null) goto L_0x0027;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.android.systemui.plugins.IntentButtonProvider.IntentButton.IconState getIcon() {
            /*
                r5 = this;
                com.android.systemui.statusbar.phone.KeyguardBottomAreaView r0 = com.android.systemui.statusbar.phone.KeyguardBottomAreaView.this
                com.android.systemui.statusbar.phone.StatusBar r0 = r0.mStatusBar
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L_0x0010
                boolean r0 = r0.isCameraAllowedByAdmin()
                if (r0 != 0) goto L_0x0010
                r0 = r1
                goto L_0x0011
            L_0x0010:
                r0 = r2
            L_0x0011:
                com.android.systemui.plugins.IntentButtonProvider$IntentButton$IconState r3 = r5.mIconState
                if (r0 != 0) goto L_0x0026
                com.android.systemui.statusbar.phone.KeyguardBottomAreaView r0 = com.android.systemui.statusbar.phone.KeyguardBottomAreaView.this
                boolean r4 = r0.mShowCameraAffordance
                if (r4 == 0) goto L_0x0026
                boolean r4 = r0.mUserSetupComplete
                if (r4 == 0) goto L_0x0026
                android.content.pm.ResolveInfo r0 = r0.resolveCameraIntent()
                if (r0 == 0) goto L_0x0026
                goto L_0x0027
            L_0x0026:
                r1 = r2
            L_0x0027:
                r3.isVisible = r1
                com.android.systemui.plugins.IntentButtonProvider$IntentButton$IconState r0 = r5.mIconState
                com.android.systemui.statusbar.phone.KeyguardBottomAreaView r1 = com.android.systemui.statusbar.phone.KeyguardBottomAreaView.this
                android.content.Context r1 = r1.mContext
                r2 = 2131231776(0x7f080420, float:1.8079643E38)
                android.graphics.drawable.Drawable r1 = r1.getDrawable(r2)
                r0.drawable = r1
                com.android.systemui.plugins.IntentButtonProvider$IntentButton$IconState r0 = r5.mIconState
                com.android.systemui.statusbar.phone.KeyguardBottomAreaView r1 = com.android.systemui.statusbar.phone.KeyguardBottomAreaView.this
                android.content.Context r1 = r1.mContext
                r2 = 2131951685(0x7f130045, float:1.9539791E38)
                java.lang.String r1 = r1.getString(r2)
                r0.contentDescription = r1
                com.android.systemui.plugins.IntentButtonProvider$IntentButton$IconState r5 = r5.mIconState
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.DefaultRightButton.getIcon():com.android.systemui.plugins.IntentButtonProvider$IntentButton$IconState");
        }

        public final Intent getIntent() {
            boolean canDismissLockScreen = KeyguardBottomAreaView.this.mKeyguardStateController.canDismissLockScreen();
            if (!KeyguardBottomAreaView.this.mKeyguardStateController.isMethodSecure() || canDismissLockScreen) {
                return CameraIntents.getInsecureCameraIntent(KeyguardBottomAreaView.this.getContext());
            }
            Context context = KeyguardBottomAreaView.this.getContext();
            Intent intent = new Intent("android.media.action.STILL_IMAGE_CAMERA_SECURE");
            String string = context.getResources().getString(C1777R.string.config_cameraGesturePackage);
            if (string == null || TextUtils.isEmpty(string)) {
                string = null;
            }
            if (string != null) {
                intent.setPackage(string);
            }
            return intent.addFlags(8388608);
        }
    }

    public class WalletCardRetriever implements QuickAccessWalletClient.OnWalletCardsRetrievedCallback {
        public WalletCardRetriever() {
        }

        public final void onWalletCardRetrievalError(GetWalletCardsError getWalletCardsError) {
            KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
            keyguardBottomAreaView.mHasCard = false;
            keyguardBottomAreaView.updateWalletVisibility();
            KeyguardBottomAreaView.this.updateAffordanceColors();
        }

        public final void onWalletCardsRetrieved(GetWalletCardsResponse getWalletCardsResponse) {
            KeyguardBottomAreaView.this.mHasCard = !getWalletCardsResponse.getWalletCards().isEmpty();
            QuickAccessWalletController quickAccessWalletController = KeyguardBottomAreaView.this.mQuickAccessWalletController;
            Objects.requireNonNull(quickAccessWalletController);
            Drawable tileIcon = quickAccessWalletController.mQuickAccessWalletClient.getTileIcon();
            if (tileIcon != null) {
                KeyguardBottomAreaView.this.mWalletButton.setImageDrawable(tileIcon);
            }
            KeyguardBottomAreaView.this.updateWalletVisibility();
            KeyguardBottomAreaView.this.updateAffordanceColors();
        }
    }

    public KeyguardBottomAreaView(Context context) {
        this(context, (AttributeSet) null);
    }

    public static void startFinishDozeAnimationElement(ImageView imageView, long j) {
        imageView.setAlpha(0.0f);
        imageView.setTranslationY((float) (imageView.getHeight() / 2));
        imageView.animate().alpha(1.0f).translationY(0.0f).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).setStartDelay(j).setDuration(250);
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void dozeTimeTick() {
        float burnInOffset = (float) (R$anim.getBurnInOffset(this.mBurnInYOffset * 2, false) - this.mBurnInYOffset);
        this.mIndicationArea.setTranslationY(this.mDarkAmount * burnInOffset);
        View view = this.mAmbientIndicationArea;
        if (view != null) {
            view.setTranslationY(burnInOffset * this.mDarkAmount);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void inflateCameraPreview() {
        /*
            r4 = this;
            android.view.ViewGroup r0 = r4.mPreviewContainer
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            com.android.systemui.statusbar.phone.KeyguardPreviewContainer r1 = r4.mCameraPreview
            r2 = 0
            if (r1 == 0) goto L_0x0015
            r0.removeView(r1)
            int r0 = r1.getVisibility()
            if (r0 != 0) goto L_0x0015
            r0 = 1
            goto L_0x0016
        L_0x0015:
            r0 = r2
        L_0x0016:
            com.android.systemui.statusbar.policy.PreviewInflater r1 = r4.mPreviewInflater
            com.android.systemui.plugins.IntentButtonProvider$IntentButton r3 = r4.mRightButton
            android.content.Intent r3 = r3.getIntent()
            com.android.systemui.statusbar.phone.KeyguardPreviewContainer r1 = r1.inflatePreview((android.content.Intent) r3)
            r4.mCameraPreview = r1
            if (r1 == 0) goto L_0x0034
            android.view.ViewGroup r3 = r4.mPreviewContainer
            r3.addView(r1)
            com.android.systemui.statusbar.phone.KeyguardPreviewContainer r1 = r4.mCameraPreview
            if (r0 == 0) goto L_0x0030
            goto L_0x0031
        L_0x0030:
            r2 = 4
        L_0x0031:
            r1.setVisibility(r2)
        L_0x0034:
            com.android.systemui.statusbar.phone.KeyguardAffordanceHelper r4 = r4.mAffordanceHelper
            if (r4 == 0) goto L_0x003b
            r4.updatePreviews()
        L_0x003b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.inflateCameraPreview():void");
    }

    public final void launchCamera(String str) {
        boolean z;
        boolean z2;
        final Intent intent = this.mRightButton.getIntent();
        intent.putExtra("com.android.systemui.camera_launch_source", str);
        ActivityIntentHelper activityIntentHelper = this.mActivityIntentHelper;
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        Objects.requireNonNull(activityIntentHelper);
        if (activityIntentHelper.getTargetActivityInfo(intent, currentUser, false) == null) {
            z = true;
        } else {
            z = false;
        }
        String action = intent.getAction();
        if (action == null) {
            z2 = false;
        } else {
            z2 = action.equals("android.media.action.STILL_IMAGE_CAMERA_SECURE");
        }
        if (!z2 || z) {
            this.mActivityStarter.startActivity(intent, false, (ActivityStarter.Callback) new ActivityStarter.Callback() {
                public final void onActivityStarted(int i) {
                    boolean z;
                    KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
                    Intent intent = KeyguardBottomAreaView.PHONE_INTENT;
                    if (i == 0 || i == 3 || i == 2) {
                        z = true;
                    } else {
                        z = false;
                    }
                    keyguardBottomAreaView.unbindCameraPrewarmService(z);
                }
            });
        } else {
            AsyncTask.execute(new Runnable() {
                public final void run() {
                    int i;
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    final boolean z = true;
                    makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    makeBasic.setRotationAnimationHint(3);
                    try {
                        IActivityTaskManager service = ActivityTaskManager.getService();
                        String basePackageName = KeyguardBottomAreaView.this.getContext().getBasePackageName();
                        String attributionTag = KeyguardBottomAreaView.this.getContext().getAttributionTag();
                        Intent intent = intent;
                        i = service.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent, intent.resolveTypeIfNeeded(KeyguardBottomAreaView.this.getContext().getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, makeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
                    } catch (RemoteException e) {
                        Log.w("StatusBar/KeyguardBottomAreaView", "Unable to start camera activity", e);
                        i = -96;
                    }
                    Intent intent2 = KeyguardBottomAreaView.PHONE_INTENT;
                    if (!(i == 0 || i == 3 || i == 2)) {
                        z = false;
                    }
                    KeyguardBottomAreaView.this.post(new Runnable() {
                        public final void run() {
                            KeyguardBottomAreaView.this.unbindCameraPrewarmService(z);
                        }
                    });
                }
            });
        }
    }

    public final void launchLeftAffordance() {
        if (this.mLeftIsVoiceAssist) {
            launchVoiceAssist();
            return;
        }
        final TelecomManager from = TelecomManager.from(this.mContext);
        if (from.isInCall()) {
            AsyncTask.execute(new Runnable() {
                public final void run() {
                    from.showInCallScreen(false);
                }
            });
            return;
        }
        boolean z = true;
        if (TextUtils.isEmpty((CharSequence) null) || ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_keyguard_left_unlock", 1) == 0) {
            z = false;
        }
        this.mActivityStarter.startActivity(this.mLeftButton.getIntent(), z);
    }

    @VisibleForTesting
    public void launchVoiceAssist() {
        boolean z;
        C14336 r0 = new Runnable() {
            public final void run() {
                AssistManager assistManager = (AssistManager) Dependency.get(AssistManager.class);
                Objects.requireNonNull(assistManager);
                assistManager.mAssistUtils.launchVoiceAssistFromKeyguard();
            }
        };
        if (!this.mKeyguardStateController.canDismissLockScreen()) {
            ((Executor) Dependency.get(Dependency.BACKGROUND_EXECUTOR)).execute(r0);
            return;
        }
        if (TextUtils.isEmpty((CharSequence) null) || ((TunerService) Dependency.get(TunerService.class)).getValue("sysui_keyguard_right_unlock", 1) == 0) {
            z = false;
        } else {
            z = true;
        }
        this.mStatusBar.executeRunnableDismissingKeyguard(r0, z, false, true);
    }

    public final void onAttachedToWindow() {
        Class<IntentButtonProvider> cls = IntentButtonProvider.class;
        Class cls2 = ExtensionController.class;
        super.onAttachedToWindow();
        AccessibilityController accessibilityController = this.mAccessibilityController;
        Objects.requireNonNull(accessibilityController);
        accessibilityController.mChangeCallbacks.add(this);
        onStateChanged(accessibilityController.mAccessibilityEnabled, accessibilityController.mTouchExplorationEnabled);
        ExtensionControllerImpl.ExtensionBuilder newExtension = ((ExtensionController) Dependency.get(cls2)).newExtension();
        KeyguardBottomAreaView$$ExternalSyntheticLambda0 keyguardBottomAreaView$$ExternalSyntheticLambda0 = KeyguardBottomAreaView$$ExternalSyntheticLambda0.INSTANCE;
        Objects.requireNonNull(newExtension);
        ExtensionControllerImpl.ExtensionImpl<T> extensionImpl = newExtension.mExtension;
        Objects.requireNonNull(extensionImpl);
        extensionImpl.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.PluginItem("com.android.systemui.action.PLUGIN_LOCKSCREEN_RIGHT_BUTTON", cls, keyguardBottomAreaView$$ExternalSyntheticLambda0));
        newExtension.withTunerFactory(new LockscreenFragment.LockButtonFactory(this.mContext, "sysui_keyguard_right"));
        newExtension.withDefault(new KeyguardBottomAreaView$$ExternalSyntheticLambda2(this));
        newExtension.mExtension.mCallbacks.add(new QSTileHost$$ExternalSyntheticLambda1(this, 3));
        this.mRightExtension = newExtension.build();
        ExtensionControllerImpl.ExtensionBuilder newExtension2 = ((ExtensionController) Dependency.get(cls2)).newExtension();
        KeyguardBottomAreaView$$ExternalSyntheticLambda1 keyguardBottomAreaView$$ExternalSyntheticLambda1 = KeyguardBottomAreaView$$ExternalSyntheticLambda1.INSTANCE;
        Objects.requireNonNull(newExtension2);
        ExtensionControllerImpl.ExtensionImpl<T> extensionImpl2 = newExtension2.mExtension;
        Objects.requireNonNull(extensionImpl2);
        extensionImpl2.mProducers.add(new ExtensionControllerImpl.ExtensionImpl.PluginItem("com.android.systemui.action.PLUGIN_LOCKSCREEN_LEFT_BUTTON", cls, keyguardBottomAreaView$$ExternalSyntheticLambda1));
        newExtension2.withTunerFactory(new LockscreenFragment.LockButtonFactory(this.mContext, "sysui_keyguard_left"));
        newExtension2.withDefault(new CustomTile$$ExternalSyntheticLambda1(this, 1));
        newExtension2.mExtension.mCallbacks.add(new CurrentUserTracker$$ExternalSyntheticLambda0(this, 1));
        this.mLeftExtension = newExtension2.build();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        getContext().registerReceiverAsUser(this.mDevicePolicyReceiver, UserHandle.ALL, intentFilter, (String) null, (Handler) null);
        KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        keyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        this.mKeyguardStateController.addCallback(this);
    }

    public final void onClick(View view) {
        if (view == this.mRightAffordanceView) {
            launchCamera("lockscreen_affordance");
        } else if (view == this.mLeftAffordanceView) {
            launchLeftAffordance();
        }
    }

    public final void onKeyguardShowingChanged() {
        QuickAccessWalletController quickAccessWalletController;
        if (this.mKeyguardStateController.isShowing() && (quickAccessWalletController = this.mQuickAccessWalletController) != null) {
            quickAccessWalletController.queryWalletCards(this.mCardRetriever);
        }
    }

    public final void onStateChanged(boolean z, boolean z2) {
        this.mRightAffordanceView.setClickable(z2);
        this.mLeftAffordanceView.setClickable(z2);
        this.mRightAffordanceView.setFocusable(z);
        this.mLeftAffordanceView.setFocusable(z);
    }

    public final ResolveInfo resolveCameraIntent() {
        return this.mContext.getPackageManager().resolveActivityAsUser(this.mRightButton.getIntent(), 65536, KeyguardUpdateMonitor.getCurrentUser());
    }

    public final void setAffordanceAlpha(float f) {
        this.mLeftAffordanceView.setAlpha(f);
        this.mRightAffordanceView.setAlpha(f);
        this.mIndicationArea.setAlpha(f);
        this.mWalletButton.setAlpha(f);
        this.mQRCodeScannerButton.setAlpha(f);
        this.mControlsButton.setAlpha(f);
    }

    public final void setDozing$2(boolean z, boolean z2) {
        this.mDozing = z;
        updateCameraVisibility();
        updateLeftAffordanceIcon();
        updateWalletVisibility();
        updateControlsVisibility();
        updateQRCodeButtonVisibility();
        if (z) {
            this.mOverlayContainer.setVisibility(4);
            return;
        }
        this.mOverlayContainer.setVisibility(0);
        if (z2) {
            long j = 0;
            if (this.mWalletButton.getVisibility() == 0) {
                startFinishDozeAnimationElement(this.mWalletButton, 0);
            }
            if (this.mQRCodeScannerButton.getVisibility() == 0) {
                startFinishDozeAnimationElement(this.mQRCodeScannerButton, 0);
            }
            if (this.mControlsButton.getVisibility() == 0) {
                startFinishDozeAnimationElement(this.mControlsButton, 0);
            }
            if (this.mLeftAffordanceView.getVisibility() == 0) {
                startFinishDozeAnimationElement(this.mLeftAffordanceView, 0);
                j = 48;
            }
            if (this.mRightAffordanceView.getVisibility() == 0) {
                startFinishDozeAnimationElement(this.mRightAffordanceView, j);
            }
        }
    }

    public final void unbindCameraPrewarmService(boolean z) {
        if (this.mPrewarmBound) {
            Messenger messenger = this.mPrewarmMessenger;
            if (messenger != null && z) {
                try {
                    messenger.send(Message.obtain((Handler) null, 1));
                } catch (RemoteException e) {
                    Log.w("StatusBar/KeyguardBottomAreaView", "Error sending camera fired message", e);
                }
            }
            this.mContext.unbindService(this.mPrewarmConnection);
            this.mPrewarmBound = false;
        }
    }

    public final void updateAffordanceColors() {
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.mContext, 16842806);
        this.mWalletButton.getDrawable().setTint(colorAttrDefaultColor);
        this.mControlsButton.getDrawable().setTint(colorAttrDefaultColor);
        this.mQRCodeScannerButton.getDrawable().setTint(colorAttrDefaultColor);
        ColorStateList colorAttr = Utils.getColorAttr(this.mContext, 17956909);
        this.mWalletButton.setBackgroundTintList(colorAttr);
        this.mControlsButton.setBackgroundTintList(colorAttr);
        this.mQRCodeScannerButton.setBackgroundTintList(colorAttr);
    }

    public final void updateCameraVisibility() {
        int i;
        KeyguardAffordanceView keyguardAffordanceView = this.mRightAffordanceView;
        if (keyguardAffordanceView != null) {
            if (this.mDozing || !this.mShowCameraAffordance || !this.mRightButton.getIcon().isVisible) {
                i = 8;
            } else {
                i = 0;
            }
            keyguardAffordanceView.setVisibility(i);
        }
    }

    public final void updateControlsVisibility() {
        ControlsComponent controlsComponent = this.mControlsComponent;
        if (controlsComponent != null) {
            this.mControlsButton.setImageResource(controlsComponent.controlsTileResourceConfiguration.getTileImageId());
            ImageView imageView = this.mControlsButton;
            Context context = getContext();
            ControlsComponent controlsComponent2 = this.mControlsComponent;
            Objects.requireNonNull(controlsComponent2);
            imageView.setContentDescription(context.getString(controlsComponent2.controlsTileResourceConfiguration.getTileTitleId()));
            boolean booleanValue = ((Boolean) this.mControlsComponent.getControlsController().map(SavedNetworkTracker$$ExternalSyntheticLambda0.INSTANCE$1).orElse(Boolean.FALSE)).booleanValue();
            if (this.mDozing || !booleanValue || !this.mControlServicesAvailable || this.mControlsComponent.getVisibility() != ControlsComponent.Visibility.AVAILABLE) {
                this.mControlsButton.setVisibility(8);
                if (this.mWalletButton.getVisibility() == 8) {
                    this.mIndicationArea.setPadding(0, 0, 0, 0);
                    return;
                }
                return;
            }
            this.mControlsButton.setVisibility(0);
            this.mControlsButton.setOnClickListener(new ScreenshotView$$ExternalSyntheticLambda10(this, 1));
            ViewGroup viewGroup = this.mIndicationArea;
            int i = this.mIndicationPadding;
            viewGroup.setPadding(i, 0, i, 0);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002e, code lost:
        if (r1 != r2.mShouldTint) goto L_0x0030;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateLeftAffordanceIcon() {
        /*
            r4 = this;
            boolean r0 = r4.mShowLeftAffordance
            r1 = 8
            if (r0 == 0) goto L_0x0041
            boolean r0 = r4.mDozing
            if (r0 == 0) goto L_0x000b
            goto L_0x0041
        L_0x000b:
            com.android.systemui.plugins.IntentButtonProvider$IntentButton r0 = r4.mLeftButton
            com.android.systemui.plugins.IntentButtonProvider$IntentButton$IconState r0 = r0.getIcon()
            com.android.systemui.statusbar.KeyguardAffordanceView r2 = r4.mLeftAffordanceView
            boolean r3 = r0.isVisible
            if (r3 == 0) goto L_0x0018
            r1 = 0
        L_0x0018:
            r2.setVisibility(r1)
            android.graphics.drawable.Drawable r1 = r0.drawable
            com.android.systemui.statusbar.KeyguardAffordanceView r2 = r4.mLeftAffordanceView
            android.graphics.drawable.Drawable r2 = r2.getDrawable()
            if (r1 != r2) goto L_0x0030
            boolean r1 = r0.tint
            com.android.systemui.statusbar.KeyguardAffordanceView r2 = r4.mLeftAffordanceView
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mShouldTint
            if (r1 == r2) goto L_0x0039
        L_0x0030:
            com.android.systemui.statusbar.KeyguardAffordanceView r1 = r4.mLeftAffordanceView
            android.graphics.drawable.Drawable r2 = r0.drawable
            boolean r3 = r0.tint
            r1.setImageDrawable(r2, r3)
        L_0x0039:
            com.android.systemui.statusbar.KeyguardAffordanceView r4 = r4.mLeftAffordanceView
            java.lang.CharSequence r0 = r0.contentDescription
            r4.setContentDescription(r0)
            return
        L_0x0041:
            com.android.systemui.statusbar.KeyguardAffordanceView r4 = r4.mLeftAffordanceView
            r4.setVisibility(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.updateLeftAffordanceIcon():void");
    }

    public final void updateQRCodeButtonVisibility() {
        boolean z;
        QuickAccessWalletController quickAccessWalletController = this.mQuickAccessWalletController;
        if (quickAccessWalletController != null) {
            Objects.requireNonNull(quickAccessWalletController);
            if (quickAccessWalletController.mWalletEnabled) {
                return;
            }
        }
        QRCodeScannerController qRCodeScannerController = this.mQRCodeScannerController;
        if (qRCodeScannerController != null) {
            Objects.requireNonNull(qRCodeScannerController);
            if (!qRCodeScannerController.mQRCodeScannerEnabled || qRCodeScannerController.mIntent == null || !qRCodeScannerController.mConfigEnableLockScreenButton) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                this.mQRCodeScannerButton.setVisibility(0);
                this.mQRCodeScannerButton.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda0(this, 2));
                ViewGroup viewGroup = this.mIndicationArea;
                int i = this.mIndicationPadding;
                viewGroup.setPadding(i, 0, i, 0);
                return;
            }
        }
        this.mQRCodeScannerButton.setVisibility(8);
        this.mIndicationArea.setPadding(0, 0, 0, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x002a, code lost:
        if (r1 != r2.mShouldTint) goto L_0x002c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateRightAffordanceIcon() {
        /*
            r4 = this;
            com.android.systemui.plugins.IntentButtonProvider$IntentButton r0 = r4.mRightButton
            com.android.systemui.plugins.IntentButtonProvider$IntentButton$IconState r0 = r0.getIcon()
            com.android.systemui.statusbar.KeyguardAffordanceView r1 = r4.mRightAffordanceView
            boolean r2 = r4.mDozing
            if (r2 != 0) goto L_0x0012
            boolean r2 = r0.isVisible
            if (r2 == 0) goto L_0x0012
            r2 = 0
            goto L_0x0014
        L_0x0012:
            r2 = 8
        L_0x0014:
            r1.setVisibility(r2)
            android.graphics.drawable.Drawable r1 = r0.drawable
            com.android.systemui.statusbar.KeyguardAffordanceView r2 = r4.mRightAffordanceView
            android.graphics.drawable.Drawable r2 = r2.getDrawable()
            if (r1 != r2) goto L_0x002c
            boolean r1 = r0.tint
            com.android.systemui.statusbar.KeyguardAffordanceView r2 = r4.mRightAffordanceView
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mShouldTint
            if (r1 == r2) goto L_0x0035
        L_0x002c:
            com.android.systemui.statusbar.KeyguardAffordanceView r1 = r4.mRightAffordanceView
            android.graphics.drawable.Drawable r2 = r0.drawable
            boolean r3 = r0.tint
            r1.setImageDrawable(r2, r3)
        L_0x0035:
            com.android.systemui.statusbar.KeyguardAffordanceView r4 = r4.mRightAffordanceView
            java.lang.CharSequence r0 = r0.contentDescription
            r4.setContentDescription(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.KeyguardBottomAreaView.updateRightAffordanceIcon():void");
    }

    public final void updateWalletVisibility() {
        QuickAccessWalletController quickAccessWalletController;
        if (!this.mDozing && (quickAccessWalletController = this.mQuickAccessWalletController) != null) {
            Objects.requireNonNull(quickAccessWalletController);
            if (quickAccessWalletController.mWalletEnabled && this.mHasCard) {
                this.mWalletButton.setVisibility(0);
                this.mWalletButton.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda3(this, 1));
                ViewGroup viewGroup = this.mIndicationArea;
                int i = this.mIndicationPadding;
                viewGroup.setPadding(i, 0, i, 0);
                return;
            }
        }
        this.mWalletButton.setVisibility(8);
        if (this.mControlsButton.getVisibility() == 8) {
            this.mIndicationArea.setPadding(0, 0, 0, 0);
        }
    }

    /* renamed from: $r8$lambda$swNNSxHxCV3-LqHmDl5jM82UINM  reason: not valid java name */
    public static void m236$r8$lambda$swNNSxHxCV3LqHmDl5jM82UINM(KeyguardBottomAreaView keyguardBottomAreaView) {
        Objects.requireNonNull(keyguardBottomAreaView);
        QRCodeScannerController qRCodeScannerController = keyguardBottomAreaView.mQRCodeScannerController;
        Objects.requireNonNull(qRCodeScannerController);
        Intent intent = qRCodeScannerController.mIntent;
        if (intent != null) {
            try {
                keyguardBottomAreaView.mContext.startActivity(intent);
            } catch (ActivityNotFoundException unused) {
                Log.e("StatusBar/KeyguardBottomAreaView", "Unexpected intent: " + intent + " when the QR code scanner button was clicked");
            }
        }
    }

    public static void $r8$lambda$w7e0GjE7nMrsYa2XpHgh7Re8_rw(KeyguardBottomAreaView keyguardBottomAreaView, View view) {
        Objects.requireNonNull(keyguardBottomAreaView);
        if (!keyguardBottomAreaView.mFalsingManager.isFalseTap(1)) {
            Intent putExtra = new Intent(keyguardBottomAreaView.mContext, ControlsActivity.class).addFlags(335544320).putExtra("extra_animate", true);
            GhostedViewLaunchAnimatorController ghostedViewLaunchAnimatorController = null;
            if (view != null) {
                ghostedViewLaunchAnimatorController = ActivityLaunchAnimator.Controller.fromView(view, (Integer) null);
            }
            if (keyguardBottomAreaView.mControlsComponent.getVisibility() == ControlsComponent.Visibility.AVAILABLE) {
                keyguardBottomAreaView.mActivityStarter.startActivity(putExtra, true, (ActivityLaunchAnimator.Controller) ghostedViewLaunchAnimatorController, true);
            } else {
                keyguardBottomAreaView.mActivityStarter.postStartActivityDismissingKeyguard(putExtra, 0, ghostedViewLaunchAnimatorController);
            }
        }
    }

    /* renamed from: -$$Nest$misPhoneVisible  reason: not valid java name */
    public static boolean m237$$Nest$misPhoneVisible(KeyguardBottomAreaView keyguardBottomAreaView) {
        Objects.requireNonNull(keyguardBottomAreaView);
        PackageManager packageManager = keyguardBottomAreaView.mContext.getPackageManager();
        if (!packageManager.hasSystemFeature("android.hardware.telephony") || packageManager.resolveActivity(PHONE_INTENT, 0) == null) {
            return false;
        }
        return true;
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int i;
        if (windowInsets.getDisplayCutout() != null) {
            i = windowInsets.getDisplayCutout().getSafeInsetBottom();
        } else {
            i = 0;
        }
        if (isPaddingRelative()) {
            setPaddingRelative(getPaddingStart(), getPaddingTop(), getPaddingEnd(), i);
        } else {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), i);
        }
        return windowInsets;
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIndicationBottomMargin = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_indication_margin_bottom);
        this.mBurnInYOffset = getResources().getDimensionPixelSize(C1777R.dimen.default_burn_in_prevention_offset);
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mIndicationArea.getLayoutParams();
        int i = marginLayoutParams.bottomMargin;
        int i2 = this.mIndicationBottomMargin;
        if (i != i2) {
            marginLayoutParams.bottomMargin = i2;
            this.mIndicationArea.setLayoutParams(marginLayoutParams);
        }
        this.mIndicationTextBottom.setTextSize(0, (float) getResources().getDimensionPixelSize(17105581));
        this.mIndicationText.setTextSize(0, (float) getResources().getDimensionPixelSize(17105581));
        ViewGroup.LayoutParams layoutParams = this.mRightAffordanceView.getLayoutParams();
        layoutParams.width = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_width);
        layoutParams.height = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_height);
        this.mRightAffordanceView.setLayoutParams(layoutParams);
        updateRightAffordanceIcon();
        ViewGroup.LayoutParams layoutParams2 = this.mLeftAffordanceView.getLayoutParams();
        layoutParams2.width = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_width);
        layoutParams2.height = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_height);
        this.mLeftAffordanceView.setLayoutParams(layoutParams2);
        updateLeftAffordanceIcon();
        ViewGroup.LayoutParams layoutParams3 = this.mWalletButton.getLayoutParams();
        layoutParams3.width = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_fixed_width);
        layoutParams3.height = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_fixed_height);
        this.mWalletButton.setLayoutParams(layoutParams3);
        ViewGroup.LayoutParams layoutParams4 = this.mQRCodeScannerButton.getLayoutParams();
        layoutParams4.width = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_fixed_width);
        layoutParams4.height = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_fixed_height);
        this.mQRCodeScannerButton.setLayoutParams(layoutParams4);
        ViewGroup.LayoutParams layoutParams5 = this.mControlsButton.getLayoutParams();
        layoutParams5.width = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_fixed_width);
        layoutParams5.height = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_affordance_fixed_height);
        this.mControlsButton.setLayoutParams(layoutParams5);
        this.mIndicationPadding = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_indication_area_padding);
        updateWalletVisibility();
        updateQRCodeButtonVisibility();
        updateAffordanceColors();
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mKeyguardStateController.removeCallback(this);
        AccessibilityController accessibilityController = this.mAccessibilityController;
        Objects.requireNonNull(accessibilityController);
        accessibilityController.mChangeCallbacks.remove(this);
        ExtensionControllerImpl.ExtensionImpl extensionImpl = this.mRightExtension;
        Objects.requireNonNull(extensionImpl);
        for (int i = 0; i < extensionImpl.mProducers.size(); i++) {
            extensionImpl.mProducers.get(i).destroy();
        }
        ExtensionControllerImpl.ExtensionImpl extensionImpl2 = this.mLeftExtension;
        Objects.requireNonNull(extensionImpl2);
        for (int i2 = 0; i2 < extensionImpl2.mProducers.size(); i2++) {
            extensionImpl2.mProducers.get(i2).destroy();
        }
        getContext().unregisterReceiver(this.mDevicePolicyReceiver);
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        QuickAccessWalletController quickAccessWalletController = this.mQuickAccessWalletController;
        if (quickAccessWalletController != null) {
            quickAccessWalletController.unregisterWalletChangeObservers(QuickAccessWalletController.WalletChangeEvent.WALLET_PREFERENCE_CHANGE, QuickAccessWalletController.WalletChangeEvent.DEFAULT_PAYMENT_APP_CHANGE);
        }
        QRCodeScannerController qRCodeScannerController = this.mQRCodeScannerController;
        if (qRCodeScannerController != null) {
            qRCodeScannerController.unregisterQRCodeScannerChangeObservers(0, 1);
        }
        ControlsComponent controlsComponent = this.mControlsComponent;
        if (controlsComponent != null) {
            controlsComponent.getControlsListingController().ifPresent(new ShellCommandHandlerImpl$$ExternalSyntheticLambda2(this, 2));
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        Context context = this.mContext;
        new LockPatternUtils(this.mContext);
        this.mPreviewInflater = new PreviewInflater(context, new ActivityIntentHelper(this.mContext));
        this.mOverlayContainer = (ViewGroup) findViewById(C1777R.C1779id.overlay_container);
        this.mRightAffordanceView = (KeyguardAffordanceView) findViewById(C1777R.C1779id.camera_button);
        this.mLeftAffordanceView = (KeyguardAffordanceView) findViewById(C1777R.C1779id.left_button);
        this.mWalletButton = (ImageView) findViewById(C1777R.C1779id.wallet_button);
        this.mQRCodeScannerButton = (ImageView) findViewById(C1777R.C1779id.qr_code_scanner_button);
        this.mControlsButton = (ImageView) findViewById(C1777R.C1779id.controls_button);
        this.mIndicationArea = (ViewGroup) findViewById(C1777R.C1779id.keyguard_indication_area);
        this.mAmbientIndicationArea = findViewById(C1777R.C1779id.ambient_indication_container);
        this.mIndicationText = (TextView) findViewById(C1777R.C1779id.keyguard_indication_text);
        this.mIndicationTextBottom = (TextView) findViewById(C1777R.C1779id.keyguard_indication_text_bottom);
        this.mIndicationBottomMargin = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_indication_margin_bottom);
        this.mBurnInYOffset = getResources().getDimensionPixelSize(C1777R.dimen.default_burn_in_prevention_offset);
        updateCameraVisibility();
        KeyguardStateController keyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);
        this.mKeyguardStateController = keyguardStateController;
        keyguardStateController.addCallback(this);
        setClipChildren(false);
        setClipToPadding(false);
        this.mRightAffordanceView.setOnClickListener(this);
        this.mLeftAffordanceView.setOnClickListener(this);
        this.mLeftAffordanceView.setAccessibilityDelegate(this.mAccessibilityDelegate);
        this.mRightAffordanceView.setAccessibilityDelegate(this.mAccessibilityDelegate);
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
        this.mFlashlightController = (FlashlightController) Dependency.get(FlashlightController.class);
        this.mAccessibilityController = (AccessibilityController) Dependency.get(AccessibilityController.class);
        this.mActivityIntentHelper = new ActivityIntentHelper(getContext());
        this.mIndicationPadding = getResources().getDimensionPixelSize(C1777R.dimen.keyguard_indication_area_padding);
        updateWalletVisibility();
        updateQRCodeButtonVisibility();
        updateControlsVisibility();
    }

    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && i == 0) {
            updateCameraVisibility();
        }
    }

    public final void updateLeftAffordance() {
        updateLeftAffordanceIcon();
        Class cls = AssistManager.class;
        ViewGroup viewGroup = this.mPreviewContainer;
        if (viewGroup != null) {
            KeyguardPreviewContainer keyguardPreviewContainer = this.mLeftPreview;
            if (keyguardPreviewContainer != null) {
                viewGroup.removeView(keyguardPreviewContainer);
            }
            if (this.mLeftIsVoiceAssist) {
                AssistManager assistManager = (AssistManager) Dependency.get(cls);
                Objects.requireNonNull(assistManager);
                if (assistManager.mAssistUtils.getActiveServiceComponentName() != null) {
                    PreviewInflater previewInflater = this.mPreviewInflater;
                    AssistManager assistManager2 = (AssistManager) Dependency.get(cls);
                    Objects.requireNonNull(assistManager2);
                    ComponentName activeServiceComponentName = assistManager2.mAssistUtils.getActiveServiceComponentName();
                    Objects.requireNonNull(previewInflater);
                    PreviewInflater.WidgetInfo widgetInfo = null;
                    try {
                        Bundle bundle = previewInflater.mContext.getPackageManager().getServiceInfo(activeServiceComponentName, 128).metaData;
                        String packageName = activeServiceComponentName.getPackageName();
                        if (bundle != null) {
                            int i = bundle.getInt("com.android.keyguard.layout");
                            if (i != 0) {
                                PreviewInflater.WidgetInfo widgetInfo2 = new PreviewInflater.WidgetInfo();
                                widgetInfo2.contextPackage = packageName;
                                widgetInfo2.layoutId = i;
                                widgetInfo = widgetInfo2;
                            }
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Failed to load preview; ");
                        m.append(activeServiceComponentName.flattenToShortString());
                        m.append(" not found");
                        Log.w("PreviewInflater", m.toString(), e);
                    }
                    this.mLeftPreview = previewInflater.inflatePreview(widgetInfo);
                }
            } else {
                this.mLeftPreview = this.mPreviewInflater.inflatePreview(this.mLeftButton.getIntent());
            }
            KeyguardPreviewContainer keyguardPreviewContainer2 = this.mLeftPreview;
            if (keyguardPreviewContainer2 != null) {
                this.mPreviewContainer.addView(keyguardPreviewContainer2);
                this.mLeftPreview.setVisibility(4);
            }
            KeyguardAffordanceHelper keyguardAffordanceHelper = this.mAffordanceHelper;
            if (keyguardAffordanceHelper != null) {
                keyguardAffordanceHelper.updatePreviews();
            }
        }
    }

    public KeyguardBottomAreaView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mHasCard = false;
        this.mCardRetriever = new WalletCardRetriever();
        this.mControlServicesAvailable = false;
        this.mPrewarmConnection = new ServiceConnection() {
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                KeyguardBottomAreaView.this.mPrewarmMessenger = new Messenger(iBinder);
            }

            public final void onServiceDisconnected(ComponentName componentName) {
                KeyguardBottomAreaView.this.mPrewarmMessenger = null;
            }
        };
        this.mRightButton = new DefaultRightButton();
        this.mLeftButton = new DefaultLeftButton();
        this.mListingCallback = new ControlsListingController.ControlsListingCallback() {
            public final void onServicesUpdated(ArrayList arrayList) {
                KeyguardBottomAreaView.this.post(new KeyguardPINView$$ExternalSyntheticLambda0(this, arrayList, 3));
            }
        };
        this.mAccessibilityDelegate = new View.AccessibilityDelegate() {
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i == 16) {
                    KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
                    if (view == keyguardBottomAreaView.mRightAffordanceView) {
                        keyguardBottomAreaView.launchCamera("lockscreen_affordance");
                        return true;
                    } else if (view == keyguardBottomAreaView.mLeftAffordanceView) {
                        keyguardBottomAreaView.launchLeftAffordance();
                        return true;
                    }
                }
                return super.performAccessibilityAction(view, i, bundle);
            }

            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                String str;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
                if (view == keyguardBottomAreaView.mRightAffordanceView) {
                    str = keyguardBottomAreaView.getResources().getString(C1777R.string.camera_label);
                } else if (view != keyguardBottomAreaView.mLeftAffordanceView) {
                    str = null;
                } else if (keyguardBottomAreaView.mLeftIsVoiceAssist) {
                    str = keyguardBottomAreaView.getResources().getString(C1777R.string.voice_assist_label);
                } else {
                    str = keyguardBottomAreaView.getResources().getString(C1777R.string.phone_label);
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(16, str));
            }
        };
        this.mDevicePolicyReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                KeyguardBottomAreaView.this.post(new Runnable() {
                    public final void run() {
                        KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
                        Intent intent = KeyguardBottomAreaView.PHONE_INTENT;
                        keyguardBottomAreaView.updateCameraVisibility();
                    }
                });
            }
        };
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
            public final void onUserSwitchComplete(int i) {
                KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
                Intent intent = KeyguardBottomAreaView.PHONE_INTENT;
                keyguardBottomAreaView.updateCameraVisibility();
            }

            public final void onUserUnlocked() {
                KeyguardBottomAreaView keyguardBottomAreaView = KeyguardBottomAreaView.this;
                Intent intent = KeyguardBottomAreaView.PHONE_INTENT;
                keyguardBottomAreaView.inflateCameraPreview();
                KeyguardBottomAreaView.this.updateCameraVisibility();
                KeyguardBottomAreaView.this.updateLeftAffordance();
            }
        };
        this.mShowLeftAffordance = getResources().getBoolean(C1777R.bool.config_keyguardShowLeftAffordance);
        this.mShowCameraAffordance = getResources().getBoolean(C1777R.bool.config_keyguardShowCameraAffordance);
    }

    public final void onUnlockedChanged() {
        updateCameraVisibility();
    }
}
