package okio;

import com.android.p012wm.shell.C1777R;

/* compiled from: Okio.kt */
public final /* synthetic */ class Okio__OkioKt {
    public static final int[] ColorStateListItem = {16843173, 16843551, 16844359, C1777R.attr.alpha, C1777R.attr.lStar};
    public static final int[] FontFamily = {C1777R.attr.fontProviderAuthority, C1777R.attr.fontProviderCerts, C1777R.attr.fontProviderFetchStrategy, C1777R.attr.fontProviderFetchTimeout, C1777R.attr.fontProviderPackage, C1777R.attr.fontProviderQuery, C1777R.attr.fontProviderSystemFontFamily};
    public static final int[] FontFamilyFont = {16844082, 16844083, 16844095, 16844143, 16844144, C1777R.attr.font, C1777R.attr.fontStyle, C1777R.attr.fontVariationSettings, C1777R.attr.fontWeight, C1777R.attr.ttcIndex};
    public static final int[] GradientColor = {16843165, 16843166, 16843169, 16843170, 16843171, 16843172, 16843265, 16843275, 16844048, 16844049, 16844050, 16844051};
    public static final int[] GradientColorItem = {16843173, 16844052};

    public static int smear(int i) {
        return (int) (((long) Integer.rotateLeft((int) (((long) i) * -862048943), 15)) * 461845907);
    }

    public static int smearedHash(Object obj) {
        int i;
        if (obj == null) {
            i = 0;
        } else {
            i = obj.hashCode();
        }
        return smear(i);
    }
}
