package androidx.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import androidx.lifecycle.Lifecycle;
import java.util.Objects;

public final class ReportFragment extends Fragment {
    public ActivityInitializationListener mProcessListener;

    public interface ActivityInitializationListener {
    }

    public static class LifecycleCallbacks implements Application.ActivityLifecycleCallbacks {
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public static void registerIn(Activity activity) {
            activity.registerActivityLifecycleCallbacks(new LifecycleCallbacks());
        }

        public void onActivityPostCreated(Activity activity, Bundle bundle) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_CREATE);
        }

        public void onActivityPostResumed(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_RESUME);
        }

        public void onActivityPostStarted(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_START);
        }

        public void onActivityPreDestroyed(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_DESTROY);
        }

        public void onActivityPrePaused(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_PAUSE);
        }

        public void onActivityPreStopped(Activity activity) {
            ReportFragment.dispatch(activity, Lifecycle.Event.ON_STOP);
        }
    }

    public static void dispatch(Activity activity, Lifecycle.Event event) {
        if (activity instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner) activity).getLifecycle().handleLifecycleEvent(event);
        } else if (activity instanceof LifecycleOwner) {
            Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle();
            if (lifecycle instanceof LifecycleRegistry) {
                ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event);
            }
        }
    }

    public static void injectIfNeededIn(Activity activity) {
        LifecycleCallbacks.registerIn(activity);
        FragmentManager fragmentManager = activity.getFragmentManager();
        if (fragmentManager.findFragmentByTag("androidx.lifecycle.LifecycleDispatcher.report_fragment_tag") == null) {
            fragmentManager.beginTransaction().add(new ReportFragment(), "androidx.lifecycle.LifecycleDispatcher.report_fragment_tag").commit();
            fragmentManager.executePendingTransactions();
        }
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mProcessListener = null;
    }

    public final void onResume() {
        super.onResume();
        ActivityInitializationListener activityInitializationListener = this.mProcessListener;
        if (activityInitializationListener != null) {
            ProcessLifecycleOwner.this.activityResumed();
        }
    }

    public final void onStart() {
        super.onStart();
        ActivityInitializationListener activityInitializationListener = this.mProcessListener;
        if (activityInitializationListener != null) {
            ProcessLifecycleOwner processLifecycleOwner = ProcessLifecycleOwner.this;
            Objects.requireNonNull(processLifecycleOwner);
            int i = processLifecycleOwner.mStartedCounter + 1;
            processLifecycleOwner.mStartedCounter = i;
            if (i == 1 && processLifecycleOwner.mStopSent) {
                processLifecycleOwner.mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
                processLifecycleOwner.mStopSent = false;
            }
        }
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onStop() {
        super.onStop();
    }
}
