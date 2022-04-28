package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.provider.Settings;
import com.android.systemui.communal.conditions.CommunalSettingCondition;
import com.android.systemui.statusbar.policy.LocationControllerImpl;

public interface SettingsProxy {
    ContentResolver getContentResolver();

    int getIntForUser(String str, int i, int i2) {
        String stringForUser = getStringForUser(str, i2);
        if (stringForUser == null) {
            return i;
        }
        try {
            return Integer.parseInt(stringForUser);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    String getStringForUser(String str, int i);

    Uri getUriFor(String str);

    boolean putStringForUser(String str, String str2, int i);

    boolean putStringForUser$1(String str, String str2, int i);

    void registerContentObserver(LocationControllerImpl.C16321 r2) {
        registerContentObserver(getUriFor("locationShowSystemOps"), r2);
    }

    void registerContentObserverForUser(String str, ContentObserver contentObserver, int i) {
        registerContentObserverForUser(getUriFor(str), contentObserver, i);
    }

    float getFloatForUser(int i) {
        String stringForUser = getStringForUser("animator_duration_scale", i);
        if (stringForUser != null) {
            try {
                return Float.parseFloat(stringForUser);
            } catch (NumberFormatException unused) {
            }
        }
        return 1.0f;
    }

    void registerContentObserver(Uri uri, ContentObserver contentObserver) {
        registerContentObserverForUser(uri, contentObserver, getUserId());
    }

    float getFloat() {
        return getFloatForUser(getUserId());
    }

    int getInt(String str, int i) {
        return getIntForUser(str, i, getUserId());
    }

    int getIntForUser(int i) throws Settings.SettingNotFoundException {
        try {
            return Integer.parseInt(getStringForUser("timeout_to_user_zero", i));
        } catch (NumberFormatException unused) {
            throw new Settings.SettingNotFoundException("timeout_to_user_zero");
        }
    }

    String getString(String str) {
        return getStringForUser(str, getUserId());
    }

    int getUserId() {
        return getContentResolver().getUserId();
    }

    boolean putInt(String str, int i) {
        return putIntForUser(str, i, getUserId());
    }

    boolean putIntForUser(String str, int i, int i2) {
        return putStringForUser(str, Integer.toString(i), i2);
    }

    boolean putString(String str, String str2) {
        return putStringForUser(str, str2, getUserId());
    }

    void registerContentObserver(Uri uri, boolean z, ContentObserver contentObserver) {
        registerContentObserverForUser(uri, z, contentObserver, getUserId());
    }

    void registerContentObserverForUser(Uri uri, ContentObserver contentObserver, int i) {
        registerContentObserverForUser(uri, false, contentObserver, i);
    }

    void unregisterContentObserver(ContentObserver contentObserver) {
        getContentResolver().unregisterContentObserver(contentObserver);
    }

    void registerContentObserverForUser(CommunalSettingCondition.C07351 r3) {
        registerContentObserverForUser(getUriFor("communal_mode_enabled"), false, r3, 0);
    }

    void registerContentObserverForUser(Uri uri, boolean z, ContentObserver contentObserver, int i) {
        getContentResolver().registerContentObserver(uri, z, contentObserver, i);
    }
}
