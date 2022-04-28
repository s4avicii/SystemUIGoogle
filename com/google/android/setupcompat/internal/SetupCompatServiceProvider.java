package com.google.android.setupcompat.internal;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.android.systemui.plugins.FalsingManager;
import com.google.android.setupcompat.ISetupCompatService;
import com.google.android.setupcompat.util.Logger;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

public final class SetupCompatServiceProvider {
    public static final Intent COMPAT_SERVICE_INTENT = new Intent().setPackage("com.google.android.setupwizard").setAction("com.google.android.setupcompat.SetupCompatService.BIND");
    public static final Logger LOG = new Logger("SetupCompatServiceProvider");
    public static boolean disableLooperCheckForTesting = false;
    @SuppressLint({"StaticFieldLeak"})
    public static volatile SetupCompatServiceProvider instance;
    public final AtomicReference<CountDownLatch> connectedConditionRef = new AtomicReference<>();
    public final Context context;
    public final ServiceConnection serviceConnection = new ServiceConnection() {
        public final void onBindingDied(ComponentName componentName) {
            SetupCompatServiceProvider.this.swapServiceContextAndNotify(new ServiceContext(State.REBIND_REQUIRED, (ISetupCompatService) null));
        }

        public final void onNullBinding(ComponentName componentName) {
            SetupCompatServiceProvider.this.swapServiceContextAndNotify(new ServiceContext(State.SERVICE_NOT_USABLE, (ISetupCompatService) null));
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            ISetupCompatService iSetupCompatService;
            State state = State.CONNECTED;
            if (iBinder == null) {
                state = State.DISCONNECTED;
                SetupCompatServiceProvider.LOG.mo18774w("Binder is null when onServiceConnected was called!");
            }
            SetupCompatServiceProvider setupCompatServiceProvider = SetupCompatServiceProvider.this;
            int i = ISetupCompatService.Stub.$r8$clinit;
            if (iBinder == null) {
                iSetupCompatService = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.setupcompat.ISetupCompatService");
                if (queryLocalInterface == null || !(queryLocalInterface instanceof ISetupCompatService)) {
                    iSetupCompatService = new ISetupCompatService.Stub.Proxy(iBinder);
                } else {
                    iSetupCompatService = (ISetupCompatService) queryLocalInterface;
                }
            }
            setupCompatServiceProvider.swapServiceContextAndNotify(new ServiceContext(state, iSetupCompatService));
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            SetupCompatServiceProvider.this.swapServiceContextAndNotify(new ServiceContext(State.DISCONNECTED, (ISetupCompatService) null));
        }
    };
    public volatile ServiceContext serviceContext = new ServiceContext(State.NOT_STARTED, (ISetupCompatService) null);

    public enum State {
        NOT_STARTED,
        BIND_FAILED,
        BINDING,
        CONNECTED,
        DISCONNECTED,
        SERVICE_NOT_USABLE,
        REBIND_REQUIRED
    }

    public final ISetupCompatService waitForConnection(long j, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
        ServiceContext serviceContext2;
        CountDownLatch countDownLatch;
        ServiceContext serviceContext3;
        synchronized (this) {
            serviceContext2 = this.serviceContext;
        }
        if (serviceContext2.state == State.CONNECTED) {
            return serviceContext2.compatService;
        }
        do {
            countDownLatch = this.connectedConditionRef.get();
            if (countDownLatch != null) {
                break;
            }
            countDownLatch = createCountDownLatch();
        } while (!this.connectedConditionRef.compareAndSet((Object) null, countDownLatch));
        Logger logger = LOG;
        logger.atInfo("Waiting for service to get connected");
        if (countDownLatch.await(j, timeUnit)) {
            synchronized (this) {
                serviceContext3 = this.serviceContext;
            }
            logger.atInfo(String.format("Finished waiting for service to get connected. Current state = %s", new Object[]{serviceContext3.state}));
            return serviceContext3.compatService;
        }
        requestServiceBind();
        throw new TimeoutException(String.format("Failed to acquire connection after [%s %s]", new Object[]{Long.valueOf(j), timeUnit}));
    }

    public static final class ServiceContext {
        public final ISetupCompatService compatService;
        public final State state;

        public ServiceContext(State state2, ISetupCompatService iSetupCompatService) {
            this.state = state2;
            this.compatService = iSetupCompatService;
            if (state2 == State.CONNECTED) {
                Objects.requireNonNull(iSetupCompatService, "CompatService cannot be null when state is connected");
            }
        }
    }

    public static SetupCompatServiceProvider getInstance(Context context2) {
        Objects.requireNonNull(context2, "Context object cannot be null.");
        SetupCompatServiceProvider setupCompatServiceProvider = instance;
        if (setupCompatServiceProvider == null) {
            synchronized (SetupCompatServiceProvider.class) {
                setupCompatServiceProvider = instance;
                if (setupCompatServiceProvider == null) {
                    setupCompatServiceProvider = new SetupCompatServiceProvider(context2.getApplicationContext());
                    instance = setupCompatServiceProvider;
                    instance.requestServiceBind();
                }
            }
        }
        return setupCompatServiceProvider;
    }

    public CountDownLatch createCountDownLatch() {
        return new CountDownLatch(1);
    }

    public State getCurrentState() {
        return this.serviceContext.state;
    }

    public ISetupCompatService getService(long j, TimeUnit timeUnit) throws TimeoutException, InterruptedException {
        boolean z;
        ServiceContext serviceContext2;
        if (disableLooperCheckForTesting || Looper.getMainLooper() != Looper.myLooper()) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            synchronized (this) {
                serviceContext2 = this.serviceContext;
            }
            switch (serviceContext2.state.ordinal()) {
                case 0:
                    throw new IllegalStateException("NOT_STARTED state only possible before instance is created.");
                case 1:
                case 5:
                    return null;
                case 2:
                case 4:
                    return waitForConnection(j, timeUnit);
                case 3:
                    return serviceContext2.compatService;
                case FalsingManager.VERSION:
                    requestServiceBind();
                    return waitForConnection(j, timeUnit);
                default:
                    StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unknown state = ");
                    m.append(serviceContext2.state);
                    throw new IllegalStateException(m.toString());
            }
        } else {
            throw new IllegalStateException("getService blocks and should not be called from the main thread.");
        }
    }

    /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
        java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
        	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
        	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
        	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
        	at java.base/java.util.Objects.checkIndex(Objects.java:372)
        	at java.base/java.util.ArrayList.get(ArrayList.java:458)
        	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public final synchronized void requestServiceBind() {
        /*
            r5 = this;
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$State r0 = com.google.android.setupcompat.internal.SetupCompatServiceProvider.State.CONNECTED
            monitor-enter(r5)
            monitor-enter(r5)     // Catch:{ all -> 0x0078 }
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$ServiceContext r1 = r5.serviceContext     // Catch:{ all -> 0x007a }
            monitor-exit(r5)     // Catch:{ all -> 0x0078 }
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$State r1 = r1.state     // Catch:{ all -> 0x0078 }
            if (r1 != r0) goto L_0x0014
            com.google.android.setupcompat.util.Logger r0 = LOG     // Catch:{ all -> 0x0078 }
            java.lang.String r1 = "Refusing to rebind since current state is already connected"
            r0.atInfo(r1)     // Catch:{ all -> 0x0078 }
            monitor-exit(r5)
            return
        L_0x0014:
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$State r2 = com.google.android.setupcompat.internal.SetupCompatServiceProvider.State.NOT_STARTED     // Catch:{ all -> 0x0078 }
            if (r1 == r2) goto L_0x0026
            com.google.android.setupcompat.util.Logger r1 = LOG     // Catch:{ all -> 0x0078 }
            java.lang.String r2 = "Unbinding existing service connection."
            r1.atInfo(r2)     // Catch:{ all -> 0x0078 }
            android.content.Context r1 = r5.context     // Catch:{ all -> 0x0078 }
            android.content.ServiceConnection r2 = r5.serviceConnection     // Catch:{ all -> 0x0078 }
            r1.unbindService(r2)     // Catch:{ all -> 0x0078 }
        L_0x0026:
            android.content.Context r1 = r5.context     // Catch:{ SecurityException -> 0x0032 }
            android.content.Intent r2 = COMPAT_SERVICE_INTENT     // Catch:{ SecurityException -> 0x0032 }
            android.content.ServiceConnection r3 = r5.serviceConnection     // Catch:{ SecurityException -> 0x0032 }
            r4 = 1
            boolean r1 = r1.bindService(r2, r3, r4)     // Catch:{ SecurityException -> 0x0032 }
            goto L_0x004a
        L_0x0032:
            r1 = move-exception
            com.google.android.setupcompat.util.Logger r2 = LOG     // Catch:{ all -> 0x0078 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0078 }
            r3.<init>()     // Catch:{ all -> 0x0078 }
            java.lang.String r4 = "Unable to bind to compat service. "
            r3.append(r4)     // Catch:{ all -> 0x0078 }
            r3.append(r1)     // Catch:{ all -> 0x0078 }
            java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x0078 }
            r2.mo18771e(r1)     // Catch:{ all -> 0x0078 }
            r1 = 0
        L_0x004a:
            r2 = 0
            if (r1 == 0) goto L_0x0065
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$State r1 = r5.getCurrentState()     // Catch:{ all -> 0x0078 }
            if (r1 == r0) goto L_0x0076
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$ServiceContext r0 = new com.google.android.setupcompat.internal.SetupCompatServiceProvider$ServiceContext     // Catch:{ all -> 0x0078 }
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$State r1 = com.google.android.setupcompat.internal.SetupCompatServiceProvider.State.BINDING     // Catch:{ all -> 0x0078 }
            r0.<init>(r1, r2)     // Catch:{ all -> 0x0078 }
            r5.swapServiceContextAndNotify(r0)     // Catch:{ all -> 0x0078 }
            com.google.android.setupcompat.util.Logger r0 = LOG     // Catch:{ all -> 0x0078 }
            java.lang.String r1 = "Context#bindService went through, now waiting for service connection"
            r0.atInfo(r1)     // Catch:{ all -> 0x0078 }
            goto L_0x0076
        L_0x0065:
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$ServiceContext r0 = new com.google.android.setupcompat.internal.SetupCompatServiceProvider$ServiceContext     // Catch:{ all -> 0x0078 }
            com.google.android.setupcompat.internal.SetupCompatServiceProvider$State r1 = com.google.android.setupcompat.internal.SetupCompatServiceProvider.State.BIND_FAILED     // Catch:{ all -> 0x0078 }
            r0.<init>(r1, r2)     // Catch:{ all -> 0x0078 }
            r5.swapServiceContextAndNotify(r0)     // Catch:{ all -> 0x0078 }
            com.google.android.setupcompat.util.Logger r0 = LOG     // Catch:{ all -> 0x0078 }
            java.lang.String r1 = "Context#bindService did not succeed."
            r0.mo18771e(r1)     // Catch:{ all -> 0x0078 }
        L_0x0076:
            monitor-exit(r5)
            return
        L_0x0078:
            r0 = move-exception
            goto L_0x007d
        L_0x007a:
            r0 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0078 }
            throw r0     // Catch:{ all -> 0x0078 }
        L_0x007d:
            monitor-exit(r5)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.setupcompat.internal.SetupCompatServiceProvider.requestServiceBind():void");
    }

    public final void swapServiceContextAndNotify(ServiceContext serviceContext2) {
        LOG.atInfo(String.format("State changed: %s -> %s", new Object[]{this.serviceContext.state, serviceContext2.state}));
        this.serviceContext = serviceContext2;
        CountDownLatch andSet = this.connectedConditionRef.getAndSet((Object) null);
        if (andSet != null) {
            andSet.countDown();
        }
    }

    public SetupCompatServiceProvider(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public static void setInstanceForTesting(SetupCompatServiceProvider setupCompatServiceProvider) {
        instance = setupCompatServiceProvider;
    }
}
