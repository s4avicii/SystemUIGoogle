package com.google.android.systemui;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.List;

public final class DisplayCutoutEmulationAdapter {
    public final Context mContext;
    public final C21561 mObserver;
    public final IOverlayManager mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));

    public final void update() {
        String stringForUser = Settings.Global.getStringForUser(this.mContext.getContentResolver(), "com.google.android.systemui.display_cutout_emulation", 0);
        if (stringForUser != null) {
            String[] split = stringForUser.split(":", 2);
            try {
                int parseInt = Integer.parseInt(split[0]);
                String str = split[1];
                if (parseInt > PreferenceManager.getDefaultSharedPreferences(this.mContext).getInt("com.google.android.systemui.display_cutout_emulation.VERSION", -1)) {
                    if (str.isEmpty() || str.startsWith("com.android.internal.display.cutout.emulation")) {
                        try {
                            List overlayInfosForTarget = this.mOverlayManager.getOverlayInfosForTarget(ThemeOverlayApplier.ANDROID_PACKAGE, 0);
                            int size = overlayInfosForTarget.size();
                            while (true) {
                                size--;
                                if (size < 0) {
                                    break;
                                } else if (!((OverlayInfo) overlayInfosForTarget.get(size)).packageName.startsWith("com.android.internal.display.cutout.emulation")) {
                                    overlayInfosForTarget.remove(size);
                                }
                            }
                            OverlayInfo[] overlayInfoArr = (OverlayInfo[]) overlayInfosForTarget.toArray(new OverlayInfo[overlayInfosForTarget.size()]);
                            String str2 = null;
                            for (OverlayInfo overlayInfo : overlayInfoArr) {
                                if (overlayInfo.isEnabled()) {
                                    str2 = overlayInfo.packageName;
                                }
                            }
                            if ((!TextUtils.isEmpty(str) || !TextUtils.isEmpty(str2)) && !TextUtils.equals(str, str2)) {
                                for (OverlayInfo overlayInfo2 : overlayInfoArr) {
                                    boolean isEnabled = overlayInfo2.isEnabled();
                                    boolean equals = TextUtils.equals(overlayInfo2.packageName, str);
                                    if (isEnabled != equals) {
                                        try {
                                            this.mOverlayManager.setEnabled(overlayInfo2.packageName, equals, 0);
                                        } catch (RemoteException e) {
                                            throw e.rethrowFromSystemServer();
                                        }
                                    }
                                }
                            }
                            PreferenceManager.getDefaultSharedPreferences(this.mContext).edit().putInt("com.google.android.systemui.display_cutout_emulation.VERSION", parseInt).apply();
                        } catch (RemoteException e2) {
                            throw e2.rethrowFromSystemServer();
                        }
                    } else {
                        Log.e("CutoutEmulationAdapter", "Invalid overlay prefix: " + stringForUser);
                    }
                }
            } catch (IndexOutOfBoundsException | NumberFormatException e3) {
                Log.e("CutoutEmulationAdapter", "Invalid configuration: " + stringForUser, e3);
            }
        }
    }

    public DisplayCutoutEmulationAdapter(Context context) {
        C21561 r0 = new ContentObserver(Handler.getMain()) {
            public final void onChange(boolean z) {
                DisplayCutoutEmulationAdapter.this.update();
            }
        };
        this.mObserver = r0;
        this.mContext = context;
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("com.google.android.systemui.display_cutout_emulation"), false, r0, 0);
        update();
    }
}
