package com.google.android.systemui.columbus;

import android.content.DialogInterface;
import android.content.pm.LauncherActivityInfo;
import android.os.Messenger;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupcompat.partnerconfig.ResourceEntry;
import com.google.android.systemui.columbus.ColumbusTargetRequestService;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONObject;

/* renamed from: com.google.android.systemui.columbus.ColumbusTargetRequestService$IncomingMessageHandler$displayDialog$1 */
/* compiled from: ColumbusTargetRequestService.kt */
public final class C2193x675ce2c1 implements Runnable {
    public final /* synthetic */ LauncherActivityInfo $appInfo;
    public final /* synthetic */ Messenger $replyTo;
    public final /* synthetic */ int $requestCode;
    public final /* synthetic */ ColumbusTargetRequestService this$0;
    public final /* synthetic */ ColumbusTargetRequestService.IncomingMessageHandler this$1;

    public C2193x675ce2c1(ColumbusTargetRequestService columbusTargetRequestService, LauncherActivityInfo launcherActivityInfo, ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler, Messenger messenger, int i) {
        this.this$0 = columbusTargetRequestService;
        this.$appInfo = launcherActivityInfo;
        this.this$1 = incomingMessageHandler;
        this.$replyTo = messenger;
        this.$requestCode = i;
    }

    public final void run() {
        int packageShownCount = this.this$0.columbusStructuredDataManager.getPackageShownCount(this.$appInfo.getComponentName().getPackageName());
        int i = 0;
        this.this$0.uiEventLogger.log(ColumbusEvent.COLUMBUS_RETARGET_DIALOG_SHOWN, 0, this.$appInfo.getComponentName().getPackageName());
        ColumbusTargetRequestDialog columbusTargetRequestDialog = new ColumbusTargetRequestDialog(this.this$0.sysUIContext);
        columbusTargetRequestDialog.show();
        LauncherActivityInfo launcherActivityInfo = this.$appInfo;
        ColumbusTargetRequestService columbusTargetRequestService = this.this$0;
        ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler = this.this$1;
        Messenger messenger = this.$replyTo;
        int i2 = this.$requestCode;
        final ColumbusTargetRequestService columbusTargetRequestService2 = columbusTargetRequestService;
        final LauncherActivityInfo launcherActivityInfo2 = launcherActivityInfo;
        final ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler2 = incomingMessageHandler;
        final Messenger messenger2 = messenger;
        final int i3 = i2;
        int i4 = i2;
        final int i5 = packageShownCount;
        C21941 r3 = new DialogInterface.OnClickListener() {
            /* JADX WARNING: Code restructure failed: missing block: B:16:?, code lost:
                r3 = java.lang.System.currentTimeMillis();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
                r10.packageStats.put(com.google.android.systemui.columbus.ColumbusStructuredDataManager.makeJSONObject$default(r10, r2, 0, r4, 2));
                r10.storePackageStats();
             */
            /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
            /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x00d4 */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public final void onClick(android.content.DialogInterface r10, int r11) {
                /*
                    r9 = this;
                    r10 = -2
                    r0 = 0
                    if (r11 == r10) goto L_0x00ba
                    r10 = -1
                    if (r11 == r10) goto L_0x0018
                    java.lang.String r9 = "Columbus/TargetRequest"
                    java.lang.String r10 = "Invalid dialog option: "
                    java.lang.Integer r11 = java.lang.Integer.valueOf(r11)
                    java.lang.String r10 = kotlin.jvm.internal.Intrinsics.stringPlus(r10, r11)
                    android.util.Log.e(r9, r10)
                    goto L_0x0155
                L_0x0018:
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r10 = r4
                    android.content.ContentResolver r10 = r10.getContentResolver()
                    java.lang.String r11 = "columbus_enabled"
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r1 = r4
                    com.android.systemui.settings.UserTracker r1 = r1.userTracker
                    int r1 = r1.getUserId()
                    r2 = 1
                    android.provider.Settings.Secure.putIntForUser(r10, r11, r2, r1)
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r10 = r4
                    android.content.ContentResolver r10 = r10.getContentResolver()
                    java.lang.String r11 = "columbus_action"
                    java.lang.String r1 = "launch"
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r2 = r4
                    com.android.systemui.settings.UserTracker r2 = r2.userTracker
                    int r2 = r2.getUserId()
                    android.provider.Settings.Secure.putStringForUser(r10, r11, r1, r2)
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r10 = r4
                    android.content.ContentResolver r10 = r10.getContentResolver()
                    java.lang.String r11 = "columbus_launch_app"
                    android.content.pm.LauncherActivityInfo r1 = r5
                    android.content.ComponentName r1 = r1.getComponentName()
                    java.lang.String r1 = r1.flattenToString()
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r2 = r4
                    com.android.systemui.settings.UserTracker r2 = r2.userTracker
                    int r2 = r2.getUserId()
                    android.provider.Settings.Secure.putStringForUser(r10, r11, r1, r2)
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r10 = r4
                    android.content.ContentResolver r10 = r10.getContentResolver()
                    java.lang.String r11 = "columbus_launch_app_shortcut"
                    android.content.pm.LauncherActivityInfo r1 = r5
                    android.content.ComponentName r1 = r1.getComponentName()
                    java.lang.String r1 = r1.flattenToString()
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r2 = r4
                    com.android.systemui.settings.UserTracker r2 = r2.userTracker
                    int r2 = r2.getUserId()
                    android.provider.Settings.Secure.putStringForUser(r10, r11, r1, r2)
                    com.google.android.systemui.columbus.ColumbusTargetRequestService$IncomingMessageHandler r10 = r6
                    android.os.Messenger r11 = r7
                    int r1 = r8
                    int r2 = com.google.android.systemui.columbus.ColumbusTargetRequestService.IncomingMessageHandler.$r8$clinit
                    java.util.Objects.requireNonNull(r10)
                    com.google.android.systemui.columbus.ColumbusTargetRequestService.IncomingMessageHandler.replyToMessenger(r11, r1, r0)
                    java.lang.String r10 = "Columbus/TargetRequest"
                    java.lang.String r11 = "Target changed to "
                    android.content.pm.LauncherActivityInfo r1 = r5
                    android.content.ComponentName r1 = r1.getComponentName()
                    java.lang.String r1 = r1.flattenToString()
                    java.lang.String r11 = kotlin.jvm.internal.Intrinsics.stringPlus(r11, r1)
                    android.util.Log.d(r10, r11)
                    int r10 = r9
                    if (r10 != 0) goto L_0x00a5
                    com.google.android.systemui.columbus.ColumbusEvent r10 = com.google.android.systemui.columbus.ColumbusEvent.COLUMBUS_RETARGET_APPROVED
                    goto L_0x00a7
                L_0x00a5:
                    com.google.android.systemui.columbus.ColumbusEvent r10 = com.google.android.systemui.columbus.ColumbusEvent.COLUMBUS_RETARGET_FOLLOW_ON_APPROVED
                L_0x00a7:
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r11 = r4
                    com.android.internal.logging.UiEventLogger r11 = r11.uiEventLogger
                    android.content.pm.LauncherActivityInfo r9 = r5
                    android.content.ComponentName r9 = r9.getComponentName()
                    java.lang.String r9 = r9.flattenToString()
                    r11.log(r10, r0, r9)
                    goto L_0x0155
                L_0x00ba:
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r10 = r4
                    com.google.android.systemui.columbus.ColumbusStructuredDataManager r10 = r10.columbusStructuredDataManager
                    android.content.pm.LauncherActivityInfo r11 = r5
                    android.content.ComponentName r11 = r11.getComponentName()
                    java.lang.String r2 = r11.getPackageName()
                    java.util.Objects.requireNonNull(r10)
                    java.lang.Object r11 = r10.lock
                    monitor-enter(r11)
                    long r3 = android.os.SystemClock.currentNetworkTimeMillis()     // Catch:{ DateTimeException -> 0x00d4 }
                L_0x00d2:
                    r4 = r3
                    goto L_0x00d9
                L_0x00d4:
                    long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0156 }
                    goto L_0x00d2
                L_0x00d9:
                    org.json.JSONArray r1 = r10.packageStats     // Catch:{ all -> 0x0156 }
                    int r1 = r1.length()     // Catch:{ all -> 0x0156 }
                    r3 = r0
                L_0x00e0:
                    if (r3 >= r1) goto L_0x0107
                    int r6 = r3 + 1
                    org.json.JSONArray r7 = r10.packageStats     // Catch:{ all -> 0x0156 }
                    org.json.JSONObject r7 = r7.getJSONObject(r3)     // Catch:{ all -> 0x0156 }
                    java.lang.String r8 = "packageName"
                    java.lang.String r8 = r7.getString(r8)     // Catch:{ all -> 0x0156 }
                    boolean r8 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r8)     // Catch:{ all -> 0x0156 }
                    if (r8 == 0) goto L_0x0105
                    java.lang.String r1 = "lastDeny"
                    r7.put(r1, r4)     // Catch:{ all -> 0x0156 }
                    org.json.JSONArray r1 = r10.packageStats     // Catch:{ all -> 0x0156 }
                    r1.put(r3, r7)     // Catch:{ all -> 0x0156 }
                    r10.storePackageStats()     // Catch:{ all -> 0x0156 }
                    monitor-exit(r11)
                    goto L_0x0117
                L_0x0105:
                    r3 = r6
                    goto L_0x00e0
                L_0x0107:
                    org.json.JSONArray r7 = r10.packageStats     // Catch:{ all -> 0x0156 }
                    r3 = 0
                    r6 = 2
                    r1 = r10
                    org.json.JSONObject r1 = com.google.android.systemui.columbus.ColumbusStructuredDataManager.makeJSONObject$default(r1, r2, r3, r4, r6)     // Catch:{ all -> 0x0156 }
                    r7.put(r1)     // Catch:{ all -> 0x0156 }
                    r10.storePackageStats()     // Catch:{ all -> 0x0156 }
                    monitor-exit(r11)
                L_0x0117:
                    com.google.android.systemui.columbus.ColumbusTargetRequestService$IncomingMessageHandler r10 = r6
                    android.os.Messenger r11 = r7
                    int r1 = r8
                    r2 = 5
                    int r3 = com.google.android.systemui.columbus.ColumbusTargetRequestService.IncomingMessageHandler.$r8$clinit
                    java.util.Objects.requireNonNull(r10)
                    com.google.android.systemui.columbus.ColumbusTargetRequestService.IncomingMessageHandler.replyToMessenger(r11, r1, r2)
                    java.lang.String r10 = "Columbus/TargetRequest"
                    java.lang.String r11 = "Target change denied by user: "
                    android.content.pm.LauncherActivityInfo r1 = r5
                    android.content.ComponentName r1 = r1.getComponentName()
                    java.lang.String r1 = r1.flattenToString()
                    java.lang.String r11 = kotlin.jvm.internal.Intrinsics.stringPlus(r11, r1)
                    android.util.Log.d(r10, r11)
                    int r10 = r9
                    if (r10 != 0) goto L_0x0142
                    com.google.android.systemui.columbus.ColumbusEvent r10 = com.google.android.systemui.columbus.ColumbusEvent.COLUMBUS_RETARGET_NOT_APPROVED
                    goto L_0x0144
                L_0x0142:
                    com.google.android.systemui.columbus.ColumbusEvent r10 = com.google.android.systemui.columbus.ColumbusEvent.COLUMBUS_RETARGET_FOLLOW_ON_NOT_APPROVED
                L_0x0144:
                    com.google.android.systemui.columbus.ColumbusTargetRequestService r11 = r4
                    com.android.internal.logging.UiEventLogger r11 = r11.uiEventLogger
                    android.content.pm.LauncherActivityInfo r9 = r5
                    android.content.ComponentName r9 = r9.getComponentName()
                    java.lang.String r9 = r9.flattenToString()
                    r11.log(r10, r0, r9)
                L_0x0155:
                    return
                L_0x0156:
                    r9 = move-exception
                    monitor-exit(r11)
                    throw r9
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.columbus.C2193x675ce2c1.C21941.onClick(android.content.DialogInterface, int):void");
            }
        };
        final ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler3 = incomingMessageHandler;
        final Messenger messenger3 = messenger;
        final int i6 = i4;
        final LauncherActivityInfo launcherActivityInfo3 = launcherActivityInfo;
        final int i7 = packageShownCount;
        C21952 r1 = r3;
        final ColumbusTargetRequestService columbusTargetRequestService3 = columbusTargetRequestService;
        C21952 r32 = new DialogInterface.OnCancelListener() {
            public final void onCancel(DialogInterface dialogInterface) {
                ColumbusEvent columbusEvent;
                ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler = incomingMessageHandler3;
                Messenger messenger = messenger3;
                int i = i6;
                int i2 = ColumbusTargetRequestService.IncomingMessageHandler.$r8$clinit;
                Objects.requireNonNull(incomingMessageHandler);
                ColumbusTargetRequestService.IncomingMessageHandler.replyToMessenger(messenger, i, 6);
                Log.d("Columbus/TargetRequest", Intrinsics.stringPlus("Target change dismissed by user: ", launcherActivityInfo3.getComponentName().flattenToString()));
                if (i7 == 0) {
                    columbusEvent = ColumbusEvent.COLUMBUS_RETARGET_NOT_APPROVED;
                } else {
                    columbusEvent = ColumbusEvent.COLUMBUS_RETARGET_FOLLOW_ON_NOT_APPROVED;
                }
                columbusTargetRequestService3.uiEventLogger.log(columbusEvent, 0, launcherActivityInfo3.getComponentName().flattenToString());
            }
        };
        columbusTargetRequestDialog.setTitle(columbusTargetRequestDialog.getContext().getString(C1777R.string.columbus_target_request_dialog_title, new Object[]{launcherActivityInfo.getLabel()}));
        columbusTargetRequestDialog.setMessage(columbusTargetRequestDialog.getContext().getString(C1777R.string.columbus_target_request_dialog_summary, new Object[]{launcherActivityInfo.getLabel()}));
        columbusTargetRequestDialog.setPositiveButton(C1777R.string.columbus_target_request_dialog_allow, r3);
        columbusTargetRequestDialog.setNegativeButton(C1777R.string.columbus_target_request_dialog_deny, r3);
        columbusTargetRequestDialog.setOnCancelListener(r1);
        columbusTargetRequestDialog.setCanceledOnTouchOutside(true);
        ColumbusTargetRequestService.IncomingMessageHandler incomingMessageHandler4 = this.this$1;
        String packageName = this.$appInfo.getComponentName().getPackageName();
        Objects.requireNonNull(incomingMessageHandler4);
        ColumbusStructuredDataManager columbusStructuredDataManager = ColumbusTargetRequestService.this.columbusStructuredDataManager;
        Objects.requireNonNull(columbusStructuredDataManager);
        synchronized (columbusStructuredDataManager.lock) {
            int length = columbusStructuredDataManager.packageStats.length();
            while (i < length) {
                int i8 = i + 1;
                JSONObject jSONObject = columbusStructuredDataManager.packageStats.getJSONObject(i);
                if (Intrinsics.areEqual(packageName, jSONObject.getString(ResourceEntry.KEY_PACKAGE_NAME))) {
                    jSONObject.put("shownCount", jSONObject.getInt("shownCount") + 1);
                    columbusStructuredDataManager.packageStats.put(i, jSONObject);
                    columbusStructuredDataManager.storePackageStats();
                    return;
                }
                i = i8;
            }
            columbusStructuredDataManager.packageStats.put(ColumbusStructuredDataManager.makeJSONObject$default(columbusStructuredDataManager, packageName, 1, 0, 4));
            columbusStructuredDataManager.storePackageStats();
        }
    }
}
