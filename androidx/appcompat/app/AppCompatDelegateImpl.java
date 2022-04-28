package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.app.TwilightManager;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.appcompat.widget.ViewUtils;
import androidx.collection.LongSparseArray;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.WindowInsetsCompat;
import androidx.leanback.R$styleable;
import com.airbnb.lottie.C0438L;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;

public final class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    public static final boolean sCanApplyOverrideConfiguration = true;
    public static final boolean sCanReturnDifferentContext = (!"robolectric".equals(Build.FINGERPRINT));
    public static final SimpleArrayMap<String, Integer> sLocalNightModes = new SimpleArrayMap<>();
    public static final int[] sWindowBackgroundStyleable = {16842836};
    public WindowDecorActionBar mActionBar;
    public ActionMenuPresenterCallback mActionMenuPresenterCallback;
    public ActionMode mActionMode;
    public PopupWindow mActionModePopup;
    public ActionBarContextView mActionModeView;
    public boolean mActivityHandlesUiMode;
    public boolean mActivityHandlesUiModeChecked;
    public final AppCompatCallback mAppCompatCallback;
    public AppCompatViewInflater mAppCompatViewInflater;
    public AppCompatWindowCallback mAppCompatWindowCallback;
    public AutoBatteryNightModeManager mAutoBatteryNightModeManager;
    public AutoTimeNightModeManager mAutoTimeNightModeManager;
    public boolean mBaseContextAttached;
    public boolean mClosingActionMenu;
    public final Context mContext;
    public boolean mCreated;
    public DecorContentParent mDecorContentParent;
    public boolean mDestroyed;
    public Configuration mEffectiveConfiguration;
    public boolean mEnableDefaultActionBarUp;
    public ViewPropertyAnimatorCompat mFadeAnim = null;
    public boolean mFeatureIndeterminateProgress;
    public boolean mFeatureProgress;
    public boolean mHandleNativeActionModes = true;
    public boolean mHasActionBar;
    public final Object mHost;
    public int mInvalidatePanelMenuFeatures;
    public boolean mInvalidatePanelMenuPosted;
    public final C00292 mInvalidatePanelMenuRunnable = new Runnable() {
        public final void run() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl.mInvalidatePanelMenuFeatures & 1) != 0) {
                appCompatDelegateImpl.doInvalidatePanelMenu(0);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl2.mInvalidatePanelMenuFeatures & 4096) != 0) {
                appCompatDelegateImpl2.doInvalidatePanelMenu(108);
            }
            AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl3.mInvalidatePanelMenuPosted = false;
            appCompatDelegateImpl3.mInvalidatePanelMenuFeatures = 0;
        }
    };
    public boolean mIsFloating;
    public int mLocalNightMode = -100;
    public boolean mLongPressBackDown;
    public SupportMenuInflater mMenuInflater;
    public boolean mOverlayActionBar;
    public boolean mOverlayActionMode;
    public PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    public PanelFeatureState[] mPanels;
    public PanelFeatureState mPreparedPanel;
    public C00326 mShowActionModePopup;
    public View mStatusGuard;
    public ViewGroup mSubDecor;
    public boolean mSubDecorInstalled;
    public Rect mTempRect1;
    public Rect mTempRect2;
    public int mThemeResId;
    public CharSequence mTitle;
    public TextView mTitleView;
    public Window mWindow;
    public boolean mWindowNoTitle;

    public final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        public ActionMenuPresenterCallback() {
        }

        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menuBuilder);
        }

        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback = AppCompatDelegateImpl.this.getWindowCallback();
            if (windowCallback == null) {
                return true;
            }
            windowCallback.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    public class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        public ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(SupportActionModeWrapper.CallbackWrapper callbackWrapper) {
            this.mWrapped = callbackWrapper;
        }

        public final boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        public final boolean onCreateActionMode(ActionMode actionMode, MenuBuilder menuBuilder) {
            return this.mWrapped.onCreateActionMode(actionMode, menuBuilder);
        }

        public final void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.mActionModePopup != null) {
                appCompatDelegateImpl.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl2.mActionModeView != null) {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = appCompatDelegateImpl2.mFadeAnim;
                if (viewPropertyAnimatorCompat != null) {
                    viewPropertyAnimatorCompat.cancel();
                }
                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                ViewPropertyAnimatorCompat animate = ViewCompat.animate(appCompatDelegateImpl3.mActionModeView);
                animate.alpha(0.0f);
                appCompatDelegateImpl3.mFadeAnim = animate;
                AppCompatDelegateImpl.this.mFadeAnim.setListener(new C0438L() {
                    public final void onAnimationEnd() {
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                        PopupWindow popupWindow = appCompatDelegateImpl.mActionModePopup;
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        } else if (appCompatDelegateImpl.mActionModeView.getParent() instanceof View) {
                            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                            ViewCompat.Api20Impl.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent());
                        }
                        AppCompatDelegateImpl.this.mActionModeView.killMode();
                        AppCompatDelegateImpl.this.mFadeAnim.setListener((ViewPropertyAnimatorListener) null);
                        AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                        appCompatDelegateImpl2.mFadeAnim = null;
                        ViewGroup viewGroup = appCompatDelegateImpl2.mSubDecor;
                        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        ViewCompat.Api20Impl.requestApplyInsets(viewGroup);
                    }
                });
            }
            AppCompatCallback appCompatCallback = AppCompatDelegateImpl.this.mAppCompatCallback;
            if (appCompatCallback != null) {
                appCompatCallback.onSupportActionModeFinished();
            }
            AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl4.mActionMode = null;
            ViewGroup viewGroup = appCompatDelegateImpl4.mSubDecor;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api20Impl.requestApplyInsets(viewGroup);
        }

        public final boolean onPrepareActionMode(ActionMode actionMode, MenuBuilder menuBuilder) {
            ViewGroup viewGroup = AppCompatDelegateImpl.this.mSubDecor;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api20Impl.requestApplyInsets(viewGroup);
            return this.mWrapped.onPrepareActionMode(actionMode, menuBuilder);
        }
    }

    public class AppCompatWindowCallback extends WindowCallbackWrapper {
        public final void onContentChanged() {
        }

        public final android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        public AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
                return true;
            }
            return false;
        }

        public final boolean onCreatePanelMenu(int i, Menu menu) {
            if (i != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        public final boolean onPreparePanel(int i, View view, Menu menu) {
            MenuBuilder menuBuilder;
            if (menu instanceof MenuBuilder) {
                menuBuilder = (MenuBuilder) menu;
            } else {
                menuBuilder = null;
            }
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.mOverrideVisibleItems = true;
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (menuBuilder != null) {
                menuBuilder.mOverrideVisibleItems = false;
            }
            return onPreparePanel;
        }

        public final void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            MenuBuilder menuBuilder = AppCompatDelegateImpl.this.getPanelState(0).menu;
            if (menuBuilder != null) {
                super.onProvideKeyboardShortcuts(list, menuBuilder, i);
            } else {
                super.onProvideKeyboardShortcuts(list, menu, i);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:74:0x01b6, code lost:
            if (androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r8) != false) goto L_0x01ba;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final android.view.ActionMode onWindowStartingActionMode(android.view.ActionMode.Callback r8, int r9) {
            /*
                r7 = this;
                androidx.appcompat.app.AppCompatDelegateImpl r0 = androidx.appcompat.app.AppCompatDelegateImpl.this
                java.util.Objects.requireNonNull(r0)
                boolean r0 = r0.mHandleNativeActionModes
                if (r0 == 0) goto L_0x0223
                if (r9 == 0) goto L_0x000d
                goto L_0x0223
            L_0x000d:
                androidx.appcompat.view.SupportActionModeWrapper$CallbackWrapper r9 = new androidx.appcompat.view.SupportActionModeWrapper$CallbackWrapper
                androidx.appcompat.app.AppCompatDelegateImpl r0 = androidx.appcompat.app.AppCompatDelegateImpl.this
                android.content.Context r0 = r0.mContext
                r9.<init>(r0, r8)
                androidx.appcompat.app.AppCompatDelegateImpl r7 = androidx.appcompat.app.AppCompatDelegateImpl.this
                androidx.appcompat.view.ActionMode r8 = r7.mActionMode
                if (r8 == 0) goto L_0x001f
                r8.finish()
            L_0x001f:
                androidx.appcompat.app.AppCompatDelegateImpl$ActionModeCallbackWrapperV9 r8 = new androidx.appcompat.app.AppCompatDelegateImpl$ActionModeCallbackWrapperV9
                r8.<init>(r9)
                r7.initWindowDecorActionBar()
                androidx.appcompat.app.WindowDecorActionBar r0 = r7.mActionBar
                r1 = 0
                r2 = 1
                r3 = 0
                if (r0 == 0) goto L_0x00a1
                androidx.appcompat.app.WindowDecorActionBar$ActionModeImpl r4 = r0.mActionMode
                if (r4 == 0) goto L_0x0035
                r4.finish()
            L_0x0035:
                androidx.appcompat.widget.ActionBarOverlayLayout r4 = r0.mOverlayLayout
                java.util.Objects.requireNonNull(r4)
                boolean r5 = r4.mHideOnContentScroll
                if (r5 == 0) goto L_0x005b
                r4.mHideOnContentScroll = r3
                r4.haltActionBarHideOffsetAnimations()
                r4.haltActionBarHideOffsetAnimations()
                androidx.appcompat.widget.ActionBarContainer r5 = r4.mActionBarTop
                int r5 = r5.getHeight()
                int r5 = java.lang.Math.min(r3, r5)
                int r5 = java.lang.Math.max(r3, r5)
                androidx.appcompat.widget.ActionBarContainer r4 = r4.mActionBarTop
                int r5 = -r5
                float r5 = (float) r5
                r4.setTranslationY(r5)
            L_0x005b:
                androidx.appcompat.widget.ActionBarContextView r4 = r0.mContextView
                r4.killMode()
                androidx.appcompat.app.WindowDecorActionBar$ActionModeImpl r4 = new androidx.appcompat.app.WindowDecorActionBar$ActionModeImpl
                androidx.appcompat.widget.ActionBarContextView r5 = r0.mContextView
                android.content.Context r5 = r5.getContext()
                r4.<init>(r5, r8)
                androidx.appcompat.view.menu.MenuBuilder r5 = r4.mMenu
                r5.stopDispatchingItemsChanged()
                androidx.appcompat.view.ActionMode$Callback r5 = r4.mCallback     // Catch:{ all -> 0x009a }
                androidx.appcompat.view.menu.MenuBuilder r6 = r4.mMenu     // Catch:{ all -> 0x009a }
                boolean r5 = r5.onCreateActionMode(r4, r6)     // Catch:{ all -> 0x009a }
                androidx.appcompat.view.menu.MenuBuilder r6 = r4.mMenu
                r6.startDispatchingItemsChanged()
                if (r5 == 0) goto L_0x008d
                r0.mActionMode = r4
                r4.invalidate()
                androidx.appcompat.widget.ActionBarContextView r5 = r0.mContextView
                r5.initForMode(r4)
                r0.animateToMode(r2)
                goto L_0x008e
            L_0x008d:
                r4 = r1
            L_0x008e:
                r7.mActionMode = r4
                if (r4 == 0) goto L_0x00a1
                androidx.appcompat.app.AppCompatCallback r0 = r7.mAppCompatCallback
                if (r0 == 0) goto L_0x00a1
                r0.onSupportActionModeStarted()
                goto L_0x00a1
            L_0x009a:
                r7 = move-exception
                androidx.appcompat.view.menu.MenuBuilder r8 = r4.mMenu
                r8.startDispatchingItemsChanged()
                throw r7
            L_0x00a1:
                androidx.appcompat.view.ActionMode r0 = r7.mActionMode
                if (r0 != 0) goto L_0x021a
                androidx.core.view.ViewPropertyAnimatorCompat r0 = r7.mFadeAnim
                if (r0 == 0) goto L_0x00ac
                r0.cancel()
            L_0x00ac:
                androidx.appcompat.view.ActionMode r0 = r7.mActionMode
                if (r0 == 0) goto L_0x00b3
                r0.finish()
            L_0x00b3:
                androidx.appcompat.app.AppCompatCallback r0 = r7.mAppCompatCallback
                if (r0 == 0) goto L_0x00be
                boolean r4 = r7.mDestroyed
                if (r4 != 0) goto L_0x00be
                r0.onWindowStartingSupportActionMode()     // Catch:{ AbstractMethodError -> 0x00be }
            L_0x00be:
                androidx.appcompat.widget.ActionBarContextView r0 = r7.mActionModeView
                if (r0 != 0) goto L_0x0179
                boolean r0 = r7.mIsFloating
                if (r0 == 0) goto L_0x014d
                android.util.TypedValue r0 = new android.util.TypedValue
                r0.<init>()
                android.content.Context r4 = r7.mContext
                android.content.res.Resources$Theme r4 = r4.getTheme()
                r5 = 2130968585(0x7f040009, float:1.7545828E38)
                r4.resolveAttribute(r5, r0, r2)
                int r5 = r0.resourceId
                if (r5 == 0) goto L_0x00fc
                android.content.Context r5 = r7.mContext
                android.content.res.Resources r5 = r5.getResources()
                android.content.res.Resources$Theme r5 = r5.newTheme()
                r5.setTo(r4)
                int r4 = r0.resourceId
                r5.applyStyle(r4, r2)
                androidx.appcompat.view.ContextThemeWrapper r4 = new androidx.appcompat.view.ContextThemeWrapper
                android.content.Context r6 = r7.mContext
                r4.<init>(r6, r3)
                android.content.res.Resources$Theme r6 = r4.getTheme()
                r6.setTo(r5)
                goto L_0x00fe
            L_0x00fc:
                android.content.Context r4 = r7.mContext
            L_0x00fe:
                androidx.appcompat.widget.ActionBarContextView r5 = new androidx.appcompat.widget.ActionBarContextView
                r5.<init>(r4)
                r7.mActionModeView = r5
                android.widget.PopupWindow r5 = new android.widget.PopupWindow
                r6 = 2130968601(0x7f040019, float:1.754586E38)
                r5.<init>(r4, r1, r6)
                r7.mActionModePopup = r5
                r6 = 2
                r5.setWindowLayoutType(r6)
                android.widget.PopupWindow r5 = r7.mActionModePopup
                androidx.appcompat.widget.ActionBarContextView r6 = r7.mActionModeView
                r5.setContentView(r6)
                android.widget.PopupWindow r5 = r7.mActionModePopup
                r6 = -1
                r5.setWidth(r6)
                android.content.res.Resources$Theme r5 = r4.getTheme()
                r6 = 2130968579(0x7f040003, float:1.7545816E38)
                r5.resolveAttribute(r6, r0, r2)
                int r0 = r0.data
                android.content.res.Resources r4 = r4.getResources()
                android.util.DisplayMetrics r4 = r4.getDisplayMetrics()
                int r0 = android.util.TypedValue.complexToDimensionPixelSize(r0, r4)
                androidx.appcompat.widget.ActionBarContextView r4 = r7.mActionModeView
                java.util.Objects.requireNonNull(r4)
                r4.mContentHeight = r0
                android.widget.PopupWindow r0 = r7.mActionModePopup
                r4 = -2
                r0.setHeight(r4)
                androidx.appcompat.app.AppCompatDelegateImpl$6 r0 = new androidx.appcompat.app.AppCompatDelegateImpl$6
                r0.<init>()
                r7.mShowActionModePopup = r0
                goto L_0x0179
            L_0x014d:
                android.view.ViewGroup r0 = r7.mSubDecor
                r4 = 2131427429(0x7f0b0065, float:1.8476474E38)
                android.view.View r0 = r0.findViewById(r4)
                androidx.appcompat.widget.ViewStubCompat r0 = (androidx.appcompat.widget.ViewStubCompat) r0
                if (r0 == 0) goto L_0x0179
                r7.initWindowDecorActionBar()
                androidx.appcompat.app.WindowDecorActionBar r4 = r7.mActionBar
                if (r4 == 0) goto L_0x0166
                android.content.Context r4 = r4.getThemedContext()
                goto L_0x0167
            L_0x0166:
                r4 = r1
            L_0x0167:
                if (r4 != 0) goto L_0x016b
                android.content.Context r4 = r7.mContext
            L_0x016b:
                android.view.LayoutInflater r4 = android.view.LayoutInflater.from(r4)
                r0.mInflater = r4
                android.view.View r0 = r0.inflate()
                androidx.appcompat.widget.ActionBarContextView r0 = (androidx.appcompat.widget.ActionBarContextView) r0
                r7.mActionModeView = r0
            L_0x0179:
                androidx.appcompat.widget.ActionBarContextView r0 = r7.mActionModeView
                if (r0 == 0) goto L_0x020b
                androidx.core.view.ViewPropertyAnimatorCompat r0 = r7.mFadeAnim
                if (r0 == 0) goto L_0x0184
                r0.cancel()
            L_0x0184:
                androidx.appcompat.widget.ActionBarContextView r0 = r7.mActionModeView
                r0.killMode()
                androidx.appcompat.view.StandaloneActionMode r0 = new androidx.appcompat.view.StandaloneActionMode
                androidx.appcompat.widget.ActionBarContextView r4 = r7.mActionModeView
                android.content.Context r4 = r4.getContext()
                androidx.appcompat.widget.ActionBarContextView r5 = r7.mActionModeView
                r0.<init>(r4, r5, r8)
                androidx.appcompat.view.menu.MenuBuilder r4 = r0.mMenu
                boolean r8 = r8.onCreateActionMode(r0, r4)
                if (r8 == 0) goto L_0x0209
                r0.invalidate()
                androidx.appcompat.widget.ActionBarContextView r8 = r7.mActionModeView
                r8.initForMode(r0)
                r7.mActionMode = r0
                boolean r8 = r7.mSubDecorInstalled
                if (r8 == 0) goto L_0x01b9
                android.view.ViewGroup r8 = r7.mSubDecor
                if (r8 == 0) goto L_0x01b9
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                boolean r8 = androidx.core.view.ViewCompat.Api19Impl.isLaidOut(r8)
                if (r8 == 0) goto L_0x01b9
                goto L_0x01ba
            L_0x01b9:
                r2 = r3
            L_0x01ba:
                r8 = 1065353216(0x3f800000, float:1.0)
                if (r2 == 0) goto L_0x01d8
                androidx.appcompat.widget.ActionBarContextView r0 = r7.mActionModeView
                r2 = 0
                r0.setAlpha(r2)
                androidx.appcompat.widget.ActionBarContextView r0 = r7.mActionModeView
                androidx.core.view.ViewPropertyAnimatorCompat r0 = androidx.core.view.ViewCompat.animate(r0)
                r0.alpha(r8)
                r7.mFadeAnim = r0
                androidx.appcompat.app.AppCompatDelegateImpl$7 r8 = new androidx.appcompat.app.AppCompatDelegateImpl$7
                r8.<init>()
                r0.setListener(r8)
                goto L_0x01f9
            L_0x01d8:
                androidx.appcompat.widget.ActionBarContextView r0 = r7.mActionModeView
                r0.setAlpha(r8)
                androidx.appcompat.widget.ActionBarContextView r8 = r7.mActionModeView
                r8.setVisibility(r3)
                androidx.appcompat.widget.ActionBarContextView r8 = r7.mActionModeView
                android.view.ViewParent r8 = r8.getParent()
                boolean r8 = r8 instanceof android.view.View
                if (r8 == 0) goto L_0x01f9
                androidx.appcompat.widget.ActionBarContextView r8 = r7.mActionModeView
                android.view.ViewParent r8 = r8.getParent()
                android.view.View r8 = (android.view.View) r8
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                androidx.core.view.ViewCompat.Api20Impl.requestApplyInsets(r8)
            L_0x01f9:
                android.widget.PopupWindow r8 = r7.mActionModePopup
                if (r8 == 0) goto L_0x020b
                android.view.Window r8 = r7.mWindow
                android.view.View r8 = r8.getDecorView()
                androidx.appcompat.app.AppCompatDelegateImpl$6 r0 = r7.mShowActionModePopup
                r8.post(r0)
                goto L_0x020b
            L_0x0209:
                r7.mActionMode = r1
            L_0x020b:
                androidx.appcompat.view.ActionMode r8 = r7.mActionMode
                if (r8 == 0) goto L_0x0216
                androidx.appcompat.app.AppCompatCallback r8 = r7.mAppCompatCallback
                if (r8 == 0) goto L_0x0216
                r8.onSupportActionModeStarted()
            L_0x0216:
                androidx.appcompat.view.ActionMode r8 = r7.mActionMode
                r7.mActionMode = r8
            L_0x021a:
                androidx.appcompat.view.ActionMode r7 = r7.mActionMode
                if (r7 == 0) goto L_0x0222
                androidx.appcompat.view.SupportActionModeWrapper r1 = r9.getActionModeWrapper(r7)
            L_0x0222:
                return r1
            L_0x0223:
                android.view.ActionMode r7 = super.onWindowStartingActionMode(r8, r9)
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.AppCompatWindowCallback.onWindowStartingActionMode(android.view.ActionMode$Callback, int):android.view.ActionMode");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:14:0x003b, code lost:
            if (r0 != false) goto L_0x006a;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:0x0068, code lost:
            if (r5 != false) goto L_0x006a;
         */
        /* JADX WARNING: Removed duplicated region for block: B:31:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean dispatchKeyShortcutEvent(android.view.KeyEvent r6) {
            /*
                r5 = this;
                boolean r0 = super.dispatchKeyShortcutEvent(r6)
                r1 = 0
                r2 = 1
                if (r0 != 0) goto L_0x006f
                androidx.appcompat.app.AppCompatDelegateImpl r5 = androidx.appcompat.app.AppCompatDelegateImpl.this
                int r0 = r6.getKeyCode()
                java.util.Objects.requireNonNull(r5)
                r5.initWindowDecorActionBar()
                androidx.appcompat.app.WindowDecorActionBar r3 = r5.mActionBar
                if (r3 == 0) goto L_0x003e
                androidx.appcompat.app.WindowDecorActionBar$ActionModeImpl r3 = r3.mActionMode
                if (r3 != 0) goto L_0x001d
                goto L_0x003a
            L_0x001d:
                androidx.appcompat.view.menu.MenuBuilder r3 = r3.mMenu
                if (r3 == 0) goto L_0x003a
                int r4 = r6.getDeviceId()
                android.view.KeyCharacterMap r4 = android.view.KeyCharacterMap.load(r4)
                int r4 = r4.getKeyboardType()
                if (r4 == r2) goto L_0x0031
                r4 = r2
                goto L_0x0032
            L_0x0031:
                r4 = r1
            L_0x0032:
                r3.setQwertyMode(r4)
                boolean r0 = r3.performShortcut(r0, r6, r1)
                goto L_0x003b
            L_0x003a:
                r0 = r1
            L_0x003b:
                if (r0 == 0) goto L_0x003e
                goto L_0x006a
            L_0x003e:
                androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r0 = r5.mPreparedPanel
                if (r0 == 0) goto L_0x0053
                int r3 = r6.getKeyCode()
                boolean r0 = r5.performPanelShortcut(r0, r3, r6)
                if (r0 == 0) goto L_0x0053
                androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r5 = r5.mPreparedPanel
                if (r5 == 0) goto L_0x006a
                r5.isHandled = r2
                goto L_0x006a
            L_0x0053:
                androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r0 = r5.mPreparedPanel
                if (r0 != 0) goto L_0x006c
                androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r0 = r5.getPanelState(r1)
                r5.preparePanel(r0, r6)
                int r3 = r6.getKeyCode()
                boolean r5 = r5.performPanelShortcut(r0, r3, r6)
                r0.isPrepared = r1
                if (r5 == 0) goto L_0x006c
            L_0x006a:
                r5 = r2
                goto L_0x006d
            L_0x006c:
                r5 = r1
            L_0x006d:
                if (r5 == 0) goto L_0x0070
            L_0x006f:
                r1 = r2
            L_0x0070:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.AppCompatWindowCallback.dispatchKeyShortcutEvent(android.view.KeyEvent):boolean");
        }

        public final View onCreatePanelView(int i) {
            return super.onCreatePanelView(i);
        }

        public final boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (i == 108) {
                appCompatDelegateImpl.initWindowDecorActionBar();
                WindowDecorActionBar windowDecorActionBar = appCompatDelegateImpl.mActionBar;
                if (windowDecorActionBar != null) {
                    windowDecorActionBar.dispatchMenuVisibilityChanged(true);
                }
            } else {
                Objects.requireNonNull(appCompatDelegateImpl);
            }
            return true;
        }

        public final void onPanelClosed(int i, Menu menu) {
            super.onPanelClosed(i, menu);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (i == 108) {
                appCompatDelegateImpl.initWindowDecorActionBar();
                WindowDecorActionBar windowDecorActionBar = appCompatDelegateImpl.mActionBar;
                if (windowDecorActionBar != null) {
                    windowDecorActionBar.dispatchMenuVisibilityChanged(false);
                }
            } else if (i == 0) {
                PanelFeatureState panelState = appCompatDelegateImpl.getPanelState(i);
                if (panelState.isOpen) {
                    appCompatDelegateImpl.closePanel(panelState, false);
                }
            } else {
                Objects.requireNonNull(appCompatDelegateImpl);
            }
        }
    }

    public class AutoBatteryNightModeManager extends AutoNightModeManager {
        public final PowerManager mPowerManager;

        public AutoBatteryNightModeManager(Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getApplicationContext().getSystemService(GlobalActionsDialogLite.GLOBAL_ACTION_KEY_POWER);
        }

        public final IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return intentFilter;
        }

        public final int getApplyableNightMode() {
            if (this.mPowerManager.isPowerSaveMode()) {
                return 2;
            }
            return 1;
        }

        public final void onChange() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            Objects.requireNonNull(appCompatDelegateImpl);
            appCompatDelegateImpl.applyDayNight(true);
        }
    }

    public abstract class AutoNightModeManager {
        public C00361 mReceiver;

        public abstract IntentFilter createIntentFilterForBroadcastReceiver();

        public abstract int getApplyableNightMode();

        public abstract void onChange();

        public AutoNightModeManager() {
        }

        public final void cleanup() {
            C00361 r0 = this.mReceiver;
            if (r0 != null) {
                try {
                    AppCompatDelegateImpl.this.mContext.unregisterReceiver(r0);
                } catch (IllegalArgumentException unused) {
                }
                this.mReceiver = null;
            }
        }

        public final void setup() {
            cleanup();
            IntentFilter createIntentFilterForBroadcastReceiver = createIntentFilterForBroadcastReceiver();
            if (createIntentFilterForBroadcastReceiver != null && createIntentFilterForBroadcastReceiver.countActions() != 0) {
                if (this.mReceiver == null) {
                    this.mReceiver = new BroadcastReceiver() {
                        public final void onReceive(Context context, Intent intent) {
                            AutoNightModeManager.this.onChange();
                        }
                    };
                }
                AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, createIntentFilterForBroadcastReceiver);
            }
        }
    }

    public class AutoTimeNightModeManager extends AutoNightModeManager {
        public final TwilightManager mTwilightManager;

        public AutoTimeNightModeManager(TwilightManager twilightManager) {
            super();
            this.mTwilightManager = twilightManager;
        }

        public final IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.TIME_SET");
            intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter.addAction("android.intent.action.TIME_TICK");
            return intentFilter;
        }

        public final int getApplyableNightMode() {
            boolean z;
            boolean z2;
            Location location;
            long j;
            long j2;
            Location location2;
            TwilightManager twilightManager = this.mTwilightManager;
            Objects.requireNonNull(twilightManager);
            TwilightManager.TwilightState twilightState = twilightManager.mTwilightState;
            boolean z3 = false;
            if (twilightState.nextUpdate > System.currentTimeMillis()) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                z2 = twilightState.isNight;
            } else {
                Location location3 = null;
                if (R$styleable.checkSelfPermission(twilightManager.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0) {
                    try {
                        if (twilightManager.mLocationManager.isProviderEnabled("network")) {
                            location2 = twilightManager.mLocationManager.getLastKnownLocation("network");
                            location = location2;
                        }
                    } catch (Exception e) {
                        Log.d("TwilightManager", "Failed to get last known location", e);
                    }
                    location2 = null;
                    location = location2;
                } else {
                    location = null;
                }
                if (R$styleable.checkSelfPermission(twilightManager.mContext, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                    try {
                        if (twilightManager.mLocationManager.isProviderEnabled("gps")) {
                            location3 = twilightManager.mLocationManager.getLastKnownLocation("gps");
                        }
                    } catch (Exception e2) {
                        Log.d("TwilightManager", "Failed to get last known location", e2);
                    }
                }
                if (location3 == null || location == null ? location3 != null : location3.getTime() > location.getTime()) {
                    location = location3;
                }
                if (location != null) {
                    TwilightManager.TwilightState twilightState2 = twilightManager.mTwilightState;
                    long currentTimeMillis = System.currentTimeMillis();
                    if (TwilightCalculator.sInstance == null) {
                        TwilightCalculator.sInstance = new TwilightCalculator();
                    }
                    TwilightCalculator twilightCalculator = TwilightCalculator.sInstance;
                    TwilightCalculator twilightCalculator2 = twilightCalculator;
                    twilightCalculator2.calculateTwilight(currentTimeMillis - 86400000, location.getLatitude(), location.getLongitude());
                    twilightCalculator2.calculateTwilight(currentTimeMillis, location.getLatitude(), location.getLongitude());
                    if (twilightCalculator.state == 1) {
                        z3 = true;
                    }
                    long j3 = twilightCalculator.sunrise;
                    long j4 = twilightCalculator.sunset;
                    long j5 = j3;
                    twilightCalculator.calculateTwilight(currentTimeMillis + 86400000, location.getLatitude(), location.getLongitude());
                    long j6 = twilightCalculator.sunrise;
                    if (j5 == -1 || j4 == -1) {
                        j = 43200000 + currentTimeMillis;
                    } else {
                        if (currentTimeMillis > j4) {
                            j2 = j6 + 0;
                        } else if (currentTimeMillis > j5) {
                            j2 = j4 + 0;
                        } else {
                            j2 = j5 + 0;
                        }
                        j = j2 + 60000;
                    }
                    twilightState2.isNight = z3;
                    twilightState2.nextUpdate = j;
                    z2 = twilightState.isNight;
                } else {
                    Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
                    int i = Calendar.getInstance().get(11);
                    if (i < 6 || i >= 22) {
                        z3 = true;
                    }
                    z2 = z3;
                }
            }
            if (z2) {
                return 2;
            }
            return 1;
        }

        public final void onChange() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            Objects.requireNonNull(appCompatDelegateImpl);
            appCompatDelegateImpl.applyDayNight(true);
        }
    }

    public class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(ContextThemeWrapper contextThemeWrapper) {
            super(contextThemeWrapper);
        }

        public final boolean dispatchKeyEvent(KeyEvent keyEvent) {
            if (AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent)) {
                return true;
            }
            return false;
        }

        public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            boolean z;
            if (motionEvent.getAction() == 0) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5) {
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                    Objects.requireNonNull(appCompatDelegateImpl);
                    appCompatDelegateImpl.closePanel(appCompatDelegateImpl.getPanelState(0), true);
                    return true;
                }
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        public final void setBackgroundResource(int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i));
        }
    }

    public final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        public PanelMenuPresenterCallback() {
        }

        public final void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            boolean z2;
            int i;
            PanelFeatureState panelFeatureState;
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            int i2 = 0;
            if (rootMenu != menuBuilder) {
                z2 = true;
            } else {
                z2 = false;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            Objects.requireNonNull(appCompatDelegateImpl);
            PanelFeatureState[] panelFeatureStateArr = appCompatDelegateImpl.mPanels;
            if (panelFeatureStateArr != null) {
                i = panelFeatureStateArr.length;
            } else {
                i = 0;
            }
            while (true) {
                if (i2 < i) {
                    panelFeatureState = panelFeatureStateArr[i2];
                    if (panelFeatureState != null && panelFeatureState.menu == menuBuilder) {
                        break;
                    }
                    i2++;
                } else {
                    panelFeatureState = null;
                    break;
                }
            }
            if (panelFeatureState == null) {
                return;
            }
            if (z2) {
                AppCompatDelegateImpl.this.callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, rootMenu);
                AppCompatDelegateImpl.this.closePanel(panelFeatureState, true);
                return;
            }
            AppCompatDelegateImpl.this.closePanel(panelFeatureState, z);
        }

        public final boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback;
            if (menuBuilder != menuBuilder.getRootMenu()) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.mHasActionBar || (windowCallback = appCompatDelegateImpl.getWindowCallback()) == null || AppCompatDelegateImpl.this.mDestroyed) {
                return true;
            }
            windowCallback.onMenuOpened(108, menuBuilder);
            return true;
        }
    }

    public static Configuration createOverrideConfigurationForDayNight(Context context, int i, Configuration configuration) {
        int i2;
        if (i == 1) {
            i2 = 16;
        } else if (i != 2) {
            i2 = context.getApplicationContext().getResources().getConfiguration().uiMode & 48;
        } else {
            i2 = 32;
        }
        Configuration configuration2 = new Configuration();
        configuration2.fontScale = 0.0f;
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        configuration2.uiMode = i2 | (configuration2.uiMode & -49);
        return configuration2;
    }

    public final Context attachBaseContext2(Context context) {
        boolean z = true;
        this.mBaseContextAttached = true;
        int i = this.mLocalNightMode;
        if (i == -100) {
            i = -100;
        }
        int mapNightMode = mapNightMode(context, i);
        Configuration configuration = null;
        if (sCanApplyOverrideConfiguration && (context instanceof android.view.ContextThemeWrapper)) {
            try {
                ((android.view.ContextThemeWrapper) context).applyOverrideConfiguration(createOverrideConfigurationForDayNight(context, mapNightMode, (Configuration) null));
                return context;
            } catch (IllegalStateException unused) {
            }
        }
        if (context instanceof ContextThemeWrapper) {
            try {
                ((ContextThemeWrapper) context).applyOverrideConfiguration(createOverrideConfigurationForDayNight(context, mapNightMode, (Configuration) null));
                return context;
            } catch (IllegalStateException unused2) {
            }
        }
        if (!sCanReturnDifferentContext) {
            return context;
        }
        Configuration configuration2 = new Configuration();
        configuration2.uiMode = -1;
        configuration2.fontScale = 0.0f;
        Configuration configuration3 = context.createConfigurationContext(configuration2).getResources().getConfiguration();
        Configuration configuration4 = context.getResources().getConfiguration();
        configuration3.uiMode = configuration4.uiMode;
        if (!configuration3.equals(configuration4)) {
            configuration = new Configuration();
            configuration.fontScale = 0.0f;
            if (configuration3.diff(configuration4) != 0) {
                float f = configuration3.fontScale;
                float f2 = configuration4.fontScale;
                if (f != f2) {
                    configuration.fontScale = f2;
                }
                int i2 = configuration3.mcc;
                int i3 = configuration4.mcc;
                if (i2 != i3) {
                    configuration.mcc = i3;
                }
                int i4 = configuration3.mnc;
                int i5 = configuration4.mnc;
                if (i4 != i5) {
                    configuration.mnc = i5;
                }
                LocaleList locales = configuration3.getLocales();
                LocaleList locales2 = configuration4.getLocales();
                if (!locales.equals(locales2)) {
                    configuration.setLocales(locales2);
                    configuration.locale = configuration4.locale;
                }
                int i6 = configuration3.touchscreen;
                int i7 = configuration4.touchscreen;
                if (i6 != i7) {
                    configuration.touchscreen = i7;
                }
                int i8 = configuration3.keyboard;
                int i9 = configuration4.keyboard;
                if (i8 != i9) {
                    configuration.keyboard = i9;
                }
                int i10 = configuration3.keyboardHidden;
                int i11 = configuration4.keyboardHidden;
                if (i10 != i11) {
                    configuration.keyboardHidden = i11;
                }
                int i12 = configuration3.navigation;
                int i13 = configuration4.navigation;
                if (i12 != i13) {
                    configuration.navigation = i13;
                }
                int i14 = configuration3.navigationHidden;
                int i15 = configuration4.navigationHidden;
                if (i14 != i15) {
                    configuration.navigationHidden = i15;
                }
                int i16 = configuration3.orientation;
                int i17 = configuration4.orientation;
                if (i16 != i17) {
                    configuration.orientation = i17;
                }
                int i18 = configuration3.screenLayout & 15;
                int i19 = configuration4.screenLayout & 15;
                if (i18 != i19) {
                    configuration.screenLayout |= i19;
                }
                int i20 = configuration3.screenLayout & 192;
                int i21 = configuration4.screenLayout & 192;
                if (i20 != i21) {
                    configuration.screenLayout |= i21;
                }
                int i22 = configuration3.screenLayout & 48;
                int i23 = configuration4.screenLayout & 48;
                if (i22 != i23) {
                    configuration.screenLayout |= i23;
                }
                int i24 = configuration3.screenLayout & 768;
                int i25 = configuration4.screenLayout & 768;
                if (i24 != i25) {
                    configuration.screenLayout |= i25;
                }
                int i26 = configuration3.colorMode & 3;
                int i27 = configuration4.colorMode & 3;
                if (i26 != i27) {
                    configuration.colorMode |= i27;
                }
                int i28 = configuration3.colorMode & 12;
                int i29 = configuration4.colorMode & 12;
                if (i28 != i29) {
                    configuration.colorMode |= i29;
                }
                int i30 = configuration3.uiMode & 15;
                int i31 = configuration4.uiMode & 15;
                if (i30 != i31) {
                    configuration.uiMode |= i31;
                }
                int i32 = configuration3.uiMode & 48;
                int i33 = configuration4.uiMode & 48;
                if (i32 != i33) {
                    configuration.uiMode |= i33;
                }
                int i34 = configuration3.screenWidthDp;
                int i35 = configuration4.screenWidthDp;
                if (i34 != i35) {
                    configuration.screenWidthDp = i35;
                }
                int i36 = configuration3.screenHeightDp;
                int i37 = configuration4.screenHeightDp;
                if (i36 != i37) {
                    configuration.screenHeightDp = i37;
                }
                int i38 = configuration3.smallestScreenWidthDp;
                int i39 = configuration4.smallestScreenWidthDp;
                if (i38 != i39) {
                    configuration.smallestScreenWidthDp = i39;
                }
                int i40 = configuration3.densityDpi;
                int i41 = configuration4.densityDpi;
                if (i40 != i41) {
                    configuration.densityDpi = i41;
                }
            }
        }
        Configuration createOverrideConfigurationForDayNight = createOverrideConfigurationForDayNight(context, mapNightMode, configuration);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 2132018059);
        contextThemeWrapper.applyOverrideConfiguration(createOverrideConfigurationForDayNight);
        boolean z2 = false;
        try {
            if (context.getTheme() == null) {
                z = false;
            }
            z2 = z;
        } catch (NullPointerException unused3) {
        }
        if (z2) {
            contextThemeWrapper.getTheme().rebase();
        }
        return contextThemeWrapper;
    }

    public final AutoNightModeManager getAutoTimeNightModeManager() {
        return getAutoTimeNightModeManager(this.mContext);
    }

    public final void onCreate() {
        this.mBaseContextAttached = true;
        applyDayNight(false);
        ensureWindow();
        Object obj = this.mHost;
        if (obj instanceof Activity) {
            String str = null;
            try {
                Activity activity = (Activity) obj;
                str = NavUtils.getParentActivityName(activity, activity.getComponentName());
            } catch (PackageManager.NameNotFoundException e) {
                throw new IllegalArgumentException(e);
            } catch (IllegalArgumentException unused) {
            }
            if (str != null) {
                WindowDecorActionBar windowDecorActionBar = this.mActionBar;
                if (windowDecorActionBar == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    windowDecorActionBar.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            synchronized (AppCompatDelegate.sActivityDelegatesLock) {
                AppCompatDelegate.removeDelegateFromActives(this);
                AppCompatDelegate.sActivityDelegates.add(new WeakReference(this));
            }
        }
        this.mEffectiveConfiguration = new Configuration(this.mContext.getResources().getConfiguration());
        this.mCreated = true;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v81, resolved type: androidx.appcompat.widget.AppCompatRatingBar} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v82, resolved type: androidx.appcompat.widget.AppCompatCheckedTextView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v83, resolved type: androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v84, resolved type: androidx.appcompat.widget.AppCompatTextView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v85, resolved type: androidx.appcompat.widget.AppCompatImageButton} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v86, resolved type: androidx.appcompat.widget.AppCompatSeekBar} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v87, resolved type: androidx.appcompat.widget.AppCompatSpinner} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v88, resolved type: androidx.appcompat.widget.AppCompatRadioButton} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v89, resolved type: androidx.appcompat.widget.AppCompatToggleButton} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v90, resolved type: androidx.appcompat.widget.AppCompatImageView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v91, resolved type: androidx.appcompat.widget.AppCompatAutoCompleteTextView} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v92, resolved type: androidx.appcompat.widget.AppCompatCheckBox} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v93, resolved type: androidx.appcompat.widget.AppCompatEditText} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v94, resolved type: androidx.appcompat.widget.AppCompatButton} */
    /* JADX WARNING: type inference failed for: r7v5 */
    /* JADX WARNING: type inference failed for: r7v6, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r7v9 */
    /* JADX WARNING: type inference failed for: r7v14, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r9v4, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r7v33 */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0117, code lost:
        if (r8.equals("ImageButton") == false) goto L_0x0146;
     */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.View onCreateView(android.view.View r7, java.lang.String r8, android.content.Context r9, android.util.AttributeSet r10) {
        /*
            r6 = this;
            androidx.appcompat.app.AppCompatViewInflater r7 = r6.mAppCompatViewInflater
            r0 = 0
            if (r7 != 0) goto L_0x005b
            android.content.Context r7 = r6.mContext
            int[] r1 = androidx.appcompat.R$styleable.AppCompatTheme
            android.content.res.TypedArray r7 = r7.obtainStyledAttributes(r1)
            r1 = 116(0x74, float:1.63E-43)
            java.lang.String r7 = r7.getString(r1)
            if (r7 != 0) goto L_0x001d
            androidx.appcompat.app.AppCompatViewInflater r7 = new androidx.appcompat.app.AppCompatViewInflater
            r7.<init>()
            r6.mAppCompatViewInflater = r7
            goto L_0x005b
        L_0x001d:
            android.content.Context r1 = r6.mContext     // Catch:{ all -> 0x0038 }
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ all -> 0x0038 }
            java.lang.Class r1 = r1.loadClass(r7)     // Catch:{ all -> 0x0038 }
            java.lang.Class[] r2 = new java.lang.Class[r0]     // Catch:{ all -> 0x0038 }
            java.lang.reflect.Constructor r1 = r1.getDeclaredConstructor(r2)     // Catch:{ all -> 0x0038 }
            java.lang.Object[] r2 = new java.lang.Object[r0]     // Catch:{ all -> 0x0038 }
            java.lang.Object r1 = r1.newInstance(r2)     // Catch:{ all -> 0x0038 }
            androidx.appcompat.app.AppCompatViewInflater r1 = (androidx.appcompat.app.AppCompatViewInflater) r1     // Catch:{ all -> 0x0038 }
            r6.mAppCompatViewInflater = r1     // Catch:{ all -> 0x0038 }
            goto L_0x005b
        L_0x0038:
            r1 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Failed to instantiate custom view inflater "
            r2.append(r3)
            r2.append(r7)
            java.lang.String r7 = ". Falling back to default."
            r2.append(r7)
            java.lang.String r7 = r2.toString()
            java.lang.String r2 = "AppCompatDelegate"
            android.util.Log.i(r2, r7, r1)
            androidx.appcompat.app.AppCompatViewInflater r7 = new androidx.appcompat.app.AppCompatViewInflater
            r7.<init>()
            r6.mAppCompatViewInflater = r7
        L_0x005b:
            androidx.appcompat.app.AppCompatViewInflater r6 = r6.mAppCompatViewInflater
            int r7 = androidx.appcompat.widget.VectorEnabledTintResources.$r8$clinit
            java.util.Objects.requireNonNull(r6)
            r7 = 4
            int[] r1 = androidx.appcompat.R$styleable.View
            android.content.res.TypedArray r1 = r9.obtainStyledAttributes(r10, r1, r0, r0)
            int r2 = r1.getResourceId(r7, r0)
            if (r2 == 0) goto L_0x0076
            java.lang.String r3 = "AppCompatViewInflater"
            java.lang.String r4 = "app:theme is now deprecated. Please move to using android:theme instead."
            android.util.Log.i(r3, r4)
        L_0x0076:
            r1.recycle()
            if (r2 == 0) goto L_0x008c
            boolean r1 = r9 instanceof androidx.appcompat.view.ContextThemeWrapper
            if (r1 == 0) goto L_0x0086
            r1 = r9
            androidx.appcompat.view.ContextThemeWrapper r1 = (androidx.appcompat.view.ContextThemeWrapper) r1
            int r1 = r1.mThemeResource
            if (r1 == r2) goto L_0x008c
        L_0x0086:
            androidx.appcompat.view.ContextThemeWrapper r1 = new androidx.appcompat.view.ContextThemeWrapper
            r1.<init>(r9, r2)
            goto L_0x008d
        L_0x008c:
            r1 = r9
        L_0x008d:
            java.util.Objects.requireNonNull(r8)
            int r2 = r8.hashCode()
            r3 = 3
            r4 = -1
            r5 = 1
            switch(r2) {
                case -1946472170: goto L_0x013b;
                case -1455429095: goto L_0x0130;
                case -1346021293: goto L_0x0125;
                case -938935918: goto L_0x011a;
                case -937446323: goto L_0x0111;
                case -658531749: goto L_0x0106;
                case -339785223: goto L_0x00fb;
                case 776382189: goto L_0x00f0;
                case 799298502: goto L_0x00e2;
                case 1125864064: goto L_0x00d4;
                case 1413872058: goto L_0x00c6;
                case 1601505219: goto L_0x00b8;
                case 1666676343: goto L_0x00aa;
                case 2001146706: goto L_0x009c;
                default: goto L_0x009a;
            }
        L_0x009a:
            goto L_0x0146
        L_0x009c:
            java.lang.String r7 = "Button"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x00a6
            goto L_0x0146
        L_0x00a6:
            r7 = 13
            goto L_0x0147
        L_0x00aa:
            java.lang.String r7 = "EditText"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x00b4
            goto L_0x0146
        L_0x00b4:
            r7 = 12
            goto L_0x0147
        L_0x00b8:
            java.lang.String r7 = "CheckBox"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x00c2
            goto L_0x0146
        L_0x00c2:
            r7 = 11
            goto L_0x0147
        L_0x00c6:
            java.lang.String r7 = "AutoCompleteTextView"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x00d0
            goto L_0x0146
        L_0x00d0:
            r7 = 10
            goto L_0x0147
        L_0x00d4:
            java.lang.String r7 = "ImageView"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x00de
            goto L_0x0146
        L_0x00de:
            r7 = 9
            goto L_0x0147
        L_0x00e2:
            java.lang.String r7 = "ToggleButton"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x00ec
            goto L_0x0146
        L_0x00ec:
            r7 = 8
            goto L_0x0147
        L_0x00f0:
            java.lang.String r7 = "RadioButton"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x00f9
            goto L_0x0146
        L_0x00f9:
            r7 = 7
            goto L_0x0147
        L_0x00fb:
            java.lang.String r7 = "Spinner"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x0104
            goto L_0x0146
        L_0x0104:
            r7 = 6
            goto L_0x0147
        L_0x0106:
            java.lang.String r7 = "SeekBar"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x010f
            goto L_0x0146
        L_0x010f:
            r7 = 5
            goto L_0x0147
        L_0x0111:
            java.lang.String r2 = "ImageButton"
            boolean r2 = r8.equals(r2)
            if (r2 != 0) goto L_0x0147
            goto L_0x0146
        L_0x011a:
            java.lang.String r7 = "TextView"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x0123
            goto L_0x0146
        L_0x0123:
            r7 = r3
            goto L_0x0147
        L_0x0125:
            java.lang.String r7 = "MultiAutoCompleteTextView"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x012e
            goto L_0x0146
        L_0x012e:
            r7 = 2
            goto L_0x0147
        L_0x0130:
            java.lang.String r7 = "CheckedTextView"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x0139
            goto L_0x0146
        L_0x0139:
            r7 = r5
            goto L_0x0147
        L_0x013b:
            java.lang.String r7 = "RatingBar"
            boolean r7 = r8.equals(r7)
            if (r7 != 0) goto L_0x0144
            goto L_0x0146
        L_0x0144:
            r7 = r0
            goto L_0x0147
        L_0x0146:
            r7 = r4
        L_0x0147:
            r2 = 0
            switch(r7) {
                case 0: goto L_0x01a5;
                case 1: goto L_0x019f;
                case 2: goto L_0x0199;
                case 3: goto L_0x0191;
                case 4: goto L_0x018b;
                case 5: goto L_0x0185;
                case 6: goto L_0x017f;
                case 7: goto L_0x0177;
                case 8: goto L_0x0171;
                case 9: goto L_0x016b;
                case 10: goto L_0x0163;
                case 11: goto L_0x015b;
                case 12: goto L_0x0155;
                case 13: goto L_0x014d;
                default: goto L_0x014b;
            }
        L_0x014b:
            r7 = r2
            goto L_0x01aa
        L_0x014d:
            androidx.appcompat.widget.AppCompatButton r7 = r6.createButton(r1, r10)
            r6.verifyNotNull(r7, r8)
            goto L_0x01aa
        L_0x0155:
            androidx.appcompat.widget.AppCompatEditText r7 = new androidx.appcompat.widget.AppCompatEditText
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x015b:
            androidx.appcompat.widget.AppCompatCheckBox r7 = r6.createCheckBox(r1, r10)
            r6.verifyNotNull(r7, r8)
            goto L_0x01aa
        L_0x0163:
            androidx.appcompat.widget.AppCompatAutoCompleteTextView r7 = r6.createAutoCompleteTextView(r1, r10)
            r6.verifyNotNull(r7, r8)
            goto L_0x01aa
        L_0x016b:
            androidx.appcompat.widget.AppCompatImageView r7 = new androidx.appcompat.widget.AppCompatImageView
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x0171:
            androidx.appcompat.widget.AppCompatToggleButton r7 = new androidx.appcompat.widget.AppCompatToggleButton
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x0177:
            androidx.appcompat.widget.AppCompatRadioButton r7 = r6.createRadioButton(r1, r10)
            r6.verifyNotNull(r7, r8)
            goto L_0x01aa
        L_0x017f:
            androidx.appcompat.widget.AppCompatSpinner r7 = new androidx.appcompat.widget.AppCompatSpinner
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x0185:
            androidx.appcompat.widget.AppCompatSeekBar r7 = new androidx.appcompat.widget.AppCompatSeekBar
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x018b:
            androidx.appcompat.widget.AppCompatImageButton r7 = new androidx.appcompat.widget.AppCompatImageButton
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x0191:
            androidx.appcompat.widget.AppCompatTextView r7 = r6.createTextView(r1, r10)
            r6.verifyNotNull(r7, r8)
            goto L_0x01aa
        L_0x0199:
            androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView r7 = new androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x019f:
            androidx.appcompat.widget.AppCompatCheckedTextView r7 = new androidx.appcompat.widget.AppCompatCheckedTextView
            r7.<init>(r1, r10)
            goto L_0x01aa
        L_0x01a5:
            androidx.appcompat.widget.AppCompatRatingBar r7 = new androidx.appcompat.widget.AppCompatRatingBar
            r7.<init>(r1, r10)
        L_0x01aa:
            if (r7 != 0) goto L_0x0205
            if (r9 == r1) goto L_0x0205
            java.lang.String r7 = "view"
            boolean r7 = r8.equals(r7)
            if (r7 == 0) goto L_0x01bd
            java.lang.String r7 = "class"
            java.lang.String r8 = r10.getAttributeValue(r2, r7)
        L_0x01bd:
            java.lang.Object[] r7 = r6.mConstructorArgs     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            r7[r0] = r1     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            r7[r5] = r10     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            r7 = 46
            int r7 = r8.indexOf(r7)     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            if (r4 != r7) goto L_0x01ea
            r7 = r0
        L_0x01cc:
            java.lang.String[] r9 = androidx.appcompat.app.AppCompatViewInflater.sClassPrefixList     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            if (r7 >= r3) goto L_0x01e3
            r9 = r9[r7]     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            android.view.View r9 = r6.createViewByPrefix(r1, r8, r9)     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            if (r9 == 0) goto L_0x01e0
            java.lang.Object[] r6 = r6.mConstructorArgs
            r6[r0] = r2
            r6[r5] = r2
            r2 = r9
            goto L_0x0204
        L_0x01e0:
            int r7 = r7 + 1
            goto L_0x01cc
        L_0x01e3:
            java.lang.Object[] r6 = r6.mConstructorArgs
            r6[r0] = r2
            r6[r5] = r2
            goto L_0x0204
        L_0x01ea:
            android.view.View r7 = r6.createViewByPrefix(r1, r8, r2)     // Catch:{ Exception -> 0x01fe, all -> 0x01f6 }
            java.lang.Object[] r6 = r6.mConstructorArgs
            r6[r0] = r2
            r6[r5] = r2
            r2 = r7
            goto L_0x0204
        L_0x01f6:
            r7 = move-exception
            java.lang.Object[] r6 = r6.mConstructorArgs
            r6[r0] = r2
            r6[r5] = r2
            throw r7
        L_0x01fe:
            java.lang.Object[] r6 = r6.mConstructorArgs
            r6[r0] = r2
            r6[r5] = r2
        L_0x0204:
            r7 = r2
        L_0x0205:
            if (r7 == 0) goto L_0x022f
            android.content.Context r6 = r7.getContext()
            boolean r8 = r6 instanceof android.content.ContextWrapper
            if (r8 == 0) goto L_0x022f
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r8 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r8 = androidx.core.view.ViewCompat.Api15Impl.hasOnClickListeners(r7)
            if (r8 != 0) goto L_0x0218
            goto L_0x022f
        L_0x0218:
            int[] r8 = androidx.appcompat.app.AppCompatViewInflater.sOnClickAttrs
            android.content.res.TypedArray r6 = r6.obtainStyledAttributes(r10, r8)
            java.lang.String r8 = r6.getString(r0)
            if (r8 == 0) goto L_0x022c
            androidx.appcompat.app.AppCompatViewInflater$DeclaredOnClickListener r9 = new androidx.appcompat.app.AppCompatViewInflater$DeclaredOnClickListener
            r9.<init>(r7, r8)
            r7.setOnClickListener(r9)
        L_0x022c:
            r6.recycle()
        L_0x022f:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.onCreateView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet):android.view.View");
    }

    public final void onStart() {
        applyDayNight(true);
    }

    public final void setContentView(View view) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        AppCompatWindowCallback appCompatWindowCallback = this.mAppCompatWindowCallback;
        Objects.requireNonNull(appCompatWindowCallback);
        appCompatWindowCallback.mWrapped.onContentChanged();
    }

    public static final class PanelFeatureState {
        public int background;
        public View createdPanelView;
        public ListMenuDecorView decorView;
        public int featureId;
        public Bundle frozenActionViewState;
        public int gravity;
        public boolean isHandled;
        public boolean isOpen;
        public boolean isPrepared;
        public ListMenuPresenter listMenuPresenter;
        public ContextThemeWrapper listPresenterContext;
        public MenuBuilder menu;
        public boolean refreshDecorView = false;
        public boolean refreshMenuContent;
        public View shownPanelView;
        public int windowAnimations;

        @SuppressLint({"BanParcelableUsage"})
        public static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
                public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.readFromParcel(parcel, classLoader);
                }

                public final Object createFromParcel(Parcel parcel) {
                    return SavedState.readFromParcel(parcel, (ClassLoader) null);
                }

                public final Object[] newArray(int i) {
                    return new SavedState[i];
                }
            };
            public int featureId;
            public boolean isOpen;
            public Bundle menuState;

            public final int describeContents() {
                return 0;
            }

            public static SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.featureId = parcel.readInt();
                boolean z = true;
                if (parcel.readInt() != 1) {
                    z = false;
                }
                savedState.isOpen = z;
                if (z) {
                    savedState.menuState = parcel.readBundle(classLoader);
                }
                return savedState;
            }

            public final void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.featureId);
                parcel.writeInt(this.isOpen ? 1 : 0);
                if (this.isOpen) {
                    parcel.writeBundle(this.menuState);
                }
            }
        }

        public PanelFeatureState(int i) {
            this.featureId = i;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0063  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x011f  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x013c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean applyDayNight(boolean r11) {
        /*
            r10 = this;
            boolean r0 = r10.mDestroyed
            r1 = 0
            if (r0 == 0) goto L_0x0006
            return r1
        L_0x0006:
            int r0 = r10.mLocalNightMode
            r2 = -100
            if (r0 == r2) goto L_0x000d
            goto L_0x000e
        L_0x000d:
            r0 = r2
        L_0x000e:
            android.content.Context r2 = r10.mContext
            int r2 = r10.mapNightMode(r2, r0)
            android.content.Context r3 = r10.mContext
            r4 = 0
            android.content.res.Configuration r2 = createOverrideConfigurationForDayNight(r3, r2, r4)
            boolean r3 = r10.mActivityHandlesUiModeChecked
            r5 = 1
            if (r3 != 0) goto L_0x005b
            java.lang.Object r3 = r10.mHost
            boolean r3 = r3 instanceof android.app.Activity
            if (r3 == 0) goto L_0x005b
            android.content.Context r3 = r10.mContext
            android.content.pm.PackageManager r3 = r3.getPackageManager()
            if (r3 != 0) goto L_0x0030
            r3 = r1
            goto L_0x005f
        L_0x0030:
            r6 = 269221888(0x100c0000, float:2.7610132E-29)
            android.content.ComponentName r7 = new android.content.ComponentName     // Catch:{ NameNotFoundException -> 0x0051 }
            android.content.Context r8 = r10.mContext     // Catch:{ NameNotFoundException -> 0x0051 }
            java.lang.Object r9 = r10.mHost     // Catch:{ NameNotFoundException -> 0x0051 }
            java.lang.Class r9 = r9.getClass()     // Catch:{ NameNotFoundException -> 0x0051 }
            r7.<init>(r8, r9)     // Catch:{ NameNotFoundException -> 0x0051 }
            android.content.pm.ActivityInfo r3 = r3.getActivityInfo(r7, r6)     // Catch:{ NameNotFoundException -> 0x0051 }
            if (r3 == 0) goto L_0x004d
            int r3 = r3.configChanges     // Catch:{ NameNotFoundException -> 0x0051 }
            r3 = r3 & 512(0x200, float:7.175E-43)
            if (r3 == 0) goto L_0x004d
            r3 = r5
            goto L_0x004e
        L_0x004d:
            r3 = r1
        L_0x004e:
            r10.mActivityHandlesUiMode = r3     // Catch:{ NameNotFoundException -> 0x0051 }
            goto L_0x005b
        L_0x0051:
            r3 = move-exception
            java.lang.String r6 = "AppCompatDelegate"
            java.lang.String r7 = "Exception while getting ActivityInfo"
            android.util.Log.d(r6, r7, r3)
            r10.mActivityHandlesUiMode = r1
        L_0x005b:
            r10.mActivityHandlesUiModeChecked = r5
            boolean r3 = r10.mActivityHandlesUiMode
        L_0x005f:
            android.content.res.Configuration r6 = r10.mEffectiveConfiguration
            if (r6 != 0) goto L_0x006d
            android.content.Context r6 = r10.mContext
            android.content.res.Resources r6 = r6.getResources()
            android.content.res.Configuration r6 = r6.getConfiguration()
        L_0x006d:
            int r6 = r6.uiMode
            r6 = r6 & 48
            int r2 = r2.uiMode
            r2 = r2 & 48
            if (r6 == r2) goto L_0x009f
            if (r11 == 0) goto L_0x009f
            if (r3 != 0) goto L_0x009f
            boolean r11 = r10.mBaseContextAttached
            if (r11 == 0) goto L_0x009f
            boolean r11 = sCanReturnDifferentContext
            if (r11 != 0) goto L_0x0087
            boolean r11 = r10.mCreated
            if (r11 == 0) goto L_0x009f
        L_0x0087:
            java.lang.Object r11 = r10.mHost
            boolean r7 = r11 instanceof android.app.Activity
            if (r7 == 0) goto L_0x009f
            android.app.Activity r11 = (android.app.Activity) r11
            boolean r11 = r11.isChild()
            if (r11 != 0) goto L_0x009f
            java.lang.Object r11 = r10.mHost
            android.app.Activity r11 = (android.app.Activity) r11
            int r1 = androidx.core.app.ActivityCompat.$r8$clinit
            r11.recreate()
            r1 = r5
        L_0x009f:
            if (r1 != 0) goto L_0x0105
            if (r6 == r2) goto L_0x0105
            android.content.Context r11 = r10.mContext
            android.content.res.Resources r11 = r11.getResources()
            android.content.res.Configuration r1 = new android.content.res.Configuration
            android.content.res.Configuration r6 = r11.getConfiguration()
            r1.<init>(r6)
            android.content.res.Configuration r6 = r11.getConfiguration()
            int r6 = r6.uiMode
            r6 = r6 & -49
            r2 = r2 | r6
            r1.uiMode = r2
            r11.updateConfiguration(r1, r4)
            int r11 = r10.mThemeResId
            if (r11 == 0) goto L_0x00d4
            android.content.Context r2 = r10.mContext
            r2.setTheme(r11)
            android.content.Context r11 = r10.mContext
            android.content.res.Resources$Theme r11 = r11.getTheme()
            int r2 = r10.mThemeResId
            r11.applyStyle(r2, r5)
        L_0x00d4:
            if (r3 == 0) goto L_0x0106
            java.lang.Object r11 = r10.mHost
            boolean r2 = r11 instanceof android.app.Activity
            if (r2 == 0) goto L_0x0106
            android.app.Activity r11 = (android.app.Activity) r11
            boolean r2 = r11 instanceof androidx.lifecycle.LifecycleOwner
            if (r2 == 0) goto L_0x00f9
            r2 = r11
            androidx.lifecycle.LifecycleOwner r2 = (androidx.lifecycle.LifecycleOwner) r2
            androidx.lifecycle.Lifecycle r2 = r2.getLifecycle()
            androidx.lifecycle.Lifecycle$State r2 = r2.getCurrentState()
            androidx.lifecycle.Lifecycle$State r3 = androidx.lifecycle.Lifecycle.State.CREATED
            boolean r2 = r2.isAtLeast(r3)
            if (r2 == 0) goto L_0x0106
            r11.onConfigurationChanged(r1)
            goto L_0x0106
        L_0x00f9:
            boolean r2 = r10.mCreated
            if (r2 == 0) goto L_0x0106
            boolean r2 = r10.mDestroyed
            if (r2 != 0) goto L_0x0106
            r11.onConfigurationChanged(r1)
            goto L_0x0106
        L_0x0105:
            r5 = r1
        L_0x0106:
            if (r5 == 0) goto L_0x0113
            java.lang.Object r11 = r10.mHost
            boolean r1 = r11 instanceof androidx.appcompat.app.AppCompatActivity
            if (r1 == 0) goto L_0x0113
            androidx.appcompat.app.AppCompatActivity r11 = (androidx.appcompat.app.AppCompatActivity) r11
            java.util.Objects.requireNonNull(r11)
        L_0x0113:
            if (r0 != 0) goto L_0x011f
            android.content.Context r11 = r10.mContext
            androidx.appcompat.app.AppCompatDelegateImpl$AutoNightModeManager r11 = r10.getAutoTimeNightModeManager(r11)
            r11.setup()
            goto L_0x0126
        L_0x011f:
            androidx.appcompat.app.AppCompatDelegateImpl$AutoTimeNightModeManager r11 = r10.mAutoTimeNightModeManager
            if (r11 == 0) goto L_0x0126
            r11.cleanup()
        L_0x0126:
            r11 = 3
            if (r0 != r11) goto L_0x013c
            android.content.Context r11 = r10.mContext
            androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager r0 = r10.mAutoBatteryNightModeManager
            if (r0 != 0) goto L_0x0136
            androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager r0 = new androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager
            r0.<init>(r11)
            r10.mAutoBatteryNightModeManager = r0
        L_0x0136:
            androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager r10 = r10.mAutoBatteryNightModeManager
            r10.setup()
            goto L_0x0143
        L_0x013c:
            androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager r10 = r10.mAutoBatteryNightModeManager
            if (r10 == 0) goto L_0x0143
            r10.cleanup()
        L_0x0143:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.applyDayNight(boolean):boolean");
    }

    public final void attachToWindow(Window window) {
        int resourceId;
        Drawable drawable;
        if (this.mWindow == null) {
            Window.Callback callback = window.getCallback();
            if (!(callback instanceof AppCompatWindowCallback)) {
                AppCompatWindowCallback appCompatWindowCallback = new AppCompatWindowCallback(callback);
                this.mAppCompatWindowCallback = appCompatWindowCallback;
                window.setCallback(appCompatWindowCallback);
                Context context = this.mContext;
                Drawable drawable2 = null;
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes((AttributeSet) null, sWindowBackgroundStyleable);
                if (obtainStyledAttributes.hasValue(0) && (resourceId = obtainStyledAttributes.getResourceId(0, 0)) != 0) {
                    AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
                    Objects.requireNonNull(appCompatDrawableManager);
                    synchronized (appCompatDrawableManager) {
                        drawable = appCompatDrawableManager.mResourceManager.getDrawable(context, resourceId, true);
                    }
                    drawable2 = drawable;
                }
                if (drawable2 != null) {
                    window.setBackgroundDrawable(drawable2);
                }
                obtainStyledAttributes.recycle();
                this.mWindow = window;
                return;
            }
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    public final void callOnPanelClosed(int i, PanelFeatureState panelFeatureState, MenuBuilder menuBuilder) {
        if (menuBuilder == null) {
            if (panelFeatureState == null && i >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.mPanels;
                if (i < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i];
                }
            }
            if (panelFeatureState != null) {
                menuBuilder = panelFeatureState.menu;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.isOpen) && !this.mDestroyed) {
            AppCompatWindowCallback appCompatWindowCallback = this.mAppCompatWindowCallback;
            Objects.requireNonNull(appCompatWindowCallback);
            appCompatWindowCallback.mWrapped.onPanelClosed(i, menuBuilder);
        }
    }

    public final void checkCloseActionMenu(MenuBuilder menuBuilder) {
        if (!this.mClosingActionMenu) {
            this.mClosingActionMenu = true;
            this.mDecorContentParent.dismissPopups();
            Window.Callback windowCallback = getWindowCallback();
            if (windowCallback != null && !this.mDestroyed) {
                windowCallback.onPanelClosed(108, menuBuilder);
            }
            this.mClosingActionMenu = false;
        }
    }

    public final void closePanel(PanelFeatureState panelFeatureState, boolean z) {
        ListMenuDecorView listMenuDecorView;
        DecorContentParent decorContentParent;
        if (!z || panelFeatureState.featureId != 0 || (decorContentParent = this.mDecorContentParent) == null || !decorContentParent.isOverflowMenuShowing()) {
            WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
            if (!(windowManager == null || !panelFeatureState.isOpen || (listMenuDecorView = panelFeatureState.decorView) == null)) {
                windowManager.removeView(listMenuDecorView);
                if (z) {
                    callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, (MenuBuilder) null);
                }
            }
            panelFeatureState.isPrepared = false;
            panelFeatureState.isHandled = false;
            panelFeatureState.isOpen = false;
            panelFeatureState.shownPanelView = null;
            panelFeatureState.refreshDecorView = true;
            if (this.mPreparedPanel == panelFeatureState) {
                this.mPreparedPanel = null;
                return;
            }
            return;
        }
        checkCloseActionMenu(panelFeatureState.menu);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0120, code lost:
        if (r6 != false) goto L_0x0122;
     */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:90:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:96:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean dispatchKeyEvent(android.view.KeyEvent r7) {
        /*
            r6 = this;
            java.lang.Object r0 = r6.mHost
            boolean r1 = r0 instanceof androidx.core.view.KeyEventDispatcher$Component
            if (r1 != 0) goto L_0x000a
            boolean r0 = r0 instanceof androidx.appcompat.app.AppCompatDialog
            if (r0 == 0) goto L_0x0014
        L_0x000a:
            android.view.Window r0 = r6.mWindow
            android.view.View r0 = r0.getDecorView()
            if (r0 == 0) goto L_0x0014
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
        L_0x0014:
            int r0 = r7.getKeyCode()
            r1 = 82
            r2 = 1
            if (r0 != r1) goto L_0x002b
            androidx.appcompat.app.AppCompatDelegateImpl$AppCompatWindowCallback r0 = r6.mAppCompatWindowCallback
            java.util.Objects.requireNonNull(r0)
            android.view.Window$Callback r0 = r0.mWrapped
            boolean r0 = r0.dispatchKeyEvent(r7)
            if (r0 == 0) goto L_0x002b
            return r2
        L_0x002b:
            int r0 = r7.getKeyCode()
            int r3 = r7.getAction()
            r4 = 0
            if (r3 != 0) goto L_0x0038
            r3 = r2
            goto L_0x0039
        L_0x0038:
            r3 = r4
        L_0x0039:
            r5 = 4
            if (r3 == 0) goto L_0x0063
            if (r0 == r5) goto L_0x0055
            if (r0 == r1) goto L_0x0042
            goto L_0x0128
        L_0x0042:
            int r0 = r7.getRepeatCount()
            if (r0 != 0) goto L_0x0129
            androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r0 = r6.getPanelState(r4)
            boolean r1 = r0.isOpen
            if (r1 != 0) goto L_0x0129
            r6.preparePanel(r0, r7)
            goto L_0x0129
        L_0x0055:
            int r7 = r7.getFlags()
            r7 = r7 & 128(0x80, float:1.794E-43)
            if (r7 == 0) goto L_0x005e
            goto L_0x005f
        L_0x005e:
            r2 = r4
        L_0x005f:
            r6.mLongPressBackDown = r2
            goto L_0x0128
        L_0x0063:
            if (r0 == r5) goto L_0x00ed
            if (r0 == r1) goto L_0x0069
            goto L_0x0128
        L_0x0069:
            androidx.appcompat.view.ActionMode r0 = r6.mActionMode
            if (r0 == 0) goto L_0x006f
            goto L_0x0129
        L_0x006f:
            androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r0 = r6.getPanelState(r4)
            androidx.appcompat.widget.DecorContentParent r1 = r6.mDecorContentParent
            if (r1 == 0) goto L_0x00a9
            boolean r1 = r1.canShowOverflowMenu()
            if (r1 == 0) goto L_0x00a9
            android.content.Context r1 = r6.mContext
            android.view.ViewConfiguration r1 = android.view.ViewConfiguration.get(r1)
            boolean r1 = r1.hasPermanentMenuKey()
            if (r1 != 0) goto L_0x00a9
            androidx.appcompat.widget.DecorContentParent r1 = r6.mDecorContentParent
            boolean r1 = r1.isOverflowMenuShowing()
            if (r1 != 0) goto L_0x00a2
            boolean r1 = r6.mDestroyed
            if (r1 != 0) goto L_0x00c9
            boolean r7 = r6.preparePanel(r0, r7)
            if (r7 == 0) goto L_0x00c9
            androidx.appcompat.widget.DecorContentParent r7 = r6.mDecorContentParent
            boolean r7 = r7.showOverflowMenu()
            goto L_0x00cf
        L_0x00a2:
            androidx.appcompat.widget.DecorContentParent r7 = r6.mDecorContentParent
            boolean r7 = r7.hideOverflowMenu()
            goto L_0x00cf
        L_0x00a9:
            boolean r1 = r0.isOpen
            if (r1 != 0) goto L_0x00cb
            boolean r3 = r0.isHandled
            if (r3 == 0) goto L_0x00b2
            goto L_0x00cb
        L_0x00b2:
            boolean r1 = r0.isPrepared
            if (r1 == 0) goto L_0x00c9
            boolean r1 = r0.refreshMenuContent
            if (r1 == 0) goto L_0x00c1
            r0.isPrepared = r4
            boolean r1 = r6.preparePanel(r0, r7)
            goto L_0x00c2
        L_0x00c1:
            r1 = r2
        L_0x00c2:
            if (r1 == 0) goto L_0x00c9
            r6.openPanel(r0, r7)
            r7 = r2
            goto L_0x00cf
        L_0x00c9:
            r7 = r4
            goto L_0x00cf
        L_0x00cb:
            r6.closePanel(r0, r2)
            r7 = r1
        L_0x00cf:
            if (r7 == 0) goto L_0x0129
            android.content.Context r6 = r6.mContext
            android.content.Context r6 = r6.getApplicationContext()
            java.lang.String r7 = "audio"
            java.lang.Object r6 = r6.getSystemService(r7)
            android.media.AudioManager r6 = (android.media.AudioManager) r6
            if (r6 == 0) goto L_0x00e5
            r6.playSoundEffect(r4)
            goto L_0x0129
        L_0x00e5:
            java.lang.String r6 = "AppCompatDelegate"
            java.lang.String r7 = "Couldn't get audio manager"
            android.util.Log.w(r6, r7)
            goto L_0x0129
        L_0x00ed:
            boolean r7 = r6.mLongPressBackDown
            r6.mLongPressBackDown = r4
            androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState r0 = r6.getPanelState(r4)
            boolean r1 = r0.isOpen
            if (r1 == 0) goto L_0x00ff
            if (r7 != 0) goto L_0x0129
            r6.closePanel(r0, r2)
            goto L_0x0129
        L_0x00ff:
            androidx.appcompat.view.ActionMode r7 = r6.mActionMode
            if (r7 == 0) goto L_0x0107
            r7.finish()
            goto L_0x0122
        L_0x0107:
            r6.initWindowDecorActionBar()
            androidx.appcompat.app.WindowDecorActionBar r6 = r6.mActionBar
            if (r6 == 0) goto L_0x0124
            androidx.appcompat.widget.DecorToolbar r7 = r6.mDecorToolbar
            if (r7 == 0) goto L_0x011f
            boolean r7 = r7.hasExpandedActionView()
            if (r7 == 0) goto L_0x011f
            androidx.appcompat.widget.DecorToolbar r6 = r6.mDecorToolbar
            r6.collapseActionView()
            r6 = r2
            goto L_0x0120
        L_0x011f:
            r6 = r4
        L_0x0120:
            if (r6 == 0) goto L_0x0124
        L_0x0122:
            r6 = r2
            goto L_0x0125
        L_0x0124:
            r6 = r4
        L_0x0125:
            if (r6 == 0) goto L_0x0128
            goto L_0x0129
        L_0x0128:
            r2 = r4
        L_0x0129:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.dispatchKeyEvent(android.view.KeyEvent):boolean");
    }

    public final void ensureSubDecor() {
        ViewGroup viewGroup;
        CharSequence charSequence;
        Context context;
        if (!this.mSubDecorInstalled) {
            TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(androidx.appcompat.R$styleable.AppCompatTheme);
            if (obtainStyledAttributes.hasValue(117)) {
                if (obtainStyledAttributes.getBoolean(126, false)) {
                    requestWindowFeature(1);
                } else if (obtainStyledAttributes.getBoolean(117, false)) {
                    requestWindowFeature(108);
                }
                if (obtainStyledAttributes.getBoolean(118, false)) {
                    requestWindowFeature(109);
                }
                if (obtainStyledAttributes.getBoolean(119, false)) {
                    requestWindowFeature(10);
                }
                this.mIsFloating = obtainStyledAttributes.getBoolean(0, false);
                obtainStyledAttributes.recycle();
                ensureWindow();
                this.mWindow.getDecorView();
                LayoutInflater from = LayoutInflater.from(this.mContext);
                if (this.mWindowNoTitle) {
                    viewGroup = this.mOverlayActionMode ? (ViewGroup) from.inflate(C1777R.layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null) : (ViewGroup) from.inflate(C1777R.layout.abc_screen_simple, (ViewGroup) null);
                } else if (this.mIsFloating) {
                    viewGroup = (ViewGroup) from.inflate(C1777R.layout.abc_dialog_title_material, (ViewGroup) null);
                    this.mOverlayActionBar = false;
                    this.mHasActionBar = false;
                } else if (this.mHasActionBar) {
                    TypedValue typedValue = new TypedValue();
                    this.mContext.getTheme().resolveAttribute(C1777R.attr.actionBarTheme, typedValue, true);
                    if (typedValue.resourceId != 0) {
                        context = new ContextThemeWrapper(this.mContext, typedValue.resourceId);
                    } else {
                        context = this.mContext;
                    }
                    viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(C1777R.layout.abc_screen_toolbar, (ViewGroup) null);
                    DecorContentParent decorContentParent = (DecorContentParent) viewGroup.findViewById(C1777R.C1779id.decor_content_parent);
                    this.mDecorContentParent = decorContentParent;
                    decorContentParent.setWindowCallback(getWindowCallback());
                    if (this.mOverlayActionBar) {
                        this.mDecorContentParent.initFeature(109);
                    }
                    if (this.mFeatureProgress) {
                        this.mDecorContentParent.initFeature(2);
                    }
                    if (this.mFeatureIndeterminateProgress) {
                        this.mDecorContentParent.initFeature(5);
                    }
                } else {
                    viewGroup = null;
                }
                if (viewGroup != null) {
                    C00303 r1 = new OnApplyWindowInsetsListener() {
                        public final WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                            boolean z;
                            int i;
                            int i2;
                            boolean z2;
                            int i3;
                            ViewGroup.MarginLayoutParams marginLayoutParams;
                            int i4;
                            int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                            Objects.requireNonNull(appCompatDelegateImpl);
                            int systemWindowInsetTop2 = windowInsetsCompat.getSystemWindowInsetTop();
                            ActionBarContextView actionBarContextView = appCompatDelegateImpl.mActionModeView;
                            int i5 = 8;
                            if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
                                z = false;
                            } else {
                                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) appCompatDelegateImpl.mActionModeView.getLayoutParams();
                                boolean z3 = true;
                                if (appCompatDelegateImpl.mActionModeView.isShown()) {
                                    if (appCompatDelegateImpl.mTempRect1 == null) {
                                        appCompatDelegateImpl.mTempRect1 = new Rect();
                                        appCompatDelegateImpl.mTempRect2 = new Rect();
                                    }
                                    Rect rect = appCompatDelegateImpl.mTempRect1;
                                    Rect rect2 = appCompatDelegateImpl.mTempRect2;
                                    rect.set(windowInsetsCompat.getSystemWindowInsetLeft(), windowInsetsCompat.getSystemWindowInsetTop(), windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                                    ViewGroup viewGroup = appCompatDelegateImpl.mSubDecor;
                                    Method method = ViewUtils.sComputeFitSystemWindowsMethod;
                                    if (method != null) {
                                        try {
                                            method.invoke(viewGroup, new Object[]{rect, rect2});
                                        } catch (Exception e) {
                                            Log.d("ViewUtils", "Could not invoke computeFitSystemWindows", e);
                                        }
                                    }
                                    int i6 = rect.top;
                                    int i7 = rect.left;
                                    int i8 = rect.right;
                                    ViewGroup viewGroup2 = appCompatDelegateImpl.mSubDecor;
                                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                                    WindowInsetsCompat rootWindowInsets = ViewCompat.Api23Impl.getRootWindowInsets(viewGroup2);
                                    if (rootWindowInsets == null) {
                                        i = 0;
                                    } else {
                                        i = rootWindowInsets.getSystemWindowInsetLeft();
                                    }
                                    if (rootWindowInsets == null) {
                                        i2 = 0;
                                    } else {
                                        i2 = rootWindowInsets.getSystemWindowInsetRight();
                                    }
                                    if (marginLayoutParams2.topMargin == i6 && marginLayoutParams2.leftMargin == i7 && marginLayoutParams2.rightMargin == i8) {
                                        z2 = false;
                                    } else {
                                        marginLayoutParams2.topMargin = i6;
                                        marginLayoutParams2.leftMargin = i7;
                                        marginLayoutParams2.rightMargin = i8;
                                        z2 = true;
                                    }
                                    if (i6 <= 0 || appCompatDelegateImpl.mStatusGuard != null) {
                                        View view2 = appCompatDelegateImpl.mStatusGuard;
                                        if (!(view2 == null || ((marginLayoutParams = (ViewGroup.MarginLayoutParams) view2.getLayoutParams()).height == (i4 = marginLayoutParams2.topMargin) && marginLayoutParams.leftMargin == i && marginLayoutParams.rightMargin == i2))) {
                                            marginLayoutParams.height = i4;
                                            marginLayoutParams.leftMargin = i;
                                            marginLayoutParams.rightMargin = i2;
                                            appCompatDelegateImpl.mStatusGuard.setLayoutParams(marginLayoutParams);
                                        }
                                    } else {
                                        View view3 = new View(appCompatDelegateImpl.mContext);
                                        appCompatDelegateImpl.mStatusGuard = view3;
                                        view3.setVisibility(8);
                                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, marginLayoutParams2.topMargin, 51);
                                        layoutParams.leftMargin = i;
                                        layoutParams.rightMargin = i2;
                                        appCompatDelegateImpl.mSubDecor.addView(appCompatDelegateImpl.mStatusGuard, -1, layoutParams);
                                    }
                                    View view4 = appCompatDelegateImpl.mStatusGuard;
                                    if (view4 != null) {
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    if (z && view4.getVisibility() != 0) {
                                        View view5 = appCompatDelegateImpl.mStatusGuard;
                                        if ((ViewCompat.Api16Impl.getWindowSystemUiVisibility(view5) & 8192) == 0) {
                                            z3 = false;
                                        }
                                        if (z3) {
                                            Context context = appCompatDelegateImpl.mContext;
                                            Object obj = ContextCompat.sLock;
                                            i3 = context.getColor(C1777R.color.abc_decor_view_status_guard_light);
                                        } else {
                                            Context context2 = appCompatDelegateImpl.mContext;
                                            Object obj2 = ContextCompat.sLock;
                                            i3 = context2.getColor(C1777R.color.abc_decor_view_status_guard);
                                        }
                                        view5.setBackgroundColor(i3);
                                    }
                                    if (!appCompatDelegateImpl.mOverlayActionMode && z) {
                                        systemWindowInsetTop2 = 0;
                                    }
                                    z3 = z2;
                                } else if (marginLayoutParams2.topMargin != 0) {
                                    marginLayoutParams2.topMargin = 0;
                                    z = false;
                                } else {
                                    z3 = false;
                                    z = false;
                                }
                                if (z3) {
                                    appCompatDelegateImpl.mActionModeView.setLayoutParams(marginLayoutParams2);
                                }
                            }
                            View view6 = appCompatDelegateImpl.mStatusGuard;
                            if (view6 != null) {
                                if (z) {
                                    i5 = 0;
                                }
                                view6.setVisibility(i5);
                            }
                            if (systemWindowInsetTop != systemWindowInsetTop2) {
                                windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), systemWindowInsetTop2, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                            }
                            return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                        }
                    };
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api21Impl.setOnApplyWindowInsetsListener(viewGroup, r1);
                    if (this.mDecorContentParent == null) {
                        this.mTitleView = (TextView) viewGroup.findViewById(C1777R.C1779id.title);
                    }
                    Method method = ViewUtils.sComputeFitSystemWindowsMethod;
                    try {
                        Method method2 = viewGroup.getClass().getMethod("makeOptionalFitsSystemWindows", new Class[0]);
                        if (!method2.isAccessible()) {
                            method2.setAccessible(true);
                        }
                        method2.invoke(viewGroup, new Object[0]);
                    } catch (NoSuchMethodException unused) {
                        Log.d("ViewUtils", "Could not find method makeOptionalFitsSystemWindows. Oh well...");
                    } catch (InvocationTargetException e) {
                        Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e);
                    } catch (IllegalAccessException e2) {
                        Log.d("ViewUtils", "Could not invoke makeOptionalFitsSystemWindows", e2);
                    }
                    ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(C1777R.C1779id.action_bar_activity_content);
                    ViewGroup viewGroup2 = (ViewGroup) this.mWindow.findViewById(16908290);
                    if (viewGroup2 != null) {
                        while (viewGroup2.getChildCount() > 0) {
                            View childAt = viewGroup2.getChildAt(0);
                            viewGroup2.removeViewAt(0);
                            contentFrameLayout.addView(childAt);
                        }
                        viewGroup2.setId(-1);
                        contentFrameLayout.setId(16908290);
                        if (viewGroup2 instanceof FrameLayout) {
                            ((FrameLayout) viewGroup2).setForeground((Drawable) null);
                        }
                    }
                    this.mWindow.setContentView(viewGroup);
                    C00315 r2 = new ContentFrameLayout.OnAttachListener() {
                    };
                    Objects.requireNonNull(contentFrameLayout);
                    contentFrameLayout.mAttachListener = r2;
                    this.mSubDecor = viewGroup;
                    Object obj = this.mHost;
                    if (obj instanceof Activity) {
                        charSequence = ((Activity) obj).getTitle();
                    } else {
                        charSequence = this.mTitle;
                    }
                    if (!TextUtils.isEmpty(charSequence)) {
                        DecorContentParent decorContentParent2 = this.mDecorContentParent;
                        if (decorContentParent2 != null) {
                            decorContentParent2.setWindowTitle(charSequence);
                        } else {
                            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
                            if (windowDecorActionBar != null) {
                                windowDecorActionBar.mDecorToolbar.setWindowTitle(charSequence);
                            } else {
                                TextView textView = this.mTitleView;
                                if (textView != null) {
                                    textView.setText(charSequence);
                                }
                            }
                        }
                    }
                    ContentFrameLayout contentFrameLayout2 = (ContentFrameLayout) this.mSubDecor.findViewById(16908290);
                    View decorView = this.mWindow.getDecorView();
                    int paddingLeft = decorView.getPaddingLeft();
                    int paddingTop = decorView.getPaddingTop();
                    int paddingRight = decorView.getPaddingRight();
                    int paddingBottom = decorView.getPaddingBottom();
                    Objects.requireNonNull(contentFrameLayout2);
                    contentFrameLayout2.mDecorPadding.set(paddingLeft, paddingTop, paddingRight, paddingBottom);
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                    if (ViewCompat.Api19Impl.isLaidOut(contentFrameLayout2)) {
                        contentFrameLayout2.requestLayout();
                    }
                    TypedArray obtainStyledAttributes2 = this.mContext.obtainStyledAttributes(androidx.appcompat.R$styleable.AppCompatTheme);
                    if (contentFrameLayout2.mMinWidthMajor == null) {
                        contentFrameLayout2.mMinWidthMajor = new TypedValue();
                    }
                    obtainStyledAttributes2.getValue(124, contentFrameLayout2.mMinWidthMajor);
                    if (contentFrameLayout2.mMinWidthMinor == null) {
                        contentFrameLayout2.mMinWidthMinor = new TypedValue();
                    }
                    obtainStyledAttributes2.getValue(125, contentFrameLayout2.mMinWidthMinor);
                    if (obtainStyledAttributes2.hasValue(122)) {
                        if (contentFrameLayout2.mFixedWidthMajor == null) {
                            contentFrameLayout2.mFixedWidthMajor = new TypedValue();
                        }
                        obtainStyledAttributes2.getValue(122, contentFrameLayout2.mFixedWidthMajor);
                    }
                    if (obtainStyledAttributes2.hasValue(123)) {
                        if (contentFrameLayout2.mFixedWidthMinor == null) {
                            contentFrameLayout2.mFixedWidthMinor = new TypedValue();
                        }
                        obtainStyledAttributes2.getValue(123, contentFrameLayout2.mFixedWidthMinor);
                    }
                    if (obtainStyledAttributes2.hasValue(120)) {
                        if (contentFrameLayout2.mFixedHeightMajor == null) {
                            contentFrameLayout2.mFixedHeightMajor = new TypedValue();
                        }
                        obtainStyledAttributes2.getValue(120, contentFrameLayout2.mFixedHeightMajor);
                    }
                    if (obtainStyledAttributes2.hasValue(121)) {
                        if (contentFrameLayout2.mFixedHeightMinor == null) {
                            contentFrameLayout2.mFixedHeightMinor = new TypedValue();
                        }
                        obtainStyledAttributes2.getValue(121, contentFrameLayout2.mFixedHeightMinor);
                    }
                    obtainStyledAttributes2.recycle();
                    contentFrameLayout2.requestLayout();
                    this.mSubDecorInstalled = true;
                    PanelFeatureState panelState = getPanelState(0);
                    if (!this.mDestroyed && panelState.menu == null) {
                        this.mInvalidatePanelMenuFeatures |= 4096;
                        if (!this.mInvalidatePanelMenuPosted) {
                            ViewCompat.Api16Impl.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
                            this.mInvalidatePanelMenuPosted = true;
                            return;
                        }
                        return;
                    }
                    return;
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("AppCompat does not support the current theme features: { windowActionBar: ");
                m.append(this.mHasActionBar);
                m.append(", windowActionBarOverlay: ");
                m.append(this.mOverlayActionBar);
                m.append(", android:windowIsFloating: ");
                m.append(this.mIsFloating);
                m.append(", windowActionModeOverlay: ");
                m.append(this.mOverlayActionMode);
                m.append(", windowNoTitle: ");
                m.append(this.mWindowNoTitle);
                m.append(" }");
                throw new IllegalArgumentException(m.toString());
            }
            obtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
    }

    public final void ensureWindow() {
        if (this.mWindow == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                attachToWindow(((Activity) obj).getWindow());
            }
        }
        if (this.mWindow == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    public final AutoNightModeManager getAutoTimeNightModeManager(Context context) {
        if (this.mAutoTimeNightModeManager == null) {
            if (TwilightManager.sInstance == null) {
                Context applicationContext = context.getApplicationContext();
                TwilightManager.sInstance = new TwilightManager(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
            }
            this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.sInstance);
        }
        return this.mAutoTimeNightModeManager;
    }

    public final MenuInflater getMenuInflater() {
        Context context;
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            if (windowDecorActionBar != null) {
                context = windowDecorActionBar.getThemedContext();
            } else {
                context = this.mContext;
            }
            this.mMenuInflater = new SupportMenuInflater(context);
        }
        return this.mMenuInflater;
    }

    public final PanelFeatureState getPanelState(int i) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[(i + 1)];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.mPanels = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
        if (panelFeatureState != null) {
            return panelFeatureState;
        }
        PanelFeatureState panelFeatureState2 = new PanelFeatureState(i);
        panelFeatureStateArr[i] = panelFeatureState2;
        return panelFeatureState2;
    }

    public final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    public final void installViewFactory() {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        if (from.getFactory() == null) {
            from.setFactory2(this);
        } else if (!(from.getFactory2() instanceof AppCompatDelegateImpl)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public final int mapNightMode(Context context, int i) {
        if (i == -100) {
            return -1;
        }
        if (i != -1) {
            if (i != 0) {
                if (!(i == 1 || i == 2)) {
                    if (i == 3) {
                        if (this.mAutoBatteryNightModeManager == null) {
                            this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context);
                        }
                        return this.mAutoBatteryNightModeManager.getApplyableNightMode();
                    }
                    throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
                }
            } else if (((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() == 0) {
                return -1;
            } else {
                return getAutoTimeNightModeManager(context).getApplyableNightMode();
            }
        }
        return i;
    }

    public final void onConfigurationChanged() {
        if (this.mHasActionBar && this.mSubDecorInstalled) {
            initWindowDecorActionBar();
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            if (windowDecorActionBar != null) {
                windowDecorActionBar.setHasEmbeddedTabs(windowDecorActionBar.mContext.getResources().getBoolean(C1777R.bool.abc_action_bar_embed_tabs));
            }
        }
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        Context context = this.mContext;
        Objects.requireNonNull(appCompatDrawableManager);
        synchronized (appCompatDrawableManager) {
            ResourceManagerInternal resourceManagerInternal = appCompatDrawableManager.mResourceManager;
            Objects.requireNonNull(resourceManagerInternal);
            synchronized (resourceManagerInternal) {
                LongSparseArray longSparseArray = resourceManagerInternal.mDrawableCaches.get(context);
                if (longSparseArray != null) {
                    longSparseArray.clear();
                }
            }
        }
        this.mEffectiveConfiguration = new Configuration(this.mContext.getResources().getConfiguration());
        applyDayNight(false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:29:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onDestroy() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mHost
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L_0x0011
            java.lang.Object r0 = androidx.appcompat.app.AppCompatDelegate.sActivityDelegatesLock
            monitor-enter(r0)
            androidx.appcompat.app.AppCompatDelegate.removeDelegateFromActives(r3)     // Catch:{ all -> 0x000e }
            monitor-exit(r0)     // Catch:{ all -> 0x000e }
            goto L_0x0011
        L_0x000e:
            r3 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x000e }
            throw r3
        L_0x0011:
            boolean r0 = r3.mInvalidatePanelMenuPosted
            if (r0 == 0) goto L_0x0020
            android.view.Window r0 = r3.mWindow
            android.view.View r0 = r0.getDecorView()
            androidx.appcompat.app.AppCompatDelegateImpl$2 r1 = r3.mInvalidatePanelMenuRunnable
            r0.removeCallbacks(r1)
        L_0x0020:
            r0 = 1
            r3.mDestroyed = r0
            int r0 = r3.mLocalNightMode
            r1 = -100
            if (r0 == r1) goto L_0x004d
            java.lang.Object r0 = r3.mHost
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L_0x004d
            android.app.Activity r0 = (android.app.Activity) r0
            boolean r0 = r0.isChangingConfigurations()
            if (r0 == 0) goto L_0x004d
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = sLocalNightModes
            java.lang.Object r1 = r3.mHost
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            int r2 = r3.mLocalNightMode
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            goto L_0x005c
        L_0x004d:
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = sLocalNightModes
            java.lang.Object r1 = r3.mHost
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            r0.remove(r1)
        L_0x005c:
            androidx.appcompat.app.AppCompatDelegateImpl$AutoTimeNightModeManager r0 = r3.mAutoTimeNightModeManager
            if (r0 == 0) goto L_0x0063
            r0.cleanup()
        L_0x0063:
            androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager r3 = r3.mAutoBatteryNightModeManager
            if (r3 == 0) goto L_0x006a
            r3.cleanup()
        L_0x006a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.onDestroy():void");
    }

    public final void onMenuModeChange(MenuBuilder menuBuilder) {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.mContext).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState panelState = getPanelState(0);
            panelState.refreshDecorView = true;
            closePanel(panelState, false);
            openPanel(panelState, (KeyEvent) null);
            return;
        }
        Window.Callback windowCallback = getWindowCallback();
        if (this.mDecorContentParent.isOverflowMenuShowing()) {
            this.mDecorContentParent.hideOverflowMenu();
            if (!this.mDestroyed) {
                windowCallback.onPanelClosed(108, getPanelState(0).menu);
            }
        } else if (windowCallback != null && !this.mDestroyed) {
            if (this.mInvalidatePanelMenuPosted && (1 & this.mInvalidatePanelMenuFeatures) != 0) {
                this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                this.mInvalidatePanelMenuRunnable.run();
            }
            PanelFeatureState panelState2 = getPanelState(0);
            MenuBuilder menuBuilder2 = panelState2.menu;
            if (menuBuilder2 != null && !panelState2.refreshMenuContent && windowCallback.onPreparePanel(0, panelState2.createdPanelView, menuBuilder2)) {
                windowCallback.onMenuOpened(108, panelState2.menu);
                this.mDecorContentParent.showOverflowMenu();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:73:0x015b, code lost:
        if (r13 != null) goto L_0x015d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0182, code lost:
        if (r13.mAdapter.getCount() > 0) goto L_0x0184;
     */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0162  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x018a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void openPanel(androidx.appcompat.app.AppCompatDelegateImpl.PanelFeatureState r14, android.view.KeyEvent r15) {
        /*
            r13 = this;
            boolean r0 = r14.isOpen
            if (r0 != 0) goto L_0x01e6
            boolean r0 = r13.mDestroyed
            if (r0 == 0) goto L_0x000a
            goto L_0x01e6
        L_0x000a:
            int r0 = r14.featureId
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x0027
            android.content.Context r0 = r13.mContext
            android.content.res.Resources r0 = r0.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.screenLayout
            r0 = r0 & 15
            r3 = 4
            if (r0 != r3) goto L_0x0023
            r0 = r2
            goto L_0x0024
        L_0x0023:
            r0 = r1
        L_0x0024:
            if (r0 == 0) goto L_0x0027
            return
        L_0x0027:
            android.view.Window$Callback r0 = r13.getWindowCallback()
            if (r0 == 0) goto L_0x003b
            int r3 = r14.featureId
            androidx.appcompat.view.menu.MenuBuilder r4 = r14.menu
            boolean r0 = r0.onMenuOpened(r3, r4)
            if (r0 != 0) goto L_0x003b
            r13.closePanel(r14, r2)
            return
        L_0x003b:
            android.content.Context r0 = r13.mContext
            java.lang.String r3 = "window"
            java.lang.Object r0 = r0.getSystemService(r3)
            android.view.WindowManager r0 = (android.view.WindowManager) r0
            if (r0 != 0) goto L_0x0049
            return
        L_0x0049:
            boolean r15 = r13.preparePanel(r14, r15)
            if (r15 != 0) goto L_0x0050
            return
        L_0x0050:
            androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView r15 = r14.decorView
            r3 = -1
            r4 = -2
            if (r15 == 0) goto L_0x006c
            boolean r5 = r14.refreshDecorView
            if (r5 == 0) goto L_0x005b
            goto L_0x006c
        L_0x005b:
            android.view.View r13 = r14.createdPanelView
            if (r13 == 0) goto L_0x01c3
            android.view.ViewGroup$LayoutParams r13 = r13.getLayoutParams()
            if (r13 == 0) goto L_0x01c3
            int r13 = r13.width
            if (r13 != r3) goto L_0x01c3
            r6 = r3
            goto L_0x01c4
        L_0x006c:
            if (r15 != 0) goto L_0x00e7
            r13.initWindowDecorActionBar()
            androidx.appcompat.app.WindowDecorActionBar r15 = r13.mActionBar
            if (r15 == 0) goto L_0x007a
            android.content.Context r15 = r15.getThemedContext()
            goto L_0x007b
        L_0x007a:
            r15 = 0
        L_0x007b:
            if (r15 != 0) goto L_0x007f
            android.content.Context r15 = r13.mContext
        L_0x007f:
            android.util.TypedValue r3 = new android.util.TypedValue
            r3.<init>()
            android.content.res.Resources r5 = r15.getResources()
            android.content.res.Resources$Theme r5 = r5.newTheme()
            android.content.res.Resources$Theme r6 = r15.getTheme()
            r5.setTo(r6)
            r6 = 2130968578(0x7f040002, float:1.7545814E38)
            r5.resolveAttribute(r6, r3, r2)
            int r6 = r3.resourceId
            if (r6 == 0) goto L_0x00a0
            r5.applyStyle(r6, r2)
        L_0x00a0:
            r6 = 2130969560(0x7f0403d8, float:1.7547805E38)
            r5.resolveAttribute(r6, r3, r2)
            int r3 = r3.resourceId
            if (r3 == 0) goto L_0x00ae
            r5.applyStyle(r3, r2)
            goto L_0x00b4
        L_0x00ae:
            r3 = 2132018047(0x7f14037f, float:1.967439E38)
            r5.applyStyle(r3, r2)
        L_0x00b4:
            androidx.appcompat.view.ContextThemeWrapper r3 = new androidx.appcompat.view.ContextThemeWrapper
            r3.<init>(r15, r1)
            android.content.res.Resources$Theme r15 = r3.getTheme()
            r15.setTo(r5)
            r14.listPresenterContext = r3
            int[] r15 = androidx.appcompat.R$styleable.AppCompatTheme
            android.content.res.TypedArray r15 = r3.obtainStyledAttributes(r15)
            r3 = 86
            int r3 = r15.getResourceId(r3, r1)
            r14.background = r3
            int r3 = r15.getResourceId(r2, r1)
            r14.windowAnimations = r3
            r15.recycle()
            androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView r15 = new androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView
            androidx.appcompat.view.ContextThemeWrapper r3 = r14.listPresenterContext
            r15.<init>(r3)
            r14.decorView = r15
            r15 = 81
            r14.gravity = r15
            goto L_0x00f6
        L_0x00e7:
            boolean r3 = r14.refreshDecorView
            if (r3 == 0) goto L_0x00f6
            int r15 = r15.getChildCount()
            if (r15 <= 0) goto L_0x00f6
            androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView r15 = r14.decorView
            r15.removeAllViews()
        L_0x00f6:
            android.view.View r15 = r14.createdPanelView
            if (r15 == 0) goto L_0x00fd
            r14.shownPanelView = r15
            goto L_0x015d
        L_0x00fd:
            androidx.appcompat.view.menu.MenuBuilder r15 = r14.menu
            if (r15 != 0) goto L_0x0102
            goto L_0x015f
        L_0x0102:
            androidx.appcompat.app.AppCompatDelegateImpl$PanelMenuPresenterCallback r15 = r13.mPanelMenuPresenterCallback
            if (r15 != 0) goto L_0x010d
            androidx.appcompat.app.AppCompatDelegateImpl$PanelMenuPresenterCallback r15 = new androidx.appcompat.app.AppCompatDelegateImpl$PanelMenuPresenterCallback
            r15.<init>()
            r13.mPanelMenuPresenterCallback = r15
        L_0x010d:
            androidx.appcompat.app.AppCompatDelegateImpl$PanelMenuPresenterCallback r13 = r13.mPanelMenuPresenterCallback
            androidx.appcompat.view.menu.ListMenuPresenter r15 = r14.listMenuPresenter
            if (r15 != 0) goto L_0x0128
            androidx.appcompat.view.menu.ListMenuPresenter r15 = new androidx.appcompat.view.menu.ListMenuPresenter
            androidx.appcompat.view.ContextThemeWrapper r3 = r14.listPresenterContext
            r15.<init>(r3)
            r14.listMenuPresenter = r15
            r15.mCallback = r13
            androidx.appcompat.view.menu.MenuBuilder r13 = r14.menu
            java.util.Objects.requireNonNull(r13)
            android.content.Context r3 = r13.mContext
            r13.addMenuPresenter(r15, r3)
        L_0x0128:
            androidx.appcompat.view.menu.ListMenuPresenter r13 = r14.listMenuPresenter
            androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView r15 = r14.decorView
            java.util.Objects.requireNonNull(r13)
            androidx.appcompat.view.menu.ExpandedMenuView r3 = r13.mMenuView
            if (r3 != 0) goto L_0x0157
            android.view.LayoutInflater r3 = r13.mInflater
            r5 = 2131623949(0x7f0e000d, float:1.8875064E38)
            android.view.View r15 = r3.inflate(r5, r15, r1)
            androidx.appcompat.view.menu.ExpandedMenuView r15 = (androidx.appcompat.view.menu.ExpandedMenuView) r15
            r13.mMenuView = r15
            androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter r15 = r13.mAdapter
            if (r15 != 0) goto L_0x014b
            androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter r15 = new androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter
            r15.<init>()
            r13.mAdapter = r15
        L_0x014b:
            androidx.appcompat.view.menu.ExpandedMenuView r15 = r13.mMenuView
            androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter r3 = r13.mAdapter
            r15.setAdapter(r3)
            androidx.appcompat.view.menu.ExpandedMenuView r15 = r13.mMenuView
            r15.setOnItemClickListener(r13)
        L_0x0157:
            androidx.appcompat.view.menu.ExpandedMenuView r13 = r13.mMenuView
            r14.shownPanelView = r13
            if (r13 == 0) goto L_0x015f
        L_0x015d:
            r13 = r2
            goto L_0x0160
        L_0x015f:
            r13 = r1
        L_0x0160:
            if (r13 == 0) goto L_0x01e4
            android.view.View r13 = r14.shownPanelView
            if (r13 != 0) goto L_0x0167
            goto L_0x0186
        L_0x0167:
            android.view.View r13 = r14.createdPanelView
            if (r13 == 0) goto L_0x016c
            goto L_0x0184
        L_0x016c:
            androidx.appcompat.view.menu.ListMenuPresenter r13 = r14.listMenuPresenter
            java.util.Objects.requireNonNull(r13)
            androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter r15 = r13.mAdapter
            if (r15 != 0) goto L_0x017c
            androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter r15 = new androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter
            r15.<init>()
            r13.mAdapter = r15
        L_0x017c:
            androidx.appcompat.view.menu.ListMenuPresenter$MenuAdapter r13 = r13.mAdapter
            int r13 = r13.getCount()
            if (r13 <= 0) goto L_0x0186
        L_0x0184:
            r13 = r2
            goto L_0x0187
        L_0x0186:
            r13 = r1
        L_0x0187:
            if (r13 != 0) goto L_0x018a
            goto L_0x01e4
        L_0x018a:
            android.view.View r13 = r14.shownPanelView
            android.view.ViewGroup$LayoutParams r13 = r13.getLayoutParams()
            if (r13 != 0) goto L_0x0197
            android.view.ViewGroup$LayoutParams r13 = new android.view.ViewGroup$LayoutParams
            r13.<init>(r4, r4)
        L_0x0197:
            int r15 = r14.background
            androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView r3 = r14.decorView
            r3.setBackgroundResource(r15)
            android.view.View r15 = r14.shownPanelView
            android.view.ViewParent r15 = r15.getParent()
            boolean r3 = r15 instanceof android.view.ViewGroup
            if (r3 == 0) goto L_0x01af
            android.view.ViewGroup r15 = (android.view.ViewGroup) r15
            android.view.View r3 = r14.shownPanelView
            r15.removeView(r3)
        L_0x01af:
            androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView r15 = r14.decorView
            android.view.View r3 = r14.shownPanelView
            r15.addView(r3, r13)
            android.view.View r13 = r14.shownPanelView
            boolean r13 = r13.hasFocus()
            if (r13 != 0) goto L_0x01c3
            android.view.View r13 = r14.shownPanelView
            r13.requestFocus()
        L_0x01c3:
            r6 = r4
        L_0x01c4:
            r14.isHandled = r1
            android.view.WindowManager$LayoutParams r13 = new android.view.WindowManager$LayoutParams
            r7 = -2
            r8 = 0
            r9 = 0
            r10 = 1002(0x3ea, float:1.404E-42)
            r11 = 8519680(0x820000, float:1.1938615E-38)
            r12 = -3
            r5 = r13
            r5.<init>(r6, r7, r8, r9, r10, r11, r12)
            int r15 = r14.gravity
            r13.gravity = r15
            int r15 = r14.windowAnimations
            r13.windowAnimations = r15
            androidx.appcompat.app.AppCompatDelegateImpl$ListMenuDecorView r15 = r14.decorView
            r0.addView(r15, r13)
            r14.isOpen = r2
            return
        L_0x01e4:
            r14.refreshDecorView = r2
        L_0x01e6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.openPanel(androidx.appcompat.app.AppCompatDelegateImpl$PanelFeatureState, android.view.KeyEvent):void");
    }

    public final boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        boolean z;
        int i;
        boolean z2;
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        Resources.Theme theme;
        DecorContentParent decorContentParent4;
        if (this.mDestroyed) {
            return false;
        }
        if (panelFeatureState.isPrepared) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
        if (!(panelFeatureState2 == null || panelFeatureState2 == panelFeatureState)) {
            closePanel(panelFeatureState2, false);
        }
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null) {
            panelFeatureState.createdPanelView = windowCallback.onCreatePanelView(panelFeatureState.featureId);
        }
        int i2 = panelFeatureState.featureId;
        if (i2 == 0 || i2 == 108) {
            z = true;
        } else {
            z = false;
        }
        if (z && (decorContentParent4 = this.mDecorContentParent) != null) {
            decorContentParent4.setMenuPrepared();
        }
        if (panelFeatureState.createdPanelView == null) {
            MenuBuilder menuBuilder = panelFeatureState.menu;
            if (menuBuilder == null || panelFeatureState.refreshMenuContent) {
                if (menuBuilder == null) {
                    Context context = this.mContext;
                    int i3 = panelFeatureState.featureId;
                    if ((i3 == 0 || i3 == 108) && this.mDecorContentParent != null) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme2 = context.getTheme();
                        theme2.resolveAttribute(C1777R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            theme = context.getResources().newTheme();
                            theme.setTo(theme2);
                            theme.applyStyle(typedValue.resourceId, true);
                            theme.resolveAttribute(C1777R.attr.actionBarWidgetTheme, typedValue, true);
                        } else {
                            theme2.resolveAttribute(C1777R.attr.actionBarWidgetTheme, typedValue, true);
                            theme = null;
                        }
                        if (typedValue.resourceId != 0) {
                            if (theme == null) {
                                theme = context.getResources().newTheme();
                                theme.setTo(theme2);
                            }
                            theme.applyStyle(typedValue.resourceId, true);
                        }
                        if (theme != null) {
                            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
                            contextThemeWrapper.getTheme().setTo(theme);
                            context = contextThemeWrapper;
                        }
                    }
                    MenuBuilder menuBuilder2 = new MenuBuilder(context);
                    menuBuilder2.mCallback = this;
                    MenuBuilder menuBuilder3 = panelFeatureState.menu;
                    if (menuBuilder2 != menuBuilder3) {
                        if (menuBuilder3 != null) {
                            menuBuilder3.removeMenuPresenter(panelFeatureState.listMenuPresenter);
                        }
                        panelFeatureState.menu = menuBuilder2;
                        ListMenuPresenter listMenuPresenter = panelFeatureState.listMenuPresenter;
                        if (listMenuPresenter != null) {
                            menuBuilder2.addMenuPresenter(listMenuPresenter, menuBuilder2.mContext);
                        }
                    }
                    if (panelFeatureState.menu == null) {
                        return false;
                    }
                }
                if (z && (decorContentParent3 = this.mDecorContentParent) != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    decorContentParent3.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                if (!windowCallback.onCreatePanelMenu(panelFeatureState.featureId, panelFeatureState.menu)) {
                    MenuBuilder menuBuilder4 = panelFeatureState.menu;
                    if (menuBuilder4 != null) {
                        if (menuBuilder4 != null) {
                            menuBuilder4.removeMenuPresenter(panelFeatureState.listMenuPresenter);
                        }
                        panelFeatureState.menu = null;
                    }
                    if (z && (decorContentParent2 = this.mDecorContentParent) != null) {
                        decorContentParent2.setMenu((MenuBuilder) null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.refreshMenuContent = false;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            Bundle bundle = panelFeatureState.frozenActionViewState;
            if (bundle != null) {
                panelFeatureState.menu.restoreActionViewStates(bundle);
                panelFeatureState.frozenActionViewState = null;
            }
            if (!windowCallback.onPreparePanel(0, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
                if (z && (decorContentParent = this.mDecorContentParent) != null) {
                    decorContentParent.setMenu((MenuBuilder) null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.startDispatchingItemsChanged();
                return false;
            }
            if (keyEvent != null) {
                i = keyEvent.getDeviceId();
            } else {
                i = -1;
            }
            if (KeyCharacterMap.load(i).getKeyboardType() != 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            panelFeatureState.menu.setQwertyMode(z2);
            panelFeatureState.menu.startDispatchingItemsChanged();
        }
        panelFeatureState.isPrepared = true;
        panelFeatureState.isHandled = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    public final boolean requestWindowFeature(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            i = 108;
        } else if (i == 9) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            i = 109;
        }
        if (this.mWindowNoTitle && i == 108) {
            return false;
        }
        if (this.mHasActionBar && i == 1) {
            this.mHasActionBar = false;
        }
        if (i == 1) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mWindowNoTitle = true;
            return true;
        } else if (i == 2) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureProgress = true;
            return true;
        } else if (i == 5) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mFeatureIndeterminateProgress = true;
            return true;
        } else if (i == 10) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mOverlayActionMode = true;
            return true;
        } else if (i == 108) {
            throwFeatureRequestIfSubDecorInstalled();
            this.mHasActionBar = true;
            return true;
        } else if (i != 109) {
            return this.mWindow.requestFeature(i);
        } else {
            throwFeatureRequestIfSubDecorInstalled();
            this.mOverlayActionBar = true;
            return true;
        }
    }

    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(charSequence);
            return;
        }
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.mDecorToolbar.setWindowTitle(charSequence);
            return;
        }
        TextView textView = this.mTitleView;
        if (textView != null) {
            textView.setText(charSequence);
        }
    }

    public final void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    public AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback, Object obj) {
        SimpleArrayMap<String, Integer> simpleArrayMap;
        Integer orDefault;
        AppCompatActivity appCompatActivity;
        this.mContext = context;
        this.mAppCompatCallback = appCompatCallback;
        this.mHost = obj;
        if (obj instanceof Dialog) {
            while (true) {
                if (context != null) {
                    if (!(context instanceof AppCompatActivity)) {
                        if (!(context instanceof ContextWrapper)) {
                            break;
                        }
                        context = ((ContextWrapper) context).getBaseContext();
                    } else {
                        appCompatActivity = (AppCompatActivity) context;
                        break;
                    }
                } else {
                    break;
                }
            }
            appCompatActivity = null;
            if (appCompatActivity != null) {
                this.mLocalNightMode = appCompatActivity.getDelegate().getLocalNightMode();
            }
        }
        if (this.mLocalNightMode == -100 && (orDefault = simpleArrayMap.getOrDefault(this.mHost.getClass().getName(), null)) != null) {
            this.mLocalNightMode = orDefault.intValue();
            (simpleArrayMap = sLocalNightModes).remove(this.mHost.getClass().getName());
        }
        if (window != null) {
            attachToWindow(window);
        }
        AppCompatDrawableManager.preload();
    }

    public final void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ((ViewGroup) this.mSubDecor.findViewById(16908290)).addView(view, layoutParams);
        AppCompatWindowCallback appCompatWindowCallback = this.mAppCompatWindowCallback;
        Objects.requireNonNull(appCompatWindowCallback);
        appCompatWindowCallback.mWrapped.onContentChanged();
    }

    public final void doInvalidatePanelMenu(int i) {
        PanelFeatureState panelState = getPanelState(i);
        if (panelState.menu != null) {
            Bundle bundle = new Bundle();
            panelState.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelState.frozenActionViewState = bundle;
            }
            panelState.menu.stopDispatchingItemsChanged();
            panelState.menu.clear();
        }
        panelState.refreshMenuContent = true;
        panelState.refreshDecorView = true;
        if ((i == 108 || i == 0) && this.mDecorContentParent != null) {
            PanelFeatureState panelState2 = getPanelState(0);
            panelState2.isPrepared = false;
            preparePanel(panelState2, (KeyEvent) null);
        }
    }

    public final <T extends View> T findViewById(int i) {
        ensureSubDecor();
        return this.mWindow.findViewById(i);
    }

    public final void initWindowDecorActionBar() {
        ensureSubDecor();
        if (this.mHasActionBar && this.mActionBar == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity) this.mHost, this.mOverlayActionBar);
            } else if (obj instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog) this.mHost);
            }
            WindowDecorActionBar windowDecorActionBar = this.mActionBar;
            if (windowDecorActionBar != null) {
                windowDecorActionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
    }

    public final void invalidateOptionsMenu() {
        initWindowDecorActionBar();
        this.mInvalidatePanelMenuFeatures |= 1;
        if (!this.mInvalidatePanelMenuPosted) {
            View decorView = this.mWindow.getDecorView();
            C00292 r2 = this.mInvalidatePanelMenuRunnable;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(decorView, r2);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        int i;
        int i2;
        PanelFeatureState panelFeatureState;
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !this.mDestroyed) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            PanelFeatureState[] panelFeatureStateArr = this.mPanels;
            if (panelFeatureStateArr != null) {
                i2 = panelFeatureStateArr.length;
                i = 0;
            } else {
                i2 = 0;
                i = 0;
            }
            while (true) {
                if (i < i2) {
                    panelFeatureState = panelFeatureStateArr[i];
                    if (panelFeatureState != null && panelFeatureState.menu == rootMenu) {
                        break;
                    }
                    i++;
                } else {
                    panelFeatureState = null;
                    break;
                }
            }
            if (panelFeatureState != null) {
                return windowCallback.onMenuItemSelected(panelFeatureState.featureId, menuItem);
            }
        }
        return false;
    }

    public final void onPostResume() {
        initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.mShowHideAnimationEnabled = true;
        }
    }

    public final void onStop() {
        initWindowDecorActionBar();
        WindowDecorActionBar windowDecorActionBar = this.mActionBar;
        if (windowDecorActionBar != null) {
            windowDecorActionBar.mShowHideAnimationEnabled = false;
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = windowDecorActionBar.mCurrentShowAnim;
            if (viewPropertyAnimatorCompatSet != null) {
                viewPropertyAnimatorCompatSet.cancel();
            }
        }
    }

    public final boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent) {
        MenuBuilder menuBuilder;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.isPrepared || preparePanel(panelFeatureState, keyEvent)) && (menuBuilder = panelFeatureState.menu) != null) {
            return menuBuilder.performShortcut(i, keyEvent, 1);
        }
        return false;
    }

    public final void setContentView(int i) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(i, viewGroup);
        AppCompatWindowCallback appCompatWindowCallback = this.mAppCompatWindowCallback;
        Objects.requireNonNull(appCompatWindowCallback);
        appCompatWindowCallback.mWrapped.onContentChanged();
    }

    public final void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        AppCompatWindowCallback appCompatWindowCallback = this.mAppCompatWindowCallback;
        Objects.requireNonNull(appCompatWindowCallback);
        appCompatWindowCallback.mWrapped.onContentChanged();
    }

    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView((View) null, str, context, attributeSet);
    }

    public final void setTheme(int i) {
        this.mThemeResId = i;
    }

    public final int getLocalNightMode() {
        return this.mLocalNightMode;
    }
}
