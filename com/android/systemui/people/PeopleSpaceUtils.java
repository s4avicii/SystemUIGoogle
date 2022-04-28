package com.android.systemui.people;

import android.app.backup.BackupManager;
import android.app.people.IPeopleManager;
import android.app.people.PeopleSpaceTile;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.Log;
import androidx.preference.PreferenceManager;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class PeopleSpaceUtils {
    public static final PeopleTileKey EMPTY_KEY = new PeopleTileKey("", -1, "");

    public enum NotificationAction {
        POSTED,
        REMOVED
    }

    public enum PeopleSpaceWidgetEvent implements UiEventLogger.UiEventEnum {
        PEOPLE_SPACE_WIDGET_DELETED(666),
        PEOPLE_SPACE_WIDGET_ADDED(667),
        PEOPLE_SPACE_WIDGET_CLICKED(668);
        
        private final int mId;

        /* access modifiers changed from: public */
        PeopleSpaceWidgetEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    public static Bitmap convertDrawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }
        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x006d, code lost:
        if (r1 == null) goto L_0x0070;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0070, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x004e, code lost:
        if (r1 != null) goto L_0x0050;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x0050, code lost:
        r1.close();
     */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<java.lang.String> getContactLookupKeysWithBirthdaysToday(android.content.Context r11) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = 1
            r0.<init>(r1)
            java.text.SimpleDateFormat r2 = new java.text.SimpleDateFormat
            java.lang.String r3 = "MM-dd"
            r2.<init>(r3)
            java.util.Date r3 = new java.util.Date
            r3.<init>()
            java.lang.String r2 = r2.format(r3)
            java.lang.String r3 = "lookup"
            java.lang.String r4 = "data1"
            java.lang.String[] r7 = new java.lang.String[]{r3, r4}
            java.lang.String r8 = "mimetype= ? AND data2=3 AND (substr(data1,6) = ? OR substr(data1,3) = ? )"
            r4 = 3
            java.lang.String[] r9 = new java.lang.String[r4]
            r4 = 0
            java.lang.String r5 = "vnd.android.cursor.item/contact_event"
            r9[r4] = r5
            r9[r1] = r2
            r1 = 2
            r9[r1] = r2
            r1 = 0
            android.content.ContentResolver r5 = r11.getContentResolver()     // Catch:{ SQLException -> 0x0056 }
            android.net.Uri r6 = android.provider.ContactsContract.Data.CONTENT_URI     // Catch:{ SQLException -> 0x0056 }
            r10 = 0
            android.database.Cursor r1 = r5.query(r6, r7, r8, r9, r10)     // Catch:{ SQLException -> 0x0056 }
        L_0x003a:
            if (r1 == 0) goto L_0x004e
            boolean r11 = r1.moveToNext()     // Catch:{ SQLException -> 0x0056 }
            if (r11 == 0) goto L_0x004e
            int r11 = r1.getColumnIndex(r3)     // Catch:{ SQLException -> 0x0056 }
            java.lang.String r11 = r1.getString(r11)     // Catch:{ SQLException -> 0x0056 }
            r0.add(r11)     // Catch:{ SQLException -> 0x0056 }
            goto L_0x003a
        L_0x004e:
            if (r1 == 0) goto L_0x0070
        L_0x0050:
            r1.close()
            goto L_0x0070
        L_0x0054:
            r11 = move-exception
            goto L_0x0071
        L_0x0056:
            r11 = move-exception
            java.lang.String r2 = "PeopleSpaceUtils"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0054 }
            r3.<init>()     // Catch:{ all -> 0x0054 }
            java.lang.String r4 = "Failed to query birthdays: "
            r3.append(r4)     // Catch:{ all -> 0x0054 }
            r3.append(r11)     // Catch:{ all -> 0x0054 }
            java.lang.String r11 = r3.toString()     // Catch:{ all -> 0x0054 }
            android.util.Log.e(r2, r11)     // Catch:{ all -> 0x0054 }
            if (r1 == 0) goto L_0x0070
            goto L_0x0050
        L_0x0070:
            return r0
        L_0x0071:
            if (r1 == 0) goto L_0x0076
            r1.close()
        L_0x0076:
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleSpaceUtils.getContactLookupKeysWithBirthdaysToday(android.content.Context):java.util.List");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x009c, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x009d, code lost:
        r6 = r14;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x009f, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00a0, code lost:
        r15 = r20;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00c6, code lost:
        r14 = r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00cd, code lost:
        r6.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x008d A[Catch:{ SQLException -> 0x009a, all -> 0x009c }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009c A[ExcHandler: all (th java.lang.Throwable), Splitter:B:13:0x003f] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00c6  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00cd  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00de A[SYNTHETIC] */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void getDataFromContacts(android.content.Context r20, com.android.systemui.people.widget.PeopleSpaceWidgetManager r21, java.util.Map<java.lang.Integer, android.app.people.PeopleSpaceTile> r22, int[] r23) {
        /*
            r1 = r23
            int r0 = r1.length
            if (r0 != 0) goto L_0x0006
            return
        L_0x0006:
            java.util.List r2 = getContactLookupKeysWithBirthdaysToday(r20)
            int r3 = r1.length
            r4 = 0
            r5 = r4
        L_0x000d:
            if (r5 >= r3) goto L_0x00e2
            r0 = r1[r5]
            java.lang.Integer r6 = java.lang.Integer.valueOf(r0)
            r12 = r22
            java.lang.Object r6 = r12.get(r6)
            r13 = r6
            android.app.people.PeopleSpaceTile r13 = (android.app.people.PeopleSpaceTile) r13
            if (r13 == 0) goto L_0x00d1
            android.net.Uri r6 = r13.getContactUri()
            if (r6 != 0) goto L_0x0028
            goto L_0x00d1
        L_0x0028:
            r6 = 0
            android.content.ContentResolver r14 = r20.getContentResolver()     // Catch:{ SQLException -> 0x00ab }
            android.net.Uri r15 = r13.getContactUri()     // Catch:{ SQLException -> 0x00ab }
            r16 = 0
            r17 = 0
            r18 = 0
            r19 = 0
            android.database.Cursor r14 = r14.query(r15, r16, r17, r18, r19)     // Catch:{ SQLException -> 0x00ab }
        L_0x003d:
            if (r14 == 0) goto L_0x00a4
            boolean r6 = r14.moveToNext()     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            if (r6 == 0) goto L_0x00a4
            java.lang.String r6 = "lookup"
            int r6 = r14.getColumnIndex(r6)     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            java.lang.String r6 = r14.getString(r6)     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            java.lang.String r7 = "starred"
            int r7 = r14.getColumnIndex(r7)     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            r8 = 1056964608(0x3f000000, float:0.5)
            if (r7 < 0) goto L_0x006d
            int r7 = r14.getInt(r7)     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            if (r7 == 0) goto L_0x0062
            r7 = 1
            goto L_0x0063
        L_0x0062:
            r7 = r4
        L_0x0063:
            if (r7 == 0) goto L_0x006d
            r7 = 1065353216(0x3f800000, float:1.0)
            float r7 = java.lang.Math.max(r8, r7)     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            r10 = r7
            goto L_0x006e
        L_0x006d:
            r10 = r8
        L_0x006e:
            boolean r7 = r6.isEmpty()     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            if (r7 != 0) goto L_0x008d
            boolean r6 = r2.contains(r6)     // Catch:{ SQLException -> 0x009f, all -> 0x009c }
            if (r6 == 0) goto L_0x008d
            r6 = 2131951955(0x7f130153, float:1.954034E38)
            r15 = r20
            java.lang.String r11 = r15.getString(r6)     // Catch:{ SQLException -> 0x009a, all -> 0x009c }
            r6 = r21
            r7 = r20
            r8 = r13
            r9 = r0
            updateTileContactFields(r6, r7, r8, r9, r10, r11)     // Catch:{ SQLException -> 0x009a, all -> 0x009c }
            goto L_0x003d
        L_0x008d:
            r15 = r20
            r11 = 0
            r6 = r21
            r7 = r20
            r8 = r13
            r9 = r0
            updateTileContactFields(r6, r7, r8, r9, r10, r11)     // Catch:{ SQLException -> 0x009a, all -> 0x009c }
            goto L_0x003d
        L_0x009a:
            r0 = move-exception
            goto L_0x00a2
        L_0x009c:
            r0 = move-exception
            r6 = r14
            goto L_0x00cb
        L_0x009f:
            r0 = move-exception
            r15 = r20
        L_0x00a2:
            r6 = r14
            goto L_0x00ae
        L_0x00a4:
            r15 = r20
            if (r14 == 0) goto L_0x00de
            goto L_0x00c7
        L_0x00a9:
            r0 = move-exception
            goto L_0x00cb
        L_0x00ab:
            r0 = move-exception
            r15 = r20
        L_0x00ae:
            java.lang.String r7 = "PeopleSpaceUtils"
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a9 }
            r8.<init>()     // Catch:{ all -> 0x00a9 }
            java.lang.String r9 = "Failed to query contact: "
            r8.append(r9)     // Catch:{ all -> 0x00a9 }
            r8.append(r0)     // Catch:{ all -> 0x00a9 }
            java.lang.String r0 = r8.toString()     // Catch:{ all -> 0x00a9 }
            android.util.Log.e(r7, r0)     // Catch:{ all -> 0x00a9 }
            if (r6 == 0) goto L_0x00de
            r14 = r6
        L_0x00c7:
            r14.close()
            goto L_0x00de
        L_0x00cb:
            if (r6 == 0) goto L_0x00d0
            r6.close()
        L_0x00d0:
            throw r0
        L_0x00d1:
            r15 = r20
            r10 = 0
            r11 = 0
            r6 = r21
            r7 = r20
            r8 = r13
            r9 = r0
            updateTileContactFields(r6, r7, r8, r9, r10, r11)
        L_0x00de:
            int r5 = r5 + 1
            goto L_0x000d
        L_0x00e2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleSpaceUtils.getDataFromContacts(android.content.Context, com.android.systemui.people.widget.PeopleSpaceWidgetManager, java.util.Map, int[]):void");
    }

    public static List<PeopleSpaceTile> getSortedTiles(IPeopleManager iPeopleManager, LauncherApps launcherApps, UserManager userManager, Stream<ShortcutInfo> stream) {
        return (List) stream.filter(PeopleSpaceUtils$$ExternalSyntheticLambda7.INSTANCE).filter(new PeopleSpaceUtils$$ExternalSyntheticLambda6(userManager)).map(new PeopleSpaceUtils$$ExternalSyntheticLambda3(launcherApps)).filter(PeopleSpaceUtils$$ExternalSyntheticLambda8.INSTANCE).map(new PeopleSpaceUtils$$ExternalSyntheticLambda2(iPeopleManager, 0)).sorted(PeopleSpaceUtils$$ExternalSyntheticLambda1.INSTANCE).collect(Collectors.toList());
    }

    public static PeopleSpaceTile removeNotificationFields(PeopleSpaceTile peopleSpaceTile) {
        PeopleSpaceTile.Builder notificationCategory = peopleSpaceTile.toBuilder().setNotificationKey((String) null).setNotificationContent((CharSequence) null).setNotificationSender((CharSequence) null).setNotificationDataUri((Uri) null).setMessagesCount(0).setNotificationCategory((String) null);
        if (!TextUtils.isEmpty(peopleSpaceTile.getNotificationKey())) {
            notificationCategory.setLastInteractionTimestamp(System.currentTimeMillis());
        }
        return notificationCategory.build();
    }

    public static void removeSharedPreferencesStorageForTile(Context context, PeopleTileKey peopleTileKey, int i, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceManager.getDefaultSharedPreferencesName(context), 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.remove(String.valueOf(i));
        String peopleTileKey2 = peopleTileKey.toString();
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(peopleTileKey2, new HashSet()));
        hashSet.remove(String.valueOf(i));
        edit.putStringSet(peopleTileKey2, hashSet);
        HashSet hashSet2 = new HashSet(sharedPreferences.getStringSet(str, new HashSet()));
        hashSet2.remove(String.valueOf(i));
        edit.putStringSet(str, hashSet2);
        edit.apply();
        SharedPreferences.Editor edit2 = context.getSharedPreferences(String.valueOf(i), 0).edit();
        edit2.remove("package_name");
        edit2.remove("user_id");
        edit2.remove("shortcut_id");
        edit2.apply();
    }

    public static void setSharedPreferencesStorageForTile(Context context, PeopleTileKey peopleTileKey, int i, Uri uri, BackupManager backupManager) {
        String str;
        if (!PeopleTileKey.isValid(peopleTileKey)) {
            Log.e("PeopleSpaceUtils", "Not storing for invalid key");
            return;
        }
        SharedPreferencesHelper.setPeopleTileKey(context.getSharedPreferences(String.valueOf(i), 0), peopleTileKey);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PreferenceManager.getDefaultSharedPreferencesName(context), 0);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        if (uri == null) {
            str = "";
        } else {
            str = uri.toString();
        }
        edit.putString(String.valueOf(i), str);
        String peopleTileKey2 = peopleTileKey.toString();
        HashSet hashSet = new HashSet(sharedPreferences.getStringSet(peopleTileKey2, new HashSet()));
        hashSet.add(String.valueOf(i));
        edit.putStringSet(peopleTileKey2, hashSet);
        if (!TextUtils.isEmpty(str)) {
            HashSet hashSet2 = new HashSet(sharedPreferences.getStringSet(str, new HashSet()));
            hashSet2.add(String.valueOf(i));
            edit.putStringSet(str, hashSet2);
        }
        edit.apply();
        backupManager.dataChanged();
    }

    public static void updateTileContactFields(PeopleSpaceWidgetManager peopleSpaceWidgetManager, Context context, PeopleSpaceTile peopleSpaceTile, int i, float f, String str) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5 = true;
        if (peopleSpaceTile.getBirthdayText() == null || !peopleSpaceTile.getBirthdayText().equals(context.getString(C1777R.string.birthday_status))) {
            z = false;
        } else {
            z = true;
        }
        if (!z || str != null) {
            z2 = false;
        } else {
            z2 = true;
        }
        if (peopleSpaceTile.getBirthdayText() == null || !peopleSpaceTile.getBirthdayText().equals(context.getString(C1777R.string.birthday_status))) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (z3 || str == null) {
            z4 = false;
        } else {
            z4 = true;
        }
        if (peopleSpaceTile.getContactAffinity() == f && !z2 && !z4) {
            z5 = false;
        }
        if (z5) {
            peopleSpaceWidgetManager.updateAppWidgetOptionsAndView(i, peopleSpaceTile.toBuilder().setBirthdayText(str).setContactAffinity(f).build());
        }
    }
}
