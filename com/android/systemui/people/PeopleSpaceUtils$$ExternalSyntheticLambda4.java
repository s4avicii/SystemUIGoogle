package com.android.systemui.people;

import android.graphics.drawable.ColorDrawable;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.systemui.people.widget.PeopleTileKey;
import com.android.systemui.statusbar.notification.row.ExpandableOutlineView;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PeopleSpaceUtils$$ExternalSyntheticLambda4 implements Function {
    public static final /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda4 INSTANCE = new PeopleSpaceUtils$$ExternalSyntheticLambda4(0);
    public static final /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda4 INSTANCE$1 = new PeopleSpaceUtils$$ExternalSyntheticLambda4(1);
    public static final /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda4 INSTANCE$2 = new PeopleSpaceUtils$$ExternalSyntheticLambda4(2);
    public static final /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda4 INSTANCE$3 = new PeopleSpaceUtils$$ExternalSyntheticLambda4(3);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ PeopleSpaceUtils$$ExternalSyntheticLambda4(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                PeopleTileKey peopleTileKey = PeopleSpaceUtils.EMPTY_KEY;
                return ((Set) ((Map.Entry) obj).getValue()).stream();
            case 1:
                ColorDrawable colorDrawable = InternetDialogController.EMPTY_DRAWABLE;
                return ((InternetDialogController.AnonymousClass1DisplayInfo) obj).uniqueName;
            case 2:
                ExpandableOutlineView expandableOutlineView = (ExpandableOutlineView) obj;
                Objects.requireNonNull(expandableOutlineView);
                return Float.valueOf(expandableOutlineView.mCurrentTopRoundness);
            default:
                return ((SysUIUnfoldComponent) obj).getNotificationPanelUnfoldAnimationController();
        }
    }
}
