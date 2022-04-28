package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.Objects;

public final class SnackbarManager {
    public static SnackbarManager snackbarManager;
    public final Object lock = new Object();

    public static class SnackbarRecord {
    }

    public SnackbarManager() {
        new Handler(Looper.getMainLooper(), new Handler.Callback() {
            public final boolean handleMessage(Message message) {
                if (message.what != 0) {
                    return false;
                }
                SnackbarManager snackbarManager = SnackbarManager.this;
                SnackbarRecord snackbarRecord = (SnackbarRecord) message.obj;
                Objects.requireNonNull(snackbarManager);
                synchronized (snackbarManager.lock) {
                    if (snackbarRecord == null) {
                        Objects.requireNonNull(snackbarRecord);
                        throw null;
                    }
                }
                return true;
            }
        });
    }
}
