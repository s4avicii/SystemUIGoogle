package com.google.android.systemui.backup;

import android.app.backup.BlobBackupHelper;
import android.content.ContentResolver;
import android.os.UserHandle;
import com.android.systemui.backup.BackupHelper;
import java.util.List;
import kotlin.collections.SetsKt__SetsKt;

/* compiled from: BackupHelperGoogle.kt */
public final class BackupHelperGoogle extends BackupHelper {
    public static final List<String> SECURE_SETTINGS_INT_KEYS = SetsKt__SetsKt.listOf("columbus_enabled", "columbus_low_sensitivity");
    public static final List<String> SECURE_SETTINGS_STRING_KEYS = SetsKt__SetsKt.listOf("columbus_action", "columbus_launch_app", "columbus_launch_app_shortcut");

    /* compiled from: BackupHelperGoogle.kt */
    public static final class SecureSettingsBackupHelper extends BlobBackupHelper {
        public final ContentResolver contentResolver;
        public final UserHandle userHandle;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public SecureSettingsBackupHelper(android.content.ContentResolver r7, android.os.UserHandle r8) {
            /*
                r6 = this;
                java.util.ArrayList r0 = new java.util.ArrayList
                r1 = 2
                r0.<init>(r1)
                java.util.List<java.lang.String> r1 = com.google.android.systemui.backup.BackupHelperGoogle.SECURE_SETTINGS_INT_KEYS
                r2 = 0
                java.lang.String[] r3 = new java.lang.String[r2]
                java.lang.Object[] r1 = r1.toArray(r3)
                java.lang.String r3 = "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>"
                java.util.Objects.requireNonNull(r1, r3)
                int r4 = r1.length
                if (r4 <= 0) goto L_0x0023
                int r4 = r0.size()
                int r5 = r1.length
                int r4 = r4 + r5
                r0.ensureCapacity(r4)
                java.util.Collections.addAll(r0, r1)
            L_0x0023:
                java.util.List<java.lang.String> r1 = com.google.android.systemui.backup.BackupHelperGoogle.SECURE_SETTINGS_STRING_KEYS
                java.lang.String[] r2 = new java.lang.String[r2]
                java.lang.Object[] r1 = r1.toArray(r2)
                java.util.Objects.requireNonNull(r1, r3)
                int r2 = r1.length
                if (r2 <= 0) goto L_0x003d
                int r2 = r0.size()
                int r3 = r1.length
                int r2 = r2 + r3
                r0.ensureCapacity(r2)
                java.util.Collections.addAll(r0, r1)
            L_0x003d:
                int r1 = r0.size()
                java.lang.String[] r1 = new java.lang.String[r1]
                java.lang.Object[] r0 = r0.toArray(r1)
                java.lang.String[] r0 = (java.lang.String[]) r0
                r1 = 1
                r6.<init>(r1, r0)
                r6.contentResolver = r7
                r6.userHandle = r8
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.backup.BackupHelperGoogle.SecureSettingsBackupHelper.<init>(android.content.ContentResolver, android.os.UserHandle):void");
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(4:18|19|20|50) */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0042, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
            android.util.Log.e("BackupHelper", kotlin.jvm.internal.Intrinsics.stringPlus("Failed to restore ", r6));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x004f, code lost:
            r0.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0052, code lost:
            throw r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x008e, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
            android.util.Log.e("BackupHelper", kotlin.jvm.internal.Intrinsics.stringPlus("Failed to restore ", r6));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:44:0x009b, code lost:
            r0.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:45:0x009e, code lost:
            throw r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:56:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0044 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x0090 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void applyRestoredPayload(java.lang.String r6, byte[] r7) {
            /*
                r5 = this;
                java.util.List<java.lang.String> r0 = com.google.android.systemui.backup.BackupHelperGoogle.SECURE_SETTINGS_INT_KEYS
                boolean r0 = kotlin.collections.CollectionsKt___CollectionsKt.contains(r0, r6)
                java.lang.String r1 = "Failed to restore "
                java.lang.String r2 = "BackupHelper"
                r3 = 0
                r4 = 1
                if (r0 == 0) goto L_0x0053
                if (r6 == 0) goto L_0x009f
                if (r7 == 0) goto L_0x009f
                int r0 = r6.length()
                if (r0 != 0) goto L_0x001a
                r0 = r4
                goto L_0x001b
            L_0x001a:
                r0 = r3
            L_0x001b:
                if (r0 != 0) goto L_0x009f
                int r0 = r7.length
                if (r0 != 0) goto L_0x0021
                r3 = r4
            L_0x0021:
                if (r3 == 0) goto L_0x0025
                goto L_0x009f
            L_0x0025:
                java.io.DataInputStream r0 = new java.io.DataInputStream
                java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream
                r3.<init>(r7)
                r0.<init>(r3)
                int r7 = r0.readInt()     // Catch:{ IOException -> 0x0044 }
                r0.close()
                android.content.ContentResolver r0 = r5.contentResolver
                android.os.UserHandle r5 = r5.userHandle
                int r5 = r5.getIdentifier()
                android.provider.Settings.Secure.putIntForUser(r0, r6, r7, r5)
                goto L_0x009f
            L_0x0042:
                r5 = move-exception
                goto L_0x004f
            L_0x0044:
                java.lang.String r5 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r6)     // Catch:{ all -> 0x0042 }
                android.util.Log.e(r2, r5)     // Catch:{ all -> 0x0042 }
                r0.close()
                goto L_0x009f
            L_0x004f:
                r0.close()
                throw r5
            L_0x0053:
                java.util.List<java.lang.String> r0 = com.google.android.systemui.backup.BackupHelperGoogle.SECURE_SETTINGS_STRING_KEYS
                boolean r0 = kotlin.collections.CollectionsKt___CollectionsKt.contains(r0, r6)
                if (r0 == 0) goto L_0x009f
                if (r6 == 0) goto L_0x009f
                if (r7 == 0) goto L_0x009f
                int r0 = r6.length()
                if (r0 != 0) goto L_0x0067
                r0 = r4
                goto L_0x0068
            L_0x0067:
                r0 = r3
            L_0x0068:
                if (r0 != 0) goto L_0x009f
                int r0 = r7.length
                if (r0 != 0) goto L_0x006e
                r3 = r4
            L_0x006e:
                if (r3 == 0) goto L_0x0071
                goto L_0x009f
            L_0x0071:
                java.io.DataInputStream r0 = new java.io.DataInputStream
                java.io.ByteArrayInputStream r3 = new java.io.ByteArrayInputStream
                r3.<init>(r7)
                r0.<init>(r3)
                java.lang.String r7 = r0.readUTF()     // Catch:{ IOException -> 0x0090 }
                r0.close()
                android.content.ContentResolver r0 = r5.contentResolver
                android.os.UserHandle r5 = r5.userHandle
                int r5 = r5.getIdentifier()
                android.provider.Settings.Secure.putStringForUser(r0, r6, r7, r5)
                goto L_0x009f
            L_0x008e:
                r5 = move-exception
                goto L_0x009b
            L_0x0090:
                java.lang.String r5 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r6)     // Catch:{ all -> 0x008e }
                android.util.Log.e(r2, r5)     // Catch:{ all -> 0x008e }
                r0.close()
                goto L_0x009f
            L_0x009b:
                r0.close()
                throw r5
            L_0x009f:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.backup.BackupHelperGoogle.SecureSettingsBackupHelper.applyRestoredPayload(java.lang.String, byte[]):void");
        }

        /* JADX INFO: finally extract failed */
        /* JADX WARNING: Can't wrap try/catch for region: R(2:9|10) */
        /* JADX WARNING: Code restructure failed: missing block: B:10:?, code lost:
            r4.close();
            android.util.Log.e("BackupHelper", kotlin.jvm.internal.Intrinsics.stringPlus("Failed to backup ", r6));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x003c, code lost:
            r4.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x003f, code lost:
            throw r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0069, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:?, code lost:
            android.util.Log.e("BackupHelper", kotlin.jvm.internal.Intrinsics.stringPlus("Failed to backup ", r6));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0076, code lost:
            r4.close();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0079, code lost:
            throw r5;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:8:0x002c, code lost:
            r5 = move-exception;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:22:0x006b */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x002e */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final byte[] getBackupPayload(java.lang.String r6) {
            /*
                r5 = this;
                java.util.List<java.lang.String> r0 = com.google.android.systemui.backup.BackupHelperGoogle.SECURE_SETTINGS_INT_KEYS
                boolean r0 = kotlin.collections.CollectionsKt___CollectionsKt.contains(r0, r6)
                java.lang.String r1 = "Failed to backup "
                java.lang.String r2 = "BackupHelper"
                r3 = 0
                if (r0 == 0) goto L_0x0040
                android.content.ContentResolver r0 = r5.contentResolver     // Catch:{ SettingNotFoundException -> 0x007a }
                android.os.UserHandle r5 = r5.userHandle     // Catch:{ SettingNotFoundException -> 0x007a }
                int r5 = r5.getIdentifier()     // Catch:{ SettingNotFoundException -> 0x007a }
                int r5 = android.provider.Settings.Secure.getIntForUser(r0, r6, r5)     // Catch:{ SettingNotFoundException -> 0x007a }
                java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
                r0.<init>()
                java.io.DataOutputStream r4 = new java.io.DataOutputStream
                r4.<init>(r0)
                r4.writeInt(r5)     // Catch:{ IOException -> 0x002e }
                byte[] r5 = r0.toByteArray()     // Catch:{ IOException -> 0x002e }
                r3 = r5
                goto L_0x0038
            L_0x002c:
                r5 = move-exception
                goto L_0x003c
            L_0x002e:
                r4.close()     // Catch:{ all -> 0x002c }
                java.lang.String r5 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r6)     // Catch:{ all -> 0x002c }
                android.util.Log.e(r2, r5)     // Catch:{ all -> 0x002c }
            L_0x0038:
                r4.close()
                goto L_0x007a
            L_0x003c:
                r4.close()
                throw r5
            L_0x0040:
                java.util.List<java.lang.String> r0 = com.google.android.systemui.backup.BackupHelperGoogle.SECURE_SETTINGS_STRING_KEYS
                boolean r0 = kotlin.collections.CollectionsKt___CollectionsKt.contains(r0, r6)
                if (r0 == 0) goto L_0x007a
                android.content.ContentResolver r0 = r5.contentResolver
                android.os.UserHandle r5 = r5.userHandle
                int r5 = r5.getIdentifier()
                java.lang.String r5 = android.provider.Settings.Secure.getStringForUser(r0, r6, r5)
                if (r5 != 0) goto L_0x0057
                goto L_0x007a
            L_0x0057:
                java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
                r0.<init>()
                java.io.DataOutputStream r4 = new java.io.DataOutputStream
                r4.<init>(r0)
                r4.writeUTF(r5)     // Catch:{ IOException -> 0x006b }
                byte[] r3 = r0.toByteArray()     // Catch:{ IOException -> 0x006b }
                goto L_0x0072
            L_0x0069:
                r5 = move-exception
                goto L_0x0076
            L_0x006b:
                java.lang.String r5 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r6)     // Catch:{ all -> 0x0069 }
                android.util.Log.e(r2, r5)     // Catch:{ all -> 0x0069 }
            L_0x0072:
                r4.close()
                goto L_0x007a
            L_0x0076:
                r4.close()
                throw r5
            L_0x007a:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.backup.BackupHelperGoogle.SecureSettingsBackupHelper.getBackupPayload(java.lang.String):byte[]");
        }
    }

    /* JADX WARNING: type inference failed for: r3v1, types: [android.app.backup.BackupHelper, com.google.android.systemui.backup.BackupHelperGoogle$SecureSettingsBackupHelper] */
    public final void onCreate(UserHandle userHandle, int i) {
        super.onCreate(userHandle, i);
        addHelper("systemui.google.secure_settings_backup", new SecureSettingsBackupHelper(getContentResolver(), userHandle));
    }
}
