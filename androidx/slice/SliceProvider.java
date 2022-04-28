package androidx.slice;

import android.app.slice.SliceManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Process;
import androidx.core.app.CoreComponentFactory;
import androidx.slice.compat.CompatPermissionManager;
import androidx.slice.compat.SliceProviderWrapperContainer$SliceProviderWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class SliceProvider extends ContentProvider implements CoreComponentFactory.CompatWrapped {
    public static Set<SliceSpec> sSpecs;
    public String[] mAuthorities;
    public String mAuthority;
    public final String[] mAutoGrantPermissions = new String[0];
    public final Object mCompatLock = new Object();
    public Context mContext = null;
    public ArrayList mPinnedSliceUris;
    public final Object mPinnedSliceUrisLock = new Object();

    public final int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        return 0;
    }

    public final Bundle call(String str, String str2, Bundle bundle) {
        return null;
    }

    public final Uri canonicalize(Uri uri) {
        return null;
    }

    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public final String getType(Uri uri) {
        return "vnd.android.slice";
    }

    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public abstract Slice onBindSlice();

    public abstract void onCreateSliceProvider();

    public final Cursor query(Uri uri, String[] strArr, Bundle bundle, CancellationSignal cancellationSignal) {
        return null;
    }

    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) {
        return null;
    }

    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    public final List<Uri> getPinnedSlices() {
        synchronized (this.mPinnedSliceUrisLock) {
            try {
                if (this.mPinnedSliceUris == null) {
                    this.mPinnedSliceUris = new ArrayList(((SliceManager) getContext().getSystemService(SliceManager.class)).getPinnedSlices());
                }
            } catch (Throwable th) {
                while (true) {
                    throw th;
                }
            }
        }
        return this.mPinnedSliceUris;
    }

    public final SliceProviderWrapperContainer$SliceProviderWrapper getWrapper() {
        return new SliceProviderWrapperContainer$SliceProviderWrapper(this, this.mAutoGrantPermissions);
    }

    public CompatPermissionManager onCreatePermissionManager(String[] strArr) {
        Context context = getContext();
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("slice_perms_");
        m.append(getClass().getName());
        return new CompatPermissionManager(context, m.toString(), Process.myUid(), strArr);
    }

    public final void attachInfo(Context context, ProviderInfo providerInfo) {
        String str;
        super.attachInfo(context, providerInfo);
        if (this.mContext == null) {
            this.mContext = context;
            if (providerInfo != null && (str = providerInfo.authority) != null) {
                if (str.indexOf(59) == -1) {
                    this.mAuthority = str;
                    this.mAuthorities = null;
                    return;
                }
                this.mAuthority = null;
                this.mAuthorities = str.split(";");
            }
        }
    }

    public final boolean onCreate() {
        onCreateSliceProvider();
        return true;
    }
}
