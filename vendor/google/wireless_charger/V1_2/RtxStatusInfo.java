package vendor.google.wireless_charger.V1_2;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import java.util.Objects;

public final class RtxStatusInfo {
    public int acctype = 0;
    public boolean chg_s = false;
    public int iout = 0;
    public int level = 0;
    public byte mode = 0;
    public byte reason = 0;
    public int vout = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RtxStatusInfo.class) {
            return false;
        }
        RtxStatusInfo rtxStatusInfo = (RtxStatusInfo) obj;
        return this.mode == rtxStatusInfo.mode && this.acctype == rtxStatusInfo.acctype && this.chg_s == rtxStatusInfo.chg_s && this.vout == rtxStatusInfo.vout && this.iout == rtxStatusInfo.iout && this.level == rtxStatusInfo.level && this.reason == rtxStatusInfo.reason;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.mode))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.acctype))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.chg_s))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.vout))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.iout))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.level))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.reason)))});
    }

    public final void readFromParcel(HwParcel hwParcel) {
        HwBlob readBuffer = hwParcel.readBuffer(28);
        this.mode = readBuffer.getInt8(0);
        this.acctype = readBuffer.getInt32(4);
        this.chg_s = readBuffer.getBool(8);
        this.vout = readBuffer.getInt32(12);
        this.iout = readBuffer.getInt32(16);
        this.level = readBuffer.getInt32(20);
        this.reason = readBuffer.getInt8(24);
    }

    public final String toString() {
        String str;
        String str2;
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("{", ".mode = ");
        byte b = this.mode;
        if (b == 0) {
            str = "DISABLED";
        } else if (b == 1) {
            str = "ACTIVE";
        } else if (b == 2) {
            str = "AVAILABLE";
        } else {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("0x");
            m2.append(Integer.toHexString(Byte.toUnsignedInt(b)));
            str = m2.toString();
        }
        m.append(str);
        m.append(", .acctype = ");
        m.append(this.acctype);
        m.append(", .chg_s = ");
        m.append(this.chg_s);
        m.append(", .vout = ");
        m.append(this.vout);
        m.append(", .iout = ");
        m.append(this.iout);
        m.append(", .level = ");
        m.append(this.level);
        m.append(", .reason = ");
        byte b2 = this.reason;
        if (b2 == 0) {
            str2 = "NONE";
        } else if (b2 == 1) {
            str2 = "BATTLOW";
        } else if (b2 == 2) {
            str2 = "OVERHEAT";
        } else if (b2 == 3) {
            str2 = "TXCONFLICT";
        } else if (b2 == 4) {
            str2 = "OCP";
        } else if (b2 == 15) {
            str2 = "UNKNOWN";
        } else {
            StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("0x");
            m3.append(Integer.toHexString(Byte.toUnsignedInt(b2)));
            str2 = m3.toString();
        }
        return MotionController$$ExternalSyntheticOutline1.m8m(m, str2, "}");
    }
}
