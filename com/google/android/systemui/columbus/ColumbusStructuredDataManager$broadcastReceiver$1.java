package com.google.android.systemui.columbus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.setupcompat.partnerconfig.ResourceEntry;
import java.util.List;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: ColumbusStructuredDataManager.kt */
public final class ColumbusStructuredDataManager$broadcastReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ ColumbusStructuredDataManager this$0;

    public ColumbusStructuredDataManager$broadcastReceiver$1(ColumbusStructuredDataManager columbusStructuredDataManager) {
        this.this$0 = columbusStructuredDataManager;
    }

    public final void onReceive(Context context, Intent intent) {
        List list;
        String dataString;
        if (intent == null || (dataString = intent.getDataString()) == null) {
            list = null;
        } else {
            list = StringsKt__StringsKt.split$default(dataString, new String[]{":"});
        }
        List list2 = list;
        if (list2 != null) {
            if (list2.size() != 2) {
                Log.e("Columbus/StructuredData", Intrinsics.stringPlus("Unexpected package name tokens: ", CollectionsKt___CollectionsKt.joinToString$default(list2, ",", (String) null, (String) null, (Function1) null, 62)));
                return;
            }
            String str = (String) list2.get(1);
            int i = 0;
            if (!intent.getBooleanExtra("android.intent.extra.REPLACING", false) && this.this$0.allowPackageList.contains(str)) {
                ColumbusStructuredDataManager columbusStructuredDataManager = this.this$0;
                Objects.requireNonNull(columbusStructuredDataManager);
                synchronized (columbusStructuredDataManager.lock) {
                    int length = columbusStructuredDataManager.packageStats.length();
                    while (i < length) {
                        int i2 = i + 1;
                        if (Intrinsics.areEqual(str, columbusStructuredDataManager.packageStats.getJSONObject(i).getString(ResourceEntry.KEY_PACKAGE_NAME))) {
                            columbusStructuredDataManager.packageStats.remove(i);
                            columbusStructuredDataManager.storePackageStats();
                            return;
                        }
                        i = i2;
                    }
                }
            }
        }
    }
}
