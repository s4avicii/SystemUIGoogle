package androidx.core.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressLint({"PrivateConstructorForUtilityClass"})
public final class ViewCompat {
    public static final int[] ACCESSIBILITY_ACTIONS_RESOURCE_IDS = {C1777R.C1779id.accessibility_custom_action_0, C1777R.C1779id.accessibility_custom_action_1, C1777R.C1779id.accessibility_custom_action_2, C1777R.C1779id.accessibility_custom_action_3, C1777R.C1779id.accessibility_custom_action_4, C1777R.C1779id.accessibility_custom_action_5, C1777R.C1779id.accessibility_custom_action_6, C1777R.C1779id.accessibility_custom_action_7, C1777R.C1779id.accessibility_custom_action_8, C1777R.C1779id.accessibility_custom_action_9, C1777R.C1779id.accessibility_custom_action_10, C1777R.C1779id.accessibility_custom_action_11, C1777R.C1779id.accessibility_custom_action_12, C1777R.C1779id.accessibility_custom_action_13, C1777R.C1779id.accessibility_custom_action_14, C1777R.C1779id.accessibility_custom_action_15, C1777R.C1779id.accessibility_custom_action_16, C1777R.C1779id.accessibility_custom_action_17, C1777R.C1779id.accessibility_custom_action_18, C1777R.C1779id.accessibility_custom_action_19, C1777R.C1779id.accessibility_custom_action_20, C1777R.C1779id.accessibility_custom_action_21, C1777R.C1779id.accessibility_custom_action_22, C1777R.C1779id.accessibility_custom_action_23, C1777R.C1779id.accessibility_custom_action_24, C1777R.C1779id.accessibility_custom_action_25, C1777R.C1779id.accessibility_custom_action_26, C1777R.C1779id.accessibility_custom_action_27, C1777R.C1779id.accessibility_custom_action_28, C1777R.C1779id.accessibility_custom_action_29, C1777R.C1779id.accessibility_custom_action_30, C1777R.C1779id.accessibility_custom_action_31};
    public static final ViewCompat$$ExternalSyntheticLambda0 NO_OP_ON_RECEIVE_CONTENT_VIEW_BEHAVIOR = ViewCompat$$ExternalSyntheticLambda0.INSTANCE;
    public static final AccessibilityPaneVisibilityManager sAccessibilityPaneVisibilityManager = new AccessibilityPaneVisibilityManager();
    public static WeakHashMap<View, ViewPropertyAnimatorCompat> sViewPropertyAnimatorMap = null;

    public static abstract class AccessibilityViewProperty<T> {
        public final int mContentChangeType;
        public final int mFrameworkMinimumSdk;
        public final int mTagKey;
        public final Class<T> mType;

        public static boolean booleanNullToFalseEquals(Boolean bool, Boolean bool2) {
            boolean z;
            boolean z2;
            if (bool == null || !bool.booleanValue()) {
                z = false;
            } else {
                z = true;
            }
            if (bool2 == null || !bool2.booleanValue()) {
                z2 = false;
            } else {
                z2 = true;
            }
            return z == z2;
        }

        public abstract T frameworkGet(View view);

        public abstract void frameworkSet(View view, T t);

        public abstract boolean shouldUpdate(T t, T t2);

        public final T get(View view) {
            boolean z;
            if (Build.VERSION.SDK_INT >= this.mFrameworkMinimumSdk) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return frameworkGet(view);
            }
            T tag = view.getTag(this.mTagKey);
            if (this.mType.isInstance(tag)) {
                return tag;
            }
            return null;
        }

        public final void set(View view, T t) {
            boolean z;
            AccessibilityDelegateCompat accessibilityDelegateCompat;
            if (Build.VERSION.SDK_INT >= this.mFrameworkMinimumSdk) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                frameworkSet(view, t);
            } else if (shouldUpdate(get(view), t)) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                View.AccessibilityDelegate accessibilityDelegate = Api29Impl.getAccessibilityDelegate(view);
                if (accessibilityDelegate == null) {
                    accessibilityDelegateCompat = null;
                } else if (accessibilityDelegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter) {
                    accessibilityDelegateCompat = ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter) accessibilityDelegate).mCompat;
                } else {
                    accessibilityDelegateCompat = new AccessibilityDelegateCompat(accessibilityDelegate);
                }
                if (accessibilityDelegateCompat == null) {
                    accessibilityDelegateCompat = new AccessibilityDelegateCompat();
                }
                ViewCompat.setAccessibilityDelegate(view, accessibilityDelegateCompat);
                view.setTag(this.mTagKey, t);
                ViewCompat.notifyViewAccessibilityStateChangedIfNeeded(view, this.mContentChangeType);
            }
        }

        public AccessibilityViewProperty(int i, Class<T> cls, int i2, int i3) {
            this.mTagKey = i;
            this.mType = cls;
            this.mContentChangeType = i2;
            this.mFrameworkMinimumSdk = i3;
        }
    }

    public static class Api21Impl {
        public static void setOnApplyWindowInsetsListener(final View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            if (onApplyWindowInsetsListener == null) {
                view.setOnApplyWindowInsetsListener((View.OnApplyWindowInsetsListener) view.getTag(C1777R.C1779id.tag_window_insets_animation_callback));
            } else {
                view.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
                    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        return onApplyWindowInsetsListener.onApplyWindowInsets(view, WindowInsetsCompat.toWindowInsetsCompat(windowInsets, view)).toWindowInsets();
                    }
                });
            }
        }

        public static WindowInsetsCompat computeSystemWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, Rect rect) {
            WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
            if (windowInsets != null) {
                return WindowInsetsCompat.toWindowInsetsCompat(view.computeSystemWindowInsets(windowInsets, rect), view);
            }
            rect.setEmpty();
            return windowInsetsCompat;
        }

        public static ColorStateList getBackgroundTintList(View view) {
            return view.getBackgroundTintList();
        }

        public static PorterDuff.Mode getBackgroundTintMode(View view) {
            return view.getBackgroundTintMode();
        }

        public static float getElevation(View view) {
            return view.getElevation();
        }

        public static String getTransitionName(View view) {
            return view.getTransitionName();
        }

        public static float getTranslationZ(View view) {
            return view.getTranslationZ();
        }

        public static float getZ(View view) {
            return view.getZ();
        }

        public static boolean isNestedScrollingEnabled(View view) {
            return view.isNestedScrollingEnabled();
        }

        public static void stopNestedScroll(View view) {
            view.stopNestedScroll();
        }

        public static void setBackgroundTintList(View view, ColorStateList colorStateList) {
            view.setBackgroundTintList(colorStateList);
        }

        public static void setBackgroundTintMode(View view, PorterDuff.Mode mode) {
            view.setBackgroundTintMode(mode);
        }

        public static void setElevation(View view, float f) {
            view.setElevation(f);
        }

        public static void setTransitionName(View view, String str) {
            view.setTransitionName(str);
        }

        public static void setTranslationZ(View view, float f) {
            view.setTranslationZ(f);
        }

        public static void setZ(View view, float f) {
            view.setZ(f);
        }
    }

    public static void replaceAccessibilityAction(View view, AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat, String str, AccessibilityViewCommand accessibilityViewCommand) {
        AccessibilityDelegateCompat accessibilityDelegateCompat;
        if (accessibilityViewCommand == null && str == null) {
            removeActionWithId(accessibilityActionCompat.getId(), view);
            notifyViewAccessibilityStateChangedIfNeeded(view, 0);
            return;
        }
        Objects.requireNonNull(accessibilityActionCompat);
        AccessibilityNodeInfoCompat.AccessibilityActionCompat accessibilityActionCompat2 = new AccessibilityNodeInfoCompat.AccessibilityActionCompat((Object) null, accessibilityActionCompat.mId, str, accessibilityViewCommand, accessibilityActionCompat.mViewCommandArgumentClass);
        View.AccessibilityDelegate accessibilityDelegate = Api29Impl.getAccessibilityDelegate(view);
        if (accessibilityDelegate == null) {
            accessibilityDelegateCompat = null;
        } else if (accessibilityDelegate instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter) {
            accessibilityDelegateCompat = ((AccessibilityDelegateCompat.AccessibilityDelegateAdapter) accessibilityDelegate).mCompat;
        } else {
            accessibilityDelegateCompat = new AccessibilityDelegateCompat(accessibilityDelegate);
        }
        if (accessibilityDelegateCompat == null) {
            accessibilityDelegateCompat = new AccessibilityDelegateCompat();
        }
        setAccessibilityDelegate(view, accessibilityDelegateCompat);
        removeActionWithId(accessibilityActionCompat2.getId(), view);
        getActionList(view).add(accessibilityActionCompat2);
        notifyViewAccessibilityStateChangedIfNeeded(view, 0);
    }

    public static class AccessibilityPaneVisibilityManager implements ViewTreeObserver.OnGlobalLayoutListener, View.OnAttachStateChangeListener {
        public final WeakHashMap<View, Boolean> mPanesToVisible = new WeakHashMap<>();

        public final void onGlobalLayout() {
        }

        public final void onViewDetachedFromWindow(View view) {
        }

        public final void onViewAttachedToWindow(View view) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(this);
        }
    }

    public static class Api15Impl {
        public static boolean hasOnClickListeners(View view) {
            return view.hasOnClickListeners();
        }
    }

    public static class Api16Impl {
        public static boolean getFitsSystemWindows(View view) {
            return view.getFitsSystemWindows();
        }

        public static int getImportantForAccessibility(View view) {
            return view.getImportantForAccessibility();
        }

        public static int getMinimumHeight(View view) {
            return view.getMinimumHeight();
        }

        public static int getMinimumWidth(View view) {
            return view.getMinimumWidth();
        }

        public static int getWindowSystemUiVisibility(View view) {
            return view.getWindowSystemUiVisibility();
        }

        public static boolean hasOverlappingRendering(View view) {
            return view.hasOverlappingRendering();
        }

        public static boolean hasTransientState(View view) {
            return view.hasTransientState();
        }

        public static boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            return view.performAccessibilityAction(i, bundle);
        }

        public static void postInvalidateOnAnimation(View view) {
            view.postInvalidateOnAnimation();
        }

        public static void postOnAnimation(View view, Runnable runnable) {
            view.postOnAnimation(runnable);
        }

        public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }

        public static void setBackground(View view, Drawable drawable) {
            view.setBackground(drawable);
        }

        public static void setHasTransientState(View view, boolean z) {
            view.setHasTransientState(z);
        }

        public static void setImportantForAccessibility(View view, int i) {
            view.setImportantForAccessibility(i);
        }

        public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
            view.postOnAnimationDelayed(runnable, j);
        }
    }

    public static class Api17Impl {
        public static int generateViewId() {
            return View.generateViewId();
        }

        public static Display getDisplay(View view) {
            return view.getDisplay();
        }

        public static int getLayoutDirection(View view) {
            return view.getLayoutDirection();
        }

        public static int getPaddingEnd(View view) {
            return view.getPaddingEnd();
        }

        public static int getPaddingStart(View view) {
            return view.getPaddingStart();
        }

        public static boolean isPaddingRelative(View view) {
            return view.isPaddingRelative();
        }

        public static void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
            view.setPaddingRelative(i, i2, i3, i4);
        }
    }

    public static class Api18Impl {
        public static Rect getClipBounds(View view) {
            return view.getClipBounds();
        }

        public static void setClipBounds(View view, Rect rect) {
            view.setClipBounds(rect);
        }
    }

    public static class Api19Impl {
        public static int getAccessibilityLiveRegion(View view) {
            return view.getAccessibilityLiveRegion();
        }

        public static boolean isAttachedToWindow(View view) {
            return view.isAttachedToWindow();
        }

        public static boolean isLaidOut(View view) {
            return view.isLaidOut();
        }

        public static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) {
            viewParent.notifySubtreeAccessibilityStateChanged(view, view2, i);
        }

        public static void setAccessibilityLiveRegion(View view, int i) {
            view.setAccessibilityLiveRegion(i);
        }

        public static void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
            accessibilityEvent.setContentChangeTypes(i);
        }
    }

    public static class Api20Impl {
        public static WindowInsets dispatchApplyWindowInsets(View view, WindowInsets windowInsets) {
            return view.dispatchApplyWindowInsets(windowInsets);
        }

        public static WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            return view.onApplyWindowInsets(windowInsets);
        }

        public static void requestApplyInsets(View view) {
            view.requestApplyInsets();
        }
    }

    public static class Api23Impl {
        public static WindowInsetsCompat getRootWindowInsets(View view) {
            WindowInsets rootWindowInsets = view.getRootWindowInsets();
            if (rootWindowInsets == null) {
                return null;
            }
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(rootWindowInsets, (View) null);
            windowInsetsCompat.mImpl.setRootWindowInsets(windowInsetsCompat);
            windowInsetsCompat.mImpl.copyRootViewBounds(view.getRootView());
            return windowInsetsCompat;
        }

        public static void setScrollIndicators(View view, int i, int i2) {
            view.setScrollIndicators(i, i2);
        }
    }

    public static class Api26Impl {
        public static int getImportantForAutofill(View view) {
            return view.getImportantForAutofill();
        }

        public static void setImportantForAutofill(View view, int i) {
            view.setImportantForAutofill(i);
        }
    }

    public static class Api28Impl {
        public static CharSequence getAccessibilityPaneTitle(View view) {
            return view.getAccessibilityPaneTitle();
        }

        public static boolean isAccessibilityHeading(View view) {
            return view.isAccessibilityHeading();
        }

        public static boolean isScreenReaderFocusable(View view) {
            return view.isScreenReaderFocusable();
        }

        public static void setAccessibilityHeading(View view, boolean z) {
            view.setAccessibilityHeading(z);
        }

        public static void setAccessibilityPaneTitle(View view, CharSequence charSequence) {
            view.setAccessibilityPaneTitle(charSequence);
        }
    }

    public static class Api29Impl {
        public static View.AccessibilityDelegate getAccessibilityDelegate(View view) {
            return view.getAccessibilityDelegate();
        }

        public static void saveAttributeDataForStyleable(View view, Context context, int[] iArr, AttributeSet attributeSet, TypedArray typedArray, int i, int i2) {
            view.saveAttributeDataForStyleable(context, iArr, attributeSet, typedArray, i, i2);
        }
    }

    public static class Api30Impl {
        public static CharSequence getStateDescription(View view) {
            return view.getStateDescription();
        }

        public static void setStateDescription(View view, CharSequence charSequence) {
            view.setStateDescription(charSequence);
        }
    }

    static {
        new AtomicInteger(1);
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        if (sViewPropertyAnimatorMap == null) {
            sViewPropertyAnimatorMap = new WeakHashMap<>();
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = sViewPropertyAnimatorMap.get(view);
        if (viewPropertyAnimatorCompat != null) {
            return viewPropertyAnimatorCompat;
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view);
        sViewPropertyAnimatorMap.put(view, viewPropertyAnimatorCompat2);
        return viewPropertyAnimatorCompat2;
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        AccessibilityDelegateCompat.AccessibilityDelegateAdapter accessibilityDelegateAdapter;
        if (accessibilityDelegateCompat == null && (Api29Impl.getAccessibilityDelegate(view) instanceof AccessibilityDelegateCompat.AccessibilityDelegateAdapter)) {
            accessibilityDelegateCompat = new AccessibilityDelegateCompat();
        }
        if (accessibilityDelegateCompat == null) {
            accessibilityDelegateAdapter = null;
        } else {
            accessibilityDelegateAdapter = accessibilityDelegateCompat.mBridge;
        }
        view.setAccessibilityDelegate(accessibilityDelegateAdapter);
    }

    public static void setAccessibilityPaneTitle(View view, CharSequence charSequence) {
        boolean z;
        new AccessibilityViewProperty<CharSequence>(CharSequence.class) {
            public final void frameworkSet(View view, Object obj) {
                Api28Impl.setAccessibilityPaneTitle(view, (CharSequence) obj);
            }

            public final boolean shouldUpdate(Object obj, Object obj2) {
                return !TextUtils.equals((CharSequence) obj, (CharSequence) obj2);
            }

            public final Object frameworkGet(View view) {
                return Api28Impl.getAccessibilityPaneTitle(view);
            }
        }.set(view, charSequence);
        if (charSequence != null) {
            AccessibilityPaneVisibilityManager accessibilityPaneVisibilityManager = sAccessibilityPaneVisibilityManager;
            Objects.requireNonNull(accessibilityPaneVisibilityManager);
            WeakHashMap<View, Boolean> weakHashMap = accessibilityPaneVisibilityManager.mPanesToVisible;
            if (view.getVisibility() == 0) {
                z = true;
            } else {
                z = false;
            }
            weakHashMap.put(view, Boolean.valueOf(z));
            view.addOnAttachStateChangeListener(accessibilityPaneVisibilityManager);
            if (Api19Impl.isAttachedToWindow(view)) {
                view.getViewTreeObserver().addOnGlobalLayoutListener(accessibilityPaneVisibilityManager);
                return;
            }
            return;
        }
        AccessibilityPaneVisibilityManager accessibilityPaneVisibilityManager2 = sAccessibilityPaneVisibilityManager;
        Objects.requireNonNull(accessibilityPaneVisibilityManager2);
        accessibilityPaneVisibilityManager2.mPanesToVisible.remove(view);
        view.removeOnAttachStateChangeListener(accessibilityPaneVisibilityManager2);
        Api16Impl.removeOnGlobalLayoutListener(view.getViewTreeObserver(), accessibilityPaneVisibilityManager2);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
        if (windowInsets != null) {
            WindowInsets dispatchApplyWindowInsets = Api20Impl.dispatchApplyWindowInsets(view, windowInsets);
            if (!dispatchApplyWindowInsets.equals(windowInsets)) {
                return WindowInsetsCompat.toWindowInsetsCompat(dispatchApplyWindowInsets, view);
            }
        }
        return windowInsetsCompat;
    }

    public static ArrayList getActionList(View view) {
        ArrayList arrayList = (ArrayList) view.getTag(C1777R.C1779id.tag_accessibility_actions);
        if (arrayList != null) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        view.setTag(C1777R.C1779id.tag_accessibility_actions, arrayList2);
        return arrayList2;
    }

    public static void notifyViewAccessibilityStateChangedIfNeeded(View view, int i) {
        boolean z;
        AccessibilityManager accessibilityManager = (AccessibilityManager) view.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled()) {
            Class<CharSequence> cls = CharSequence.class;
            if (Api28Impl.getAccessibilityPaneTitle(view) == null || view.getVisibility() != 0) {
                z = false;
            } else {
                z = true;
            }
            int i2 = 32;
            if (Api19Impl.getAccessibilityLiveRegion(view) != 0 || z) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain();
                if (!z) {
                    i2 = 2048;
                }
                obtain.setEventType(i2);
                Api19Impl.setContentChangeTypes(obtain, i);
                if (z) {
                    Class<CharSequence> cls2 = CharSequence.class;
                    obtain.getText().add(Api28Impl.getAccessibilityPaneTitle(view));
                    if (Api16Impl.getImportantForAccessibility(view) == 0) {
                        Api16Impl.setImportantForAccessibility(view, 1);
                    }
                    ViewParent parent = view.getParent();
                    while (true) {
                        if (!(parent instanceof View)) {
                            break;
                        } else if (Api16Impl.getImportantForAccessibility((View) parent) == 4) {
                            Api16Impl.setImportantForAccessibility(view, 2);
                            break;
                        } else {
                            parent = parent.getParent();
                        }
                    }
                }
                view.sendAccessibilityEventUnchecked(obtain);
            } else if (i == 32) {
                AccessibilityEvent obtain2 = AccessibilityEvent.obtain();
                view.onInitializeAccessibilityEvent(obtain2);
                obtain2.setEventType(32);
                Api19Impl.setContentChangeTypes(obtain2, i);
                obtain2.setSource(view);
                view.onPopulateAccessibilityEvent(obtain2);
                Class<CharSequence> cls3 = CharSequence.class;
                obtain2.getText().add(Api28Impl.getAccessibilityPaneTitle(view));
                accessibilityManager.sendAccessibilityEvent(obtain2);
            } else if (view.getParent() != null) {
                try {
                    Api19Impl.notifySubtreeAccessibilityStateChanged(view.getParent(), view, view, i);
                } catch (AbstractMethodError e) {
                    Log.e("ViewCompat", view.getParent().getClass().getSimpleName() + " does not fully implement ViewParent", e);
                }
            }
        }
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
        if (windowInsets != null) {
            WindowInsets onApplyWindowInsets = Api20Impl.onApplyWindowInsets(view, windowInsets);
            if (!onApplyWindowInsets.equals(windowInsets)) {
                return WindowInsetsCompat.toWindowInsetsCompat(onApplyWindowInsets, view);
            }
        }
        return windowInsetsCompat;
    }

    public static void removeActionWithId(int i, View view) {
        ArrayList actionList = getActionList(view);
        for (int i2 = 0; i2 < actionList.size(); i2++) {
            if (((AccessibilityNodeInfoCompat.AccessibilityActionCompat) actionList.get(i2)).getId() == i) {
                actionList.remove(i2);
                return;
            }
        }
    }

    public static class Api24Impl {
        public static void setPointerIcon(View view, PointerIcon pointerIcon) {
            view.setPointerIcon(pointerIcon);
        }
    }
}
