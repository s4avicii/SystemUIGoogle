package vendor.google.wireless_charger.V1_3;

import android.hidl.base.V1_0.DebugInfo$$ExternalSyntheticOutline0;
import android.os.HidlSupport;
import androidx.leanback.R$layout;
import java.util.Objects;

public final class FanDetailedInfo {
    public byte count = 0;
    public short currentRpm = 0;
    public byte fanMode = 0;
    public short maximumRpm = 0;
    public short minimumRpm = 0;
    public byte type = 0;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != FanDetailedInfo.class) {
            return false;
        }
        FanDetailedInfo fanDetailedInfo = (FanDetailedInfo) obj;
        return this.fanMode == fanDetailedInfo.fanMode && this.currentRpm == fanDetailedInfo.currentRpm && this.minimumRpm == fanDetailedInfo.minimumRpm && this.maximumRpm == fanDetailedInfo.maximumRpm && this.type == fanDetailedInfo.type && this.count == fanDetailedInfo.count;
    }

    public final int hashCode() {
        return Objects.hash(new Object[]{Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.fanMode))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.currentRpm))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.minimumRpm))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.maximumRpm))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.type))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.count)))});
    }

    public final String toString() {
        StringBuilder m = DebugInfo$$ExternalSyntheticOutline0.m2m("{", ".fanMode = ");
        m.append(R$layout.toString(this.fanMode));
        m.append(", .currentRpm = ");
        m.append(this.currentRpm);
        m.append(", .minimumRpm = ");
        m.append(this.minimumRpm);
        m.append(", .maximumRpm = ");
        m.append(this.maximumRpm);
        m.append(", .type = ");
        m.append(this.type);
        m.append(", .count = ");
        m.append(this.count);
        m.append("}");
        return m.toString();
    }
}
