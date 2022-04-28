package com.android.systemui.toast;

import android.animation.Animator;
import android.content.Context;
import android.view.View;
import com.android.systemui.plugins.ToastPlugin;

public final class SystemUIToast implements ToastPlugin.Toast {
    public final Context mContext;
    public int mDefaultGravity;
    public int mDefaultY;
    public final Animator mInAnimator;
    public final Animator mOutAnimator;
    public final String mPackageName;
    public final ToastPlugin.Toast mPluginToast;
    public final CharSequence mText;
    public final View mToastView;
    public final int mUserId;

    /* JADX WARNING: Removed duplicated region for block: B:41:0x00fa  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00fe  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public SystemUIToast(android.view.LayoutInflater r17, android.content.Context r18, java.lang.CharSequence r19, com.android.systemui.plugins.ToastPlugin.Toast r20, java.lang.String r21, int r22, int r23) {
        /*
            r16 = this;
            r0 = r16
            r1 = r19
            r2 = r21
            r3 = r22
            r16.<init>()
            r4 = r18
            r0.mContext = r4
            r0.mText = r1
            r5 = r20
            r0.mPluginToast = r5
            r0.mPackageName = r2
            r0.mUserId = r3
            java.lang.String r6 = "SystemUIToast"
            boolean r7 = r16.isPluginToast()
            r8 = 2131429024(0x7f0b06a0, float:1.847971E38)
            r9 = 2131428102(0x7f0b0306, float:1.847784E38)
            r10 = 0
            r11 = 1
            r12 = 0
            if (r7 == 0) goto L_0x0036
            android.view.View r7 = r20.getView()
            if (r7 == 0) goto L_0x0036
            android.view.View r1 = r20.getView()
            goto L_0x0146
        L_0x0036:
            r5 = 2131624617(0x7f0e02a9, float:1.8876419E38)
            r7 = r17
            android.view.View r5 = r7.inflate(r5, r10)
            android.view.View r7 = r5.findViewById(r8)
            android.widget.TextView r7 = (android.widget.TextView) r7
            android.view.View r13 = r5.findViewById(r9)
            android.widget.ImageView r13 = (android.widget.ImageView) r13
            r7.setText(r1)
            android.content.pm.PackageManager r1 = r18.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0057 }
            android.content.pm.ApplicationInfo r10 = r1.getApplicationInfoAsUser(r2, r12, r3)     // Catch:{ NameNotFoundException -> 0x0057 }
            goto L_0x0073
        L_0x0057:
            java.lang.String r1 = "Package name not found package="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r2 = r0.mPackageName
            r1.append(r2)
            java.lang.String r2 = " user="
            r1.append(r2)
            int r2 = r0.mUserId
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            android.util.Log.e(r6, r1)
        L_0x0073:
            r1 = 8
            if (r10 == 0) goto L_0x008c
            int r2 = r10.targetSdkVersion
            r3 = 31
            if (r2 >= r3) goto L_0x008c
            r2 = 2147483647(0x7fffffff, float:NaN)
            r7.setMaxLines(r2)
            android.view.View r2 = r5.findViewById(r9)
            r2.setVisibility(r1)
            goto L_0x0145
        L_0x008c:
            android.content.Context r2 = r0.mContext
            java.lang.String r3 = r0.mPackageName
            int r4 = r0.mUserId
            android.content.Context r7 = r2.getApplicationContext()
            boolean r7 = r7 instanceof android.app.Application
            if (r7 != 0) goto L_0x009b
            goto L_0x00f7
        L_0x009b:
            android.content.pm.PackageManager r7 = r2.getPackageManager()     // Catch:{ NameNotFoundException -> 0x00db }
            r14 = 128(0x80, double:6.32E-322)
            android.content.pm.PackageManager$ApplicationInfoFlags r12 = android.content.pm.PackageManager.ApplicationInfoFlags.of(r14)     // Catch:{ NameNotFoundException -> 0x00db }
            android.content.pm.ApplicationInfo r12 = r7.getApplicationInfoAsUser(r3, r12, r4)     // Catch:{ NameNotFoundException -> 0x00db }
            if (r12 == 0) goto L_0x00f7
            int r14 = r12.flags     // Catch:{ NameNotFoundException -> 0x00db }
            r15 = r14 & 128(0x80, float:1.794E-43)
            if (r15 == 0) goto L_0x00b3
            r15 = r11
            goto L_0x00b4
        L_0x00b3:
            r15 = 0
        L_0x00b4:
            if (r15 == 0) goto L_0x00c1
            java.lang.String r14 = r12.packageName     // Catch:{ NameNotFoundException -> 0x00db }
            android.content.Intent r7 = r7.getLaunchIntentForPackage(r14)     // Catch:{ NameNotFoundException -> 0x00db }
            if (r7 == 0) goto L_0x00bf
            goto L_0x00c9
        L_0x00bf:
            r11 = 0
            goto L_0x00c9
        L_0x00c1:
            r7 = r14 & 1
            if (r7 == 0) goto L_0x00c7
            r7 = r11
            goto L_0x00c8
        L_0x00c7:
            r7 = 0
        L_0x00c8:
            r11 = r11 ^ r7
        L_0x00c9:
            if (r11 != 0) goto L_0x00cc
            goto L_0x00f7
        L_0x00cc:
            android.util.IconDrawableFactory r2 = android.util.IconDrawableFactory.newInstance(r2)     // Catch:{ NameNotFoundException -> 0x00db }
            int r7 = r12.uid     // Catch:{ NameNotFoundException -> 0x00db }
            int r7 = android.os.UserHandle.getUserId(r7)     // Catch:{ NameNotFoundException -> 0x00db }
            android.graphics.drawable.Drawable r2 = r2.getBadgedIcon(r12, r7)     // Catch:{ NameNotFoundException -> 0x00db }
            goto L_0x00f8
        L_0x00db:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r7 = "Couldn't find application info for packageName="
            r2.append(r7)
            r2.append(r3)
            java.lang.String r3 = " userId="
            r2.append(r3)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            android.util.Log.e(r6, r2)
        L_0x00f7:
            r2 = 0
        L_0x00f8:
            if (r2 != 0) goto L_0x00fe
            r13.setVisibility(r1)
            goto L_0x0145
        L_0x00fe:
            r13.setImageDrawable(r2)
            if (r10 != 0) goto L_0x0119
            java.lang.String r1 = "No appInfo for pkg="
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.String r2 = r0.mPackageName
            r1.append(r2)
            java.lang.String r2 = " usr="
            r1.append(r2)
            int r2 = r0.mUserId
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(r1, r2, r6)
            goto L_0x0145
        L_0x0119:
            int r1 = r10.labelRes
            if (r1 == 0) goto L_0x0145
            android.content.Context r1 = r0.mContext     // Catch:{ NameNotFoundException -> 0x0140 }
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0140 }
            android.content.res.Configuration r2 = new android.content.res.Configuration     // Catch:{ NameNotFoundException -> 0x0140 }
            android.content.Context r3 = r0.mContext     // Catch:{ NameNotFoundException -> 0x0140 }
            android.content.res.Resources r3 = r3.getResources()     // Catch:{ NameNotFoundException -> 0x0140 }
            android.content.res.Configuration r3 = r3.getConfiguration()     // Catch:{ NameNotFoundException -> 0x0140 }
            r2.<init>(r3)     // Catch:{ NameNotFoundException -> 0x0140 }
            android.content.res.Resources r1 = r1.getResourcesForApplication(r10, r2)     // Catch:{ NameNotFoundException -> 0x0140 }
            int r2 = r10.labelRes     // Catch:{ NameNotFoundException -> 0x0140 }
            java.lang.String r1 = r1.getString(r2)     // Catch:{ NameNotFoundException -> 0x0140 }
            r13.setContentDescription(r1)     // Catch:{ NameNotFoundException -> 0x0140 }
            goto L_0x0145
        L_0x0140:
            java.lang.String r1 = "Cannot find application resources for icon label."
            android.util.Log.d(r6, r1)
        L_0x0145:
            r1 = r5
        L_0x0146:
            r0.mToastView = r1
            boolean r2 = r16.isPluginToast()
            java.lang.String r3 = "alpha"
            java.lang.String r4 = "scaleY"
            java.lang.String r5 = "scaleX"
            r6 = 0
            r7 = 1065353216(0x3f800000, float:1.0)
            r10 = 2
            if (r2 == 0) goto L_0x016a
            com.android.systemui.plugins.ToastPlugin$Toast r2 = r0.mPluginToast
            android.animation.Animator r2 = r2.getInAnimation()
            if (r2 == 0) goto L_0x016a
            com.android.systemui.plugins.ToastPlugin$Toast r2 = r0.mPluginToast
            android.animation.Animator r2 = r2.getInAnimation()
            goto L_0x0200
        L_0x016a:
            android.view.View r2 = r1.findViewById(r9)
            android.view.View r8 = r1.findViewById(r8)
            if (r2 == 0) goto L_0x01ff
            if (r8 != 0) goto L_0x0178
            goto L_0x01ff
        L_0x0178:
            android.view.animation.LinearInterpolator r9 = new android.view.animation.LinearInterpolator
            r9.<init>()
            android.view.animation.PathInterpolator r11 = new android.view.animation.PathInterpolator
            r11.<init>(r6, r6, r6, r7)
            float[] r7 = new float[r10]
            r7 = {1063675494, 1065353216} // fill-array
            android.animation.ObjectAnimator r7 = android.animation.ObjectAnimator.ofFloat(r1, r5, r7)
            r7.setInterpolator(r11)
            r12 = 333(0x14d, double:1.645E-321)
            r7.setDuration(r12)
            float[] r14 = new float[r10]
            r14 = {1063675494, 1065353216} // fill-array
            android.animation.ObjectAnimator r14 = android.animation.ObjectAnimator.ofFloat(r1, r4, r14)
            r14.setInterpolator(r11)
            r14.setDuration(r12)
            float[] r11 = new float[r10]
            r11 = {0, 1065353216} // fill-array
            android.animation.ObjectAnimator r11 = android.animation.ObjectAnimator.ofFloat(r1, r3, r11)
            r11.setInterpolator(r9)
            r12 = 66
            r11.setDuration(r12)
            r8.setAlpha(r6)
            float[] r12 = new float[r10]
            r12 = {0, 1065353216} // fill-array
            android.animation.ObjectAnimator r8 = android.animation.ObjectAnimator.ofFloat(r8, r3, r12)
            r8.setInterpolator(r9)
            r12 = 283(0x11b, double:1.4E-321)
            r8.setDuration(r12)
            r12 = 50
            r8.setStartDelay(r12)
            r2.setAlpha(r6)
            float[] r10 = new float[r10]
            r10 = {0, 1065353216} // fill-array
            android.animation.ObjectAnimator r2 = android.animation.ObjectAnimator.ofFloat(r2, r3, r10)
            r2.setInterpolator(r9)
            r9 = 283(0x11b, double:1.4E-321)
            r2.setDuration(r9)
            r2.setStartDelay(r12)
            android.animation.AnimatorSet r9 = new android.animation.AnimatorSet
            r9.<init>()
            r10 = 5
            android.animation.Animator[] r10 = new android.animation.Animator[r10]
            r12 = 0
            r10[r12] = r7
            r7 = 1
            r10[r7] = r14
            r7 = 2
            r10[r7] = r11
            r7 = 3
            r10[r7] = r8
            r7 = 4
            r10[r7] = r2
            r9.playTogether(r10)
            r2 = r9
            goto L_0x0200
        L_0x01ff:
            r2 = 0
        L_0x0200:
            r0.mInAnimator = r2
            boolean r2 = r16.isPluginToast()
            if (r2 == 0) goto L_0x0218
            com.android.systemui.plugins.ToastPlugin$Toast r2 = r0.mPluginToast
            android.animation.Animator r2 = r2.getOutAnimation()
            if (r2 == 0) goto L_0x0218
            com.android.systemui.plugins.ToastPlugin$Toast r1 = r0.mPluginToast
            android.animation.Animator r1 = r1.getOutAnimation()
            goto L_0x02ce
        L_0x0218:
            r2 = 2131428102(0x7f0b0306, float:1.847784E38)
            android.view.View r2 = r1.findViewById(r2)
            r7 = 2131429024(0x7f0b06a0, float:1.847971E38)
            android.view.View r7 = r1.findViewById(r7)
            if (r2 == 0) goto L_0x02cd
            if (r7 != 0) goto L_0x022c
            goto L_0x02cd
        L_0x022c:
            android.view.animation.LinearInterpolator r8 = new android.view.animation.LinearInterpolator
            r8.<init>()
            android.view.animation.PathInterpolator r9 = new android.view.animation.PathInterpolator
            r10 = 1050253722(0x3e99999a, float:0.3)
            r11 = 1065353216(0x3f800000, float:1.0)
            r9.<init>(r10, r6, r11, r11)
            r10 = 2
            float[] r11 = new float[r10]
            r11 = {1065353216, 1063675494} // fill-array
            android.animation.ObjectAnimator r5 = android.animation.ObjectAnimator.ofFloat(r1, r5, r11)
            r5.setInterpolator(r9)
            r11 = 250(0xfa, double:1.235E-321)
            r5.setDuration(r11)
            float[] r13 = new float[r10]
            r13 = {1065353216, 1063675494} // fill-array
            android.animation.ObjectAnimator r4 = android.animation.ObjectAnimator.ofFloat(r1, r4, r13)
            r4.setInterpolator(r9)
            r4.setDuration(r11)
            float[] r9 = new float[r10]
            float r11 = r1.getElevation()
            r12 = 0
            r9[r12] = r11
            r11 = 1
            r9[r11] = r6
            java.lang.String r6 = "elevation"
            android.animation.ObjectAnimator r6 = android.animation.ObjectAnimator.ofFloat(r1, r6, r9)
            r6.setInterpolator(r8)
            r11 = 40
            r6.setDuration(r11)
            r11 = 150(0x96, double:7.4E-322)
            r6.setStartDelay(r11)
            float[] r9 = new float[r10]
            r9 = {1065353216, 0} // fill-array
            android.animation.ObjectAnimator r1 = android.animation.ObjectAnimator.ofFloat(r1, r3, r9)
            r1.setInterpolator(r8)
            r13 = 100
            r1.setDuration(r13)
            r1.setStartDelay(r11)
            float[] r9 = new float[r10]
            r9 = {1065353216, 0} // fill-array
            android.animation.ObjectAnimator r7 = android.animation.ObjectAnimator.ofFloat(r7, r3, r9)
            r7.setInterpolator(r8)
            r11 = 166(0xa6, double:8.2E-322)
            r7.setDuration(r11)
            float[] r9 = new float[r10]
            r9 = {1065353216, 0} // fill-array
            android.animation.ObjectAnimator r2 = android.animation.ObjectAnimator.ofFloat(r2, r3, r9)
            r2.setInterpolator(r8)
            r2.setDuration(r11)
            android.animation.AnimatorSet r3 = new android.animation.AnimatorSet
            r3.<init>()
            r8 = 6
            android.animation.Animator[] r8 = new android.animation.Animator[r8]
            r9 = 0
            r8[r9] = r5
            r5 = 1
            r8[r5] = r4
            r8[r10] = r6
            r4 = 3
            r8[r4] = r1
            r1 = 4
            r8[r1] = r7
            r1 = 5
            r8[r1] = r2
            r3.playTogether(r8)
            r1 = r3
            goto L_0x02ce
        L_0x02cd:
            r1 = 0
        L_0x02ce:
            r0.mOutAnimator = r1
            r1 = r23
            r0.onOrientationChange(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.toast.SystemUIToast.<init>(android.view.LayoutInflater, android.content.Context, java.lang.CharSequence, com.android.systemui.plugins.ToastPlugin$Toast, java.lang.String, int, int):void");
    }

    public final boolean isPluginToast() {
        if (this.mPluginToast != null) {
            return true;
        }
        return false;
    }

    public final void onOrientationChange(int i) {
        ToastPlugin.Toast toast = this.mPluginToast;
        if (toast != null) {
            toast.onOrientationChange(i);
        }
        this.mDefaultY = this.mContext.getResources().getDimensionPixelSize(17105612);
        this.mDefaultGravity = this.mContext.getResources().getInteger(17694947);
    }

    public final Integer getGravity() {
        if (!isPluginToast() || this.mPluginToast.getGravity() == null) {
            return Integer.valueOf(this.mDefaultGravity);
        }
        return this.mPluginToast.getGravity();
    }

    public final Integer getHorizontalMargin() {
        if (!isPluginToast() || this.mPluginToast.getHorizontalMargin() == null) {
            return 0;
        }
        return this.mPluginToast.getHorizontalMargin();
    }

    public final Integer getVerticalMargin() {
        if (!isPluginToast() || this.mPluginToast.getVerticalMargin() == null) {
            return 0;
        }
        return this.mPluginToast.getVerticalMargin();
    }

    public final Integer getXOffset() {
        if (!isPluginToast() || this.mPluginToast.getXOffset() == null) {
            return 0;
        }
        return this.mPluginToast.getXOffset();
    }

    public final Integer getYOffset() {
        if (!isPluginToast() || this.mPluginToast.getYOffset() == null) {
            return Integer.valueOf(this.mDefaultY);
        }
        return this.mPluginToast.getYOffset();
    }

    public final Animator getInAnimation() {
        return this.mInAnimator;
    }

    public final Animator getOutAnimation() {
        return this.mOutAnimator;
    }

    public final View getView() {
        return this.mToastView;
    }
}
