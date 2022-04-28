package com.android.systemui.assist;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.FrameworkStatsLog;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.FalsingManager;
import java.util.Set;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: AssistLogger.kt */
public class AssistLogger {
    public static final Set<AssistantSessionEvent> SESSION_END_EVENTS = SetsKt__SetsKt.setOf(AssistantSessionEvent.ASSISTANT_SESSION_INVOCATION_CANCELLED, AssistantSessionEvent.ASSISTANT_SESSION_CLOSE);
    public final AssistUtils assistUtils;
    public final Context context;
    public InstanceId currentInstanceId;
    public final InstanceIdSequence instanceIdSequence = new InstanceIdSequence(1048576);
    public final PhoneStateMonitor phoneStateMonitor;
    public final UiEventLogger uiEventLogger;

    public void reportAssistantInvocationExtraData() {
    }

    public final void reportAssistantInvocationEventFromLegacy(int i, boolean z, ComponentName componentName, Integer num) {
        Integer num2;
        AssistantInvocationEvent assistantInvocationEvent;
        ComponentName componentName2;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = i;
        if (num == null) {
            num2 = null;
        } else {
            switch (num.intValue()) {
                case 1:
                    i5 = 1;
                    break;
                case 2:
                    i5 = 2;
                    break;
                case 3:
                    i5 = 3;
                    break;
                case 4:
                    i5 = 4;
                    break;
                case 5:
                    i5 = 5;
                    break;
                case FalsingManager.VERSION /*6*/:
                    i5 = 6;
                    break;
                case 7:
                    i5 = 7;
                    break;
                case 8:
                    i5 = 8;
                    break;
                case 9:
                    i5 = 9;
                    break;
                case 10:
                    i5 = 10;
                    break;
                default:
                    i5 = 0;
                    break;
            }
            num2 = Integer.valueOf(i5);
        }
        if (z) {
            switch (i6) {
                case 1:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_TOUCH_GESTURE;
                    break;
                case 2:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_PHYSICAL_GESTURE;
                    break;
                case 3:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_HOTWORD;
                    break;
                case 4:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_QUICK_SEARCH_BAR;
                    break;
                case 5:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_HOME_LONG_PRESS;
                    break;
                case FalsingManager.VERSION /*6*/:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_POWER_LONG_PRESS;
                    break;
                default:
                    assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_UNKNOWN;
                    break;
            }
        } else if (i6 == 1) {
            assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_START_TOUCH_GESTURE;
        } else if (i6 != 2) {
            assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_START_UNKNOWN;
        } else {
            assistantInvocationEvent = AssistantInvocationEvent.ASSISTANT_INVOCATION_START_PHYSICAL_GESTURE;
        }
        AssistantInvocationEvent assistantInvocationEvent2 = assistantInvocationEvent;
        if (componentName == null) {
            componentName2 = this.assistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser());
        } else {
            componentName2 = componentName;
        }
        try {
            i2 = this.context.getPackageManager().getApplicationInfo(componentName2.getPackageName(), 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AssistLogger", "Unable to find Assistant UID", e);
            i2 = 0;
        }
        if (num2 == null) {
            switch (this.phoneStateMonitor.getPhoneState()) {
                case 1:
                    i3 = 1;
                    break;
                case 2:
                    i3 = 2;
                    break;
                case 3:
                    i3 = 3;
                    break;
                case 4:
                    i3 = 4;
                    break;
                case 5:
                    i3 = 5;
                    break;
                case FalsingManager.VERSION /*6*/:
                    i3 = 6;
                    break;
                case 7:
                    i3 = 7;
                    break;
                case 8:
                    i3 = 8;
                    break;
                case 9:
                    i3 = 9;
                    break;
                case 10:
                    i3 = 10;
                    break;
                default:
                    i4 = 0;
                    break;
            }
        } else {
            i4 = num2.intValue();
        }
        i3 = i4;
        int id = assistantInvocationEvent2.getId();
        String flattenToString = componentName2.flattenToString();
        InstanceId instanceId = this.currentInstanceId;
        if (instanceId == null) {
            instanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId;
        FrameworkStatsLog.write(281, id, i2, flattenToString, instanceId.getId(), i3, false);
        reportAssistantInvocationExtraData();
    }

    public final void reportAssistantSessionEvent(UiEventLogger.UiEventEnum uiEventEnum) {
        ComponentName assistComponentForUser = this.assistUtils.getAssistComponentForUser(KeyguardUpdateMonitor.getCurrentUser());
        int i = 0;
        try {
            i = this.context.getPackageManager().getApplicationInfo(assistComponentForUser.getPackageName(), 0).uid;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AssistLogger", "Unable to find Assistant UID", e);
        }
        UiEventLogger uiEventLogger2 = this.uiEventLogger;
        String flattenToString = assistComponentForUser.flattenToString();
        InstanceId instanceId = this.currentInstanceId;
        if (instanceId == null) {
            instanceId = this.instanceIdSequence.newInstanceId();
        }
        this.currentInstanceId = instanceId;
        uiEventLogger2.logWithInstanceId(uiEventEnum, i, flattenToString, instanceId);
        if (CollectionsKt___CollectionsKt.contains(SESSION_END_EVENTS, uiEventEnum)) {
            this.currentInstanceId = null;
        }
    }

    public AssistLogger(Context context2, UiEventLogger uiEventLogger2, AssistUtils assistUtils2, PhoneStateMonitor phoneStateMonitor2) {
        this.context = context2;
        this.uiEventLogger = uiEventLogger2;
        this.assistUtils = assistUtils2;
        this.phoneStateMonitor = phoneStateMonitor2;
    }
}
