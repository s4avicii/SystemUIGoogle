package com.android.systemui.util.settings;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.Settings;

public final class SecureSettingsImpl implements SecureSettings {
    public final ContentResolver mContentResolver;

    public final String getStringForUser(String str, int i) {
        return Settings.Secure.getStringForUser(this.mContentResolver, str, i);
    }

    public final boolean putStringForUser(String str, String str2, int i) {
        return Settings.Secure.putStringForUser(this.mContentResolver, str, str2, i);
    }

    public final boolean putStringForUser$1(String str, String str2, int i) {
        return Settings.Secure.putStringForUser(this.mContentResolver, str, str2, (String) null, false, i, true);
    }

    public SecureSettingsImpl(ContentResolver contentResolver) {
        this.mContentResolver = contentResolver;
    }

    public final Uri getUriFor(String str) {
        return Settings.Secure.getUriFor(str);
    }

    public final ContentResolver getContentResolver() {
        return this.mContentResolver;
    }
}
