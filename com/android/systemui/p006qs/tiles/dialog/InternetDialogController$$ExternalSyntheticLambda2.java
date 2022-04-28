package com.android.systemui.p006qs.tiles.dialog;

import android.content.Context;
import java.util.Set;
import java.util.function.Function;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialogController$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ Set f$0;
    public final /* synthetic */ Context f$1;

    public /* synthetic */ InternetDialogController$$ExternalSyntheticLambda2(Set set, Context context) {
        this.f$0 = set;
        this.f$1 = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0073  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x007b  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0080  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object apply(java.lang.Object r5) {
        /*
            r4 = this;
            java.util.Set r0 = r4.f$0
            android.content.Context r4 = r4.f$1
            com.android.systemui.qs.tiles.dialog.InternetDialogController$1DisplayInfo r5 = (com.android.systemui.p006qs.tiles.dialog.InternetDialogController.AnonymousClass1DisplayInfo) r5
            java.lang.CharSequence r1 = r5.originalName
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x0099
            android.telephony.SubscriptionInfo r0 = r5.subscriptionInfo
            if (r0 == 0) goto L_0x0055
            int r0 = r0.getSubscriptionId()
            java.lang.String r1 = android.os.Build.VERSION.CODENAME
            java.lang.String r2 = "REL"
            boolean r2 = r2.equals(r1)
            r3 = 0
            if (r2 == 0) goto L_0x0022
            goto L_0x002b
        L_0x0022:
            java.lang.String r2 = "T"
            int r1 = r1.compareTo(r2)
            if (r1 < 0) goto L_0x002b
            r3 = 1
        L_0x002b:
            if (r3 == 0) goto L_0x003a
            java.lang.Class<android.telephony.SubscriptionManager> r1 = android.telephony.SubscriptionManager.class
            java.lang.Object r4 = r4.getSystemService(r1)
            android.telephony.SubscriptionManager r4 = (android.telephony.SubscriptionManager) r4
            java.lang.String r4 = r4.getPhoneNumber(r0)
            goto L_0x004a
        L_0x003a:
            java.lang.Class<android.telephony.TelephonyManager> r1 = android.telephony.TelephonyManager.class
            java.lang.Object r4 = r4.getSystemService(r1)
            android.telephony.TelephonyManager r4 = (android.telephony.TelephonyManager) r4
            android.telephony.TelephonyManager r4 = r4.createForSubscriptionId(r0)
            java.lang.String r4 = r4.getLine1Number()
        L_0x004a:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 != 0) goto L_0x0055
            java.lang.String r4 = android.telephony.PhoneNumberUtils.formatNumber(r4)
            goto L_0x0056
        L_0x0055:
            r4 = 0
        L_0x0056:
            android.text.BidiFormatter r0 = android.text.BidiFormatter.getInstance()
            android.text.TextDirectionHeuristic r1 = android.text.TextDirectionHeuristics.LTR
            java.lang.String r4 = r0.unicodeWrap(r4, r1)
            if (r4 == 0) goto L_0x0073
            int r0 = r4.length()
            r1 = 4
            if (r0 <= r1) goto L_0x0075
            int r0 = r4.length()
            int r0 = r0 - r1
            java.lang.String r4 = r4.substring(r0)
            goto L_0x0075
        L_0x0073:
            java.lang.String r4 = ""
        L_0x0075:
            boolean r0 = android.text.TextUtils.isEmpty(r4)
            if (r0 == 0) goto L_0x0080
            java.lang.CharSequence r4 = r5.originalName
            r5.uniqueName = r4
            goto L_0x009d
        L_0x0080:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.CharSequence r1 = r5.originalName
            r0.append(r1)
            java.lang.String r1 = " "
            r0.append(r1)
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r5.uniqueName = r4
            goto L_0x009d
        L_0x0099:
            java.lang.CharSequence r4 = r5.originalName
            r5.uniqueName = r4
        L_0x009d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.p006qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object");
    }
}
