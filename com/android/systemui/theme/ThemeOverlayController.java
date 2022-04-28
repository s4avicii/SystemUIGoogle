package com.android.systemui.theme;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.om.FabricatedOverlay;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline1;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import com.android.internal.graphics.ColorUtils;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.systemui.CoreStartable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.monet.ColorScheme;
import com.android.systemui.monet.Style;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.settings.SecureSettings;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.Executor;
import org.json.JSONObject;

public class ThemeOverlayController extends CoreStartable {
    public boolean mAcceptColorEvents = true;
    public final Executor mBgExecutor;
    public final Handler mBgHandler;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C16634 mBroadcastReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            boolean equals = "android.intent.action.MANAGED_PROFILE_ADDED".equals(intent.getAction());
            boolean isManagedProfile = ThemeOverlayController.this.mUserManager.isManagedProfile(intent.getIntExtra("android.intent.extra.user_handle", 0));
            if (equals) {
                if (ThemeOverlayController.this.mDeviceProvisionedController.isCurrentUserSetup() || !isManagedProfile) {
                    Log.d("ThemeOverlayController", "Updating overlays for user switch / profile added.");
                    ThemeOverlayController.this.reevaluateSystemTheme(true);
                    return;
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("User setup not finished when ");
                m.append(intent.getAction());
                m.append(" was received. Deferring... Managed profile? ");
                m.append(isManagedProfile);
                Log.i("ThemeOverlayController", m.toString());
            } else if (!"android.intent.action.WALLPAPER_CHANGED".equals(intent.getAction())) {
            } else {
                if (intent.getBooleanExtra("android.service.wallpaper.extra.FROM_FOREGROUND_APP", false)) {
                    ThemeOverlayController.this.mAcceptColorEvents = true;
                    Log.i("ThemeOverlayController", "Wallpaper changed, allowing color events again");
                    return;
                }
                StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Wallpaper changed from background app, keep deferring color events. Accepting: ");
                m2.append(ThemeOverlayController.this.mAcceptColorEvents);
                Log.i("ThemeOverlayController", m2.toString());
            }
        }
    };
    public ColorScheme mColorScheme;
    public final SparseArray<WallpaperColors> mCurrentColors = new SparseArray<>();
    public boolean mDeferredThemeEvaluation;
    public final SparseArray<WallpaperColors> mDeferredWallpaperColors = new SparseArray<>();
    public final SparseIntArray mDeferredWallpaperColorsFlags = new SparseIntArray();
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final C16601 mDeviceProvisionedListener = new DeviceProvisionedController.DeviceProvisionedListener() {
        public final void onUserSetupChanged() {
            if (ThemeOverlayController.this.mDeviceProvisionedController.isCurrentUserSetup() && ThemeOverlayController.this.mDeferredThemeEvaluation) {
                Log.i("ThemeOverlayController", "Applying deferred theme");
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                themeOverlayController.mDeferredThemeEvaluation = false;
                themeOverlayController.reevaluateSystemTheme(true);
            }
        }
    };
    public final boolean mIsMonetEnabled;
    public final Executor mMainExecutor;
    public int mMainWallpaperColor = 0;
    public boolean mNeedsOverlayCreation;
    public FabricatedOverlay mNeutralOverlay;
    public final C16612 mOnColorsChangedListener = new WallpaperManager.OnColorsChangedListener() {
        public final void onColorsChanged(WallpaperColors wallpaperColors, int i) {
            throw new IllegalStateException("This should never be invoked, all messages should arrive on the overload that has a user id");
        }

        public final void onColorsChanged(WallpaperColors wallpaperColors, int i, int i2) {
            boolean z = i2 == ThemeOverlayController.this.mUserTracker.getUserId();
            if (z) {
                ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                if (!themeOverlayController.mAcceptColorEvents) {
                    WakefulnessLifecycle wakefulnessLifecycle = themeOverlayController.mWakefulnessLifecycle;
                    Objects.requireNonNull(wakefulnessLifecycle);
                    if (wakefulnessLifecycle.mWakefulness != 0) {
                        ThemeOverlayController.this.mDeferredWallpaperColors.put(i2, wallpaperColors);
                        ThemeOverlayController.this.mDeferredWallpaperColorsFlags.put(i2, i);
                        Log.i("ThemeOverlayController", "colors received; processing deferred until screen off: " + wallpaperColors + " user: " + i2);
                        return;
                    }
                }
            }
            if (z && wallpaperColors != null) {
                ThemeOverlayController themeOverlayController2 = ThemeOverlayController.this;
                themeOverlayController2.mAcceptColorEvents = false;
                themeOverlayController2.mDeferredWallpaperColors.put(i2, (Object) null);
                ThemeOverlayController.this.mDeferredWallpaperColorsFlags.put(i2, 0);
            }
            ThemeOverlayController.m255$$Nest$mhandleWallpaperColors(ThemeOverlayController.this, wallpaperColors, i, i2);
        }
    };
    public FabricatedOverlay mSecondaryOverlay;
    public final SecureSettings mSecureSettings;
    public boolean mSkipSettingChange;
    public final ThemeOverlayApplier mThemeManager;
    public Style mThemeStyle = Style.TONAL_SPOT;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final C16623 mUserTrackerCallback = new UserTracker.Callback() {
        public final void onUserChanged(int i) {
            boolean isManagedProfile = ThemeOverlayController.this.mUserManager.isManagedProfile(i);
            if (ThemeOverlayController.this.mDeviceProvisionedController.isCurrentUserSetup() || !isManagedProfile) {
                Log.d("ThemeOverlayController", "Updating overlays for user switch / profile added.");
                ThemeOverlayController.this.reevaluateSystemTheme(true);
                return;
            }
            Log.i("ThemeOverlayController", "User setup not finished when new user event was received. Deferring... Managed profile? " + isManagedProfile);
        }
    };
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WallpaperManager mWallpaperManager;

    public static boolean isSeedColorSet(JSONObject jSONObject, WallpaperColors wallpaperColors) {
        String str;
        if (wallpaperColors == null || (str = (String) jSONObject.opt("android.theme.customization.system_palette")) == null) {
            return false;
        }
        if (!str.startsWith("#")) {
            str = SupportMenuInflater$$ExternalSyntheticOutline0.m4m("#", str);
        }
        int parseColor = Color.parseColor(str);
        for (Integer intValue : ColorScheme.Companion.getSeedColors(wallpaperColors)) {
            if (intValue.intValue() == parseColor) {
                DialogFragment$$ExternalSyntheticOutline0.m17m("Same as previous set system palette: ", str, "ThemeOverlayController");
                return true;
            }
        }
        return false;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mSystemColors=");
        m.append(this.mCurrentColors);
        printWriter.println(m.toString());
        printWriter.println("mMainWallpaperColor=" + Integer.toHexString(this.mMainWallpaperColor));
        printWriter.println("mSecondaryOverlay=" + this.mSecondaryOverlay);
        printWriter.println("mNeutralOverlay=" + this.mNeutralOverlay);
        StringBuilder sb = new StringBuilder();
        sb.append("mIsMonetEnabled=");
        StringBuilder m2 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb, this.mIsMonetEnabled, printWriter, "mColorScheme=");
        m2.append(this.mColorScheme);
        printWriter.println(m2.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append("mNeedsOverlayCreation=");
        StringBuilder m3 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(sb2, this.mNeedsOverlayCreation, printWriter, "mAcceptColorEvents="), this.mAcceptColorEvents, printWriter, "mDeferredThemeEvaluation="), this.mDeferredThemeEvaluation, printWriter, "mThemeStyle=");
        m3.append(this.mThemeStyle);
        printWriter.println(m3.toString());
    }

    public final FabricatedOverlay getOverlay(int i, int i2, Style style) {
        ArrayList arrayList;
        String str;
        String str2;
        int i3 = this.mContext.getResources().getConfiguration().uiMode;
        ColorScheme colorScheme = new ColorScheme(i, style);
        this.mColorScheme = colorScheme;
        if (i2 == 1) {
            arrayList = new ArrayList();
            arrayList.addAll(colorScheme.accent1);
            arrayList.addAll(colorScheme.accent2);
            arrayList.addAll(colorScheme.accent3);
        } else {
            arrayList = new ArrayList();
            arrayList.addAll(colorScheme.neutral1);
            arrayList.addAll(colorScheme.neutral2);
        }
        if (i2 == 1) {
            str = "accent";
        } else {
            str = "neutral";
        }
        ColorScheme colorScheme2 = this.mColorScheme;
        Objects.requireNonNull(colorScheme2);
        int size = colorScheme2.accent1.size();
        FabricatedOverlay.Builder builder = new FabricatedOverlay.Builder(ThemeOverlayApplier.SYSUI_PACKAGE, str, ThemeOverlayApplier.ANDROID_PACKAGE);
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            int i5 = i4 % size;
            int i6 = (i4 / size) + 1;
            if (i5 == 0) {
                str2 = "android:color/system_" + str + i6 + "_10";
            } else if (i5 != 1) {
                StringBuilder sb = new StringBuilder();
                sb.append("android:color/system_");
                sb.append(str);
                sb.append(i6);
                sb.append("_");
                sb.append(i5 - 1);
                sb.append("00");
                str2 = sb.toString();
            } else {
                str2 = "android:color/system_" + str + i6 + "_50";
            }
            builder.setResourceValue(str2, 28, ColorUtils.setAlphaComponent(((Integer) arrayList.get(i4)).intValue(), 255));
        }
        return builder.build();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:25|26) */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        r0 = com.android.systemui.monet.Style.TONAL_SPOT;
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x00cf */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void reevaluateSystemTheme(boolean r12) {
        /*
            r11 = this;
            android.util.SparseArray<android.app.WallpaperColors> r0 = r11.mCurrentColors
            com.android.systemui.settings.UserTracker r1 = r11.mUserTracker
            int r1 = r1.getUserId()
            java.lang.Object r0 = r0.get(r1)
            android.app.WallpaperColors r0 = (android.app.WallpaperColors) r0
            r1 = 0
            if (r0 != 0) goto L_0x0013
            r0 = r1
            goto L_0x0027
        L_0x0013:
            java.util.List r0 = com.android.systemui.monet.ColorScheme.Companion.getSeedColors(r0)
            boolean r2 = r0.isEmpty()
            if (r2 != 0) goto L_0x020e
            java.lang.Object r0 = r0.get(r1)
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
        L_0x0027:
            int r2 = r11.mMainWallpaperColor
            if (r2 != r0) goto L_0x002e
            if (r12 != 0) goto L_0x002e
            return
        L_0x002e:
            r11.mMainWallpaperColor = r0
            boolean r12 = r11.mIsMonetEnabled
            java.lang.String r2 = "ThemeOverlayController"
            r3 = 1
            if (r12 == 0) goto L_0x0067
            com.android.systemui.monet.Style r12 = r11.mThemeStyle
            android.content.om.FabricatedOverlay r12 = r11.getOverlay(r0, r3, r12)
            r11.mSecondaryOverlay = r12
            int r12 = r11.mMainWallpaperColor
            com.android.systemui.monet.Style r0 = r11.mThemeStyle
            android.content.om.FabricatedOverlay r12 = r11.getOverlay(r12, r1, r0)
            r11.mNeutralOverlay = r12
            r11.mNeedsOverlayCreation = r3
            java.lang.String r12 = "fetched overlays. accent: "
            java.lang.StringBuilder r12 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r12)
            android.content.om.FabricatedOverlay r0 = r11.mSecondaryOverlay
            r12.append(r0)
            java.lang.String r0 = " neutral: "
            r12.append(r0)
            android.content.om.FabricatedOverlay r0 = r11.mNeutralOverlay
            r12.append(r0)
            java.lang.String r12 = r12.toString()
            android.util.Log.d(r2, r12)
        L_0x0067:
            java.lang.String r12 = "#"
            com.android.systemui.settings.UserTracker r0 = r11.mUserTracker
            int r8 = r0.getUserId()
            com.android.systemui.util.settings.SecureSettings r0 = r11.mSecureSettings
            java.lang.String r4 = "theme_customization_overlay_packages"
            java.lang.String r0 = r0.getStringForUser(r4, r8)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "updateThemeOverlays. Setting: "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r2, r4)
            android.util.ArrayMap r6 = new android.util.ArrayMap
            r6.<init>()
            com.android.systemui.monet.Style r4 = r11.mThemeStyle
            boolean r5 = android.text.TextUtils.isEmpty(r0)
            if (r5 != 0) goto L_0x00d9
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00d3 }
            r5.<init>(r0)     // Catch:{ JSONException -> 0x00d3 }
            java.util.ArrayList r0 = com.android.systemui.theme.ThemeOverlayApplier.THEME_CATEGORIES     // Catch:{ JSONException -> 0x00d3 }
            java.util.Iterator r0 = r0.iterator()     // Catch:{ JSONException -> 0x00d3 }
        L_0x00a5:
            boolean r7 = r0.hasNext()     // Catch:{ JSONException -> 0x00d3 }
            if (r7 == 0) goto L_0x00c4
            java.lang.Object r7 = r0.next()     // Catch:{ JSONException -> 0x00d3 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ JSONException -> 0x00d3 }
            boolean r9 = r5.has(r7)     // Catch:{ JSONException -> 0x00d3 }
            if (r9 == 0) goto L_0x00a5
            android.content.om.OverlayIdentifier r9 = new android.content.om.OverlayIdentifier     // Catch:{ JSONException -> 0x00d3 }
            java.lang.String r10 = r5.getString(r7)     // Catch:{ JSONException -> 0x00d3 }
            r9.<init>(r10)     // Catch:{ JSONException -> 0x00d3 }
            r6.put(r7, r9)     // Catch:{ JSONException -> 0x00d3 }
            goto L_0x00a5
        L_0x00c4:
            java.lang.String r0 = "android.theme.customization.theme_style"
            java.lang.String r0 = r5.getString(r0)     // Catch:{ IllegalArgumentException -> 0x00cf }
            com.android.systemui.monet.Style r0 = com.android.systemui.monet.Style.valueOf(r0)     // Catch:{ IllegalArgumentException -> 0x00cf }
            goto L_0x00d1
        L_0x00cf:
            com.android.systemui.monet.Style r0 = com.android.systemui.monet.Style.TONAL_SPOT     // Catch:{ JSONException -> 0x00d3 }
        L_0x00d1:
            r4 = r0
            goto L_0x00d9
        L_0x00d3:
            r0 = move-exception
            java.lang.String r5 = "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES."
            android.util.Log.i(r2, r5, r0)
        L_0x00d9:
            boolean r0 = r11.mIsMonetEnabled
            if (r0 == 0) goto L_0x00f7
            com.android.systemui.monet.Style r0 = r11.mThemeStyle
            if (r4 == r0) goto L_0x00f7
            r11.mThemeStyle = r4
            int r0 = r11.mMainWallpaperColor
            android.content.om.FabricatedOverlay r0 = r11.getOverlay(r0, r1, r4)
            r11.mNeutralOverlay = r0
            int r0 = r11.mMainWallpaperColor
            com.android.systemui.monet.Style r4 = r11.mThemeStyle
            android.content.om.FabricatedOverlay r0 = r11.getOverlay(r0, r3, r4)
            r11.mSecondaryOverlay = r0
            r11.mNeedsOverlayCreation = r3
        L_0x00f7:
            java.lang.String r0 = "android.theme.customization.system_palette"
            java.lang.Object r4 = r6.get(r0)
            android.content.om.OverlayIdentifier r4 = (android.content.om.OverlayIdentifier) r4
            boolean r5 = r11.mIsMonetEnabled
            java.lang.String r7 = "android.theme.customization.accent_color"
            if (r5 == 0) goto L_0x015d
            if (r4 == 0) goto L_0x015d
            java.lang.String r5 = r4.getPackageName()
            if (r5 == 0) goto L_0x015d
            java.lang.String r5 = r4.getPackageName()     // Catch:{ Exception -> 0x0147 }
            java.lang.String r5 = r5.toLowerCase()     // Catch:{ Exception -> 0x0147 }
            boolean r9 = r5.startsWith(r12)     // Catch:{ Exception -> 0x0147 }
            if (r9 != 0) goto L_0x012a
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0147 }
            r9.<init>()     // Catch:{ Exception -> 0x0147 }
            r9.append(r12)     // Catch:{ Exception -> 0x0147 }
            r9.append(r5)     // Catch:{ Exception -> 0x0147 }
            java.lang.String r5 = r9.toString()     // Catch:{ Exception -> 0x0147 }
        L_0x012a:
            int r12 = android.graphics.Color.parseColor(r5)     // Catch:{ Exception -> 0x0147 }
            com.android.systemui.monet.Style r5 = r11.mThemeStyle     // Catch:{ Exception -> 0x0147 }
            android.content.om.FabricatedOverlay r5 = r11.getOverlay(r12, r1, r5)     // Catch:{ Exception -> 0x0147 }
            r11.mNeutralOverlay = r5     // Catch:{ Exception -> 0x0147 }
            com.android.systemui.monet.Style r5 = r11.mThemeStyle     // Catch:{ Exception -> 0x0147 }
            android.content.om.FabricatedOverlay r12 = r11.getOverlay(r12, r3, r5)     // Catch:{ Exception -> 0x0147 }
            r11.mSecondaryOverlay = r12     // Catch:{ Exception -> 0x0147 }
            r11.mNeedsOverlayCreation = r3     // Catch:{ Exception -> 0x0147 }
            r6.remove(r0)     // Catch:{ Exception -> 0x0147 }
            r6.remove(r7)     // Catch:{ Exception -> 0x0147 }
            goto L_0x0169
        L_0x0147:
            r12 = move-exception
            java.lang.String r5 = "Invalid color definition: "
            java.lang.StringBuilder r5 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r5)
            java.lang.String r4 = r4.getPackageName()
            r5.append(r4)
            java.lang.String r4 = r5.toString()
            android.util.Log.w(r2, r4, r12)
            goto L_0x0169
        L_0x015d:
            boolean r12 = r11.mIsMonetEnabled
            if (r12 != 0) goto L_0x0169
            if (r4 == 0) goto L_0x0169
            r6.remove(r0)     // Catch:{ NumberFormatException -> 0x0169 }
            r6.remove(r7)     // Catch:{ NumberFormatException -> 0x0169 }
        L_0x0169:
            boolean r12 = r6.containsKey(r0)
            if (r12 != 0) goto L_0x017a
            android.content.om.FabricatedOverlay r12 = r11.mNeutralOverlay
            if (r12 == 0) goto L_0x017a
            android.content.om.OverlayIdentifier r12 = r12.getIdentifier()
            r6.put(r0, r12)
        L_0x017a:
            boolean r12 = r6.containsKey(r7)
            if (r12 != 0) goto L_0x018b
            android.content.om.FabricatedOverlay r12 = r11.mSecondaryOverlay
            if (r12 == 0) goto L_0x018b
            android.content.om.OverlayIdentifier r12 = r12.getIdentifier()
            r6.put(r7, r12)
        L_0x018b:
            java.util.HashSet r9 = new java.util.HashSet
            r9.<init>()
            android.os.UserManager r12 = r11.mUserManager
            java.util.List r12 = r12.getEnabledProfiles(r8)
            java.util.Iterator r12 = r12.iterator()
        L_0x019a:
            boolean r0 = r12.hasNext()
            if (r0 == 0) goto L_0x01b4
            java.lang.Object r0 = r12.next()
            android.content.pm.UserInfo r0 = (android.content.pm.UserInfo) r0
            boolean r4 = r0.isManagedProfile()
            if (r4 == 0) goto L_0x019a
            android.os.UserHandle r0 = r0.getUserHandle()
            r9.add(r0)
            goto L_0x019a
        L_0x01b4:
            java.lang.String r12 = "Applying overlays: "
            java.lang.StringBuilder r12 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r12)
            java.util.Set r0 = r6.keySet()
            java.util.stream.Stream r0 = r0.stream()
            com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda0 r4 = new com.android.systemui.theme.ThemeOverlayController$$ExternalSyntheticLambda0
            r4.<init>(r6)
            java.util.stream.Stream r0 = r0.map(r4)
            java.lang.String r4 = ", "
            java.util.stream.Collector r4 = java.util.stream.Collectors.joining(r4)
            java.lang.Object r0 = r0.collect(r4)
            java.lang.String r0 = (java.lang.String) r0
            androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline2.m15m(r12, r0, r2)
            boolean r12 = r11.mNeedsOverlayCreation
            if (r12 == 0) goto L_0x01fc
            r11.mNeedsOverlayCreation = r1
            com.android.systemui.theme.ThemeOverlayApplier r5 = r11.mThemeManager
            r12 = 2
            android.content.om.FabricatedOverlay[] r7 = new android.content.om.FabricatedOverlay[r12]
            android.content.om.FabricatedOverlay r12 = r11.mSecondaryOverlay
            r7[r1] = r12
            android.content.om.FabricatedOverlay r11 = r11.mNeutralOverlay
            r7[r3] = r11
            java.util.Objects.requireNonNull(r5)
            java.util.concurrent.Executor r11 = r5.mBgExecutor
            com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda0 r12 = new com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda0
            r4 = r12
            r4.<init>(r5, r6, r7, r8, r9)
            r11.execute(r12)
            goto L_0x020d
        L_0x01fc:
            com.android.systemui.theme.ThemeOverlayApplier r5 = r11.mThemeManager
            r7 = 0
            java.util.Objects.requireNonNull(r5)
            java.util.concurrent.Executor r11 = r5.mBgExecutor
            com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda0 r12 = new com.android.systemui.theme.ThemeOverlayApplier$$ExternalSyntheticLambda0
            r4 = r12
            r4.<init>(r5, r6, r7, r8, r9)
            r11.execute(r12)
        L_0x020d:
            return
        L_0x020e:
            java.util.NoSuchElementException r11 = new java.util.NoSuchElementException
            java.lang.String r12 = "List is empty."
            r11.<init>(r12)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.theme.ThemeOverlayController.reevaluateSystemTheme(boolean):void");
    }

    public final void start() {
        Log.d("ThemeOverlayController", "Start");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.MANAGED_PROFILE_ADDED");
        intentFilter.addAction("android.intent.action.WALLPAPER_CHANGED");
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter, this.mMainExecutor, UserHandle.ALL);
        this.mSecureSettings.registerContentObserverForUser(Settings.Secure.getUriFor("theme_customization_overlay_packages"), false, new ContentObserver(this.mBgHandler) {
            public final void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
                ExifInterface$$ExternalSyntheticOutline1.m14m("Overlay changed for user: ", i2, "ThemeOverlayController");
                if (ThemeOverlayController.this.mUserTracker.getUserId() == i2) {
                    if (!ThemeOverlayController.this.mDeviceProvisionedController.isUserSetup(i2)) {
                        Log.i("ThemeOverlayController", "Theme application deferred when setting changed.");
                        ThemeOverlayController.this.mDeferredThemeEvaluation = true;
                        return;
                    }
                    ThemeOverlayController themeOverlayController = ThemeOverlayController.this;
                    if (themeOverlayController.mSkipSettingChange) {
                        Log.d("ThemeOverlayController", "Skipping setting change");
                        ThemeOverlayController.this.mSkipSettingChange = false;
                        return;
                    }
                    themeOverlayController.reevaluateSystemTheme(true);
                }
            }
        }, -1);
        if (this.mIsMonetEnabled) {
            this.mUserTracker.addCallback(this.mUserTrackerCallback, this.mMainExecutor);
            this.mDeviceProvisionedController.addCallback(this.mDeviceProvisionedListener);
            if (this.mIsMonetEnabled) {
                WifiEntry$$ExternalSyntheticLambda2 wifiEntry$$ExternalSyntheticLambda2 = new WifiEntry$$ExternalSyntheticLambda2(this, 10);
                if (!this.mDeviceProvisionedController.isCurrentUserSetup()) {
                    wifiEntry$$ExternalSyntheticLambda2.run();
                } else {
                    this.mBgExecutor.execute(wifiEntry$$ExternalSyntheticLambda2);
                }
                this.mWallpaperManager.addOnColorsChangedListener(this.mOnColorsChangedListener, (Handler) null, -1);
                WakefulnessLifecycle wakefulnessLifecycle = this.mWakefulnessLifecycle;
                C16656 r1 = new WakefulnessLifecycle.Observer() {
                    public final void onFinishedGoingToSleep() {
                        int userId = ThemeOverlayController.this.mUserTracker.getUserId();
                        WallpaperColors wallpaperColors = ThemeOverlayController.this.mDeferredWallpaperColors.get(userId);
                        if (wallpaperColors != null) {
                            int i = ThemeOverlayController.this.mDeferredWallpaperColorsFlags.get(userId);
                            ThemeOverlayController.this.mDeferredWallpaperColors.put(userId, (Object) null);
                            ThemeOverlayController.this.mDeferredWallpaperColorsFlags.put(userId, 0);
                            ThemeOverlayController.m255$$Nest$mhandleWallpaperColors(ThemeOverlayController.this, wallpaperColors, i, userId);
                        }
                    }
                };
                Objects.requireNonNull(wakefulnessLifecycle);
                wakefulnessLifecycle.mObservers.add(r1);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:45:0x0116, code lost:
        if (r9.has("android.theme.customization.system_palette") != false) goto L_0x0118;
     */
    /* renamed from: -$$Nest$mhandleWallpaperColors  reason: not valid java name */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void m255$$Nest$mhandleWallpaperColors(com.android.systemui.theme.ThemeOverlayController r12, android.app.WallpaperColors r13, int r14, int r15) {
        /*
            java.util.Objects.requireNonNull(r12)
            java.lang.String r0 = "android.theme.customization.accent_color"
            java.lang.String r1 = "android.theme.customization.color_source"
            com.android.systemui.settings.UserTracker r2 = r12.mUserTracker
            int r2 = r2.getUserId()
            android.util.SparseArray<android.app.WallpaperColors> r3 = r12.mCurrentColors
            java.lang.Object r3 = r3.get(r15)
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x0019
            r3 = r4
            goto L_0x001a
        L_0x0019:
            r3 = r5
        L_0x001a:
            android.app.WallpaperManager r6 = r12.mWallpaperManager
            r7 = 2
            int r6 = r6.getWallpaperIdForUser(r7, r15)
            android.app.WallpaperManager r8 = r12.mWallpaperManager
            int r8 = r8.getWallpaperIdForUser(r4, r15)
            if (r6 <= r8) goto L_0x002b
            r6 = r7
            goto L_0x002c
        L_0x002b:
            r6 = r4
        L_0x002c:
            r6 = r6 & r14
            java.lang.String r8 = "ThemeOverlayController"
            if (r6 == 0) goto L_0x0052
            android.util.SparseArray<android.app.WallpaperColors> r9 = r12.mCurrentColors
            r9.put(r15, r13)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "got new colors: "
            r9.append(r10)
            r9.append(r13)
            java.lang.String r10 = " where: "
            r9.append(r10)
            r9.append(r14)
            java.lang.String r9 = r9.toString()
            android.util.Log.d(r8, r9)
        L_0x0052:
            if (r15 == r2) goto L_0x0073
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "Colors "
            r12.append(r14)
            r12.append(r13)
            java.lang.String r13 = " for user "
            r12.append(r13)
            r12.append(r15)
            java.lang.String r13 = ". Not for current user: "
            r12.append(r13)
            com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(r12, r2, r8)
            goto L_0x0175
        L_0x0073:
            com.android.systemui.statusbar.policy.DeviceProvisionedController r9 = r12.mDeviceProvisionedController
            if (r9 == 0) goto L_0x00d8
            boolean r9 = r9.isCurrentUserSetup()
            if (r9 != 0) goto L_0x00d8
            if (r3 == 0) goto L_0x0097
            java.lang.StringBuilder r14 = new java.lang.StringBuilder
            r14.<init>()
            java.lang.String r15 = "Wallpaper color event deferred until setup is finished: "
            r14.append(r15)
            r14.append(r13)
            java.lang.String r13 = r14.toString()
            android.util.Log.i(r8, r13)
            r12.mDeferredThemeEvaluation = r4
            goto L_0x0175
        L_0x0097:
            boolean r9 = r12.mDeferredThemeEvaluation
            if (r9 == 0) goto L_0x00b1
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r14 = "Wallpaper color event received, but we already were deferring eval: "
            r12.append(r14)
            r12.append(r13)
            java.lang.String r12 = r12.toString()
            android.util.Log.i(r8, r12)
            goto L_0x0175
        L_0x00b1:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r10 = "During user setup, but allowing first color event: had? "
            r9.append(r10)
            r9.append(r3)
            java.lang.String r3 = " has? "
            r9.append(r3)
            android.util.SparseArray<android.app.WallpaperColors> r3 = r12.mCurrentColors
            java.lang.Object r15 = r3.get(r15)
            if (r15 == 0) goto L_0x00cd
            r15 = r4
            goto L_0x00ce
        L_0x00cd:
            r15 = r5
        L_0x00ce:
            r9.append(r15)
            java.lang.String r15 = r9.toString()
            android.util.Log.i(r8, r15)
        L_0x00d8:
            com.android.systemui.util.settings.SecureSettings r15 = r12.mSecureSettings
            java.lang.String r3 = "theme_customization_overlay_packages"
            java.lang.String r15 = r15.getStringForUser(r3, r2)
            r2 = 3
            if (r14 != r2) goto L_0x00e6
            r2 = r4
            goto L_0x00e7
        L_0x00e6:
            r2 = r5
        L_0x00e7:
            if (r15 != 0) goto L_0x00ef
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x016c }
            r9.<init>()     // Catch:{ JSONException -> 0x016c }
            goto L_0x00f4
        L_0x00ef:
            org.json.JSONObject r9 = new org.json.JSONObject     // Catch:{ JSONException -> 0x016c }
            r9.<init>(r15)     // Catch:{ JSONException -> 0x016c }
        L_0x00f4:
            java.lang.String r10 = "preset"
            java.lang.String r11 = r9.optString(r1)     // Catch:{ JSONException -> 0x016c }
            boolean r10 = r10.equals(r11)     // Catch:{ JSONException -> 0x016c }
            if (r10 != 0) goto L_0x0172
            if (r6 == 0) goto L_0x0172
            boolean r13 = isSeedColorSet(r9, r13)     // Catch:{ JSONException -> 0x016c }
            if (r13 != 0) goto L_0x0172
            r12.mSkipSettingChange = r4     // Catch:{ JSONException -> 0x016c }
            boolean r13 = r9.has(r0)     // Catch:{ JSONException -> 0x016c }
            java.lang.String r4 = "android.theme.customization.system_palette"
            if (r13 != 0) goto L_0x0118
            boolean r13 = r9.has(r4)     // Catch:{ JSONException -> 0x016c }
            if (r13 == 0) goto L_0x0123
        L_0x0118:
            r9.remove(r0)     // Catch:{ JSONException -> 0x016c }
            r9.remove(r4)     // Catch:{ JSONException -> 0x016c }
            java.lang.String r13 = "android.theme.customization.color_index"
            r9.remove(r13)     // Catch:{ JSONException -> 0x016c }
        L_0x0123:
            java.lang.String r13 = "android.theme.customization.color_both"
            if (r2 == 0) goto L_0x012a
            java.lang.String r0 = "1"
            goto L_0x012c
        L_0x012a:
            java.lang.String r0 = "0"
        L_0x012c:
            r9.put(r13, r0)     // Catch:{ JSONException -> 0x016c }
            if (r14 != r7) goto L_0x0134
            java.lang.String r13 = "lock_wallpaper"
            goto L_0x0136
        L_0x0134:
            java.lang.String r13 = "home_wallpaper"
        L_0x0136:
            r9.put(r1, r13)     // Catch:{ JSONException -> 0x016c }
            java.lang.String r13 = "_applied_timestamp"
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ JSONException -> 0x016c }
            r9.put(r13, r0)     // Catch:{ JSONException -> 0x016c }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ JSONException -> 0x016c }
            r13.<init>()     // Catch:{ JSONException -> 0x016c }
            java.lang.String r14 = "Updating theme setting from "
            r13.append(r14)     // Catch:{ JSONException -> 0x016c }
            r13.append(r15)     // Catch:{ JSONException -> 0x016c }
            java.lang.String r14 = " to "
            r13.append(r14)     // Catch:{ JSONException -> 0x016c }
            java.lang.String r14 = r9.toString()     // Catch:{ JSONException -> 0x016c }
            r13.append(r14)     // Catch:{ JSONException -> 0x016c }
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x016c }
            android.util.Log.d(r8, r13)     // Catch:{ JSONException -> 0x016c }
            com.android.systemui.util.settings.SecureSettings r13 = r12.mSecureSettings     // Catch:{ JSONException -> 0x016c }
            java.lang.String r14 = r9.toString()     // Catch:{ JSONException -> 0x016c }
            r13.putString(r3, r14)     // Catch:{ JSONException -> 0x016c }
            goto L_0x0172
        L_0x016c:
            r13 = move-exception
            java.lang.String r14 = "Failed to parse THEME_CUSTOMIZATION_OVERLAY_PACKAGES."
            android.util.Log.i(r8, r14, r13)
        L_0x0172:
            r12.reevaluateSystemTheme(r5)
        L_0x0175:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.theme.ThemeOverlayController.m255$$Nest$mhandleWallpaperColors(com.android.systemui.theme.ThemeOverlayController, android.app.WallpaperColors, int, int):void");
    }

    public ThemeOverlayController(Context context, BroadcastDispatcher broadcastDispatcher, Handler handler, Executor executor, Executor executor2, ThemeOverlayApplier themeOverlayApplier, SecureSettings secureSettings, WallpaperManager wallpaperManager, UserManager userManager, DeviceProvisionedController deviceProvisionedController, UserTracker userTracker, DumpManager dumpManager, FeatureFlags featureFlags, WakefulnessLifecycle wakefulnessLifecycle) {
        super(context);
        this.mIsMonetEnabled = featureFlags.isEnabled(Flags.MONET);
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserManager = userManager;
        this.mBgExecutor = executor2;
        this.mMainExecutor = executor;
        this.mBgHandler = handler;
        this.mThemeManager = themeOverlayApplier;
        this.mSecureSettings = secureSettings;
        this.mWallpaperManager = wallpaperManager;
        this.mUserTracker = userTracker;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        dumpManager.registerDumpable("ThemeOverlayController", this);
    }
}
