package com.android.systemui.recents;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import androidx.preference.R$id;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.statusbar.phone.StatusBar;
import dagger.Lazy;
import java.util.Objects;
import java.util.Optional;

public final class ScreenPinningRequest implements View.OnClickListener, NavigationModeController.ModeChangedListener {
    public final AccessibilityManager mAccessibilityService;
    public final Context mContext;
    public int mNavBarMode = ((NavigationModeController) Dependency.get(NavigationModeController.class)).addListener(this);
    public RequestWindowView mRequestWindow;
    public final Lazy<Optional<StatusBar>> mStatusBarOptionalLazy;
    public final WindowManager mWindowManager;
    public int taskId;

    public class RequestWindowView extends FrameLayout {
        public final BroadcastDispatcher mBroadcastDispatcher = ((BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class));
        public final ColorDrawable mColor;
        public ValueAnimator mColorAnim;
        public ViewGroup mLayout;
        public final C10643 mReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                    RequestWindowView requestWindowView = RequestWindowView.this;
                    requestWindowView.post(requestWindowView.mUpdateLayoutRunnable);
                } else if (intent.getAction().equals("android.intent.action.USER_SWITCHED") || intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
                    ScreenPinningRequest screenPinningRequest = ScreenPinningRequest.this;
                    Objects.requireNonNull(screenPinningRequest);
                    RequestWindowView requestWindowView2 = screenPinningRequest.mRequestWindow;
                    if (requestWindowView2 != null) {
                        screenPinningRequest.mWindowManager.removeView(requestWindowView2);
                        screenPinningRequest.mRequestWindow = null;
                    }
                }
            }
        };
        public boolean mShowCancel;
        public final C10632 mUpdateLayoutRunnable = new Runnable() {
            public final void run() {
                int i;
                ViewGroup viewGroup = RequestWindowView.this.mLayout;
                if (viewGroup != null && viewGroup.getParent() != null) {
                    RequestWindowView requestWindowView = RequestWindowView.this;
                    ViewGroup viewGroup2 = requestWindowView.mLayout;
                    ScreenPinningRequest screenPinningRequest = ScreenPinningRequest.this;
                    int rotation = RequestWindowView.getRotation(requestWindowView.mContext);
                    Objects.requireNonNull(screenPinningRequest);
                    if (rotation == 3) {
                        i = 19;
                    } else if (rotation == 1) {
                        i = 21;
                    } else {
                        i = 81;
                    }
                    viewGroup2.setLayoutParams(new FrameLayout.LayoutParams(-2, -2, i));
                }
            }
        };

        public RequestWindowView(Context context, boolean z) {
            super(context);
            ColorDrawable colorDrawable = new ColorDrawable(0);
            this.mColor = colorDrawable;
            setClickable(true);
            setOnClickListener(ScreenPinningRequest.this);
            setBackground(colorDrawable);
            this.mShowCancel = z;
        }

        public final void onAttachedToWindow() {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ScreenPinningRequest.this.mWindowManager.getDefaultDisplay().getMetrics(displayMetrics);
            float f = displayMetrics.density;
            int rotation = getRotation(this.mContext);
            inflateView(rotation);
            int color = this.mContext.getColor(C1777R.color.screen_pinning_request_window_bg);
            if (ActivityManager.isHighEndGfx()) {
                this.mLayout.setAlpha(0.0f);
                if (rotation == 3) {
                    this.mLayout.setTranslationX(f * -96.0f);
                } else if (rotation == 1) {
                    this.mLayout.setTranslationX(f * 96.0f);
                } else {
                    this.mLayout.setTranslationY(f * 96.0f);
                }
                this.mLayout.animate().alpha(1.0f).translationX(0.0f).translationY(0.0f).setDuration(300).setInterpolator(new DecelerateInterpolator()).start();
                ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), new Object[]{0, Integer.valueOf(color)});
                this.mColorAnim = ofObject;
                ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        RequestWindowView.this.mColor.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    }
                });
                this.mColorAnim.setDuration(1000);
                this.mColorAnim.start();
            } else {
                this.mColor.setColor(color);
            }
            IntentFilter intentFilter = new IntentFilter("android.intent.action.CONFIGURATION_CHANGED");
            intentFilter.addAction("android.intent.action.USER_SWITCHED");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.mBroadcastDispatcher.registerReceiver(this.mReceiver, intentFilter);
        }

        public final void onDetachedFromWindow() {
            this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
        }

        public static int getRotation(Context context) {
            if (context.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                return 0;
            }
            return R$id.getRotation(context);
        }

        /* JADX WARNING: Removed duplicated region for block: B:29:0x00c6  */
        /* JADX WARNING: Removed duplicated region for block: B:30:0x00d4  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x00fd  */
        /* JADX WARNING: Removed duplicated region for block: B:36:0x00ff  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x0112  */
        /* JADX WARNING: Removed duplicated region for block: B:40:0x0116  */
        /* JADX WARNING: Removed duplicated region for block: B:51:0x016c  */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x0224  */
        /* JADX WARNING: Removed duplicated region for block: B:69:0x0248  */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x024b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void inflateView(int r11) {
            /*
                r10 = this;
                android.content.Context r0 = r10.getContext()
                r1 = 3
                r2 = 1
                if (r11 != r1) goto L_0x000c
                r3 = 2131624458(0x7f0e020a, float:1.8876096E38)
                goto L_0x0015
            L_0x000c:
                if (r11 != r2) goto L_0x0012
                r3 = 2131624457(0x7f0e0209, float:1.8876094E38)
                goto L_0x0015
            L_0x0012:
                r3 = 2131624453(0x7f0e0205, float:1.8876086E38)
            L_0x0015:
                r4 = 0
                android.view.View r0 = android.view.View.inflate(r0, r3, r4)
                android.view.ViewGroup r0 = (android.view.ViewGroup) r0
                r10.mLayout = r0
                r0.setClickable(r2)
                android.view.ViewGroup r0 = r10.mLayout
                r3 = 0
                r0.setLayoutDirection(r3)
                android.view.ViewGroup r0 = r10.mLayout
                r5 = 2131428758(0x7f0b0596, float:1.847917E38)
                android.view.View r0 = r0.findViewById(r5)
                r0.setLayoutDirection(r1)
                android.view.ViewGroup r0 = r10.mLayout
                r5 = 2131428747(0x7f0b058b, float:1.8479147E38)
                android.view.View r0 = r0.findViewById(r5)
                com.android.systemui.shared.system.WindowManagerWrapper r5 = com.android.systemui.shared.system.WindowManagerWrapper.sInstance
                com.android.systemui.recents.ScreenPinningRequest r6 = com.android.systemui.recents.ScreenPinningRequest.this
                int r6 = r6.mNavBarMode
                boolean r6 = androidx.leanback.R$color.isGesturalMode(r6)
                if (r6 != 0) goto L_0x00a9
                android.content.Context r6 = r10.mContext
                int r6 = r6.getDisplayId()
                java.util.Objects.requireNonNull(r5)
                android.view.IWindowManager r5 = android.view.WindowManagerGlobal.getWindowManagerService()     // Catch:{ RemoteException -> 0x005a }
                boolean r5 = r5.hasNavigationBar(r6)     // Catch:{ RemoteException -> 0x005a }
                goto L_0x005b
            L_0x005a:
                r5 = r3
            L_0x005b:
                if (r5 == 0) goto L_0x00a9
                android.content.Context r5 = r10.mContext
                boolean r5 = com.android.systemui.shared.recents.utilities.Utilities.isTablet(r5)
                if (r5 != 0) goto L_0x00a9
                r0.setLayoutDirection(r1)
                android.content.Context r5 = r10.mContext
                android.content.res.Resources r5 = r5.getResources()
                android.content.res.Configuration r5 = r5.getConfiguration()
                int r5 = r5.getLayoutDirection()
                if (r5 == r2) goto L_0x0079
                goto L_0x00ae
            L_0x0079:
                android.widget.LinearLayout r0 = (android.widget.LinearLayout) r0
                int r5 = r0.getOrientation()
                if (r5 != r2) goto L_0x00ae
                int r5 = r0.getChildCount()
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>(r5)
                r7 = r3
            L_0x008b:
                if (r7 >= r5) goto L_0x0097
                android.view.View r8 = r0.getChildAt(r7)
                r6.add(r8)
                int r7 = r7 + 1
                goto L_0x008b
            L_0x0097:
                r0.removeAllViews()
                int r5 = r5 - r2
            L_0x009b:
                if (r5 < 0) goto L_0x00ae
                java.lang.Object r7 = r6.get(r5)
                android.view.View r7 = (android.view.View) r7
                r0.addView(r7)
                int r5 = r5 + -1
                goto L_0x009b
            L_0x00a9:
                r5 = 8
                r0.setVisibility(r5)
            L_0x00ae:
                android.view.ViewGroup r0 = r10.mLayout
                r5 = 2131428754(0x7f0b0592, float:1.8479161E38)
                android.view.View r0 = r0.findViewById(r5)
                android.widget.Button r0 = (android.widget.Button) r0
                com.android.systemui.recents.ScreenPinningRequest r5 = com.android.systemui.recents.ScreenPinningRequest.this
                r0.setOnClickListener(r5)
                boolean r0 = r10.mShowCancel
                r5 = 2131428748(0x7f0b058c, float:1.847915E38)
                r6 = 4
                if (r0 == 0) goto L_0x00d4
                android.view.ViewGroup r0 = r10.mLayout
                android.view.View r0 = r0.findViewById(r5)
                android.widget.Button r0 = (android.widget.Button) r0
                com.android.systemui.recents.ScreenPinningRequest r5 = com.android.systemui.recents.ScreenPinningRequest.this
                r0.setOnClickListener(r5)
                goto L_0x00df
            L_0x00d4:
                android.view.ViewGroup r0 = r10.mLayout
                android.view.View r0 = r0.findViewById(r5)
                android.widget.Button r0 = (android.widget.Button) r0
                r0.setVisibility(r6)
            L_0x00df:
                com.android.systemui.recents.ScreenPinningRequest r0 = com.android.systemui.recents.ScreenPinningRequest.this
                dagger.Lazy<java.util.Optional<com.android.systemui.statusbar.phone.StatusBar>> r0 = r0.mStatusBarOptionalLazy
                java.lang.Object r0 = r0.get()
                java.util.Optional r0 = (java.util.Optional) r0
                com.android.wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda2 r5 = com.android.p012wm.shell.dagger.WMShellBaseModule$$ExternalSyntheticLambda2.INSTANCE$1
                java.util.Optional r0 = r0.map(r5)
                java.lang.Object r0 = r0.orElse(r4)
                com.android.systemui.navigationbar.NavigationBarView r0 = (com.android.systemui.navigationbar.NavigationBarView) r0
                if (r0 == 0) goto L_0x00ff
                boolean r4 = r0.isRecentsButtonVisible()
                if (r4 == 0) goto L_0x00ff
                r4 = r2
                goto L_0x0100
            L_0x00ff:
                r4 = r3
            L_0x0100:
                com.android.systemui.recents.ScreenPinningRequest r5 = com.android.systemui.recents.ScreenPinningRequest.this
                android.view.accessibility.AccessibilityManager r5 = r5.mAccessibilityService
                boolean r5 = r5.isTouchExplorationEnabled()
                com.android.systemui.recents.ScreenPinningRequest r7 = com.android.systemui.recents.ScreenPinningRequest.this
                int r7 = r7.mNavBarMode
                boolean r7 = androidx.leanback.R$color.isGesturalMode(r7)
                if (r7 == 0) goto L_0x0116
                r4 = 2131953182(0x7f13061e, float:1.9542828E38)
                goto L_0x016a
            L_0x0116:
                r7 = 2131428750(0x7f0b058e, float:1.8479153E38)
                r8 = 2131428751(0x7f0b058f, float:1.8479155E38)
                r9 = 2131428757(0x7f0b0595, float:1.8479167E38)
                if (r4 == 0) goto L_0x0146
                android.view.ViewGroup r4 = r10.mLayout
                android.view.View r4 = r4.findViewById(r9)
                r4.setVisibility(r3)
                android.view.ViewGroup r4 = r10.mLayout
                android.view.View r4 = r4.findViewById(r8)
                r4.setVisibility(r6)
                android.view.ViewGroup r4 = r10.mLayout
                android.view.View r4 = r4.findViewById(r7)
                r4.setVisibility(r6)
                if (r5 == 0) goto L_0x0142
                r4 = 2131953181(0x7f13061d, float:1.9542826E38)
                goto L_0x016a
            L_0x0142:
                r4 = 2131953180(0x7f13061c, float:1.9542824E38)
                goto L_0x016a
            L_0x0146:
                android.view.ViewGroup r4 = r10.mLayout
                android.view.View r4 = r4.findViewById(r9)
                r4.setVisibility(r6)
                android.view.ViewGroup r4 = r10.mLayout
                android.view.View r4 = r4.findViewById(r8)
                r4.setVisibility(r3)
                android.view.ViewGroup r4 = r10.mLayout
                android.view.View r4 = r4.findViewById(r7)
                r4.setVisibility(r3)
                if (r5 == 0) goto L_0x0167
                r4 = 2131953184(0x7f130620, float:1.9542832E38)
                goto L_0x016a
            L_0x0167:
                r4 = 2131953183(0x7f13061f, float:1.954283E38)
            L_0x016a:
                if (r0 == 0) goto L_0x01c0
                android.view.ViewGroup r7 = r10.mLayout
                r8 = 2131428746(0x7f0b058a, float:1.8479145E38)
                android.view.View r7 = r7.findViewById(r8)
                android.widget.ImageView r7 = (android.widget.ImageView) r7
                com.android.systemui.recents.OverviewProxyService r8 = r0.mOverviewProxyService
                boolean r8 = r8.shouldShowSwipeUpUI()
                if (r8 == 0) goto L_0x0183
                r8 = 2131232280(0x7f080618, float:1.8080665E38)
                goto L_0x0186
            L_0x0183:
                r8 = 2131232279(0x7f080617, float:1.8080663E38)
            L_0x0186:
                com.android.systemui.navigationbar.buttons.KeyButtonDrawable r8 = r0.getDrawable(r8)
                r0.orientBackButton(r8)
                r7.setImageDrawable(r8)
                android.view.ViewGroup r7 = r10.mLayout
                r8 = 2131428753(0x7f0b0591, float:1.847916E38)
                android.view.View r7 = r7.findViewById(r8)
                android.widget.ImageView r7 = (android.widget.ImageView) r7
                com.android.systemui.recents.OverviewProxyService r8 = r0.mOverviewProxyService
                boolean r8 = r8.shouldShowSwipeUpUI()
                if (r8 == 0) goto L_0x01ab
                r8 = 2131232283(0x7f08061b, float:1.808067E38)
                com.android.systemui.navigationbar.buttons.KeyButtonDrawable r8 = r0.getDrawable(r8)
                goto L_0x01b2
            L_0x01ab:
                r8 = 2131232282(0x7f08061a, float:1.8080669E38)
                com.android.systemui.navigationbar.buttons.KeyButtonDrawable r8 = r0.getDrawable(r8)
            L_0x01b2:
                boolean r0 = r0.mIsVertical
                if (r0 == 0) goto L_0x01b9
                r0 = 1119092736(0x42b40000, float:90.0)
                goto L_0x01ba
            L_0x01b9:
                r0 = 0
            L_0x01ba:
                r8.setRotation(r0)
                r7.setImageDrawable(r8)
            L_0x01c0:
                android.content.res.Resources r0 = r10.getResources()
                r7 = 2131166948(0x7f0706e4, float:1.7948156E38)
                int r0 = r0.getDimensionPixelSize(r7)
                android.text.SpannableStringBuilder r7 = new android.text.SpannableStringBuilder
                r7.<init>()
                android.content.Context r8 = r10.getContext()
                java.lang.CharSequence r4 = r8.getText(r4)
                android.text.style.BulletSpan r8 = new android.text.style.BulletSpan
                r8.<init>(r0)
                r7.append(r4, r8, r3)
                java.lang.String r4 = java.lang.System.lineSeparator()
                r7.append(r4)
                android.content.Context r4 = r10.getContext()
                r8 = 2131953186(0x7f130622, float:1.9542836E38)
                java.lang.CharSequence r4 = r4.getText(r8)
                android.text.style.BulletSpan r8 = new android.text.style.BulletSpan
                r8.<init>(r0)
                r7.append(r4, r8, r3)
                java.lang.String r4 = java.lang.System.lineSeparator()
                r7.append(r4)
                android.content.Context r4 = r10.getContext()
                r8 = 2131953179(0x7f13061b, float:1.9542822E38)
                java.lang.CharSequence r4 = r4.getText(r8)
                android.text.style.BulletSpan r8 = new android.text.style.BulletSpan
                r8.<init>(r0)
                r7.append(r4, r8, r3)
                android.view.ViewGroup r0 = r10.mLayout
                r4 = 2131428749(0x7f0b058d, float:1.8479151E38)
                android.view.View r0 = r0.findViewById(r4)
                android.widget.TextView r0 = (android.widget.TextView) r0
                r0.setText(r7)
                if (r5 == 0) goto L_0x0225
                r3 = r6
            L_0x0225:
                android.view.ViewGroup r0 = r10.mLayout
                r4 = 2131428743(0x7f0b0587, float:1.847914E38)
                android.view.View r0 = r0.findViewById(r4)
                r0.setVisibility(r3)
                android.view.ViewGroup r0 = r10.mLayout
                r4 = 2131428744(0x7f0b0588, float:1.8479141E38)
                android.view.View r0 = r0.findViewById(r4)
                r0.setVisibility(r3)
                android.view.ViewGroup r0 = r10.mLayout
                com.android.systemui.recents.ScreenPinningRequest r3 = com.android.systemui.recents.ScreenPinningRequest.this
                java.util.Objects.requireNonNull(r3)
                android.widget.FrameLayout$LayoutParams r3 = new android.widget.FrameLayout$LayoutParams
                if (r11 != r1) goto L_0x024b
                r11 = 19
                goto L_0x0252
            L_0x024b:
                if (r11 != r2) goto L_0x0250
                r11 = 21
                goto L_0x0252
            L_0x0250:
                r11 = 81
            L_0x0252:
                r1 = -2
                r3.<init>(r1, r1, r11)
                r10.addView(r0, r3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.recents.ScreenPinningRequest.RequestWindowView.inflateView(int):void");
        }

        public final void onConfigurationChanged() {
            removeAllViews();
            inflateView(getRotation(this.mContext));
        }
    }

    public ScreenPinningRequest(Context context, Lazy<Optional<StatusBar>> lazy) {
        this.mContext = context;
        this.mStatusBarOptionalLazy = lazy;
        this.mAccessibilityService = (AccessibilityManager) context.getSystemService("accessibility");
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        OverviewProxyService overviewProxyService = (OverviewProxyService) Dependency.get(OverviewProxyService.class);
    }

    public final void onClick(View view) {
        if (view.getId() == C1777R.C1779id.screen_pinning_ok_button || this.mRequestWindow == view) {
            try {
                ActivityTaskManager.getService().startSystemLockTaskMode(this.taskId);
            } catch (RemoteException unused) {
            }
        }
        RequestWindowView requestWindowView = this.mRequestWindow;
        if (requestWindowView != null) {
            this.mWindowManager.removeView(requestWindowView);
            this.mRequestWindow = null;
        }
    }

    public final void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }
}
