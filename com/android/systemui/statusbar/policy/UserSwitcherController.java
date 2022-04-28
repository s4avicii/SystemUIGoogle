package com.android.systemui.statusbar.policy;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.telephony.TelephonyCallback;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.WindowManagerGlobal;
import android.widget.BaseAdapter;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline0;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1;
import com.android.systemui.Dumpable;
import com.android.systemui.GuestResumeSessionReceiver;
import com.android.systemui.SystemUISecondaryUserService;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.p006qs.QSUserSwitcherEvent;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.people.PeopleTileViewHelper$$ExternalSyntheticLambda4;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda1;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.util.settings.SecureSettings;
import com.android.wifitrackerlib.WifiEntry$$ExternalSyntheticLambda2;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public final class UserSwitcherController implements Dumpable {
    public final IActivityManager mActivityManager;
    public final ActivityStarter mActivityStarter;
    public final ArrayList<WeakReference<BaseUserAdapter>> mAdapters = new ArrayList<>();
    @VisibleForTesting
    public Dialog mAddUserDialog;
    public boolean mAddUsersFromLockScreen;
    public final Executor mBgExecutor;
    public final C16545 mCallback;
    public final Context mContext;
    public String mCreateSupervisedUserPackage;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    @VisibleForTesting
    public AlertDialog mExitGuestDialog;
    public FalsingManager mFalsingManager;
    public SparseBooleanArray mForcePictureLoadForUserId = new SparseBooleanArray(2);
    public final C16556 mGuaranteeGuestPresentAfterProvisioned;
    public final AtomicBoolean mGuestCreationScheduled;
    public final AtomicBoolean mGuestIsResetting;
    @VisibleForTesting
    public final GuestResumeSessionReceiver mGuestResumeSessionReceiver;
    public final boolean mGuestUserAutoCreated;
    public final Handler mHandler;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final KeyguardStateController mKeyguardStateController;
    public int mLastNonGuestUser = 0;
    public final LatencyTracker mLatencyTracker;
    @VisibleForTesting
    public boolean mPauseRefreshUsers;
    public final C16512 mPhoneStateListener;
    public C16523 mReceiver;
    public boolean mResumeUserOnGuestLogout = true;
    public int mSecondaryUser = -10000;
    public Intent mSecondaryUserServiceIntent;
    public final C16501 mSettingsObserver;
    public final Lazy<ShadeController> mShadeController;
    public boolean mSimpleUserSwitcher;
    public final UiEventLogger mUiEventLogger;
    public final Executor mUiExecutor;
    public final C16534 mUnpauseRefreshUsers;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public ArrayList<UserRecord> mUsers = new ArrayList<>();
    public View mView;

    @VisibleForTesting
    public final class AddUserDialog extends SystemUIDialog implements DialogInterface.OnClickListener {
        public final void onClick(DialogInterface dialogInterface, int i) {
            int i2;
            if (i == -2) {
                i2 = 0;
            } else {
                i2 = 2;
            }
            if (!UserSwitcherController.this.mFalsingManager.isFalseTap(i2)) {
                if (i == -3) {
                    cancel();
                    return;
                }
                UserSwitcherController.this.mDialogLaunchAnimator.dismissStack(this);
                if (!ActivityManager.isUserAMonkey()) {
                    UserSwitcherController.this.mShadeController.get().collapsePanel();
                    Context context = getContext();
                    Context context2 = getContext();
                    int i3 = CreateUserActivity.$r8$clinit;
                    Intent intent = new Intent(context2, CreateUserActivity.class);
                    intent.addFlags(335544320);
                    context.startActivity(intent);
                }
            }
        }

        public AddUserDialog(Context context) {
            super(context);
            setTitle(C1777R.string.user_add_user_title);
            setMessage(context.getString(C1777R.string.user_add_user_message_short));
            setButton(-3, context.getString(17039360), this);
            setButton(-1, context.getString(17039370), this);
            SystemUIDialog.setWindowOnTop(this, UserSwitcherController.this.mKeyguardStateController.isShowing());
        }
    }

    public static abstract class BaseUserAdapter extends BaseAdapter {
        public final UserSwitcherController mController;
        public final KeyguardStateController mKeyguardStateController;

        public final long getItemId(int i) {
            return (long) i;
        }

        public static Drawable getIconDrawable(Context context, UserRecord userRecord) {
            int i;
            if (userRecord.isAddUser) {
                i = C1777R.C1778drawable.ic_account_circle;
            } else if (userRecord.isGuest) {
                i = C1777R.C1778drawable.ic_account_circle_filled;
            } else if (userRecord.isAddSupervisedUser) {
                i = C1777R.C1778drawable.ic_add_supervised_user;
            } else {
                i = C1777R.C1778drawable.ic_avatar_user;
            }
            return context.getDrawable(i);
        }

        public final int getCount() {
            boolean isShowing = this.mKeyguardStateController.isShowing();
            int size = getUsers().size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                boolean z = getUsers().get(i2).isGuest;
                if (getUsers().get(i2).isRestricted && isShowing) {
                    break;
                }
                i++;
            }
            return i;
        }

        public final UserRecord getItem(int i) {
            return getUsers().get(i);
        }

        public String getName(Context context, UserRecord userRecord) {
            int i;
            if (userRecord.isGuest) {
                if (userRecord.isCurrent) {
                    if (this.mController.mGuestUserAutoCreated) {
                        i = C1777R.string.guest_reset_guest;
                    } else {
                        i = C1777R.string.guest_exit_guest;
                    }
                    return context.getString(i);
                }
                UserInfo userInfo = userRecord.info;
                int i2 = C1777R.string.guest_nickname;
                if (userInfo != null) {
                    return context.getString(C1777R.string.guest_nickname);
                }
                UserSwitcherController userSwitcherController = this.mController;
                if (!userSwitcherController.mGuestUserAutoCreated) {
                    return context.getString(C1777R.string.guest_new_guest);
                }
                if (userSwitcherController.mGuestIsResetting.get()) {
                    i2 = C1777R.string.guest_resetting;
                }
                return context.getString(i2);
            } else if (userRecord.isAddUser) {
                return context.getString(C1777R.string.user_add_user);
            } else {
                if (userRecord.isAddSupervisedUser) {
                    return context.getString(C1777R.string.add_user_supervised);
                }
                return userRecord.info.name;
            }
        }

        public ArrayList<UserRecord> getUsers() {
            return this.mController.getUsers();
        }

        public final void onUserListItemClicked(UserRecord userRecord, UserSwitchDialogController.DialogShower dialogShower) {
            this.mController.onUserListItemClicked(userRecord, dialogShower);
        }

        public BaseUserAdapter(UserSwitcherController userSwitcherController) {
            this.mController = userSwitcherController;
            this.mKeyguardStateController = userSwitcherController.getKeyguardStateController();
            userSwitcherController.addAdapter(new WeakReference(this));
        }
    }

    public final class ExitGuestDialog extends SystemUIDialog implements DialogInterface.OnClickListener {
        public final int mGuestId;
        public final int mTargetId;

        public final void onClick(DialogInterface dialogInterface, int i) {
            int i2;
            if (i == -2) {
                i2 = 0;
            } else {
                i2 = 3;
            }
            if (!UserSwitcherController.this.mFalsingManager.isFalseTap(i2)) {
                if (i == -3) {
                    cancel();
                    return;
                }
                UserSwitcherController.this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_REMOVE);
                UserSwitcherController.this.mDialogLaunchAnimator.dismissStack(this);
                UserSwitcherController.this.removeGuestUser(this.mGuestId, this.mTargetId);
            }
        }

        public ExitGuestDialog(Context context, int i, int i2) {
            super(context);
            int i3;
            int i4;
            if (UserSwitcherController.this.mGuestUserAutoCreated) {
                i3 = C1777R.string.guest_reset_guest_dialog_title;
            } else {
                i3 = C1777R.string.guest_exit_guest_dialog_title;
            }
            setTitle(i3);
            setMessage(context.getString(C1777R.string.guest_exit_guest_dialog_message));
            setButton(-3, context.getString(17039360), this);
            if (UserSwitcherController.this.mGuestUserAutoCreated) {
                i4 = C1777R.string.guest_reset_guest_confirm_button;
            } else {
                i4 = C1777R.string.guest_exit_guest_dialog_remove;
            }
            setButton(-1, context.getString(i4), this);
            SystemUIDialog.setWindowOnTop(this, UserSwitcherController.this.mKeyguardStateController.isShowing());
            setCanceledOnTouchOutside(false);
            this.mGuestId = i;
            this.mTargetId = i2;
        }
    }

    public static final class UserRecord {
        public RestrictedLockUtils.EnforcedAdmin enforcedAdmin;
        public final UserInfo info;
        public final boolean isAddSupervisedUser;
        public final boolean isAddUser;
        public final boolean isCurrent;
        public boolean isDisabledByAdmin;
        public final boolean isGuest;
        public final boolean isRestricted;
        public boolean isSwitchToEnabled;
        public final Bitmap picture;

        public final int resolveId() {
            UserInfo userInfo;
            if (this.isGuest || (userInfo = this.info) == null) {
                return -10000;
            }
            return userInfo.id;
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("UserRecord(");
            if (this.info != null) {
                m.append("name=\"");
                m.append(this.info.name);
                m.append("\" id=");
                m.append(this.info.id);
            } else if (this.isGuest) {
                m.append("<add guest placeholder>");
            } else if (this.isAddUser) {
                m.append("<add user placeholder>");
            }
            if (this.isGuest) {
                m.append(" <isGuest>");
            }
            if (this.isAddUser) {
                m.append(" <isAddUser>");
            }
            if (this.isAddSupervisedUser) {
                m.append(" <isAddSupervisedUser>");
            }
            if (this.isCurrent) {
                m.append(" <isCurrent>");
            }
            if (this.picture != null) {
                m.append(" <hasPicture>");
            }
            if (this.isRestricted) {
                m.append(" <isRestricted>");
            }
            if (this.isDisabledByAdmin) {
                m.append(" <isDisabledByAdmin>");
                m.append(" enforcedAdmin=");
                m.append(this.enforcedAdmin);
            }
            if (this.isSwitchToEnabled) {
                m.append(" <isSwitchToEnabled>");
            }
            m.append(')');
            return m.toString();
        }

        public UserRecord(UserInfo userInfo, Bitmap bitmap, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6) {
            this.info = userInfo;
            this.picture = bitmap;
            this.isGuest = z;
            this.isCurrent = z2;
            this.isAddUser = z3;
            this.isRestricted = z4;
            this.isSwitchToEnabled = z5;
            this.isAddSupervisedUser = z6;
        }
    }

    public UserSwitcherController(Context context, IActivityManager iActivityManager, UserManager userManager, UserTracker userTracker, KeyguardStateController keyguardStateController, DeviceProvisionedController deviceProvisionedController, DevicePolicyManager devicePolicyManager, Handler handler, ActivityStarter activityStarter, BroadcastDispatcher broadcastDispatcher, UiEventLogger uiEventLogger, FalsingManager falsingManager, TelephonyListenerManager telephonyListenerManager, SecureSettings secureSettings, Executor executor, Executor executor2, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, DumpManager dumpManager, Lazy<ShadeController> lazy, DialogLaunchAnimator dialogLaunchAnimator) {
        Context context2 = context;
        UserTracker userTracker2 = userTracker;
        KeyguardStateController keyguardStateController2 = keyguardStateController;
        Handler handler2 = handler;
        BroadcastDispatcher broadcastDispatcher2 = broadcastDispatcher;
        UiEventLogger uiEventLogger2 = uiEventLogger;
        C16512 r14 = new TelephonyCallback.CallStateListener() {
            public int mCallState;

            public final void onCallStateChanged(int i) {
                if (this.mCallState != i) {
                    this.mCallState = i;
                    UserSwitcherController.this.refreshUsers(-10000);
                }
            }
        };
        this.mPhoneStateListener = r14;
        this.mReceiver = new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                int i;
                boolean z;
                Context context2;
                int i2;
                int i3;
                int i4;
                boolean z2;
                boolean z3;
                Context context3 = context;
                Intent intent2 = intent;
                if ("android.intent.action.USER_SWITCHED".equals(intent.getAction())) {
                    AlertDialog alertDialog = UserSwitcherController.this.mExitGuestDialog;
                    if (alertDialog != null && alertDialog.isShowing()) {
                        UserSwitcherController.this.mExitGuestDialog.cancel();
                        UserSwitcherController.this.mExitGuestDialog = null;
                    }
                    int intExtra = intent2.getIntExtra("android.intent.extra.user_handle", -1);
                    UserInfo userInfo = UserSwitcherController.this.mUserManager.getUserInfo(intExtra);
                    int size = UserSwitcherController.this.mUsers.size();
                    int i5 = 0;
                    while (i5 < size) {
                        UserRecord userRecord = UserSwitcherController.this.mUsers.get(i5);
                        UserInfo userInfo2 = userRecord.info;
                        if (userInfo2 == null) {
                            i3 = intExtra;
                            i4 = size;
                        } else {
                            if (userInfo2.id == intExtra) {
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                            if (userRecord.isCurrent != z2) {
                                ArrayList<UserRecord> arrayList = UserSwitcherController.this.mUsers;
                                i3 = intExtra;
                                i4 = size;
                                UserRecord userRecord2 = r10;
                                z3 = z2;
                                UserRecord userRecord3 = new UserRecord(userInfo2, userRecord.picture, userRecord.isGuest, z2, userRecord.isAddUser, userRecord.isRestricted, userRecord.isSwitchToEnabled, userRecord.isAddSupervisedUser);
                                arrayList.set(i5, userRecord2);
                            } else {
                                i3 = intExtra;
                                i4 = size;
                                z3 = z2;
                            }
                            if (z3 && !userRecord.isGuest) {
                                UserSwitcherController.this.mLastNonGuestUser = userRecord.info.id;
                            }
                            if ((userInfo == null || !userInfo.isAdmin()) && userRecord.isRestricted) {
                                UserSwitcherController.this.mUsers.remove(i5);
                                i5--;
                            }
                        }
                        i5++;
                        Context context4 = context;
                        intExtra = i3;
                        size = i4;
                    }
                    UserSwitcherController.this.notifyAdapters();
                    UserSwitcherController userSwitcherController = UserSwitcherController.this;
                    int i6 = userSwitcherController.mSecondaryUser;
                    if (i6 != -10000) {
                        context2 = context;
                        context2.stopServiceAsUser(userSwitcherController.mSecondaryUserServiceIntent, UserHandle.of(i6));
                        UserSwitcherController.this.mSecondaryUser = -10000;
                    } else {
                        context2 = context;
                    }
                    if (!(userInfo == null || (i2 = userInfo.id) == 0)) {
                        context2.startServiceAsUser(UserSwitcherController.this.mSecondaryUserServiceIntent, UserHandle.of(i2));
                        UserSwitcherController.this.mSecondaryUser = userInfo.id;
                    }
                    UserSwitcherController userSwitcherController2 = UserSwitcherController.this;
                    if (userSwitcherController2.mGuestUserAutoCreated) {
                        userSwitcherController2.guaranteeGuestPresent();
                    }
                    z = true;
                    i = -10000;
                } else {
                    if ("android.intent.action.USER_INFO_CHANGED".equals(intent.getAction())) {
                        i = intent2.getIntExtra("android.intent.extra.user_handle", -10000);
                    } else if (!"android.intent.action.USER_UNLOCKED".equals(intent.getAction()) || intent2.getIntExtra("android.intent.extra.user_handle", -10000) == 0) {
                        i = -10000;
                    } else {
                        return;
                    }
                    z = false;
                }
                UserSwitcherController.this.refreshUsers(i);
                if (z) {
                    UserSwitcherController.this.mUnpauseRefreshUsers.run();
                }
            }
        };
        this.mUnpauseRefreshUsers = new Runnable() {
            public final void run() {
                UserSwitcherController.this.mHandler.removeCallbacks(this);
                UserSwitcherController userSwitcherController = UserSwitcherController.this;
                userSwitcherController.mPauseRefreshUsers = false;
                userSwitcherController.refreshUsers(-10000);
            }
        };
        C16545 r15 = new KeyguardStateController.Callback() {
            public final void onKeyguardShowingChanged() {
                if (!UserSwitcherController.this.mKeyguardStateController.isShowing()) {
                    UserSwitcherController userSwitcherController = UserSwitcherController.this;
                    userSwitcherController.mHandler.post(new WifiEntry$$ExternalSyntheticLambda2(userSwitcherController, 9));
                    return;
                }
                UserSwitcherController.this.notifyAdapters();
            }
        };
        this.mCallback = r15;
        this.mGuaranteeGuestPresentAfterProvisioned = new DeviceProvisionedController.DeviceProvisionedListener() {
            public final void onDeviceProvisionedChanged() {
                if (UserSwitcherController.this.isDeviceAllowedToAddGuest()) {
                    UserSwitcherController.this.mBgExecutor.execute(new AccessPoint$$ExternalSyntheticLambda1(this, 8));
                    UserSwitcherController.this.guaranteeGuestPresent();
                }
            }
        };
        this.mContext = context2;
        this.mActivityManager = iActivityManager;
        this.mUserTracker = userTracker2;
        this.mUiEventLogger = uiEventLogger2;
        this.mFalsingManager = falsingManager;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = latencyTracker;
        GuestResumeSessionReceiver guestResumeSessionReceiver = new GuestResumeSessionReceiver(this, userTracker2, uiEventLogger2, secureSettings);
        this.mGuestResumeSessionReceiver = guestResumeSessionReceiver;
        this.mBgExecutor = executor;
        this.mUiExecutor = executor2;
        if (!UserManager.isGuestUserEphemeral()) {
            broadcastDispatcher2.registerReceiver(guestResumeSessionReceiver, new IntentFilter("android.intent.action.USER_SWITCHED"), (Executor) null, UserHandle.SYSTEM);
        }
        this.mGuestUserAutoCreated = context.getResources().getBoolean(17891665);
        this.mGuestIsResetting = new AtomicBoolean();
        this.mGuestCreationScheduled = new AtomicBoolean();
        this.mKeyguardStateController = keyguardStateController2;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mHandler = handler2;
        this.mActivityStarter = activityStarter;
        this.mUserManager = userManager;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mShadeController = lazy;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_ADDED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.intent.action.USER_INFO_CHANGED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_STOPPED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        broadcastDispatcher2.registerReceiver(this.mReceiver, intentFilter, (Executor) null, UserHandle.SYSTEM);
        this.mSimpleUserSwitcher = shouldUseSimpleUserSwitcher();
        this.mSecondaryUserServiceIntent = new Intent(context2, SystemUISecondaryUserService.class);
        Context context3 = context;
        context3.registerReceiverAsUser(this.mReceiver, UserHandle.SYSTEM, new IntentFilter(), "com.android.systemui.permission.SELF", (Handler) null, 2);
        C16501 r1 = new ContentObserver(handler2) {
            public final void onChange(boolean z) {
                UserSwitcherController userSwitcherController = UserSwitcherController.this;
                userSwitcherController.mSimpleUserSwitcher = userSwitcherController.shouldUseSimpleUserSwitcher();
                UserSwitcherController userSwitcherController2 = UserSwitcherController.this;
                boolean z2 = false;
                if (Settings.Global.getInt(userSwitcherController2.mContext.getContentResolver(), "add_users_when_locked", 0) != 0) {
                    z2 = true;
                }
                userSwitcherController2.mAddUsersFromLockScreen = z2;
                UserSwitcherController.this.refreshUsers(-10000);
            }
        };
        this.mSettingsObserver = r1;
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("lockscreenSimpleUserSwitcher"), true, r1);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("add_users_when_locked"), true, r1);
        context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("allow_user_switching_when_system_user_locked"), true, r1);
        r1.onChange(false);
        keyguardStateController2.addCallback(r15);
        telephonyListenerManager.addCallStateListener(r14);
        this.mCreateSupervisedUserPackage = context2.getString(17040026);
        dumpManager.registerDumpable("UserSwitcherController", this);
        refreshUsers(-10000);
    }

    @VisibleForTesting
    public void addAdapter(WeakReference<BaseUserAdapter> weakReference) {
        this.mAdapters.add(weakReference);
    }

    public final void checkIfAddUserDisallowedByAdminOnly(UserRecord userRecord) {
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced = RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, "no_add_user", this.mUserTracker.getUserId());
        if (checkIfRestrictionEnforced == null || RestrictedLockUtilsInternal.hasBaseUserRestriction(this.mContext, "no_add_user", this.mUserTracker.getUserId())) {
            userRecord.isDisabledByAdmin = false;
            userRecord.enforcedAdmin = null;
            return;
        }
        userRecord.isDisabledByAdmin = true;
        userRecord.enforcedAdmin = checkIfRestrictionEnforced;
    }

    public final int createGuest() {
        try {
            UserManager userManager = this.mUserManager;
            Context context = this.mContext;
            UserInfo createGuest = userManager.createGuest(context, context.getString(C1777R.string.guest_nickname));
            if (createGuest != null) {
                return createGuest.id;
            }
            Log.e("UserSwitcherController", "Couldn't create guest, most likely because there already exists one");
            return -10000;
        } catch (UserManager.UserOperationException e) {
            Log.e("UserSwitcherController", "Couldn't create guest user", e);
            return -10000;
        }
    }

    public final boolean currentUserCanCreateUsers() {
        UserInfo userInfo = this.mUserTracker.getUserInfo();
        if (userInfo == null || ((!userInfo.isAdmin() && this.mUserTracker.getUserId() != 0) || !(!this.mUserManager.hasBaseUserRestriction("no_add_user", UserHandle.SYSTEM)))) {
            return false;
        }
        return true;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "UserSwitcherController state:", "  mLastNonGuestUser=");
        m.append(this.mLastNonGuestUser);
        printWriter.println(m.toString());
        printWriter.print("  mUsers.size=");
        printWriter.println(this.mUsers.size());
        for (int i = 0; i < this.mUsers.size(); i++) {
            printWriter.print("    ");
            printWriter.println(this.mUsers.get(i).toString());
        }
        StringBuilder m2 = KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m26m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("mSimpleUserSwitcher="), this.mSimpleUserSwitcher, printWriter, "mGuestUserAutoCreated=");
        m2.append(this.mGuestUserAutoCreated);
        printWriter.println(m2.toString());
    }

    public final String getCurrentUserName() {
        UserRecord orElse;
        UserInfo userInfo;
        if (this.mUsers.isEmpty() || (orElse = this.mUsers.stream().filter(PeopleTileViewHelper$$ExternalSyntheticLambda4.INSTANCE$1).findFirst().orElse((Object) null)) == null || (userInfo = orElse.info) == null) {
            return null;
        }
        if (orElse.isGuest) {
            return this.mContext.getString(C1777R.string.guest_nickname);
        }
        return userInfo.name;
    }

    public final boolean isDeviceAllowedToAddGuest() {
        if (!this.mDeviceProvisionedController.isDeviceProvisioned() || this.mDevicePolicyManager.isDeviceManaged()) {
            return false;
        }
        return true;
    }

    public final void notifyAdapters() {
        for (int size = this.mAdapters.size() - 1; size >= 0; size--) {
            BaseUserAdapter baseUserAdapter = (BaseUserAdapter) this.mAdapters.get(size).get();
            if (baseUserAdapter != null) {
                baseUserAdapter.notifyDataSetChanged();
            } else {
                this.mAdapters.remove(size);
            }
        }
    }

    @VisibleForTesting
    public void onUserListItemClicked(UserRecord userRecord, UserSwitchDialogController.DialogShower dialogShower) {
        int i;
        UserInfo userInfo;
        int i2;
        UserInfo userInfo2;
        int i3 = 0;
        if (userRecord.isGuest && userRecord.info == null) {
            i = createGuest();
            if (i != -10000) {
                this.mUiEventLogger.log(QSUserSwitcherEvent.QS_USER_GUEST_ADD);
            } else {
                return;
            }
        } else if (userRecord.isAddUser) {
            Dialog dialog = this.mAddUserDialog;
            if (dialog != null && dialog.isShowing()) {
                this.mAddUserDialog.cancel();
            }
            AddUserDialog addUserDialog = new AddUserDialog(this.mContext);
            this.mAddUserDialog = addUserDialog;
            if (dialogShower != null) {
                dialogShower.showDialog(addUserDialog);
                return;
            } else {
                addUserDialog.show();
                return;
            }
        } else if (userRecord.isAddSupervisedUser) {
            Intent addFlags = new Intent().setAction("android.os.action.CREATE_SUPERVISED_USER").setPackage(this.mCreateSupervisedUserPackage).addFlags(268435456);
            if (this.mContext.getPackageManager().resolveActivity(addFlags, 0) == null) {
                addFlags.setPackage((String) null).setClassName(ThemeOverlayApplier.SETTINGS_PACKAGE, "com.android.settings.users.AddSupervisedUserActivity");
            }
            this.mContext.startActivity(addFlags);
            return;
        } else {
            i = userRecord.info.id;
        }
        int userId = this.mUserTracker.getUserId();
        if (userId == i) {
            if (userRecord.isGuest) {
                if (this.mResumeUserOnGuestLogout && (i2 = this.mLastNonGuestUser) != 0 && (userInfo2 = this.mUserManager.getUserInfo(i2)) != null && userInfo2.isEnabled() && userInfo2.supportsSwitchToByUser()) {
                    i3 = userInfo2.id;
                }
                showExitGuestDialog(i, i3, dialogShower);
            }
        } else if (!UserManager.isGuestUserEphemeral() || (userInfo = this.mUserManager.getUserInfo(userId)) == null || !userInfo.isGuest()) {
            if (dialogShower != null) {
                dialogShower.dismiss();
            }
            switchToUserId(i);
        } else {
            showExitGuestDialog(userId, userRecord.resolveId(), dialogShower);
        }
    }

    public final void refreshUsers(int i) {
        UserInfo userInfo;
        if (i != -10000) {
            this.mForcePictureLoadForUserId.put(i, true);
        }
        if (!this.mPauseRefreshUsers) {
            boolean z = this.mForcePictureLoadForUserId.get(-1);
            SparseArray sparseArray = new SparseArray(this.mUsers.size());
            int size = this.mUsers.size();
            for (int i2 = 0; i2 < size; i2++) {
                UserRecord userRecord = this.mUsers.get(i2);
                if (!(userRecord == null || userRecord.picture == null || (userInfo = userRecord.info) == null || z || this.mForcePictureLoadForUserId.get(userInfo.id))) {
                    sparseArray.put(userRecord.info.id, userRecord.picture);
                }
            }
            this.mForcePictureLoadForUserId.clear();
            this.mBgExecutor.execute(new ScrimView$$ExternalSyntheticLambda1(this, sparseArray, 2));
        }
    }

    public final void removeGuestUser(int i, int i2) {
        UserInfo userInfo = this.mUserTracker.getUserInfo();
        if (userInfo.id != i) {
            StringBuilder m = ExifInterface$$ExternalSyntheticOutline0.m13m("User requesting to start a new session (", i, ") is not current user (");
            m.append(userInfo.id);
            m.append(")");
            Log.w("UserSwitcherController", m.toString());
        } else if (!userInfo.isGuest()) {
            Log.w("UserSwitcherController", "User requesting to start a new session (" + i + ") is not a guest");
        } else if (!this.mUserManager.markGuestForDeletion(userInfo.id)) {
            GridLayoutManager$$ExternalSyntheticOutline1.m20m("Couldn't mark the guest for deletion for user ", i, "UserSwitcherController");
        } else if (i2 == -10000) {
            try {
                int createGuest = createGuest();
                if (createGuest == -10000) {
                    Log.e("UserSwitcherController", "Could not create new guest, switching back to system user");
                    switchToUserId(0);
                    this.mUserManager.removeUser(userInfo.id);
                    WindowManagerGlobal.getWindowManagerService().lockNow((Bundle) null);
                    return;
                }
                switchToUserId(createGuest);
                this.mUserManager.removeUser(userInfo.id);
            } catch (RemoteException unused) {
                Log.e("UserSwitcherController", "Couldn't remove guest because ActivityManager or WindowManager is dead");
            }
        } else {
            if (this.mGuestUserAutoCreated) {
                this.mGuestIsResetting.set(true);
            }
            switchToUserId(i2);
            this.mUserManager.removeUser(userInfo.id);
        }
    }

    public final boolean shouldUseSimpleUserSwitcher() {
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "lockscreenSimpleUserSwitcher", this.mContext.getResources().getBoolean(17891651) ? 1 : 0) != 0) {
            return true;
        }
        return false;
    }

    public final void showExitGuestDialog(int i, int i2, UserSwitchDialogController.DialogShower dialogShower) {
        AlertDialog alertDialog = this.mExitGuestDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mExitGuestDialog.cancel();
        }
        ExitGuestDialog exitGuestDialog = new ExitGuestDialog(this.mContext, i, i2);
        this.mExitGuestDialog = exitGuestDialog;
        if (dialogShower != null) {
            dialogShower.showDialog(exitGuestDialog);
        } else {
            exitGuestDialog.show();
        }
    }

    public final void switchToUserId(int i) {
        try {
            this.mInteractionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(37, this.mView).setTimeout(20000));
            this.mLatencyTracker.onActionStart(12);
            if (!this.mPauseRefreshUsers) {
                this.mHandler.postDelayed(this.mUnpauseRefreshUsers, 3000);
                this.mPauseRefreshUsers = true;
            }
            this.mActivityManager.switchUser(i);
        } catch (RemoteException e) {
            Log.e("UserSwitcherController", "Couldn't switch user.", e);
        }
    }

    public final void guaranteeGuestPresent() {
        if (isDeviceAllowedToAddGuest() && this.mUserManager.findCurrentGuestUser() == null && this.mGuestCreationScheduled.compareAndSet(false, true)) {
            this.mBgExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda7(this, 3));
        }
    }

    @VisibleForTesting
    public KeyguardStateController getKeyguardStateController() {
        return this.mKeyguardStateController;
    }

    @VisibleForTesting
    public ArrayList<UserRecord> getUsers() {
        return this.mUsers;
    }
}
