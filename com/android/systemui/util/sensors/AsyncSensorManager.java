package com.android.systemui.util.sensors;

import android.content.Context;
import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.MemoryFile;
import com.android.internal.util.Preconditions;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.SensorManagerPlugin;
import com.android.systemui.shared.plugins.PluginManager;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda20;
import com.android.systemui.util.concurrency.ThreadFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class AsyncSensorManager extends SensorManager implements PluginListener<SensorManagerPlugin> {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Executor mExecutor;
    public final SensorManager mInner;
    public final ArrayList mPlugins = new ArrayList();
    public final List<Sensor> mSensorCache;

    public final int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int i) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final boolean flushImpl(SensorEventListener sensorEventListener) {
        return this.mInner.flush(sensorEventListener);
    }

    public final List<Sensor> getFullDynamicSensorList() {
        return this.mInner.getSensorList(-1);
    }

    public final boolean initDataInjectionImpl(boolean z) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final boolean injectSensorDataImpl(Sensor sensor, float[] fArr, int i, long j) {
        throw new UnsupportedOperationException("not implemented");
    }

    public final void onPluginConnected(Plugin plugin, Context context) {
        this.mPlugins.add((SensorManagerPlugin) plugin);
    }

    public final void onPluginDisconnected(Plugin plugin) {
        this.mPlugins.remove((SensorManagerPlugin) plugin);
    }

    public final void registerDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback, Handler handler) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda5(this, dynamicSensorCallback, handler));
    }

    public final boolean registerListenerImpl(SensorEventListener sensorEventListener, Sensor sensor, int i, Handler handler, int i2, int i3) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda4(this, sensorEventListener, sensor, i, i2, handler));
        return true;
    }

    public final boolean requestTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor) {
        if (triggerEventListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        } else if (sensor != null) {
            this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda6(this, triggerEventListener, sensor));
            return true;
        } else {
            throw new IllegalArgumentException("sensor cannot be null");
        }
    }

    public final boolean setOperationParameterImpl(SensorAdditionalInfo sensorAdditionalInfo) {
        this.mExecutor.execute(new StatusBar$$ExternalSyntheticLambda20(this, sensorAdditionalInfo, 3));
        return true;
    }

    public final void unregisterDynamicSensorCallbackImpl(SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda0(this, dynamicSensorCallback, 0));
    }

    public final void unregisterListenerImpl(SensorEventListener sensorEventListener, Sensor sensor) {
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda3(this, sensor, sensorEventListener));
    }

    public AsyncSensorManager(SensorManager sensorManager, ThreadFactory threadFactory, PluginManager pluginManager) {
        this.mInner = sensorManager;
        this.mExecutor = threadFactory.buildExecutorOnNewThread("async_sensor");
        this.mSensorCache = sensorManager.getSensorList(-1);
        if (pluginManager != null) {
            pluginManager.addPluginListener(this, SensorManagerPlugin.class, true);
        }
    }

    public final boolean cancelTriggerSensorImpl(TriggerEventListener triggerEventListener, Sensor sensor, boolean z) {
        Preconditions.checkArgument(z);
        this.mExecutor.execute(new AsyncSensorManager$$ExternalSyntheticLambda7(this, triggerEventListener, sensor));
        return true;
    }

    public final List<Sensor> getFullSensorList() {
        return this.mSensorCache;
    }
}
