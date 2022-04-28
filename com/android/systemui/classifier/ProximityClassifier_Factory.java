package com.android.systemui.classifier;

import com.android.systemui.util.DeviceConfigProxy;
import com.google.android.systemui.columbus.actions.Action;
import com.google.android.systemui.columbus.actions.UnpinNotifications;
import com.google.android.systemui.columbus.actions.UserSelectedAction;
import dagger.internal.Factory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.inject.Provider;
import kotlin.collections.SetsKt__SetsKt;

public final class ProximityClassifier_Factory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider dataProvider;
    public final Provider deviceConfigProxyProvider;
    public final Provider distanceClassifierProvider;

    public /* synthetic */ ProximityClassifier_Factory(Provider provider, Provider provider2, Provider provider3, int i) {
        this.$r8$classId = i;
        this.distanceClassifierProvider = provider;
        this.dataProvider = provider2;
        this.deviceConfigProxyProvider = provider3;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                return new ProximityClassifier((DistanceClassifier) this.distanceClassifierProvider.get(), (FalsingDataProvider) this.dataProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get());
            default:
                UnpinNotifications unpinNotifications = (UnpinNotifications) this.dataProvider.get();
                UserSelectedAction userSelectedAction = (UserSelectedAction) this.deviceConfigProxyProvider.get();
                ArrayList arrayList = new ArrayList(3);
                Object[] array = ((List) this.distanceClassifierProvider.get()).toArray(new Action[0]);
                Objects.requireNonNull(array, "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                if (array.length > 0) {
                    arrayList.ensureCapacity(arrayList.size() + array.length);
                    Collections.addAll(arrayList, array);
                }
                arrayList.add(unpinNotifications);
                arrayList.add(userSelectedAction);
                List listOf = SetsKt__SetsKt.listOf(arrayList.toArray(new Action[arrayList.size()]));
                Objects.requireNonNull(listOf, "Cannot return null from a non-@Nullable @Provides method");
                return listOf;
        }
    }
}
