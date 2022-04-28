package com.android.systemui.controls.management;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ServiceInfo;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.applications.ServiceListing;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.settings.UserTracker;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsListingControllerImpl.kt */
public final class ControlsListingControllerImpl implements ControlsListingController {
    public Set<ComponentName> availableComponents;
    public List<? extends ServiceInfo> availableServices;
    public final Executor backgroundExecutor;
    public final LinkedHashSet callbacks;
    public final Context context;
    public int currentUserId;
    public ServiceListing serviceListing;
    public final Function1<Context, ServiceListing> serviceListingBuilder;
    public final ControlsListingControllerImpl$serviceListingCallback$1 serviceListingCallback;
    public AtomicInteger userChangeInProgress;

    @VisibleForTesting
    public ControlsListingControllerImpl(Context context2, Executor executor, Function1<? super Context, ? extends ServiceListing> function1, UserTracker userTracker) {
        this.context = context2;
        this.backgroundExecutor = executor;
        this.serviceListingBuilder = function1;
        this.serviceListing = (ServiceListing) function1.invoke(context2);
        this.callbacks = new LinkedHashSet();
        this.availableComponents = EmptySet.INSTANCE;
        this.availableServices = EmptyList.INSTANCE;
        this.userChangeInProgress = new AtomicInteger(0);
        this.currentUserId = userTracker.getUserId();
        ControlsListingControllerImpl$serviceListingCallback$1 controlsListingControllerImpl$serviceListingCallback$1 = new ControlsListingControllerImpl$serviceListingCallback$1(this);
        this.serviceListingCallback = controlsListingControllerImpl$serviceListingCallback$1;
        Log.d("ControlsListingControllerImpl", "Initializing");
        ServiceListing serviceListing2 = this.serviceListing;
        Objects.requireNonNull(serviceListing2);
        serviceListing2.mCallbacks.add(controlsListingControllerImpl$serviceListingCallback$1);
        this.serviceListing.setListening(true);
        this.serviceListing.reload();
    }

    public final void addCallback(Object obj) {
        this.backgroundExecutor.execute(new ControlsListingControllerImpl$addCallback$1(this, (ControlsListingController.ControlsListingCallback) obj));
    }

    public final void changeUser(UserHandle userHandle) {
        this.userChangeInProgress.incrementAndGet();
        this.serviceListing.setListening(false);
        this.backgroundExecutor.execute(new ControlsListingControllerImpl$changeUser$1(this, userHandle));
    }

    public final ArrayList getCurrentServices() {
        List<? extends ServiceInfo> list = this.availableServices;
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
        for (ServiceInfo controlsServiceInfo : list) {
            arrayList.add(new ControlsServiceInfo(this.context, controlsServiceInfo));
        }
        return arrayList;
    }

    public final void removeCallback(Object obj) {
        this.backgroundExecutor.execute(new ControlsListingControllerImpl$removeCallback$1(this, (ControlsListingController.ControlsListingCallback) obj));
    }

    public final CharSequence getAppLabel(ComponentName componentName) {
        Object obj;
        Iterator it = getCurrentServices().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((ControlsServiceInfo) obj).componentName, componentName)) {
                break;
            }
        }
        ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
        if (controlsServiceInfo == null) {
            return null;
        }
        return controlsServiceInfo.loadLabel();
    }

    public ControlsListingControllerImpl(Context context2, Executor executor, UserTracker userTracker) {
        this(context2, executor, C07421.INSTANCE, userTracker);
    }

    public final int getCurrentUserId() {
        return this.currentUserId;
    }
}
