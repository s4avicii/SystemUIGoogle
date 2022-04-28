package com.android.systemui.shared.system;

import android.app.AppGlobals;
import android.content.pm.IPackageManager;

public final class PackageManagerWrapper {
    public static final IPackageManager mIPackageManager = AppGlobals.getPackageManager();
    public static final PackageManagerWrapper sInstance = new PackageManagerWrapper();
}
