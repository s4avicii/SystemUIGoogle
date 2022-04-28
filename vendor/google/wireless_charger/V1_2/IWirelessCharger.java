package vendor.google.wireless_charger.V1_2;

import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.RemoteException;
import com.google.android.systemui.reversecharging.ReverseWirelessCharger;
import java.util.ArrayList;
import java.util.Iterator;
import vendor.google.wireless_charger.V1_2.IWirelessChargerRtxStatusCallback;

public interface IWirelessCharger extends vendor.google.wireless_charger.V1_1.IWirelessCharger {

    public static final class Proxy implements IWirelessCharger {
        public IHwBinder mRemote;

        public final void getRtxInformation(ReverseWirelessCharger.LocalRtxInformationCallback localRtxInformationCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.2::IWirelessCharger");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                hwParcel2.readInt8();
                RtxStatusInfo rtxStatusInfo = new RtxStatusInfo();
                rtxStatusInfo.readFromParcel(hwParcel2);
                localRtxInformationCallback.onValues(rtxStatusInfo);
            } finally {
                hwParcel2.release();
            }
        }

        public final int hashCode() {
            return this.mRemote.hashCode();
        }

        public final boolean isRtxSupported() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.2::IWirelessCharger");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, 0);
        }

        public final byte registerRtxCallback(IWirelessChargerRtxStatusCallback iWirelessChargerRtxStatusCallback) throws RemoteException {
            IHwBinder iHwBinder;
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.2::IWirelessCharger");
            if (iWirelessChargerRtxStatusCallback == null) {
                iHwBinder = null;
            } else {
                iHwBinder = (IWirelessChargerRtxStatusCallback.Stub) iWirelessChargerRtxStatusCallback;
            }
            hwParcel.writeStrongBinder(iHwBinder);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt8();
            } finally {
                hwParcel2.release();
            }
        }

        public final byte setRtxMode(boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.2::IWirelessCharger");
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt8();
            } finally {
                hwParcel2.release();
            }
        }

        public final String toString() {
            HwParcel hwParcel;
            try {
                StringBuilder sb = new StringBuilder();
                HwParcel hwParcel2 = new HwParcel();
                hwParcel2.writeInterfaceToken("android.hidl.base@1.0::IBase");
                hwParcel = new HwParcel();
                this.mRemote.transact(256136003, hwParcel2, hwParcel, 0);
                hwParcel.verifySuccess();
                hwParcel2.releaseTemporaryStorage();
                String readString = hwParcel.readString();
                hwParcel.release();
                sb.append(readString);
                sb.append("@Proxy");
                return sb.toString();
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.google.wireless_charger@1.2::IWirelessCharger]@Proxy";
            } catch (Throwable th) {
                hwParcel.release();
                throw th;
            }
        }

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = iHwBinder;
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final IHwBinder asBinder() {
            return this.mRemote;
        }
    }

    void getRtxInformation(ReverseWirelessCharger.LocalRtxInformationCallback localRtxInformationCallback) throws RemoteException;

    boolean isRtxSupported() throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    byte registerRtxCallback(IWirelessChargerRtxStatusCallback iWirelessChargerRtxStatusCallback) throws RemoteException;

    byte setRtxMode(boolean z) throws RemoteException;

    @Deprecated
    static IWirelessCharger getService() throws RemoteException {
        HwParcel hwParcel;
        IHwBinder service = HwBinder.getService("vendor.google.wireless_charger@1.2::IWirelessCharger", "default");
        if (service == null) {
            return null;
        }
        IWirelessCharger queryLocalInterface = service.queryLocalInterface("vendor.google.wireless_charger@1.2::IWirelessCharger");
        if (queryLocalInterface != null && (queryLocalInterface instanceof IWirelessCharger)) {
            return queryLocalInterface;
        }
        Proxy proxy = new Proxy(service);
        try {
            HwParcel hwParcel2 = new HwParcel();
            hwParcel2.writeInterfaceToken("android.hidl.base@1.0::IBase");
            hwParcel = new HwParcel();
            proxy.mRemote.transact(256067662, hwParcel2, hwParcel, 0);
            hwParcel.verifySuccess();
            hwParcel2.releaseTemporaryStorage();
            ArrayList readStringVector = hwParcel.readStringVector();
            hwParcel.release();
            Iterator it = readStringVector.iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals("vendor.google.wireless_charger@1.2::IWirelessCharger")) {
                    return proxy;
                }
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        } catch (Throwable th) {
            hwParcel.release();
            throw th;
        }
    }
}
