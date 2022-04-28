package com.android.keyguard;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import java.util.Objects;

/* renamed from: com.android.keyguard.KeyguardSecurityContainer$UserSwitcherViewMode$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0519xd955338c implements View.OnClickListener {
    public final /* synthetic */ KeyguardSecurityContainer.UserSwitcherViewMode f$0;
    public final /* synthetic */ ViewGroup f$1;
    public final /* synthetic */ UserSwitcherController.BaseUserAdapter f$2;

    public /* synthetic */ C0519xd955338c(KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode, ViewGroup viewGroup, KeyguardSecurityContainer.UserSwitcherViewMode.C05161 r3) {
        this.f$0 = userSwitcherViewMode;
        this.f$1 = viewGroup;
        this.f$2 = r3;
    }

    public final void onClick(View view) {
        KeyguardSecurityContainer.UserSwitcherViewMode userSwitcherViewMode = this.f$0;
        ViewGroup viewGroup = this.f$1;
        UserSwitcherController.BaseUserAdapter baseUserAdapter = this.f$2;
        Objects.requireNonNull(userSwitcherViewMode);
        if (!userSwitcherViewMode.mFalsingManager.isFalseTap(1)) {
            KeyguardUserSwitcherPopupMenu keyguardUserSwitcherPopupMenu = new KeyguardUserSwitcherPopupMenu(view.getContext(), userSwitcherViewMode.mFalsingManager);
            userSwitcherViewMode.mPopup = keyguardUserSwitcherPopupMenu;
            keyguardUserSwitcherPopupMenu.setAnchorView(viewGroup);
            userSwitcherViewMode.mPopup.setAdapter(baseUserAdapter);
            userSwitcherViewMode.mPopup.setOnItemClickListener(new AdapterView.OnItemClickListener(baseUserAdapter) {
                public final /* synthetic */ UserSwitcherController.BaseUserAdapter val$adapter;

                {
                    this.val$adapter = r2;
                }

                public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                    if (!UserSwitcherViewMode.this.mFalsingManager.isFalseTap(1) && view.isEnabled()) {
                        UserSwitcherController.UserRecord item = this.val$adapter.getItem(i - 1);
                        if (!item.isCurrent) {
                            UserSwitcherController.BaseUserAdapter baseUserAdapter = this.val$adapter;
                            Objects.requireNonNull(baseUserAdapter);
                            baseUserAdapter.onUserListItemClicked(item, (UserSwitchDialogController.DialogShower) null);
                        }
                        UserSwitcherViewMode.this.mPopup.dismiss();
                        UserSwitcherViewMode.this.mPopup = null;
                    }
                }
            });
            userSwitcherViewMode.mPopup.show();
        }
    }
}
