package com.android.p012wm.shell.protolog;

import android.util.Log;
import com.android.internal.protolog.BaseProtoLogImpl;
import com.android.internal.protolog.ProtoLogViewerConfigReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import org.json.JSONException;

/* renamed from: com.android.wm.shell.protolog.ShellProtoLogImpl */
public final class ShellProtoLogImpl extends BaseProtoLogImpl {
    public static final String LOG_FILENAME = new File("wm_shell_log.pb").getAbsolutePath();
    public static ShellProtoLogImpl sServiceInstance = null;

    public final int stopTextLogging(String[] strArr, PrintWriter printWriter) {
        return setLogging(true, false, printWriter, strArr);
    }

    static {
        BaseProtoLogImpl.addLogGroupEnum(ShellProtoLogGroup.values());
    }

    public ShellProtoLogImpl() {
        super(new File(LOG_FILENAME), (String) null, 1048576, new ProtoLogViewerConfigReader());
    }

    public static synchronized ShellProtoLogImpl getSingleInstance() {
        ShellProtoLogImpl shellProtoLogImpl;
        synchronized (ShellProtoLogImpl.class) {
            if (sServiceInstance == null) {
                sServiceInstance = new ShellProtoLogImpl();
            }
            shellProtoLogImpl = sServiceInstance;
        }
        return shellProtoLogImpl;
    }

    public final int startTextLogging(String[] strArr, PrintWriter printWriter) {
        InputStream resourceAsStream;
        try {
            resourceAsStream = ShellProtoLogImpl.class.getClassLoader().getResourceAsStream("wm_shell_protolog.json");
            this.mViewerConfig.loadViewerConfig(resourceAsStream);
            int logging = setLogging(true, true, printWriter, strArr);
            if (resourceAsStream != null) {
                resourceAsStream.close();
            }
            return logging;
        } catch (IOException e) {
            Log.i("ProtoLogImpl", "Unable to load log definitions: IOException while reading wm_shell_protolog. " + e);
            return -1;
        } catch (JSONException e2) {
            Log.i("ProtoLogImpl", "Unable to load log definitions: JSON parsing exception while reading wm_shell_protolog. " + e2);
            return -1;
        } catch (Throwable th) {
            th.addSuppressed(th);
        }
        throw th;
    }

    /* renamed from: d */
    public static void m79d(ShellProtoLogGroup shellProtoLogGroup, int i, int i2, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.DEBUG, shellProtoLogGroup, i, i2, str, objArr);
    }

    public static boolean isEnabled(ShellProtoLogGroup shellProtoLogGroup) {
        if (shellProtoLogGroup.isLogToLogcat() || (shellProtoLogGroup.isLogToProto() && getSingleInstance().isProtoEnabled())) {
            return true;
        }
        return false;
    }

    /* renamed from: v */
    public static void m80v(ShellProtoLogGroup shellProtoLogGroup, int i, int i2, String str, Object... objArr) {
        getSingleInstance().log(BaseProtoLogImpl.LogLevel.VERBOSE, shellProtoLogGroup, i, i2, str, objArr);
    }
}
