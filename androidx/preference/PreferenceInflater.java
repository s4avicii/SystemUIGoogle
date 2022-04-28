package androidx.preference;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.InflateException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParserException;

public final class PreferenceInflater {
    public static final HashMap<String, Constructor> CONSTRUCTOR_MAP = new HashMap<>();
    public static final Class<?>[] CONSTRUCTOR_SIGNATURE = {Context.class, AttributeSet.class};
    public final Object[] mConstructorArgs = new Object[2];
    public final Context mContext;
    public String[] mDefaultPackages;
    public PreferenceManager mPreferenceManager;

    public final Preference createItemFromTag(String str, AttributeSet attributeSet) {
        try {
            if (-1 == str.indexOf(46)) {
                return createItem(str, this.mDefaultPackages, attributeSet);
            }
            return createItem(str, (String[]) null, attributeSet);
        } catch (InflateException e) {
            throw e;
        } catch (ClassNotFoundException e2) {
            InflateException inflateException = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class (not found)" + str);
            inflateException.initCause(e2);
            throw inflateException;
        } catch (Exception e3) {
            InflateException inflateException2 = new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str);
            inflateException2.initCause(e3);
            throw inflateException2;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0078, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0079, code lost:
        r12 = new android.view.InflateException(r13.getPositionDescription() + ": Error inflating class " + r11);
        r12.initCause(r10);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0097, code lost:
        throw r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0098, code lost:
        r10 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0099, code lost:
        throw r10;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0078 A[ExcHandler: Exception (r10v5 'e' java.lang.Exception A[CUSTOM_DECLARE]), Splitter:B:2:0x000d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.preference.Preference createItem(java.lang.String r11, java.lang.String[] r12, android.util.AttributeSet r13) throws java.lang.ClassNotFoundException, android.view.InflateException {
        /*
            r10 = this;
            java.util.HashMap<java.lang.String, java.lang.reflect.Constructor> r0 = CONSTRUCTOR_MAP
            java.lang.Object r0 = r0.get(r11)
            java.lang.reflect.Constructor r0 = (java.lang.reflect.Constructor) r0
            java.lang.String r1 = ": Error inflating class "
            r2 = 1
            if (r0 != 0) goto L_0x006d
            android.content.Context r0 = r10.mContext     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.lang.ClassLoader r0 = r0.getClassLoader()     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r3 = 0
            if (r12 == 0) goto L_0x005b
            int r4 = r12.length     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            if (r4 != 0) goto L_0x001a
            goto L_0x005b
        L_0x001a:
            int r4 = r12.length     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r5 = 0
            r6 = r3
            r7 = r5
        L_0x001e:
            if (r6 >= r4) goto L_0x003a
            r8 = r12[r6]     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0036, Exception -> 0x0078 }
            r9.<init>()     // Catch:{ ClassNotFoundException -> 0x0036, Exception -> 0x0078 }
            r9.append(r8)     // Catch:{ ClassNotFoundException -> 0x0036, Exception -> 0x0078 }
            r9.append(r11)     // Catch:{ ClassNotFoundException -> 0x0036, Exception -> 0x0078 }
            java.lang.String r8 = r9.toString()     // Catch:{ ClassNotFoundException -> 0x0036, Exception -> 0x0078 }
            java.lang.Class r5 = java.lang.Class.forName(r8, r3, r0)     // Catch:{ ClassNotFoundException -> 0x0036, Exception -> 0x0078 }
            goto L_0x003a
        L_0x0036:
            r7 = move-exception
            int r6 = r6 + 1
            goto L_0x001e
        L_0x003a:
            if (r5 != 0) goto L_0x005f
            if (r7 != 0) goto L_0x005a
            android.view.InflateException r10 = new android.view.InflateException     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r12.<init>()     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.lang.String r0 = r13.getPositionDescription()     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r12.append(r0)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r12.append(r1)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r12.append(r11)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.lang.String r12 = r12.toString()     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r10.<init>(r12)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            throw r10     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
        L_0x005a:
            throw r7     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
        L_0x005b:
            java.lang.Class r5 = java.lang.Class.forName(r11, r3, r0)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
        L_0x005f:
            java.lang.Class<?>[] r12 = CONSTRUCTOR_SIGNATURE     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.lang.reflect.Constructor r0 = r5.getConstructor(r12)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r0.setAccessible(r2)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.util.HashMap<java.lang.String, java.lang.reflect.Constructor> r12 = CONSTRUCTOR_MAP     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r12.put(r11, r0)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
        L_0x006d:
            java.lang.Object[] r10 = r10.mConstructorArgs     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            r10[r2] = r13     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            java.lang.Object r10 = r0.newInstance(r10)     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            androidx.preference.Preference r10 = (androidx.preference.Preference) r10     // Catch:{ ClassNotFoundException -> 0x0098, Exception -> 0x0078 }
            return r10
        L_0x0078:
            r10 = move-exception
            android.view.InflateException r12 = new android.view.InflateException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r13 = r13.getPositionDescription()
            r0.append(r13)
            r0.append(r1)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            r12.<init>(r11)
            r12.initCause(r10)
            throw r12
        L_0x0098:
            r10 = move-exception
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceInflater.createItem(java.lang.String, java.lang.String[], android.util.AttributeSet):androidx.preference.Preference");
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=androidx.preference.PreferenceScreen, code=androidx.preference.PreferenceGroup, for r7v0, types: [androidx.preference.PreferenceScreen] */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x001a A[Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0033 A[SYNTHETIC, Splitter:B:18:0x0033] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.preference.PreferenceGroup inflate(android.content.res.XmlResourceParser r6, androidx.preference.PreferenceGroup r7) {
        /*
            r5 = this;
            java.lang.Object[] r0 = r5.mConstructorArgs
            monitor-enter(r0)
            android.util.AttributeSet r1 = android.util.Xml.asAttributeSet(r6)     // Catch:{ all -> 0x0083 }
            java.lang.Object[] r2 = r5.mConstructorArgs     // Catch:{ all -> 0x0083 }
            r3 = 0
            android.content.Context r4 = r5.mContext     // Catch:{ all -> 0x0083 }
            r2[r3] = r4     // Catch:{ all -> 0x0083 }
        L_0x000e:
            int r2 = r6.next()     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            r3 = 2
            if (r2 == r3) goto L_0x0018
            r4 = 1
            if (r2 != r4) goto L_0x000e
        L_0x0018:
            if (r2 != r3) goto L_0x0033
            java.lang.String r2 = r6.getName()     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            androidx.preference.Preference r2 = r5.createItemFromTag(r2, r1)     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            androidx.preference.PreferenceGroup r2 = (androidx.preference.PreferenceGroup) r2     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            if (r7 != 0) goto L_0x002c
            androidx.preference.PreferenceManager r7 = r5.mPreferenceManager     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            r2.onAttachedToHierarchy(r7)     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            r7 = r2
        L_0x002c:
            r5.rInflate(r6, r7, r1)     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            monitor-exit(r0)     // Catch:{ all -> 0x0083 }
            return r7
        L_0x0031:
            r5 = move-exception
            goto L_0x004e
        L_0x0033:
            android.view.InflateException r5 = new android.view.InflateException     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            r7.<init>()     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            java.lang.String r1 = r6.getPositionDescription()     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            r7.append(r1)     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            java.lang.String r1 = ": No start tag found!"
            r7.append(r1)     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            java.lang.String r7 = r7.toString()     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            r5.<init>(r7)     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
            throw r5     // Catch:{ InflateException -> 0x0081, XmlPullParserException -> 0x0073, IOException -> 0x0031 }
        L_0x004e:
            android.view.InflateException r7 = new android.view.InflateException     // Catch:{ all -> 0x0083 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0083 }
            r1.<init>()     // Catch:{ all -> 0x0083 }
            java.lang.String r6 = r6.getPositionDescription()     // Catch:{ all -> 0x0083 }
            r1.append(r6)     // Catch:{ all -> 0x0083 }
            java.lang.String r6 = ": "
            r1.append(r6)     // Catch:{ all -> 0x0083 }
            java.lang.String r6 = r5.getMessage()     // Catch:{ all -> 0x0083 }
            r1.append(r6)     // Catch:{ all -> 0x0083 }
            java.lang.String r6 = r1.toString()     // Catch:{ all -> 0x0083 }
            r7.<init>(r6)     // Catch:{ all -> 0x0083 }
            r7.initCause(r5)     // Catch:{ all -> 0x0083 }
            throw r7     // Catch:{ all -> 0x0083 }
        L_0x0073:
            r5 = move-exception
            android.view.InflateException r6 = new android.view.InflateException     // Catch:{ all -> 0x0083 }
            java.lang.String r7 = r5.getMessage()     // Catch:{ all -> 0x0083 }
            r6.<init>(r7)     // Catch:{ all -> 0x0083 }
            r6.initCause(r5)     // Catch:{ all -> 0x0083 }
            throw r6     // Catch:{ all -> 0x0083 }
        L_0x0081:
            r5 = move-exception
            throw r5     // Catch:{ all -> 0x0083 }
        L_0x0083:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0083 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.preference.PreferenceInflater.inflate(android.content.res.XmlResourceParser, androidx.preference.PreferenceScreen):androidx.preference.PreferenceGroup");
    }

    public PreferenceInflater(Context context, PreferenceManager preferenceManager) {
        this.mContext = context;
        this.mPreferenceManager = preferenceManager;
        this.mDefaultPackages = new String[]{Preference.class.getPackage().getName() + ".", SwitchPreference.class.getPackage().getName() + "."};
    }

    public final void rInflate(XmlResourceParser xmlResourceParser, Preference preference, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int depth = xmlResourceParser.getDepth();
        while (true) {
            int next = xmlResourceParser.next();
            if ((next == 3 && xmlResourceParser.getDepth() <= depth) || next == 1) {
                return;
            }
            if (next == 2) {
                String name = xmlResourceParser.getName();
                if ("intent".equals(name)) {
                    try {
                        Intent parseIntent = Intent.parseIntent(this.mContext.getResources(), xmlResourceParser, attributeSet);
                        Objects.requireNonNull(preference);
                        preference.mIntent = parseIntent;
                    } catch (IOException e) {
                        XmlPullParserException xmlPullParserException = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException.initCause(e);
                        throw xmlPullParserException;
                    }
                } else if ("extra".equals(name)) {
                    Resources resources = this.mContext.getResources();
                    Objects.requireNonNull(preference);
                    if (preference.mExtras == null) {
                        preference.mExtras = new Bundle();
                    }
                    resources.parseBundleExtra("extra", attributeSet, preference.mExtras);
                    try {
                        int depth2 = xmlResourceParser.getDepth();
                        while (true) {
                            int next2 = xmlResourceParser.next();
                            if (next2 == 1 || (next2 == 3 && xmlResourceParser.getDepth() <= depth2)) {
                                break;
                            }
                        }
                    } catch (IOException e2) {
                        XmlPullParserException xmlPullParserException2 = new XmlPullParserException("Error parsing preference");
                        xmlPullParserException2.initCause(e2);
                        throw xmlPullParserException2;
                    }
                } else {
                    Preference createItemFromTag = createItemFromTag(name, attributeSet);
                    PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
                    Objects.requireNonNull(preferenceGroup);
                    preferenceGroup.addPreference(createItemFromTag);
                    rInflate(xmlResourceParser, createItemFromTag, attributeSet);
                }
            }
        }
    }
}
