package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.IBinder;
import android.os.UserHandle;
import android.service.controls.Control;
import android.service.controls.IControlsSubscriber;
import android.service.controls.IControlsSubscription;
import android.service.controls.actions.ControlAction;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.controls.controller.ControlsBindingController;
import com.android.systemui.controls.controller.ControlsProviderLifecycleManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@VisibleForTesting
/* compiled from: ControlsBindingControllerImpl.kt */
public class ControlsBindingControllerImpl implements ControlsBindingController {
    public static final ControlsBindingControllerImpl$Companion$emptyCallback$1 emptyCallback = new ControlsBindingControllerImpl$Companion$emptyCallback$1();
    public final ControlsBindingControllerImpl$actionCallbackService$1 actionCallbackService = new ControlsBindingControllerImpl$actionCallbackService$1(this);
    public final DelayableExecutor backgroundExecutor;
    public final Context context;
    public ControlsProviderLifecycleManager currentProvider;
    public UserHandle currentUser;
    public final Lazy<ControlsController> lazyController;
    public StatefulControlSubscriber statefulControlSubscriber;

    /* compiled from: ControlsBindingControllerImpl.kt */
    public abstract class CallbackRunnable implements Runnable {
        public final ControlsProviderLifecycleManager provider;
        public final IBinder token;

        public abstract void doRun();

        public CallbackRunnable(IBinder iBinder) {
            this.token = iBinder;
            this.provider = ControlsBindingControllerImpl.this.currentProvider;
        }

        public final void run() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager == null) {
                Log.e("ControlsBindingControllerImpl", "No current provider set");
            } else if (!Intrinsics.areEqual(controlsProviderLifecycleManager.user, ControlsBindingControllerImpl.this.currentUser)) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("User ");
                ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = this.provider;
                Objects.requireNonNull(controlsProviderLifecycleManager2);
                m.append(controlsProviderLifecycleManager2.user);
                m.append(" is not current user");
                Log.e("ControlsBindingControllerImpl", m.toString());
            } else {
                IBinder iBinder = this.token;
                ControlsProviderLifecycleManager controlsProviderLifecycleManager3 = this.provider;
                Objects.requireNonNull(controlsProviderLifecycleManager3);
                if (!Intrinsics.areEqual(iBinder, controlsProviderLifecycleManager3.token)) {
                    StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Provider for token:");
                    m2.append(this.token);
                    m2.append(" does not exist anymore");
                    Log.e("ControlsBindingControllerImpl", m2.toString());
                    return;
                }
                doRun();
            }
        }
    }

    /* compiled from: ControlsBindingControllerImpl.kt */
    public final class LoadSubscriber extends IControlsSubscriber.Stub {
        public Lambda _loadCancelInternal;
        public ControlsBindingController.LoadCallback callback;
        public AtomicBoolean isTerminated = new AtomicBoolean(false);
        public final ArrayList<Control> loadedControls = new ArrayList<>();
        public final long requestLimit;
        public IControlsSubscription subscription;

        public LoadSubscriber(ControlsBindingController.LoadCallback loadCallback, long j) {
            this.callback = loadCallback;
            this.requestLimit = j;
        }

        public final void maybeTerminateAndRun(CallbackRunnable callbackRunnable) {
            if (!this.isTerminated.get()) {
                this._loadCancelInternal = C0736x5cb900b7.INSTANCE;
                this.callback = ControlsBindingControllerImpl.emptyCallback;
                ControlsProviderLifecycleManager controlsProviderLifecycleManager = ControlsBindingControllerImpl.this.currentProvider;
                if (controlsProviderLifecycleManager != null) {
                    Runnable runnable = controlsProviderLifecycleManager.onLoadCanceller;
                    if (runnable != null) {
                        runnable.run();
                    }
                    controlsProviderLifecycleManager.onLoadCanceller = null;
                }
                ControlsBindingControllerImpl.this.backgroundExecutor.execute(new C0737x5cb900b8(this, callbackRunnable));
            }
        }

        public final void onComplete(IBinder iBinder) {
            maybeTerminateAndRun(new OnLoadRunnable(ControlsBindingControllerImpl.this, iBinder, this.loadedControls, this.callback));
        }

        public final void onError(IBinder iBinder, String str) {
            maybeTerminateAndRun(new OnLoadErrorRunnable(ControlsBindingControllerImpl.this, iBinder, str, this.callback));
        }

        public final void onNext(IBinder iBinder, Control control) {
            ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
            controlsBindingControllerImpl.backgroundExecutor.execute(new ControlsBindingControllerImpl$LoadSubscriber$onNext$1(this, control, controlsBindingControllerImpl, iBinder));
        }

        public final void onSubscribe(IBinder iBinder, IControlsSubscription iControlsSubscription) {
            this.subscription = iControlsSubscription;
            ControlsBindingControllerImpl controlsBindingControllerImpl = ControlsBindingControllerImpl.this;
            this._loadCancelInternal = new ControlsBindingControllerImpl$LoadSubscriber$onSubscribe$1(controlsBindingControllerImpl, this);
            controlsBindingControllerImpl.backgroundExecutor.execute(new OnSubscribeRunnable(controlsBindingControllerImpl, iBinder, iControlsSubscription, this.requestLimit));
        }
    }

    /* compiled from: ControlsBindingControllerImpl.kt */
    public final class OnActionResponseRunnable extends CallbackRunnable {
        public final String controlId;
        public final int response;

        public OnActionResponseRunnable(IBinder iBinder, String str, int i) {
            super(iBinder);
            this.controlId = str;
            this.response = i;
        }

        public final void doRun() {
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager != null) {
                ControlsBindingControllerImpl.this.lazyController.get().onActionResponse(controlsProviderLifecycleManager.componentName, this.controlId, this.response);
            }
        }
    }

    /* compiled from: ControlsBindingControllerImpl.kt */
    public final class OnCancelAndLoadRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final List<Control> list;
        public final IControlsSubscription subscription;

        public final void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Canceling and loading controls");
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager != null) {
                controlsProviderLifecycleManager.cancelSubscription(this.subscription);
            }
            this.callback.accept(this.list);
        }

        public OnCancelAndLoadRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, ArrayList arrayList, IControlsSubscription iControlsSubscription, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            this.list = arrayList;
            this.subscription = iControlsSubscription;
            this.callback = loadCallback;
        }
    }

    /* compiled from: ControlsBindingControllerImpl.kt */
    public final class OnLoadErrorRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final String error;

        public final void doRun() {
            this.callback.error(this.error);
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager != null) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onError receive from '");
                m.append(controlsProviderLifecycleManager.componentName);
                m.append("': ");
                m.append(this.error);
                Log.e("ControlsBindingControllerImpl", m.toString());
            }
        }

        public OnLoadErrorRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, String str, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            this.error = str;
            this.callback = loadCallback;
        }
    }

    /* compiled from: ControlsBindingControllerImpl.kt */
    public final class OnLoadRunnable extends CallbackRunnable {
        public final ControlsBindingController.LoadCallback callback;
        public final List<Control> list;

        public final void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Complete and loading controls");
            this.callback.accept(this.list);
        }

        public OnLoadRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, ArrayList arrayList, ControlsBindingController.LoadCallback loadCallback) {
            super(iBinder);
            this.list = arrayList;
            this.callback = loadCallback;
        }
    }

    /* compiled from: ControlsBindingControllerImpl.kt */
    public final class OnSubscribeRunnable extends CallbackRunnable {
        public final long requestLimit;
        public final IControlsSubscription subscription;

        public final void doRun() {
            Log.d("ControlsBindingControllerImpl", "LoadSubscription: Starting subscription");
            ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.provider;
            if (controlsProviderLifecycleManager != null) {
                controlsProviderLifecycleManager.startSubscription(this.subscription, this.requestLimit);
            }
        }

        public OnSubscribeRunnable(ControlsBindingControllerImpl controlsBindingControllerImpl, IBinder iBinder, IControlsSubscription iControlsSubscription, long j) {
            super(iBinder);
            this.subscription = iControlsSubscription;
            this.requestLimit = j;
        }
    }

    public final void action(ComponentName componentName, ControlInfo controlInfo, ControlAction controlAction) {
        if (this.statefulControlSubscriber == null) {
            Log.w("ControlsBindingControllerImpl", "No actions can occur outside of an active subscription. Ignoring.");
            return;
        }
        ControlsProviderLifecycleManager retrieveLifecycleManager = retrieveLifecycleManager(componentName);
        String str = controlInfo.controlId;
        Objects.requireNonNull(retrieveLifecycleManager);
        retrieveLifecycleManager.invokeOrQueue(new ControlsProviderLifecycleManager.Action(str, controlAction));
    }

    public final ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1 bindAndLoad(ComponentName componentName, ControlsControllerImpl$loadForComponent$2 controlsControllerImpl$loadForComponent$2) {
        LoadSubscriber loadSubscriber = new LoadSubscriber(controlsControllerImpl$loadForComponent$2, 100000);
        ControlsProviderLifecycleManager retrieveLifecycleManager = retrieveLifecycleManager(componentName);
        Objects.requireNonNull(retrieveLifecycleManager);
        retrieveLifecycleManager.onLoadCanceller = retrieveLifecycleManager.executor.executeDelayed(new ControlsProviderLifecycleManager$maybeBindAndLoad$1(retrieveLifecycleManager, loadSubscriber), 20, TimeUnit.SECONDS);
        retrieveLifecycleManager.invokeOrQueue(new ControlsProviderLifecycleManager.Load(loadSubscriber));
        return new ControlsBindingControllerImpl$LoadSubscriber$loadCancel$1(loadSubscriber);
    }

    public final void bindAndLoadSuggested(ComponentName componentName, ControlsControllerImpl$startSeeding$1 controlsControllerImpl$startSeeding$1) {
        LoadSubscriber loadSubscriber = new LoadSubscriber(controlsControllerImpl$startSeeding$1, 36);
        ControlsProviderLifecycleManager retrieveLifecycleManager = retrieveLifecycleManager(componentName);
        Objects.requireNonNull(retrieveLifecycleManager);
        retrieveLifecycleManager.onLoadCanceller = retrieveLifecycleManager.executor.executeDelayed(new ControlsProviderLifecycleManager$maybeBindAndLoadSuggested$1(retrieveLifecycleManager, loadSubscriber), 20, TimeUnit.SECONDS);
        retrieveLifecycleManager.invokeOrQueue(new ControlsProviderLifecycleManager.Suggest(loadSubscriber));
    }

    public final void changeUser(UserHandle userHandle) {
        if (!Intrinsics.areEqual(userHandle, this.currentUser)) {
            unbind();
            this.currentUser = userHandle;
        }
    }

    @VisibleForTesting
    /* renamed from: createProviderManager$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public ControlsProviderLifecycleManager mo7605xb2527126(ComponentName componentName) {
        return new ControlsProviderLifecycleManager(this.context, this.backgroundExecutor, this.actionCallbackService, this.currentUser, componentName);
    }

    public final void onComponentRemoved(ComponentName componentName) {
        this.backgroundExecutor.execute(new ControlsBindingControllerImpl$onComponentRemoved$1(this, componentName));
    }

    public final ControlsProviderLifecycleManager retrieveLifecycleManager(ComponentName componentName) {
        ComponentName componentName2;
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            if (controlsProviderLifecycleManager == null) {
                componentName2 = null;
            } else {
                componentName2 = controlsProviderLifecycleManager.componentName;
            }
            if (!Intrinsics.areEqual(componentName2, componentName)) {
                unbind();
            }
        }
        ControlsProviderLifecycleManager controlsProviderLifecycleManager2 = this.currentProvider;
        if (controlsProviderLifecycleManager2 == null) {
            controlsProviderLifecycleManager2 = mo7605xb2527126(componentName);
        }
        this.currentProvider = controlsProviderLifecycleManager2;
        return controlsProviderLifecycleManager2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("  ControlsBindingController:\n");
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    currentUser=");
        m.append(this.currentUser);
        m.append(10);
        sb.append(m.toString());
        sb.append(Intrinsics.stringPlus("    StatefulControlSubscriber=", this.statefulControlSubscriber));
        sb.append("    Providers=" + this.currentProvider + 10);
        return sb.toString();
    }

    public final void unsubscribe() {
        StatefulControlSubscriber statefulControlSubscriber2 = this.statefulControlSubscriber;
        if (statefulControlSubscriber2 != null && statefulControlSubscriber2.subscriptionOpen) {
            statefulControlSubscriber2.bgExecutor.execute(new StatefulControlSubscriber$cancel$1(statefulControlSubscriber2));
        }
        this.statefulControlSubscriber = null;
    }

    public ControlsBindingControllerImpl(Context context2, DelayableExecutor delayableExecutor, Lazy<ControlsController> lazy, UserTracker userTracker) {
        this.context = context2;
        this.backgroundExecutor = delayableExecutor;
        this.lazyController = lazy;
        this.currentUser = userTracker.getUserHandle();
    }

    public final void subscribe(StructureInfo structureInfo) {
        unsubscribe();
        ControlsProviderLifecycleManager retrieveLifecycleManager = retrieveLifecycleManager(structureInfo.componentName);
        StatefulControlSubscriber statefulControlSubscriber2 = new StatefulControlSubscriber(this.lazyController.get(), retrieveLifecycleManager, this.backgroundExecutor);
        this.statefulControlSubscriber = statefulControlSubscriber2;
        List<ControlInfo> list = structureInfo.controls;
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
        for (ControlInfo controlInfo : list) {
            Objects.requireNonNull(controlInfo);
            arrayList.add(controlInfo.controlId);
        }
        retrieveLifecycleManager.invokeOrQueue(new ControlsProviderLifecycleManager.Subscribe(arrayList, statefulControlSubscriber2));
    }

    public final void unbind() {
        unsubscribe();
        ControlsProviderLifecycleManager controlsProviderLifecycleManager = this.currentProvider;
        if (controlsProviderLifecycleManager != null) {
            Runnable runnable = controlsProviderLifecycleManager.onLoadCanceller;
            if (runnable != null) {
                runnable.run();
            }
            controlsProviderLifecycleManager.onLoadCanceller = null;
            controlsProviderLifecycleManager.executor.execute(new ControlsProviderLifecycleManager$bindService$1(controlsProviderLifecycleManager, false));
        }
        this.currentProvider = null;
    }
}
