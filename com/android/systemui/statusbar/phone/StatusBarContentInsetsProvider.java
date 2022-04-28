package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.LruCache;
import android.util.Pair;
import android.view.DisplayCutout;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.preference.R$id;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: StatusBarContentInsetsProvider.kt */
public final class StatusBarContentInsetsProvider implements CallbackController<StatusBarContentInsetsChangedListener>, ConfigurationController.ConfigurationListener, Dumpable {
    public final ConfigurationController configurationController;
    public final Context context;
    public final LruCache<CacheKey, Rect> insetsCache = new LruCache<>(16);
    public final Lazy isPrivacyDotEnabled$delegate = LazyKt__LazyJVMKt.lazy$1(new StatusBarContentInsetsProvider$isPrivacyDotEnabled$2(this));
    public final LinkedHashSet listeners = new LinkedHashSet();

    /* compiled from: StatusBarContentInsetsProvider.kt */
    public static final class CacheKey {
        public final int rotation;
        public final String uniqueDisplayId;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CacheKey)) {
                return false;
            }
            CacheKey cacheKey = (CacheKey) obj;
            return Intrinsics.areEqual(this.uniqueDisplayId, cacheKey.uniqueDisplayId) && this.rotation == cacheKey.rotation;
        }

        public final int hashCode() {
            return Integer.hashCode(this.rotation) + (this.uniqueDisplayId.hashCode() * 31);
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CacheKey(uniqueDisplayId=");
            m.append(this.uniqueDisplayId);
            m.append(", rotation=");
            return Insets$$ExternalSyntheticOutline0.m11m(m, this.rotation, ')');
        }

        public CacheKey(String str, int i) {
            this.uniqueDisplayId = str;
            this.rotation = i;
        }
    }

    public final void addCallback(Object obj) {
        this.listeners.add((StatusBarContentInsetsChangedListener) obj);
    }

    public final boolean currentRotationHasCornerCutout() {
        DisplayCutout cutout = this.context.getDisplay().getCutout();
        if (cutout == null) {
            return false;
        }
        Rect boundingRectTop = cutout.getBoundingRectTop();
        Point point = new Point();
        this.context.getDisplay().getRealSize(point);
        if (boundingRectTop.left <= 0 || boundingRectTop.right >= point.y) {
            return true;
        }
        return false;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        for (Map.Entry next : this.insetsCache.snapshot().entrySet()) {
            printWriter.println(((CacheKey) next.getKey()) + " -> " + ((Rect) next.getValue()));
        }
        printWriter.println(this.insetsCache);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0197, code lost:
        if (r3.right >= r14) goto L_0x0199;
     */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01b5  */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x019e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.Rect getAndSetCalculatedAreaForRotation(int r20, android.content.res.Resources r21, com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider.CacheKey r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r21
            android.content.Context r3 = r0.context
            android.view.Display r3 = r3.getDisplay()
            android.view.DisplayCutout r3 = r3.getCutout()
            android.content.Context r4 = r0.context
            int r4 = androidx.preference.R$id.getExactRotation(r4)
            r5 = 2131166936(0x7f0706d8, float:1.7948131E38)
            int r5 = r2.getDimensionPixelSize(r5)
            kotlin.Lazy r6 = r0.isPrivacyDotEnabled$delegate
            java.lang.Object r6 = r6.getValue()
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r6 = r6.booleanValue()
            r7 = 0
            if (r6 == 0) goto L_0x0034
            r6 = 2131166708(0x7f0705f4, float:1.7947669E38)
            int r6 = r2.getDimensionPixelSize(r6)
            goto L_0x0035
        L_0x0034:
            r6 = r7
        L_0x0035:
            kotlin.Lazy r8 = r0.isPrivacyDotEnabled$delegate
            java.lang.Object r8 = r8.getValue()
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto L_0x004b
            r8 = 2131166707(0x7f0705f3, float:1.7947667E38)
            int r2 = r2.getDimensionPixelSize(r8)
            goto L_0x004c
        L_0x004b:
            r2 = r7
        L_0x004c:
            com.android.systemui.statusbar.policy.ConfigurationController r8 = r0.configurationController
            boolean r8 = r8.isLayoutRtl()
            if (r8 == 0) goto L_0x005e
            int r6 = java.lang.Math.max(r6, r5)
            r18 = r6
            r6 = r5
            r5 = r18
            goto L_0x0062
        L_0x005e:
            int r6 = java.lang.Math.max(r6, r5)
        L_0x0062:
            android.content.Context r8 = r0.context
            android.content.res.Resources r8 = r8.getResources()
            android.content.res.Configuration r8 = r8.getConfiguration()
            android.app.WindowConfiguration r8 = r8.windowConfiguration
            android.graphics.Rect r8 = r8.getMaxBounds()
            android.content.Context r9 = r0.context
            int r9 = com.android.internal.policy.SystemBarUtils.getStatusBarHeightForRotation(r9, r1)
            com.android.systemui.statusbar.policy.ConfigurationController r10 = r0.configurationController
            boolean r10 = r10.isLayoutRtl()
            r11 = 2
            if (r4 == 0) goto L_0x008d
            if (r4 == r11) goto L_0x008d
            android.graphics.Rect r12 = new android.graphics.Rect
            int r13 = r8.bottom
            int r14 = r8.right
            r12.<init>(r7, r7, r13, r14)
            goto L_0x008e
        L_0x008d:
            r12 = r8
        L_0x008e:
            int r13 = r12.right
            int r12 = r12.bottom
            int r14 = r8.width()
            int r8 = r8.height()
            r15 = 1
            if (r1 == r15) goto L_0x00a3
            r7 = 3
            if (r1 != r7) goto L_0x00a1
            goto L_0x00a3
        L_0x00a1:
            r7 = 0
            goto L_0x00a4
        L_0x00a3:
            r7 = r15
        L_0x00a4:
            if (r7 == 0) goto L_0x00a7
            r13 = r12
        L_0x00a7:
            if (r3 != 0) goto L_0x00ab
            r3 = 0
            goto L_0x00af
        L_0x00ab:
            java.util.List r3 = r3.getBoundingRects()
        L_0x00af:
            if (r3 == 0) goto L_0x01c1
            boolean r7 = r3.isEmpty()
            if (r7 == 0) goto L_0x00b9
            goto L_0x01c1
        L_0x00b9:
            int r4 = r4 - r1
            if (r4 >= 0) goto L_0x00be
            int r4 = r4 + 4
        L_0x00be:
            android.util.Pair r1 = new android.util.Pair
            java.lang.Integer r7 = java.lang.Integer.valueOf(r14)
            java.lang.Integer r12 = java.lang.Integer.valueOf(r8)
            r1.<init>(r7, r12)
            java.lang.Object r7 = r1.first
            java.lang.Integer r7 = (java.lang.Integer) r7
            java.lang.Object r1 = r1.second
            java.lang.Integer r1 = (java.lang.Integer) r1
            if (r4 == 0) goto L_0x010d
            if (r4 == r15) goto L_0x0102
            if (r4 == r11) goto L_0x00ee
            android.graphics.Rect r12 = new android.graphics.Rect
            int r17 = r7.intValue()
            int r11 = r17 - r9
            int r7 = r7.intValue()
            int r1 = r1.intValue()
            r15 = 0
            r12.<init>(r11, r15, r7, r1)
            goto L_0x0117
        L_0x00ee:
            r15 = 0
            android.graphics.Rect r12 = new android.graphics.Rect
            int r11 = r1.intValue()
            int r11 = r11 - r9
            int r7 = r7.intValue()
            int r1 = r1.intValue()
            r12.<init>(r15, r11, r7, r1)
            goto L_0x0117
        L_0x0102:
            r15 = 0
            android.graphics.Rect r12 = new android.graphics.Rect
            int r1 = r1.intValue()
            r12.<init>(r15, r15, r9, r1)
            goto L_0x0117
        L_0x010d:
            r15 = 0
            android.graphics.Rect r12 = new android.graphics.Rect
            int r1 = r7.intValue()
            r12.<init>(r15, r15, r1, r9)
        L_0x0117:
            java.util.Iterator r1 = r3.iterator()
        L_0x011b:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x01b9
            java.lang.Object r3 = r1.next()
            android.graphics.Rect r3 = (android.graphics.Rect) r3
            if (r14 >= r8) goto L_0x0134
            int r7 = r3.top
            int r11 = r3.bottom
            boolean r16 = r12.intersects(r15, r7, r14, r11)
            r15 = r16
            goto L_0x0141
        L_0x0134:
            if (r14 <= r8) goto L_0x0140
            int r7 = r3.left
            int r11 = r3.right
            boolean r7 = r12.intersects(r7, r15, r11, r8)
            r15 = r7
            goto L_0x0141
        L_0x0140:
            r15 = 0
        L_0x0141:
            if (r15 != 0) goto L_0x0144
            goto L_0x017a
        L_0x0144:
            if (r4 == 0) goto L_0x015b
            r7 = 1
            if (r4 == r7) goto L_0x0156
            r7 = 2
            if (r4 == r7) goto L_0x0151
            int r7 = r3.top
            if (r7 > 0) goto L_0x0161
            goto L_0x015f
        L_0x0151:
            int r7 = r3.right
            if (r7 < r14) goto L_0x0161
            goto L_0x015f
        L_0x0156:
            int r7 = r3.bottom
            if (r7 < r8) goto L_0x0161
            goto L_0x015f
        L_0x015b:
            int r7 = r3.left
            if (r7 > 0) goto L_0x0161
        L_0x015f:
            r15 = 1
            goto L_0x0162
        L_0x0161:
            r15 = 0
        L_0x0162:
            if (r15 == 0) goto L_0x017d
            if (r4 == 0) goto L_0x016e
            r7 = 2
            if (r4 == r7) goto L_0x016e
            int r3 = r3.height()
            goto L_0x0172
        L_0x016e:
            int r3 = r3.width()
        L_0x0172:
            if (r10 == 0) goto L_0x0175
            int r3 = r3 + r2
        L_0x0175:
            int r3 = java.lang.Math.max(r3, r5)
            r5 = r3
        L_0x017a:
            r7 = 2
            r15 = 1
            goto L_0x01b6
        L_0x017d:
            if (r4 == 0) goto L_0x0194
            r15 = 1
            if (r4 == r15) goto L_0x018f
            r7 = 2
            if (r4 == r7) goto L_0x018a
            int r7 = r3.bottom
            if (r7 < r8) goto L_0x019b
            goto L_0x0199
        L_0x018a:
            int r7 = r3.left
            if (r7 > 0) goto L_0x019b
            goto L_0x0199
        L_0x018f:
            int r7 = r3.top
            if (r7 > 0) goto L_0x019b
            goto L_0x0199
        L_0x0194:
            r15 = 1
            int r7 = r3.right
            if (r7 < r14) goto L_0x019b
        L_0x0199:
            r7 = r15
            goto L_0x019c
        L_0x019b:
            r7 = 0
        L_0x019c:
            if (r7 == 0) goto L_0x01b5
            r7 = 2
            if (r4 == 0) goto L_0x01a8
            if (r4 == r7) goto L_0x01a8
            int r3 = r3.height()
            goto L_0x01ac
        L_0x01a8:
            int r3 = r3.width()
        L_0x01ac:
            if (r10 != 0) goto L_0x01af
            int r3 = r3 + r2
        L_0x01af:
            int r3 = java.lang.Math.max(r6, r3)
            r6 = r3
            goto L_0x01b6
        L_0x01b5:
            r7 = 2
        L_0x01b6:
            r15 = 0
            goto L_0x011b
        L_0x01b9:
            android.graphics.Rect r1 = new android.graphics.Rect
            int r13 = r13 - r6
            r2 = 0
            r1.<init>(r5, r2, r13, r9)
            goto L_0x01c8
        L_0x01c1:
            r2 = 0
            android.graphics.Rect r1 = new android.graphics.Rect
            int r13 = r13 - r6
            r1.<init>(r5, r2, r13, r9)
        L_0x01c8:
            android.util.LruCache<com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider$CacheKey, android.graphics.Rect> r0 = r0.insetsCache
            r2 = r22
            r0.put(r2, r1)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider.getAndSetCalculatedAreaForRotation(int, android.content.res.Resources, com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider$CacheKey):android.graphics.Rect");
    }

    public final Rect getBoundingRectForPrivacyChipForRotation(int i) {
        Rect rect = this.insetsCache.get(new CacheKey(this.context.getDisplay().getUniqueId(), i));
        if (rect == null) {
            rect = getStatusBarContentAreaForRotation(i);
        }
        Resources resourcesForRotation = R$id.getResourcesForRotation(i, this.context);
        return StatusBarContentInsetsProviderKt.getPrivacyChipBoundingRectForInsets(rect, resourcesForRotation.getDimensionPixelSize(C1777R.dimen.ongoing_appops_dot_diameter), resourcesForRotation.getDimensionPixelSize(C1777R.dimen.ongoing_appops_chip_max_width), this.configurationController.isLayoutRtl());
    }

    public final Rect getStatusBarContentAreaForRotation(int i) {
        CacheKey cacheKey = new CacheKey(this.context.getDisplay().getUniqueId(), i);
        Rect rect = this.insetsCache.get(cacheKey);
        if (rect == null) {
            return getAndSetCalculatedAreaForRotation(i, R$id.getResourcesForRotation(i, this.context), cacheKey);
        }
        return rect;
    }

    public final Pair<Integer, Integer> getStatusBarContentInsetsForCurrentRotation() {
        int i;
        int exactRotation = R$id.getExactRotation(this.context);
        CacheKey cacheKey = new CacheKey(this.context.getDisplay().getUniqueId(), exactRotation);
        Point point = new Point();
        this.context.getDisplay().getRealSize(point);
        int exactRotation2 = R$id.getExactRotation(this.context);
        if (!(exactRotation2 == 0 || exactRotation2 == 2)) {
            int i2 = point.y;
            point.y = point.x;
            point.x = i2;
        }
        if (exactRotation == 0 || exactRotation == 2) {
            i = point.x;
        } else {
            i = point.y;
        }
        Rect rect = this.insetsCache.get(cacheKey);
        if (rect == null) {
            rect = getAndSetCalculatedAreaForRotation(exactRotation, R$id.getResourcesForRotation(exactRotation, this.context), cacheKey);
        }
        return new Pair<>(Integer.valueOf(rect.left), Integer.valueOf(i - rect.right));
    }

    public final int getStatusBarPaddingTop(Integer num) {
        Resources resources;
        if (num == null) {
            resources = null;
        } else {
            resources = R$id.getResourcesForRotation(num.intValue(), this.context);
        }
        if (resources == null) {
            resources = this.context.getResources();
        }
        return resources.getDimensionPixelSize(C1777R.dimen.status_bar_padding_top);
    }

    public final void onDensityOrFontScaleChanged() {
        this.insetsCache.evictAll();
        for (StatusBarContentInsetsChangedListener onStatusBarContentInsetsChanged : this.listeners) {
            onStatusBarContentInsetsChanged.onStatusBarContentInsetsChanged();
        }
    }

    public final void onMaxBoundsChanged() {
        for (StatusBarContentInsetsChangedListener onStatusBarContentInsetsChanged : this.listeners) {
            onStatusBarContentInsetsChanged.onStatusBarContentInsetsChanged();
        }
    }

    public final void onThemeChanged() {
        this.insetsCache.evictAll();
        for (StatusBarContentInsetsChangedListener onStatusBarContentInsetsChanged : this.listeners) {
            onStatusBarContentInsetsChanged.onStatusBarContentInsetsChanged();
        }
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((StatusBarContentInsetsChangedListener) obj);
    }

    public StatusBarContentInsetsProvider(Context context2, ConfigurationController configurationController2, DumpManager dumpManager) {
        this.context = context2;
        this.configurationController = configurationController2;
        configurationController2.addCallback(this);
        dumpManager.registerDumpable("StatusBarInsetsProvider", this);
    }
}
