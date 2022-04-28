package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.collection.LongSparseArray;
import androidx.collection.LruCache;
import androidx.collection.SimpleArrayMap;
import androidx.collection.SparseArrayCompat;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.WeakHashMap;

public final class ResourceManagerInternal {
    public static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache();
    public static final PorterDuff.Mode DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
    public static ResourceManagerInternal INSTANCE;
    public SimpleArrayMap<String, InflateDelegate> mDelegates;
    public final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap<>(0);
    public boolean mHasCheckedVectorDrawableSetup;
    public ResourceManagerHooks mHooks;
    public SparseArrayCompat<String> mKnownDrawableIdTags;
    public WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
    public TypedValue mTypedValue;

    public static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache() {
            super(6);
        }
    }

    public interface InflateDelegate {
        Drawable createFromXmlInner(Context context, XmlResourceParser xmlResourceParser, AttributeSet attributeSet, Resources.Theme theme);
    }

    public interface ResourceManagerHooks {
    }

    public final synchronized boolean addDrawableToCache(Context context, long j, Drawable drawable) {
        Drawable.ConstantState constantState = drawable.getConstantState();
        if (constantState == null) {
            return false;
        }
        LongSparseArray longSparseArray = this.mDrawableCaches.get(context);
        if (longSparseArray == null) {
            longSparseArray = new LongSparseArray();
            this.mDrawableCaches.put(context, longSparseArray);
        }
        longSparseArray.put(j, new WeakReference(constantState));
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0032, code lost:
        r5 = r0.mValues;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0040, code lost:
        return null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized android.graphics.drawable.Drawable getCachedDrawable(android.content.Context r4, long r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            java.util.WeakHashMap<android.content.Context, androidx.collection.LongSparseArray<java.lang.ref.WeakReference<android.graphics.drawable.Drawable$ConstantState>>> r0 = r3.mDrawableCaches     // Catch:{ all -> 0x0041 }
            java.lang.Object r0 = r0.get(r4)     // Catch:{ all -> 0x0041 }
            androidx.collection.LongSparseArray r0 = (androidx.collection.LongSparseArray) r0     // Catch:{ all -> 0x0041 }
            r1 = 0
            if (r0 != 0) goto L_0x000e
            monitor-exit(r3)
            return r1
        L_0x000e:
            java.lang.Object r2 = r0.get(r5, r1)     // Catch:{ all -> 0x0041 }
            java.lang.ref.WeakReference r2 = (java.lang.ref.WeakReference) r2     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x003f
            java.lang.Object r2 = r2.get()     // Catch:{ all -> 0x0041 }
            android.graphics.drawable.Drawable$ConstantState r2 = (android.graphics.drawable.Drawable.ConstantState) r2     // Catch:{ all -> 0x0041 }
            if (r2 == 0) goto L_0x0028
            android.content.res.Resources r4 = r4.getResources()     // Catch:{ all -> 0x0041 }
            android.graphics.drawable.Drawable r4 = r2.newDrawable(r4)     // Catch:{ all -> 0x0041 }
            monitor-exit(r3)
            return r4
        L_0x0028:
            long[] r4 = r0.mKeys     // Catch:{ all -> 0x0041 }
            int r2 = r0.mSize     // Catch:{ all -> 0x0041 }
            int r4 = androidx.recyclerview.R$dimen.binarySearch((long[]) r4, (int) r2, (long) r5)     // Catch:{ all -> 0x0041 }
            if (r4 < 0) goto L_0x003f
            java.lang.Object[] r5 = r0.mValues     // Catch:{ all -> 0x0041 }
            r6 = r5[r4]     // Catch:{ all -> 0x0041 }
            java.lang.Object r2 = androidx.collection.LongSparseArray.DELETED     // Catch:{ all -> 0x0041 }
            if (r6 == r2) goto L_0x003f
            r5[r4] = r2     // Catch:{ all -> 0x0041 }
            r4 = 1
            r0.mGarbage = r4     // Catch:{ all -> 0x0041 }
        L_0x003f:
            monitor-exit(r3)
            return r1
        L_0x0041:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ResourceManagerInternal.getCachedDrawable(android.content.Context, long):android.graphics.drawable.Drawable");
    }

    public final synchronized Drawable getDrawable(Context context, int i) {
        return getDrawable(context, i, false);
    }

    public final synchronized ColorStateList getTintList(Context context, int i) {
        ColorStateList colorStateList;
        SparseArrayCompat sparseArrayCompat;
        try {
            WeakHashMap<Context, SparseArrayCompat<ColorStateList>> weakHashMap = this.mTintLists;
            ColorStateList colorStateList2 = null;
            if (weakHashMap == null || (sparseArrayCompat = weakHashMap.get(context)) == null) {
                colorStateList = null;
            } else {
                colorStateList = (ColorStateList) sparseArrayCompat.get(i, (Integer) null);
            }
            if (colorStateList == null) {
                ResourceManagerHooks resourceManagerHooks = this.mHooks;
                if (resourceManagerHooks != null) {
                    colorStateList2 = ((AppCompatDrawableManager.C00621) resourceManagerHooks).getTintListForDrawableRes(context, i);
                }
                if (colorStateList2 != null) {
                    if (this.mTintLists == null) {
                        this.mTintLists = new WeakHashMap<>();
                    }
                    SparseArrayCompat sparseArrayCompat2 = this.mTintLists.get(context);
                    if (sparseArrayCompat2 == null) {
                        sparseArrayCompat2 = new SparseArrayCompat();
                        this.mTintLists.put(context, sparseArrayCompat2);
                    }
                    sparseArrayCompat2.append(i, colorStateList2);
                }
                colorStateList = colorStateList2;
            }
        } catch (Throwable th) {
            throw th;
        }
        return colorStateList;
    }

    public static synchronized ResourceManagerInternal get() {
        ResourceManagerInternal resourceManagerInternal;
        synchronized (ResourceManagerInternal.class) {
            if (INSTANCE == null) {
                INSTANCE = new ResourceManagerInternal();
            }
            resourceManagerInternal = INSTANCE;
        }
        return resourceManagerInternal;
    }

    public static synchronized PorterDuffColorFilter getPorterDuffColorFilter(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        synchronized (ResourceManagerInternal.class) {
            ColorFilterLruCache colorFilterLruCache = COLOR_FILTER_CACHE;
            Objects.requireNonNull(colorFilterLruCache);
            int i2 = (i + 31) * 31;
            porterDuffColorFilter = (PorterDuffColorFilter) colorFilterLruCache.get(Integer.valueOf(mode.hashCode() + i2));
            if (porterDuffColorFilter == null) {
                porterDuffColorFilter = new PorterDuffColorFilter(i, mode);
                Objects.requireNonNull(colorFilterLruCache);
                PorterDuffColorFilter porterDuffColorFilter2 = (PorterDuffColorFilter) colorFilterLruCache.put(Integer.valueOf(mode.hashCode() + i2), porterDuffColorFilter);
            }
        }
        return porterDuffColorFilter;
    }

    public final Drawable createDrawableIfNeeded(Context context, int i) {
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        context.getResources().getValue(i, typedValue, true);
        long j = (((long) typedValue.assetCookie) << 32) | ((long) typedValue.data);
        Drawable cachedDrawable = getCachedDrawable(context, j);
        if (cachedDrawable != null) {
            return cachedDrawable;
        }
        LayerDrawable layerDrawable = null;
        if (this.mHooks != null) {
            if (i == C1777R.C1778drawable.abc_cab_background_top_material) {
                layerDrawable = new LayerDrawable(new Drawable[]{getDrawable(context, C1777R.C1778drawable.abc_cab_background_internal_bg), getDrawable(context, C1777R.C1778drawable.abc_cab_background_top_mtrl_alpha)});
            } else if (i == C1777R.C1778drawable.abc_ratingbar_material) {
                layerDrawable = AppCompatDrawableManager.C00621.getRatingBarLayerDrawable(this, context, C1777R.dimen.abc_star_big);
            } else if (i == C1777R.C1778drawable.abc_ratingbar_indicator_material) {
                layerDrawable = AppCompatDrawableManager.C00621.getRatingBarLayerDrawable(this, context, C1777R.dimen.abc_star_medium);
            } else if (i == C1777R.C1778drawable.abc_ratingbar_small_material) {
                layerDrawable = AppCompatDrawableManager.C00621.getRatingBarLayerDrawable(this, context, C1777R.dimen.abc_star_small);
            }
        }
        if (layerDrawable != null) {
            layerDrawable.setChangingConfigurations(typedValue.changingConfigurations);
            addDrawableToCache(context, j, layerDrawable);
        }
        return layerDrawable;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v1, resolved type: android.graphics.drawable.Drawable} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: android.graphics.PorterDuff$Mode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: android.graphics.PorterDuff$Mode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v10, resolved type: android.graphics.PorterDuff$Mode} */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x002b, code lost:
        if (r0 == false) goto L_0x01ac;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01a8 A[Catch:{ Exception -> 0x00dd, all -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00a9 A[Catch:{ Exception -> 0x00dd, all -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00d5 A[Catch:{ Exception -> 0x00dd, all -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00f0 A[Catch:{ Exception -> 0x00dd, all -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x00f6 A[Catch:{ Exception -> 0x00dd, all -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00fe A[Catch:{ Exception -> 0x00dd, all -> 0x00d2 }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x019a A[Catch:{ Exception -> 0x00dd, all -> 0x00d2 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized android.graphics.drawable.Drawable getDrawable(android.content.Context r13, int r14, boolean r15) {
        /*
            r12 = this;
            monitor-enter(r12)
            boolean r0 = r12.mHasCheckedVectorDrawableSetup     // Catch:{ all -> 0x00d2 }
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x0008
            goto L_0x002d
        L_0x0008:
            r12.mHasCheckedVectorDrawableSetup = r2     // Catch:{ all -> 0x00d2 }
            r0 = 2131231586(0x7f080362, float:1.8079257E38)
            android.graphics.drawable.Drawable r0 = r12.getDrawable(r13, r0)     // Catch:{ all -> 0x00d2 }
            if (r0 == 0) goto L_0x01ac
            boolean r3 = r0 instanceof androidx.vectordrawable.graphics.drawable.VectorDrawableCompat     // Catch:{ all -> 0x00d2 }
            if (r3 != 0) goto L_0x002a
            java.lang.Class r0 = r0.getClass()     // Catch:{ all -> 0x00d2 }
            java.lang.String r0 = r0.getName()     // Catch:{ all -> 0x00d2 }
            java.lang.String r3 = "android.graphics.drawable.VectorDrawable"
            boolean r0 = r3.equals(r0)     // Catch:{ all -> 0x00d2 }
            if (r0 == 0) goto L_0x0028
            goto L_0x002a
        L_0x0028:
            r0 = r1
            goto L_0x002b
        L_0x002a:
            r0 = r2
        L_0x002b:
            if (r0 == 0) goto L_0x01ac
        L_0x002d:
            androidx.collection.SimpleArrayMap<java.lang.String, androidx.appcompat.widget.ResourceManagerInternal$InflateDelegate> r0 = r12.mDelegates     // Catch:{ all -> 0x00d2 }
            r3 = 0
            if (r0 == 0) goto L_0x00ed
            boolean r0 = r0.isEmpty()     // Catch:{ all -> 0x00d2 }
            if (r0 != 0) goto L_0x00ed
            androidx.collection.SparseArrayCompat<java.lang.String> r0 = r12.mKnownDrawableIdTags     // Catch:{ all -> 0x00d2 }
            java.lang.String r4 = "appcompat_skip_skip"
            if (r0 == 0) goto L_0x0059
            java.lang.Object r0 = r0.get(r14, r3)     // Catch:{ all -> 0x00d2 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ all -> 0x00d2 }
            boolean r5 = r4.equals(r0)     // Catch:{ all -> 0x00d2 }
            if (r5 != 0) goto L_0x00ed
            if (r0 == 0) goto L_0x0060
            androidx.collection.SimpleArrayMap<java.lang.String, androidx.appcompat.widget.ResourceManagerInternal$InflateDelegate> r5 = r12.mDelegates     // Catch:{ all -> 0x00d2 }
            java.util.Objects.requireNonNull(r5)     // Catch:{ all -> 0x00d2 }
            java.lang.Object r0 = r5.getOrDefault(r0, r3)     // Catch:{ all -> 0x00d2 }
            if (r0 != 0) goto L_0x0060
            goto L_0x00ed
        L_0x0059:
            androidx.collection.SparseArrayCompat r0 = new androidx.collection.SparseArrayCompat     // Catch:{ all -> 0x00d2 }
            r0.<init>()     // Catch:{ all -> 0x00d2 }
            r12.mKnownDrawableIdTags = r0     // Catch:{ all -> 0x00d2 }
        L_0x0060:
            android.util.TypedValue r0 = r12.mTypedValue     // Catch:{ all -> 0x00d2 }
            if (r0 != 0) goto L_0x006b
            android.util.TypedValue r0 = new android.util.TypedValue     // Catch:{ all -> 0x00d2 }
            r0.<init>()     // Catch:{ all -> 0x00d2 }
            r12.mTypedValue = r0     // Catch:{ all -> 0x00d2 }
        L_0x006b:
            android.util.TypedValue r0 = r12.mTypedValue     // Catch:{ all -> 0x00d2 }
            android.content.res.Resources r5 = r13.getResources()     // Catch:{ all -> 0x00d2 }
            r5.getValue(r14, r0, r2)     // Catch:{ all -> 0x00d2 }
            int r6 = r0.assetCookie     // Catch:{ all -> 0x00d2 }
            long r6 = (long) r6     // Catch:{ all -> 0x00d2 }
            r8 = 32
            long r6 = r6 << r8
            int r8 = r0.data     // Catch:{ all -> 0x00d2 }
            long r8 = (long) r8     // Catch:{ all -> 0x00d2 }
            long r6 = r6 | r8
            android.graphics.drawable.Drawable r8 = r12.getCachedDrawable(r13, r6)     // Catch:{ all -> 0x00d2 }
            if (r8 == 0) goto L_0x0085
            goto L_0x00ee
        L_0x0085:
            java.lang.CharSequence r9 = r0.string     // Catch:{ all -> 0x00d2 }
            if (r9 == 0) goto L_0x00e5
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00d2 }
            java.lang.String r10 = ".xml"
            boolean r9 = r9.endsWith(r10)     // Catch:{ all -> 0x00d2 }
            if (r9 == 0) goto L_0x00e5
            android.content.res.XmlResourceParser r5 = r5.getXml(r14)     // Catch:{ Exception -> 0x00dd }
            android.util.AttributeSet r9 = android.util.Xml.asAttributeSet(r5)     // Catch:{ Exception -> 0x00dd }
        L_0x009d:
            int r10 = r5.next()     // Catch:{ Exception -> 0x00dd }
            r11 = 2
            if (r10 == r11) goto L_0x00a7
            if (r10 == r2) goto L_0x00a7
            goto L_0x009d
        L_0x00a7:
            if (r10 != r11) goto L_0x00d5
            java.lang.String r10 = r5.getName()     // Catch:{ Exception -> 0x00dd }
            androidx.collection.SparseArrayCompat<java.lang.String> r11 = r12.mKnownDrawableIdTags     // Catch:{ Exception -> 0x00dd }
            r11.append(r14, r10)     // Catch:{ Exception -> 0x00dd }
            androidx.collection.SimpleArrayMap<java.lang.String, androidx.appcompat.widget.ResourceManagerInternal$InflateDelegate> r11 = r12.mDelegates     // Catch:{ Exception -> 0x00dd }
            java.util.Objects.requireNonNull(r11)     // Catch:{ Exception -> 0x00dd }
            java.lang.Object r10 = r11.getOrDefault(r10, r3)     // Catch:{ Exception -> 0x00dd }
            androidx.appcompat.widget.ResourceManagerInternal$InflateDelegate r10 = (androidx.appcompat.widget.ResourceManagerInternal.InflateDelegate) r10     // Catch:{ Exception -> 0x00dd }
            if (r10 == 0) goto L_0x00c7
            android.content.res.Resources$Theme r11 = r13.getTheme()     // Catch:{ Exception -> 0x00dd }
            android.graphics.drawable.Drawable r8 = r10.createFromXmlInner(r13, r5, r9, r11)     // Catch:{ Exception -> 0x00dd }
        L_0x00c7:
            if (r8 == 0) goto L_0x00e5
            int r0 = r0.changingConfigurations     // Catch:{ Exception -> 0x00dd }
            r8.setChangingConfigurations(r0)     // Catch:{ Exception -> 0x00dd }
            r12.addDrawableToCache(r13, r6, r8)     // Catch:{ Exception -> 0x00dd }
            goto L_0x00e5
        L_0x00d2:
            r13 = move-exception
            goto L_0x01b6
        L_0x00d5:
            org.xmlpull.v1.XmlPullParserException r0 = new org.xmlpull.v1.XmlPullParserException     // Catch:{ Exception -> 0x00dd }
            java.lang.String r5 = "No start tag found"
            r0.<init>(r5)     // Catch:{ Exception -> 0x00dd }
            throw r0     // Catch:{ Exception -> 0x00dd }
        L_0x00dd:
            r0 = move-exception
            java.lang.String r5 = "ResourceManagerInternal"
            java.lang.String r6 = "Exception while inflating drawable"
            android.util.Log.e(r5, r6, r0)     // Catch:{ all -> 0x00d2 }
        L_0x00e5:
            if (r8 != 0) goto L_0x00ee
            androidx.collection.SparseArrayCompat<java.lang.String> r0 = r12.mKnownDrawableIdTags     // Catch:{ all -> 0x00d2 }
            r0.append(r14, r4)     // Catch:{ all -> 0x00d2 }
            goto L_0x00ee
        L_0x00ed:
            r8 = r3
        L_0x00ee:
            if (r8 != 0) goto L_0x00f4
            android.graphics.drawable.Drawable r8 = r12.createDrawableIfNeeded(r13, r14)     // Catch:{ all -> 0x00d2 }
        L_0x00f4:
            if (r8 != 0) goto L_0x00fc
            java.lang.Object r0 = androidx.core.content.ContextCompat.sLock     // Catch:{ all -> 0x00d2 }
            android.graphics.drawable.Drawable r8 = r13.getDrawable(r14)     // Catch:{ all -> 0x00d2 }
        L_0x00fc:
            if (r8 == 0) goto L_0x01a6
            android.content.res.ColorStateList r0 = r12.getTintList(r13, r14)     // Catch:{ all -> 0x00d2 }
            if (r0 == 0) goto L_0x0124
            boolean r13 = androidx.appcompat.widget.DrawableUtils.canSafelyMutateDrawable(r8)     // Catch:{ all -> 0x00d2 }
            if (r13 == 0) goto L_0x010e
            android.graphics.drawable.Drawable r8 = r8.mutate()     // Catch:{ all -> 0x00d2 }
        L_0x010e:
            r8.setTintList(r0)     // Catch:{ all -> 0x00d2 }
            androidx.appcompat.widget.ResourceManagerInternal$ResourceManagerHooks r13 = r12.mHooks     // Catch:{ all -> 0x00d2 }
            if (r13 != 0) goto L_0x0116
            goto L_0x011d
        L_0x0116:
            r13 = 2131231573(0x7f080355, float:1.807923E38)
            if (r14 != r13) goto L_0x011d
            android.graphics.PorterDuff$Mode r3 = android.graphics.PorterDuff.Mode.MULTIPLY     // Catch:{ all -> 0x00d2 }
        L_0x011d:
            if (r3 == 0) goto L_0x01a4
            r8.setTintMode(r3)     // Catch:{ all -> 0x00d2 }
            goto L_0x01a4
        L_0x0124:
            androidx.appcompat.widget.ResourceManagerInternal$ResourceManagerHooks r0 = r12.mHooks     // Catch:{ all -> 0x00d2 }
            if (r0 == 0) goto L_0x019b
            r0 = 16908301(0x102000d, float:2.3877265E-38)
            r4 = 16908303(0x102000f, float:2.387727E-38)
            r5 = 16908288(0x1020000, float:2.387723E-38)
            r6 = 2130968818(0x7f0400f2, float:1.75463E38)
            r7 = 2130968820(0x7f0400f4, float:1.7546304E38)
            r9 = 2131231564(0x7f08034c, float:1.8079213E38)
            if (r14 != r9) goto L_0x0162
            r1 = r8
            android.graphics.drawable.LayerDrawable r1 = (android.graphics.drawable.LayerDrawable) r1     // Catch:{ all -> 0x00d2 }
            android.graphics.drawable.Drawable r5 = r1.findDrawableByLayerId(r5)     // Catch:{ all -> 0x00d2 }
            int r9 = androidx.appcompat.widget.ThemeUtils.getThemeAttrColor(r13, r7)     // Catch:{ all -> 0x00d2 }
            android.graphics.PorterDuff$Mode r10 = androidx.appcompat.widget.AppCompatDrawableManager.DEFAULT_MODE     // Catch:{ all -> 0x00d2 }
            androidx.appcompat.widget.AppCompatDrawableManager.C00621.setPorterDuffColorFilter(r5, r9, r10)     // Catch:{ all -> 0x00d2 }
            android.graphics.drawable.Drawable r4 = r1.findDrawableByLayerId(r4)     // Catch:{ all -> 0x00d2 }
            int r5 = androidx.appcompat.widget.ThemeUtils.getThemeAttrColor(r13, r7)     // Catch:{ all -> 0x00d2 }
            androidx.appcompat.widget.AppCompatDrawableManager.C00621.setPorterDuffColorFilter(r4, r5, r10)     // Catch:{ all -> 0x00d2 }
            android.graphics.drawable.Drawable r0 = r1.findDrawableByLayerId(r0)     // Catch:{ all -> 0x00d2 }
            int r1 = androidx.appcompat.widget.ThemeUtils.getThemeAttrColor(r13, r6)     // Catch:{ all -> 0x00d2 }
            androidx.appcompat.widget.AppCompatDrawableManager.C00621.setPorterDuffColorFilter(r0, r1, r10)     // Catch:{ all -> 0x00d2 }
            goto L_0x0197
        L_0x0162:
            r9 = 2131231555(0x7f080343, float:1.8079194E38)
            if (r14 == r9) goto L_0x0171
            r9 = 2131231554(0x7f080342, float:1.8079192E38)
            if (r14 == r9) goto L_0x0171
            r9 = 2131231556(0x7f080344, float:1.8079196E38)
            if (r14 != r9) goto L_0x0198
        L_0x0171:
            r1 = r8
            android.graphics.drawable.LayerDrawable r1 = (android.graphics.drawable.LayerDrawable) r1     // Catch:{ all -> 0x00d2 }
            android.graphics.drawable.Drawable r5 = r1.findDrawableByLayerId(r5)     // Catch:{ all -> 0x00d2 }
            int r7 = androidx.appcompat.widget.ThemeUtils.getDisabledThemeAttrColor(r13, r7)     // Catch:{ all -> 0x00d2 }
            android.graphics.PorterDuff$Mode r9 = androidx.appcompat.widget.AppCompatDrawableManager.DEFAULT_MODE     // Catch:{ all -> 0x00d2 }
            androidx.appcompat.widget.AppCompatDrawableManager.C00621.setPorterDuffColorFilter(r5, r7, r9)     // Catch:{ all -> 0x00d2 }
            android.graphics.drawable.Drawable r4 = r1.findDrawableByLayerId(r4)     // Catch:{ all -> 0x00d2 }
            int r5 = androidx.appcompat.widget.ThemeUtils.getThemeAttrColor(r13, r6)     // Catch:{ all -> 0x00d2 }
            androidx.appcompat.widget.AppCompatDrawableManager.C00621.setPorterDuffColorFilter(r4, r5, r9)     // Catch:{ all -> 0x00d2 }
            android.graphics.drawable.Drawable r0 = r1.findDrawableByLayerId(r0)     // Catch:{ all -> 0x00d2 }
            int r1 = androidx.appcompat.widget.ThemeUtils.getThemeAttrColor(r13, r6)     // Catch:{ all -> 0x00d2 }
            androidx.appcompat.widget.AppCompatDrawableManager.C00621.setPorterDuffColorFilter(r0, r1, r9)     // Catch:{ all -> 0x00d2 }
        L_0x0197:
            r1 = r2
        L_0x0198:
            if (r1 == 0) goto L_0x019b
            goto L_0x01a4
        L_0x019b:
            boolean r13 = r12.tintDrawableUsingColorFilter(r13, r14, r8)     // Catch:{ all -> 0x00d2 }
            if (r13 != 0) goto L_0x01a4
            if (r15 == 0) goto L_0x01a4
            goto L_0x01a5
        L_0x01a4:
            r3 = r8
        L_0x01a5:
            r8 = r3
        L_0x01a6:
            if (r8 == 0) goto L_0x01aa
            int[] r13 = androidx.appcompat.widget.DrawableUtils.CHECKED_STATE_SET     // Catch:{ all -> 0x00d2 }
        L_0x01aa:
            monitor-exit(r12)
            return r8
        L_0x01ac:
            r12.mHasCheckedVectorDrawableSetup = r1     // Catch:{ all -> 0x00d2 }
            java.lang.IllegalStateException r13 = new java.lang.IllegalStateException     // Catch:{ all -> 0x00d2 }
            java.lang.String r14 = "This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat."
            r13.<init>(r14)     // Catch:{ all -> 0x00d2 }
            throw r13     // Catch:{ all -> 0x00d2 }
        L_0x01b6:
            monitor-exit(r12)
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ResourceManagerInternal.getDrawable(android.content.Context, int, boolean):android.graphics.drawable.Drawable");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean tintDrawableUsingColorFilter(android.content.Context r7, int r8, android.graphics.drawable.Drawable r9) {
        /*
            r6 = this;
            androidx.appcompat.widget.ResourceManagerInternal$ResourceManagerHooks r6 = r6.mHooks
            r0 = 1
            r1 = 0
            if (r6 == 0) goto L_0x006f
            androidx.appcompat.widget.AppCompatDrawableManager$1 r6 = (androidx.appcompat.widget.AppCompatDrawableManager.C00621) r6
            android.graphics.PorterDuff$Mode r2 = androidx.appcompat.widget.AppCompatDrawableManager.DEFAULT_MODE
            int[] r3 = r6.COLORFILTER_TINT_COLOR_CONTROL_NORMAL
            boolean r3 = androidx.appcompat.widget.AppCompatDrawableManager.C00621.arrayContains(r3, r8)
            r4 = 16842801(0x1010031, float:2.3693695E-38)
            r5 = -1
            if (r3 == 0) goto L_0x001a
            r4 = 2130968820(0x7f0400f4, float:1.7546304E38)
            goto L_0x0046
        L_0x001a:
            int[] r3 = r6.COLORFILTER_COLOR_CONTROL_ACTIVATED
            boolean r3 = androidx.appcompat.widget.AppCompatDrawableManager.C00621.arrayContains(r3, r8)
            if (r3 == 0) goto L_0x0026
            r4 = 2130968818(0x7f0400f2, float:1.75463E38)
            goto L_0x0046
        L_0x0026:
            int[] r6 = r6.COLORFILTER_COLOR_BACKGROUND_MULTIPLY
            boolean r6 = androidx.appcompat.widget.AppCompatDrawableManager.C00621.arrayContains(r6, r8)
            if (r6 == 0) goto L_0x0031
            android.graphics.PorterDuff$Mode r2 = android.graphics.PorterDuff.Mode.MULTIPLY
            goto L_0x0046
        L_0x0031:
            r6 = 2131231541(0x7f080335, float:1.8079166E38)
            if (r8 != r6) goto L_0x0041
            r6 = 16842800(0x1010030, float:2.3693693E-38)
            r8 = 1109603123(0x42233333, float:40.8)
            int r8 = java.lang.Math.round(r8)
            goto L_0x0048
        L_0x0041:
            r6 = 2131231520(0x7f080320, float:1.8079123E38)
            if (r8 != r6) goto L_0x004a
        L_0x0046:
            r6 = r4
            r8 = r5
        L_0x0048:
            r3 = r0
            goto L_0x004d
        L_0x004a:
            r6 = r1
            r3 = r6
            r8 = r5
        L_0x004d:
            if (r3 == 0) goto L_0x006b
            boolean r3 = androidx.appcompat.widget.DrawableUtils.canSafelyMutateDrawable(r9)
            if (r3 == 0) goto L_0x0059
            android.graphics.drawable.Drawable r9 = r9.mutate()
        L_0x0059:
            int r6 = androidx.appcompat.widget.ThemeUtils.getThemeAttrColor(r7, r6)
            android.graphics.PorterDuffColorFilter r6 = androidx.appcompat.widget.AppCompatDrawableManager.getPorterDuffColorFilter(r6, r2)
            r9.setColorFilter(r6)
            if (r8 == r5) goto L_0x0069
            r9.setAlpha(r8)
        L_0x0069:
            r6 = r0
            goto L_0x006c
        L_0x006b:
            r6 = r1
        L_0x006c:
            if (r6 == 0) goto L_0x006f
            goto L_0x0070
        L_0x006f:
            r0 = r1
        L_0x0070:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ResourceManagerInternal.tintDrawableUsingColorFilter(android.content.Context, int, android.graphics.drawable.Drawable):boolean");
    }
}
