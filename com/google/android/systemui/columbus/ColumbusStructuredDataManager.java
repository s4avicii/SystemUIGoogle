package com.google.android.systemui.columbus;

import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.settings.UserTracker;
import com.google.android.setupcompat.partnerconfig.ResourceEntry;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.Executor;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ColumbusStructuredDataManager.kt */
public final class ColumbusStructuredDataManager {
    public final Set<String> allowPackageList;
    public final Executor bgExecutor;
    public final ContentResolver contentResolver;
    public final Object lock = new Object();
    public JSONArray packageStats;
    public final UserTracker userTracker;

    public static JSONObject makeJSONObject$default(ColumbusStructuredDataManager columbusStructuredDataManager, String str, int i, long j, int i2) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            j = 0;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(ResourceEntry.KEY_PACKAGE_NAME, str);
        jSONObject.put("shownCount", i);
        jSONObject.put("lastDeny", j);
        return jSONObject;
    }

    public final JSONArray fetchPackageStats() {
        JSONArray jSONArray;
        synchronized (this.lock) {
            String stringForUser = Settings.Secure.getStringForUser(this.contentResolver, "columbus_package_stats", this.userTracker.getUserId());
            if (stringForUser == null) {
                stringForUser = "[]";
            }
            try {
                jSONArray = new JSONArray(stringForUser);
            } catch (JSONException e) {
                Log.e("Columbus/StructuredData", "Failed to parse package counts", e);
                jSONArray = new JSONArray();
            }
        }
        return jSONArray;
    }

    public final long getLastDenyTimestamp(String str) {
        synchronized (this.lock) {
            int length = this.packageStats.length();
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                JSONObject jSONObject = this.packageStats.getJSONObject(i);
                if (Intrinsics.areEqual(str, jSONObject.getString(ResourceEntry.KEY_PACKAGE_NAME))) {
                    long j = jSONObject.getLong("lastDeny");
                    return j;
                }
                i = i2;
            }
            return 0;
        }
    }

    public final int getPackageShownCount(String str) {
        synchronized (this.lock) {
            int length = this.packageStats.length();
            int i = 0;
            while (i < length) {
                int i2 = i + 1;
                JSONObject jSONObject = this.packageStats.getJSONObject(i);
                if (Intrinsics.areEqual(str, jSONObject.getString(ResourceEntry.KEY_PACKAGE_NAME))) {
                    int i3 = jSONObject.getInt("shownCount");
                    return i3;
                }
                i = i2;
            }
            return 0;
        }
    }

    public final void storePackageStats() {
        synchronized (this.lock) {
            Settings.Secure.putStringForUser(this.contentResolver, "columbus_package_stats", this.packageStats.toString(), this.userTracker.getUserId());
        }
    }

    public ColumbusStructuredDataManager(Context context, UserTracker userTracker2, Executor executor) {
        this.userTracker = userTracker2;
        this.bgExecutor = executor;
        this.contentResolver = context.getContentResolver();
        String[] stringArray = context.getResources().getStringArray(C1777R.array.columbus_sumatra_package_allow_list);
        this.allowPackageList = SetsKt__SetsKt.setOf(Arrays.copyOf(stringArray, stringArray.length));
        ColumbusStructuredDataManager$userTrackerCallback$1 columbusStructuredDataManager$userTrackerCallback$1 = new ColumbusStructuredDataManager$userTrackerCallback$1(this);
        ColumbusStructuredDataManager$broadcastReceiver$1 columbusStructuredDataManager$broadcastReceiver$1 = new ColumbusStructuredDataManager$broadcastReceiver$1(this);
        this.packageStats = fetchPackageStats();
        userTracker2.addCallback(columbusStructuredDataManager$userTrackerCallback$1, executor);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        context.registerReceiver(columbusStructuredDataManager$broadcastReceiver$1, intentFilter);
    }
}
