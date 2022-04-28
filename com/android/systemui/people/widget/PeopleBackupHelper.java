package com.android.systemui.people.widget;

import android.app.backup.BackupDataOutput;
import android.app.backup.SharedPreferencesBackupHelper;
import android.app.people.IPeopleManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.ParcelFileDescriptor;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;

public final class PeopleBackupHelper extends SharedPreferencesBackupHelper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AppWidgetManager mAppWidgetManager;
    public final Context mContext;
    public final IPeopleManager mIPeopleManager;
    public final PackageManager mPackageManager;
    public final UserHandle mUserHandle;

    public enum SharedFileEntryType {
        UNKNOWN,
        WIDGET_ID,
        PEOPLE_TILE_KEY,
        CONTACT_URI
    }

    public PeopleBackupHelper(Context context, UserHandle userHandle, String[] strArr) {
        super(context, strArr);
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mPackageManager = context.getPackageManager();
        this.mIPeopleManager = IPeopleManager.Stub.asInterface(ServiceManager.getService("people"));
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(4:13|14|15|(2:17|18)(3:19|20|21)) */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        r4 = (java.util.Set) r6.getValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003c, code lost:
        if (com.android.systemui.people.widget.PeopleTileKey.fromString(r3) != null) goto L_0x003e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0040, code lost:
        return com.android.systemui.people.widget.PeopleBackupHelper.SharedFileEntryType.PEOPLE_TILE_KEY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        android.net.Uri.parse(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0046, code lost:
        return com.android.systemui.people.widget.PeopleBackupHelper.SharedFileEntryType.CONTACT_URI;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0047, code lost:
        return r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0048, code lost:
        r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m("Malformed value, skipping:");
        r0.append(r6.getValue());
        android.util.Log.w("PeopleBackupHelper", r0.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x005a, code lost:
        return r2;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0032 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.android.systemui.people.widget.PeopleBackupHelper.SharedFileEntryType getEntryType(java.util.Map.Entry<java.lang.String, ?> r6) {
        /*
            java.lang.String r0 = "Malformed value, skipping:"
            java.lang.String r1 = "PeopleBackupHelper"
            com.android.systemui.people.widget.PeopleBackupHelper$SharedFileEntryType r2 = com.android.systemui.people.widget.PeopleBackupHelper.SharedFileEntryType.UNKNOWN
            java.lang.Object r3 = r6.getKey()
            java.lang.String r3 = (java.lang.String) r3
            if (r3 != 0) goto L_0x000f
            return r2
        L_0x000f:
            java.lang.Integer.parseInt(r3)     // Catch:{ NumberFormatException -> 0x0032 }
            java.lang.Object r4 = r6.getValue()     // Catch:{ Exception -> 0x001b }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x001b }
            com.android.systemui.people.widget.PeopleBackupHelper$SharedFileEntryType r6 = com.android.systemui.people.widget.PeopleBackupHelper.SharedFileEntryType.WIDGET_ID     // Catch:{ NumberFormatException -> 0x0032 }
            return r6
        L_0x001b:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ NumberFormatException -> 0x0032 }
            r4.<init>()     // Catch:{ NumberFormatException -> 0x0032 }
            r4.append(r0)     // Catch:{ NumberFormatException -> 0x0032 }
            java.lang.Object r5 = r6.getValue()     // Catch:{ NumberFormatException -> 0x0032 }
            r4.append(r5)     // Catch:{ NumberFormatException -> 0x0032 }
            java.lang.String r4 = r4.toString()     // Catch:{ NumberFormatException -> 0x0032 }
            android.util.Log.w(r1, r4)     // Catch:{ NumberFormatException -> 0x0032 }
            return r2
        L_0x0032:
            java.lang.Object r4 = r6.getValue()     // Catch:{ Exception -> 0x0048 }
            java.util.Set r4 = (java.util.Set) r4     // Catch:{ Exception -> 0x0048 }
            com.android.systemui.people.widget.PeopleTileKey r6 = com.android.systemui.people.widget.PeopleTileKey.fromString(r3)
            if (r6 == 0) goto L_0x0041
            com.android.systemui.people.widget.PeopleBackupHelper$SharedFileEntryType r6 = com.android.systemui.people.widget.PeopleBackupHelper.SharedFileEntryType.PEOPLE_TILE_KEY
            return r6
        L_0x0041:
            android.net.Uri.parse(r3)     // Catch:{ Exception -> 0x0047 }
            com.android.systemui.people.widget.PeopleBackupHelper$SharedFileEntryType r6 = com.android.systemui.people.widget.PeopleBackupHelper.SharedFileEntryType.CONTACT_URI     // Catch:{ Exception -> 0x0047 }
            return r6
        L_0x0047:
            return r2
        L_0x0048:
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.Object r6 = r6.getValue()
            r0.append(r6)
            java.lang.String r6 = r0.toString()
            android.util.Log.w(r1, r6)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleBackupHelper.getEntryType(java.util.Map$Entry):com.android.systemui.people.widget.PeopleBackupHelper$SharedFileEntryType");
    }

    public final void performBackup(ParcelFileDescriptor parcelFileDescriptor, BackupDataOutput backupDataOutput, ParcelFileDescriptor parcelFileDescriptor2) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mContext);
        if (!defaultSharedPreferences.getAll().isEmpty()) {
            SharedPreferences.Editor edit = this.mContext.getSharedPreferences("shared_backup", 0).edit();
            edit.clear();
            int identifier = this.mUserHandle.getIdentifier();
            ArrayList arrayList = new ArrayList();
            for (int valueOf : this.mAppWidgetManager.getAppWidgetIds(new ComponentName(this.mContext, PeopleSpaceWidgetProvider.class))) {
                String valueOf2 = String.valueOf(valueOf);
                if (this.mContext.getSharedPreferences(valueOf2, 0).getInt("user_id", -1) == identifier) {
                    arrayList.add(valueOf2);
                }
            }
            if (!arrayList.isEmpty()) {
                defaultSharedPreferences.getAll().entrySet().forEach(new PeopleBackupHelper$$ExternalSyntheticLambda0(this, edit, arrayList));
                edit.apply();
                super.performBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor2);
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isReadyForRestore(android.app.people.IPeopleManager r3, android.content.pm.PackageManager r4, com.android.systemui.people.widget.PeopleTileKey r5) {
        /*
            boolean r0 = com.android.systemui.people.widget.PeopleTileKey.isValid(r5)
            if (r0 != 0) goto L_0x0008
            r3 = 1
            return r3
        L_0x0008:
            r0 = 0
            java.util.Objects.requireNonNull(r5)     // Catch:{ NameNotFoundException -> 0x001e }
            java.lang.String r1 = r5.mPackageName     // Catch:{ NameNotFoundException -> 0x001e }
            int r2 = r5.mUserId     // Catch:{ NameNotFoundException -> 0x001e }
            r4.getPackageInfoAsUser(r1, r0, r2)     // Catch:{ NameNotFoundException -> 0x001e }
            java.lang.String r4 = r5.mPackageName     // Catch:{  }
            int r1 = r5.mUserId     // Catch:{  }
            java.lang.String r5 = r5.mShortcutId     // Catch:{  }
            boolean r3 = r3.isConversation(r4, r1, r5)     // Catch:{  }
            return r3
        L_0x001e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleBackupHelper.isReadyForRestore(android.app.people.IPeopleManager, android.content.pm.PackageManager, com.android.systemui.people.widget.PeopleTileKey):boolean");
    }

    public static void updateWidgets(Context context) {
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(new ComponentName(context, PeopleSpaceWidgetProvider.class));
        if (appWidgetIds != null && appWidgetIds.length != 0) {
            Intent intent = new Intent(context, PeopleSpaceWidgetProvider.class);
            intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
            intent.putExtra("appWidgetIds", appWidgetIds);
            context.sendBroadcast(intent);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0031 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void restoreEntity(android.app.backup.BackupDataInputStream r13) {
        /*
            r12 = this;
            super.restoreEntity(r13)
            android.content.Context r13 = r12.mContext
            java.lang.String r0 = "shared_backup"
            r1 = 0
            android.content.SharedPreferences r13 = r13.getSharedPreferences(r0, r1)
            android.content.Context r0 = r12.mContext
            android.content.SharedPreferences r0 = android.preference.PreferenceManager.getDefaultSharedPreferences(r0)
            android.content.SharedPreferences$Editor r0 = r0.edit()
            android.content.Context r2 = r12.mContext
            java.lang.String r3 = "shared_follow_up"
            android.content.SharedPreferences r2 = r2.getSharedPreferences(r3, r1)
            android.content.SharedPreferences$Editor r2 = r2.edit()
            java.util.Map r3 = r13.getAll()
            java.util.Set r3 = r3.entrySet()
            java.util.Iterator r3 = r3.iterator()
            r4 = r1
        L_0x0031:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x010f
            java.lang.Object r5 = r3.next()
            java.util.Map$Entry r5 = (java.util.Map.Entry) r5
            java.lang.Object r6 = r5.getKey()
            java.lang.String r6 = (java.lang.String) r6
            com.android.systemui.people.widget.PeopleBackupHelper$SharedFileEntryType r7 = getEntryType(r5)
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "add_user_id_to_uri_"
            r8.append(r9)
            r8.append(r6)
            java.lang.String r8 = r8.toString()
            r9 = -1
            int r8 = r13.getInt(r8, r9)
            int r7 = r7.ordinal()
            r10 = 1
            if (r7 == r10) goto L_0x00ec
            r11 = 2
            if (r7 == r11) goto L_0x009e
            r11 = 3
            if (r7 == r11) goto L_0x0082
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r7 = "Key not identified, skipping:"
            r5.append(r7)
            r5.append(r6)
            java.lang.String r5 = r5.toString()
            java.lang.String r6 = "PeopleBackupHelper"
            android.util.Log.e(r6, r5)
            goto L_0x0109
        L_0x0082:
            java.lang.Object r5 = r5.getValue()
            java.util.Set r5 = (java.util.Set) r5
            android.net.Uri r6 = android.net.Uri.parse(r6)
            if (r8 == r9) goto L_0x0096
            android.os.UserHandle r7 = android.os.UserHandle.of(r8)
            android.net.Uri r6 = android.content.ContentProvider.createContentUriForUser(r6, r7)
        L_0x0096:
            java.lang.String r6 = r6.toString()
            r0.putStringSet(r6, r5)
            goto L_0x0109
        L_0x009e:
            java.lang.Object r5 = r5.getValue()
            java.util.Set r5 = (java.util.Set) r5
            com.android.systemui.people.widget.PeopleTileKey r6 = com.android.systemui.people.widget.PeopleTileKey.fromString(r6)
            if (r6 != 0) goto L_0x00ab
            goto L_0x0109
        L_0x00ab:
            android.os.UserHandle r7 = r12.mUserHandle
            int r7 = r7.getIdentifier()
            r6.mUserId = r7
            boolean r7 = com.android.systemui.people.widget.PeopleTileKey.isValid(r6)
            if (r7 != 0) goto L_0x00ba
            goto L_0x0109
        L_0x00ba:
            android.app.people.IPeopleManager r7 = r12.mIPeopleManager
            android.content.pm.PackageManager r8 = r12.mPackageManager
            boolean r7 = isReadyForRestore(r7, r8, r6)
            if (r7 != 0) goto L_0x00cb
            java.lang.String r8 = r6.toString()
            r2.putStringSet(r8, r5)
        L_0x00cb:
            java.lang.String r8 = r6.toString()
            r0.putStringSet(r8, r5)
            android.content.Context r8 = r12.mContext
            java.util.Iterator r5 = r5.iterator()
        L_0x00d8:
            boolean r9 = r5.hasNext()
            if (r9 == 0) goto L_0x010a
            java.lang.Object r9 = r5.next()
            java.lang.String r9 = (java.lang.String) r9
            android.content.SharedPreferences r9 = r8.getSharedPreferences(r9, r1)
            com.android.systemui.people.SharedPreferencesHelper.setPeopleTileKey(r9, r6)
            goto L_0x00d8
        L_0x00ec:
            java.lang.Object r5 = r5.getValue()
            java.lang.String r5 = java.lang.String.valueOf(r5)
            android.net.Uri r5 = android.net.Uri.parse(r5)
            if (r8 == r9) goto L_0x0102
            android.os.UserHandle r7 = android.os.UserHandle.of(r8)
            android.net.Uri r5 = android.content.ContentProvider.createContentUriForUser(r5, r7)
        L_0x0102:
            java.lang.String r5 = r5.toString()
            r0.putString(r6, r5)
        L_0x0109:
            r7 = r10
        L_0x010a:
            if (r7 != 0) goto L_0x0031
            r4 = r10
            goto L_0x0031
        L_0x010f:
            r0.apply()
            r2.apply()
            android.content.SharedPreferences$Editor r13 = r13.edit()
            r13.clear()
            r13.apply()
            if (r4 == 0) goto L_0x015c
            android.content.Context r13 = r12.mContext
            int r0 = com.android.systemui.people.PeopleBackupFollowUpJob.$r8$clinit
            java.lang.Class<android.app.job.JobScheduler> r0 = android.app.job.JobScheduler.class
            java.lang.Object r0 = r13.getSystemService(r0)
            android.app.job.JobScheduler r0 = (android.app.job.JobScheduler) r0
            android.os.PersistableBundle r1 = new android.os.PersistableBundle
            r1.<init>()
            long r2 = java.lang.System.currentTimeMillis()
            java.lang.String r4 = "start_date"
            r1.putLong(r4, r2)
            android.app.job.JobInfo$Builder r2 = new android.app.job.JobInfo$Builder
            android.content.ComponentName r3 = new android.content.ComponentName
            java.lang.Class<com.android.systemui.people.PeopleBackupFollowUpJob> r4 = com.android.systemui.people.PeopleBackupFollowUpJob.class
            r3.<init>(r13, r4)
            r13 = 74823873(0x475b8c1, float:2.8884446E-36)
            r2.<init>(r13, r3)
            long r3 = com.android.systemui.people.PeopleBackupFollowUpJob.JOB_PERIODIC_DURATION
            android.app.job.JobInfo$Builder r13 = r2.setPeriodic(r3)
            android.app.job.JobInfo$Builder r13 = r13.setExtras(r1)
            android.app.job.JobInfo r13 = r13.build()
            r0.schedule(r13)
        L_0x015c:
            android.content.Context r12 = r12.mContext
            updateWidgets(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.widget.PeopleBackupHelper.restoreEntity(android.app.backup.BackupDataInputStream):void");
    }

    @VisibleForTesting
    public PeopleBackupHelper(Context context, UserHandle userHandle, String[] strArr, PackageManager packageManager, IPeopleManager iPeopleManager) {
        super(context, strArr);
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mPackageManager = packageManager;
        this.mIPeopleManager = iPeopleManager;
        this.mAppWidgetManager = AppWidgetManager.getInstance(context);
    }
}
