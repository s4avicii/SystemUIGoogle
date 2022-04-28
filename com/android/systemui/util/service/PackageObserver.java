package com.android.systemui.util.service;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import com.android.systemui.util.service.Observer;
import com.google.android.collect.Lists;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public final class PackageObserver implements Observer {
    public static final boolean DEBUG = Log.isLoggable("PackageObserver", 3);
    public final ArrayList<WeakReference<Observer.Callback>> mCallbacks = Lists.newArrayList();
    public final Context mContext;
    public final String mPackageName;
    public final C17091 mReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            if (PackageObserver.DEBUG) {
                Log.d("PackageObserver", "package added receiver - onReceive");
            }
            Iterator<WeakReference<Observer.Callback>> it = PackageObserver.this.mCallbacks.iterator();
            while (it.hasNext()) {
                Observer.Callback callback = (Observer.Callback) it.next().get();
                if (callback != null) {
                    callback.onSourceChanged();
                } else {
                    it.remove();
                }
            }
        }
    };

    public final void addCallback(PersistentConnectionManager$$ExternalSyntheticLambda0 persistentConnectionManager$$ExternalSyntheticLambda0) {
        if (DEBUG) {
            Log.d("PackageObserver", "addCallback:" + persistentConnectionManager$$ExternalSyntheticLambda0);
        }
        this.mCallbacks.add(new WeakReference(persistentConnectionManager$$ExternalSyntheticLambda0));
        if (this.mCallbacks.size() <= 1) {
            IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart(this.mPackageName, 0);
            this.mContext.registerReceiver(this.mReceiver, intentFilter, 2);
        }
    }

    public final void removeCallback(PersistentConnectionManager$$ExternalSyntheticLambda0 persistentConnectionManager$$ExternalSyntheticLambda0) {
        if (DEBUG) {
            Log.d("PackageObserver", "removeCallback:" + persistentConnectionManager$$ExternalSyntheticLambda0);
        }
        if (this.mCallbacks.removeIf(new PackageObserver$$ExternalSyntheticLambda0(persistentConnectionManager$$ExternalSyntheticLambda0)) && this.mCallbacks.isEmpty()) {
            this.mContext.unregisterReceiver(this.mReceiver);
        }
    }

    public PackageObserver(Context context, ComponentName componentName) {
        this.mContext = context;
        this.mPackageName = componentName.getPackageName();
    }
}
