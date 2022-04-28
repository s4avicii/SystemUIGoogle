package androidx.startup;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Trace;
import com.android.p012wm.shell.C1777R;
import java.util.HashSet;
import java.util.Objects;

public class InitializationProvider extends ContentProvider {
    public final int delete(Uri uri, String str, String[] strArr) {
        throw new IllegalStateException("Not allowed.");
    }

    public final String getType(Uri uri) {
        throw new IllegalStateException("Not allowed.");
    }

    public final Uri insert(Uri uri, ContentValues contentValues) {
        throw new IllegalStateException("Not allowed.");
    }

    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        throw new IllegalStateException("Not allowed.");
    }

    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new IllegalStateException("Not allowed.");
    }

    public final boolean onCreate() {
        Context context = getContext();
        if (context == null) {
            throw new StartupException("Context cannot be null");
        } else if (context.getApplicationContext() == null) {
            return true;
        } else {
            if (AppInitializer.sInstance == null) {
                synchronized (AppInitializer.sLock) {
                    if (AppInitializer.sInstance == null) {
                        AppInitializer.sInstance = new AppInitializer(context);
                    }
                }
            }
            AppInitializer appInitializer = AppInitializer.sInstance;
            Objects.requireNonNull(appInitializer);
            try {
                Trace.beginSection("Startup");
                Bundle bundle = appInitializer.mContext.getPackageManager().getProviderInfo(new ComponentName(appInitializer.mContext.getPackageName(), InitializationProvider.class.getName()), 128).metaData;
                String string = appInitializer.mContext.getString(C1777R.string.androidx_startup);
                if (bundle != null) {
                    HashSet hashSet = new HashSet();
                    for (String next : bundle.keySet()) {
                        if (string.equals(bundle.getString(next, (String) null))) {
                            Class<?> cls = Class.forName(next);
                            if (Initializer.class.isAssignableFrom(cls)) {
                                appInitializer.mDiscovered.add(cls);
                                appInitializer.doInitialize(cls, hashSet);
                            }
                        }
                    }
                }
                Trace.endSection();
                return true;
            } catch (PackageManager.NameNotFoundException | ClassNotFoundException e) {
                throw new StartupException(e);
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }
    }
}
