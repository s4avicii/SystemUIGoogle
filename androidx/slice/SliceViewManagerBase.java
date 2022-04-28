package androidx.slice;

import android.app.slice.SliceManager;
import android.content.ContentProviderClient;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Pair;
import androidx.slice.SliceViewManager;
import androidx.slice.widget.SliceLiveData;
import androidx.slice.widget.SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0;
import java.util.Objects;
import java.util.concurrent.Executor;

public abstract class SliceViewManagerBase extends SliceViewManager {
    public final Context mContext;
    public final ArrayMap<Pair<Uri, SliceViewManager.SliceCallback>, SliceListenerImpl> mListenerLookup = new ArrayMap<>();

    public class SliceListenerImpl {
        public final SliceViewManager.SliceCallback mCallback;
        public final Executor mExecutor;
        public final C03492 mObserver = new ContentObserver(new Handler(Looper.getMainLooper())) {
            public final void onChange(boolean z) {
                AsyncTask.execute(SliceListenerImpl.this.mUpdateSlice);
            }
        };
        public boolean mPinned;
        public final C03471 mUpdateSlice = new Runnable() {
            public final void run() {
                SliceListenerImpl sliceListenerImpl = SliceListenerImpl.this;
                Objects.requireNonNull(sliceListenerImpl);
                if (!sliceListenerImpl.mPinned) {
                    try {
                        SliceViewManagerBase.this.pinSlice(sliceListenerImpl.mUri);
                        sliceListenerImpl.mPinned = true;
                    } catch (SecurityException unused) {
                    }
                }
                SliceListenerImpl sliceListenerImpl2 = SliceListenerImpl.this;
                Context context = SliceViewManagerBase.this.mContext;
                final Slice wrap = SliceConvert.wrap(((SliceManager) context.getSystemService(SliceManager.class)).bindSlice(sliceListenerImpl2.mUri, SliceConvert.unwrap(SliceLiveData.SUPPORTED_SPECS)), context);
                SliceListenerImpl.this.mExecutor.execute(new Runnable() {
                    public final void run() {
                        SliceViewManager.SliceCallback sliceCallback = SliceListenerImpl.this.mCallback;
                        Slice slice = wrap;
                        SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 = (SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0) sliceCallback;
                        Objects.requireNonNull(sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0);
                        SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl = sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0.f$0;
                        Objects.requireNonNull(sliceLiveDataImpl);
                        sliceLiveDataImpl.postValue(slice);
                    }
                });
            }
        };
        public Uri mUri;

        public SliceListenerImpl(Uri uri, C03461 r5, SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0) {
            this.mUri = uri;
            this.mExecutor = r5;
            this.mCallback = sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0;
        }
    }

    public final void registerSliceCallback(Uri uri, SliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0 sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0) {
        final Handler handler = new Handler(Looper.getMainLooper());
        SliceListenerImpl sliceListenerImpl = new SliceListenerImpl(uri, new Executor() {
            public final void execute(Runnable runnable) {
                handler.post(runnable);
            }
        }, sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0);
        Pair pair = new Pair(uri, sliceLiveData$SliceLiveDataImpl$$ExternalSyntheticLambda0);
        synchronized (this.mListenerLookup) {
            try {
                SliceListenerImpl put = this.mListenerLookup.put(pair, sliceListenerImpl);
                if (put != null) {
                    SliceViewManagerBase.this.mContext.getContentResolver().unregisterContentObserver(put.mObserver);
                    if (put.mPinned) {
                        SliceViewManagerBase.this.unpinSlice(put.mUri);
                        put.mPinned = false;
                    }
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        ContentProviderClient acquireContentProviderClient = this.mContext.getContentResolver().acquireContentProviderClient(sliceListenerImpl.mUri);
        if (acquireContentProviderClient != null) {
            acquireContentProviderClient.release();
            this.mContext.getContentResolver().registerContentObserver(sliceListenerImpl.mUri, true, sliceListenerImpl.mObserver);
            if (!sliceListenerImpl.mPinned) {
                try {
                    pinSlice(sliceListenerImpl.mUri);
                    sliceListenerImpl.mPinned = true;
                } catch (SecurityException unused) {
                }
            }
        }
    }

    public SliceViewManagerBase(Context context) {
        this.mContext = context;
    }
}
