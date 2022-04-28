package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleDispatcher;
import androidx.startup.Initializer;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ProcessLifecycleInitializer implements Initializer<LifecycleOwner> {
    public final Object create(Context context) {
        if (!LifecycleDispatcher.sInitialized.getAndSet(true)) {
            ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new LifecycleDispatcher.DispatcherActivityCallback());
        }
        ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.sInstance;
        Objects.requireNonNull(processLifecycleOwner);
        processLifecycleOwner.mHandler = new Handler();
        processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new EmptyActivityLifecycleCallbacks() {
            public void onActivityCreated(Activity activity, Bundle bundle) {
            }

            public void onActivityPaused(Activity activity) {
                ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
                Objects.requireNonNull(processLifecycleOwner);
                int i = processLifecycleOwner.mResumedCounter - 1;
                processLifecycleOwner.mResumedCounter = i;
                if (i == 0) {
                    processLifecycleOwner.mHandler.postDelayed(processLifecycleOwner.mDelayedPauseRunnable, 700);
                }
            }

            public void onActivityPreCreated(Activity activity, Bundle bundle) {
                activity.registerActivityLifecycleCallbacks(new EmptyActivityLifecycleCallbacks() {
                    public void onActivityPostResumed(Activity activity) {
                        ProcessLifecycleOwner.this.activityResumed();
                    }

                    public void onActivityPostStarted(Activity activity) {
                        ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
                        Objects.requireNonNull(processLifecycleOwner);
                        int i = processLifecycleOwner.mStartedCounter + 1;
                        processLifecycleOwner.mStartedCounter = i;
                        if (i == 1 && processLifecycleOwner.mStopSent) {
                            processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                            processLifecycleOwner.mStopSent = false;
                        }
                    }
                });
            }

            public void onActivityStopped(Activity activity) {
                ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
                Objects.requireNonNull(processLifecycleOwner);
                int i = processLifecycleOwner.mStartedCounter - 1;
                processLifecycleOwner.mStartedCounter = i;
                if (i == 0 && processLifecycleOwner.mPauseSent) {
                    processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
                    processLifecycleOwner.mStopSent = true;
                }
            }
        });
        return processLifecycleOwner;
    }

    public final List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
