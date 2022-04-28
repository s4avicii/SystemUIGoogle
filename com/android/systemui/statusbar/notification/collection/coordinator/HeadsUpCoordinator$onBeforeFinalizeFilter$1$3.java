package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.preference.R$id;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: HeadsUpCoordinator.kt */
final /* synthetic */ class HeadsUpCoordinator$onBeforeFinalizeFilter$1$3 extends FunctionReferenceImpl implements Function1<String, GroupLocation> {
    public HeadsUpCoordinator$onBeforeFinalizeFilter$1$3(Map map) {
        super(1, map, R$id.class, "getLocation", "getLocation(Ljava/util/Map;Ljava/lang/String;)Lcom/android/systemui/statusbar/notification/collection/coordinator/GroupLocation;", 1);
    }

    public final Object invoke(Object obj) {
        return (GroupLocation) ((Map) this.receiver).getOrDefault((String) obj, GroupLocation.Detached);
    }
}
