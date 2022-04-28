package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.LocaleList;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ConfigurationControllerImpl.kt */
public final class ConfigurationControllerImpl implements ConfigurationController {
    public final Context context;
    public int density;
    public float fontScale;
    public final boolean inCarMode;
    public final Configuration lastConfig = new Configuration();
    public int layoutDirection;
    public final ArrayList listeners = new ArrayList();
    public LocaleList localeList;
    public Rect maxBounds;
    public int smallestScreenWidth;
    public int uiMode;

    public final void addCallback(Object obj) {
        ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) obj;
        this.listeners.add(configurationListener);
        configurationListener.onDensityOrFontScaleChanged();
    }

    public final boolean isLayoutRtl() {
        if (this.layoutDirection == 1) {
            return true;
        }
        return false;
    }

    public final void notifyThemeChanged() {
        Iterator it = new ArrayList(this.listeners).iterator();
        while (it.hasNext()) {
            ConfigurationController.ConfigurationListener configurationListener = (ConfigurationController.ConfigurationListener) it.next();
            if (this.listeners.contains(configurationListener)) {
                configurationListener.onThemeChanged();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0047, code lost:
        if (r4 == false) goto L_0x0069;
     */
    /* JADX WARNING: Removed duplicated region for block: B:110:? A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x006f  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c5  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00e5  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x014e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onConfigurationChanged(android.content.res.Configuration r11) {
        /*
            r10 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            java.util.ArrayList r1 = r10.listeners
            r0.<init>(r1)
            java.util.Iterator r1 = r0.iterator()
        L_0x000b:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0023
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r2 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r2
            java.util.ArrayList r3 = r10.listeners
            boolean r3 = r3.contains(r2)
            if (r3 == 0) goto L_0x000b
            r2.onConfigChanged(r11)
            goto L_0x000b
        L_0x0023:
            float r1 = r11.fontScale
            int r2 = r11.densityDpi
            int r3 = r11.uiMode
            r3 = r3 & 48
            int r4 = r10.uiMode
            r5 = 0
            r6 = 1
            if (r3 == r4) goto L_0x0033
            r4 = r6
            goto L_0x0034
        L_0x0033:
            r4 = r5
        L_0x0034:
            int r7 = r10.density
            if (r2 != r7) goto L_0x0049
            float r7 = r10.fontScale
            int r7 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r7 != 0) goto L_0x0040
            r7 = r6
            goto L_0x0041
        L_0x0040:
            r7 = r5
        L_0x0041:
            if (r7 == 0) goto L_0x0049
            boolean r7 = r10.inCarMode
            if (r7 == 0) goto L_0x0069
            if (r4 == 0) goto L_0x0069
        L_0x0049:
            java.util.Iterator r7 = r0.iterator()
        L_0x004d:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x0065
            java.lang.Object r8 = r7.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r8 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r8
            java.util.ArrayList r9 = r10.listeners
            boolean r9 = r9.contains(r8)
            if (r9 == 0) goto L_0x004d
            r8.onDensityOrFontScaleChanged()
            goto L_0x004d
        L_0x0065:
            r10.density = r2
            r10.fontScale = r1
        L_0x0069:
            int r1 = r11.smallestScreenWidthDp
            int r2 = r10.smallestScreenWidth
            if (r1 == r2) goto L_0x008d
            r10.smallestScreenWidth = r1
            java.util.Iterator r1 = r0.iterator()
        L_0x0075:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x008d
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r2 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r2
            java.util.ArrayList r7 = r10.listeners
            boolean r7 = r7.contains(r2)
            if (r7 == 0) goto L_0x0075
            r2.onSmallestScreenWidthChanged()
            goto L_0x0075
        L_0x008d:
            android.app.WindowConfiguration r1 = r11.windowConfiguration
            android.graphics.Rect r1 = r1.getMaxBounds()
            android.graphics.Rect r2 = r10.maxBounds
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r2 != 0) goto L_0x00b9
            r10.maxBounds = r1
            java.util.Iterator r1 = r0.iterator()
        L_0x00a1:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x00b9
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r2 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r2
            java.util.ArrayList r7 = r10.listeners
            boolean r7 = r7.contains(r2)
            if (r7 == 0) goto L_0x00a1
            r2.onMaxBoundsChanged()
            goto L_0x00a1
        L_0x00b9:
            android.os.LocaleList r1 = r11.getLocales()
            android.os.LocaleList r2 = r10.localeList
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r2 != 0) goto L_0x00e3
            r10.localeList = r1
            java.util.Iterator r1 = r0.iterator()
        L_0x00cb:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x00e3
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r2 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r2
            java.util.ArrayList r7 = r10.listeners
            boolean r7 = r7.contains(r2)
            if (r7 == 0) goto L_0x00cb
            r2.onLocaleListChanged()
            goto L_0x00cb
        L_0x00e3:
            if (r4 == 0) goto L_0x0112
            android.content.Context r1 = r10.context
            android.content.res.Resources$Theme r1 = r1.getTheme()
            android.content.Context r2 = r10.context
            int r2 = r2.getThemeResId()
            r1.applyStyle(r2, r6)
            r10.uiMode = r3
            java.util.Iterator r1 = r0.iterator()
        L_0x00fa:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0112
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r2 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r2
            java.util.ArrayList r3 = r10.listeners
            boolean r3 = r3.contains(r2)
            if (r3 == 0) goto L_0x00fa
            r2.onUiModeChanged()
            goto L_0x00fa
        L_0x0112:
            int r1 = r10.layoutDirection
            int r2 = r11.getLayoutDirection()
            if (r1 == r2) goto L_0x0143
            int r1 = r11.getLayoutDirection()
            r10.layoutDirection = r1
            java.util.Iterator r1 = r0.iterator()
        L_0x0124:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0143
            java.lang.Object r2 = r1.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r2 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r2
            java.util.ArrayList r3 = r10.listeners
            boolean r3 = r3.contains(r2)
            if (r3 == 0) goto L_0x0124
            int r3 = r10.layoutDirection
            if (r3 != r6) goto L_0x013e
            r3 = r6
            goto L_0x013f
        L_0x013e:
            r3 = r5
        L_0x013f:
            r2.onLayoutDirectionChanged(r3)
            goto L_0x0124
        L_0x0143:
            android.content.res.Configuration r1 = r10.lastConfig
            int r11 = r1.updateFrom(r11)
            r1 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = r11 & r1
            if (r11 == 0) goto L_0x016a
            java.util.Iterator r11 = r0.iterator()
        L_0x0152:
            boolean r0 = r11.hasNext()
            if (r0 == 0) goto L_0x016a
            java.lang.Object r0 = r11.next()
            com.android.systemui.statusbar.policy.ConfigurationController$ConfigurationListener r0 = (com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener) r0
            java.util.ArrayList r1 = r10.listeners
            boolean r1 = r1.contains(r0)
            if (r1 == 0) goto L_0x0152
            r0.onThemeChanged()
            goto L_0x0152
        L_0x016a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.ConfigurationControllerImpl.onConfigurationChanged(android.content.res.Configuration):void");
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((ConfigurationController.ConfigurationListener) obj);
    }

    public ConfigurationControllerImpl(Context context2) {
        boolean z;
        Configuration configuration = context2.getResources().getConfiguration();
        this.context = context2;
        this.fontScale = configuration.fontScale;
        this.density = configuration.densityDpi;
        this.smallestScreenWidth = configuration.smallestScreenWidthDp;
        int i = configuration.uiMode;
        if ((i & 15) == 3) {
            z = true;
        } else {
            z = false;
        }
        this.inCarMode = z;
        this.uiMode = i & 48;
        this.localeList = configuration.getLocales();
        this.layoutDirection = configuration.getLayoutDirection();
    }
}
