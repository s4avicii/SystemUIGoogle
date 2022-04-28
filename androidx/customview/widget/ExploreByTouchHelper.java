package androidx.customview.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.customview.widget.FocusStrategy;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    public static final Rect INVALID_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final C01271 NODE_ADAPTER = new FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat>() {
    };
    public static final C01282 SPARSE_VALUES_ADAPTER = new Object() {
    };
    public int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
    public final View mHost;
    public int mHoveredVirtualViewId = Integer.MIN_VALUE;
    public int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    public final AccessibilityManager mManager;
    public MyNodeProvider mNodeProvider;
    public final int[] mTempGlobalRect = new int[2];
    public final Rect mTempParentRect = new Rect();
    public final Rect mTempScreenRect = new Rect();
    public final Rect mTempVisibleRect = new Rect();

    public class MyNodeProvider extends AccessibilityNodeProviderCompat {
        public final AccessibilityNodeInfoCompat findFocus(int i) {
            int i2;
            if (i == 2) {
                i2 = ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId;
            } else {
                i2 = ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId;
            }
            if (i2 == Integer.MIN_VALUE) {
                return null;
            }
            return createAccessibilityNodeInfo(i2);
        }

        public MyNodeProvider() {
        }

        public final AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int i) {
            return new AccessibilityNodeInfoCompat(AccessibilityNodeInfo.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(i).mInfo));
        }

        public final boolean performAction(int i, int i2, Bundle bundle) {
            int i3;
            ExploreByTouchHelper exploreByTouchHelper = ExploreByTouchHelper.this;
            Objects.requireNonNull(exploreByTouchHelper);
            if (i != -1) {
                boolean z = true;
                if (i2 == 1) {
                    return exploreByTouchHelper.requestKeyboardFocusForVirtualView(i);
                }
                if (i2 == 2) {
                    return exploreByTouchHelper.clearKeyboardFocusForVirtualView(i);
                }
                if (i2 != 64) {
                    if (i2 != 128) {
                        return exploreByTouchHelper.onPerformActionForVirtualView(i, i2, bundle);
                    }
                    if (exploreByTouchHelper.mAccessibilityFocusedVirtualViewId == i) {
                        exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
                        exploreByTouchHelper.mHost.invalidate();
                        exploreByTouchHelper.sendEventForVirtualView(i, 65536);
                        return z;
                    }
                } else if (exploreByTouchHelper.mManager.isEnabled() && exploreByTouchHelper.mManager.isTouchExplorationEnabled() && (i3 = exploreByTouchHelper.mAccessibilityFocusedVirtualViewId) != i) {
                    if (i3 != Integer.MIN_VALUE) {
                        exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
                        exploreByTouchHelper.mHost.invalidate();
                        exploreByTouchHelper.sendEventForVirtualView(i3, 65536);
                    }
                    exploreByTouchHelper.mAccessibilityFocusedVirtualViewId = i;
                    exploreByTouchHelper.mHost.invalidate();
                    exploreByTouchHelper.sendEventForVirtualView(i, 32768);
                    return z;
                }
                z = false;
                return z;
            }
            View view = exploreByTouchHelper.mHost;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api16Impl.performAccessibilityAction(view, i2, bundle);
        }
    }

    public final AccessibilityEvent createEvent(int i, int i2) {
        if (i != -1) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
            AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo = obtainAccessibilityNodeInfo(i);
            obtain.getText().add(obtainAccessibilityNodeInfo.getText());
            obtain.setContentDescription(obtainAccessibilityNodeInfo.mInfo.getContentDescription());
            obtain.setScrollable(obtainAccessibilityNodeInfo.mInfo.isScrollable());
            obtain.setPassword(obtainAccessibilityNodeInfo.mInfo.isPassword());
            obtain.setEnabled(obtainAccessibilityNodeInfo.mInfo.isEnabled());
            obtain.setChecked(obtainAccessibilityNodeInfo.mInfo.isChecked());
            onPopulateEventForVirtualView(i, obtain);
            if (!obtain.getText().isEmpty() || obtain.getContentDescription() != null) {
                obtain.setClassName(obtainAccessibilityNodeInfo.mInfo.getClassName());
                obtain.setSource(this.mHost, i);
                obtain.setPackageName(this.mHost.getContext().getPackageName());
                return obtain;
            }
            throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
        }
        AccessibilityEvent obtain2 = AccessibilityEvent.obtain(i2);
        this.mHost.onInitializeAccessibilityEvent(obtain2);
        return obtain2;
    }

    public abstract int getVirtualViewAt(float f, float f2);

    public abstract void getVisibleVirtualViews(ArrayList arrayList);

    public final AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int i) {
        if (i != -1) {
            return createNodeForChild(i);
        }
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain(this.mHost);
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = new AccessibilityNodeInfoCompat(obtain);
        View view = this.mHost;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        view.onInitializeAccessibilityNodeInfo(obtain);
        ArrayList arrayList = new ArrayList();
        getVisibleVirtualViews(arrayList);
        if (obtain.getChildCount() <= 0 || arrayList.size() <= 0) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < size; i2++) {
                accessibilityNodeInfoCompat.mInfo.addChild(this.mHost, ((Integer) arrayList.get(i2)).intValue());
            }
            return accessibilityNodeInfoCompat;
        }
        throw new RuntimeException("Views cannot have both real and virtual children");
    }

    public abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle);

    public void onPopulateEventForVirtualView(int i, AccessibilityEvent accessibilityEvent) {
    }

    public void onPopulateNodeForHost(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
    }

    public abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    public void onVirtualViewKeyboardFocusChanged(int i, boolean z) {
    }

    public final boolean sendEventForVirtualView(int i, int i2) {
        ViewParent parent;
        if (i == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return false;
        }
        return parent.requestSendAccessibilityEvent(this.mHost, createEvent(i, i2));
    }

    public final boolean clearKeyboardFocusForVirtualView(int i) {
        if (this.mKeyboardFocusedVirtualViewId != i) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(i, false);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public final boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int i;
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 7 || action == 9) {
            int virtualViewAt = getVirtualViewAt(motionEvent.getX(), motionEvent.getY());
            int i2 = this.mHoveredVirtualViewId;
            if (i2 != virtualViewAt) {
                this.mHoveredVirtualViewId = virtualViewAt;
                sendEventForVirtualView(virtualViewAt, 128);
                sendEventForVirtualView(i2, 256);
            }
            if (virtualViewAt != Integer.MIN_VALUE) {
                return true;
            }
            return false;
        } else if (action != 10 || (i = this.mHoveredVirtualViewId) == Integer.MIN_VALUE) {
            return false;
        } else {
            if (i != Integer.MIN_VALUE) {
                this.mHoveredVirtualViewId = Integer.MIN_VALUE;
                sendEventForVirtualView(Integer.MIN_VALUE, 128);
                sendEventForVirtualView(i, 256);
            }
            return true;
        }
    }

    public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new MyNodeProvider();
        }
        return this.mNodeProvider;
    }

    public final void invalidateVirtualView(int i) {
        ViewParent parent;
        if (i != Integer.MIN_VALUE && this.mManager.isEnabled() && (parent = this.mHost.getParent()) != null) {
            AccessibilityEvent createEvent = createEvent(i, 2048);
            createEvent.setContentChangeTypes(0);
            parent.requestSendAccessibilityEvent(this.mHost, createEvent);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: androidx.core.view.accessibility.AccessibilityNodeInfoCompat} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: androidx.core.view.accessibility.AccessibilityNodeInfoCompat} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v23, resolved type: androidx.core.view.accessibility.AccessibilityNodeInfoCompat} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v24, resolved type: androidx.core.view.accessibility.AccessibilityNodeInfoCompat} */
    /* JADX WARNING: Code restructure failed: missing block: B:58:0x0144, code lost:
        if (r13 < ((r17 * r17) + ((r12 * 13) * r12))) goto L_0x0146;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:106:0x0150 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x014b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean moveFocus(int r20, android.graphics.Rect r21) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r21
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r0.getVisibleVirtualViews(r3)
            androidx.collection.SparseArrayCompat r4 = new androidx.collection.SparseArrayCompat
            r4.<init>()
            r5 = 0
            r6 = r5
        L_0x0015:
            int r7 = r3.size()
            if (r6 >= r7) goto L_0x0039
            java.lang.Object r7 = r3.get(r6)
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r7 = r7.intValue()
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat r7 = r0.createNodeForChild(r7)
            java.lang.Object r8 = r3.get(r6)
            java.lang.Integer r8 = (java.lang.Integer) r8
            int r8 = r8.intValue()
            r4.put(r8, r7)
            int r6 = r6 + 1
            goto L_0x0015
        L_0x0039:
            int r3 = r0.mKeyboardFocusedVirtualViewId
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            r7 = 0
            if (r3 != r6) goto L_0x0042
            r3 = r7
            goto L_0x0048
        L_0x0042:
            java.lang.Object r3 = r4.get(r3, r7)
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat r3 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat) r3
        L_0x0048:
            r8 = 2
            r9 = -1
            r10 = 1
            if (r1 == r10) goto L_0x0157
            if (r1 == r8) goto L_0x0157
            r8 = 130(0x82, float:1.82E-43)
            r11 = 66
            r12 = 33
            r13 = 17
            if (r1 == r13) goto L_0x0068
            if (r1 == r12) goto L_0x0068
            if (r1 == r11) goto L_0x0068
            if (r1 != r8) goto L_0x0060
            goto L_0x0068
        L_0x0060:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}."
            r0.<init>(r1)
            throw r0
        L_0x0068:
            android.graphics.Rect r14 = new android.graphics.Rect
            r14.<init>()
            int r15 = r0.mKeyboardFocusedVirtualViewId
            java.lang.String r7 = "direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}."
            if (r15 == r6) goto L_0x007d
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat r2 = r0.obtainAccessibilityNodeInfo(r15)
            android.view.accessibility.AccessibilityNodeInfo r2 = r2.mInfo
            r2.getBoundsInScreen(r14)
            goto L_0x00aa
        L_0x007d:
            if (r2 == 0) goto L_0x0083
            r14.set(r2)
            goto L_0x00aa
        L_0x0083:
            android.view.View r2 = r0.mHost
            int r15 = r2.getWidth()
            int r2 = r2.getHeight()
            if (r1 == r13) goto L_0x00a7
            if (r1 == r12) goto L_0x00a3
            if (r1 == r11) goto L_0x009f
            if (r1 != r8) goto L_0x0099
            r14.set(r5, r9, r15, r9)
            goto L_0x00aa
        L_0x0099:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r7)
            throw r0
        L_0x009f:
            r14.set(r9, r5, r9, r2)
            goto L_0x00aa
        L_0x00a3:
            r14.set(r5, r2, r15, r2)
            goto L_0x00aa
        L_0x00a7:
            r14.set(r15, r5, r15, r2)
        L_0x00aa:
            androidx.customview.widget.ExploreByTouchHelper$2 r2 = SPARSE_VALUES_ADAPTER
            androidx.customview.widget.ExploreByTouchHelper$1 r15 = NODE_ADAPTER
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>(r14)
            if (r1 == r13) goto L_0x00de
            if (r1 == r12) goto L_0x00d5
            if (r1 == r11) goto L_0x00cb
            if (r1 != r8) goto L_0x00c5
            int r7 = r14.height()
            int r7 = r7 + r10
            int r7 = -r7
            r6.offset(r5, r7)
            goto L_0x00e6
        L_0x00c5:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r7)
            throw r0
        L_0x00cb:
            int r7 = r14.width()
            int r7 = r7 + r10
            int r7 = -r7
            r6.offset(r7, r5)
            goto L_0x00e6
        L_0x00d5:
            int r7 = r14.height()
            int r7 = r7 + r10
            r6.offset(r5, r7)
            goto L_0x00e6
        L_0x00de:
            int r7 = r14.width()
            int r7 = r7 + r10
            r6.offset(r7, r5)
        L_0x00e6:
            java.util.Objects.requireNonNull(r2)
            int r2 = r4.mSize
            android.graphics.Rect r7 = new android.graphics.Rect
            r7.<init>()
            r8 = r5
            r16 = 0
        L_0x00f3:
            if (r8 >= r2) goto L_0x0153
            java.lang.Object[] r11 = r4.mValues
            r11 = r11[r8]
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat r11 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat) r11
            if (r11 != r3) goto L_0x00fe
            goto L_0x0150
        L_0x00fe:
            java.util.Objects.requireNonNull(r15)
            java.util.Objects.requireNonNull(r11)
            android.view.accessibility.AccessibilityNodeInfo r12 = r11.mInfo
            r12.getBoundsInScreen(r7)
            boolean r12 = androidx.customview.widget.FocusStrategy.isCandidate(r14, r7, r1)
            if (r12 != 0) goto L_0x0110
            goto L_0x0148
        L_0x0110:
            boolean r12 = androidx.customview.widget.FocusStrategy.isCandidate(r14, r6, r1)
            if (r12 != 0) goto L_0x0117
            goto L_0x0146
        L_0x0117:
            boolean r12 = androidx.customview.widget.FocusStrategy.beamBeats(r1, r14, r7, r6)
            if (r12 == 0) goto L_0x011e
            goto L_0x0146
        L_0x011e:
            boolean r12 = androidx.customview.widget.FocusStrategy.beamBeats(r1, r14, r6, r7)
            if (r12 == 0) goto L_0x0125
            goto L_0x0148
        L_0x0125:
            int r12 = androidx.customview.widget.FocusStrategy.majorAxisDistance(r1, r14, r7)
            int r13 = androidx.customview.widget.FocusStrategy.minorAxisDistance(r1, r14, r7)
            int r17 = r12 * 13
            int r17 = r17 * r12
            int r13 = r13 * r13
            int r13 = r13 + r17
            int r12 = androidx.customview.widget.FocusStrategy.majorAxisDistance(r1, r14, r6)
            int r17 = androidx.customview.widget.FocusStrategy.minorAxisDistance(r1, r14, r6)
            int r18 = r12 * 13
            int r18 = r18 * r12
            int r17 = r17 * r17
            int r12 = r17 + r18
            if (r13 >= r12) goto L_0x0148
        L_0x0146:
            r12 = r10
            goto L_0x0149
        L_0x0148:
            r12 = r5
        L_0x0149:
            if (r12 == 0) goto L_0x0150
            r6.set(r7)
            r16 = r11
        L_0x0150:
            int r8 = r8 + 1
            goto L_0x00f3
        L_0x0153:
            r1 = r16
            goto L_0x01c2
        L_0x0157:
            android.view.View r2 = r0.mHost
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r6 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            int r2 = androidx.core.view.ViewCompat.Api17Impl.getLayoutDirection(r2)
            if (r2 != r10) goto L_0x0163
            r2 = r10
            goto L_0x0164
        L_0x0163:
            r2 = r5
        L_0x0164:
            androidx.customview.widget.ExploreByTouchHelper$2 r6 = SPARSE_VALUES_ADAPTER
            androidx.customview.widget.ExploreByTouchHelper$1 r7 = NODE_ADAPTER
            java.util.Objects.requireNonNull(r6)
            int r6 = r4.mSize
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>(r6)
            r12 = r5
        L_0x0173:
            if (r12 >= r6) goto L_0x0181
            java.lang.Object[] r13 = r4.mValues
            r13 = r13[r12]
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat r13 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat) r13
            r11.add(r13)
            int r12 = r12 + 1
            goto L_0x0173
        L_0x0181:
            androidx.customview.widget.FocusStrategy$SequentialComparator r6 = new androidx.customview.widget.FocusStrategy$SequentialComparator
            r6.<init>(r2, r7)
            java.util.Collections.sort(r11, r6)
            if (r1 == r10) goto L_0x01a9
            if (r1 != r8) goto L_0x01a1
            int r1 = r11.size()
            if (r3 != 0) goto L_0x0195
            r2 = r9
            goto L_0x0199
        L_0x0195:
            int r2 = r11.lastIndexOf(r3)
        L_0x0199:
            int r2 = r2 + r10
            if (r2 >= r1) goto L_0x01bc
            java.lang.Object r7 = r11.get(r2)
            goto L_0x01bd
        L_0x01a1:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD}."
            r0.<init>(r1)
            throw r0
        L_0x01a9:
            int r1 = r11.size()
            if (r3 != 0) goto L_0x01b0
            goto L_0x01b4
        L_0x01b0:
            int r1 = r11.indexOf(r3)
        L_0x01b4:
            int r1 = r1 + r9
            if (r1 < 0) goto L_0x01bc
            java.lang.Object r7 = r11.get(r1)
            goto L_0x01bd
        L_0x01bc:
            r7 = 0
        L_0x01bd:
            r16 = r7
            androidx.core.view.accessibility.AccessibilityNodeInfoCompat r16 = (androidx.core.view.accessibility.AccessibilityNodeInfoCompat) r16
            goto L_0x0153
        L_0x01c2:
            if (r1 != 0) goto L_0x01c7
            r6 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x01da
        L_0x01c7:
            int r2 = r4.mSize
            if (r5 >= r2) goto L_0x01d6
            java.lang.Object[] r2 = r4.mValues
            r2 = r2[r5]
            if (r2 != r1) goto L_0x01d3
            r9 = r5
            goto L_0x01d6
        L_0x01d3:
            int r5 = r5 + 1
            goto L_0x01c7
        L_0x01d6:
            int[] r1 = r4.mKeys
            r6 = r1[r9]
        L_0x01da:
            boolean r0 = r0.requestKeyboardFocusForVirtualView(r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.customview.widget.ExploreByTouchHelper.moveFocus(int, android.graphics.Rect):boolean");
    }

    public final boolean requestKeyboardFocusForVirtualView(int i) {
        int i2;
        if ((!this.mHost.isFocused() && !this.mHost.requestFocus()) || (i2 = this.mKeyboardFocusedVirtualViewId) == i) {
            return false;
        }
        if (i2 != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i2);
        }
        if (i == Integer.MIN_VALUE) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = i;
        onVirtualViewKeyboardFocusChanged(i, true);
        sendEventForVirtualView(i, 8);
        return true;
    }

    public ExploreByTouchHelper(View view) {
        if (view != null) {
            this.mHost = view;
            this.mManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
            view.setFocusable(true);
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api16Impl.getImportantForAccessibility(view) == 0) {
                ViewCompat.Api16Impl.setImportantForAccessibility(view, 1);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("View may not be null");
    }

    public final AccessibilityNodeInfoCompat createNodeForChild(int i) {
        boolean z;
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = new AccessibilityNodeInfoCompat(obtain);
        obtain.setEnabled(true);
        obtain.setFocusable(true);
        accessibilityNodeInfoCompat.setClassName("android.view.View");
        Rect rect = INVALID_BOUNDS;
        accessibilityNodeInfoCompat.setBoundsInParent(rect);
        obtain.setBoundsInScreen(rect);
        View view = this.mHost;
        accessibilityNodeInfoCompat.mParentVirtualDescendantId = -1;
        obtain.setParent(view);
        onPopulateNodeForVirtualView(i, accessibilityNodeInfoCompat);
        if (accessibilityNodeInfoCompat.getText() == null && obtain.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        obtain.getBoundsInParent(this.mTempParentRect);
        obtain.getBoundsInScreen(this.mTempScreenRect);
        if (!this.mTempParentRect.equals(rect) || !this.mTempScreenRect.equals(rect)) {
            int actions = obtain.getActions();
            if ((actions & 64) != 0) {
                throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            } else if ((actions & 128) == 0) {
                obtain.setPackageName(this.mHost.getContext().getPackageName());
                View view2 = this.mHost;
                accessibilityNodeInfoCompat.mVirtualDescendantId = i;
                obtain.setSource(view2, i);
                boolean z2 = false;
                if (this.mAccessibilityFocusedVirtualViewId == i) {
                    obtain.setAccessibilityFocused(true);
                    accessibilityNodeInfoCompat.addAction(128);
                } else {
                    obtain.setAccessibilityFocused(false);
                    accessibilityNodeInfoCompat.addAction(64);
                }
                if (this.mKeyboardFocusedVirtualViewId == i) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    accessibilityNodeInfoCompat.addAction(2);
                } else if (obtain.isFocusable()) {
                    accessibilityNodeInfoCompat.addAction(1);
                }
                obtain.setFocused(z);
                this.mHost.getLocationOnScreen(this.mTempGlobalRect);
                if (this.mTempScreenRect.equals(rect)) {
                    Rect rect2 = this.mTempParentRect;
                    accessibilityNodeInfoCompat.setBoundsInParent(rect2);
                    Rect rect3 = new Rect();
                    rect3.set(rect2);
                    if (accessibilityNodeInfoCompat.mParentVirtualDescendantId != -1) {
                        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat2 = new AccessibilityNodeInfoCompat(AccessibilityNodeInfo.obtain());
                        Rect rect4 = new Rect();
                        for (int i2 = accessibilityNodeInfoCompat.mParentVirtualDescendantId; i2 != -1; i2 = accessibilityNodeInfoCompat2.mParentVirtualDescendantId) {
                            View view3 = this.mHost;
                            accessibilityNodeInfoCompat2.mParentVirtualDescendantId = -1;
                            accessibilityNodeInfoCompat2.mInfo.setParent(view3, -1);
                            accessibilityNodeInfoCompat2.setBoundsInParent(INVALID_BOUNDS);
                            onPopulateNodeForVirtualView(i2, accessibilityNodeInfoCompat2);
                            accessibilityNodeInfoCompat2.mInfo.getBoundsInParent(rect4);
                            rect3.offset(rect4.left, rect4.top);
                        }
                        accessibilityNodeInfoCompat2.mInfo.recycle();
                    }
                    this.mHost.getLocationOnScreen(this.mTempGlobalRect);
                    rect3.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
                    accessibilityNodeInfoCompat.mInfo.setBoundsInScreen(rect3);
                    accessibilityNodeInfoCompat.mInfo.getBoundsInScreen(this.mTempScreenRect);
                }
                if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
                    this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
                    if (this.mTempScreenRect.intersect(this.mTempVisibleRect)) {
                        accessibilityNodeInfoCompat.mInfo.setBoundsInScreen(this.mTempScreenRect);
                        Rect rect5 = this.mTempScreenRect;
                        if (rect5 != null && !rect5.isEmpty() && this.mHost.getWindowVisibility() == 0) {
                            ViewParent parent = this.mHost.getParent();
                            while (true) {
                                if (parent instanceof View) {
                                    View view4 = (View) parent;
                                    if (view4.getAlpha() <= 0.0f || view4.getVisibility() != 0) {
                                        break;
                                    }
                                    parent = view4.getParent();
                                } else if (parent != null) {
                                    z2 = true;
                                }
                            }
                        }
                        if (z2) {
                            accessibilityNodeInfoCompat.mInfo.setVisibleToUser(true);
                        }
                    }
                }
                return accessibilityNodeInfoCompat;
            } else {
                throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            }
        } else {
            throw new RuntimeException("Callbacks must set parent bounds or screen bounds in populateNodeForVirtualViewId()");
        }
    }

    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        onPopulateNodeForHost(accessibilityNodeInfoCompat);
    }

    public final void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }
}
