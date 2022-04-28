package com.android.systemui.p006qs.external;

import android.content.Context;
import android.content.SharedPreferences;

/* renamed from: com.android.systemui.qs.external.CustomTileStatePersister */
/* compiled from: CustomTileStatePersister.kt */
public final class CustomTileStatePersister {
    public final SharedPreferences sharedPreferences;

    public CustomTileStatePersister(Context context) {
        this.sharedPreferences = context.getSharedPreferences("custom_tiles_state", 0);
    }
}
