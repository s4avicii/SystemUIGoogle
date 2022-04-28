package com.android.systemui.wallet.p011ui;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Handler;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.service.quickaccesswallet.WalletServiceEvent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda4;
import com.android.settingslib.Utils;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.LifecycleActivity;
import java.util.Objects;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.wallet.ui.WalletActivity */
public class WalletActivity extends LifecycleActivity implements QuickAccessWalletClient.WalletServiceEventListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter mActivityStarter;
    public final Executor mExecutor;
    public FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final Handler mHandler;
    public boolean mHasRegisteredListener;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public C17441 mKeyguardUpdateMonitorCallback;
    public final StatusBarKeyguardViewManager mKeyguardViewManager;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;
    public QuickAccessWalletClient mWalletClient;
    public WalletScreenController mWalletScreenController;

    public final void onDestroy() {
        this.mKeyguardStateController.removeCallback(this.mWalletScreenController);
        C17441 r0 = this.mKeyguardUpdateMonitorCallback;
        if (r0 != null) {
            this.mKeyguardUpdateMonitor.removeCallback(r0);
        }
        WalletScreenController walletScreenController = this.mWalletScreenController;
        Objects.requireNonNull(walletScreenController);
        if (!walletScreenController.mIsDismissed) {
            walletScreenController.mIsDismissed = true;
            walletScreenController.mSelectedCardId = null;
            walletScreenController.mHandler.removeCallbacks(walletScreenController.mSelectionRunnable);
            walletScreenController.mWalletClient.notifyWalletDismissed();
            WalletView walletView = walletScreenController.mWalletView;
            Objects.requireNonNull(walletView);
            if (walletView.mCardCarouselContainer.getVisibility() == 0) {
                walletView.mCardCarousel.animate().translationX(walletView.mAnimationTranslationX).setInterpolator(walletView.mOutInterpolator).setDuration(200).start();
                walletView.mCardCarouselContainer.animate().alpha(0.0f).setDuration(100).setStartDelay(50).start();
            }
            walletScreenController.mContext = null;
        }
        this.mWalletClient.removeWalletServiceEventListener(this);
        this.mHasRegisteredListener = false;
        super.onDestroy();
    }

    public WalletActivity(KeyguardStateController keyguardStateController, KeyguardDismissUtil keyguardDismissUtil, ActivityStarter activityStarter, Executor executor, Handler handler, FalsingManager falsingManager, FalsingCollector falsingCollector, UserTracker userTracker, KeyguardUpdateMonitor keyguardUpdateMonitor, StatusBarKeyguardViewManager statusBarKeyguardViewManager, UiEventLogger uiEventLogger) {
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
        this.mActivityStarter = activityStarter;
        this.mExecutor = executor;
        this.mHandler = handler;
        this.mFalsingManager = falsingManager;
        this.mFalsingCollector = falsingCollector;
        this.mUserTracker = userTracker;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardViewManager = statusBarKeyguardViewManager;
        this.mUiEventLogger = uiEventLogger;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().addFlags(Integer.MIN_VALUE);
        requestWindowFeature(1);
        setContentView(C1777R.layout.quick_access_wallet);
        Toolbar toolbar = (Toolbar) findViewById(C1777R.C1779id.action_bar);
        if (toolbar != null) {
            setActionBar(toolbar);
        }
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar = getActionBar();
        Drawable drawable = getDrawable(C1777R.C1778drawable.ic_close);
        drawable.setTint(getColor(C1777R.color.material_dynamic_neutral70));
        actionBar.setHomeAsUpIndicator(drawable);
        getActionBar().setHomeActionContentDescription(C1777R.string.accessibility_desc_close);
        WalletView walletView = (WalletView) requireViewById(C1777R.C1779id.wallet_view);
        this.mWalletClient = QuickAccessWalletClient.create(this);
        this.mWalletScreenController = new WalletScreenController(this, walletView, this.mWalletClient, this.mActivityStarter, this.mExecutor, this.mHandler, this.mUserTracker, this.mFalsingManager, this.mKeyguardUpdateMonitor, this.mKeyguardStateController, this.mUiEventLogger);
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() {
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                Log.d("WalletActivity", "Biometric running state has changed.");
                WalletActivity.this.mWalletScreenController.queryWalletCards();
            }
        };
        walletView.mFalsingCollector = this.mFalsingCollector;
        walletView.mShowWalletAppOnClickListener = new WalletActivity$$ExternalSyntheticLambda0(this, 0);
        walletView.mDeviceLockedActionOnClickListener = new BubbleStackView$$ExternalSyntheticLambda4(this, 4);
    }

    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1777R.C1781menu.wallet_activity_options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == 16908332) {
            finish();
            return true;
        } else if (itemId != C1777R.C1779id.wallet_lockscreen_settings) {
            return super.onOptionsItemSelected(menuItem);
        } else {
            this.mActivityStarter.startActivity(new Intent("android.settings.LOCK_SCREEN_SETTINGS").addFlags(335544320), true);
            return true;
        }
    }

    public final void onPause() {
        super.onPause();
        this.mKeyguardViewManager.requestFp(false);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        KeyguardUpdateMonitor keyguardUpdateMonitor = statusBarKeyguardViewManager.mKeyguardUpdateManager;
        Objects.requireNonNull(keyguardUpdateMonitor);
        keyguardUpdateMonitor.mOccludingAppRequestingFace = false;
        keyguardUpdateMonitor.updateFaceListeningState(2);
    }

    public final void onResume() {
        super.onResume();
        this.mWalletScreenController.queryWalletCards();
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = this.mKeyguardViewManager;
        Utils.getColorAttrDefaultColor(this, 17956900);
        statusBarKeyguardViewManager.requestFp(true);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager2 = this.mKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager2);
        KeyguardUpdateMonitor keyguardUpdateMonitor = statusBarKeyguardViewManager2.mKeyguardUpdateManager;
        Objects.requireNonNull(keyguardUpdateMonitor);
        keyguardUpdateMonitor.mOccludingAppRequestingFace = true;
        keyguardUpdateMonitor.updateFaceListeningState(2);
    }

    public final void onStart() {
        super.onStart();
        if (!this.mHasRegisteredListener) {
            this.mWalletClient.addWalletServiceEventListener(this);
            this.mHasRegisteredListener = true;
        }
        this.mKeyguardStateController.addCallback(this.mWalletScreenController);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }

    public final void onStop() {
        super.onStop();
        finish();
    }

    public final void onWalletServiceEvent(WalletServiceEvent walletServiceEvent) {
        int eventType = walletServiceEvent.getEventType();
        if (eventType == 1) {
            return;
        }
        if (eventType != 2) {
            Log.w("WalletActivity", "onWalletServiceEvent: Unknown event type");
        } else {
            this.mWalletScreenController.queryWalletCards();
        }
    }
}
