package com.android.systemui.user;

import android.view.View;
import android.widget.ListAdapter;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.user.UserSwitcherActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: UserSwitcherActivity.kt */
public final class UserSwitcherActivity$onCreate$2$1 implements View.OnClickListener {
    public final /* synthetic */ UserSwitcherActivity this$0;

    public UserSwitcherActivity$onCreate$2$1(UserSwitcherActivity userSwitcherActivity) {
        this.this$0 = userSwitcherActivity;
    }

    public final void onClick(View view) {
        UserSwitcherActivity userSwitcherActivity = this.this$0;
        int i = UserSwitcherActivity.$r8$clinit;
        Objects.requireNonNull(userSwitcherActivity);
        ArrayList arrayList = new ArrayList();
        Iterator it = userSwitcherActivity.addUserRecords.iterator();
        while (it.hasNext()) {
            arrayList.add((UserSwitcherController.UserRecord) it.next());
        }
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        T itemAdapter = new UserSwitcherActivity.ItemAdapter(userSwitcherActivity, userSwitcherActivity.layoutInflater, new UserSwitcherActivity$showPopupMenu$popupMenuAdapter$1(userSwitcherActivity), new UserSwitcherActivity$showPopupMenu$popupMenuAdapter$2(userSwitcherActivity));
        ref$ObjectRef.element = itemAdapter;
        itemAdapter.addAll(arrayList);
        UserSwitcherPopupMenu userSwitcherPopupMenu = new UserSwitcherPopupMenu(userSwitcherActivity, userSwitcherActivity.falsingManager);
        View view2 = userSwitcherActivity.addButton;
        if (view2 == null) {
            view2 = null;
        }
        userSwitcherPopupMenu.setAnchorView(view2);
        userSwitcherPopupMenu.setAdapter((ListAdapter) ref$ObjectRef.element);
        userSwitcherPopupMenu.setOnItemClickListener(new UserSwitcherActivity$showPopupMenu$2$1(userSwitcherActivity, ref$ObjectRef, userSwitcherPopupMenu));
        userSwitcherPopupMenu.show();
    }
}
