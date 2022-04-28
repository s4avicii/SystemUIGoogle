package vendor.google.wireless_charger.V1_3;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.RemoteException;
import com.google.android.systemui.dreamliner.WirelessChargerImpl;
import com.google.android.systemui.reversecharging.ReverseWirelessCharger;
import java.util.ArrayList;
import vendor.google.wireless_charger.V1_0.DockInfo;
import vendor.google.wireless_charger.V1_0.KeyExchangeResponse;
import vendor.google.wireless_charger.V1_2.IWirelessChargerRtxStatusCallback;
import vendor.google.wireless_charger.V1_2.RtxStatusInfo;

public interface IWirelessCharger extends vendor.google.wireless_charger.V1_2.IWirelessCharger {

    public static final class Proxy implements IWirelessCharger {
        public IHwBinder mRemote;

        public final void challenge(byte b, ArrayList arrayList, WirelessChargerImpl.ChallengeCallbackWrapper challengeCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.0::IWirelessCharger");
            hwParcel.writeInt8(b);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                challengeCallbackWrapper.onValues(hwParcel2.readInt8(), hwParcel2.readInt8Vector());
            } finally {
                hwParcel2.release();
            }
        }

        public final void getFan(byte b, WirelessChargerImpl.GetFanSimpleInformationCallbackWrapper getFanSimpleInformationCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                byte readInt8 = hwParcel2.readInt8();
                FanInfo fanInfo = new FanInfo();
                HwBlob readBuffer = hwParcel2.readBuffer(4);
                fanInfo.fanMode = readBuffer.getInt8(0);
                fanInfo.currentRpm = readBuffer.getInt16(2);
                getFanSimpleInformationCallbackWrapper.onValues(readInt8, fanInfo);
            } finally {
                hwParcel2.release();
            }
        }

        public final void getFanInformation(byte b, WirelessChargerImpl.GetFanInformationCallbackWrapper getFanInformationCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                byte readInt8 = hwParcel2.readInt8();
                FanDetailedInfo fanDetailedInfo = new FanDetailedInfo();
                HwBlob readBuffer = hwParcel2.readBuffer(10);
                fanDetailedInfo.fanMode = readBuffer.getInt8(0);
                fanDetailedInfo.currentRpm = readBuffer.getInt16(2);
                fanDetailedInfo.minimumRpm = readBuffer.getInt16(4);
                fanDetailedInfo.maximumRpm = readBuffer.getInt16(6);
                fanDetailedInfo.type = readBuffer.getInt8(8);
                fanDetailedInfo.count = readBuffer.getInt8(9);
                getFanInformationCallbackWrapper.onValues(readInt8, fanDetailedInfo);
            } finally {
                hwParcel2.release();
            }
        }

        public final int getFanLevel() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        public final void getFeatures(long j, WirelessChargerImpl.GetFeaturesCallbackWrapper getFeaturesCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt64(j);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                getFeaturesCallbackWrapper.onValues(hwParcel2.readInt8(), hwParcel2.readInt64());
            } finally {
                hwParcel2.release();
            }
        }

        public final void getInformation(WirelessChargerImpl.GetInformationCallbackWrapper getInformationCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.0::IWirelessCharger");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                byte readInt8 = hwParcel2.readInt8();
                DockInfo dockInfo = new DockInfo();
                dockInfo.readFromParcel(hwParcel2);
                getInformationCallbackWrapper.onValues(readInt8, dockInfo);
            } finally {
                hwParcel2.release();
            }
        }

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

        public final void getWpcAuthCertificate(byte b, short s, short s2, WirelessChargerImpl.GetWpcAuthCertificateCallbackWrapper getWpcAuthCertificateCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt8(b);
            hwParcel.writeInt16(s);
            hwParcel.writeInt16(s2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                getWpcAuthCertificateCallbackWrapper.onValues(hwParcel2.readInt8(), hwParcel2.readInt8Vector());
            } finally {
                hwParcel2.release();
            }
        }

        public final void getWpcAuthChallengeResponse(byte b, ArrayList arrayList, WirelessChargerImpl.GetWpcAuthChallengeResponseCallbackWrapper getWpcAuthChallengeResponseCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt8(b);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                getWpcAuthChallengeResponseCallbackWrapper.onValues(hwParcel2.readInt8(), hwParcel2.readInt8(), hwParcel2.readInt8(), hwParcel2.readInt8(), hwParcel2.readInt8Vector(), hwParcel2.readInt8Vector());
            } finally {
                hwParcel2.release();
            }
        }

        public final void getWpcAuthDigests(byte b, WirelessChargerImpl.GetWpcAuthDigestsCallbackWrapper getWpcAuthDigestsCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                byte readInt8 = hwParcel2.readInt8();
                byte readInt82 = hwParcel2.readInt8();
                byte readInt83 = hwParcel2.readInt8();
                ArrayList arrayList = new ArrayList();
                HwBlob readBuffer = hwParcel2.readBuffer(16);
                int int32 = readBuffer.getInt32(8);
                long handle = readBuffer.handle();
                HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer((long) (int32 * 32), handle, 0, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array((long) (i * 32), bArr, 32);
                    arrayList.add(bArr);
                }
                getWpcAuthDigestsCallbackWrapper.onValues(readInt8, readInt82, readInt83, arrayList);
            } finally {
                hwParcel2.release();
            }
        }

        public final int hashCode() {
            return this.mRemote.hashCode();
        }

        public final void isDockPresent(WirelessChargerImpl wirelessChargerImpl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.0::IWirelessCharger");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                wirelessChargerImpl.onValues(hwParcel2.readBool(), hwParcel2.readInt8(), hwParcel2.readInt8(), hwParcel2.readBool(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
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

        public final void keyExchange(ArrayList arrayList, WirelessChargerImpl.KeyExchangeCallbackWrapper keyExchangeCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.0::IWirelessCharger");
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                byte readInt8 = hwParcel2.readInt8();
                KeyExchangeResponse keyExchangeResponse = new KeyExchangeResponse();
                HwBlob readBuffer = hwParcel2.readBuffer(24);
                keyExchangeResponse.dockId = readBuffer.getInt8(0);
                int int32 = readBuffer.getInt32(16);
                HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer((long) (int32 * 1), readBuffer.handle(), 8, true);
                keyExchangeResponse.dockPublicKey.clear();
                for (int i = 0; i < int32; i++) {
                    keyExchangeResponse.dockPublicKey.add(Byte.valueOf(readEmbeddedBuffer.getInt8((long) (i * 1))));
                }
                keyExchangeCallbackWrapper.onValues(readInt8, keyExchangeResponse);
            } finally {
                hwParcel2.release();
            }
        }

        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, 0);
        }

        public final byte registerCallback(WirelessChargerImpl.WirelessChargerInfoCallback wirelessChargerInfoCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.1::IWirelessCharger");
            hwParcel.writeStrongBinder(wirelessChargerInfoCallback);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt8();
            } finally {
                hwParcel2.release();
            }
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

        public final void setFan(byte b, byte b2, short s, WirelessChargerImpl.SetFanCallbackWrapper setFanCallbackWrapper) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt8(b);
            hwParcel.writeInt8(b2);
            hwParcel.writeInt16(s);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                byte readInt8 = hwParcel2.readInt8();
                FanInfo fanInfo = new FanInfo();
                HwBlob readBuffer = hwParcel2.readBuffer(4);
                fanInfo.fanMode = readBuffer.getInt8(0);
                fanInfo.currentRpm = readBuffer.getInt16(2);
                setFanCallbackWrapper.onValues(readInt8, fanInfo);
            } finally {
                hwParcel2.release();
            }
        }

        public final byte setFeatures(long j, long j2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.google.wireless_charger@1.3::IWirelessCharger");
            hwParcel.writeInt64(j);
            hwParcel.writeInt64(j2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 0);
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
                return "[class or subclass of vendor.google.wireless_charger@1.3::IWirelessCharger]@Proxy";
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

    void getFan(byte b, WirelessChargerImpl.GetFanSimpleInformationCallbackWrapper getFanSimpleInformationCallbackWrapper) throws RemoteException;

    void getFanInformation(byte b, WirelessChargerImpl.GetFanInformationCallbackWrapper getFanInformationCallbackWrapper) throws RemoteException;

    int getFanLevel() throws RemoteException;

    void getFeatures(long j, WirelessChargerImpl.GetFeaturesCallbackWrapper getFeaturesCallbackWrapper) throws RemoteException;

    void getWpcAuthCertificate(byte b, short s, short s2, WirelessChargerImpl.GetWpcAuthCertificateCallbackWrapper getWpcAuthCertificateCallbackWrapper) throws RemoteException;

    void getWpcAuthChallengeResponse(byte b, ArrayList arrayList, WirelessChargerImpl.GetWpcAuthChallengeResponseCallbackWrapper getWpcAuthChallengeResponseCallbackWrapper) throws RemoteException;

    void getWpcAuthDigests(byte b, WirelessChargerImpl.GetWpcAuthDigestsCallbackWrapper getWpcAuthDigestsCallbackWrapper) throws RemoteException;

    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    void setFan(byte b, byte b2, short s, WirelessChargerImpl.SetFanCallbackWrapper setFanCallbackWrapper) throws RemoteException;

    byte setFeatures(long j, long j2) throws RemoteException;
}
