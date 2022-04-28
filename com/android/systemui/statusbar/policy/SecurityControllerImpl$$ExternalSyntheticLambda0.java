package com.android.systemui.statusbar.policy;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SecurityControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SecurityControllerImpl f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ SecurityControllerImpl$$ExternalSyntheticLambda0(SecurityControllerImpl securityControllerImpl, int i) {
        this.f$0 = securityControllerImpl;
        this.f$1 = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r8 = this;
            com.android.systemui.statusbar.policy.SecurityControllerImpl r0 = r8.f$0
            int r8 = r8.f$1
            java.util.Objects.requireNonNull(r0)
            java.lang.String r1 = "Refreshing CA Certs "
            java.lang.String r2 = "SecurityController"
            r3 = 0
            android.content.Context r4 = r0.mContext     // Catch:{ RemoteException | AssertionError | InterruptedException -> 0x006a, all -> 0x0068 }
            android.os.UserHandle r5 = android.os.UserHandle.of(r8)     // Catch:{ RemoteException | AssertionError | InterruptedException -> 0x006a, all -> 0x0068 }
            android.security.KeyChain$KeyChainConnection r4 = android.security.KeyChain.bindAsUser(r4, r5)     // Catch:{ RemoteException | AssertionError | InterruptedException -> 0x006a, all -> 0x0068 }
            android.security.IKeyChainService r5 = r4.getService()     // Catch:{ all -> 0x005c }
            android.content.pm.StringParceledListSlice r5 = r5.getUserCaAliases()     // Catch:{ all -> 0x005c }
            java.util.List r5 = r5.getList()     // Catch:{ all -> 0x005c }
            boolean r5 = r5.isEmpty()     // Catch:{ all -> 0x005c }
            if (r5 != 0) goto L_0x002a
            r5 = 1
            goto L_0x002b
        L_0x002a:
            r5 = 0
        L_0x002b:
            android.util.Pair r6 = new android.util.Pair     // Catch:{ all -> 0x005c }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x005c }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x005c }
            r6.<init>(r7, r5)     // Catch:{ all -> 0x005c }
            r4.close()     // Catch:{ RemoteException | AssertionError | InterruptedException -> 0x005a }
            boolean r8 = com.android.systemui.statusbar.policy.SecurityControllerImpl.DEBUG
            if (r8 == 0) goto L_0x0051
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r1)
            r8.append(r6)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r2, r8)
        L_0x0051:
            java.lang.Object r8 = r6.second
            if (r8 == 0) goto L_0x00a2
            android.util.ArrayMap<java.lang.Integer, java.lang.Boolean> r1 = r0.mHasCACerts
            java.lang.Object r2 = r6.first
            goto L_0x0098
        L_0x005a:
            r4 = move-exception
            goto L_0x006c
        L_0x005c:
            r5 = move-exception
            if (r4 == 0) goto L_0x0067
            r4.close()     // Catch:{ all -> 0x0063 }
            goto L_0x0067
        L_0x0063:
            r4 = move-exception
            r5.addSuppressed(r4)     // Catch:{ RemoteException | AssertionError | InterruptedException -> 0x006a, all -> 0x0068 }
        L_0x0067:
            throw r5     // Catch:{ RemoteException | AssertionError | InterruptedException -> 0x006a, all -> 0x0068 }
        L_0x0068:
            r8 = move-exception
            goto L_0x00a5
        L_0x006a:
            r4 = move-exception
            r6 = r3
        L_0x006c:
            java.lang.String r5 = "failed to get CA certs"
            android.util.Log.i(r2, r5, r4)     // Catch:{ all -> 0x00a3 }
            android.util.Pair r4 = new android.util.Pair     // Catch:{ all -> 0x00a3 }
            java.lang.Integer r8 = java.lang.Integer.valueOf(r8)     // Catch:{ all -> 0x00a3 }
            r4.<init>(r8, r3)     // Catch:{ all -> 0x00a3 }
            boolean r8 = com.android.systemui.statusbar.policy.SecurityControllerImpl.DEBUG
            if (r8 == 0) goto L_0x0090
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r1)
            r8.append(r4)
            java.lang.String r8 = r8.toString()
            android.util.Log.d(r2, r8)
        L_0x0090:
            java.lang.Object r8 = r4.second
            if (r8 == 0) goto L_0x00a2
            android.util.ArrayMap<java.lang.Integer, java.lang.Boolean> r1 = r0.mHasCACerts
            java.lang.Object r2 = r4.first
        L_0x0098:
            java.lang.Integer r2 = (java.lang.Integer) r2
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            r1.put(r2, r8)
            r0.fireCallbacks()
        L_0x00a2:
            return
        L_0x00a3:
            r8 = move-exception
            r3 = r6
        L_0x00a5:
            boolean r4 = com.android.systemui.statusbar.policy.SecurityControllerImpl.DEBUG
            if (r4 == 0) goto L_0x00bb
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r1)
            r4.append(r3)
            java.lang.String r1 = r4.toString()
            android.util.Log.d(r2, r1)
        L_0x00bb:
            if (r3 == 0) goto L_0x00cf
            java.lang.Object r1 = r3.second
            if (r1 == 0) goto L_0x00cf
            android.util.ArrayMap<java.lang.Integer, java.lang.Boolean> r2 = r0.mHasCACerts
            java.lang.Object r3 = r3.first
            java.lang.Integer r3 = (java.lang.Integer) r3
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            r2.put(r3, r1)
            r0.fireCallbacks()
        L_0x00cf:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SecurityControllerImpl$$ExternalSyntheticLambda0.run():void");
    }
}
