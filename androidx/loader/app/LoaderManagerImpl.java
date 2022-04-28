package androidx.loader.app;

import android.util.Log;
import androidx.collection.SparseArrayCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.io.PrintWriter;
import java.util.Objects;

public final class LoaderManagerImpl extends LoaderManager {
    public final LifecycleOwner mLifecycleOwner;
    public final LoaderViewModel mLoaderViewModel;

    public static class LoaderInfo<D> extends MutableLiveData<D> {
        public final void onActive() {
            if (Log.isLoggable("LoaderManager", 2)) {
                Log.v("LoaderManager", "  Starting: " + this);
            }
            throw null;
        }

        public final void onInactive() {
            if (Log.isLoggable("LoaderManager", 2)) {
                Log.v("LoaderManager", "  Stopping: " + this);
            }
            throw null;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder(64);
            sb.append("LoaderInfo{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" #");
            sb.append(0);
            sb.append(" : ");
            throw null;
        }

        public final void removeObserver(Observer<? super D> observer) {
            super.removeObserver(observer);
        }

        public final void setValue(D d) {
            super.setValue(d);
        }
    }

    public static class LoaderViewModel extends ViewModel {
        public static final C02521 FACTORY = new ViewModelProvider.Factory() {
            public final ViewModel create() {
                return new LoaderViewModel();
            }
        };
        public SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat<>();

        public final void onCleared() {
            SparseArrayCompat<LoaderInfo> sparseArrayCompat = this.mLoaders;
            Objects.requireNonNull(sparseArrayCompat);
            if (sparseArrayCompat.mSize > 0) {
                SparseArrayCompat<LoaderInfo> sparseArrayCompat2 = this.mLoaders;
                Objects.requireNonNull(sparseArrayCompat2);
                LoaderInfo loaderInfo = (LoaderInfo) sparseArrayCompat2.mValues[0];
                Objects.requireNonNull(loaderInfo);
                if (Log.isLoggable("LoaderManager", 3)) {
                    Log.d("LoaderManager", "  Destroying: " + loaderInfo);
                }
                throw null;
            }
            SparseArrayCompat<LoaderInfo> sparseArrayCompat3 = this.mLoaders;
            Objects.requireNonNull(sparseArrayCompat3);
            int i = sparseArrayCompat3.mSize;
            Object[] objArr = sparseArrayCompat3.mValues;
            for (int i2 = 0; i2 < i; i2++) {
                objArr[i2] = null;
            }
            sparseArrayCompat3.mSize = 0;
        }
    }

    @Deprecated
    public final void dump(String str, PrintWriter printWriter) {
        LoaderViewModel loaderViewModel = this.mLoaderViewModel;
        Objects.requireNonNull(loaderViewModel);
        SparseArrayCompat<LoaderInfo> sparseArrayCompat = loaderViewModel.mLoaders;
        Objects.requireNonNull(sparseArrayCompat);
        if (sparseArrayCompat.mSize > 0) {
            printWriter.print(str);
            printWriter.println("Loaders:");
            SparseArrayCompat<LoaderInfo> sparseArrayCompat2 = loaderViewModel.mLoaders;
            Objects.requireNonNull(sparseArrayCompat2);
            if (sparseArrayCompat2.mSize > 0) {
                SparseArrayCompat<LoaderInfo> sparseArrayCompat3 = loaderViewModel.mLoaders;
                Objects.requireNonNull(sparseArrayCompat3);
                printWriter.print(str);
                printWriter.print("  #");
                SparseArrayCompat<LoaderInfo> sparseArrayCompat4 = loaderViewModel.mLoaders;
                Objects.requireNonNull(sparseArrayCompat4);
                printWriter.print(sparseArrayCompat4.mKeys[0]);
                printWriter.print(": ");
                ((LoaderInfo) sparseArrayCompat3.mValues[0]).toString();
                throw null;
            }
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("LoaderManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        sb.append(this.mLifecycleOwner.getClass().getSimpleName());
        sb.append("{");
        sb.append(Integer.toHexString(System.identityHashCode(this.mLifecycleOwner)));
        sb.append("}}");
        return sb.toString();
    }

    public LoaderManagerImpl(LifecycleOwner lifecycleOwner, ViewModelStore viewModelStore) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mLoaderViewModel = (LoaderViewModel) new ViewModelProvider(viewModelStore, LoaderViewModel.FACTORY).get(LoaderViewModel.class);
    }
}
