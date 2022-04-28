package com.google.android.systemui.gamedashboard;

import android.app.AutomaticZenRule;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.service.notification.Condition;
import android.service.notification.ZenPolicy;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.CurrentUserTracker;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.Map;
import java.util.Objects;

public final class GameModeDndController {
    public static final ComponentName COMPONENT_NAME = new ComponentName(ThemeOverlayApplier.SYSUI_PACKAGE, GameDndConfigActivity.class.getName());
    public static final Uri CONDITION_ID = new Uri.Builder().scheme("android-app").authority(ThemeOverlayApplier.SYSUI_PACKAGE).appendPath("game-mode-dnd-controller").build();
    public final Context mContext;
    public boolean mFilterActive;
    public boolean mFilterActiveOld;
    public boolean mGameActive;
    public boolean mGameActiveOld;
    public final C22812 mIntentReceiver;
    public final NotificationManager mNotificationManager;
    public String mRuleId = getOrCreateRuleId();
    public String mRuleName;
    public boolean mUserActive;
    public boolean mUserActiveOld;
    public final C22801 mUserTracker;

    public final AutomaticZenRule fetchRule() {
        String str = this.mRuleId;
        if (str == null) {
            return null;
        }
        AutomaticZenRule automaticZenRule = this.mNotificationManager.getAutomaticZenRule(str);
        if (automaticZenRule != null) {
            return automaticZenRule;
        }
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Fetched new rule id outside of user switch handler: ");
        m.append(this.mRuleId);
        Log.v("GameModeDndController", m.toString());
        String orCreateRuleId = getOrCreateRuleId();
        this.mRuleId = orCreateRuleId;
        return this.mNotificationManager.getAutomaticZenRule(orCreateRuleId);
    }

    public final String getOrCreateRuleId() {
        for (Map.Entry next : this.mNotificationManager.getAutomaticZenRules().entrySet()) {
            if (((AutomaticZenRule) next.getValue()).getConditionId().equals(CONDITION_ID)) {
                return (String) next.getKey();
            }
        }
        try {
            return this.mNotificationManager.addAutomaticZenRule(new AutomaticZenRule(this.mRuleName, (ComponentName) null, COMPONENT_NAME, CONDITION_ID, (ZenPolicy) null, 1, true));
        } catch (IllegalArgumentException unused) {
            Log.w("GameModeDndController", "Failed to create zen rule due to missing configuration Activity.");
            return null;
        }
    }

    public final boolean isGameModeDndOn() {
        if (!this.mGameActive || !this.mFilterActive || !this.mUserActive) {
            return false;
        }
        return true;
    }

    public final void updateRule() {
        boolean z;
        boolean z2;
        int i;
        try {
            AutomaticZenRule fetchRule = fetchRule();
            if (fetchRule != null) {
                boolean z3 = this.mFilterActive;
                boolean z4 = false;
                if (z3 != this.mFilterActiveOld) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    if (z3) {
                        i = 2;
                    } else {
                        i = 1;
                    }
                    fetchRule.setInterruptionFilter(i);
                    this.mNotificationManager.updateAutomaticZenRule(this.mRuleId, fetchRule);
                    Log.v("GameModeDndController", "Updated filter: " + this.mFilterActive);
                }
                if (!fetchRule.getName().equals(this.mRuleName)) {
                    fetchRule.setName(this.mRuleName);
                    this.mNotificationManager.updateAutomaticZenRule(this.mRuleId, fetchRule);
                }
                ComponentName configurationActivity = fetchRule.getConfigurationActivity();
                ComponentName componentName = COMPONENT_NAME;
                if (!configurationActivity.equals(componentName)) {
                    fetchRule.setConfigurationActivity(componentName);
                    this.mNotificationManager.updateAutomaticZenRule(this.mRuleId, fetchRule);
                }
                if (!this.mUserActiveOld || !this.mGameActiveOld || !this.mFilterActiveOld) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                if (z2 != isGameModeDndOn()) {
                    z4 = true;
                }
                if (z4) {
                    Condition condition = new Condition(fetchRule.getConditionId(), "", isGameModeDndOn() ? 1 : 0);
                    Log.v("GameModeDndController", "Updated condition: " + Condition.stateToString(condition.state));
                    this.mNotificationManager.setAutomaticZenRuleState(this.mRuleId, condition);
                }
                this.mGameActiveOld = this.mGameActive;
                this.mUserActiveOld = this.mUserActive;
                this.mFilterActiveOld = this.mFilterActive;
            }
        } catch (Exception e) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Failed to update Game Mode DND rule: ");
            m.append(this.mRuleId);
            Log.e("GameModeDndController", m.toString(), e);
        }
    }

    public GameModeDndController(Context context, NotificationManager notificationManager, BroadcastDispatcher broadcastDispatcher) {
        boolean z;
        this.mContext = context;
        this.mNotificationManager = notificationManager;
        this.mRuleName = context.getString(C1777R.string.game_mode_dnd_rule_name);
        AutomaticZenRule fetchRule = fetchRule();
        if (fetchRule == null || fetchRule.getInterruptionFilter() != 2) {
            z = false;
        } else {
            z = true;
        }
        this.mFilterActive = z;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        C22812 r0 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                Objects.requireNonNull(action);
                char c = 65535;
                switch (action.hashCode()) {
                    case -2128145023:
                        if (action.equals("android.intent.action.SCREEN_OFF")) {
                            c = 0;
                            break;
                        }
                        break;
                    case -19011148:
                        if (action.equals("android.intent.action.LOCALE_CHANGED")) {
                            c = 1;
                            break;
                        }
                        break;
                    case 823795052:
                        if (action.equals("android.intent.action.USER_PRESENT")) {
                            c = 2;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        GameModeDndController.this.mUserActive = false;
                        break;
                    case 1:
                        GameModeDndController gameModeDndController = GameModeDndController.this;
                        gameModeDndController.mRuleName = gameModeDndController.mContext.getString(C1777R.string.game_mode_dnd_rule_name);
                        break;
                    case 2:
                        GameModeDndController.this.mUserActive = true;
                        break;
                }
                GameModeDndController.this.updateRule();
            }
        };
        this.mIntentReceiver = r0;
        context.registerReceiver(r0, intentFilter);
        C22801 r2 = new CurrentUserTracker(broadcastDispatcher) {
            public final void onUserSwitched(int i) {
                GameModeDndController gameModeDndController = GameModeDndController.this;
                Uri uri = GameModeDndController.CONDITION_ID;
                gameModeDndController.mRuleId = gameModeDndController.getOrCreateRuleId();
            }
        };
        this.mUserTracker = r2;
        r2.startTracking();
        updateRule();
    }
}
