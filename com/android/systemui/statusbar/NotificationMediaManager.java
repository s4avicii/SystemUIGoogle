package com.android.systemui.statusbar;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.AsyncTask;
import android.os.Trace;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.widget.ImageView;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaData;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.SmartspaceMediaData;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.LockscreenWallpaper;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.Utils;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.wmshell.WMShell$$ExternalSyntheticLambda6;
import com.android.wifitrackerlib.SavedNetworkTracker$$ExternalSyntheticLambda1;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;

public final class NotificationMediaManager implements Dumpable {
    public static final HashSet<Integer> PAUSED_MEDIA_STATES;
    public BackDropView mBackdrop;
    public ImageView mBackdropBack;
    public ImageView mBackdropFront;
    public BiometricUnlockController mBiometricUnlockController;
    public final SysuiColorExtractor mColorExtractor = ((SysuiColorExtractor) Dependency.get(SysuiColorExtractor.class));
    public final Context mContext;
    public final NotificationEntryManager mEntryManager;
    public final C11687 mHideBackdropFront = new Runnable() {
        public final void run() {
            NotificationMediaManager.this.mBackdropFront.setVisibility(4);
            NotificationMediaManager.this.mBackdropFront.animate().cancel();
            NotificationMediaManager.this.mBackdropFront.setImageDrawable((Drawable) null);
        }
    };
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardStateController mKeyguardStateController = ((KeyguardStateController) Dependency.get(KeyguardStateController.class));
    public LockscreenWallpaper mLockscreenWallpaper;
    public final DelayableExecutor mMainExecutor;
    public final MediaArtworkProcessor mMediaArtworkProcessor;
    public MediaController mMediaController;
    public final MediaDataManager mMediaDataManager;
    public final C11621 mMediaListener = new MediaController.Callback() {
        public final void onMetadataChanged(MediaMetadata mediaMetadata) {
            super.onMetadataChanged(mediaMetadata);
            Objects.requireNonNull(NotificationMediaManager.this.mMediaArtworkProcessor);
            NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
            notificationMediaManager.mMediaMetadata = mediaMetadata;
            notificationMediaManager.dispatchUpdateMediaMetaData(true);
        }

        public final void onPlaybackStateChanged(PlaybackState playbackState) {
            super.onPlaybackStateChanged(playbackState);
            if (playbackState != null) {
                NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                int state = playbackState.getState();
                Objects.requireNonNull(notificationMediaManager);
                boolean z = true;
                if (state == 1 || state == 7 || state == 0) {
                    z = false;
                }
                if (!z) {
                    NotificationMediaManager notificationMediaManager2 = NotificationMediaManager.this;
                    Objects.requireNonNull(notificationMediaManager2);
                    notificationMediaManager2.mMediaNotificationKey = null;
                    Objects.requireNonNull(notificationMediaManager2.mMediaArtworkProcessor);
                    notificationMediaManager2.mMediaMetadata = null;
                    MediaController mediaController = notificationMediaManager2.mMediaController;
                    if (mediaController != null) {
                        mediaController.unregisterCallback(notificationMediaManager2.mMediaListener);
                    }
                    notificationMediaManager2.mMediaController = null;
                }
                NotificationMediaManager.this.findAndUpdateMediaNotifications();
            }
        }
    };
    public final ArrayList<MediaListener> mMediaListeners;
    public MediaMetadata mMediaMetadata;
    public String mMediaNotificationKey;
    public final NotifCollection mNotifCollection;
    public final NotifPipeline mNotifPipeline;
    public Lazy<NotificationShadeWindowController> mNotificationShadeWindowController;
    public NotificationPresenter mPresenter;
    public final ArraySet mProcessArtworkTasks = new ArraySet();
    public ScrimController mScrimController;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final StatusBarStateController mStatusBarStateController = ((StatusBarStateController) Dependency.get(StatusBarStateController.class));
    public final boolean mUsingNotifPipeline;
    public final NotificationVisibilityProvider mVisibilityProvider;

    public interface MediaListener {
        void onPrimaryMetadataOrStateChanged(MediaMetadata mediaMetadata, int i) {
        }
    }

    public static final class ProcessArtworkTask extends AsyncTask<Bitmap, Void, Bitmap> {
        public final boolean mAllowEnterAnimation;
        public final WeakReference<NotificationMediaManager> mManagerRef;
        public final boolean mMetaDataChanged;

        /* JADX WARNING: Removed duplicated region for block: B:50:0x00ef  */
        /* JADX WARNING: Removed duplicated region for block: B:52:0x00f5  */
        /* JADX WARNING: Removed duplicated region for block: B:55:0x00fe  */
        /* JADX WARNING: Removed duplicated region for block: B:61:0x010c  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x0112  */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x011b  */
        /* JADX WARNING: Removed duplicated region for block: B:73:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object doInBackground(java.lang.Object[] r8) {
            /*
                r7 = this;
                android.graphics.Bitmap[] r8 = (android.graphics.Bitmap[]) r8
                java.lang.ref.WeakReference<com.android.systemui.statusbar.NotificationMediaManager> r0 = r7.mManagerRef
                java.lang.Object r0 = r0.get()
                com.android.systemui.statusbar.NotificationMediaManager r0 = (com.android.systemui.statusbar.NotificationMediaManager) r0
                r1 = 0
                if (r0 == 0) goto L_0x011f
                int r2 = r8.length
                if (r2 == 0) goto L_0x011f
                boolean r7 = r7.isCancelled()
                if (r7 == 0) goto L_0x0018
                goto L_0x011f
            L_0x0018:
                r7 = 0
                r8 = r8[r7]
                com.android.systemui.statusbar.MediaArtworkProcessor r2 = r0.mMediaArtworkProcessor
                android.content.Context r0 = r0.mContext
                java.util.Objects.requireNonNull(r2)
                android.renderscript.RenderScript r3 = android.renderscript.RenderScript.create(r0)
                android.renderscript.Element r4 = android.renderscript.Element.U8_4(r3)
                android.renderscript.ScriptIntrinsicBlur r4 = android.renderscript.ScriptIntrinsicBlur.create(r3, r4)
                android.view.Display r0 = r0.getDisplay()     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                if (r0 != 0) goto L_0x0035
                goto L_0x003a
            L_0x0035:
                android.graphics.Point r5 = r2.mTmpSize     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                r0.getSize(r5)     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
            L_0x003a:
                android.graphics.Rect r0 = new android.graphics.Rect     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                int r5 = r8.getWidth()     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                int r6 = r8.getHeight()     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                r0.<init>(r7, r7, r5, r6)     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                android.graphics.Point r2 = r2.mTmpSize     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                int r5 = r2.x     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                int r5 = r5 / 6
                int r2 = r2.y     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                int r2 = r2 / 6
                int r2 = java.lang.Math.max(r5, r2)     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                android.util.MathUtils.fitRect(r0, r2)     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                int r2 = r0.width()     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                int r0 = r0.height()     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                r5 = 1
                android.graphics.Bitmap r0 = android.graphics.Bitmap.createScaledBitmap(r8, r2, r0, r5)     // Catch:{ IllegalArgumentException -> 0x00de, all -> 0x00d9 }
                android.graphics.Bitmap$Config r2 = r0.getConfig()     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                if (r2 == r5) goto L_0x007e
                android.graphics.Bitmap r7 = r0.copy(r5, r7)     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                r0.recycle()     // Catch:{ IllegalArgumentException -> 0x007b, all -> 0x0076 }
                r0 = r7
                goto L_0x007e
            L_0x0076:
                r8 = move-exception
                r0 = r7
                r7 = r1
                goto L_0x0109
            L_0x007b:
                r8 = move-exception
                goto L_0x00e1
            L_0x007e:
                int r7 = r0.getWidth()     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                int r2 = r0.getHeight()     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                android.graphics.Bitmap$Config r5 = android.graphics.Bitmap.Config.ARGB_8888     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                android.graphics.Bitmap r7 = android.graphics.Bitmap.createBitmap(r7, r2, r5)     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                android.renderscript.Allocation$MipmapControl r2 = android.renderscript.Allocation.MipmapControl.MIPMAP_NONE     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                r5 = 2
                android.renderscript.Allocation r2 = android.renderscript.Allocation.createFromBitmap(r3, r0, r2, r5)     // Catch:{ IllegalArgumentException -> 0x00d7, all -> 0x00d2 }
                android.renderscript.Allocation r3 = android.renderscript.Allocation.createFromBitmap(r3, r7)     // Catch:{ IllegalArgumentException -> 0x00cf, all -> 0x00cd }
                r5 = 1103626240(0x41c80000, float:25.0)
                r4.setRadius(r5)     // Catch:{ IllegalArgumentException -> 0x00cb }
                r4.setInput(r2)     // Catch:{ IllegalArgumentException -> 0x00cb }
                r4.forEach(r3)     // Catch:{ IllegalArgumentException -> 0x00cb }
                r3.copyTo(r7)     // Catch:{ IllegalArgumentException -> 0x00cb }
                androidx.palette.graphics.Palette$Swatch r8 = com.android.systemui.statusbar.notification.MediaNotificationProcessor.findBackgroundSwatch(r8)     // Catch:{ IllegalArgumentException -> 0x00cb }
                android.graphics.Canvas r5 = new android.graphics.Canvas     // Catch:{ IllegalArgumentException -> 0x00cb }
                r5.<init>(r7)     // Catch:{ IllegalArgumentException -> 0x00cb }
                int r8 = r8.mRgb     // Catch:{ IllegalArgumentException -> 0x00cb }
                r6 = 178(0xb2, float:2.5E-43)
                int r8 = com.android.internal.graphics.ColorUtils.setAlphaComponent(r8, r6)     // Catch:{ IllegalArgumentException -> 0x00cb }
                r5.drawColor(r8)     // Catch:{ IllegalArgumentException -> 0x00cb }
                if (r2 != 0) goto L_0x00bc
                goto L_0x00bf
            L_0x00bc:
                r2.destroy()
            L_0x00bf:
                r3.destroy()
                r4.destroy()
                r0.recycle()
                r1 = r7
                goto L_0x011f
            L_0x00cb:
                r7 = move-exception
                goto L_0x00e5
            L_0x00cd:
                r7 = move-exception
                goto L_0x0104
            L_0x00cf:
                r7 = move-exception
                r3 = r1
                goto L_0x00e5
            L_0x00d2:
                r7 = move-exception
                r8 = r7
                r7 = r1
                r2 = r7
                goto L_0x0106
            L_0x00d7:
                r7 = move-exception
                goto L_0x00e3
            L_0x00d9:
                r7 = move-exception
                r8 = r7
                r7 = r1
                r0 = r7
                goto L_0x0109
            L_0x00de:
                r7 = move-exception
                r8 = r7
                r7 = r1
            L_0x00e1:
                r0 = r7
                r7 = r8
            L_0x00e3:
                r2 = r1
                r3 = r2
            L_0x00e5:
                java.lang.String r8 = "MediaArtworkProcessor"
                java.lang.String r5 = "error while processing artwork"
                android.util.Log.e(r8, r5, r7)     // Catch:{ all -> 0x0102 }
                if (r2 != 0) goto L_0x00ef
                goto L_0x00f2
            L_0x00ef:
                r2.destroy()
            L_0x00f2:
                if (r3 != 0) goto L_0x00f5
                goto L_0x00f8
            L_0x00f5:
                r3.destroy()
            L_0x00f8:
                r4.destroy()
                if (r0 != 0) goto L_0x00fe
                goto L_0x011f
            L_0x00fe:
                r0.recycle()
                goto L_0x011f
            L_0x0102:
                r7 = move-exception
                r1 = r3
            L_0x0104:
                r8 = r7
                r7 = r1
            L_0x0106:
                r1 = r0
                r0 = r1
                r1 = r2
            L_0x0109:
                if (r1 != 0) goto L_0x010c
                goto L_0x010f
            L_0x010c:
                r1.destroy()
            L_0x010f:
                if (r7 != 0) goto L_0x0112
                goto L_0x0115
            L_0x0112:
                r7.destroy()
            L_0x0115:
                r4.destroy()
                if (r0 != 0) goto L_0x011b
                goto L_0x011e
            L_0x011b:
                r0.recycle()
            L_0x011e:
                throw r8
            L_0x011f:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationMediaManager.ProcessArtworkTask.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        public final void onCancelled(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            if (bitmap != null) {
                bitmap.recycle();
            }
            NotificationMediaManager notificationMediaManager = this.mManagerRef.get();
            if (notificationMediaManager != null) {
                notificationMediaManager.mProcessArtworkTasks.remove(this);
            }
        }

        public final void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            NotificationMediaManager notificationMediaManager = this.mManagerRef.get();
            if (notificationMediaManager != null && !isCancelled()) {
                notificationMediaManager.mProcessArtworkTasks.remove(this);
                notificationMediaManager.finishUpdateMediaMetaData(this.mMetaDataChanged, this.mAllowEnterAnimation, bitmap);
            }
        }

        public ProcessArtworkTask(NotificationMediaManager notificationMediaManager, boolean z, boolean z2) {
            this.mManagerRef = new WeakReference<>(notificationMediaManager);
            this.mMetaDataChanged = z;
            this.mAllowEnterAnimation = z2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x008d  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x0135  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0139  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x019d  */
    /* JADX WARNING: Removed duplicated region for block: B:98:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void finishUpdateMediaMetaData(boolean r16, boolean r17, android.graphics.Bitmap r18) {
        /*
            r15 = this;
            r0 = r15
            r1 = r18
            com.android.systemui.statusbar.phone.ScrimState r2 = com.android.systemui.statusbar.phone.ScrimState.AOD
            r3 = 0
            if (r1 == 0) goto L_0x0014
            android.graphics.drawable.BitmapDrawable r4 = new android.graphics.drawable.BitmapDrawable
            android.widget.ImageView r5 = r0.mBackdropBack
            android.content.res.Resources r5 = r5.getResources()
            r4.<init>(r5, r1)
            goto L_0x0015
        L_0x0014:
            r4 = r3
        L_0x0015:
            r1 = 0
            r5 = 1
            if (r4 == 0) goto L_0x001b
            r6 = r5
            goto L_0x001c
        L_0x001b:
            r6 = r1
        L_0x001c:
            if (r4 != 0) goto L_0x0066
            com.android.systemui.statusbar.phone.LockscreenWallpaper r7 = r0.mLockscreenWallpaper
            if (r7 == 0) goto L_0x0049
            boolean r8 = r7.mCached
            if (r8 == 0) goto L_0x0029
            android.graphics.Bitmap r7 = r7.mCache
            goto L_0x004a
        L_0x0029:
            android.app.WallpaperManager r8 = r7.mWallpaperManager
            boolean r8 = r8.isWallpaperSupported()
            if (r8 != 0) goto L_0x0036
            r7.mCached = r5
            r7.mCache = r3
            goto L_0x0049
        L_0x0036:
            int r8 = r7.mCurrentUserId
            com.android.systemui.statusbar.phone.LockscreenWallpaper$LoaderResult r8 = r7.loadBitmap(r8)
            boolean r9 = r8.success
            if (r9 == 0) goto L_0x0046
            r7.mCached = r5
            android.graphics.Bitmap r8 = r8.bitmap
            r7.mCache = r8
        L_0x0046:
            android.graphics.Bitmap r7 = r7.mCache
            goto L_0x004a
        L_0x0049:
            r7 = r3
        L_0x004a:
            if (r7 == 0) goto L_0x0066
            com.android.systemui.statusbar.phone.LockscreenWallpaper$WallpaperDrawable r4 = new com.android.systemui.statusbar.phone.LockscreenWallpaper$WallpaperDrawable
            android.widget.ImageView r8 = r0.mBackdropBack
            android.content.res.Resources r8 = r8.getResources()
            com.android.systemui.statusbar.phone.LockscreenWallpaper$WallpaperDrawable$ConstantState r9 = new com.android.systemui.statusbar.phone.LockscreenWallpaper$WallpaperDrawable$ConstantState
            r9.<init>(r7)
            r4.<init>(r8, r9)
            com.android.systemui.plugins.statusbar.StatusBarStateController r7 = r0.mStatusBarStateController
            int r7 = r7.getState()
            if (r7 != r5) goto L_0x0066
            r7 = r5
            goto L_0x0067
        L_0x0066:
            r7 = r1
        L_0x0067:
            dagger.Lazy<com.android.systemui.statusbar.NotificationShadeWindowController> r8 = r0.mNotificationShadeWindowController
            java.lang.Object r8 = r8.get()
            com.android.systemui.statusbar.NotificationShadeWindowController r8 = (com.android.systemui.statusbar.NotificationShadeWindowController) r8
            dagger.Lazy<java.util.Optional<com.android.systemui.statusbar.phone.StatusBar>> r9 = r0.mStatusBarOptionalLazy
            java.lang.Object r9 = r9.get()
            java.util.Optional r9 = (java.util.Optional) r9
            com.android.systemui.statusbar.NotificationMediaManager$$ExternalSyntheticLambda0 r10 = com.android.systemui.statusbar.NotificationMediaManager$$ExternalSyntheticLambda0.INSTANCE
            java.util.Optional r9 = r9.map(r10)
            java.lang.Boolean r10 = java.lang.Boolean.FALSE
            java.lang.Object r9 = r9.orElse(r10)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r4 == 0) goto L_0x008d
            r10 = r5
            goto L_0x008e
        L_0x008d:
            r10 = r1
        L_0x008e:
            com.android.systemui.colorextraction.SysuiColorExtractor r11 = r0.mColorExtractor
            r11.setHasMediaArtwork(r6)
            com.android.systemui.statusbar.phone.ScrimController r6 = r0.mScrimController
            if (r6 == 0) goto L_0x00e7
            com.android.systemui.statusbar.phone.ScrimState[] r11 = com.android.systemui.statusbar.phone.ScrimState.values()
            int r12 = r11.length
            r13 = r1
        L_0x009d:
            if (r13 >= r12) goto L_0x00a9
            r14 = r11[r13]
            java.util.Objects.requireNonNull(r14)
            r14.mHasBackdrop = r10
            int r13 = r13 + 1
            goto L_0x009d
        L_0x00a9:
            com.android.systemui.statusbar.phone.ScrimState r11 = r6.mState
            if (r11 == r2) goto L_0x00b1
            com.android.systemui.statusbar.phone.ScrimState r12 = com.android.systemui.statusbar.phone.ScrimState.PULSING
            if (r11 != r12) goto L_0x00e7
        L_0x00b1:
            java.util.Objects.requireNonNull(r11)
            float r11 = r11.mBehindAlpha
            boolean r12 = java.lang.Float.isNaN(r11)
            if (r12 != 0) goto L_0x00c8
            float r12 = r6.mBehindAlpha
            int r12 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
            if (r12 == 0) goto L_0x00e7
            r6.mBehindAlpha = r11
            r6.updateScrims()
            goto L_0x00e7
        L_0x00c8:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Scrim opacity is NaN for state: "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            com.android.systemui.statusbar.phone.ScrimState r2 = r6.mState
            r1.append(r2)
            java.lang.String r2 = ", back: "
            r1.append(r2)
            float r2 = r6.mBehindAlpha
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00e7:
            r6 = 2
            r11 = 0
            if (r10 != 0) goto L_0x00ed
            goto L_0x0193
        L_0x00ed:
            com.android.systemui.plugins.statusbar.StatusBarStateController r10 = r0.mStatusBarStateController
            int r10 = r10.getState()
            if (r10 != 0) goto L_0x00f7
            if (r7 == 0) goto L_0x0193
        L_0x00f7:
            com.android.systemui.statusbar.phone.BiometricUnlockController r7 = r0.mBiometricUnlockController
            if (r7 == 0) goto L_0x0193
            int r7 = r7.mMode
            if (r7 == r6) goto L_0x0193
            if (r9 != 0) goto L_0x0193
            com.android.systemui.statusbar.BackDropView r2 = r0.mBackdrop
            int r2 = r2.getVisibility()
            r3 = 1065353216(0x3f800000, float:1.0)
            if (r2 == 0) goto L_0x0135
            com.android.systemui.statusbar.BackDropView r2 = r0.mBackdrop
            r2.setVisibility(r1)
            if (r17 == 0) goto L_0x0121
            com.android.systemui.statusbar.BackDropView r2 = r0.mBackdrop
            r2.setAlpha(r11)
            com.android.systemui.statusbar.BackDropView r2 = r0.mBackdrop
            android.view.ViewPropertyAnimator r2 = r2.animate()
            r2.alpha(r3)
            goto L_0x012f
        L_0x0121:
            com.android.systemui.statusbar.BackDropView r2 = r0.mBackdrop
            android.view.ViewPropertyAnimator r2 = r2.animate()
            r2.cancel()
            com.android.systemui.statusbar.BackDropView r2 = r0.mBackdrop
            r2.setAlpha(r3)
        L_0x012f:
            if (r8 == 0) goto L_0x0137
            r8.setBackdropShowing(r5)
            goto L_0x0137
        L_0x0135:
            r5 = r16
        L_0x0137:
            if (r5 == 0) goto L_0x0224
            android.widget.ImageView r2 = r0.mBackdropBack
            android.graphics.drawable.Drawable r2 = r2.getDrawable()
            if (r2 == 0) goto L_0x0169
            android.widget.ImageView r2 = r0.mBackdropBack
            android.graphics.drawable.Drawable r2 = r2.getDrawable()
            android.graphics.drawable.Drawable$ConstantState r2 = r2.getConstantState()
            android.widget.ImageView r5 = r0.mBackdropFront
            android.content.res.Resources r5 = r5.getResources()
            android.graphics.drawable.Drawable r2 = r2.newDrawable(r5)
            android.graphics.drawable.Drawable r2 = r2.mutate()
            android.widget.ImageView r5 = r0.mBackdropFront
            r5.setImageDrawable(r2)
            android.widget.ImageView r2 = r0.mBackdropFront
            r2.setAlpha(r3)
            android.widget.ImageView r2 = r0.mBackdropFront
            r2.setVisibility(r1)
            goto L_0x016f
        L_0x0169:
            android.widget.ImageView r1 = r0.mBackdropFront
            r2 = 4
            r1.setVisibility(r2)
        L_0x016f:
            android.widget.ImageView r1 = r0.mBackdropBack
            r1.setImageDrawable(r4)
            android.widget.ImageView r1 = r0.mBackdropFront
            int r1 = r1.getVisibility()
            if (r1 != 0) goto L_0x0224
            android.widget.ImageView r1 = r0.mBackdropFront
            android.view.ViewPropertyAnimator r1 = r1.animate()
            r2 = 250(0xfa, double:1.235E-321)
            android.view.ViewPropertyAnimator r1 = r1.setDuration(r2)
            android.view.ViewPropertyAnimator r1 = r1.alpha(r11)
            com.android.systemui.statusbar.NotificationMediaManager$7 r0 = r0.mHideBackdropFront
            r1.withEndAction(r0)
            goto L_0x0224
        L_0x0193:
            com.android.systemui.statusbar.BackDropView r4 = r0.mBackdrop
            int r4 = r4.getVisibility()
            r7 = 8
            if (r4 == r7) goto L_0x0224
            com.android.systemui.plugins.statusbar.StatusBarStateController r4 = r0.mStatusBarStateController
            boolean r4 = r4.isDozing()
            if (r4 == 0) goto L_0x01aa
            boolean r2 = r2.mAnimateChange
            if (r2 != 0) goto L_0x01aa
            goto L_0x01ab
        L_0x01aa:
            r5 = r1
        L_0x01ab:
            com.android.systemui.statusbar.policy.KeyguardStateController r2 = r0.mKeyguardStateController
            boolean r2 = r2.isBypassFadingAnimation()
            com.android.systemui.statusbar.phone.BiometricUnlockController r4 = r0.mBiometricUnlockController
            if (r4 == 0) goto L_0x01b9
            int r4 = r4.mMode
            if (r4 == r6) goto L_0x01bb
        L_0x01b9:
            if (r5 == 0) goto L_0x01bd
        L_0x01bb:
            if (r2 == 0) goto L_0x01bf
        L_0x01bd:
            if (r9 == 0) goto L_0x01cf
        L_0x01bf:
            com.android.systemui.statusbar.BackDropView r2 = r0.mBackdrop
            r2.setVisibility(r7)
            android.widget.ImageView r0 = r0.mBackdropBack
            r0.setImageDrawable(r3)
            if (r8 == 0) goto L_0x0224
            r8.setBackdropShowing(r1)
            goto L_0x0224
        L_0x01cf:
            if (r8 == 0) goto L_0x01d4
            r8.setBackdropShowing(r1)
        L_0x01d4:
            com.android.systemui.statusbar.BackDropView r1 = r0.mBackdrop
            android.view.ViewPropertyAnimator r1 = r1.animate()
            android.view.ViewPropertyAnimator r1 = r1.alpha(r11)
            android.view.animation.AccelerateDecelerateInterpolator r2 = com.android.systemui.animation.Interpolators.ACCELERATE_DECELERATE
            android.view.ViewPropertyAnimator r1 = r1.setInterpolator(r2)
            r2 = 300(0x12c, double:1.48E-321)
            android.view.ViewPropertyAnimator r1 = r1.setDuration(r2)
            r2 = 0
            android.view.ViewPropertyAnimator r1 = r1.setStartDelay(r2)
            com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0 r2 = new com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0
            r3 = 3
            r2.<init>(r15, r3)
            r1.withEndAction(r2)
            com.android.systemui.statusbar.policy.KeyguardStateController r1 = r0.mKeyguardStateController
            boolean r1 = r1.isKeyguardFadingAway()
            if (r1 == 0) goto L_0x0224
            com.android.systemui.statusbar.BackDropView r1 = r0.mBackdrop
            android.view.ViewPropertyAnimator r1 = r1.animate()
            com.android.systemui.statusbar.policy.KeyguardStateController r2 = r0.mKeyguardStateController
            long r2 = r2.getShortenedFadingAwayDuration()
            android.view.ViewPropertyAnimator r1 = r1.setDuration(r2)
            com.android.systemui.statusbar.policy.KeyguardStateController r0 = r0.mKeyguardStateController
            long r2 = r0.getKeyguardFadingAwayDelay()
            android.view.ViewPropertyAnimator r0 = r1.setStartDelay(r2)
            android.view.animation.LinearInterpolator r1 = com.android.systemui.animation.Interpolators.LINEAR
            android.view.ViewPropertyAnimator r0 = r0.setInterpolator(r1)
            r0.start()
        L_0x0224:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationMediaManager.finishUpdateMediaMetaData(boolean, boolean, android.graphics.Bitmap):void");
    }

    static {
        HashSet<Integer> hashSet = new HashSet<>();
        PAUSED_MEDIA_STATES = hashSet;
        hashSet.add(0);
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(7);
        hashSet.add(8);
    }

    public static boolean isPlayingState(int i) {
        return !PAUSED_MEDIA_STATES.contains(Integer.valueOf(i));
    }

    public final void addCallback(MediaListener mediaListener) {
        int i;
        PlaybackState playbackState;
        this.mMediaListeners.add(mediaListener);
        MediaMetadata mediaMetadata = this.mMediaMetadata;
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
            i = 0;
        } else {
            i = playbackState.getState();
        }
        mediaListener.onPrimaryMetadataOrStateChanged(mediaMetadata, i);
    }

    public final void dispatchUpdateMediaMetaData(boolean z) {
        int i;
        PlaybackState playbackState;
        NotificationPresenter notificationPresenter = this.mPresenter;
        if (notificationPresenter != null) {
            ((StatusBarNotificationPresenter) notificationPresenter).updateMediaMetaData(z, true);
        }
        MediaController mediaController = this.mMediaController;
        if (mediaController == null || (playbackState = mediaController.getPlaybackState()) == null) {
            i = 0;
        } else {
            i = playbackState.getState();
        }
        ArrayList arrayList = new ArrayList(this.mMediaListeners);
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            ((MediaListener) arrayList.get(i2)).onPrimaryMetadataOrStateChanged(this.mMediaMetadata, i);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.print("    mMediaNotificationKey=");
        printWriter.println(this.mMediaNotificationKey);
        printWriter.print("    mMediaController=");
        printWriter.print(this.mMediaController);
        if (this.mMediaController != null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" state=");
            m.append(this.mMediaController.getPlaybackState());
            printWriter.print(m.toString());
        }
        printWriter.println();
        printWriter.print("    mMediaMetadata=");
        printWriter.print(this.mMediaMetadata);
        if (this.mMediaMetadata != null) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(" title=");
            m2.append(this.mMediaMetadata.getText("android.media.metadata.TITLE"));
            printWriter.print(m2.toString());
        }
        printWriter.println();
    }

    public final void findAndUpdateMediaNotifications() {
        boolean z;
        boolean findPlayingMediaNotification;
        if (this.mUsingNotifPipeline) {
            z = findPlayingMediaNotification(this.mNotifPipeline.getAllNotifs());
        } else {
            synchronized (this.mEntryManager) {
                findPlayingMediaNotification = findPlayingMediaNotification(this.mEntryManager.getAllNotifs());
            }
            if (findPlayingMediaNotification) {
                this.mEntryManager.updateNotifications("NotificationMediaManager - metaDataChanged");
            }
            z = findPlayingMediaNotification;
        }
        dispatchUpdateMediaMetaData(z);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x004f, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.drawable.Icon getMediaIcon() {
        /*
            r3 = this;
            java.lang.String r0 = r3.mMediaNotificationKey
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            boolean r2 = r3.mUsingNotifPipeline
            if (r2 == 0) goto L_0x0027
            com.android.systemui.statusbar.notification.collection.NotifPipeline r3 = r3.mNotifPipeline
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r3.getEntry(r0)
            java.util.Optional r3 = java.util.Optional.ofNullable(r3)
            com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda4 r0 = com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda4.INSTANCE$1
            java.util.Optional r3 = r3.map(r0)
            com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda3 r0 = com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda3.INSTANCE$1
            java.util.Optional r3 = r3.map(r0)
            java.lang.Object r3 = r3.orElse(r1)
            android.graphics.drawable.Icon r3 = (android.graphics.drawable.Icon) r3
            return r3
        L_0x0027:
            com.android.systemui.statusbar.notification.NotificationEntryManager r0 = r3.mEntryManager
            monitor-enter(r0)
            com.android.systemui.statusbar.notification.NotificationEntryManager r2 = r3.mEntryManager     // Catch:{ all -> 0x0050 }
            java.lang.String r3 = r3.mMediaNotificationKey     // Catch:{ all -> 0x0050 }
            com.android.systemui.statusbar.notification.collection.NotificationEntry r3 = r2.getActiveNotificationUnfiltered(r3)     // Catch:{ all -> 0x0050 }
            if (r3 == 0) goto L_0x004e
            com.android.systemui.statusbar.notification.icon.IconPack r2 = r3.mIcons     // Catch:{ all -> 0x0050 }
            java.util.Objects.requireNonNull(r2)     // Catch:{ all -> 0x0050 }
            com.android.systemui.statusbar.StatusBarIconView r2 = r2.mShelfIcon     // Catch:{ all -> 0x0050 }
            if (r2 != 0) goto L_0x003e
            goto L_0x004e
        L_0x003e:
            com.android.systemui.statusbar.notification.icon.IconPack r3 = r3.mIcons     // Catch:{ all -> 0x0050 }
            java.util.Objects.requireNonNull(r3)     // Catch:{ all -> 0x0050 }
            com.android.systemui.statusbar.StatusBarIconView r3 = r3.mShelfIcon     // Catch:{ all -> 0x0050 }
            java.util.Objects.requireNonNull(r3)     // Catch:{ all -> 0x0050 }
            com.android.internal.statusbar.StatusBarIcon r3 = r3.mIcon     // Catch:{ all -> 0x0050 }
            android.graphics.drawable.Icon r3 = r3.icon     // Catch:{ all -> 0x0050 }
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            return r3
        L_0x004e:
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            return r1
        L_0x0050:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0050 }
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationMediaManager.getMediaIcon():android.graphics.drawable.Icon");
    }

    public final void updateMediaMetaData(boolean z, boolean z2) {
        boolean z3;
        Bitmap bitmap;
        Trace.beginSection("StatusBar#updateMediaMetaData");
        if (this.mBackdrop == null) {
            Trace.endSection();
            return;
        }
        BiometricUnlockController biometricUnlockController = this.mBiometricUnlockController;
        if (biometricUnlockController == null || !biometricUnlockController.isWakeAndUnlock()) {
            z3 = false;
        } else {
            z3 = true;
        }
        if (this.mKeyguardStateController.isLaunchTransitionFadingAway() || z3) {
            this.mBackdrop.setVisibility(4);
            Trace.endSection();
            return;
        }
        MediaMetadata mediaMetadata = this.mMediaMetadata;
        if (mediaMetadata == null || this.mKeyguardBypassController.getBypassEnabled()) {
            bitmap = null;
        } else {
            bitmap = mediaMetadata.getBitmap("android.media.metadata.ART");
            if (bitmap == null) {
                bitmap = mediaMetadata.getBitmap("android.media.metadata.ALBUM_ART");
            }
        }
        if (z) {
            Iterator it = this.mProcessArtworkTasks.iterator();
            while (it.hasNext()) {
                ((AsyncTask) it.next()).cancel(true);
            }
            this.mProcessArtworkTasks.clear();
        }
        if (bitmap == null || Utils.useQsMediaPlayer(this.mContext)) {
            finishUpdateMediaMetaData(z, z2, (Bitmap) null);
        } else {
            this.mProcessArtworkTasks.add(new ProcessArtworkTask(this, z, z2).execute(new Bitmap[]{bitmap}));
        }
        Trace.endSection();
    }

    /* renamed from: -$$Nest$mremoveEntry  reason: not valid java name */
    public static void m227$$Nest$mremoveEntry(NotificationMediaManager notificationMediaManager, NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationMediaManager);
        Objects.requireNonNull(notificationEntry);
        if (notificationEntry.mKey.equals(notificationMediaManager.mMediaNotificationKey)) {
            notificationMediaManager.mMediaNotificationKey = null;
            Objects.requireNonNull(notificationMediaManager.mMediaArtworkProcessor);
            notificationMediaManager.mMediaMetadata = null;
            MediaController mediaController = notificationMediaManager.mMediaController;
            if (mediaController != null) {
                mediaController.unregisterCallback(notificationMediaManager.mMediaListener);
            }
            notificationMediaManager.mMediaController = null;
            notificationMediaManager.dispatchUpdateMediaMetaData(true);
        }
        notificationMediaManager.mMediaDataManager.onNotificationRemoved(notificationEntry.mKey);
    }

    public NotificationMediaManager(Context context, Lazy<Optional<StatusBar>> lazy, Lazy<NotificationShadeWindowController> lazy2, NotificationVisibilityProvider notificationVisibilityProvider, NotificationEntryManager notificationEntryManager, MediaArtworkProcessor mediaArtworkProcessor, KeyguardBypassController keyguardBypassController, NotifPipeline notifPipeline, NotifCollection notifCollection, NotifPipelineFlags notifPipelineFlags, DelayableExecutor delayableExecutor, MediaDataManager mediaDataManager, DumpManager dumpManager) {
        this.mContext = context;
        this.mMediaArtworkProcessor = mediaArtworkProcessor;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mMediaListeners = new ArrayList<>();
        this.mStatusBarOptionalLazy = lazy;
        this.mNotificationShadeWindowController = lazy2;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mEntryManager = notificationEntryManager;
        this.mMainExecutor = delayableExecutor;
        this.mMediaDataManager = mediaDataManager;
        this.mNotifPipeline = notifPipeline;
        this.mNotifCollection = notifCollection;
        if (!notifPipelineFlags.isNewPipelineEnabled()) {
            notificationEntryManager.addNotificationEntryListener(new NotificationEntryListener() {
                public final void onEntryInflated(NotificationEntry notificationEntry) {
                    NotificationMediaManager.this.findAndUpdateMediaNotifications();
                }

                public final void onEntryReinflated(NotificationEntry notificationEntry) {
                    NotificationMediaManager.this.findAndUpdateMediaNotifications();
                }

                public final void onEntryRemoved(NotificationEntry notificationEntry, boolean z) {
                    NotificationMediaManager.m227$$Nest$mremoveEntry(NotificationMediaManager.this, notificationEntry);
                }

                public final void onPendingEntryAdded(NotificationEntry notificationEntry) {
                    NotificationMediaManager.this.mMediaDataManager.onNotificationAdded(notificationEntry.mKey, notificationEntry.mSbn);
                }

                public final void onPreEntryUpdated(NotificationEntry notificationEntry) {
                    NotificationMediaManager.this.mMediaDataManager.onNotificationAdded(notificationEntry.mKey, notificationEntry.mSbn);
                }
            });
            notificationEntryManager.addCollectionListener(new NotifCollectionListener() {
                public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                    NotificationMediaManager.m227$$Nest$mremoveEntry(NotificationMediaManager.this, notificationEntry);
                }
            });
            mediaDataManager.addListener(new MediaDataManager.Listener() {
                public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
                }

                public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
                }

                public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
                }

                public final void onMediaDataRemoved(String str) {
                    NotificationEntry pendingOrActiveNotif = NotificationMediaManager.this.mEntryManager.getPendingOrActiveNotif(str);
                    if (pendingOrActiveNotif != null) {
                        NotificationMediaManager notificationMediaManager = NotificationMediaManager.this;
                        notificationMediaManager.mEntryManager.performRemoveNotification(pendingOrActiveNotif.mSbn, new DismissedByUserStats(3, notificationMediaManager.mVisibilityProvider.obtain(pendingOrActiveNotif, true)), 2);
                    }
                }
            });
            this.mUsingNotifPipeline = false;
        } else {
            notifPipeline.addCollectionListener(new NotifCollectionListener() {
                public final void onEntryAdded(NotificationEntry notificationEntry) {
                    MediaDataManager mediaDataManager = NotificationMediaManager.this.mMediaDataManager;
                    Objects.requireNonNull(notificationEntry);
                    mediaDataManager.onNotificationAdded(notificationEntry.mKey, notificationEntry.mSbn);
                }

                public final void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
                    NotificationMediaManager.this.findAndUpdateMediaNotifications();
                }

                public final void onEntryCleanUp(NotificationEntry notificationEntry) {
                    NotificationMediaManager.m227$$Nest$mremoveEntry(NotificationMediaManager.this, notificationEntry);
                }

                public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                    NotificationMediaManager.m227$$Nest$mremoveEntry(NotificationMediaManager.this, notificationEntry);
                }

                public final void onEntryUpdated(NotificationEntry notificationEntry) {
                    MediaDataManager mediaDataManager = NotificationMediaManager.this.mMediaDataManager;
                    Objects.requireNonNull(notificationEntry);
                    mediaDataManager.onNotificationAdded(notificationEntry.mKey, notificationEntry.mSbn);
                }
            });
            mediaDataManager.addListener(new MediaDataManager.Listener() {
                public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
                }

                public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
                }

                public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
                }

                public final void onMediaDataRemoved(String str) {
                    NotificationMediaManager.this.mNotifPipeline.getAllNotifs().stream().filter(new SavedNetworkTracker$$ExternalSyntheticLambda1(str, 1)).findAny().ifPresent(new WMShell$$ExternalSyntheticLambda6(this, 1));
                }
            });
            this.mUsingNotifPipeline = true;
        }
        dumpManager.registerDumpable(this);
    }

    public final boolean findPlayingMediaNotification(Collection<NotificationEntry> collection) {
        boolean z;
        NotificationEntry notificationEntry;
        MediaController mediaController;
        boolean z2;
        MediaSession.Token token;
        int i;
        Iterator<NotificationEntry> it = collection.iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                notificationEntry = null;
                mediaController = null;
                break;
            }
            notificationEntry = it.next();
            Objects.requireNonNull(notificationEntry);
            if (notificationEntry.mSbn.getNotification().isMediaNotification() && (token = (MediaSession.Token) notificationEntry.mSbn.getNotification().extras.getParcelable("android.mediaSession")) != null) {
                mediaController = new MediaController(this.mContext, token);
                PlaybackState playbackState = mediaController.getPlaybackState();
                if (playbackState != null) {
                    i = playbackState.getState();
                } else {
                    i = 0;
                }
                if (3 == i) {
                    break;
                }
            }
        }
        if (mediaController != null) {
            MediaController mediaController2 = this.mMediaController;
            if (mediaController2 == mediaController) {
                z2 = true;
            } else if (mediaController2 == null) {
                z2 = false;
            } else {
                z2 = mediaController2.controlsSameSession(mediaController);
            }
            if (!z2) {
                Objects.requireNonNull(this.mMediaArtworkProcessor);
                this.mMediaMetadata = null;
                MediaController mediaController3 = this.mMediaController;
                if (mediaController3 != null) {
                    mediaController3.unregisterCallback(this.mMediaListener);
                }
                this.mMediaController = mediaController;
                mediaController.registerCallback(this.mMediaListener);
                this.mMediaMetadata = this.mMediaController.getMetadata();
                z = true;
            }
        }
        if (notificationEntry != null && !notificationEntry.mSbn.getKey().equals(this.mMediaNotificationKey)) {
            this.mMediaNotificationKey = notificationEntry.mSbn.getKey();
        }
        return z;
    }
}
