package com.google.android.systemui.gamedashboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AutomaticZenRule;
import android.app.GameManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.util.ArraySet;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowInsets;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda21;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda4;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda5;
import com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda1;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda0;
import com.android.systemui.wallet.p011ui.WalletActivity$$ExternalSyntheticLambda0;
import com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger;
import java.util.List;
import java.util.Objects;

public final class GameMenuActivity extends Activity implements View.OnApplyWindowInsetsListener {
    public static final IntentFilter DND_FILTER = new IntentFilter("android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL");
    public final ActivityStarter mActivityStarter;
    public final ArraySet<Integer> mAvailableModes = new ArraySet<>();
    public View mBackNavigationButton;
    public RadioButton mBatteryModeRadioButton;
    public final Context mContext;
    public final EntryPointController mController;
    public View mCurrentView;
    public WidgetContainer mCurrentWidgetContainer;
    public GameDashboardButton mDndButton;
    public final GameModeDndController mDndController;
    public final C22793 mDndReceiver;
    public GameManager mGameManager;
    public View mGameMenuMainView;
    public View mGameModeView;
    public WidgetView mGameModeWidget;
    public boolean mGameModesSupported;
    public final LayoutInflater mLayoutInflater;
    public final Handler mMainHandler;
    public int mMaxWidgetsPerContainer;
    public RadioButton mPerformanceModeRadioButton;
    public PlayGamesWidget mPlayGamesWidget;
    public View mSettingsButton;
    public int mShortAnimationDuration;
    public final ShortcutBarController mShortcutBarController;
    public RadioButton mStandardModeRadioButton;
    public final GameDashboardUiEventLogger mUiEventLogger;
    public LinearLayout mWidgetsView;
    public YouTubeLiveWidget mYouTubeLiveWidget;

    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1) {
            finish();
        }
    }

    public final void navigateToGameModeView() {
        this.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_GAME_MODE_WIDGET);
        navigateToView(this.mGameModeView, C1777R.string.game_menu_game_mode_title);
        GameManager gameManager = this.mGameManager;
        EntryPointController entryPointController = this.mController;
        Objects.requireNonNull(entryPointController);
        toggleGameModeRadioButtons(gameManager.getGameMode(entryPointController.mGamePackageName));
    }

    public final void navigateToView(final View view, final int i) {
        if (view != null) {
            view.setAlpha(0.0f);
            view.setVisibility(0);
            view.animate().alpha(1.0f).setDuration((long) this.mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    if (GameMenuActivity.this.mCurrentView.getId() == C1777R.C1779id.game_menu_main) {
                        GameMenuActivity.this.mBackNavigationButton.setVisibility(0);
                        GameMenuActivity.this.mSettingsButton.setVisibility(4);
                    } else {
                        GameMenuActivity.this.mBackNavigationButton.setVisibility(4);
                        GameMenuActivity.this.mSettingsButton.setVisibility(0);
                    }
                    ((TextView) GameMenuActivity.this.findViewById(C1777R.C1779id.game_menu_title)).setText(i);
                    GameMenuActivity gameMenuActivity = GameMenuActivity.this;
                    View view = view;
                    gameMenuActivity.mCurrentView = view;
                    view.animate().setListener((Animator.AnimatorListener) null);
                }
            });
            final View view2 = this.mCurrentView;
            view2.animate().alpha(0.0f).setDuration((long) this.mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    view2.setVisibility(8);
                    view2.animate().setListener((Animator.AnimatorListener) null);
                }
            });
        }
    }

    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        DisplayCutout displayCutout;
        int i;
        int i2;
        if (windowInsets != null) {
            displayCutout = windowInsets.getDisplayCutout();
        } else {
            displayCutout = null;
        }
        if (displayCutout != null) {
            int safeInsetLeft = displayCutout.getSafeInsetLeft();
            int safeInsetRight = displayCutout.getSafeInsetRight();
            if (safeInsetLeft >= safeInsetRight) {
                i2 = safeInsetLeft - safeInsetRight;
                i = 0;
            } else {
                i = safeInsetRight - safeInsetLeft;
                i2 = 0;
            }
            view.setPadding(i, 0, i2, 0);
            windowInsets.consumeDisplayCutout();
        }
        return windowInsets;
    }

    public final void onBackPressed() {
        if (this.mCurrentView.getId() == C1777R.C1779id.game_menu_main) {
            super.onBackPressed();
            return;
        }
        navigateToView(this.mGameMenuMainView, C1777R.string.game_dashboard_game_menu_title);
        updateWidgets();
    }

    public final void toggleGameModeRadioButtons(int i) {
        boolean z;
        boolean z2;
        RadioButton radioButton = this.mPerformanceModeRadioButton;
        boolean z3 = false;
        if (i == 2) {
            z = true;
        } else {
            z = false;
        }
        radioButton.setChecked(z);
        RadioButton radioButton2 = this.mStandardModeRadioButton;
        if (i == 1) {
            z2 = true;
        } else {
            z2 = false;
        }
        radioButton2.setChecked(z2);
        RadioButton radioButton3 = this.mBatteryModeRadioButton;
        if (i == 3) {
            z3 = true;
        }
        radioButton3.setChecked(z3);
    }

    public final void updateWidgets() {
        boolean z;
        EntryPointController entryPointController = this.mController;
        Objects.requireNonNull(entryPointController);
        String str = entryPointController.mGamePackageName;
        int gameMode = this.mGameManager.getGameMode(str);
        if (!this.mGameModesSupported) {
            this.mGameModeWidget.setEnabled(false);
            this.mGameModeWidget.update(getDrawable(C1777R.C1778drawable.ic_game_mode), C1777R.string.game_menu_game_mode_title, C1777R.string.game_mode_unsupported_description, (View.OnClickListener) null);
        } else if (gameMode == 2) {
            this.mGameModeWidget.setEnabled(true);
            this.mGameModeWidget.update(getDrawable(C1777R.C1778drawable.ic_game_mode_performance_widget), C1777R.string.game_mode_performance_title, C1777R.string.game_mode_performance_description, new WalletActivity$$ExternalSyntheticLambda0(this, 1));
        } else if (gameMode == 3) {
            this.mGameModeWidget.setEnabled(true);
            this.mGameModeWidget.update(getDrawable(C1777R.C1778drawable.ic_game_mode_battery_widget), C1777R.string.game_mode_battery_title, C1777R.string.game_mode_battery_description, new BubbleStackView$$ExternalSyntheticLambda4(this, 5));
        } else {
            this.mGameModeWidget.update(getDrawable(C1777R.C1778drawable.ic_game_mode), C1777R.string.game_menu_game_mode_title, C1777R.string.game_menu_game_mode_description, new BubbleStackView$$ExternalSyntheticLambda5(this, 2));
        }
        YouTubeLiveWidget youTubeLiveWidget = this.mYouTubeLiveWidget;
        Objects.requireNonNull(youTubeLiveWidget);
        List<ResolveInfo> queryIntentActivities = youTubeLiveWidget.mContext.getPackageManager().queryIntentActivities(new Intent("com.google.android.youtube.intent.action.CREATE_LIVE_STREAM").setPackage("com.google.android.youtube"), 65536);
        if (queryIntentActivities == null || queryIntentActivities.isEmpty()) {
            z = false;
        } else {
            z = true;
        }
        if (!z) {
            youTubeLiveWidget.mWidgetView.update(youTubeLiveWidget.mContext.getDrawable(C1777R.C1778drawable.ic_youtube_live_logo), C1777R.string.game_menu_youtube_live_stream_widget_title, C1777R.string.game_menu_youtube_live_stream_widget_description, (View.OnClickListener) null);
            youTubeLiveWidget.mWidgetView.setEnabled(false);
        } else {
            youTubeLiveWidget.mWidgetView.update(youTubeLiveWidget.mContext.getDrawable(C1777R.C1778drawable.ic_youtube_live_logo), C1777R.string.game_menu_youtube_live_stream_widget_title, C1777R.string.game_menu_youtube_live_stream_widget_description, new YouTubeLiveWidget$$ExternalSyntheticLambda0(youTubeLiveWidget, str));
        }
        PlayGamesWidget playGamesWidget = this.mPlayGamesWidget;
        if (playGamesWidget != null) {
            Objects.requireNonNull(playGamesWidget);
            playGamesWidget.mWidgetView.setLoading(true);
            playGamesWidget.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_PLAY_GAMES_IMPRESSION);
            playGamesWidget.mMainHandler.post(new BubbleStackView$$ExternalSyntheticLambda21(playGamesWidget, str, 6));
        }
    }

    public GameMenuActivity(Context context, EntryPointController entryPointController, ActivityStarter activityStarter, ShortcutBarController shortcutBarController, GameModeDndController gameModeDndController, LayoutInflater layoutInflater, Handler handler, GameDashboardUiEventLogger gameDashboardUiEventLogger) {
        this.mContext = context;
        this.mController = entryPointController;
        this.mShortcutBarController = shortcutBarController;
        this.mActivityStarter = activityStarter;
        this.mDndController = gameModeDndController;
        this.mLayoutInflater = layoutInflater;
        this.mDndReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                boolean z;
                GameDashboardUiEventLogger.GameDashboardEvent gameDashboardEvent;
                if (GameMenuActivity.this.mDndController != null && "android.app.action.INTERRUPTION_FILTER_CHANGED_INTERNAL".equals(intent.getAction())) {
                    GameModeDndController gameModeDndController = GameMenuActivity.this.mDndController;
                    Objects.requireNonNull(gameModeDndController);
                    AutomaticZenRule fetchRule = gameModeDndController.fetchRule();
                    if (fetchRule == null || fetchRule.getInterruptionFilter() != 2) {
                        z = false;
                    } else {
                        z = true;
                    }
                    gameModeDndController.mFilterActive = z;
                    gameModeDndController.mGameActiveOld = gameModeDndController.mGameActive;
                    gameModeDndController.mUserActiveOld = gameModeDndController.mUserActive;
                    gameModeDndController.mFilterActiveOld = z;
                    GameDashboardButton gameDashboardButton = GameMenuActivity.this.mDndButton;
                    Objects.requireNonNull(gameDashboardButton);
                    if (gameDashboardButton.mToggled != GameMenuActivity.this.mDndController.isGameModeDndOn()) {
                        GameMenuActivity gameMenuActivity = GameMenuActivity.this;
                        gameMenuActivity.mDndButton.setToggled(gameMenuActivity.mDndController.isGameModeDndOn(), true);
                    }
                    GameMenuActivity gameMenuActivity2 = GameMenuActivity.this;
                    GameDashboardUiEventLogger gameDashboardUiEventLogger = gameMenuActivity2.mUiEventLogger;
                    if (gameMenuActivity2.mDndController.isGameModeDndOn()) {
                        gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_ENABLE_DND;
                    } else {
                        gameDashboardEvent = GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_MENU_DISABLE_DND;
                    }
                    gameDashboardUiEventLogger.log(gameDashboardEvent);
                }
            }
        };
        this.mMainHandler = handler;
        this.mUiEventLogger = gameDashboardUiEventLogger;
        Objects.requireNonNull(gameDashboardUiEventLogger);
        gameDashboardUiEventLogger.mEntryPointController = entryPointController;
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x02a7  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x02a9  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x02bd  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x02dc A[LOOP:2: B:44:0x02d1->B:46:0x02dc, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x02f1 A[EDGE_INSN: B:54:0x02f1->B:47:0x02f1 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onCreate(android.os.Bundle r14) {
        /*
            r13 = this;
            super.onCreate(r14)
            r14 = 2131624106(0x7f0e00aa, float:1.8875382E38)
            r13.setContentView(r14)
            android.view.Window r14 = r13.getWindow()
            android.view.View r14 = r14.getDecorView()
            android.view.WindowInsetsController r0 = r14.getWindowInsetsController()
            int r1 = android.view.WindowInsets.Type.navigationBars()
            r0.hide(r1)
            r14.setOnApplyWindowInsetsListener(r13)
            java.lang.Class<android.app.GameManager> r14 = android.app.GameManager.class
            java.lang.Object r14 = r13.getSystemService(r14)
            android.app.GameManager r14 = (android.app.GameManager) r14
            r13.mGameManager = r14
            com.google.android.systemui.gamedashboard.EntryPointController r0 = r13.mController
            java.util.Objects.requireNonNull(r0)
            java.lang.String r0 = r0.mGamePackageName
            int[] r14 = r14.getAvailableGameModes(r0)
            int r0 = r14.length
            r1 = 0
            r2 = r1
        L_0x0037:
            if (r2 >= r0) goto L_0x0047
            r3 = r14[r2]
            android.util.ArraySet<java.lang.Integer> r4 = r13.mAvailableModes
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r4.add(r3)
            int r2 = r2 + 1
            goto L_0x0037
        L_0x0047:
            android.util.ArraySet<java.lang.Integer> r14 = r13.mAvailableModes
            int r14 = r14.size()
            r0 = 1
            if (r14 <= 0) goto L_0x005b
            android.util.ArraySet<java.lang.Integer> r14 = r13.mAvailableModes
            java.lang.Integer r2 = java.lang.Integer.valueOf(r0)
            r14.add(r2)
            r13.mGameModesSupported = r0
        L_0x005b:
            android.content.res.Resources r14 = r13.getResources()
            r2 = 17694720(0x10e0000, float:2.608128E-38)
            int r14 = r14.getInteger(r2)
            r13.mShortAnimationDuration = r14
            r14 = 2131428002(0x7f0b02a2, float:1.8477636E38)
            android.view.View r14 = r13.findViewById(r14)
            r13.mBackNavigationButton = r14
            com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda10 r2 = new com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda10
            r3 = 2
            r2.<init>(r13, r3)
            r14.setOnClickListener(r2)
            android.view.View r14 = r13.mBackNavigationButton
            r2 = 2131165733(0x7f070225, float:1.7945691E38)
            android.view.ViewParent r4 = r14.getParent()
            android.view.View r4 = (android.view.View) r4
            com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda5 r5 = new com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda5
            r5.<init>(r13, r2, r14, r4)
            r4.post(r5)
            r14 = 2131428009(0x7f0b02a9, float:1.847765E38)
            android.view.View r14 = r13.findViewById(r14)
            r13.mSettingsButton = r14
            com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda2 r4 = new com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda2
            r4.<init>(r13, r3)
            r14.setOnClickListener(r4)
            android.view.View r14 = r13.mSettingsButton
            android.view.ViewParent r4 = r14.getParent()
            android.view.View r4 = (android.view.View) r4
            com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda5 r5 = new com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda5
            r5.<init>(r13, r2, r14, r4)
            r4.post(r5)
            r14 = 2131428003(0x7f0b02a3, float:1.8477638E38)
            android.view.View r14 = r13.findViewById(r14)
            com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda3 r2 = new com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda3
            r4 = 3
            r2.<init>(r13, r4)
            r14.setOnClickListener(r2)
            r2 = 2131165742(0x7f07022e, float:1.794571E38)
            android.view.ViewParent r5 = r14.getParent()
            android.view.View r5 = (android.view.View) r5
            com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda5 r6 = new com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda5
            r6.<init>(r13, r2, r14, r5)
            r5.post(r6)
            r14 = 2131428006(0x7f0b02a6, float:1.8477644E38)
            android.view.View r14 = r13.findViewById(r14)
            r13.mGameMenuMainView = r14
            r14 = 2131428008(0x7f0b02a8, float:1.8477648E38)
            android.view.View r14 = r13.findViewById(r14)
            com.google.android.systemui.gamedashboard.GameDashboardButton r14 = (com.google.android.systemui.gamedashboard.GameDashboardButton) r14
            com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda5 r2 = new com.android.systemui.media.MediaControlPanel$$ExternalSyntheticLambda5
            r2.<init>(r13, r14, r0)
            r14.setOnClickListener(r2)
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r13.mShortcutBarController
            java.util.Objects.requireNonNull(r2)
            com.google.android.systemui.gamedashboard.ShortcutBarView r2 = r2.mView
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsScreenshotVisible
            r14.setToggled(r2, r1)
            r14 = 2131428007(0x7f0b02a7, float:1.8477646E38)
            android.view.View r14 = r13.findViewById(r14)
            com.google.android.systemui.gamedashboard.GameDashboardButton r14 = (com.google.android.systemui.gamedashboard.GameDashboardButton) r14
            com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda2 r2 = new com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda2
            r2.<init>(r13, r14, r1)
            r14.setOnClickListener(r2)
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r13.mShortcutBarController
            java.util.Objects.requireNonNull(r2)
            com.google.android.systemui.gamedashboard.ShortcutBarView r2 = r2.mView
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsRecordVisible
            r14.setToggled(r2, r1)
            r14 = 2131428005(0x7f0b02a5, float:1.8477642E38)
            android.view.View r14 = r13.findViewById(r14)
            com.google.android.systemui.gamedashboard.GameDashboardButton r14 = (com.google.android.systemui.gamedashboard.GameDashboardButton) r14
            com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda4 r2 = new com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda4
            r2.<init>(r13, r14)
            r14.setOnClickListener(r2)
            com.google.android.systemui.gamedashboard.ShortcutBarController r2 = r13.mShortcutBarController
            java.util.Objects.requireNonNull(r2)
            com.google.android.systemui.gamedashboard.ShortcutBarView r2 = r2.mView
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsFpsVisible
            r14.setToggled(r2, r1)
            android.content.Context r14 = r13.mContext
            com.google.android.systemui.gamedashboard.GameMenuActivity$3 r2 = r13.mDndReceiver
            android.content.IntentFilter r5 = DND_FILTER
            r14.registerReceiver(r2, r5)
            r14 = 2131428016(0x7f0b02b0, float:1.8477665E38)
            android.view.View r14 = r13.findViewById(r14)
            r13.mGameModeView = r14
            r14 = 2131428581(0x7f0b04e5, float:1.847881E38)
            android.view.View r14 = r13.findViewById(r14)
            android.widget.RadioButton r14 = (android.widget.RadioButton) r14
            r13.mPerformanceModeRadioButton = r14
            android.util.ArraySet<java.lang.Integer> r14 = r13.mAvailableModes
            java.lang.Integer r2 = java.lang.Integer.valueOf(r3)
            boolean r14 = r14.contains(r2)
            r2 = 4
            if (r14 == 0) goto L_0x0180
            android.widget.RadioButton r14 = r13.mPerformanceModeRadioButton
            com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda1 r5 = new com.android.settingslib.users.AvatarPickerActivity$$ExternalSyntheticLambda1
            r5.<init>(r13, r4)
            r14.setOnClickListener(r5)
            android.widget.RadioButton r14 = r13.mPerformanceModeRadioButton
            r14.setEnabled(r0)
            r14 = 2131428580(0x7f0b04e4, float:1.8478808E38)
            android.view.View r14 = r13.findViewById(r14)
            com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3 r5 = new com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda3
            r5.<init>(r13, r2)
            r14.setOnClickListener(r5)
            goto L_0x0185
        L_0x0180:
            android.widget.RadioButton r14 = r13.mPerformanceModeRadioButton
            r14.setEnabled(r1)
        L_0x0185:
            r14 = 2131428914(0x7f0b0632, float:1.8479486E38)
            android.view.View r14 = r13.findViewById(r14)
            android.widget.RadioButton r14 = (android.widget.RadioButton) r14
            r13.mStandardModeRadioButton = r14
            com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1 r5 = new com.android.wm.shell.pip.phone.PipMenuView$$ExternalSyntheticLambda1
            r5.<init>(r13, r2)
            r14.setOnClickListener(r5)
            r14 = 2131427574(0x7f0b00f6, float:1.8476768E38)
            android.view.View r5 = r13.findViewById(r14)
            android.widget.RadioButton r5 = (android.widget.RadioButton) r5
            r13.mBatteryModeRadioButton = r5
            android.view.View r14 = r13.findViewById(r14)
            android.widget.RadioButton r14 = (android.widget.RadioButton) r14
            r13.mBatteryModeRadioButton = r14
            android.util.ArraySet<java.lang.Integer> r14 = r13.mAvailableModes
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            boolean r14 = r14.contains(r5)
            if (r14 == 0) goto L_0x01d6
            android.widget.RadioButton r14 = r13.mBatteryModeRadioButton
            com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda1 r5 = new com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda1
            r5.<init>(r13, r1)
            r14.setOnClickListener(r5)
            r14 = 2131427573(0x7f0b00f5, float:1.8476766E38)
            android.view.View r14 = r13.findViewById(r14)
            com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda3 r5 = new com.google.android.systemui.gamedashboard.GameMenuActivity$$ExternalSyntheticLambda3
            r5.<init>(r13)
            r14.setOnClickListener(r5)
            android.widget.RadioButton r14 = r13.mBatteryModeRadioButton
            r14.setEnabled(r0)
            goto L_0x01db
        L_0x01d6:
            android.widget.RadioButton r14 = r13.mBatteryModeRadioButton
            r14.setEnabled(r1)
        L_0x01db:
            r14 = 2131428004(0x7f0b02a4, float:1.847764E38)
            android.view.View r14 = r13.findViewById(r14)
            com.google.android.systemui.gamedashboard.GameDashboardButton r14 = (com.google.android.systemui.gamedashboard.GameDashboardButton) r14
            r13.mDndButton = r14
            r14 = 2131428913(0x7f0b0631, float:1.8479484E38)
            android.view.View r14 = r13.findViewById(r14)
            com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda9 r5 = new com.android.systemui.screenshot.ScreenshotView$$ExternalSyntheticLambda9
            r5.<init>(r13, r3)
            r14.setOnClickListener(r5)
            android.view.View r14 = r13.mGameMenuMainView
            r13.mCurrentView = r14
            r14 = 2131428014(0x7f0b02ae, float:1.847766E38)
            android.view.View r14 = r13.findViewById(r14)
            android.widget.LinearLayout r14 = (android.widget.LinearLayout) r14
            r13.mWidgetsView = r14
            android.content.Context r14 = r13.mContext
            android.content.res.Resources r14 = r14.getResources()
            android.content.res.Configuration r14 = r14.getConfiguration()
            int r14 = r14.orientation
            if (r14 == r3) goto L_0x0215
            r13.mMaxWidgetsPerContainer = r3
            goto L_0x0217
        L_0x0215:
            r13.mMaxWidgetsPerContainer = r4
        L_0x0217:
            android.view.LayoutInflater r14 = r13.mLayoutInflater
            android.widget.LinearLayout r4 = r13.mWidgetsView
            r5 = 2131624112(0x7f0e00b0, float:1.8875395E38)
            android.view.View r14 = r14.inflate(r5, r4, r1)
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = (com.google.android.systemui.gamedashboard.WidgetContainer) r14
            r13.mCurrentWidgetContainer = r14
            android.view.LayoutInflater r4 = r13.mLayoutInflater
            r6 = 2131624111(0x7f0e00af, float:1.8875392E38)
            android.view.View r14 = r4.inflate(r6, r14, r1)
            com.google.android.systemui.gamedashboard.WidgetView r14 = (com.google.android.systemui.gamedashboard.WidgetView) r14
            r13.mGameModeWidget = r14
            com.google.android.systemui.gamedashboard.WidgetContainer r4 = r13.mCurrentWidgetContainer
            r4.addWidget(r14)
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = r13.mCurrentWidgetContainer
            com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger r4 = r13.mUiEventLogger
            android.view.LayoutInflater r7 = android.view.LayoutInflater.from(r13)
            android.view.View r14 = r7.inflate(r6, r14, r1)
            com.google.android.systemui.gamedashboard.WidgetView r14 = (com.google.android.systemui.gamedashboard.WidgetView) r14
            com.google.android.systemui.gamedashboard.YouTubeLiveWidget r7 = new com.google.android.systemui.gamedashboard.YouTubeLiveWidget
            r7.<init>(r13, r14, r4)
            r13.mYouTubeLiveWidget = r7
            com.google.android.systemui.gamedashboard.WidgetContainer r4 = r13.mCurrentWidgetContainer
            r4.addWidget(r14)
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = r13.mCurrentWidgetContainer
            java.util.Objects.requireNonNull(r14)
            int r14 = r14.mWidgetCount
            int r4 = r13.mMaxWidgetsPerContainer
            if (r14 != r4) goto L_0x0270
            android.widget.LinearLayout r14 = r13.mWidgetsView
            com.google.android.systemui.gamedashboard.WidgetContainer r4 = r13.mCurrentWidgetContainer
            r14.addView(r4)
            android.view.LayoutInflater r14 = r13.mLayoutInflater
            android.widget.LinearLayout r4 = r13.mWidgetsView
            android.view.View r14 = r14.inflate(r5, r4, r1)
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = (com.google.android.systemui.gamedashboard.WidgetContainer) r14
            r13.mCurrentWidgetContainer = r14
        L_0x0270:
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = r13.mCurrentWidgetContainer
            android.os.Handler r4 = r13.mMainHandler
            com.google.android.systemui.gamedashboard.GameDashboardUiEventLogger r5 = r13.mUiEventLogger
            android.content.pm.PackageManager r7 = r13.getPackageManager()     // Catch:{ NameNotFoundException -> 0x029d }
            java.lang.String r8 = "com.google.android.play.games"
            r9 = 8
            android.content.pm.PackageInfo r7 = r7.getPackageInfo(r8, r9)     // Catch:{ NameNotFoundException -> 0x029d }
            android.content.pm.ProviderInfo[] r7 = r7.providers     // Catch:{ NameNotFoundException -> 0x029d }
            int r8 = r7.length     // Catch:{ NameNotFoundException -> 0x029d }
            r9 = r1
        L_0x0286:
            if (r9 >= r8) goto L_0x02a4
            r10 = r7[r9]     // Catch:{ NameNotFoundException -> 0x029d }
            java.lang.String r11 = r10.authority     // Catch:{ NameNotFoundException -> 0x029d }
            java.lang.String r12 = "com.google.android.play.games.dashboard.tile.provider"
            boolean r11 = r11.equals(r12)     // Catch:{ NameNotFoundException -> 0x029d }
            if (r11 == 0) goto L_0x029a
            boolean r10 = r10.enabled     // Catch:{ NameNotFoundException -> 0x029d }
            if (r10 == 0) goto L_0x029a
            r7 = r0
            goto L_0x02a5
        L_0x029a:
            int r9 = r9 + 1
            goto L_0x0286
        L_0x029d:
            java.lang.String r7 = "PlayGamesWidget"
            java.lang.String r8 = "Play Games package not found."
            android.util.Log.v(r7, r8)
        L_0x02a4:
            r7 = r1
        L_0x02a5:
            if (r7 != 0) goto L_0x02a9
            r14 = 0
            goto L_0x02b9
        L_0x02a9:
            android.view.LayoutInflater r7 = android.view.LayoutInflater.from(r13)
            android.view.View r14 = r7.inflate(r6, r14, r1)
            com.google.android.systemui.gamedashboard.WidgetView r14 = (com.google.android.systemui.gamedashboard.WidgetView) r14
            com.google.android.systemui.gamedashboard.PlayGamesWidget r7 = new com.google.android.systemui.gamedashboard.PlayGamesWidget
            r7.<init>(r13, r14, r4, r5)
            r14 = r7
        L_0x02b9:
            r13.mPlayGamesWidget = r14
            if (r14 == 0) goto L_0x02c4
            com.google.android.systemui.gamedashboard.WidgetContainer r4 = r13.mCurrentWidgetContainer
            com.google.android.systemui.gamedashboard.WidgetView r14 = r14.mWidgetView
            r4.addWidget(r14)
        L_0x02c4:
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = r13.mCurrentWidgetContainer
            java.util.Objects.requireNonNull(r14)
            int r14 = r14.mWidgetCount
            if (r14 != r0) goto L_0x02f1
            int r14 = r13.mMaxWidgetsPerContainer
            if (r14 != r3) goto L_0x02f1
        L_0x02d1:
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = r13.mCurrentWidgetContainer
            java.util.Objects.requireNonNull(r14)
            int r14 = r14.mWidgetCount
            int r0 = r13.mMaxWidgetsPerContainer
            if (r14 >= r0) goto L_0x02f1
            com.google.android.systemui.gamedashboard.WidgetContainer r14 = r13.mCurrentWidgetContainer
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r13)
            android.view.View r14 = r0.inflate(r6, r14, r1)
            com.google.android.systemui.gamedashboard.WidgetView r14 = (com.google.android.systemui.gamedashboard.WidgetView) r14
            r14.setVisibility(r2)
            com.google.android.systemui.gamedashboard.WidgetContainer r0 = r13.mCurrentWidgetContainer
            r0.addWidget(r14)
            goto L_0x02d1
        L_0x02f1:
            android.widget.LinearLayout r14 = r13.mWidgetsView
            com.google.android.systemui.gamedashboard.WidgetContainer r13 = r13.mCurrentWidgetContainer
            r14.addView(r13)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.gamedashboard.GameMenuActivity.onCreate(android.os.Bundle):void");
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mContext.unregisterReceiver(this.mDndReceiver);
    }

    public final void onGameModeSelectionChanged(View view) {
        int i;
        int id = view.getId();
        if (id == C1777R.C1779id.performance_mode || id == C1777R.C1779id.performance_mode_radio_button) {
            this.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_GAME_MODE_PERFORMANCE);
            i = 2;
        } else if (id == C1777R.C1779id.standard_mode || id == C1777R.C1779id.standard_mode_radio_button) {
            this.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_GAME_MODE_STANDARD);
            i = 1;
        } else if (id == C1777R.C1779id.battery_mode || id == C1777R.C1779id.battery_mode_radio_button) {
            this.mUiEventLogger.log(GameDashboardUiEventLogger.GameDashboardEvent.GAME_DASHBOARD_GAME_MODE_BATTERY);
            i = 3;
        } else {
            i = 0;
        }
        EntryPointController entryPointController = this.mController;
        Objects.requireNonNull(entryPointController);
        String str = entryPointController.mGamePackageName;
        int gameMode = this.mGameManager.getGameMode(str);
        if (gameMode != i) {
            toggleGameModeRadioButtons(gameMode);
            SystemUIDialog systemUIDialog = new SystemUIDialog(this);
            systemUIDialog.setTitle(C1777R.string.game_mode_restart_dialog_title);
            if (i == 1) {
                systemUIDialog.setMessage(C1777R.string.game_mode_restart_dialog_message_standard);
            } else if (i == 2) {
                systemUIDialog.setMessage(C1777R.string.game_mode_restart_dialog_message_performance);
            } else if (i == 3) {
                systemUIDialog.setMessage(C1777R.string.game_mode_restart_dialog_message_battery);
            }
            systemUIDialog.setPositiveButton(C1777R.string.game_mode_restart_dialog_confirm, new GameMenuActivity$$ExternalSyntheticLambda0(this, i, str));
            systemUIDialog.setNegativeButton(C1777R.string.game_mode_restart_dialog_cancel, new InputMethodPreference$$ExternalSyntheticLambda1(this, 1));
            systemUIDialog.show();
        }
    }

    public final void onResume() {
        boolean z;
        super.onResume();
        GameModeDndController gameModeDndController = this.mDndController;
        Objects.requireNonNull(gameModeDndController);
        AutomaticZenRule fetchRule = gameModeDndController.fetchRule();
        if (fetchRule == null || fetchRule.getInterruptionFilter() != 2) {
            z = false;
        } else {
            z = true;
        }
        gameModeDndController.mFilterActive = z;
        gameModeDndController.mGameActiveOld = gameModeDndController.mGameActive;
        gameModeDndController.mUserActiveOld = gameModeDndController.mUserActive;
        gameModeDndController.mFilterActiveOld = z;
        this.mDndButton.setToggled(this.mDndController.isGameModeDndOn(), false);
        this.mDndButton.setOnClickListener(new RemoteInputView$$ExternalSyntheticLambda0(this, 2));
        updateWidgets();
    }
}
