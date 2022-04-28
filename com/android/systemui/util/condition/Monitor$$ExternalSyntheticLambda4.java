package com.android.systemui.util.condition;

import android.text.TextUtils;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import java.util.HashMap;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class Monitor$$ExternalSyntheticLambda4 implements Predicate {
    public static final /* synthetic */ Monitor$$ExternalSyntheticLambda4 INSTANCE = new Monitor$$ExternalSyntheticLambda4(0);
    public static final /* synthetic */ Monitor$$ExternalSyntheticLambda4 INSTANCE$1 = new Monitor$$ExternalSyntheticLambda4(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ Monitor$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    public final boolean test(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((Condition) obj).isOverridingCondition();
            default:
                HashMap hashMap = PeopleSpaceWidgetManager.mListeners;
                return !TextUtils.isEmpty((String) obj);
        }
    }
}
