package com.android.systemui.statusbar;

import android.app.AlertDialog;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import android.view.ContextThemeWrapper;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyboardShortcutGroup;
import android.view.KeyboardShortcutInfo;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.MetricsLogger;
import com.android.p012wm.shell.C1777R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class KeyboardShortcuts {
    public static KeyboardShortcuts sInstance;
    public static final Object sLock = new Object();
    public final C11312 mApplicationItemsComparator = new Comparator<KeyboardShortcutInfo>() {
        public final int compare(Object obj, Object obj2) {
            boolean z;
            boolean z2;
            KeyboardShortcutInfo keyboardShortcutInfo = (KeyboardShortcutInfo) obj;
            KeyboardShortcutInfo keyboardShortcutInfo2 = (KeyboardShortcutInfo) obj2;
            if (keyboardShortcutInfo.getLabel() == null || keyboardShortcutInfo.getLabel().toString().isEmpty()) {
                z = true;
            } else {
                z = false;
            }
            if (keyboardShortcutInfo2.getLabel() == null || keyboardShortcutInfo2.getLabel().toString().isEmpty()) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z && z2) {
                return 0;
            }
            if (z) {
                return 1;
            }
            if (z2) {
                return -1;
            }
            return keyboardShortcutInfo.getLabel().toString().compareToIgnoreCase(keyboardShortcutInfo2.getLabel().toString());
        }
    };
    public KeyCharacterMap mBackupKeyCharacterMap;
    public final ContextThemeWrapper mContext;
    public final C11301 mDialogCloseListener = new DialogInterface.OnClickListener() {
        public final void onClick(DialogInterface dialogInterface, int i) {
            KeyboardShortcuts keyboardShortcuts = KeyboardShortcuts.this;
            Objects.requireNonNull(keyboardShortcuts);
            AlertDialog alertDialog = keyboardShortcuts.mKeyboardShortcutsDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
                keyboardShortcuts.mKeyboardShortcutsDialog = null;
            }
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public KeyCharacterMap mKeyCharacterMap;
    public AlertDialog mKeyboardShortcutsDialog;
    public final SparseArray<Drawable> mModifierDrawables;
    public final int[] mModifierList = {65536, 4096, 2, 1, 4, 8};
    public final SparseArray<String> mModifierNames;
    public final IPackageManager mPackageManager;
    public final SparseArray<Drawable> mSpecialCharacterDrawables;
    public final SparseArray<String> mSpecialCharacterNames;

    public final class ShortcutKeyAccessibilityDelegate extends View.AccessibilityDelegate {
        public String mContentDescription;

        public ShortcutKeyAccessibilityDelegate(String str) {
            this.mContentDescription = str;
        }

        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            String str = this.mContentDescription;
            if (str != null) {
                accessibilityNodeInfo.setContentDescription(str.toLowerCase());
            }
        }
    }

    public static final class StringDrawableContainer {
        public Drawable mDrawable;
        public String mString;

        public StringDrawableContainer(String str, Drawable drawable) {
            this.mString = str;
            this.mDrawable = drawable;
        }
    }

    public KeyboardShortcuts(Context context) {
        Context context2 = context;
        SparseArray<String> sparseArray = new SparseArray<>();
        this.mSpecialCharacterNames = sparseArray;
        SparseArray<String> sparseArray2 = new SparseArray<>();
        this.mModifierNames = sparseArray2;
        SparseArray<Drawable> sparseArray3 = new SparseArray<>();
        this.mSpecialCharacterDrawables = sparseArray3;
        SparseArray<Drawable> sparseArray4 = new SparseArray<>();
        this.mModifierDrawables = sparseArray4;
        this.mContext = new ContextThemeWrapper(context2, 16974371);
        this.mPackageManager = AppGlobals.getPackageManager();
        sparseArray.put(3, context2.getString(C1777R.string.keyboard_key_home));
        sparseArray.put(4, context2.getString(C1777R.string.keyboard_key_back));
        sparseArray.put(19, context2.getString(C1777R.string.keyboard_key_dpad_up));
        sparseArray.put(20, context2.getString(C1777R.string.keyboard_key_dpad_down));
        sparseArray.put(21, context2.getString(C1777R.string.keyboard_key_dpad_left));
        sparseArray.put(22, context2.getString(C1777R.string.keyboard_key_dpad_right));
        sparseArray.put(23, context2.getString(C1777R.string.keyboard_key_dpad_center));
        sparseArray.put(56, ".");
        sparseArray.put(61, context2.getString(C1777R.string.keyboard_key_tab));
        sparseArray.put(62, context2.getString(C1777R.string.keyboard_key_space));
        sparseArray.put(66, context2.getString(C1777R.string.keyboard_key_enter));
        sparseArray.put(67, context2.getString(C1777R.string.keyboard_key_backspace));
        sparseArray.put(85, context2.getString(C1777R.string.keyboard_key_media_play_pause));
        sparseArray.put(86, context2.getString(C1777R.string.keyboard_key_media_stop));
        sparseArray.put(87, context2.getString(C1777R.string.keyboard_key_media_next));
        sparseArray.put(88, context2.getString(C1777R.string.keyboard_key_media_previous));
        sparseArray.put(89, context2.getString(C1777R.string.keyboard_key_media_rewind));
        sparseArray.put(90, context2.getString(C1777R.string.keyboard_key_media_fast_forward));
        sparseArray.put(92, context2.getString(C1777R.string.keyboard_key_page_up));
        sparseArray.put(93, context2.getString(C1777R.string.keyboard_key_page_down));
        sparseArray.put(96, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"A"}));
        sparseArray.put(97, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"B"}));
        sparseArray.put(98, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"C"}));
        sparseArray.put(99, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"X"}));
        sparseArray.put(100, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"Y"}));
        sparseArray.put(101, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"Z"}));
        sparseArray.put(102, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"L1"}));
        sparseArray.put(103, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"R1"}));
        sparseArray.put(104, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"L2"}));
        sparseArray.put(105, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"R2"}));
        sparseArray.put(108, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"Start"}));
        sparseArray.put(109, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"Select"}));
        sparseArray.put(110, context2.getString(C1777R.string.keyboard_key_button_template, new Object[]{"Mode"}));
        sparseArray.put(112, context2.getString(C1777R.string.keyboard_key_forward_del));
        sparseArray.put(111, "Esc");
        sparseArray.put(120, "SysRq");
        sparseArray.put(121, "Break");
        sparseArray.put(116, "Scroll Lock");
        sparseArray.put(122, context2.getString(C1777R.string.keyboard_key_move_home));
        sparseArray.put(123, context2.getString(C1777R.string.keyboard_key_move_end));
        sparseArray.put(124, context2.getString(C1777R.string.keyboard_key_insert));
        sparseArray.put(131, "F1");
        sparseArray.put(132, "F2");
        sparseArray.put(133, "F3");
        sparseArray.put(134, "F4");
        sparseArray.put(135, "F5");
        sparseArray.put(136, "F6");
        sparseArray.put(137, "F7");
        sparseArray.put(138, "F8");
        sparseArray.put(139, "F9");
        sparseArray.put(140, "F10");
        sparseArray.put(141, "F11");
        sparseArray.put(142, "F12");
        sparseArray.put(143, context2.getString(C1777R.string.keyboard_key_num_lock));
        sparseArray.put(144, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"0"}));
        sparseArray.put(145, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"1"}));
        sparseArray.put(146, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"2"}));
        sparseArray.put(147, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"3"}));
        sparseArray.put(148, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"4"}));
        sparseArray.put(149, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"5"}));
        sparseArray.put(150, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"6"}));
        sparseArray.put(151, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"7"}));
        sparseArray.put(152, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"8"}));
        sparseArray.put(153, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"9"}));
        sparseArray.put(154, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"/"}));
        sparseArray.put(155, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"*"}));
        sparseArray.put(156, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"-"}));
        sparseArray.put(157, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"+"}));
        sparseArray.put(158, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"."}));
        sparseArray.put(159, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{","}));
        sparseArray.put(160, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{context2.getString(C1777R.string.keyboard_key_enter)}));
        sparseArray.put(161, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"="}));
        sparseArray.put(162, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{"("}));
        sparseArray.put(163, context2.getString(C1777R.string.keyboard_key_numpad_template, new Object[]{")"}));
        sparseArray.put(211, "半角/全角");
        sparseArray.put(212, "英数");
        sparseArray.put(213, "無変換");
        sparseArray.put(214, "変換");
        sparseArray.put(215, "かな");
        sparseArray2.put(65536, "Meta");
        sparseArray2.put(4096, "Ctrl");
        sparseArray2.put(2, "Alt");
        sparseArray2.put(1, "Shift");
        sparseArray2.put(4, "Sym");
        sparseArray2.put(8, "Fn");
        sparseArray3.put(67, context2.getDrawable(C1777R.C1778drawable.ic_ksh_key_backspace));
        sparseArray3.put(66, context2.getDrawable(C1777R.C1778drawable.ic_ksh_key_enter));
        sparseArray3.put(19, context2.getDrawable(C1777R.C1778drawable.ic_ksh_key_up));
        sparseArray3.put(22, context2.getDrawable(C1777R.C1778drawable.ic_ksh_key_right));
        sparseArray3.put(20, context2.getDrawable(C1777R.C1778drawable.ic_ksh_key_down));
        sparseArray3.put(21, context2.getDrawable(C1777R.C1778drawable.ic_ksh_key_left));
        sparseArray4.put(65536, context2.getDrawable(C1777R.C1778drawable.ic_ksh_key_meta));
    }

    public static void dismiss() {
        synchronized (sLock) {
            KeyboardShortcuts keyboardShortcuts = sInstance;
            if (keyboardShortcuts != null) {
                MetricsLogger.hidden(keyboardShortcuts.mContext, 500);
                KeyboardShortcuts keyboardShortcuts2 = sInstance;
                Objects.requireNonNull(keyboardShortcuts2);
                AlertDialog alertDialog = keyboardShortcuts2.mKeyboardShortcutsDialog;
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    keyboardShortcuts2.mKeyboardShortcutsDialog = null;
                }
                sInstance = null;
            }
        }
    }

    public static void show(Context context, int i) {
        MetricsLogger.visible(context, 500);
        synchronized (sLock) {
            KeyboardShortcuts keyboardShortcuts = sInstance;
            if (keyboardShortcuts != null && !keyboardShortcuts.mContext.equals(context)) {
                dismiss();
            }
            if (sInstance == null) {
                sInstance = new KeyboardShortcuts(context);
            }
            sInstance.showKeyboardShortcuts(i);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0048 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.graphics.drawable.Icon getIconForIntentCategory(java.lang.String r7, int r8) {
        /*
            r6 = this;
            android.content.Intent r1 = new android.content.Intent
            java.lang.String r0 = "android.intent.action.MAIN"
            r1.<init>(r0)
            r1.addCategory(r7)
            r7 = 0
            android.content.pm.IPackageManager r0 = r6.mPackageManager     // Catch:{ RemoteException -> 0x0030 }
            android.view.ContextThemeWrapper r2 = r6.mContext     // Catch:{ RemoteException -> 0x0030 }
            android.content.ContentResolver r2 = r2.getContentResolver()     // Catch:{ RemoteException -> 0x0030 }
            java.lang.String r2 = r1.resolveTypeIfNeeded(r2)     // Catch:{ RemoteException -> 0x0030 }
            r3 = 0
            r5 = r8
            android.content.pm.ResolveInfo r0 = r0.resolveIntent(r1, r2, r3, r5)     // Catch:{ RemoteException -> 0x0030 }
            if (r0 == 0) goto L_0x0038
            android.content.pm.ActivityInfo r0 = r0.activityInfo     // Catch:{ RemoteException -> 0x0030 }
            if (r0 != 0) goto L_0x0025
            goto L_0x0038
        L_0x0025:
            android.content.pm.IPackageManager r6 = r6.mPackageManager     // Catch:{ RemoteException -> 0x0030 }
            java.lang.String r0 = r0.packageName     // Catch:{ RemoteException -> 0x0030 }
            r1 = 0
            android.content.pm.PackageInfo r6 = r6.getPackageInfo(r0, r1, r8)     // Catch:{ RemoteException -> 0x0030 }
            goto L_0x0039
        L_0x0030:
            r6 = move-exception
            java.lang.String r8 = "KeyboardShortcuts"
            java.lang.String r0 = "PackageManagerService is dead"
            android.util.Log.e(r8, r0, r6)
        L_0x0038:
            r6 = r7
        L_0x0039:
            if (r6 == 0) goto L_0x0048
            android.content.pm.ApplicationInfo r6 = r6.applicationInfo
            int r8 = r6.icon
            if (r8 == 0) goto L_0x0048
            java.lang.String r6 = r6.packageName
            android.graphics.drawable.Icon r6 = android.graphics.drawable.Icon.createWithResource(r6, r8)
            return r6
        L_0x0048:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyboardShortcuts.getIconForIntentCategory(java.lang.String, int):android.graphics.drawable.Icon");
    }

    public final void showKeyboardShortcuts(int i) {
        InputDevice inputDevice;
        InputManager instance = InputManager.getInstance();
        this.mBackupKeyCharacterMap = instance.getInputDevice(-1).getKeyCharacterMap();
        if (i == -1 || (inputDevice = instance.getInputDevice(i)) == null) {
            int[] inputDeviceIds = instance.getInputDeviceIds();
            int i2 = 0;
            while (true) {
                if (i2 >= inputDeviceIds.length) {
                    this.mKeyCharacterMap = this.mBackupKeyCharacterMap;
                    break;
                }
                InputDevice inputDevice2 = instance.getInputDevice(inputDeviceIds[i2]);
                if (inputDevice2.getId() != -1 && inputDevice2.isFullKeyboard()) {
                    this.mKeyCharacterMap = inputDevice2.getKeyCharacterMap();
                    break;
                }
                i2++;
            }
        } else {
            this.mKeyCharacterMap = inputDevice.getKeyCharacterMap();
        }
        ((WindowManager) this.mContext.getSystemService("window")).requestAppKeyboardShortcuts(new WindowManager.KeyboardShortcutsReceiver() {
            public final void onKeyboardShortcutsReceived(List<KeyboardShortcutGroup> list) {
                PackageInfo packageInfo;
                KeyboardShortcuts keyboardShortcuts = KeyboardShortcuts.this;
                Objects.requireNonNull(keyboardShortcuts);
                KeyboardShortcutGroup keyboardShortcutGroup = new KeyboardShortcutGroup(keyboardShortcuts.mContext.getString(C1777R.string.keyboard_shortcut_group_system), true);
                keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(keyboardShortcuts.mContext.getString(C1777R.string.keyboard_shortcut_group_system_home), 66, 65536));
                keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(keyboardShortcuts.mContext.getString(C1777R.string.keyboard_shortcut_group_system_back), 67, 65536));
                keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(keyboardShortcuts.mContext.getString(C1777R.string.keyboard_shortcut_group_system_recents), 61, 2));
                keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(keyboardShortcuts.mContext.getString(C1777R.string.keyboard_shortcut_group_system_notifications), 42, 65536));
                keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(keyboardShortcuts.mContext.getString(C1777R.string.keyboard_shortcut_group_system_shortcuts_helper), 76, 65536));
                keyboardShortcutGroup.addItem(new KeyboardShortcutInfo(keyboardShortcuts.mContext.getString(C1777R.string.keyboard_shortcut_group_system_switch_input), 62, 65536));
                list.add(keyboardShortcutGroup);
                KeyboardShortcuts keyboardShortcuts2 = KeyboardShortcuts.this;
                Objects.requireNonNull(keyboardShortcuts2);
                int userId = keyboardShortcuts2.mContext.getUserId();
                ArrayList arrayList = new ArrayList();
                ComponentName assistComponentForUser = new AssistUtils(keyboardShortcuts2.mContext).getAssistComponentForUser(userId);
                KeyboardShortcutGroup keyboardShortcutGroup2 = null;
                if (assistComponentForUser != null) {
                    try {
                        packageInfo = keyboardShortcuts2.mPackageManager.getPackageInfo(assistComponentForUser.getPackageName(), 0, userId);
                    } catch (RemoteException unused) {
                        Log.e("KeyboardShortcuts", "PackageManagerService is dead");
                        packageInfo = null;
                    }
                    if (packageInfo != null) {
                        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
                        arrayList.add(new KeyboardShortcutInfo(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications_assist), Icon.createWithResource(applicationInfo.packageName, applicationInfo.icon), 0, 65536));
                    }
                }
                Icon iconForIntentCategory = keyboardShortcuts2.getIconForIntentCategory("android.intent.category.APP_BROWSER", userId);
                if (iconForIntentCategory != null) {
                    arrayList.add(new KeyboardShortcutInfo(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications_browser), iconForIntentCategory, 30, 65536));
                }
                Icon iconForIntentCategory2 = keyboardShortcuts2.getIconForIntentCategory("android.intent.category.APP_CONTACTS", userId);
                if (iconForIntentCategory2 != null) {
                    arrayList.add(new KeyboardShortcutInfo(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications_contacts), iconForIntentCategory2, 31, 65536));
                }
                Icon iconForIntentCategory3 = keyboardShortcuts2.getIconForIntentCategory("android.intent.category.APP_EMAIL", userId);
                if (iconForIntentCategory3 != null) {
                    arrayList.add(new KeyboardShortcutInfo(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications_email), iconForIntentCategory3, 33, 65536));
                }
                Icon iconForIntentCategory4 = keyboardShortcuts2.getIconForIntentCategory("android.intent.category.APP_MESSAGING", userId);
                if (iconForIntentCategory4 != null) {
                    arrayList.add(new KeyboardShortcutInfo(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications_sms), iconForIntentCategory4, 47, 65536));
                }
                Icon iconForIntentCategory5 = keyboardShortcuts2.getIconForIntentCategory("android.intent.category.APP_MUSIC", userId);
                if (iconForIntentCategory5 != null) {
                    arrayList.add(new KeyboardShortcutInfo(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications_music), iconForIntentCategory5, 44, 65536));
                }
                Icon iconForIntentCategory6 = keyboardShortcuts2.getIconForIntentCategory("android.intent.category.APP_CALENDAR", userId);
                if (iconForIntentCategory6 != null) {
                    arrayList.add(new KeyboardShortcutInfo(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications_calendar), iconForIntentCategory6, 40, 65536));
                }
                if (arrayList.size() != 0) {
                    Collections.sort(arrayList, keyboardShortcuts2.mApplicationItemsComparator);
                    keyboardShortcutGroup2 = new KeyboardShortcutGroup(keyboardShortcuts2.mContext.getString(C1777R.string.keyboard_shortcut_group_applications), arrayList, true);
                }
                if (keyboardShortcutGroup2 != null) {
                    list.add(keyboardShortcutGroup2);
                }
                KeyboardShortcuts keyboardShortcuts3 = KeyboardShortcuts.this;
                Objects.requireNonNull(keyboardShortcuts3);
                keyboardShortcuts3.mHandler.post(new Runnable(list) {
                    public final /* synthetic */ List val$keyboardShortcutGroups;

                    {
                        this.val$keyboardShortcutGroups = r2;
                    }

                    /* JADX WARNING: Removed duplicated region for block: B:43:0x018a  */
                    /* JADX WARNING: Removed duplicated region for block: B:44:0x0193  */
                    /* JADX WARNING: Removed duplicated region for block: B:47:0x019c  */
                    /* JADX WARNING: Removed duplicated region for block: B:48:0x01ae  */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void run() {
                        /*
                            r27 = this;
                            r0 = r27
                            com.android.systemui.statusbar.KeyboardShortcuts r1 = com.android.systemui.statusbar.KeyboardShortcuts.this
                            java.util.List r0 = r0.val$keyboardShortcutGroups
                            java.util.Objects.requireNonNull(r1)
                            android.app.AlertDialog$Builder r2 = new android.app.AlertDialog$Builder
                            android.view.ContextThemeWrapper r3 = r1.mContext
                            r2.<init>(r3)
                            android.view.ContextThemeWrapper r3 = r1.mContext
                            java.lang.String r4 = "layout_inflater"
                            java.lang.Object r3 = r3.getSystemService(r4)
                            android.view.LayoutInflater r3 = (android.view.LayoutInflater) r3
                            r4 = 2131624151(0x7f0e00d7, float:1.8875474E38)
                            r5 = 0
                            android.view.View r3 = r3.inflate(r4, r5)
                            r4 = 2131428161(0x7f0b0341, float:1.8477959E38)
                            android.view.View r4 = r3.findViewById(r4)
                            android.widget.LinearLayout r4 = (android.widget.LinearLayout) r4
                            android.view.ContextThemeWrapper r6 = r1.mContext
                            android.view.LayoutInflater r6 = android.view.LayoutInflater.from(r6)
                            int r7 = r0.size()
                            r8 = 2131624150(0x7f0e00d6, float:1.8875472E38)
                            r9 = 0
                            android.view.View r10 = r6.inflate(r8, r5, r9)
                            android.widget.TextView r10 = (android.widget.TextView) r10
                            r10.measure(r9, r9)
                            int r11 = r10.getMeasuredHeight()
                            int r12 = r10.getMeasuredHeight()
                            int r13 = r10.getPaddingTop()
                            int r12 = r12 - r13
                            int r10 = r10.getPaddingBottom()
                            int r12 = r12 - r10
                            r13 = r4
                            r10 = r9
                        L_0x0056:
                            if (r10 >= r7) goto L_0x02f9
                            java.lang.Object r15 = r0.get(r10)
                            android.view.KeyboardShortcutGroup r15 = (android.view.KeyboardShortcutGroup) r15
                            r5 = 2131624147(0x7f0e00d3, float:1.8875465E38)
                            android.view.View r5 = r6.inflate(r5, r13, r9)
                            android.widget.TextView r5 = (android.widget.TextView) r5
                            java.lang.CharSequence r8 = r15.getLabel()
                            r5.setText(r8)
                            boolean r8 = r15.isSystemGroup()
                            if (r8 == 0) goto L_0x007e
                            android.view.ContextThemeWrapper r8 = r1.mContext
                            r14 = 16843829(0x1010435, float:2.3696576E-38)
                            android.content.res.ColorStateList r8 = com.android.settingslib.Utils.getColorAttr(r8, r14)
                            goto L_0x008b
                        L_0x007e:
                            android.view.ContextThemeWrapper r8 = r1.mContext
                            r14 = 2131099897(0x7f0600f9, float:1.781216E38)
                            int r8 = r8.getColor(r14)
                            android.content.res.ColorStateList r8 = android.content.res.ColorStateList.valueOf(r8)
                        L_0x008b:
                            r5.setTextColor(r8)
                            r13.addView(r5)
                            r5 = 2131624148(0x7f0e00d4, float:1.8875468E38)
                            android.view.View r5 = r6.inflate(r5, r13, r9)
                            android.widget.LinearLayout r5 = (android.widget.LinearLayout) r5
                            java.util.List r8 = r15.getItems()
                            int r8 = r8.size()
                            r14 = r9
                        L_0x00a3:
                            if (r14 >= r8) goto L_0x02bf
                            java.util.List r13 = r15.getItems()
                            java.lang.Object r13 = r13.get(r14)
                            android.view.KeyboardShortcutInfo r13 = (android.view.KeyboardShortcutInfo) r13
                            java.util.ArrayList r9 = new java.util.ArrayList
                            r9.<init>()
                            int r16 = r13.getModifiers()
                            r17 = r0
                            if (r16 != 0) goto L_0x00c5
                            r21 = r2
                            r18 = r4
                            r19 = r8
                            r20 = r15
                            goto L_0x010b
                        L_0x00c5:
                            r18 = r4
                            r0 = 0
                        L_0x00c8:
                            int[] r4 = r1.mModifierList
                            r19 = r8
                            int r8 = r4.length
                            if (r0 >= r8) goto L_0x0104
                            r4 = r4[r0]
                            r8 = r16 & r4
                            if (r8 == 0) goto L_0x00f7
                            com.android.systemui.statusbar.KeyboardShortcuts$StringDrawableContainer r8 = new com.android.systemui.statusbar.KeyboardShortcuts$StringDrawableContainer
                            r20 = r15
                            android.util.SparseArray<java.lang.String> r15 = r1.mModifierNames
                            java.lang.Object r15 = r15.get(r4)
                            java.lang.String r15 = (java.lang.String) r15
                            r21 = r2
                            android.util.SparseArray<android.graphics.drawable.Drawable> r2 = r1.mModifierDrawables
                            java.lang.Object r2 = r2.get(r4)
                            android.graphics.drawable.Drawable r2 = (android.graphics.drawable.Drawable) r2
                            r8.<init>(r15, r2)
                            r9.add(r8)
                            int r2 = ~r4
                            r2 = r16 & r2
                            r16 = r2
                            goto L_0x00fb
                        L_0x00f7:
                            r21 = r2
                            r20 = r15
                        L_0x00fb:
                            int r0 = r0 + 1
                            r8 = r19
                            r15 = r20
                            r2 = r21
                            goto L_0x00c8
                        L_0x0104:
                            r21 = r2
                            r20 = r15
                            if (r16 == 0) goto L_0x010b
                            r9 = 0
                        L_0x010b:
                            java.lang.String r0 = "KeyboardShortcuts"
                            if (r9 != 0) goto L_0x0111
                            goto L_0x0199
                        L_0x0111:
                            char r2 = r13.getBaseCharacter()
                            if (r2 <= 0) goto L_0x0120
                            char r2 = r13.getBaseCharacter()
                            java.lang.String r2 = java.lang.String.valueOf(r2)
                            goto L_0x0186
                        L_0x0120:
                            android.util.SparseArray<android.graphics.drawable.Drawable> r2 = r1.mSpecialCharacterDrawables
                            int r4 = r13.getKeycode()
                            java.lang.Object r2 = r2.get(r4)
                            if (r2 == 0) goto L_0x0145
                            android.util.SparseArray<android.graphics.drawable.Drawable> r2 = r1.mSpecialCharacterDrawables
                            int r4 = r13.getKeycode()
                            java.lang.Object r2 = r2.get(r4)
                            android.graphics.drawable.Drawable r2 = (android.graphics.drawable.Drawable) r2
                            android.util.SparseArray<java.lang.String> r4 = r1.mSpecialCharacterNames
                            int r8 = r13.getKeycode()
                            java.lang.Object r4 = r4.get(r8)
                            java.lang.String r4 = (java.lang.String) r4
                            goto L_0x0188
                        L_0x0145:
                            android.util.SparseArray<java.lang.String> r2 = r1.mSpecialCharacterNames
                            int r4 = r13.getKeycode()
                            java.lang.Object r2 = r2.get(r4)
                            if (r2 == 0) goto L_0x015e
                            android.util.SparseArray<java.lang.String> r2 = r1.mSpecialCharacterNames
                            int r4 = r13.getKeycode()
                            java.lang.Object r2 = r2.get(r4)
                            java.lang.String r2 = (java.lang.String) r2
                            goto L_0x0186
                        L_0x015e:
                            int r2 = r13.getKeycode()
                            if (r2 != 0) goto L_0x0165
                            goto L_0x019a
                        L_0x0165:
                            android.view.KeyCharacterMap r2 = r1.mKeyCharacterMap
                            int r4 = r13.getKeycode()
                            char r2 = r2.getDisplayLabel(r4)
                            if (r2 == 0) goto L_0x0176
                            java.lang.String r2 = java.lang.String.valueOf(r2)
                            goto L_0x0186
                        L_0x0176:
                            android.view.KeyCharacterMap r2 = r1.mBackupKeyCharacterMap
                            int r4 = r13.getKeycode()
                            char r2 = r2.getDisplayLabel(r4)
                            if (r2 == 0) goto L_0x0199
                            java.lang.String r2 = java.lang.String.valueOf(r2)
                        L_0x0186:
                            r4 = r2
                            r2 = 0
                        L_0x0188:
                            if (r4 == 0) goto L_0x0193
                            com.android.systemui.statusbar.KeyboardShortcuts$StringDrawableContainer r8 = new com.android.systemui.statusbar.KeyboardShortcuts$StringDrawableContainer
                            r8.<init>(r4, r2)
                            r9.add(r8)
                            goto L_0x019a
                        L_0x0193:
                            java.lang.String r2 = "Keyboard Shortcut does not have a text representation, skipping."
                            android.util.Log.w(r0, r2)
                            goto L_0x019a
                        L_0x0199:
                            r9 = 0
                        L_0x019a:
                            if (r9 != 0) goto L_0x01ae
                            java.lang.String r2 = "Keyboard Shortcut contains unsupported keys, skipping."
                            android.util.Log.w(r0, r2)
                            r24 = r1
                            r25 = r3
                            r26 = r10
                            r23 = r12
                            r1 = 2131624150(0x7f0e00d6, float:1.8875472E38)
                            goto L_0x02a7
                        L_0x01ae:
                            r0 = 2131624145(0x7f0e00d1, float:1.8875461E38)
                            r2 = 0
                            android.view.View r0 = r6.inflate(r0, r5, r2)
                            android.graphics.drawable.Icon r4 = r13.getIcon()
                            if (r4 == 0) goto L_0x01cf
                            r4 = 2131428162(0x7f0b0342, float:1.847796E38)
                            android.view.View r4 = r0.findViewById(r4)
                            android.widget.ImageView r4 = (android.widget.ImageView) r4
                            android.graphics.drawable.Icon r8 = r13.getIcon()
                            r4.setImageIcon(r8)
                            r4.setVisibility(r2)
                        L_0x01cf:
                            r2 = 2131428164(0x7f0b0344, float:1.8477965E38)
                            android.view.View r2 = r0.findViewById(r2)
                            android.widget.TextView r2 = (android.widget.TextView) r2
                            java.lang.CharSequence r4 = r13.getLabel()
                            r2.setText(r4)
                            android.graphics.drawable.Icon r4 = r13.getIcon()
                            if (r4 == 0) goto L_0x01f3
                            android.view.ViewGroup$LayoutParams r4 = r2.getLayoutParams()
                            android.widget.RelativeLayout$LayoutParams r4 = (android.widget.RelativeLayout.LayoutParams) r4
                            r8 = 20
                            r4.removeRule(r8)
                            r2.setLayoutParams(r4)
                        L_0x01f3:
                            r2 = 2131428163(0x7f0b0343, float:1.8477963E38)
                            android.view.View r2 = r0.findViewById(r2)
                            android.view.ViewGroup r2 = (android.view.ViewGroup) r2
                            int r4 = r9.size()
                            r8 = 0
                        L_0x0201:
                            if (r8 >= r4) goto L_0x0299
                            java.lang.Object r13 = r9.get(r8)
                            com.android.systemui.statusbar.KeyboardShortcuts$StringDrawableContainer r13 = (com.android.systemui.statusbar.KeyboardShortcuts.StringDrawableContainer) r13
                            android.graphics.drawable.Drawable r15 = r13.mDrawable
                            if (r15 == 0) goto L_0x0256
                            r15 = 2131624149(0x7f0e00d5, float:1.887547E38)
                            r16 = r4
                            r4 = 0
                            android.view.View r15 = r6.inflate(r15, r2, r4)
                            android.widget.ImageView r15 = (android.widget.ImageView) r15
                            android.graphics.Bitmap$Config r4 = android.graphics.Bitmap.Config.ARGB_8888
                            android.graphics.Bitmap r4 = android.graphics.Bitmap.createBitmap(r12, r12, r4)
                            r22 = r9
                            android.graphics.Canvas r9 = new android.graphics.Canvas
                            r9.<init>(r4)
                            r23 = r12
                            android.graphics.drawable.Drawable r12 = r13.mDrawable
                            r24 = r1
                            int r1 = r9.getWidth()
                            r25 = r3
                            int r3 = r9.getHeight()
                            r26 = r10
                            r10 = 0
                            r12.setBounds(r10, r10, r1, r3)
                            android.graphics.drawable.Drawable r1 = r13.mDrawable
                            r1.draw(r9)
                            r15.setImageBitmap(r4)
                            r1 = 1
                            r15.setImportantForAccessibility(r1)
                            com.android.systemui.statusbar.KeyboardShortcuts$ShortcutKeyAccessibilityDelegate r1 = new com.android.systemui.statusbar.KeyboardShortcuts$ShortcutKeyAccessibilityDelegate
                            java.lang.String r3 = r13.mString
                            r1.<init>(r3)
                            r15.setAccessibilityDelegate(r1)
                            r2.addView(r15)
                            goto L_0x0286
                        L_0x0256:
                            r24 = r1
                            r25 = r3
                            r16 = r4
                            r22 = r9
                            r26 = r10
                            r23 = r12
                            java.lang.String r1 = r13.mString
                            if (r1 == 0) goto L_0x0286
                            r1 = 2131624150(0x7f0e00d6, float:1.8875472E38)
                            r3 = 0
                            android.view.View r4 = r6.inflate(r1, r2, r3)
                            android.widget.TextView r4 = (android.widget.TextView) r4
                            r4.setMinimumWidth(r11)
                            java.lang.String r3 = r13.mString
                            r4.setText(r3)
                            com.android.systemui.statusbar.KeyboardShortcuts$ShortcutKeyAccessibilityDelegate r3 = new com.android.systemui.statusbar.KeyboardShortcuts$ShortcutKeyAccessibilityDelegate
                            java.lang.String r9 = r13.mString
                            r3.<init>(r9)
                            r4.setAccessibilityDelegate(r3)
                            r2.addView(r4)
                            goto L_0x0289
                        L_0x0286:
                            r1 = 2131624150(0x7f0e00d6, float:1.8875472E38)
                        L_0x0289:
                            int r8 = r8 + 1
                            r4 = r16
                            r9 = r22
                            r12 = r23
                            r1 = r24
                            r3 = r25
                            r10 = r26
                            goto L_0x0201
                        L_0x0299:
                            r24 = r1
                            r25 = r3
                            r26 = r10
                            r23 = r12
                            r1 = 2131624150(0x7f0e00d6, float:1.8875472E38)
                            r5.addView(r0)
                        L_0x02a7:
                            int r14 = r14 + 1
                            r0 = r17
                            r4 = r18
                            r13 = r4
                            r8 = r19
                            r15 = r20
                            r2 = r21
                            r12 = r23
                            r1 = r24
                            r3 = r25
                            r10 = r26
                            r9 = 0
                            goto L_0x00a3
                        L_0x02bf:
                            r17 = r0
                            r24 = r1
                            r21 = r2
                            r25 = r3
                            r18 = r4
                            r26 = r10
                            r23 = r12
                            r1 = 2131624150(0x7f0e00d6, float:1.8875472E38)
                            r13.addView(r5)
                            int r0 = r7 + -1
                            r9 = r26
                            if (r9 >= r0) goto L_0x02e5
                            r0 = 2131624146(0x7f0e00d2, float:1.8875463E38)
                            r2 = 0
                            android.view.View r0 = r6.inflate(r0, r13, r2)
                            r13.addView(r0)
                            goto L_0x02e6
                        L_0x02e5:
                            r2 = 0
                        L_0x02e6:
                            int r10 = r9 + 1
                            r8 = r1
                            r9 = r2
                            r0 = r17
                            r4 = r18
                            r2 = r21
                            r12 = r23
                            r1 = r24
                            r3 = r25
                            r5 = 0
                            goto L_0x0056
                        L_0x02f9:
                            r24 = r1
                            r0 = r2
                            r0.setView(r3)
                            r1 = 2131953108(0x7f1305d4, float:1.9542678E38)
                            r2 = r24
                            com.android.systemui.statusbar.KeyboardShortcuts$1 r3 = r2.mDialogCloseListener
                            r0.setPositiveButton(r1, r3)
                            android.app.AlertDialog r0 = r0.create()
                            r2.mKeyboardShortcutsDialog = r0
                            r1 = 1
                            r0.setCanceledOnTouchOutside(r1)
                            android.app.AlertDialog r0 = r2.mKeyboardShortcutsDialog
                            android.view.Window r0 = r0.getWindow()
                            r1 = 2008(0x7d8, float:2.814E-42)
                            r0.setType(r1)
                            java.lang.Object r1 = com.android.systemui.statusbar.KeyboardShortcuts.sLock
                            monitor-enter(r1)
                            com.android.systemui.statusbar.KeyboardShortcuts r0 = com.android.systemui.statusbar.KeyboardShortcuts.sInstance     // Catch:{ all -> 0x032c }
                            if (r0 == 0) goto L_0x032a
                            android.app.AlertDialog r0 = r2.mKeyboardShortcutsDialog     // Catch:{ all -> 0x032c }
                            r0.show()     // Catch:{ all -> 0x032c }
                        L_0x032a:
                            monitor-exit(r1)     // Catch:{ all -> 0x032c }
                            return
                        L_0x032c:
                            r0 = move-exception
                            monitor-exit(r1)     // Catch:{ all -> 0x032c }
                            throw r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.KeyboardShortcuts.C11334.run():void");
                    }
                });
            }
        }, i);
    }
}
