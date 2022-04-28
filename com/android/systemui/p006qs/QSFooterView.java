package com.android.systemui.p006qs;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.TouchAnimator;

/* renamed from: com.android.systemui.qs.QSFooterView */
public class QSFooterView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public TextView mBuildText;
    public final C09911 mDeveloperSettingsObserver = new ContentObserver(new Handler(this.mContext.getMainLooper())) {
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            QSFooterView qSFooterView = QSFooterView.this;
            int i = QSFooterView.$r8$clinit;
            qSFooterView.setBuildText();
        }
    };
    public View mEditButton;
    public View.OnClickListener mExpandClickListener;
    public boolean mExpanded;
    public float mExpansionAmount;
    public TouchAnimator mFooterAnimator;
    public PageIndicator mPageIndicator;
    public boolean mQsDisabled;
    public boolean mShouldShowBuildText;

    public void onDetachedFromWindow() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mDeveloperSettingsObserver);
        super.onDetachedFromWindow();
    }

    public final boolean performAccessibilityAction(int i, Bundle bundle) {
        View.OnClickListener onClickListener;
        if (i != 262144 || (onClickListener = this.mExpandClickListener) == null) {
            return super.performAccessibilityAction(i, bundle);
        }
        onClickListener.onClick((View) null);
        return true;
    }

    public final void setBuildText() {
        boolean z;
        boolean z2;
        if (this.mBuildText != null) {
            Context context = this.mContext;
            UserManager userManager = (UserManager) context.getSystemService("user");
            if (Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", Build.TYPE.equals("eng") ? 1 : 0) != 0) {
                z = true;
            } else {
                z = false;
            }
            boolean hasUserRestriction = userManager.hasUserRestriction("no_debugging_features");
            if (!userManager.isAdminUser() || hasUserRestriction || !z) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (z2) {
                this.mBuildText.setText(this.mContext.getString(17039810, new Object[]{Build.VERSION.RELEASE_OR_CODENAME, Build.ID}));
                this.mBuildText.setSelected(true);
                this.mShouldShowBuildText = true;
                return;
            }
            this.mBuildText.setText((CharSequence) null);
            this.mShouldShowBuildText = false;
            this.mBuildText.setSelected(false);
        }
    }

    public final void updateResources() {
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        builder.addFloat(this.mPageIndicator, "alpha", 0.0f, 1.0f);
        builder.addFloat(this.mBuildText, "alpha", 0.0f, 1.0f);
        builder.addFloat(this.mEditButton, "alpha", 0.0f, 1.0f);
        builder.mStartDelay = 0.9f;
        this.mFooterAnimator = builder.build();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(C1777R.dimen.qs_footers_margin_bottom);
        setLayoutParams(marginLayoutParams);
    }

    public QSFooterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("development_settings_enabled"), false, this.mDeveloperSettingsObserver, -1);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mPageIndicator = (PageIndicator) findViewById(C1777R.C1779id.footer_page_indicator);
        this.mBuildText = (TextView) findViewById(C1777R.C1779id.build);
        this.mEditButton = findViewById(16908291);
        updateResources();
        setImportantForAccessibility(1);
        setBuildText();
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }
}
