package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline0;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    public int mBackStackId = -1;
    public boolean mCancelable = true;
    public boolean mCreatingDialog;
    public Dialog mDialog;
    public boolean mDialogCreated = false;
    public C01681 mDismissRunnable = new Runnable() {
        @SuppressLint({"SyntheticAccessor"})
        public final void run() {
            DialogFragment dialogFragment = DialogFragment.this;
            dialogFragment.mOnDismissListener.onDismiss(dialogFragment.mDialog);
        }
    };
    public boolean mDismissed;
    public Handler mHandler;
    public C01714 mObserver = new Observer<LifecycleOwner>() {
        @SuppressLint({"SyntheticAccessor"})
        public final void onChanged(Object obj) {
            if (((LifecycleOwner) obj) != null) {
                DialogFragment dialogFragment = DialogFragment.this;
                if (dialogFragment.mShowsDialog) {
                    View requireView = dialogFragment.requireView();
                    if (requireView.getParent() != null) {
                        throw new IllegalStateException("DialogFragment can not be attached to a container view");
                    } else if (DialogFragment.this.mDialog != null) {
                        if (FragmentManager.isLoggingEnabled(3)) {
                            Log.d("FragmentManager", "DialogFragment " + this + " setting the content view on " + DialogFragment.this.mDialog);
                        }
                        DialogFragment.this.mDialog.setContentView(requireView);
                    }
                }
            }
        }
    };
    public C01692 mOnCancelListener = new DialogInterface.OnCancelListener() {
        @SuppressLint({"SyntheticAccessor"})
        public final void onCancel(DialogInterface dialogInterface) {
            DialogFragment dialogFragment = DialogFragment.this;
            Dialog dialog = dialogFragment.mDialog;
            if (dialog != null) {
                dialogFragment.onCancel(dialog);
            }
        }
    };
    public C01703 mOnDismissListener = new DialogInterface.OnDismissListener() {
        @SuppressLint({"SyntheticAccessor"})
        public final void onDismiss(DialogInterface dialogInterface) {
            DialogFragment dialogFragment = DialogFragment.this;
            Dialog dialog = dialogFragment.mDialog;
            if (dialog != null) {
                dialogFragment.onDismiss(dialog);
            }
        }
    };
    public boolean mShowsDialog = true;
    public int mStyle = 0;
    public int mTheme = 0;
    public boolean mViewDestroyed;

    public void onCancel(DialogInterface dialogInterface) {
    }

    public Dialog onCreateDialog() {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "onCreateDialog called for DialogFragment " + this);
        }
        return new Dialog(requireContext(), this.mTheme);
    }

    public final void onDestroyView() {
        this.mCalled = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = true;
            dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
            this.mDialog.dismiss();
            if (!this.mDismissed) {
                onDismiss(this.mDialog);
            }
            this.mDialog = null;
            this.mDialogCreated = false;
        }
    }

    public final void onDetach() {
        this.mCalled = true;
        if (!this.mDismissed) {
            this.mDismissed = true;
        }
        this.mViewLifecycleOwnerLiveData.removeObserver(this.mObserver);
    }

    public void onStart() {
        this.mCalled = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = false;
            dialog.show();
            View decorView = this.mDialog.getWindow().getDecorView();
            decorView.setTag(C1777R.C1779id.view_tree_lifecycle_owner, this);
            decorView.setTag(C1777R.C1779id.view_tree_view_model_store_owner, this);
            decorView.setTag(C1777R.C1779id.view_tree_saved_state_registry_owner, this);
        }
    }

    public void onStop() {
        this.mCalled = true;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.hide();
        }
    }

    public final void onViewStateRestored(Bundle bundle) {
        Bundle bundle2;
        this.mCalled = true;
        if (this.mDialog != null && bundle != null && (bundle2 = bundle.getBundle("android:savedDialogState")) != null) {
            this.mDialog.onRestoreInstanceState(bundle2);
        }
    }

    public final FragmentContainer createFragmentContainer() {
        final Fragment.C01734 r0 = new FragmentContainer() {
            public final View onFindViewById(int i) {
                View view = Fragment.this.mView;
                if (view != null) {
                    return view.findViewById(i);
                }
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Fragment ");
                m.append(Fragment.this);
                m.append(" does not have a view");
                throw new IllegalStateException(m.toString());
            }

            public final boolean onHasView() {
                if (Fragment.this.mView != null) {
                    return true;
                }
                return false;
            }
        };
        return new FragmentContainer() {
            public final View onFindViewById(int i) {
                if (r0.onHasView()) {
                    return r0.onFindViewById(i);
                }
                DialogFragment dialogFragment = DialogFragment.this;
                Objects.requireNonNull(dialogFragment);
                Dialog dialog = dialogFragment.mDialog;
                if (dialog != null) {
                    return dialog.findViewById(i);
                }
                return null;
            }

            public final boolean onHasView() {
                if (!r0.onHasView()) {
                    DialogFragment dialogFragment = DialogFragment.this;
                    Objects.requireNonNull(dialogFragment);
                    if (dialogFragment.mDialogCreated) {
                        return true;
                    }
                    return false;
                }
                return true;
            }
        };
    }

    public final void dismissInternal(boolean z, boolean z2) {
        if (!this.mDismissed) {
            this.mDismissed = true;
            Dialog dialog = this.mDialog;
            if (dialog != null) {
                dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
                this.mDialog.dismiss();
                if (!z2) {
                    if (Looper.myLooper() == this.mHandler.getLooper()) {
                        onDismiss(this.mDialog);
                    } else {
                        this.mHandler.post(this.mDismissRunnable);
                    }
                }
            }
            this.mViewDestroyed = true;
            if (this.mBackStackId >= 0) {
                FragmentManager parentFragmentManager = getParentFragmentManager();
                int i = this.mBackStackId;
                if (i >= 0) {
                    parentFragmentManager.enqueueAction(new FragmentManager.PopBackStackState(i), z);
                    this.mBackStackId = -1;
                    return;
                }
                throw new IllegalArgumentException(VendorAtomValue$$ExternalSyntheticOutline0.m0m("Bad id: ", i));
            }
            BackStackRecord backStackRecord = new BackStackRecord(getParentFragmentManager());
            FragmentManager fragmentManager = this.mFragmentManager;
            if (fragmentManager == null || fragmentManager == backStackRecord.mManager) {
                backStackRecord.addOp(new FragmentTransaction.C0192Op(3, this));
                if (z) {
                    backStackRecord.commitInternal(true);
                } else {
                    backStackRecord.commitInternal(false);
                }
            } else {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Cannot remove Fragment attached to a different FragmentManager. Fragment ");
                m.append(toString());
                m.append(" is already attached to a FragmentManager.");
                throw new IllegalStateException(m.toString());
            }
        }
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (!this.mViewDestroyed) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "onDismiss called for DialogFragment " + this);
            }
            dismissInternal(true, true);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            Bundle onSaveInstanceState = dialog.onSaveInstanceState();
            onSaveInstanceState.putBoolean("android:dialogShowing", false);
            bundle.putBundle("android:savedDialogState", onSaveInstanceState);
        }
        int i = this.mStyle;
        if (i != 0) {
            bundle.putInt("android:style", i);
        }
        int i2 = this.mTheme;
        if (i2 != 0) {
            bundle.putInt("android:theme", i2);
        }
        boolean z = this.mCancelable;
        if (!z) {
            bundle.putBoolean("android:cancelable", z);
        }
        boolean z2 = this.mShowsDialog;
        if (!z2) {
            bundle.putBoolean("android:showsDialog", z2);
        }
        int i3 = this.mBackStackId;
        if (i3 != -1) {
            bundle.putInt("android:backStackId", i3);
        }
    }

    public final Dialog requireDialog() {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            return dialog;
        }
        throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
    }

    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mViewLifecycleOwnerLiveData.observeForever(this.mObserver);
        this.mDismissed = false;
    }

    public void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        this.mHandler = new Handler();
        if (this.mContainerId == 0) {
            z = true;
        } else {
            z = false;
        }
        this.mShowsDialog = z;
        if (bundle != null) {
            this.mStyle = bundle.getInt("android:style", 0);
            this.mTheme = bundle.getInt("android:theme", 0);
            this.mCancelable = bundle.getBoolean("android:cancelable", true);
            this.mShowsDialog = bundle.getBoolean("android:showsDialog", this.mShowsDialog);
            this.mBackStackId = bundle.getInt("android:backStackId", -1);
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0046 A[Catch:{ all -> 0x006b }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.view.LayoutInflater onGetLayoutInflater(android.os.Bundle r8) {
        /*
            r7 = this;
            android.view.LayoutInflater r8 = super.onGetLayoutInflater(r8)
            boolean r0 = r7.mShowsDialog
            java.lang.String r1 = "FragmentManager"
            r2 = 2
            if (r0 == 0) goto L_0x009b
            boolean r3 = r7.mCreatingDialog
            if (r3 == 0) goto L_0x0011
            goto L_0x009b
        L_0x0011:
            if (r0 != 0) goto L_0x0014
            goto L_0x006f
        L_0x0014:
            boolean r0 = r7.mDialogCreated
            if (r0 != 0) goto L_0x006f
            r0 = 0
            r3 = 1
            r7.mCreatingDialog = r3     // Catch:{ all -> 0x006b }
            android.app.Dialog r4 = r7.onCreateDialog()     // Catch:{ all -> 0x006b }
            r7.mDialog = r4     // Catch:{ all -> 0x006b }
            boolean r5 = r7.mShowsDialog     // Catch:{ all -> 0x006b }
            if (r5 == 0) goto L_0x0065
            int r5 = r7.mStyle     // Catch:{ all -> 0x006b }
            if (r5 == r3) goto L_0x003b
            if (r5 == r2) goto L_0x003b
            r6 = 3
            if (r5 == r6) goto L_0x0030
            goto L_0x003e
        L_0x0030:
            android.view.Window r5 = r4.getWindow()     // Catch:{ all -> 0x006b }
            if (r5 == 0) goto L_0x003b
            r6 = 24
            r5.addFlags(r6)     // Catch:{ all -> 0x006b }
        L_0x003b:
            r4.requestWindowFeature(r3)     // Catch:{ all -> 0x006b }
        L_0x003e:
            android.content.Context r4 = r7.getContext()     // Catch:{ all -> 0x006b }
            boolean r5 = r4 instanceof android.app.Activity     // Catch:{ all -> 0x006b }
            if (r5 == 0) goto L_0x004d
            android.app.Dialog r5 = r7.mDialog     // Catch:{ all -> 0x006b }
            android.app.Activity r4 = (android.app.Activity) r4     // Catch:{ all -> 0x006b }
            r5.setOwnerActivity(r4)     // Catch:{ all -> 0x006b }
        L_0x004d:
            android.app.Dialog r4 = r7.mDialog     // Catch:{ all -> 0x006b }
            boolean r5 = r7.mCancelable     // Catch:{ all -> 0x006b }
            r4.setCancelable(r5)     // Catch:{ all -> 0x006b }
            android.app.Dialog r4 = r7.mDialog     // Catch:{ all -> 0x006b }
            androidx.fragment.app.DialogFragment$2 r5 = r7.mOnCancelListener     // Catch:{ all -> 0x006b }
            r4.setOnCancelListener(r5)     // Catch:{ all -> 0x006b }
            android.app.Dialog r4 = r7.mDialog     // Catch:{ all -> 0x006b }
            androidx.fragment.app.DialogFragment$3 r5 = r7.mOnDismissListener     // Catch:{ all -> 0x006b }
            r4.setOnDismissListener(r5)     // Catch:{ all -> 0x006b }
            r7.mDialogCreated = r3     // Catch:{ all -> 0x006b }
            goto L_0x0068
        L_0x0065:
            r3 = 0
            r7.mDialog = r3     // Catch:{ all -> 0x006b }
        L_0x0068:
            r7.mCreatingDialog = r0
            goto L_0x006f
        L_0x006b:
            r8 = move-exception
            r7.mCreatingDialog = r0
            throw r8
        L_0x006f:
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r2)
            if (r0 == 0) goto L_0x008e
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "get layout inflater for DialogFragment "
            r0.append(r2)
            r0.append(r7)
            java.lang.String r2 = " from dialog context"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            android.util.Log.d(r1, r0)
        L_0x008e:
            android.app.Dialog r7 = r7.mDialog
            if (r7 == 0) goto L_0x009a
            android.content.Context r7 = r7.getContext()
            android.view.LayoutInflater r8 = r8.cloneInContext(r7)
        L_0x009a:
            return r8
        L_0x009b:
            boolean r0 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r2)
            if (r0 == 0) goto L_0x00c1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "getting layout inflater for DialogFragment "
            r0.append(r2)
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            boolean r7 = r7.mShowsDialog
            if (r7 != 0) goto L_0x00bc
            java.lang.String r7 = "mShowsDialog = false: "
            androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0.m17m(r7, r0, r1)
            goto L_0x00c1
        L_0x00bc:
            java.lang.String r7 = "mCreatingDialog = true: "
            androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0.m17m(r7, r0, r1)
        L_0x00c1:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DialogFragment.onGetLayoutInflater(android.os.Bundle):android.view.LayoutInflater");
    }

    public final void performCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Bundle bundle2;
        super.performCreateView(layoutInflater, viewGroup, bundle);
        if (this.mView == null && this.mDialog != null && bundle != null && (bundle2 = bundle.getBundle("android:savedDialogState")) != null) {
            this.mDialog.onRestoreInstanceState(bundle2);
        }
    }
}
