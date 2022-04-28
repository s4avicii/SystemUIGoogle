package com.google.android.material.tabs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Pools$SimplePool;
import androidx.core.util.Pools$SynchronizedPool;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.mediarouter.R$bool;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.internal.ViewUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.WeakHashMap;

@ViewPager.DecorView
public class TabLayout extends HorizontalScrollView {
    public static final Pools$SynchronizedPool tabPool = new Pools$SynchronizedPool(16);
    public AdapterChangeListener adapterChangeListener;
    public int contentInsetStart;
    public ViewPagerOnTabSelectedListener currentVpSelectedListener;
    public boolean inlineLabel;
    public int mode;
    public TabLayoutOnPageChangeListener pageChangeListener;
    public PagerAdapter pagerAdapter;
    public PagerAdapterObserver pagerAdapterObserver;
    public final int requestedTabMaxWidth;
    public final int requestedTabMinWidth;
    public ValueAnimator scrollAnimator;
    public final int scrollableTabMinWidth;
    public final ArrayList<BaseOnTabSelectedListener> selectedListeners = new ArrayList<>();
    public Tab selectedTab;
    public boolean setupViewPagerImplicitly;
    public final SlidingTabIndicator slidingTabIndicator;
    public final int tabBackgroundResId;
    public int tabGravity;
    public ColorStateList tabIconTint;
    public PorterDuff.Mode tabIconTintMode;
    public int tabIndicatorAnimationDuration;
    public boolean tabIndicatorFullWidth;
    public int tabIndicatorGravity;
    public int tabIndicatorHeight = -1;
    public TabIndicatorInterpolator tabIndicatorInterpolator;
    public int tabMaxWidth = Integer.MAX_VALUE;
    public int tabPaddingBottom;
    public int tabPaddingEnd;
    public int tabPaddingStart;
    public int tabPaddingTop;
    public ColorStateList tabRippleColorStateList;
    public Drawable tabSelectedIndicator = new GradientDrawable();
    public int tabSelectedIndicatorColor = 0;
    public int tabTextAppearance;
    public ColorStateList tabTextColors;
    public float tabTextMultiLineSize;
    public float tabTextSize;
    public final Pools$SimplePool tabViewPool = new Pools$SimplePool(12);
    public final ArrayList<Tab> tabs = new ArrayList<>();
    public boolean unboundedRipple;
    public ViewPager viewPager;

    public class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
        public boolean autoRefresh;

        public AdapterChangeListener() {
        }

        public final void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2) {
            TabLayout tabLayout = TabLayout.this;
            if (tabLayout.viewPager == viewPager) {
                tabLayout.setPagerAdapter(pagerAdapter2, this.autoRefresh);
            }
        }
    }

    @Deprecated
    public interface BaseOnTabSelectedListener<T extends Tab> {
        void onTabReselected();

        void onTabSelected(T t);

        void onTabUnselected();
    }

    public class PagerAdapterObserver extends DataSetObserver {
        public PagerAdapterObserver() {
        }

        public final void onChanged() {
            TabLayout.this.populateFromPagerAdapter();
        }

        public final void onInvalidated() {
            TabLayout.this.populateFromPagerAdapter();
        }
    }

    public class SlidingTabIndicator extends LinearLayout {
        public ValueAnimator indicatorAnimator;
        public int selectedPosition = -1;
        public float selectionOffset;

        public SlidingTabIndicator(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        public final void draw(Canvas canvas) {
            int height = TabLayout.this.tabSelectedIndicator.getBounds().height();
            if (height < 0) {
                height = TabLayout.this.tabSelectedIndicator.getIntrinsicHeight();
            }
            int i = TabLayout.this.tabIndicatorGravity;
            int i2 = 0;
            if (i == 0) {
                i2 = getHeight() - height;
                height = getHeight();
            } else if (i == 1) {
                i2 = (getHeight() - height) / 2;
                height = (getHeight() + height) / 2;
            } else if (i != 2) {
                if (i != 3) {
                    height = 0;
                } else {
                    height = getHeight();
                }
            }
            if (TabLayout.this.tabSelectedIndicator.getBounds().width() > 0) {
                Rect bounds = TabLayout.this.tabSelectedIndicator.getBounds();
                TabLayout.this.tabSelectedIndicator.setBounds(bounds.left, i2, bounds.right, height);
                TabLayout tabLayout = TabLayout.this;
                Drawable drawable = tabLayout.tabSelectedIndicator;
                int i3 = tabLayout.tabSelectedIndicatorColor;
                if (i3 != 0) {
                    drawable.setTint(i3);
                } else {
                    drawable.setTintList((ColorStateList) null);
                }
                drawable.draw(canvas);
            }
            super.draw(canvas);
        }

        public final void jumpIndicatorToSelectedPosition() {
            View childAt = getChildAt(this.selectedPosition);
            TabLayout tabLayout = TabLayout.this;
            TabIndicatorInterpolator tabIndicatorInterpolator = tabLayout.tabIndicatorInterpolator;
            Drawable drawable = tabLayout.tabSelectedIndicator;
            Objects.requireNonNull(tabIndicatorInterpolator);
            RectF calculateIndicatorWidthForTab = TabIndicatorInterpolator.calculateIndicatorWidthForTab(tabLayout, childAt);
            drawable.setBounds((int) calculateIndicatorWidthForTab.left, drawable.getBounds().top, (int) calculateIndicatorWidthForTab.right, drawable.getBounds().bottom);
        }

        public final void tweenIndicatorPosition(View view, View view2, float f) {
            boolean z;
            if (view == null || view.getWidth() <= 0) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                TabLayout tabLayout = TabLayout.this;
                tabLayout.tabIndicatorInterpolator.setIndicatorBoundsForOffset(tabLayout, view, view2, f, tabLayout.tabSelectedIndicator);
            } else {
                Drawable drawable = TabLayout.this.tabSelectedIndicator;
                drawable.setBounds(-1, drawable.getBounds().top, -1, TabLayout.this.tabSelectedIndicator.getBounds().bottom);
            }
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postInvalidateOnAnimation(this);
        }

        public final void updateOrRecreateIndicatorAnimation(boolean z, final int i, int i2) {
            final View childAt = getChildAt(this.selectedPosition);
            final View childAt2 = getChildAt(i);
            if (childAt2 == null) {
                jumpIndicatorToSelectedPosition();
                return;
            }
            C20891 r2 = new ValueAnimator.AnimatorUpdateListener() {
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SlidingTabIndicator.this.tweenIndicatorPosition(childAt, childAt2, valueAnimator.getAnimatedFraction());
                }
            };
            if (z) {
                ValueAnimator valueAnimator = new ValueAnimator();
                this.indicatorAnimator = valueAnimator;
                valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                valueAnimator.setDuration((long) i2);
                valueAnimator.setFloatValues(new float[]{0.0f, 1.0f});
                valueAnimator.addUpdateListener(r2);
                valueAnimator.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        SlidingTabIndicator.this.selectedPosition = i;
                    }

                    public final void onAnimationStart(Animator animator) {
                        SlidingTabIndicator.this.selectedPosition = i;
                    }
                });
                valueAnimator.start();
                return;
            }
            this.indicatorAnimator.removeAllUpdateListeners();
            this.indicatorAnimator.addUpdateListener(r2);
        }

        public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            ValueAnimator valueAnimator = this.indicatorAnimator;
            if (valueAnimator == null || !valueAnimator.isRunning()) {
                jumpIndicatorToSelectedPosition();
            } else {
                updateOrRecreateIndicatorAnimation(false, this.selectedPosition, -1);
            }
        }

        public final void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (View.MeasureSpec.getMode(i) == 1073741824) {
                TabLayout tabLayout = TabLayout.this;
                boolean z = true;
                if (tabLayout.tabGravity == 1 || tabLayout.mode == 2) {
                    int childCount = getChildCount();
                    int i3 = 0;
                    for (int i4 = 0; i4 < childCount; i4++) {
                        View childAt = getChildAt(i4);
                        if (childAt.getVisibility() == 0) {
                            i3 = Math.max(i3, childAt.getMeasuredWidth());
                        }
                    }
                    if (i3 > 0) {
                        if (i3 * childCount <= getMeasuredWidth() - (((int) ViewUtils.dpToPx(getContext(), 16)) * 2)) {
                            boolean z2 = false;
                            for (int i5 = 0; i5 < childCount; i5++) {
                                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getChildAt(i5).getLayoutParams();
                                if (layoutParams.width != i3 || layoutParams.weight != 0.0f) {
                                    layoutParams.width = i3;
                                    layoutParams.weight = 0.0f;
                                    z2 = true;
                                }
                            }
                            z = z2;
                        } else {
                            TabLayout tabLayout2 = TabLayout.this;
                            tabLayout2.tabGravity = 0;
                            tabLayout2.updateTabViews(false);
                        }
                        if (z) {
                            super.onMeasure(i, i2);
                        }
                    }
                }
            }
        }

        public final void onRtlPropertiesChanged(int i) {
            super.onRtlPropertiesChanged(i);
        }
    }

    public static class Tab {
        public CharSequence contentDesc;
        public View customView;
        public Drawable icon;

        /* renamed from: id */
        public int f136id = -1;
        public TabLayout parent;
        public int position = -1;
        public CharSequence text;
        public TabView view;
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        public int previousScrollState;
        public int scrollState;
        public final WeakReference<TabLayout> tabLayoutRef;

        public final void onPageScrollStateChanged(int i) {
            this.previousScrollState = this.scrollState;
            this.scrollState = i;
        }

        public final void onPageScrolled(int i, float f, int i2) {
            boolean z;
            TabLayout tabLayout = this.tabLayoutRef.get();
            if (tabLayout != null) {
                int i3 = this.scrollState;
                boolean z2 = false;
                if (i3 != 2 || this.previousScrollState == 1) {
                    z = true;
                } else {
                    z = false;
                }
                if (!(i3 == 2 && this.previousScrollState == 0)) {
                    z2 = true;
                }
                tabLayout.setScrollPosition(i, f, z, z2);
            }
        }

        public final void onPageSelected(int i) {
            int i2;
            boolean z;
            Tab tab;
            TabLayout tabLayout = this.tabLayoutRef.get();
            if (tabLayout != null) {
                Tab tab2 = tabLayout.selectedTab;
                if (tab2 != null) {
                    i2 = tab2.position;
                } else {
                    i2 = -1;
                }
                if (i2 != i && i < tabLayout.tabs.size()) {
                    int i3 = this.scrollState;
                    if (i3 == 0 || (i3 == 2 && this.previousScrollState == 0)) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (i < 0 || i >= tabLayout.tabs.size()) {
                        tab = null;
                    } else {
                        tab = tabLayout.tabs.get(i);
                    }
                    tabLayout.selectTab(tab, z);
                }
            }
        }

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.tabLayoutRef = new WeakReference<>(tabLayout);
        }
    }

    public final class TabView extends LinearLayout {
        public static final /* synthetic */ int $r8$clinit = 0;
        public Drawable baseBackgroundDrawable;
        public ImageView customIconView;
        public TextView customTextView;
        public View customView;
        public int defaultMaxLines = 2;
        public ImageView iconView;
        public Tab tab;
        public TextView textView;

        /* JADX WARNING: type inference failed for: r3v0, types: [android.graphics.drawable.RippleDrawable] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public TabView(android.content.Context r7) {
            /*
                r5 = this;
                com.google.android.material.tabs.TabLayout.this = r6
                r5.<init>(r7)
                r0 = 2
                r5.defaultMaxLines = r0
                int r0 = r6.tabBackgroundResId
                r1 = 0
                if (r0 == 0) goto L_0x0025
                android.graphics.drawable.Drawable r7 = androidx.appcompat.content.res.AppCompatResources.getDrawable(r7, r0)
                r5.baseBackgroundDrawable = r7
                if (r7 == 0) goto L_0x0027
                boolean r7 = r7.isStateful()
                if (r7 == 0) goto L_0x0027
                android.graphics.drawable.Drawable r7 = r5.baseBackgroundDrawable
                int[] r0 = r5.getDrawableState()
                r7.setState(r0)
                goto L_0x0027
            L_0x0025:
                r5.baseBackgroundDrawable = r1
            L_0x0027:
                android.graphics.drawable.GradientDrawable r7 = new android.graphics.drawable.GradientDrawable
                r7.<init>()
                r0 = 0
                r7.setColor(r0)
                android.content.res.ColorStateList r0 = r6.tabRippleColorStateList
                if (r0 == 0) goto L_0x0058
                android.graphics.drawable.GradientDrawable r0 = new android.graphics.drawable.GradientDrawable
                r0.<init>()
                r2 = 925353388(0x3727c5ac, float:1.0E-5)
                r0.setCornerRadius(r2)
                r2 = -1
                r0.setColor(r2)
                android.content.res.ColorStateList r2 = r6.tabRippleColorStateList
                android.content.res.ColorStateList r2 = com.google.android.material.ripple.RippleUtils.convertToRippleDrawableColor(r2)
                android.graphics.drawable.RippleDrawable r3 = new android.graphics.drawable.RippleDrawable
                boolean r4 = r6.unboundedRipple
                if (r4 == 0) goto L_0x0050
                r7 = r1
            L_0x0050:
                if (r4 == 0) goto L_0x0053
                goto L_0x0054
            L_0x0053:
                r1 = r0
            L_0x0054:
                r3.<init>(r2, r7, r1)
                r7 = r3
            L_0x0058:
                java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r0 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
                androidx.core.view.ViewCompat.Api16Impl.setBackground(r5, r7)
                r6.invalidate()
                int r7 = r6.tabPaddingStart
                int r0 = r6.tabPaddingTop
                int r1 = r6.tabPaddingEnd
                int r2 = r6.tabPaddingBottom
                androidx.core.view.ViewCompat.Api17Impl.setPaddingRelative(r5, r7, r0, r1, r2)
                r7 = 17
                r5.setGravity(r7)
                boolean r6 = r6.inlineLabel
                r7 = 1
                r6 = r6 ^ r7
                r5.setOrientation(r6)
                r5.setClickable(r7)
                android.content.Context r6 = r5.getContext()
                r7 = 1002(0x3ea, float:1.404E-42)
                android.view.PointerIcon r6 = android.view.PointerIcon.getSystemIcon(r6, r7)
                androidx.core.view.ViewCompat.Api24Impl.setPointerIcon(r5, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.TabView.<init>(com.google.android.material.tabs.TabLayout, android.content.Context):void");
        }

        public final void update() {
            View view;
            int i;
            boolean z;
            Tab tab2 = this.tab;
            if (tab2 != null) {
                view = tab2.customView;
            } else {
                view = null;
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                if (parent != this) {
                    if (parent != null) {
                        ((ViewGroup) parent).removeView(view);
                    }
                    addView(view);
                }
                this.customView = view;
                TextView textView2 = this.textView;
                if (textView2 != null) {
                    textView2.setVisibility(8);
                }
                ImageView imageView = this.iconView;
                if (imageView != null) {
                    imageView.setVisibility(8);
                    this.iconView.setImageDrawable((Drawable) null);
                }
                TextView textView3 = (TextView) view.findViewById(16908308);
                this.customTextView = textView3;
                if (textView3 != null) {
                    this.defaultMaxLines = textView3.getMaxLines();
                }
                this.customIconView = (ImageView) view.findViewById(16908294);
            } else {
                View view2 = this.customView;
                if (view2 != null) {
                    removeView(view2);
                    this.customView = null;
                }
                this.customTextView = null;
                this.customIconView = null;
            }
            boolean z2 = false;
            if (this.customView == null) {
                if (this.iconView == null) {
                    ImageView imageView2 = (ImageView) LayoutInflater.from(getContext()).inflate(C1777R.layout.design_layout_tab_icon, this, false);
                    this.iconView = imageView2;
                    addView(imageView2, 0);
                }
                if (this.textView == null) {
                    TextView textView4 = (TextView) LayoutInflater.from(getContext()).inflate(C1777R.layout.design_layout_tab_text, this, false);
                    this.textView = textView4;
                    addView(textView4);
                    this.defaultMaxLines = this.textView.getMaxLines();
                }
                this.textView.setTextAppearance(TabLayout.this.tabTextAppearance);
                ColorStateList colorStateList = TabLayout.this.tabTextColors;
                if (colorStateList != null) {
                    this.textView.setTextColor(colorStateList);
                }
                updateTextAndIcon(this.textView, this.iconView);
                final ImageView imageView3 = this.iconView;
                if (imageView3 != null) {
                    imageView3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                            if (r1.getVisibility() == 0) {
                                Objects.requireNonNull(TabView.this);
                            }
                        }
                    });
                }
                final TextView textView5 = this.textView;
                if (textView5 != null) {
                    textView5.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                            if (textView5.getVisibility() == 0) {
                                Objects.requireNonNull(TabView.this);
                            }
                        }
                    });
                }
            } else {
                TextView textView6 = this.customTextView;
                if (!(textView6 == null && this.customIconView == null)) {
                    updateTextAndIcon(textView6, this.customIconView);
                }
            }
            if (tab2 != null && !TextUtils.isEmpty(tab2.contentDesc)) {
                setContentDescription(tab2.contentDesc);
            }
            if (tab2 != null) {
                TabLayout tabLayout = tab2.parent;
                if (tabLayout != null) {
                    Tab tab3 = tabLayout.selectedTab;
                    if (tab3 != null) {
                        i = tab3.position;
                    } else {
                        i = -1;
                    }
                    if (i == -1 || i != tab2.position) {
                        z = false;
                    } else {
                        z = true;
                    }
                    if (z) {
                        z2 = true;
                    }
                } else {
                    throw new IllegalArgumentException("Tab not attached to a TabLayout");
                }
            }
            setSelected(z2);
        }

        /* JADX WARNING: Removed duplicated region for block: B:12:0x002f  */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0035  */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x003b  */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0055  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x006e  */
        /* JADX WARNING: Removed duplicated region for block: B:41:0x00b2  */
        /* JADX WARNING: Removed duplicated region for block: B:43:0x00b7  */
        /* JADX WARNING: Removed duplicated region for block: B:7:0x001b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void updateTextAndIcon(android.widget.TextView r7, android.widget.ImageView r8) {
            /*
                r6 = this;
                com.google.android.material.tabs.TabLayout$Tab r0 = r6.tab
                r1 = 0
                if (r0 == 0) goto L_0x0018
                java.util.Objects.requireNonNull(r0)
                android.graphics.drawable.Drawable r0 = r0.icon
                if (r0 == 0) goto L_0x0018
                com.google.android.material.tabs.TabLayout$Tab r0 = r6.tab
                java.util.Objects.requireNonNull(r0)
                android.graphics.drawable.Drawable r0 = r0.icon
                android.graphics.drawable.Drawable r0 = r0.mutate()
                goto L_0x0019
            L_0x0018:
                r0 = r1
            L_0x0019:
                if (r0 == 0) goto L_0x002b
                com.google.android.material.tabs.TabLayout r2 = com.google.android.material.tabs.TabLayout.this
                android.content.res.ColorStateList r2 = r2.tabIconTint
                r0.setTintList(r2)
                com.google.android.material.tabs.TabLayout r2 = com.google.android.material.tabs.TabLayout.this
                android.graphics.PorterDuff$Mode r2 = r2.tabIconTintMode
                if (r2 == 0) goto L_0x002b
                r0.setTintMode(r2)
            L_0x002b:
                com.google.android.material.tabs.TabLayout$Tab r2 = r6.tab
                if (r2 == 0) goto L_0x0035
                java.util.Objects.requireNonNull(r2)
                java.lang.CharSequence r2 = r2.text
                goto L_0x0036
            L_0x0035:
                r2 = r1
            L_0x0036:
                r3 = 8
                r4 = 0
                if (r8 == 0) goto L_0x004d
                if (r0 == 0) goto L_0x0047
                r8.setImageDrawable(r0)
                r8.setVisibility(r4)
                r6.setVisibility(r4)
                goto L_0x004d
            L_0x0047:
                r8.setVisibility(r3)
                r8.setImageDrawable(r1)
            L_0x004d:
                boolean r0 = android.text.TextUtils.isEmpty(r2)
                r0 = r0 ^ 1
                if (r7 == 0) goto L_0x006c
                if (r0 == 0) goto L_0x0066
                r7.setText(r2)
                com.google.android.material.tabs.TabLayout$Tab r5 = r6.tab
                java.util.Objects.requireNonNull(r5)
                r7.setVisibility(r4)
                r6.setVisibility(r4)
                goto L_0x006c
            L_0x0066:
                r7.setVisibility(r3)
                r7.setText(r1)
            L_0x006c:
                if (r8 == 0) goto L_0x00ae
                android.view.ViewGroup$LayoutParams r7 = r8.getLayoutParams()
                android.view.ViewGroup$MarginLayoutParams r7 = (android.view.ViewGroup.MarginLayoutParams) r7
                if (r0 == 0) goto L_0x0086
                int r5 = r8.getVisibility()
                if (r5 != 0) goto L_0x0086
                android.content.Context r5 = r6.getContext()
                float r3 = com.google.android.material.internal.ViewUtils.dpToPx(r5, r3)
                int r3 = (int) r3
                goto L_0x0087
            L_0x0086:
                r3 = r4
            L_0x0087:
                com.google.android.material.tabs.TabLayout r5 = com.google.android.material.tabs.TabLayout.this
                boolean r5 = r5.inlineLabel
                if (r5 == 0) goto L_0x009f
                int r5 = r7.getMarginEnd()
                if (r3 == r5) goto L_0x00ae
                r7.setMarginEnd(r3)
                r7.bottomMargin = r4
                r8.setLayoutParams(r7)
                r8.requestLayout()
                goto L_0x00ae
            L_0x009f:
                int r5 = r7.bottomMargin
                if (r3 == r5) goto L_0x00ae
                r7.bottomMargin = r3
                r7.setMarginEnd(r4)
                r8.setLayoutParams(r7)
                r8.requestLayout()
            L_0x00ae:
                com.google.android.material.tabs.TabLayout$Tab r7 = r6.tab
                if (r7 == 0) goto L_0x00b4
                java.lang.CharSequence r1 = r7.contentDesc
            L_0x00b4:
                if (r0 == 0) goto L_0x00b7
                goto L_0x00b8
            L_0x00b7:
                r2 = r1
            L_0x00b8:
                r6.setTooltipText(r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.TabView.updateTextAndIcon(android.widget.TextView, android.widget.ImageView):void");
        }

        public final void drawableStateChanged() {
            super.drawableStateChanged();
            int[] drawableState = getDrawableState();
            Drawable drawable = this.baseBackgroundDrawable;
            boolean z = false;
            if (drawable != null && drawable.isStateful()) {
                z = false | this.baseBackgroundDrawable.setState(drawableState);
            }
            if (z) {
                invalidate();
                TabLayout.this.invalidate();
            }
        }

        public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
            Tab tab2 = this.tab;
            Objects.requireNonNull(tab2);
            accessibilityNodeInfo.setCollectionItemInfo((AccessibilityNodeInfo.CollectionItemInfo) AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, tab2.position, 1, isSelected()).mInfo);
            if (isSelected()) {
                accessibilityNodeInfo.setClickable(false);
                accessibilityNodeInfo.removeAction((AccessibilityNodeInfo.AccessibilityAction) AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK.mAction);
            }
            accessibilityNodeInfo.getExtras().putCharSequence("AccessibilityNodeInfo.roleDescription", getResources().getString(C1777R.string.item_view_role_description));
        }

        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0094, code lost:
            if (((r0 / r2.getPaint().getTextSize()) * r2.getLineWidth(0)) > ((float) ((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight()))) goto L_0x0096;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void onMeasure(int r8, int r9) {
            /*
                r7 = this;
                int r0 = android.view.View.MeasureSpec.getSize(r8)
                int r1 = android.view.View.MeasureSpec.getMode(r8)
                com.google.android.material.tabs.TabLayout r2 = com.google.android.material.tabs.TabLayout.this
                java.util.Objects.requireNonNull(r2)
                int r2 = r2.tabMaxWidth
                if (r2 <= 0) goto L_0x001f
                if (r1 == 0) goto L_0x0015
                if (r0 <= r2) goto L_0x001f
            L_0x0015:
                com.google.android.material.tabs.TabLayout r8 = com.google.android.material.tabs.TabLayout.this
                int r8 = r8.tabMaxWidth
                r0 = -2147483648(0xffffffff80000000, float:-0.0)
                int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r0)
            L_0x001f:
                super.onMeasure(r8, r9)
                android.widget.TextView r0 = r7.textView
                if (r0 == 0) goto L_0x00a6
                com.google.android.material.tabs.TabLayout r0 = com.google.android.material.tabs.TabLayout.this
                float r0 = r0.tabTextSize
                int r1 = r7.defaultMaxLines
                android.widget.ImageView r2 = r7.iconView
                r3 = 1
                if (r2 == 0) goto L_0x0039
                int r2 = r2.getVisibility()
                if (r2 != 0) goto L_0x0039
                r1 = r3
                goto L_0x0047
            L_0x0039:
                android.widget.TextView r2 = r7.textView
                if (r2 == 0) goto L_0x0047
                int r2 = r2.getLineCount()
                if (r2 <= r3) goto L_0x0047
                com.google.android.material.tabs.TabLayout r0 = com.google.android.material.tabs.TabLayout.this
                float r0 = r0.tabTextMultiLineSize
            L_0x0047:
                android.widget.TextView r2 = r7.textView
                float r2 = r2.getTextSize()
                android.widget.TextView r4 = r7.textView
                int r4 = r4.getLineCount()
                android.widget.TextView r5 = r7.textView
                int r5 = r5.getMaxLines()
                int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r2 != 0) goto L_0x0061
                if (r5 < 0) goto L_0x00a6
                if (r1 == r5) goto L_0x00a6
            L_0x0061:
                com.google.android.material.tabs.TabLayout r5 = com.google.android.material.tabs.TabLayout.this
                int r5 = r5.mode
                r6 = 0
                if (r5 != r3) goto L_0x0097
                if (r2 <= 0) goto L_0x0097
                if (r4 != r3) goto L_0x0097
                android.widget.TextView r2 = r7.textView
                android.text.Layout r2 = r2.getLayout()
                if (r2 == 0) goto L_0x0096
                float r4 = r2.getLineWidth(r6)
                android.text.TextPaint r2 = r2.getPaint()
                float r2 = r2.getTextSize()
                float r2 = r0 / r2
                float r2 = r2 * r4
                int r4 = r7.getMeasuredWidth()
                int r5 = r7.getPaddingLeft()
                int r4 = r4 - r5
                int r5 = r7.getPaddingRight()
                int r4 = r4 - r5
                float r4 = (float) r4
                int r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
                if (r2 <= 0) goto L_0x0097
            L_0x0096:
                r3 = r6
            L_0x0097:
                if (r3 == 0) goto L_0x00a6
                android.widget.TextView r2 = r7.textView
                r2.setTextSize(r6, r0)
                android.widget.TextView r0 = r7.textView
                r0.setMaxLines(r1)
                super.onMeasure(r8, r9)
            L_0x00a6:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.TabView.onMeasure(int, int):void");
        }

        public final boolean performClick() {
            boolean performClick = super.performClick();
            if (this.tab == null) {
                return performClick;
            }
            if (!performClick) {
                playSoundEffect(0);
            }
            Tab tab2 = this.tab;
            Objects.requireNonNull(tab2);
            TabLayout tabLayout = tab2.parent;
            if (tabLayout != null) {
                tabLayout.selectTab(tab2, true);
                return true;
            }
            throw new IllegalArgumentException("Tab not attached to a TabLayout");
        }

        public final void setSelected(boolean z) {
            if (isSelected() != z) {
            }
            super.setSelected(z);
            TextView textView2 = this.textView;
            if (textView2 != null) {
                textView2.setSelected(z);
            }
            ImageView imageView = this.iconView;
            if (imageView != null) {
                imageView.setSelected(z);
            }
            View view = this.customView;
            if (view != null) {
                view.setSelected(z);
            }
        }
    }

    public static class ViewPagerOnTabSelectedListener implements BaseOnTabSelectedListener {
        public final ViewPager viewPager;

        public final void onTabReselected() {
        }

        public final void onTabUnselected() {
        }

        public final void onTabSelected(Tab tab) {
            ViewPager viewPager2 = this.viewPager;
            Objects.requireNonNull(tab);
            viewPager2.setCurrentItem(tab.position);
        }

        public ViewPagerOnTabSelectedListener(ViewPager viewPager2) {
            this.viewPager = viewPager2;
        }
    }

    public final void addView(View view) {
        addViewInternal(view);
    }

    public final void animateToTab(int i) {
        boolean z;
        if (i != -1) {
            if (getWindowToken() != null) {
                WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (ViewCompat.Api19Impl.isLaidOut(this)) {
                    SlidingTabIndicator slidingTabIndicator2 = this.slidingTabIndicator;
                    Objects.requireNonNull(slidingTabIndicator2);
                    int childCount = slidingTabIndicator2.getChildCount();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= childCount) {
                            z = false;
                            break;
                        } else if (slidingTabIndicator2.getChildAt(i2).getWidth() <= 0) {
                            z = true;
                            break;
                        } else {
                            i2++;
                        }
                    }
                    if (!z) {
                        int scrollX = getScrollX();
                        int calculateScrollXForTab = calculateScrollXForTab(i, 0.0f);
                        if (scrollX != calculateScrollXForTab) {
                            if (this.scrollAnimator == null) {
                                ValueAnimator valueAnimator = new ValueAnimator();
                                this.scrollAnimator = valueAnimator;
                                valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
                                this.scrollAnimator.setDuration((long) this.tabIndicatorAnimationDuration);
                                this.scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                        TabLayout.this.scrollTo(((Integer) valueAnimator.getAnimatedValue()).intValue(), 0);
                                    }
                                });
                            }
                            this.scrollAnimator.setIntValues(new int[]{scrollX, calculateScrollXForTab});
                            this.scrollAnimator.start();
                        }
                        SlidingTabIndicator slidingTabIndicator3 = this.slidingTabIndicator;
                        int i3 = this.tabIndicatorAnimationDuration;
                        Objects.requireNonNull(slidingTabIndicator3);
                        ValueAnimator valueAnimator2 = slidingTabIndicator3.indicatorAnimator;
                        if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                            slidingTabIndicator3.indicatorAnimator.cancel();
                        }
                        slidingTabIndicator3.updateOrRecreateIndicatorAnimation(true, i, i3);
                        return;
                    }
                }
            }
            setScrollPosition(i, 0.0f, true, true);
        }
    }

    public final void onDraw(Canvas canvas) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View childAt = this.slidingTabIndicator.getChildAt(i);
            if (childAt instanceof TabView) {
                TabView tabView = (TabView) childAt;
                int i2 = TabView.$r8$clinit;
                Objects.requireNonNull(tabView);
                Drawable drawable = tabView.baseBackgroundDrawable;
                if (drawable != null) {
                    drawable.setBounds(tabView.getLeft(), tabView.getTop(), tabView.getRight(), tabView.getBottom());
                    tabView.baseBackgroundDrawable.draw(canvas);
                }
            }
        }
        super.onDraw(canvas);
    }

    public final void setScrollPosition(int i, float f, boolean z, boolean z2) {
        int i2;
        int round = Math.round(((float) i) + f);
        if (round >= 0 && round < this.slidingTabIndicator.getChildCount()) {
            if (z2) {
                SlidingTabIndicator slidingTabIndicator2 = this.slidingTabIndicator;
                Objects.requireNonNull(slidingTabIndicator2);
                ValueAnimator valueAnimator = slidingTabIndicator2.indicatorAnimator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    slidingTabIndicator2.indicatorAnimator.cancel();
                }
                slidingTabIndicator2.selectedPosition = i;
                slidingTabIndicator2.selectionOffset = f;
                slidingTabIndicator2.tweenIndicatorPosition(slidingTabIndicator2.getChildAt(i), slidingTabIndicator2.getChildAt(slidingTabIndicator2.selectedPosition + 1), slidingTabIndicator2.selectionOffset);
            }
            ValueAnimator valueAnimator2 = this.scrollAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.scrollAnimator.cancel();
            }
            if (i < 0) {
                i2 = 0;
            } else {
                i2 = calculateScrollXForTab(i, f);
            }
            scrollTo(i2, 0);
            if (z) {
                setSelectedTabView(round);
            }
        }
    }

    public final void updateTabViews(boolean z) {
        for (int i = 0; i < this.slidingTabIndicator.getChildCount(); i++) {
            View childAt = this.slidingTabIndicator.getChildAt(i);
            int i2 = this.requestedTabMinWidth;
            if (i2 == -1) {
                int i3 = this.mode;
                if (i3 == 0 || i3 == 2) {
                    i2 = this.scrollableTabMinWidth;
                } else {
                    i2 = 0;
                }
            }
            childAt.setMinimumWidth(i2);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            if (this.mode == 1 && this.tabGravity == 0) {
                layoutParams.width = 0;
                layoutParams.weight = 1.0f;
            } else {
                layoutParams.width = -2;
                layoutParams.weight = 0.0f;
            }
            if (z) {
                childAt.requestLayout();
            }
        }
    }

    public final void addTab(Tab tab, boolean z) {
        int size = this.tabs.size();
        if (tab.parent == this) {
            tab.position = size;
            this.tabs.add(size, tab);
            int size2 = this.tabs.size();
            while (true) {
                size++;
                if (size >= size2) {
                    break;
                }
                Tab tab2 = this.tabs.get(size);
                Objects.requireNonNull(tab2);
                tab2.position = size;
            }
            TabView tabView = tab.view;
            tabView.setSelected(false);
            tabView.setActivated(false);
            SlidingTabIndicator slidingTabIndicator2 = this.slidingTabIndicator;
            int i = tab.position;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -1);
            if (this.mode == 1 && this.tabGravity == 0) {
                layoutParams.width = 0;
                layoutParams.weight = 1.0f;
            } else {
                layoutParams.width = -2;
                layoutParams.weight = 0.0f;
            }
            slidingTabIndicator2.addView(tabView, i, layoutParams);
            if (z) {
                TabLayout tabLayout = tab.parent;
                if (tabLayout != null) {
                    tabLayout.selectTab(tab, true);
                    return;
                }
                throw new IllegalArgumentException("Tab not attached to a TabLayout");
            }
            return;
        }
        throw new IllegalArgumentException("Tab belongs to a different TabLayout.");
    }

    public final void addView(View view, int i) {
        addViewInternal(view);
    }

    public final void addViewInternal(View view) {
        if (view instanceof TabItem) {
            TabItem tabItem = (TabItem) view;
            Tab newTab = newTab();
            CharSequence charSequence = tabItem.text;
            if (charSequence != null) {
                if (TextUtils.isEmpty(newTab.contentDesc) && !TextUtils.isEmpty(charSequence)) {
                    newTab.view.setContentDescription(charSequence);
                }
                newTab.text = charSequence;
                TabView tabView = newTab.view;
                if (tabView != null) {
                    tabView.update();
                }
            }
            Drawable drawable = tabItem.icon;
            if (drawable != null) {
                newTab.icon = drawable;
                TabLayout tabLayout = newTab.parent;
                if (tabLayout.tabGravity == 1 || tabLayout.mode == 2) {
                    tabLayout.updateTabViews(true);
                }
                TabView tabView2 = newTab.view;
                if (tabView2 != null) {
                    tabView2.update();
                }
            }
            int i = tabItem.customLayout;
            if (i != 0) {
                newTab.customView = LayoutInflater.from(newTab.view.getContext()).inflate(i, newTab.view, false);
                TabView tabView3 = newTab.view;
                if (tabView3 != null) {
                    tabView3.update();
                }
            }
            if (!TextUtils.isEmpty(tabItem.getContentDescription())) {
                newTab.contentDesc = tabItem.getContentDescription();
                TabView tabView4 = newTab.view;
                if (tabView4 != null) {
                    tabView4.update();
                }
            }
            addTab(newTab, this.tabs.isEmpty());
            return;
        }
        throw new IllegalArgumentException("Only TabItem instances can be added to TabLayout");
    }

    public final int calculateScrollXForTab(int i, float f) {
        View childAt;
        View view;
        int i2 = this.mode;
        int i3 = 0;
        if ((i2 != 0 && i2 != 2) || (childAt = this.slidingTabIndicator.getChildAt(i)) == null) {
            return 0;
        }
        int i4 = i + 1;
        if (i4 < this.slidingTabIndicator.getChildCount()) {
            view = this.slidingTabIndicator.getChildAt(i4);
        } else {
            view = null;
        }
        int width = childAt.getWidth();
        if (view != null) {
            i3 = view.getWidth();
        }
        int left = ((width / 2) + childAt.getLeft()) - (getWidth() / 2);
        int i5 = (int) (((float) (width + i3)) * 0.5f * f);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (ViewCompat.Api17Impl.getLayoutDirection(this) == 0) {
            return left + i5;
        }
        return left - i5;
    }

    public final FrameLayout.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return generateDefaultLayoutParams();
    }

    public final Tab newTab() {
        TabView tabView;
        int i;
        Tab tab = (Tab) tabPool.acquire();
        if (tab == null) {
            tab = new Tab();
        }
        tab.parent = this;
        Pools$SimplePool pools$SimplePool = this.tabViewPool;
        if (pools$SimplePool != null) {
            tabView = (TabView) pools$SimplePool.acquire();
        } else {
            tabView = null;
        }
        if (tabView == null) {
            tabView = new TabView(getContext());
        }
        if (tab != tabView.tab) {
            tabView.tab = tab;
            tabView.update();
        }
        tabView.setFocusable(true);
        int i2 = this.requestedTabMinWidth;
        if (i2 == -1) {
            int i3 = this.mode;
            if (i3 == 0 || i3 == 2) {
                i = this.scrollableTabMinWidth;
            } else {
                i = 0;
            }
            i2 = i;
        }
        tabView.setMinimumWidth(i2);
        if (TextUtils.isEmpty(tab.contentDesc)) {
            tabView.setContentDescription(tab.text);
        } else {
            tabView.setContentDescription(tab.contentDesc);
        }
        tab.view = tabView;
        int i4 = tab.f136id;
        if (i4 != -1) {
            tabView.setId(i4);
        }
        return tab;
    }

    public final void populateFromPagerAdapter() {
        Tab tab;
        int i = -1;
        int childCount = this.slidingTabIndicator.getChildCount() - 1;
        while (true) {
            tab = null;
            if (childCount < 0) {
                break;
            }
            TabView tabView = (TabView) this.slidingTabIndicator.getChildAt(childCount);
            this.slidingTabIndicator.removeViewAt(childCount);
            if (tabView != null) {
                if (tabView.tab != null) {
                    tabView.tab = null;
                    tabView.update();
                }
                tabView.setSelected(false);
                this.tabViewPool.release(tabView);
            }
            requestLayout();
            childCount--;
        }
        Iterator<Tab> it = this.tabs.iterator();
        while (it.hasNext()) {
            Tab next = it.next();
            it.remove();
            Objects.requireNonNull(next);
            next.parent = null;
            next.view = null;
            next.icon = null;
            next.f136id = -1;
            next.text = null;
            next.contentDesc = null;
            next.position = -1;
            next.customView = null;
            tabPool.release(next);
        }
        this.selectedTab = null;
        PagerAdapter pagerAdapter2 = this.pagerAdapter;
        if (pagerAdapter2 != null) {
            int count = pagerAdapter2.getCount();
            for (int i2 = 0; i2 < count; i2++) {
                Tab newTab = newTab();
                Objects.requireNonNull(this.pagerAdapter);
                if (TextUtils.isEmpty(newTab.contentDesc) && !TextUtils.isEmpty((CharSequence) null)) {
                    newTab.view.setContentDescription((CharSequence) null);
                }
                newTab.text = null;
                TabView tabView2 = newTab.view;
                if (tabView2 != null) {
                    tabView2.update();
                }
                addTab(newTab, false);
            }
            ViewPager viewPager2 = this.viewPager;
            if (viewPager2 != null && count > 0) {
                int i3 = viewPager2.mCurItem;
                Tab tab2 = this.selectedTab;
                if (tab2 != null) {
                    i = tab2.position;
                }
                if (i3 != i && i3 < this.tabs.size()) {
                    if (i3 >= 0 && i3 < this.tabs.size()) {
                        tab = this.tabs.get(i3);
                    }
                    selectTab(tab, true);
                }
            }
        }
    }

    public final void selectTab(Tab tab, boolean z) {
        int i;
        Tab tab2 = this.selectedTab;
        if (tab2 != tab) {
            if (tab != null) {
                i = tab.position;
            } else {
                i = -1;
            }
            if (z) {
                if ((tab2 == null || tab2.position == -1) && i != -1) {
                    setScrollPosition(i, 0.0f, true, true);
                } else {
                    animateToTab(i);
                }
                if (i != -1) {
                    setSelectedTabView(i);
                }
            }
            this.selectedTab = tab;
            if (tab2 != null) {
                for (int size = this.selectedListeners.size() - 1; size >= 0; size--) {
                    this.selectedListeners.get(size).onTabUnselected();
                }
            }
            if (tab != null) {
                for (int size2 = this.selectedListeners.size() - 1; size2 >= 0; size2--) {
                    this.selectedListeners.get(size2).onTabSelected(tab);
                }
            }
        } else if (tab2 != null) {
            for (int size3 = this.selectedListeners.size() - 1; size3 >= 0; size3--) {
                this.selectedListeners.get(size3).onTabReselected();
            }
            Objects.requireNonNull(tab);
            animateToTab(tab.position);
        }
    }

    public final void setPagerAdapter(PagerAdapter pagerAdapter2, boolean z) {
        PagerAdapterObserver pagerAdapterObserver2;
        PagerAdapter pagerAdapter3 = this.pagerAdapter;
        if (!(pagerAdapter3 == null || (pagerAdapterObserver2 = this.pagerAdapterObserver) == null)) {
            pagerAdapter3.mObservable.unregisterObserver(pagerAdapterObserver2);
        }
        this.pagerAdapter = pagerAdapter2;
        if (z && pagerAdapter2 != null) {
            if (this.pagerAdapterObserver == null) {
                this.pagerAdapterObserver = new PagerAdapterObserver();
            }
            pagerAdapter2.mObservable.registerObserver(this.pagerAdapterObserver);
        }
        populateFromPagerAdapter();
    }

    public final void setSelectedTabView(int i) {
        boolean z;
        int childCount = this.slidingTabIndicator.getChildCount();
        if (i < childCount) {
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.slidingTabIndicator.getChildAt(i2);
                boolean z2 = true;
                if (i2 == i) {
                    z = true;
                } else {
                    z = false;
                }
                childAt.setSelected(z);
                if (i2 != i) {
                    z2 = false;
                }
                childAt.setActivated(z2);
            }
        }
    }

    public final void setupWithViewPager(ViewPager viewPager2, boolean z) {
        ArrayList arrayList;
        ViewPager viewPager3 = this.viewPager;
        if (viewPager3 != null) {
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener = this.pageChangeListener;
            if (!(tabLayoutOnPageChangeListener == null || (arrayList = viewPager3.mOnPageChangeListeners) == null)) {
                arrayList.remove(tabLayoutOnPageChangeListener);
            }
            AdapterChangeListener adapterChangeListener2 = this.adapterChangeListener;
            if (adapterChangeListener2 != null) {
                ViewPager viewPager4 = this.viewPager;
                Objects.requireNonNull(viewPager4);
                ArrayList arrayList2 = viewPager4.mAdapterChangeListeners;
                if (arrayList2 != null) {
                    arrayList2.remove(adapterChangeListener2);
                }
            }
        }
        ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener = this.currentVpSelectedListener;
        if (viewPagerOnTabSelectedListener != null) {
            this.selectedListeners.remove(viewPagerOnTabSelectedListener);
            this.currentVpSelectedListener = null;
        }
        if (viewPager2 != null) {
            this.viewPager = viewPager2;
            if (this.pageChangeListener == null) {
                this.pageChangeListener = new TabLayoutOnPageChangeListener(this);
            }
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener2 = this.pageChangeListener;
            Objects.requireNonNull(tabLayoutOnPageChangeListener2);
            tabLayoutOnPageChangeListener2.scrollState = 0;
            tabLayoutOnPageChangeListener2.previousScrollState = 0;
            TabLayoutOnPageChangeListener tabLayoutOnPageChangeListener3 = this.pageChangeListener;
            if (viewPager2.mOnPageChangeListeners == null) {
                viewPager2.mOnPageChangeListeners = new ArrayList();
            }
            viewPager2.mOnPageChangeListeners.add(tabLayoutOnPageChangeListener3);
            ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener2 = new ViewPagerOnTabSelectedListener(viewPager2);
            this.currentVpSelectedListener = viewPagerOnTabSelectedListener2;
            if (!this.selectedListeners.contains(viewPagerOnTabSelectedListener2)) {
                this.selectedListeners.add(viewPagerOnTabSelectedListener2);
            }
            PagerAdapter pagerAdapter2 = viewPager2.mAdapter;
            if (pagerAdapter2 != null) {
                setPagerAdapter(pagerAdapter2, true);
            }
            if (this.adapterChangeListener == null) {
                this.adapterChangeListener = new AdapterChangeListener();
            }
            AdapterChangeListener adapterChangeListener3 = this.adapterChangeListener;
            Objects.requireNonNull(adapterChangeListener3);
            adapterChangeListener3.autoRefresh = true;
            AdapterChangeListener adapterChangeListener4 = this.adapterChangeListener;
            if (viewPager2.mAdapterChangeListeners == null) {
                viewPager2.mAdapterChangeListeners = new ArrayList();
            }
            viewPager2.mAdapterChangeListeners.add(adapterChangeListener4);
            setScrollPosition(viewPager2.mCurItem, 0.0f, true, true);
        } else {
            this.viewPager = null;
            setPagerAdapter((PagerAdapter) null, false);
        }
        this.setupViewPagerImplicitly = z;
    }

    public final boolean shouldDelayChildPressedState() {
        if (Math.max(0, ((this.slidingTabIndicator.getWidth() - getWidth()) - getPaddingLeft()) - getPaddingRight()) > 0) {
            return true;
        }
        return false;
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x0264, code lost:
        if (r13 != 2) goto L_0x0276;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public TabLayout(android.content.Context r13, android.util.AttributeSet r14) {
        /*
            r12 = this;
            r0 = 2130969945(0x7f040559, float:1.7548586E38)
            r1 = 2132018391(0x7f1404d7, float:1.9675087E38)
            android.content.Context r13 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r13, r14, r0, r1)
            r12.<init>(r13, r14, r0)
            java.util.ArrayList r13 = new java.util.ArrayList
            r13.<init>()
            r12.tabs = r13
            android.graphics.drawable.GradientDrawable r13 = new android.graphics.drawable.GradientDrawable
            r13.<init>()
            r12.tabSelectedIndicator = r13
            r13 = 0
            r12.tabSelectedIndicatorColor = r13
            r0 = 2147483647(0x7fffffff, float:NaN)
            r12.tabMaxWidth = r0
            r0 = -1
            r12.tabIndicatorHeight = r0
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r12.selectedListeners = r1
            androidx.core.util.Pools$SimplePool r1 = new androidx.core.util.Pools$SimplePool
            r2 = 12
            r1.<init>(r2)
            r12.tabViewPool = r1
            android.content.Context r1 = r12.getContext()
            r12.setHorizontalScrollBarEnabled(r13)
            com.google.android.material.tabs.TabLayout$SlidingTabIndicator r9 = new com.google.android.material.tabs.TabLayout$SlidingTabIndicator
            r9.<init>(r1)
            r12.slidingTabIndicator = r9
            android.widget.FrameLayout$LayoutParams r3 = new android.widget.FrameLayout$LayoutParams
            r4 = -2
            r3.<init>(r4, r0)
            super.addView(r9, r13, r3)
            int[] r5 = com.google.android.material.R$styleable.TabLayout
            r10 = 1
            int[] r8 = new int[r10]
            r11 = 23
            r8[r13] = r11
            r6 = 2130969945(0x7f040559, float:1.7548586E38)
            r7 = 2132018391(0x7f1404d7, float:1.9675087E38)
            r3 = r1
            r4 = r14
            android.content.res.TypedArray r14 = com.google.android.material.internal.ThemeEnforcement.obtainStyledAttributes(r3, r4, r5, r6, r7, r8)
            android.graphics.drawable.Drawable r3 = r12.getBackground()
            boolean r3 = r3 instanceof android.graphics.drawable.ColorDrawable
            if (r3 == 0) goto L_0x008f
            android.graphics.drawable.Drawable r3 = r12.getBackground()
            android.graphics.drawable.ColorDrawable r3 = (android.graphics.drawable.ColorDrawable) r3
            com.google.android.material.shape.MaterialShapeDrawable r4 = new com.google.android.material.shape.MaterialShapeDrawable
            r4.<init>()
            int r3 = r3.getColor()
            android.content.res.ColorStateList r3 = android.content.res.ColorStateList.valueOf(r3)
            r4.setFillColor(r3)
            r4.initializeElevationOverlay(r1)
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            float r3 = androidx.core.view.ViewCompat.Api21Impl.getElevation(r12)
            r4.setElevation(r3)
            androidx.core.view.ViewCompat.Api16Impl.setBackground(r12, r4)
        L_0x008f:
            r3 = 5
            android.graphics.drawable.Drawable r3 = com.google.android.material.resources.MaterialResources.getDrawable(r1, r14, r3)
            android.graphics.drawable.Drawable r4 = r12.tabSelectedIndicator
            if (r4 == r3) goto L_0x00bd
            if (r3 == 0) goto L_0x009b
            goto L_0x00a0
        L_0x009b:
            android.graphics.drawable.GradientDrawable r3 = new android.graphics.drawable.GradientDrawable
            r3.<init>()
        L_0x00a0:
            r12.tabSelectedIndicator = r3
            int r4 = r12.tabIndicatorHeight
            if (r4 == r0) goto L_0x00a7
            goto L_0x00ab
        L_0x00a7:
            int r4 = r3.getIntrinsicHeight()
        L_0x00ab:
            android.graphics.drawable.Drawable r3 = r12.tabSelectedIndicator
            android.graphics.Rect r3 = r3.getBounds()
            android.graphics.drawable.Drawable r5 = r12.tabSelectedIndicator
            int r6 = r3.left
            int r3 = r3.right
            r5.setBounds(r6, r13, r3, r4)
            r9.requestLayout()
        L_0x00bd:
            r3 = 8
            int r3 = r14.getColor(r3, r13)
            r12.tabSelectedIndicatorColor = r3
            r12.updateTabViews(r13)
            r3 = 11
            int r3 = r14.getDimensionPixelSize(r3, r0)
            android.graphics.drawable.Drawable r4 = r12.tabSelectedIndicator
            android.graphics.Rect r4 = r4.getBounds()
            android.graphics.drawable.Drawable r5 = r12.tabSelectedIndicator
            int r6 = r4.left
            int r4 = r4.right
            r5.setBounds(r6, r13, r4, r3)
            r9.requestLayout()
            r3 = 10
            int r3 = r14.getInt(r3, r13)
            int r4 = r12.tabIndicatorGravity
            if (r4 == r3) goto L_0x00f1
            r12.tabIndicatorGravity = r3
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r9)
        L_0x00f1:
            r3 = 7
            int r3 = r14.getInt(r3, r13)
            if (r3 == 0) goto L_0x0119
            if (r3 != r10) goto L_0x0102
            com.google.android.material.tabs.ElasticTabIndicatorInterpolator r3 = new com.google.android.material.tabs.ElasticTabIndicatorInterpolator
            r3.<init>()
            r12.tabIndicatorInterpolator = r3
            goto L_0x0120
        L_0x0102:
            java.lang.IllegalArgumentException r12 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            r13.<init>()
            r13.append(r3)
            java.lang.String r14 = " is not a valid TabIndicatorAnimationMode"
            r13.append(r14)
            java.lang.String r13 = r13.toString()
            r12.<init>(r13)
            throw r12
        L_0x0119:
            com.google.android.material.tabs.TabIndicatorInterpolator r3 = new com.google.android.material.tabs.TabIndicatorInterpolator
            r3.<init>()
            r12.tabIndicatorInterpolator = r3
        L_0x0120:
            r3 = 9
            boolean r3 = r14.getBoolean(r3, r10)
            r12.tabIndicatorFullWidth = r3
            r9.jumpIndicatorToSelectedPosition()
            java.util.WeakHashMap<android.view.View, androidx.core.view.ViewPropertyAnimatorCompat> r3 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            androidx.core.view.ViewCompat.Api16Impl.postInvalidateOnAnimation(r9)
            r3 = 16
            int r3 = r14.getDimensionPixelSize(r3, r13)
            r12.tabPaddingBottom = r3
            r12.tabPaddingEnd = r3
            r12.tabPaddingTop = r3
            r12.tabPaddingStart = r3
            r4 = 19
            int r3 = r14.getDimensionPixelSize(r4, r3)
            r12.tabPaddingStart = r3
            r3 = 20
            int r4 = r12.tabPaddingTop
            int r3 = r14.getDimensionPixelSize(r3, r4)
            r12.tabPaddingTop = r3
            r3 = 18
            int r4 = r12.tabPaddingEnd
            int r3 = r14.getDimensionPixelSize(r3, r4)
            r12.tabPaddingEnd = r3
            r3 = 17
            int r4 = r12.tabPaddingBottom
            int r3 = r14.getDimensionPixelSize(r3, r4)
            r12.tabPaddingBottom = r3
            r3 = 2132017902(0x7f1402ee, float:1.9674096E38)
            int r3 = r14.getResourceId(r11, r3)
            r12.tabTextAppearance = r3
            int[] r4 = androidx.appcompat.R$styleable.TextAppearance
            android.content.res.TypedArray r3 = r1.obtainStyledAttributes(r3, r4)
            int r4 = r3.getDimensionPixelSize(r13, r13)     // Catch:{ all -> 0x027a }
            float r4 = (float) r4     // Catch:{ all -> 0x027a }
            r12.tabTextSize = r4     // Catch:{ all -> 0x027a }
            r4 = 3
            android.content.res.ColorStateList r5 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (android.content.res.TypedArray) r3, (int) r4)     // Catch:{ all -> 0x027a }
            r12.tabTextColors = r5     // Catch:{ all -> 0x027a }
            r3.recycle()
            r3 = 24
            boolean r5 = r14.hasValue(r3)
            if (r5 == 0) goto L_0x0192
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (android.content.res.TypedArray) r14, (int) r3)
            r12.tabTextColors = r3
        L_0x0192:
            r3 = 22
            boolean r5 = r14.hasValue(r3)
            r6 = 2
            if (r5 == 0) goto L_0x01bc
            int r3 = r14.getColor(r3, r13)
            android.content.res.ColorStateList r5 = r12.tabTextColors
            int r5 = r5.getDefaultColor()
            int[][] r7 = new int[r6][]
            int[] r8 = new int[r6]
            int[] r11 = android.widget.HorizontalScrollView.SELECTED_STATE_SET
            r7[r13] = r11
            r8[r13] = r3
            int[] r3 = android.widget.HorizontalScrollView.EMPTY_STATE_SET
            r7[r10] = r3
            r8[r10] = r5
            android.content.res.ColorStateList r3 = new android.content.res.ColorStateList
            r3.<init>(r7, r8)
            r12.tabTextColors = r3
        L_0x01bc:
            android.content.res.ColorStateList r3 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (android.content.res.TypedArray) r14, (int) r4)
            r12.tabIconTint = r3
            r3 = 4
            int r3 = r14.getInt(r3, r0)
            r4 = 0
            android.graphics.PorterDuff$Mode r3 = com.google.android.material.internal.ViewUtils.parseTintMode(r3, r4)
            r12.tabIconTintMode = r3
            r3 = 21
            android.content.res.ColorStateList r1 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r1, (android.content.res.TypedArray) r14, (int) r3)
            r12.tabRippleColorStateList = r1
            r1 = 6
            r3 = 300(0x12c, float:4.2E-43)
            int r1 = r14.getInt(r1, r3)
            r12.tabIndicatorAnimationDuration = r1
            r1 = 14
            int r1 = r14.getDimensionPixelSize(r1, r0)
            r12.requestedTabMinWidth = r1
            r1 = 13
            int r0 = r14.getDimensionPixelSize(r1, r0)
            r12.requestedTabMaxWidth = r0
            int r0 = r14.getResourceId(r13, r13)
            r12.tabBackgroundResId = r0
            int r0 = r14.getDimensionPixelSize(r10, r13)
            r12.contentInsetStart = r0
            r0 = 15
            int r0 = r14.getInt(r0, r10)
            r12.mode = r0
            int r0 = r14.getInt(r6, r13)
            r12.tabGravity = r0
            boolean r0 = r14.getBoolean(r2, r13)
            r12.inlineLabel = r0
            r0 = 25
            boolean r0 = r14.getBoolean(r0, r13)
            r12.unboundedRipple = r0
            r14.recycle()
            android.content.res.Resources r14 = r12.getResources()
            r0 = 2131165653(0x7f0701d5, float:1.794553E38)
            int r0 = r14.getDimensionPixelSize(r0)
            float r0 = (float) r0
            r12.tabTextMultiLineSize = r0
            r0 = 2131165651(0x7f0701d3, float:1.7945525E38)
            int r14 = r14.getDimensionPixelSize(r0)
            r12.scrollableTabMinWidth = r14
            int r14 = r12.mode
            if (r14 == 0) goto L_0x023a
            if (r14 != r6) goto L_0x0238
            goto L_0x023a
        L_0x0238:
            r14 = r13
            goto L_0x0243
        L_0x023a:
            int r14 = r12.contentInsetStart
            int r0 = r12.tabPaddingStart
            int r14 = r14 - r0
            int r14 = java.lang.Math.max(r13, r14)
        L_0x0243:
            androidx.core.view.ViewCompat.Api17Impl.setPaddingRelative(r9, r14, r13, r13, r13)
            int r13 = r12.mode
            java.lang.String r14 = "TabLayout"
            if (r13 == 0) goto L_0x025e
            if (r13 == r10) goto L_0x0251
            if (r13 == r6) goto L_0x0251
            goto L_0x0276
        L_0x0251:
            int r13 = r12.tabGravity
            if (r13 != r6) goto L_0x025a
            java.lang.String r13 = "GRAVITY_START is not supported with the current tab mode, GRAVITY_CENTER will be used instead"
            android.util.Log.w(r14, r13)
        L_0x025a:
            r9.setGravity(r10)
            goto L_0x0276
        L_0x025e:
            int r13 = r12.tabGravity
            if (r13 == 0) goto L_0x026b
            if (r13 == r10) goto L_0x0267
            if (r13 == r6) goto L_0x0270
            goto L_0x0276
        L_0x0267:
            r9.setGravity(r10)
            goto L_0x0276
        L_0x026b:
            java.lang.String r13 = "MODE_SCROLLABLE + GRAVITY_FILL is not supported, GRAVITY_START will be used instead"
            android.util.Log.w(r14, r13)
        L_0x0270:
            r13 = 8388611(0x800003, float:1.1754948E-38)
            r9.setGravity(r13)
        L_0x0276:
            r12.updateTabViews(r10)
            return
        L_0x027a:
            r12 = move-exception
            r3.recycle()
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public final void addView(View view, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        R$bool.setParentAbsoluteElevation(this);
        if (this.viewPager == null) {
            ViewParent parent = getParent();
            if (parent instanceof ViewPager) {
                setupWithViewPager((ViewPager) parent, true);
            }
        }
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.setupViewPagerImplicitly) {
            setupWithViewPager((ViewPager) null, false);
            this.setupViewPagerImplicitly = false;
        }
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo) AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(1, this.tabs.size(), 1).mInfo);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009f, code lost:
        if (r0 != 2) goto L_0x00b9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00aa, code lost:
        if (r8.getMeasuredWidth() != getMeasuredWidth()) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00ac, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00b6, code lost:
        if (r8.getMeasuredWidth() < getMeasuredWidth()) goto L_0x00ac;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r8, int r9) {
        /*
            r7 = this;
            android.content.Context r0 = r7.getContext()
            java.util.ArrayList<com.google.android.material.tabs.TabLayout$Tab> r1 = r7.tabs
            int r1 = r1.size()
            r2 = 0
            r3 = r2
        L_0x000c:
            r4 = 1
            if (r3 >= r1) goto L_0x002a
            java.util.ArrayList<com.google.android.material.tabs.TabLayout$Tab> r5 = r7.tabs
            java.lang.Object r5 = r5.get(r3)
            com.google.android.material.tabs.TabLayout$Tab r5 = (com.google.android.material.tabs.TabLayout.Tab) r5
            if (r5 == 0) goto L_0x0027
            android.graphics.drawable.Drawable r6 = r5.icon
            if (r6 == 0) goto L_0x0027
            java.lang.CharSequence r5 = r5.text
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 != 0) goto L_0x0027
            r1 = r4
            goto L_0x002b
        L_0x0027:
            int r3 = r3 + 1
            goto L_0x000c
        L_0x002a:
            r1 = r2
        L_0x002b:
            if (r1 == 0) goto L_0x0034
            boolean r1 = r7.inlineLabel
            if (r1 != 0) goto L_0x0034
            r1 = 72
            goto L_0x0036
        L_0x0034:
            r1 = 48
        L_0x0036:
            float r0 = com.google.android.material.internal.ViewUtils.dpToPx(r0, r1)
            int r0 = java.lang.Math.round(r0)
            int r1 = android.view.View.MeasureSpec.getMode(r9)
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r5 = 1073741824(0x40000000, float:2.0)
            if (r1 == r3) goto L_0x005a
            if (r1 == 0) goto L_0x004b
            goto L_0x006d
        L_0x004b:
            int r9 = r7.getPaddingTop()
            int r9 = r9 + r0
            int r0 = r7.getPaddingBottom()
            int r0 = r0 + r9
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r5)
            goto L_0x006d
        L_0x005a:
            int r1 = r7.getChildCount()
            if (r1 != r4) goto L_0x006d
            int r1 = android.view.View.MeasureSpec.getSize(r9)
            if (r1 < r0) goto L_0x006d
            android.view.View r1 = r7.getChildAt(r2)
            r1.setMinimumHeight(r0)
        L_0x006d:
            int r0 = android.view.View.MeasureSpec.getSize(r8)
            int r1 = android.view.View.MeasureSpec.getMode(r8)
            if (r1 == 0) goto L_0x008b
            int r1 = r7.requestedTabMaxWidth
            if (r1 <= 0) goto L_0x007c
            goto L_0x0089
        L_0x007c:
            float r0 = (float) r0
            android.content.Context r1 = r7.getContext()
            r3 = 56
            float r1 = com.google.android.material.internal.ViewUtils.dpToPx(r1, r3)
            float r0 = r0 - r1
            int r1 = (int) r0
        L_0x0089:
            r7.tabMaxWidth = r1
        L_0x008b:
            super.onMeasure(r8, r9)
            int r8 = r7.getChildCount()
            if (r8 != r4) goto L_0x00d9
            android.view.View r8 = r7.getChildAt(r2)
            int r0 = r7.mode
            if (r0 == 0) goto L_0x00ae
            if (r0 == r4) goto L_0x00a2
            r1 = 2
            if (r0 == r1) goto L_0x00ae
            goto L_0x00b9
        L_0x00a2:
            int r0 = r8.getMeasuredWidth()
            int r1 = r7.getMeasuredWidth()
            if (r0 == r1) goto L_0x00b9
        L_0x00ac:
            r2 = r4
            goto L_0x00b9
        L_0x00ae:
            int r0 = r8.getMeasuredWidth()
            int r1 = r7.getMeasuredWidth()
            if (r0 >= r1) goto L_0x00b9
            goto L_0x00ac
        L_0x00b9:
            if (r2 == 0) goto L_0x00d9
            int r0 = r7.getPaddingTop()
            int r1 = r7.getPaddingBottom()
            int r1 = r1 + r0
            android.view.ViewGroup$LayoutParams r0 = r8.getLayoutParams()
            int r0 = r0.height
            int r9 = android.view.ViewGroup.getChildMeasureSpec(r9, r1, r0)
            int r7 = r7.getMeasuredWidth()
            int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r5)
            r8.measure(r7, r9)
        L_0x00d9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.tabs.TabLayout.onMeasure(int, int):void");
    }

    public final void setElevation(float f) {
        super.setElevation(f);
        R$bool.setElevation(this, f);
    }

    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        addViewInternal(view);
    }
}
