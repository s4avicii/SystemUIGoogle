package androidx.emoji2.text;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.util.Log;
import androidx.core.provider.FontRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public final class DefaultEmojiCompatConfig$DefaultEmojiCompatConfigFactory {
    public final DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper mHelper = new DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper_API28();

    public FontRequest queryForDefaultFontRequest(Context context) {
        ProviderInfo providerInfo;
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        Objects.requireNonNull(packageManager, "Package manager required to locate emoji font provider");
        Iterator it = this.mHelper.queryIntentContentProviders(packageManager, new Intent("androidx.content.action.LOAD_EMOJI_FONT")).iterator();
        while (true) {
            if (!it.hasNext()) {
                providerInfo = null;
                break;
            }
            providerInfo = this.mHelper.getProviderInfo((ResolveInfo) it.next());
            boolean z = true;
            if (providerInfo == null || (applicationInfo = providerInfo.applicationInfo) == null || (applicationInfo.flags & 1) != 1) {
                z = false;
                continue;
            }
            if (z) {
                break;
            }
        }
        if (providerInfo == null) {
            return null;
        }
        try {
            String str = providerInfo.authority;
            String str2 = providerInfo.packageName;
            Signature[] signingSignatures = this.mHelper.getSigningSignatures(packageManager, str2);
            ArrayList arrayList = new ArrayList();
            for (Signature byteArray : signingSignatures) {
                arrayList.add(byteArray.toByteArray());
            }
            return new FontRequest(str, str2, "emojicompat-emoji-font", Collections.singletonList(arrayList));
        } catch (PackageManager.NameNotFoundException e) {
            Log.wtf("emoji2.text.DefaultEmojiConfig", e);
            return null;
        }
    }
}
