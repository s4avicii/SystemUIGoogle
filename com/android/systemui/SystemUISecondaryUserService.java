package com.android.systemui;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

public class SystemUISecondaryUserService extends Service {
    public final IBinder onBind(Intent intent) {
        return null;
    }

    public final void onCreate() {
        super.onCreate();
        SystemUIApplication systemUIApplication = (SystemUIApplication) getApplication();
        Objects.requireNonNull(systemUIApplication);
        TreeMap treeMap = new TreeMap(Comparator.comparing(SystemUIApplication$$ExternalSyntheticLambda3.INSTANCE));
        SystemUIFactory systemUIFactory = SystemUIFactory.mFactory;
        Objects.requireNonNull(systemUIFactory);
        treeMap.putAll(systemUIFactory.mSysUIComponent.getPerUserStartables());
        systemUIApplication.startServicesIfNeeded(treeMap, "StartSecondaryServices", (String) null);
    }
}
