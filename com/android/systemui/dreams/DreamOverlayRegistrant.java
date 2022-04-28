package com.android.systemui.dreams;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.dreams.IDreamManager;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.CoreStartable;

public class DreamOverlayRegistrant extends CoreStartable {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayRegistrant", 3);
    public boolean mCurrentRegisteredState = false;
    public final IDreamManager mDreamManager;
    public final ComponentName mOverlayServiceComponent;
    public final C07841 mReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            if (DreamOverlayRegistrant.DEBUG) {
                Log.d("DreamOverlayRegistrant", "package changed receiver - onReceive");
            }
            DreamOverlayRegistrant.this.registerOverlayService();
        }
    };
    public final Resources mResources;

    public final void registerOverlayService() {
        ComponentName componentName;
        String str;
        int i;
        PackageManager packageManager = this.mContext.getPackageManager();
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(this.mOverlayServiceComponent);
        boolean z = false;
        if (componentEnabledSetting != 3) {
            if (this.mResources.getBoolean(C1777R.bool.config_dreamOverlayServiceEnabled)) {
                i = 1;
            } else {
                i = 2;
            }
            if (i != componentEnabledSetting) {
                packageManager.setComponentEnabledSetting(this.mOverlayServiceComponent, i, 0);
            }
        }
        if (packageManager.getComponentEnabledSetting(this.mOverlayServiceComponent) == 1) {
            z = true;
        }
        if (this.mCurrentRegisteredState != z) {
            this.mCurrentRegisteredState = z;
            try {
                if (DEBUG) {
                    if (z) {
                        str = "registering dream overlay service:" + this.mOverlayServiceComponent;
                    } else {
                        str = "clearing dream overlay service";
                    }
                    Log.d("DreamOverlayRegistrant", str);
                }
                IDreamManager iDreamManager = this.mDreamManager;
                if (this.mCurrentRegisteredState) {
                    componentName = this.mOverlayServiceComponent;
                } else {
                    componentName = null;
                }
                iDreamManager.registerDreamOverlayService(componentName);
            } catch (RemoteException e) {
                Log.e("DreamOverlayRegistrant", "could not register dream overlay service:" + e);
            }
        }
    }

    public final void start() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(this.mOverlayServiceComponent.getPackageName(), 0);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        registerOverlayService();
    }

    public DreamOverlayRegistrant(Context context, Resources resources) {
        super(context);
        this.mResources = resources;
        this.mDreamManager = IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
        this.mOverlayServiceComponent = new ComponentName(this.mContext, DreamOverlayService.class);
    }
}
