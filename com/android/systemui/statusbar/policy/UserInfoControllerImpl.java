package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.util.Log;
import com.android.internal.util.UserIcons;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.drawable.UserIconDrawable;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class UserInfoControllerImpl implements UserInfoController {
    public final ArrayList<UserInfoController.OnUserInfoChangedListener> mCallbacks = new ArrayList<>();
    public final Context mContext;
    public final C16482 mProfileReceiver;
    public final C16471 mReceiver;
    public String mUserAccount;
    public Drawable mUserDrawable;
    public AsyncTask<Void, Void, UserInfoQueryResult> mUserInfoTask;
    public String mUserName;

    public static class UserInfoQueryResult {
        public Drawable mAvatar;
        public String mName;
        public String mUserAccount;

        public UserInfoQueryResult(String str, Drawable drawable, String str2) {
            this.mName = str;
            this.mAvatar = drawable;
            this.mUserAccount = str2;
        }
    }

    public final void addCallback(Object obj) {
        UserInfoController.OnUserInfoChangedListener onUserInfoChangedListener = (UserInfoController.OnUserInfoChangedListener) obj;
        this.mCallbacks.add(onUserInfoChangedListener);
        onUserInfoChangedListener.onUserInfoChanged(this.mUserName, this.mUserDrawable);
    }

    public final void reloadUserInfo() {
        final boolean z;
        AsyncTask<Void, Void, UserInfoQueryResult> asyncTask = this.mUserInfoTask;
        if (asyncTask != null) {
            asyncTask.cancel(false);
            this.mUserInfoTask = null;
        }
        try {
            UserInfo currentUser = ActivityManager.getService().getCurrentUser();
            final Context createPackageContextAsUser = this.mContext.createPackageContextAsUser(ThemeOverlayApplier.ANDROID_PACKAGE, 0, new UserHandle(currentUser.id));
            final int i = currentUser.id;
            final boolean isGuest = currentUser.isGuest();
            final String str = currentUser.name;
            if (this.mContext.getThemeResId() != 2132018190) {
                z = true;
            } else {
                z = false;
            }
            Resources resources = this.mContext.getResources();
            final int max = Math.max(resources.getDimensionPixelSize(C1777R.dimen.multi_user_avatar_expanded_size), resources.getDimensionPixelSize(C1777R.dimen.multi_user_avatar_keyguard_size));
            C16493 r7 = new AsyncTask<Void, Void, UserInfoQueryResult>() {
                public final Object doInBackground(Object[] objArr) {
                    UserIconDrawable userIconDrawable;
                    Cursor query;
                    int i;
                    Void[] voidArr = (Void[]) objArr;
                    UserManager userManager = UserManager.get(UserInfoControllerImpl.this.mContext);
                    String str = str;
                    Bitmap userIcon = userManager.getUserIcon(i);
                    if (userIcon != null) {
                        UserIconDrawable userIconDrawable2 = new UserIconDrawable(max);
                        userIconDrawable2.setIcon(userIcon);
                        userIconDrawable2.setBadgeIfManagedUser(UserInfoControllerImpl.this.mContext, i);
                        if (userIconDrawable2.mSize > 0) {
                            int i2 = userIconDrawable2.mSize;
                            userIconDrawable2.onBoundsChange(new Rect(0, 0, i2, i2));
                            userIconDrawable2.rebake();
                            userIconDrawable2.mFrameColor = null;
                            userIconDrawable2.mFramePaint = null;
                            userIconDrawable2.mClearPaint = null;
                            Drawable drawable = userIconDrawable2.mUserDrawable;
                            if (drawable != null) {
                                drawable.setCallback((Drawable.Callback) null);
                                userIconDrawable2.mUserDrawable = null;
                                userIconDrawable = userIconDrawable2;
                            } else {
                                Bitmap bitmap = userIconDrawable2.mUserIcon;
                                userIconDrawable = userIconDrawable2;
                                if (bitmap != null) {
                                    bitmap.recycle();
                                    userIconDrawable2.mUserIcon = null;
                                    userIconDrawable = userIconDrawable2;
                                }
                            }
                        } else {
                            throw new IllegalStateException("Baking requires an explicit intrinsic size");
                        }
                    } else {
                        Resources resources = createPackageContextAsUser.getResources();
                        if (isGuest) {
                            i = -10000;
                        } else {
                            i = i;
                        }
                        userIconDrawable = UserIcons.getDefaultUserIcon(resources, i, z);
                    }
                    if (userManager.getUsers().size() <= 1 && (query = createPackageContextAsUser.getContentResolver().query(ContactsContract.Profile.CONTENT_URI, new String[]{"_id", "display_name"}, (String) null, (String[]) null, (String) null)) != null) {
                        try {
                            if (query.moveToFirst()) {
                                str = query.getString(query.getColumnIndex("display_name"));
                            }
                        } finally {
                            query.close();
                        }
                    }
                    return new UserInfoQueryResult(str, userIconDrawable, userManager.getUserAccount(i));
                }

                public final void onPostExecute(Object obj) {
                    UserInfoQueryResult userInfoQueryResult = (UserInfoQueryResult) obj;
                    UserInfoControllerImpl userInfoControllerImpl = UserInfoControllerImpl.this;
                    Objects.requireNonNull(userInfoQueryResult);
                    userInfoControllerImpl.mUserName = userInfoQueryResult.mName;
                    UserInfoControllerImpl userInfoControllerImpl2 = UserInfoControllerImpl.this;
                    userInfoControllerImpl2.mUserDrawable = userInfoQueryResult.mAvatar;
                    userInfoControllerImpl2.mUserAccount = userInfoQueryResult.mUserAccount;
                    userInfoControllerImpl2.mUserInfoTask = null;
                    Iterator<UserInfoController.OnUserInfoChangedListener> it = userInfoControllerImpl2.mCallbacks.iterator();
                    while (it.hasNext()) {
                        it.next().onUserInfoChanged(userInfoControllerImpl2.mUserName, userInfoControllerImpl2.mUserDrawable);
                    }
                }
            };
            this.mUserInfoTask = r7;
            r7.execute(new Void[0]);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("UserInfoController", "Couldn't create user context", e);
            throw new RuntimeException(e);
        } catch (RemoteException e2) {
            Log.e("UserInfoController", "Couldn't get user info", e2);
            throw new RuntimeException(e2);
        }
    }

    public final void removeCallback(Object obj) {
        this.mCallbacks.remove((UserInfoController.OnUserInfoChangedListener) obj);
    }

    public UserInfoControllerImpl(Context context) {
        C16471 r0 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    UserInfoControllerImpl.this.reloadUserInfo();
                }
            }
        };
        this.mReceiver = r0;
        C16482 r2 = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if ("android.provider.Contacts.PROFILE_CHANGED".equals(action) || "android.intent.action.USER_INFO_CHANGED".equals(action)) {
                    try {
                        if (intent.getIntExtra("android.intent.extra.user_handle", getSendingUserId()) == ActivityManager.getService().getCurrentUser().id) {
                            UserInfoControllerImpl.this.reloadUserInfo();
                        }
                    } catch (RemoteException e) {
                        Log.e("UserInfoController", "Couldn't get current user id for profile change", e);
                    }
                }
            }
        };
        this.mProfileReceiver = r2;
        this.mContext = context;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        context.registerReceiver(r0, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.provider.Contacts.PROFILE_CHANGED");
        intentFilter2.addAction("android.intent.action.USER_INFO_CHANGED");
        context.registerReceiverAsUser(r2, UserHandle.ALL, intentFilter2, (String) null, (Handler) null, 2);
    }
}
