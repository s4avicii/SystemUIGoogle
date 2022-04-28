package com.android.systemui.statusbar.phone;

import android.app.ActivityManager;
import android.app.IWallpaperManager;
import android.app.IWallpaperManagerCallback;
import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.IndentingPrintWriter;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import libcore.io.IoUtils;

public final class LockscreenWallpaper extends IWallpaperManagerCallback.Stub implements Runnable, Dumpable {
    public Bitmap mCache;
    public boolean mCached;
    public int mCurrentUserId = ActivityManager.getCurrentUser();

    /* renamed from: mH */
    public final Handler f81mH;
    public AsyncTask<Void, Void, LoaderResult> mLoader;
    public final NotificationMediaManager mMediaManager;
    public final WallpaperManager mWallpaperManager;

    public static class WallpaperDrawable extends DrawableWrapper {
        public final ConstantState mState;
        public final Rect mTmpRect = new Rect();

        public static class ConstantState extends Drawable.ConstantState {
            public final Bitmap mBackground;

            public final int getChangingConfigurations() {
                return 0;
            }

            public final Drawable newDrawable() {
                return new WallpaperDrawable((Resources) null, this);
            }

            public final Drawable newDrawable(Resources resources) {
                return new WallpaperDrawable(resources, this);
            }

            public ConstantState(Bitmap bitmap) {
                this.mBackground = bitmap;
            }
        }

        public final int getIntrinsicHeight() {
            return -1;
        }

        public final int getIntrinsicWidth() {
            return -1;
        }

        public WallpaperDrawable(Resources resources, ConstantState constantState) {
            super(new BitmapDrawable(resources, constantState.mBackground));
            this.mState = constantState;
        }

        public final void onBoundsChange(Rect rect) {
            float f;
            float f2;
            int width = getBounds().width();
            int height = getBounds().height();
            int width2 = this.mState.mBackground.getWidth();
            int height2 = this.mState.mBackground.getHeight();
            if (width2 * height > width * height2) {
                f2 = (float) height;
                f = (float) height2;
            } else {
                f2 = (float) width;
                f = (float) width2;
            }
            float f3 = f2 / f;
            if (f3 <= 1.0f) {
                f3 = 1.0f;
            }
            float f4 = ((float) height2) * f3;
            float f5 = (((float) height) - f4) * 0.5f;
            this.mTmpRect.set(rect.left, Math.round(f5) + rect.top, Math.round(((float) width2) * f3) + rect.left, Math.round(f4 + f5) + rect.top);
            super.onBoundsChange(this.mTmpRect);
        }

        public final void setXfermode(Xfermode xfermode) {
            getDrawable().setXfermode(xfermode);
        }

        public final Drawable.ConstantState getConstantState() {
            return this.mState;
        }
    }

    public final void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
    }

    public static class LoaderResult {
        public final Bitmap bitmap;
        public final boolean success;

        public LoaderResult(boolean z, Bitmap bitmap2) {
            this.success = z;
            this.bitmap = bitmap2;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Class<LockscreenWallpaper> cls = LockscreenWallpaper.class;
        printWriter.println("LockscreenWallpaper:");
        IndentingPrintWriter increaseIndent = new IndentingPrintWriter(printWriter, "  ").increaseIndent();
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mCached=");
        m.append(this.mCached);
        increaseIndent.println(m.toString());
        increaseIndent.println("mCache=" + this.mCache);
        increaseIndent.println("mCurrentUserId=" + this.mCurrentUserId);
        increaseIndent.println("mSelectedUser=null");
    }

    public final LoaderResult loadBitmap(int i) {
        if (!this.mWallpaperManager.isWallpaperSupported()) {
            return new LoaderResult(true, (Bitmap) null);
        }
        ParcelFileDescriptor wallpaperFile = this.mWallpaperManager.getWallpaperFile(2, i);
        if (wallpaperFile == null) {
            return new LoaderResult(true, (Bitmap) null);
        }
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.HARDWARE;
            return new LoaderResult(true, BitmapFactory.decodeFileDescriptor(wallpaperFile.getFileDescriptor(), (Rect) null, options));
        } catch (OutOfMemoryError e) {
            Log.w("LockscreenWallpaper", "Can't decode file", e);
            return new LoaderResult(false, (Bitmap) null);
        } finally {
            IoUtils.closeQuietly(wallpaperFile);
        }
    }

    public final void onWallpaperChanged() {
        Handler handler = this.f81mH;
        if (handler == null) {
            Log.wtfStack("LockscreenWallpaper", "Trying to use LockscreenWallpaper before initialization.");
            return;
        }
        handler.removeCallbacks(this);
        this.f81mH.post(this);
    }

    public final void run() {
        AsyncTask<Void, Void, LoaderResult> asyncTask = this.mLoader;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        final int i = this.mCurrentUserId;
        this.mLoader = new AsyncTask<Void, Void, LoaderResult>() {
            public final Object doInBackground(Object[] objArr) {
                Void[] voidArr = (Void[]) objArr;
                return LockscreenWallpaper.this.loadBitmap(i);
            }

            public final void onPostExecute(Object obj) {
                LoaderResult loaderResult = (LoaderResult) obj;
                super.onPostExecute(loaderResult);
                if (!isCancelled()) {
                    if (loaderResult.success) {
                        LockscreenWallpaper lockscreenWallpaper = LockscreenWallpaper.this;
                        lockscreenWallpaper.mCached = true;
                        lockscreenWallpaper.mCache = loaderResult.bitmap;
                        lockscreenWallpaper.mMediaManager.updateMediaMetaData(true, true);
                    }
                    LockscreenWallpaper.this.mLoader = null;
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public LockscreenWallpaper(WallpaperManager wallpaperManager, IWallpaperManager iWallpaperManager, DumpManager dumpManager, NotificationMediaManager notificationMediaManager, Handler handler) {
        Class<LockscreenWallpaper> cls = LockscreenWallpaper.class;
        dumpManager.registerDumpable("LockscreenWallpaper", this);
        this.mWallpaperManager = wallpaperManager;
        this.mMediaManager = notificationMediaManager;
        this.f81mH = handler;
        if (iWallpaperManager != null) {
            try {
                iWallpaperManager.setLockWallpaperCallback(this);
            } catch (RemoteException e) {
                Log.e("LockscreenWallpaper", "System dead?" + e);
            }
        }
    }
}
