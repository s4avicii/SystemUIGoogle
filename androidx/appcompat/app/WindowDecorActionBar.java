package androidx.appcompat.app;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.appcompat.R$styleable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegateImpl;
import androidx.appcompat.app.WindowDecorActionBar;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.ViewPropertyAnimatorCompatSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.ActionBarContainer;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.ActionBarOverlayLayout;
import androidx.appcompat.widget.ActionMenuPresenter;
import androidx.appcompat.widget.DecorToolbar;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ToolbarWidgetWrapper;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorUpdateListener;
import com.airbnb.lottie.C0438L;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.WeakHashMap;

public final class WindowDecorActionBar extends ActionBar implements ActionBarOverlayLayout.ActionBarVisibilityCallback {
    public static final AccelerateInterpolator sHideInterpolator = new AccelerateInterpolator();
    public static final DecelerateInterpolator sShowInterpolator = new DecelerateInterpolator();
    public ActionModeImpl mActionMode;
    public ActionBarContainer mContainerView;
    public boolean mContentAnimations = true;
    public View mContentView;
    public Context mContext;
    public ActionBarContextView mContextView;
    public int mCurWindowVisibility = 0;
    public ViewPropertyAnimatorCompatSet mCurrentShowAnim;
    public DecorToolbar mDecorToolbar;
    public ActionModeImpl mDeferredDestroyActionMode;
    public ActionMode.Callback mDeferredModeDestroyCallback;
    public boolean mDisplayHomeAsUpSet;
    public boolean mHasEmbeddedTabs;
    public boolean mHiddenBySystem;
    public final C00391 mHideListener = new C0438L() {
        public final void onAnimationEnd() {
            View view;
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            if (windowDecorActionBar.mContentAnimations && (view = windowDecorActionBar.mContentView) != null) {
                view.setTranslationY(0.0f);
                WindowDecorActionBar.this.mContainerView.setTranslationY(0.0f);
            }
            WindowDecorActionBar.this.mContainerView.setVisibility(8);
            ActionBarContainer actionBarContainer = WindowDecorActionBar.this.mContainerView;
            Objects.requireNonNull(actionBarContainer);
            actionBarContainer.mIsTransitioning = false;
            actionBarContainer.setDescendantFocusability(262144);
            WindowDecorActionBar windowDecorActionBar2 = WindowDecorActionBar.this;
            windowDecorActionBar2.mCurrentShowAnim = null;
            ActionMode.Callback callback = windowDecorActionBar2.mDeferredModeDestroyCallback;
            if (callback != null) {
                callback.onDestroyActionMode(windowDecorActionBar2.mDeferredDestroyActionMode);
                windowDecorActionBar2.mDeferredDestroyActionMode = null;
                windowDecorActionBar2.mDeferredModeDestroyCallback = null;
            }
            ActionBarOverlayLayout actionBarOverlayLayout = WindowDecorActionBar.this.mOverlayLayout;
            if (actionBarOverlayLayout != null) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api20Impl.requestApplyInsets(actionBarOverlayLayout);
            }
        }
    };
    public boolean mHideOnContentScroll;
    public boolean mLastMenuVisibility;
    public ArrayList<ActionBar.OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<>();
    public boolean mNowShowing = true;
    public ActionBarOverlayLayout mOverlayLayout;
    public boolean mShowHideAnimationEnabled;
    public final C00402 mShowListener = new C0438L() {
        public final void onAnimationEnd() {
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            windowDecorActionBar.mCurrentShowAnim = null;
            windowDecorActionBar.mContainerView.requestLayout();
        }
    };
    public boolean mShowingForMode;
    public Context mThemedContext;
    public final C00413 mUpdateListener = new ViewPropertyAnimatorUpdateListener() {
    };

    public class ActionModeImpl extends ActionMode implements MenuBuilder.Callback {
        public final Context mActionModeContext;
        public ActionMode.Callback mCallback;
        public WeakReference<View> mCustomView;
        public final MenuBuilder mMenu;

        public final void setSubtitle(CharSequence charSequence) {
            ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
            Objects.requireNonNull(actionBarContextView);
            actionBarContextView.mSubtitle = charSequence;
            actionBarContextView.initTitle();
        }

        public final void setTitle(CharSequence charSequence) {
            ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
            Objects.requireNonNull(actionBarContextView);
            actionBarContextView.mTitle = charSequence;
            actionBarContextView.initTitle();
            ViewCompat.setAccessibilityPaneTitle(actionBarContextView, charSequence);
        }

        public ActionModeImpl(Context context, AppCompatDelegateImpl.ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9) {
            this.mActionModeContext = context;
            this.mCallback = actionModeCallbackWrapperV9;
            MenuBuilder menuBuilder = new MenuBuilder(context);
            menuBuilder.mDefaultShowAsAction = 1;
            this.mMenu = menuBuilder;
            menuBuilder.mCallback = this;
        }

        public final void finish() {
            WindowDecorActionBar windowDecorActionBar = WindowDecorActionBar.this;
            if (windowDecorActionBar.mActionMode == this) {
                if (!(!windowDecorActionBar.mHiddenBySystem)) {
                    windowDecorActionBar.mDeferredDestroyActionMode = this;
                    windowDecorActionBar.mDeferredModeDestroyCallback = this.mCallback;
                } else {
                    this.mCallback.onDestroyActionMode(this);
                }
                this.mCallback = null;
                WindowDecorActionBar.this.animateToMode(false);
                ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
                Objects.requireNonNull(actionBarContextView);
                if (actionBarContextView.mClose == null) {
                    actionBarContextView.killMode();
                }
                WindowDecorActionBar windowDecorActionBar2 = WindowDecorActionBar.this;
                ActionBarOverlayLayout actionBarOverlayLayout = windowDecorActionBar2.mOverlayLayout;
                boolean z = windowDecorActionBar2.mHideOnContentScroll;
                Objects.requireNonNull(actionBarOverlayLayout);
                if (z != actionBarOverlayLayout.mHideOnContentScroll) {
                    actionBarOverlayLayout.mHideOnContentScroll = z;
                    if (!z) {
                        actionBarOverlayLayout.haltActionBarHideOffsetAnimations();
                        actionBarOverlayLayout.haltActionBarHideOffsetAnimations();
                        actionBarOverlayLayout.mActionBarTop.setTranslationY((float) (-Math.max(0, Math.min(0, actionBarOverlayLayout.mActionBarTop.getHeight()))));
                    }
                }
                WindowDecorActionBar.this.mActionMode = null;
            }
        }

        public final View getCustomView() {
            WeakReference<View> weakReference = this.mCustomView;
            if (weakReference != null) {
                return weakReference.get();
            }
            return null;
        }

        public final MenuInflater getMenuInflater() {
            return new SupportMenuInflater(this.mActionModeContext);
        }

        public final CharSequence getSubtitle() {
            ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
            Objects.requireNonNull(actionBarContextView);
            return actionBarContextView.mSubtitle;
        }

        public final CharSequence getTitle() {
            ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
            Objects.requireNonNull(actionBarContextView);
            return actionBarContextView.mTitle;
        }

        public final void invalidate() {
            if (WindowDecorActionBar.this.mActionMode == this) {
                this.mMenu.stopDispatchingItemsChanged();
                try {
                    this.mCallback.onPrepareActionMode(this, this.mMenu);
                } finally {
                    this.mMenu.startDispatchingItemsChanged();
                }
            }
        }

        public final boolean isTitleOptional() {
            ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
            Objects.requireNonNull(actionBarContextView);
            return actionBarContextView.mTitleOptional;
        }

        public final boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            ActionMode.Callback callback = this.mCallback;
            if (callback != null) {
                return callback.onActionItemClicked(this, menuItem);
            }
            return false;
        }

        public final void onMenuModeChange(MenuBuilder menuBuilder) {
            if (this.mCallback != null) {
                invalidate();
                ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
                Objects.requireNonNull(actionBarContextView);
                ActionMenuPresenter actionMenuPresenter = actionBarContextView.mActionMenuPresenter;
                if (actionMenuPresenter != null) {
                    actionMenuPresenter.showOverflowMenu();
                }
            }
        }

        public final void setCustomView(View view) {
            WindowDecorActionBar.this.mContextView.setCustomView(view);
            this.mCustomView = new WeakReference<>(view);
        }

        public final void setTitleOptionalHint(boolean z) {
            this.mTitleOptionalHint = z;
            ActionBarContextView actionBarContextView = WindowDecorActionBar.this.mContextView;
            Objects.requireNonNull(actionBarContextView);
            if (z != actionBarContextView.mTitleOptional) {
                actionBarContextView.requestLayout();
            }
            actionBarContextView.mTitleOptional = z;
        }

        public final void setSubtitle(int i) {
            setSubtitle((CharSequence) WindowDecorActionBar.this.mContext.getResources().getString(i));
        }

        public final void setTitle(int i) {
            setTitle((CharSequence) WindowDecorActionBar.this.mContext.getResources().getString(i));
        }

        public final MenuBuilder getMenu() {
            return this.mMenu;
        }
    }

    public WindowDecorActionBar(Activity activity, boolean z) {
        new ArrayList();
        View decorView = activity.getWindow().getDecorView();
        init(decorView);
        if (!z) {
            this.mContentView = decorView.findViewById(16908290);
        }
    }

    public final void animateToMode(boolean z) {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat;
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2;
        long j;
        if (z) {
            if (!this.mShowingForMode) {
                this.mShowingForMode = true;
                updateVisibility(false);
            }
        } else if (this.mShowingForMode) {
            this.mShowingForMode = false;
            updateVisibility(false);
        }
        ActionBarContainer actionBarContainer = this.mContainerView;
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api19Impl.isLaidOut(actionBarContainer)) {
            if (z) {
                viewPropertyAnimatorCompat = this.mDecorToolbar.setupAnimatorToVisibility(4, 100);
                viewPropertyAnimatorCompat2 = this.mContextView.setupAnimatorToVisibility(0, 200);
            } else {
                ViewPropertyAnimatorCompat viewPropertyAnimatorCompat3 = this.mDecorToolbar.setupAnimatorToVisibility(0, 200);
                viewPropertyAnimatorCompat = this.mContextView.setupAnimatorToVisibility(8, 100);
                viewPropertyAnimatorCompat2 = viewPropertyAnimatorCompat3;
            }
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = new ViewPropertyAnimatorCompatSet();
            viewPropertyAnimatorCompatSet.mAnimators.add(viewPropertyAnimatorCompat);
            Objects.requireNonNull(viewPropertyAnimatorCompat);
            View view = viewPropertyAnimatorCompat.mView.get();
            if (view != null) {
                j = view.animate().getDuration();
            } else {
                j = 0;
            }
            Objects.requireNonNull(viewPropertyAnimatorCompat2);
            View view2 = viewPropertyAnimatorCompat2.mView.get();
            if (view2 != null) {
                view2.animate().setStartDelay(j);
            }
            viewPropertyAnimatorCompatSet.mAnimators.add(viewPropertyAnimatorCompat2);
            viewPropertyAnimatorCompatSet.start();
        } else if (z) {
            this.mDecorToolbar.setVisibility(4);
            this.mContextView.setVisibility(0);
        } else {
            this.mDecorToolbar.setVisibility(0);
            this.mContextView.setVisibility(8);
        }
    }

    public final void dispatchMenuVisibilityChanged(boolean z) {
        if (z != this.mLastMenuVisibility) {
            this.mLastMenuVisibility = z;
            int size = this.mMenuVisibilityListeners.size();
            for (int i = 0; i < size; i++) {
                this.mMenuVisibilityListeners.get(i).onMenuVisibilityChanged();
            }
        }
    }

    public final Context getThemedContext() {
        if (this.mThemedContext == null) {
            TypedValue typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(C1777R.attr.actionBarWidgetTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                this.mThemedContext = new ContextThemeWrapper(this.mContext, i);
            } else {
                this.mThemedContext = this.mContext;
            }
        }
        return this.mThemedContext;
    }

    public final void setDefaultDisplayHomeAsUpEnabled(boolean z) {
        int i;
        if (!this.mDisplayHomeAsUpSet) {
            if (z) {
                i = 4;
            } else {
                i = 0;
            }
            int displayOptions = this.mDecorToolbar.getDisplayOptions();
            this.mDisplayHomeAsUpSet = true;
            this.mDecorToolbar.setDisplayOptions((i & 4) | (displayOptions & -5));
        }
    }

    public final void setHasEmbeddedTabs(boolean z) {
        boolean z2;
        boolean z3;
        this.mHasEmbeddedTabs = z;
        if (!z) {
            this.mDecorToolbar.setEmbeddedTabView();
            Objects.requireNonNull(this.mContainerView);
        } else {
            Objects.requireNonNull(this.mContainerView);
            this.mDecorToolbar.setEmbeddedTabView();
        }
        boolean z4 = true;
        if (this.mDecorToolbar.getNavigationMode() == 2) {
            z2 = true;
        } else {
            z2 = false;
        }
        DecorToolbar decorToolbar = this.mDecorToolbar;
        if (this.mHasEmbeddedTabs || !z2) {
            z3 = false;
        } else {
            z3 = true;
        }
        decorToolbar.setCollapsible(z3);
        ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
        if (this.mHasEmbeddedTabs || !z2) {
            z4 = false;
        }
        Objects.requireNonNull(actionBarOverlayLayout);
        actionBarOverlayLayout.mHasNonEmbeddedTabs = z4;
    }

    public final void updateVisibility(boolean z) {
        boolean z2;
        View view;
        View view2;
        View view3;
        boolean z3 = this.mHiddenBySystem;
        if (!this.mShowingForMode && z3) {
            z2 = false;
        } else {
            z2 = true;
        }
        ViewPropertyAnimatorCompat.C01232 r7 = null;
        if (z2) {
            if (!this.mNowShowing) {
                this.mNowShowing = true;
                ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet = this.mCurrentShowAnim;
                if (viewPropertyAnimatorCompatSet != null) {
                    viewPropertyAnimatorCompatSet.cancel();
                }
                this.mContainerView.setVisibility(0);
                if (this.mCurWindowVisibility != 0 || (!this.mShowHideAnimationEnabled && !z)) {
                    this.mContainerView.setAlpha(1.0f);
                    this.mContainerView.setTranslationY(0.0f);
                    if (this.mContentAnimations && (view2 = this.mContentView) != null) {
                        view2.setTranslationY(0.0f);
                    }
                    this.mShowListener.onAnimationEnd();
                } else {
                    this.mContainerView.setTranslationY(0.0f);
                    float f = (float) (-this.mContainerView.getHeight());
                    if (z) {
                        int[] iArr = {0, 0};
                        this.mContainerView.getLocationInWindow(iArr);
                        f -= (float) iArr[1];
                    }
                    this.mContainerView.setTranslationY(f);
                    ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet2 = new ViewPropertyAnimatorCompatSet();
                    ViewPropertyAnimatorCompat animate = ViewCompat.animate(this.mContainerView);
                    animate.translationY(0.0f);
                    C00413 r2 = this.mUpdateListener;
                    View view4 = animate.mView.get();
                    if (view4 != null) {
                        if (r2 != null) {
                            r7 = new ValueAnimator.AnimatorUpdateListener(view4) {
                                public final /* synthetic */ View val$view;

                                {
                                    this.val$view = r2;
                                }

                                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    WindowDecorActionBar.C00413 r0 = (WindowDecorActionBar.C00413) ViewPropertyAnimatorUpdateListener.this;
                                    Objects.requireNonNull(r0);
                                    ((View) WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
                                }
                            };
                        }
                        view4.animate().setUpdateListener(r7);
                    }
                    if (!viewPropertyAnimatorCompatSet2.mIsStarted) {
                        viewPropertyAnimatorCompatSet2.mAnimators.add(animate);
                    }
                    if (this.mContentAnimations && (view3 = this.mContentView) != null) {
                        view3.setTranslationY(f);
                        ViewPropertyAnimatorCompat animate2 = ViewCompat.animate(this.mContentView);
                        animate2.translationY(0.0f);
                        if (!viewPropertyAnimatorCompatSet2.mIsStarted) {
                            viewPropertyAnimatorCompatSet2.mAnimators.add(animate2);
                        }
                    }
                    DecelerateInterpolator decelerateInterpolator = sShowInterpolator;
                    boolean z4 = viewPropertyAnimatorCompatSet2.mIsStarted;
                    if (!z4) {
                        viewPropertyAnimatorCompatSet2.mInterpolator = decelerateInterpolator;
                    }
                    if (!z4) {
                        viewPropertyAnimatorCompatSet2.mDuration = 250;
                    }
                    C00402 r0 = this.mShowListener;
                    if (!z4) {
                        viewPropertyAnimatorCompatSet2.mListener = r0;
                    }
                    this.mCurrentShowAnim = viewPropertyAnimatorCompatSet2;
                    viewPropertyAnimatorCompatSet2.start();
                }
                ActionBarOverlayLayout actionBarOverlayLayout = this.mOverlayLayout;
                if (actionBarOverlayLayout != null) {
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api20Impl.requestApplyInsets(actionBarOverlayLayout);
                }
            }
        } else if (this.mNowShowing) {
            this.mNowShowing = false;
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet3 = this.mCurrentShowAnim;
            if (viewPropertyAnimatorCompatSet3 != null) {
                viewPropertyAnimatorCompatSet3.cancel();
            }
            if (this.mCurWindowVisibility != 0 || (!this.mShowHideAnimationEnabled && !z)) {
                this.mHideListener.onAnimationEnd();
                return;
            }
            this.mContainerView.setAlpha(1.0f);
            ActionBarContainer actionBarContainer = this.mContainerView;
            Objects.requireNonNull(actionBarContainer);
            actionBarContainer.mIsTransitioning = true;
            actionBarContainer.setDescendantFocusability(393216);
            ViewPropertyAnimatorCompatSet viewPropertyAnimatorCompatSet4 = new ViewPropertyAnimatorCompatSet();
            float f2 = (float) (-this.mContainerView.getHeight());
            if (z) {
                int[] iArr2 = {0, 0};
                this.mContainerView.getLocationInWindow(iArr2);
                f2 -= (float) iArr2[1];
            }
            ViewPropertyAnimatorCompat animate3 = ViewCompat.animate(this.mContainerView);
            animate3.translationY(f2);
            C00413 r1 = this.mUpdateListener;
            View view5 = animate3.mView.get();
            if (view5 != null) {
                if (r1 != null) {
                    r7 = new ValueAnimator.AnimatorUpdateListener(view5) {
                        public final /* synthetic */ View val$view;

                        {
                            this.val$view = r2;
                        }

                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            WindowDecorActionBar.C00413 r0 = (WindowDecorActionBar.C00413) ViewPropertyAnimatorUpdateListener.this;
                            Objects.requireNonNull(r0);
                            ((View) WindowDecorActionBar.this.mContainerView.getParent()).invalidate();
                        }
                    };
                }
                view5.animate().setUpdateListener(r7);
            }
            if (!viewPropertyAnimatorCompatSet4.mIsStarted) {
                viewPropertyAnimatorCompatSet4.mAnimators.add(animate3);
            }
            if (this.mContentAnimations && (view = this.mContentView) != null) {
                ViewPropertyAnimatorCompat animate4 = ViewCompat.animate(view);
                animate4.translationY(f2);
                if (!viewPropertyAnimatorCompatSet4.mIsStarted) {
                    viewPropertyAnimatorCompatSet4.mAnimators.add(animate4);
                }
            }
            AccelerateInterpolator accelerateInterpolator = sHideInterpolator;
            boolean z5 = viewPropertyAnimatorCompatSet4.mIsStarted;
            if (!z5) {
                viewPropertyAnimatorCompatSet4.mInterpolator = accelerateInterpolator;
            }
            if (!z5) {
                viewPropertyAnimatorCompatSet4.mDuration = 250;
            }
            C00391 r9 = this.mHideListener;
            if (!z5) {
                viewPropertyAnimatorCompatSet4.mListener = r9;
            }
            this.mCurrentShowAnim = viewPropertyAnimatorCompatSet4;
            viewPropertyAnimatorCompatSet4.start();
        }
    }

    public final void init(View view) {
        DecorToolbar decorToolbar;
        boolean z;
        String str;
        ActionBarOverlayLayout actionBarOverlayLayout = (ActionBarOverlayLayout) view.findViewById(C1777R.C1779id.decor_content_parent);
        this.mOverlayLayout = actionBarOverlayLayout;
        if (actionBarOverlayLayout != null) {
            actionBarOverlayLayout.mActionBarVisibilityCallback = this;
            if (actionBarOverlayLayout.getWindowToken() != null) {
                ActionBarOverlayLayout.ActionBarVisibilityCallback actionBarVisibilityCallback = actionBarOverlayLayout.mActionBarVisibilityCallback;
                int i = actionBarOverlayLayout.mWindowVisibility;
                WindowDecorActionBar windowDecorActionBar = (WindowDecorActionBar) actionBarVisibilityCallback;
                Objects.requireNonNull(windowDecorActionBar);
                windowDecorActionBar.mCurWindowVisibility = i;
                int i2 = actionBarOverlayLayout.mLastSystemUiVisibility;
                if (i2 != 0) {
                    actionBarOverlayLayout.onWindowSystemUiVisibilityChanged(i2);
                    WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api20Impl.requestApplyInsets(actionBarOverlayLayout);
                }
            }
        }
        View findViewById = view.findViewById(C1777R.C1779id.action_bar);
        if (findViewById instanceof DecorToolbar) {
            decorToolbar = (DecorToolbar) findViewById;
        } else if (findViewById instanceof Toolbar) {
            Toolbar toolbar = (Toolbar) findViewById;
            Objects.requireNonNull(toolbar);
            if (toolbar.mWrapper == null) {
                toolbar.mWrapper = new ToolbarWidgetWrapper(toolbar);
            }
            decorToolbar = toolbar.mWrapper;
        } else {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Can't make a decor toolbar out of ");
            if (findViewById != null) {
                str = findViewById.getClass().getSimpleName();
            } else {
                str = "null";
            }
            m.append(str);
            throw new IllegalStateException(m.toString());
        }
        this.mDecorToolbar = decorToolbar;
        this.mContextView = (ActionBarContextView) view.findViewById(C1777R.C1779id.action_context_bar);
        ActionBarContainer actionBarContainer = (ActionBarContainer) view.findViewById(C1777R.C1779id.action_bar_container);
        this.mContainerView = actionBarContainer;
        DecorToolbar decorToolbar2 = this.mDecorToolbar;
        if (decorToolbar2 == null || this.mContextView == null || actionBarContainer == null) {
            throw new IllegalStateException("WindowDecorActionBar can only be used with a compatible window decor layout");
        }
        this.mContext = decorToolbar2.getContext();
        if ((this.mDecorToolbar.getDisplayOptions() & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            this.mDisplayHomeAsUpSet = true;
        }
        Context context = this.mContext;
        int i3 = context.getApplicationInfo().targetSdkVersion;
        this.mDecorToolbar.setHomeButtonEnabled();
        setHasEmbeddedTabs(context.getResources().getBoolean(C1777R.bool.abc_action_bar_embed_tabs));
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes((AttributeSet) null, R$styleable.ActionBar, C1777R.attr.actionBarStyle, 0);
        if (obtainStyledAttributes.getBoolean(14, false)) {
            ActionBarOverlayLayout actionBarOverlayLayout2 = this.mOverlayLayout;
            Objects.requireNonNull(actionBarOverlayLayout2);
            if (actionBarOverlayLayout2.mOverlayMode) {
                this.mHideOnContentScroll = true;
                ActionBarOverlayLayout actionBarOverlayLayout3 = this.mOverlayLayout;
                Objects.requireNonNull(actionBarOverlayLayout3);
                if (true != actionBarOverlayLayout3.mHideOnContentScroll) {
                    actionBarOverlayLayout3.mHideOnContentScroll = true;
                }
            } else {
                throw new IllegalStateException("Action bar must be in overlay mode (Window.FEATURE_OVERLAY_ACTION_BAR) to enable hide on content scroll");
            }
        }
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(12, 0);
        if (dimensionPixelSize != 0) {
            ActionBarContainer actionBarContainer2 = this.mContainerView;
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api21Impl.setElevation(actionBarContainer2, (float) dimensionPixelSize);
        }
        obtainStyledAttributes.recycle();
    }

    public WindowDecorActionBar(Dialog dialog) {
        new ArrayList();
        init(dialog.getWindow().getDecorView());
    }
}
