package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.IControlsActionCallback;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.service.controls.actions.ControlActionWrapper;
import android.util.ArraySet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2;
import com.android.internal.annotations.GuardedBy;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsProviderLifecycleManager.kt */
public final class ControlsProviderLifecycleManager implements IBinder.DeathRecipient {
    public final String TAG;
    public final IControlsActionCallback.Stub actionCallbackService;
    public int bindTryCount;
    public final ComponentName componentName;
    public final Context context;
    public final DelayableExecutor executor;
    public final Intent intent;
    public Runnable onLoadCanceller;
    @GuardedBy({"queuedServiceMethods"})
    public final ArraySet queuedServiceMethods = new ArraySet();
    public boolean requiresBound;
    public final ControlsProviderLifecycleManager$serviceConnection$1 serviceConnection;
    public final Binder token;
    public final UserHandle user;
    public ServiceWrapper wrapper;

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Action extends ServiceMethod {
        public final ControlAction action;

        /* renamed from: id */
        public final String f45id;

        public Action(String str, ControlAction controlAction) {
            super();
            this.f45id = str;
            this.action = controlAction;
        }

        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo7670x93b7231b() {
            String str = ControlsProviderLifecycleManager.this.TAG;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onAction ");
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            Objects.requireNonNull(controlsProviderLifecycleManager);
            m.append(controlsProviderLifecycleManager.componentName);
            m.append(" - ");
            ExifInterface$$ExternalSyntheticOutline2.m15m(m, this.f45id, str);
            ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = ControlsProviderLifecycleManager.this;
            ServiceWrapper serviceWrapper = controlsProviderLifecycleManager2.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            String str2 = this.f45id;
            ControlAction controlAction = this.action;
            try {
                serviceWrapper.service.action(str2, new ControlActionWrapper(controlAction), controlsProviderLifecycleManager2.actionCallbackService);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Load extends ServiceMethod {
        public final IControlsSubscriber.Stub subscriber;

        public Load(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
            super();
            this.subscriber = loadSubscriber;
        }

        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo7670x93b7231b() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            String str = controlsProviderLifecycleManager.TAG;
            Objects.requireNonNull(controlsProviderLifecycleManager);
            Log.d(str, Intrinsics.stringPlus("load ", controlsProviderLifecycleManager.componentName));
            ServiceWrapper serviceWrapper = ControlsProviderLifecycleManager.this.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.load(this.subscriber);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public abstract class ServiceMethod {
        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public abstract boolean mo7670x93b7231b();

        public ServiceMethod() {
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Subscribe extends ServiceMethod {
        public final List<String> list;
        public final IControlsSubscriber subscriber;

        public Subscribe(ArrayList arrayList, StatefulControlSubscriber statefulControlSubscriber) {
            super();
            this.list = arrayList;
            this.subscriber = statefulControlSubscriber;
        }

        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo7670x93b7231b() {
            String str = ControlsProviderLifecycleManager.this.TAG;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("subscribe ");
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            Objects.requireNonNull(controlsProviderLifecycleManager);
            m.append(controlsProviderLifecycleManager.componentName);
            m.append(" - ");
            m.append(this.list);
            Log.d(str, m.toString());
            ServiceWrapper serviceWrapper = ControlsProviderLifecycleManager.this.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.subscribe(this.list, this.subscriber);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    /* compiled from: ControlsProviderLifecycleManager.kt */
    public final class Suggest extends ServiceMethod {
        public final IControlsSubscriber.Stub subscriber;

        public Suggest(ControlsBindingControllerImpl.LoadSubscriber loadSubscriber) {
            super();
            this.subscriber = loadSubscriber;
        }

        /* renamed from: callWrapper$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
        public final boolean mo7670x93b7231b() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
            String str = controlsProviderLifecycleManager.TAG;
            Objects.requireNonNull(controlsProviderLifecycleManager);
            Log.d(str, Intrinsics.stringPlus("suggest ", controlsProviderLifecycleManager.componentName));
            ServiceWrapper serviceWrapper = ControlsProviderLifecycleManager.this.wrapper;
            if (serviceWrapper == null) {
                return false;
            }
            try {
                serviceWrapper.service.loadSuggested(this.subscriber);
                return true;
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
                return false;
            }
        }
    }

    public final void binderDied() {
        if (this.wrapper != null) {
            this.wrapper = null;
            if (this.requiresBound) {
                Log.d(this.TAG, "binderDied");
            }
        }
    }

    public final void cancelSubscription(IControlsSubscription iControlsSubscription) {
        Log.d(this.TAG, Intrinsics.stringPlus("cancelSubscription: ", iControlsSubscription));
        if (this.wrapper != null) {
            try {
                iControlsSubscription.cancel();
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            }
        }
    }

    public final void invokeOrQueue(ServiceMethod serviceMethod) {
        Unit unit;
        if (this.wrapper == null) {
            unit = null;
        } else {
            if (!serviceMethod.mo7670x93b7231b()) {
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsProviderLifecycleManager.this;
                Objects.requireNonNull(controlsProviderLifecycleManager);
                synchronized (controlsProviderLifecycleManager.queuedServiceMethods) {
                    controlsProviderLifecycleManager.queuedServiceMethods.add(serviceMethod);
                }
                ControlsProviderLifecycleManager.this.binderDied();
            }
            unit = Unit.INSTANCE;
        }
        if (unit == null) {
            synchronized (this.queuedServiceMethods) {
                this.queuedServiceMethods.add(serviceMethod);
            }
            this.executor.execute(new ControlsProviderLifecycleManager$bindService$1(this, true));
        }
    }

    public final void startSubscription(IControlsSubscription iControlsSubscription, long j) {
        Log.d(this.TAG, Intrinsics.stringPlus("startSubscription: ", iControlsSubscription));
        if (this.wrapper != null) {
            try {
                iControlsSubscription.request(j);
            } catch (Exception e) {
                Log.e("ServiceWrapper", "Caught exception from ControlsProviderService", e);
            }
        }
    }

    public final String toString() {
        return "ControlsProviderLifecycleManager(" + Intrinsics.stringPlus("component=", this.componentName) + Intrinsics.stringPlus(", user=", this.user) + ")";
    }

    public ControlsProviderLifecycleManager(Context context2, DelayableExecutor delayableExecutor, ControlsBindingControllerImpl$actionCallbackService$1 controlsBindingControllerImpl$actionCallbackService$1, UserHandle userHandle, ComponentName componentName2) {
        this.context = context2;
        this.executor = delayableExecutor;
        this.actionCallbackService = controlsBindingControllerImpl$actionCallbackService$1;
        this.user = userHandle;
        this.componentName = componentName2;
        Binder binder = new Binder();
        this.token = binder;
        Class<ControlsProviderLifecycleManager> cls = ControlsProviderLifecycleManager.class;
        this.TAG = "ControlsProviderLifecycleManager";
        Intent intent2 = new Intent();
        intent2.setComponent(componentName2);
        Bundle bundle = new Bundle();
        bundle.putBinder("CALLBACK_TOKEN", binder);
        intent2.putExtra("CALLBACK_BUNDLE", bundle);
        this.intent = intent2;
        this.serviceConnection = new ControlsProviderLifecycleManager$serviceConnection$1(this);
    }
}
