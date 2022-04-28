package com.android.systemui.volume;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;

public final class Events {
    public static final String[] DISMISS_REASONS = {"unknown", "touch_outside", "volume_controller", "timeout", "screen_off", "settings_clicked", "done_clicked", "a11y_stream_changed", "output_chooser", "usb_temperature_below_threshold"};
    public static final String[] EVENT_TAGS = {"show_dialog", "dismiss_dialog", "active_stream_changed", "expand", "key", "collection_started", "collection_stopped", "icon_click", "settings_click", "touch_level_changed", "level_changed", "internal_ringer_mode_changed", "external_ringer_mode_changed", "zen_mode_changed", "suppressor_changed", "mute_changed", "touch_level_done", "zen_mode_config_changed", "ringer_toggle", "show_usb_overheat_alarm", "dismiss_usb_overheat_alarm", "odi_captions_click", "odi_captions_tooltip_click"};
    public static final String[] SHOW_REASONS = {"unknown", "volume_changed", "remote_volume_changed", "usb_temperature_above_threshold"};
    public static final String TAG = Util.logTag(Events.class);
    @VisibleForTesting
    public static MetricsLogger sLegacyLogger = new MetricsLogger();
    @VisibleForTesting
    public static UiEventLogger sUiEventLogger = new UiEventLoggerImpl();

    @VisibleForTesting
    public enum VolumeDialogCloseEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE(134),
        VOLUME_DIALOG_DISMISS_SYSTEM(135),
        VOLUME_DIALOG_DISMISS_TIMEOUT(136),
        VOLUME_DIALOG_DISMISS_SCREEN_OFF(137),
        VOLUME_DIALOG_DISMISS_SETTINGS(138),
        VOLUME_DIALOG_DISMISS_STREAM_GONE(140),
        VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED(142);
        
        private final int mId;

        /* access modifiers changed from: public */
        VolumeDialogCloseEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    @VisibleForTesting
    public enum VolumeDialogEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        VOLUME_DIALOG_SETTINGS_CLICK(143),
        VOLUME_DIALOG_EXPAND_DETAILS(144),
        VOLUME_DIALOG_COLLAPSE_DETAILS(145),
        VOLUME_DIALOG_ACTIVE_STREAM_CHANGED(146),
        VOLUME_DIALOG_MUTE_STREAM(147),
        VOLUME_DIALOG_UNMUTE_STREAM(148),
        VOLUME_DIALOG_TO_VIBRATE_STREAM(149),
        VOLUME_DIALOG_SLIDER(150),
        VOLUME_DIALOG_SLIDER_TO_ZERO(151),
        VOLUME_KEY_TO_ZERO(152),
        VOLUME_KEY(153),
        RINGER_MODE_SILENT(154),
        RINGER_MODE_VIBRATE(155),
        RINGER_MODE_NORMAL(334),
        USB_OVERHEAT_ALARM(160),
        USB_OVERHEAT_ALARM_DISMISSED(161);
        
        private final int mId;

        /* access modifiers changed from: public */
        VolumeDialogEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    @VisibleForTesting
    public enum VolumeDialogOpenEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        VOLUME_DIALOG_SHOW_VOLUME_CHANGED(128),
        VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED(129),
        VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED(130);
        
        private final int mId;

        /* access modifiers changed from: public */
        VolumeDialogOpenEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    @VisibleForTesting
    public enum ZenModeEvent implements UiEventLogger.UiEventEnum {
        INVALID(0),
        ZEN_MODE_OFF(335),
        ZEN_MODE_IMPORTANT_ONLY(157),
        ZEN_MODE_ALARMS_ONLY(158),
        ZEN_MODE_NO_INTERRUPTIONS(159);
        
        private final int mId;

        /* access modifiers changed from: public */
        ZenModeEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0329, code lost:
        r14 = r3.toString();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0189, code lost:
        r14 = r15[0].intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x0191, code lost:
        if (r14 == 0) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x0193, code lost:
        if (r14 == 1) goto L_0x0199;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0195, code lost:
        if (r14 == 2) goto L_0x019c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0197, code lost:
        r8 = r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x0199, code lost:
        r8 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x019b, code lost:
        r8 = "silent";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x019c, code lost:
        r3.append(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x01a2, code lost:
        if (r15.length <= 1) goto L_0x0329;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01a4, code lost:
        r3.append(android.media.AudioSystem.streamToString(r15[0].intValue()));
        r3.append(' ');
        r3.append(r15[1]);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeEvent(int r14, java.lang.Object... r15) {
        /*
            java.lang.System.currentTimeMillis()
            java.lang.String r0 = TAG
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.INVALID
            java.lang.String[] r2 = EVENT_TAGS
            int r3 = r2.length
            if (r14 < r3) goto L_0x0010
            java.lang.String r14 = ""
            goto L_0x032d
        L_0x0010:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "writeEvent "
            r3.<init>(r4)
            r2 = r2[r14]
            r3.append(r2)
            int r2 = r15.length
            if (r2 != 0) goto L_0x0038
            r15 = 8
            if (r14 != r15) goto L_0x0032
            com.android.internal.logging.MetricsLogger r14 = sLegacyLogger
            r15 = 1386(0x56a, float:1.942E-42)
            r14.action(r15)
            com.android.internal.logging.UiEventLogger r14 = sUiEventLogger
            com.android.systemui.volume.Events$VolumeDialogEvent r15 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_SETTINGS_CLICK
            r14.log(r15)
        L_0x0032:
            java.lang.String r14 = r3.toString()
            goto L_0x032d
        L_0x0038:
            java.lang.String r2 = " "
            r3.append(r2)
            r2 = 1457(0x5b1, float:2.042E-42)
            java.lang.String r4 = " keyguard="
            r5 = 32
            r6 = 3
            r7 = 0
            java.lang.String r8 = "normal"
            java.lang.String r9 = "silent"
            java.lang.String r10 = "unknown"
            java.lang.String r11 = "vibrate"
            r12 = 1
            r13 = 2
            switch(r14) {
                case 0: goto L_0x02e0;
                case 1: goto L_0x0296;
                case 2: goto L_0x0273;
                case 3: goto L_0x024f;
                case 4: goto L_0x0216;
                case 5: goto L_0x0055;
                case 6: goto L_0x0055;
                case 7: goto L_0x01bd;
                case 8: goto L_0x0055;
                case 9: goto L_0x01a1;
                case 10: goto L_0x01a1;
                case 11: goto L_0x0189;
                case 12: goto L_0x017a;
                case 13: goto L_0x013a;
                case 14: goto L_0x0128;
                case 15: goto L_0x01a1;
                case 16: goto L_0x0104;
                case 17: goto L_0x0055;
                case 18: goto L_0x00c9;
                case 19: goto L_0x0093;
                case 20: goto L_0x005e;
                default: goto L_0x0055;
            }
        L_0x0055:
            java.util.List r14 = java.util.Arrays.asList(r15)
            r3.append(r14)
            goto L_0x0329
        L_0x005e:
            com.android.internal.logging.MetricsLogger r14 = sLegacyLogger
            r14.hidden(r2)
            com.android.internal.logging.UiEventLogger r14 = sUiEventLogger
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.USB_OVERHEAT_ALARM_DISMISSED
            r14.log(r1)
            int r14 = r15.length
            if (r14 <= r12) goto L_0x0329
            r14 = r15[r12]
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            com.android.internal.logging.MetricsLogger r1 = sLegacyLogger
            boolean r2 = r14.booleanValue()
            java.lang.String r5 = "dismiss_usb_overheat_alarm"
            r1.histogram(r5, r2)
            r15 = r15[r7]
            java.lang.Integer r15 = (java.lang.Integer) r15
            java.lang.String[] r1 = DISMISS_REASONS
            int r15 = r15.intValue()
            r15 = r1[r15]
            r3.append(r15)
            r3.append(r4)
            r3.append(r14)
            goto L_0x0329
        L_0x0093:
            com.android.internal.logging.MetricsLogger r14 = sLegacyLogger
            r14.visible(r2)
            com.android.internal.logging.UiEventLogger r14 = sUiEventLogger
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.USB_OVERHEAT_ALARM
            r14.log(r1)
            int r14 = r15.length
            if (r14 <= r12) goto L_0x0329
            r14 = r15[r12]
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            com.android.internal.logging.MetricsLogger r1 = sLegacyLogger
            boolean r2 = r14.booleanValue()
            java.lang.String r5 = "show_usb_overheat_alarm"
            r1.histogram(r5, r2)
            r15 = r15[r7]
            java.lang.Integer r15 = (java.lang.Integer) r15
            java.lang.String[] r1 = SHOW_REASONS
            int r15 = r15.intValue()
            r15 = r1[r15]
            r3.append(r15)
            r3.append(r4)
            r3.append(r14)
            goto L_0x0329
        L_0x00c9:
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            com.android.internal.logging.MetricsLogger r15 = sLegacyLogger
            r2 = 1385(0x569, float:1.941E-42)
            int r4 = r14.intValue()
            r15.action(r2, r4)
            com.android.internal.logging.UiEventLogger r15 = sUiEventLogger
            int r2 = r14.intValue()
            if (r2 == 0) goto L_0x00eb
            if (r2 == r12) goto L_0x00e8
            if (r2 == r13) goto L_0x00e5
            goto L_0x00ed
        L_0x00e5:
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.RINGER_MODE_NORMAL
            goto L_0x00ed
        L_0x00e8:
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.RINGER_MODE_VIBRATE
            goto L_0x00ed
        L_0x00eb:
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.RINGER_MODE_SILENT
        L_0x00ed:
            r15.log(r1)
            int r14 = r14.intValue()
            if (r14 == 0) goto L_0x00fe
            if (r14 == r12) goto L_0x00fc
            if (r14 == r13) goto L_0x00ff
            r8 = r10
            goto L_0x00ff
        L_0x00fc:
            r8 = r11
            goto L_0x00ff
        L_0x00fe:
            r8 = r9
        L_0x00ff:
            r3.append(r8)
            goto L_0x0329
        L_0x0104:
            int r14 = r15.length
            if (r14 <= r12) goto L_0x01a1
            r14 = r15[r12]
            java.lang.Integer r14 = (java.lang.Integer) r14
            com.android.internal.logging.MetricsLogger r1 = sLegacyLogger
            r2 = 209(0xd1, float:2.93E-43)
            int r4 = r14.intValue()
            r1.action(r2, r4)
            com.android.internal.logging.UiEventLogger r1 = sUiEventLogger
            int r14 = r14.intValue()
            if (r14 != 0) goto L_0x0121
            com.android.systemui.volume.Events$VolumeDialogEvent r14 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_SLIDER_TO_ZERO
            goto L_0x0123
        L_0x0121:
            com.android.systemui.volume.Events$VolumeDialogEvent r14 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_SLIDER
        L_0x0123:
            r1.log(r14)
            goto L_0x01a1
        L_0x0128:
            int r14 = r15.length
            if (r14 <= r12) goto L_0x0329
            r14 = r15[r7]
            r3.append(r14)
            r3.append(r5)
            r14 = r15[r12]
            r3.append(r14)
            goto L_0x0329
        L_0x013a:
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r15 = r14.intValue()
            if (r15 == 0) goto L_0x0154
            if (r15 == r12) goto L_0x0151
            if (r15 == r13) goto L_0x014e
            if (r15 == r6) goto L_0x014b
            goto L_0x0156
        L_0x014b:
            java.lang.String r10 = "alarms"
            goto L_0x0156
        L_0x014e:
            java.lang.String r10 = "no_interruptions"
            goto L_0x0156
        L_0x0151:
            java.lang.String r10 = "important_interruptions"
            goto L_0x0156
        L_0x0154:
            java.lang.String r10 = "off"
        L_0x0156:
            r3.append(r10)
            com.android.internal.logging.UiEventLogger r15 = sUiEventLogger
            int r14 = r14.intValue()
            if (r14 == 0) goto L_0x0173
            if (r14 == r12) goto L_0x0170
            if (r14 == r13) goto L_0x016d
            if (r14 == r6) goto L_0x016a
            com.android.systemui.volume.Events$ZenModeEvent r14 = com.android.systemui.volume.Events.ZenModeEvent.INVALID
            goto L_0x0175
        L_0x016a:
            com.android.systemui.volume.Events$ZenModeEvent r14 = com.android.systemui.volume.Events.ZenModeEvent.ZEN_MODE_ALARMS_ONLY
            goto L_0x0175
        L_0x016d:
            com.android.systemui.volume.Events$ZenModeEvent r14 = com.android.systemui.volume.Events.ZenModeEvent.ZEN_MODE_NO_INTERRUPTIONS
            goto L_0x0175
        L_0x0170:
            com.android.systemui.volume.Events$ZenModeEvent r14 = com.android.systemui.volume.Events.ZenModeEvent.ZEN_MODE_IMPORTANT_ONLY
            goto L_0x0175
        L_0x0173:
            com.android.systemui.volume.Events$ZenModeEvent r14 = com.android.systemui.volume.Events.ZenModeEvent.ZEN_MODE_OFF
        L_0x0175:
            r15.log(r14)
            goto L_0x0329
        L_0x017a:
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            com.android.internal.logging.MetricsLogger r1 = sLegacyLogger
            r2 = 213(0xd5, float:2.98E-43)
            int r14 = r14.intValue()
            r1.action(r2, r14)
        L_0x0189:
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r14 = r14.intValue()
            if (r14 == 0) goto L_0x019b
            if (r14 == r12) goto L_0x0199
            if (r14 == r13) goto L_0x019c
            r8 = r10
            goto L_0x019c
        L_0x0199:
            r8 = r11
            goto L_0x019c
        L_0x019b:
            r8 = r9
        L_0x019c:
            r3.append(r8)
            goto L_0x0329
        L_0x01a1:
            int r14 = r15.length
            if (r14 <= r12) goto L_0x0329
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            int r14 = r14.intValue()
            java.lang.String r14 = android.media.AudioSystem.streamToString(r14)
            r3.append(r14)
            r3.append(r5)
            r14 = r15[r12]
            r3.append(r14)
            goto L_0x0329
        L_0x01bd:
            int r14 = r15.length
            if (r14 <= r12) goto L_0x0329
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            com.android.internal.logging.MetricsLogger r2 = sLegacyLogger
            r4 = 212(0xd4, float:2.97E-43)
            int r7 = r14.intValue()
            r2.action(r4, r7)
            r15 = r15[r12]
            java.lang.Integer r15 = (java.lang.Integer) r15
            com.android.internal.logging.UiEventLogger r2 = sUiEventLogger
            int r4 = r15.intValue()
            if (r4 == r12) goto L_0x01e6
            if (r4 == r13) goto L_0x01e3
            if (r4 == r6) goto L_0x01e0
            goto L_0x01e8
        L_0x01e0:
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_TO_VIBRATE_STREAM
            goto L_0x01e8
        L_0x01e3:
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_MUTE_STREAM
            goto L_0x01e8
        L_0x01e6:
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_UNMUTE_STREAM
        L_0x01e8:
            r2.log(r1)
            int r14 = r14.intValue()
            java.lang.String r14 = android.media.AudioSystem.streamToString(r14)
            r3.append(r14)
            r3.append(r5)
            int r14 = r15.intValue()
            if (r14 == r12) goto L_0x020e
            if (r14 == r13) goto L_0x020b
            if (r14 == r6) goto L_0x0211
            java.lang.String r15 = "unknown_state_"
            java.lang.String r11 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0.m0m(r15, r14)
            goto L_0x0211
        L_0x020b:
            java.lang.String r11 = "mute"
            goto L_0x0211
        L_0x020e:
            java.lang.String r11 = "unmute"
        L_0x0211:
            r3.append(r11)
            goto L_0x0329
        L_0x0216:
            int r14 = r15.length
            if (r14 <= r12) goto L_0x0329
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            com.android.internal.logging.MetricsLogger r1 = sLegacyLogger
            r2 = 211(0xd3, float:2.96E-43)
            int r4 = r14.intValue()
            r1.action(r2, r4)
            r15 = r15[r12]
            java.lang.Integer r15 = (java.lang.Integer) r15
            com.android.internal.logging.UiEventLogger r1 = sUiEventLogger
            int r2 = r15.intValue()
            if (r2 != 0) goto L_0x0237
            com.android.systemui.volume.Events$VolumeDialogEvent r2 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_KEY_TO_ZERO
            goto L_0x0239
        L_0x0237:
            com.android.systemui.volume.Events$VolumeDialogEvent r2 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_KEY
        L_0x0239:
            r1.log(r2)
            int r14 = r14.intValue()
            java.lang.String r14 = android.media.AudioSystem.streamToString(r14)
            r3.append(r14)
            r3.append(r5)
            r3.append(r15)
            goto L_0x0329
        L_0x024f:
            r14 = r15[r7]
            java.lang.Boolean r14 = (java.lang.Boolean) r14
            com.android.internal.logging.MetricsLogger r15 = sLegacyLogger
            r1 = 208(0xd0, float:2.91E-43)
            boolean r2 = r14.booleanValue()
            r15.visibility(r1, r2)
            com.android.internal.logging.UiEventLogger r15 = sUiEventLogger
            boolean r1 = r14.booleanValue()
            if (r1 == 0) goto L_0x0269
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_EXPAND_DETAILS
            goto L_0x026b
        L_0x0269:
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_COLLAPSE_DETAILS
        L_0x026b:
            r15.log(r1)
            r3.append(r14)
            goto L_0x0329
        L_0x0273:
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            com.android.internal.logging.MetricsLogger r15 = sLegacyLogger
            r1 = 210(0xd2, float:2.94E-43)
            int r2 = r14.intValue()
            r15.action(r1, r2)
            com.android.internal.logging.UiEventLogger r15 = sUiEventLogger
            com.android.systemui.volume.Events$VolumeDialogEvent r1 = com.android.systemui.volume.Events.VolumeDialogEvent.VOLUME_DIALOG_ACTIVE_STREAM_CHANGED
            r15.log(r1)
            int r14 = r14.intValue()
            java.lang.String r14 = android.media.AudioSystem.streamToString(r14)
            r3.append(r14)
            goto L_0x0329
        L_0x0296:
            com.android.internal.logging.MetricsLogger r14 = sLegacyLogger
            r1 = 207(0xcf, float:2.9E-43)
            r14.hidden(r1)
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            com.android.internal.logging.UiEventLogger r15 = sUiEventLogger
            int r1 = r14.intValue()
            if (r1 == r12) goto L_0x02cf
            if (r1 == r13) goto L_0x02cc
            if (r1 == r6) goto L_0x02c9
            r2 = 4
            if (r1 == r2) goto L_0x02c6
            r2 = 5
            if (r1 == r2) goto L_0x02c3
            r2 = 7
            if (r1 == r2) goto L_0x02c0
            r2 = 9
            if (r1 == r2) goto L_0x02bd
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.INVALID
            goto L_0x02d1
        L_0x02bd:
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_USB_TEMP_ALARM_CHANGED
            goto L_0x02d1
        L_0x02c0:
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_STREAM_GONE
            goto L_0x02d1
        L_0x02c3:
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_SETTINGS
            goto L_0x02d1
        L_0x02c6:
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_SCREEN_OFF
            goto L_0x02d1
        L_0x02c9:
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_TIMEOUT
            goto L_0x02d1
        L_0x02cc:
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_SYSTEM
            goto L_0x02d1
        L_0x02cf:
            com.android.systemui.volume.Events$VolumeDialogCloseEvent r1 = com.android.systemui.volume.Events.VolumeDialogCloseEvent.VOLUME_DIALOG_DISMISS_TOUCH_OUTSIDE
        L_0x02d1:
            r15.log(r1)
            java.lang.String[] r15 = DISMISS_REASONS
            int r14 = r14.intValue()
            r14 = r15[r14]
            r3.append(r14)
            goto L_0x0329
        L_0x02e0:
            com.android.internal.logging.MetricsLogger r14 = sLegacyLogger
            r1 = 207(0xcf, float:2.9E-43)
            r14.visible(r1)
            int r14 = r15.length
            if (r14 <= r12) goto L_0x0329
            r14 = r15[r7]
            java.lang.Integer r14 = (java.lang.Integer) r14
            r15 = r15[r12]
            java.lang.Boolean r15 = (java.lang.Boolean) r15
            com.android.internal.logging.MetricsLogger r1 = sLegacyLogger
            boolean r2 = r15.booleanValue()
            java.lang.String r5 = "volume_from_keyguard"
            r1.histogram(r5, r2)
            com.android.internal.logging.UiEventLogger r1 = sUiEventLogger
            int r2 = r14.intValue()
            if (r2 == r12) goto L_0x0313
            if (r2 == r13) goto L_0x0310
            if (r2 == r6) goto L_0x030d
            com.android.systemui.volume.Events$VolumeDialogOpenEvent r2 = com.android.systemui.volume.Events.VolumeDialogOpenEvent.INVALID
            goto L_0x0315
        L_0x030d:
            com.android.systemui.volume.Events$VolumeDialogOpenEvent r2 = com.android.systemui.volume.Events.VolumeDialogOpenEvent.VOLUME_DIALOG_SHOW_USB_TEMP_ALARM_CHANGED
            goto L_0x0315
        L_0x0310:
            com.android.systemui.volume.Events$VolumeDialogOpenEvent r2 = com.android.systemui.volume.Events.VolumeDialogOpenEvent.VOLUME_DIALOG_SHOW_REMOTE_VOLUME_CHANGED
            goto L_0x0315
        L_0x0313:
            com.android.systemui.volume.Events$VolumeDialogOpenEvent r2 = com.android.systemui.volume.Events.VolumeDialogOpenEvent.VOLUME_DIALOG_SHOW_VOLUME_CHANGED
        L_0x0315:
            r1.log(r2)
            java.lang.String[] r1 = SHOW_REASONS
            int r14 = r14.intValue()
            r14 = r1[r14]
            r3.append(r14)
            r3.append(r4)
            r3.append(r15)
        L_0x0329:
            java.lang.String r14 = r3.toString()
        L_0x032d:
            android.util.Log.i(r0, r14)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.Events.writeEvent(int, java.lang.Object[]):void");
    }
}
