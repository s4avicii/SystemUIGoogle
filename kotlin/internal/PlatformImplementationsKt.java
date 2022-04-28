package kotlin.internal;

/* compiled from: PlatformImplementations.kt */
public final class PlatformImplementationsKt {
    public static final PlatformImplementations IMPLEMENTATIONS;

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: kotlin.internal.PlatformImplementations} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0049  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00be  */
    static {
        /*
            java.lang.Class<kotlin.internal.PlatformImplementations> r0 = kotlin.internal.PlatformImplementations.class
            java.lang.String r1 = "java.specification.version"
            java.lang.String r1 = java.lang.System.getProperty(r1)
            if (r1 != 0) goto L_0x000b
            goto L_0x003d
        L_0x000b:
            r2 = 6
            r3 = 46
            r4 = 0
            int r2 = kotlin.text.StringsKt__StringsKt.indexOf$default(r1, r3, r4, r2)
            r5 = 65536(0x10000, float:9.18355E-41)
            if (r2 >= 0) goto L_0x001d
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x003d }
            int r1 = r1 * r5
            goto L_0x0040
        L_0x001d:
            int r6 = r2 + 1
            r7 = 4
            int r3 = kotlin.text.StringsKt__StringsKt.indexOf$default(r1, r3, r6, r7)
            if (r3 >= 0) goto L_0x002a
            int r3 = r1.length()
        L_0x002a:
            java.lang.String r2 = r1.substring(r4, r2)
            java.lang.String r1 = r1.substring(r6, r3)
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ NumberFormatException -> 0x003d }
            int r2 = r2 * r5
            int r1 = java.lang.Integer.parseInt(r1)     // Catch:{ NumberFormatException -> 0x003d }
            int r1 = r1 + r2
            goto L_0x0040
        L_0x003d:
            r1 = 65542(0x10006, float:9.1844E-41)
        L_0x0040:
            r2 = 65544(0x10008, float:9.1847E-41)
            java.lang.String r3 = ", base type classloader: "
            java.lang.String r4 = "Instance classloader: "
            if (r1 < r2) goto L_0x00b9
            java.lang.Class<kotlin.internal.jdk8.JDK8PlatformImplementations> r2 = kotlin.internal.jdk8.JDK8PlatformImplementations.class
            java.lang.Object r2 = r2.newInstance()     // Catch:{ ClassNotFoundException -> 0x007f }
            kotlin.internal.PlatformImplementations r2 = (kotlin.internal.PlatformImplementations) r2     // Catch:{ ClassCastException -> 0x0053 }
            goto L_0x0133
        L_0x0053:
            r5 = move-exception
            java.lang.Class r2 = r2.getClass()     // Catch:{ ClassNotFoundException -> 0x007f }
            java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x007f }
            java.lang.ClassLoader r6 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x007f }
            java.lang.ClassCastException r7 = new java.lang.ClassCastException     // Catch:{ ClassNotFoundException -> 0x007f }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x007f }
            r8.<init>()     // Catch:{ ClassNotFoundException -> 0x007f }
            r8.append(r4)     // Catch:{ ClassNotFoundException -> 0x007f }
            r8.append(r2)     // Catch:{ ClassNotFoundException -> 0x007f }
            r8.append(r3)     // Catch:{ ClassNotFoundException -> 0x007f }
            r8.append(r6)     // Catch:{ ClassNotFoundException -> 0x007f }
            java.lang.String r2 = r8.toString()     // Catch:{ ClassNotFoundException -> 0x007f }
            r7.<init>(r2)     // Catch:{ ClassNotFoundException -> 0x007f }
            java.lang.Throwable r2 = r7.initCause(r5)     // Catch:{ ClassNotFoundException -> 0x007f }
            throw r2     // Catch:{ ClassNotFoundException -> 0x007f }
        L_0x007f:
            java.lang.String r2 = "kotlin.internal.JRE8PlatformImplementations"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ ClassNotFoundException -> 0x00b9 }
            java.lang.Object r2 = r2.newInstance()     // Catch:{ ClassNotFoundException -> 0x00b9 }
            kotlin.internal.PlatformImplementations r2 = (kotlin.internal.PlatformImplementations) r2     // Catch:{ ClassCastException -> 0x008d }
            goto L_0x0133
        L_0x008d:
            r5 = move-exception
            java.lang.Class r2 = r2.getClass()     // Catch:{ ClassNotFoundException -> 0x00b9 }
            java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x00b9 }
            java.lang.ClassLoader r6 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x00b9 }
            java.lang.ClassCastException r7 = new java.lang.ClassCastException     // Catch:{ ClassNotFoundException -> 0x00b9 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00b9 }
            r8.<init>()     // Catch:{ ClassNotFoundException -> 0x00b9 }
            r8.append(r4)     // Catch:{ ClassNotFoundException -> 0x00b9 }
            r8.append(r2)     // Catch:{ ClassNotFoundException -> 0x00b9 }
            r8.append(r3)     // Catch:{ ClassNotFoundException -> 0x00b9 }
            r8.append(r6)     // Catch:{ ClassNotFoundException -> 0x00b9 }
            java.lang.String r2 = r8.toString()     // Catch:{ ClassNotFoundException -> 0x00b9 }
            r7.<init>(r2)     // Catch:{ ClassNotFoundException -> 0x00b9 }
            java.lang.Throwable r2 = r7.initCause(r5)     // Catch:{ ClassNotFoundException -> 0x00b9 }
            throw r2     // Catch:{ ClassNotFoundException -> 0x00b9 }
        L_0x00b9:
            r2 = 65543(0x10007, float:9.1845E-41)
            if (r1 < r2) goto L_0x012e
            java.lang.Class<kotlin.internal.jdk7.JDK7PlatformImplementations> r1 = kotlin.internal.jdk7.JDK7PlatformImplementations.class
            java.lang.Object r1 = r1.newInstance()     // Catch:{ ClassNotFoundException -> 0x00f4 }
            r2 = r1
            kotlin.internal.PlatformImplementations r2 = (kotlin.internal.PlatformImplementations) r2     // Catch:{ ClassCastException -> 0x00c8 }
            goto L_0x0133
        L_0x00c8:
            r2 = move-exception
            java.lang.Class r1 = r1.getClass()     // Catch:{ ClassNotFoundException -> 0x00f4 }
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x00f4 }
            java.lang.ClassLoader r5 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x00f4 }
            java.lang.ClassCastException r6 = new java.lang.ClassCastException     // Catch:{ ClassNotFoundException -> 0x00f4 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x00f4 }
            r7.<init>()     // Catch:{ ClassNotFoundException -> 0x00f4 }
            r7.append(r4)     // Catch:{ ClassNotFoundException -> 0x00f4 }
            r7.append(r1)     // Catch:{ ClassNotFoundException -> 0x00f4 }
            r7.append(r3)     // Catch:{ ClassNotFoundException -> 0x00f4 }
            r7.append(r5)     // Catch:{ ClassNotFoundException -> 0x00f4 }
            java.lang.String r1 = r7.toString()     // Catch:{ ClassNotFoundException -> 0x00f4 }
            r6.<init>(r1)     // Catch:{ ClassNotFoundException -> 0x00f4 }
            java.lang.Throwable r1 = r6.initCause(r2)     // Catch:{ ClassNotFoundException -> 0x00f4 }
            throw r1     // Catch:{ ClassNotFoundException -> 0x00f4 }
        L_0x00f4:
            java.lang.String r1 = "kotlin.internal.JRE7PlatformImplementations"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ ClassNotFoundException -> 0x012e }
            java.lang.Object r1 = r1.newInstance()     // Catch:{ ClassNotFoundException -> 0x012e }
            r2 = r1
            kotlin.internal.PlatformImplementations r2 = (kotlin.internal.PlatformImplementations) r2     // Catch:{ ClassCastException -> 0x0102 }
            goto L_0x0133
        L_0x0102:
            r2 = move-exception
            java.lang.Class r1 = r1.getClass()     // Catch:{ ClassNotFoundException -> 0x012e }
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x012e }
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x012e }
            java.lang.ClassCastException r5 = new java.lang.ClassCastException     // Catch:{ ClassNotFoundException -> 0x012e }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x012e }
            r6.<init>()     // Catch:{ ClassNotFoundException -> 0x012e }
            r6.append(r4)     // Catch:{ ClassNotFoundException -> 0x012e }
            r6.append(r1)     // Catch:{ ClassNotFoundException -> 0x012e }
            r6.append(r3)     // Catch:{ ClassNotFoundException -> 0x012e }
            r6.append(r0)     // Catch:{ ClassNotFoundException -> 0x012e }
            java.lang.String r0 = r6.toString()     // Catch:{ ClassNotFoundException -> 0x012e }
            r5.<init>(r0)     // Catch:{ ClassNotFoundException -> 0x012e }
            java.lang.Throwable r0 = r5.initCause(r2)     // Catch:{ ClassNotFoundException -> 0x012e }
            throw r0     // Catch:{ ClassNotFoundException -> 0x012e }
        L_0x012e:
            kotlin.internal.PlatformImplementations r2 = new kotlin.internal.PlatformImplementations
            r2.<init>()
        L_0x0133:
            IMPLEMENTATIONS = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.internal.PlatformImplementationsKt.<clinit>():void");
    }
}
