package kotlinx.coroutines.internal;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ServiceLoader;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: FastServiceLoader.kt */
public final class FastServiceLoader {
    /* renamed from: loadMainDispatcherFactory$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    public static List m134x7e18e9ec() {
        ClassLoader classLoader;
        List<T> list;
        MainDispatcherFactory mainDispatcherFactory;
        Class<MainDispatcherFactory> cls = MainDispatcherFactory.class;
        if (!FastServiceLoaderKt.ANDROID_DETECTED) {
            ClassLoader classLoader2 = cls.getClassLoader();
            try {
                return m135xb0d520f4(classLoader2);
            } catch (Throwable unused) {
                return CollectionsKt___CollectionsKt.toList(ServiceLoader.load(cls, classLoader2));
            }
        } else {
            try {
                ArrayList arrayList = new ArrayList(2);
                MainDispatcherFactory mainDispatcherFactory2 = null;
                try {
                    mainDispatcherFactory = cls.cast(Class.forName("kotlinx.coroutines.android.AndroidDispatcherFactory", true, cls.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (ClassNotFoundException unused2) {
                    mainDispatcherFactory = null;
                }
                if (mainDispatcherFactory != null) {
                    arrayList.add(mainDispatcherFactory);
                }
                try {
                    mainDispatcherFactory2 = cls.cast(Class.forName("kotlinx.coroutines.test.internal.TestMainDispatcherFactory", true, cls.getClassLoader()).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (ClassNotFoundException unused3) {
                }
                if (mainDispatcherFactory2 == null) {
                    return arrayList;
                }
                arrayList.add(mainDispatcherFactory2);
                return arrayList;
            } catch (Throwable unused4) {
                list = CollectionsKt___CollectionsKt.toList(ServiceLoader.load(cls, classLoader));
            }
        }
        return list;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x007b, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        kotlin.p018io.CloseableKt.closeFinally(r3, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x007f, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0082, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:?, code lost:
        r7.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0086, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0087, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0088, code lost:
        kotlin.ExceptionsKt.addSuppressed(r9, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008b, code lost:
        throw r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a9, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00aa, code lost:
        kotlin.p018io.CloseableKt.closeFinally(r4, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00ad, code lost:
        throw r0;
     */
    /* renamed from: loadProviders$external__kotlinx_coroutines__android_common__kotlinx_coroutines */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.ArrayList m135xb0d520f4(java.lang.ClassLoader r9) {
        /*
            java.lang.Class<kotlinx.coroutines.internal.MainDispatcherFactory> r0 = kotlinx.coroutines.internal.MainDispatcherFactory.class
            java.lang.String r1 = r0.getName()
            java.lang.String r2 = "META-INF/services/"
            java.lang.String r1 = kotlin.jvm.internal.Intrinsics.stringPlus(r2, r1)
            java.util.Enumeration r1 = r9.getResources(r1)
            java.util.ArrayList r1 = java.util.Collections.list(r1)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.Iterator r1 = r1.iterator()
        L_0x001d:
            boolean r3 = r1.hasNext()
            r4 = 0
            if (r3 == 0) goto L_0x00ae
            java.lang.Object r3 = r1.next()
            java.net.URL r3 = (java.net.URL) r3
            java.lang.String r5 = r3.toString()
            r6 = 0
            java.lang.String r7 = "jar"
            boolean r7 = r5.startsWith(r7)
            if (r7 == 0) goto L_0x008c
            java.lang.String r3 = "jar:file:"
            java.lang.String r3 = kotlin.text.StringsKt__StringsKt.substringAfter$default(r5, r3)
            r7 = 33
            r8 = 6
            int r7 = kotlin.text.StringsKt__StringsKt.indexOf$default(r3, r7, r4, r8)
            r8 = -1
            if (r7 != r8) goto L_0x0048
            goto L_0x004c
        L_0x0048:
            java.lang.String r3 = r3.substring(r4, r7)
        L_0x004c:
            java.lang.String r7 = "!/"
            java.lang.String r5 = kotlin.text.StringsKt__StringsKt.substringAfter$default(r5, r7)
            java.util.jar.JarFile r7 = new java.util.jar.JarFile
            r7.<init>(r3, r4)
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ all -> 0x0080 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ all -> 0x0080 }
            java.util.zip.ZipEntry r8 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x0080 }
            r8.<init>(r5)     // Catch:{ all -> 0x0080 }
            java.io.InputStream r5 = r7.getInputStream(r8)     // Catch:{ all -> 0x0080 }
            java.lang.String r8 = "UTF-8"
            r4.<init>(r5, r8)     // Catch:{ all -> 0x0080 }
            r3.<init>(r4)     // Catch:{ all -> 0x0080 }
            java.util.List r4 = parseFile(r3)     // Catch:{ all -> 0x0079 }
            kotlin.p018io.CloseableKt.closeFinally(r3, r6)     // Catch:{ all -> 0x0080 }
            r7.close()     // Catch:{ all -> 0x0077 }
            goto L_0x00a2
        L_0x0077:
            r9 = move-exception
            throw r9
        L_0x0079:
            r9 = move-exception
            throw r9     // Catch:{ all -> 0x007b }
        L_0x007b:
            r0 = move-exception
            kotlin.p018io.CloseableKt.closeFinally(r3, r9)     // Catch:{ all -> 0x0080 }
            throw r0     // Catch:{ all -> 0x0080 }
        L_0x0080:
            r9 = move-exception
            throw r9     // Catch:{ all -> 0x0082 }
        L_0x0082:
            r0 = move-exception
            r7.close()     // Catch:{ all -> 0x0087 }
            throw r0
        L_0x0087:
            r0 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r9, r0)
            throw r9
        L_0x008c:
            java.io.BufferedReader r4 = new java.io.BufferedReader
            java.io.InputStreamReader r5 = new java.io.InputStreamReader
            java.io.InputStream r3 = r3.openStream()
            r5.<init>(r3)
            r4.<init>(r5)
            java.util.List r3 = parseFile(r4)     // Catch:{ all -> 0x00a7 }
            kotlin.p018io.CloseableKt.closeFinally(r4, r6)
            r4 = r3
        L_0x00a2:
            kotlin.collections.CollectionsKt__ReversedViewsKt.addAll((java.util.Collection) r2, (java.util.Collection) r4)
            goto L_0x001d
        L_0x00a7:
            r9 = move-exception
            throw r9     // Catch:{ all -> 0x00a9 }
        L_0x00a9:
            r0 = move-exception
            kotlin.p018io.CloseableKt.closeFinally(r4, r9)
            throw r0
        L_0x00ae:
            java.util.Set r1 = kotlin.collections.CollectionsKt___CollectionsKt.toSet(r2)
            boolean r2 = r1.isEmpty()
            r2 = r2 ^ 1
            if (r2 == 0) goto L_0x0117
            java.util.ArrayList r2 = new java.util.ArrayList
            r3 = 10
            int r3 = kotlin.collections.CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(r1, r3)
            r2.<init>(r3)
            java.util.Iterator r1 = r1.iterator()
        L_0x00c9:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0116
            java.lang.Object r3 = r1.next()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.Class r3 = java.lang.Class.forName(r3, r4, r9)
            boolean r5 = r0.isAssignableFrom(r3)
            if (r5 == 0) goto L_0x00f3
            java.lang.Class[] r5 = new java.lang.Class[r4]
            java.lang.reflect.Constructor r3 = r3.getDeclaredConstructor(r5)
            java.lang.Object[] r5 = new java.lang.Object[r4]
            java.lang.Object r3 = r3.newInstance(r5)
            java.lang.Object r3 = r0.cast(r3)
            r2.add(r3)
            goto L_0x00c9
        L_0x00f3:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r1 = "Expected service of class "
            r9.append(r1)
            r9.append(r0)
            java.lang.String r0 = ", but found "
            r9.append(r0)
            r9.append(r3)
            java.lang.String r9 = r9.toString()
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r9 = r9.toString()
            r0.<init>(r9)
            throw r0
        L_0x0116:
            return r2
        L_0x0117:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "No providers were loaded with FastServiceLoader"
            java.lang.String r0 = r0.toString()
            r9.<init>(r0)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.FastServiceLoader.m135xb0d520f4(java.lang.ClassLoader):java.util.ArrayList");
    }

    public static List parseFile(BufferedReader bufferedReader) {
        boolean z;
        boolean z2;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return CollectionsKt___CollectionsKt.toList(linkedHashSet);
            }
            boolean z3 = false;
            int indexOf$default = StringsKt__StringsKt.indexOf$default(readLine, "#", 0, false, 6);
            if (indexOf$default != -1) {
                readLine = readLine.substring(0, indexOf$default);
            }
            String obj = StringsKt__StringsKt.trim(readLine).toString();
            int i = 0;
            while (true) {
                if (i >= obj.length()) {
                    z = true;
                    break;
                }
                char charAt = obj.charAt(i);
                i++;
                if (charAt == '.' || Character.isJavaIdentifierPart(charAt)) {
                    z2 = true;
                    continue;
                } else {
                    z2 = false;
                    continue;
                }
                if (!z2) {
                    z = false;
                    break;
                }
            }
            if (z) {
                if (obj.length() > 0) {
                    z3 = true;
                }
                if (z3) {
                    linkedHashSet.add(obj);
                }
            } else {
                throw new IllegalArgumentException(Intrinsics.stringPlus("Illegal service provider class name: ", obj).toString());
            }
        }
    }
}
