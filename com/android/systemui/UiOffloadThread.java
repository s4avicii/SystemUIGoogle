package com.android.systemui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class UiOffloadThread {
    public final ExecutorService mExecutorService = Executors.newSingleThreadExecutor();
}
