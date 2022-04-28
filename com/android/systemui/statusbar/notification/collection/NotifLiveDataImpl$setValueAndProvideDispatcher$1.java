package com.android.systemui.statusbar.notification.collection;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Trace;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import androidx.lifecycle.Observer;
import com.android.systemui.util.ListenerSet;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotifLiveDataStoreImpl.kt */
public final class NotifLiveDataImpl$setValueAndProvideDispatcher$1 extends Lambda implements Function0<Unit> {
    public final /* synthetic */ T $value;
    public final /* synthetic */ NotifLiveDataImpl<T> this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotifLiveDataImpl$setValueAndProvideDispatcher$1(NotifLiveDataImpl<T> notifLiveDataImpl, T t) {
        super(0);
        this.this$0 = notifLiveDataImpl;
        this.$value = t;
    }

    public final Object invoke() {
        ListenerSet<Observer<T>> listenerSet = this.this$0.syncObservers;
        Objects.requireNonNull(listenerSet);
        if (!listenerSet.listeners.isEmpty()) {
            String m = MotionController$$ExternalSyntheticOutline1.m8m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("NotifLiveData("), this.this$0.name, ").dispatchToSyncObservers");
            NotifLiveDataImpl<T> notifLiveDataImpl = this.this$0;
            T t = this.$value;
            Trace.beginSection(m);
            try {
                Iterator<Observer<T>> it = notifLiveDataImpl.syncObservers.iterator();
                while (it.hasNext()) {
                    it.next().onChanged(t);
                }
            } finally {
                Trace.endSection();
            }
        }
        ListenerSet<Observer<T>> listenerSet2 = this.this$0.asyncObservers;
        Objects.requireNonNull(listenerSet2);
        if (!listenerSet2.listeners.isEmpty()) {
            final NotifLiveDataImpl<T> notifLiveDataImpl2 = this.this$0;
            notifLiveDataImpl2.mainExecutor.execute(new Runnable() {
                public final void run() {
                    NotifLiveDataImpl<Object> notifLiveDataImpl = notifLiveDataImpl2;
                    Objects.requireNonNull(notifLiveDataImpl);
                    T t = notifLiveDataImpl.atomicValue.get();
                    if (!Intrinsics.areEqual(notifLiveDataImpl.lastAsyncValue, t)) {
                        notifLiveDataImpl.lastAsyncValue = t;
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NotifLiveData(");
                        m.append(notifLiveDataImpl.name);
                        m.append(").dispatchToAsyncObservers");
                        Trace.beginSection(m.toString());
                        try {
                            Iterator<Observer<T>> it = notifLiveDataImpl.asyncObservers.iterator();
                            while (it.hasNext()) {
                                it.next().onChanged(t);
                            }
                        } finally {
                            Trace.endSection();
                        }
                    }
                }
            });
        }
        return Unit.INSTANCE;
    }
}
