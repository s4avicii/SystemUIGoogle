package androidx.media;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import java.util.Arrays;
import java.util.Objects;

public class AudioAttributesImplBase implements AudioAttributesImpl {
    public int mContentType = 0;
    public int mFlags = 0;
    public int mLegacyStream = -1;
    public int mUsage = 0;

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.mContentType), Integer.valueOf(this.mFlags), Integer.valueOf(this.mUsage), Integer.valueOf(this.mLegacyStream)});
    }

    public final boolean equals(Object obj) {
        int i;
        if (!(obj instanceof AudioAttributesImplBase)) {
            return false;
        }
        AudioAttributesImplBase audioAttributesImplBase = (AudioAttributesImplBase) obj;
        int i2 = this.mContentType;
        Objects.requireNonNull(audioAttributesImplBase);
        if (i2 != audioAttributesImplBase.mContentType) {
            return false;
        }
        int i3 = this.mFlags;
        int i4 = audioAttributesImplBase.mFlags;
        int i5 = audioAttributesImplBase.mLegacyStream;
        int i6 = 4;
        if (i5 != -1) {
            i6 = i5;
        } else {
            int i7 = audioAttributesImplBase.mUsage;
            int i8 = AudioAttributesCompat.$r8$clinit;
            if ((i4 & 1) == 1) {
                i6 = 7;
            } else if ((i4 & 4) == 4) {
                i6 = 6;
            } else {
                switch (i7) {
                    case 2:
                        i6 = 0;
                        break;
                    case 3:
                        i = 8;
                        break;
                    case 4:
                        break;
                    case 5:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                        i = 5;
                        break;
                    case FalsingManager.VERSION /*6*/:
                        i = 2;
                        break;
                    case QSTileImpl.C1034H.STALE /*11*/:
                        i = 10;
                        break;
                    case C0961QS.VERSION /*13*/:
                        i6 = 1;
                        break;
                    default:
                        i6 = 3;
                        break;
                }
                i6 = i;
            }
        }
        if (i6 == 6) {
            i4 |= 4;
        } else if (i6 == 7) {
            i4 |= 1;
        }
        if (i3 == (i4 & 273) && this.mUsage == audioAttributesImplBase.mUsage && this.mLegacyStream == i5) {
            return true;
        }
        return false;
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (this.mLegacyStream != -1) {
            sb.append(" stream=");
            sb.append(this.mLegacyStream);
            sb.append(" derived");
        }
        sb.append(" usage=");
        int i = this.mUsage;
        int i2 = AudioAttributesCompat.$r8$clinit;
        switch (i) {
            case 0:
                str = "USAGE_UNKNOWN";
                break;
            case 1:
                str = "USAGE_MEDIA";
                break;
            case 2:
                str = "USAGE_VOICE_COMMUNICATION";
                break;
            case 3:
                str = "USAGE_VOICE_COMMUNICATION_SIGNALLING";
                break;
            case 4:
                str = "USAGE_ALARM";
                break;
            case 5:
                str = "USAGE_NOTIFICATION";
                break;
            case FalsingManager.VERSION /*6*/:
                str = "USAGE_NOTIFICATION_RINGTONE";
                break;
            case 7:
                str = "USAGE_NOTIFICATION_COMMUNICATION_REQUEST";
                break;
            case 8:
                str = "USAGE_NOTIFICATION_COMMUNICATION_INSTANT";
                break;
            case 9:
                str = "USAGE_NOTIFICATION_COMMUNICATION_DELAYED";
                break;
            case 10:
                str = "USAGE_NOTIFICATION_EVENT";
                break;
            case QSTileImpl.C1034H.STALE /*11*/:
                str = "USAGE_ASSISTANCE_ACCESSIBILITY";
                break;
            case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
                str = "USAGE_ASSISTANCE_NAVIGATION_GUIDANCE";
                break;
            case C0961QS.VERSION /*13*/:
                str = "USAGE_ASSISTANCE_SONIFICATION";
                break;
            case 14:
                str = "USAGE_GAME";
                break;
            case 16:
                str = "USAGE_ASSISTANT";
                break;
            default:
                str = VendorAtomValue$$ExternalSyntheticOutline0.m0m("unknown usage ", i);
                break;
        }
        sb.append(str);
        sb.append(" content=");
        sb.append(this.mContentType);
        sb.append(" flags=0x");
        sb.append(Integer.toHexString(this.mFlags).toUpperCase());
        return sb.toString();
    }
}
