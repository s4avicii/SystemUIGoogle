package com.android.p012wm.shell.common;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Slog;
import com.android.p012wm.shell.common.RemoteCallable;
import com.android.systemui.wmshell.BubblesManager$5$$ExternalSyntheticLambda0;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.common.SingleInstanceRemoteListener */
public final class SingleInstanceRemoteListener<C extends RemoteCallable, L extends IInterface> {
    public final C mCallableController;
    public L mListener;
    public final C18481 mListenerDeathRecipient = new IBinder.DeathRecipient() {
        public final void binderDied() {
            C c = SingleInstanceRemoteListener.this.mCallableController;
            c.getRemoteCallExecutor().execute(new BubblesManager$5$$ExternalSyntheticLambda0(this, c, 4));
        }
    };
    public final Consumer<C> mOnRegisterCallback;
    public final Consumer<C> mOnUnregisterCallback;

    public final void register(L l) {
        L l2 = this.mListener;
        if (l2 != null) {
            l2.asBinder().unlinkToDeath(this.mListenerDeathRecipient, 0);
        }
        if (l != null) {
            try {
                l.asBinder().linkToDeath(this.mListenerDeathRecipient, 0);
            } catch (RemoteException unused) {
                Slog.e("SingleInstanceRemoteListener", "Failed to link to death");
                return;
            }
        }
        this.mListener = l;
        this.mOnRegisterCallback.accept(this.mCallableController);
    }

    public final void unregister() {
        L l = this.mListener;
        if (l != null) {
            l.asBinder().unlinkToDeath(this.mListenerDeathRecipient, 0);
        }
        this.mListener = null;
        this.mOnUnregisterCallback.accept(this.mCallableController);
    }

    public SingleInstanceRemoteListener(C c, Consumer<C> consumer, Consumer<C> consumer2) {
        this.mCallableController = c;
        this.mOnRegisterCallback = consumer;
        this.mOnUnregisterCallback = consumer2;
    }
}
