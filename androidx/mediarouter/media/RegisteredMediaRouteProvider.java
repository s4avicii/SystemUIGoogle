package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class RegisteredMediaRouteProvider extends MediaRouteProvider implements ServiceConnection {
    public static final boolean DEBUG = Log.isLoggable("MediaRouteProviderProxy", 3);
    public Connection mActiveConnection;
    public boolean mBound;
    public final ComponentName mComponentName;
    public boolean mConnectionReady;
    public ControllerCallback mControllerCallback;
    public final ArrayList<ControllerConnection> mControllerConnections = new ArrayList<>();
    public final PrivateHandler mPrivateHandler;
    public boolean mStarted;

    public final class Connection implements IBinder.DeathRecipient {
        public int mNextControllerId = 1;
        public int mNextRequestId = 1;
        public final SparseArray<MediaRouter.ControlRequestCallback> mPendingCallbacks = new SparseArray<>();
        public int mPendingRegisterRequestId;
        public final ReceiveHandler mReceiveHandler;
        public final Messenger mReceiveMessenger;
        public final Messenger mServiceMessenger;
        public int mServiceVersion;

        public Connection(Messenger messenger) {
            this.mServiceMessenger = messenger;
            ReceiveHandler receiveHandler = new ReceiveHandler(this);
            this.mReceiveHandler = receiveHandler;
            this.mReceiveMessenger = new Messenger(receiveHandler);
        }

        public final void binderDied() {
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() {
                public final void run() {
                    Connection connection = Connection.this;
                    RegisteredMediaRouteProvider registeredMediaRouteProvider = RegisteredMediaRouteProvider.this;
                    Objects.requireNonNull(registeredMediaRouteProvider);
                    if (registeredMediaRouteProvider.mActiveConnection == connection) {
                        if (RegisteredMediaRouteProvider.DEBUG) {
                            Log.d("MediaRouteProviderProxy", registeredMediaRouteProvider + ": Service connection died");
                        }
                        registeredMediaRouteProvider.disconnect();
                    }
                }
            });
        }

        public final void selectRoute(int i) {
            int i2 = this.mNextRequestId;
            this.mNextRequestId = i2 + 1;
            sendRequest(5, i2, i, (Bundle) null, (Bundle) null);
        }

        public final void setVolume(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt("volume", i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(7, i3, i, (Bundle) null, bundle);
        }

        public final void updateVolume(int i, int i2) {
            Bundle bundle = new Bundle();
            bundle.putInt("volume", i2);
            int i3 = this.mNextRequestId;
            this.mNextRequestId = i3 + 1;
            sendRequest(8, i3, i, (Bundle) null, bundle);
        }

        public final boolean sendRequest(int i, int i2, int i3, Bundle bundle, Bundle bundle2) {
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.arg1 = i2;
            obtain.arg2 = i3;
            obtain.obj = bundle;
            obtain.setData(bundle2);
            obtain.replyTo = this.mReceiveMessenger;
            try {
                this.mServiceMessenger.send(obtain);
                return true;
            } catch (DeadObjectException unused) {
                return false;
            } catch (RemoteException e) {
                if (i == 2) {
                    return false;
                }
                Log.e("MediaRouteProviderProxy", "Could not send message to service.", e);
                return false;
            }
        }
    }

    public interface ControllerCallback {
    }

    public interface ControllerConnection {
        void attachConnection(Connection connection);

        void detachConnection();

        int getControllerId();
    }

    public static final class PrivateHandler extends Handler {
    }

    public static final class ReceiveHandler extends Handler {
        public final WeakReference<Connection> mConnectionRef;

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v2, resolved type: java.lang.String} */
        /* JADX WARNING: type inference failed for: r10v0 */
        /* JADX WARNING: type inference failed for: r10v4 */
        /* JADX WARNING: type inference failed for: r10v6 */
        /* JADX WARNING: type inference failed for: r10v7, types: [java.lang.Object, androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection] */
        /* JADX WARNING: type inference failed for: r10v8 */
        /* JADX WARNING: type inference failed for: r10v9 */
        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r20) {
            /*
                r19 = this;
                r0 = r19
                r1 = r20
                java.lang.ref.WeakReference<androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection> r0 = r0.mConnectionRef
                java.lang.Object r0 = r0.get()
                androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r0 = (androidx.mediarouter.media.RegisteredMediaRouteProvider.Connection) r0
                if (r0 == 0) goto L_0x0272
                int r2 = r1.what
                int r3 = r1.arg1
                int r4 = r1.arg2
                java.lang.Object r5 = r1.obj
                android.os.Bundle r6 = r20.peekData()
                r7 = 0
                java.lang.String r8 = "MediaRouteProviderProxy"
                r9 = 1
                r10 = 0
                switch(r2) {
                    case 0: goto L_0x0216;
                    case 1: goto L_0x0257;
                    case 2: goto L_0x01bb;
                    case 3: goto L_0x019f;
                    case 4: goto L_0x017a;
                    case 5: goto L_0x0163;
                    case 6: goto L_0x0132;
                    case 7: goto L_0x0078;
                    case 8: goto L_0x0024;
                    default: goto L_0x0022;
                }
            L_0x0022:
                goto L_0x0258
            L_0x0024:
                androidx.mediarouter.media.RegisteredMediaRouteProvider r2 = androidx.mediarouter.media.RegisteredMediaRouteProvider.this
                java.util.Objects.requireNonNull(r2)
                androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r3 = r2.mActiveConnection
                if (r3 != r0) goto L_0x0258
                java.util.ArrayList<androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection> r0 = r2.mControllerConnections
                java.util.Iterator r0 = r0.iterator()
            L_0x0033:
                boolean r3 = r0.hasNext()
                if (r3 == 0) goto L_0x0046
                java.lang.Object r3 = r0.next()
                androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection r3 = (androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection) r3
                int r5 = r3.getControllerId()
                if (r5 != r4) goto L_0x0033
                r10 = r3
            L_0x0046:
                androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerCallback r0 = r2.mControllerCallback
                if (r0 == 0) goto L_0x006b
                boolean r3 = r10 instanceof androidx.mediarouter.media.MediaRouteProvider.RouteController
                if (r3 == 0) goto L_0x006b
                r3 = r10
                androidx.mediarouter.media.MediaRouteProvider$RouteController r3 = (androidx.mediarouter.media.MediaRouteProvider.RouteController) r3
                androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher$$ExternalSyntheticLambda0 r0 = (androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher$$ExternalSyntheticLambda0) r0
                androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher r0 = r0.f$0
                java.util.Objects.requireNonNull(r0)
                androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher$Callback r0 = r0.mCallback
                androidx.mediarouter.media.MediaRouter$GlobalMediaRouter r0 = (androidx.mediarouter.media.MediaRouter.GlobalMediaRouter) r0
                java.util.Objects.requireNonNull(r0)
                androidx.mediarouter.media.MediaRouteProvider$RouteController r4 = r0.mSelectedRouteController
                if (r4 != r3) goto L_0x006b
                androidx.mediarouter.media.MediaRouter$RouteInfo r3 = r0.chooseFallbackRoute()
                r4 = 2
                r0.selectRoute(r3, r4)
            L_0x006b:
                java.util.ArrayList<androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection> r0 = r2.mControllerConnections
                r0.remove(r10)
                r10.detachConnection()
                r2.updateBinding()
                goto L_0x0258
            L_0x0078:
                if (r5 == 0) goto L_0x007e
                boolean r2 = r5 instanceof android.os.Bundle
                if (r2 == 0) goto L_0x0258
            L_0x007e:
                android.os.Bundle r5 = (android.os.Bundle) r5
                int r2 = r0.mServiceVersion
                if (r2 == 0) goto L_0x0258
                java.lang.String r2 = "groupRoute"
                android.os.Parcelable r2 = r5.getParcelable(r2)
                android.os.Bundle r2 = (android.os.Bundle) r2
                if (r2 == 0) goto L_0x0094
                androidx.mediarouter.media.MediaRouteDescriptor r3 = new androidx.mediarouter.media.MediaRouteDescriptor
                r3.<init>(r2)
                goto L_0x0095
            L_0x0094:
                r3 = r10
            L_0x0095:
                java.lang.String r2 = "dynamicRoutes"
                java.util.ArrayList r2 = r5.getParcelableArrayList(r2)
                java.util.ArrayList r5 = new java.util.ArrayList
                r5.<init>()
                java.util.Iterator r2 = r2.iterator()
            L_0x00a4:
                boolean r6 = r2.hasNext()
                if (r6 == 0) goto L_0x00e7
                java.lang.Object r6 = r2.next()
                android.os.Bundle r6 = (android.os.Bundle) r6
                if (r6 != 0) goto L_0x00b4
                r6 = r10
                goto L_0x00e3
            L_0x00b4:
                java.lang.String r11 = "mrDescriptor"
                android.os.Bundle r11 = r6.getBundle(r11)
                if (r11 == 0) goto L_0x00c3
                androidx.mediarouter.media.MediaRouteDescriptor r12 = new androidx.mediarouter.media.MediaRouteDescriptor
                r12.<init>(r11)
                r14 = r12
                goto L_0x00c4
            L_0x00c3:
                r14 = r10
            L_0x00c4:
                java.lang.String r11 = "selectionState"
                int r15 = r6.getInt(r11, r9)
                java.lang.String r11 = "isUnselectable"
                boolean r16 = r6.getBoolean(r11, r7)
                java.lang.String r11 = "isGroupable"
                boolean r17 = r6.getBoolean(r11, r7)
                java.lang.String r11 = "isTransferable"
                boolean r18 = r6.getBoolean(r11, r7)
                androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController$DynamicRouteDescriptor r6 = new androidx.mediarouter.media.MediaRouteProvider$DynamicGroupRouteController$DynamicRouteDescriptor
                r13 = r6
                r13.<init>(r14, r15, r16, r17, r18)
            L_0x00e3:
                r5.add(r6)
                goto L_0x00a4
            L_0x00e7:
                androidx.mediarouter.media.RegisteredMediaRouteProvider r2 = androidx.mediarouter.media.RegisteredMediaRouteProvider.this
                java.util.Objects.requireNonNull(r2)
                androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r6 = r2.mActiveConnection
                if (r6 != r0) goto L_0x0257
                boolean r0 = androidx.mediarouter.media.RegisteredMediaRouteProvider.DEBUG
                if (r0 == 0) goto L_0x010b
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                r0.append(r2)
                java.lang.String r6 = ": DynamicRouteDescriptors changed, descriptors="
                r0.append(r6)
                r0.append(r5)
                java.lang.String r0 = r0.toString()
                android.util.Log.d(r8, r0)
            L_0x010b:
                java.util.ArrayList<androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection> r0 = r2.mControllerConnections
                java.util.Iterator r0 = r0.iterator()
            L_0x0111:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0124
                java.lang.Object r2 = r0.next()
                androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection r2 = (androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection) r2
                int r6 = r2.getControllerId()
                if (r6 != r4) goto L_0x0111
                r10 = r2
            L_0x0124:
                boolean r0 = r10 instanceof androidx.mediarouter.media.RegisteredMediaRouteProvider.RegisteredDynamicController
                if (r0 == 0) goto L_0x0257
                androidx.mediarouter.media.RegisteredMediaRouteProvider$RegisteredDynamicController r10 = (androidx.mediarouter.media.RegisteredMediaRouteProvider.RegisteredDynamicController) r10
                java.util.Objects.requireNonNull(r10)
                r10.notifyDynamicRoutesChanged(r3, r5)
                goto L_0x0257
            L_0x0132:
                boolean r2 = r5 instanceof android.os.Bundle
                if (r2 == 0) goto L_0x015c
                android.os.Bundle r5 = (android.os.Bundle) r5
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r2 = r0.mPendingCallbacks
                java.lang.Object r2 = r2.get(r3)
                androidx.mediarouter.media.MediaRouter$ControlRequestCallback r2 = (androidx.mediarouter.media.MediaRouter.ControlRequestCallback) r2
                if (r5 == 0) goto L_0x0155
                java.lang.String r4 = "routeId"
                boolean r4 = r5.containsKey(r4)
                if (r4 == 0) goto L_0x0155
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r0 = r0.mPendingCallbacks
                r0.remove(r3)
                r2.onResult(r5)
                goto L_0x0258
            L_0x0155:
                java.lang.String r0 = "DynamicGroupRouteController is created without valid route id."
                r2.onError(r0, r5)
                goto L_0x0258
            L_0x015c:
                java.lang.String r0 = "No further information on the dynamic group controller"
                android.util.Log.w(r8, r0)
                goto L_0x0258
            L_0x0163:
                if (r5 == 0) goto L_0x0169
                boolean r2 = r5 instanceof android.os.Bundle
                if (r2 == 0) goto L_0x0258
            L_0x0169:
                android.os.Bundle r5 = (android.os.Bundle) r5
                int r2 = r0.mServiceVersion
                if (r2 == 0) goto L_0x0258
                androidx.mediarouter.media.RegisteredMediaRouteProvider r2 = androidx.mediarouter.media.RegisteredMediaRouteProvider.this
                androidx.mediarouter.media.MediaRouteProviderDescriptor r3 = androidx.mediarouter.media.MediaRouteProviderDescriptor.fromBundle(r5)
                r2.onConnectionDescriptorChanged(r0, r3)
                goto L_0x0257
            L_0x017a:
                if (r5 == 0) goto L_0x0180
                boolean r2 = r5 instanceof android.os.Bundle
                if (r2 == 0) goto L_0x0258
            L_0x0180:
                if (r6 != 0) goto L_0x0183
                goto L_0x0189
            L_0x0183:
                java.lang.String r2 = "error"
                java.lang.String r10 = r6.getString(r2)
            L_0x0189:
                android.os.Bundle r5 = (android.os.Bundle) r5
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r2 = r0.mPendingCallbacks
                java.lang.Object r2 = r2.get(r3)
                androidx.mediarouter.media.MediaRouter$ControlRequestCallback r2 = (androidx.mediarouter.media.MediaRouter.ControlRequestCallback) r2
                if (r2 == 0) goto L_0x0258
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r0 = r0.mPendingCallbacks
                r0.remove(r3)
                r2.onError(r10, r5)
                goto L_0x0257
            L_0x019f:
                if (r5 == 0) goto L_0x01a5
                boolean r2 = r5 instanceof android.os.Bundle
                if (r2 == 0) goto L_0x0258
            L_0x01a5:
                android.os.Bundle r5 = (android.os.Bundle) r5
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r2 = r0.mPendingCallbacks
                java.lang.Object r2 = r2.get(r3)
                androidx.mediarouter.media.MediaRouter$ControlRequestCallback r2 = (androidx.mediarouter.media.MediaRouter.ControlRequestCallback) r2
                if (r2 == 0) goto L_0x0258
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r0 = r0.mPendingCallbacks
                r0.remove(r3)
                r2.onResult(r5)
                goto L_0x0257
            L_0x01bb:
                if (r5 == 0) goto L_0x01c1
                boolean r2 = r5 instanceof android.os.Bundle
                if (r2 == 0) goto L_0x0258
            L_0x01c1:
                android.os.Bundle r5 = (android.os.Bundle) r5
                int r2 = r0.mServiceVersion
                if (r2 != 0) goto L_0x0258
                int r2 = r0.mPendingRegisterRequestId
                if (r3 != r2) goto L_0x0258
                if (r4 < r9) goto L_0x0258
                r0.mPendingRegisterRequestId = r7
                r0.mServiceVersion = r4
                androidx.mediarouter.media.RegisteredMediaRouteProvider r2 = androidx.mediarouter.media.RegisteredMediaRouteProvider.this
                androidx.mediarouter.media.MediaRouteProviderDescriptor r3 = androidx.mediarouter.media.MediaRouteProviderDescriptor.fromBundle(r5)
                r2.onConnectionDescriptorChanged(r0, r3)
                androidx.mediarouter.media.RegisteredMediaRouteProvider r2 = androidx.mediarouter.media.RegisteredMediaRouteProvider.this
                java.util.Objects.requireNonNull(r2)
                androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r3 = r2.mActiveConnection
                if (r3 != r0) goto L_0x0257
                r2.mConnectionReady = r9
                java.util.ArrayList<androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection> r0 = r2.mControllerConnections
                int r0 = r0.size()
            L_0x01eb:
                if (r7 >= r0) goto L_0x01fd
                java.util.ArrayList<androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection> r3 = r2.mControllerConnections
                java.lang.Object r3 = r3.get(r7)
                androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection r3 = (androidx.mediarouter.media.RegisteredMediaRouteProvider.ControllerConnection) r3
                androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r4 = r2.mActiveConnection
                r3.attachConnection(r4)
                int r7 = r7 + 1
                goto L_0x01eb
            L_0x01fd:
                androidx.mediarouter.media.MediaRouteDiscoveryRequest r0 = r2.mDiscoveryRequest
                if (r0 == 0) goto L_0x0257
                androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r10 = r2.mActiveConnection
                java.util.Objects.requireNonNull(r10)
                int r12 = r10.mNextRequestId
                int r2 = r12 + 1
                r10.mNextRequestId = r2
                android.os.Bundle r14 = r0.mBundle
                r15 = 0
                r11 = 10
                r13 = 0
                r10.sendRequest(r11, r12, r13, r14, r15)
                goto L_0x0257
            L_0x0216:
                int r2 = r0.mPendingRegisterRequestId
                if (r3 != r2) goto L_0x0245
                r0.mPendingRegisterRequestId = r7
                androidx.mediarouter.media.RegisteredMediaRouteProvider r2 = androidx.mediarouter.media.RegisteredMediaRouteProvider.this
                java.util.Objects.requireNonNull(r2)
                androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r4 = r2.mActiveConnection
                if (r4 != r0) goto L_0x0245
                boolean r4 = androidx.mediarouter.media.RegisteredMediaRouteProvider.DEBUG
                if (r4 == 0) goto L_0x0242
                java.lang.StringBuilder r4 = new java.lang.StringBuilder
                r4.<init>()
                r4.append(r2)
                java.lang.String r5 = ": Service connection error - "
                r4.append(r5)
                java.lang.String r5 = "Registration failed"
                r4.append(r5)
                java.lang.String r4 = r4.toString()
                android.util.Log.d(r8, r4)
            L_0x0242:
                r2.unbind()
            L_0x0245:
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r2 = r0.mPendingCallbacks
                java.lang.Object r2 = r2.get(r3)
                androidx.mediarouter.media.MediaRouter$ControlRequestCallback r2 = (androidx.mediarouter.media.MediaRouter.ControlRequestCallback) r2
                if (r2 == 0) goto L_0x0257
                android.util.SparseArray<androidx.mediarouter.media.MediaRouter$ControlRequestCallback> r0 = r0.mPendingCallbacks
                r0.remove(r3)
                r2.onError(r10, r10)
            L_0x0257:
                r7 = r9
            L_0x0258:
                if (r7 != 0) goto L_0x0272
                boolean r0 = androidx.mediarouter.media.RegisteredMediaRouteProvider.DEBUG
                if (r0 == 0) goto L_0x0272
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r2 = "Unhandled message from server: "
                r0.append(r2)
                r0.append(r1)
                java.lang.String r0 = r0.toString()
                android.util.Log.d(r8, r0)
            L_0x0272:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.RegisteredMediaRouteProvider.ReceiveHandler.handleMessage(android.os.Message):void");
        }

        public ReceiveHandler(Connection connection) {
            this.mConnectionRef = new WeakReference<>(connection);
        }
    }

    public final class RegisteredDynamicController extends MediaRouteProvider.DynamicGroupRouteController implements ControllerConnection {
        public Connection mConnection;
        public int mControllerId = -1;
        public String mGroupableSectionTitle;
        public final String mInitialMemberRouteId;
        public int mPendingSetVolume = -1;
        public int mPendingUpdateVolumeDelta;
        public boolean mSelected;
        public String mTransferableSectionTitle;

        public final void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        public final void onUnselect() {
            onUnselect(0);
        }

        public RegisteredDynamicController(String str) {
            this.mInitialMemberRouteId = str;
        }

        public final void attachConnection(Connection connection) {
            C02851 r0 = new MediaRouter.ControlRequestCallback() {
                public final void onError(String str, Bundle bundle) {
                    Log.d("MediaRouteProviderProxy", "Error: " + str + ", data: " + bundle);
                }

                public final void onResult(Bundle bundle) {
                    RegisteredDynamicController.this.mGroupableSectionTitle = bundle.getString("groupableTitle");
                    RegisteredDynamicController.this.mTransferableSectionTitle = bundle.getString("transferableTitle");
                }
            };
            this.mConnection = connection;
            String str = this.mInitialMemberRouteId;
            Objects.requireNonNull(connection);
            int i = connection.mNextControllerId;
            connection.mNextControllerId = i + 1;
            int i2 = connection.mNextRequestId;
            connection.mNextRequestId = i2 + 1;
            Bundle bundle = new Bundle();
            bundle.putString("memberRouteId", str);
            connection.sendRequest(11, i2, i, (Bundle) null, bundle);
            connection.mPendingCallbacks.put(i2, r0);
            this.mControllerId = i;
            if (this.mSelected) {
                connection.selectRoute(i);
                int i3 = this.mPendingSetVolume;
                if (i3 >= 0) {
                    connection.setVolume(this.mControllerId, i3);
                    this.mPendingSetVolume = -1;
                }
                int i4 = this.mPendingUpdateVolumeDelta;
                if (i4 != 0) {
                    connection.updateVolume(this.mControllerId, i4);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        public final void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(4, i2, i, (Bundle) null, (Bundle) null);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        public final void onAddMemberRoute(String str) {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                Bundle bundle = new Bundle();
                bundle.putString("memberRouteId", str);
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(12, i2, i, (Bundle) null, bundle);
            }
        }

        public final void onRelease() {
            RegisteredMediaRouteProvider registeredMediaRouteProvider = RegisteredMediaRouteProvider.this;
            Objects.requireNonNull(registeredMediaRouteProvider);
            registeredMediaRouteProvider.mControllerConnections.remove(this);
            detachConnection();
            registeredMediaRouteProvider.updateBinding();
        }

        public final void onRemoveMemberRoute(String str) {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                Bundle bundle = new Bundle();
                bundle.putString("memberRouteId", str);
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(13, i2, i, (Bundle) null, bundle);
            }
        }

        public final void onSetVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, i);
                return;
            }
            this.mPendingSetVolume = i;
            this.mPendingUpdateVolumeDelta = 0;
        }

        public final void onUnselect(int i) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                int i2 = this.mControllerId;
                Bundle bundle = new Bundle();
                bundle.putInt("unselectReason", i);
                int i3 = connection.mNextRequestId;
                connection.mNextRequestId = i3 + 1;
                connection.sendRequest(6, i3, i2, (Bundle) null, bundle);
            }
        }

        public final void onUpdateMemberRoutes(List<String> list) {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("memberRouteIds", new ArrayList(list));
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(14, i2, i, (Bundle) null, bundle);
            }
        }

        public final void onUpdateVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, i);
            } else {
                this.mPendingUpdateVolumeDelta += i;
            }
        }

        public final int getControllerId() {
            return this.mControllerId;
        }

        public final String getGroupableSelectionTitle() {
            return this.mGroupableSectionTitle;
        }

        public final String getTransferableSectionTitle() {
            return this.mTransferableSectionTitle;
        }
    }

    public final class RegisteredRouteController extends MediaRouteProvider.RouteController implements ControllerConnection {
        public Connection mConnection;
        public int mControllerId;
        public int mPendingSetVolume = -1;
        public int mPendingUpdateVolumeDelta;
        public final String mRouteGroupId;
        public final String mRouteId;
        public boolean mSelected;

        public final void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        public final void onUnselect() {
            onUnselect(0);
        }

        public RegisteredRouteController(String str, String str2) {
            this.mRouteId = str;
            this.mRouteGroupId = str2;
        }

        public final void attachConnection(Connection connection) {
            this.mConnection = connection;
            String str = this.mRouteId;
            String str2 = this.mRouteGroupId;
            Objects.requireNonNull(connection);
            int i = connection.mNextControllerId;
            connection.mNextControllerId = i + 1;
            Bundle bundle = new Bundle();
            bundle.putString("routeId", str);
            bundle.putString("routeGroupId", str2);
            int i2 = connection.mNextRequestId;
            connection.mNextRequestId = i2 + 1;
            connection.sendRequest(3, i2, i, (Bundle) null, bundle);
            this.mControllerId = i;
            if (this.mSelected) {
                connection.selectRoute(i);
                int i3 = this.mPendingSetVolume;
                if (i3 >= 0) {
                    connection.setVolume(this.mControllerId, i3);
                    this.mPendingSetVolume = -1;
                }
                int i4 = this.mPendingUpdateVolumeDelta;
                if (i4 != 0) {
                    connection.updateVolume(this.mControllerId, i4);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        public final void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                int i = this.mControllerId;
                int i2 = connection.mNextRequestId;
                connection.mNextRequestId = i2 + 1;
                connection.sendRequest(4, i2, i, (Bundle) null, (Bundle) null);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        public final void onRelease() {
            RegisteredMediaRouteProvider registeredMediaRouteProvider = RegisteredMediaRouteProvider.this;
            Objects.requireNonNull(registeredMediaRouteProvider);
            registeredMediaRouteProvider.mControllerConnections.remove(this);
            detachConnection();
            registeredMediaRouteProvider.updateBinding();
        }

        public final void onSetVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, i);
                return;
            }
            this.mPendingSetVolume = i;
            this.mPendingUpdateVolumeDelta = 0;
        }

        public final void onUnselect(int i) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                int i2 = this.mControllerId;
                Bundle bundle = new Bundle();
                bundle.putInt("unselectReason", i);
                int i3 = connection.mNextRequestId;
                connection.mNextRequestId = i3 + 1;
                connection.sendRequest(6, i3, i2, (Bundle) null, bundle);
            }
        }

        public final void onUpdateVolume(int i) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, i);
            } else {
                this.mPendingUpdateVolumeDelta += i;
            }
        }

        public final int getControllerId() {
            return this.mControllerId;
        }
    }

    public final MediaRouteProvider.RouteController onCreateRouteController(String str) {
        if (str != null) {
            return createRouteController(str, (String) null);
        }
        throw new IllegalArgumentException("routeId cannot be null");
    }

    public RegisteredMediaRouteProvider(Context context, ComponentName componentName) {
        super(context, new MediaRouteProvider.ProviderMetadata(componentName));
        this.mComponentName = componentName;
        this.mPrivateHandler = new PrivateHandler();
    }

    public final void bind() {
        if (!this.mBound) {
            boolean z = DEBUG;
            if (z) {
                Log.d("MediaRouteProviderProxy", this + ": Binding");
            }
            Intent intent = new Intent("android.media.MediaRouteProviderService");
            intent.setComponent(this.mComponentName);
            try {
                boolean bindService = this.mContext.bindService(intent, this, 4097);
                this.mBound = bindService;
                if (!bindService && z) {
                    Log.d("MediaRouteProviderProxy", this + ": Bind failed");
                }
            } catch (SecurityException e) {
                if (DEBUG) {
                    Log.d("MediaRouteProviderProxy", this + ": Bind failed", e);
                }
            }
        }
    }

    public final RegisteredRouteController createRouteController(String str, String str2) {
        MediaRouteProviderDescriptor mediaRouteProviderDescriptor = this.mDescriptor;
        if (mediaRouteProviderDescriptor == null) {
            return null;
        }
        List<MediaRouteDescriptor> list = mediaRouteProviderDescriptor.mRoutes;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            if (list.get(i).getId().equals(str)) {
                RegisteredRouteController registeredRouteController = new RegisteredRouteController(str, str2);
                this.mControllerConnections.add(registeredRouteController);
                if (this.mConnectionReady) {
                    registeredRouteController.attachConnection(this.mActiveConnection);
                }
                updateBinding();
                return registeredRouteController;
            }
        }
        return null;
    }

    public final void disconnect() {
        if (this.mActiveConnection != null) {
            setDescriptor((MediaRouteProviderDescriptor) null);
            this.mConnectionReady = false;
            int size = this.mControllerConnections.size();
            for (int i = 0; i < size; i++) {
                this.mControllerConnections.get(i).detachConnection();
            }
            Connection connection = this.mActiveConnection;
            Objects.requireNonNull(connection);
            connection.sendRequest(2, 0, 0, (Bundle) null, (Bundle) null);
            ReceiveHandler receiveHandler = connection.mReceiveHandler;
            Objects.requireNonNull(receiveHandler);
            receiveHandler.mConnectionRef.clear();
            connection.mServiceMessenger.getBinder().unlinkToDeath(connection, 0);
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() {
                public final void run() {
                    Connection connection = Connection.this;
                    Objects.requireNonNull(connection);
                    int size = connection.mPendingCallbacks.size();
                    for (int i = 0; i < size; i++) {
                        connection.mPendingCallbacks.valueAt(i).onError((String) null, (Bundle) null);
                    }
                    connection.mPendingCallbacks.clear();
                }
            });
            this.mActiveConnection = null;
        }
    }

    public final void onConnectionDescriptorChanged(Connection connection, MediaRouteProviderDescriptor mediaRouteProviderDescriptor) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d("MediaRouteProviderProxy", this + ": Descriptor changed, descriptor=" + mediaRouteProviderDescriptor);
            }
            setDescriptor(mediaRouteProviderDescriptor);
        }
    }

    public final MediaRouteProvider.DynamicGroupRouteController onCreateDynamicGroupRouteController(String str) {
        if (str != null) {
            MediaRouteProviderDescriptor mediaRouteProviderDescriptor = this.mDescriptor;
            if (mediaRouteProviderDescriptor != null) {
                List<MediaRouteDescriptor> list = mediaRouteProviderDescriptor.mRoutes;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (list.get(i).getId().equals(str)) {
                        RegisteredDynamicController registeredDynamicController = new RegisteredDynamicController(str);
                        this.mControllerConnections.add(registeredDynamicController);
                        if (this.mConnectionReady) {
                            registeredDynamicController.attachConnection(this.mActiveConnection);
                        }
                        updateBinding();
                        return registeredDynamicController;
                    }
                }
            }
            return null;
        }
        throw new IllegalArgumentException("initialMemberRouteId cannot be null.");
    }

    public final void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest) {
        Bundle bundle;
        if (this.mConnectionReady) {
            Connection connection = this.mActiveConnection;
            Objects.requireNonNull(connection);
            int i = connection.mNextRequestId;
            connection.mNextRequestId = i + 1;
            if (mediaRouteDiscoveryRequest != null) {
                bundle = mediaRouteDiscoveryRequest.mBundle;
            } else {
                bundle = null;
            }
            connection.sendRequest(10, i, 0, bundle, (Bundle) null);
        }
        updateBinding();
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x007e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onServiceConnected(android.content.ComponentName r10, android.os.IBinder r11) {
        /*
            r9 = this;
            boolean r10 = DEBUG
            java.lang.String r0 = "MediaRouteProviderProxy"
            if (r10 == 0) goto L_0x001a
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            java.lang.String r1 = ": Connected"
            r10.append(r1)
            java.lang.String r10 = r10.toString()
            android.util.Log.d(r0, r10)
        L_0x001a:
            boolean r10 = r9.mBound
            if (r10 == 0) goto L_0x0092
            r9.disconnect()
            if (r11 == 0) goto L_0x0029
            android.os.Messenger r10 = new android.os.Messenger
            r10.<init>(r11)
            goto L_0x002a
        L_0x0029:
            r10 = 0
        L_0x002a:
            r11 = 0
            r1 = 1
            if (r10 == 0) goto L_0x0036
            android.os.IBinder r2 = r10.getBinder()     // Catch:{ NullPointerException -> 0x0036 }
            if (r2 == 0) goto L_0x0036
            r2 = r1
            goto L_0x0037
        L_0x0036:
            r2 = r11
        L_0x0037:
            if (r2 == 0) goto L_0x007e
            androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection r2 = new androidx.mediarouter.media.RegisteredMediaRouteProvider$Connection
            r2.<init>(r10)
            int r5 = r2.mNextRequestId
            int r10 = r5 + 1
            r2.mNextRequestId = r10
            r2.mPendingRegisterRequestId = r5
            r4 = 1
            r6 = 4
            r7 = 0
            r8 = 0
            r3 = r2
            boolean r10 = r3.sendRequest(r4, r5, r6, r7, r8)
            if (r10 != 0) goto L_0x0052
            goto L_0x0060
        L_0x0052:
            android.os.Messenger r10 = r2.mServiceMessenger     // Catch:{ RemoteException -> 0x005d }
            android.os.IBinder r10 = r10.getBinder()     // Catch:{ RemoteException -> 0x005d }
            r10.linkToDeath(r2, r11)     // Catch:{ RemoteException -> 0x005d }
            r11 = r1
            goto L_0x0060
        L_0x005d:
            r2.binderDied()
        L_0x0060:
            if (r11 == 0) goto L_0x0065
            r9.mActiveConnection = r2
            goto L_0x0092
        L_0x0065:
            boolean r10 = DEBUG
            if (r10 == 0) goto L_0x0092
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            java.lang.String r9 = ": Registration failed"
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.d(r0, r9)
            goto L_0x0092
        L_0x007e:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r9)
            java.lang.String r9 = ": Service returned invalid messenger binder"
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            android.util.Log.e(r0, r9)
        L_0x0092:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.RegisteredMediaRouteProvider.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        if (DEBUG) {
            Log.d("MediaRouteProviderProxy", this + ": Service disconnected");
        }
        disconnect();
    }

    public final void start() {
        if (!this.mStarted) {
            if (DEBUG) {
                Log.d("MediaRouteProviderProxy", this + ": Starting");
            }
            this.mStarted = true;
            updateBinding();
        }
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Service connection ");
        m.append(this.mComponentName.flattenToShortString());
        return m.toString();
    }

    public final void unbind() {
        if (this.mBound) {
            if (DEBUG) {
                Log.d("MediaRouteProviderProxy", this + ": Unbinding");
            }
            this.mBound = false;
            disconnect();
            try {
                this.mContext.unbindService(this);
            } catch (IllegalArgumentException e) {
                Log.e("MediaRouteProviderProxy", this + ": unbindService failed", e);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0010, code lost:
        if (r2.mControllerConnections.isEmpty() == false) goto L_0x0014;
     */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0016  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateBinding() {
        /*
            r2 = this;
            boolean r0 = r2.mStarted
            if (r0 == 0) goto L_0x0013
            androidx.mediarouter.media.MediaRouteDiscoveryRequest r0 = r2.mDiscoveryRequest
            r1 = 1
            if (r0 == 0) goto L_0x000a
            goto L_0x0014
        L_0x000a:
            java.util.ArrayList<androidx.mediarouter.media.RegisteredMediaRouteProvider$ControllerConnection> r0 = r2.mControllerConnections
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0013
            goto L_0x0014
        L_0x0013:
            r1 = 0
        L_0x0014:
            if (r1 == 0) goto L_0x001a
            r2.bind()
            goto L_0x001d
        L_0x001a:
            r2.unbind()
        L_0x001d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.media.RegisteredMediaRouteProvider.updateBinding():void");
    }

    public final MediaRouteProvider.RouteController onCreateRouteController(String str, String str2) {
        if (str == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        } else if (str2 != null) {
            return createRouteController(str, str2);
        } else {
            throw new IllegalArgumentException("routeGroupId cannot be null");
        }
    }
}
