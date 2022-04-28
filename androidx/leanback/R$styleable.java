package androidx.leanback;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Process;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import kotlin.jvm.functions.Function1;

public class R$styleable {
    public static final int[] LeanbackGuidedStepTheme = {C1777R.attr.guidanceBreadcrumbStyle, C1777R.attr.guidanceContainerStyle, C1777R.attr.guidanceDescriptionStyle, C1777R.attr.guidanceEntryAnimation, C1777R.attr.guidanceIconStyle, C1777R.attr.guidanceTitleStyle, C1777R.attr.guidedActionCheckedAnimation, C1777R.attr.guidedActionContentWidth, C1777R.attr.guidedActionContentWidthNoIcon, C1777R.attr.guidedActionContentWidthWeight, C1777R.attr.guidedActionContentWidthWeightTwoPanels, C1777R.attr.guidedActionDescriptionMinLines, C1777R.attr.guidedActionDisabledChevronAlpha, C1777R.attr.guidedActionEnabledChevronAlpha, C1777R.attr.guidedActionItemCheckmarkStyle, C1777R.attr.guidedActionItemChevronStyle, C1777R.attr.guidedActionItemContainerStyle, C1777R.attr.guidedActionItemContentStyle, C1777R.attr.guidedActionItemDescriptionStyle, C1777R.attr.guidedActionItemIconStyle, C1777R.attr.guidedActionItemTitleStyle, C1777R.attr.guidedActionPressedAnimation, C1777R.attr.guidedActionTitleMaxLines, C1777R.attr.guidedActionTitleMinLines, C1777R.attr.guidedActionUncheckedAnimation, C1777R.attr.guidedActionUnpressedAnimation, C1777R.attr.guidedActionVerticalPadding, C1777R.attr.guidedActionsBackground, C1777R.attr.guidedActionsBackgroundDark, C1777R.attr.guidedActionsContainerStyle, C1777R.attr.guidedActionsElevation, C1777R.attr.guidedActionsEntryAnimation, C1777R.attr.guidedActionsListStyle, C1777R.attr.guidedActionsSelectorDrawable, C1777R.attr.guidedActionsSelectorHideAnimation, C1777R.attr.guidedActionsSelectorShowAnimation, C1777R.attr.guidedActionsSelectorStyle, C1777R.attr.guidedButtonActionsListStyle, C1777R.attr.guidedButtonActionsWidthWeight, C1777R.attr.guidedStepBackground, C1777R.attr.guidedStepEntryAnimation, C1777R.attr.guidedStepExitAnimation, C1777R.attr.guidedStepHeightWeight, C1777R.attr.guidedStepImeAppearingAnimation, C1777R.attr.guidedStepImeDisappearingAnimation, C1777R.attr.guidedStepKeyline, C1777R.attr.guidedStepReentryAnimation, C1777R.attr.guidedStepReturnAnimation, C1777R.attr.guidedStepTheme, C1777R.attr.guidedStepThemeFlag, C1777R.attr.guidedSubActionsListStyle};
    public static final int[] PagingIndicator = {C1777R.attr.arrowBgColor, C1777R.attr.arrowColor, C1777R.attr.arrowRadius, C1777R.attr.dotBgColor, C1777R.attr.dotToArrowGap, C1777R.attr.dotToDotGap, C1777R.attr.lbDotRadius};
    public static final int[] lbBaseCardView = {C1777R.attr.activatedAnimationDuration, C1777R.attr.cardBackground, C1777R.attr.cardForeground, C1777R.attr.cardType, C1777R.attr.extraVisibility, C1777R.attr.infoVisibility, C1777R.attr.selectedAnimationDelay, C1777R.attr.selectedAnimationDuration};
    public static final int[] lbBaseCardView_Layout = {C1777R.attr.layout_viewType};
    public static final int[] lbBaseGridView = {16842927, 16843028, 16843029, C1777R.attr.focusOutEnd, C1777R.attr.focusOutFront, C1777R.attr.focusOutSideEnd, C1777R.attr.focusOutSideStart, C1777R.attr.horizontalMargin, C1777R.attr.verticalMargin};
    public static final int[] lbDatePicker = {16843583, 16843584, C1777R.attr.datePickerFormat, C1777R.attr.pickerItemLayout, C1777R.attr.pickerItemTextViewId};
    public static final int[] lbHorizontalGridView = {C1777R.attr.numberOfRows, C1777R.attr.rowHeight};
    public static final int[] lbImageCardView = {C1777R.attr.infoAreaBackground, C1777R.attr.lbImageCardViewType};
    public static final int[] lbPicker = {C1777R.attr.pickerItemLayout, C1777R.attr.pickerItemTextViewId};
    public static final int[] lbPinPicker = {C1777R.attr.columnCount, C1777R.attr.pickerItemLayout, C1777R.attr.pickerItemTextViewId};
    public static final int[] lbResizingTextView = {C1777R.attr.maintainLineSpacing, C1777R.attr.resizeTrigger, C1777R.attr.resizedPaddingAdjustmentBottom, C1777R.attr.resizedPaddingAdjustmentTop, C1777R.attr.resizedTextSize};
    public static final int[] lbSearchOrbView = {C1777R.attr.searchOrbBrightColor, C1777R.attr.searchOrbColor, C1777R.attr.searchOrbIcon, C1777R.attr.searchOrbIconColor};
    public static final int[] lbSlide = {16843073, 16843160, 16843746, C1777R.attr.lb_slideEdge};
    public static final int[] lbTimePicker = {C1777R.attr.is24HourFormat, C1777R.attr.pickerItemLayout, C1777R.attr.pickerItemTextViewId, C1777R.attr.useCurrentTime};
    public static final int[] lbVerticalGridView = {C1777R.attr.columnWidth, C1777R.attr.numberOfColumns};

    public static final void appendElement(StringBuilder sb, Object obj, Function1 function1) {
        boolean z;
        if (function1 != null) {
            sb.append((CharSequence) function1.invoke(obj));
            return;
        }
        if (obj == null) {
            z = true;
        } else {
            z = obj instanceof CharSequence;
        }
        if (z) {
            sb.append((CharSequence) obj);
        } else if (obj instanceof Character) {
            sb.append(((Character) obj).charValue());
        } else {
            sb.append(String.valueOf(obj));
        }
    }

    public static int checkSelfPermission(Context context, String str) {
        boolean z;
        int i;
        int myPid = Process.myPid();
        int myUid = Process.myUid();
        String packageName = context.getPackageName();
        if (context.checkPermission(str, myPid, myUid) == -1) {
            return -1;
        }
        String permissionToOp = AppOpsManager.permissionToOp(str);
        if (permissionToOp != null) {
            if (packageName == null) {
                String[] packagesForUid = context.getPackageManager().getPackagesForUid(myUid);
                if (packagesForUid == null || packagesForUid.length <= 0) {
                    return -1;
                }
                packageName = packagesForUid[0];
            }
            int myUid2 = Process.myUid();
            String packageName2 = context.getPackageName();
            int i2 = 1;
            if (myUid2 != myUid || !Objects.equals(packageName2, packageName)) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(AppOpsManager.class);
                int callingUid = Binder.getCallingUid();
                if (appOpsManager == null) {
                    i = 1;
                } else {
                    i = appOpsManager.checkOpNoThrow(permissionToOp, callingUid, packageName);
                }
                if (i == 0) {
                    String opPackageName = context.getOpPackageName();
                    if (appOpsManager != null) {
                        i2 = appOpsManager.checkOpNoThrow(permissionToOp, myUid, opPackageName);
                    }
                    i = i2;
                }
            } else {
                i = ((AppOpsManager) context.getSystemService(AppOpsManager.class)).noteProxyOpNoThrow(permissionToOp, packageName);
            }
            if (i != 0) {
                return -2;
            }
        }
        return 0;
    }
}
