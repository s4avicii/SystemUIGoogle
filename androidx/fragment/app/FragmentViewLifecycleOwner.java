package androidx.fragment.app;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryController;
import androidx.savedstate.SavedStateRegistryOwner;
import java.util.Objects;

public final class FragmentViewLifecycleOwner implements SavedStateRegistryOwner, ViewModelStoreOwner {
    public LifecycleRegistry mLifecycleRegistry = null;
    public SavedStateRegistryController mSavedStateRegistryController = null;
    public final ViewModelStore mViewModelStore;

    public final void handleLifecycleEvent(Lifecycle.Event event) {
        this.mLifecycleRegistry.handleLifecycleEvent(event);
    }

    public final void initialize() {
        if (this.mLifecycleRegistry == null) {
            this.mLifecycleRegistry = new LifecycleRegistry(this, true);
            this.mSavedStateRegistryController = new SavedStateRegistryController(this);
        }
    }

    public FragmentViewLifecycleOwner(ViewModelStore viewModelStore) {
        this.mViewModelStore = viewModelStore;
    }

    public final Lifecycle getLifecycle() {
        initialize();
        return this.mLifecycleRegistry;
    }

    public final SavedStateRegistry getSavedStateRegistry() {
        initialize();
        SavedStateRegistryController savedStateRegistryController = this.mSavedStateRegistryController;
        Objects.requireNonNull(savedStateRegistryController);
        return savedStateRegistryController.mRegistry;
    }

    public final ViewModelStore getViewModelStore() {
        initialize();
        return this.mViewModelStore;
    }
}
