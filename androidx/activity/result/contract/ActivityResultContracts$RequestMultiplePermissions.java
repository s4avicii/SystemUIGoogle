package androidx.activity.result.contract;

import android.content.Intent;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ActivityResultContracts$RequestMultiplePermissions extends ActivityResultContract<String[], Map<String, Boolean>> {
    public final Object parseResult(int i, Intent intent) {
        boolean z;
        if (i != -1) {
            return Collections.emptyMap();
        }
        if (intent == null) {
            return Collections.emptyMap();
        }
        String[] stringArrayExtra = intent.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
        int[] intArrayExtra = intent.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS");
        if (intArrayExtra == null || stringArrayExtra == null) {
            return Collections.emptyMap();
        }
        HashMap hashMap = new HashMap();
        int length = stringArrayExtra.length;
        for (int i2 = 0; i2 < length; i2++) {
            String str = stringArrayExtra[i2];
            if (intArrayExtra[i2] == 0) {
                z = true;
            } else {
                z = false;
            }
            hashMap.put(str, Boolean.valueOf(z));
        }
        return hashMap;
    }
}
