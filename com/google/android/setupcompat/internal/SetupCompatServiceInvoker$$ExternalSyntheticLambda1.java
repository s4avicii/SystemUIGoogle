package com.google.android.setupcompat.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.setupcompat.ISetupCompatService;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SetupCompatServiceInvoker$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SetupCompatServiceInvoker f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ Bundle f$2;

    public /* synthetic */ SetupCompatServiceInvoker$$ExternalSyntheticLambda1(SetupCompatServiceInvoker setupCompatServiceInvoker, int i, Bundle bundle) {
        this.f$0 = setupCompatServiceInvoker;
        this.f$1 = i;
        this.f$2 = bundle;
    }

    public final void run() {
        SetupCompatServiceInvoker setupCompatServiceInvoker = this.f$0;
        int i = this.f$1;
        Bundle bundle = this.f$2;
        Objects.requireNonNull(setupCompatServiceInvoker);
        try {
            Context context = setupCompatServiceInvoker.context;
            ISetupCompatService service = SetupCompatServiceProvider.getInstance(context).getService(setupCompatServiceInvoker.waitTimeInMillisForServiceConnection, TimeUnit.MILLISECONDS);
            if (service != null) {
                Bundle bundle2 = Bundle.EMPTY;
                service.logMetric(i, bundle);
                return;
            }
            SetupCompatServiceInvoker.LOG.mo18774w("logMetric failed since service reference is null. Are the permissions valid?");
        } catch (RemoteException | IllegalStateException | InterruptedException | TimeoutException e) {
            SetupCompatServiceInvoker.LOG.mo18772e(String.format("Exception occurred while trying to log metric = [%s]", new Object[]{bundle}), e);
        }
    }
}
