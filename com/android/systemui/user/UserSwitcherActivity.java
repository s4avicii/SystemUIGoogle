package com.android.systemui.user;

import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.helper.widget.Flow;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.LifecycleActivity;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UserSwitcherActivity.kt */
public final class UserSwitcherActivity extends LifecycleActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UserSwitcherActivity$adapter$1 adapter;
    public View addButton;
    public ArrayList addUserRecords = new ArrayList();
    public final BroadcastDispatcher broadcastDispatcher;
    public UserSwitcherActivity$initBroadcastReceiver$1 broadcastReceiver;
    public final FalsingManager falsingManager;
    public final LayoutInflater layoutInflater;
    public final UserSwitcherController.UserRecord manageUserRecord = new UserSwitcherController.UserRecord((UserInfo) null, (Bitmap) null, false, false, false, false, false, false);
    public ViewGroup parent;
    public final ShadeController shadeController;
    public final UserManager userManager;
    public final UserSwitcherController userSwitcherController;

    /* compiled from: UserSwitcherActivity.kt */
    public static final class ItemAdapter extends ArrayAdapter<UserSwitcherController.UserRecord> {
        public final Function1<UserSwitcherController.UserRecord, Drawable> iconGetter;
        public final LayoutInflater layoutInflater;
        public final int resource = C1777R.layout.user_switcher_fullscreen_popup_item;
        public final Function1<UserSwitcherController.UserRecord, String> textGetter;

        public ItemAdapter(UserSwitcherActivity userSwitcherActivity, LayoutInflater layoutInflater2, Function1 function1, Function1 function12) {
            super(userSwitcherActivity, C1777R.layout.user_switcher_fullscreen_popup_item);
            this.layoutInflater = layoutInflater2;
            this.textGetter = function1;
            this.iconGetter = function12;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            UserSwitcherController.UserRecord userRecord = (UserSwitcherController.UserRecord) getItem(i);
            if (view == null) {
                view = this.layoutInflater.inflate(this.resource, viewGroup, false);
            }
            ((ImageView) view.requireViewById(C1777R.C1779id.icon)).setImageDrawable(this.iconGetter.invoke(userRecord));
            ((TextView) view.requireViewById(C1777R.C1779id.text)).setText(this.textGetter.invoke(userRecord));
            return view;
        }
    }

    public final void buildUserViews() {
        ViewGroup viewGroup = this.parent;
        View view = null;
        if (viewGroup == null) {
            viewGroup = null;
        }
        int childCount = viewGroup.getChildCount();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < childCount) {
            int i4 = i + 1;
            ViewGroup viewGroup2 = this.parent;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            if (Intrinsics.areEqual(viewGroup2.getChildAt(i).getTag(), "user_view")) {
                if (i2 != 0) {
                    i = i3;
                }
                i2++;
                i3 = i;
            }
            i = i4;
        }
        ViewGroup viewGroup3 = this.parent;
        if (viewGroup3 == null) {
            viewGroup3 = null;
        }
        viewGroup3.removeViews(i3, i2);
        this.addUserRecords.clear();
        Flow flow = (Flow) requireViewById(C1777R.C1779id.flow);
        int count = this.adapter.getCount();
        int i5 = 0;
        while (i5 < count) {
            int i6 = i5 + 1;
            UserSwitcherController.UserRecord item = this.adapter.getItem(i5);
            if (item.isAddUser || item.isAddSupervisedUser || (item.isGuest && item.info == null)) {
                this.addUserRecords.add(item);
            } else {
                UserSwitcherActivity$adapter$1 userSwitcherActivity$adapter$1 = this.adapter;
                ViewGroup viewGroup4 = this.parent;
                if (viewGroup4 == null) {
                    viewGroup4 = null;
                }
                View view2 = userSwitcherActivity$adapter$1.getView(i5, (View) null, viewGroup4);
                view2.setId(View.generateViewId());
                ViewGroup viewGroup5 = this.parent;
                if (viewGroup5 == null) {
                    viewGroup5 = null;
                }
                viewGroup5.addView(view2);
                flow.addView(view2);
                view2.setOnClickListener(new UserSwitcherActivity$buildUserViews$1(this, item));
            }
            i5 = i6;
        }
        if (!this.addUserRecords.isEmpty()) {
            this.addUserRecords.add(this.manageUserRecord);
            View view3 = this.addButton;
            if (view3 != null) {
                view = view3;
            }
            view.setVisibility(0);
            return;
        }
        View view4 = this.addButton;
        if (view4 != null) {
            view = view4;
        }
        view.setVisibility(8);
    }

    public UserSwitcherActivity(UserSwitcherController userSwitcherController2, BroadcastDispatcher broadcastDispatcher2, LayoutInflater layoutInflater2, FalsingManager falsingManager2, UserManager userManager2, ShadeController shadeController2) {
        this.userSwitcherController = userSwitcherController2;
        this.broadcastDispatcher = broadcastDispatcher2;
        this.layoutInflater = layoutInflater2;
        this.falsingManager = falsingManager2;
        this.userManager = userManager2;
        this.shadeController = shadeController2;
        this.adapter = new UserSwitcherActivity$adapter$1(this, userSwitcherController2);
    }

    public final void onCreate(Bundle bundle) {
        UserSwitcherActivity$initBroadcastReceiver$1 userSwitcherActivity$initBroadcastReceiver$1;
        super.onCreate(bundle);
        setContentView(C1777R.layout.user_switcher_fullscreen);
        getWindow().getDecorView().setSystemUiVisibility(770);
        this.parent = (ViewGroup) requireViewById(C1777R.C1779id.user_switcher_root);
        requireViewById(C1777R.C1779id.cancel).setOnClickListener(new UserSwitcherActivity$onCreate$1$1(this));
        View requireViewById = requireViewById(C1777R.C1779id.add);
        requireViewById.setOnClickListener(new UserSwitcherActivity$onCreate$2$1(this));
        this.addButton = requireViewById;
        UserSwitcherController userSwitcherController2 = this.userSwitcherController;
        ViewGroup viewGroup = this.parent;
        if (viewGroup == null) {
            viewGroup = null;
        }
        Objects.requireNonNull(userSwitcherController2);
        userSwitcherController2.mView = viewGroup;
        this.broadcastReceiver = new UserSwitcherActivity$initBroadcastReceiver$1(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        BroadcastDispatcher broadcastDispatcher2 = this.broadcastDispatcher;
        UserSwitcherActivity$initBroadcastReceiver$1 userSwitcherActivity$initBroadcastReceiver$12 = this.broadcastReceiver;
        if (userSwitcherActivity$initBroadcastReceiver$12 == null) {
            userSwitcherActivity$initBroadcastReceiver$1 = null;
        } else {
            userSwitcherActivity$initBroadcastReceiver$1 = userSwitcherActivity$initBroadcastReceiver$12;
        }
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher2, userSwitcherActivity$initBroadcastReceiver$1, intentFilter, (Executor) null, (UserHandle) null, 28);
        buildUserViews();
    }

    public final void onDestroy() {
        super.onDestroy();
        BroadcastDispatcher broadcastDispatcher2 = this.broadcastDispatcher;
        UserSwitcherActivity$initBroadcastReceiver$1 userSwitcherActivity$initBroadcastReceiver$1 = this.broadcastReceiver;
        if (userSwitcherActivity$initBroadcastReceiver$1 == null) {
            userSwitcherActivity$initBroadcastReceiver$1 = null;
        }
        broadcastDispatcher2.unregisterReceiver(userSwitcherActivity$initBroadcastReceiver$1);
    }

    public final void onBackPressed() {
        finish();
    }
}
