package androidx.core.graphics;

import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import androidx.collection.LruCache;
import androidx.core.content.res.ResourcesCompat;
import androidx.transition.ViewUtilsBase;
import java.util.Objects;

public final class TypefaceCompat {
    public static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(16);
    public static final TypefaceCompatApi29Impl sTypefaceCompatImpl = new TypefaceCompatApi29Impl();

    public static class ResourcesCallbackAdapter extends ViewUtilsBase {
        public ResourcesCompat.FontCallback mFontCallback;

        public ResourcesCallbackAdapter(ResourcesCompat.FontCallback fontCallback) {
            this.mFontCallback = fontCallback;
        }
    }

    public static void clearCache() {
        LruCache<String, Typeface> lruCache = sTypefaceCache;
        Objects.requireNonNull(lruCache);
        lruCache.trimToSize(-1);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0060, code lost:
        if (r4 != null) goto L_0x0063;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0067, code lost:
        if ((r12 & 1) == 0) goto L_0x006c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0069, code lost:
        r11 = 700;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x006c, code lost:
        r11 = 400;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0070, code lost:
        if ((r12 & 2) == 0) goto L_0x0073;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0072, code lost:
        r2 = 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:?, code lost:
        return new android.graphics.Typeface.CustomFallbackBuilder(r4.build()).setStyle(new android.graphics.fonts.FontStyle(r11, r2)).build();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface createFromFontInfo(android.content.Context r10, androidx.core.provider.FontsContractCompat$FontInfo[] r11, int r12) {
        /*
            androidx.core.graphics.TypefaceCompatApi29Impl r0 = sTypefaceCompatImpl
            java.util.Objects.requireNonNull(r0)
            android.content.ContentResolver r10 = r10.getContentResolver()
            r0 = 0
            int r1 = r11.length     // Catch:{ Exception -> 0x0087 }
            r2 = 0
            r4 = r0
            r3 = r2
        L_0x000e:
            r5 = 1
            if (r3 >= r1) goto L_0x0060
            r6 = r11[r3]     // Catch:{ Exception -> 0x0087 }
            java.util.Objects.requireNonNull(r6)     // Catch:{ IOException -> 0x005d }
            android.net.Uri r7 = r6.mUri     // Catch:{ IOException -> 0x005d }
            java.lang.String r8 = "r"
            android.os.ParcelFileDescriptor r7 = r10.openFileDescriptor(r7, r8, r0)     // Catch:{ IOException -> 0x005d }
            if (r7 != 0) goto L_0x0024
            if (r7 == 0) goto L_0x005d
            goto L_0x004f
        L_0x0024:
            android.graphics.fonts.Font$Builder r8 = new android.graphics.fonts.Font$Builder     // Catch:{ all -> 0x0053 }
            r8.<init>(r7)     // Catch:{ all -> 0x0053 }
            int r9 = r6.mWeight     // Catch:{ all -> 0x0053 }
            android.graphics.fonts.Font$Builder r8 = r8.setWeight(r9)     // Catch:{ all -> 0x0053 }
            boolean r9 = r6.mItalic     // Catch:{ all -> 0x0053 }
            if (r9 == 0) goto L_0x0034
            goto L_0x0035
        L_0x0034:
            r5 = r2
        L_0x0035:
            android.graphics.fonts.Font$Builder r5 = r8.setSlant(r5)     // Catch:{ all -> 0x0053 }
            int r6 = r6.mTtcIndex     // Catch:{ all -> 0x0053 }
            android.graphics.fonts.Font$Builder r5 = r5.setTtcIndex(r6)     // Catch:{ all -> 0x0053 }
            android.graphics.fonts.Font r5 = r5.build()     // Catch:{ all -> 0x0053 }
            if (r4 != 0) goto L_0x004c
            android.graphics.fonts.FontFamily$Builder r6 = new android.graphics.fonts.FontFamily$Builder     // Catch:{ all -> 0x0053 }
            r6.<init>(r5)     // Catch:{ all -> 0x0053 }
            r4 = r6
            goto L_0x004f
        L_0x004c:
            r4.addFont(r5)     // Catch:{ all -> 0x0053 }
        L_0x004f:
            r7.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x005d
        L_0x0053:
            r5 = move-exception
            r7.close()     // Catch:{ all -> 0x0058 }
            goto L_0x005c
        L_0x0058:
            r6 = move-exception
            r5.addSuppressed(r6)     // Catch:{ IOException -> 0x005d }
        L_0x005c:
            throw r5     // Catch:{ IOException -> 0x005d }
        L_0x005d:
            int r3 = r3 + 1
            goto L_0x000e
        L_0x0060:
            if (r4 != 0) goto L_0x0063
            goto L_0x0087
        L_0x0063:
            android.graphics.fonts.FontStyle r10 = new android.graphics.fonts.FontStyle     // Catch:{ Exception -> 0x0087 }
            r11 = r12 & 1
            if (r11 == 0) goto L_0x006c
            r11 = 700(0x2bc, float:9.81E-43)
            goto L_0x006e
        L_0x006c:
            r11 = 400(0x190, float:5.6E-43)
        L_0x006e:
            r12 = r12 & 2
            if (r12 == 0) goto L_0x0073
            r2 = r5
        L_0x0073:
            r10.<init>(r11, r2)     // Catch:{ Exception -> 0x0087 }
            android.graphics.Typeface$CustomFallbackBuilder r11 = new android.graphics.Typeface$CustomFallbackBuilder     // Catch:{ Exception -> 0x0087 }
            android.graphics.fonts.FontFamily r12 = r4.build()     // Catch:{ Exception -> 0x0087 }
            r11.<init>(r12)     // Catch:{ Exception -> 0x0087 }
            android.graphics.Typeface$CustomFallbackBuilder r10 = r11.setStyle(r10)     // Catch:{ Exception -> 0x0087 }
            android.graphics.Typeface r0 = r10.build()     // Catch:{ Exception -> 0x0087 }
        L_0x0087:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompat.createFromFontInfo(android.content.Context, androidx.core.provider.FontsContractCompat$FontInfo[], int):android.graphics.Typeface");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0025, code lost:
        if (r0.equals(r5) == false) goto L_0x0029;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.graphics.Typeface createFromResourcesFamilyXml(android.content.Context r8, androidx.core.content.res.FontResourcesParserCompat.FamilyResourceEntry r9, android.content.res.Resources r10, int r11, int r12, androidx.core.content.res.ResourcesCompat.FontCallback r13, boolean r14) {
        /*
            boolean r0 = r9 instanceof androidx.core.content.res.FontResourcesParserCompat.ProviderResourceEntry
            r1 = 0
            r2 = 0
            r3 = 1
            r4 = -3
            if (r0 == 0) goto L_0x0156
            androidx.core.content.res.FontResourcesParserCompat$ProviderResourceEntry r9 = (androidx.core.content.res.FontResourcesParserCompat.ProviderResourceEntry) r9
            java.lang.String r0 = r9.mSystemFontFamilyName
            if (r0 == 0) goto L_0x0028
            boolean r5 = r0.isEmpty()
            if (r5 == 0) goto L_0x0015
            goto L_0x0028
        L_0x0015:
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r0, r1)
            android.graphics.Typeface r5 = android.graphics.Typeface.DEFAULT
            android.graphics.Typeface r5 = android.graphics.Typeface.create(r5, r1)
            if (r0 == 0) goto L_0x0028
            boolean r5 = r0.equals(r5)
            if (r5 != 0) goto L_0x0028
            goto L_0x0029
        L_0x0028:
            r0 = r2
        L_0x0029:
            if (r0 == 0) goto L_0x0031
            if (r13 == 0) goto L_0x0030
            r13.callbackSuccessAsync(r0)
        L_0x0030:
            return r0
        L_0x0031:
            if (r14 == 0) goto L_0x0038
            int r0 = r9.mStrategy
            if (r0 != 0) goto L_0x003b
            goto L_0x003a
        L_0x0038:
            if (r13 != 0) goto L_0x003b
        L_0x003a:
            r1 = r3
        L_0x003b:
            r0 = -1
            if (r14 == 0) goto L_0x0041
            int r14 = r9.mTimeoutMs
            goto L_0x0042
        L_0x0041:
            r14 = r0
        L_0x0042:
            android.os.Handler r3 = new android.os.Handler
            android.os.Looper r5 = android.os.Looper.getMainLooper()
            r3.<init>(r5)
            androidx.core.graphics.TypefaceCompat$ResourcesCallbackAdapter r5 = new androidx.core.graphics.TypefaceCompat$ResourcesCallbackAdapter
            r5.<init>(r13)
            androidx.core.provider.FontRequest r9 = r9.mRequest
            androidx.core.provider.CallbackWithHandler r13 = new androidx.core.provider.CallbackWithHandler
            r13.<init>(r5, r3)
            if (r1 == 0) goto L_0x00d1
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r1 = androidx.core.provider.FontRequestWorker.sTypefaceCache
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.util.Objects.requireNonNull(r9)
            java.lang.String r6 = r9.mIdentifier
            r1.append(r6)
            java.lang.String r6 = "-"
            r1.append(r6)
            r1.append(r12)
            java.lang.String r1 = r1.toString()
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r6 = androidx.core.provider.FontRequestWorker.sTypefaceCache
            java.lang.Object r6 = r6.get(r1)
            android.graphics.Typeface r6 = (android.graphics.Typeface) r6
            if (r6 == 0) goto L_0x0089
            androidx.core.provider.CallbackWithHandler$1 r8 = new androidx.core.provider.CallbackWithHandler$1
            r8.<init>(r6)
            r3.post(r8)
            r2 = r6
            goto L_0x01d2
        L_0x0089:
            if (r14 != r0) goto L_0x0096
            androidx.core.provider.FontRequestWorker$TypefaceResult r8 = androidx.core.provider.FontRequestWorker.getFontSync(r1, r8, r9, r12)
            r13.onTypefaceResult(r8)
            android.graphics.Typeface r2 = r8.mTypeface
            goto L_0x01d2
        L_0x0096:
            androidx.core.provider.FontRequestWorker$1 r0 = new androidx.core.provider.FontRequestWorker$1
            r0.<init>(r1, r8, r9, r12)
            java.util.concurrent.ThreadPoolExecutor r8 = androidx.core.provider.FontRequestWorker.DEFAULT_EXECUTOR_SERVICE     // Catch:{ InterruptedException -> 0x00c3 }
            java.util.concurrent.Future r8 = r8.submit(r0)     // Catch:{ InterruptedException -> 0x00c3 }
            long r0 = (long) r14
            java.util.concurrent.TimeUnit r9 = java.util.concurrent.TimeUnit.MILLISECONDS     // Catch:{ ExecutionException -> 0x00bc, InterruptedException -> 0x00ba, TimeoutException -> 0x00b1 }
            java.lang.Object r8 = r8.get(r0, r9)     // Catch:{ ExecutionException -> 0x00bc, InterruptedException -> 0x00ba, TimeoutException -> 0x00b1 }
            androidx.core.provider.FontRequestWorker$TypefaceResult r8 = (androidx.core.provider.FontRequestWorker.TypefaceResult) r8     // Catch:{ InterruptedException -> 0x00c3 }
            r13.onTypefaceResult(r8)     // Catch:{ InterruptedException -> 0x00c3 }
            android.graphics.Typeface r2 = r8.mTypeface     // Catch:{ InterruptedException -> 0x00c3 }
            goto L_0x01d2
        L_0x00b1:
            java.lang.InterruptedException r8 = new java.lang.InterruptedException     // Catch:{ InterruptedException -> 0x00c3 }
            java.lang.String r9 = "timeout"
            r8.<init>(r9)     // Catch:{ InterruptedException -> 0x00c3 }
            throw r8     // Catch:{ InterruptedException -> 0x00c3 }
        L_0x00ba:
            r8 = move-exception
            throw r8     // Catch:{ InterruptedException -> 0x00c3 }
        L_0x00bc:
            r8 = move-exception
            java.lang.RuntimeException r9 = new java.lang.RuntimeException     // Catch:{ InterruptedException -> 0x00c3 }
            r9.<init>(r8)     // Catch:{ InterruptedException -> 0x00c3 }
            throw r9     // Catch:{ InterruptedException -> 0x00c3 }
        L_0x00c3:
            androidx.transition.ViewUtilsBase r8 = r13.mCallback
            android.os.Handler r9 = r13.mCallbackHandler
            androidx.core.provider.CallbackWithHandler$2 r13 = new androidx.core.provider.CallbackWithHandler$2
            r13.<init>(r4)
            r9.post(r13)
            goto L_0x01d2
        L_0x00d1:
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r14 = androidx.core.provider.FontRequestWorker.sTypefaceCache
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.util.Objects.requireNonNull(r9)
            java.lang.String r0 = r9.mIdentifier
            r14.append(r0)
            java.lang.String r0 = "-"
            r14.append(r0)
            r14.append(r12)
            java.lang.String r14 = r14.toString()
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r0 = androidx.core.provider.FontRequestWorker.sTypefaceCache
            java.lang.Object r0 = r0.get(r14)
            android.graphics.Typeface r0 = (android.graphics.Typeface) r0
            if (r0 == 0) goto L_0x0101
            androidx.core.provider.CallbackWithHandler$1 r8 = new androidx.core.provider.CallbackWithHandler$1
            r8.<init>(r0)
            r3.post(r8)
            r2 = r0
            goto L_0x01d2
        L_0x0101:
            androidx.core.provider.FontRequestWorker$2 r0 = new androidx.core.provider.FontRequestWorker$2
            r0.<init>()
            java.lang.Object r5 = androidx.core.provider.FontRequestWorker.LOCK
            monitor-enter(r5)
            androidx.collection.SimpleArrayMap<java.lang.String, java.util.ArrayList<androidx.core.util.Consumer<androidx.core.provider.FontRequestWorker$TypefaceResult>>> r13 = androidx.core.provider.FontRequestWorker.PENDING_REPLIES     // Catch:{ all -> 0x0153 }
            java.util.Objects.requireNonNull(r13)     // Catch:{ all -> 0x0153 }
            java.lang.Object r1 = r13.getOrDefault(r14, r2)     // Catch:{ all -> 0x0153 }
            java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch:{ all -> 0x0153 }
            if (r1 == 0) goto L_0x011c
            r1.add(r0)     // Catch:{ all -> 0x0153 }
            monitor-exit(r5)     // Catch:{ all -> 0x0153 }
            goto L_0x01d2
        L_0x011c:
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0153 }
            r1.<init>()     // Catch:{ all -> 0x0153 }
            r1.add(r0)     // Catch:{ all -> 0x0153 }
            r13.put(r14, r1)     // Catch:{ all -> 0x0153 }
            monitor-exit(r5)     // Catch:{ all -> 0x0153 }
            androidx.core.provider.FontRequestWorker$3 r13 = new androidx.core.provider.FontRequestWorker$3
            r13.<init>(r14, r8, r9, r12)
            java.util.concurrent.ThreadPoolExecutor r8 = androidx.core.provider.FontRequestWorker.DEFAULT_EXECUTOR_SERVICE
            androidx.core.provider.FontRequestWorker$4 r9 = new androidx.core.provider.FontRequestWorker$4
            r9.<init>(r14)
            android.os.Looper r14 = android.os.Looper.myLooper()
            if (r14 != 0) goto L_0x0144
            android.os.Handler r14 = new android.os.Handler
            android.os.Looper r0 = android.os.Looper.getMainLooper()
            r14.<init>(r0)
            goto L_0x0149
        L_0x0144:
            android.os.Handler r14 = new android.os.Handler
            r14.<init>()
        L_0x0149:
            androidx.core.provider.RequestExecutor$ReplyRunnable r0 = new androidx.core.provider.RequestExecutor$ReplyRunnable
            r0.<init>(r14, r13, r9)
            r8.execute(r0)
            goto L_0x01d2
        L_0x0153:
            r8 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x0153 }
            throw r8
        L_0x0156:
            androidx.core.graphics.TypefaceCompatApi29Impl r8 = sTypefaceCompatImpl
            androidx.core.content.res.FontResourcesParserCompat$FontFamilyFilesResourceEntry r9 = (androidx.core.content.res.FontResourcesParserCompat.FontFamilyFilesResourceEntry) r9
            java.util.Objects.requireNonNull(r8)
            androidx.core.content.res.FontResourcesParserCompat$FontFileResourceEntry[] r8 = r9.mEntries     // Catch:{ Exception -> 0x01c7 }
            int r9 = r8.length     // Catch:{ Exception -> 0x01c7 }
            r14 = r1
            r0 = r2
        L_0x0162:
            if (r14 >= r9) goto L_0x01a0
            r5 = r8[r14]     // Catch:{ Exception -> 0x01c7 }
            android.graphics.fonts.Font$Builder r6 = new android.graphics.fonts.Font$Builder     // Catch:{ IOException -> 0x019d }
            java.util.Objects.requireNonNull(r5)     // Catch:{ IOException -> 0x019d }
            int r7 = r5.mResourceId     // Catch:{ IOException -> 0x019d }
            r6.<init>(r10, r7)     // Catch:{ IOException -> 0x019d }
            int r7 = r5.mWeight     // Catch:{ IOException -> 0x019d }
            android.graphics.fonts.Font$Builder r6 = r6.setWeight(r7)     // Catch:{ IOException -> 0x019d }
            boolean r7 = r5.mItalic     // Catch:{ IOException -> 0x019d }
            if (r7 == 0) goto L_0x017c
            r7 = r3
            goto L_0x017d
        L_0x017c:
            r7 = r1
        L_0x017d:
            android.graphics.fonts.Font$Builder r6 = r6.setSlant(r7)     // Catch:{ IOException -> 0x019d }
            int r7 = r5.mTtcIndex     // Catch:{ IOException -> 0x019d }
            android.graphics.fonts.Font$Builder r6 = r6.setTtcIndex(r7)     // Catch:{ IOException -> 0x019d }
            java.lang.String r5 = r5.mVariationSettings     // Catch:{ IOException -> 0x019d }
            android.graphics.fonts.Font$Builder r5 = r6.setFontVariationSettings(r5)     // Catch:{ IOException -> 0x019d }
            android.graphics.fonts.Font r5 = r5.build()     // Catch:{ IOException -> 0x019d }
            if (r0 != 0) goto L_0x019a
            android.graphics.fonts.FontFamily$Builder r6 = new android.graphics.fonts.FontFamily$Builder     // Catch:{ IOException -> 0x019d }
            r6.<init>(r5)     // Catch:{ IOException -> 0x019d }
            r0 = r6
            goto L_0x019d
        L_0x019a:
            r0.addFont(r5)     // Catch:{ IOException -> 0x019d }
        L_0x019d:
            int r14 = r14 + 1
            goto L_0x0162
        L_0x01a0:
            if (r0 != 0) goto L_0x01a3
            goto L_0x01c7
        L_0x01a3:
            android.graphics.fonts.FontStyle r8 = new android.graphics.fonts.FontStyle     // Catch:{ Exception -> 0x01c7 }
            r9 = r12 & 1
            if (r9 == 0) goto L_0x01ac
            r9 = 700(0x2bc, float:9.81E-43)
            goto L_0x01ae
        L_0x01ac:
            r9 = 400(0x190, float:5.6E-43)
        L_0x01ae:
            r14 = r12 & 2
            if (r14 == 0) goto L_0x01b3
            r1 = r3
        L_0x01b3:
            r8.<init>(r9, r1)     // Catch:{ Exception -> 0x01c7 }
            android.graphics.Typeface$CustomFallbackBuilder r9 = new android.graphics.Typeface$CustomFallbackBuilder     // Catch:{ Exception -> 0x01c7 }
            android.graphics.fonts.FontFamily r14 = r0.build()     // Catch:{ Exception -> 0x01c7 }
            r9.<init>(r14)     // Catch:{ Exception -> 0x01c7 }
            android.graphics.Typeface$CustomFallbackBuilder r8 = r9.setStyle(r8)     // Catch:{ Exception -> 0x01c7 }
            android.graphics.Typeface r2 = r8.build()     // Catch:{ Exception -> 0x01c7 }
        L_0x01c7:
            if (r13 == 0) goto L_0x01d2
            if (r2 == 0) goto L_0x01cf
            r13.callbackSuccessAsync(r2)
            goto L_0x01d2
        L_0x01cf:
            r13.callbackFailAsync(r4)
        L_0x01d2:
            if (r2 == 0) goto L_0x01dd
            androidx.collection.LruCache<java.lang.String, android.graphics.Typeface> r8 = sTypefaceCache
            java.lang.String r9 = createResourceUid(r10, r11, r12)
            r8.put(r9, r2)
        L_0x01dd:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.graphics.TypefaceCompat.createFromResourcesFamilyXml(android.content.Context, androidx.core.content.res.FontResourcesParserCompat$FamilyResourceEntry, android.content.res.Resources, int, int, androidx.core.content.res.ResourcesCompat$FontCallback, boolean):android.graphics.Typeface");
    }

    public static Typeface createFromResourcesFontFile(Resources resources, int i, int i2) {
        Typeface typeface;
        Objects.requireNonNull(sTypefaceCompatImpl);
        try {
            Font build = new Font.Builder(resources, i).build();
            typeface = new Typeface.CustomFallbackBuilder(new FontFamily.Builder(build).build()).setStyle(build.getStyle()).build();
        } catch (Exception unused) {
            typeface = null;
        }
        if (typeface != null) {
            sTypefaceCache.put(createResourceUid(resources, i, i2), typeface);
        }
        return typeface;
    }

    public static String createResourceUid(Resources resources, int i, int i2) {
        return resources.getResourcePackageName(i) + "-" + i + "-" + i2;
    }
}
