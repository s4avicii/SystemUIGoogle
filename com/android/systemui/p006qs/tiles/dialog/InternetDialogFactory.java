package com.android.systemui.p006qs.tiles.dialog;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogFactory */
/* compiled from: InternetDialogFactory.kt */
public final class InternetDialogFactory {
    public static InternetDialog internetDialog;
    public final Context context;
    public final DialogLaunchAnimator dialogLaunchAnimator;
    public final Executor executor;
    public final Handler handler;
    public final InternetDialogController internetDialogController;
    public final KeyguardStateController keyguardStateController;
    public final UiEventLogger uiEventLogger;

    public final void create(boolean z, boolean z2, View view) {
        if (internetDialog == null) {
            InternetDialog internetDialog2 = new InternetDialog(this.context, this, this.internetDialogController, z, z2, this.uiEventLogger, this.handler, this.executor, this.keyguardStateController);
            internetDialog = internetDialog2;
            if (view != null) {
                this.dialogLaunchAnimator.showFromView(internetDialog2, view, true);
            } else {
                internetDialog2.show();
            }
        } else if (InternetDialogFactoryKt.DEBUG) {
            Log.d("InternetDialogFactory", "InternetDialog is showing, do not create it twice.");
        }
    }

    public InternetDialogFactory(Handler handler2, Executor executor2, InternetDialogController internetDialogController2, Context context2, UiEventLogger uiEventLogger2, DialogLaunchAnimator dialogLaunchAnimator2, KeyguardStateController keyguardStateController2) {
        this.handler = handler2;
        this.executor = executor2;
        this.internetDialogController = internetDialogController2;
        this.context = context2;
        this.uiEventLogger = uiEventLogger2;
        this.dialogLaunchAnimator = dialogLaunchAnimator2;
        this.keyguardStateController = keyguardStateController2;
    }
}
