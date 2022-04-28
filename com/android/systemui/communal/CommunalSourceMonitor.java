package com.android.systemui.communal;

import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.util.condition.Monitor;
import com.google.android.collect.Lists;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public final class CommunalSourceMonitor {
    public static final boolean DEBUG = Log.isLoggable("CommunalSourceMonitor", 3);
    public boolean mAllCommunalConditionsMet = false;
    public final ArrayList<WeakReference<Callback>> mCallbacks = Lists.newArrayList();
    public final CommunalSourceMonitor$$ExternalSyntheticLambda0 mConditionsCallback = new CommunalSourceMonitor$$ExternalSyntheticLambda0(this);
    public final Monitor mConditionsMonitor;
    public CommunalSource mCurrentSource;
    public final Executor mExecutor;
    public boolean mListeningForConditions = false;

    public interface Callback {
        void onSourceAvailable(WeakReference<CommunalSource> weakReference);
    }

    @VisibleForTesting
    public CommunalSourceMonitor(Executor executor, Monitor monitor) {
        this.mExecutor = executor;
        this.mConditionsMonitor = monitor;
    }
}
