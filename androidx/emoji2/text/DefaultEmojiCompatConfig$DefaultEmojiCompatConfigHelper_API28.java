package androidx.emoji2.text;

import android.content.pm.PackageManager;
import android.content.pm.Signature;

public final class DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper_API28 extends DefaultEmojiCompatConfig$DefaultEmojiCompatConfigHelper_API19 {
    public final Signature[] getSigningSignatures(PackageManager packageManager, String str) throws PackageManager.NameNotFoundException {
        return packageManager.getPackageInfo(str, 64).signatures;
    }
}
