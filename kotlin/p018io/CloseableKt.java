package kotlin.p018io;

import android.app.Notification;
import android.service.notification.StatusBarNotification;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import androidx.appcompat.widget.WithHint;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.io.Closeable;
import java.util.Objects;
import kotlin.ExceptionsKt;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: kotlin.io.CloseableKt */
/* compiled from: Closeable.kt */
public final class CloseableKt {
    public static final int[] METRICS_GESTURE_TYPE_MAP = {0, 186, 187, 188, 189, 190, 191, 192, 193, 194, 195};

    public static final void closeFinally(Closeable closeable, Throwable th) {
        if (closeable != null) {
            if (th == null) {
                closeable.close();
                return;
            }
            try {
                closeable.close();
            } catch (Throwable th2) {
                ExceptionsKt.addSuppressed(th, th2);
            }
        }
    }

    public static InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo editorInfo, View view) {
        if (inputConnection != null && editorInfo.hintText == null) {
            ViewParent parent = view.getParent();
            while (true) {
                if (!(parent instanceof View)) {
                    break;
                } else if (parent instanceof WithHint) {
                    editorInfo.hintText = ((WithHint) parent).getHint();
                    break;
                } else {
                    parent = parent.getParent();
                }
            }
        }
        return inputConnection;
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0085 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final long systemProp(java.lang.String r24, long r25, long r27, long r29) {
        /*
            r0 = r24
            r1 = r27
            r3 = r29
            int r5 = kotlinx.coroutines.internal.SystemPropsKt__SystemPropsKt.AVAILABLE_PROCESSORS
            java.lang.String r6 = java.lang.System.getProperty(r24)     // Catch:{ SecurityException -> 0x000d }
            goto L_0x000e
        L_0x000d:
            r6 = 0
        L_0x000e:
            if (r6 != 0) goto L_0x0014
            r8 = r25
            goto L_0x00aa
        L_0x0014:
            r7 = 10
            int r8 = r6.length()
            r9 = 0
            r10 = 1
            if (r8 != 0) goto L_0x0022
        L_0x001e:
            r21 = r6
            goto L_0x007d
        L_0x0022:
            char r11 = r6.charAt(r9)
            r12 = 48
            int r12 = kotlin.jvm.internal.Intrinsics.compare(r11, r12)
            r13 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r12 >= 0) goto L_0x0045
            if (r8 != r10) goto L_0x0036
            goto L_0x001e
        L_0x0036:
            r12 = 45
            if (r11 != r12) goto L_0x003e
            r13 = -9223372036854775808
            r11 = r10
            goto L_0x0046
        L_0x003e:
            r12 = 43
            if (r11 != r12) goto L_0x001e
            r12 = r9
            r11 = r10
            goto L_0x0047
        L_0x0045:
            r11 = r9
        L_0x0046:
            r12 = r11
        L_0x0047:
            r15 = 0
            r17 = -256204778801521550(0xfc71c71c71c71c72, double:-2.772000429909333E291)
            r9 = r15
            r15 = r17
        L_0x0051:
            if (r11 >= r8) goto L_0x0085
            int r19 = r11 + 1
            char r11 = r6.charAt(r11)
            int r11 = java.lang.Character.digit(r11, r7)
            if (r11 >= 0) goto L_0x0060
            goto L_0x001e
        L_0x0060:
            int r20 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r20 >= 0) goto L_0x0072
            int r15 = (r15 > r17 ? 1 : (r15 == r17 ? 0 : -1))
            if (r15 != 0) goto L_0x001e
            r21 = r6
            long r5 = (long) r7
            long r15 = r13 / r5
            int r5 = (r9 > r15 ? 1 : (r9 == r15 ? 0 : -1))
            if (r5 >= 0) goto L_0x0074
            goto L_0x007d
        L_0x0072:
            r21 = r6
        L_0x0074:
            long r5 = (long) r7
            long r9 = r9 * r5
            long r5 = (long) r11
            long r22 = r13 + r5
            int r11 = (r9 > r22 ? 1 : (r9 == r22 ? 0 : -1))
            if (r11 >= 0) goto L_0x007f
        L_0x007d:
            r5 = 0
            goto L_0x0093
        L_0x007f:
            long r9 = r9 - r5
            r11 = r19
            r6 = r21
            goto L_0x0051
        L_0x0085:
            r21 = r6
            if (r12 == 0) goto L_0x008e
            java.lang.Long r5 = java.lang.Long.valueOf(r9)
            goto L_0x0093
        L_0x008e:
            long r5 = -r9
            java.lang.Long r5 = java.lang.Long.valueOf(r5)
        L_0x0093:
            r6 = 39
            java.lang.String r7 = "System property '"
            if (r5 == 0) goto L_0x00df
            long r8 = r5.longValue()
            int r5 = (r1 > r8 ? 1 : (r1 == r8 ? 0 : -1))
            if (r5 > 0) goto L_0x00a7
            int r5 = (r8 > r3 ? 1 : (r8 == r3 ? 0 : -1))
            if (r5 > 0) goto L_0x00a7
            r5 = 1
            goto L_0x00a8
        L_0x00a7:
            r5 = 0
        L_0x00a8:
            if (r5 == 0) goto L_0x00ab
        L_0x00aa:
            return r8
        L_0x00ab:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r7)
            r10.append(r0)
            java.lang.String r0 = "' should be in range "
            r10.append(r0)
            r10.append(r1)
            java.lang.String r0 = ".."
            r10.append(r0)
            r10.append(r3)
            java.lang.String r0 = ", but is '"
            r10.append(r0)
            r10.append(r8)
            r10.append(r6)
            java.lang.String r0 = r10.toString()
            java.lang.String r0 = r0.toString()
            r5.<init>(r0)
            throw r5
        L_0x00df:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r7)
            r2.append(r0)
            java.lang.String r0 = "' has unrecognized value '"
            r2.append(r0)
            r5 = r21
            r2.append(r5)
            r2.append(r6)
            java.lang.String r0 = r2.toString()
            java.lang.String r0 = r0.toString()
            r1.<init>(r0)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.p018io.CloseableKt.systemProp(java.lang.String, long, long, long):long");
    }

    public static int systemProp$default(String str, int i, int i2, int i3, int i4) {
        if ((i4 & 4) != 0) {
            i2 = 1;
        }
        if ((i4 & 8) != 0) {
            i3 = Integer.MAX_VALUE;
        }
        return (int) systemProp(str, (long) i, (long) i2, (long) i3);
    }

    public static final boolean access$isColorizedForegroundService(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        Notification notification = notificationEntry.mSbn.getNotification();
        if (!notification.isForegroundService() || !notification.isColorized() || notificationEntry.getImportance() <= 1) {
            return false;
        }
        return true;
    }

    public static final boolean access$isImportantCall(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        if (!notificationEntry.mSbn.getNotification().isStyle(Notification.CallStyle.class) || notificationEntry.getImportance() <= 1) {
            return false;
        }
        return true;
    }

    public static final boolean access$isSystemMax(NotificationEntry notificationEntry) {
        boolean z;
        if (notificationEntry.getImportance() >= 4) {
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            if (Intrinsics.areEqual(ThemeOverlayApplier.ANDROID_PACKAGE, statusBarNotification.getPackageName()) || Intrinsics.areEqual(ThemeOverlayApplier.SYSUI_PACKAGE, statusBarNotification.getPackageName())) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }
}
