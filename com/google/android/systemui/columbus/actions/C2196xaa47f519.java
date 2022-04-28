package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.content.res.ColorStateList;
import com.android.p012wm.shell.C1777R;

/* renamed from: com.google.android.systemui.columbus.actions.LaunchApp$keyguardUpdateMonitorCallback$1$onKeyguardBouncerChanged$1 */
/* compiled from: LaunchApp.kt */
public final class C2196xaa47f519 implements Runnable {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ LaunchApp this$0;

    public C2196xaa47f519(LaunchApp launchApp, Context context) {
        this.this$0 = launchApp;
        this.$context = context;
    }

    public final void run() {
        this.this$0.statusBarKeyguardViewManager.showBouncerMessage(this.$context.getString(C1777R.string.columbus_bouncer_message), ColorStateList.valueOf(-1));
    }
}
