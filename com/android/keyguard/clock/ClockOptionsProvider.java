package com.android.keyguard.clock;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.util.List;
import java.util.Objects;
import javax.inject.Provider;

public final class ClockOptionsProvider extends ContentProvider {
    public Provider<List<ClockInfo>> mClockInfosProvider;

    public static class MyWriter implements ContentProvider.PipeDataWriter<Bitmap> {
        public final void writeDataToPipe(ParcelFileDescriptor parcelFileDescriptor, Uri uri, String str, Bundle bundle, Object obj) {
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
            Bitmap bitmap = (Bitmap) obj;
            try {
                autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, autoCloseOutputStream);
                autoCloseOutputStream.close();
                return;
            } catch (Exception e) {
                Log.w("ClockOptionsProvider", "fail to write to pipe", e);
                return;
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
            throw th;
        }
    }

    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public final boolean onCreate() {
        return true;
    }

    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    @VisibleForTesting
    public ClockOptionsProvider(Provider<List<ClockInfo>> provider) {
        this.mClockInfosProvider = provider;
    }

    public final String getType(Uri uri) {
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() <= 0) {
            return "vnd.android.cursor.dir/clock_faces";
        }
        if ("preview".equals(pathSegments.get(0)) || "thumbnail".equals(pathSegments.get(0))) {
            return "image/png";
        }
        return "vnd.android.cursor.dir/clock_faces";
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: com.android.keyguard.clock.ClockInfo} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.os.ParcelFileDescriptor openFile(android.net.Uri r14, java.lang.String r15) throws java.io.FileNotFoundException {
        /*
            r13 = this;
            java.util.List r15 = r14.getPathSegments()
            int r0 = r15.size()
            r1 = 2
            if (r0 != r1) goto L_0x009b
            r0 = 0
            java.lang.Object r1 = r15.get(r0)
            java.lang.String r2 = "preview"
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x0025
            java.lang.Object r1 = r15.get(r0)
            java.lang.String r3 = "thumbnail"
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x009b
        L_0x0025:
            r1 = 1
            java.lang.Object r1 = r15.get(r1)
            java.lang.String r1 = (java.lang.String) r1
            boolean r3 = android.text.TextUtils.isEmpty(r1)
            if (r3 != 0) goto L_0x0093
            javax.inject.Provider<java.util.List<com.android.keyguard.clock.ClockInfo>> r3 = r13.mClockInfosProvider
            java.lang.Object r3 = r3.get()
            java.util.List r3 = (java.util.List) r3
            r4 = r0
        L_0x003b:
            int r5 = r3.size()
            r6 = 0
            if (r4 >= r5) goto L_0x005e
            java.lang.Object r5 = r3.get(r4)
            com.android.keyguard.clock.ClockInfo r5 = (com.android.keyguard.clock.ClockInfo) r5
            java.util.Objects.requireNonNull(r5)
            java.lang.String r5 = r5.mId
            boolean r5 = r1.equals(r5)
            if (r5 == 0) goto L_0x005b
            java.lang.Object r1 = r3.get(r4)
            r6 = r1
            com.android.keyguard.clock.ClockInfo r6 = (com.android.keyguard.clock.ClockInfo) r6
            goto L_0x005e
        L_0x005b:
            int r4 = r4 + 1
            goto L_0x003b
        L_0x005e:
            if (r6 == 0) goto L_0x008b
            r10 = 0
            java.lang.Object r15 = r15.get(r0)
            boolean r15 = r2.equals(r15)
            if (r15 == 0) goto L_0x0074
            java.util.function.Supplier<android.graphics.Bitmap> r15 = r6.mPreview
            java.lang.Object r15 = r15.get()
            android.graphics.Bitmap r15 = (android.graphics.Bitmap) r15
            goto L_0x007c
        L_0x0074:
            java.util.function.Supplier<android.graphics.Bitmap> r15 = r6.mThumbnail
            java.lang.Object r15 = r15.get()
            android.graphics.Bitmap r15 = (android.graphics.Bitmap) r15
        L_0x007c:
            r11 = r15
            com.android.keyguard.clock.ClockOptionsProvider$MyWriter r12 = new com.android.keyguard.clock.ClockOptionsProvider$MyWriter
            r12.<init>()
            java.lang.String r9 = "image/png"
            r7 = r13
            r8 = r14
            android.os.ParcelFileDescriptor r13 = r7.openPipeHelper(r8, r9, r10, r11, r12)
            return r13
        L_0x008b:
            java.io.FileNotFoundException r13 = new java.io.FileNotFoundException
            java.lang.String r14 = "Invalid preview url, id not found"
            r13.<init>(r14)
            throw r13
        L_0x0093:
            java.io.FileNotFoundException r13 = new java.io.FileNotFoundException
            java.lang.String r14 = "Invalid preview url, missing id"
            r13.<init>(r14)
            throw r13
        L_0x009b:
            java.io.FileNotFoundException r13 = new java.io.FileNotFoundException
            java.lang.String r14 = "Invalid preview url"
            r13.<init>(r14)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.clock.ClockOptionsProvider.openFile(android.net.Uri, java.lang.String):android.os.ParcelFileDescriptor");
    }

    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (!"/list_options".equals(uri.getPath())) {
            return null;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"name", "title", "id", "thumbnail", "preview"});
        List list = this.mClockInfosProvider.get();
        for (int i = 0; i < list.size(); i++) {
            ClockInfo clockInfo = (ClockInfo) list.get(i);
            MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
            Objects.requireNonNull(clockInfo);
            newRow.add("name", clockInfo.mName).add("title", clockInfo.mTitle.get()).add("id", clockInfo.mId).add("thumbnail", new Uri.Builder().scheme("content").authority("com.android.keyguard.clock").appendPath("thumbnail").appendPath(clockInfo.mId).build()).add("preview", new Uri.Builder().scheme("content").authority("com.android.keyguard.clock").appendPath("preview").appendPath(clockInfo.mId).build());
        }
        return matrixCursor;
    }
}
