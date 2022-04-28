package android.support.p000v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.support.p000v4.media.MediaMetadataCompat;
import java.util.ArrayList;

/* renamed from: android.support.v4.media.session.IMediaControllerCallback */
public interface IMediaControllerCallback extends IInterface {
    void onExtrasChanged(Bundle bundle) throws RemoteException;

    void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException;

    void onQueueChanged(ArrayList arrayList) throws RemoteException;

    void onQueueTitleChanged(CharSequence charSequence) throws RemoteException;

    void onSessionDestroyed() throws RemoteException;

    void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException;

    /* renamed from: android.support.v4.media.session.IMediaControllerCallback$Stub */
    public static abstract class Stub extends Binder implements IMediaControllerCallback {
        public final IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, "android.support.v4.media.session.IMediaControllerCallback");
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.support.v4.media.MediaMetadataCompat} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v11, resolved type: android.os.Bundle} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v14, resolved type: android.support.v4.media.session.ParcelableVolumeInfo} */
        /* JADX WARNING: type inference failed for: r0v1 */
        /* JADX WARNING: type inference failed for: r0v8, types: [java.lang.CharSequence] */
        /* JADX WARNING: type inference failed for: r0v17 */
        /* JADX WARNING: type inference failed for: r0v18 */
        /* JADX WARNING: type inference failed for: r0v19 */
        /* JADX WARNING: type inference failed for: r0v20 */
        /* JADX WARNING: type inference failed for: r0v21 */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean onTransact(int r4, android.os.Parcel r5, android.os.Parcel r6, int r7) throws android.os.RemoteException {
            /*
                r3 = this;
                r0 = 1598968902(0x5f4e5446, float:1.4867585E19)
                r1 = 1
                java.lang.String r2 = "android.support.v4.media.session.IMediaControllerCallback"
                if (r4 == r0) goto L_0x013f
                r0 = 0
                switch(r4) {
                    case 1: goto L_0x0119;
                    case 2: goto L_0x0112;
                    case 3: goto L_0x00ee;
                    case 4: goto L_0x00d8;
                    case 5: goto L_0x00cb;
                    case 6: goto L_0x00b5;
                    case 7: goto L_0x009f;
                    case 8: goto L_0x0089;
                    case 9: goto L_0x006c;
                    case 10: goto L_0x0065;
                    case 11: goto L_0x0043;
                    case 12: goto L_0x0026;
                    case 13: goto L_0x0011;
                    default: goto L_0x000c;
                }
            L_0x000c:
                boolean r3 = super.onTransact(r4, r5, r6, r7)
                return r3
            L_0x0011:
                r5.enforceInterface(r2)
                android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback.StubCompat) r3
                java.lang.ref.WeakReference<android.support.v4.media.session.MediaControllerCompat$Callback> r3 = r3.mCallback
                java.lang.Object r3 = r3.get()
                android.support.v4.media.session.MediaControllerCompat$Callback r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback) r3
                if (r3 == 0) goto L_0x0025
                r4 = 13
                r3.postToHandler(r4, r0, r0)
            L_0x0025:
                return r1
            L_0x0026:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback.StubCompat) r3
                java.lang.ref.WeakReference<android.support.v4.media.session.MediaControllerCompat$Callback> r3 = r3.mCallback
                java.lang.Object r3 = r3.get()
                android.support.v4.media.session.MediaControllerCompat$Callback r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback) r3
                if (r3 == 0) goto L_0x0042
                r5 = 12
                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                r3.postToHandler(r5, r4, r0)
            L_0x0042:
                return r1
            L_0x0043:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x004e
                r4 = r1
                goto L_0x004f
            L_0x004e:
                r4 = 0
            L_0x004f:
                android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback.StubCompat) r3
                java.lang.ref.WeakReference<android.support.v4.media.session.MediaControllerCompat$Callback> r3 = r3.mCallback
                java.lang.Object r3 = r3.get()
                android.support.v4.media.session.MediaControllerCompat$Callback r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback) r3
                if (r3 == 0) goto L_0x0064
                r5 = 11
                java.lang.Boolean r4 = java.lang.Boolean.valueOf(r4)
                r3.postToHandler(r5, r4, r0)
            L_0x0064:
                return r1
            L_0x0065:
                r5.enforceInterface(r2)
                r5.readInt()
                return r1
            L_0x006c:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback.StubCompat) r3
                java.lang.ref.WeakReference<android.support.v4.media.session.MediaControllerCompat$Callback> r3 = r3.mCallback
                java.lang.Object r3 = r3.get()
                android.support.v4.media.session.MediaControllerCompat$Callback r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback) r3
                if (r3 == 0) goto L_0x0088
                r5 = 9
                java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
                r3.postToHandler(r5, r4, r0)
            L_0x0088:
                return r1
            L_0x0089:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x009b
                android.os.Parcelable$Creator<android.support.v4.media.session.ParcelableVolumeInfo> r4 = android.support.p000v4.media.session.ParcelableVolumeInfo.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.support.v4.media.session.ParcelableVolumeInfo r0 = (android.support.p000v4.media.session.ParcelableVolumeInfo) r0
            L_0x009b:
                r3.onVolumeInfoChanged(r0)
                return r1
            L_0x009f:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00b1
                android.os.Parcelable$Creator r4 = android.os.Bundle.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x00b1:
                r3.onExtrasChanged(r0)
                return r1
            L_0x00b5:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00c7
                android.os.Parcelable$Creator r4 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                java.lang.CharSequence r0 = (java.lang.CharSequence) r0
            L_0x00c7:
                r3.onQueueTitleChanged(r0)
                return r1
            L_0x00cb:
                r5.enforceInterface(r2)
                android.os.Parcelable$Creator<android.support.v4.media.session.MediaSessionCompat$QueueItem> r4 = android.support.p000v4.media.session.MediaSessionCompat.QueueItem.CREATOR
                java.util.ArrayList r4 = r5.createTypedArrayList(r4)
                r3.onQueueChanged(r4)
                return r1
            L_0x00d8:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x00ea
                android.os.Parcelable$Creator<android.support.v4.media.MediaMetadataCompat> r4 = android.support.p000v4.media.MediaMetadataCompat.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                r0 = r4
                android.support.v4.media.MediaMetadataCompat r0 = (android.support.p000v4.media.MediaMetadataCompat) r0
            L_0x00ea:
                r3.onMetadataChanged(r0)
                return r1
            L_0x00ee:
                r5.enforceInterface(r2)
                int r4 = r5.readInt()
                if (r4 == 0) goto L_0x0100
                android.os.Parcelable$Creator<android.support.v4.media.session.PlaybackStateCompat> r4 = android.support.p000v4.media.session.PlaybackStateCompat.CREATOR
                java.lang.Object r4 = r4.createFromParcel(r5)
                android.support.v4.media.session.PlaybackStateCompat r4 = (android.support.p000v4.media.session.PlaybackStateCompat) r4
                goto L_0x0101
            L_0x0100:
                r4 = r0
            L_0x0101:
                android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback.StubCompat) r3
                java.lang.ref.WeakReference<android.support.v4.media.session.MediaControllerCompat$Callback> r3 = r3.mCallback
                java.lang.Object r3 = r3.get()
                android.support.v4.media.session.MediaControllerCompat$Callback r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback) r3
                if (r3 == 0) goto L_0x0111
                r5 = 2
                r3.postToHandler(r5, r4, r0)
            L_0x0111:
                return r1
            L_0x0112:
                r5.enforceInterface(r2)
                r3.onSessionDestroyed()
                return r1
            L_0x0119:
                r5.enforceInterface(r2)
                java.lang.String r4 = r5.readString()
                int r6 = r5.readInt()
                if (r6 == 0) goto L_0x012f
                android.os.Parcelable$Creator r6 = android.os.Bundle.CREATOR
                java.lang.Object r5 = r6.createFromParcel(r5)
                r0 = r5
                android.os.Bundle r0 = (android.os.Bundle) r0
            L_0x012f:
                android.support.v4.media.session.MediaControllerCompat$Callback$StubCompat r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback.StubCompat) r3
                java.lang.ref.WeakReference<android.support.v4.media.session.MediaControllerCompat$Callback> r3 = r3.mCallback
                java.lang.Object r3 = r3.get()
                android.support.v4.media.session.MediaControllerCompat$Callback r3 = (android.support.p000v4.media.session.MediaControllerCompat.Callback) r3
                if (r3 == 0) goto L_0x013e
                r3.postToHandler(r1, r4, r0)
            L_0x013e:
                return r1
            L_0x013f:
                r6.writeString(r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.p000v4.media.session.IMediaControllerCallback.Stub.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }
}
