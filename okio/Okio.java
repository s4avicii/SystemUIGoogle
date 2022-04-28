package okio;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.os.Build;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import androidx.recyclerview.R$dimen;
import java.util.Locale;
import java.util.Objects;

public final class Okio {
    public static boolean contains(Object[] objArr, Object obj) {
        for (Object equals : objArr) {
            if (Objects.equals(equals, obj)) {
                return true;
            }
        }
        return false;
    }

    public static Object createTable(int i) {
        if (i < 2 || i > 1073741824 || Integer.highestOneBit(i) != i) {
            throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("must be power of 2 between 2^1 and 2^30: ", i));
        } else if (i <= 256) {
            return new byte[i];
        } else {
            if (i <= 65536) {
                return new short[i];
            }
            return new int[i];
        }
    }

    public static float interpolate(float[] fArr, float f, float f2) {
        if (f2 >= 1.0f) {
            return 1.0f;
        }
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        int min = Math.min((int) (((float) (fArr.length - 1)) * f2), fArr.length - 2);
        return MotionController$$ExternalSyntheticOutline0.m7m(fArr[min + 1], fArr[min], (f2 - (((float) min) * f)) / f, fArr[min]);
    }

    public static boolean isDateInputKeyboardMissingSeparatorCharacters() {
        String str = Build.MANUFACTURER;
        Locale locale = Locale.ENGLISH;
        if (str.toLowerCase(locale).equals("lge") || str.toLowerCase(locale).equals("samsung")) {
            return true;
        }
        return false;
    }

    public static int tableGet(Object obj, int i) {
        if (obj instanceof byte[]) {
            return ((byte[]) obj)[i] & 255;
        }
        if (obj instanceof short[]) {
            return ((short[]) obj)[i] & 65535;
        }
        return ((int[]) obj)[i];
    }

    public static void tableSet(Object obj, int i, int i2) {
        if (obj instanceof byte[]) {
            ((byte[]) obj)[i] = (byte) i2;
        } else if (obj instanceof short[]) {
            ((short[]) obj)[i] = (short) i2;
        } else {
            ((int[]) obj)[i] = i2;
        }
    }

    public static int remove(Object obj, Object obj2, int i, Object obj3, int[] iArr, Object[] objArr, Object[] objArr2) {
        int i2;
        int i3;
        int smearedHash = Okio__OkioKt.smearedHash(obj);
        int i4 = smearedHash & i;
        int tableGet = tableGet(obj3, i4);
        if (tableGet == 0) {
            return -1;
        }
        int i5 = ~i;
        int i6 = smearedHash & i5;
        int i7 = -1;
        while (true) {
            i2 = tableGet - 1;
            i3 = iArr[i2];
            if ((i3 & i5) != i6 || !R$dimen.equal(obj, objArr[i2]) || (objArr2 != null && !R$dimen.equal(obj2, objArr2[i2]))) {
                int i8 = i3 & i;
                if (i8 == 0) {
                    return -1;
                }
                int i9 = i8;
                i7 = i2;
                tableGet = i9;
            }
        }
        int i10 = i3 & i;
        if (i7 == -1) {
            tableSet(obj3, i4, i10);
        } else {
            iArr[i7] = (i10 & i) | (iArr[i7] & i5);
        }
        return i2;
    }
}
