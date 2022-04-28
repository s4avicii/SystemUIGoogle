package com.android.settingslib.fuelgauge;

public final class BatterySaverUtils {
    /* JADX WARNING: Can't wrap try/catch for region: R(7:21|22|23|24|25|26|(1:33)) */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00d1, code lost:
        return true;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:24:0x0080 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized boolean setPowerSaveMode(android.content.Context r9, boolean r10, boolean r11) {
        /*
            java.lang.Class<com.android.settingslib.fuelgauge.BatterySaverUtils> r0 = com.android.settingslib.fuelgauge.BatterySaverUtils.class
            monitor-enter(r0)
            android.content.ContentResolver r1 = r9.getContentResolver()     // Catch:{ all -> 0x00d4 }
            android.os.Bundle r2 = new android.os.Bundle     // Catch:{ all -> 0x00d4 }
            r3 = 1
            r2.<init>(r3)     // Catch:{ all -> 0x00d4 }
            java.lang.String r4 = "extra_confirm_only"
            r5 = 0
            r2.putBoolean(r4, r5)     // Catch:{ all -> 0x00d4 }
            r4 = 268435456(0x10000000, float:2.5243549E-29)
            if (r10 == 0) goto L_0x0041
            if (r11 == 0) goto L_0x0041
            android.content.ContentResolver r6 = r9.getContentResolver()     // Catch:{ all -> 0x00d4 }
            java.lang.String r7 = "low_power_warning_acknowledged"
            int r6 = android.provider.Settings.Secure.getInt(r6, r7, r5)     // Catch:{ all -> 0x00d4 }
            if (r6 == 0) goto L_0x0027
            r6 = r5
            goto L_0x003d
        L_0x0027:
            java.lang.String r6 = "PNW.startSaverConfirmation"
            android.content.Intent r7 = new android.content.Intent     // Catch:{ all -> 0x00d4 }
            r7.<init>(r6)     // Catch:{ all -> 0x00d4 }
            r7.setFlags(r4)     // Catch:{ all -> 0x00d4 }
            java.lang.String r6 = "com.android.systemui"
            r7.setPackage(r6)     // Catch:{ all -> 0x00d4 }
            r7.putExtras(r2)     // Catch:{ all -> 0x00d4 }
            r9.sendBroadcast(r7)     // Catch:{ all -> 0x00d4 }
            r6 = r3
        L_0x003d:
            if (r6 == 0) goto L_0x0041
            monitor-exit(r0)
            return r5
        L_0x0041:
            if (r10 == 0) goto L_0x004f
            if (r11 != 0) goto L_0x004f
            android.content.ContentResolver r11 = r9.getContentResolver()     // Catch:{ all -> 0x00d4 }
            java.lang.String r6 = "low_power_warning_acknowledged"
            r7 = -2
            android.provider.Settings.Secure.putIntForUser(r11, r6, r3, r7)     // Catch:{ all -> 0x00d4 }
        L_0x004f:
            java.lang.Class<android.os.PowerManager> r11 = android.os.PowerManager.class
            java.lang.Object r11 = r9.getSystemService(r11)     // Catch:{ all -> 0x00d4 }
            android.os.PowerManager r11 = (android.os.PowerManager) r11     // Catch:{ all -> 0x00d4 }
            boolean r11 = r11.setPowerSaveModeEnabled(r10)     // Catch:{ all -> 0x00d4 }
            if (r11 == 0) goto L_0x00d2
            if (r10 == 0) goto L_0x00d0
            java.lang.String r10 = "low_power_manual_activation_count"
            int r10 = android.provider.Settings.Secure.getInt(r1, r10, r5)     // Catch:{ all -> 0x00d4 }
            int r10 = r10 + r3
            java.lang.String r11 = "low_power_manual_activation_count"
            android.provider.Settings.Secure.putInt(r1, r11, r10)     // Catch:{ all -> 0x00d4 }
            android.content.ContentResolver r11 = r9.getContentResolver()     // Catch:{ all -> 0x00d4 }
            java.lang.String r6 = "low_power_mode_suggestion_params"
            java.lang.String r11 = android.provider.Settings.Global.getString(r11, r6)     // Catch:{ all -> 0x00d4 }
            android.util.KeyValueListParser r6 = new android.util.KeyValueListParser     // Catch:{ all -> 0x00d4 }
            r7 = 44
            r6.<init>(r7)     // Catch:{ all -> 0x00d4 }
            r6.setString(r11)     // Catch:{ IllegalArgumentException -> 0x0080 }
            goto L_0x0096
        L_0x0080:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00d4 }
            r7.<init>()     // Catch:{ all -> 0x00d4 }
            java.lang.String r8 = "Bad constants: "
            r7.append(r8)     // Catch:{ all -> 0x00d4 }
            r7.append(r11)     // Catch:{ all -> 0x00d4 }
            java.lang.String r11 = r7.toString()     // Catch:{ all -> 0x00d4 }
            java.lang.String r7 = "BatterySaverUtils"
            android.util.Slog.wtf(r7, r11)     // Catch:{ all -> 0x00d4 }
        L_0x0096:
            r11 = 4
            java.lang.String r7 = "start_nth"
            int r11 = r6.getInt(r7, r11)     // Catch:{ all -> 0x00d4 }
            r7 = 8
            java.lang.String r8 = "end_nth"
            int r6 = r6.getInt(r8, r7)     // Catch:{ all -> 0x00d4 }
            if (r10 < r11) goto L_0x00d0
            if (r10 > r6) goto L_0x00d0
            java.lang.String r10 = "low_power_trigger_level"
            int r10 = android.provider.Settings.Global.getInt(r1, r10, r5)     // Catch:{ all -> 0x00d4 }
            if (r10 != 0) goto L_0x00d0
            java.lang.String r10 = "suppress_auto_battery_saver_suggestion"
            int r10 = android.provider.Settings.Secure.getInt(r1, r10, r5)     // Catch:{ all -> 0x00d4 }
            if (r10 != 0) goto L_0x00d0
            java.lang.String r10 = "PNW.autoSaverSuggestion"
            android.content.Intent r11 = new android.content.Intent     // Catch:{ all -> 0x00d4 }
            r11.<init>(r10)     // Catch:{ all -> 0x00d4 }
            r11.setFlags(r4)     // Catch:{ all -> 0x00d4 }
            java.lang.String r10 = "com.android.systemui"
            r11.setPackage(r10)     // Catch:{ all -> 0x00d4 }
            r11.putExtras(r2)     // Catch:{ all -> 0x00d4 }
            r9.sendBroadcast(r11)     // Catch:{ all -> 0x00d4 }
        L_0x00d0:
            monitor-exit(r0)
            return r3
        L_0x00d2:
            monitor-exit(r0)
            return r5
        L_0x00d4:
            r9 = move-exception
            monitor-exit(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.fuelgauge.BatterySaverUtils.setPowerSaveMode(android.content.Context, boolean, boolean):boolean");
    }
}
