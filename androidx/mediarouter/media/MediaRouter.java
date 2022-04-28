package androidx.mediarouter.media;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.core.util.Pair;
import androidx.mediarouter.media.MediaRoute2Provider;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import com.android.p012wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda15;
import com.android.settingslib.wifi.WifiTracker;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.google.common.util.concurrent.ListenableFuture;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public final class MediaRouter {
    public static final boolean DEBUG = Log.isLoggable("MediaRouter", 3);
    public static GlobalMediaRouter sGlobal;
    public final ArrayList<CallbackRecord> mCallbackRecords = new ArrayList<>();
    public final Context mContext;

    public static abstract class Callback {
        public void onProviderAdded() {
        }

        public void onProviderChanged() {
        }

        public void onProviderRemoved() {
        }

        public void onRouteAdded() {
        }

        public abstract void onRouteChanged(RouteInfo routeInfo);

        public void onRouteRemoved() {
        }

        @Deprecated
        public void onRouteSelected(RouteInfo routeInfo) {
        }

        @Deprecated
        public void onRouteUnselected() {
        }

        public void onRouteVolumeChanged(RouteInfo routeInfo) {
        }

        public void onRouterParamsChanged(MediaRouterParams mediaRouterParams) {
        }
    }

    public static abstract class ControlRequestCallback {
        public void onError(String str, Bundle bundle) {
        }

        public void onResult(Bundle bundle) {
        }
    }

    public static final class GlobalMediaRouter implements SystemMediaRouteProvider.SyncCallback, RegisteredMediaRouteProviderWatcher.Callback {
        public MediaRouterActiveScanThrottlingHelper mActiveScanThrottlingHelper;
        public final Context mApplicationContext;
        public RouteInfo mBluetoothRoute;
        public int mCallbackCount;
        public final CallbackHandler mCallbackHandler = new CallbackHandler();
        public RouteInfo mDefaultRoute;
        public MediaRouteDiscoveryRequest mDiscoveryRequest;
        public MediaRouteDiscoveryRequest mDiscoveryRequestForMr2Provider;
        public C02823 mDynamicRoutesListener = new MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener() {
            public final void onRoutesChanged(MediaRouteProvider.DynamicGroupRouteController dynamicGroupRouteController, MediaRouteDescriptor mediaRouteDescriptor, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> collection) {
                GlobalMediaRouter globalMediaRouter = GlobalMediaRouter.this;
                if (dynamicGroupRouteController == globalMediaRouter.mRequestedRouteController && mediaRouteDescriptor != null) {
                    RouteInfo routeInfo = globalMediaRouter.mRequestedRoute;
                    Objects.requireNonNull(routeInfo);
                    ProviderInfo providerInfo = routeInfo.mProvider;
                    String id = mediaRouteDescriptor.getId();
                    RouteInfo routeInfo2 = new RouteInfo(providerInfo, id, GlobalMediaRouter.this.assignRouteUniqueId(providerInfo, id));
                    routeInfo2.maybeUpdateDescriptor(mediaRouteDescriptor);
                    GlobalMediaRouter globalMediaRouter2 = GlobalMediaRouter.this;
                    if (globalMediaRouter2.mSelectedRoute != routeInfo2) {
                        globalMediaRouter2.notifyTransfer(globalMediaRouter2, routeInfo2, globalMediaRouter2.mRequestedRouteController, 3, globalMediaRouter2.mRequestedRoute, collection);
                        GlobalMediaRouter globalMediaRouter3 = GlobalMediaRouter.this;
                        globalMediaRouter3.mRequestedRoute = null;
                        globalMediaRouter3.mRequestedRouteController = null;
                    }
                } else if (dynamicGroupRouteController == globalMediaRouter.mSelectedRouteController) {
                    if (mediaRouteDescriptor != null) {
                        globalMediaRouter.updateRouteDescriptorAndNotify(globalMediaRouter.mSelectedRoute, mediaRouteDescriptor);
                    }
                    GlobalMediaRouter.this.mSelectedRoute.updateDynamicDescriptors(collection);
                }
            }
        };
        public boolean mIsInitialized;
        public final boolean mLowRam;
        public MediaRoute2Provider mMr2Provider;
        public final RemoteControlClientCompat$PlaybackInfo mPlaybackInfo = new RemoteControlClientCompat$PlaybackInfo();
        public final ProviderCallback mProviderCallback = new ProviderCallback();
        public final ArrayList<ProviderInfo> mProviders = new ArrayList<>();
        public RegisteredMediaRouteProviderWatcher mRegisteredProviderWatcher;
        public final ArrayList<RemoteControlClientRecord> mRemoteControlClients = new ArrayList<>();
        public RouteInfo mRequestedRoute;
        public MediaRouteProvider.DynamicGroupRouteController mRequestedRouteController;
        public final HashMap mRouteControllerMap = new HashMap();
        public final ArrayList<WeakReference<MediaRouter>> mRouters = new ArrayList<>();
        public final ArrayList<RouteInfo> mRoutes = new ArrayList<>();
        public RouteInfo mSelectedRoute;
        public MediaRouteProvider.RouteController mSelectedRouteController;
        public SystemMediaRouteProvider.Api24Impl mSystemProvider;
        public PrepareTransferNotifier mTransferNotifier;
        public boolean mTransferReceiverDeclared;
        public final HashMap mUniqueIdMap = new HashMap();

        public final class CallbackHandler extends Handler {
            public final ArrayList mDynamicGroupRoutes = new ArrayList();
            public final ArrayList<CallbackRecord> mTempCallbackRecords = new ArrayList<>();

            public CallbackHandler() {
            }

            public static void invokeCallback(CallbackRecord callbackRecord, int i, Object obj) {
                RouteInfo routeInfo;
                boolean z;
                MediaRouter mediaRouter = callbackRecord.mRouter;
                Callback callback = callbackRecord.mCallback;
                int i2 = 65280 & i;
                if (i2 == 256) {
                    if (i == 264 || i == 262) {
                        routeInfo = (RouteInfo) ((Pair) obj).second;
                    } else {
                        routeInfo = (RouteInfo) obj;
                    }
                    if (i == 264 || i == 262) {
                        RouteInfo routeInfo2 = (RouteInfo) ((Pair) obj).first;
                    }
                    if (routeInfo != null) {
                        if ((callbackRecord.mFlags & 2) != 0 || routeInfo.matchesSelector(callbackRecord.mSelector)) {
                            z = true;
                        } else {
                            MediaRouter.getGlobalRouter();
                            z = false;
                        }
                        if (z) {
                            switch (i) {
                                case 257:
                                    callback.onRouteAdded();
                                    return;
                                case 258:
                                    callback.onRouteRemoved();
                                    return;
                                case 259:
                                    callback.onRouteChanged(routeInfo);
                                    return;
                                case 260:
                                    callback.onRouteVolumeChanged(routeInfo);
                                    return;
                                case 261:
                                    Objects.requireNonNull(callback);
                                    return;
                                case 262:
                                    Objects.requireNonNull(callback);
                                    callback.onRouteSelected(routeInfo);
                                    return;
                                case 263:
                                    Objects.requireNonNull(callback);
                                    callback.onRouteUnselected();
                                    return;
                                case 264:
                                    Objects.requireNonNull(callback);
                                    callback.onRouteSelected(routeInfo);
                                    return;
                                default:
                                    return;
                            }
                        }
                    }
                } else if (i2 == 512) {
                    ProviderInfo providerInfo = (ProviderInfo) obj;
                    switch (i) {
                        case 513:
                            callback.onProviderAdded();
                            return;
                        case 514:
                            callback.onProviderRemoved();
                            return;
                        case 515:
                            callback.onProviderChanged();
                            return;
                        default:
                            return;
                    }
                } else if (i2 == 768 && i == 769) {
                    callback.onRouterParamsChanged((MediaRouterParams) obj);
                }
            }

            public final void handleMessage(Message message) {
                int findUserRouteRecord;
                int i = message.what;
                Object obj = message.obj;
                if (i == 259) {
                    RouteInfo selectedRoute = GlobalMediaRouter.this.getSelectedRoute();
                    Objects.requireNonNull(selectedRoute);
                    String str = selectedRoute.mUniqueId;
                    RouteInfo routeInfo = (RouteInfo) obj;
                    Objects.requireNonNull(routeInfo);
                    if (str.equals(routeInfo.mUniqueId)) {
                        GlobalMediaRouter.this.updateSelectedRouteIfNeeded(true);
                    }
                }
                if (i == 262) {
                    RouteInfo routeInfo2 = (RouteInfo) ((Pair) obj).second;
                    GlobalMediaRouter.this.mSystemProvider.onSyncRouteSelected(routeInfo2);
                    if (GlobalMediaRouter.this.mDefaultRoute != null && routeInfo2.isDefaultOrBluetooth()) {
                        Iterator it = this.mDynamicGroupRoutes.iterator();
                        while (it.hasNext()) {
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteRemoved((RouteInfo) it.next());
                        }
                        this.mDynamicGroupRoutes.clear();
                    }
                } else if (i != 264) {
                    switch (i) {
                        case 257:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteAdded((RouteInfo) obj);
                            break;
                        case 258:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteRemoved((RouteInfo) obj);
                            break;
                        case 259:
                            SystemMediaRouteProvider.Api24Impl api24Impl = GlobalMediaRouter.this.mSystemProvider;
                            RouteInfo routeInfo3 = (RouteInfo) obj;
                            Objects.requireNonNull(api24Impl);
                            if (routeInfo3.getProviderInstance() != api24Impl && (findUserRouteRecord = api24Impl.findUserRouteRecord(routeInfo3)) >= 0) {
                                api24Impl.updateUserRouteProperties(api24Impl.mUserRouteRecords.get(findUserRouteRecord));
                                break;
                            }
                    }
                } else {
                    RouteInfo routeInfo4 = (RouteInfo) ((Pair) obj).second;
                    this.mDynamicGroupRoutes.add(routeInfo4);
                    GlobalMediaRouter.this.mSystemProvider.onSyncRouteAdded(routeInfo4);
                    GlobalMediaRouter.this.mSystemProvider.onSyncRouteSelected(routeInfo4);
                }
                try {
                    int size = GlobalMediaRouter.this.mRouters.size();
                    while (true) {
                        size--;
                        if (size >= 0) {
                            MediaRouter mediaRouter = (MediaRouter) GlobalMediaRouter.this.mRouters.get(size).get();
                            if (mediaRouter == null) {
                                GlobalMediaRouter.this.mRouters.remove(size);
                            } else {
                                this.mTempCallbackRecords.addAll(mediaRouter.mCallbackRecords);
                            }
                        } else {
                            int size2 = this.mTempCallbackRecords.size();
                            for (int i2 = 0; i2 < size2; i2++) {
                                invokeCallback(this.mTempCallbackRecords.get(i2), i, obj);
                            }
                            return;
                        }
                    }
                } finally {
                    this.mTempCallbackRecords.clear();
                }
            }

            public final void post(int i, Object obj) {
                obtainMessage(i, obj).sendToTarget();
            }
        }

        public final class Mr2ProviderCallback extends MediaRoute2Provider.Callback {
            public Mr2ProviderCallback() {
            }
        }

        public final class ProviderCallback extends MediaRouteProvider.Callback {
            public ProviderCallback() {
            }
        }

        public final class RemoteControlClientRecord {
        }

        public final RouteInfo chooseFallbackRoute() {
            boolean z;
            Iterator<RouteInfo> it = this.mRoutes.iterator();
            while (it.hasNext()) {
                RouteInfo next = it.next();
                if (next != this.mDefaultRoute) {
                    if (next.getProviderInstance() != this.mSystemProvider || !next.supportsControlCategory("android.media.intent.category.LIVE_AUDIO") || next.supportsControlCategory("android.media.intent.category.LIVE_VIDEO")) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z && next.isSelectable()) {
                        return next;
                    }
                }
            }
            return this.mDefaultRoute;
        }

        @SuppressLint({"NewApi", "SyntheticAccessor"})
        public final void ensureInitialized() {
            if (!this.mIsInitialized) {
                this.mIsInitialized = true;
                Context context = this.mApplicationContext;
                int i = MediaTransferReceiver.$r8$clinit;
                Intent intent = new Intent(context, MediaTransferReceiver.class);
                intent.setPackage(context.getPackageName());
                boolean z = false;
                if (context.getPackageManager().queryBroadcastReceivers(intent, 0).size() > 0) {
                    z = true;
                }
                this.mTransferReceiverDeclared = z;
                if (z) {
                    this.mMr2Provider = new MediaRoute2Provider(this.mApplicationContext, new Mr2ProviderCallback());
                } else {
                    this.mMr2Provider = null;
                }
                this.mSystemProvider = new SystemMediaRouteProvider.Api24Impl(this.mApplicationContext, this);
                this.mActiveScanThrottlingHelper = new MediaRouterActiveScanThrottlingHelper(new Runnable() {
                    public final void run() {
                        GlobalMediaRouter.this.updateDiscoveryRequest();
                    }
                });
                addProvider(this.mSystemProvider);
                MediaRoute2Provider mediaRoute2Provider = this.mMr2Provider;
                if (mediaRoute2Provider != null) {
                    addProvider(mediaRoute2Provider);
                }
                RegisteredMediaRouteProviderWatcher registeredMediaRouteProviderWatcher = new RegisteredMediaRouteProviderWatcher(this.mApplicationContext, this);
                this.mRegisteredProviderWatcher = registeredMediaRouteProviderWatcher;
                if (!registeredMediaRouteProviderWatcher.mRunning) {
                    registeredMediaRouteProviderWatcher.mRunning = true;
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
                    intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
                    intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
                    intentFilter.addDataScheme("package");
                    registeredMediaRouteProviderWatcher.mContext.registerReceiver(registeredMediaRouteProviderWatcher.mScanPackagesReceiver, intentFilter, (String) null, registeredMediaRouteProviderWatcher.mHandler);
                    registeredMediaRouteProviderWatcher.mHandler.post(registeredMediaRouteProviderWatcher.mScanPackagesRunnable);
                }
            }
        }

        public final ProviderInfo findProviderInfo(MediaRouteProvider mediaRouteProvider) {
            int size = this.mProviders.size();
            for (int i = 0; i < size; i++) {
                if (this.mProviders.get(i).mProviderInstance == mediaRouteProvider) {
                    return this.mProviders.get(i);
                }
            }
            return null;
        }

        public final int findRouteByUniqueId(String str) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                if (this.mRoutes.get(i).mUniqueId.equals(str)) {
                    return i;
                }
            }
            return -1;
        }

        public final RouteInfo getSelectedRoute() {
            RouteInfo routeInfo = this.mSelectedRoute;
            if (routeInfo != null) {
                return routeInfo;
            }
            throw new IllegalStateException("There is no currently selected route.  The media router has not yet been fully initialized.");
        }

        public final void maybeUpdateMemberRouteControllers() {
            if (this.mSelectedRoute.isGroup()) {
                List<RouteInfo> memberRoutes = this.mSelectedRoute.getMemberRoutes();
                HashSet hashSet = new HashSet();
                for (RouteInfo routeInfo : memberRoutes) {
                    hashSet.add(routeInfo.mUniqueId);
                }
                Iterator it = this.mRouteControllerMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry entry = (Map.Entry) it.next();
                    if (!hashSet.contains(entry.getKey())) {
                        MediaRouteProvider.RouteController routeController = (MediaRouteProvider.RouteController) entry.getValue();
                        routeController.onUnselect(0);
                        routeController.onRelease();
                        it.remove();
                    }
                }
                for (RouteInfo next : memberRoutes) {
                    if (!this.mRouteControllerMap.containsKey(next.mUniqueId)) {
                        MediaRouteProvider.RouteController onCreateRouteController = next.getProviderInstance().onCreateRouteController(next.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                        onCreateRouteController.onSelect();
                        this.mRouteControllerMap.put(next.mUniqueId, onCreateRouteController);
                    }
                }
            }
        }

        public final void notifyTransfer(GlobalMediaRouter globalMediaRouter, RouteInfo routeInfo, MediaRouteProvider.RouteController routeController, int i, RouteInfo routeInfo2, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> collection) {
            PrepareTransferNotifier prepareTransferNotifier = this.mTransferNotifier;
            if (prepareTransferNotifier != null) {
                if (!prepareTransferNotifier.mFinished && !prepareTransferNotifier.mCanceled) {
                    prepareTransferNotifier.mCanceled = true;
                    MediaRouteProvider.RouteController routeController2 = prepareTransferNotifier.mToRouteController;
                    if (routeController2 != null) {
                        routeController2.onUnselect(0);
                        prepareTransferNotifier.mToRouteController.onRelease();
                    }
                }
                this.mTransferNotifier = null;
            }
            PrepareTransferNotifier prepareTransferNotifier2 = new PrepareTransferNotifier(globalMediaRouter, routeInfo, routeController, i, routeInfo2, collection);
            this.mTransferNotifier = prepareTransferNotifier2;
            prepareTransferNotifier2.finishTransfer();
        }

        public final void selectRoute(RouteInfo routeInfo, int i) {
            if (!this.mRoutes.contains(routeInfo)) {
                Log.w("MediaRouter", "Ignoring attempt to select removed route: " + routeInfo);
            } else if (!routeInfo.mEnabled) {
                Log.w("MediaRouter", "Ignoring attempt to select disabled route: " + routeInfo);
            } else {
                MediaRouteProvider providerInstance = routeInfo.getProviderInstance();
                MediaRoute2Provider mediaRoute2Provider = this.mMr2Provider;
                if (providerInstance != mediaRoute2Provider || this.mSelectedRoute == routeInfo) {
                    selectRouteInternal(routeInfo, i);
                    return;
                }
                String str = routeInfo.mDescriptorId;
                Objects.requireNonNull(mediaRoute2Provider);
                MediaRoute2Info routeById = mediaRoute2Provider.getRouteById(str);
                if (routeById == null) {
                    MotionLayout$$ExternalSyntheticOutline0.m9m("transferTo: Specified route not found. routeId=", str, "MR2Provider");
                } else {
                    mediaRoute2Provider.mMediaRouter2.transferTo(routeById);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0021, code lost:
            if (r0 != false) goto L_0x002c;
         */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x00b8 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:23:0x00b9  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void selectRouteInternal(androidx.mediarouter.media.MediaRouter.RouteInfo r13, int r14) {
            /*
                r12 = this;
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r0 = androidx.mediarouter.media.MediaRouter.sGlobal
                r1 = 0
                r2 = 1
                r3 = 3
                if (r0 == 0) goto L_0x002c
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r12.mBluetoothRoute
                if (r0 == 0) goto L_0x00b4
                java.util.Objects.requireNonNull(r13)
                androidx.mediarouter.media.MediaRouter.checkCallingThread()
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r0 = androidx.mediarouter.media.MediaRouter.getGlobalRouter()
                java.util.Objects.requireNonNull(r0)
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r0.mDefaultRoute
                if (r0 == 0) goto L_0x0024
                if (r0 != r13) goto L_0x0020
                r0 = r2
                goto L_0x0021
            L_0x0020:
                r0 = r1
            L_0x0021:
                if (r0 == 0) goto L_0x00b4
                goto L_0x002c
            L_0x0024:
                java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
                java.lang.String r13 = "There is no default route.  The media router has not yet been fully initialized."
                r12.<init>(r13)
                throw r12
            L_0x002c:
                java.lang.Thread r0 = java.lang.Thread.currentThread()
                java.lang.StackTraceElement[] r0 = r0.getStackTrace()
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r5 = r3
            L_0x003a:
                int r6 = r0.length
                if (r5 >= r6) goto L_0x0066
                r6 = r0[r5]
                java.lang.String r7 = r6.getClassName()
                r4.append(r7)
                java.lang.String r7 = "."
                r4.append(r7)
                java.lang.String r7 = r6.getMethodName()
                r4.append(r7)
                java.lang.String r7 = ":"
                r4.append(r7)
                int r6 = r6.getLineNumber()
                r4.append(r6)
                java.lang.String r6 = "  "
                r4.append(r6)
                int r5 = r5 + 1
                goto L_0x003a
            L_0x0066:
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r0 = androidx.mediarouter.media.MediaRouter.sGlobal
                if (r0 != 0) goto L_0x0090
                java.lang.String r0 = "MediaRouter"
                java.lang.String r5 = "setSelectedRouteInternal is called while sGlobal is null: pkgName="
                java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
                android.content.Context r6 = r12.mApplicationContext
                java.lang.String r6 = r6.getPackageName()
                r5.append(r6)
                java.lang.String r6 = ", callers="
                r5.append(r6)
                java.lang.String r4 = r4.toString()
                r5.append(r4)
                java.lang.String r4 = r5.toString()
                android.util.Log.w(r0, r4)
                goto L_0x00b4
            L_0x0090:
                java.lang.String r0 = "MediaRouter"
                java.lang.String r5 = "Default route is selected while a BT route is available: pkgName="
                java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
                android.content.Context r6 = r12.mApplicationContext
                java.lang.String r6 = r6.getPackageName()
                r5.append(r6)
                java.lang.String r6 = ", callers="
                r5.append(r6)
                java.lang.String r4 = r4.toString()
                r5.append(r4)
                java.lang.String r4 = r5.toString()
                android.util.Log.w(r0, r4)
            L_0x00b4:
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r12.mSelectedRoute
                if (r0 != r13) goto L_0x00b9
                return
            L_0x00b9:
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r12.mRequestedRoute
                r4 = 0
                if (r0 == 0) goto L_0x00ce
                r12.mRequestedRoute = r4
                androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController r0 = r12.mRequestedRouteController
                if (r0 == 0) goto L_0x00ce
                r0.onUnselect(r3)
                androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController r0 = r12.mRequestedRouteController
                r0.onRelease()
                r12.mRequestedRouteController = r4
            L_0x00ce:
                boolean r0 = r12.mTransferReceiverDeclared
                if (r0 == 0) goto L_0x0155
                java.util.Objects.requireNonNull(r13)
                androidx.mediarouter.media.MediaRouter$ProviderInfo r0 = r13.mProvider
                java.util.Objects.requireNonNull(r0)
                androidx.mediarouter.media.MediaRouteProviderDescriptor r0 = r0.mDescriptor
                if (r0 == 0) goto L_0x00e3
                boolean r0 = r0.mSupportsDynamicGroupRoute
                if (r0 == 0) goto L_0x00e3
                r1 = r2
            L_0x00e3:
                if (r1 == 0) goto L_0x0155
                androidx.mediarouter.media.MediaRouteProvider r0 = r13.getProviderInstance()
                java.lang.String r1 = r13.mDescriptorId
                androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController r0 = r0.onCreateDynamicGroupRouteController(r1)
                if (r0 == 0) goto L_0x013e
                android.content.Context r14 = r12.mApplicationContext
                java.lang.Object r1 = androidx.core.content.ContextCompat.sLock
                java.util.concurrent.Executor r14 = r14.getMainExecutor()
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter$3 r1 = r12.mDynamicRoutesListener
                java.lang.Object r2 = r0.mLock
                monitor-enter(r2)
                if (r14 == 0) goto L_0x0133
                if (r1 == 0) goto L_0x012b
                r0.mExecutor = r14     // Catch:{ all -> 0x013b }
                r0.mListener = r1     // Catch:{ all -> 0x013b }
                java.util.ArrayList r14 = r0.mPendingRoutes     // Catch:{ all -> 0x013b }
                if (r14 == 0) goto L_0x0122
                boolean r14 = r14.isEmpty()     // Catch:{ all -> 0x013b }
                if (r14 != 0) goto L_0x0122
                androidx.mediarouter.media.MediaRouteDescriptor r14 = r0.mPendingGroupRoute     // Catch:{ all -> 0x013b }
                java.util.ArrayList r3 = r0.mPendingRoutes     // Catch:{ all -> 0x013b }
                r0.mPendingGroupRoute = r4     // Catch:{ all -> 0x013b }
                r0.mPendingRoutes = r4     // Catch:{ all -> 0x013b }
                java.util.concurrent.Executor r4 = r0.mExecutor     // Catch:{ all -> 0x013b }
                androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController$1 r5 = new androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController$1     // Catch:{ all -> 0x013b }
                r5.<init>(r1, r14, r3)     // Catch:{ all -> 0x013b }
                r4.execute(r5)     // Catch:{ all -> 0x013b }
            L_0x0122:
                monitor-exit(r2)     // Catch:{ all -> 0x013b }
                r12.mRequestedRoute = r13
                r12.mRequestedRouteController = r0
                r0.onSelect()
                return
            L_0x012b:
                java.lang.NullPointerException r12 = new java.lang.NullPointerException     // Catch:{ all -> 0x013b }
                java.lang.String r13 = "Listener shouldn't be null"
                r12.<init>(r13)     // Catch:{ all -> 0x013b }
                throw r12     // Catch:{ all -> 0x013b }
            L_0x0133:
                java.lang.NullPointerException r12 = new java.lang.NullPointerException     // Catch:{ all -> 0x013b }
                java.lang.String r13 = "Executor shouldn't be null"
                r12.<init>(r13)     // Catch:{ all -> 0x013b }
                throw r12     // Catch:{ all -> 0x013b }
            L_0x013b:
                r12 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x013b }
                throw r12
            L_0x013e:
                java.lang.String r0 = "MediaRouter"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "setSelectedRouteInternal: Failed to create dynamic group route controller. route="
                r1.append(r2)
                r1.append(r13)
                java.lang.String r1 = r1.toString()
                android.util.Log.w(r0, r1)
            L_0x0155:
                androidx.mediarouter.media.MediaRouteProvider r0 = r13.getProviderInstance()
                java.lang.String r1 = r13.mDescriptorId
                androidx.mediarouter.media.MediaRouteProvider$RouteController r8 = r0.onCreateRouteController(r1)
                if (r8 == 0) goto L_0x0164
                r8.onSelect()
            L_0x0164:
                boolean r0 = androidx.mediarouter.media.MediaRouter.DEBUG
                if (r0 == 0) goto L_0x017e
                java.lang.String r0 = "MediaRouter"
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Route selected: "
                r1.append(r2)
                r1.append(r13)
                java.lang.String r1 = r1.toString()
                android.util.Log.d(r0, r1)
            L_0x017e:
                androidx.mediarouter.media.MediaRouter$RouteInfo r0 = r12.mSelectedRoute
                if (r0 != 0) goto L_0x019c
                r12.mSelectedRoute = r13
                r12.mSelectedRouteController = r8
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter$CallbackHandler r12 = r12.mCallbackHandler
                r0 = 262(0x106, float:3.67E-43)
                androidx.core.util.Pair r1 = new androidx.core.util.Pair
                r1.<init>(r4, r13)
                java.util.Objects.requireNonNull(r12)
                android.os.Message r12 = r12.obtainMessage(r0, r1)
                r12.arg1 = r14
                r12.sendToTarget()
                return
            L_0x019c:
                r10 = 0
                r11 = 0
                r5 = r12
                r6 = r12
                r7 = r13
                r9 = r14
                r5.notifyTransfer(r6, r7, r8, r9, r10, r11)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.MediaRouter.GlobalMediaRouter.selectRouteInternal(androidx.mediarouter.media.MediaRouter$RouteInfo, int):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0108, code lost:
            if (r0.mDiscoveryRequestForMr2Provider.isActiveScan() == r2) goto L_0x013f;
         */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0096  */
        /* JADX WARNING: Removed duplicated region for block: B:29:0x00a6  */
        /* JADX WARNING: Removed duplicated region for block: B:99:0x00a7 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void updateDiscoveryRequest() {
            /*
                r20 = this;
                r0 = r20
                androidx.mediarouter.media.MediaRouteSelector$Builder r1 = new androidx.mediarouter.media.MediaRouteSelector$Builder
                r1.<init>()
                androidx.mediarouter.media.MediaRouterActiveScanThrottlingHelper r2 = r0.mActiveScanThrottlingHelper
                java.util.Objects.requireNonNull(r2)
                r3 = 0
                r2.mSuppressActiveScanTimeout = r3
                r3 = 0
                r2.mActiveScan = r3
                long r4 = android.os.SystemClock.elapsedRealtime()
                r2.mCurrentTime = r4
                android.os.Handler r4 = r2.mHandler
                java.lang.Runnable r2 = r2.mUpdateDiscoveryRequestRunnable
                r4.removeCallbacks(r2)
                java.util.ArrayList<java.lang.ref.WeakReference<androidx.mediarouter.media.MediaRouter>> r2 = r0.mRouters
                int r2 = r2.size()
                r4 = r3
                r5 = r4
            L_0x0028:
                int r2 = r2 + -1
                if (r2 < 0) goto L_0x00c2
                java.util.ArrayList<java.lang.ref.WeakReference<androidx.mediarouter.media.MediaRouter>> r6 = r0.mRouters
                java.lang.Object r6 = r6.get(r2)
                java.lang.ref.WeakReference r6 = (java.lang.ref.WeakReference) r6
                java.lang.Object r6 = r6.get()
                androidx.mediarouter.media.MediaRouter r6 = (androidx.mediarouter.media.MediaRouter) r6
                if (r6 != 0) goto L_0x0045
                java.util.ArrayList<java.lang.ref.WeakReference<androidx.mediarouter.media.MediaRouter>> r4 = r0.mRouters
                r4.remove(r2)
                r16 = r2
                goto L_0x00bd
            L_0x0045:
                java.util.ArrayList<androidx.mediarouter.media.MediaRouter$CallbackRecord> r7 = r6.mCallbackRecords
                int r7 = r7.size()
                int r3 = r3 + r7
                r8 = r5
                r5 = r4
            L_0x004e:
                if (r4 >= r7) goto L_0x00b8
                java.util.ArrayList<androidx.mediarouter.media.MediaRouter$CallbackRecord> r9 = r6.mCallbackRecords
                java.lang.Object r9 = r9.get(r4)
                androidx.mediarouter.media.MediaRouter$CallbackRecord r9 = (androidx.mediarouter.media.MediaRouter.CallbackRecord) r9
                androidx.mediarouter.media.MediaRouteSelector r10 = r9.mSelector
                if (r10 == 0) goto L_0x00af
                java.util.ArrayList r10 = r10.getControlCategories()
                r1.addControlCategories(r10)
                int r10 = r9.mFlags
                r11 = 1
                r10 = r10 & r11
                if (r10 == 0) goto L_0x006a
                r5 = r11
            L_0x006a:
                androidx.mediarouter.media.MediaRouterActiveScanThrottlingHelper r10 = r0.mActiveScanThrottlingHelper
                long r12 = r9.mTimestamp
                if (r5 != 0) goto L_0x0074
                java.util.Objects.requireNonNull(r10)
                goto L_0x007e
            L_0x0074:
                long r14 = r10.mCurrentTime
                long r16 = r14 - r12
                r18 = 30000(0x7530, double:1.4822E-319)
                int r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
                if (r16 < 0) goto L_0x0083
            L_0x007e:
                r16 = r2
                r17 = r3
                goto L_0x0094
            L_0x0083:
                r16 = r2
                r17 = r3
                long r2 = r10.mSuppressActiveScanTimeout
                long r12 = r12 + r18
                long r12 = r12 - r14
                long r2 = java.lang.Math.max(r2, r12)
                r10.mSuppressActiveScanTimeout = r2
                r10.mActiveScan = r11
            L_0x0094:
                if (r5 == 0) goto L_0x0097
                r8 = r11
            L_0x0097:
                int r2 = r9.mFlags
                r3 = r2 & 4
                if (r3 == 0) goto L_0x00a2
                boolean r3 = r0.mLowRam
                if (r3 != 0) goto L_0x00a2
                r8 = r11
            L_0x00a2:
                r2 = r2 & 8
                if (r2 == 0) goto L_0x00a7
                r8 = r11
            L_0x00a7:
                int r4 = r4 + 1
                r5 = 0
                r2 = r16
                r3 = r17
                goto L_0x004e
            L_0x00af:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.String r1 = "selector must not be null"
                r0.<init>(r1)
                throw r0
            L_0x00b8:
                r16 = r2
                r17 = r3
                r5 = r8
            L_0x00bd:
                r4 = 0
                r2 = r16
                goto L_0x0028
            L_0x00c2:
                androidx.mediarouter.media.MediaRouterActiveScanThrottlingHelper r2 = r0.mActiveScanThrottlingHelper
                java.util.Objects.requireNonNull(r2)
                boolean r4 = r2.mActiveScan
                if (r4 == 0) goto L_0x00da
                long r6 = r2.mSuppressActiveScanTimeout
                r8 = 0
                int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r4 <= 0) goto L_0x00da
                android.os.Handler r4 = r2.mHandler
                java.lang.Runnable r8 = r2.mUpdateDiscoveryRequestRunnable
                r4.postDelayed(r8, r6)
            L_0x00da:
                boolean r2 = r2.mActiveScan
                r0.mCallbackCount = r3
                if (r5 == 0) goto L_0x00e5
                androidx.mediarouter.media.MediaRouteSelector r3 = r1.build()
                goto L_0x00e7
            L_0x00e5:
                androidx.mediarouter.media.MediaRouteSelector r3 = androidx.mediarouter.media.MediaRouteSelector.EMPTY
            L_0x00e7:
                androidx.mediarouter.media.MediaRouteSelector r1 = r1.build()
                boolean r4 = r0.mTransferReceiverDeclared
                r6 = 0
                java.lang.String r7 = "MediaRouter"
                if (r4 != 0) goto L_0x00f3
                goto L_0x013f
            L_0x00f3:
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r4 = r0.mDiscoveryRequestForMr2Provider
                if (r4 == 0) goto L_0x010b
                r4.ensureSelector()
                androidx.mediarouter.media.MediaRouteSelector r4 = r4.mSelector
                boolean r4 = r4.equals(r1)
                if (r4 == 0) goto L_0x010b
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r4 = r0.mDiscoveryRequestForMr2Provider
                boolean r4 = r4.isActiveScan()
                if (r4 != r2) goto L_0x010b
                goto L_0x013f
            L_0x010b:
                boolean r4 = r1.isEmpty()
                if (r4 == 0) goto L_0x011b
                if (r2 != 0) goto L_0x011b
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r1 = r0.mDiscoveryRequestForMr2Provider
                if (r1 != 0) goto L_0x0118
                goto L_0x013f
            L_0x0118:
                r0.mDiscoveryRequestForMr2Provider = r6
                goto L_0x0122
            L_0x011b:
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r4 = new androidx.mediarouter.media.MediaRouteDiscoveryRequest
                r4.<init>(r1, r2)
                r0.mDiscoveryRequestForMr2Provider = r4
            L_0x0122:
                boolean r1 = androidx.mediarouter.media.MediaRouter.DEBUG
                if (r1 == 0) goto L_0x0138
                java.lang.String r1 = "Updated MediaRoute2Provider's discovery request: "
                java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r4 = r0.mDiscoveryRequestForMr2Provider
                r1.append(r4)
                java.lang.String r1 = r1.toString()
                android.util.Log.d(r7, r1)
            L_0x0138:
                androidx.mediarouter.media.MediaRoute2Provider r1 = r0.mMr2Provider
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r4 = r0.mDiscoveryRequestForMr2Provider
                r1.setDiscoveryRequest(r4)
            L_0x013f:
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r1 = r0.mDiscoveryRequest
                if (r1 == 0) goto L_0x0157
                r1.ensureSelector()
                androidx.mediarouter.media.MediaRouteSelector r1 = r1.mSelector
                boolean r1 = r1.equals(r3)
                if (r1 == 0) goto L_0x0157
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r1 = r0.mDiscoveryRequest
                boolean r1 = r1.isActiveScan()
                if (r1 != r2) goto L_0x0157
                return
            L_0x0157:
                boolean r1 = r3.isEmpty()
                if (r1 == 0) goto L_0x0167
                if (r2 != 0) goto L_0x0167
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r1 = r0.mDiscoveryRequest
                if (r1 != 0) goto L_0x0164
                return
            L_0x0164:
                r0.mDiscoveryRequest = r6
                goto L_0x016e
            L_0x0167:
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r1 = new androidx.mediarouter.media.MediaRouteDiscoveryRequest
                r1.<init>(r3, r2)
                r0.mDiscoveryRequest = r1
            L_0x016e:
                boolean r1 = androidx.mediarouter.media.MediaRouter.DEBUG
                if (r1 == 0) goto L_0x0184
                java.lang.String r1 = "Updated discovery request: "
                java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r3 = r0.mDiscoveryRequest
                r1.append(r3)
                java.lang.String r1 = r1.toString()
                android.util.Log.d(r7, r1)
            L_0x0184:
                if (r5 == 0) goto L_0x0191
                if (r2 != 0) goto L_0x0191
                boolean r1 = r0.mLowRam
                if (r1 == 0) goto L_0x0191
                java.lang.String r1 = "Forcing passive route discovery on a low-RAM device, system performance may be affected.  Please consider using CALLBACK_FLAG_REQUEST_DISCOVERY instead of CALLBACK_FLAG_FORCE_DISCOVERY."
                android.util.Log.i(r7, r1)
            L_0x0191:
                java.util.ArrayList<androidx.mediarouter.media.MediaRouter$ProviderInfo> r1 = r0.mProviders
                int r1 = r1.size()
                r2 = 0
            L_0x0198:
                if (r2 >= r1) goto L_0x01b1
                java.util.ArrayList<androidx.mediarouter.media.MediaRouter$ProviderInfo> r3 = r0.mProviders
                java.lang.Object r3 = r3.get(r2)
                androidx.mediarouter.media.MediaRouter$ProviderInfo r3 = (androidx.mediarouter.media.MediaRouter.ProviderInfo) r3
                androidx.mediarouter.media.MediaRouteProvider r3 = r3.mProviderInstance
                androidx.mediarouter.media.MediaRoute2Provider r4 = r0.mMr2Provider
                if (r3 != r4) goto L_0x01a9
                goto L_0x01ae
            L_0x01a9:
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r4 = r0.mDiscoveryRequest
                r3.setDiscoveryRequest(r4)
            L_0x01ae:
                int r2 = r2 + 1
                goto L_0x0198
            L_0x01b1:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.MediaRouter.GlobalMediaRouter.updateDiscoveryRequest():void");
        }

        @SuppressLint({"NewApi"})
        public final void updatePlaybackInfoFromSelectedRoute() {
            MediaRouter2.RoutingController routingController;
            if (this.mSelectedRoute != null) {
                Objects.requireNonNull(this.mPlaybackInfo);
                RemoteControlClientCompat$PlaybackInfo remoteControlClientCompat$PlaybackInfo = this.mPlaybackInfo;
                Objects.requireNonNull(this.mSelectedRoute);
                Objects.requireNonNull(remoteControlClientCompat$PlaybackInfo);
                RemoteControlClientCompat$PlaybackInfo remoteControlClientCompat$PlaybackInfo2 = this.mPlaybackInfo;
                this.mSelectedRoute.getVolumeHandling();
                Objects.requireNonNull(remoteControlClientCompat$PlaybackInfo2);
                RemoteControlClientCompat$PlaybackInfo remoteControlClientCompat$PlaybackInfo3 = this.mPlaybackInfo;
                Objects.requireNonNull(this.mSelectedRoute);
                Objects.requireNonNull(remoteControlClientCompat$PlaybackInfo3);
                RemoteControlClientCompat$PlaybackInfo remoteControlClientCompat$PlaybackInfo4 = this.mPlaybackInfo;
                Objects.requireNonNull(this.mSelectedRoute);
                Objects.requireNonNull(remoteControlClientCompat$PlaybackInfo4);
                if (!this.mTransferReceiverDeclared || this.mSelectedRoute.getProviderInstance() != this.mMr2Provider) {
                    Objects.requireNonNull(this.mPlaybackInfo);
                } else {
                    RemoteControlClientCompat$PlaybackInfo remoteControlClientCompat$PlaybackInfo5 = this.mPlaybackInfo;
                    MediaRouteProvider.RouteController routeController = this.mSelectedRouteController;
                    int i = MediaRoute2Provider.$r8$clinit;
                    if ((routeController instanceof MediaRoute2Provider.GroupRouteController) && (routingController = ((MediaRoute2Provider.GroupRouteController) routeController).mRoutingController) != null) {
                        routingController.getId();
                    }
                    Objects.requireNonNull(remoteControlClientCompat$PlaybackInfo5);
                }
                if (this.mRemoteControlClients.size() > 0) {
                    Objects.requireNonNull(this.mRemoteControlClients.get(0));
                    throw null;
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0024, code lost:
            if (r2 == r6.mDescriptor) goto L_0x0026;
         */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x0196 A[LOOP:4: B:62:0x0194->B:63:0x0196, LOOP_END] */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x01b6  */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x01e4  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void updateProviderContents(androidx.mediarouter.media.MediaRouter.ProviderInfo r17, androidx.mediarouter.media.MediaRouteProviderDescriptor r18) {
            /*
                r16 = this;
                r0 = r16
                r1 = r17
                r2 = r18
                androidx.mediarouter.media.MediaRouteProviderDescriptor r3 = r1.mDescriptor
                r4 = 1
                if (r3 == r2) goto L_0x000f
                r1.mDescriptor = r2
                r3 = r4
                goto L_0x0010
            L_0x000f:
                r3 = 0
            L_0x0010:
                if (r3 != 0) goto L_0x0013
                return
            L_0x0013:
                java.lang.String r3 = "MediaRouter"
                if (r2 == 0) goto L_0x0177
                boolean r6 = r18.isValid()
                if (r6 != 0) goto L_0x0026
                androidx.mediarouter.media.SystemMediaRouteProvider$Api24Impl r6 = r0.mSystemProvider
                java.util.Objects.requireNonNull(r6)
                androidx.mediarouter.media.MediaRouteProviderDescriptor r6 = r6.mDescriptor
                if (r2 != r6) goto L_0x0177
            L_0x0026:
                java.util.List<androidx.mediarouter.media.MediaRouteDescriptor> r2 = r2.mRoutes
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                java.util.ArrayList r7 = new java.util.ArrayList
                r7.<init>()
                java.util.Iterator r2 = r2.iterator()
                r8 = 0
                r9 = 0
            L_0x0038:
                boolean r10 = r2.hasNext()
                java.lang.String r11 = "Route added: "
                r12 = 257(0x101, float:3.6E-43)
                if (r10 == 0) goto L_0x011b
                java.lang.Object r10 = r2.next()
                androidx.mediarouter.media.MediaRouteDescriptor r10 = (androidx.mediarouter.media.MediaRouteDescriptor) r10
                if (r10 == 0) goto L_0x0105
                boolean r13 = r10.isValid()
                if (r13 != 0) goto L_0x0052
                goto L_0x0105
            L_0x0052:
                java.lang.String r13 = r10.getId()
                java.util.ArrayList r14 = r1.mRoutes
                int r14 = r14.size()
                r15 = 0
            L_0x005d:
                if (r15 >= r14) goto L_0x0073
                java.util.ArrayList r5 = r1.mRoutes
                java.lang.Object r5 = r5.get(r15)
                androidx.mediarouter.media.MediaRouter$RouteInfo r5 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r5
                java.lang.String r5 = r5.mDescriptorId
                boolean r5 = r5.equals(r13)
                if (r5 == 0) goto L_0x0070
                goto L_0x0074
            L_0x0070:
                int r15 = r15 + 1
                goto L_0x005d
            L_0x0073:
                r15 = -1
            L_0x0074:
                if (r15 >= 0) goto L_0x00bf
                java.lang.String r5 = r0.assignRouteUniqueId(r1, r13)
                androidx.mediarouter.media.MediaRouter$RouteInfo r14 = new androidx.mediarouter.media.MediaRouter$RouteInfo
                r14.<init>(r1, r13, r5)
                java.util.ArrayList r5 = r1.mRoutes
                int r13 = r9 + 1
                r5.add(r9, r14)
                java.util.ArrayList<androidx.mediarouter.media.MediaRouter$RouteInfo> r5 = r0.mRoutes
                r5.add(r14)
                java.util.List r5 = r10.getGroupMemberIds()
                int r5 = r5.size()
                if (r5 <= 0) goto L_0x009e
                androidx.core.util.Pair r5 = new androidx.core.util.Pair
                r5.<init>(r14, r10)
                r6.add(r5)
                goto L_0x00bc
            L_0x009e:
                r14.maybeUpdateDescriptor(r10)
                boolean r5 = androidx.mediarouter.media.MediaRouter.DEBUG
                if (r5 == 0) goto L_0x00b7
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                r5.append(r11)
                r5.append(r14)
                java.lang.String r5 = r5.toString()
                android.util.Log.d(r3, r5)
            L_0x00b7:
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter$CallbackHandler r5 = r0.mCallbackHandler
                r5.post(r12, r14)
            L_0x00bc:
                r9 = r13
                goto L_0x0038
            L_0x00bf:
                if (r15 >= r9) goto L_0x00d7
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r11 = "Ignoring route descriptor with duplicate id: "
                r5.append(r11)
                r5.append(r10)
                java.lang.String r5 = r5.toString()
                android.util.Log.w(r3, r5)
                goto L_0x0038
            L_0x00d7:
                java.util.ArrayList r5 = r1.mRoutes
                java.lang.Object r5 = r5.get(r15)
                androidx.mediarouter.media.MediaRouter$RouteInfo r5 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r5
                java.util.ArrayList r11 = r1.mRoutes
                int r13 = r9 + 1
                java.util.Collections.swap(r11, r15, r9)
                java.util.List r9 = r10.getGroupMemberIds()
                int r9 = r9.size()
                if (r9 <= 0) goto L_0x00f9
                androidx.core.util.Pair r9 = new androidx.core.util.Pair
                r9.<init>(r5, r10)
                r7.add(r9)
                goto L_0x00bc
            L_0x00f9:
                int r9 = r0.updateRouteDescriptorAndNotify(r5, r10)
                if (r9 == 0) goto L_0x00bc
                androidx.mediarouter.media.MediaRouter$RouteInfo r9 = r0.mSelectedRoute
                if (r5 != r9) goto L_0x00bc
                r8 = r4
                goto L_0x00bc
            L_0x0105:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r11 = "Ignoring invalid system route descriptor: "
                r5.append(r11)
                r5.append(r10)
                java.lang.String r5 = r5.toString()
                android.util.Log.w(r3, r5)
                goto L_0x0038
            L_0x011b:
                java.util.Iterator r2 = r6.iterator()
            L_0x011f:
                boolean r5 = r2.hasNext()
                if (r5 == 0) goto L_0x0152
                java.lang.Object r5 = r2.next()
                androidx.core.util.Pair r5 = (androidx.core.util.Pair) r5
                F r6 = r5.first
                androidx.mediarouter.media.MediaRouter$RouteInfo r6 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r6
                S r5 = r5.second
                androidx.mediarouter.media.MediaRouteDescriptor r5 = (androidx.mediarouter.media.MediaRouteDescriptor) r5
                r6.maybeUpdateDescriptor(r5)
                boolean r5 = androidx.mediarouter.media.MediaRouter.DEBUG
                if (r5 == 0) goto L_0x014c
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                r5.append(r11)
                r5.append(r6)
                java.lang.String r5 = r5.toString()
                android.util.Log.d(r3, r5)
            L_0x014c:
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter$CallbackHandler r5 = r0.mCallbackHandler
                r5.post(r12, r6)
                goto L_0x011f
            L_0x0152:
                java.util.Iterator r2 = r7.iterator()
                r5 = r8
            L_0x0157:
                boolean r6 = r2.hasNext()
                if (r6 == 0) goto L_0x018d
                java.lang.Object r6 = r2.next()
                androidx.core.util.Pair r6 = (androidx.core.util.Pair) r6
                F r7 = r6.first
                androidx.mediarouter.media.MediaRouter$RouteInfo r7 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r7
                S r6 = r6.second
                androidx.mediarouter.media.MediaRouteDescriptor r6 = (androidx.mediarouter.media.MediaRouteDescriptor) r6
                int r6 = r0.updateRouteDescriptorAndNotify(r7, r6)
                if (r6 == 0) goto L_0x0157
                androidx.mediarouter.media.MediaRouter$RouteInfo r6 = r0.mSelectedRoute
                if (r7 != r6) goto L_0x0157
                r5 = r4
                goto L_0x0157
            L_0x0177:
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Ignoring invalid provider descriptor: "
                r5.append(r6)
                r5.append(r2)
                java.lang.String r2 = r5.toString()
                android.util.Log.w(r3, r2)
                r5 = 0
                r9 = 0
            L_0x018d:
                java.util.ArrayList r2 = r1.mRoutes
                int r2 = r2.size()
                int r2 = r2 - r4
            L_0x0194:
                if (r2 < r9) goto L_0x01aa
                java.util.ArrayList r6 = r1.mRoutes
                java.lang.Object r6 = r6.get(r2)
                androidx.mediarouter.media.MediaRouter$RouteInfo r6 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r6
                r7 = 0
                r6.maybeUpdateDescriptor(r7)
                java.util.ArrayList<androidx.mediarouter.media.MediaRouter$RouteInfo> r7 = r0.mRoutes
                r7.remove(r6)
                int r2 = r2 + -1
                goto L_0x0194
            L_0x01aa:
                r0.updateSelectedRouteIfNeeded(r5)
                java.util.ArrayList r2 = r1.mRoutes
                int r2 = r2.size()
                int r2 = r2 - r4
            L_0x01b4:
                if (r2 < r9) goto L_0x01e0
                java.util.ArrayList r4 = r1.mRoutes
                java.lang.Object r4 = r4.remove(r2)
                androidx.mediarouter.media.MediaRouter$RouteInfo r4 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r4
                boolean r5 = androidx.mediarouter.media.MediaRouter.DEBUG
                if (r5 == 0) goto L_0x01d6
                java.lang.StringBuilder r5 = new java.lang.StringBuilder
                r5.<init>()
                java.lang.String r6 = "Route removed: "
                r5.append(r6)
                r5.append(r4)
                java.lang.String r5 = r5.toString()
                android.util.Log.d(r3, r5)
            L_0x01d6:
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter$CallbackHandler r5 = r0.mCallbackHandler
                r6 = 258(0x102, float:3.62E-43)
                r5.post(r6, r4)
                int r2 = r2 + -1
                goto L_0x01b4
            L_0x01e0:
                boolean r2 = androidx.mediarouter.media.MediaRouter.DEBUG
                if (r2 == 0) goto L_0x01f8
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                r2.<init>()
                java.lang.String r4 = "Provider changed: "
                r2.append(r4)
                r2.append(r1)
                java.lang.String r2 = r2.toString()
                android.util.Log.d(r3, r2)
            L_0x01f8:
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter$CallbackHandler r0 = r0.mCallbackHandler
                r2 = 515(0x203, float:7.22E-43)
                r0.post(r2, r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.MediaRouter.GlobalMediaRouter.updateProviderContents(androidx.mediarouter.media.MediaRouter$ProviderInfo, androidx.mediarouter.media.MediaRouteProviderDescriptor):void");
        }

        public final void updateSelectedRouteIfNeeded(boolean z) {
            boolean z2;
            boolean z3;
            RouteInfo routeInfo = this.mDefaultRoute;
            if (routeInfo != null && !routeInfo.isSelectable()) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Clearing the default route because it is no longer selectable: ");
                m.append(this.mDefaultRoute);
                Log.i("MediaRouter", m.toString());
                this.mDefaultRoute = null;
            }
            if (this.mDefaultRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it = this.mRoutes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RouteInfo next = it.next();
                    if (next.getProviderInstance() != this.mSystemProvider || !next.mDescriptorId.equals("DEFAULT_ROUTE")) {
                        z3 = false;
                    } else {
                        z3 = true;
                    }
                    if (z3 && next.isSelectable()) {
                        this.mDefaultRoute = next;
                        StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Found default route: ");
                        m2.append(this.mDefaultRoute);
                        Log.i("MediaRouter", m2.toString());
                        break;
                    }
                }
            }
            RouteInfo routeInfo2 = this.mBluetoothRoute;
            if (routeInfo2 != null && !routeInfo2.isSelectable()) {
                StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Clearing the bluetooth route because it is no longer selectable: ");
                m3.append(this.mBluetoothRoute);
                Log.i("MediaRouter", m3.toString());
                this.mBluetoothRoute = null;
            }
            if (this.mBluetoothRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it2 = this.mRoutes.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    RouteInfo next2 = it2.next();
                    if (next2.getProviderInstance() != this.mSystemProvider || !next2.supportsControlCategory("android.media.intent.category.LIVE_AUDIO") || next2.supportsControlCategory("android.media.intent.category.LIVE_VIDEO")) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z2 && next2.isSelectable()) {
                        this.mBluetoothRoute = next2;
                        StringBuilder m4 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Found bluetooth route: ");
                        m4.append(this.mBluetoothRoute);
                        Log.i("MediaRouter", m4.toString());
                        break;
                    }
                }
            }
            RouteInfo routeInfo3 = this.mSelectedRoute;
            if (routeInfo3 == null || !routeInfo3.mEnabled) {
                StringBuilder m5 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Unselecting the current route because it is no longer selectable: ");
                m5.append(this.mSelectedRoute);
                Log.i("MediaRouter", m5.toString());
                selectRouteInternal(chooseFallbackRoute(), 0);
            } else if (z) {
                maybeUpdateMemberRouteControllers();
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        public GlobalMediaRouter(Context context) {
            this.mApplicationContext = context;
            this.mLowRam = ((ActivityManager) context.getSystemService("activity")).isLowRamDevice();
        }

        public final void addProvider(MediaRouteProvider mediaRouteProvider) {
            if (findProviderInfo(mediaRouteProvider) == null) {
                ProviderInfo providerInfo = new ProviderInfo(mediaRouteProvider);
                this.mProviders.add(providerInfo);
                if (MediaRouter.DEBUG) {
                    Log.d("MediaRouter", "Provider added: " + providerInfo);
                }
                this.mCallbackHandler.post(513, providerInfo);
                updateProviderContents(providerInfo, mediaRouteProvider.mDescriptor);
                ProviderCallback providerCallback = this.mProviderCallback;
                MediaRouter.checkCallingThread();
                mediaRouteProvider.mCallback = providerCallback;
                mediaRouteProvider.setDiscoveryRequest(this.mDiscoveryRequest);
            }
        }

        public final String assignRouteUniqueId(ProviderInfo providerInfo, String str) {
            Objects.requireNonNull(providerInfo);
            MediaRouteProvider.ProviderMetadata providerMetadata = providerInfo.mMetadata;
            Objects.requireNonNull(providerMetadata);
            String flattenToShortString = providerMetadata.mComponentName.flattenToShortString();
            String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m6m(flattenToShortString, ":", str);
            if (findRouteByUniqueId(m) < 0) {
                this.mUniqueIdMap.put(new Pair(flattenToShortString, str), m);
                return m;
            }
            Log.w("MediaRouter", "Either " + str + " isn't unique in " + flattenToShortString + " or we're trying to assign a unique ID for an already added route");
            int i = 2;
            while (true) {
                String format = String.format(Locale.US, "%s_%d", new Object[]{m, Integer.valueOf(i)});
                if (findRouteByUniqueId(format) < 0) {
                    this.mUniqueIdMap.put(new Pair(flattenToShortString, str), format);
                    return format;
                }
                i++;
            }
        }

        public final int updateRouteDescriptorAndNotify(RouteInfo routeInfo, MediaRouteDescriptor mediaRouteDescriptor) {
            int maybeUpdateDescriptor = routeInfo.maybeUpdateDescriptor(mediaRouteDescriptor);
            if (maybeUpdateDescriptor != 0) {
                if ((maybeUpdateDescriptor & 1) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d("MediaRouter", "Route changed: " + routeInfo);
                    }
                    this.mCallbackHandler.post(259, routeInfo);
                }
                if ((maybeUpdateDescriptor & 2) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d("MediaRouter", "Route volume changed: " + routeInfo);
                    }
                    this.mCallbackHandler.post(260, routeInfo);
                }
                if ((maybeUpdateDescriptor & 4) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d("MediaRouter", "Route presentation display changed: " + routeInfo);
                    }
                    this.mCallbackHandler.post(261, routeInfo);
                }
            }
            return maybeUpdateDescriptor;
        }
    }

    public static final class ProviderInfo {
        public MediaRouteProviderDescriptor mDescriptor;
        public final MediaRouteProvider.ProviderMetadata mMetadata;
        public final MediaRouteProvider mProviderInstance;
        public final ArrayList mRoutes = new ArrayList();

        public final RouteInfo findRouteByDescriptorId(String str) {
            int size = this.mRoutes.size();
            for (int i = 0; i < size; i++) {
                if (((RouteInfo) this.mRoutes.get(i)).mDescriptorId.equals(str)) {
                    return (RouteInfo) this.mRoutes.get(i);
                }
            }
            return null;
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("MediaRouter.RouteProviderInfo{ packageName=");
            MediaRouteProvider.ProviderMetadata providerMetadata = this.mMetadata;
            Objects.requireNonNull(providerMetadata);
            m.append(providerMetadata.mComponentName.getPackageName());
            m.append(" }");
            return m.toString();
        }

        public ProviderInfo(MediaRouteProvider mediaRouteProvider) {
            this.mProviderInstance = mediaRouteProvider;
            Objects.requireNonNull(mediaRouteProvider);
            this.mMetadata = mediaRouteProvider.mMetadata;
        }
    }

    public static class RouteInfo {
        public boolean mCanDisconnect;
        public int mConnectionState;
        public final ArrayList<IntentFilter> mControlFilters = new ArrayList<>();
        public String mDescription;
        public MediaRouteDescriptor mDescriptor;
        public final String mDescriptorId;
        public int mDeviceType;
        public ArrayMap mDynamicGroupDescriptors;
        public boolean mEnabled;
        public Bundle mExtras;
        public Uri mIconUri;
        public ArrayList mMemberRoutes = new ArrayList();
        public String mName;
        public int mPlaybackStream;
        public int mPlaybackType;
        public int mPresentationDisplayId = -1;
        public final ProviderInfo mProvider;
        public IntentSender mSettingsIntent;
        public final String mUniqueId;
        public int mVolume;
        public int mVolumeHandling;
        public int mVolumeMax;

        public static final class DynamicGroupState {
            public final MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor mDynamicDescriptor;

            public final boolean isGroupable() {
                MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor = this.mDynamicDescriptor;
                if (dynamicRouteDescriptor != null) {
                    Objects.requireNonNull(dynamicRouteDescriptor);
                    if (dynamicRouteDescriptor.mIsGroupable) {
                        return true;
                    }
                }
                return false;
            }

            public DynamicGroupState(MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicRouteDescriptor) {
                this.mDynamicDescriptor = dynamicRouteDescriptor;
            }
        }

        public final List<RouteInfo> getMemberRoutes() {
            return Collections.unmodifiableList(this.mMemberRoutes);
        }

        public final MediaRouteProvider getProviderInstance() {
            ProviderInfo providerInfo = this.mProvider;
            Objects.requireNonNull(providerInfo);
            MediaRouter.checkCallingThread();
            return providerInfo.mProviderInstance;
        }

        public final boolean isSelectable() {
            if (this.mDescriptor == null || !this.mEnabled) {
                return false;
            }
            return true;
        }

        public final boolean matchesSelector(MediaRouteSelector mediaRouteSelector) {
            if (mediaRouteSelector != null) {
                MediaRouter.checkCallingThread();
                ArrayList<IntentFilter> arrayList = this.mControlFilters;
                if (arrayList == null) {
                    return false;
                }
                mediaRouteSelector.ensureControlCategories();
                if (mediaRouteSelector.mControlCategories.isEmpty()) {
                    return false;
                }
                Iterator<IntentFilter> it = arrayList.iterator();
                while (it.hasNext()) {
                    IntentFilter next = it.next();
                    if (next != null) {
                        for (String hasCategory : mediaRouteSelector.mControlCategories) {
                            if (next.hasCategory(hasCategory)) {
                                return true;
                            }
                        }
                        continue;
                    }
                }
                return false;
            }
            throw new IllegalArgumentException("selector must not be null");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0103, code lost:
            if (r3.hasNext() != false) goto L_0x010d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:53:0x0109, code lost:
            if (r4.hasNext() != false) goto L_0x010d;
         */
        /* JADX WARNING: Removed duplicated region for block: B:109:0x028d  */
        /* JADX WARNING: Removed duplicated region for block: B:110:0x0292  */
        /* JADX WARNING: Removed duplicated region for block: B:113:0x010d A[EDGE_INSN: B:113:0x010d->B:55:0x010d ?: BREAK  , SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x0110  */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x012d  */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x0144  */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x015a  */
        /* JADX WARNING: Removed duplicated region for block: B:69:0x0171  */
        /* JADX WARNING: Removed duplicated region for block: B:72:0x0188  */
        /* JADX WARNING: Removed duplicated region for block: B:75:0x019f  */
        /* JADX WARNING: Removed duplicated region for block: B:78:0x01b5  */
        /* JADX WARNING: Removed duplicated region for block: B:81:0x01cf  */
        /* JADX WARNING: Removed duplicated region for block: B:84:0x01ec  */
        /* JADX WARNING: Removed duplicated region for block: B:87:0x0204  */
        /* JADX WARNING: Removed duplicated region for block: B:90:0x0223  */
        /* JADX WARNING: Removed duplicated region for block: B:93:0x022a  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int maybeUpdateDescriptor(androidx.mediarouter.media.MediaRouteDescriptor r12) {
            /*
                r11 = this;
                androidx.mediarouter.media.MediaRouteDescriptor r0 = r11.mDescriptor
                r1 = 0
                if (r0 == r12) goto L_0x0293
                r11.mDescriptor = r12
                if (r12 == 0) goto L_0x0293
                java.lang.String r0 = r11.mName
                android.os.Bundle r2 = r12.mBundle
                java.lang.String r3 = "name"
                java.lang.String r2 = r2.getString(r3)
                boolean r0 = java.util.Objects.equals(r0, r2)
                r2 = 1
                if (r0 != 0) goto L_0x0024
                android.os.Bundle r0 = r12.mBundle
                java.lang.String r0 = r0.getString(r3)
                r11.mName = r0
                r0 = r2
                goto L_0x0025
            L_0x0024:
                r0 = r1
            L_0x0025:
                java.lang.String r3 = r11.mDescription
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "status"
                java.lang.String r4 = r4.getString(r5)
                boolean r3 = java.util.Objects.equals(r3, r4)
                if (r3 != 0) goto L_0x0040
                android.os.Bundle r3 = r12.mBundle
                java.lang.String r3 = r3.getString(r5)
                r11.mDescription = r3
                r0 = r0 | 1
            L_0x0040:
                android.net.Uri r3 = r11.mIconUri
                android.net.Uri r4 = r12.getIconUri()
                boolean r3 = java.util.Objects.equals(r3, r4)
                if (r3 != 0) goto L_0x0054
                android.net.Uri r3 = r12.getIconUri()
                r11.mIconUri = r3
                r0 = r0 | 1
            L_0x0054:
                boolean r3 = r11.mEnabled
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "enabled"
                boolean r4 = r4.getBoolean(r5, r2)
                if (r3 == r4) goto L_0x006a
                android.os.Bundle r3 = r12.mBundle
                boolean r3 = r3.getBoolean(r5, r2)
                r11.mEnabled = r3
                r0 = r0 | 1
            L_0x006a:
                int r3 = r11.mConnectionState
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "connectionState"
                int r4 = r4.getInt(r5, r1)
                if (r3 == r4) goto L_0x0080
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5, r1)
                r11.mConnectionState = r3
                r0 = r0 | 1
            L_0x0080:
                java.util.ArrayList<android.content.IntentFilter> r3 = r11.mControlFilters
                r12.ensureControlFilters()
                java.util.List<android.content.IntentFilter> r4 = r12.mControlFilters
                if (r3 != r4) goto L_0x008b
                goto L_0x010b
            L_0x008b:
                if (r3 == 0) goto L_0x010d
                if (r4 != 0) goto L_0x0091
                goto L_0x010d
            L_0x0091:
                java.util.ListIterator r3 = r3.listIterator()
                java.util.ListIterator r4 = r4.listIterator()
            L_0x0099:
                boolean r5 = r3.hasNext()
                if (r5 == 0) goto L_0x00ff
                boolean r5 = r4.hasNext()
                if (r5 == 0) goto L_0x00ff
                java.lang.Object r5 = r3.next()
                android.content.IntentFilter r5 = (android.content.IntentFilter) r5
                java.lang.Object r6 = r4.next()
                android.content.IntentFilter r6 = (android.content.IntentFilter) r6
                if (r5 != r6) goto L_0x00b4
                goto L_0x00f9
            L_0x00b4:
                if (r5 == 0) goto L_0x00fb
                if (r6 != 0) goto L_0x00b9
                goto L_0x00fb
            L_0x00b9:
                int r7 = r5.countActions()
                int r8 = r6.countActions()
                if (r7 == r8) goto L_0x00c4
                goto L_0x00fb
            L_0x00c4:
                r8 = r1
            L_0x00c5:
                if (r8 >= r7) goto L_0x00d9
                java.lang.String r9 = r5.getAction(r8)
                java.lang.String r10 = r6.getAction(r8)
                boolean r9 = r9.equals(r10)
                if (r9 != 0) goto L_0x00d6
                goto L_0x00fb
            L_0x00d6:
                int r8 = r8 + 1
                goto L_0x00c5
            L_0x00d9:
                int r7 = r5.countCategories()
                int r8 = r6.countCategories()
                if (r7 == r8) goto L_0x00e4
                goto L_0x00fb
            L_0x00e4:
                r8 = r1
            L_0x00e5:
                if (r8 >= r7) goto L_0x00f9
                java.lang.String r9 = r5.getCategory(r8)
                java.lang.String r10 = r6.getCategory(r8)
                boolean r9 = r9.equals(r10)
                if (r9 != 0) goto L_0x00f6
                goto L_0x00fb
            L_0x00f6:
                int r8 = r8 + 1
                goto L_0x00e5
            L_0x00f9:
                r5 = r2
                goto L_0x00fc
            L_0x00fb:
                r5 = r1
            L_0x00fc:
                if (r5 != 0) goto L_0x0099
                goto L_0x010d
            L_0x00ff:
                boolean r3 = r3.hasNext()
                if (r3 != 0) goto L_0x010d
                boolean r3 = r4.hasNext()
                if (r3 != 0) goto L_0x010d
            L_0x010b:
                r3 = r2
                goto L_0x010e
            L_0x010d:
                r3 = r1
            L_0x010e:
                if (r3 != 0) goto L_0x0121
                java.util.ArrayList<android.content.IntentFilter> r3 = r11.mControlFilters
                r3.clear()
                java.util.ArrayList<android.content.IntentFilter> r3 = r11.mControlFilters
                r12.ensureControlFilters()
                java.util.List<android.content.IntentFilter> r4 = r12.mControlFilters
                r3.addAll(r4)
                r0 = r0 | 1
            L_0x0121:
                int r3 = r11.mPlaybackType
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "playbackType"
                int r4 = r4.getInt(r5, r2)
                if (r3 == r4) goto L_0x0137
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5, r2)
                r11.mPlaybackType = r3
                r0 = r0 | 1
            L_0x0137:
                int r3 = r11.mPlaybackStream
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "playbackStream"
                r6 = -1
                int r4 = r4.getInt(r5, r6)
                if (r3 == r4) goto L_0x014e
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5, r6)
                r11.mPlaybackStream = r3
                r0 = r0 | 1
            L_0x014e:
                int r3 = r11.mDeviceType
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "deviceType"
                int r4 = r4.getInt(r5)
                if (r3 == r4) goto L_0x0164
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5)
                r11.mDeviceType = r3
                r0 = r0 | 1
            L_0x0164:
                int r3 = r11.mVolumeHandling
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "volumeHandling"
                int r4 = r4.getInt(r5, r1)
                if (r3 == r4) goto L_0x017b
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5, r1)
                r11.mVolumeHandling = r3
                r0 = r0 | 3
            L_0x017b:
                int r3 = r11.mVolume
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "volume"
                int r4 = r4.getInt(r5)
                if (r3 == r4) goto L_0x0192
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5)
                r11.mVolume = r3
                r0 = r0 | 3
            L_0x0192:
                int r3 = r11.mVolumeMax
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "volumeMax"
                int r4 = r4.getInt(r5)
                if (r3 == r4) goto L_0x01a9
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5)
                r11.mVolumeMax = r3
                r0 = r0 | 3
            L_0x01a9:
                int r3 = r11.mPresentationDisplayId
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "presentationDisplayId"
                int r4 = r4.getInt(r5, r6)
                if (r3 == r4) goto L_0x01bf
                android.os.Bundle r3 = r12.mBundle
                int r3 = r3.getInt(r5, r6)
                r11.mPresentationDisplayId = r3
                r0 = r0 | 5
            L_0x01bf:
                android.os.Bundle r3 = r11.mExtras
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "extras"
                android.os.Bundle r4 = r4.getBundle(r5)
                boolean r3 = java.util.Objects.equals(r3, r4)
                if (r3 != 0) goto L_0x01d9
                android.os.Bundle r3 = r12.mBundle
                android.os.Bundle r3 = r3.getBundle(r5)
                r11.mExtras = r3
                r0 = r0 | 1
            L_0x01d9:
                android.content.IntentSender r3 = r11.mSettingsIntent
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "settingsIntent"
                android.os.Parcelable r4 = r4.getParcelable(r5)
                android.content.IntentSender r4 = (android.content.IntentSender) r4
                boolean r3 = java.util.Objects.equals(r3, r4)
                if (r3 != 0) goto L_0x01f8
                android.os.Bundle r3 = r12.mBundle
                android.os.Parcelable r3 = r3.getParcelable(r5)
                android.content.IntentSender r3 = (android.content.IntentSender) r3
                r11.mSettingsIntent = r3
                r0 = r0 | 1
            L_0x01f8:
                boolean r3 = r11.mCanDisconnect
                android.os.Bundle r4 = r12.mBundle
                java.lang.String r5 = "canDisconnect"
                boolean r4 = r4.getBoolean(r5, r1)
                if (r3 == r4) goto L_0x020e
                android.os.Bundle r3 = r12.mBundle
                boolean r3 = r3.getBoolean(r5, r1)
                r11.mCanDisconnect = r3
                r0 = r0 | 5
            L_0x020e:
                java.util.List r12 = r12.getGroupMemberIds()
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                int r4 = r12.size()
                java.util.ArrayList r5 = r11.mMemberRoutes
                int r5 = r5.size()
                if (r4 == r5) goto L_0x0224
                r1 = r2
            L_0x0224:
                boolean r4 = r12.isEmpty()
                if (r4 != 0) goto L_0x028b
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r4 = androidx.mediarouter.media.MediaRouter.getGlobalRouter()
                java.util.Iterator r12 = r12.iterator()
            L_0x0232:
                boolean r5 = r12.hasNext()
                if (r5 == 0) goto L_0x028b
                java.lang.Object r5 = r12.next()
                java.lang.String r5 = (java.lang.String) r5
                androidx.mediarouter.media.MediaRouter$ProviderInfo r6 = r11.mProvider
                java.util.Objects.requireNonNull(r4)
                java.util.Objects.requireNonNull(r6)
                androidx.mediarouter.media.MediaRouteProvider$ProviderMetadata r6 = r6.mMetadata
                java.util.Objects.requireNonNull(r6)
                android.content.ComponentName r6 = r6.mComponentName
                java.lang.String r6 = r6.flattenToShortString()
                java.util.HashMap r7 = r4.mUniqueIdMap
                androidx.core.util.Pair r8 = new androidx.core.util.Pair
                r8.<init>(r6, r5)
                java.lang.Object r5 = r7.get(r8)
                java.lang.String r5 = (java.lang.String) r5
                java.util.ArrayList<androidx.mediarouter.media.MediaRouter$RouteInfo> r6 = r4.mRoutes
                java.util.Iterator r6 = r6.iterator()
            L_0x0264:
                boolean r7 = r6.hasNext()
                if (r7 == 0) goto L_0x0279
                java.lang.Object r7 = r6.next()
                androidx.mediarouter.media.MediaRouter$RouteInfo r7 = (androidx.mediarouter.media.MediaRouter.RouteInfo) r7
                java.lang.String r8 = r7.mUniqueId
                boolean r8 = r8.equals(r5)
                if (r8 == 0) goto L_0x0264
                goto L_0x027a
            L_0x0279:
                r7 = 0
            L_0x027a:
                if (r7 == 0) goto L_0x0232
                r3.add(r7)
                if (r1 != 0) goto L_0x0232
                java.util.ArrayList r5 = r11.mMemberRoutes
                boolean r5 = r5.contains(r7)
                if (r5 != 0) goto L_0x0232
                r1 = r2
                goto L_0x0232
            L_0x028b:
                if (r1 == 0) goto L_0x0292
                r11.mMemberRoutes = r3
                r1 = r0 | 1
                goto L_0x0293
            L_0x0292:
                r1 = r0
            L_0x0293:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.MediaRouter.RouteInfo.maybeUpdateDescriptor(androidx.mediarouter.media.MediaRouteDescriptor):int");
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("MediaRouter.RouteInfo{ uniqueId=");
            m.append(this.mUniqueId);
            m.append(", name=");
            m.append(this.mName);
            m.append(", description=");
            m.append(this.mDescription);
            m.append(", iconUri=");
            m.append(this.mIconUri);
            m.append(", enabled=");
            m.append(this.mEnabled);
            m.append(", connectionState=");
            m.append(this.mConnectionState);
            m.append(", canDisconnect=");
            m.append(this.mCanDisconnect);
            m.append(", playbackType=");
            m.append(this.mPlaybackType);
            m.append(", playbackStream=");
            m.append(this.mPlaybackStream);
            m.append(", deviceType=");
            m.append(this.mDeviceType);
            m.append(", volumeHandling=");
            m.append(this.mVolumeHandling);
            m.append(", volume=");
            m.append(this.mVolume);
            m.append(", volumeMax=");
            m.append(this.mVolumeMax);
            m.append(", presentationDisplayId=");
            m.append(this.mPresentationDisplayId);
            m.append(", extras=");
            m.append(this.mExtras);
            m.append(", settingsIntent=");
            m.append(this.mSettingsIntent);
            m.append(", providerPackageName=");
            ProviderInfo providerInfo = this.mProvider;
            Objects.requireNonNull(providerInfo);
            MediaRouteProvider.ProviderMetadata providerMetadata = providerInfo.mMetadata;
            Objects.requireNonNull(providerMetadata);
            m.append(providerMetadata.mComponentName.getPackageName());
            sb.append(m.toString());
            if (isGroup()) {
                sb.append(", members=[");
                int size = this.mMemberRoutes.size();
                for (int i = 0; i < size; i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    if (this.mMemberRoutes.get(i) != this) {
                        RouteInfo routeInfo = (RouteInfo) this.mMemberRoutes.get(i);
                        Objects.requireNonNull(routeInfo);
                        sb.append(routeInfo.mUniqueId);
                    }
                }
                sb.append(']');
            }
            sb.append(" }");
            return sb.toString();
        }

        public final void updateDynamicDescriptors(Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> collection) {
            this.mMemberRoutes.clear();
            if (this.mDynamicGroupDescriptors == null) {
                this.mDynamicGroupDescriptors = new ArrayMap();
            }
            this.mDynamicGroupDescriptors.clear();
            for (MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor next : collection) {
                Objects.requireNonNull(next);
                RouteInfo findRouteByDescriptorId = this.mProvider.findRouteByDescriptorId(next.mMediaRouteDescriptor.getId());
                if (findRouteByDescriptorId != null) {
                    this.mDynamicGroupDescriptors.put(findRouteByDescriptorId.mUniqueId, next);
                    int i = next.mSelectionState;
                    if (i == 2 || i == 3) {
                        this.mMemberRoutes.add(findRouteByDescriptorId);
                    }
                }
            }
            MediaRouter.getGlobalRouter().mCallbackHandler.post(259, this);
        }

        public RouteInfo(ProviderInfo providerInfo, String str, String str2) {
            this.mProvider = providerInfo;
            this.mDescriptorId = str;
            this.mUniqueId = str2;
        }

        public static MediaRouteProvider.DynamicGroupRouteController getDynamicGroupController() {
            MediaRouter.checkCallingThread();
            MediaRouteProvider.RouteController routeController = MediaRouter.getGlobalRouter().mSelectedRouteController;
            if (routeController instanceof MediaRouteProvider.DynamicGroupRouteController) {
                return (MediaRouteProvider.DynamicGroupRouteController) routeController;
            }
            return null;
        }

        public final DynamicGroupState getDynamicGroupState(RouteInfo routeInfo) {
            Objects.requireNonNull(routeInfo, "route must not be null");
            ArrayMap arrayMap = this.mDynamicGroupDescriptors;
            if (arrayMap == null || !arrayMap.containsKey(routeInfo.mUniqueId)) {
                return null;
            }
            ArrayMap arrayMap2 = this.mDynamicGroupDescriptors;
            String str = routeInfo.mUniqueId;
            Objects.requireNonNull(arrayMap2);
            return new DynamicGroupState((MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor) arrayMap2.getOrDefault(str, null));
        }

        public final int getVolumeHandling() {
            boolean z;
            if (isGroup()) {
                if (MediaRouter.sGlobal == null) {
                    z = false;
                } else {
                    Objects.requireNonNull(MediaRouter.getGlobalRouter());
                    z = true;
                }
                if (!z) {
                    return 0;
                }
            }
            return this.mVolumeHandling;
        }

        public final boolean isDefaultOrBluetooth() {
            boolean z;
            MediaRouter.checkCallingThread();
            GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
            Objects.requireNonNull(globalRouter);
            RouteInfo routeInfo = globalRouter.mDefaultRoute;
            if (routeInfo != null) {
                if (routeInfo == this) {
                    z = true;
                } else {
                    z = false;
                }
                if (z || this.mDeviceType == 3) {
                    return true;
                }
                MediaRouteProvider providerInstance = getProviderInstance();
                Objects.requireNonNull(providerInstance);
                MediaRouteProvider.ProviderMetadata providerMetadata = providerInstance.mMetadata;
                Objects.requireNonNull(providerMetadata);
                if (!TextUtils.equals(providerMetadata.mComponentName.getPackageName(), ThemeOverlayApplier.ANDROID_PACKAGE) || !supportsControlCategory("android.media.intent.category.LIVE_AUDIO") || supportsControlCategory("android.media.intent.category.LIVE_VIDEO")) {
                    return false;
                }
                return true;
            }
            throw new IllegalStateException("There is no default route.  The media router has not yet been fully initialized.");
        }

        public final boolean isGroup() {
            if (getMemberRoutes().size() >= 1) {
                return true;
            }
            return false;
        }

        public final boolean isSelected() {
            MediaRouter.checkCallingThread();
            if (MediaRouter.getGlobalRouter().getSelectedRoute() == this) {
                return true;
            }
            return false;
        }

        public final void requestSetVolume(int i) {
            MediaRouteProvider.RouteController routeController;
            MediaRouteProvider.RouteController routeController2;
            MediaRouter.checkCallingThread();
            GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
            int min = Math.min(this.mVolumeMax, Math.max(0, i));
            Objects.requireNonNull(globalRouter);
            if (this == globalRouter.mSelectedRoute && (routeController2 = globalRouter.mSelectedRouteController) != null) {
                routeController2.onSetVolume(min);
            } else if (!globalRouter.mRouteControllerMap.isEmpty() && (routeController = (MediaRouteProvider.RouteController) globalRouter.mRouteControllerMap.get(this.mUniqueId)) != null) {
                routeController.onSetVolume(min);
            }
        }

        public final void requestUpdateVolume(int i) {
            MediaRouteProvider.RouteController routeController;
            MediaRouteProvider.RouteController routeController2;
            MediaRouter.checkCallingThread();
            if (i != 0) {
                GlobalMediaRouter globalRouter = MediaRouter.getGlobalRouter();
                Objects.requireNonNull(globalRouter);
                if (this == globalRouter.mSelectedRoute && (routeController2 = globalRouter.mSelectedRouteController) != null) {
                    routeController2.onUpdateVolume(i);
                } else if (!globalRouter.mRouteControllerMap.isEmpty() && (routeController = (MediaRouteProvider.RouteController) globalRouter.mRouteControllerMap.get(this.mUniqueId)) != null) {
                    routeController.onUpdateVolume(i);
                }
            }
        }

        public final void select() {
            MediaRouter.checkCallingThread();
            MediaRouter.getGlobalRouter().selectRoute(this, 3);
        }

        public final boolean supportsControlCategory(String str) {
            MediaRouter.checkCallingThread();
            int size = this.mControlFilters.size();
            for (int i = 0; i < size; i++) {
                if (this.mControlFilters.get(i).hasCategory(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public static final class CallbackRecord {
        public final Callback mCallback;
        public int mFlags;
        public final MediaRouter mRouter;
        public MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;
        public long mTimestamp;

        public CallbackRecord(MediaRouter mediaRouter, Callback callback) {
            this.mRouter = mediaRouter;
            this.mCallback = callback;
        }
    }

    public static final class PrepareTransferNotifier {
        public boolean mCanceled = false;
        public boolean mFinished = false;
        public final RouteInfo mFromRoute;
        public ListenableFuture<Void> mFuture = null;
        public final ArrayList mMemberRoutes;
        public final int mReason;
        public final RouteInfo mRequestedRoute;
        public final WeakReference<GlobalMediaRouter> mRouter;
        public final RouteInfo mToRoute;
        public final MediaRouteProvider.RouteController mToRouteController;

        public PrepareTransferNotifier(GlobalMediaRouter globalMediaRouter, RouteInfo routeInfo, MediaRouteProvider.RouteController routeController, int i, RouteInfo routeInfo2, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> collection) {
            ArrayList arrayList = null;
            this.mRouter = new WeakReference<>(globalMediaRouter);
            this.mToRoute = routeInfo;
            this.mToRouteController = routeController;
            this.mReason = i;
            this.mFromRoute = globalMediaRouter.mSelectedRoute;
            this.mRequestedRoute = routeInfo2;
            this.mMemberRoutes = collection != null ? new ArrayList(collection) : arrayList;
            globalMediaRouter.mCallbackHandler.postDelayed(new BubbleStackView$$ExternalSyntheticLambda15(this, 1), WifiTracker.MAX_SCAN_RESULT_AGE_MILLIS);
        }

        public final void finishTransfer() {
            ListenableFuture<Void> listenableFuture;
            RouteInfo routeInfo;
            MediaRouter.checkCallingThread();
            if (!this.mFinished && !this.mCanceled) {
                GlobalMediaRouter globalMediaRouter = this.mRouter.get();
                if (globalMediaRouter != null && globalMediaRouter.mTransferNotifier == this && ((listenableFuture = this.mFuture) == null || !listenableFuture.isCancelled())) {
                    this.mFinished = true;
                    globalMediaRouter.mTransferNotifier = null;
                    GlobalMediaRouter globalMediaRouter2 = this.mRouter.get();
                    if (globalMediaRouter2 != null && globalMediaRouter2.mSelectedRoute == (routeInfo = this.mFromRoute)) {
                        GlobalMediaRouter.CallbackHandler callbackHandler = globalMediaRouter2.mCallbackHandler;
                        int i = this.mReason;
                        Objects.requireNonNull(callbackHandler);
                        Message obtainMessage = callbackHandler.obtainMessage(263, routeInfo);
                        obtainMessage.arg1 = i;
                        obtainMessage.sendToTarget();
                        MediaRouteProvider.RouteController routeController = globalMediaRouter2.mSelectedRouteController;
                        if (routeController != null) {
                            routeController.onUnselect(this.mReason);
                            globalMediaRouter2.mSelectedRouteController.onRelease();
                        }
                        if (!globalMediaRouter2.mRouteControllerMap.isEmpty()) {
                            for (MediaRouteProvider.RouteController routeController2 : globalMediaRouter2.mRouteControllerMap.values()) {
                                routeController2.onUnselect(this.mReason);
                                routeController2.onRelease();
                            }
                            globalMediaRouter2.mRouteControllerMap.clear();
                        }
                        globalMediaRouter2.mSelectedRouteController = null;
                    }
                    GlobalMediaRouter globalMediaRouter3 = this.mRouter.get();
                    if (globalMediaRouter3 != null) {
                        RouteInfo routeInfo2 = this.mToRoute;
                        globalMediaRouter3.mSelectedRoute = routeInfo2;
                        globalMediaRouter3.mSelectedRouteController = this.mToRouteController;
                        RouteInfo routeInfo3 = this.mRequestedRoute;
                        if (routeInfo3 == null) {
                            GlobalMediaRouter.CallbackHandler callbackHandler2 = globalMediaRouter3.mCallbackHandler;
                            Pair pair = new Pair(this.mFromRoute, routeInfo2);
                            int i2 = this.mReason;
                            Objects.requireNonNull(callbackHandler2);
                            Message obtainMessage2 = callbackHandler2.obtainMessage(262, pair);
                            obtainMessage2.arg1 = i2;
                            obtainMessage2.sendToTarget();
                        } else {
                            GlobalMediaRouter.CallbackHandler callbackHandler3 = globalMediaRouter3.mCallbackHandler;
                            Pair pair2 = new Pair(routeInfo3, routeInfo2);
                            int i3 = this.mReason;
                            Objects.requireNonNull(callbackHandler3);
                            Message obtainMessage3 = callbackHandler3.obtainMessage(264, pair2);
                            obtainMessage3.arg1 = i3;
                            obtainMessage3.sendToTarget();
                        }
                        globalMediaRouter3.mRouteControllerMap.clear();
                        globalMediaRouter3.maybeUpdateMemberRouteControllers();
                        globalMediaRouter3.updatePlaybackInfoFromSelectedRoute();
                        ArrayList arrayList = this.mMemberRoutes;
                        if (arrayList != null) {
                            globalMediaRouter3.mSelectedRoute.updateDynamicDescriptors(arrayList);
                        }
                    }
                } else if (!this.mFinished && !this.mCanceled) {
                    this.mCanceled = true;
                    MediaRouteProvider.RouteController routeController3 = this.mToRouteController;
                    if (routeController3 != null) {
                        routeController3.onUnselect(0);
                        this.mToRouteController.onRelease();
                    }
                }
            }
        }
    }

    public static GlobalMediaRouter getGlobalRouter() {
        GlobalMediaRouter globalMediaRouter = sGlobal;
        if (globalMediaRouter == null) {
            return null;
        }
        globalMediaRouter.ensureInitialized();
        return sGlobal;
    }

    public static MediaRouter getInstance(Context context) {
        if (context != null) {
            checkCallingThread();
            if (sGlobal == null) {
                sGlobal = new GlobalMediaRouter(context.getApplicationContext());
            }
            GlobalMediaRouter globalMediaRouter = sGlobal;
            Objects.requireNonNull(globalMediaRouter);
            int size = globalMediaRouter.mRouters.size();
            while (true) {
                size--;
                if (size >= 0) {
                    MediaRouter mediaRouter = (MediaRouter) globalMediaRouter.mRouters.get(size).get();
                    if (mediaRouter == null) {
                        globalMediaRouter.mRouters.remove(size);
                    } else if (mediaRouter.mContext == context) {
                        return mediaRouter;
                    }
                } else {
                    MediaRouter mediaRouter2 = new MediaRouter(context);
                    globalMediaRouter.mRouters.add(new WeakReference(mediaRouter2));
                    return mediaRouter2;
                }
            }
        } else {
            throw new IllegalArgumentException("context must not be null");
        }
    }

    public static boolean isRouteAvailable(MediaRouteSelector mediaRouteSelector) {
        if (mediaRouteSelector != null) {
            checkCallingThread();
            GlobalMediaRouter globalRouter = getGlobalRouter();
            Objects.requireNonNull(globalRouter);
            if (mediaRouteSelector.isEmpty()) {
                return false;
            }
            if (!globalRouter.mLowRam) {
                int size = globalRouter.mRoutes.size();
                int i = 0;
                while (i < size) {
                    RouteInfo routeInfo = globalRouter.mRoutes.get(i);
                    if (routeInfo.isDefaultOrBluetooth() || !routeInfo.matchesSelector(mediaRouteSelector)) {
                        i++;
                    }
                }
                return false;
            }
            return true;
        }
        throw new IllegalArgumentException("selector must not be null");
    }

    public static void unselect(int i) {
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException("Unsupported reason to unselect route");
        }
        checkCallingThread();
        GlobalMediaRouter globalRouter = getGlobalRouter();
        RouteInfo chooseFallbackRoute = globalRouter.chooseFallbackRoute();
        if (globalRouter.getSelectedRoute() != chooseFallbackRoute) {
            globalRouter.selectRoute(chooseFallbackRoute, i);
        }
    }

    public final void addCallback(MediaRouteSelector mediaRouteSelector, Callback callback, int i) {
        CallbackRecord callbackRecord;
        if (mediaRouteSelector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (callback != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d("MediaRouter", "addCallback: selector=" + mediaRouteSelector + ", callback=" + callback + ", flags=" + Integer.toHexString(i));
            }
            int size = this.mCallbackRecords.size();
            boolean z = false;
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (this.mCallbackRecords.get(i2).mCallback == callback) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 < 0) {
                callbackRecord = new CallbackRecord(this, callback);
                this.mCallbackRecords.add(callbackRecord);
            } else {
                callbackRecord = this.mCallbackRecords.get(i2);
            }
            boolean z2 = true;
            if (i != callbackRecord.mFlags) {
                callbackRecord.mFlags = i;
                z = true;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if ((i & 1) != 0) {
                z = true;
            }
            callbackRecord.mTimestamp = elapsedRealtime;
            MediaRouteSelector mediaRouteSelector2 = callbackRecord.mSelector;
            mediaRouteSelector2.ensureControlCategories();
            mediaRouteSelector.ensureControlCategories();
            if (!mediaRouteSelector2.mControlCategories.containsAll(mediaRouteSelector.mControlCategories)) {
                MediaRouteSelector.Builder builder = new MediaRouteSelector.Builder(callbackRecord.mSelector);
                builder.addControlCategories(mediaRouteSelector.getControlCategories());
                callbackRecord.mSelector = builder.build();
            } else {
                z2 = z;
            }
            if (z2) {
                getGlobalRouter().updateDiscoveryRequest();
            }
        } else {
            throw new IllegalArgumentException("callback must not be null");
        }
    }

    public final void removeCallback(Callback callback) {
        if (callback != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d("MediaRouter", "removeCallback: callback=" + callback);
            }
            int size = this.mCallbackRecords.size();
            int i = 0;
            while (true) {
                if (i >= size) {
                    i = -1;
                    break;
                } else if (this.mCallbackRecords.get(i).mCallback == callback) {
                    break;
                } else {
                    i++;
                }
            }
            if (i >= 0) {
                this.mCallbackRecords.remove(i);
                getGlobalRouter().updateDiscoveryRequest();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    public MediaRouter(Context context) {
        this.mContext = context;
    }

    public static void checkCallingThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("The media router service must only be accessed on the application's main thread.");
        }
    }

    public static RouteInfo getSelectedRoute() {
        checkCallingThread();
        return getGlobalRouter().getSelectedRoute();
    }
}
