package com.google.android.systemui.statusbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import kotlin.coroutines.Continuation;

public abstract class INotificationVoiceReplyService$Stub extends Binder implements IInterface {
    public final IBinder asBinder() {
        return this;
    }

    public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        INotificationVoiceReplyServiceCallbacks iNotificationVoiceReplyServiceCallbacks;
        int i3 = i;
        if (i3 < 1 || i3 > 16777215) {
            Parcel parcel3 = parcel;
        } else {
            parcel.enforceInterface("com.google.android.systemui.statusbar.INotificationVoiceReplyService");
        }
        if (i3 != 1598968902) {
            if (i3 == 1) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                if (readStrongBinder == null) {
                    iNotificationVoiceReplyServiceCallbacks = null;
                } else {
                    IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.systemui.statusbar.INotificationVoiceReplyServiceCallbacks");
                    if (queryLocalInterface == null || !(queryLocalInterface instanceof INotificationVoiceReplyServiceCallbacks)) {
                        iNotificationVoiceReplyServiceCallbacks = new INotificationVoiceReplyServiceCallbacks$Stub$Proxy(readStrongBinder);
                    } else {
                        iNotificationVoiceReplyServiceCallbacks = (INotificationVoiceReplyServiceCallbacks) queryLocalInterface;
                    }
                }
                parcel.enforceNoDataAvail();
                NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$1 = (NotificationVoiceReplyManagerService$binder$1) this;
                NotificationVoiceReplyManagerService.access$ensureCallerIsAgsa(notificationVoiceReplyManagerService$binder$1.this$0);
                NotificationVoiceReplyManagerService notificationVoiceReplyManagerService = notificationVoiceReplyManagerService$binder$1.this$0;
                NotificationVoiceReplyManagerService.access$serially(notificationVoiceReplyManagerService, new C2336xfdf5bb64(notificationVoiceReplyManagerService, notificationVoiceReplyManagerService$binder$1, iNotificationVoiceReplyServiceCallbacks, (Continuation<? super C2336xfdf5bb64>) null));
            } else if (i3 == 2) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$12 = (NotificationVoiceReplyManagerService$binder$1) this;
                NotificationVoiceReplyManagerService.access$ensureCallerIsAgsa(notificationVoiceReplyManagerService$binder$12.this$0);
                NotificationVoiceReplyManagerService notificationVoiceReplyManagerService2 = notificationVoiceReplyManagerService$binder$12.this$0;
                NotificationVoiceReplyManagerService.access$serially(notificationVoiceReplyManagerService2, new NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1(notificationVoiceReplyManagerService2, notificationVoiceReplyManagerService$binder$12, readInt, readString, (Continuation<? super NotificationVoiceReplyManagerService$binder$1$startVoiceReply$1>) null));
            } else if (i3 == 3) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$13 = (NotificationVoiceReplyManagerService$binder$1) this;
                NotificationVoiceReplyManagerService.access$ensureCallerIsAgsa(notificationVoiceReplyManagerService$binder$13.this$0);
                NotificationVoiceReplyManagerService notificationVoiceReplyManagerService3 = notificationVoiceReplyManagerService$binder$13.this$0;
                NotificationVoiceReplyManagerService.access$serially(notificationVoiceReplyManagerService3, new C2335x426a2397(notificationVoiceReplyManagerService3, notificationVoiceReplyManagerService$binder$13, readInt2, readInt3, (Continuation<? super C2335x426a2397>) null));
            } else if (i3 == 4) {
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$14 = (NotificationVoiceReplyManagerService$binder$1) this;
                NotificationVoiceReplyManagerService.access$ensureCallerIsAgsa(notificationVoiceReplyManagerService$binder$14.this$0);
                NotificationVoiceReplyManagerService notificationVoiceReplyManagerService4 = notificationVoiceReplyManagerService$binder$14.this$0;
                NotificationVoiceReplyManagerService.access$serially(notificationVoiceReplyManagerService4, new C2342x9e5e1de6(notificationVoiceReplyManagerService4, notificationVoiceReplyManagerService$binder$14, readInt4, (Continuation<? super C2342x9e5e1de6>) null));
            } else if (i3 != 5) {
                return super.onTransact(i, parcel, parcel2, i2);
            } else {
                NotificationVoiceReplyManagerService$binder$1 notificationVoiceReplyManagerService$binder$15 = (NotificationVoiceReplyManagerService$binder$1) this;
                NotificationVoiceReplyManagerService.access$ensureCallerIsAgsa(notificationVoiceReplyManagerService$binder$15.this$0);
                NotificationVoiceReplyManagerService notificationVoiceReplyManagerService5 = notificationVoiceReplyManagerService$binder$15.this$0;
                NotificationVoiceReplyManagerService.access$serially(notificationVoiceReplyManagerService5, new C2334xdd6b9453(notificationVoiceReplyManagerService5, (Continuation<? super C2334xdd6b9453>) null));
            }
            return true;
        }
        parcel2.writeString("com.google.android.systemui.statusbar.INotificationVoiceReplyService");
        return true;
    }

    public INotificationVoiceReplyService$Stub() {
        attachInterface(this, "com.google.android.systemui.statusbar.INotificationVoiceReplyService");
    }
}
