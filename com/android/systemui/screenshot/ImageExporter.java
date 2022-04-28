package com.android.systemui.screenshot;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.Trace;
import android.provider.MediaStore;
import androidx.exifinterface.media.ExifInterface;
import com.android.internal.annotations.VisibleForTesting;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class ImageExporter {
    public static final Duration PENDING_ENTRY_TTL = Duration.ofHours(24);
    public static final String SCREENSHOTS_PATH = (Environment.DIRECTORY_PICTURES + File.separator + Environment.DIRECTORY_SCREENSHOTS);
    public Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;
    public int mQuality = 100;
    public final ContentResolver mResolver;

    public static final class ImageExportException extends IOException {
        public ImageExportException(String str) {
            super(str);
        }

        public ImageExportException(String str, IOException iOException) {
            super(str, iOException);
        }
    }

    public static class Result {
        public String fileName;
        public Bitmap.CompressFormat format;
        public boolean published;
        public UUID requestId;
        public long timestamp;
        public Uri uri;

        public final String toString() {
            return "Result{" + "uri=" + this.uri + ", requestId=" + this.requestId + ", fileName='" + this.fileName + '\'' + ", timestamp=" + this.timestamp + ", format=" + this.format + ", published=" + this.published + ", deleted=" + false + '}';
        }
    }

    public static class Task {
        public final Bitmap mBitmap;
        public final ZonedDateTime mCaptureTime;
        public final String mFileName;
        public final Bitmap.CompressFormat mFormat;
        public final boolean mPublish = true;
        public final int mQuality;
        public final UUID mRequestId;
        public final ContentResolver mResolver;

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0073 A[SYNTHETIC, Splitter:B:15:0x0073] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.android.systemui.screenshot.ImageExporter.Result execute() throws com.android.systemui.screenshot.ImageExporter.ImageExportException, java.lang.InterruptedException {
            /*
                r12 = this;
                java.lang.String r0 = "ImageExporter_execute"
                android.os.Trace.beginSection(r0)
                com.android.systemui.screenshot.ImageExporter$Result r0 = new com.android.systemui.screenshot.ImageExporter$Result
                r0.<init>()
                r1 = 0
                android.content.ContentResolver r2 = r12.mResolver     // Catch:{ ImageExportException -> 0x006f }
                android.graphics.Bitmap$CompressFormat r3 = r12.mFormat     // Catch:{ ImageExportException -> 0x006f }
                java.time.ZonedDateTime r4 = r12.mCaptureTime     // Catch:{ ImageExportException -> 0x006f }
                java.lang.String r5 = r12.mFileName     // Catch:{ ImageExportException -> 0x006f }
                android.net.Uri r2 = com.android.systemui.screenshot.ImageExporter.m219$$Nest$smcreateEntry(r2, r3, r4, r5)     // Catch:{ ImageExportException -> 0x006f }
                com.android.systemui.screenshot.ImageExporter.m221$$Nest$smthrowIfInterrupted()     // Catch:{ ImageExportException -> 0x006b }
                android.content.ContentResolver r3 = r12.mResolver     // Catch:{ ImageExportException -> 0x006b }
                android.graphics.Bitmap r4 = r12.mBitmap     // Catch:{ ImageExportException -> 0x006b }
                android.graphics.Bitmap$CompressFormat r5 = r12.mFormat     // Catch:{ ImageExportException -> 0x006b }
                int r6 = r12.mQuality     // Catch:{ ImageExportException -> 0x006b }
                com.android.systemui.screenshot.ImageExporter.m223$$Nest$smwriteImage(r3, r4, r5, r6, r2)     // Catch:{ ImageExportException -> 0x006b }
                com.android.systemui.screenshot.ImageExporter.m221$$Nest$smthrowIfInterrupted()     // Catch:{ ImageExportException -> 0x006b }
                android.graphics.Bitmap r3 = r12.mBitmap     // Catch:{ ImageExportException -> 0x006b }
                int r9 = r3.getWidth()     // Catch:{ ImageExportException -> 0x006b }
                android.graphics.Bitmap r3 = r12.mBitmap     // Catch:{ ImageExportException -> 0x006b }
                int r10 = r3.getHeight()     // Catch:{ ImageExportException -> 0x006b }
                android.content.ContentResolver r6 = r12.mResolver     // Catch:{ ImageExportException -> 0x006b }
                java.util.UUID r8 = r12.mRequestId     // Catch:{ ImageExportException -> 0x006b }
                java.time.ZonedDateTime r11 = r12.mCaptureTime     // Catch:{ ImageExportException -> 0x006b }
                r7 = r2
                com.android.systemui.screenshot.ImageExporter.m222$$Nest$smwriteExif(r6, r7, r8, r9, r10, r11)     // Catch:{ ImageExportException -> 0x006b }
                com.android.systemui.screenshot.ImageExporter.m221$$Nest$smthrowIfInterrupted()     // Catch:{ ImageExportException -> 0x006b }
                boolean r3 = r12.mPublish     // Catch:{ ImageExportException -> 0x006b }
                if (r3 == 0) goto L_0x004d
                android.content.ContentResolver r3 = r12.mResolver     // Catch:{ ImageExportException -> 0x006b }
                com.android.systemui.screenshot.ImageExporter.m220$$Nest$smpublishEntry(r3, r2)     // Catch:{ ImageExportException -> 0x006b }
                r3 = 1
                r0.published = r3     // Catch:{ ImageExportException -> 0x006b }
            L_0x004d:
                java.time.ZonedDateTime r3 = r12.mCaptureTime     // Catch:{ ImageExportException -> 0x006b }
                java.time.Instant r3 = r3.toInstant()     // Catch:{ ImageExportException -> 0x006b }
                long r3 = r3.toEpochMilli()     // Catch:{ ImageExportException -> 0x006b }
                r0.timestamp = r3     // Catch:{ ImageExportException -> 0x006b }
                java.util.UUID r3 = r12.mRequestId     // Catch:{ ImageExportException -> 0x006b }
                r0.requestId = r3     // Catch:{ ImageExportException -> 0x006b }
                r0.uri = r2     // Catch:{ ImageExportException -> 0x006b }
                java.lang.String r3 = r12.mFileName     // Catch:{ ImageExportException -> 0x006b }
                r0.fileName = r3     // Catch:{ ImageExportException -> 0x006b }
                android.graphics.Bitmap$CompressFormat r3 = r12.mFormat     // Catch:{ ImageExportException -> 0x006b }
                r0.format = r3     // Catch:{ ImageExportException -> 0x006b }
                android.os.Trace.endSection()
                return r0
            L_0x006b:
                r0 = move-exception
                goto L_0x0071
            L_0x006d:
                r12 = move-exception
                goto L_0x0079
            L_0x006f:
                r0 = move-exception
                r2 = r1
            L_0x0071:
                if (r2 == 0) goto L_0x0078
                android.content.ContentResolver r12 = r12.mResolver     // Catch:{ all -> 0x006d }
                r12.delete(r2, r1)     // Catch:{ all -> 0x006d }
            L_0x0078:
                throw r0     // Catch:{ all -> 0x006d }
            L_0x0079:
                android.os.Trace.endSection()
                throw r12
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ImageExporter.Task.execute():com.android.systemui.screenshot.ImageExporter$Result");
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("export [");
            m.append(this.mBitmap);
            m.append("] to [");
            m.append(this.mFormat);
            m.append("] at quality ");
            m.append(this.mQuality);
            return m.toString();
        }

        public Task(ContentResolver contentResolver, UUID uuid, Bitmap bitmap, ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat, int i) {
            this.mResolver = contentResolver;
            this.mRequestId = uuid;
            this.mBitmap = bitmap;
            this.mCaptureTime = zonedDateTime;
            this.mFormat = compressFormat;
            this.mQuality = i;
            this.mFileName = ImageExporter.createFilename(zonedDateTime, compressFormat);
        }
    }

    @VisibleForTesting
    public static String createFilename(ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat) {
        String str;
        Object[] objArr = new Object[2];
        objArr[0] = zonedDateTime;
        int i = C10691.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        if (i == 1) {
            str = "jpg";
        } else if (i == 2) {
            str = "png";
        } else if (i == 3 || i == 4 || i == 5) {
            str = "webp";
        } else {
            throw new IllegalArgumentException("Unknown CompressFormat!");
        }
        objArr[1] = str;
        return String.format("Screenshot_%1$tY%<tm%<td-%<tH%<tM%<tS.%2$s", objArr);
    }

    /* renamed from: com.android.systemui.screenshot.ImageExporter$1 */
    public static /* synthetic */ class C10691 {
        public static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$CompressFormat;

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                android.graphics.Bitmap$CompressFormat[] r0 = android.graphics.Bitmap.CompressFormat.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$android$graphics$Bitmap$CompressFormat = r0
                android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.JPEG     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$CompressFormat     // Catch:{ NoSuchFieldError -> 0x001d }
                android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.PNG     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$CompressFormat     // Catch:{ NoSuchFieldError -> 0x0028 }
                android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.WEBP     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$CompressFormat     // Catch:{ NoSuchFieldError -> 0x0033 }
                android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.WEBP_LOSSLESS     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$android$graphics$Bitmap$CompressFormat     // Catch:{ NoSuchFieldError -> 0x003e }
                android.graphics.Bitmap$CompressFormat r1 = android.graphics.Bitmap.CompressFormat.WEBP_LOSSY     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.screenshot.ImageExporter.C10691.<clinit>():void");
        }
    }

    /* renamed from: -$$Nest$smcreateEntry  reason: not valid java name */
    public static Uri m219$$Nest$smcreateEntry(ContentResolver contentResolver, Bitmap.CompressFormat compressFormat, ZonedDateTime zonedDateTime, String str) {
        Trace.beginSection("ImageExporter_createEntry");
        try {
            Uri insert = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, createMetadata(zonedDateTime, compressFormat, str));
            if (insert != null) {
                return insert;
            }
            throw new ImageExportException("ContentResolver#insert returned null.");
        } finally {
            Trace.endSection();
        }
    }

    /* renamed from: -$$Nest$smpublishEntry  reason: not valid java name */
    public static void m220$$Nest$smpublishEntry(ContentResolver contentResolver, Uri uri) {
        Trace.beginSection("ImageExporter_publishEntry");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_pending", 0);
            contentValues.putNull("date_expires");
            if (contentResolver.update(uri, contentValues, (Bundle) null) < 1) {
                throw new ImageExportException("Failed to publish entry. ContentResolver#update reported no rows updated.");
            }
        } finally {
            Trace.endSection();
        }
    }

    /* renamed from: -$$Nest$smwriteExif  reason: not valid java name */
    public static void m222$$Nest$smwriteExif(ContentResolver contentResolver, Uri uri, UUID uuid, int i, int i2, ZonedDateTime zonedDateTime) {
        Trace.beginSection("ImageExporter_writeExif");
        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = contentResolver.openFile(uri, "rw", (CancellationSignal) null);
            if (parcelFileDescriptor != null) {
                ExifInterface exifInterface = new ExifInterface(parcelFileDescriptor.getFileDescriptor());
                updateExifAttributes(exifInterface, uuid, i, i2, zonedDateTime);
                exifInterface.saveAttributes();
                FileUtils.closeQuietly(parcelFileDescriptor);
                Trace.endSection();
                return;
            }
            throw new ImageExportException("ContentResolver#openFile returned null.");
        } catch (IOException e) {
            throw new ImageExportException("ExifInterface threw an exception reading from the file descriptor.", e);
        } catch (IOException e2) {
            throw new ImageExportException("ExifInterface threw an exception writing to the file descriptor.", e2);
        } catch (FileNotFoundException e3) {
            try {
                throw new ImageExportException("ContentResolver#openFile threw an exception.", e3);
            } catch (Throwable th) {
                FileUtils.closeQuietly(parcelFileDescriptor);
                Trace.endSection();
                throw th;
            }
        }
    }

    /* renamed from: -$$Nest$smwriteImage  reason: not valid java name */
    public static void m223$$Nest$smwriteImage(ContentResolver contentResolver, Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i, Uri uri) {
        OutputStream openOutputStream;
        Trace.beginSection("ImageExporter_writeImage");
        try {
            openOutputStream = contentResolver.openOutputStream(uri);
            SystemClock.elapsedRealtime();
            if (bitmap.compress(compressFormat, i, openOutputStream)) {
                if (openOutputStream != null) {
                    openOutputStream.close();
                }
                Trace.endSection();
                return;
            }
            throw new ImageExportException("Bitmap.compress returned false. (Failure unknown)");
        } catch (IOException e) {
            try {
                throw new ImageExportException("ContentResolver#openOutputStream threw an exception.", e);
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
        throw th;
    }

    public static ContentValues createMetadata(ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat, String str) {
        String str2;
        ContentValues contentValues = new ContentValues();
        contentValues.put("relative_path", SCREENSHOTS_PATH);
        contentValues.put("_display_name", str);
        int i = C10691.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        if (i == 1) {
            str2 = "image/jpeg";
        } else if (i == 2) {
            str2 = "image/png";
        } else if (i == 3 || i == 4 || i == 5) {
            str2 = "image/webp";
        } else {
            throw new IllegalArgumentException("Unknown CompressFormat!");
        }
        contentValues.put("mime_type", str2);
        contentValues.put("date_added", Long.valueOf(zonedDateTime.toEpochSecond()));
        contentValues.put("date_modified", Long.valueOf(zonedDateTime.toEpochSecond()));
        contentValues.put("date_expires", Long.valueOf(zonedDateTime.plus(PENDING_ENTRY_TTL).toEpochSecond()));
        contentValues.put("is_pending", 1);
        return contentValues;
    }

    /* renamed from: -$$Nest$smthrowIfInterrupted  reason: not valid java name */
    public static void m221$$Nest$smthrowIfInterrupted() {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    public ImageExporter(ContentResolver contentResolver) {
        this.mResolver = contentResolver;
    }

    public static void updateExifAttributes(ExifInterface exifInterface, UUID uuid, int i, int i2, ZonedDateTime zonedDateTime) {
        exifInterface.setAttribute("ImageUniqueID", uuid.toString());
        exifInterface.setAttribute("Software", "Android " + Build.DISPLAY);
        exifInterface.setAttribute("ImageWidth", Integer.toString(i));
        exifInterface.setAttribute("ImageLength", Integer.toString(i2));
        String format = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(zonedDateTime);
        String format2 = DateTimeFormatter.ofPattern("SSS").format(zonedDateTime);
        String format3 = DateTimeFormatter.ofPattern("xxx").format(zonedDateTime);
        exifInterface.setAttribute("DateTimeOriginal", format);
        exifInterface.setAttribute("SubSecTimeOriginal", format2);
        exifInterface.setAttribute("OffsetTimeOriginal", format3);
    }
}
