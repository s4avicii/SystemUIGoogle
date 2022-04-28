package com.android.p012wm.shell.pip.phone;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MagnificationSpec;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6;
import com.android.p012wm.shell.C1777R;
import com.android.p012wm.shell.common.ShellExecutor;
import com.android.p012wm.shell.pip.PipBoundsState;
import com.android.p012wm.shell.pip.PipSnapAlgorithm;
import com.android.p012wm.shell.pip.PipTaskOrganizer;
import com.android.systemui.scrim.ScrimView$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection */
public final class PipAccessibilityInteractionConnection {
    public ArrayList mAccessibilityNodeInfoList;
    public final AccessibilityCallbacks mCallbacks;
    public final PipAccessibilityInteractionConnectionImpl mConnectionImpl;
    public final Context mContext;
    public final Rect mExpandedBounds = new Rect();
    public final Rect mExpandedMovementBounds = new Rect();
    public final ShellExecutor mMainExcutor;
    public final PipMotionHelper mMotionHelper;
    public final Rect mNormalBounds = new Rect();
    public final Rect mNormalMovementBounds = new Rect();
    public final PipBoundsState mPipBoundsState;
    public final PipSnapAlgorithm mSnapAlgorithm;
    public final PipTaskOrganizer mTaskOrganizer;
    public Rect mTmpBounds = new Rect();
    public final Runnable mUnstashCallback;
    public final Runnable mUpdateMovementBoundCallback;

    /* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$AccessibilityCallbacks */
    public interface AccessibilityCallbacks {
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipAccessibilityInteractionConnection$PipAccessibilityInteractionConnectionImpl */
    public class PipAccessibilityInteractionConnectionImpl extends IAccessibilityInteractionConnection.Stub {
        public final void clearAccessibilityFocus() throws RemoteException {
        }

        public final void findAccessibilityNodeInfoByAccessibilityId(long j, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec, Bundle bundle) throws RemoteException {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new C1895xdda2519e(this, j, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec, bundle));
        }

        public final void findAccessibilityNodeInfosByText(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec) throws RemoteException {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new C1899xdda251a2(this, j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec));
        }

        public final void findAccessibilityNodeInfosByViewId(long j, String str, Region region, int i, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i2, int i3, long j2, MagnificationSpec magnificationSpec) throws RemoteException {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new C1898xdda251a1(this, j, str, region, i, iAccessibilityInteractionConnectionCallback, i2, i3, j2, magnificationSpec));
        }

        public final void findFocus(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec) throws RemoteException {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new C1896xdda2519f(this, j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec));
        }

        public final void focusSearch(long j, int i, Region region, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2, MagnificationSpec magnificationSpec) throws RemoteException {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new C1897xdda251a0(this, j, i, region, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2, magnificationSpec));
        }

        public final void notifyOutsideTouch() throws RemoteException {
        }

        public final void performAccessibilityAction(long j, int i, Bundle bundle, int i2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int i3, int i4, long j2) throws RemoteException {
            PipAccessibilityInteractionConnection.this.mMainExcutor.execute(new C1894xdda2519d(this, j, i, bundle, i2, iAccessibilityInteractionConnectionCallback, i3, i4, j2));
        }

        public PipAccessibilityInteractionConnectionImpl() {
        }
    }

    public final List<AccessibilityNodeInfo> getNodeList() {
        if (this.mAccessibilityNodeInfoList == null) {
            this.mAccessibilityNodeInfoList = new ArrayList(1);
        }
        Context context = this.mContext;
        AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
        obtain.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID, -3);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_DISMISS);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_MOVE_WINDOW);
        obtain.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_pip_resize, context.getString(C1777R.string.accessibility_action_pip_resize)));
        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_pip_stash, context.getString(C1777R.string.accessibility_action_pip_stash)));
        obtain.addAction(new AccessibilityNodeInfo.AccessibilityAction(C1777R.C1779id.action_pip_unstash, context.getString(C1777R.string.accessibility_action_pip_unstash)));
        obtain.setImportantForAccessibility(true);
        obtain.setClickable(true);
        obtain.setVisibleToUser(true);
        this.mAccessibilityNodeInfoList.clear();
        this.mAccessibilityNodeInfoList.add(obtain);
        return this.mAccessibilityNodeInfoList;
    }

    public PipAccessibilityInteractionConnection(Context context, PipBoundsState pipBoundsState, PipMotionHelper pipMotionHelper, PipTaskOrganizer pipTaskOrganizer, PipSnapAlgorithm pipSnapAlgorithm, PipTouchHandler$$ExternalSyntheticLambda2 pipTouchHandler$$ExternalSyntheticLambda2, ScrimView$$ExternalSyntheticLambda0 scrimView$$ExternalSyntheticLambda0, KeyguardUpdateMonitor$$ExternalSyntheticLambda6 keyguardUpdateMonitor$$ExternalSyntheticLambda6, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mMainExcutor = shellExecutor;
        this.mPipBoundsState = pipBoundsState;
        this.mMotionHelper = pipMotionHelper;
        this.mTaskOrganizer = pipTaskOrganizer;
        this.mSnapAlgorithm = pipSnapAlgorithm;
        this.mUpdateMovementBoundCallback = scrimView$$ExternalSyntheticLambda0;
        this.mUnstashCallback = keyguardUpdateMonitor$$ExternalSyntheticLambda6;
        this.mCallbacks = pipTouchHandler$$ExternalSyntheticLambda2;
        this.mConnectionImpl = new PipAccessibilityInteractionConnectionImpl();
    }
}
