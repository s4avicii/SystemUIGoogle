package androidx.slice;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import androidx.core.util.Pair;
import androidx.versionedparcelable.CustomVersionedParcelable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import okio.Okio;

public final class SliceItem extends CustomVersionedParcelable {
    public String mFormat;
    public String[] mHints;
    public SliceItemHolder mHolder;
    public Object mObj;
    public CharSequence mSanitizedText;
    public String mSubType;

    public interface ActionHandler {
        void onAction();
    }

    public SliceItem(Object obj, String str, String str2, String[] strArr) {
        this.mHints = strArr;
        this.mFormat = str;
        this.mSubType = str2;
        this.mObj = obj;
    }

    public final boolean hasAnyHints(String... strArr) {
        for (String contains : strArr) {
            if (Okio.contains(this.mHints, contains)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01d4, code lost:
        if (r14.equals("image") == false) goto L_0x01fb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String toString(java.lang.String r15) {
        /*
            r14 = this;
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r15)
            java.lang.String r1 = r14.mFormat
            r0.append(r1)
            java.lang.String r1 = r14.mSubType
            if (r1 == 0) goto L_0x001c
            r1 = 60
            r0.append(r1)
            java.lang.String r1 = r14.mSubType
            r0.append(r1)
            r1 = 62
            r0.append(r1)
        L_0x001c:
            r1 = 32
            r0.append(r1)
            java.lang.String[] r2 = r14.mHints
            int r3 = r2.length
            if (r3 <= 0) goto L_0x002c
            androidx.slice.Slice.appendHints(r0, r2)
            r0.append(r1)
        L_0x002c:
            java.lang.String r1 = "  "
            java.lang.String r1 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r15, r1)
            java.lang.String r2 = r14.mFormat
            java.util.Objects.requireNonNull(r2)
            int r3 = r2.hashCode()
            java.lang.String r4 = "slice"
            java.lang.String r5 = "image"
            java.lang.String r6 = "text"
            java.lang.String r7 = "long"
            java.lang.String r8 = "int"
            java.lang.String r9 = "action"
            r10 = 4
            r11 = 0
            switch(r3) {
                case -1422950858: goto L_0x007c;
                case 104431: goto L_0x0073;
                case 3327612: goto L_0x006a;
                case 3556653: goto L_0x0061;
                case 100313435: goto L_0x0058;
                case 109526418: goto L_0x004f;
                default: goto L_0x004e;
            }
        L_0x004e:
            goto L_0x0085
        L_0x004f:
            boolean r2 = r2.equals(r4)
            if (r2 != 0) goto L_0x0056
            goto L_0x0085
        L_0x0056:
            r2 = 5
            goto L_0x0086
        L_0x0058:
            boolean r2 = r2.equals(r5)
            if (r2 != 0) goto L_0x005f
            goto L_0x0085
        L_0x005f:
            r2 = r10
            goto L_0x0086
        L_0x0061:
            boolean r2 = r2.equals(r6)
            if (r2 != 0) goto L_0x0068
            goto L_0x0085
        L_0x0068:
            r2 = 3
            goto L_0x0086
        L_0x006a:
            boolean r2 = r2.equals(r7)
            if (r2 != 0) goto L_0x0071
            goto L_0x0085
        L_0x0071:
            r2 = 2
            goto L_0x0086
        L_0x0073:
            boolean r2 = r2.equals(r8)
            if (r2 != 0) goto L_0x007a
            goto L_0x0085
        L_0x007a:
            r2 = 1
            goto L_0x0086
        L_0x007c:
            boolean r2 = r2.equals(r9)
            if (r2 != 0) goto L_0x0083
            goto L_0x0085
        L_0x0083:
            r2 = r11
            goto L_0x0086
        L_0x0085:
            r2 = -1
        L_0x0086:
            r3 = 125(0x7d, float:1.75E-43)
            r12 = 10
            java.lang.String r13 = "{\n"
            switch(r2) {
                case 0: goto L_0x0190;
                case 1: goto L_0x0113;
                case 2: goto L_0x00d1;
                case 3: goto L_0x00c0;
                case 4: goto L_0x00b7;
                case 5: goto L_0x009e;
                default: goto L_0x0090;
            }
        L_0x0090:
            java.lang.String r14 = r14.mFormat
            java.util.Objects.requireNonNull(r14)
            int r15 = r14.hashCode()
            switch(r15) {
                case -1422950858: goto L_0x01f2;
                case 104431: goto L_0x01e9;
                case 3327612: goto L_0x01e0;
                case 3556653: goto L_0x01d7;
                case 100313435: goto L_0x01d0;
                case 100358090: goto L_0x01c5;
                case 109526418: goto L_0x01bc;
                default: goto L_0x009c;
            }
        L_0x009c:
            goto L_0x01fb
        L_0x009e:
            r0.append(r13)
            androidx.slice.Slice r14 = r14.getSlice()
            java.lang.String r14 = r14.toString(r1)
            r0.append(r14)
            r0.append(r12)
            r0.append(r15)
            r0.append(r3)
            goto L_0x021d
        L_0x00b7:
            java.lang.Object r14 = r14.mObj
            androidx.core.graphics.drawable.IconCompat r14 = (androidx.core.graphics.drawable.IconCompat) r14
            r0.append(r14)
            goto L_0x021d
        L_0x00c0:
            r15 = 34
            r0.append(r15)
            java.lang.Object r14 = r14.mObj
            java.lang.CharSequence r14 = (java.lang.CharSequence) r14
            r0.append(r14)
            r0.append(r15)
            goto L_0x021d
        L_0x00d1:
            java.lang.String r15 = r14.mSubType
            java.lang.String r1 = "millis"
            boolean r15 = r1.equals(r15)
            if (r15 == 0) goto L_0x0105
            long r1 = r14.getLong()
            r3 = -1
            int r15 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r15 != 0) goto L_0x00ec
            java.lang.String r14 = "INFINITY"
            r0.append(r14)
            goto L_0x021d
        L_0x00ec:
            long r1 = r14.getLong()
            java.util.Calendar r14 = java.util.Calendar.getInstance()
            long r3 = r14.getTimeInMillis()
            r5 = 1000(0x3e8, double:4.94E-321)
            r7 = 262144(0x40000, float:3.67342E-40)
            java.lang.CharSequence r14 = android.text.format.DateUtils.getRelativeTimeSpanString(r1, r3, r5, r7)
            r0.append(r14)
            goto L_0x021d
        L_0x0105:
            long r14 = r14.getLong()
            r0.append(r14)
            r14 = 76
            r0.append(r14)
            goto L_0x021d
        L_0x0113:
            java.lang.String r15 = r14.mSubType
            java.lang.String r1 = "color"
            boolean r15 = r1.equals(r15)
            if (r15 == 0) goto L_0x0159
            int r14 = r14.getInt()
            java.lang.Object[] r15 = new java.lang.Object[r10]
            int r1 = android.graphics.Color.alpha(r14)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r15[r11] = r1
            int r1 = android.graphics.Color.red(r14)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r2 = 1
            r15[r2] = r1
            int r1 = android.graphics.Color.green(r14)
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r2 = 2
            r15[r2] = r1
            int r14 = android.graphics.Color.blue(r14)
            java.lang.Integer r14 = java.lang.Integer.valueOf(r14)
            r1 = 3
            r15[r1] = r14
            java.lang.String r14 = "a=0x%02x r=0x%02x g=0x%02x b=0x%02x"
            java.lang.String r14 = java.lang.String.format(r14, r15)
            r0.append(r14)
            goto L_0x021d
        L_0x0159:
            java.lang.String r15 = r14.mSubType
            java.lang.String r1 = "layout_direction"
            boolean r15 = r1.equals(r15)
            if (r15 == 0) goto L_0x0187
            int r14 = r14.getInt()
            if (r14 == 0) goto L_0x0180
            r15 = 1
            if (r14 == r15) goto L_0x017d
            r15 = 2
            if (r14 == r15) goto L_0x017a
            r15 = 3
            if (r14 == r15) goto L_0x0177
            java.lang.String r14 = java.lang.Integer.toString(r14)
            goto L_0x0182
        L_0x0177:
            java.lang.String r14 = "LOCALE"
            goto L_0x0182
        L_0x017a:
            java.lang.String r14 = "INHERIT"
            goto L_0x0182
        L_0x017d:
            java.lang.String r14 = "RTL"
            goto L_0x0182
        L_0x0180:
            java.lang.String r14 = "LTR"
        L_0x0182:
            r0.append(r14)
            goto L_0x021d
        L_0x0187:
            int r14 = r14.getInt()
            r0.append(r14)
            goto L_0x021d
        L_0x0190:
            java.lang.Object r2 = r14.mObj
            androidx.core.util.Pair r2 = (androidx.core.util.Pair) r2
            F r2 = r2.first
            r4 = 91
            r0.append(r4)
            r0.append(r2)
            java.lang.String r2 = "] "
            r0.append(r2)
            r0.append(r13)
            androidx.slice.Slice r14 = r14.getSlice()
            java.lang.String r14 = r14.toString(r1)
            r0.append(r14)
            r0.append(r12)
            r0.append(r15)
            r0.append(r3)
            goto L_0x021d
        L_0x01bc:
            boolean r15 = r14.equals(r4)
            if (r15 != 0) goto L_0x01c3
            goto L_0x01fb
        L_0x01c3:
            r10 = 6
            goto L_0x01fc
        L_0x01c5:
            java.lang.String r15 = "input"
            boolean r15 = r14.equals(r15)
            if (r15 != 0) goto L_0x01ce
            goto L_0x01fb
        L_0x01ce:
            r10 = 5
            goto L_0x01fc
        L_0x01d0:
            boolean r15 = r14.equals(r5)
            if (r15 != 0) goto L_0x01fc
            goto L_0x01fb
        L_0x01d7:
            boolean r15 = r14.equals(r6)
            if (r15 != 0) goto L_0x01de
            goto L_0x01fb
        L_0x01de:
            r10 = 3
            goto L_0x01fc
        L_0x01e0:
            boolean r15 = r14.equals(r7)
            if (r15 != 0) goto L_0x01e7
            goto L_0x01fb
        L_0x01e7:
            r10 = 2
            goto L_0x01fc
        L_0x01e9:
            boolean r15 = r14.equals(r8)
            if (r15 != 0) goto L_0x01f0
            goto L_0x01fb
        L_0x01f0:
            r10 = 1
            goto L_0x01fc
        L_0x01f2:
            boolean r15 = r14.equals(r9)
            if (r15 != 0) goto L_0x01f9
            goto L_0x01fb
        L_0x01f9:
            r10 = r11
            goto L_0x01fc
        L_0x01fb:
            r10 = -1
        L_0x01fc:
            switch(r10) {
                case 0: goto L_0x0218;
                case 1: goto L_0x0215;
                case 2: goto L_0x0212;
                case 3: goto L_0x020f;
                case 4: goto L_0x020c;
                case 5: goto L_0x0209;
                case 6: goto L_0x0206;
                default: goto L_0x01ff;
            }
        L_0x01ff:
            java.lang.String r15 = "Unrecognized format: "
            java.lang.String r14 = androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0.m4m(r15, r14)
            goto L_0x021a
        L_0x0206:
            java.lang.String r14 = "Slice"
            goto L_0x021a
        L_0x0209:
            java.lang.String r14 = "RemoteInput"
            goto L_0x021a
        L_0x020c:
            java.lang.String r14 = "Image"
            goto L_0x021a
        L_0x020f:
            java.lang.String r14 = "Text"
            goto L_0x021a
        L_0x0212:
            java.lang.String r14 = "Long"
            goto L_0x021a
        L_0x0215:
            java.lang.String r14 = "Int"
            goto L_0x021a
        L_0x0218:
            java.lang.String r14 = "Action"
        L_0x021a:
            r0.append(r14)
        L_0x021d:
            java.lang.String r14 = "\n"
            r0.append(r14)
            java.lang.String r14 = r0.toString()
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.slice.SliceItem.toString(java.lang.String):java.lang.String");
    }

    public final void addHint() {
        Object[] objArr;
        Class<String> cls = String.class;
        String[] strArr = this.mHints;
        int i = 0;
        if (strArr != null) {
            int length = strArr.length;
            objArr = (Object[]) Array.newInstance(cls, length + 1);
            System.arraycopy(strArr, 0, objArr, 0, length);
            i = length;
        } else {
            objArr = (Object[]) Array.newInstance(cls, 1);
        }
        objArr[i] = "partial";
        this.mHints = (String[]) objArr;
    }

    public final boolean fireActionInternal(Context context, Intent intent) throws PendingIntent.CanceledException {
        F f = ((Pair) this.mObj).first;
        if (f instanceof PendingIntent) {
            ((PendingIntent) f).send(context, 0, intent, (PendingIntent.OnFinished) null, (Handler) null);
            return false;
        }
        ((ActionHandler) f).onAction();
        return true;
    }

    public final PendingIntent getAction() {
        F f = ((Pair) this.mObj).first;
        if (f instanceof PendingIntent) {
            return (PendingIntent) f;
        }
        return null;
    }

    public final List<String> getHints() {
        return Arrays.asList(this.mHints);
    }

    public final int getInt() {
        return ((Integer) this.mObj).intValue();
    }

    public final long getLong() {
        return ((Long) this.mObj).longValue();
    }

    public final CharSequence getSanitizedText() {
        if (this.mSanitizedText == null) {
            CharSequence charSequence = (CharSequence) this.mObj;
            if (charSequence instanceof Spannable) {
                fixSpannableText((Spannable) charSequence);
            } else if (charSequence instanceof Spanned) {
                Spanned spanned = (Spanned) charSequence;
                boolean z = false;
                Object[] spans = spanned.getSpans(0, spanned.length(), Object.class);
                int length = spans.length;
                int i = 0;
                while (true) {
                    boolean z2 = true;
                    if (i >= length) {
                        z = true;
                        break;
                    }
                    Object obj = spans[i];
                    if (!(obj instanceof AlignmentSpan) && !(obj instanceof ForegroundColorSpan) && !(obj instanceof RelativeSizeSpan) && !(obj instanceof StyleSpan)) {
                        z2 = false;
                    }
                    if (!z2) {
                        break;
                    }
                    i++;
                }
                if (!z) {
                    SpannableString spannableString = new SpannableString(charSequence);
                    fixSpannableText(spannableString);
                    charSequence = spannableString;
                }
            }
            this.mSanitizedText = charSequence;
        }
        return this.mSanitizedText;
    }

    public final Slice getSlice() {
        if ("action".equals(this.mFormat)) {
            return (Slice) ((Pair) this.mObj).second;
        }
        return (Slice) this.mObj;
    }

    public final boolean hasHint(String str) {
        return Okio.contains(this.mHints, str);
    }

    public static void fixSpannableText(Spannable spannable) {
        boolean z;
        Object obj;
        for (Object obj2 : spannable.getSpans(0, spannable.length(), Object.class)) {
            if ((obj2 instanceof AlignmentSpan) || (obj2 instanceof ForegroundColorSpan) || (obj2 instanceof RelativeSizeSpan) || (obj2 instanceof StyleSpan)) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                obj = obj2;
            } else {
                obj = null;
            }
            if (obj != obj2) {
                if (obj != null) {
                    spannable.setSpan(obj, spannable.getSpanStart(obj2), spannable.getSpanEnd(obj2), spannable.getSpanFlags(obj2));
                }
                spannable.removeSpan(obj2);
            }
        }
    }

    public SliceItem(Object obj, String str, String str2, List<String> list) {
        this(obj, str, str2, (String[]) list.toArray(new String[list.size()]));
    }

    public SliceItem() {
        this.mHints = Slice.NO_HINTS;
        this.mFormat = "text";
        this.mSubType = null;
    }

    public SliceItem(PendingIntent pendingIntent, Slice slice, String str, String[] strArr) {
        this((Object) new Pair(pendingIntent, slice), "action", str, strArr);
    }

    public final String toString() {
        return toString("");
    }
}
