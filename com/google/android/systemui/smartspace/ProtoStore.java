package com.google.android.systemui.smartspace;

import android.content.Context;
import android.util.Log;
import com.android.systemui.smartspace.nano.SmartspaceProto$CardWrapper;
import com.google.protobuf.nano.MessageNano;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public final class ProtoStore {
    public final Object mContext;

    public /* synthetic */ ProtoStore(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public final void store(SmartspaceProto$CardWrapper smartspaceProto$CardWrapper, String str) {
        FileOutputStream openFileOutput;
        try {
            openFileOutput = ((Context) this.mContext).openFileOutput(str, 0);
            if (smartspaceProto$CardWrapper != null) {
                openFileOutput.write(MessageNano.toByteArray(smartspaceProto$CardWrapper));
            } else {
                Log.d("ProtoStore", "deleting " + str);
                ((Context) this.mContext).deleteFile(str);
            }
            if (openFileOutput != null) {
                openFileOutput.close();
                return;
            }
            return;
        } catch (FileNotFoundException unused) {
            Log.d("ProtoStore", "file does not exist");
            return;
        } catch (Exception e) {
            Log.e("ProtoStore", "unable to write file", e);
            return;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:1:0x0014 */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x0014 A[LOOP:0: B:1:0x0014->B:12:0x0014, LOOP_START, SYNTHETIC, Splitter:B:1:0x0014] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ ProtoStore(java.io.FileInputStream r4) {
        /*
            r3 = this;
            r3.<init>()
            android.util.ArrayMap r0 = new android.util.ArrayMap
            r0.<init>()
            r3.mContext = r0
            java.io.BufferedReader r0 = new java.io.BufferedReader
            java.io.InputStreamReader r1 = new java.io.InputStreamReader
            r1.<init>(r4)
            r0.<init>(r1)
        L_0x0014:
            java.lang.String r4 = r0.readLine()     // Catch:{ IOException -> 0x0043 }
            if (r4 == 0) goto L_0x004b
            r1 = 58
            int r1 = r4.indexOf(r1)     // Catch:{ IOException -> 0x0043 }
            r2 = -1
            if (r1 != r2) goto L_0x0024
            goto L_0x0014
        L_0x0024:
            r2 = 0
            java.lang.String r2 = r4.substring(r2, r1)     // Catch:{ IOException -> 0x0043 }
            int r1 = r1 + 1
            java.lang.String r4 = r4.substring(r1)     // Catch:{ IOException -> 0x0043 }
            java.lang.Object r1 = r3.mContext     // Catch:{ NumberFormatException -> 0x0014 }
            java.util.Map r1 = (java.util.Map) r1     // Catch:{ NumberFormatException -> 0x0014 }
            java.lang.String r2 = r2.trim()     // Catch:{ NumberFormatException -> 0x0014 }
            float r4 = java.lang.Float.parseFloat(r4)     // Catch:{ NumberFormatException -> 0x0014 }
            java.lang.Float r4 = java.lang.Float.valueOf(r4)     // Catch:{ NumberFormatException -> 0x0014 }
            r1.put(r2, r4)     // Catch:{ NumberFormatException -> 0x0014 }
            goto L_0x0014
        L_0x0043:
            r3 = move-exception
            java.lang.String r4 = "Elmyra/SensorCalibration"
            java.lang.String r0 = "Error reading calibration file"
            android.util.Log.e(r4, r0, r3)
        L_0x004b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.ProtoStore.<init>(java.io.FileInputStream):void");
    }
}
