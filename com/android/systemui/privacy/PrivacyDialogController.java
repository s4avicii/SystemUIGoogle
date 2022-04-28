package com.android.systemui.privacy;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.permission.PermissionManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function4;

/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogController {
    public final ActivityStarter activityStarter;
    public final AppOpsController appOpsController;
    public final Executor backgroundExecutor;
    public Dialog dialog;
    public final DialogProvider dialogProvider;
    public final KeyguardStateController keyguardStateController;
    public final PrivacyDialogController$onDialogDismissed$1 onDialogDismissed = new PrivacyDialogController$onDialogDismissed$1(this);
    public final PackageManager packageManager;
    public final PermissionManager permissionManager;
    public final PrivacyItemController privacyItemController;
    public final PrivacyLogger privacyLogger;
    public final UiEventLogger uiEventLogger;
    public final Executor uiExecutor;
    public final UserTracker userTracker;

    /* compiled from: PrivacyDialogController.kt */
    public interface DialogProvider {
        PrivacyDialog makeDialog(Context context, ArrayList arrayList, Function4 function4);
    }

    public static Intent getDefaultManageAppPermissionsIntent(String str, int i) {
        Intent intent = new Intent("android.intent.action.MANAGE_APP_PERMISSIONS");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.USER", UserHandle.of(i));
        return intent;
    }

    public PrivacyDialogController(PermissionManager permissionManager2, PackageManager packageManager2, PrivacyItemController privacyItemController2, UserTracker userTracker2, ActivityStarter activityStarter2, Executor executor, Executor executor2, PrivacyLogger privacyLogger2, KeyguardStateController keyguardStateController2, AppOpsController appOpsController2, UiEventLogger uiEventLogger2, DialogProvider dialogProvider2) {
        this.permissionManager = permissionManager2;
        this.packageManager = packageManager2;
        this.privacyItemController = privacyItemController2;
        this.userTracker = userTracker2;
        this.activityStarter = activityStarter2;
        this.backgroundExecutor = executor;
        this.uiExecutor = executor2;
        this.privacyLogger = privacyLogger2;
        this.keyguardStateController = keyguardStateController2;
        this.appOpsController = appOpsController2;
        this.uiEventLogger = uiEventLogger2;
        this.dialogProvider = dialogProvider2;
    }
}
