package com.android.p012wm.shell.dagger;

import com.android.p012wm.shell.common.TransactionPool;
import dagger.internal.Factory;

/* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideTransactionPoolFactory */
public final class WMShellBaseModule_ProvideTransactionPoolFactory implements Factory<TransactionPool> {

    /* renamed from: com.android.wm.shell.dagger.WMShellBaseModule_ProvideTransactionPoolFactory$InstanceHolder */
    public static final class InstanceHolder {
        public static final WMShellBaseModule_ProvideTransactionPoolFactory INSTANCE = new WMShellBaseModule_ProvideTransactionPoolFactory();
    }

    public final Object get() {
        return new TransactionPool();
    }
}
