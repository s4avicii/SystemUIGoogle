package com.android.systemui.flags;

import com.android.p012wm.shell.C1777R;

public final class Flags {
    public static final ResourceBooleanFlag BOUNCER_USER_SWITCHER = new ResourceBooleanFlag(204, C1777R.bool.config_enableBouncerUserSwitcher);
    public static final ResourceBooleanFlag CHARGING_RIPPLE = new ResourceBooleanFlag(203, C1777R.bool.flag_charging_ripple);
    public static final BooleanFlag COMBINED_QS_HEADERS = new BooleanFlag(501, false);
    public static final BooleanFlag COMBINED_STATUS_BAR_SIGNAL_ICONS = new BooleanFlag(601, false);
    public static final int[][] ETHERNET_ICONS = {new int[]{C1777R.C1778drawable.stat_sys_ethernet}, new int[]{C1777R.C1778drawable.stat_sys_ethernet_fully}};
    public static final ResourceBooleanFlag FULL_SCREEN_USER_SWITCHER = new ResourceBooleanFlag(506, C1777R.bool.config_enableFullscreenUserSwitcher);
    public static final BooleanFlag LOCKSCREEN_ANIMATIONS = new BooleanFlag(201, true);
    public static final BooleanFlag MEDIA_MUTE_AWAIT = new BooleanFlag(904, true);
    public static final BooleanFlag MEDIA_NEARBY_DEVICES = new BooleanFlag(903, true);
    public static final BooleanFlag MEDIA_SESSION_ACTIONS = new BooleanFlag(901, true);
    public static final BooleanFlag MEDIA_SESSION_LAYOUT = new BooleanFlag(902, true);
    public static final BooleanFlag MEDIA_TAP_TO_TRANSFER = new BooleanFlag(900, true);
    public static final ResourceBooleanFlag MONET = new ResourceBooleanFlag(800, C1777R.bool.flag_monet);
    public static final BooleanFlag NEW_FOOTER = new BooleanFlag(504, true);
    public static final BooleanFlag NEW_NOTIFICATION_PIPELINE_RENDERING = new BooleanFlag(101, true);
    public static final BooleanFlag NEW_PIPELINE_CRASH_ON_CALL_TO_OLD_PIPELINE = new BooleanFlag(107, false);
    public static final BooleanFlag NEW_UNLOCK_SWIPE_ANIMATION = new BooleanFlag(202, true);
    public static final ResourceBooleanFlag NOTIFICATION_DRAG_TO_CONTENTS = new ResourceBooleanFlag(108, C1777R.bool.config_notificationToContents);
    public static final BooleanFlag NOTIFICATION_PIPELINE_DEVELOPER_LOGGING = new BooleanFlag(103, false);
    public static final BooleanFlag NSSL_DEBUG_LINES = new BooleanFlag(105, false);
    public static final BooleanFlag NSSL_DEBUG_REMOVE_ANIMATION = new BooleanFlag(106, false);
    public static final BooleanFlag ONGOING_CALL_IN_IMMERSIVE = new BooleanFlag(701, true);
    public static final BooleanFlag ONGOING_CALL_IN_IMMERSIVE_CHIP_TAP = new BooleanFlag(702, true);
    public static final BooleanFlag ONGOING_CALL_STATUS_BAR_CHIP = new BooleanFlag(700, true);
    public static final BooleanFlag POWER_MENU_LITE = new BooleanFlag(300, true);
    public static final ResourceBooleanFlag QS_USER_DETAIL_SHORTCUT = new ResourceBooleanFlag(503, C1777R.bool.flag_lockscreen_qs_user_detail_shortcut);
    public static final BooleanFlag SIMULATE_DOCK_THROUGH_CHARGING = new BooleanFlag(1000, true);
    public static final ResourceBooleanFlag SMARTSPACE = new ResourceBooleanFlag(402, C1777R.bool.flag_smartspace);
    public static final BooleanFlag SMARTSPACE_DEDUPING = new BooleanFlag(400, true);
    public static final BooleanFlag SMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED = new BooleanFlag(401, true);
    public static final ResourceBooleanFlag STATUS_BAR_USER_SWITCHER = new ResourceBooleanFlag(602, C1777R.bool.flag_user_switcher_chip);
}
