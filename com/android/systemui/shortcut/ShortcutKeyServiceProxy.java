package com.android.systemui.shortcut;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import com.android.internal.policy.IShortcutService;
import java.util.Objects;

public final class ShortcutKeyServiceProxy extends IShortcutService.Stub {
    public Callbacks mCallbacks;
    public final C1127H mHandler = new C1127H();
    public final Object mLock = new Object();

    public interface Callbacks {
    }

    /* renamed from: com.android.systemui.shortcut.ShortcutKeyServiceProxy$H */
    public final class C1127H extends Handler {
        public C1127H() {
        }

        public final void handleMessage(Message message) {
            if (message.what == 1) {
                Callbacks callbacks = ShortcutKeyServiceProxy.this.mCallbacks;
                long longValue = ((Long) message.obj).longValue();
                ShortcutKeyDispatcher shortcutKeyDispatcher = (ShortcutKeyDispatcher) callbacks;
                Objects.requireNonNull(shortcutKeyDispatcher);
                int i = shortcutKeyDispatcher.mContext.getResources().getConfiguration().orientation;
                if ((longValue == 281474976710727L || longValue == 281474976710728L) && i == 2) {
                    shortcutKeyDispatcher.mSplitScreenOptional.ifPresent(new ShortcutKeyDispatcher$$ExternalSyntheticLambda0(shortcutKeyDispatcher, longValue));
                }
            }
        }
    }

    public final void notifyShortcutKeyPressed(long j) throws RemoteException {
        synchronized (this.mLock) {
            this.mHandler.obtainMessage(1, Long.valueOf(j)).sendToTarget();
        }
    }

    public ShortcutKeyServiceProxy(Callbacks callbacks) {
        this.mCallbacks = callbacks;
    }
}
