package androidx.mediarouter.app;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentController;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.mediarouter.media.MediaRouterParams;
import com.android.p012wm.shell.C1777R;
import com.google.android.setupdesign.R$styleable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class MediaRouteButton extends View {
    public static final int[] CHECKABLE_STATE_SET = {16842911};
    public static final int[] CHECKED_STATE_SET = {16842912};
    public static ConnectivityReceiver sConnectivityReceiver;
    public static final SparseArray<Drawable.ConstantState> sRemoteIndicatorCache = new SparseArray<>(2);
    public boolean mAlwaysVisible;
    public boolean mAttachedToWindow;
    public ColorStateList mButtonTint;
    public final MediaRouterCallback mCallback;
    public boolean mCheatSheetEnabled;
    public int mConnectionState;
    public R$styleable mDialogFactory;
    public int mLastConnectionState;
    public int mMinHeight;
    public int mMinWidth;
    public Drawable mRemoteIndicator;
    public RemoteIndicatorLoader mRemoteIndicatorLoader;
    public int mRemoteIndicatorResIdToLoad;
    public final MediaRouter mRouter;
    public MediaRouteSelector mSelector;
    public int mVisibility;

    public final class MediaRouterCallback extends MediaRouter.Callback {
        public MediaRouterCallback() {
        }

        public final void onProviderAdded() {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onProviderChanged() {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onProviderRemoved() {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onRouteAdded() {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onRouteChanged(MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onRouteRemoved() {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onRouteSelected(MediaRouter.RouteInfo routeInfo) {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onRouteUnselected() {
            MediaRouteButton.this.refreshRoute();
        }

        public final void onRouterParamsChanged(MediaRouterParams mediaRouterParams) {
            if (mediaRouterParams == null) {
                Objects.requireNonNull(MediaRouteButton.this);
                return;
            }
            throw null;
        }
    }

    public final class RemoteIndicatorLoader extends AsyncTask<Void, Void, Drawable> {
        public final Context mContext;
        public final int mResId;

        public RemoteIndicatorLoader(int i, Context context) {
            this.mResId = i;
            this.mContext = context;
        }

        public final Object doInBackground(Object[] objArr) {
            Void[] voidArr = (Void[]) objArr;
            if (MediaRouteButton.sRemoteIndicatorCache.get(this.mResId) == null) {
                return AppCompatResources.getDrawable(this.mContext, this.mResId);
            }
            return null;
        }

        public final void onCancelled(Object obj) {
            Drawable drawable = (Drawable) obj;
            if (drawable != null) {
                MediaRouteButton.sRemoteIndicatorCache.put(this.mResId, drawable.getConstantState());
            }
            MediaRouteButton.this.mRemoteIndicatorLoader = null;
        }

        public final void onPostExecute(Object obj) {
            Drawable drawable = (Drawable) obj;
            if (drawable != null) {
                MediaRouteButton.sRemoteIndicatorCache.put(this.mResId, drawable.getConstantState());
                MediaRouteButton.this.mRemoteIndicatorLoader = null;
            } else {
                Drawable.ConstantState constantState = MediaRouteButton.sRemoteIndicatorCache.get(this.mResId);
                if (constantState != null) {
                    drawable = constantState.newDrawable();
                }
                MediaRouteButton.this.mRemoteIndicatorLoader = null;
            }
            MediaRouteButton.this.setRemoteIndicatorDrawableInternal(drawable);
        }
    }

    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.mRouter == null) {
            return onCreateDrawableState;
        }
        int i2 = this.mConnectionState;
        if (i2 == 1) {
            View.mergeDrawableStates(onCreateDrawableState, CHECKABLE_STATE_SET);
        } else if (i2 == 2) {
            View.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    public static final class ConnectivityReceiver extends BroadcastReceiver {
        public ArrayList mButtons;
        public final Context mContext;
        public boolean mIsConnected = true;

        public ConnectivityReceiver(Context context) {
            this.mContext = context;
            this.mButtons = new ArrayList();
        }

        public final void onReceive(Context context, Intent intent) {
            boolean z;
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && this.mIsConnected != (!intent.getBooleanExtra("noConnectivity", false))) {
                this.mIsConnected = z;
                Iterator it = this.mButtons.iterator();
                while (it.hasNext()) {
                    ((MediaRouteButton) it.next()).refreshVisibility();
                }
            }
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaRouteButton(android.content.Context r10, android.util.AttributeSet r11) {
        /*
            r9 = this;
            android.view.ContextThemeWrapper r0 = new android.view.ContextThemeWrapper
            int r1 = androidx.mediarouter.app.MediaRouterThemeHelper.getRouterThemeId(r10)
            r0.<init>(r10, r1)
            r10 = 2130969469(0x7f04037d, float:1.754762E38)
            int r10 = androidx.mediarouter.app.MediaRouterThemeHelper.getThemeResource(r0, r10)
            if (r10 == 0) goto L_0x0018
            android.view.ContextThemeWrapper r1 = new android.view.ContextThemeWrapper
            r1.<init>(r0, r10)
            r0 = r1
        L_0x0018:
            r10 = 2130969457(0x7f040371, float:1.7547596E38)
            r9.<init>(r0, r11, r10)
            androidx.mediarouter.media.MediaRouteSelector r0 = androidx.mediarouter.media.MediaRouteSelector.EMPTY
            r9.mSelector = r0
            com.google.android.setupdesign.R$styleable r0 = com.google.android.setupdesign.R$styleable.sDefault
            r9.mDialogFactory = r0
            r0 = 0
            r9.mVisibility = r0
            android.content.Context r8 = r9.getContext()
            int[] r3 = androidx.mediarouter.R$styleable.MediaRouteButton
            android.content.res.TypedArray r10 = r8.obtainStyledAttributes(r11, r3, r10, r0)
            r7 = 0
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r1 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            r6 = 2130969457(0x7f040371, float:1.7547596E38)
            r1 = r9
            r2 = r8
            r4 = r11
            r5 = r10
            androidx.core.view.ViewCompat.Api29Impl.saveAttributeDataForStyleable(r1, r2, r3, r4, r5, r6, r7)
            boolean r11 = r9.isInEditMode()
            r1 = 3
            if (r11 == 0) goto L_0x0058
            r11 = 0
            r9.mRouter = r11
            r9.mCallback = r11
            int r10 = r10.getResourceId(r1, r0)
            android.graphics.drawable.Drawable r10 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r8, r10)
            r9.mRemoteIndicator = r10
            goto L_0x00f4
        L_0x0058:
            androidx.mediarouter.media.MediaRouter r11 = androidx.mediarouter.media.MediaRouter.getInstance(r8)
            r9.mRouter = r11
            androidx.mediarouter.app.MediaRouteButton$MediaRouterCallback r11 = new androidx.mediarouter.app.MediaRouteButton$MediaRouterCallback
            r11.<init>()
            r9.mCallback = r11
            androidx.mediarouter.media.MediaRouter$RouteInfo r11 = androidx.mediarouter.media.MediaRouter.getSelectedRoute()
            boolean r2 = r11.isDefaultOrBluetooth()
            r3 = 1
            r2 = r2 ^ r3
            if (r2 == 0) goto L_0x0074
            int r11 = r11.mConnectionState
            goto L_0x0075
        L_0x0074:
            r11 = r0
        L_0x0075:
            r9.mConnectionState = r11
            r9.mLastConnectionState = r11
            androidx.mediarouter.app.MediaRouteButton$ConnectivityReceiver r11 = sConnectivityReceiver
            if (r11 != 0) goto L_0x0088
            androidx.mediarouter.app.MediaRouteButton$ConnectivityReceiver r11 = new androidx.mediarouter.app.MediaRouteButton$ConnectivityReceiver
            android.content.Context r2 = r8.getApplicationContext()
            r11.<init>(r2)
            sConnectivityReceiver = r11
        L_0x0088:
            r11 = 4
            android.content.res.ColorStateList r11 = r10.getColorStateList(r11)
            r9.mButtonTint = r11
            int r11 = r10.getDimensionPixelSize(r0, r0)
            r9.mMinWidth = r11
            int r11 = r10.getDimensionPixelSize(r3, r0)
            r9.mMinHeight = r11
            int r11 = r10.getResourceId(r1, r0)
            r1 = 2
            int r1 = r10.getResourceId(r1, r0)
            r9.mRemoteIndicatorResIdToLoad = r1
            r10.recycle()
            int r10 = r9.mRemoteIndicatorResIdToLoad
            if (r10 == 0) goto L_0x00c0
            android.util.SparseArray<android.graphics.drawable.Drawable$ConstantState> r1 = sRemoteIndicatorCache
            java.lang.Object r10 = r1.get(r10)
            android.graphics.drawable.Drawable$ConstantState r10 = (android.graphics.drawable.Drawable.ConstantState) r10
            if (r10 == 0) goto L_0x00c0
            android.graphics.drawable.Drawable r10 = r10.newDrawable()
            r9.mRemoteIndicatorResIdToLoad = r0
            r9.setRemoteIndicatorDrawableInternal(r10)
        L_0x00c0:
            android.graphics.drawable.Drawable r10 = r9.mRemoteIndicator
            if (r10 != 0) goto L_0x00ee
            if (r11 == 0) goto L_0x00eb
            android.util.SparseArray<android.graphics.drawable.Drawable$ConstantState> r10 = sRemoteIndicatorCache
            java.lang.Object r10 = r10.get(r11)
            android.graphics.drawable.Drawable$ConstantState r10 = (android.graphics.drawable.Drawable.ConstantState) r10
            if (r10 == 0) goto L_0x00d8
            android.graphics.drawable.Drawable r10 = r10.newDrawable()
            r9.setRemoteIndicatorDrawableInternal(r10)
            goto L_0x00ee
        L_0x00d8:
            androidx.mediarouter.app.MediaRouteButton$RemoteIndicatorLoader r10 = new androidx.mediarouter.app.MediaRouteButton$RemoteIndicatorLoader
            android.content.Context r1 = r9.getContext()
            r10.<init>(r11, r1)
            r9.mRemoteIndicatorLoader = r10
            java.util.concurrent.Executor r11 = android.os.AsyncTask.SERIAL_EXECUTOR
            java.lang.Void[] r0 = new java.lang.Void[r0]
            r10.executeOnExecutor(r11, r0)
            goto L_0x00ee
        L_0x00eb:
            r9.loadRemoteIndicatorIfNeeded()
        L_0x00ee:
            r9.updateContentDescription()
            r9.setClickable(r3)
        L_0x00f4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteButton.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public final void loadRemoteIndicatorIfNeeded() {
        if (this.mRemoteIndicatorResIdToLoad > 0) {
            RemoteIndicatorLoader remoteIndicatorLoader = this.mRemoteIndicatorLoader;
            if (remoteIndicatorLoader != null) {
                remoteIndicatorLoader.cancel(false);
            }
            RemoteIndicatorLoader remoteIndicatorLoader2 = new RemoteIndicatorLoader(this.mRemoteIndicatorResIdToLoad, getContext());
            this.mRemoteIndicatorLoader = remoteIndicatorLoader2;
            this.mRemoteIndicatorResIdToLoad = 0;
            remoteIndicatorLoader2.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, new Void[0]);
        }
    }

    public final void refreshRoute() {
        int i;
        Objects.requireNonNull(this.mRouter);
        MediaRouter.RouteInfo selectedRoute = MediaRouter.getSelectedRoute();
        boolean z = true;
        boolean z2 = !selectedRoute.isDefaultOrBluetooth();
        if (z2) {
            i = selectedRoute.mConnectionState;
        } else {
            i = 0;
        }
        if (this.mConnectionState != i) {
            this.mConnectionState = i;
            updateContentDescription();
            refreshDrawableState();
        }
        if (i == 1) {
            loadRemoteIndicatorIfNeeded();
        }
        if (this.mAttachedToWindow) {
            if (!this.mAlwaysVisible && !z2) {
                MediaRouter mediaRouter = this.mRouter;
                MediaRouteSelector mediaRouteSelector = this.mSelector;
                Objects.requireNonNull(mediaRouter);
                if (!MediaRouter.isRouteAvailable(mediaRouteSelector)) {
                    z = false;
                }
            }
            setEnabled(z);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x001c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void refreshVisibility() {
        /*
            r2 = this;
            int r0 = r2.mVisibility
            if (r0 != 0) goto L_0x0013
            boolean r0 = r2.mAlwaysVisible
            if (r0 != 0) goto L_0x0013
            androidx.mediarouter.app.MediaRouteButton$ConnectivityReceiver r0 = sConnectivityReceiver
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.mIsConnected
            if (r0 != 0) goto L_0x0013
            r0 = 4
            goto L_0x0015
        L_0x0013:
            int r0 = r2.mVisibility
        L_0x0015:
            super.setVisibility(r0)
            android.graphics.drawable.Drawable r0 = r2.mRemoteIndicator
            if (r0 == 0) goto L_0x0029
            int r2 = r2.getVisibility()
            r1 = 0
            if (r2 != 0) goto L_0x0025
            r2 = 1
            goto L_0x0026
        L_0x0025:
            r2 = r1
        L_0x0026:
            r0.setVisible(r2, r1)
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteButton.refreshVisibility():void");
    }

    public final void setRemoteIndicatorDrawableInternal(Drawable drawable) {
        boolean z;
        RemoteIndicatorLoader remoteIndicatorLoader = this.mRemoteIndicatorLoader;
        if (remoteIndicatorLoader != null) {
            remoteIndicatorLoader.cancel(false);
        }
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null) {
            drawable2.setCallback((Drawable.Callback) null);
            unscheduleDrawable(this.mRemoteIndicator);
        }
        if (drawable != null) {
            if (this.mButtonTint != null) {
                drawable = drawable.mutate();
                drawable.setTintList(this.mButtonTint);
            }
            drawable.setCallback(this);
            drawable.setState(getDrawableState());
            if (getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            drawable.setVisible(z, false);
        }
        this.mRemoteIndicator = drawable;
        refreshDrawableState();
    }

    public final void setVisibility(int i) {
        this.mVisibility = i;
        refreshVisibility();
    }

    public final void updateContentDescription() {
        int i;
        int i2 = this.mConnectionState;
        if (i2 == 1) {
            i = C1777R.string.mr_cast_button_connecting;
        } else if (i2 != 2) {
            i = C1777R.string.mr_cast_button_disconnected;
        } else {
            i = C1777R.string.mr_cast_button_connected;
        }
        String string = getContext().getString(i);
        setContentDescription(string);
        if (!this.mCheatSheetEnabled || TextUtils.isEmpty(string)) {
            string = null;
        }
        setTooltipText(string);
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mRemoteIndicator != null) {
            this.mRemoteIndicator.setState(getDrawableState());
            if (this.mRemoteIndicator.getCurrent() instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) this.mRemoteIndicator.getCurrent();
                int i = this.mConnectionState;
                if (i == 1 || this.mLastConnectionState != i) {
                    if (!animationDrawable.isRunning()) {
                        animationDrawable.start();
                    }
                } else if (i == 2 && !animationDrawable.isRunning()) {
                    animationDrawable.selectDrawable(animationDrawable.getNumberOfFrames() - 1);
                }
            }
            invalidate();
        }
        this.mLastConnectionState = this.mConnectionState;
    }

    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mRemoteIndicator;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            this.mAttachedToWindow = true;
            if (!this.mSelector.isEmpty()) {
                MediaRouter mediaRouter = this.mRouter;
                MediaRouteSelector mediaRouteSelector = this.mSelector;
                MediaRouterCallback mediaRouterCallback = this.mCallback;
                Objects.requireNonNull(mediaRouter);
                mediaRouter.addCallback(mediaRouteSelector, mediaRouterCallback, 0);
            }
            refreshRoute();
            ConnectivityReceiver connectivityReceiver = sConnectivityReceiver;
            Objects.requireNonNull(connectivityReceiver);
            if (connectivityReceiver.mButtons.size() == 0) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                connectivityReceiver.mContext.registerReceiver(connectivityReceiver, intentFilter);
            }
            connectivityReceiver.mButtons.add(this);
        }
    }

    public final void onDetachedFromWindow() {
        if (!isInEditMode()) {
            this.mAttachedToWindow = false;
            if (!this.mSelector.isEmpty()) {
                this.mRouter.removeCallback(this.mCallback);
            }
            ConnectivityReceiver connectivityReceiver = sConnectivityReceiver;
            Objects.requireNonNull(connectivityReceiver);
            connectivityReceiver.mButtons.remove(this);
            if (connectivityReceiver.mButtons.size() == 0) {
                connectivityReceiver.mContext.unregisterReceiver(connectivityReceiver);
            }
        }
        super.onDetachedFromWindow();
    }

    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mRemoteIndicator != null) {
            int paddingLeft = getPaddingLeft();
            int width = getWidth() - getPaddingRight();
            int paddingTop = getPaddingTop();
            int height = getHeight() - getPaddingBottom();
            int intrinsicWidth = this.mRemoteIndicator.getIntrinsicWidth();
            int intrinsicHeight = this.mRemoteIndicator.getIntrinsicHeight();
            int i = (((width - paddingLeft) - intrinsicWidth) / 2) + paddingLeft;
            int i2 = (((height - paddingTop) - intrinsicHeight) / 2) + paddingTop;
            this.mRemoteIndicator.setBounds(i, i2, intrinsicWidth + i, intrinsicHeight + i2);
            this.mRemoteIndicator.draw(canvas);
        }
    }

    public final void onMeasure(int i, int i2) {
        int i3;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int i4 = this.mMinWidth;
        Drawable drawable = this.mRemoteIndicator;
        int i5 = 0;
        if (drawable != null) {
            i3 = getPaddingRight() + getPaddingLeft() + drawable.getIntrinsicWidth();
        } else {
            i3 = 0;
        }
        int max = Math.max(i4, i3);
        int i6 = this.mMinHeight;
        Drawable drawable2 = this.mRemoteIndicator;
        if (drawable2 != null) {
            i5 = getPaddingBottom() + getPaddingTop() + drawable2.getIntrinsicHeight();
        }
        int max2 = Math.max(i6, i5);
        if (mode == Integer.MIN_VALUE) {
            size = Math.min(size, max);
        } else if (mode != 1073741824) {
            size = max;
        }
        if (mode2 == Integer.MIN_VALUE) {
            size2 = Math.min(size2, max2);
        } else if (mode2 != 1073741824) {
            size2 = max2;
        }
        setMeasuredDimension(size, size2);
    }

    public final boolean performClick() {
        boolean z;
        MediaRouteSelector mediaRouteSelector;
        Activity activity;
        FragmentManagerImpl fragmentManagerImpl;
        boolean performClick = super.performClick();
        if (!performClick) {
            playSoundEffect(0);
        }
        loadRemoteIndicatorIfNeeded();
        if (this.mAttachedToWindow) {
            Objects.requireNonNull(this.mRouter);
            MediaRouter.checkCallingThread();
            MediaRouter.getGlobalRouter();
            Context context = getContext();
            while (true) {
                mediaRouteSelector = null;
                if (!(context instanceof ContextWrapper)) {
                    activity = null;
                    break;
                } else if (context instanceof Activity) {
                    activity = (Activity) context;
                    break;
                } else {
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
            if (activity instanceof FragmentActivity) {
                FragmentActivity fragmentActivity = (FragmentActivity) activity;
                Objects.requireNonNull(fragmentActivity);
                FragmentController fragmentController = fragmentActivity.mFragments;
                Objects.requireNonNull(fragmentController);
                fragmentManagerImpl = fragmentController.mHost.mFragmentManager;
            } else {
                fragmentManagerImpl = null;
            }
            if (fragmentManagerImpl != null) {
                Objects.requireNonNull(this.mRouter);
                if (MediaRouter.getSelectedRoute().isDefaultOrBluetooth()) {
                    if (fragmentManagerImpl.findFragmentByTag("android.support.v7.mediarouter:MediaRouteChooserDialogFragment") != null) {
                        Log.w("MediaRouteButton", "showDialog(): Route chooser dialog already showing!");
                    } else {
                        Objects.requireNonNull(this.mDialogFactory);
                        MediaRouteChooserDialogFragment mediaRouteChooserDialogFragment = new MediaRouteChooserDialogFragment();
                        MediaRouteSelector mediaRouteSelector2 = this.mSelector;
                        if (mediaRouteSelector2 != null) {
                            mediaRouteChooserDialogFragment.ensureRouteSelector();
                            if (!mediaRouteChooserDialogFragment.mSelector.equals(mediaRouteSelector2)) {
                                mediaRouteChooserDialogFragment.mSelector = mediaRouteSelector2;
                                Bundle bundle = mediaRouteChooserDialogFragment.mArguments;
                                if (bundle == null) {
                                    bundle = new Bundle();
                                }
                                bundle.putBundle("selector", mediaRouteSelector2.mBundle);
                                mediaRouteChooserDialogFragment.setArguments(bundle);
                                AppCompatDialog appCompatDialog = mediaRouteChooserDialogFragment.mDialog;
                                if (appCompatDialog != null) {
                                    if (mediaRouteChooserDialogFragment.mUseDynamicGroup) {
                                        ((MediaRouteDynamicChooserDialog) appCompatDialog).setRouteSelector(mediaRouteSelector2);
                                    } else {
                                        ((MediaRouteChooserDialog) appCompatDialog).setRouteSelector(mediaRouteSelector2);
                                    }
                                }
                            }
                            BackStackRecord backStackRecord = new BackStackRecord(fragmentManagerImpl);
                            backStackRecord.doAddOp(0, mediaRouteChooserDialogFragment, "android.support.v7.mediarouter:MediaRouteChooserDialogFragment", 1);
                            backStackRecord.commitInternal(true);
                        } else {
                            throw new IllegalArgumentException("selector must not be null");
                        }
                    }
                } else if (fragmentManagerImpl.findFragmentByTag("android.support.v7.mediarouter:MediaRouteControllerDialogFragment") != null) {
                    Log.w("MediaRouteButton", "showDialog(): Route controller dialog already showing!");
                } else {
                    Objects.requireNonNull(this.mDialogFactory);
                    MediaRouteControllerDialogFragment mediaRouteControllerDialogFragment = new MediaRouteControllerDialogFragment();
                    MediaRouteSelector mediaRouteSelector3 = this.mSelector;
                    if (mediaRouteSelector3 != null) {
                        if (mediaRouteControllerDialogFragment.mSelector == null) {
                            Bundle bundle2 = mediaRouteControllerDialogFragment.mArguments;
                            if (bundle2 != null) {
                                Bundle bundle3 = bundle2.getBundle("selector");
                                MediaRouteSelector mediaRouteSelector4 = MediaRouteSelector.EMPTY;
                                if (bundle3 != null) {
                                    mediaRouteSelector = new MediaRouteSelector(bundle3, (ArrayList) null);
                                }
                                mediaRouteControllerDialogFragment.mSelector = mediaRouteSelector;
                            }
                            if (mediaRouteControllerDialogFragment.mSelector == null) {
                                mediaRouteControllerDialogFragment.mSelector = MediaRouteSelector.EMPTY;
                            }
                        }
                        if (!mediaRouteControllerDialogFragment.mSelector.equals(mediaRouteSelector3)) {
                            mediaRouteControllerDialogFragment.mSelector = mediaRouteSelector3;
                            Bundle bundle4 = mediaRouteControllerDialogFragment.mArguments;
                            if (bundle4 == null) {
                                bundle4 = new Bundle();
                            }
                            bundle4.putBundle("selector", mediaRouteSelector3.mBundle);
                            mediaRouteControllerDialogFragment.setArguments(bundle4);
                            AppCompatDialog appCompatDialog2 = mediaRouteControllerDialogFragment.mDialog;
                            if (appCompatDialog2 != null && mediaRouteControllerDialogFragment.mUseDynamicGroup) {
                                ((MediaRouteDynamicControllerDialog) appCompatDialog2).setRouteSelector(mediaRouteSelector3);
                            }
                        }
                        BackStackRecord backStackRecord2 = new BackStackRecord(fragmentManagerImpl);
                        backStackRecord2.doAddOp(0, mediaRouteControllerDialogFragment, "android.support.v7.mediarouter:MediaRouteControllerDialogFragment", 1);
                        backStackRecord2.commitInternal(true);
                    } else {
                        throw new IllegalArgumentException("selector must not be null");
                    }
                }
                z = true;
                if (!z || performClick) {
                    return true;
                }
                return false;
            }
            throw new IllegalStateException("The activity must be a subclass of FragmentActivity");
        }
        z = false;
        if (!z) {
        }
        return true;
    }

    public final boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable) || drawable == this.mRemoteIndicator) {
            return true;
        }
        return false;
    }
}
