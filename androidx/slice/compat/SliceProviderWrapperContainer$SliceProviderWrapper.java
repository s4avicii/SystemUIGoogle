package androidx.slice.compat;

import android.app.PendingIntent;
import android.app.slice.Slice;
import android.app.slice.SliceManager;
import android.app.slice.SliceProvider;
import android.app.slice.SliceSpec;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import androidx.slice.SliceConvert;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public final class SliceProviderWrapperContainer$SliceProviderWrapper extends SliceProvider {
    public String[] mAutoGrantPermissions;
    public SliceManager mSliceManager;
    public androidx.slice.SliceProvider mSliceProvider;

    public final boolean onCreate() {
        return true;
    }

    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        this.mSliceProvider.attachInfo(context, providerInfo);
        super.attachInfo(context, providerInfo);
        this.mSliceManager = (SliceManager) context.getSystemService(SliceManager.class);
    }

    public final Bundle call(String str, String str2, Bundle bundle) {
        Intent intent;
        if (this.mAutoGrantPermissions != null) {
            Uri uri = null;
            if ("bind_slice".equals(str)) {
                if (bundle != null) {
                    uri = (Uri) bundle.getParcelable("slice_uri");
                }
            } else if ("map_slice".equals(str) && (intent = (Intent) bundle.getParcelable("slice_intent")) != null) {
                onMapIntentToUri(intent);
                throw null;
            }
            if (!(uri == null || this.mSliceManager.checkSlicePermission(uri, Binder.getCallingPid(), Binder.getCallingUid()) == 0)) {
                checkPermissions(uri);
            }
        }
        if ("androidx.remotecallback.method.PROVIDER_CALLBACK".equals(str)) {
            return this.mSliceProvider.call(str, str2, bundle);
        }
        return super.call(str, str2, bundle);
    }

    public final void checkPermissions(Uri uri) {
        if (uri != null) {
            for (String str : this.mAutoGrantPermissions) {
                if (getContext().checkCallingPermission(str) == 0) {
                    this.mSliceManager.grantSlicePermission(str, uri);
                    getContext().getContentResolver().notifyChange(uri, (ContentObserver) null);
                    return;
                }
            }
        }
    }

    public final PendingIntent onCreatePermissionRequest(Uri uri) {
        if (this.mAutoGrantPermissions != null) {
            checkPermissions(uri);
        }
        androidx.slice.SliceProvider sliceProvider = this.mSliceProvider;
        getCallingPackage();
        Objects.requireNonNull(sliceProvider);
        return super.onCreatePermissionRequest(uri);
    }

    public final Collection<Uri> onGetSliceDescendants(Uri uri) {
        Objects.requireNonNull(this.mSliceProvider);
        return Collections.emptyList();
    }

    public final Uri onMapIntentToUri(Intent intent) {
        Objects.requireNonNull(this.mSliceProvider);
        throw new UnsupportedOperationException("This provider has not implemented intent to uri mapping");
    }

    public final void onSlicePinned(Uri uri) {
        Objects.requireNonNull(this.mSliceProvider);
        androidx.slice.SliceProvider sliceProvider = this.mSliceProvider;
        Objects.requireNonNull(sliceProvider);
        ArrayList arrayList = (ArrayList) sliceProvider.getPinnedSlices();
        if (!arrayList.contains(uri)) {
            arrayList.add(uri);
        }
    }

    public final void onSliceUnpinned(Uri uri) {
        Objects.requireNonNull(this.mSliceProvider);
        androidx.slice.SliceProvider sliceProvider = this.mSliceProvider;
        Objects.requireNonNull(sliceProvider);
        ArrayList arrayList = (ArrayList) sliceProvider.getPinnedSlices();
        if (arrayList.contains(uri)) {
            arrayList.remove(uri);
        }
    }

    public SliceProviderWrapperContainer$SliceProviderWrapper(androidx.slice.SliceProvider sliceProvider, String[] strArr) {
        super(strArr);
        this.mAutoGrantPermissions = (strArr == null || strArr.length == 0) ? null : strArr;
        this.mSliceProvider = sliceProvider;
    }

    public final Slice onBindSlice(Uri uri, Set<SliceSpec> set) {
        androidx.slice.SliceProvider.sSpecs = SliceConvert.wrap(set);
        try {
            return SliceConvert.unwrap(this.mSliceProvider.onBindSlice());
        } catch (Exception e) {
            Log.wtf("SliceProviderWrapper", "Slice with URI " + uri.toString() + " is invalid.", e);
            return null;
        } finally {
            androidx.slice.SliceProvider.sSpecs = null;
        }
    }
}
