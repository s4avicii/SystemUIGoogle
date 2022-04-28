package com.android.systemui.navigationbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import androidx.leanback.R$color;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.KeyButtonDrawable;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.buttons.ReverseLinearLayout;
import com.android.systemui.recents.OverviewProxyService;
import java.util.Objects;

public class NavigationBarInflaterView extends FrameLayout implements NavigationModeController.ModeChangedListener {
    public boolean mAlternativeOrder;
    @VisibleForTesting
    public SparseArray<ButtonDispatcher> mButtonDispatchers;
    public String mCurrentLayout;
    public FrameLayout mHorizontal;
    public boolean mIsVertical;
    public LayoutInflater mLandscapeInflater;
    public View mLastLandscape;
    public View mLastPortrait;
    public LayoutInflater mLayoutInflater;
    public int mNavBarMode = 0;
    public OverviewProxyService mOverviewProxyService;
    public FrameLayout mVertical;

    public static void addAll(ButtonDispatcher buttonDispatcher, ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            int id = viewGroup.getChildAt(i).getId();
            Objects.requireNonNull(buttonDispatcher);
            if (id == buttonDispatcher.mId) {
                buttonDispatcher.addView(viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                addAll(buttonDispatcher, (ViewGroup) viewGroup.getChildAt(i));
            }
        }
    }

    public final void inflateButtons(String[] strArr, ViewGroup viewGroup, boolean z, boolean z2) {
        LayoutInflater layoutInflater;
        View view;
        View view2;
        int i;
        String str;
        for (String str2 : strArr) {
            if (z) {
                layoutInflater = this.mLandscapeInflater;
            } else {
                layoutInflater = this.mLayoutInflater;
            }
            String extractButton = extractButton(str2);
            if ("left".equals(extractButton)) {
                extractButton = extractButton("space");
            } else if ("right".equals(extractButton)) {
                extractButton = extractButton("menu_ime");
            }
            String str3 = null;
            if ("home".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.home, viewGroup, false);
            } else if ("back".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.back, viewGroup, false);
            } else if ("recent".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.recent_apps, viewGroup, false);
            } else if ("menu_ime".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.menu_ime, viewGroup, false);
            } else if ("space".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.nav_key_space, viewGroup, false);
            } else if ("clipboard".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.clipboard, viewGroup, false);
            } else if ("contextual".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.contextual, viewGroup, false);
            } else if ("home_handle".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.home_handle, viewGroup, false);
            } else if ("ime_switcher".equals(extractButton)) {
                view = layoutInflater.inflate(C1777R.layout.ime_switcher, viewGroup, false);
            } else if (extractButton.startsWith("key")) {
                if (!extractButton.contains(":")) {
                    str = null;
                } else {
                    str = extractButton.substring(extractButton.indexOf(":") + 1, extractButton.indexOf(")"));
                }
                int extractKeycode = extractKeycode(extractButton);
                view = layoutInflater.inflate(C1777R.layout.custom_key, viewGroup, false);
                KeyButtonView keyButtonView = (KeyButtonView) view;
                Objects.requireNonNull(keyButtonView);
                keyButtonView.mCode = extractKeycode;
                if (str != null) {
                    if (str.contains(":")) {
                        new AsyncTask<Icon, Void, Drawable>() {
                            public final Object doInBackground(Object[] objArr) {
                                return ((Icon[]) objArr)[0].loadDrawable(KeyButtonView.this.mContext);
                            }

                            public final void onPostExecute(Object obj) {
                                KeyButtonView.this.setImageDrawable((Drawable) obj);
                            }
                        }.execute(new Icon[]{Icon.createWithContentUri(str)});
                    } else if (str.contains("/")) {
                        int indexOf = str.indexOf(47);
                        new AsyncTask<Icon, Void, Drawable>() {
                            public final Object doInBackground(Object[] objArr) {
                                return ((Icon[]) objArr)[0].loadDrawable(KeyButtonView.this.mContext);
                            }

                            public final void onPostExecute(Object obj) {
                                KeyButtonView.this.setImageDrawable((Drawable) obj);
                            }
                        }.execute(new Icon[]{Icon.createWithResource(str.substring(0, indexOf), Integer.parseInt(str.substring(indexOf + 1)))});
                    }
                }
            } else {
                view = null;
            }
            if (view != null) {
                if (str2.contains("[")) {
                    str3 = str2.substring(str2.indexOf("[") + 1, str2.indexOf("]"));
                }
                if (str3 != null) {
                    if (str3.contains("W") || str3.contains("A")) {
                        ReverseLinearLayout.ReverseRelativeLayout reverseRelativeLayout = new ReverseLinearLayout.ReverseRelativeLayout(this.mContext);
                        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(view.getLayoutParams());
                        if (z) {
                            if (z2) {
                                i = 48;
                            } else {
                                i = 80;
                            }
                        } else if (z2) {
                            i = 8388611;
                        } else {
                            i = 8388613;
                        }
                        if (str3.endsWith("WC")) {
                            i = 17;
                        } else if (str3.endsWith("C")) {
                            i = 16;
                        }
                        reverseRelativeLayout.mDefaultGravity = i;
                        reverseRelativeLayout.setGravity(i);
                        reverseRelativeLayout.addView(view, layoutParams);
                        if (str3.contains("W")) {
                            reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams(0, -1, Float.parseFloat(str3.substring(0, str3.indexOf("W")))));
                        } else {
                            reverseRelativeLayout.setLayoutParams(new LinearLayout.LayoutParams((int) (Float.parseFloat(str3.substring(0, str3.indexOf("A"))) * this.mContext.getResources().getDisplayMetrics().density), -1));
                        }
                        reverseRelativeLayout.setClipChildren(false);
                        reverseRelativeLayout.setClipToPadding(false);
                        view = reverseRelativeLayout;
                    } else {
                        float parseFloat = Float.parseFloat(str3);
                        ViewGroup.LayoutParams layoutParams2 = view.getLayoutParams();
                        layoutParams2.width = (int) (((float) layoutParams2.width) * parseFloat);
                    }
                }
                viewGroup.addView(view);
                addToDispatchers(view);
                if (z) {
                    view2 = this.mLastLandscape;
                } else {
                    view2 = this.mLastPortrait;
                }
                if (view instanceof ReverseLinearLayout.ReverseRelativeLayout) {
                    view = ((ReverseLinearLayout.ReverseRelativeLayout) view).getChildAt(0);
                }
                if (view2 != null) {
                    view.setAccessibilityTraversalAfter(view2.getId());
                }
                if (z) {
                    this.mLastLandscape = view;
                } else {
                    this.mLastPortrait = view;
                }
            }
        }
    }

    public final void updateAlternativeOrder() {
        updateAlternativeOrder(this.mHorizontal.findViewById(C1777R.C1779id.ends_group));
        updateAlternativeOrder(this.mHorizontal.findViewById(C1777R.C1779id.center_group));
        updateAlternativeOrder(this.mVertical.findViewById(C1777R.C1779id.ends_group));
        updateAlternativeOrder(this.mVertical.findViewById(C1777R.C1779id.center_group));
    }

    public static String extractButton(String str) {
        if (!str.contains("[")) {
            return str;
        }
        return str.substring(0, str.indexOf("["));
    }

    public static int extractKeycode(String str) {
        if (!str.contains("(")) {
            return 1;
        }
        return Integer.parseInt(str.substring(str.indexOf("(") + 1, str.indexOf(":")));
    }

    public final void addToDispatchers(View view) {
        SparseArray<ButtonDispatcher> sparseArray = this.mButtonDispatchers;
        if (sparseArray != null) {
            int indexOfKey = sparseArray.indexOfKey(view.getId());
            if (indexOfKey >= 0) {
                this.mButtonDispatchers.valueAt(indexOfKey).addView(view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int childCount = viewGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    addToDispatchers(viewGroup.getChildAt(i));
                }
            }
        }
    }

    public final void clearViews() {
        if (this.mButtonDispatchers != null) {
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                ButtonDispatcher valueAt = this.mButtonDispatchers.valueAt(i);
                Objects.requireNonNull(valueAt);
                valueAt.mViews.clear();
            }
        }
        ViewGroup viewGroup = (ViewGroup) this.mHorizontal.findViewById(C1777R.C1779id.nav_buttons);
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            ((ViewGroup) viewGroup.getChildAt(i2)).removeAllViews();
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mVertical.findViewById(C1777R.C1779id.nav_buttons);
        for (int i3 = 0; i3 < viewGroup2.getChildCount(); i3++) {
            ((ViewGroup) viewGroup2.getChildAt(i3)).removeAllViews();
        }
    }

    @VisibleForTesting
    public void createInflaters() {
        this.mLayoutInflater = LayoutInflater.from(this.mContext);
        Configuration configuration = new Configuration();
        configuration.setTo(this.mContext.getResources().getConfiguration());
        configuration.orientation = 2;
        this.mLandscapeInflater = LayoutInflater.from(this.mContext.createConfigurationContext(configuration));
    }

    public final String getDefaultLayout() {
        int i;
        if (R$color.isGesturalMode(this.mNavBarMode)) {
            i = C1777R.string.config_navBarLayoutHandle;
        } else if (this.mOverviewProxyService.shouldShowSwipeUpUI()) {
            i = C1777R.string.config_navBarLayoutQuickstep;
        } else {
            i = C1777R.string.config_navBarLayout;
        }
        return getContext().getString(i);
    }

    public final void inflateLayout(String str) {
        this.mCurrentLayout = str;
        if (str == null) {
            str = getDefaultLayout();
        }
        String[] split = str.split(";", 3);
        if (split.length != 3) {
            Log.d("NavBarInflater", "Invalid layout.");
            split = getDefaultLayout().split(";", 3);
        }
        String[] split2 = split[0].split(",");
        String[] split3 = split[1].split(",");
        String[] split4 = split[2].split(",");
        inflateButtons(split2, (ViewGroup) this.mHorizontal.findViewById(C1777R.C1779id.ends_group), false, true);
        inflateButtons(split2, (ViewGroup) this.mVertical.findViewById(C1777R.C1779id.ends_group), true, true);
        inflateButtons(split3, (ViewGroup) this.mHorizontal.findViewById(C1777R.C1779id.center_group), false, false);
        inflateButtons(split3, (ViewGroup) this.mVertical.findViewById(C1777R.C1779id.center_group), true, false);
        ((LinearLayout) this.mHorizontal.findViewById(C1777R.C1779id.ends_group)).addView(new Space(this.mContext), new LinearLayout.LayoutParams(0, 0, 1.0f));
        ((LinearLayout) this.mVertical.findViewById(C1777R.C1779id.ends_group)).addView(new Space(this.mContext), new LinearLayout.LayoutParams(0, 0, 1.0f));
        inflateButtons(split4, (ViewGroup) this.mHorizontal.findViewById(C1777R.C1779id.ends_group), false, false);
        inflateButtons(split4, (ViewGroup) this.mVertical.findViewById(C1777R.C1779id.ends_group), true, false);
        updateButtonDispatchersCurrentView();
    }

    public final void onDetachedFromWindow() {
        NavigationModeController navigationModeController = (NavigationModeController) Dependency.get(NavigationModeController.class);
        Objects.requireNonNull(navigationModeController);
        navigationModeController.mListeners.remove(this);
        super.onDetachedFromWindow();
    }

    public final void updateButtonDispatchersCurrentView() {
        FrameLayout frameLayout;
        if (this.mButtonDispatchers != null) {
            if (this.mIsVertical) {
                frameLayout = this.mVertical;
            } else {
                frameLayout = this.mHorizontal;
            }
            for (int i = 0; i < this.mButtonDispatchers.size(); i++) {
                ButtonDispatcher valueAt = this.mButtonDispatchers.valueAt(i);
                Objects.requireNonNull(valueAt);
                View findViewById = frameLayout.findViewById(valueAt.mId);
                valueAt.mCurrentView = findViewById;
                KeyButtonDrawable keyButtonDrawable = valueAt.mImageDrawable;
                if (keyButtonDrawable != null) {
                    keyButtonDrawable.setCallback(findViewById);
                }
                View view = valueAt.mCurrentView;
                if (view != null) {
                    view.setTranslationX(0.0f);
                    valueAt.mCurrentView.setTranslationY(0.0f);
                    valueAt.mCurrentView.setTranslationZ(0.0f);
                }
            }
        }
    }

    public NavigationBarInflaterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        createInflaters();
        this.mOverviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
        this.mNavBarMode = ((NavigationModeController) Dependency.get(NavigationModeController.class)).addListener(this);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        removeAllViews();
        FrameLayout frameLayout = (FrameLayout) this.mLayoutInflater.inflate(C1777R.layout.navigation_layout, this, false);
        this.mHorizontal = frameLayout;
        addView(frameLayout);
        FrameLayout frameLayout2 = (FrameLayout) this.mLayoutInflater.inflate(C1777R.layout.navigation_layout_vertical, this, false);
        this.mVertical = frameLayout2;
        addView(frameLayout2);
        updateAlternativeOrder();
        clearViews();
        inflateLayout(getDefaultLayout());
    }

    public final void updateAlternativeOrder(View view) {
        if (view instanceof ReverseLinearLayout) {
            ReverseLinearLayout reverseLinearLayout = (ReverseLinearLayout) view;
            boolean z = this.mAlternativeOrder;
            Objects.requireNonNull(reverseLinearLayout);
            reverseLinearLayout.mIsAlternativeOrder = z;
            reverseLinearLayout.updateOrder();
        }
    }

    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }
}
