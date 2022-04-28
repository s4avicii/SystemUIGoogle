package androidx.coordinatorlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.collection.SimpleArrayMap;
import androidx.coordinatorlayout.R$styleable;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import com.android.p012wm.shell.C1777R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

public class CoordinatorLayout extends ViewGroup implements NestedScrollingParent2, NestedScrollingParent3 {
    public static final Class<?>[] CONSTRUCTOR_PARAMS = {Context.class, AttributeSet.class};
    public static final ViewElevationComparator TOP_SORTED_CHILDREN_COMPARATOR = new ViewElevationComparator();
    public static final String WIDGET_PACKAGE_NAME;
    public static final ThreadLocal<Map<String, Constructor<Behavior>>> sConstructors = new ThreadLocal<>();
    public static final Pools$SynchronizedPool sRectPool = new Pools$SynchronizedPool(12);
    public C01061 mApplyWindowInsetsListener;
    public final int[] mBehaviorConsumed;
    public View mBehaviorTouchView;
    public final DirectedAcyclicGraph mChildDag;
    public final ArrayList mDependencySortedChildren;
    public boolean mDisallowInterceptReset;
    public boolean mDrawStatusBarBackground;
    public boolean mIsAttachedToWindow;
    public int[] mKeylines;
    public WindowInsetsCompat mLastInsets;
    public boolean mNeedsPreDrawListener;
    public final NestedScrollingParentHelper mNestedScrollingParentHelper;
    public View mNestedScrollingTarget;
    public final int[] mNestedScrollingV2ConsumedCompat;
    public ViewGroup.OnHierarchyChangeListener mOnHierarchyChangeListener;
    public OnPreDrawListener mOnPreDrawListener;
    public Drawable mStatusBarBackground;
    public final ArrayList mTempList1;

    public interface AttachedBehavior {
        Behavior getBehavior();
    }

    public static abstract class Behavior<V extends View> {
        public Behavior() {
        }

        public boolean getInsetDodgeRect(View view, Rect rect) {
            return false;
        }

        public boolean layoutDependsOn(View view, View view2) {
            return false;
        }

        public void onAttachedToLayoutParams(LayoutParams layoutParams) {
        }

        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, V v, View view) {
            return false;
        }

        public void onDependentViewRemoved(CoordinatorLayout coordinatorLayout, View view) {
        }

        public void onDetachedFromLayoutParams() {
        }

        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int i) {
            return false;
        }

        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
            return false;
        }

        public boolean onNestedPreFling(View view) {
            return false;
        }

        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i, int i2, int[] iArr, int i3) {
        }

        public void onNestedScroll(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3, int[] iArr) {
            iArr[0] = iArr[0] + i2;
            iArr[1] = iArr[1] + i3;
        }

        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, V v, Rect rect, boolean z) {
            return false;
        }

        public void onRestoreInstanceState(View view, Parcelable parcelable) {
        }

        public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, View view2, int i, int i2) {
            return false;
        }

        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, V v, View view, int i) {
        }

        public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
            return false;
        }

        public Behavior(Context context, AttributeSet attributeSet) {
        }

        public Parcelable onSaveInstanceState(View view) {
            return View.BaseSavedState.EMPTY_STATE;
        }
    }

    @Deprecated
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DefaultBehavior {
        Class<? extends Behavior> value();
    }

    public class HierarchyChangeListener implements ViewGroup.OnHierarchyChangeListener {
        public HierarchyChangeListener() {
        }

        public final void onChildViewAdded(View view, View view2) {
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewAdded(view, view2);
            }
        }

        public final void onChildViewRemoved(View view, View view2) {
            CoordinatorLayout.this.onChildViewsChanged(2);
            ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener = CoordinatorLayout.this.mOnHierarchyChangeListener;
            if (onHierarchyChangeListener != null) {
                onHierarchyChangeListener.onChildViewRemoved(view, view2);
            }
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int anchorGravity = 0;
        public int dodgeInsetEdges = 0;
        public int gravity = 0;
        public int insetEdge = 0;
        public int keyline = -1;
        public View mAnchorDirectChild;
        public int mAnchorId = -1;
        public View mAnchorView;
        public Behavior mBehavior;
        public boolean mBehaviorResolved = false;
        public boolean mDidAcceptNestedScrollNonTouch;
        public boolean mDidAcceptNestedScrollTouch;
        public boolean mDidBlockInteraction;
        public boolean mDidChangeAfterNestedScroll;
        public int mInsetOffsetX;
        public int mInsetOffsetY;
        public final Rect mLastChildRect = new Rect();

        public LayoutParams() {
            super(-2, -2);
        }

        public final boolean isNestedScrollAccepted(int i) {
            if (i == 0) {
                return this.mDidAcceptNestedScrollTouch;
            }
            if (i != 1) {
                return false;
            }
            return this.mDidAcceptNestedScrollNonTouch;
        }

        public final void setBehavior(Behavior behavior) {
            Behavior behavior2 = this.mBehavior;
            if (behavior2 != behavior) {
                if (behavior2 != null) {
                    behavior2.onDetachedFromLayoutParams();
                }
                this.mBehavior = behavior;
                this.mBehaviorResolved = true;
                if (behavior != null) {
                    behavior.onAttachedToLayoutParams(this);
                }
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            Behavior behavior;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout_Layout);
            this.gravity = obtainStyledAttributes.getInteger(0, 0);
            this.mAnchorId = obtainStyledAttributes.getResourceId(1, -1);
            this.anchorGravity = obtainStyledAttributes.getInteger(2, 0);
            this.keyline = obtainStyledAttributes.getInteger(6, -1);
            this.insetEdge = obtainStyledAttributes.getInt(5, 0);
            this.dodgeInsetEdges = obtainStyledAttributes.getInt(4, 0);
            boolean hasValue = obtainStyledAttributes.hasValue(3);
            this.mBehaviorResolved = hasValue;
            if (hasValue) {
                String string = obtainStyledAttributes.getString(3);
                String str = CoordinatorLayout.WIDGET_PACKAGE_NAME;
                if (TextUtils.isEmpty(string)) {
                    behavior = null;
                } else {
                    if (string.startsWith(".")) {
                        string = context.getPackageName() + string;
                    } else if (string.indexOf(46) < 0) {
                        String str2 = CoordinatorLayout.WIDGET_PACKAGE_NAME;
                        if (!TextUtils.isEmpty(str2)) {
                            string = str2 + '.' + string;
                        }
                    }
                    try {
                        ThreadLocal<Map<String, Constructor<Behavior>>> threadLocal = CoordinatorLayout.sConstructors;
                        Map map = threadLocal.get();
                        if (map == null) {
                            map = new HashMap();
                            threadLocal.set(map);
                        }
                        Constructor<?> constructor = (Constructor) map.get(string);
                        if (constructor == null) {
                            constructor = Class.forName(string, false, context.getClassLoader()).getConstructor(CoordinatorLayout.CONSTRUCTOR_PARAMS);
                            constructor.setAccessible(true);
                            map.put(string, constructor);
                        }
                        behavior = (Behavior) constructor.newInstance(new Object[]{context, attributeSet});
                    } catch (Exception e) {
                        throw new RuntimeException(SupportMenuInflater$$ExternalSyntheticOutline0.m4m("Could not inflate Behavior subclass ", string), e);
                    }
                }
                this.mBehavior = behavior;
            }
            obtainStyledAttributes.recycle();
            Behavior behavior2 = this.mBehavior;
            if (behavior2 != null) {
                behavior2.onAttachedToLayoutParams(this);
            }
        }

        public LayoutParams(LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public class OnPreDrawListener implements ViewTreeObserver.OnPreDrawListener {
        public OnPreDrawListener() {
        }

        public final boolean onPreDraw() {
            CoordinatorLayout.this.onChildViewsChanged(0);
            return true;
        }
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, (ClassLoader) null);
            }

            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public SparseArray<Parcelable> behaviorStates;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            int readInt = parcel.readInt();
            int[] iArr = new int[readInt];
            parcel.readIntArray(iArr);
            Parcelable[] readParcelableArray = parcel.readParcelableArray(classLoader);
            this.behaviorStates = new SparseArray<>(readInt);
            for (int i = 0; i < readInt; i++) {
                this.behaviorStates.append(iArr[i], readParcelableArray[i]);
            }
        }

        public final void writeToParcel(Parcel parcel, int i) {
            int i2;
            parcel.writeParcelable(this.mSuperState, i);
            SparseArray<Parcelable> sparseArray = this.behaviorStates;
            if (sparseArray != null) {
                i2 = sparseArray.size();
            } else {
                i2 = 0;
            }
            parcel.writeInt(i2);
            int[] iArr = new int[i2];
            Parcelable[] parcelableArr = new Parcelable[i2];
            for (int i3 = 0; i3 < i2; i3++) {
                iArr[i3] = this.behaviorStates.keyAt(i3);
                parcelableArr[i3] = this.behaviorStates.valueAt(i3);
            }
            parcel.writeIntArray(iArr);
            parcel.writeParcelableArray(parcelableArr, i);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public static class ViewElevationComparator implements Comparator<View> {
        public final int compare(Object obj, Object obj2) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            float z = ViewCompat.Api21Impl.getZ((View) obj);
            float z2 = ViewCompat.Api21Impl.getZ((View) obj2);
            if (z > z2) {
                return -1;
            }
            if (z < z2) {
                return 1;
            }
            return 0;
        }
    }

    public CoordinatorLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public final void onMeasureChild(View view, int i, int i2, int i3) {
        measureChildWithMargins(view, i, i2, i3, 0);
    }

    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr) {
        onNestedPreScroll(view, i, i2, iArr, 0);
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        onNestedScroll(view, i, i2, i3, i4, 0);
    }

    public final void onNestedScrollAccepted(View view, View view2, int i) {
        onNestedScrollAccepted(view, view2, i, 0);
    }

    public final boolean onStartNestedScroll(View view, View view2, int i) {
        return onStartNestedScroll(view, view2, i, 0);
    }

    public final void onStopNestedScroll(View view) {
        onStopNestedScroll(view, 0);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.Class<androidx.coordinatorlayout.widget.CoordinatorLayout> r0 = androidx.coordinatorlayout.widget.CoordinatorLayout.class
            java.lang.Package r0 = r0.getPackage()
            if (r0 == 0) goto L_0x000d
            java.lang.String r0 = r0.getName()
            goto L_0x000e
        L_0x000d:
            r0 = 0
        L_0x000e:
            WIDGET_PACKAGE_NAME = r0
            androidx.coordinatorlayout.widget.CoordinatorLayout$ViewElevationComparator r0 = new androidx.coordinatorlayout.widget.CoordinatorLayout$ViewElevationComparator
            r0.<init>()
            TOP_SORTED_CHILDREN_COMPARATOR = r0
            r0 = 2
            java.lang.Class[] r0 = new java.lang.Class[r0]
            r1 = 0
            java.lang.Class<android.content.Context> r2 = android.content.Context.class
            r0[r1] = r2
            r1 = 1
            java.lang.Class<android.util.AttributeSet> r2 = android.util.AttributeSet.class
            r0[r1] = r2
            CONSTRUCTOR_PARAMS = r0
            java.lang.ThreadLocal r0 = new java.lang.ThreadLocal
            r0.<init>()
            sConstructors = r0
            androidx.core.util.Pools$SynchronizedPool r0 = new androidx.core.util.Pools$SynchronizedPool
            r1 = 12
            r0.<init>(r1)
            sRectPool = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.<clinit>():void");
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1777R.attr.coordinatorLayoutStyle);
    }

    public static Rect acquireTempRect() {
        Rect rect = (Rect) sRectPool.acquire();
        if (rect == null) {
            return new Rect();
        }
        return rect;
    }

    public static void getDesiredAnchoredChildRectWithoutConstraints(int i, Rect rect, Rect rect2, LayoutParams layoutParams, int i2, int i3) {
        int i4;
        int i5;
        int i6 = layoutParams.gravity;
        if (i6 == 0) {
            i6 = 17;
        }
        int absoluteGravity = Gravity.getAbsoluteGravity(i6, i);
        int i7 = layoutParams.anchorGravity;
        if ((i7 & 7) == 0) {
            i7 |= 8388611;
        }
        if ((i7 & 112) == 0) {
            i7 |= 48;
        }
        int absoluteGravity2 = Gravity.getAbsoluteGravity(i7, i);
        int i8 = absoluteGravity & 7;
        int i9 = absoluteGravity & 112;
        int i10 = absoluteGravity2 & 7;
        int i11 = absoluteGravity2 & 112;
        if (i10 == 1) {
            i4 = rect.left + (rect.width() / 2);
        } else if (i10 != 5) {
            i4 = rect.left;
        } else {
            i4 = rect.right;
        }
        if (i11 == 16) {
            i5 = rect.top + (rect.height() / 2);
        } else if (i11 != 80) {
            i5 = rect.top;
        } else {
            i5 = rect.bottom;
        }
        if (i8 == 1) {
            i4 -= i2 / 2;
        } else if (i8 != 5) {
            i4 -= i2;
        }
        if (i9 == 16) {
            i5 -= i3 / 2;
        } else if (i9 != 80) {
            i5 -= i3;
        }
        rect2.set(i4, i5, i2 + i4, i3 + i5);
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (!(layoutParams instanceof LayoutParams) || !super.checkLayoutParams(layoutParams)) {
            return false;
        }
        return true;
    }

    public final void dispatchDependentViewsChanged(View view) {
        DirectedAcyclicGraph directedAcyclicGraph = this.mChildDag;
        Objects.requireNonNull(directedAcyclicGraph);
        SimpleArrayMap simpleArrayMap = (SimpleArrayMap) directedAcyclicGraph.mGraph;
        Objects.requireNonNull(simpleArrayMap);
        ArrayList arrayList = (ArrayList) simpleArrayMap.getOrDefault(view, null);
        if (arrayList != null && !arrayList.isEmpty()) {
            for (int i = 0; i < arrayList.size(); i++) {
                View view2 = (View) arrayList.get(i);
                LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                Behavior behavior = layoutParams.mBehavior;
                if (behavior != null) {
                    behavior.onDependentViewChanged(this, view2, view);
                }
            }
        }
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LayoutParams) {
            return new LayoutParams((LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public final List<View> getDependencies(View view) {
        DirectedAcyclicGraph directedAcyclicGraph = this.mChildDag;
        Objects.requireNonNull(directedAcyclicGraph);
        SimpleArrayMap simpleArrayMap = (SimpleArrayMap) directedAcyclicGraph.mGraph;
        Objects.requireNonNull(simpleArrayMap);
        int i = simpleArrayMap.mSize;
        ArrayList arrayList = null;
        for (int i2 = 0; i2 < i; i2++) {
            ArrayList arrayList2 = (ArrayList) ((SimpleArrayMap) directedAcyclicGraph.mGraph).valueAt(i2);
            if (arrayList2 != null && arrayList2.contains(view)) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(((SimpleArrayMap) directedAcyclicGraph.mGraph).keyAt(i2));
            }
        }
        if (arrayList == null) {
            return Collections.emptyList();
        }
        return arrayList;
    }

    public final void getDescendantRect(View view, Rect rect) {
        ThreadLocal<Matrix> threadLocal = ViewGroupUtils.sMatrix;
        rect.set(0, 0, view.getWidth(), view.getHeight());
        ThreadLocal<Matrix> threadLocal2 = ViewGroupUtils.sMatrix;
        Matrix matrix = threadLocal2.get();
        if (matrix == null) {
            matrix = new Matrix();
            threadLocal2.set(matrix);
        } else {
            matrix.reset();
        }
        ViewGroupUtils.offsetDescendantMatrix(this, view, matrix);
        ThreadLocal<RectF> threadLocal3 = ViewGroupUtils.sRectF;
        RectF rectF = threadLocal3.get();
        if (rectF == null) {
            rectF = new RectF();
            threadLocal3.set(rectF);
        }
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }

    public final int getKeyline(int i) {
        int[] iArr = this.mKeylines;
        if (iArr == null) {
            Log.e("CoordinatorLayout", "No keylines defined for " + this + " - attempted index lookup " + i);
            return 0;
        } else if (i >= 0 && i < iArr.length) {
            return iArr[i];
        } else {
            Log.e("CoordinatorLayout", "Keyline index " + i + " out of range for " + this);
            return 0;
        }
    }

    public final int getNestedScrollAxes() {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mNestedScrollingParentHelper;
        Objects.requireNonNull(nestedScrollingParentHelper);
        return nestedScrollingParentHelper.mNestedScrollAxesNonTouch | nestedScrollingParentHelper.mNestedScrollAxesTouch;
    }

    public final void onChildViewsChanged(int i) {
        int i2;
        Rect rect;
        int i3;
        boolean z;
        boolean z2;
        boolean z3;
        int width;
        int i4;
        int i5;
        int i6;
        int height;
        int i7;
        int i8;
        int i9;
        int i10;
        Rect rect2;
        int i11;
        int i12;
        Behavior behavior;
        int i13 = i;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(this);
        int size = this.mDependencySortedChildren.size();
        Rect acquireTempRect = acquireTempRect();
        Rect acquireTempRect2 = acquireTempRect();
        Rect acquireTempRect3 = acquireTempRect();
        int i14 = 0;
        while (i14 < size) {
            View view = (View) this.mDependencySortedChildren.get(i14);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (i13 == 0 && view.getVisibility() == 8) {
                i3 = size;
                rect = acquireTempRect3;
                i2 = i14;
            } else {
                int i15 = 0;
                while (i15 < i14) {
                    if (layoutParams.mAnchorDirectChild == ((View) this.mDependencySortedChildren.get(i15))) {
                        LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                        if (layoutParams2.mAnchorView != null) {
                            Rect acquireTempRect4 = acquireTempRect();
                            Rect acquireTempRect5 = acquireTempRect();
                            Rect acquireTempRect6 = acquireTempRect();
                            getDescendantRect(layoutParams2.mAnchorView, acquireTempRect4);
                            getChildRect(view, false, acquireTempRect5);
                            int measuredWidth = view.getMeasuredWidth();
                            i12 = size;
                            int measuredHeight = view.getMeasuredHeight();
                            boolean z4 = true;
                            int i16 = measuredWidth;
                            Rect rect3 = acquireTempRect6;
                            i11 = i14;
                            Rect rect4 = acquireTempRect5;
                            Rect rect5 = acquireTempRect4;
                            rect2 = acquireTempRect3;
                            LayoutParams layoutParams3 = layoutParams2;
                            i10 = i15;
                            getDesiredAnchoredChildRectWithoutConstraints(layoutDirection, acquireTempRect4, rect3, layoutParams2, i16, measuredHeight);
                            Rect rect6 = rect3;
                            if (rect6.left == rect4.left && rect6.top == rect4.top) {
                                z4 = false;
                            }
                            constrainChildRect(layoutParams3, rect6, i16, measuredHeight);
                            int i17 = rect6.left - rect4.left;
                            int i18 = rect6.top - rect4.top;
                            if (i17 != 0) {
                                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                                view.offsetLeftAndRight(i17);
                            }
                            if (i18 != 0) {
                                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
                                view.offsetTopAndBottom(i18);
                            }
                            if (z4 && (behavior = layoutParams3.mBehavior) != null) {
                                behavior.onDependentViewChanged(this, view, layoutParams3.mAnchorView);
                            }
                            rect5.setEmpty();
                            Pools$SynchronizedPool pools$SynchronizedPool = sRectPool;
                            pools$SynchronizedPool.release(rect5);
                            rect4.setEmpty();
                            pools$SynchronizedPool.release(rect4);
                            rect6.setEmpty();
                            pools$SynchronizedPool.release(rect6);
                            i15 = i10 + 1;
                            size = i12;
                            i14 = i11;
                            acquireTempRect3 = rect2;
                        }
                    }
                    i10 = i15;
                    i12 = size;
                    rect2 = acquireTempRect3;
                    i11 = i14;
                    i15 = i10 + 1;
                    size = i12;
                    i14 = i11;
                    acquireTempRect3 = rect2;
                }
                int i19 = size;
                Rect rect7 = acquireTempRect3;
                i2 = i14;
                getChildRect(view, true, acquireTempRect2);
                if (layoutParams.insetEdge != 0 && !acquireTempRect2.isEmpty()) {
                    int absoluteGravity = Gravity.getAbsoluteGravity(layoutParams.insetEdge, layoutDirection);
                    int i20 = absoluteGravity & 112;
                    if (i20 == 48) {
                        acquireTempRect.top = Math.max(acquireTempRect.top, acquireTempRect2.bottom);
                    } else if (i20 == 80) {
                        acquireTempRect.bottom = Math.max(acquireTempRect.bottom, getHeight() - acquireTempRect2.top);
                    }
                    int i21 = absoluteGravity & 7;
                    if (i21 == 3) {
                        acquireTempRect.left = Math.max(acquireTempRect.left, acquireTempRect2.right);
                    } else if (i21 == 5) {
                        acquireTempRect.right = Math.max(acquireTempRect.right, getWidth() - acquireTempRect2.left);
                    }
                }
                if (layoutParams.dodgeInsetEdges != 0 && view.getVisibility() == 0) {
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap4 = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api19Impl.isLaidOut(view) && view.getWidth() > 0 && view.getHeight() > 0) {
                        LayoutParams layoutParams4 = (LayoutParams) view.getLayoutParams();
                        Objects.requireNonNull(layoutParams4);
                        Behavior behavior2 = layoutParams4.mBehavior;
                        Rect acquireTempRect7 = acquireTempRect();
                        Rect acquireTempRect8 = acquireTempRect();
                        acquireTempRect8.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
                        if (behavior2 == null || !behavior2.getInsetDodgeRect(view, acquireTempRect7)) {
                            acquireTempRect7.set(acquireTempRect8);
                        } else if (!acquireTempRect8.contains(acquireTempRect7)) {
                            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Rect should be within the child's bounds. Rect:");
                            m.append(acquireTempRect7.toShortString());
                            m.append(" | Bounds:");
                            m.append(acquireTempRect8.toShortString());
                            throw new IllegalArgumentException(m.toString());
                        }
                        acquireTempRect8.setEmpty();
                        Pools$SynchronizedPool pools$SynchronizedPool2 = sRectPool;
                        pools$SynchronizedPool2.release(acquireTempRect8);
                        if (acquireTempRect7.isEmpty()) {
                            acquireTempRect7.setEmpty();
                            pools$SynchronizedPool2.release(acquireTempRect7);
                        } else {
                            int absoluteGravity2 = Gravity.getAbsoluteGravity(layoutParams4.dodgeInsetEdges, layoutDirection);
                            if ((absoluteGravity2 & 48) != 48 || (i8 = (acquireTempRect7.top - layoutParams4.topMargin) - layoutParams4.mInsetOffsetY) >= (i9 = acquireTempRect.top)) {
                                z2 = false;
                            } else {
                                setInsetOffsetY(view, i9 - i8);
                                z2 = true;
                            }
                            if ((absoluteGravity2 & 80) == 80 && (height = ((getHeight() - acquireTempRect7.bottom) - layoutParams4.bottomMargin) + layoutParams4.mInsetOffsetY) < (i7 = acquireTempRect.bottom)) {
                                setInsetOffsetY(view, height - i7);
                                z2 = true;
                            }
                            if (!z2) {
                                setInsetOffsetY(view, 0);
                            }
                            if ((absoluteGravity2 & 3) != 3 || (i5 = (acquireTempRect7.left - layoutParams4.leftMargin) - layoutParams4.mInsetOffsetX) >= (i6 = acquireTempRect.left)) {
                                z3 = false;
                            } else {
                                setInsetOffsetX(view, i6 - i5);
                                z3 = true;
                            }
                            if ((absoluteGravity2 & 5) == 5 && (width = ((getWidth() - acquireTempRect7.right) - layoutParams4.rightMargin) + layoutParams4.mInsetOffsetX) < (i4 = acquireTempRect.right)) {
                                setInsetOffsetX(view, width - i4);
                                z3 = true;
                            }
                            if (!z3) {
                                setInsetOffsetX(view, 0);
                            }
                            acquireTempRect7.setEmpty();
                            pools$SynchronizedPool2.release(acquireTempRect7);
                        }
                    }
                }
                if (i13 != 2) {
                    LayoutParams layoutParams5 = (LayoutParams) view.getLayoutParams();
                    Objects.requireNonNull(layoutParams5);
                    rect = rect7;
                    rect.set(layoutParams5.mLastChildRect);
                    if (rect.equals(acquireTempRect2)) {
                        i3 = i19;
                    } else {
                        LayoutParams layoutParams6 = (LayoutParams) view.getLayoutParams();
                        Objects.requireNonNull(layoutParams6);
                        layoutParams6.mLastChildRect.set(acquireTempRect2);
                    }
                } else {
                    rect = rect7;
                }
                i3 = i19;
                for (int i22 = i2 + 1; i22 < i3; i22++) {
                    View view2 = (View) this.mDependencySortedChildren.get(i22);
                    LayoutParams layoutParams7 = (LayoutParams) view2.getLayoutParams();
                    Objects.requireNonNull(layoutParams7);
                    Behavior behavior3 = layoutParams7.mBehavior;
                    if (behavior3 != null && behavior3.layoutDependsOn(view2, view)) {
                        if (i13 != 0 || !layoutParams7.mDidChangeAfterNestedScroll) {
                            if (i13 != 2) {
                                z = behavior3.onDependentViewChanged(this, view2, view);
                            } else {
                                behavior3.onDependentViewRemoved(this, view);
                                z = true;
                            }
                            if (i13 == 1) {
                                layoutParams7.mDidChangeAfterNestedScroll = z;
                            }
                        } else {
                            layoutParams7.mDidChangeAfterNestedScroll = false;
                        }
                    }
                }
            }
            i14 = i2 + 1;
            size = i3;
            acquireTempRect3 = rect;
        }
        Rect rect8 = acquireTempRect3;
        acquireTempRect.setEmpty();
        Pools$SynchronizedPool pools$SynchronizedPool3 = sRectPool;
        pools$SynchronizedPool3.release(acquireTempRect);
        acquireTempRect2.setEmpty();
        pools$SynchronizedPool3.release(acquireTempRect2);
        rect8.setEmpty();
        pools$SynchronizedPool3.release(rect8);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        int layoutDirection = ViewCompat.Api17Impl.getLayoutDirection(this);
        int size = this.mDependencySortedChildren.size();
        for (int i5 = 0; i5 < size; i5++) {
            View view = (View) this.mDependencySortedChildren.get(i5);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                Behavior behavior = layoutParams.mBehavior;
                if (behavior == null || !behavior.onLayoutChild(this, view, layoutDirection)) {
                    onLayoutChild(view, layoutDirection);
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0196, code lost:
        if (r0.onMeasureChild(r30, r19, r24, r20, r25) == false) goto L_0x01ab;
     */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0144  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x016f  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0177  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x0199  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r31, int r32) {
        /*
            r30 = this;
            r6 = r30
            r30.prepareChildren()
            int r0 = r30.getChildCount()
            r7 = 0
            r1 = r7
        L_0x000b:
            r2 = 1
            if (r1 >= r0) goto L_0x0042
            android.view.View r3 = r6.getChildAt(r1)
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r4 = r6.mChildDag
            java.util.Objects.requireNonNull(r4)
            java.lang.Object r5 = r4.mGraph
            androidx.collection.SimpleArrayMap r5 = (androidx.collection.SimpleArrayMap) r5
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mSize
            r8 = r7
        L_0x0021:
            if (r8 >= r5) goto L_0x003a
            java.lang.Object r9 = r4.mGraph
            androidx.collection.SimpleArrayMap r9 = (androidx.collection.SimpleArrayMap) r9
            java.lang.Object r9 = r9.valueAt(r8)
            java.util.ArrayList r9 = (java.util.ArrayList) r9
            if (r9 == 0) goto L_0x0037
            boolean r9 = r9.contains(r3)
            if (r9 == 0) goto L_0x0037
            r3 = r2
            goto L_0x003b
        L_0x0037:
            int r8 = r8 + 1
            goto L_0x0021
        L_0x003a:
            r3 = r7
        L_0x003b:
            if (r3 == 0) goto L_0x003f
            r0 = r2
            goto L_0x0043
        L_0x003f:
            int r1 = r1 + 1
            goto L_0x000b
        L_0x0042:
            r0 = r7
        L_0x0043:
            boolean r1 = r6.mNeedsPreDrawListener
            if (r0 == r1) goto L_0x0077
            if (r0 == 0) goto L_0x0064
            boolean r0 = r6.mIsAttachedToWindow
            if (r0 == 0) goto L_0x0061
            androidx.coordinatorlayout.widget.CoordinatorLayout$OnPreDrawListener r0 = r6.mOnPreDrawListener
            if (r0 != 0) goto L_0x0058
            androidx.coordinatorlayout.widget.CoordinatorLayout$OnPreDrawListener r0 = new androidx.coordinatorlayout.widget.CoordinatorLayout$OnPreDrawListener
            r0.<init>()
            r6.mOnPreDrawListener = r0
        L_0x0058:
            android.view.ViewTreeObserver r0 = r30.getViewTreeObserver()
            androidx.coordinatorlayout.widget.CoordinatorLayout$OnPreDrawListener r1 = r6.mOnPreDrawListener
            r0.addOnPreDrawListener(r1)
        L_0x0061:
            r6.mNeedsPreDrawListener = r2
            goto L_0x0077
        L_0x0064:
            boolean r0 = r6.mIsAttachedToWindow
            if (r0 == 0) goto L_0x0075
            androidx.coordinatorlayout.widget.CoordinatorLayout$OnPreDrawListener r0 = r6.mOnPreDrawListener
            if (r0 == 0) goto L_0x0075
            android.view.ViewTreeObserver r0 = r30.getViewTreeObserver()
            androidx.coordinatorlayout.widget.CoordinatorLayout$OnPreDrawListener r1 = r6.mOnPreDrawListener
            r0.removeOnPreDrawListener(r1)
        L_0x0075:
            r6.mNeedsPreDrawListener = r7
        L_0x0077:
            int r8 = r30.getPaddingLeft()
            int r0 = r30.getPaddingTop()
            int r9 = r30.getPaddingRight()
            int r1 = r30.getPaddingBottom()
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r10 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r30)
            if (r10 != r2) goto L_0x0091
            r11 = r2
            goto L_0x0092
        L_0x0091:
            r11 = r7
        L_0x0092:
            int r12 = android.view.View.MeasureSpec.getMode(r31)
            int r13 = android.view.View.MeasureSpec.getSize(r31)
            int r14 = android.view.View.MeasureSpec.getMode(r32)
            int r15 = android.view.View.MeasureSpec.getSize(r32)
            int r16 = r8 + r9
            int r17 = r0 + r1
            int r0 = r30.getSuggestedMinimumWidth()
            int r1 = r30.getSuggestedMinimumHeight()
            androidx.core.view.WindowInsetsCompat r3 = r6.mLastInsets
            if (r3 == 0) goto L_0x00bb
            boolean r3 = androidx.core.view.ViewCompat.Api16Impl.getFitsSystemWindows(r30)
            if (r3 == 0) goto L_0x00bb
            r18 = r2
            goto L_0x00bd
        L_0x00bb:
            r18 = r7
        L_0x00bd:
            java.util.ArrayList r2 = r6.mDependencySortedChildren
            int r5 = r2.size()
            r4 = r0
            r3 = r1
            r1 = r7
            r2 = r1
        L_0x00c7:
            if (r2 >= r5) goto L_0x01f2
            java.util.ArrayList r0 = r6.mDependencySortedChildren
            java.lang.Object r0 = r0.get(r2)
            r19 = r0
            android.view.View r19 = (android.view.View) r19
            int r0 = r19.getVisibility()
            r7 = 8
            if (r0 != r7) goto L_0x00e9
            r21 = r2
            r28 = r5
            r22 = r8
            r26 = r9
            r27 = r10
            r23 = 0
            goto L_0x01e4
        L_0x00e9:
            android.view.ViewGroup$LayoutParams r0 = r19.getLayoutParams()
            r7 = r0
            androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r7 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r7
            int r0 = r7.keyline
            if (r0 < 0) goto L_0x0135
            if (r12 == 0) goto L_0x0135
            int r0 = r6.getKeyline(r0)
            r21 = r1
            int r1 = r7.gravity
            if (r1 != 0) goto L_0x0103
            r1 = 8388661(0x800035, float:1.1755018E-38)
        L_0x0103:
            int r1 = android.view.Gravity.getAbsoluteGravity(r1, r10)
            r1 = r1 & 7
            r22 = r2
            r2 = 3
            if (r1 != r2) goto L_0x0110
            if (r11 == 0) goto L_0x0115
        L_0x0110:
            r2 = 5
            if (r1 != r2) goto L_0x0121
            if (r11 == 0) goto L_0x0121
        L_0x0115:
            int r1 = r13 - r9
            int r1 = r1 - r0
            r0 = 0
            int r1 = java.lang.Math.max(r0, r1)
            r2 = r0
            r20 = r1
            goto L_0x013c
        L_0x0121:
            if (r1 != r2) goto L_0x0125
            if (r11 == 0) goto L_0x012a
        L_0x0125:
            r2 = 3
            if (r1 != r2) goto L_0x0133
            if (r11 == 0) goto L_0x0133
        L_0x012a:
            int r0 = r0 - r8
            r2 = 0
            int r0 = java.lang.Math.max(r2, r0)
            r20 = r0
            goto L_0x013c
        L_0x0133:
            r2 = 0
            goto L_0x013a
        L_0x0135:
            r21 = r1
            r22 = r2
            goto L_0x0133
        L_0x013a:
            r20 = r2
        L_0x013c:
            if (r18 == 0) goto L_0x016f
            boolean r0 = androidx.core.view.ViewCompat.Api16Impl.getFitsSystemWindows(r19)
            if (r0 != 0) goto L_0x016f
            androidx.core.view.WindowInsetsCompat r0 = r6.mLastInsets
            int r0 = r0.getSystemWindowInsetLeft()
            androidx.core.view.WindowInsetsCompat r1 = r6.mLastInsets
            int r1 = r1.getSystemWindowInsetRight()
            int r1 = r1 + r0
            androidx.core.view.WindowInsetsCompat r0 = r6.mLastInsets
            int r0 = r0.getSystemWindowInsetTop()
            androidx.core.view.WindowInsetsCompat r2 = r6.mLastInsets
            int r2 = r2.getSystemWindowInsetBottom()
            int r2 = r2 + r0
            int r0 = r13 - r1
            int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r12)
            int r1 = r15 - r2
            int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r1, r14)
            r24 = r0
            r25 = r1
            goto L_0x0173
        L_0x016f:
            r24 = r31
            r25 = r32
        L_0x0173:
            androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r0 = r7.mBehavior
            if (r0 == 0) goto L_0x0199
            r2 = r21
            r1 = r30
            r21 = r22
            r23 = 0
            r22 = r8
            r8 = r2
            r2 = r19
            r26 = r9
            r9 = r3
            r3 = r24
            r27 = r10
            r10 = r4
            r4 = r20
            r28 = r5
            r5 = r25
            boolean r0 = r0.onMeasureChild(r1, r2, r3, r4, r5)
            if (r0 != 0) goto L_0x01b9
            goto L_0x01ab
        L_0x0199:
            r28 = r5
            r26 = r9
            r27 = r10
            r23 = 0
            r9 = r3
            r10 = r4
            r29 = r22
            r22 = r8
            r8 = r21
            r21 = r29
        L_0x01ab:
            r5 = 0
            r0 = r30
            r1 = r19
            r2 = r24
            r3 = r20
            r4 = r25
            r0.measureChildWithMargins(r1, r2, r3, r4, r5)
        L_0x01b9:
            int r0 = r19.getMeasuredWidth()
            int r0 = r0 + r16
            int r1 = r7.leftMargin
            int r0 = r0 + r1
            int r1 = r7.rightMargin
            int r0 = r0 + r1
            int r0 = java.lang.Math.max(r10, r0)
            int r1 = r19.getMeasuredHeight()
            int r1 = r1 + r17
            int r2 = r7.topMargin
            int r1 = r1 + r2
            int r2 = r7.bottomMargin
            int r1 = r1 + r2
            int r1 = java.lang.Math.max(r9, r1)
            int r2 = r19.getMeasuredState()
            int r2 = android.view.View.combineMeasuredStates(r8, r2)
            r4 = r0
            r3 = r1
            r1 = r2
        L_0x01e4:
            int r2 = r21 + 1
            r8 = r22
            r7 = r23
            r9 = r26
            r10 = r27
            r5 = r28
            goto L_0x00c7
        L_0x01f2:
            r8 = r1
            r9 = r3
            r10 = r4
            r0 = -16777216(0xffffffffff000000, float:-1.7014118E38)
            r0 = r0 & r8
            r1 = r31
            int r0 = android.view.View.resolveSizeAndState(r10, r1, r0)
            int r1 = r8 << 16
            r2 = r32
            int r1 = android.view.View.resolveSizeAndState(r9, r2, r1)
            r6.setMeasuredDimension(r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.onMeasure(int, int):void");
    }

    public final void onNestedPreScroll(View view, int i, int i2, int[] iArr, int i3) {
        Behavior behavior;
        int i4;
        int i5;
        int childCount = getChildCount();
        boolean z = false;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() == 8) {
                int i9 = i3;
            } else {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(i3) && (behavior = layoutParams.mBehavior) != null) {
                    int[] iArr2 = this.mBehaviorConsumed;
                    iArr2[0] = 0;
                    iArr2[1] = 0;
                    behavior.onNestedPreScroll(this, childAt, view, i, i2, iArr2, i3);
                    if (i > 0) {
                        i4 = Math.max(i6, this.mBehaviorConsumed[0]);
                    } else {
                        i4 = Math.min(i6, this.mBehaviorConsumed[0]);
                    }
                    i6 = i4;
                    if (i2 > 0) {
                        i5 = Math.max(i7, this.mBehaviorConsumed[1]);
                    } else {
                        i5 = Math.min(i7, this.mBehaviorConsumed[1]);
                    }
                    i7 = i5;
                    z = true;
                }
            }
        }
        iArr[0] = i6;
        iArr[1] = i7;
        if (z) {
            onChildViewsChanged(1);
        }
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5) {
        onNestedScroll(view, i, i2, i3, i4, 0, this.mNestedScrollingV2ConsumedCompat);
    }

    public final void onNestedScrollAccepted(View view, View view2, int i, int i2) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mNestedScrollingParentHelper;
        Objects.requireNonNull(nestedScrollingParentHelper);
        if (i2 == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = i;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = i;
        }
        this.mNestedScrollingTarget = view2;
        int childCount = getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            Objects.requireNonNull((LayoutParams) getChildAt(i3).getLayoutParams());
        }
    }

    public final void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        Objects.requireNonNull(savedState);
        super.onRestoreInstanceState(savedState.mSuperState);
        SparseArray<Parcelable> sparseArray = savedState.behaviorStates;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            Behavior behavior = getResolvedLayoutParams(childAt).mBehavior;
            if (!(id == -1 || behavior == null || (parcelable2 = sparseArray.get(id)) == null)) {
                behavior.onRestoreInstanceState(childAt, parcelable2);
            }
        }
    }

    public final Parcelable onSaveInstanceState() {
        Parcelable onSaveInstanceState;
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SparseArray<Parcelable> sparseArray = new SparseArray<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int id = childAt.getId();
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            Objects.requireNonNull(layoutParams);
            Behavior behavior = layoutParams.mBehavior;
            if (!(id == -1 || behavior == null || (onSaveInstanceState = behavior.onSaveInstanceState(childAt)) == null)) {
                sparseArray.append(id, onSaveInstanceState);
            }
        }
        savedState.behaviorStates = sparseArray;
        return savedState;
    }

    public final boolean onStartNestedScroll(View view, View view2, int i, int i2) {
        int i3 = i2;
        int childCount = getChildCount();
        boolean z = false;
        for (int i4 = 0; i4 < childCount; i4++) {
            View childAt = getChildAt(i4);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                Objects.requireNonNull(layoutParams);
                Behavior behavior = layoutParams.mBehavior;
                if (behavior != null) {
                    boolean onStartNestedScroll = behavior.onStartNestedScroll(this, childAt, view, view2, i, i2);
                    z |= onStartNestedScroll;
                    if (i3 == 0) {
                        layoutParams.mDidAcceptNestedScrollTouch = onStartNestedScroll;
                    } else if (i3 == 1) {
                        layoutParams.mDidAcceptNestedScrollNonTouch = onStartNestedScroll;
                    }
                } else if (i3 == 0) {
                    layoutParams.mDidAcceptNestedScrollTouch = false;
                } else if (i3 == 1) {
                    layoutParams.mDidAcceptNestedScrollNonTouch = false;
                }
            }
        }
        return z;
    }

    public final void onStopNestedScroll(View view, int i) {
        NestedScrollingParentHelper nestedScrollingParentHelper = this.mNestedScrollingParentHelper;
        Objects.requireNonNull(nestedScrollingParentHelper);
        if (i == 1) {
            nestedScrollingParentHelper.mNestedScrollAxesNonTouch = 0;
        } else {
            nestedScrollingParentHelper.mNestedScrollAxesTouch = 0;
        }
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.isNestedScrollAccepted(i)) {
                Behavior behavior = layoutParams.mBehavior;
                if (behavior != null) {
                    behavior.onStopNestedScroll(this, childAt, view, i);
                }
                if (i == 0) {
                    layoutParams.mDidAcceptNestedScrollTouch = false;
                } else if (i == 1) {
                    layoutParams.mDidAcceptNestedScrollNonTouch = false;
                }
                layoutParams.mDidChangeAfterNestedScroll = false;
            }
        }
        this.mNestedScrollingTarget = null;
    }

    public final boolean performEvent(Behavior behavior, View view, MotionEvent motionEvent, int i) {
        if (i == 0) {
            return behavior.onInterceptTouchEvent(this, view, motionEvent);
        }
        if (i == 1) {
            return behavior.onTouchEvent(this, view, motionEvent);
        }
        throw new IllegalArgumentException();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0082, code lost:
        if (r5 != false) goto L_0x00d9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:121:0x01a1 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0130  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void prepareChildren() {
        /*
            r12 = this;
            java.util.ArrayList r0 = r12.mDependencySortedChildren
            r0.clear()
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r0 = r12.mChildDag
            java.util.Objects.requireNonNull(r0)
            java.lang.Object r1 = r0.mGraph
            androidx.collection.SimpleArrayMap r1 = (androidx.collection.SimpleArrayMap) r1
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.mSize
            r2 = 0
            r3 = r2
        L_0x0015:
            if (r3 >= r1) goto L_0x0030
            java.lang.Object r4 = r0.mGraph
            androidx.collection.SimpleArrayMap r4 = (androidx.collection.SimpleArrayMap) r4
            java.lang.Object r4 = r4.valueAt(r3)
            java.util.ArrayList r4 = (java.util.ArrayList) r4
            if (r4 == 0) goto L_0x002d
            r4.clear()
            java.lang.Object r5 = r0.mListPool
            androidx.core.util.Pools$SimplePool r5 = (androidx.core.util.Pools$SimplePool) r5
            r5.release(r4)
        L_0x002d:
            int r3 = r3 + 1
            goto L_0x0015
        L_0x0030:
            java.lang.Object r0 = r0.mGraph
            androidx.collection.SimpleArrayMap r0 = (androidx.collection.SimpleArrayMap) r0
            r0.clear()
            int r0 = r12.getChildCount()
            r1 = r2
        L_0x003c:
            if (r1 >= r0) goto L_0x01ce
            android.view.View r3 = r12.getChildAt(r1)
            androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r4 = getResolvedLayoutParams(r3)
            int r5 = r4.mAnchorId
            r6 = -1
            r7 = 0
            r8 = 1
            if (r5 != r6) goto L_0x0053
            r4.mAnchorDirectChild = r7
            r4.mAnchorView = r7
            goto L_0x00d9
        L_0x0053:
            android.view.View r5 = r4.mAnchorView
            if (r5 == 0) goto L_0x0084
            int r5 = r5.getId()
            int r6 = r4.mAnchorId
            if (r5 == r6) goto L_0x0060
            goto L_0x007d
        L_0x0060:
            android.view.View r5 = r4.mAnchorView
            android.view.ViewParent r6 = r5.getParent()
        L_0x0066:
            if (r6 == r12) goto L_0x007f
            if (r6 == 0) goto L_0x0079
            if (r6 != r3) goto L_0x006d
            goto L_0x0079
        L_0x006d:
            boolean r9 = r6 instanceof android.view.View
            if (r9 == 0) goto L_0x0074
            r5 = r6
            android.view.View r5 = (android.view.View) r5
        L_0x0074:
            android.view.ViewParent r6 = r6.getParent()
            goto L_0x0066
        L_0x0079:
            r4.mAnchorDirectChild = r7
            r4.mAnchorView = r7
        L_0x007d:
            r5 = r2
            goto L_0x0082
        L_0x007f:
            r4.mAnchorDirectChild = r5
            r5 = r8
        L_0x0082:
            if (r5 != 0) goto L_0x00d9
        L_0x0084:
            int r5 = r4.mAnchorId
            android.view.View r5 = r12.findViewById(r5)
            r4.mAnchorView = r5
            if (r5 == 0) goto L_0x00cf
            if (r5 != r12) goto L_0x00a3
            boolean r5 = r12.isInEditMode()
            if (r5 == 0) goto L_0x009b
            r4.mAnchorDirectChild = r7
            r4.mAnchorView = r7
            goto L_0x00d9
        L_0x009b:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "View can not be anchored to the the parent CoordinatorLayout"
            r12.<init>(r0)
            throw r12
        L_0x00a3:
            android.view.ViewParent r6 = r5.getParent()
        L_0x00a7:
            if (r6 == r12) goto L_0x00cc
            if (r6 == 0) goto L_0x00cc
            if (r6 != r3) goto L_0x00c0
            boolean r5 = r12.isInEditMode()
            if (r5 == 0) goto L_0x00b8
            r4.mAnchorDirectChild = r7
            r4.mAnchorView = r7
            goto L_0x00d9
        L_0x00b8:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "Anchor must not be a descendant of the anchored view"
            r12.<init>(r0)
            throw r12
        L_0x00c0:
            boolean r9 = r6 instanceof android.view.View
            if (r9 == 0) goto L_0x00c7
            r5 = r6
            android.view.View r5 = (android.view.View) r5
        L_0x00c7:
            android.view.ViewParent r6 = r6.getParent()
            goto L_0x00a7
        L_0x00cc:
            r4.mAnchorDirectChild = r5
            goto L_0x00d9
        L_0x00cf:
            boolean r5 = r12.isInEditMode()
            if (r5 == 0) goto L_0x01a9
            r4.mAnchorDirectChild = r7
            r4.mAnchorView = r7
        L_0x00d9:
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r5 = r12.mChildDag
            java.util.Objects.requireNonNull(r5)
            java.lang.Object r6 = r5.mGraph
            androidx.collection.SimpleArrayMap r6 = (androidx.collection.SimpleArrayMap) r6
            boolean r6 = r6.containsKey(r3)
            if (r6 != 0) goto L_0x00ef
            java.lang.Object r5 = r5.mGraph
            androidx.collection.SimpleArrayMap r5 = (androidx.collection.SimpleArrayMap) r5
            r5.put(r3, r7)
        L_0x00ef:
            r5 = r2
        L_0x00f0:
            if (r5 >= r0) goto L_0x01a5
            if (r5 != r1) goto L_0x00f6
            goto L_0x01a1
        L_0x00f6:
            android.view.View r6 = r12.getChildAt(r5)
            android.view.View r9 = r4.mAnchorDirectChild
            if (r6 == r9) goto L_0x012d
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r9 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r9 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r12)
            android.view.ViewGroup$LayoutParams r10 = r6.getLayoutParams()
            androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r10 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r10
            int r10 = r10.insetEdge
            int r10 = android.view.Gravity.getAbsoluteGravity(r10, r9)
            if (r10 == 0) goto L_0x011d
            int r11 = r4.dodgeInsetEdges
            int r9 = android.view.Gravity.getAbsoluteGravity(r11, r9)
            r9 = r9 & r10
            if (r9 != r10) goto L_0x011d
            r9 = r8
            goto L_0x011e
        L_0x011d:
            r9 = r2
        L_0x011e:
            if (r9 != 0) goto L_0x012d
            androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r9 = r4.mBehavior
            if (r9 == 0) goto L_0x012b
            boolean r9 = r9.layoutDependsOn(r3, r6)
            if (r9 == 0) goto L_0x012b
            goto L_0x012d
        L_0x012b:
            r9 = r2
            goto L_0x012e
        L_0x012d:
            r9 = r8
        L_0x012e:
            if (r9 == 0) goto L_0x01a1
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r9 = r12.mChildDag
            java.util.Objects.requireNonNull(r9)
            java.lang.Object r9 = r9.mGraph
            androidx.collection.SimpleArrayMap r9 = (androidx.collection.SimpleArrayMap) r9
            boolean r9 = r9.containsKey(r6)
            if (r9 != 0) goto L_0x0155
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r9 = r12.mChildDag
            java.util.Objects.requireNonNull(r9)
            java.lang.Object r10 = r9.mGraph
            androidx.collection.SimpleArrayMap r10 = (androidx.collection.SimpleArrayMap) r10
            boolean r10 = r10.containsKey(r6)
            if (r10 != 0) goto L_0x0155
            java.lang.Object r9 = r9.mGraph
            androidx.collection.SimpleArrayMap r9 = (androidx.collection.SimpleArrayMap) r9
            r9.put(r6, r7)
        L_0x0155:
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r9 = r12.mChildDag
            java.util.Objects.requireNonNull(r9)
            java.lang.Object r10 = r9.mGraph
            androidx.collection.SimpleArrayMap r10 = (androidx.collection.SimpleArrayMap) r10
            boolean r10 = r10.containsKey(r6)
            if (r10 == 0) goto L_0x0199
            java.lang.Object r10 = r9.mGraph
            androidx.collection.SimpleArrayMap r10 = (androidx.collection.SimpleArrayMap) r10
            boolean r10 = r10.containsKey(r3)
            if (r10 == 0) goto L_0x0199
            java.lang.Object r10 = r9.mGraph
            androidx.collection.SimpleArrayMap r10 = (androidx.collection.SimpleArrayMap) r10
            java.util.Objects.requireNonNull(r10)
            java.lang.Object r10 = r10.getOrDefault(r6, r7)
            java.util.ArrayList r10 = (java.util.ArrayList) r10
            if (r10 != 0) goto L_0x0195
            java.lang.Object r10 = r9.mListPool
            androidx.core.util.Pools$SimplePool r10 = (androidx.core.util.Pools$SimplePool) r10
            java.lang.Object r10 = r10.acquire()
            java.util.ArrayList r10 = (java.util.ArrayList) r10
            if (r10 != 0) goto L_0x018e
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
        L_0x018e:
            java.lang.Object r9 = r9.mGraph
            androidx.collection.SimpleArrayMap r9 = (androidx.collection.SimpleArrayMap) r9
            r9.put(r6, r10)
        L_0x0195:
            r10.add(r3)
            goto L_0x01a1
        L_0x0199:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.String r0 = "All nodes must be present in the graph before being added as an edge"
            r12.<init>(r0)
            throw r12
        L_0x01a1:
            int r5 = r5 + 1
            goto L_0x00f0
        L_0x01a5:
            int r1 = r1 + 1
            goto L_0x003c
        L_0x01a9:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Could not find CoordinatorLayout descendant view with id "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            android.content.res.Resources r12 = r12.getResources()
            int r2 = r4.mAnchorId
            java.lang.String r12 = r12.getResourceName(r2)
            r1.append(r12)
            java.lang.String r12 = " to anchor view "
            r1.append(r12)
            r1.append(r3)
            java.lang.String r12 = r1.toString()
            r0.<init>(r12)
            throw r0
        L_0x01ce:
            java.util.ArrayList r0 = r12.mDependencySortedChildren
            androidx.coordinatorlayout.widget.DirectedAcyclicGraph r1 = r12.mChildDag
            java.util.Objects.requireNonNull(r1)
            java.lang.Object r3 = r1.mSortResult
            java.util.ArrayList r3 = (java.util.ArrayList) r3
            r3.clear()
            java.lang.Object r3 = r1.mSortTmpMarked
            java.util.HashSet r3 = (java.util.HashSet) r3
            r3.clear()
            java.lang.Object r3 = r1.mGraph
            androidx.collection.SimpleArrayMap r3 = (androidx.collection.SimpleArrayMap) r3
            java.util.Objects.requireNonNull(r3)
            int r3 = r3.mSize
        L_0x01ec:
            if (r2 >= r3) goto L_0x0204
            java.lang.Object r4 = r1.mGraph
            androidx.collection.SimpleArrayMap r4 = (androidx.collection.SimpleArrayMap) r4
            java.lang.Object r4 = r4.keyAt(r2)
            java.lang.Object r5 = r1.mSortResult
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            java.lang.Object r6 = r1.mSortTmpMarked
            java.util.HashSet r6 = (java.util.HashSet) r6
            r1.dfs(r4, r5, r6)
            int r2 = r2 + 1
            goto L_0x01ec
        L_0x0204:
            java.lang.Object r1 = r1.mSortResult
            java.util.ArrayList r1 = (java.util.ArrayList) r1
            r0.addAll(r1)
            java.util.ArrayList r12 = r12.mDependencySortedChildren
            java.util.Collections.reverse(r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.prepareChildren():void");
    }

    public final void resetTouchBehaviors() {
        View view = this.mBehaviorTouchView;
        if (view != null) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Objects.requireNonNull(layoutParams);
            Behavior behavior = layoutParams.mBehavior;
            if (behavior != null) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                behavior.onTouchEvent(this, this.mBehaviorTouchView, obtain);
                obtain.recycle();
            }
            this.mBehaviorTouchView = null;
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams2 = (LayoutParams) getChildAt(i).getLayoutParams();
            Objects.requireNonNull(layoutParams2);
            layoutParams2.mDidBlockInteraction = false;
        }
        this.mDisallowInterceptReset = false;
    }

    public final void setupForInsets() {
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api16Impl.getFitsSystemWindows(this)) {
            if (this.mApplyWindowInsetsListener == null) {
                this.mApplyWindowInsetsListener = new OnApplyWindowInsetsListener() {
                    public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        boolean z;
                        CoordinatorLayout coordinatorLayout = CoordinatorLayout.this;
                        Objects.requireNonNull(coordinatorLayout);
                        if (!Objects.equals(coordinatorLayout.mLastInsets, windowInsetsCompat)) {
                            coordinatorLayout.mLastInsets = windowInsetsCompat;
                            boolean z2 = true;
                            if (windowInsetsCompat.getSystemWindowInsetTop() > 0) {
                                z = true;
                            } else {
                                z = false;
                            }
                            coordinatorLayout.mDrawStatusBarBackground = z;
                            if (z || coordinatorLayout.getBackground() != null) {
                                z2 = false;
                            }
                            coordinatorLayout.setWillNotDraw(z2);
                            if (!windowInsetsCompat.mImpl.isConsumed()) {
                                int childCount = coordinatorLayout.getChildCount();
                                for (int i = 0; i < childCount; i++) {
                                    View childAt = coordinatorLayout.getChildAt(i);
                                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                    if (ViewCompat.Api16Impl.getFitsSystemWindows(childAt)) {
                                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                                        Objects.requireNonNull(layoutParams);
                                        if (layoutParams.mBehavior != null && windowInsetsCompat.mImpl.isConsumed()) {
                                            break;
                                        }
                                    }
                                }
                            }
                            coordinatorLayout.requestLayout();
                        }
                        return windowInsetsCompat;
                    }
                };
            }
            ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, this.mApplyWindowInsetsListener);
            setSystemUiVisibility(1280);
            return;
        }
        ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(this, (OnApplyWindowInsetsListener) null);
    }

    public CoordinatorLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray typedArray;
        this.mDependencySortedChildren = new ArrayList();
        this.mChildDag = new DirectedAcyclicGraph();
        this.mTempList1 = new ArrayList();
        this.mBehaviorConsumed = new int[2];
        this.mNestedScrollingV2ConsumedCompat = new int[2];
        this.mNestedScrollingParentHelper = new NestedScrollingParentHelper();
        if (i == 0) {
            typedArray = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout, 0, 2132018741);
        } else {
            typedArray = context.obtainStyledAttributes(attributeSet, R$styleable.CoordinatorLayout, i, 0);
        }
        if (i == 0) {
            int[] iArr = R$styleable.CoordinatorLayout;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr, attributeSet, typedArray, 0, 2132018741);
        } else {
            int[] iArr2 = R$styleable.CoordinatorLayout;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api29Impl.saveAttributeDataForStyleable(this, context, iArr2, attributeSet, typedArray, i, 0);
        }
        int resourceId = typedArray.getResourceId(0, 0);
        if (resourceId != 0) {
            Resources resources = context.getResources();
            this.mKeylines = resources.getIntArray(resourceId);
            float f = resources.getDisplayMetrics().density;
            int length = this.mKeylines.length;
            for (int i2 = 0; i2 < length; i2++) {
                int[] iArr3 = this.mKeylines;
                iArr3[i2] = (int) (((float) iArr3[i2]) * f);
            }
        }
        this.mStatusBarBackground = typedArray.getDrawable(1);
        typedArray.recycle();
        setupForInsets();
        super.setOnHierarchyChangeListener(new HierarchyChangeListener());
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api16Impl.getImportantForAccessibility(this) == 0) {
            ViewCompat.Api16Impl.setImportantForAccessibility(this, 1);
        }
    }

    public static LayoutParams getResolvedLayoutParams(View view) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        if (!layoutParams.mBehaviorResolved) {
            if (view instanceof AttachedBehavior) {
                Behavior behavior = ((AttachedBehavior) view).getBehavior();
                if (behavior == null) {
                    Log.e("CoordinatorLayout", "Attached behavior class is null");
                }
                layoutParams.setBehavior(behavior);
                layoutParams.mBehaviorResolved = true;
            } else {
                DefaultBehavior defaultBehavior = null;
                for (Class cls = view.getClass(); cls != null; cls = cls.getSuperclass()) {
                    defaultBehavior = (DefaultBehavior) cls.getAnnotation(DefaultBehavior.class);
                    if (defaultBehavior != null) {
                        break;
                    }
                }
                if (defaultBehavior != null) {
                    try {
                        layoutParams.setBehavior((Behavior) defaultBehavior.value().getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
                    } catch (Exception e) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Default behavior class ");
                        m.append(defaultBehavior.value().getName());
                        m.append(" could not be instantiated. Did you forget a default constructor?");
                        Log.e("CoordinatorLayout", m.toString(), e);
                    }
                }
                layoutParams.mBehaviorResolved = true;
            }
        }
        return layoutParams;
    }

    public static void setInsetOffsetX(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i2 = layoutParams.mInsetOffsetX;
        if (i2 != i) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.offsetLeftAndRight(i - i2);
            layoutParams.mInsetOffsetX = i;
        }
    }

    public static void setInsetOffsetY(View view, int i) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int i2 = layoutParams.mInsetOffsetY;
        if (i2 != i) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.offsetTopAndBottom(i - i2);
            layoutParams.mInsetOffsetY = i;
        }
    }

    public final void constrainChildRect(LayoutParams layoutParams, Rect rect, int i, int i2) {
        int width = getWidth();
        int height = getHeight();
        int max = Math.max(getPaddingLeft() + layoutParams.leftMargin, Math.min(rect.left, ((width - getPaddingRight()) - i) - layoutParams.rightMargin));
        int max2 = Math.max(getPaddingTop() + layoutParams.topMargin, Math.min(rect.top, ((height - getPaddingBottom()) - i2) - layoutParams.bottomMargin));
        rect.set(max, max2, i + max, i2 + max2);
    }

    public final boolean drawChild(Canvas canvas, View view, long j) {
        Behavior behavior = ((LayoutParams) view.getLayoutParams()).mBehavior;
        if (behavior != null) {
            Objects.requireNonNull(behavior);
        }
        return super.drawChild(canvas, view, j);
    }

    public final void drawableStateChanged() {
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        Drawable drawable = this.mStatusBarBackground;
        boolean z = false;
        if (drawable != null && drawable.isStateful()) {
            z = false | drawable.setState(drawableState);
        }
        if (z) {
            invalidate();
        }
    }

    public final void getChildRect(View view, boolean z, Rect rect) {
        if (view.isLayoutRequested() || view.getVisibility() == 8) {
            rect.setEmpty();
        } else if (z) {
            getDescendantRect(view, rect);
        } else {
            rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        }
    }

    public final List<View> getDependencySortedChildren() {
        prepareChildren();
        return Collections.unmodifiableList(this.mDependencySortedChildren);
    }

    public final int getSuggestedMinimumHeight() {
        return Math.max(super.getSuggestedMinimumHeight(), getPaddingBottom() + getPaddingTop());
    }

    public final int getSuggestedMinimumWidth() {
        return Math.max(super.getSuggestedMinimumWidth(), getPaddingRight() + getPaddingLeft());
    }

    public final boolean isPointInChildBounds(View view, int i, int i2) {
        Rect acquireTempRect = acquireTempRect();
        getDescendantRect(view, acquireTempRect);
        try {
            return acquireTempRect.contains(i, i2);
        } finally {
            acquireTempRect.setEmpty();
            sRectPool.release(acquireTempRect);
        }
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        resetTouchBehaviors();
        if (this.mNeedsPreDrawListener) {
            if (this.mOnPreDrawListener == null) {
                this.mOnPreDrawListener = new OnPreDrawListener();
            }
            getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        }
        if (this.mLastInsets == null) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.getFitsSystemWindows(this)) {
                ViewCompat.Api20Impl.requestApplyInsets(this);
            }
        }
        this.mIsAttachedToWindow = true;
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resetTouchBehaviors();
        if (this.mNeedsPreDrawListener && this.mOnPreDrawListener != null) {
            getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        }
        View view = this.mNestedScrollingTarget;
        if (view != null) {
            onStopNestedScroll(view);
        }
        this.mIsAttachedToWindow = false;
    }

    public final void onDraw(Canvas canvas) {
        int i;
        super.onDraw(canvas);
        if (this.mDrawStatusBarBackground && this.mStatusBarBackground != null) {
            WindowInsetsCompat windowInsetsCompat = this.mLastInsets;
            if (windowInsetsCompat != null) {
                i = windowInsetsCompat.getSystemWindowInsetTop();
            } else {
                i = 0;
            }
            if (i > 0) {
                this.mStatusBarBackground.setBounds(0, 0, getWidth(), i);
                this.mStatusBarBackground.draw(canvas);
            }
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            resetTouchBehaviors();
        }
        boolean performIntercept = performIntercept(motionEvent, 0);
        if (actionMasked == 1 || actionMasked == 3) {
            this.mBehaviorTouchView = null;
            resetTouchBehaviors();
        }
        return performIntercept;
    }

    public final void onLayoutChild(View view, int i) {
        boolean z;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Objects.requireNonNull(layoutParams);
        View view2 = layoutParams.mAnchorView;
        int i2 = 0;
        if (view2 != null || layoutParams.mAnchorId == -1) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            throw new IllegalStateException("An anchor may not be changed after CoordinatorLayout measurement begins before layout is complete.");
        } else if (view2 != null) {
            Rect acquireTempRect = acquireTempRect();
            Rect acquireTempRect2 = acquireTempRect();
            try {
                getDescendantRect(view2, acquireTempRect);
                LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
                int measuredWidth = view.getMeasuredWidth();
                int measuredHeight = view.getMeasuredHeight();
                getDesiredAnchoredChildRectWithoutConstraints(i, acquireTempRect, acquireTempRect2, layoutParams2, measuredWidth, measuredHeight);
                constrainChildRect(layoutParams2, acquireTempRect2, measuredWidth, measuredHeight);
                view.layout(acquireTempRect2.left, acquireTempRect2.top, acquireTempRect2.right, acquireTempRect2.bottom);
            } finally {
                acquireTempRect.setEmpty();
                Pools$SynchronizedPool pools$SynchronizedPool = sRectPool;
                pools$SynchronizedPool.release(acquireTempRect);
                acquireTempRect2.setEmpty();
                pools$SynchronizedPool.release(acquireTempRect2);
            }
        } else {
            int i3 = layoutParams.keyline;
            if (i3 >= 0) {
                LayoutParams layoutParams3 = (LayoutParams) view.getLayoutParams();
                int i4 = layoutParams3.gravity;
                if (i4 == 0) {
                    i4 = 8388661;
                }
                int absoluteGravity = Gravity.getAbsoluteGravity(i4, i);
                int i5 = absoluteGravity & 7;
                int i6 = absoluteGravity & 112;
                int width = getWidth();
                int height = getHeight();
                int measuredWidth2 = view.getMeasuredWidth();
                int measuredHeight2 = view.getMeasuredHeight();
                if (i == 1) {
                    i3 = width - i3;
                }
                int keyline = getKeyline(i3) - measuredWidth2;
                if (i5 == 1) {
                    keyline += measuredWidth2 / 2;
                } else if (i5 == 5) {
                    keyline += measuredWidth2;
                }
                if (i6 == 16) {
                    i2 = 0 + (measuredHeight2 / 2);
                } else if (i6 == 80) {
                    i2 = measuredHeight2 + 0;
                }
                int max = Math.max(getPaddingLeft() + layoutParams3.leftMargin, Math.min(keyline, ((width - getPaddingRight()) - measuredWidth2) - layoutParams3.rightMargin));
                int max2 = Math.max(getPaddingTop() + layoutParams3.topMargin, Math.min(i2, ((height - getPaddingBottom()) - measuredHeight2) - layoutParams3.bottomMargin));
                view.layout(max, max2, measuredWidth2 + max, measuredHeight2 + max2);
                return;
            }
            LayoutParams layoutParams4 = (LayoutParams) view.getLayoutParams();
            Rect acquireTempRect3 = acquireTempRect();
            acquireTempRect3.set(getPaddingLeft() + layoutParams4.leftMargin, getPaddingTop() + layoutParams4.topMargin, (getWidth() - getPaddingRight()) - layoutParams4.rightMargin, (getHeight() - getPaddingBottom()) - layoutParams4.bottomMargin);
            if (this.mLastInsets != null) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api16Impl.getFitsSystemWindows(this) && !ViewCompat.Api16Impl.getFitsSystemWindows(view)) {
                    acquireTempRect3.left = this.mLastInsets.getSystemWindowInsetLeft() + acquireTempRect3.left;
                    acquireTempRect3.top = this.mLastInsets.getSystemWindowInsetTop() + acquireTempRect3.top;
                    acquireTempRect3.right -= this.mLastInsets.getSystemWindowInsetRight();
                    acquireTempRect3.bottom -= this.mLastInsets.getSystemWindowInsetBottom();
                }
            }
            Rect acquireTempRect4 = acquireTempRect();
            int i7 = layoutParams4.gravity;
            if ((i7 & 7) == 0) {
                i7 |= 8388611;
            }
            if ((i7 & 112) == 0) {
                i7 |= 48;
            }
            Gravity.apply(i7, view.getMeasuredWidth(), view.getMeasuredHeight(), acquireTempRect3, acquireTempRect4, i);
            view.layout(acquireTempRect4.left, acquireTempRect4.top, acquireTempRect4.right, acquireTempRect4.bottom);
            acquireTempRect3.setEmpty();
            Pools$SynchronizedPool pools$SynchronizedPool2 = sRectPool;
            pools$SynchronizedPool2.release(acquireTempRect3);
            acquireTempRect4.setEmpty();
            pools$SynchronizedPool2.release(acquireTempRect4);
        }
    }

    public final boolean onNestedFling(View view, float f, float f2, boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(0)) {
                    Behavior behavior = layoutParams.mBehavior;
                }
            }
        }
        return false;
    }

    public final boolean onNestedPreFling(View view, float f, float f2) {
        Behavior behavior;
        int childCount = getChildCount();
        boolean z = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(0) && (behavior = layoutParams.mBehavior) != null) {
                    z |= behavior.onNestedPreFling(view);
                }
            }
        }
        return z;
    }

    public final void onNestedScroll(View view, int i, int i2, int i3, int i4, int i5, int[] iArr) {
        Behavior behavior;
        int i6;
        int i7;
        int childCount = getChildCount();
        boolean z = false;
        int i8 = 0;
        int i9 = 0;
        for (int i10 = 0; i10 < childCount; i10++) {
            View childAt = getChildAt(i10);
            if (childAt.getVisibility() == 8) {
                int i11 = i5;
            } else {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isNestedScrollAccepted(i5) && (behavior = layoutParams.mBehavior) != null) {
                    int[] iArr2 = this.mBehaviorConsumed;
                    iArr2[0] = 0;
                    iArr2[1] = 0;
                    behavior.onNestedScroll(this, childAt, i2, i3, i4, iArr2);
                    if (i3 > 0) {
                        i6 = Math.max(i8, this.mBehaviorConsumed[0]);
                    } else {
                        i6 = Math.min(i8, this.mBehaviorConsumed[0]);
                    }
                    i8 = i6;
                    if (i4 > 0) {
                        i7 = Math.max(i9, this.mBehaviorConsumed[1]);
                    } else {
                        i7 = Math.min(i9, this.mBehaviorConsumed[1]);
                    }
                    i9 = i7;
                    z = true;
                }
            }
        }
        iArr[0] = iArr[0] + i8;
        iArr[1] = iArr[1] + i9;
        if (z) {
            onChildViewsChanged(1);
        }
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        int actionMasked = motionEvent.getActionMasked();
        View view = this.mBehaviorTouchView;
        boolean z2 = false;
        if (view != null) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Objects.requireNonNull(layoutParams);
            Behavior behavior = layoutParams.mBehavior;
            z = behavior != null ? behavior.onTouchEvent(this, this.mBehaviorTouchView, motionEvent) : false;
        } else {
            z = performIntercept(motionEvent, 1);
            if (actionMasked != 0 && z) {
                z2 = true;
            }
        }
        if (this.mBehaviorTouchView == null || actionMasked == 3) {
            z |= super.onTouchEvent(motionEvent);
        } else if (z2) {
            MotionEvent obtain = MotionEvent.obtain(motionEvent);
            obtain.setAction(3);
            super.onTouchEvent(obtain);
            obtain.recycle();
        }
        if (actionMasked == 1 || actionMasked == 3) {
            this.mBehaviorTouchView = null;
            resetTouchBehaviors();
        }
        return z;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0067, code lost:
        r6 = performEvent(r10, r8, r14, r15);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean performIntercept(android.view.MotionEvent r14, int r15) {
        /*
            r13 = this;
            int r0 = r14.getActionMasked()
            java.util.ArrayList r1 = r13.mTempList1
            r1.clear()
            boolean r2 = r13.isChildrenDrawingOrderEnabled()
            int r3 = r13.getChildCount()
            int r4 = r3 + -1
        L_0x0013:
            if (r4 < 0) goto L_0x0027
            if (r2 == 0) goto L_0x001c
            int r5 = r13.getChildDrawingOrder(r3, r4)
            goto L_0x001d
        L_0x001c:
            r5 = r4
        L_0x001d:
            android.view.View r5 = r13.getChildAt(r5)
            r1.add(r5)
            int r4 = r4 + -1
            goto L_0x0013
        L_0x0027:
            androidx.coordinatorlayout.widget.CoordinatorLayout$ViewElevationComparator r2 = TOP_SORTED_CHILDREN_COMPARATOR
            if (r2 == 0) goto L_0x002e
            java.util.Collections.sort(r1, r2)
        L_0x002e:
            int r2 = r1.size()
            r3 = 0
            r4 = 0
            r5 = r4
            r6 = r5
            r7 = r6
        L_0x0037:
            if (r5 >= r2) goto L_0x00b7
            java.lang.Object r8 = r1.get(r5)
            android.view.View r8 = (android.view.View) r8
            android.view.ViewGroup$LayoutParams r9 = r8.getLayoutParams()
            androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r9 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r9
            java.util.Objects.requireNonNull(r9)
            androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r10 = r9.mBehavior
            r11 = 3
            if (r6 != 0) goto L_0x004f
            if (r7 == 0) goto L_0x0060
        L_0x004f:
            if (r0 == 0) goto L_0x0060
            if (r10 == 0) goto L_0x00b4
            if (r3 != 0) goto L_0x005c
            android.view.MotionEvent r3 = android.view.MotionEvent.obtain(r14)
            r3.setAction(r11)
        L_0x005c:
            r13.performEvent(r10, r8, r3, r15)
            goto L_0x00b4
        L_0x0060:
            r12 = 1
            if (r7 != 0) goto L_0x0098
            if (r6 != 0) goto L_0x0098
            if (r10 == 0) goto L_0x0098
            boolean r6 = r13.performEvent(r10, r8, r14, r15)
            if (r6 == 0) goto L_0x0098
            r13.mBehaviorTouchView = r8
            if (r0 == r11) goto L_0x0098
            if (r0 == r12) goto L_0x0098
            r7 = r4
        L_0x0074:
            if (r7 >= r5) goto L_0x0098
            java.lang.Object r8 = r1.get(r7)
            android.view.View r8 = (android.view.View) r8
            android.view.ViewGroup$LayoutParams r10 = r8.getLayoutParams()
            androidx.coordinatorlayout.widget.CoordinatorLayout$LayoutParams r10 = (androidx.coordinatorlayout.widget.CoordinatorLayout.LayoutParams) r10
            java.util.Objects.requireNonNull(r10)
            androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r10 = r10.mBehavior
            if (r10 == 0) goto L_0x0095
            if (r3 != 0) goto L_0x0092
            android.view.MotionEvent r3 = android.view.MotionEvent.obtain(r14)
            r3.setAction(r11)
        L_0x0092:
            r13.performEvent(r10, r8, r3, r15)
        L_0x0095:
            int r7 = r7 + 1
            goto L_0x0074
        L_0x0098:
            androidx.coordinatorlayout.widget.CoordinatorLayout$Behavior r7 = r9.mBehavior
            if (r7 != 0) goto L_0x009e
            r9.mDidBlockInteraction = r4
        L_0x009e:
            boolean r7 = r9.mDidBlockInteraction
            if (r7 == 0) goto L_0x00a4
            r8 = r12
            goto L_0x00a8
        L_0x00a4:
            r8 = r7 | 0
            r9.mDidBlockInteraction = r8
        L_0x00a8:
            if (r8 == 0) goto L_0x00ae
            if (r7 != 0) goto L_0x00ae
            r7 = r12
            goto L_0x00af
        L_0x00ae:
            r7 = r4
        L_0x00af:
            if (r8 == 0) goto L_0x00b4
            if (r7 != 0) goto L_0x00b4
            goto L_0x00b7
        L_0x00b4:
            int r5 = r5 + 1
            goto L_0x0037
        L_0x00b7:
            r1.clear()
            if (r3 == 0) goto L_0x00bf
            r3.recycle()
        L_0x00bf:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.coordinatorlayout.widget.CoordinatorLayout.performIntercept(android.view.MotionEvent, int):boolean");
    }

    public final boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Objects.requireNonNull(layoutParams);
        Behavior behavior = layoutParams.mBehavior;
        if (behavior == null || !behavior.onRequestChildRectangleOnScreen(this, view, rect, z)) {
            return super.requestChildRectangleOnScreen(view, rect, z);
        }
        return true;
    }

    public final void requestDisallowInterceptTouchEvent(boolean z) {
        super.requestDisallowInterceptTouchEvent(z);
        if (z && !this.mDisallowInterceptReset) {
            if (this.mBehaviorTouchView == null) {
                int childCount = getChildCount();
                MotionEvent motionEvent = null;
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    Objects.requireNonNull(layoutParams);
                    Behavior behavior = layoutParams.mBehavior;
                    if (behavior != null) {
                        if (motionEvent == null) {
                            long uptimeMillis = SystemClock.uptimeMillis();
                            motionEvent = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                        }
                        behavior.onInterceptTouchEvent(this, childAt, motionEvent);
                    }
                }
                if (motionEvent != null) {
                    motionEvent.recycle();
                }
            }
            resetTouchBehaviors();
            this.mDisallowInterceptReset = true;
        }
    }

    public final void setFitsSystemWindows(boolean z) {
        super.setFitsSystemWindows(z);
        setupForInsets();
    }

    public final void setVisibility(int i) {
        boolean z;
        super.setVisibility(i);
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        Drawable drawable = this.mStatusBarBackground;
        if (drawable != null && drawable.isVisible() != z) {
            this.mStatusBarBackground.setVisible(z, false);
        }
    }

    public final boolean verifyDrawable(Drawable drawable) {
        if (super.verifyDrawable(drawable) || drawable == this.mStatusBarBackground) {
            return true;
        }
        return false;
    }

    public final void setOnHierarchyChangeListener(ViewGroup.OnHierarchyChangeListener onHierarchyChangeListener) {
        this.mOnHierarchyChangeListener = onHierarchyChangeListener;
    }
}
