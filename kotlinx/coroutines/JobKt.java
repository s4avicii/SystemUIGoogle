package kotlinx.coroutines;

import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.Job;

public final class JobKt {
    public static final String cancellationReasonDebugString(int i) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(i);
        sb.append(':');
        switch (i) {
            case -1:
                str = "REASON_NOT_CANCELED";
                break;
            case 0:
                str = "REASON_UNKNOWN";
                break;
            case 1:
                str = "REASON_CLICK";
                break;
            case 3:
                str = "REASON_CANCEL_ALL";
                break;
            case 4:
                str = "REASON_ERROR";
                break;
            case 5:
                str = "REASON_PACKAGE_CHANGED";
                break;
            case FalsingManager.VERSION:
                str = "REASON_USER_STOPPED";
                break;
            case 7:
                str = "REASON_PACKAGE_BANNED";
                break;
            case 8:
                str = "REASON_APP_CANCEL";
                break;
            case 9:
                str = "REASON_APP_CANCEL_ALL";
                break;
            case 10:
                str = "REASON_LISTENER_CANCEL";
                break;
            case QSTileImpl.C1034H.STALE:
                str = "REASON_LISTENER_CANCEL_ALL";
                break;
            case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS:
                str = "REASON_GROUP_SUMMARY_CANCELED";
                break;
            case C0961QS.VERSION:
                str = "REASON_GROUP_OPTIMIZATION";
                break;
            case 14:
                str = "REASON_PACKAGE_SUSPENDED";
                break;
            case 15:
                str = "REASON_PROFILE_TURNED_OFF";
                break;
            case 16:
                str = "REASON_UNAUTOBUNDLED";
                break;
            case 17:
                str = "REASON_CHANNEL_BANNED";
                break;
            case 18:
                str = "REASON_SNOOZED";
                break;
            case 19:
                str = "REASON_TIMEOUT";
                break;
            default:
                str = "unknown";
                break;
        }
        sb.append(str);
        return sb.toString();
    }

    public static final void ensureActive(CoroutineContext coroutineContext) {
        Job job = (Job) coroutineContext.get(Job.Key.$$INSTANCE);
        if (job != null && !job.isActive()) {
            throw job.getCancellationException();
        }
    }
}
