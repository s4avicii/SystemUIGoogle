package androidx.core.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import androidx.activity.result.ActivityResultRegistry$3$$ExternalSyntheticOutline0;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.core.content.res.FontResourcesParserCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class FontProvider {
    public static final C01121 sByteArrayComparator = new Comparator<byte[]>() {
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v2, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v2, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: byte} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: byte} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final int compare(java.lang.Object r4, java.lang.Object r5) {
            /*
                r3 = this;
                byte[] r4 = (byte[]) r4
                byte[] r5 = (byte[]) r5
                int r3 = r4.length
                int r0 = r5.length
                if (r3 == r0) goto L_0x000b
                int r3 = r4.length
                int r4 = r5.length
                goto L_0x001a
            L_0x000b:
                r3 = 0
                r0 = r3
            L_0x000d:
                int r1 = r4.length
                if (r0 >= r1) goto L_0x001f
                byte r1 = r4[r0]
                byte r2 = r5[r0]
                if (r1 == r2) goto L_0x001c
                byte r3 = r4[r0]
                byte r4 = r5[r0]
            L_0x001a:
                int r3 = r3 - r4
                goto L_0x001f
            L_0x001c:
                int r0 = r0 + 1
                goto L_0x000d
            L_0x001f:
                return r3
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontProvider.C01121.compare(java.lang.Object, java.lang.Object):int");
        }
    };

    public static FontsContractCompat$FontInfo[] query(Context context, FontRequest fontRequest, String str, CancellationSignal cancellationSignal) {
        int i;
        int i2;
        Uri uri;
        int i3;
        boolean z;
        String str2 = str;
        ArrayList arrayList = new ArrayList();
        Uri build = new Uri.Builder().scheme("content").authority(str2).build();
        Uri build2 = new Uri.Builder().scheme("content").authority(str2).appendPath("file").build();
        Cursor cursor = null;
        try {
            ContentResolver contentResolver = context.getContentResolver();
            Objects.requireNonNull(fontRequest);
            cursor = contentResolver.query(build, new String[]{"_id", "file_id", "font_ttc_index", "font_variation_settings", "font_weight", "font_italic", "result_code"}, "query = ?", new String[]{fontRequest.mQuery}, (String) null, cancellationSignal);
            if (cursor != null && cursor.getCount() > 0) {
                int columnIndex = cursor.getColumnIndex("result_code");
                ArrayList arrayList2 = new ArrayList();
                int columnIndex2 = cursor.getColumnIndex("_id");
                int columnIndex3 = cursor.getColumnIndex("file_id");
                int columnIndex4 = cursor.getColumnIndex("font_ttc_index");
                int columnIndex5 = cursor.getColumnIndex("font_weight");
                int columnIndex6 = cursor.getColumnIndex("font_italic");
                while (cursor.moveToNext()) {
                    if (columnIndex != -1) {
                        i = cursor.getInt(columnIndex);
                    } else {
                        i = 0;
                    }
                    if (columnIndex4 != -1) {
                        i2 = cursor.getInt(columnIndex4);
                    } else {
                        i2 = 0;
                    }
                    if (columnIndex3 == -1) {
                        uri = ContentUris.withAppendedId(build, cursor.getLong(columnIndex2));
                    } else {
                        uri = ContentUris.withAppendedId(build2, cursor.getLong(columnIndex3));
                    }
                    Uri uri2 = uri;
                    if (columnIndex5 != -1) {
                        i3 = cursor.getInt(columnIndex5);
                    } else {
                        i3 = 400;
                    }
                    int i4 = i3;
                    if (columnIndex6 == -1 || cursor.getInt(columnIndex6) != 1) {
                        z = false;
                    } else {
                        z = true;
                    }
                    arrayList2.add(new FontsContractCompat$FontInfo(uri2, i2, i4, z, i));
                }
                arrayList = arrayList2;
            }
            return (FontsContractCompat$FontInfo[]) arrayList.toArray(new FontsContractCompat$FontInfo[0]);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static FontsContractCompat$FontFamilyResult getFontFamilyResult(Context context, FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        ProviderInfo provider = getProvider(context.getPackageManager(), fontRequest, context.getResources());
        if (provider == null) {
            return new FontsContractCompat$FontFamilyResult(1, (FontsContractCompat$FontInfo[]) null);
        }
        return new FontsContractCompat$FontFamilyResult(0, query(context, fontRequest, provider.authority, (CancellationSignal) null));
    }

    public static ProviderInfo getProvider(PackageManager packageManager, FontRequest fontRequest, Resources resources) throws PackageManager.NameNotFoundException {
        boolean z;
        Objects.requireNonNull(fontRequest);
        String str = fontRequest.mProviderAuthority;
        ProviderInfo resolveContentProvider = packageManager.resolveContentProvider(str, 0);
        if (resolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException(SupportMenuInflater$$ExternalSyntheticOutline0.m4m("No package found for authority: ", str));
        } else if (resolveContentProvider.packageName.equals(fontRequest.mProviderPackage)) {
            Signature[] signatureArr = packageManager.getPackageInfo(resolveContentProvider.packageName, 64).signatures;
            ArrayList arrayList = new ArrayList();
            for (Signature byteArray : signatureArr) {
                arrayList.add(byteArray.toByteArray());
            }
            Collections.sort(arrayList, sByteArrayComparator);
            List<List<byte[]>> list = fontRequest.mCertificates;
            if (list == null) {
                list = FontResourcesParserCompat.readCerts(resources, 0);
            }
            for (int i = 0; i < list.size(); i++) {
                ArrayList arrayList2 = new ArrayList(list.get(i));
                Collections.sort(arrayList2, sByteArrayComparator);
                if (arrayList.size() == arrayList2.size()) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= arrayList.size()) {
                            z = true;
                            break;
                        } else if (!Arrays.equals((byte[]) arrayList.get(i2), (byte[]) arrayList2.get(i2))) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                z = false;
                if (z) {
                    return resolveContentProvider;
                }
            }
            return null;
        } else {
            StringBuilder m = ActivityResultRegistry$3$$ExternalSyntheticOutline0.m3m("Found content provider ", str, ", but package was not ");
            m.append(fontRequest.mProviderPackage);
            throw new PackageManager.NameNotFoundException(m.toString());
        }
    }
}
