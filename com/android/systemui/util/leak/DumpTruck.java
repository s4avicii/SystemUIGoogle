package com.android.systemui.util.leak;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class DumpTruck {
    public final StringBuilder body = new StringBuilder();
    public final Context context;
    public Uri hprofUri;
    public final GarbageMonitor mGarbageMonitor;
    public long rss;

    public static boolean zipUp(String str, ArrayList<String> arrayList) {
        BufferedInputStream bufferedInputStream;
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(str));
            try {
                byte[] bArr = new byte[1048576];
                Iterator<String> it = arrayList.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(next));
                    zipOutputStream.putNextEntry(new ZipEntry(next));
                    while (true) {
                        int read = bufferedInputStream.read(bArr, 0, 1048576);
                        if (read <= 0) {
                            break;
                        }
                        zipOutputStream.write(bArr, 0, read);
                    }
                    zipOutputStream.closeEntry();
                    bufferedInputStream.close();
                }
                zipOutputStream.close();
                return true;
            } catch (Throwable th) {
                zipOutputStream.close();
                throw th;
            }
            throw th;
        } catch (IOException e) {
            Log.e("DumpTruck", "error zipping up profile data", e);
            return false;
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
    }

    public DumpTruck(Context context2, GarbageMonitor garbageMonitor) {
        this.context = context2;
        this.mGarbageMonitor = garbageMonitor;
    }
}
