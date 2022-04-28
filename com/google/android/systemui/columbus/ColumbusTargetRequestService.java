package com.google.android.systemui.columbus;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.settings.UserTracker;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;

/* compiled from: ColumbusTargetRequestService.kt */
public final class ColumbusTargetRequestService extends Service {
    public static final long PACKAGE_DENY_COOLDOWN_MS = TimeUnit.DAYS.toMillis(5);
    public final Set<String> allowCertList;
    public final Set<String> allowPackageList;
    public final ColumbusContext columbusContext;
    public final ColumbusSettings columbusSettings;
    public final ColumbusStructuredDataManager columbusStructuredDataManager;
    public LauncherApps launcherApps;
    public final Handler mainHandler;
    public final MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
    public final Messenger messenger;
    public final Context sysUIContext;
    public final UiEventLogger uiEventLogger;
    public final UserTracker userTracker;

    /* compiled from: ColumbusTargetRequestService.kt */
    public final class IncomingMessageHandler extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;

        public IncomingMessageHandler(Looper looper) {
            super(looper);
        }

        public static void replyToMessenger(Messenger messenger, int i, int i2) {
            if (messenger != null) {
                Message what = Message.obtain().setWhat(i2);
                what.arg1 = i;
                messenger.send(what);
            }
        }

        public final LauncherActivityInfo getAppInfoForPackage(String str) {
            List<LauncherActivityInfo> activityList;
            PendingIntent pendingIntent;
            ColumbusTargetRequestService columbusTargetRequestService = ColumbusTargetRequestService.this;
            LauncherApps launcherApps = columbusTargetRequestService.launcherApps;
            T t = null;
            if (launcherApps == null || (activityList = launcherApps.getActivityList(str, columbusTargetRequestService.userTracker.getUserHandle())) == null) {
                return null;
            }
            ColumbusTargetRequestService columbusTargetRequestService2 = ColumbusTargetRequestService.this;
            Iterator<T> it = activityList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                T next = it.next();
                LauncherActivityInfo launcherActivityInfo = (LauncherActivityInfo) next;
                boolean z = false;
                try {
                    LauncherApps launcherApps2 = columbusTargetRequestService2.launcherApps;
                    if (launcherApps2 == null) {
                        pendingIntent = null;
                    } else {
                        pendingIntent = launcherApps2.getMainActivityLaunchIntent(launcherActivityInfo.getComponentName(), (Bundle) null, columbusTargetRequestService2.userTracker.getUserHandle());
                    }
                    if (pendingIntent != null) {
                        z = true;
                        continue;
                    } else {
                        continue;
                    }
                } catch (RuntimeException unused) {
                }
                if (z) {
                    t = next;
                    break;
                }
            }
            return (LauncherActivityInfo) t;
        }

        public final void handleMessage(Message message) {
            String str;
            boolean z;
            String[] packagesForUid = ColumbusTargetRequestService.this.getPackageManager().getPackagesForUid(message.sendingUid);
            boolean z2 = false;
            if (packagesForUid == null) {
                str = null;
            } else {
                str = packagesForUid[0];
            }
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    Log.w("Columbus/TargetRequest", Intrinsics.stringPlus("Invalid request type: ", Integer.valueOf(i)));
                    return;
                }
                if (str != null && !packageIsNotAllowed(str)) {
                    if (ColumbusTargetRequestService.this.columbusStructuredDataManager.getPackageShownCount(str) >= 3) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z && getAppInfoForPackage(str) != null) {
                        if (packageIsTarget(str)) {
                            replyToMessenger(message.replyTo, message.what, 0);
                            return;
                        } else if (packageNeedsToCoolDown(str)) {
                            replyToMessenger(message.replyTo, message.what, 3);
                            return;
                        } else {
                            replyToMessenger(message.replyTo, message.what, 1);
                            return;
                        }
                    }
                }
                replyToMessenger(message.replyTo, message.what, 2);
            } else if (str == null || packageIsNotAllowed(str)) {
                replyToMessenger(message.replyTo, message.what, 1);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Unsupported caller: ", str));
            } else if (packageIsTarget(str)) {
                replyToMessenger(message.replyTo, message.what, 0);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller already target: ", str));
            } else if (packageNeedsToCoolDown(str)) {
                replyToMessenger(message.replyTo, message.what, 2);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller throttled: ", str));
            } else {
                if (ColumbusTargetRequestService.this.columbusStructuredDataManager.getPackageShownCount(str) >= 3) {
                    z2 = true;
                }
                if (z2) {
                    replyToMessenger(message.replyTo, message.what, 3);
                    Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller already shown max times: ", str));
                    return;
                }
                LauncherActivityInfo appInfoForPackage = getAppInfoForPackage(str);
                if (appInfoForPackage == null) {
                    replyToMessenger(message.replyTo, message.what, 4);
                    Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Caller not launchable: ", str));
                    return;
                }
                Messenger messenger = message.replyTo;
                int i2 = message.what;
                ColumbusTargetRequestService columbusTargetRequestService = ColumbusTargetRequestService.this;
                columbusTargetRequestService.mainHandler.post(new C2193x675ce2c1(columbusTargetRequestService, appInfoForPackage, this, messenger, i2));
            }
        }

        public final boolean packageIsNotAllowed(String str) {
            Signature[] signatureArr;
            if (!ColumbusTargetRequestService.this.allowPackageList.contains(str)) {
                return true;
            }
            PackageInfo packageInfo = ColumbusTargetRequestService.this.sysUIContext.getPackageManager().getPackageInfo(str, 134217728);
            if (packageInfo.signingInfo.hasMultipleSigners()) {
                signatureArr = packageInfo.signingInfo.getApkContentsSigners();
            } else {
                signatureArr = packageInfo.signingInfo.getSigningCertificateHistory();
            }
            ColumbusTargetRequestService columbusTargetRequestService = ColumbusTargetRequestService.this;
            ArrayList arrayList = new ArrayList(signatureArr.length);
            int length = signatureArr.length;
            boolean z = false;
            int i = 0;
            while (i < length) {
                Signature signature = signatureArr[i];
                i++;
                arrayList.add(new String(columbusTargetRequestService.messageDigest.digest(signature.toByteArray()), Charsets.UTF_16));
            }
            ColumbusTargetRequestService columbusTargetRequestService2 = ColumbusTargetRequestService.this;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (columbusTargetRequestService2.allowCertList.contains((String) it.next())) {
                        z = true;
                        break;
                    }
                }
            }
            return !z;
        }

        public final boolean packageIsTarget(String str) {
            String str2;
            boolean isColumbusEnabled = ColumbusTargetRequestService.this.columbusSettings.isColumbusEnabled();
            boolean areEqual = Intrinsics.areEqual("launch", ColumbusTargetRequestService.this.columbusSettings.selectedAction());
            ComponentName unflattenFromString = ComponentName.unflattenFromString(ColumbusTargetRequestService.this.columbusSettings.selectedApp());
            if (unflattenFromString == null) {
                str2 = null;
            } else {
                str2 = unflattenFromString.getPackageName();
            }
            boolean areEqual2 = Intrinsics.areEqual(str, str2);
            if (!isColumbusEnabled || !areEqual || !areEqual2) {
                return false;
            }
            return true;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:5:?, code lost:
            r1 = java.lang.System.currentTimeMillis();
         */
        /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
        /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x000f */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean packageNeedsToCoolDown(java.lang.String r4) {
            /*
                r3 = this;
                com.google.android.systemui.columbus.ColumbusTargetRequestService r3 = com.google.android.systemui.columbus.ColumbusTargetRequestService.this
                com.google.android.systemui.columbus.ColumbusStructuredDataManager r3 = r3.columbusStructuredDataManager
                java.util.Objects.requireNonNull(r3)
                java.lang.Object r0 = r3.lock
                monitor-enter(r0)
                long r1 = android.os.SystemClock.currentNetworkTimeMillis()     // Catch:{ DateTimeException -> 0x000f }
                goto L_0x0013
            L_0x000f:
                long r1 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0023 }
            L_0x0013:
                long r3 = r3.getLastDenyTimestamp(r4)     // Catch:{ all -> 0x0023 }
                long r1 = r1 - r3
                monitor-exit(r0)
                long r3 = com.google.android.systemui.columbus.ColumbusTargetRequestService.PACKAGE_DENY_COOLDOWN_MS
                int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                if (r3 >= 0) goto L_0x0021
                r3 = 1
                goto L_0x0022
            L_0x0021:
                r3 = 0
            L_0x0022:
                return r3
            L_0x0023:
                r3 = move-exception
                monitor-exit(r0)
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.columbus.ColumbusTargetRequestService.IncomingMessageHandler.packageNeedsToCoolDown(java.lang.String):boolean");
        }
    }

    public final int onStartCommand(Intent intent, int i, int i2) {
        return 2;
    }

    public final IBinder onBind(Intent intent) {
        ColumbusContext columbusContext2 = this.columbusContext;
        Objects.requireNonNull(columbusContext2);
        if (columbusContext2.packageManager.hasSystemFeature("com.google.android.feature.QUICK_TAP")) {
            return this.messenger.getBinder();
        }
        return null;
    }

    public ColumbusTargetRequestService(Context context, UserTracker userTracker2, ColumbusSettings columbusSettings2, ColumbusStructuredDataManager columbusStructuredDataManager2, UiEventLogger uiEventLogger2, Handler handler, Looper looper) {
        this.sysUIContext = context;
        this.userTracker = userTracker2;
        this.columbusSettings = columbusSettings2;
        this.columbusStructuredDataManager = columbusStructuredDataManager2;
        this.uiEventLogger = uiEventLogger2;
        this.mainHandler = handler;
        this.columbusContext = new ColumbusContext(context);
        this.messenger = new Messenger(new IncomingMessageHandler(looper));
        String[] stringArray = context.getResources().getStringArray(C1777R.array.columbus_sumatra_package_allow_list);
        this.allowPackageList = SetsKt__SetsKt.setOf(Arrays.copyOf(stringArray, stringArray.length));
        String[] stringArray2 = context.getResources().getStringArray(C1777R.array.columbus_sumatra_cert_allow_list);
        this.allowCertList = SetsKt__SetsKt.setOf(Arrays.copyOf(stringArray2, stringArray2.length));
    }

    public final void onCreate() {
        LauncherApps launcherApps2;
        super.onCreate();
        Object systemService = getSystemService("launcherapps");
        if (systemService instanceof LauncherApps) {
            launcherApps2 = (LauncherApps) systemService;
        } else {
            launcherApps2 = null;
        }
        this.launcherApps = launcherApps2;
    }
}
