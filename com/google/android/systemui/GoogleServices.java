package com.google.android.systemui;

import android.content.Context;
import android.provider.DeviceConfig;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.VendorServices;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.google.android.systemui.autorotate.AutorotateDataService;
import com.google.android.systemui.autorotate.AutorotateDataService$$ExternalSyntheticLambda0;
import com.google.android.systemui.columbus.ColumbusServiceWrapper;
import com.google.android.systemui.coversheet.CoversheetService;
import com.google.android.systemui.elmyra.ElmyraService;
import com.google.android.systemui.elmyra.ServiceConfigurationGoogle;
import com.google.android.systemui.face.FaceNotificationService;
import com.google.android.systemui.input.TouchContextService;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;

public class GoogleServices extends VendorServices {
    public final AutorotateDataService mAutorotateDataService;
    public final Lazy<ColumbusServiceWrapper> mColumbusServiceLazy;
    public final Lazy<ServiceConfigurationGoogle> mServiceConfigurationGoogle;
    public final ArrayList<Object> mServices = new ArrayList<>();
    public final UiEventLogger mUiEventLogger;

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (int i = 0; i < this.mServices.size(); i++) {
            if (this.mServices.get(i) instanceof Dumpable) {
                ((Dumpable) this.mServices.get(i)).dump(fileDescriptor, printWriter, strArr);
            }
        }
    }

    public final void addService(Object obj) {
        if (obj != null) {
            this.mServices.add(obj);
        }
    }

    public final void start() {
        addService(new DisplayCutoutEmulationAdapter(this.mContext));
        addService(new CoversheetService(this.mContext));
        AutorotateDataService autorotateDataService = this.mAutorotateDataService;
        Objects.requireNonNull(autorotateDataService);
        autorotateDataService.mLatencyTracker = LatencyTracker.getInstance(autorotateDataService.mContext);
        autorotateDataService.readFlagsToControlSensorLogging();
        DeviceConfigProxy deviceConfigProxy = autorotateDataService.mDeviceConfig;
        DelayableExecutor delayableExecutor = autorotateDataService.mMainExecutor;
        AutorotateDataService$$ExternalSyntheticLambda0 autorotateDataService$$ExternalSyntheticLambda0 = new AutorotateDataService$$ExternalSyntheticLambda0(autorotateDataService);
        Objects.requireNonNull(deviceConfigProxy);
        DeviceConfig.addOnPropertiesChangedListener("window_manager", delayableExecutor, autorotateDataService$$ExternalSyntheticLambda0);
        addService(this.mAutorotateDataService);
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.context_hub") && this.mContext.getPackageManager().hasSystemFeature("android.hardware.sensor.assist")) {
            addService(new ElmyraService(this.mContext, this.mServiceConfigurationGoogle.get(), this.mUiEventLogger));
        }
        if (this.mContext.getPackageManager().hasSystemFeature("com.google.android.feature.QUICK_TAP")) {
            addService(this.mColumbusServiceLazy.get());
        }
        if (this.mContext.getPackageManager().hasSystemFeature("android.hardware.biometrics.face")) {
            addService(new FaceNotificationService(this.mContext));
        }
        if (this.mContext.getResources().getBoolean(C1777R.bool.config_touch_context_enabled)) {
            addService(new TouchContextService(this.mContext));
        }
    }

    public GoogleServices(Context context, Lazy<ServiceConfigurationGoogle> lazy, UiEventLogger uiEventLogger, Lazy<ColumbusServiceWrapper> lazy2, AutorotateDataService autorotateDataService) {
        super(context);
        this.mServiceConfigurationGoogle = lazy;
        this.mUiEventLogger = uiEventLogger;
        this.mColumbusServiceLazy = lazy2;
        this.mAutorotateDataService = autorotateDataService;
    }
}
