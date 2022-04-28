package com.google.android.systemui.columbus.actions;

import android.content.Context;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.google.android.systemui.columbus.ColumbusEvent;
import com.google.android.systemui.columbus.IColumbusServiceGestureListener;
import com.google.android.systemui.columbus.sensors.GestureSensor;
import java.util.Collections;
import java.util.Set;

/* compiled from: SettingsAction.kt */
public final class SettingsAction extends ServiceAction {
    public final StatusBar statusBar;
    public final Set<String> supportedCallerPackages = Collections.singleton(ThemeOverlayApplier.SETTINGS_PACKAGE);
    public final String tag = "Columbus/SettingsAction";
    public final UiEventLogger uiEventLogger;

    public final void onTrigger(GestureSensor.DetectionProperties detectionProperties) {
        this.uiEventLogger.log(ColumbusEvent.COLUMBUS_INVOKED_ON_SETTINGS);
        this.statusBar.collapseShade();
        IColumbusServiceGestureListener iColumbusServiceGestureListener = this.columbusServiceGestureListener;
        if (iColumbusServiceGestureListener != null) {
            try {
                iColumbusServiceGestureListener.onTrigger();
            } catch (DeadObjectException e) {
                Log.e("Columbus/ServiceAction", "Listener crashed or closed without unregistering", e);
                this.columbusServiceGestureListener = null;
                updateAvailable();
            } catch (RemoteException e2) {
                Log.e("Columbus/ServiceAction", "Unable to send trigger, setting listener to null", e2);
                this.columbusServiceGestureListener = null;
                updateAvailable();
            }
        }
    }

    public SettingsAction(Context context, StatusBar statusBar2, UiEventLogger uiEventLogger2) {
        super(context);
        this.statusBar = statusBar2;
        this.uiEventLogger = uiEventLogger2;
    }

    /* renamed from: getTag$vendor__unbundled_google__packages__SystemUIGoogle__android_common__sysuig */
    public final String mo19228xa00bbd41() {
        return this.tag;
    }
}
