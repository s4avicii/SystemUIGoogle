package com.android.systemui.communal;

import android.util.Log;
import com.android.systemui.CoreStartable;

public class CommunalSourcePrimer extends CoreStartable {
    public final void start() {
    }

    static {
        Log.isLoggable("CommunalSourcePrimer", 3);
    }

    public final void onBootCompleted() {
        throw null;
    }
}
