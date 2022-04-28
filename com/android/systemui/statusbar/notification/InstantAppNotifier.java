package com.android.systemui.statusbar.notification;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AppGlobals;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SynchronousUserSwitchObserver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Pair;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda18;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda2;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

public class InstantAppNotifier extends CoreStartable implements CommandQueue.Callbacks, KeyguardStateController.Callback {
    public final CommandQueue mCommandQueue;
    public final ArraySet<Pair<String, Integer>> mCurrentNotifs = new ArraySet<>();
    public boolean mDockedStackExists;
    public final Handler mHandler = new Handler();
    public KeyguardStateController mKeyguardStateController;
    public final Optional<LegacySplitScreen> mSplitScreenOptional;
    public final Executor mUiBgExecutor;
    public final C12321 mUserSwitchListener = new SynchronousUserSwitchObserver() {
        public static final /* synthetic */ int $r8$clinit = 0;

        public final void onUserSwitching(int i) throws RemoteException {
        }

        public final void onUserSwitchComplete(int i) throws RemoteException {
            InstantAppNotifier.this.mHandler.post(new BubbleStackView$$ExternalSyntheticLambda18(this, 4));
        }
    };

    public final void appTransitionStarting(int i, long j, long j2, boolean z) {
        if (this.mContext.getDisplayId() == i) {
            updateForegroundInstantApps();
        }
    }

    public final void checkAndPostForStack(ActivityTaskManager.RootTaskInfo rootTaskInfo, ArraySet<Pair<String, Integer>> arraySet, NotificationManager notificationManager, IPackageManager iPackageManager) {
        ApplicationInfo applicationInfo;
        if (rootTaskInfo != null) {
            try {
                ComponentName componentName = rootTaskInfo.topActivity;
                if (componentName != null) {
                    String packageName = componentName.getPackageName();
                    if (!arraySet.remove(new Pair(packageName, Integer.valueOf(rootTaskInfo.userId))) && (applicationInfo = iPackageManager.getApplicationInfo(packageName, 8192, rootTaskInfo.userId)) != null && applicationInfo.isInstantApp()) {
                        int i = rootTaskInfo.userId;
                        int[] iArr = rootTaskInfo.childTaskIds;
                        postInstantAppNotif(packageName, i, applicationInfo, notificationManager, iArr[iArr.length - 1]);
                    }
                }
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
            }
        }
    }

    public final void postInstantAppNotif(String str, int i, ApplicationInfo applicationInfo, NotificationManager notificationManager, int i2) {
        int i3;
        Notification.Action action;
        PendingIntent pendingIntent;
        String str2;
        Intent intent;
        PendingIntent pendingIntent2;
        char c;
        Notification.Builder builder;
        ComponentName componentName;
        String str3 = str;
        int i4 = i;
        ApplicationInfo applicationInfo2 = applicationInfo;
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", this.mContext.getString(C1777R.string.instant_apps));
        this.mCurrentNotifs.add(new Pair(str3, Integer.valueOf(i)));
        String string = this.mContext.getString(C1777R.string.instant_apps_help_url);
        boolean z = !string.isEmpty();
        Context context = this.mContext;
        if (z) {
            i3 = C1777R.string.instant_apps_message_with_help;
        } else {
            i3 = C1777R.string.instant_apps_message;
        }
        String string2 = context.getString(i3);
        UserHandle of = UserHandle.of(i);
        Notification.Action build = new Notification.Action.Builder((Icon) null, this.mContext.getString(C1777R.string.app_info), PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", str3, (String) null)), 67108864, (Bundle) null, of)).build();
        if (z) {
            str2 = "android.intent.action.VIEW";
            action = build;
            pendingIntent = PendingIntent.getActivityAsUser(this.mContext, 0, new Intent("android.intent.action.VIEW").setData(Uri.parse(string)), 67108864, (Bundle) null, of);
        } else {
            str2 = "android.intent.action.VIEW";
            action = build;
            pendingIntent = null;
        }
        List recentTasks = ActivityTaskManager.getInstance().getRecentTasks(5, 0, i4);
        int i5 = 0;
        while (true) {
            if (i5 >= recentTasks.size()) {
                intent = null;
                break;
            } else if (((ActivityManager.RecentTaskInfo) recentTasks.get(i5)).id == i2) {
                intent = ((ActivityManager.RecentTaskInfo) recentTasks.get(i5)).baseIntent;
                break;
            } else {
                i5++;
            }
        }
        Notification.Builder builder2 = new Notification.Builder(this.mContext, "GEN");
        if (intent == null || !intent.isWebIntent()) {
            builder = builder2;
            c = 0;
            pendingIntent2 = pendingIntent;
        } else {
            intent.setComponent((ComponentName) null).setPackage((String) null).addFlags(512).addFlags(268435456);
            Notification.Builder builder3 = builder2;
            c = 0;
            pendingIntent2 = pendingIntent;
            PendingIntent activityAsUser = PendingIntent.getActivityAsUser(this.mContext, 0, intent, 67108864, (Bundle) null, of);
            try {
                componentName = AppGlobals.getPackageManager().getInstantAppInstallerComponent();
            } catch (RemoteException e) {
                e.rethrowFromSystemServer();
                componentName = null;
            }
            Intent addCategory = new Intent().setComponent(componentName).setAction(str2).addCategory("android.intent.category.BROWSABLE");
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("unique:");
            m.append(System.currentTimeMillis());
            builder = builder3;
            builder.addAction(new Notification.Action.Builder((Icon) null, this.mContext.getString(C1777R.string.go_to_web), PendingIntent.getActivityAsUser(this.mContext, 0, addCategory.addCategory(m.toString()).putExtra("android.intent.extra.PACKAGE_NAME", applicationInfo2.packageName).putExtra("android.intent.extra.VERSION_CODE", applicationInfo2.versionCode & Integer.MAX_VALUE).putExtra("android.intent.extra.LONG_VERSION_CODE", applicationInfo2.longVersionCode).putExtra("android.intent.extra.INSTANT_APP_FAILURE", activityAsUser), 67108864, (Bundle) null, of)).build());
        }
        Notification.Builder color = builder.addExtras(bundle).addAction(action).setContentIntent(pendingIntent2).setColor(this.mContext.getColor(C1777R.color.instant_apps_color));
        Context context2 = this.mContext;
        Object[] objArr = new Object[1];
        objArr[c] = applicationInfo2.loadLabel(context2.getPackageManager());
        notificationManager.notifyAsUser(str3, 7, color.setContentTitle(context2.getString(C1777R.string.instant_apps_title, objArr)).setLargeIcon(Icon.createWithResource(str3, applicationInfo2.icon)).setSmallIcon(Icon.createWithResource(this.mContext.getPackageName(), C1777R.C1778drawable.instant_icon)).setContentText(string2).setStyle(new Notification.BigTextStyle().bigText(string2)).setOngoing(true).build(), new UserHandle(i4));
    }

    public final void start() {
        this.mKeyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);
        try {
            ActivityManager.getService().registerUserSwitchObserver(this.mUserSwitchListener, "InstantAppNotifier");
        } catch (RemoteException unused) {
        }
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mKeyguardStateController.addCallback(this);
        this.mSplitScreenOptional.ifPresent(new WMShell$$ExternalSyntheticLambda2(this, 2));
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        for (StatusBarNotification statusBarNotification : notificationManager.getActiveNotifications()) {
            if (statusBarNotification.getId() == 7) {
                notificationManager.cancel(statusBarNotification.getTag(), statusBarNotification.getId());
            }
        }
    }

    public final void updateForegroundInstantApps() {
        this.mUiBgExecutor.execute(new InstantAppNotifier$$ExternalSyntheticLambda0(this, (NotificationManager) this.mContext.getSystemService(NotificationManager.class), AppGlobals.getPackageManager()));
    }

    public InstantAppNotifier(Context context, CommandQueue commandQueue, Executor executor, Optional<LegacySplitScreen> optional) {
        super(context);
        this.mSplitScreenOptional = optional;
        this.mCommandQueue = commandQueue;
        this.mUiBgExecutor = executor;
    }

    public final void onKeyguardShowingChanged() {
        updateForegroundInstantApps();
    }

    public final void preloadRecentApps() {
        updateForegroundInstantApps();
    }
}
