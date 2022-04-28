package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import androidx.activity.ComponentActivity;
import androidx.activity.OnBackPressedDispatcher;
import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.ActivityResultRegistryOwner;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.app.LoaderManagerImpl;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public class FragmentActivity extends ComponentActivity {
    public boolean mCreated;
    public final LifecycleRegistry mFragmentLifecycleRegistry = new LifecycleRegistry(this, true);
    public final FragmentController mFragments = new FragmentController(new HostCallbacks());
    public boolean mResumed;
    public boolean mStopped = true;

    public class HostCallbacks extends FragmentHostCallback<FragmentActivity> implements ViewModelStoreOwner, OnBackPressedDispatcherOwner, ActivityResultRegistryOwner, SavedStateRegistryOwner, FragmentOnAttachListener {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        public final ActivityResultRegistry getActivityResultRegistry() {
            FragmentActivity fragmentActivity = FragmentActivity.this;
            Objects.requireNonNull(fragmentActivity);
            return fragmentActivity.mActivityResultRegistry;
        }

        public final Lifecycle getLifecycle() {
            return FragmentActivity.this.mFragmentLifecycleRegistry;
        }

        public final OnBackPressedDispatcher getOnBackPressedDispatcher() {
            FragmentActivity fragmentActivity = FragmentActivity.this;
            Objects.requireNonNull(fragmentActivity);
            return fragmentActivity.mOnBackPressedDispatcher;
        }

        public final SavedStateRegistry getSavedStateRegistry() {
            return FragmentActivity.this.getSavedStateRegistry();
        }

        public final ViewModelStore getViewModelStore() {
            return FragmentActivity.this.getViewModelStore();
        }

        public final void onAttachFragment$1() {
            Objects.requireNonNull(FragmentActivity.this);
        }

        public final View onFindViewById(int i) {
            return FragmentActivity.this.findViewById(i);
        }

        public final LayoutInflater onGetLayoutInflater() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext(FragmentActivity.this);
        }

        public final boolean onHasView() {
            Window window = FragmentActivity.this.getWindow();
            if (window == null || window.peekDecorView() == null) {
                return false;
            }
            return true;
        }

        public final void onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.supportInvalidateOptionsMenu();
        }

        public final FragmentActivity onGetHost$1() {
            return FragmentActivity.this;
        }
    }

    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        FragmentManagerImpl fragmentManagerImpl = fragmentController.mHost.mFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        View onCreateView = fragmentManagerImpl.mLayoutInflaterFactory.onCreateView(view, str, context, attributeSet);
        return onCreateView == null ? super.onCreateView(view, str, context, attributeSet) : onCreateView;
    }

    public static boolean markState(FragmentManager fragmentManager) {
        FragmentActivity fragmentActivity;
        Lifecycle.State state = Lifecycle.State.CREATED;
        Lifecycle.State state2 = Lifecycle.State.STARTED;
        Objects.requireNonNull(fragmentManager);
        boolean z = false;
        for (Fragment next : fragmentManager.mFragmentStore.getFragments()) {
            if (next != null) {
                FragmentHostCallback<?> fragmentHostCallback = next.mHost;
                if (fragmentHostCallback == null) {
                    fragmentActivity = null;
                } else {
                    fragmentActivity = fragmentHostCallback.onGetHost$1();
                }
                if (fragmentActivity != null) {
                    z |= markState(next.getChildFragmentManager());
                }
                FragmentViewLifecycleOwner fragmentViewLifecycleOwner = next.mViewLifecycleOwner;
                if (fragmentViewLifecycleOwner != null) {
                    fragmentViewLifecycleOwner.initialize();
                    LifecycleRegistry lifecycleRegistry = fragmentViewLifecycleOwner.mLifecycleRegistry;
                    Objects.requireNonNull(lifecycleRegistry);
                    if (lifecycleRegistry.mState.isAtLeast(state2)) {
                        FragmentViewLifecycleOwner fragmentViewLifecycleOwner2 = next.mViewLifecycleOwner;
                        Objects.requireNonNull(fragmentViewLifecycleOwner2);
                        fragmentViewLifecycleOwner2.mLifecycleRegistry.setCurrentState(state);
                        z = true;
                    }
                }
                LifecycleRegistry lifecycleRegistry2 = next.mLifecycleRegistry;
                Objects.requireNonNull(lifecycleRegistry2);
                if (lifecycleRegistry2.mState.isAtLeast(state2)) {
                    next.mLifecycleRegistry.setCurrentState(state);
                    z = true;
                }
            }
        }
        return z;
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        this.mFragments.noteStateNotSaved();
        super.onActivityResult(i, i2, intent);
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.mFragments.noteStateNotSaved();
        super.onConfigurationChanged(configuration);
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.dispatchConfigurationChanged(configuration);
    }

    public final boolean onCreatePanelMenu(int i, Menu menu) {
        if (i != 0) {
            return super.onCreatePanelMenu(i, menu);
        }
        boolean onCreatePanelMenu = super.onCreatePanelMenu(i, menu);
        FragmentController fragmentController = this.mFragments;
        getMenuInflater();
        Objects.requireNonNull(fragmentController);
        return fragmentController.mHost.mFragmentManager.dispatchCreateOptionsMenu() | onCreatePanelMenu;
    }

    public final void onMultiWindowModeChanged(boolean z) {
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.dispatchMultiWindowModeChanged(z);
    }

    public final void onNewIntent(@SuppressLint({"UnknownNullness"}) Intent intent) {
        this.mFragments.noteStateNotSaved();
        super.onNewIntent(intent);
    }

    public void onPanelClosed(int i, Menu menu) {
        if (i == 0) {
            FragmentController fragmentController = this.mFragments;
            Objects.requireNonNull(fragmentController);
            fragmentController.mHost.mFragmentManager.dispatchOptionsMenuClosed();
        }
        super.onPanelClosed(i, menu);
    }

    public final void onPictureInPictureModeChanged(boolean z) {
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.dispatchPictureInPictureModeChanged(z);
    }

    public final boolean onPreparePanel(int i, View view, Menu menu) {
        if (i != 0) {
            return super.onPreparePanel(i, view, menu);
        }
        boolean onPreparePanel = super.onPreparePanel(0, view, menu);
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        return fragmentController.mHost.mFragmentManager.dispatchPrepareOptionsMenu() | onPreparePanel;
    }

    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        this.mFragments.noteStateNotSaved();
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    public final void onResume() {
        this.mFragments.noteStateNotSaved();
        super.onResume();
        this.mResumed = true;
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.execPendingActions(true);
    }

    public void onStart() {
        this.mFragments.noteStateNotSaved();
        super.onStart();
        this.mStopped = false;
        if (!this.mCreated) {
            this.mCreated = true;
            FragmentController fragmentController = this.mFragments;
            Objects.requireNonNull(fragmentController);
            FragmentManagerImpl fragmentManagerImpl = fragmentController.mHost.mFragmentManager;
            Objects.requireNonNull(fragmentManagerImpl);
            fragmentManagerImpl.mStateSaved = false;
            fragmentManagerImpl.mStopped = false;
            FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
            Objects.requireNonNull(fragmentManagerViewModel);
            fragmentManagerViewModel.mIsStateSaved = false;
            fragmentManagerImpl.dispatchStateChange(4);
        }
        FragmentController fragmentController2 = this.mFragments;
        Objects.requireNonNull(fragmentController2);
        fragmentController2.mHost.mFragmentManager.execPendingActions(true);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        FragmentController fragmentController3 = this.mFragments;
        Objects.requireNonNull(fragmentController3);
        FragmentManagerImpl fragmentManagerImpl2 = fragmentController3.mHost.mFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl2);
        fragmentManagerImpl2.mStateSaved = false;
        fragmentManagerImpl2.mStopped = false;
        FragmentManagerViewModel fragmentManagerViewModel2 = fragmentManagerImpl2.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel2);
        fragmentManagerViewModel2.mIsStateSaved = false;
        fragmentManagerImpl2.dispatchStateChange(5);
    }

    public final void onStateNotSaved() {
        this.mFragments.noteStateNotSaved();
    }

    public FragmentActivity() {
        getSavedStateRegistry().registerSavedStateProvider("android:support:lifecycle", new FragmentActivity$$ExternalSyntheticLambda1(this));
        addOnContextAvailableListener(new FragmentActivity$$ExternalSyntheticLambda0(this));
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        printWriter.print(str);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String str2 = str + "  ";
        printWriter.print(str2);
        printWriter.print("mCreated=");
        printWriter.print(this.mCreated);
        printWriter.print(" mResumed=");
        printWriter.print(this.mResumed);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        if (getApplication() != null) {
            new LoaderManagerImpl(this, getViewModelStore()).dump(str2, printWriter);
        }
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.dump(str, fileDescriptor, printWriter, strArr);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.dispatchCreate();
    }

    public void onDestroy() {
        super.onDestroy();
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.dispatchDestroy();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    public final void onLowMemory() {
        super.onLowMemory();
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        fragmentController.mHost.mFragmentManager.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int i, MenuItem menuItem) {
        if (super.onMenuItemSelected(i, menuItem)) {
            return true;
        }
        if (i == 0) {
            FragmentController fragmentController = this.mFragments;
            Objects.requireNonNull(fragmentController);
            return fragmentController.mHost.mFragmentManager.dispatchOptionsItemSelected();
        } else if (i != 6) {
            return false;
        } else {
            FragmentController fragmentController2 = this.mFragments;
            Objects.requireNonNull(fragmentController2);
            return fragmentController2.mHost.mFragmentManager.dispatchContextItemSelected();
        }
    }

    public final void onPause() {
        super.onPause();
        this.mResumed = false;
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        FragmentManagerImpl fragmentManagerImpl = fragmentController.mHost.mFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.dispatchStateChange(5);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
    }

    public void onPostResume() {
        super.onPostResume();
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        FragmentManagerImpl fragmentManagerImpl = fragmentController.mHost.mFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.mStateSaved = false;
        fragmentManagerImpl.mStopped = false;
        FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel);
        fragmentManagerViewModel.mIsStateSaved = false;
        fragmentManagerImpl.dispatchStateChange(7);
    }

    public void onStop() {
        FragmentController fragmentController;
        super.onStop();
        this.mStopped = true;
        do {
            fragmentController = this.mFragments;
            Objects.requireNonNull(fragmentController);
        } while (markState(fragmentController.mHost.mFragmentManager));
        FragmentController fragmentController2 = this.mFragments;
        Objects.requireNonNull(fragmentController2);
        FragmentManagerImpl fragmentManagerImpl = fragmentController2.mHost.mFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        fragmentManagerImpl.mStopped = true;
        FragmentManagerViewModel fragmentManagerViewModel = fragmentManagerImpl.mNonConfig;
        Objects.requireNonNull(fragmentManagerViewModel);
        fragmentManagerViewModel.mIsStateSaved = true;
        fragmentManagerImpl.dispatchStateChange(4);
        this.mFragmentLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }

    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        FragmentController fragmentController = this.mFragments;
        Objects.requireNonNull(fragmentController);
        FragmentManagerImpl fragmentManagerImpl = fragmentController.mHost.mFragmentManager;
        Objects.requireNonNull(fragmentManagerImpl);
        View onCreateView = fragmentManagerImpl.mLayoutInflaterFactory.onCreateView((View) null, str, context, attributeSet);
        return onCreateView == null ? super.onCreateView(str, context, attributeSet) : onCreateView;
    }

    @Deprecated
    public void supportInvalidateOptionsMenu() {
        invalidateOptionsMenu();
    }
}
