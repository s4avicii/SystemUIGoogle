package androidx.mediarouter.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.p000v4.media.MediaDescriptionCompat;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.session.MediaControllerCompat;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.ColorUtils;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.mediarouter.app.OverlayListView;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import com.android.p012wm.shell.C1777R;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class MediaRouteControllerDialog extends AlertDialog {
    public static final int CONNECTION_TIMEOUT_MILLIS = ((int) TimeUnit.SECONDS.toMillis(30));
    public static final boolean DEBUG = Log.isLoggable("MediaRouteCtrlDialog", 3);
    public AccelerateDecelerateInterpolator mAccelerateDecelerateInterpolator;
    public final AccessibilityManager mAccessibilityManager;
    public int mArtIconBackgroundColor;
    public Bitmap mArtIconBitmap;
    public boolean mArtIconIsLoaded;
    public Bitmap mArtIconLoadedBitmap;
    public Uri mArtIconUri;
    public ImageView mArtView;
    public boolean mAttachedToWindow;
    public final MediaRouterCallback mCallback;
    public Context mContext = getContext();
    public MediaControllerCallback mControllerCallback = new MediaControllerCallback();
    public boolean mCreated;
    public FrameLayout mDefaultControlLayout;
    public MediaDescriptionCompat mDescription;
    public LinearLayout mDialogAreaLayout;
    public int mDialogContentWidth;
    public Button mDisconnectButton;
    public View mDividerView;
    public final boolean mEnableGroupVolumeUX;
    public FrameLayout mExpandableAreaLayout;
    public Interpolator mFastOutSlowInInterpolator;
    public FetchArtTask mFetchArtTask;
    public MediaRouteExpandCollapseButton mGroupExpandCollapseButton;
    public int mGroupListAnimationDurationMs;
    public C02541 mGroupListFadeInAnimation = new Runnable() {
        public final void run() {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            Objects.requireNonNull(mediaRouteControllerDialog);
            mediaRouteControllerDialog.clearGroupListAnimation(true);
            mediaRouteControllerDialog.mVolumeGroupList.requestLayout();
            mediaRouteControllerDialog.mVolumeGroupList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public final void onGlobalLayout() {
                    MediaRouteControllerDialog.this.mVolumeGroupList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                    Objects.requireNonNull(mediaRouteControllerDialog);
                    HashSet hashSet = mediaRouteControllerDialog.mGroupMemberRoutesAdded;
                    if (hashSet == null || hashSet.size() == 0) {
                        mediaRouteControllerDialog.finishAnimation(true);
                        return;
                    }
                    C025712 r0 = new Animation.AnimationListener() {
                        public final void onAnimationRepeat(Animation animation) {
                        }

                        public final void onAnimationStart(Animation animation) {
                        }

                        public final void onAnimationEnd(Animation animation) {
                            MediaRouteControllerDialog.this.finishAnimation(true);
                        }
                    };
                    int firstVisiblePosition = mediaRouteControllerDialog.mVolumeGroupList.getFirstVisiblePosition();
                    boolean z = false;
                    for (int i = 0; i < mediaRouteControllerDialog.mVolumeGroupList.getChildCount(); i++) {
                        View childAt = mediaRouteControllerDialog.mVolumeGroupList.getChildAt(i);
                        if (mediaRouteControllerDialog.mGroupMemberRoutesAdded.contains((MediaRouter.RouteInfo) mediaRouteControllerDialog.mVolumeGroupAdapter.getItem(firstVisiblePosition + i))) {
                            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                            alphaAnimation.setDuration((long) mediaRouteControllerDialog.mGroupListFadeInDurationMs);
                            alphaAnimation.setFillEnabled(true);
                            alphaAnimation.setFillAfter(true);
                            if (!z) {
                                alphaAnimation.setAnimationListener(r0);
                                z = true;
                            }
                            childAt.clearAnimation();
                            childAt.startAnimation(alphaAnimation);
                        }
                    }
                }
            });
        }
    };
    public int mGroupListFadeInDurationMs;
    public int mGroupListFadeOutDurationMs;
    public ArrayList mGroupMemberRoutes;
    public HashSet mGroupMemberRoutesAdded;
    public HashSet mGroupMemberRoutesAnimatingWithBitmap;
    public HashSet mGroupMemberRoutesRemoved;
    public boolean mHasPendingUpdate;
    public Interpolator mInterpolator;
    public boolean mIsGroupExpanded;
    public boolean mIsGroupListAnimating;
    public boolean mIsGroupListAnimationPending;
    public Interpolator mLinearOutSlowInInterpolator;
    public MediaControllerCompat mMediaController;
    public LinearLayout mMediaMainControlLayout;
    public boolean mPendingUpdateAnimationNeeded;
    public ImageButton mPlaybackControlButton;
    public RelativeLayout mPlaybackControlLayout;
    public final MediaRouter.RouteInfo mRoute;
    public MediaRouter.RouteInfo mRouteInVolumeSliderTouched;
    public TextView mRouteNameTextView;
    public final MediaRouter mRouter = MediaRouter.getInstance(this.mContext);
    public PlaybackStateCompat mState;
    public Button mStopCastingButton;
    public TextView mSubtitleView;
    public TextView mTitleView;
    public VolumeChangeListener mVolumeChangeListener;
    public boolean mVolumeControlEnabled = true;
    public LinearLayout mVolumeControlLayout;
    public VolumeGroupAdapter mVolumeGroupAdapter;
    public OverlayListView mVolumeGroupList;
    public int mVolumeGroupListItemHeight;
    public int mVolumeGroupListItemIconSize;
    public int mVolumeGroupListMaxHeight;
    public final int mVolumeGroupListPaddingTop;
    public SeekBar mVolumeSlider;
    public HashMap mVolumeSliderMap;

    public final class ClickListener implements View.OnClickListener {
        public ClickListener() {
        }

        /* JADX WARNING: Removed duplicated region for block: B:39:0x00b4 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:55:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onClick(android.view.View r11) {
            /*
                r10 = this;
                int r11 = r11.getId()
                r0 = 1
                r1 = 16908313(0x1020019, float:2.38773E-38)
                if (r11 == r1) goto L_0x00f2
                r2 = 16908314(0x102001a, float:2.3877302E-38)
                if (r11 != r2) goto L_0x0011
                goto L_0x00f2
            L_0x0011:
                r1 = 2131428425(0x7f0b0449, float:1.8478494E38)
                if (r11 != r1) goto L_0x00e7
                androidx.mediarouter.app.MediaRouteControllerDialog r11 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.support.v4.media.session.MediaControllerCompat r1 = r11.mMediaController
                if (r1 == 0) goto L_0x010e
                android.support.v4.media.session.PlaybackStateCompat r1 = r11.mState
                if (r1 == 0) goto L_0x010e
                int r1 = r1.mState
                r2 = 3
                r3 = 0
                if (r1 != r2) goto L_0x0028
                r1 = r0
                goto L_0x0029
            L_0x0028:
                r1 = r3
            L_0x0029:
                r4 = 0
                if (r1 == 0) goto L_0x0054
                java.util.Objects.requireNonNull(r11)
                android.support.v4.media.session.PlaybackStateCompat r11 = r11.mState
                java.util.Objects.requireNonNull(r11)
                long r6 = r11.mActions
                r8 = 514(0x202, double:2.54E-321)
                long r6 = r6 & r8
                int r11 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r11 == 0) goto L_0x0040
                r11 = r0
                goto L_0x0041
            L_0x0040:
                r11 = r3
            L_0x0041:
                if (r11 == 0) goto L_0x0054
                androidx.mediarouter.app.MediaRouteControllerDialog r11 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.support.v4.media.session.MediaControllerCompat r11 = r11.mMediaController
                android.support.v4.media.session.MediaControllerCompat$TransportControlsApi29 r11 = r11.getTransportControls()
                android.media.session.MediaController$TransportControls r11 = r11.mControlsFwk
                r11.pause()
                r3 = 2131952815(0x7f1304af, float:1.9542083E38)
                goto L_0x00a8
            L_0x0054:
                if (r1 == 0) goto L_0x007f
                androidx.mediarouter.app.MediaRouteControllerDialog r11 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                java.util.Objects.requireNonNull(r11)
                android.support.v4.media.session.PlaybackStateCompat r11 = r11.mState
                java.util.Objects.requireNonNull(r11)
                long r6 = r11.mActions
                r8 = 1
                long r6 = r6 & r8
                int r11 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                if (r11 == 0) goto L_0x006b
                r11 = r0
                goto L_0x006c
            L_0x006b:
                r11 = r3
            L_0x006c:
                if (r11 == 0) goto L_0x007f
                androidx.mediarouter.app.MediaRouteControllerDialog r11 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.support.v4.media.session.MediaControllerCompat r11 = r11.mMediaController
                android.support.v4.media.session.MediaControllerCompat$TransportControlsApi29 r11 = r11.getTransportControls()
                android.media.session.MediaController$TransportControls r11 = r11.mControlsFwk
                r11.stop()
                r3 = 2131952817(0x7f1304b1, float:1.9542087E38)
                goto L_0x00a8
            L_0x007f:
                if (r1 != 0) goto L_0x00a8
                androidx.mediarouter.app.MediaRouteControllerDialog r11 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                java.util.Objects.requireNonNull(r11)
                android.support.v4.media.session.PlaybackStateCompat r11 = r11.mState
                java.util.Objects.requireNonNull(r11)
                long r1 = r11.mActions
                r6 = 516(0x204, double:2.55E-321)
                long r1 = r1 & r6
                int r11 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
                if (r11 == 0) goto L_0x0095
                goto L_0x0096
            L_0x0095:
                r0 = r3
            L_0x0096:
                if (r0 == 0) goto L_0x00a8
                androidx.mediarouter.app.MediaRouteControllerDialog r11 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.support.v4.media.session.MediaControllerCompat r11 = r11.mMediaController
                android.support.v4.media.session.MediaControllerCompat$TransportControlsApi29 r11 = r11.getTransportControls()
                android.media.session.MediaController$TransportControls r11 = r11.mControlsFwk
                r11.play()
                r3 = 2131952816(0x7f1304b0, float:1.9542085E38)
            L_0x00a8:
                androidx.mediarouter.app.MediaRouteControllerDialog r11 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.view.accessibility.AccessibilityManager r11 = r11.mAccessibilityManager
                if (r11 == 0) goto L_0x010e
                boolean r11 = r11.isEnabled()
                if (r11 == 0) goto L_0x010e
                if (r3 == 0) goto L_0x010e
                r11 = 16384(0x4000, float:2.2959E-41)
                android.view.accessibility.AccessibilityEvent r11 = android.view.accessibility.AccessibilityEvent.obtain(r11)
                androidx.mediarouter.app.MediaRouteControllerDialog r0 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.content.Context r0 = r0.mContext
                java.lang.String r0 = r0.getPackageName()
                r11.setPackageName(r0)
                java.lang.Class<androidx.mediarouter.app.MediaRouteControllerDialog$ClickListener> r0 = androidx.mediarouter.app.MediaRouteControllerDialog.ClickListener.class
                java.lang.String r0 = r0.getName()
                r11.setClassName(r0)
                java.util.List r0 = r11.getText()
                androidx.mediarouter.app.MediaRouteControllerDialog r1 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.content.Context r1 = r1.mContext
                java.lang.String r1 = r1.getString(r3)
                r0.add(r1)
                androidx.mediarouter.app.MediaRouteControllerDialog r10 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                android.view.accessibility.AccessibilityManager r10 = r10.mAccessibilityManager
                r10.sendAccessibilityEvent(r11)
                goto L_0x010e
            L_0x00e7:
                r0 = 2131428423(0x7f0b0447, float:1.847849E38)
                if (r11 != r0) goto L_0x010e
                androidx.mediarouter.app.MediaRouteControllerDialog r10 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                r10.dismiss()
                goto L_0x010e
            L_0x00f2:
                androidx.mediarouter.app.MediaRouteControllerDialog r2 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                androidx.mediarouter.media.MediaRouter$RouteInfo r2 = r2.mRoute
                boolean r2 = r2.isSelected()
                if (r2 == 0) goto L_0x0109
                androidx.mediarouter.app.MediaRouteControllerDialog r2 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                androidx.mediarouter.media.MediaRouter r2 = r2.mRouter
                if (r11 != r1) goto L_0x0103
                r0 = 2
            L_0x0103:
                java.util.Objects.requireNonNull(r2)
                androidx.mediarouter.media.MediaRouter.unselect(r0)
            L_0x0109:
                androidx.mediarouter.app.MediaRouteControllerDialog r10 = androidx.mediarouter.app.MediaRouteControllerDialog.this
                r10.dismiss()
            L_0x010e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteControllerDialog.ClickListener.onClick(android.view.View):void");
        }
    }

    public class FetchArtTask extends AsyncTask<Void, Void, Bitmap> {
        public int mBackgroundColor;
        public final Bitmap mIconBitmap;
        public final Uri mIconUri;
        public long mStartTimeMillis;

        public FetchArtTask() {
            Bitmap bitmap;
            boolean z;
            MediaDescriptionCompat mediaDescriptionCompat = MediaRouteControllerDialog.this.mDescription;
            Uri uri = null;
            if (mediaDescriptionCompat == null) {
                bitmap = null;
            } else {
                bitmap = mediaDescriptionCompat.mIcon;
            }
            if (bitmap == null || !bitmap.isRecycled()) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                Log.w("MediaRouteCtrlDialog", "Can't fetch the given art bitmap because it's already recycled.");
                bitmap = null;
            }
            this.mIconBitmap = bitmap;
            MediaDescriptionCompat mediaDescriptionCompat2 = MediaRouteControllerDialog.this.mDescription;
            this.mIconUri = mediaDescriptionCompat2 != null ? mediaDescriptionCompat2.mIconUri : uri;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(9:15|16|17|18|(2:20|(1:22)(1:75))|23|(1:25)(1:28)|29|(1:31)(4:32|33|34|35)) */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0048 */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x0053 A[Catch:{ IOException -> 0x0080 }] */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x00cc A[SYNTHETIC, Splitter:B:47:0x00cc] */
        /* JADX WARNING: Removed duplicated region for block: B:50:0x00d2 A[SYNTHETIC, Splitter:B:50:0x00d2] */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x00df  */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x00e1  */
        /* JADX WARNING: Removed duplicated region for block: B:62:0x00e4  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x00f9 A[ADDED_TO_REGION] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object doInBackground(java.lang.Object[] r11) {
            /*
                r10 = this;
                java.lang.Void[] r11 = (java.lang.Void[]) r11
                java.lang.String r11 = "Unable to open: "
                android.graphics.Bitmap r0 = r10.mIconBitmap
                r1 = 1
                r2 = 0
                java.lang.String r3 = "MediaRouteCtrlDialog"
                r4 = 0
                if (r0 == 0) goto L_0x000f
                goto L_0x00d7
            L_0x000f:
                android.net.Uri r0 = r10.mIconUri
                if (r0 == 0) goto L_0x00d6
                java.io.BufferedInputStream r0 = r10.openInputStreamByScheme(r0)     // Catch:{ IOException -> 0x00b4, all -> 0x00b2 }
                if (r0 != 0) goto L_0x0031
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0080 }
                r5.<init>()     // Catch:{ IOException -> 0x0080 }
                r5.append(r11)     // Catch:{ IOException -> 0x0080 }
                android.net.Uri r6 = r10.mIconUri     // Catch:{ IOException -> 0x0080 }
                r5.append(r6)     // Catch:{ IOException -> 0x0080 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0080 }
                android.util.Log.w(r3, r5)     // Catch:{ IOException -> 0x0080 }
                if (r0 == 0) goto L_0x0131
                goto L_0x00aa
            L_0x0031:
                android.graphics.BitmapFactory$Options r5 = new android.graphics.BitmapFactory$Options     // Catch:{ IOException -> 0x0080 }
                r5.<init>()     // Catch:{ IOException -> 0x0080 }
                r5.inJustDecodeBounds = r1     // Catch:{ IOException -> 0x0080 }
                android.graphics.BitmapFactory.decodeStream(r0, r4, r5)     // Catch:{ IOException -> 0x0080 }
                int r6 = r5.outWidth     // Catch:{ IOException -> 0x0080 }
                if (r6 == 0) goto L_0x00aa
                int r6 = r5.outHeight     // Catch:{ IOException -> 0x0080 }
                if (r6 != 0) goto L_0x0044
                goto L_0x00aa
            L_0x0044:
                r0.reset()     // Catch:{ IOException -> 0x0048 }
                goto L_0x006a
            L_0x0048:
                r0.close()     // Catch:{ IOException -> 0x0080 }
                android.net.Uri r6 = r10.mIconUri     // Catch:{ IOException -> 0x0080 }
                java.io.BufferedInputStream r0 = r10.openInputStreamByScheme(r6)     // Catch:{ IOException -> 0x0080 }
                if (r0 != 0) goto L_0x006a
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0080 }
                r5.<init>()     // Catch:{ IOException -> 0x0080 }
                r5.append(r11)     // Catch:{ IOException -> 0x0080 }
                android.net.Uri r6 = r10.mIconUri     // Catch:{ IOException -> 0x0080 }
                r5.append(r6)     // Catch:{ IOException -> 0x0080 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0080 }
                android.util.Log.w(r3, r5)     // Catch:{ IOException -> 0x0080 }
                if (r0 == 0) goto L_0x0131
                goto L_0x00aa
            L_0x006a:
                r5.inJustDecodeBounds = r2     // Catch:{ IOException -> 0x0080 }
                androidx.mediarouter.app.MediaRouteControllerDialog r6 = androidx.mediarouter.app.MediaRouteControllerDialog.this     // Catch:{ IOException -> 0x0080 }
                int r7 = r5.outWidth     // Catch:{ IOException -> 0x0080 }
                int r8 = r5.outHeight     // Catch:{ IOException -> 0x0080 }
                java.util.Objects.requireNonNull(r6)     // Catch:{ IOException -> 0x0080 }
                r9 = 1056964608(0x3f000000, float:0.5)
                if (r7 < r8) goto L_0x0082
                int r6 = r6.mDialogContentWidth     // Catch:{ IOException -> 0x0080 }
                float r6 = (float) r6     // Catch:{ IOException -> 0x0080 }
                float r8 = (float) r8     // Catch:{ IOException -> 0x0080 }
                float r6 = r6 * r8
                float r7 = (float) r7     // Catch:{ IOException -> 0x0080 }
                goto L_0x008a
            L_0x0080:
                r5 = move-exception
                goto L_0x00b6
            L_0x0082:
                int r6 = r6.mDialogContentWidth     // Catch:{ IOException -> 0x0080 }
                float r6 = (float) r6     // Catch:{ IOException -> 0x0080 }
                r7 = 1091567616(0x41100000, float:9.0)
                float r6 = r6 * r7
                r7 = 1098907648(0x41800000, float:16.0)
            L_0x008a:
                float r6 = r6 / r7
                float r6 = r6 + r9
                int r6 = (int) r6     // Catch:{ IOException -> 0x0080 }
                int r7 = r5.outHeight     // Catch:{ IOException -> 0x0080 }
                int r7 = r7 / r6
                int r6 = java.lang.Integer.highestOneBit(r7)     // Catch:{ IOException -> 0x0080 }
                int r6 = java.lang.Math.max(r1, r6)     // Catch:{ IOException -> 0x0080 }
                r5.inSampleSize = r6     // Catch:{ IOException -> 0x0080 }
                boolean r6 = r10.isCancelled()     // Catch:{ IOException -> 0x0080 }
                if (r6 == 0) goto L_0x00a1
                goto L_0x00aa
            L_0x00a1:
                android.graphics.Bitmap r11 = android.graphics.BitmapFactory.decodeStream(r0, r4, r5)     // Catch:{ IOException -> 0x0080 }
                r0.close()     // Catch:{ IOException -> 0x00a8 }
            L_0x00a8:
                r0 = r11
                goto L_0x00d7
            L_0x00aa:
                r0.close()     // Catch:{ IOException -> 0x0131 }
                goto L_0x0131
            L_0x00af:
                r10 = move-exception
                r4 = r0
                goto L_0x00d0
            L_0x00b2:
                r10 = move-exception
                goto L_0x00d0
            L_0x00b4:
                r5 = move-exception
                r0 = r4
            L_0x00b6:
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x00af }
                r6.<init>()     // Catch:{ all -> 0x00af }
                r6.append(r11)     // Catch:{ all -> 0x00af }
                android.net.Uri r11 = r10.mIconUri     // Catch:{ all -> 0x00af }
                r6.append(r11)     // Catch:{ all -> 0x00af }
                java.lang.String r11 = r6.toString()     // Catch:{ all -> 0x00af }
                android.util.Log.w(r3, r11, r5)     // Catch:{ all -> 0x00af }
                if (r0 == 0) goto L_0x00d6
                r0.close()     // Catch:{ IOException -> 0x00d6 }
                goto L_0x00d6
            L_0x00d0:
                if (r4 == 0) goto L_0x00d5
                r4.close()     // Catch:{ IOException -> 0x00d5 }
            L_0x00d5:
                throw r10
            L_0x00d6:
                r0 = r4
            L_0x00d7:
                if (r0 == 0) goto L_0x00e1
                boolean r11 = r0.isRecycled()
                if (r11 == 0) goto L_0x00e1
                r11 = r1
                goto L_0x00e2
            L_0x00e1:
                r11 = r2
            L_0x00e2:
                if (r11 == 0) goto L_0x00f9
                java.lang.StringBuilder r10 = new java.lang.StringBuilder
                r10.<init>()
                java.lang.String r11 = "Can't use recycled bitmap: "
                r10.append(r11)
                r10.append(r0)
                java.lang.String r10 = r10.toString()
                android.util.Log.w(r3, r10)
                goto L_0x0131
            L_0x00f9:
                if (r0 == 0) goto L_0x0130
                int r11 = r0.getWidth()
                int r3 = r0.getHeight()
                if (r11 >= r3) goto L_0x0130
                androidx.palette.graphics.Palette$Builder r11 = new androidx.palette.graphics.Palette$Builder
                r11.<init>(r0)
                r11.mMaxColors = r1
                androidx.palette.graphics.Palette r11 = r11.generate()
                java.util.List<androidx.palette.graphics.Palette$Swatch> r1 = r11.mSwatches
                java.util.List r1 = java.util.Collections.unmodifiableList(r1)
                boolean r1 = r1.isEmpty()
                if (r1 == 0) goto L_0x011d
                goto L_0x012e
            L_0x011d:
                java.util.List<androidx.palette.graphics.Palette$Swatch> r11 = r11.mSwatches
                java.util.List r11 = java.util.Collections.unmodifiableList(r11)
                java.lang.Object r11 = r11.get(r2)
                androidx.palette.graphics.Palette$Swatch r11 = (androidx.palette.graphics.Palette.Swatch) r11
                java.util.Objects.requireNonNull(r11)
                int r2 = r11.mRgb
            L_0x012e:
                r10.mBackgroundColor = r2
            L_0x0130:
                r4 = r0
            L_0x0131:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteControllerDialog.FetchArtTask.doInBackground(java.lang.Object[]):java.lang.Object");
        }

        public final void onPostExecute(Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            mediaRouteControllerDialog.mFetchArtTask = null;
            if (!Objects.equals(mediaRouteControllerDialog.mArtIconBitmap, this.mIconBitmap) || !Objects.equals(MediaRouteControllerDialog.this.mArtIconUri, this.mIconUri)) {
                MediaRouteControllerDialog mediaRouteControllerDialog2 = MediaRouteControllerDialog.this;
                mediaRouteControllerDialog2.mArtIconBitmap = this.mIconBitmap;
                mediaRouteControllerDialog2.mArtIconLoadedBitmap = bitmap;
                mediaRouteControllerDialog2.mArtIconUri = this.mIconUri;
                mediaRouteControllerDialog2.mArtIconBackgroundColor = this.mBackgroundColor;
                boolean z = true;
                mediaRouteControllerDialog2.mArtIconIsLoaded = true;
                MediaRouteControllerDialog mediaRouteControllerDialog3 = MediaRouteControllerDialog.this;
                if (SystemClock.uptimeMillis() - this.mStartTimeMillis <= 120) {
                    z = false;
                }
                mediaRouteControllerDialog3.update(z);
            }
        }

        public final void onPreExecute() {
            this.mStartTimeMillis = SystemClock.uptimeMillis();
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            Objects.requireNonNull(mediaRouteControllerDialog);
            mediaRouteControllerDialog.mArtIconIsLoaded = false;
            mediaRouteControllerDialog.mArtIconLoadedBitmap = null;
            mediaRouteControllerDialog.mArtIconBackgroundColor = 0;
        }

        public final BufferedInputStream openInputStreamByScheme(Uri uri) throws IOException {
            InputStream inputStream;
            String lowerCase = uri.getScheme().toLowerCase();
            if ("android.resource".equals(lowerCase) || "content".equals(lowerCase) || "file".equals(lowerCase)) {
                inputStream = MediaRouteControllerDialog.this.mContext.getContentResolver().openInputStream(uri);
            } else {
                URLConnection openConnection = new URL(uri.toString()).openConnection();
                int i = MediaRouteControllerDialog.CONNECTION_TIMEOUT_MILLIS;
                openConnection.setConnectTimeout(i);
                openConnection.setReadTimeout(i);
                inputStream = openConnection.getInputStream();
            }
            if (inputStream == null) {
                return null;
            }
            return new BufferedInputStream(inputStream);
        }
    }

    public final class MediaControllerCallback extends MediaControllerCompat.Callback {
        public MediaControllerCallback() {
        }

        public final void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
            MediaDescriptionCompat mediaDescriptionCompat;
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            if (mediaMetadataCompat == null) {
                mediaDescriptionCompat = null;
            } else {
                mediaDescriptionCompat = mediaMetadataCompat.getDescription();
            }
            mediaRouteControllerDialog.mDescription = mediaDescriptionCompat;
            MediaRouteControllerDialog.this.updateArtIconIfNeeded();
            MediaRouteControllerDialog.this.update(false);
        }

        public final void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            mediaRouteControllerDialog.mState = playbackStateCompat;
            mediaRouteControllerDialog.update(false);
        }

        public final void onSessionDestroyed() {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            MediaControllerCompat mediaControllerCompat = mediaRouteControllerDialog.mMediaController;
            if (mediaControllerCompat != null) {
                mediaControllerCompat.unregisterCallback(mediaRouteControllerDialog.mControllerCallback);
                MediaRouteControllerDialog.this.mMediaController = null;
            }
        }
    }

    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        public final void onRouteChanged(MediaRouter.RouteInfo routeInfo) {
            MediaRouteControllerDialog.this.update(true);
        }

        public final void onRouteUnselected() {
            MediaRouteControllerDialog.this.update(false);
        }

        public final void onRouteVolumeChanged(MediaRouter.RouteInfo routeInfo) {
            SeekBar seekBar = (SeekBar) MediaRouteControllerDialog.this.mVolumeSliderMap.get(routeInfo);
            int i = routeInfo.mVolume;
            if (MediaRouteControllerDialog.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("onRouteVolumeChanged(), route.getVolume:", i, "MediaRouteCtrlDialog");
            }
            if (seekBar != null && MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched != routeInfo) {
                seekBar.setProgress(i);
            }
        }
    }

    public class VolumeChangeListener implements SeekBar.OnSeekBarChangeListener {
        public final C02661 mStopTrackingTouch = new Runnable() {
            public final void run() {
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                if (mediaRouteControllerDialog.mRouteInVolumeSliderTouched != null) {
                    mediaRouteControllerDialog.mRouteInVolumeSliderTouched = null;
                    if (mediaRouteControllerDialog.mHasPendingUpdate) {
                        mediaRouteControllerDialog.update(mediaRouteControllerDialog.mPendingUpdateAnimationNeeded);
                    }
                }
            }
        };

        public VolumeChangeListener() {
        }

        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) seekBar.getTag();
                if (MediaRouteControllerDialog.DEBUG) {
                    Log.d("MediaRouteCtrlDialog", "onProgressChanged(): calling MediaRouter.RouteInfo.requestSetVolume(" + i + ")");
                }
                routeInfo.requestSetVolume(i);
            }
        }

        public final void onStartTrackingTouch(SeekBar seekBar) {
            MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
            if (mediaRouteControllerDialog.mRouteInVolumeSliderTouched != null) {
                mediaRouteControllerDialog.mVolumeSlider.removeCallbacks(this.mStopTrackingTouch);
            }
            MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched = (MediaRouter.RouteInfo) seekBar.getTag();
        }

        public final void onStopTrackingTouch(SeekBar seekBar) {
            MediaRouteControllerDialog.this.mVolumeSlider.postDelayed(this.mStopTrackingTouch, 500);
        }
    }

    public class VolumeGroupAdapter extends ArrayAdapter<MediaRouter.RouteInfo> {
        public final float mDisabledAlpha;

        public final View getView(int i, View view, ViewGroup viewGroup) {
            int i2;
            boolean z;
            int i3 = 0;
            if (view == null) {
                view = LayoutInflater.from(viewGroup.getContext()).inflate(C1777R.layout.mr_controller_volume_item, viewGroup, false);
            } else {
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                Objects.requireNonNull(mediaRouteControllerDialog);
                MediaRouteControllerDialog.setLayoutHeight((LinearLayout) view.findViewById(C1777R.C1779id.volume_item_container), mediaRouteControllerDialog.mVolumeGroupListItemHeight);
                View findViewById = view.findViewById(C1777R.C1779id.mr_volume_item_icon);
                ViewGroup.LayoutParams layoutParams = findViewById.getLayoutParams();
                int i4 = mediaRouteControllerDialog.mVolumeGroupListItemIconSize;
                layoutParams.width = i4;
                layoutParams.height = i4;
                findViewById.setLayoutParams(layoutParams);
            }
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) getItem(i);
            if (routeInfo != null) {
                boolean z2 = routeInfo.mEnabled;
                TextView textView = (TextView) view.findViewById(C1777R.C1779id.mr_name);
                textView.setEnabled(z2);
                textView.setText(routeInfo.mName);
                MediaRouteVolumeSlider mediaRouteVolumeSlider = (MediaRouteVolumeSlider) view.findViewById(C1777R.C1779id.mr_volume_slider);
                MediaRouterThemeHelper.setVolumeSliderColor(viewGroup.getContext(), mediaRouteVolumeSlider, MediaRouteControllerDialog.this.mVolumeGroupList);
                mediaRouteVolumeSlider.setTag(routeInfo);
                MediaRouteControllerDialog.this.mVolumeSliderMap.put(routeInfo, mediaRouteVolumeSlider);
                mediaRouteVolumeSlider.setHideThumb(!z2);
                mediaRouteVolumeSlider.setEnabled(z2);
                if (z2) {
                    MediaRouteControllerDialog mediaRouteControllerDialog2 = MediaRouteControllerDialog.this;
                    Objects.requireNonNull(mediaRouteControllerDialog2);
                    if (!mediaRouteControllerDialog2.mVolumeControlEnabled || routeInfo.getVolumeHandling() != 1) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        mediaRouteVolumeSlider.setMax(routeInfo.mVolumeMax);
                        mediaRouteVolumeSlider.setProgress(routeInfo.mVolume);
                        mediaRouteVolumeSlider.setOnSeekBarChangeListener(MediaRouteControllerDialog.this.mVolumeChangeListener);
                    } else {
                        mediaRouteVolumeSlider.setMax(100);
                        mediaRouteVolumeSlider.setProgress(100);
                        mediaRouteVolumeSlider.setEnabled(false);
                    }
                }
                ImageView imageView = (ImageView) view.findViewById(C1777R.C1779id.mr_volume_item_icon);
                if (z2) {
                    i2 = 255;
                } else {
                    i2 = (int) (this.mDisabledAlpha * 255.0f);
                }
                imageView.setAlpha(i2);
                LinearLayout linearLayout = (LinearLayout) view.findViewById(C1777R.C1779id.volume_item_container);
                if (MediaRouteControllerDialog.this.mGroupMemberRoutesAnimatingWithBitmap.contains(routeInfo)) {
                    i3 = 4;
                }
                linearLayout.setVisibility(i3);
                HashSet hashSet = MediaRouteControllerDialog.this.mGroupMemberRoutesAdded;
                if (hashSet != null && hashSet.contains(routeInfo)) {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
                    alphaAnimation.setDuration(0);
                    alphaAnimation.setFillEnabled(true);
                    alphaAnimation.setFillAfter(true);
                    view.clearAnimation();
                    view.startAnimation(alphaAnimation);
                }
            }
            return view;
        }

        public final boolean isEnabled(int i) {
            return false;
        }

        public VolumeGroupAdapter(Context context, List<MediaRouter.RouteInfo> list) {
            super(context, 0, list);
            this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(context);
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaRouteControllerDialog(android.content.Context r3) {
        /*
            r2 = this;
            r0 = 1
            android.view.ContextThemeWrapper r3 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogContext(r3, r0)
            int r1 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogStyle(r3)
            r2.<init>(r3, r1)
            r2.mVolumeControlEnabled = r0
            androidx.mediarouter.app.MediaRouteControllerDialog$1 r1 = new androidx.mediarouter.app.MediaRouteControllerDialog$1
            r1.<init>()
            r2.mGroupListFadeInAnimation = r1
            android.content.Context r1 = r2.getContext()
            r2.mContext = r1
            androidx.mediarouter.app.MediaRouteControllerDialog$MediaControllerCallback r1 = new androidx.mediarouter.app.MediaRouteControllerDialog$MediaControllerCallback
            r1.<init>()
            r2.mControllerCallback = r1
            android.content.Context r1 = r2.mContext
            androidx.mediarouter.media.MediaRouter r1 = androidx.mediarouter.media.MediaRouter.getInstance(r1)
            r2.mRouter = r1
            androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r1 = androidx.mediarouter.media.MediaRouter.sGlobal
            if (r1 != 0) goto L_0x0030
            r0 = 0
            goto L_0x0037
        L_0x0030:
            androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r1 = androidx.mediarouter.media.MediaRouter.getGlobalRouter()
            java.util.Objects.requireNonNull(r1)
        L_0x0037:
            r2.mEnableGroupVolumeUX = r0
            androidx.mediarouter.app.MediaRouteControllerDialog$MediaRouterCallback r0 = new androidx.mediarouter.app.MediaRouteControllerDialog$MediaRouterCallback
            r0.<init>()
            r2.mCallback = r0
            androidx.mediarouter.media.MediaRouter$RouteInfo r0 = androidx.mediarouter.media.MediaRouter.getSelectedRoute()
            r2.mRoute = r0
            r2.setMediaSession()
            android.content.Context r0 = r2.mContext
            android.content.res.Resources r0 = r0.getResources()
            r1 = 2131166378(0x7f0704aa, float:1.7947E38)
            int r0 = r0.getDimensionPixelSize(r1)
            r2.mVolumeGroupListPaddingTop = r0
            android.content.Context r0 = r2.mContext
            java.lang.String r1 = "accessibility"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.accessibility.AccessibilityManager r0 = (android.view.accessibility.AccessibilityManager) r0
            r2.mAccessibilityManager = r0
            r0 = 2131558409(0x7f0d0009, float:1.8742133E38)
            android.view.animation.Interpolator r0 = android.view.animation.AnimationUtils.loadInterpolator(r3, r0)
            r2.mLinearOutSlowInInterpolator = r0
            r0 = 2131558408(0x7f0d0008, float:1.874213E38)
            android.view.animation.Interpolator r3 = android.view.animation.AnimationUtils.loadInterpolator(r3, r0)
            r2.mFastOutSlowInInterpolator = r3
            android.view.animation.AccelerateDecelerateInterpolator r3 = new android.view.animation.AccelerateDecelerateInterpolator
            r3.<init>()
            r2.mAccelerateDecelerateInterpolator = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteControllerDialog.<init>(android.content.Context):void");
    }

    public final void finishAnimation(boolean z) {
        this.mGroupMemberRoutesAdded = null;
        this.mGroupMemberRoutesRemoved = null;
        this.mIsGroupListAnimating = false;
        if (this.mIsGroupListAnimationPending) {
            this.mIsGroupListAnimationPending = false;
            updateLayoutHeight(z);
        }
        this.mVolumeGroupList.setEnabled(true);
    }

    public final int getMainControllerHeight(boolean z) {
        int i;
        if (!z && this.mVolumeControlLayout.getVisibility() != 0) {
            return 0;
        }
        int paddingBottom = this.mMediaMainControlLayout.getPaddingBottom() + this.mMediaMainControlLayout.getPaddingTop() + 0;
        if (z) {
            paddingBottom += this.mPlaybackControlLayout.getMeasuredHeight();
        }
        if (this.mVolumeControlLayout.getVisibility() == 0) {
            i = this.mVolumeControlLayout.getMeasuredHeight() + paddingBottom;
        } else {
            i = paddingBottom;
        }
        if (!z || this.mVolumeControlLayout.getVisibility() != 0) {
            return i;
        }
        return i + this.mDividerView.getMeasuredHeight();
    }

    public final boolean canShowPlaybackControlLayout() {
        if (this.mDescription == null && this.mState == null) {
            return false;
        }
        return true;
    }

    public final void clearGroupListAnimation(boolean z) {
        HashSet hashSet;
        int firstVisiblePosition = this.mVolumeGroupList.getFirstVisiblePosition();
        for (int i = 0; i < this.mVolumeGroupList.getChildCount(); i++) {
            View childAt = this.mVolumeGroupList.getChildAt(i);
            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) this.mVolumeGroupAdapter.getItem(firstVisiblePosition + i);
            if (!z || (hashSet = this.mGroupMemberRoutesAdded) == null || !hashSet.contains(routeInfo)) {
                ((LinearLayout) childAt.findViewById(C1777R.C1779id.volume_item_container)).setVisibility(0);
                AnimationSet animationSet = new AnimationSet(true);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
                alphaAnimation.setDuration(0);
                animationSet.addAnimation(alphaAnimation);
                new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f).setDuration(0);
                animationSet.setFillAfter(true);
                animationSet.setFillEnabled(true);
                childAt.clearAnimation();
                childAt.startAnimation(animationSet);
            }
        }
        OverlayListView overlayListView = this.mVolumeGroupList;
        Objects.requireNonNull(overlayListView);
        Iterator it = overlayListView.mOverlayObjects.iterator();
        while (it.hasNext()) {
            OverlayListView.OverlayObject overlayObject = (OverlayListView.OverlayObject) it.next();
            Objects.requireNonNull(overlayObject);
            overlayObject.mIsAnimationStarted = true;
            overlayObject.mIsAnimationEnded = true;
            OverlayListView.OverlayObject.OnAnimationEndListener onAnimationEndListener = overlayObject.mListener;
            if (onAnimationEndListener != null) {
                C025510 r2 = (C025510) onAnimationEndListener;
                MediaRouteControllerDialog.this.mGroupMemberRoutesAnimatingWithBitmap.remove(r2.val$route);
                MediaRouteControllerDialog.this.mVolumeGroupAdapter.notifyDataSetChanged();
            }
        }
        if (!z) {
            finishAnimation(false);
        }
    }

    public final boolean isGroup() {
        if (!this.mRoute.isGroup() || this.mRoute.getMemberRoutes().size() <= 1) {
            return false;
        }
        return true;
    }

    public final void onDetachedFromWindow() {
        this.mRouter.removeCallback(this.mCallback);
        setMediaSession();
        this.mAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        int i2;
        if (i != 25 && i != 24) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.mEnableGroupVolumeUX || !this.mIsGroupExpanded) {
            MediaRouter.RouteInfo routeInfo = this.mRoute;
            if (i == 25) {
                i2 = -1;
            } else {
                i2 = 1;
            }
            routeInfo.requestUpdateVolume(i2);
        }
        return true;
    }

    public final boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 25 || i == 24) {
            return true;
        }
        return super.onKeyUp(i, keyEvent);
    }

    public final void setMediaSession() {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat != null) {
            mediaControllerCompat.unregisterCallback(this.mControllerCallback);
            this.mMediaController = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:123:0x01e9  */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01ef  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0165  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x0167  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x0171  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0179  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void update(boolean r12) {
        /*
            r11 = this;
            androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r11.mRouteInVolumeSliderTouched
            r1 = 1
            if (r0 == 0) goto L_0x000d
            r11.mHasPendingUpdate = r1
            boolean r0 = r11.mPendingUpdateAnimationNeeded
            r12 = r12 | r0
            r11.mPendingUpdateAnimationNeeded = r12
            return
        L_0x000d:
            r0 = 0
            r11.mHasPendingUpdate = r0
            r11.mPendingUpdateAnimationNeeded = r0
            androidx.mediarouter.media.MediaRouter$RouteInfo r2 = r11.mRoute
            boolean r2 = r2.isSelected()
            if (r2 == 0) goto L_0x0209
            androidx.mediarouter.media.MediaRouter$RouteInfo r2 = r11.mRoute
            boolean r2 = r2.isDefaultOrBluetooth()
            if (r2 == 0) goto L_0x0024
            goto L_0x0209
        L_0x0024:
            boolean r2 = r11.mCreated
            if (r2 != 0) goto L_0x0029
            return
        L_0x0029:
            android.widget.TextView r2 = r11.mRouteNameTextView
            androidx.mediarouter.media.MediaRouter$RouteInfo r3 = r11.mRoute
            java.util.Objects.requireNonNull(r3)
            java.lang.String r3 = r3.mName
            r2.setText(r3)
            android.widget.Button r2 = r11.mDisconnectButton
            androidx.mediarouter.media.MediaRouter$RouteInfo r3 = r11.mRoute
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mCanDisconnect
            r4 = 8
            if (r3 == 0) goto L_0x0044
            r3 = r0
            goto L_0x0045
        L_0x0044:
            r3 = r4
        L_0x0045:
            r2.setVisibility(r3)
            boolean r2 = r11.mArtIconIsLoaded
            r3 = 0
            if (r2 == 0) goto L_0x0085
            android.graphics.Bitmap r2 = r11.mArtIconLoadedBitmap
            if (r2 == 0) goto L_0x0059
            boolean r2 = r2.isRecycled()
            if (r2 == 0) goto L_0x0059
            r2 = r1
            goto L_0x005a
        L_0x0059:
            r2 = r0
        L_0x005a:
            if (r2 == 0) goto L_0x0071
            java.lang.String r2 = "Can't set artwork image with recycled bitmap: "
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            android.graphics.Bitmap r5 = r11.mArtIconLoadedBitmap
            r2.append(r5)
            java.lang.String r2 = r2.toString()
            java.lang.String r5 = "MediaRouteCtrlDialog"
            android.util.Log.w(r5, r2)
            goto L_0x007f
        L_0x0071:
            android.widget.ImageView r2 = r11.mArtView
            android.graphics.Bitmap r5 = r11.mArtIconLoadedBitmap
            r2.setImageBitmap(r5)
            android.widget.ImageView r2 = r11.mArtView
            int r5 = r11.mArtIconBackgroundColor
            r2.setBackgroundColor(r5)
        L_0x007f:
            r11.mArtIconIsLoaded = r0
            r11.mArtIconLoadedBitmap = r3
            r11.mArtIconBackgroundColor = r0
        L_0x0085:
            boolean r2 = r11.mEnableGroupVolumeUX
            if (r2 != 0) goto L_0x00aa
            boolean r2 = r11.isGroup()
            if (r2 == 0) goto L_0x00aa
            android.widget.LinearLayout r2 = r11.mVolumeControlLayout
            r2.setVisibility(r4)
            r11.mIsGroupExpanded = r1
            androidx.mediarouter.app.OverlayListView r2 = r11.mVolumeGroupList
            r2.setVisibility(r0)
            boolean r2 = r11.mIsGroupExpanded
            if (r2 == 0) goto L_0x00a2
            android.view.animation.Interpolator r2 = r11.mLinearOutSlowInInterpolator
            goto L_0x00a4
        L_0x00a2:
            android.view.animation.Interpolator r2 = r11.mFastOutSlowInInterpolator
        L_0x00a4:
            r11.mInterpolator = r2
            r11.updateLayoutHeight(r0)
            goto L_0x00fc
        L_0x00aa:
            boolean r2 = r11.mIsGroupExpanded
            if (r2 == 0) goto L_0x00b2
            boolean r2 = r11.mEnableGroupVolumeUX
            if (r2 == 0) goto L_0x00c3
        L_0x00b2:
            androidx.mediarouter.media.MediaRouter$RouteInfo r2 = r11.mRoute
            boolean r5 = r11.mVolumeControlEnabled
            if (r5 == 0) goto L_0x00c0
            int r2 = r2.getVolumeHandling()
            if (r2 != r1) goto L_0x00c0
            r2 = r1
            goto L_0x00c1
        L_0x00c0:
            r2 = r0
        L_0x00c1:
            if (r2 != 0) goto L_0x00c9
        L_0x00c3:
            android.widget.LinearLayout r2 = r11.mVolumeControlLayout
            r2.setVisibility(r4)
            goto L_0x00fc
        L_0x00c9:
            android.widget.LinearLayout r2 = r11.mVolumeControlLayout
            int r2 = r2.getVisibility()
            if (r2 != r4) goto L_0x00fc
            android.widget.LinearLayout r2 = r11.mVolumeControlLayout
            r2.setVisibility(r0)
            android.widget.SeekBar r2 = r11.mVolumeSlider
            androidx.mediarouter.media.MediaRouter$RouteInfo r5 = r11.mRoute
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mVolumeMax
            r2.setMax(r5)
            android.widget.SeekBar r2 = r11.mVolumeSlider
            androidx.mediarouter.media.MediaRouter$RouteInfo r5 = r11.mRoute
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mVolume
            r2.setProgress(r5)
            androidx.mediarouter.app.MediaRouteExpandCollapseButton r2 = r11.mGroupExpandCollapseButton
            boolean r5 = r11.isGroup()
            if (r5 == 0) goto L_0x00f8
            r5 = r0
            goto L_0x00f9
        L_0x00f8:
            r5 = r4
        L_0x00f9:
            r2.setVisibility(r5)
        L_0x00fc:
            boolean r2 = r11.canShowPlaybackControlLayout()
            if (r2 == 0) goto L_0x0205
            android.support.v4.media.MediaDescriptionCompat r2 = r11.mDescription
            if (r2 != 0) goto L_0x0108
            r2 = r3
            goto L_0x010a
        L_0x0108:
            java.lang.CharSequence r2 = r2.mTitle
        L_0x010a:
            boolean r5 = android.text.TextUtils.isEmpty(r2)
            r5 = r5 ^ r1
            android.support.v4.media.MediaDescriptionCompat r6 = r11.mDescription
            if (r6 != 0) goto L_0x0114
            goto L_0x0116
        L_0x0114:
            java.lang.CharSequence r3 = r6.mSubtitle
        L_0x0116:
            boolean r6 = android.text.TextUtils.isEmpty(r3)
            r6 = r6 ^ r1
            androidx.mediarouter.media.MediaRouter$RouteInfo r7 = r11.mRoute
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.mPresentationDisplayId
            r8 = -1
            if (r7 == r8) goto L_0x012e
            android.widget.TextView r2 = r11.mTitleView
            r3 = 2131952808(0x7f1304a8, float:1.954207E38)
            r2.setText(r3)
            goto L_0x015f
        L_0x012e:
            android.support.v4.media.session.PlaybackStateCompat r7 = r11.mState
            if (r7 == 0) goto L_0x0157
            int r7 = r7.mState
            if (r7 != 0) goto L_0x0137
            goto L_0x0157
        L_0x0137:
            if (r5 != 0) goto L_0x0144
            if (r6 != 0) goto L_0x0144
            android.widget.TextView r2 = r11.mTitleView
            r3 = 2131952813(0x7f1304ad, float:1.954208E38)
            r2.setText(r3)
            goto L_0x015f
        L_0x0144:
            if (r5 == 0) goto L_0x014d
            android.widget.TextView r5 = r11.mTitleView
            r5.setText(r2)
            r2 = r1
            goto L_0x014e
        L_0x014d:
            r2 = r0
        L_0x014e:
            if (r6 == 0) goto L_0x0160
            android.widget.TextView r5 = r11.mSubtitleView
            r5.setText(r3)
            r3 = r1
            goto L_0x0161
        L_0x0157:
            android.widget.TextView r2 = r11.mTitleView
            r3 = 2131952814(0x7f1304ae, float:1.9542081E38)
            r2.setText(r3)
        L_0x015f:
            r2 = r1
        L_0x0160:
            r3 = r0
        L_0x0161:
            android.widget.TextView r5 = r11.mTitleView
            if (r2 == 0) goto L_0x0167
            r2 = r0
            goto L_0x0168
        L_0x0167:
            r2 = r4
        L_0x0168:
            r5.setVisibility(r2)
            android.widget.TextView r2 = r11.mSubtitleView
            if (r3 == 0) goto L_0x0171
            r3 = r0
            goto L_0x0172
        L_0x0171:
            r3 = r4
        L_0x0172:
            r2.setVisibility(r3)
            android.support.v4.media.session.PlaybackStateCompat r2 = r11.mState
            if (r2 == 0) goto L_0x0205
            int r2 = r2.mState
            r3 = 6
            if (r2 == r3) goto L_0x0184
            r3 = 3
            if (r2 != r3) goto L_0x0182
            goto L_0x0184
        L_0x0182:
            r2 = r0
            goto L_0x0185
        L_0x0184:
            r2 = r1
        L_0x0185:
            android.widget.ImageButton r3 = r11.mPlaybackControlButton
            android.content.Context r3 = r3.getContext()
            r5 = 0
            if (r2 == 0) goto L_0x01a9
            android.support.v4.media.session.PlaybackStateCompat r7 = r11.mState
            java.util.Objects.requireNonNull(r7)
            long r7 = r7.mActions
            r9 = 514(0x202, double:2.54E-321)
            long r7 = r7 & r9
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x019f
            r7 = r1
            goto L_0x01a0
        L_0x019f:
            r7 = r0
        L_0x01a0:
            if (r7 == 0) goto L_0x01a9
            r2 = 2130969464(0x7f040378, float:1.754761E38)
            r5 = 2131952815(0x7f1304af, float:1.9542083E38)
            goto L_0x01e4
        L_0x01a9:
            if (r2 == 0) goto L_0x01c5
            android.support.v4.media.session.PlaybackStateCompat r7 = r11.mState
            java.util.Objects.requireNonNull(r7)
            long r7 = r7.mActions
            r9 = 1
            long r7 = r7 & r9
            int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r7 == 0) goto L_0x01bb
            r7 = r1
            goto L_0x01bc
        L_0x01bb:
            r7 = r0
        L_0x01bc:
            if (r7 == 0) goto L_0x01c5
            r2 = 2130969468(0x7f04037c, float:1.7547619E38)
            r5 = 2131952817(0x7f1304b1, float:1.9542087E38)
            goto L_0x01e4
        L_0x01c5:
            if (r2 != 0) goto L_0x01e1
            android.support.v4.media.session.PlaybackStateCompat r2 = r11.mState
            java.util.Objects.requireNonNull(r2)
            long r7 = r2.mActions
            r9 = 516(0x204, double:2.55E-321)
            long r7 = r7 & r9
            int r2 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r2 == 0) goto L_0x01d7
            r2 = r1
            goto L_0x01d8
        L_0x01d7:
            r2 = r0
        L_0x01d8:
            if (r2 == 0) goto L_0x01e1
            r2 = 2130969465(0x7f040379, float:1.7547613E38)
            r5 = 2131952816(0x7f1304b0, float:1.9542085E38)
            goto L_0x01e4
        L_0x01e1:
            r1 = r0
            r2 = r1
            r5 = r2
        L_0x01e4:
            android.widget.ImageButton r6 = r11.mPlaybackControlButton
            if (r1 == 0) goto L_0x01e9
            goto L_0x01ea
        L_0x01e9:
            r0 = r4
        L_0x01ea:
            r6.setVisibility(r0)
            if (r1 == 0) goto L_0x0205
            android.widget.ImageButton r0 = r11.mPlaybackControlButton
            int r1 = androidx.mediarouter.app.MediaRouterThemeHelper.getThemeResource(r3, r2)
            r0.setImageResource(r1)
            android.widget.ImageButton r0 = r11.mPlaybackControlButton
            android.content.res.Resources r1 = r3.getResources()
            java.lang.CharSequence r1 = r1.getText(r5)
            r0.setContentDescription(r1)
        L_0x0205:
            r11.updateLayoutHeight(r12)
            return
        L_0x0209:
            r11.dismiss()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteControllerDialog.update(boolean):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0035, code lost:
        if (r0 == false) goto L_0x0037;
     */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateArtIconIfNeeded() {
        /*
            r6 = this;
            android.support.v4.media.MediaDescriptionCompat r0 = r6.mDescription
            r1 = 0
            if (r0 != 0) goto L_0x0007
            r2 = r1
            goto L_0x0009
        L_0x0007:
            android.graphics.Bitmap r2 = r0.mIcon
        L_0x0009:
            if (r0 != 0) goto L_0x000c
            goto L_0x000e
        L_0x000c:
            android.net.Uri r1 = r0.mIconUri
        L_0x000e:
            androidx.mediarouter.app.MediaRouteControllerDialog$FetchArtTask r0 = r6.mFetchArtTask
            if (r0 != 0) goto L_0x0015
            android.graphics.Bitmap r3 = r6.mArtIconBitmap
            goto L_0x0017
        L_0x0015:
            android.graphics.Bitmap r3 = r0.mIconBitmap
        L_0x0017:
            if (r0 != 0) goto L_0x001c
            android.net.Uri r0 = r6.mArtIconUri
            goto L_0x001e
        L_0x001c:
            android.net.Uri r0 = r0.mIconUri
        L_0x001e:
            r4 = 0
            r5 = 1
            if (r3 == r2) goto L_0x0023
            goto L_0x0037
        L_0x0023:
            if (r3 != 0) goto L_0x0039
            if (r0 == 0) goto L_0x002e
            boolean r2 = r0.equals(r1)
            if (r2 == 0) goto L_0x002e
            goto L_0x0032
        L_0x002e:
            if (r0 != 0) goto L_0x0034
            if (r1 != 0) goto L_0x0034
        L_0x0032:
            r0 = r5
            goto L_0x0035
        L_0x0034:
            r0 = r4
        L_0x0035:
            if (r0 != 0) goto L_0x0039
        L_0x0037:
            r0 = r5
            goto L_0x003a
        L_0x0039:
            r0 = r4
        L_0x003a:
            if (r0 == 0) goto L_0x005a
            boolean r0 = r6.isGroup()
            if (r0 == 0) goto L_0x0047
            boolean r0 = r6.mEnableGroupVolumeUX
            if (r0 != 0) goto L_0x0047
            goto L_0x005a
        L_0x0047:
            androidx.mediarouter.app.MediaRouteControllerDialog$FetchArtTask r0 = r6.mFetchArtTask
            if (r0 == 0) goto L_0x004e
            r0.cancel(r5)
        L_0x004e:
            androidx.mediarouter.app.MediaRouteControllerDialog$FetchArtTask r0 = new androidx.mediarouter.app.MediaRouteControllerDialog$FetchArtTask
            r0.<init>()
            r6.mFetchArtTask = r0
            java.lang.Void[] r6 = new java.lang.Void[r4]
            r0.execute(r6)
        L_0x005a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteControllerDialog.updateArtIconIfNeeded():void");
    }

    public final void updateLayout() {
        int dialogWidth = MediaRouteDialogHelper.getDialogWidth(this.mContext);
        getWindow().setLayout(dialogWidth, -2);
        View decorView = getWindow().getDecorView();
        this.mDialogContentWidth = (dialogWidth - decorView.getPaddingLeft()) - decorView.getPaddingRight();
        Resources resources = this.mContext.getResources();
        this.mVolumeGroupListItemIconSize = resources.getDimensionPixelSize(C1777R.dimen.mr_controller_volume_group_list_item_icon_size);
        this.mVolumeGroupListItemHeight = resources.getDimensionPixelSize(C1777R.dimen.mr_controller_volume_group_list_item_height);
        this.mVolumeGroupListMaxHeight = resources.getDimensionPixelSize(C1777R.dimen.mr_controller_volume_group_list_max_height);
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        updateArtIconIfNeeded();
        update(false);
    }

    public final void updateLayoutHeight(final boolean z) {
        this.mDefaultControlLayout.requestLayout();
        this.mDefaultControlLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public final void onGlobalLayout() {
                int i;
                int i2;
                boolean z;
                boolean z2;
                HashMap hashMap;
                HashMap hashMap2;
                Bitmap bitmap;
                float f;
                float f2;
                ImageView.ScaleType scaleType;
                MediaRouteControllerDialog.this.mDefaultControlLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                if (mediaRouteControllerDialog.mIsGroupListAnimating) {
                    mediaRouteControllerDialog.mIsGroupListAnimationPending = true;
                    return;
                }
                boolean z3 = z;
                int i3 = mediaRouteControllerDialog.mMediaMainControlLayout.getLayoutParams().height;
                MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, -1);
                mediaRouteControllerDialog.updateMediaControlVisibility(mediaRouteControllerDialog.canShowPlaybackControlLayout());
                View decorView = mediaRouteControllerDialog.getWindow().getDecorView();
                decorView.measure(View.MeasureSpec.makeMeasureSpec(mediaRouteControllerDialog.getWindow().getAttributes().width, 1073741824), 0);
                MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, i3);
                if (!(mediaRouteControllerDialog.mArtView.getDrawable() instanceof BitmapDrawable) || (bitmap = ((BitmapDrawable) mediaRouteControllerDialog.mArtView.getDrawable()).getBitmap()) == null) {
                    i = 0;
                } else {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    if (width >= height) {
                        f = ((float) mediaRouteControllerDialog.mDialogContentWidth) * ((float) height);
                        f2 = (float) width;
                    } else {
                        f = ((float) mediaRouteControllerDialog.mDialogContentWidth) * 9.0f;
                        f2 = 16.0f;
                    }
                    i = (int) ((f / f2) + 0.5f);
                    ImageView imageView = mediaRouteControllerDialog.mArtView;
                    if (bitmap.getWidth() >= bitmap.getHeight()) {
                        scaleType = ImageView.ScaleType.FIT_XY;
                    } else {
                        scaleType = ImageView.ScaleType.FIT_CENTER;
                    }
                    imageView.setScaleType(scaleType);
                }
                int mainControllerHeight = mediaRouteControllerDialog.getMainControllerHeight(mediaRouteControllerDialog.canShowPlaybackControlLayout());
                int size = mediaRouteControllerDialog.mGroupMemberRoutes.size();
                if (mediaRouteControllerDialog.isGroup()) {
                    i2 = mediaRouteControllerDialog.mRoute.getMemberRoutes().size() * mediaRouteControllerDialog.mVolumeGroupListItemHeight;
                } else {
                    i2 = 0;
                }
                if (size > 0) {
                    i2 += mediaRouteControllerDialog.mVolumeGroupListPaddingTop;
                }
                int min = Math.min(i2, mediaRouteControllerDialog.mVolumeGroupListMaxHeight);
                if (!mediaRouteControllerDialog.mIsGroupExpanded) {
                    min = 0;
                }
                int max = Math.max(i, min) + mainControllerHeight;
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int height2 = rect.height() - (mediaRouteControllerDialog.mDialogAreaLayout.getMeasuredHeight() - mediaRouteControllerDialog.mDefaultControlLayout.getMeasuredHeight());
                if (i <= 0 || max > height2) {
                    if (mediaRouteControllerDialog.mMediaMainControlLayout.getMeasuredHeight() + mediaRouteControllerDialog.mVolumeGroupList.getLayoutParams().height >= mediaRouteControllerDialog.mDefaultControlLayout.getMeasuredHeight()) {
                        mediaRouteControllerDialog.mArtView.setVisibility(8);
                    }
                    max = min + mainControllerHeight;
                    i = 0;
                } else {
                    mediaRouteControllerDialog.mArtView.setVisibility(0);
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mArtView, i);
                }
                if (!mediaRouteControllerDialog.canShowPlaybackControlLayout() || max > height2) {
                    mediaRouteControllerDialog.mPlaybackControlLayout.setVisibility(8);
                } else {
                    mediaRouteControllerDialog.mPlaybackControlLayout.setVisibility(0);
                }
                if (mediaRouteControllerDialog.mPlaybackControlLayout.getVisibility() == 0) {
                    z = true;
                } else {
                    z = false;
                }
                mediaRouteControllerDialog.updateMediaControlVisibility(z);
                if (mediaRouteControllerDialog.mPlaybackControlLayout.getVisibility() == 0) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                int mainControllerHeight2 = mediaRouteControllerDialog.getMainControllerHeight(z2);
                int max2 = Math.max(i, min) + mainControllerHeight2;
                if (max2 > height2) {
                    min -= max2 - height2;
                } else {
                    height2 = max2;
                }
                mediaRouteControllerDialog.mMediaMainControlLayout.clearAnimation();
                mediaRouteControllerDialog.mVolumeGroupList.clearAnimation();
                mediaRouteControllerDialog.mDefaultControlLayout.clearAnimation();
                if (z3) {
                    mediaRouteControllerDialog.animateLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, mainControllerHeight2);
                    mediaRouteControllerDialog.animateLayoutHeight(mediaRouteControllerDialog.mVolumeGroupList, min);
                    mediaRouteControllerDialog.animateLayoutHeight(mediaRouteControllerDialog.mDefaultControlLayout, height2);
                } else {
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mMediaMainControlLayout, mainControllerHeight2);
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mVolumeGroupList, min);
                    MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mDefaultControlLayout, height2);
                }
                MediaRouteControllerDialog.setLayoutHeight(mediaRouteControllerDialog.mExpandableAreaLayout, rect.height());
                List<MediaRouter.RouteInfo> memberRoutes = mediaRouteControllerDialog.mRoute.getMemberRoutes();
                if (memberRoutes.isEmpty()) {
                    mediaRouteControllerDialog.mGroupMemberRoutes.clear();
                    mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
                } else if (new HashSet(mediaRouteControllerDialog.mGroupMemberRoutes).equals(new HashSet(memberRoutes))) {
                    mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
                } else {
                    if (z3) {
                        OverlayListView overlayListView = mediaRouteControllerDialog.mVolumeGroupList;
                        VolumeGroupAdapter volumeGroupAdapter = mediaRouteControllerDialog.mVolumeGroupAdapter;
                        hashMap = new HashMap();
                        int firstVisiblePosition = overlayListView.getFirstVisiblePosition();
                        for (int i4 = 0; i4 < overlayListView.getChildCount(); i4++) {
                            Object item = volumeGroupAdapter.getItem(firstVisiblePosition + i4);
                            View childAt = overlayListView.getChildAt(i4);
                            hashMap.put(item, new Rect(childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()));
                        }
                    } else {
                        hashMap = null;
                    }
                    if (z3) {
                        Context context = mediaRouteControllerDialog.mContext;
                        OverlayListView overlayListView2 = mediaRouteControllerDialog.mVolumeGroupList;
                        VolumeGroupAdapter volumeGroupAdapter2 = mediaRouteControllerDialog.mVolumeGroupAdapter;
                        hashMap2 = new HashMap();
                        int firstVisiblePosition2 = overlayListView2.getFirstVisiblePosition();
                        for (int i5 = 0; i5 < overlayListView2.getChildCount(); i5++) {
                            Object item2 = volumeGroupAdapter2.getItem(firstVisiblePosition2 + i5);
                            View childAt2 = overlayListView2.getChildAt(i5);
                            Bitmap createBitmap = Bitmap.createBitmap(childAt2.getWidth(), childAt2.getHeight(), Bitmap.Config.ARGB_8888);
                            childAt2.draw(new Canvas(createBitmap));
                            hashMap2.put(item2, new BitmapDrawable(context.getResources(), createBitmap));
                        }
                    } else {
                        hashMap2 = null;
                    }
                    ArrayList arrayList = mediaRouteControllerDialog.mGroupMemberRoutes;
                    HashSet hashSet = new HashSet(memberRoutes);
                    hashSet.removeAll(arrayList);
                    mediaRouteControllerDialog.mGroupMemberRoutesAdded = hashSet;
                    HashSet hashSet2 = new HashSet(mediaRouteControllerDialog.mGroupMemberRoutes);
                    hashSet2.removeAll(memberRoutes);
                    mediaRouteControllerDialog.mGroupMemberRoutesRemoved = hashSet2;
                    mediaRouteControllerDialog.mGroupMemberRoutes.addAll(0, mediaRouteControllerDialog.mGroupMemberRoutesAdded);
                    mediaRouteControllerDialog.mGroupMemberRoutes.removeAll(mediaRouteControllerDialog.mGroupMemberRoutesRemoved);
                    mediaRouteControllerDialog.mVolumeGroupAdapter.notifyDataSetChanged();
                    if (z3 && mediaRouteControllerDialog.mIsGroupExpanded) {
                        if (mediaRouteControllerDialog.mGroupMemberRoutesRemoved.size() + mediaRouteControllerDialog.mGroupMemberRoutesAdded.size() > 0) {
                            mediaRouteControllerDialog.mVolumeGroupList.setEnabled(false);
                            mediaRouteControllerDialog.mVolumeGroupList.requestLayout();
                            mediaRouteControllerDialog.mIsGroupListAnimating = true;
                            mediaRouteControllerDialog.mVolumeGroupList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(hashMap, hashMap2) {
                                public final /* synthetic */ Map val$previousRouteBitmapMap;
                                public final /* synthetic */ Map val$previousRouteBoundMap;

                                {
                                    this.val$previousRouteBoundMap = r2;
                                    this.val$previousRouteBitmapMap = r3;
                                }

                                public final void onGlobalLayout() {
                                    OverlayListView.OverlayObject overlayObject;
                                    int i;
                                    MediaRouteControllerDialog.this.mVolumeGroupList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                    MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                                    Map map = this.val$previousRouteBoundMap;
                                    Map map2 = this.val$previousRouteBitmapMap;
                                    Objects.requireNonNull(mediaRouteControllerDialog);
                                    HashSet hashSet = mediaRouteControllerDialog.mGroupMemberRoutesAdded;
                                    if (hashSet != null && mediaRouteControllerDialog.mGroupMemberRoutesRemoved != null) {
                                        int size = hashSet.size() - mediaRouteControllerDialog.mGroupMemberRoutesRemoved.size();
                                        C02659 r4 = new Animation.AnimationListener() {
                                            public final void onAnimationEnd(Animation animation) {
                                            }

                                            public final void onAnimationRepeat(Animation animation) {
                                            }

                                            public final void onAnimationStart(Animation animation) {
                                                OverlayListView overlayListView = MediaRouteControllerDialog.this.mVolumeGroupList;
                                                Objects.requireNonNull(overlayListView);
                                                Iterator it = overlayListView.mOverlayObjects.iterator();
                                                while (it.hasNext()) {
                                                    OverlayListView.OverlayObject overlayObject = (OverlayListView.OverlayObject) it.next();
                                                    Objects.requireNonNull(overlayObject);
                                                    if (!overlayObject.mIsAnimationStarted) {
                                                        overlayObject.mStartTime = overlayListView.getDrawingTime();
                                                        overlayObject.mIsAnimationStarted = true;
                                                    }
                                                }
                                                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                                                mediaRouteControllerDialog.mVolumeGroupList.postDelayed(mediaRouteControllerDialog.mGroupListFadeInAnimation, (long) mediaRouteControllerDialog.mGroupListAnimationDurationMs);
                                            }
                                        };
                                        int firstVisiblePosition = mediaRouteControllerDialog.mVolumeGroupList.getFirstVisiblePosition();
                                        boolean z = false;
                                        for (int i2 = 0; i2 < mediaRouteControllerDialog.mVolumeGroupList.getChildCount(); i2++) {
                                            View childAt = mediaRouteControllerDialog.mVolumeGroupList.getChildAt(i2);
                                            MediaRouter.RouteInfo routeInfo = (MediaRouter.RouteInfo) mediaRouteControllerDialog.mVolumeGroupAdapter.getItem(firstVisiblePosition + i2);
                                            Rect rect = (Rect) map.get(routeInfo);
                                            int top = childAt.getTop();
                                            if (rect != null) {
                                                i = rect.top;
                                            } else {
                                                i = (mediaRouteControllerDialog.mVolumeGroupListItemHeight * size) + top;
                                            }
                                            AnimationSet animationSet = new AnimationSet(true);
                                            HashSet hashSet2 = mediaRouteControllerDialog.mGroupMemberRoutesAdded;
                                            if (hashSet2 != null && hashSet2.contains(routeInfo)) {
                                                AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 0.0f);
                                                alphaAnimation.setDuration((long) mediaRouteControllerDialog.mGroupListFadeInDurationMs);
                                                animationSet.addAnimation(alphaAnimation);
                                                i = top;
                                            }
                                            TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (i - top), 0.0f);
                                            translateAnimation.setDuration((long) mediaRouteControllerDialog.mGroupListAnimationDurationMs);
                                            animationSet.addAnimation(translateAnimation);
                                            animationSet.setFillAfter(true);
                                            animationSet.setFillEnabled(true);
                                            animationSet.setInterpolator(mediaRouteControllerDialog.mInterpolator);
                                            if (!z) {
                                                animationSet.setAnimationListener(r4);
                                                z = true;
                                            }
                                            childAt.clearAnimation();
                                            childAt.startAnimation(animationSet);
                                            map.remove(routeInfo);
                                            map2.remove(routeInfo);
                                        }
                                        for (Map.Entry entry : map2.entrySet()) {
                                            MediaRouter.RouteInfo routeInfo2 = (MediaRouter.RouteInfo) entry.getKey();
                                            BitmapDrawable bitmapDrawable = (BitmapDrawable) entry.getValue();
                                            Rect rect2 = (Rect) map.get(routeInfo2);
                                            if (mediaRouteControllerDialog.mGroupMemberRoutesRemoved.contains(routeInfo2)) {
                                                overlayObject = new OverlayListView.OverlayObject(bitmapDrawable, rect2);
                                                overlayObject.mStartAlpha = 1.0f;
                                                overlayObject.mEndAlpha = 0.0f;
                                                overlayObject.mDuration = (long) mediaRouteControllerDialog.mGroupListFadeOutDurationMs;
                                                overlayObject.mInterpolator = mediaRouteControllerDialog.mInterpolator;
                                            } else {
                                                OverlayListView.OverlayObject overlayObject2 = new OverlayListView.OverlayObject(bitmapDrawable, rect2);
                                                overlayObject2.mDeltaY = mediaRouteControllerDialog.mVolumeGroupListItemHeight * size;
                                                overlayObject2.mDuration = (long) mediaRouteControllerDialog.mGroupListAnimationDurationMs;
                                                overlayObject2.mInterpolator = mediaRouteControllerDialog.mInterpolator;
                                                overlayObject2.mListener = new OverlayListView.OverlayObject.OnAnimationEndListener(routeInfo2) {
                                                    public final /* synthetic */ MediaRouter.RouteInfo val$route;

                                                    {
                                                        this.val$route = r2;
                                                    }
                                                };
                                                mediaRouteControllerDialog.mGroupMemberRoutesAnimatingWithBitmap.add(routeInfo2);
                                                overlayObject = overlayObject2;
                                            }
                                            OverlayListView overlayListView = mediaRouteControllerDialog.mVolumeGroupList;
                                            Objects.requireNonNull(overlayListView);
                                            overlayListView.mOverlayObjects.add(overlayObject);
                                        }
                                    }
                                }
                            });
                            return;
                        }
                    }
                    mediaRouteControllerDialog.mGroupMemberRoutesAdded = null;
                    mediaRouteControllerDialog.mGroupMemberRoutesRemoved = null;
                }
            }
        });
    }

    public final void updateMediaControlVisibility(boolean z) {
        int i;
        View view = this.mDividerView;
        int i2 = 0;
        if (this.mVolumeControlLayout.getVisibility() != 0 || !z) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        LinearLayout linearLayout = this.mMediaMainControlLayout;
        if (this.mVolumeControlLayout.getVisibility() == 8 && !z) {
            i2 = 8;
        }
        linearLayout.setVisibility(i2);
    }

    public static void setLayoutHeight(View view, int i) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    public final void animateLayoutHeight(final ViewGroup viewGroup, final int i) {
        final int i2 = viewGroup.getLayoutParams().height;
        C02637 r1 = new Animation() {
            public final void applyTransformation(float f, Transformation transformation) {
                int i = i2;
                MediaRouteControllerDialog.setLayoutHeight(viewGroup, i - ((int) (((float) (i - i)) * f)));
            }
        };
        r1.setDuration((long) this.mGroupListAnimationDurationMs);
        r1.setInterpolator(this.mInterpolator);
        viewGroup.startAnimation(r1);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(MediaRouteSelector.EMPTY, this.mCallback, 2);
        Objects.requireNonNull(this.mRouter);
        boolean z = MediaRouter.DEBUG;
        setMediaSession();
    }

    public final void onCreate(Bundle bundle) {
        Interpolator interpolator;
        super.onCreate(bundle);
        getWindow().setBackgroundDrawableResource(17170445);
        setContentView((int) C1777R.layout.mr_controller_material_dialog_b);
        findViewById(16908315).setVisibility(8);
        ClickListener clickListener = new ClickListener();
        FrameLayout frameLayout = (FrameLayout) findViewById(C1777R.C1779id.mr_expandable_area);
        this.mExpandableAreaLayout = frameLayout;
        frameLayout.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                MediaRouteControllerDialog.this.dismiss();
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(C1777R.C1779id.mr_dialog_area);
        this.mDialogAreaLayout = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
            }
        });
        Context context = this.mContext;
        int themeColor = MediaRouterThemeHelper.getThemeColor(context, 0, C1777R.attr.colorPrimary);
        if (ColorUtils.calculateContrast(themeColor, MediaRouterThemeHelper.getThemeColor(context, 0, 16842801)) < 3.0d) {
            themeColor = MediaRouterThemeHelper.getThemeColor(context, 0, C1777R.attr.colorAccent);
        }
        Button button = (Button) findViewById(16908314);
        this.mDisconnectButton = button;
        button.setText(C1777R.string.mr_controller_disconnect);
        this.mDisconnectButton.setTextColor(themeColor);
        this.mDisconnectButton.setOnClickListener(clickListener);
        Button button2 = (Button) findViewById(16908313);
        this.mStopCastingButton = button2;
        button2.setText(C1777R.string.mr_controller_stop_casting);
        this.mStopCastingButton.setTextColor(themeColor);
        this.mStopCastingButton.setOnClickListener(clickListener);
        this.mRouteNameTextView = (TextView) findViewById(C1777R.C1779id.mr_name);
        ((ImageButton) findViewById(C1777R.C1779id.mr_close)).setOnClickListener(clickListener);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(C1777R.C1779id.mr_custom_control);
        this.mDefaultControlLayout = (FrameLayout) findViewById(C1777R.C1779id.mr_default_control);
        C02604 r1 = new View.OnClickListener() {
            public final void onClick(View view) {
                MediaControllerCompat mediaControllerCompat = MediaRouteControllerDialog.this.mMediaController;
                if (mediaControllerCompat != null) {
                    MediaControllerCompat.MediaControllerImplApi21 mediaControllerImplApi21 = mediaControllerCompat.mImpl;
                    Objects.requireNonNull(mediaControllerImplApi21);
                    PendingIntent sessionActivity = mediaControllerImplApi21.mControllerFwk.getSessionActivity();
                    if (sessionActivity != null) {
                        try {
                            sessionActivity.send();
                            MediaRouteControllerDialog.this.dismiss();
                        } catch (PendingIntent.CanceledException unused) {
                            Log.e("MediaRouteCtrlDialog", sessionActivity + " was not sent, it had been canceled.");
                        }
                    }
                }
            }
        };
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.mr_art);
        this.mArtView = imageView;
        imageView.setOnClickListener(r1);
        findViewById(C1777R.C1779id.mr_control_title_container).setOnClickListener(r1);
        this.mMediaMainControlLayout = (LinearLayout) findViewById(C1777R.C1779id.mr_media_main_control);
        this.mDividerView = findViewById(C1777R.C1779id.mr_control_divider);
        this.mPlaybackControlLayout = (RelativeLayout) findViewById(C1777R.C1779id.mr_playback_control);
        this.mTitleView = (TextView) findViewById(C1777R.C1779id.mr_control_title);
        this.mSubtitleView = (TextView) findViewById(C1777R.C1779id.mr_control_subtitle);
        ImageButton imageButton = (ImageButton) findViewById(C1777R.C1779id.mr_control_playback_ctrl);
        this.mPlaybackControlButton = imageButton;
        imageButton.setOnClickListener(clickListener);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(C1777R.C1779id.mr_volume_control);
        this.mVolumeControlLayout = linearLayout2;
        linearLayout2.setVisibility(8);
        SeekBar seekBar = (SeekBar) findViewById(C1777R.C1779id.mr_volume_slider);
        this.mVolumeSlider = seekBar;
        seekBar.setTag(this.mRoute);
        VolumeChangeListener volumeChangeListener = new VolumeChangeListener();
        this.mVolumeChangeListener = volumeChangeListener;
        this.mVolumeSlider.setOnSeekBarChangeListener(volumeChangeListener);
        this.mVolumeGroupList = (OverlayListView) findViewById(C1777R.C1779id.mr_volume_group_list);
        this.mGroupMemberRoutes = new ArrayList();
        VolumeGroupAdapter volumeGroupAdapter = new VolumeGroupAdapter(this.mVolumeGroupList.getContext(), this.mGroupMemberRoutes);
        this.mVolumeGroupAdapter = volumeGroupAdapter;
        this.mVolumeGroupList.setAdapter(volumeGroupAdapter);
        this.mGroupMemberRoutesAnimatingWithBitmap = new HashSet();
        Context context2 = this.mContext;
        LinearLayout linearLayout3 = this.mMediaMainControlLayout;
        OverlayListView overlayListView = this.mVolumeGroupList;
        boolean isGroup = isGroup();
        int themeColor2 = MediaRouterThemeHelper.getThemeColor(context2, 0, C1777R.attr.colorPrimary);
        int themeColor3 = MediaRouterThemeHelper.getThemeColor(context2, 0, C1777R.attr.colorPrimaryDark);
        if (isGroup && MediaRouterThemeHelper.getControllerColor(context2, 0) == -570425344) {
            themeColor3 = themeColor2;
            themeColor2 = -1;
        }
        linearLayout3.setBackgroundColor(themeColor2);
        overlayListView.setBackgroundColor(themeColor3);
        linearLayout3.setTag(Integer.valueOf(themeColor2));
        overlayListView.setTag(Integer.valueOf(themeColor3));
        MediaRouterThemeHelper.setVolumeSliderColor(this.mContext, (MediaRouteVolumeSlider) this.mVolumeSlider, this.mMediaMainControlLayout);
        HashMap hashMap = new HashMap();
        this.mVolumeSliderMap = hashMap;
        hashMap.put(this.mRoute, this.mVolumeSlider);
        MediaRouteExpandCollapseButton mediaRouteExpandCollapseButton = (MediaRouteExpandCollapseButton) findViewById(C1777R.C1779id.mr_group_expand_collapse);
        this.mGroupExpandCollapseButton = mediaRouteExpandCollapseButton;
        mediaRouteExpandCollapseButton.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                Interpolator interpolator;
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                boolean z = !mediaRouteControllerDialog.mIsGroupExpanded;
                mediaRouteControllerDialog.mIsGroupExpanded = z;
                if (z) {
                    mediaRouteControllerDialog.mVolumeGroupList.setVisibility(0);
                }
                MediaRouteControllerDialog mediaRouteControllerDialog2 = MediaRouteControllerDialog.this;
                Objects.requireNonNull(mediaRouteControllerDialog2);
                if (mediaRouteControllerDialog2.mIsGroupExpanded) {
                    interpolator = mediaRouteControllerDialog2.mLinearOutSlowInInterpolator;
                } else {
                    interpolator = mediaRouteControllerDialog2.mFastOutSlowInInterpolator;
                }
                mediaRouteControllerDialog2.mInterpolator = interpolator;
                MediaRouteControllerDialog.this.updateLayoutHeight(true);
            }
        });
        if (this.mIsGroupExpanded) {
            interpolator = this.mLinearOutSlowInInterpolator;
        } else {
            interpolator = this.mFastOutSlowInInterpolator;
        }
        this.mInterpolator = interpolator;
        this.mGroupListAnimationDurationMs = this.mContext.getResources().getInteger(C1777R.integer.mr_controller_volume_group_list_animation_duration_ms);
        this.mGroupListFadeInDurationMs = this.mContext.getResources().getInteger(C1777R.integer.mr_controller_volume_group_list_fade_in_duration_ms);
        this.mGroupListFadeOutDurationMs = this.mContext.getResources().getInteger(C1777R.integer.mr_controller_volume_group_list_fade_out_duration_ms);
        this.mCreated = true;
        updateLayout();
    }
}
