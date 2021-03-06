package com.android.settingslib.deviceinfo;

import android.content.IntentFilter;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

public abstract class AbstractConnectivityPreferenceController extends AbstractPreferenceController implements LifecycleObserver, OnStart, OnStop {
    public abstract String[] getConnectivityIntents();

    public final void onStart() {
        IntentFilter intentFilter = new IntentFilter();
        for (String addAction : getConnectivityIntents()) {
            intentFilter.addAction(addAction);
        }
        throw null;
    }

    public final void onStop() {
        throw null;
    }
}
