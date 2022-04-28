package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.SparseArray;
import android.util.TypedValue;
import java.util.Objects;
import java.util.WeakHashMap;

public final class ResourcesCompat {
    public static final Object sColorStateCacheLock = new Object();
    public static final WeakHashMap<ColorStateListCacheKey, SparseArray<ColorStateListCacheEntry>> sColorStateCaches = new WeakHashMap<>(0);
    public static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    public static abstract class FontCallback {
        public abstract void onFontRetrievalFailed(int i);

        public abstract void onFontRetrieved(Typeface typeface);

        public final void callbackFailAsync(final int i) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public final void run() {
                    FontCallback.this.onFontRetrievalFailed(i);
                }
            });
        }

        public final void callbackSuccessAsync(final Typeface typeface) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public final void run() {
                    FontCallback.this.onFontRetrieved(typeface);
                }
            });
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b5 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface loadFont(android.content.Context r14, int r15, android.util.TypedValue r16, int r17, androidx.core.content.res.ResourcesCompat.FontCallback r18, boolean r19, boolean r20) {
        /*
            r8 = r15
            r0 = r16
            r5 = r17
            r9 = r18
            android.content.res.Resources r3 = r14.getResources()
            r1 = 1
            r3.getValue(r15, r0, r1)
            java.lang.String r10 = "ResourcesCompat"
            java.lang.CharSequence r1 = r0.string
            if (r1 == 0) goto L_0x00d7
            java.lang.String r11 = r1.toString()
            java.lang.String r0 = "res/"
            boolean r0 = r11.startsWith(r0)
            r12 = -3
            r13 = 0
            if (r0 != 0) goto L_0x002b
            if (r9 == 0) goto L_0x00b3
            r9.callbackFailAsync(r12)
            goto L_0x00b3
        L_0x002b:
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r0 = androidx.core.graphics.TypefaceCompat.sTypefaceCache
            java.lang.String r1 = androidx.core.graphics.TypefaceCompat.createResourceUid(r3, r15, r5)
            java.lang.Object r0 = r0.get(r1)
            android.graphics.Typeface r0 = (android.graphics.Typeface) r0
            if (r0 == 0) goto L_0x0041
            if (r9 == 0) goto L_0x003e
            r9.callbackSuccessAsync(r0)
        L_0x003e:
            r13 = r0
            goto L_0x00b3
        L_0x0041:
            if (r20 == 0) goto L_0x0045
            goto L_0x00b3
        L_0x0045:
            java.lang.String r0 = r11.toLowerCase()     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            java.lang.String r1 = ".xml"
            boolean r0 = r0.endsWith(r1)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            if (r0 == 0) goto L_0x0073
            android.content.res.XmlResourceParser r0 = r3.getXml(r15)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry r2 = androidx.core.content.res.FontResourcesParserCompat.parse(r0, r3)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            if (r2 != 0) goto L_0x0066
            java.lang.String r0 = "Failed to find font-family tag"
            android.util.Log.e(r10, r0)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            if (r9 == 0) goto L_0x00b3
            r9.callbackFailAsync(r12)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            goto L_0x00b3
        L_0x0066:
            r1 = r14
            r4 = r15
            r5 = r17
            r6 = r18
            r7 = r19
            android.graphics.Typeface r13 = androidx.core.graphics.TypefaceCompat.createFromResourcesFamilyXml(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            goto L_0x00b3
        L_0x0073:
            android.graphics.Typeface r0 = androidx.core.graphics.TypefaceCompat.createFromResourcesFontFile(r3, r15, r5)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            if (r9 == 0) goto L_0x003e
            if (r0 == 0) goto L_0x007f
            r9.callbackSuccessAsync(r0)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            goto L_0x003e
        L_0x007f:
            r9.callbackFailAsync(r12)     // Catch:{ XmlPullParserException -> 0x0099, IOException -> 0x0083 }
            goto L_0x003e
        L_0x0083:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to read xml resource "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.e(r10, r1, r0)
            goto L_0x00ae
        L_0x0099:
            r0 = move-exception
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Failed to parse xml resource "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            android.util.Log.e(r10, r1, r0)
        L_0x00ae:
            if (r9 == 0) goto L_0x00b3
            r9.callbackFailAsync(r12)
        L_0x00b3:
            if (r13 != 0) goto L_0x00d6
            if (r9 != 0) goto L_0x00d6
            if (r20 == 0) goto L_0x00ba
            goto L_0x00d6
        L_0x00ba:
            android.content.res.Resources$NotFoundException r0 = new android.content.res.Resources$NotFoundException
            java.lang.String r1 = "Font resource ID #0x"
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r2 = java.lang.Integer.toHexString(r15)
            r1.append(r2)
            java.lang.String r2 = " could not be retrieved."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00d6:
            return r13
        L_0x00d7:
            android.content.res.Resources$NotFoundException r1 = new android.content.res.Resources$NotFoundException
            java.lang.String r2 = "Resource \""
            java.lang.StringBuilder r2 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r2)
            java.lang.String r3 = r3.getResourceName(r15)
            r2.append(r3)
            java.lang.String r3 = "\" ("
            r2.append(r3)
            java.lang.String r3 = java.lang.Integer.toHexString(r15)
            r2.append(r3)
            java.lang.String r3 = ") is not a Font: "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ResourcesCompat.loadFont(android.content.Context, int, android.util.TypedValue, int, androidx.core.content.res.ResourcesCompat$FontCallback, boolean, boolean):android.graphics.Typeface");
    }

    public static class ColorStateListCacheEntry {
        public final Configuration mConfiguration;
        public final ColorStateList mValue;

        public ColorStateListCacheEntry(ColorStateList colorStateList, Configuration configuration) {
            this.mValue = colorStateList;
            this.mConfiguration = configuration;
        }
    }

    public static final class ColorStateListCacheKey {
        public final Resources mResources;
        public final Resources.Theme mTheme;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || ColorStateListCacheKey.class != obj.getClass()) {
                return false;
            }
            ColorStateListCacheKey colorStateListCacheKey = (ColorStateListCacheKey) obj;
            return this.mResources.equals(colorStateListCacheKey.mResources) && Objects.equals(this.mTheme, colorStateListCacheKey.mTheme);
        }

        public final int hashCode() {
            return Objects.hash(new Object[]{this.mResources, this.mTheme});
        }

        public ColorStateListCacheKey(Resources resources, Resources.Theme theme) {
            this.mResources = resources;
            this.mTheme = theme;
        }
    }

    public static Typeface getFont(Context context, int i) throws Resources.NotFoundException {
        if (context.isRestricted()) {
            return null;
        }
        return loadFont(context, i, new TypedValue(), 0, (FontCallback) null, false, false);
    }
}
