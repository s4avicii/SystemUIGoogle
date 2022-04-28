package com.android.systemui.theme;

import android.content.om.FabricatedOverlay;
import android.content.om.OverlayIdentifier;
import android.content.om.OverlayManagerTransaction;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ThemeOverlayApplier$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ ThemeOverlayApplier f$0;
    public final /* synthetic */ Map f$1;
    public final /* synthetic */ FabricatedOverlay[] f$2;
    public final /* synthetic */ int f$3;
    public final /* synthetic */ Set f$4;

    public /* synthetic */ ThemeOverlayApplier$$ExternalSyntheticLambda0(ThemeOverlayApplier themeOverlayApplier, ArrayMap arrayMap, FabricatedOverlay[] fabricatedOverlayArr, int i, HashSet hashSet) {
        this.f$0 = themeOverlayApplier;
        this.f$1 = arrayMap;
        this.f$2 = fabricatedOverlayArr;
        this.f$3 = i;
        this.f$4 = hashSet;
    }

    public final void run() {
        ThemeOverlayApplier themeOverlayApplier = this.f$0;
        Map map = this.f$1;
        FabricatedOverlay[] fabricatedOverlayArr = this.f$2;
        int i = this.f$3;
        Set set = this.f$4;
        Objects.requireNonNull(themeOverlayApplier);
        HashSet hashSet = new HashSet(ThemeOverlayApplier.THEME_CATEGORIES);
        ArrayList arrayList = new ArrayList();
        ((Set) hashSet.stream().map(new ThemeOverlayApplier$$ExternalSyntheticLambda2(themeOverlayApplier)).collect(Collectors.toSet())).forEach(new ThemeOverlayApplier$$ExternalSyntheticLambda1(themeOverlayApplier, arrayList, 0));
        List<Pair> list = (List) arrayList.stream().filter(new ThemeOverlayApplier$$ExternalSyntheticLambda5(themeOverlayApplier, 0)).filter(new ThemeOverlayApplier$$ExternalSyntheticLambda6(hashSet, 0)).filter(new ThemeOverlayApplier$$ExternalSyntheticLambda4(map, 0)).filter(ThemeOverlayApplier$$ExternalSyntheticLambda7.INSTANCE).map(ThemeOverlayApplier$$ExternalSyntheticLambda3.INSTANCE).collect(Collectors.toList());
        OverlayManagerTransaction.Builder transactionBuilder = themeOverlayApplier.getTransactionBuilder();
        HashSet hashSet2 = new HashSet();
        if (fabricatedOverlayArr != null) {
            for (FabricatedOverlay fabricatedOverlay : fabricatedOverlayArr) {
                hashSet2.add(fabricatedOverlay.getIdentifier());
                transactionBuilder.registerFabricatedOverlay(fabricatedOverlay);
            }
        }
        for (Pair pair : list) {
            OverlayIdentifier overlayIdentifier = new OverlayIdentifier((String) pair.second);
            themeOverlayApplier.setEnabled(transactionBuilder, overlayIdentifier, (String) pair.first, i, set, false, hashSet2.contains(overlayIdentifier));
        }
        for (String str : ThemeOverlayApplier.THEME_CATEGORIES) {
            if (map.containsKey(str)) {
                OverlayIdentifier overlayIdentifier2 = (OverlayIdentifier) map.get(str);
                themeOverlayApplier.setEnabled(transactionBuilder, overlayIdentifier2, str, i, set, true, hashSet2.contains(overlayIdentifier2));
            }
        }
        try {
            themeOverlayApplier.mOverlayManager.commit(transactionBuilder.build());
        } catch (IllegalStateException | SecurityException e) {
            Log.e("ThemeOverlayApplier", "setEnabled failed", e);
        }
    }
}
