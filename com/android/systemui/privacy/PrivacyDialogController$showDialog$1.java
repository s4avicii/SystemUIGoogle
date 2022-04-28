package com.android.systemui.privacy;

import android.content.Context;

/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogController$showDialog$1 implements Runnable {
    public final /* synthetic */ Context $context;
    public final /* synthetic */ PrivacyDialogController this$0;

    public PrivacyDialogController$showDialog$1(PrivacyDialogController privacyDialogController, Context context) {
        this.this$0 = privacyDialogController;
        this.$context = context;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:24:0x008a, code lost:
        if (r10.micCameraAvailable != false) goto L_0x009a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0096, code lost:
        if (r10.locationAvailable != false) goto L_0x009a;
     */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x007f A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a6  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00d7  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x0125  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x0128  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0190  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x01a1  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x01a4 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00bf A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r30 = this;
            r0 = r30
            com.android.systemui.privacy.PrivacyType r1 = com.android.systemui.privacy.PrivacyType.TYPE_MICROPHONE
            com.android.systemui.privacy.PrivacyType r2 = com.android.systemui.privacy.PrivacyType.TYPE_LOCATION
            com.android.systemui.privacy.PrivacyType r3 = com.android.systemui.privacy.PrivacyType.TYPE_CAMERA
            com.android.systemui.privacy.PrivacyDialogController r4 = r0.this$0
            java.util.Objects.requireNonNull(r4)
            android.permission.PermissionManager r5 = r4.permissionManager
            com.android.systemui.appops.AppOpsController r4 = r4.appOpsController
            boolean r4 = r4.isMicMuted()
            java.util.List r4 = r5.getIndicatorAppOpUsageData(r4)
            com.android.systemui.privacy.PrivacyDialogController r5 = r0.this$0
            com.android.systemui.settings.UserTracker r5 = r5.userTracker
            java.util.List r5 = r5.getUserProfiles()
            com.android.systemui.privacy.PrivacyDialogController r6 = r0.this$0
            com.android.systemui.privacy.logging.PrivacyLogger r6 = r6.privacyLogger
            r6.logUnfilteredPermGroupUsage(r4)
            com.android.systemui.privacy.PrivacyDialogController r6 = r0.this$0
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.Iterator r4 = r4.iterator()
        L_0x0033:
            boolean r8 = r4.hasNext()
            if (r8 == 0) goto L_0x01a8
            java.lang.Object r8 = r4.next()
            android.permission.PermissionGroupUsage r8 = (android.permission.PermissionGroupUsage) r8
            java.lang.String r9 = r8.getPermissionGroupName()
            java.util.Objects.requireNonNull(r6)
            int r10 = r9.hashCode()
            r11 = -1140935117(0xffffffffbbfeb633, float:-0.0077731847)
            if (r10 == r11) goto L_0x0070
            r11 = 828638019(0x31640343, float:3.318022E-9)
            if (r10 == r11) goto L_0x0065
            r11 = 1581272376(0x5e404d38, float:3.4641983E18)
            if (r10 == r11) goto L_0x005a
            goto L_0x0078
        L_0x005a:
            java.lang.String r10 = "android.permission-group.MICROPHONE"
            boolean r9 = r9.equals(r10)
            if (r9 != 0) goto L_0x0063
            goto L_0x0078
        L_0x0063:
            r9 = r1
            goto L_0x007b
        L_0x0065:
            java.lang.String r10 = "android.permission-group.LOCATION"
            boolean r9 = r9.equals(r10)
            if (r9 != 0) goto L_0x006e
            goto L_0x0078
        L_0x006e:
            r9 = r2
            goto L_0x007b
        L_0x0070:
            java.lang.String r10 = "android.permission-group.CAMERA"
            boolean r9 = r9.equals(r10)
            if (r9 != 0) goto L_0x007a
        L_0x0078:
            r9 = 0
            goto L_0x007b
        L_0x007a:
            r9 = r3
        L_0x007b:
            if (r9 != 0) goto L_0x007f
            r14 = 0
            goto L_0x009b
        L_0x007f:
            if (r9 == r3) goto L_0x0083
            if (r9 != r1) goto L_0x008d
        L_0x0083:
            com.android.systemui.privacy.PrivacyItemController r10 = r6.privacyItemController
            java.util.Objects.requireNonNull(r10)
            boolean r10 = r10.micCameraAvailable
            if (r10 == 0) goto L_0x008d
            goto L_0x009a
        L_0x008d:
            if (r9 != r2) goto L_0x0099
            com.android.systemui.privacy.PrivacyItemController r10 = r6.privacyItemController
            java.util.Objects.requireNonNull(r10)
            boolean r10 = r10.locationAvailable
            if (r10 == 0) goto L_0x0099
            goto L_0x009a
        L_0x0099:
            r9 = 0
        L_0x009a:
            r14 = r9
        L_0x009b:
            java.util.Iterator r9 = r5.iterator()
        L_0x009f:
            boolean r10 = r9.hasNext()
            r13 = 0
            if (r10 == 0) goto L_0x00bf
            java.lang.Object r10 = r9.next()
            r15 = r10
            android.content.pm.UserInfo r15 = (android.content.pm.UserInfo) r15
            int r15 = r15.id
            int r16 = r8.getUid()
            int r12 = android.os.UserHandle.getUserId(r16)
            if (r15 != r12) goto L_0x00bb
            r12 = 1
            goto L_0x00bc
        L_0x00bb:
            r12 = r13
        L_0x00bc:
            if (r12 == 0) goto L_0x009f
            goto L_0x00c0
        L_0x00bf:
            r10 = 0
        L_0x00c0:
            android.content.pm.UserInfo r10 = (android.content.pm.UserInfo) r10
            if (r10 != 0) goto L_0x00ca
            boolean r9 = r8.isPhoneCall()
            if (r9 == 0) goto L_0x00cc
        L_0x00ca:
            if (r14 != 0) goto L_0x00d1
        L_0x00cc:
            r29 = r1
            r12 = 0
            goto L_0x019e
        L_0x00d1:
            boolean r9 = r8.isPhoneCall()
            if (r9 == 0) goto L_0x00dc
            java.lang.String r9 = ""
        L_0x00d9:
            r17 = r9
            goto L_0x0101
        L_0x00dc:
            java.lang.String r9 = r8.getPackageName()
            int r12 = r8.getUid()
            android.content.pm.PackageManager r15 = r6.packageManager     // Catch:{ NameNotFoundException -> 0x00f5 }
            int r12 = android.os.UserHandle.getUserId(r12)     // Catch:{ NameNotFoundException -> 0x00f5 }
            android.content.pm.ApplicationInfo r12 = r15.getApplicationInfoAsUser(r9, r13, r12)     // Catch:{ NameNotFoundException -> 0x00f5 }
            android.content.pm.PackageManager r15 = r6.packageManager     // Catch:{ NameNotFoundException -> 0x00f5 }
            java.lang.CharSequence r9 = r12.loadLabel(r15)     // Catch:{ NameNotFoundException -> 0x00f5 }
            goto L_0x00d9
        L_0x00f5:
            java.lang.String r12 = "Label not found for: "
            java.lang.String r12 = kotlin.jvm.internal.Intrinsics.stringPlus(r12, r9)
            java.lang.String r15 = "PrivacyDialogController"
            android.util.Log.w(r15, r12)
            goto L_0x00d9
        L_0x0101:
            int r9 = r8.getUid()
            int r9 = android.os.UserHandle.getUserId(r9)
            com.android.systemui.privacy.PrivacyDialog$PrivacyElement r12 = new com.android.systemui.privacy.PrivacyDialog$PrivacyElement
            java.lang.String r15 = r8.getPackageName()
            java.lang.CharSequence r18 = r8.getAttributionTag()
            java.lang.CharSequence r19 = r8.getAttributionLabel()
            java.lang.CharSequence r20 = r8.getProxyLabel()
            long r21 = r8.getLastAccessTimeMillis()
            boolean r23 = r8.isActive()
            if (r10 != 0) goto L_0x0128
            r24 = r13
            goto L_0x012e
        L_0x0128:
            boolean r10 = r10.isManagedProfile()
            r24 = r10
        L_0x012e:
            boolean r25 = r8.isPhoneCall()
            java.lang.String r26 = r8.getPermissionGroupName()
            java.lang.String r10 = r8.getPackageName()
            java.lang.String r16 = r8.getPermissionGroupName()
            java.lang.CharSequence r8 = r8.getAttributionTag()
            if (r8 == 0) goto L_0x0190
            android.content.Intent r13 = new android.content.Intent
            java.lang.String r11 = "android.intent.action.MANAGE_PERMISSION_USAGE"
            r13.<init>(r11)
            r13.setPackage(r10)
            java.lang.String r11 = r16.toString()
            r29 = r1
            java.lang.String r1 = "android.intent.extra.PERMISSION_GROUP_NAME"
            r13.putExtra(r1, r11)
            r1 = 1
            java.lang.String[] r11 = new java.lang.String[r1]
            java.lang.String r8 = r8.toString()
            r16 = 0
            r11[r16] = r8
            java.lang.String r8 = "android.intent.extra.ATTRIBUTION_TAGS"
            r13.putExtra(r8, r11)
            java.lang.String r8 = "android.intent.extra.SHOWING_ATTRIBUTION"
            r13.putExtra(r8, r1)
            android.content.pm.PackageManager r1 = r6.packageManager
            r27 = 0
            android.content.pm.PackageManager$ResolveInfoFlags r8 = android.content.pm.PackageManager.ResolveInfoFlags.of(r27)
            android.content.pm.ResolveInfo r1 = r1.resolveActivity(r13, r8)
            if (r1 != 0) goto L_0x0181
            android.content.Intent r1 = com.android.systemui.privacy.PrivacyDialogController.getDefaultManageAppPermissionsIntent(r10, r9)
            goto L_0x0196
        L_0x0181:
            android.content.ComponentName r8 = new android.content.ComponentName
            android.content.pm.ActivityInfo r1 = r1.activityInfo
            java.lang.String r1 = r1.name
            r8.<init>(r10, r1)
            r13.setComponent(r8)
            r27 = r13
            goto L_0x0198
        L_0x0190:
            r29 = r1
            android.content.Intent r1 = com.android.systemui.privacy.PrivacyDialogController.getDefaultManageAppPermissionsIntent(r10, r9)
        L_0x0196:
            r27 = r1
        L_0x0198:
            r13 = r12
            r16 = r9
            r13.<init>(r14, r15, r16, r17, r18, r19, r20, r21, r23, r24, r25, r26, r27)
        L_0x019e:
            if (r12 != 0) goto L_0x01a1
            goto L_0x01a4
        L_0x01a1:
            r7.add(r12)
        L_0x01a4:
            r1 = r29
            goto L_0x0033
        L_0x01a8:
            com.android.systemui.privacy.PrivacyDialogController r1 = r0.this$0
            java.util.concurrent.Executor r2 = r1.uiExecutor
            com.android.systemui.privacy.PrivacyDialogController$showDialog$1$1 r3 = new com.android.systemui.privacy.PrivacyDialogController$showDialog$1$1
            android.content.Context r0 = r0.$context
            r3.<init>(r1, r7, r0)
            r2.execute(r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.privacy.PrivacyDialogController$showDialog$1.run():void");
    }
}
