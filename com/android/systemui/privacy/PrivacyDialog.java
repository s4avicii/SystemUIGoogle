package com.android.systemui.privacy;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: PrivacyDialog.kt */
public final class PrivacyDialog extends SystemUIDialog {
    public final PrivacyDialog$clickListener$1 clickListener;
    public final ArrayList dismissListeners = new ArrayList();
    public final AtomicBoolean dismissed = new AtomicBoolean(false);
    public final String enterpriseText;
    public final int iconColorSolid = Utils.getColorAttrDefaultColor(getContext(), 16843827);
    public final List<PrivacyElement> list;
    public final String phonecall;
    public ViewGroup rootView;

    /* compiled from: PrivacyDialog.kt */
    public interface OnDialogDismissed {
        void onDialogDismissed();
    }

    /* compiled from: PrivacyDialog.kt */
    public static final class PrivacyElement {
        public final boolean active;
        public final CharSequence applicationName;
        public final CharSequence attributionLabel;
        public final CharSequence attributionTag;
        public final StringBuilder builder;
        public final boolean enterprise;
        public final long lastActiveTimestamp;
        public final Intent navigationIntent;
        public final String packageName;
        public final CharSequence permGroupName;
        public final boolean phoneCall;
        public final CharSequence proxyLabel;
        public final PrivacyType type;
        public final int userId;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PrivacyElement)) {
                return false;
            }
            PrivacyElement privacyElement = (PrivacyElement) obj;
            return this.type == privacyElement.type && Intrinsics.areEqual(this.packageName, privacyElement.packageName) && this.userId == privacyElement.userId && Intrinsics.areEqual(this.applicationName, privacyElement.applicationName) && Intrinsics.areEqual(this.attributionTag, privacyElement.attributionTag) && Intrinsics.areEqual(this.attributionLabel, privacyElement.attributionLabel) && Intrinsics.areEqual(this.proxyLabel, privacyElement.proxyLabel) && this.lastActiveTimestamp == privacyElement.lastActiveTimestamp && this.active == privacyElement.active && this.enterprise == privacyElement.enterprise && this.phoneCall == privacyElement.phoneCall && Intrinsics.areEqual(this.permGroupName, privacyElement.permGroupName) && Intrinsics.areEqual(this.navigationIntent, privacyElement.navigationIntent);
        }

        public PrivacyElement(PrivacyType privacyType, String str, int i, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, long j, boolean z, boolean z2, boolean z3, String str2, Intent intent) {
            String str3 = str;
            CharSequence charSequence5 = charSequence;
            CharSequence charSequence6 = charSequence2;
            CharSequence charSequence7 = charSequence3;
            CharSequence charSequence8 = charSequence4;
            boolean z4 = z;
            boolean z5 = z2;
            boolean z6 = z3;
            String str4 = str2;
            Intent intent2 = intent;
            this.type = privacyType;
            this.packageName = str3;
            this.userId = i;
            this.applicationName = charSequence5;
            this.attributionTag = charSequence6;
            this.attributionLabel = charSequence7;
            this.proxyLabel = charSequence8;
            this.lastActiveTimestamp = j;
            this.active = z4;
            this.enterprise = z5;
            this.phoneCall = z6;
            this.permGroupName = str4;
            this.navigationIntent = intent2;
            StringBuilder sb = new StringBuilder("PrivacyElement(");
            this.builder = sb;
            sb.append(Intrinsics.stringPlus("type=", privacyType.getLogName()));
            sb.append(Intrinsics.stringPlus(", packageName=", str3));
            sb.append(Intrinsics.stringPlus(", userId=", Integer.valueOf(i)));
            sb.append(Intrinsics.stringPlus(", appName=", charSequence5));
            if (charSequence6 != null) {
                sb.append(Intrinsics.stringPlus(", attributionTag=", charSequence6));
            }
            if (charSequence7 != null) {
                sb.append(Intrinsics.stringPlus(", attributionLabel=", charSequence7));
            }
            if (charSequence8 != null) {
                sb.append(Intrinsics.stringPlus(", proxyLabel=", charSequence8));
            }
            sb.append(Intrinsics.stringPlus(", lastActive=", Long.valueOf(j)));
            if (z4) {
                sb.append(", active");
            }
            if (z5) {
                sb.append(", enterprise");
            }
            if (z6) {
                sb.append(", phoneCall");
            }
            sb.append(", permGroupName=" + str4 + ')');
            sb.append(Intrinsics.stringPlus(", navigationIntent=", intent2));
        }

        public final int hashCode() {
            int i;
            int i2;
            int i3;
            int hashCode = (this.applicationName.hashCode() + FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.userId, (this.packageName.hashCode() + (this.type.hashCode() * 31)) * 31, 31)) * 31;
            CharSequence charSequence = this.attributionTag;
            int i4 = 0;
            if (charSequence == null) {
                i = 0;
            } else {
                i = charSequence.hashCode();
            }
            int i5 = (hashCode + i) * 31;
            CharSequence charSequence2 = this.attributionLabel;
            if (charSequence2 == null) {
                i2 = 0;
            } else {
                i2 = charSequence2.hashCode();
            }
            int i6 = (i5 + i2) * 31;
            CharSequence charSequence3 = this.proxyLabel;
            if (charSequence3 == null) {
                i3 = 0;
            } else {
                i3 = charSequence3.hashCode();
            }
            int hashCode2 = (Long.hashCode(this.lastActiveTimestamp) + ((i6 + i3) * 31)) * 31;
            boolean z = this.active;
            boolean z2 = true;
            if (z) {
                z = true;
            }
            int i7 = (hashCode2 + (z ? 1 : 0)) * 31;
            boolean z3 = this.enterprise;
            if (z3) {
                z3 = true;
            }
            int i8 = (i7 + (z3 ? 1 : 0)) * 31;
            boolean z4 = this.phoneCall;
            if (!z4) {
                z2 = z4;
            }
            int hashCode3 = (this.permGroupName.hashCode() + ((i8 + (z2 ? 1 : 0)) * 31)) * 31;
            Intent intent = this.navigationIntent;
            if (intent != null) {
                i4 = intent.hashCode();
            }
            return hashCode3 + i4;
        }

        public final String toString() {
            return this.builder.toString();
        }
    }

    public PrivacyDialog(Context context, ArrayList arrayList, Function4 function4) {
        super(context, C1777R.style.PrivacyDialog);
        this.list = arrayList;
        this.enterpriseText = Intrinsics.stringPlus(" ", context.getString(C1777R.string.ongoing_privacy_dialog_enterprise));
        this.phonecall = context.getString(C1777R.string.ongoing_privacy_dialog_phonecall);
        this.clickListener = new PrivacyDialog$clickListener$1(function4);
    }

    public final void onCreate(Bundle bundle) {
        int i;
        int i2;
        CharSequence charSequence;
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            window.getAttributes().setFitInsetsTypes(window.getAttributes().getFitInsetsTypes() | WindowInsets.Type.statusBars());
            window.getAttributes().receiveInsetsIgnoringZOrder = true;
            window.setGravity(49);
        }
        setContentView(C1777R.layout.privacy_dialog);
        this.rootView = (ViewGroup) requireViewById(C1777R.C1779id.root);
        for (PrivacyElement privacyElement : this.list) {
            ViewGroup viewGroup = this.rootView;
            String str = null;
            if (viewGroup == null) {
                viewGroup = null;
            }
            LayoutInflater from = LayoutInflater.from(getContext());
            ViewGroup viewGroup2 = this.rootView;
            if (viewGroup2 == null) {
                viewGroup2 = null;
            }
            View inflate = from.inflate(C1777R.layout.privacy_dialog_item, viewGroup2, false);
            Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
            ViewGroup viewGroup3 = (ViewGroup) inflate;
            Objects.requireNonNull(privacyElement);
            PrivacyType privacyType = privacyElement.type;
            Context context = getContext();
            int ordinal = privacyType.ordinal();
            if (ordinal == 0) {
                i = C1777R.C1778drawable.privacy_item_circle_camera;
            } else if (ordinal == 1) {
                i = C1777R.C1778drawable.privacy_item_circle_microphone;
            } else if (ordinal == 2) {
                i = C1777R.C1778drawable.privacy_item_circle_location;
            } else {
                throw new NoWhenBranchMatchedException();
            }
            Drawable drawable = context.getDrawable(i);
            Objects.requireNonNull(drawable, "null cannot be cast to non-null type android.graphics.drawable.LayerDrawable");
            LayerDrawable layerDrawable = (LayerDrawable) drawable;
            layerDrawable.findDrawableByLayerId(C1777R.C1779id.icon).setTint(this.iconColorSolid);
            ImageView imageView = (ImageView) viewGroup3.requireViewById(C1777R.C1779id.icon);
            imageView.setImageDrawable(layerDrawable);
            imageView.setContentDescription(privacyElement.type.getName(imageView.getContext()));
            if (privacyElement.active) {
                i2 = C1777R.string.ongoing_privacy_dialog_using_op;
            } else {
                i2 = C1777R.string.ongoing_privacy_dialog_recent_op;
            }
            if (privacyElement.phoneCall) {
                charSequence = this.phonecall;
            } else {
                charSequence = privacyElement.applicationName;
            }
            if (privacyElement.enterprise) {
                charSequence = TextUtils.concat(new CharSequence[]{charSequence, this.enterpriseText});
            }
            CharSequence string = getContext().getString(i2, new Object[]{charSequence});
            CharSequence charSequence2 = privacyElement.attributionLabel;
            CharSequence charSequence3 = privacyElement.proxyLabel;
            if (charSequence2 != null && charSequence3 != null) {
                str = getContext().getString(C1777R.string.ongoing_privacy_dialog_attribution_proxy_label, new Object[]{charSequence2, charSequence3});
            } else if (charSequence2 != null) {
                str = getContext().getString(C1777R.string.ongoing_privacy_dialog_attribution_label, new Object[]{charSequence2});
            } else if (charSequence3 != null) {
                str = getContext().getString(C1777R.string.ongoing_privacy_dialog_attribution_text, new Object[]{charSequence3});
            }
            if (str != null) {
                string = TextUtils.concat(new CharSequence[]{string, " ", str});
            }
            ((TextView) viewGroup3.requireViewById(C1777R.C1779id.text)).setText(string);
            if (privacyElement.phoneCall) {
                viewGroup3.requireViewById(C1777R.C1779id.chevron).setVisibility(8);
            }
            viewGroup3.setTag(privacyElement);
            if (!privacyElement.phoneCall) {
                viewGroup3.setOnClickListener(this.clickListener);
            }
            viewGroup.addView(viewGroup3);
        }
    }

    public final void onStop() {
        super.onStop();
        this.dismissed.set(true);
        Iterator it = this.dismissListeners.iterator();
        while (it.hasNext()) {
            it.remove();
            OnDialogDismissed onDialogDismissed = (OnDialogDismissed) ((WeakReference) it.next()).get();
            if (onDialogDismissed != null) {
                onDialogDismissed.onDialogDismissed();
            }
        }
    }
}
