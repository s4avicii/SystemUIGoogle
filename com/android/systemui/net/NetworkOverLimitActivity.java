package com.android.systemui.net;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.INetworkPolicyManager;
import android.net.NetworkTemplate;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class NetworkOverLimitActivity extends Activity {
    public static final /* synthetic */ int $r8$clinit = 0;

    public final void onCreate(Bundle bundle) {
        int i;
        super.onCreate(bundle);
        final NetworkTemplate parcelableExtra = getIntent().getParcelableExtra("android.net.NETWORK_TEMPLATE");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (parcelableExtra.getMatchRule() != 1) {
            i = C1777R.string.data_usage_disabled_dialog_title;
        } else {
            i = C1777R.string.data_usage_disabled_dialog_mobile_title;
        }
        builder.setTitle(i);
        builder.setMessage(C1777R.string.data_usage_disabled_dialog);
        builder.setPositiveButton(17039370, (DialogInterface.OnClickListener) null);
        builder.setNegativeButton(C1777R.string.data_usage_disabled_dialog_enable, new DialogInterface.OnClickListener() {
            public final void onClick(DialogInterface dialogInterface, int i) {
                NetworkOverLimitActivity networkOverLimitActivity = NetworkOverLimitActivity.this;
                NetworkTemplate networkTemplate = parcelableExtra;
                int i2 = NetworkOverLimitActivity.$r8$clinit;
                Objects.requireNonNull(networkOverLimitActivity);
                try {
                    INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy")).snoozeLimit(networkTemplate);
                } catch (RemoteException e) {
                    Log.w("NetworkOverLimitActivity", "problem snoozing network policy", e);
                }
            }
        });
        AlertDialog create = builder.create();
        create.getWindow().setType(2003);
        create.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public final void onDismiss(DialogInterface dialogInterface) {
                NetworkOverLimitActivity.this.finish();
            }
        });
        create.show();
    }
}
