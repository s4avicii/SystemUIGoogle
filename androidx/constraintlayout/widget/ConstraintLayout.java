package androidx.constraintlayout.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.solver.widgets.Barrier;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Guideline;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.solver.widgets.WidgetContainer;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Constraints;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.p005qs.C0961QS;
import com.google.android.setupcompat.logging.CustomEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ConstraintLayout extends ViewGroup {
    public static final /* synthetic */ int $r8$clinit = 0;
    public SparseArray<View> mChildrenByIds = new SparseArray<>();
    public ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList<>(4);
    public ConstraintSet mConstraintSet = null;
    public int mConstraintSetId = -1;
    public HashMap<String, Integer> mDesignIds = new HashMap<>();
    public boolean mDirtyHierarchy = true;
    public ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
    public int mMaxHeight = Integer.MAX_VALUE;
    public int mMaxWidth = Integer.MAX_VALUE;
    public Measurer mMeasurer = new Measurer(this);
    public int mMinHeight = 0;
    public int mMinWidth = 0;
    public int mOnMeasureHeightMeasureSpec = 0;
    public int mOnMeasureWidthMeasureSpec = 0;
    public int mOptimizationLevel = 7;
    public SparseArray<ConstraintWidget> mTempMapIdToWidget = new SparseArray<>();

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public int baselineToBaseline = -1;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        public float circleAngle = 0.0f;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public boolean constrainedHeight = false;
        public boolean constrainedWidth = false;
        public String constraintTag = null;
        public String dimensionRatio = null;
        public int dimensionRatioSide = 1;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int endToEnd = -1;
        public int endToStart = -1;
        public int goneBottomMargin = -1;
        public int goneEndMargin = -1;
        public int goneLeftMargin = -1;
        public int goneRightMargin = -1;
        public int goneStartMargin = -1;
        public int goneTopMargin = -1;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        public boolean horizontalDimensionFixed = true;
        public float horizontalWeight = -1.0f;
        public boolean isGuideline = false;
        public boolean isHelper = false;
        public boolean isInPlaceholder = false;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public int matchConstraintDefaultHeight = 0;
        public int matchConstraintDefaultWidth = 0;
        public int matchConstraintMaxHeight = 0;
        public int matchConstraintMaxWidth = 0;
        public int matchConstraintMinHeight = 0;
        public int matchConstraintMinWidth = 0;
        public float matchConstraintPercentHeight = 1.0f;
        public float matchConstraintPercentWidth = 1.0f;
        public boolean needsBaseline = false;
        public int orientation = -1;
        public int resolveGoneLeftMargin = -1;
        public int resolveGoneRightMargin = -1;
        public int resolvedGuideBegin;
        public int resolvedGuideEnd;
        public float resolvedGuidePercent;
        public float resolvedHorizontalBias = 0.5f;
        public int resolvedLeftToLeft = -1;
        public int resolvedLeftToRight = -1;
        public int resolvedRightToLeft = -1;
        public int resolvedRightToRight = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        public boolean verticalDimensionFixed = true;
        public float verticalWeight = -1.0f;
        public ConstraintWidget widget = new ConstraintWidget();

        public static class Table {
            public static final SparseIntArray map;

            static {
                SparseIntArray sparseIntArray = new SparseIntArray();
                map = sparseIntArray;
                sparseIntArray.append(63, 8);
                sparseIntArray.append(64, 9);
                sparseIntArray.append(66, 10);
                sparseIntArray.append(67, 11);
                sparseIntArray.append(73, 12);
                sparseIntArray.append(72, 13);
                sparseIntArray.append(45, 14);
                sparseIntArray.append(44, 15);
                sparseIntArray.append(42, 16);
                sparseIntArray.append(46, 2);
                sparseIntArray.append(48, 3);
                sparseIntArray.append(47, 4);
                sparseIntArray.append(81, 49);
                sparseIntArray.append(82, 50);
                sparseIntArray.append(52, 5);
                sparseIntArray.append(53, 6);
                sparseIntArray.append(54, 7);
                sparseIntArray.append(0, 1);
                sparseIntArray.append(68, 17);
                sparseIntArray.append(69, 18);
                sparseIntArray.append(51, 19);
                sparseIntArray.append(50, 20);
                sparseIntArray.append(85, 21);
                sparseIntArray.append(88, 22);
                sparseIntArray.append(86, 23);
                sparseIntArray.append(83, 24);
                sparseIntArray.append(87, 25);
                sparseIntArray.append(84, 26);
                sparseIntArray.append(59, 29);
                sparseIntArray.append(74, 30);
                sparseIntArray.append(49, 44);
                sparseIntArray.append(61, 45);
                sparseIntArray.append(76, 46);
                sparseIntArray.append(60, 47);
                sparseIntArray.append(75, 48);
                sparseIntArray.append(40, 27);
                sparseIntArray.append(39, 28);
                sparseIntArray.append(77, 31);
                sparseIntArray.append(55, 32);
                sparseIntArray.append(79, 33);
                sparseIntArray.append(78, 34);
                sparseIntArray.append(80, 35);
                sparseIntArray.append(57, 36);
                sparseIntArray.append(56, 37);
                sparseIntArray.append(58, 38);
                sparseIntArray.append(62, 39);
                sparseIntArray.append(71, 40);
                sparseIntArray.append(65, 41);
                sparseIntArray.append(43, 42);
                sparseIntArray.append(41, 43);
                sparseIntArray.append(70, 51);
            }
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            int i;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                int i3 = Table.map.get(index);
                switch (i3) {
                    case 1:
                        this.orientation = obtainStyledAttributes.getInt(index, this.orientation);
                        break;
                    case 2:
                        int resourceId = obtainStyledAttributes.getResourceId(index, this.circleConstraint);
                        this.circleConstraint = resourceId;
                        if (resourceId != -1) {
                            break;
                        } else {
                            this.circleConstraint = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 3:
                        this.circleRadius = obtainStyledAttributes.getDimensionPixelSize(index, this.circleRadius);
                        break;
                    case 4:
                        float f = obtainStyledAttributes.getFloat(index, this.circleAngle) % 360.0f;
                        this.circleAngle = f;
                        if (f >= 0.0f) {
                            break;
                        } else {
                            this.circleAngle = (360.0f - f) % 360.0f;
                            break;
                        }
                    case 5:
                        this.guideBegin = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideBegin);
                        break;
                    case FalsingManager.VERSION /*6*/:
                        this.guideEnd = obtainStyledAttributes.getDimensionPixelOffset(index, this.guideEnd);
                        break;
                    case 7:
                        this.guidePercent = obtainStyledAttributes.getFloat(index, this.guidePercent);
                        break;
                    case 8:
                        int resourceId2 = obtainStyledAttributes.getResourceId(index, this.leftToLeft);
                        this.leftToLeft = resourceId2;
                        if (resourceId2 != -1) {
                            break;
                        } else {
                            this.leftToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 9:
                        int resourceId3 = obtainStyledAttributes.getResourceId(index, this.leftToRight);
                        this.leftToRight = resourceId3;
                        if (resourceId3 != -1) {
                            break;
                        } else {
                            this.leftToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 10:
                        int resourceId4 = obtainStyledAttributes.getResourceId(index, this.rightToLeft);
                        this.rightToLeft = resourceId4;
                        if (resourceId4 != -1) {
                            break;
                        } else {
                            this.rightToLeft = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case QSTileImpl.C1034H.STALE /*11*/:
                        int resourceId5 = obtainStyledAttributes.getResourceId(index, this.rightToRight);
                        this.rightToRight = resourceId5;
                        if (resourceId5 != -1) {
                            break;
                        } else {
                            this.rightToRight = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case KeyguardSliceProvider.ALARM_VISIBILITY_HOURS /*12*/:
                        int resourceId6 = obtainStyledAttributes.getResourceId(index, this.topToTop);
                        this.topToTop = resourceId6;
                        if (resourceId6 != -1) {
                            break;
                        } else {
                            this.topToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case C0961QS.VERSION /*13*/:
                        int resourceId7 = obtainStyledAttributes.getResourceId(index, this.topToBottom);
                        this.topToBottom = resourceId7;
                        if (resourceId7 != -1) {
                            break;
                        } else {
                            this.topToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 14:
                        int resourceId8 = obtainStyledAttributes.getResourceId(index, this.bottomToTop);
                        this.bottomToTop = resourceId8;
                        if (resourceId8 != -1) {
                            break;
                        } else {
                            this.bottomToTop = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 15:
                        int resourceId9 = obtainStyledAttributes.getResourceId(index, this.bottomToBottom);
                        this.bottomToBottom = resourceId9;
                        if (resourceId9 != -1) {
                            break;
                        } else {
                            this.bottomToBottom = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 16:
                        int resourceId10 = obtainStyledAttributes.getResourceId(index, this.baselineToBaseline);
                        this.baselineToBaseline = resourceId10;
                        if (resourceId10 != -1) {
                            break;
                        } else {
                            this.baselineToBaseline = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 17:
                        int resourceId11 = obtainStyledAttributes.getResourceId(index, this.startToEnd);
                        this.startToEnd = resourceId11;
                        if (resourceId11 != -1) {
                            break;
                        } else {
                            this.startToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 18:
                        int resourceId12 = obtainStyledAttributes.getResourceId(index, this.startToStart);
                        this.startToStart = resourceId12;
                        if (resourceId12 != -1) {
                            break;
                        } else {
                            this.startToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 19:
                        int resourceId13 = obtainStyledAttributes.getResourceId(index, this.endToStart);
                        this.endToStart = resourceId13;
                        if (resourceId13 != -1) {
                            break;
                        } else {
                            this.endToStart = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 20:
                        int resourceId14 = obtainStyledAttributes.getResourceId(index, this.endToEnd);
                        this.endToEnd = resourceId14;
                        if (resourceId14 != -1) {
                            break;
                        } else {
                            this.endToEnd = obtainStyledAttributes.getInt(index, -1);
                            break;
                        }
                    case 21:
                        this.goneLeftMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneLeftMargin);
                        break;
                    case 22:
                        this.goneTopMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneTopMargin);
                        break;
                    case 23:
                        this.goneRightMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneRightMargin);
                        break;
                    case 24:
                        this.goneBottomMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneBottomMargin);
                        break;
                    case 25:
                        this.goneStartMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneStartMargin);
                        break;
                    case 26:
                        this.goneEndMargin = obtainStyledAttributes.getDimensionPixelSize(index, this.goneEndMargin);
                        break;
                    case 27:
                        this.constrainedWidth = obtainStyledAttributes.getBoolean(index, this.constrainedWidth);
                        break;
                    case 28:
                        this.constrainedHeight = obtainStyledAttributes.getBoolean(index, this.constrainedHeight);
                        break;
                    case 29:
                        this.horizontalBias = obtainStyledAttributes.getFloat(index, this.horizontalBias);
                        break;
                    case 30:
                        this.verticalBias = obtainStyledAttributes.getFloat(index, this.verticalBias);
                        break;
                    case 31:
                        int i4 = obtainStyledAttributes.getInt(index, 0);
                        this.matchConstraintDefaultWidth = i4;
                        if (i4 != 1) {
                            break;
                        } else {
                            Log.e("ConstraintLayout", "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            break;
                        }
                    case 32:
                        int i5 = obtainStyledAttributes.getInt(index, 0);
                        this.matchConstraintDefaultHeight = i5;
                        if (i5 != 1) {
                            break;
                        } else {
                            Log.e("ConstraintLayout", "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            break;
                        }
                    case 33:
                        try {
                            this.matchConstraintMinWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinWidth);
                            break;
                        } catch (Exception unused) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinWidth) != -2) {
                                break;
                            } else {
                                this.matchConstraintMinWidth = -2;
                                break;
                            }
                        }
                    case 34:
                        try {
                            this.matchConstraintMaxWidth = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxWidth);
                            break;
                        } catch (Exception unused2) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxWidth) != -2) {
                                break;
                            } else {
                                this.matchConstraintMaxWidth = -2;
                                break;
                            }
                        }
                    case 35:
                        this.matchConstraintPercentWidth = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentWidth));
                        this.matchConstraintDefaultWidth = 2;
                        break;
                    case 36:
                        try {
                            this.matchConstraintMinHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMinHeight);
                            break;
                        } catch (Exception unused3) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMinHeight) != -2) {
                                break;
                            } else {
                                this.matchConstraintMinHeight = -2;
                                break;
                            }
                        }
                    case 37:
                        try {
                            this.matchConstraintMaxHeight = obtainStyledAttributes.getDimensionPixelSize(index, this.matchConstraintMaxHeight);
                            break;
                        } catch (Exception unused4) {
                            if (obtainStyledAttributes.getInt(index, this.matchConstraintMaxHeight) != -2) {
                                break;
                            } else {
                                this.matchConstraintMaxHeight = -2;
                                break;
                            }
                        }
                    case 38:
                        this.matchConstraintPercentHeight = Math.max(0.0f, obtainStyledAttributes.getFloat(index, this.matchConstraintPercentHeight));
                        this.matchConstraintDefaultHeight = 2;
                        break;
                    default:
                        switch (i3) {
                            case 44:
                                String string = obtainStyledAttributes.getString(index);
                                this.dimensionRatio = string;
                                this.dimensionRatioSide = -1;
                                if (string == null) {
                                    break;
                                } else {
                                    int length = string.length();
                                    int indexOf = this.dimensionRatio.indexOf(44);
                                    if (indexOf <= 0 || indexOf >= length - 1) {
                                        i = 0;
                                    } else {
                                        String substring = this.dimensionRatio.substring(0, indexOf);
                                        if (substring.equalsIgnoreCase("W")) {
                                            this.dimensionRatioSide = 0;
                                        } else if (substring.equalsIgnoreCase("H")) {
                                            this.dimensionRatioSide = 1;
                                        }
                                        i = indexOf + 1;
                                    }
                                    int indexOf2 = this.dimensionRatio.indexOf(58);
                                    if (indexOf2 >= 0 && indexOf2 < length - 1) {
                                        String substring2 = this.dimensionRatio.substring(i, indexOf2);
                                        String substring3 = this.dimensionRatio.substring(indexOf2 + 1);
                                        if (substring2.length() > 0 && substring3.length() > 0) {
                                            try {
                                                float parseFloat = Float.parseFloat(substring2);
                                                float parseFloat2 = Float.parseFloat(substring3);
                                                if (parseFloat > 0.0f && parseFloat2 > 0.0f) {
                                                    if (this.dimensionRatioSide != 1) {
                                                        Math.abs(parseFloat / parseFloat2);
                                                        break;
                                                    } else {
                                                        Math.abs(parseFloat2 / parseFloat);
                                                        break;
                                                    }
                                                }
                                            } catch (NumberFormatException unused5) {
                                                break;
                                            }
                                        }
                                    } else {
                                        String substring4 = this.dimensionRatio.substring(i);
                                        if (substring4.length() <= 0) {
                                            break;
                                        } else {
                                            Float.parseFloat(substring4);
                                            break;
                                        }
                                    }
                                }
                                break;
                            case 45:
                                this.horizontalWeight = obtainStyledAttributes.getFloat(index, this.horizontalWeight);
                                break;
                            case 46:
                                this.verticalWeight = obtainStyledAttributes.getFloat(index, this.verticalWeight);
                                break;
                            case 47:
                                this.horizontalChainStyle = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 48:
                                this.verticalChainStyle = obtainStyledAttributes.getInt(index, 0);
                                break;
                            case 49:
                                this.editorAbsoluteX = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteX);
                                break;
                            case CustomEvent.MAX_STR_LENGTH /*50*/:
                                this.editorAbsoluteY = obtainStyledAttributes.getDimensionPixelOffset(index, this.editorAbsoluteY);
                                break;
                            case 51:
                                this.constraintTag = obtainStyledAttributes.getString(index);
                                break;
                        }
                }
            }
            obtainStyledAttributes.recycle();
            validate();
        }

        public final void validate() {
            this.isGuideline = false;
            this.horizontalDimensionFixed = true;
            this.verticalDimensionFixed = true;
            int i = this.width;
            if (i == -2 && this.constrainedWidth) {
                this.horizontalDimensionFixed = false;
                if (this.matchConstraintDefaultWidth == 0) {
                    this.matchConstraintDefaultWidth = 1;
                }
            }
            int i2 = this.height;
            if (i2 == -2 && this.constrainedHeight) {
                this.verticalDimensionFixed = false;
                if (this.matchConstraintDefaultHeight == 0) {
                    this.matchConstraintDefaultHeight = 1;
                }
            }
            if (i == 0 || i == -1) {
                this.horizontalDimensionFixed = false;
                if (i == 0 && this.matchConstraintDefaultWidth == 1) {
                    this.width = -2;
                    this.constrainedWidth = true;
                }
            }
            if (i2 == 0 || i2 == -1) {
                this.verticalDimensionFixed = false;
                if (i2 == 0 && this.matchConstraintDefaultHeight == 1) {
                    this.height = -2;
                    this.constrainedHeight = true;
                }
            }
            if (this.guidePercent != -1.0f || this.guideBegin != -1 || this.guideEnd != -1) {
                this.isGuideline = true;
                this.horizontalDimensionFixed = true;
                this.verticalDimensionFixed = true;
                if (!(this.widget instanceof Guideline)) {
                    this.widget = new Guideline();
                }
                ((Guideline) this.widget).setOrientation(this.orientation);
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:15:0x0048  */
        /* JADX WARNING: Removed duplicated region for block: B:18:0x004f  */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x0056  */
        /* JADX WARNING: Removed duplicated region for block: B:24:0x005c  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0062  */
        /* JADX WARNING: Removed duplicated region for block: B:34:0x0074  */
        /* JADX WARNING: Removed duplicated region for block: B:35:0x007c  */
        @android.annotation.TargetApi(17)
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void resolveLayoutDirection(int r10) {
            /*
                r9 = this;
                int r0 = r9.leftMargin
                int r1 = r9.rightMargin
                super.resolveLayoutDirection(r10)
                int r10 = r9.getLayoutDirection()
                r2 = 0
                r3 = 1
                if (r3 != r10) goto L_0x0011
                r10 = r3
                goto L_0x0012
            L_0x0011:
                r10 = r2
            L_0x0012:
                r4 = -1
                r9.resolvedRightToLeft = r4
                r9.resolvedRightToRight = r4
                r9.resolvedLeftToLeft = r4
                r9.resolvedLeftToRight = r4
                int r5 = r9.goneLeftMargin
                r9.resolveGoneLeftMargin = r5
                int r5 = r9.goneRightMargin
                r9.resolveGoneRightMargin = r5
                float r5 = r9.horizontalBias
                r9.resolvedHorizontalBias = r5
                int r6 = r9.guideBegin
                r9.resolvedGuideBegin = r6
                int r7 = r9.guideEnd
                r9.resolvedGuideEnd = r7
                float r8 = r9.guidePercent
                r9.resolvedGuidePercent = r8
                if (r10 == 0) goto L_0x008e
                int r10 = r9.startToEnd
                if (r10 == r4) goto L_0x003d
                r9.resolvedRightToLeft = r10
            L_0x003b:
                r2 = r3
                goto L_0x0044
            L_0x003d:
                int r10 = r9.startToStart
                if (r10 == r4) goto L_0x0044
                r9.resolvedRightToRight = r10
                goto L_0x003b
            L_0x0044:
                int r10 = r9.endToStart
                if (r10 == r4) goto L_0x004b
                r9.resolvedLeftToRight = r10
                r2 = r3
            L_0x004b:
                int r10 = r9.endToEnd
                if (r10 == r4) goto L_0x0052
                r9.resolvedLeftToLeft = r10
                r2 = r3
            L_0x0052:
                int r10 = r9.goneStartMargin
                if (r10 == r4) goto L_0x0058
                r9.resolveGoneRightMargin = r10
            L_0x0058:
                int r10 = r9.goneEndMargin
                if (r10 == r4) goto L_0x005e
                r9.resolveGoneLeftMargin = r10
            L_0x005e:
                r10 = 1065353216(0x3f800000, float:1.0)
                if (r2 == 0) goto L_0x0066
                float r2 = r10 - r5
                r9.resolvedHorizontalBias = r2
            L_0x0066:
                boolean r2 = r9.isGuideline
                if (r2 == 0) goto L_0x00b2
                int r2 = r9.orientation
                if (r2 != r3) goto L_0x00b2
                r2 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r3 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
                if (r3 == 0) goto L_0x007c
                float r10 = r10 - r8
                r9.resolvedGuidePercent = r10
                r9.resolvedGuideBegin = r4
                r9.resolvedGuideEnd = r4
                goto L_0x00b2
            L_0x007c:
                if (r6 == r4) goto L_0x0085
                r9.resolvedGuideEnd = r6
                r9.resolvedGuideBegin = r4
                r9.resolvedGuidePercent = r2
                goto L_0x00b2
            L_0x0085:
                if (r7 == r4) goto L_0x00b2
                r9.resolvedGuideBegin = r7
                r9.resolvedGuideEnd = r4
                r9.resolvedGuidePercent = r2
                goto L_0x00b2
            L_0x008e:
                int r10 = r9.startToEnd
                if (r10 == r4) goto L_0x0094
                r9.resolvedLeftToRight = r10
            L_0x0094:
                int r10 = r9.startToStart
                if (r10 == r4) goto L_0x009a
                r9.resolvedLeftToLeft = r10
            L_0x009a:
                int r10 = r9.endToStart
                if (r10 == r4) goto L_0x00a0
                r9.resolvedRightToLeft = r10
            L_0x00a0:
                int r10 = r9.endToEnd
                if (r10 == r4) goto L_0x00a6
                r9.resolvedRightToRight = r10
            L_0x00a6:
                int r10 = r9.goneStartMargin
                if (r10 == r4) goto L_0x00ac
                r9.resolveGoneLeftMargin = r10
            L_0x00ac:
                int r10 = r9.goneEndMargin
                if (r10 == r4) goto L_0x00b2
                r9.resolveGoneRightMargin = r10
            L_0x00b2:
                int r10 = r9.endToStart
                if (r10 != r4) goto L_0x00fc
                int r10 = r9.endToEnd
                if (r10 != r4) goto L_0x00fc
                int r10 = r9.startToStart
                if (r10 != r4) goto L_0x00fc
                int r10 = r9.startToEnd
                if (r10 != r4) goto L_0x00fc
                int r10 = r9.rightToLeft
                if (r10 == r4) goto L_0x00d1
                r9.resolvedRightToLeft = r10
                int r10 = r9.rightMargin
                if (r10 > 0) goto L_0x00df
                if (r1 <= 0) goto L_0x00df
                r9.rightMargin = r1
                goto L_0x00df
            L_0x00d1:
                int r10 = r9.rightToRight
                if (r10 == r4) goto L_0x00df
                r9.resolvedRightToRight = r10
                int r10 = r9.rightMargin
                if (r10 > 0) goto L_0x00df
                if (r1 <= 0) goto L_0x00df
                r9.rightMargin = r1
            L_0x00df:
                int r10 = r9.leftToLeft
                if (r10 == r4) goto L_0x00ee
                r9.resolvedLeftToLeft = r10
                int r10 = r9.leftMargin
                if (r10 > 0) goto L_0x00fc
                if (r0 <= 0) goto L_0x00fc
                r9.leftMargin = r0
                goto L_0x00fc
            L_0x00ee:
                int r10 = r9.leftToRight
                if (r10 == r4) goto L_0x00fc
                r9.resolvedLeftToRight = r10
                int r10 = r9.leftMargin
                if (r10 > 0) goto L_0x00fc
                if (r0 <= 0) goto L_0x00fc
                r9.leftMargin = r0
            L_0x00fc:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.resolveLayoutDirection(int):void");
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    public class Measurer implements BasicMeasure.Measurer {
        public ConstraintLayout layout;

        /* JADX WARNING: Removed duplicated region for block: B:109:0x017a  */
        /* JADX WARNING: Removed duplicated region for block: B:110:0x0184  */
        /* JADX WARNING: Removed duplicated region for block: B:113:0x0195  */
        /* JADX WARNING: Removed duplicated region for block: B:115:0x019b  */
        /* JADX WARNING: Removed duplicated region for block: B:118:0x01a4  */
        /* JADX WARNING: Removed duplicated region for block: B:119:0x01a9  */
        /* JADX WARNING: Removed duplicated region for block: B:122:0x01ae  */
        /* JADX WARNING: Removed duplicated region for block: B:125:0x01b6  */
        /* JADX WARNING: Removed duplicated region for block: B:126:0x01bb  */
        /* JADX WARNING: Removed duplicated region for block: B:129:0x01c0  */
        /* JADX WARNING: Removed duplicated region for block: B:132:0x01c8 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:141:0x01e3  */
        /* JADX WARNING: Removed duplicated region for block: B:143:0x01e9  */
        /* JADX WARNING: Removed duplicated region for block: B:146:0x0200  */
        /* JADX WARNING: Removed duplicated region for block: B:147:0x0202  */
        /* JADX WARNING: Removed duplicated region for block: B:152:0x020c  */
        /* JADX WARNING: Removed duplicated region for block: B:153:0x020e  */
        /* JADX WARNING: Removed duplicated region for block: B:156:0x0215  */
        /* JADX WARNING: Removed duplicated region for block: B:39:0x00ad  */
        /* JADX WARNING: Removed duplicated region for block: B:66:0x0113  */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x011e  */
        /* JADX WARNING: Removed duplicated region for block: B:71:0x0120  */
        /* JADX WARNING: Removed duplicated region for block: B:73:0x0123  */
        /* JADX WARNING: Removed duplicated region for block: B:74:0x0125  */
        /* JADX WARNING: Removed duplicated region for block: B:77:0x012a A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:81:0x0132 A[ADDED_TO_REGION] */
        /* JADX WARNING: Removed duplicated region for block: B:88:0x0141  */
        /* JADX WARNING: Removed duplicated region for block: B:89:0x0143  */
        /* JADX WARNING: Removed duplicated region for block: B:93:0x014c  */
        /* JADX WARNING: Removed duplicated region for block: B:94:0x014e  */
        /* JADX WARNING: Removed duplicated region for block: B:97:0x015f A[ADDED_TO_REGION] */
        @android.annotation.SuppressLint({"WrongCall"})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void measure(androidx.constraintlayout.solver.widgets.ConstraintWidget r18, androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure.Measure r19) {
            /*
                r17 = this;
                r0 = r17
                r1 = r18
                r2 = r19
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
                if (r1 != 0) goto L_0x000b
                return
            L_0x000b:
                int r4 = r1.mVisibility
                r5 = 8
                r6 = 0
                if (r4 != r5) goto L_0x0019
                r2.measuredWidth = r6
                r2.measuredHeight = r6
                r2.measuredBaseline = r6
                return
            L_0x0019:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r2.horizontalBehavior
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = r2.verticalBehavior
                int r7 = r2.horizontalDimension
                int r8 = r2.verticalDimension
                androidx.constraintlayout.widget.ConstraintLayout r9 = r0.layout
                int r9 = r9.getPaddingTop()
                androidx.constraintlayout.widget.ConstraintLayout r10 = r0.layout
                int r10 = r10.getPaddingBottom()
                int r10 = r10 + r9
                androidx.constraintlayout.widget.ConstraintLayout r9 = r0.layout
                int r11 = androidx.constraintlayout.widget.ConstraintLayout.$r8$clinit
                int r9 = r9.getPaddingWidth()
                int r11 = r4.ordinal()
                r12 = 3
                r13 = 2
                r14 = -1
                r15 = 1
                if (r11 == 0) goto L_0x00a0
                if (r11 == r15) goto L_0x0093
                if (r11 == r13) goto L_0x0063
                if (r11 == r12) goto L_0x0049
                r7 = r6
                r9 = r7
                goto L_0x00a7
            L_0x0049:
                androidx.constraintlayout.widget.ConstraintLayout r7 = r0.layout
                int r7 = r7.mOnMeasureWidthMeasureSpec
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r1.mLeft
                if (r11 == 0) goto L_0x0055
                int r11 = r11.mMargin
                int r11 = r11 + r6
                goto L_0x0056
            L_0x0055:
                r11 = r6
            L_0x0056:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r1.mRight
                if (r12 == 0) goto L_0x005d
                int r12 = r12.mMargin
                int r11 = r11 + r12
            L_0x005d:
                int r9 = r9 + r11
                int r7 = android.view.ViewGroup.getChildMeasureSpec(r7, r9, r14)
                goto L_0x00a6
            L_0x0063:
                androidx.constraintlayout.widget.ConstraintLayout r7 = r0.layout
                int r7 = r7.mOnMeasureWidthMeasureSpec
                r11 = -2
                int r7 = android.view.ViewGroup.getChildMeasureSpec(r7, r9, r11)
                int r9 = r1.mMatchConstraintDefaultWidth
                if (r9 != r15) goto L_0x0072
                r9 = r15
                goto L_0x0073
            L_0x0072:
                r9 = r6
            L_0x0073:
                boolean r11 = r2.useDeprecated
                if (r11 == 0) goto L_0x0090
                if (r9 == 0) goto L_0x0085
                if (r9 == 0) goto L_0x0090
                int[] r9 = r1.wrapMeasure
                r9 = r9[r6]
                int r11 = r18.getWidth()
                if (r9 == r11) goto L_0x0090
            L_0x0085:
                int r7 = r18.getWidth()
                r11 = 1073741824(0x40000000, float:2.0)
                int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r11)
                goto L_0x00a6
            L_0x0090:
                r11 = 1073741824(0x40000000, float:2.0)
                goto L_0x009e
            L_0x0093:
                r11 = 1073741824(0x40000000, float:2.0)
                androidx.constraintlayout.widget.ConstraintLayout r7 = r0.layout
                int r7 = r7.mOnMeasureWidthMeasureSpec
                r12 = -2
                int r7 = android.view.ViewGroup.getChildMeasureSpec(r7, r9, r12)
            L_0x009e:
                r9 = r15
                goto L_0x00a7
            L_0x00a0:
                r11 = 1073741824(0x40000000, float:2.0)
                int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r11)
            L_0x00a6:
                r9 = r6
            L_0x00a7:
                int r11 = r5.ordinal()
                if (r11 == 0) goto L_0x0113
                if (r11 == r15) goto L_0x0106
                if (r11 == r13) goto L_0x00d6
                r8 = 3
                if (r11 == r8) goto L_0x00b8
                r0 = r6
                r8 = r0
                goto L_0x011a
            L_0x00b8:
                androidx.constraintlayout.widget.ConstraintLayout r0 = r0.layout
                int r0 = r0.mOnMeasureHeightMeasureSpec
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r1.mLeft
                if (r8 == 0) goto L_0x00c6
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r1.mTop
                int r8 = r8.mMargin
                int r8 = r8 + r6
                goto L_0x00c7
            L_0x00c6:
                r8 = r6
            L_0x00c7:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r1.mRight
                if (r11 == 0) goto L_0x00d0
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r1.mBottom
                int r11 = r11.mMargin
                int r8 = r8 + r11
            L_0x00d0:
                int r10 = r10 + r8
                int r0 = android.view.ViewGroup.getChildMeasureSpec(r0, r10, r14)
                goto L_0x0119
            L_0x00d6:
                androidx.constraintlayout.widget.ConstraintLayout r0 = r0.layout
                int r0 = r0.mOnMeasureHeightMeasureSpec
                r8 = -2
                int r0 = android.view.ViewGroup.getChildMeasureSpec(r0, r10, r8)
                int r8 = r1.mMatchConstraintDefaultHeight
                if (r8 != r15) goto L_0x00e5
                r8 = r15
                goto L_0x00e6
            L_0x00e5:
                r8 = r6
            L_0x00e6:
                boolean r10 = r2.useDeprecated
                if (r10 == 0) goto L_0x0103
                if (r8 == 0) goto L_0x00f8
                if (r8 == 0) goto L_0x0103
                int[] r8 = r1.wrapMeasure
                r8 = r8[r15]
                int r10 = r18.getHeight()
                if (r8 == r10) goto L_0x0103
            L_0x00f8:
                int r0 = r18.getHeight()
                r11 = 1073741824(0x40000000, float:2.0)
                int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r11)
                goto L_0x0119
            L_0x0103:
                r11 = 1073741824(0x40000000, float:2.0)
                goto L_0x0111
            L_0x0106:
                r11 = 1073741824(0x40000000, float:2.0)
                androidx.constraintlayout.widget.ConstraintLayout r0 = r0.layout
                int r0 = r0.mOnMeasureHeightMeasureSpec
                r8 = -2
                int r0 = android.view.ViewGroup.getChildMeasureSpec(r0, r10, r8)
            L_0x0111:
                r8 = r15
                goto L_0x011a
            L_0x0113:
                r11 = 1073741824(0x40000000, float:2.0)
                int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r11)
            L_0x0119:
                r8 = r6
            L_0x011a:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                if (r4 != r10) goto L_0x0120
                r11 = r15
                goto L_0x0121
            L_0x0120:
                r11 = r6
            L_0x0121:
                if (r5 != r10) goto L_0x0125
                r10 = r15
                goto L_0x0126
            L_0x0125:
                r10 = r6
            L_0x0126:
                androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
                if (r5 == r12) goto L_0x012f
                if (r5 != r3) goto L_0x012d
                goto L_0x012f
            L_0x012d:
                r5 = r6
                goto L_0x0130
            L_0x012f:
                r5 = r15
            L_0x0130:
                if (r4 == r12) goto L_0x0137
                if (r4 != r3) goto L_0x0135
                goto L_0x0137
            L_0x0135:
                r3 = r6
                goto L_0x0138
            L_0x0137:
                r3 = r15
            L_0x0138:
                r4 = 0
                if (r11 == 0) goto L_0x0143
                float r12 = r1.mDimensionRatio
                int r12 = (r12 > r4 ? 1 : (r12 == r4 ? 0 : -1))
                if (r12 <= 0) goto L_0x0143
                r12 = r15
                goto L_0x0144
            L_0x0143:
                r12 = r6
            L_0x0144:
                if (r10 == 0) goto L_0x014e
                float r13 = r1.mDimensionRatio
                int r4 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
                if (r4 <= 0) goto L_0x014e
                r4 = r15
                goto L_0x014f
            L_0x014e:
                r4 = r6
            L_0x014f:
                java.lang.Object r13 = r1.mCompanionWidget
                android.view.View r13 = (android.view.View) r13
                android.view.ViewGroup$LayoutParams r16 = r13.getLayoutParams()
                r14 = r16
                androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r14 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r14
                boolean r15 = r2.useDeprecated
                if (r15 != 0) goto L_0x0172
                if (r11 == 0) goto L_0x0172
                int r11 = r1.mMatchConstraintDefaultWidth
                if (r11 != 0) goto L_0x0172
                if (r10 == 0) goto L_0x0172
                int r10 = r1.mMatchConstraintDefaultHeight
                if (r10 == 0) goto L_0x016c
                goto L_0x0172
            L_0x016c:
                r8 = r6
                r9 = r8
                r15 = r9
            L_0x016f:
                r0 = -1
                goto L_0x01fe
            L_0x0172:
                boolean r10 = r13 instanceof androidx.constraintlayout.widget.VirtualLayout
                if (r10 == 0) goto L_0x0184
                boolean r10 = r1 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout
                if (r10 == 0) goto L_0x0184
                r10 = r1
                androidx.constraintlayout.solver.widgets.VirtualLayout r10 = (androidx.constraintlayout.solver.widgets.VirtualLayout) r10
                r11 = r13
                androidx.constraintlayout.widget.VirtualLayout r11 = (androidx.constraintlayout.widget.VirtualLayout) r11
                r11.onMeasure(r10, r7, r0)
                goto L_0x0187
            L_0x0184:
                r13.measure(r7, r0)
            L_0x0187:
                int r10 = r13.getMeasuredWidth()
                int r11 = r13.getMeasuredHeight()
                int r15 = r13.getBaseline()
                if (r9 == 0) goto L_0x0199
                int[] r9 = r1.wrapMeasure
                r9[r6] = r10
            L_0x0199:
                if (r8 == 0) goto L_0x01a0
                int[] r8 = r1.wrapMeasure
                r9 = 1
                r8[r9] = r11
            L_0x01a0:
                int r8 = r1.mMatchConstraintMinWidth
                if (r8 <= 0) goto L_0x01a9
                int r8 = java.lang.Math.max(r8, r10)
                goto L_0x01aa
            L_0x01a9:
                r8 = r10
            L_0x01aa:
                int r9 = r1.mMatchConstraintMaxWidth
                if (r9 <= 0) goto L_0x01b2
                int r8 = java.lang.Math.min(r9, r8)
            L_0x01b2:
                int r9 = r1.mMatchConstraintMinHeight
                if (r9 <= 0) goto L_0x01bb
                int r9 = java.lang.Math.max(r9, r11)
                goto L_0x01bc
            L_0x01bb:
                r9 = r11
            L_0x01bc:
                int r6 = r1.mMatchConstraintMaxHeight
                if (r6 <= 0) goto L_0x01c4
                int r9 = java.lang.Math.min(r6, r9)
            L_0x01c4:
                r6 = 1056964608(0x3f000000, float:0.5)
                if (r12 == 0) goto L_0x01d1
                if (r5 == 0) goto L_0x01d1
                float r3 = r1.mDimensionRatio
                float r4 = (float) r9
                float r4 = r4 * r3
                float r4 = r4 + r6
                int r8 = (int) r4
                goto L_0x01db
            L_0x01d1:
                if (r4 == 0) goto L_0x01db
                if (r3 == 0) goto L_0x01db
                float r3 = r1.mDimensionRatio
                float r4 = (float) r8
                float r4 = r4 / r3
                float r4 = r4 + r6
                int r9 = (int) r4
            L_0x01db:
                if (r10 != r8) goto L_0x01df
                if (r11 == r9) goto L_0x016f
            L_0x01df:
                r3 = 1073741824(0x40000000, float:2.0)
                if (r10 == r8) goto L_0x01e7
                int r7 = android.view.View.MeasureSpec.makeMeasureSpec(r8, r3)
            L_0x01e7:
                if (r11 == r9) goto L_0x01ed
                int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r3)
            L_0x01ed:
                r13.measure(r7, r0)
                int r8 = r13.getMeasuredWidth()
                int r9 = r13.getMeasuredHeight()
                int r15 = r13.getBaseline()
                goto L_0x016f
            L_0x01fe:
                if (r15 == r0) goto L_0x0202
                r0 = 1
                goto L_0x0203
            L_0x0202:
                r0 = 0
            L_0x0203:
                int r3 = r2.horizontalDimension
                if (r8 != r3) goto L_0x020e
                int r3 = r2.verticalDimension
                if (r9 == r3) goto L_0x020c
                goto L_0x020e
            L_0x020c:
                r6 = 0
                goto L_0x020f
            L_0x020e:
                r6 = 1
            L_0x020f:
                r2.measuredNeedsSolverPass = r6
                boolean r3 = r14.needsBaseline
                if (r3 == 0) goto L_0x0216
                r0 = 1
            L_0x0216:
                if (r0 == 0) goto L_0x0222
                r3 = -1
                if (r15 == r3) goto L_0x0222
                int r1 = r1.mBaselineDistance
                if (r1 == r15) goto L_0x0222
                r1 = 1
                r2.measuredNeedsSolverPass = r1
            L_0x0222:
                r2.measuredWidth = r8
                r2.measuredHeight = r9
                r2.measuredHasBaseline = r0
                r2.measuredBaseline = r15
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.Measurer.measure(androidx.constraintlayout.solver.widgets.ConstraintWidget, androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure):void");
        }

        public Measurer(ConstraintLayout constraintLayout) {
            this.layout = constraintLayout;
        }
    }

    public ConstraintLayout(Context context) {
        super(context);
        init((AttributeSet) null, 0, 0);
    }

    public final void forceLayout() {
        this.mDirtyHierarchy = true;
        super.forceLayout();
    }

    public final ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public void requestLayout() {
        this.mDirtyHierarchy = true;
        super.requestLayout();
    }

    public final boolean shouldDelayChildPressedState() {
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:157:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x020a  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x0212  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void applyConstraintsFromLayoutParams(boolean r25, android.view.View r26, androidx.constraintlayout.solver.widgets.ConstraintWidget r27, androidx.constraintlayout.widget.ConstraintLayout.LayoutParams r28, android.util.SparseArray<androidx.constraintlayout.solver.widgets.ConstraintWidget> r29) {
        /*
            r24 = this;
            r0 = r24
            r1 = r26
            r7 = r27
            r8 = r28
            r9 = r29
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r14 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r15 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r6 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            r28.validate()
            int r2 = r26.getVisibility()
            r7.mVisibility = r2
            boolean r2 = r8.isInPlaceholder
            if (r2 == 0) goto L_0x002b
            r2 = 8
            r7.mVisibility = r2
        L_0x002b:
            r7.mCompanionWidget = r1
            boolean r2 = r1 instanceof androidx.constraintlayout.widget.ConstraintHelper
            if (r2 == 0) goto L_0x003d
            androidx.constraintlayout.widget.ConstraintHelper r1 = (androidx.constraintlayout.widget.ConstraintHelper) r1
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.mLayoutWidget
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.mIsRtl
            r1.resolveRtl(r7, r2)
        L_0x003d:
            boolean r1 = r8.isGuideline
            r4 = -1
            if (r1 == 0) goto L_0x0073
            r0 = r7
            androidx.constraintlayout.solver.widgets.Guideline r0 = (androidx.constraintlayout.solver.widgets.Guideline) r0
            int r1 = r8.resolvedGuideBegin
            int r2 = r8.resolvedGuideEnd
            float r3 = r8.resolvedGuidePercent
            r5 = -1082130432(0xffffffffbf800000, float:-1.0)
            int r6 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r6 == 0) goto L_0x005b
            if (r6 <= 0) goto L_0x03a3
            r0.mRelativePercent = r3
            r0.mRelativeBegin = r4
            r0.mRelativeEnd = r4
            goto L_0x03a3
        L_0x005b:
            if (r1 == r4) goto L_0x0067
            if (r1 <= r4) goto L_0x03a3
            r0.mRelativePercent = r5
            r0.mRelativeBegin = r1
            r0.mRelativeEnd = r4
            goto L_0x03a3
        L_0x0067:
            if (r2 == r4) goto L_0x03a3
            if (r2 <= r4) goto L_0x03a3
            r0.mRelativePercent = r5
            r0.mRelativeBegin = r4
            r0.mRelativeEnd = r2
            goto L_0x03a3
        L_0x0073:
            int r1 = r8.resolvedLeftToLeft
            int r2 = r8.resolvedLeftToRight
            int r3 = r8.resolvedRightToLeft
            int r4 = r8.resolvedRightToRight
            r16 = r6
            int r6 = r8.resolveGoneLeftMargin
            r17 = r11
            int r11 = r8.resolveGoneRightMargin
            r18 = r12
            float r12 = r8.resolvedHorizontalBias
            r19 = r3
            int r3 = r8.circleConstraint
            r20 = r5
            r5 = -1
            if (r3 == r5) goto L_0x00c2
            java.lang.Object r0 = r9.get(r3)
            r2 = r0
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r2
            if (r2 == 0) goto L_0x00b1
            float r6 = r8.circleAngle
            int r4 = r8.circleRadius
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER
            r9 = 0
            r0 = r27
            r1 = r3
            r11 = r5
            r26 = r20
            r12 = 0
            r20 = r10
            r10 = 1
            r5 = r9
            r0.immediateConnect(r1, r2, r3, r4, r5)
            r7.mCircleConstraintAngle = r6
            goto L_0x00b8
        L_0x00b1:
            r11 = r5
            r26 = r20
            r12 = 0
            r20 = r10
            r10 = 1
        L_0x00b8:
            r0 = r26
            r2 = r12
            r19 = r13
            r1 = r16
            r13 = r11
            goto L_0x0214
        L_0x00c2:
            r3 = r5
            r26 = r20
            r5 = 0
            r20 = r10
            r10 = 1
            if (r1 == r3) goto L_0x00f8
            java.lang.Object r1 = r9.get(r1)
            r21 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r21 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r21
            if (r21 == 0) goto L_0x00ee
            int r2 = r8.leftMargin
            r1 = r27
            r22 = r2
            r2 = r15
            r10 = r19
            r19 = r13
            r13 = r3
            r3 = r21
            r23 = r4
            r4 = r15
            r5 = r22
            r21 = r16
            r1.immediateConnect(r2, r3, r4, r5, r6)
            goto L_0x0115
        L_0x00ee:
            r23 = r4
            r21 = r16
            r10 = r19
            r19 = r13
            r13 = r3
            goto L_0x0115
        L_0x00f8:
            r23 = r4
            r21 = r16
            r10 = r19
            r19 = r13
            r13 = r3
            if (r2 == r13) goto L_0x0115
            java.lang.Object r1 = r9.get(r2)
            r3 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x0115
            int r5 = r8.leftMargin
            r1 = r27
            r2 = r15
            r4 = r14
            r1.immediateConnect(r2, r3, r4, r5, r6)
        L_0x0115:
            if (r10 == r13) goto L_0x012b
            java.lang.Object r1 = r9.get(r10)
            r3 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x0142
            int r5 = r8.rightMargin
            r1 = r27
            r2 = r14
            r4 = r15
            r6 = r11
            r1.immediateConnect(r2, r3, r4, r5, r6)
            goto L_0x0142
        L_0x012b:
            r1 = r23
            if (r1 == r13) goto L_0x0142
            java.lang.Object r1 = r9.get(r1)
            r3 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x0142
            int r5 = r8.rightMargin
            r1 = r27
            r2 = r14
            r4 = r14
            r6 = r11
            r1.immediateConnect(r2, r3, r4, r5, r6)
        L_0x0142:
            int r1 = r8.topToTop
            if (r1 == r13) goto L_0x015d
            java.lang.Object r1 = r9.get(r1)
            r3 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x0177
            int r5 = r8.topMargin
            int r6 = r8.goneTopMargin
            r1 = r27
            r2 = r26
            r4 = r26
            r1.immediateConnect(r2, r3, r4, r5, r6)
            goto L_0x0177
        L_0x015d:
            int r1 = r8.topToBottom
            if (r1 == r13) goto L_0x0177
            java.lang.Object r1 = r9.get(r1)
            r3 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x0177
            int r5 = r8.topMargin
            int r6 = r8.goneTopMargin
            r1 = r27
            r2 = r26
            r4 = r21
            r1.immediateConnect(r2, r3, r4, r5, r6)
        L_0x0177:
            int r1 = r8.bottomToTop
            if (r1 == r13) goto L_0x0192
            java.lang.Object r1 = r9.get(r1)
            r3 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x01ac
            int r5 = r8.bottomMargin
            int r6 = r8.goneBottomMargin
            r1 = r27
            r2 = r21
            r4 = r26
            r1.immediateConnect(r2, r3, r4, r5, r6)
            goto L_0x01ac
        L_0x0192:
            int r1 = r8.bottomToBottom
            if (r1 == r13) goto L_0x01ac
            java.lang.Object r1 = r9.get(r1)
            r3 = r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            if (r3 == 0) goto L_0x01ac
            int r5 = r8.bottomMargin
            int r6 = r8.goneBottomMargin
            r1 = r27
            r2 = r21
            r4 = r21
            r1.immediateConnect(r2, r3, r4, r5, r6)
        L_0x01ac:
            int r1 = r8.baselineToBaseline
            if (r1 == r13) goto L_0x0201
            android.util.SparseArray<android.view.View> r0 = r0.mChildrenByIds
            java.lang.Object r0 = r0.get(r1)
            android.view.View r0 = (android.view.View) r0
            int r1 = r8.baselineToBaseline
            java.lang.Object r1 = r9.get(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r1
            if (r1 == 0) goto L_0x0201
            if (r0 == 0) goto L_0x0201
            android.view.ViewGroup$LayoutParams r2 = r0.getLayoutParams()
            boolean r2 = r2 instanceof androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
            if (r2 == 0) goto L_0x0201
            android.view.ViewGroup$LayoutParams r0 = r0.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r0 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r0
            r2 = 1
            r8.needsBaseline = r2
            r0.needsBaseline = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BASELINE
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r7.getAnchor(r3)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.getAnchor(r3)
            r3 = 0
            r4.connect(r1, r3, r13, r2)
            r7.hasBaseline = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.widget
            java.util.Objects.requireNonNull(r0)
            r0.hasBaseline = r2
            r0 = r26
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r7.getAnchor(r0)
            r1.reset()
            r1 = r21
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r7.getAnchor(r1)
            r2.reset()
            goto L_0x0205
        L_0x0201:
            r0 = r26
            r1 = r21
        L_0x0205:
            r2 = 0
            int r3 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1))
            if (r3 < 0) goto L_0x020c
            r7.mHorizontalBiasPercent = r12
        L_0x020c:
            float r3 = r8.verticalBias
            int r4 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r4 < 0) goto L_0x0214
            r7.mVerticalBiasPercent = r3
        L_0x0214:
            if (r25 == 0) goto L_0x0224
            int r3 = r8.editorAbsoluteX
            if (r3 != r13) goto L_0x021e
            int r4 = r8.editorAbsoluteY
            if (r4 == r13) goto L_0x0224
        L_0x021e:
            int r4 = r8.editorAbsoluteY
            r7.f15mX = r3
            r7.f16mY = r4
        L_0x0224:
            boolean r3 = r8.horizontalDimensionFixed
            r4 = -2
            if (r3 != 0) goto L_0x0261
            int r3 = r8.width
            if (r3 != r13) goto L_0x0251
            boolean r3 = r8.constrainedWidth
            if (r3 == 0) goto L_0x0239
            r3 = r19
            r7.setHorizontalDimensionBehaviour(r3)
            r5 = r20
            goto L_0x0240
        L_0x0239:
            r3 = r19
            r5 = r20
            r7.setHorizontalDimensionBehaviour(r5)
        L_0x0240:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r7.getAnchor(r15)
            int r9 = r8.leftMargin
            r6.mMargin = r9
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r7.getAnchor(r14)
            int r9 = r8.rightMargin
            r6.mMargin = r9
            goto L_0x025c
        L_0x0251:
            r3 = r19
            r5 = r20
            r7.setHorizontalDimensionBehaviour(r3)
            r6 = 0
            r7.setWidth(r6)
        L_0x025c:
            r9 = r17
            r6 = r18
            goto L_0x027b
        L_0x0261:
            r6 = r18
            r3 = r19
            r5 = r20
            r7.setHorizontalDimensionBehaviour(r6)
            int r9 = r8.width
            r7.setWidth(r9)
            int r9 = r8.width
            if (r9 != r4) goto L_0x0279
            r9 = r17
            r7.setHorizontalDimensionBehaviour(r9)
            goto L_0x027b
        L_0x0279:
            r9 = r17
        L_0x027b:
            boolean r10 = r8.verticalDimensionFixed
            if (r10 != 0) goto L_0x02a7
            int r4 = r8.height
            if (r4 != r13) goto L_0x029f
            boolean r4 = r8.constrainedHeight
            if (r4 == 0) goto L_0x028b
            r7.setVerticalDimensionBehaviour(r3)
            goto L_0x028e
        L_0x028b:
            r7.setVerticalDimensionBehaviour(r5)
        L_0x028e:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.getAnchor(r0)
            int r3 = r8.topMargin
            r0.mMargin = r3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r7.getAnchor(r1)
            int r1 = r8.bottomMargin
            r0.mMargin = r1
            goto L_0x02b6
        L_0x029f:
            r7.setVerticalDimensionBehaviour(r3)
            r0 = 0
            r7.setHeight(r0)
            goto L_0x02b6
        L_0x02a7:
            r7.setVerticalDimensionBehaviour(r6)
            int r0 = r8.height
            r7.setHeight(r0)
            int r0 = r8.height
            if (r0 != r4) goto L_0x02b6
            r7.setVerticalDimensionBehaviour(r9)
        L_0x02b6:
            java.lang.String r0 = r8.dimensionRatio
            if (r0 == 0) goto L_0x034b
            int r1 = r0.length()
            if (r1 != 0) goto L_0x02c4
            r7.mDimensionRatio = r2
            goto L_0x034b
        L_0x02c4:
            int r1 = r0.length()
            r3 = 44
            int r3 = r0.indexOf(r3)
            if (r3 <= 0) goto L_0x02f1
            int r4 = r1 + -1
            if (r3 >= r4) goto L_0x02f1
            r4 = 0
            java.lang.String r5 = r0.substring(r4, r3)
            java.lang.String r4 = "W"
            boolean r4 = r5.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x02e3
            r4 = 0
            goto L_0x02ee
        L_0x02e3:
            java.lang.String r4 = "H"
            boolean r4 = r5.equalsIgnoreCase(r4)
            if (r4 == 0) goto L_0x02ed
            r4 = 1
            goto L_0x02ee
        L_0x02ed:
            r4 = r13
        L_0x02ee:
            r5 = 1
            int r3 = r3 + r5
            goto L_0x02f4
        L_0x02f1:
            r5 = 1
            r4 = r13
            r3 = 0
        L_0x02f4:
            r6 = 58
            int r6 = r0.indexOf(r6)
            if (r6 < 0) goto L_0x0333
            int r1 = r1 - r5
            if (r6 >= r1) goto L_0x0333
            java.lang.String r1 = r0.substring(r3, r6)
            int r6 = r6 + r5
            java.lang.String r0 = r0.substring(r6)
            int r3 = r1.length()
            if (r3 <= 0) goto L_0x0342
            int r3 = r0.length()
            if (r3 <= 0) goto L_0x0342
            float r1 = java.lang.Float.parseFloat(r1)     // Catch:{ NumberFormatException -> 0x0342 }
            float r0 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x0342 }
            int r3 = (r1 > r2 ? 1 : (r1 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x0342
            int r3 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r3 <= 0) goto L_0x0342
            r3 = 1
            if (r4 != r3) goto L_0x032d
            float r0 = r0 / r1
            float r5 = java.lang.Math.abs(r0)     // Catch:{ NumberFormatException -> 0x0342 }
            goto L_0x0343
        L_0x032d:
            float r1 = r1 / r0
            float r5 = java.lang.Math.abs(r1)     // Catch:{ NumberFormatException -> 0x0342 }
            goto L_0x0343
        L_0x0333:
            java.lang.String r0 = r0.substring(r3)
            int r1 = r0.length()
            if (r1 <= 0) goto L_0x0342
            float r5 = java.lang.Float.parseFloat(r0)     // Catch:{ NumberFormatException -> 0x0342 }
            goto L_0x0343
        L_0x0342:
            r5 = r2
        L_0x0343:
            int r0 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x034b
            r7.mDimensionRatio = r5
            r7.mDimensionRatioSide = r4
        L_0x034b:
            float r0 = r8.horizontalWeight
            float[] r1 = r7.mWeight
            r3 = 0
            r1[r3] = r0
            float r0 = r8.verticalWeight
            r4 = 1
            r1[r4] = r0
            int r0 = r8.horizontalChainStyle
            r7.mHorizontalChainStyle = r0
            int r0 = r8.verticalChainStyle
            r7.mVerticalChainStyle = r0
            int r0 = r8.matchConstraintDefaultWidth
            int r1 = r8.matchConstraintMinWidth
            int r5 = r8.matchConstraintMaxWidth
            float r4 = r8.matchConstraintPercentWidth
            r7.mMatchConstraintDefaultWidth = r0
            r7.mMatchConstraintMinWidth = r1
            r1 = 2147483647(0x7fffffff, float:NaN)
            if (r5 != r1) goto L_0x0371
            r5 = r3
        L_0x0371:
            r7.mMatchConstraintMaxWidth = r5
            r7.mMatchConstraintPercentWidth = r4
            int r5 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
            r6 = 2
            r9 = 1065353216(0x3f800000, float:1.0)
            if (r5 <= 0) goto L_0x0384
            int r4 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1))
            if (r4 >= 0) goto L_0x0384
            if (r0 != 0) goto L_0x0384
            r7.mMatchConstraintDefaultWidth = r6
        L_0x0384:
            int r0 = r8.matchConstraintDefaultHeight
            int r4 = r8.matchConstraintMinHeight
            int r5 = r8.matchConstraintMaxHeight
            float r8 = r8.matchConstraintPercentHeight
            r7.mMatchConstraintDefaultHeight = r0
            r7.mMatchConstraintMinHeight = r4
            if (r5 != r1) goto L_0x0393
            r5 = r3
        L_0x0393:
            r7.mMatchConstraintMaxHeight = r5
            r7.mMatchConstraintPercentHeight = r8
            int r1 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x03a3
            int r1 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r1 >= 0) goto L_0x03a3
            if (r0 != 0) goto L_0x03a3
            r7.mMatchConstraintDefaultHeight = r6
        L_0x03a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.applyConstraintsFromLayoutParams(boolean, android.view.View, androidx.constraintlayout.solver.widgets.ConstraintWidget, androidx.constraintlayout.widget.ConstraintLayout$LayoutParams, android.util.SparseArray):void");
    }

    public void dispatchDraw(Canvas canvas) {
        Object tag;
        int size;
        ArrayList<ConstraintHelper> arrayList = this.mConstraintHelpers;
        if (arrayList != null && (size = arrayList.size()) > 0) {
            for (int i = 0; i < size; i++) {
                this.mConstraintHelpers.get(i).updatePreDraw(this);
            }
        }
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            int childCount = getChildCount();
            float width = (float) getWidth();
            float height = (float) getHeight();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                if (!(childAt.getVisibility() == 8 || (tag = childAt.getTag()) == null || !(tag instanceof String))) {
                    String[] split = ((String) tag).split(",");
                    if (split.length == 4) {
                        int parseInt = Integer.parseInt(split[0]);
                        int parseInt2 = Integer.parseInt(split[1]);
                        int parseInt3 = Integer.parseInt(split[2]);
                        int i3 = (int) ((((float) parseInt) / 1080.0f) * width);
                        int i4 = (int) ((((float) parseInt2) / 1920.0f) * height);
                        Paint paint = new Paint();
                        paint.setColor(-65536);
                        float f = (float) i3;
                        float f2 = (float) (i3 + ((int) ((((float) parseInt3) / 1080.0f) * width)));
                        Canvas canvas2 = canvas;
                        float f3 = (float) i4;
                        float f4 = f;
                        float f5 = f;
                        float f6 = f3;
                        Paint paint2 = paint;
                        float f7 = f2;
                        Paint paint3 = paint2;
                        canvas2.drawLine(f4, f6, f7, f3, paint3);
                        float parseInt4 = (float) (i4 + ((int) ((((float) Integer.parseInt(split[3])) / 1920.0f) * height)));
                        float f8 = f2;
                        float f9 = parseInt4;
                        canvas2.drawLine(f8, f6, f7, f9, paint3);
                        float f10 = parseInt4;
                        float f11 = f5;
                        canvas2.drawLine(f8, f10, f11, f9, paint3);
                        float f12 = f5;
                        canvas2.drawLine(f12, f10, f11, f3, paint3);
                        Paint paint4 = paint2;
                        paint4.setColor(-16711936);
                        Paint paint5 = paint4;
                        float f13 = f2;
                        Paint paint6 = paint5;
                        canvas2.drawLine(f12, f3, f13, parseInt4, paint6);
                        canvas2.drawLine(f12, parseInt4, f13, f3, paint6);
                    }
                }
            }
        }
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public final Object getDesignInformation(String str) {
        HashMap<String, Integer> hashMap;
        if (!(str instanceof String) || (hashMap = this.mDesignIds) == null || !hashMap.containsKey(str)) {
            return null;
        }
        return this.mDesignIds.get(str);
    }

    public final View getViewById(int i) {
        return this.mChildrenByIds.get(i);
    }

    public final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).widget;
    }

    public final void init(AttributeSet attributeSet, int i, int i2) {
        ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutWidget;
        Objects.requireNonNull(constraintWidgetContainer);
        constraintWidgetContainer.mCompanionWidget = this;
        ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutWidget;
        Measurer measurer = this.mMeasurer;
        Objects.requireNonNull(constraintWidgetContainer2);
        constraintWidgetContainer2.mMeasurer = measurer;
        DependencyGraph dependencyGraph = constraintWidgetContainer2.mDependencyGraph;
        Objects.requireNonNull(dependencyGraph);
        dependencyGraph.mMeasurer = measurer;
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout, i, i2);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i3 = 0; i3 < indexCount; i3++) {
                int index = obtainStyledAttributes.getIndex(i3);
                if (index == 9) {
                    this.mMinWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinWidth);
                } else if (index == 10) {
                    this.mMinHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMinHeight);
                } else if (index == 7) {
                    this.mMaxWidth = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxWidth);
                } else if (index == 8) {
                    this.mMaxHeight = obtainStyledAttributes.getDimensionPixelOffset(index, this.mMaxHeight);
                } else if (index == 89) {
                    this.mOptimizationLevel = obtainStyledAttributes.getInt(index, this.mOptimizationLevel);
                } else if (index == 38) {
                    int resourceId = obtainStyledAttributes.getResourceId(index, 0);
                    if (resourceId != 0) {
                        try {
                            parseLayoutDescription(resourceId);
                        } catch (Resources.NotFoundException unused) {
                        }
                    }
                } else if (index == 18) {
                    int resourceId2 = obtainStyledAttributes.getResourceId(index, 0);
                    try {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.load(getContext(), resourceId2);
                    } catch (Resources.NotFoundException unused2) {
                        this.mConstraintSet = null;
                    }
                    this.mConstraintSetId = resourceId2;
                }
            }
            obtainStyledAttributes.recycle();
        }
        ConstraintWidgetContainer constraintWidgetContainer3 = this.mLayoutWidget;
        int i4 = this.mOptimizationLevel;
        Objects.requireNonNull(constraintWidgetContainer3);
        constraintWidgetContainer3.mOptimizationLevel = i4;
    }

    public void onMeasure(int i, int i2) {
        boolean z;
        String str;
        int findId;
        int i3;
        ConstraintSet constraintSet;
        ConstraintWidget constraintWidget;
        int i4 = i;
        int i5 = i2;
        this.mOnMeasureWidthMeasureSpec = i4;
        this.mOnMeasureHeightMeasureSpec = i5;
        ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutWidget;
        boolean isRtl = isRtl();
        Objects.requireNonNull(constraintWidgetContainer);
        constraintWidgetContainer.mIsRtl = isRtl;
        if (this.mDirtyHierarchy) {
            int i6 = 0;
            this.mDirtyHierarchy = false;
            int childCount = getChildCount();
            int i7 = 0;
            while (true) {
                if (i7 >= childCount) {
                    z = false;
                    break;
                } else if (getChildAt(i7).isLayoutRequested()) {
                    z = true;
                    break;
                } else {
                    i7++;
                }
            }
            if (z) {
                boolean isInEditMode = isInEditMode();
                int childCount2 = getChildCount();
                for (int i8 = 0; i8 < childCount2; i8++) {
                    ConstraintWidget viewWidget = getViewWidget(getChildAt(i8));
                    if (viewWidget != null) {
                        viewWidget.reset();
                    }
                }
                int i9 = -1;
                if (isInEditMode) {
                    for (int i10 = 0; i10 < childCount2; i10++) {
                        View childAt = getChildAt(i10);
                        try {
                            String resourceName = getResources().getResourceName(childAt.getId());
                            setDesignInformation(resourceName, Integer.valueOf(childAt.getId()));
                            int indexOf = resourceName.indexOf(47);
                            if (indexOf != -1) {
                                resourceName = resourceName.substring(indexOf + 1);
                            }
                            int id = childAt.getId();
                            if (id == 0) {
                                constraintWidget = this.mLayoutWidget;
                            } else {
                                View view = this.mChildrenByIds.get(id);
                                if (view == null && (view = findViewById(id)) != null && view != this && view.getParent() == this) {
                                    onViewAdded(view);
                                }
                                if (view == this) {
                                    constraintWidget = this.mLayoutWidget;
                                } else if (view == null) {
                                    constraintWidget = null;
                                } else {
                                    constraintWidget = ((LayoutParams) view.getLayoutParams()).widget;
                                }
                            }
                            Objects.requireNonNull(constraintWidget);
                            constraintWidget.mDebugName = resourceName;
                        } catch (Resources.NotFoundException unused) {
                        }
                    }
                }
                if (this.mConstraintSetId != -1) {
                    int i11 = 0;
                    while (i11 < childCount2) {
                        View childAt2 = getChildAt(i11);
                        if (childAt2.getId() == this.mConstraintSetId && (childAt2 instanceof Constraints)) {
                            Constraints constraints = (Constraints) childAt2;
                            if (constraints.myConstraintSet == null) {
                                constraints.myConstraintSet = new ConstraintSet();
                            }
                            ConstraintSet constraintSet2 = constraints.myConstraintSet;
                            Objects.requireNonNull(constraintSet2);
                            int childCount3 = constraints.getChildCount();
                            constraintSet2.mConstraints.clear();
                            int i12 = i6;
                            while (i12 < childCount3) {
                                View childAt3 = constraints.getChildAt(i12);
                                Constraints.LayoutParams layoutParams = (Constraints.LayoutParams) childAt3.getLayoutParams();
                                int id2 = childAt3.getId();
                                if (!constraintSet2.mForceId || id2 != i9) {
                                    if (!constraintSet2.mConstraints.containsKey(Integer.valueOf(id2))) {
                                        i3 = childCount3;
                                        constraintSet2.mConstraints.put(Integer.valueOf(id2), new ConstraintSet.Constraint());
                                    } else {
                                        i3 = childCount3;
                                    }
                                    ConstraintSet.Constraint constraint = constraintSet2.mConstraints.get(Integer.valueOf(id2));
                                    if (childAt3 instanceof ConstraintHelper) {
                                        ConstraintHelper constraintHelper = (ConstraintHelper) childAt3;
                                        Objects.requireNonNull(constraint);
                                        constraint.fillFromConstraints(id2, layoutParams);
                                        if (constraintHelper instanceof Barrier) {
                                            ConstraintSet.Layout layout = constraint.layout;
                                            layout.mHelperType = 1;
                                            Barrier barrier = (Barrier) constraintHelper;
                                            layout.mBarrierDirection = barrier.mIndicatedType;
                                            constraintSet = constraintSet2;
                                            layout.mReferenceIds = Arrays.copyOf(barrier.mIds, barrier.mCount);
                                            ConstraintSet.Layout layout2 = constraint.layout;
                                            Barrier barrier2 = barrier.mBarrier;
                                            Objects.requireNonNull(barrier2);
                                            layout2.mBarrierMargin = barrier2.mMargin;
                                            constraint.fillFromConstraints(id2, layoutParams);
                                            i12++;
                                            childCount3 = i3;
                                            constraintSet2 = constraintSet;
                                            i9 = -1;
                                        }
                                    }
                                    constraintSet = constraintSet2;
                                    constraint.fillFromConstraints(id2, layoutParams);
                                    i12++;
                                    childCount3 = i3;
                                    constraintSet2 = constraintSet;
                                    i9 = -1;
                                } else {
                                    throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                                }
                            }
                            this.mConstraintSet = constraints.myConstraintSet;
                        }
                        i11++;
                        i6 = 0;
                        i9 = -1;
                    }
                }
                ConstraintSet constraintSet3 = this.mConstraintSet;
                if (constraintSet3 != null) {
                    constraintSet3.applyToInternal(this);
                }
                ConstraintWidgetContainer constraintWidgetContainer2 = this.mLayoutWidget;
                Objects.requireNonNull(constraintWidgetContainer2);
                constraintWidgetContainer2.mChildren.clear();
                int size = this.mConstraintHelpers.size();
                if (size > 0) {
                    for (int i13 = 0; i13 < size; i13++) {
                        ConstraintHelper constraintHelper2 = this.mConstraintHelpers.get(i13);
                        Objects.requireNonNull(constraintHelper2);
                        if (constraintHelper2.isInEditMode()) {
                            constraintHelper2.setIds(constraintHelper2.mReferenceIds);
                        }
                        String str2 = constraintHelper2.mReferenceIds;
                        if (str2 != null) {
                            constraintHelper2.setIds(str2);
                        }
                        HelperWidget helperWidget = constraintHelper2.mHelperWidget;
                        if (helperWidget != null) {
                            helperWidget.removeAllIds();
                            for (int i14 = 0; i14 < constraintHelper2.mCount; i14++) {
                                int i15 = constraintHelper2.mIds[i14];
                                View viewById = getViewById(i15);
                                if (viewById == null && (findId = constraintHelper2.findId(this, str)) != 0) {
                                    constraintHelper2.mIds[i14] = findId;
                                    constraintHelper2.mMap.put(Integer.valueOf(findId), (str = constraintHelper2.mMap.get(Integer.valueOf(i15))));
                                    viewById = getViewById(findId);
                                }
                                if (viewById != null) {
                                    constraintHelper2.mHelperWidget.add(getViewWidget(viewById));
                                }
                            }
                            constraintHelper2.mHelperWidget.updateConstraints();
                        }
                    }
                }
                for (int i16 = 0; i16 < childCount2; i16++) {
                    View childAt4 = getChildAt(i16);
                    if (childAt4 instanceof Placeholder) {
                        Placeholder placeholder = (Placeholder) childAt4;
                        Objects.requireNonNull(placeholder);
                        if (placeholder.mContentId == -1 && !placeholder.isInEditMode()) {
                            placeholder.setVisibility(placeholder.mEmptyVisibility);
                        }
                        View findViewById = findViewById(placeholder.mContentId);
                        placeholder.mContent = findViewById;
                        if (findViewById != null) {
                            ((LayoutParams) findViewById.getLayoutParams()).isInPlaceholder = true;
                            placeholder.mContent.setVisibility(0);
                            placeholder.setVisibility(0);
                        }
                    }
                }
                this.mTempMapIdToWidget.clear();
                this.mTempMapIdToWidget.put(0, this.mLayoutWidget);
                this.mTempMapIdToWidget.put(getId(), this.mLayoutWidget);
                for (int i17 = 0; i17 < childCount2; i17++) {
                    View childAt5 = getChildAt(i17);
                    this.mTempMapIdToWidget.put(childAt5.getId(), getViewWidget(childAt5));
                }
                for (int i18 = 0; i18 < childCount2; i18++) {
                    View childAt6 = getChildAt(i18);
                    ConstraintWidget viewWidget2 = getViewWidget(childAt6);
                    if (viewWidget2 != null) {
                        LayoutParams layoutParams2 = (LayoutParams) childAt6.getLayoutParams();
                        ConstraintWidgetContainer constraintWidgetContainer3 = this.mLayoutWidget;
                        Objects.requireNonNull(constraintWidgetContainer3);
                        constraintWidgetContainer3.mChildren.add(viewWidget2);
                        ConstraintWidget constraintWidget2 = viewWidget2.mParent;
                        if (constraintWidget2 != null) {
                            ((WidgetContainer) constraintWidget2).mChildren.remove(viewWidget2);
                            viewWidget2.mParent = null;
                        }
                        viewWidget2.mParent = constraintWidgetContainer3;
                        applyConstraintsFromLayoutParams(isInEditMode, childAt6, viewWidget2, layoutParams2, this.mTempMapIdToWidget);
                    }
                }
            }
            if (z) {
                this.mLayoutWidget.updateHierarchy();
            }
        }
        resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, i4, i5);
        int width = this.mLayoutWidget.getWidth();
        int height = this.mLayoutWidget.getHeight();
        ConstraintWidgetContainer constraintWidgetContainer4 = this.mLayoutWidget;
        Objects.requireNonNull(constraintWidgetContainer4);
        boolean z2 = constraintWidgetContainer4.mWidthMeasuredTooSmall;
        ConstraintWidgetContainer constraintWidgetContainer5 = this.mLayoutWidget;
        Objects.requireNonNull(constraintWidgetContainer5);
        resolveMeasuredDimension(i, i2, width, height, z2, constraintWidgetContainer5.mHeightMeasuredTooSmall);
    }

    public void parseLayoutDescription(int i) {
        new ConstraintLayoutStates(getContext(), i);
    }

    /* JADX WARNING: Removed duplicated region for block: B:103:0x01c0  */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x04a2  */
    /* JADX WARNING: Removed duplicated region for block: B:224:0x04de  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0088  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:407:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0100  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x010f  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0138  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x013b  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:97:0x01b6 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void resolveSystem(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r26, int r27, int r28, int r29) {
        /*
            r25 = this;
            r0 = r25
            r1 = r26
            r2 = r27
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            int r4 = android.view.View.MeasureSpec.getMode(r28)
            int r5 = android.view.View.MeasureSpec.getSize(r28)
            int r6 = android.view.View.MeasureSpec.getMode(r29)
            int r7 = android.view.View.MeasureSpec.getSize(r29)
            int r8 = r25.getPaddingTop()
            int r9 = r25.getPaddingBottom()
            int r9 = r9 + r8
            int r10 = r25.getPaddingWidth()
            int r11 = r25.getPaddingStart()
            int r12 = r25.getPaddingEnd()
            if (r11 > 0) goto L_0x0037
            if (r12 <= 0) goto L_0x0032
            goto L_0x0037
        L_0x0032:
            int r11 = r25.getPaddingLeft()
            goto L_0x003e
        L_0x0037:
            boolean r13 = r25.isRtl()
            if (r13 == 0) goto L_0x003e
            r11 = r12
        L_0x003e:
            int r5 = r5 - r10
            int r7 = r7 - r9
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            int r10 = r25.getPaddingTop()
            int r12 = r25.getPaddingBottom()
            int r12 = r12 + r10
            int r10 = r25.getPaddingWidth()
            int r13 = r25.getChildCount()
            r14 = -2147483648(0xffffffff80000000, float:-0.0)
            r15 = 0
            if (r4 == r14) goto L_0x0078
            if (r4 == 0) goto L_0x006a
            r14 = 1073741824(0x40000000, float:2.0)
            if (r4 == r14) goto L_0x0060
            r14 = r3
            goto L_0x0074
        L_0x0060:
            int r14 = r0.mMaxWidth
            int r14 = r14 - r10
            int r14 = java.lang.Math.min(r14, r5)
            r16 = r3
            goto L_0x0084
        L_0x006a:
            if (r13 != 0) goto L_0x0073
            int r14 = r0.mMinWidth
            int r14 = java.lang.Math.max(r15, r14)
            goto L_0x0082
        L_0x0073:
            r14 = r9
        L_0x0074:
            r16 = r14
            r14 = r15
            goto L_0x0084
        L_0x0078:
            if (r13 != 0) goto L_0x0081
            int r14 = r0.mMinWidth
            int r14 = java.lang.Math.max(r15, r14)
            goto L_0x0082
        L_0x0081:
            r14 = r5
        L_0x0082:
            r16 = r9
        L_0x0084:
            r15 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r6 == r15) goto L_0x00b0
            if (r6 == 0) goto L_0x009c
            r15 = 1073741824(0x40000000, float:2.0)
            if (r6 == r15) goto L_0x0091
            r13 = r3
            r15 = 0
            goto L_0x00a8
        L_0x0091:
            int r13 = r0.mMaxHeight
            int r13 = r13 - r12
            int r13 = java.lang.Math.min(r13, r7)
            r15 = r3
            r17 = r15
            goto L_0x00be
        L_0x009c:
            if (r13 != 0) goto L_0x00a6
            int r13 = r0.mMinHeight
            r15 = 0
            int r13 = java.lang.Math.max(r15, r13)
            goto L_0x00bb
        L_0x00a6:
            r15 = 0
            r13 = r9
        L_0x00a8:
            r17 = r3
            r24 = r15
            r15 = r13
            r13 = r24
            goto L_0x00be
        L_0x00b0:
            r15 = 0
            if (r13 != 0) goto L_0x00ba
            int r13 = r0.mMinHeight
            int r13 = java.lang.Math.max(r15, r13)
            goto L_0x00bb
        L_0x00ba:
            r13 = r7
        L_0x00bb:
            r17 = r3
            r15 = r9
        L_0x00be:
            int r3 = r26.getWidth()
            r18 = r9
            r9 = 1
            if (r14 != r3) goto L_0x00cd
            int r3 = r26.getHeight()
            if (r13 == r3) goto L_0x00d4
        L_0x00cd:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph r3 = r1.mDependencyGraph
            java.util.Objects.requireNonNull(r3)
            r3.mNeedRedoMeasures = r9
        L_0x00d4:
            r3 = 0
            r1.f15mX = r3
            r1.f16mY = r3
            int r9 = r0.mMaxWidth
            int r9 = r9 - r10
            r20 = r7
            int[] r7 = r1.mMaxDimension
            r7[r3] = r9
            int r9 = r0.mMaxHeight
            int r9 = r9 - r12
            r19 = 1
            r7[r19] = r9
            r1.mMinWidth = r3
            r1.mMinHeight = r3
            r3 = r16
            r1.setHorizontalDimensionBehaviour(r3)
            r1.setWidth(r14)
            r1.setVerticalDimensionBehaviour(r15)
            r1.setHeight(r13)
            int r3 = r0.mMinWidth
            int r3 = r3 - r10
            if (r3 >= 0) goto L_0x0104
            r7 = 0
            r1.mMinWidth = r7
            goto L_0x0107
        L_0x0104:
            r7 = 0
            r1.mMinWidth = r3
        L_0x0107:
            int r0 = r0.mMinHeight
            int r0 = r0 - r12
            if (r0 >= 0) goto L_0x010f
            r1.mMinHeight = r7
            goto L_0x0111
        L_0x010f:
            r1.mMinHeight = r0
        L_0x0111:
            r1.mPaddingLeft = r11
            r1.mPaddingTop = r8
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure r0 = r1.mBasicMeasureSolver
            java.util.Objects.requireNonNull(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r3 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r7 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r9 = r1.mMeasurer
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r10 = r1.mChildren
            int r10 = r10.size()
            int r11 = r26.getWidth()
            int r12 = r26.getHeight()
            r13 = 128(0x80, float:1.794E-43)
            r14 = r2 & 128(0x80, float:1.794E-43)
            if (r14 != r13) goto L_0x0138
            r13 = 1
            goto L_0x0139
        L_0x0138:
            r13 = 0
        L_0x0139:
            if (r13 != 0) goto L_0x0148
            r14 = 64
            r2 = r2 & r14
            if (r2 != r14) goto L_0x0142
            r2 = 1
            goto L_0x0143
        L_0x0142:
            r2 = 0
        L_0x0143:
            if (r2 == 0) goto L_0x0146
            goto L_0x0148
        L_0x0146:
            r2 = 0
            goto L_0x0149
        L_0x0148:
            r2 = 1
        L_0x0149:
            if (r2 == 0) goto L_0x01ac
            r14 = 0
        L_0x014c:
            if (r14 >= r10) goto L_0x01ac
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r15 = r1.mChildren
            java.lang.Object r15 = r15.get(r14)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r15
            java.util.Objects.requireNonNull(r15)
            r25 = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r15.mListDimensionBehaviors
            r21 = r3
            r16 = 0
            r3 = r2[r16]
            if (r3 != r8) goto L_0x0167
            r3 = 1
            goto L_0x0168
        L_0x0167:
            r3 = 0
        L_0x0168:
            r16 = 1
            r2 = r2[r16]
            if (r2 != r8) goto L_0x0170
            r2 = 1
            goto L_0x0171
        L_0x0170:
            r2 = 0
        L_0x0171:
            if (r3 == 0) goto L_0x017e
            if (r2 == 0) goto L_0x017e
            float r2 = r15.mDimensionRatio
            r3 = 0
            int r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r2 <= 0) goto L_0x017e
            r2 = 1
            goto L_0x017f
        L_0x017e:
            r2 = 0
        L_0x017f:
            boolean r3 = r15.isInHorizontalChain()
            if (r3 == 0) goto L_0x0188
            if (r2 == 0) goto L_0x0188
            goto L_0x01aa
        L_0x0188:
            boolean r3 = r15.isInVerticalChain()
            if (r3 == 0) goto L_0x0191
            if (r2 == 0) goto L_0x0191
            goto L_0x01aa
        L_0x0191:
            boolean r2 = r15 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout
            if (r2 == 0) goto L_0x0196
            goto L_0x01aa
        L_0x0196:
            boolean r2 = r15.isInHorizontalChain()
            if (r2 != 0) goto L_0x01aa
            boolean r2 = r15.isInVerticalChain()
            if (r2 == 0) goto L_0x01a3
            goto L_0x01aa
        L_0x01a3:
            int r14 = r14 + 1
            r2 = r25
            r3 = r21
            goto L_0x014c
        L_0x01aa:
            r2 = 0
            goto L_0x01b2
        L_0x01ac:
            r25 = r2
            r21 = r3
            r2 = r25
        L_0x01b2:
            r3 = 1073741824(0x40000000, float:2.0)
            if (r4 != r3) goto L_0x01b8
            if (r6 == r3) goto L_0x01ba
        L_0x01b8:
            if (r13 == 0) goto L_0x01bc
        L_0x01ba:
            r3 = 1
            goto L_0x01bd
        L_0x01bc:
            r3 = 0
        L_0x01bd:
            r2 = r2 & r3
            if (r2 == 0) goto L_0x04a2
            int[] r2 = r1.mMaxDimension
            r14 = 0
            r2 = r2[r14]
            int r2 = java.lang.Math.min(r2, r5)
            int[] r5 = r1.mMaxDimension
            r14 = 1
            r5 = r5[r14]
            r15 = r20
            int r5 = java.lang.Math.min(r5, r15)
            r15 = 1073741824(0x40000000, float:2.0)
            if (r4 != r15) goto L_0x01eb
            int r15 = r26.getWidth()
            if (r15 == r2) goto L_0x01e8
            r1.setWidth(r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph r2 = r1.mDependencyGraph
            java.util.Objects.requireNonNull(r2)
            r2.mNeedBuildGraph = r14
        L_0x01e8:
            r2 = 1073741824(0x40000000, float:2.0)
            goto L_0x01ec
        L_0x01eb:
            r2 = r15
        L_0x01ec:
            if (r6 != r2) goto L_0x0204
            int r2 = r26.getHeight()
            if (r2 == r5) goto L_0x0200
            r1.setHeight(r5)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph r2 = r1.mDependencyGraph
            java.util.Objects.requireNonNull(r2)
            r5 = 1
            r2.mNeedBuildGraph = r5
            goto L_0x0201
        L_0x0200:
            r5 = 1
        L_0x0201:
            r2 = 1073741824(0x40000000, float:2.0)
            goto L_0x0205
        L_0x0204:
            r5 = 1
        L_0x0205:
            if (r4 != r2) goto L_0x03e8
            if (r6 != r2) goto L_0x03e8
            androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph r2 = r1.mDependencyGraph
            java.util.Objects.requireNonNull(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            r13 = r13 & r5
            boolean r5 = r2.mNeedBuildGraph
            if (r5 != 0) goto L_0x021c
            boolean r5 = r2.mNeedRedoMeasures
            if (r5 == 0) goto L_0x021a
            goto L_0x021c
        L_0x021a:
            r5 = 0
            goto L_0x0251
        L_0x021c:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r5 = r2.container
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r5 = r5.mChildren
            java.util.Iterator r5 = r5.iterator()
        L_0x0224:
            boolean r15 = r5.hasNext()
            if (r15 == 0) goto L_0x023e
            java.lang.Object r15 = r5.next()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r15
            r3 = 0
            r15.measured = r3
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r3 = r15.horizontalRun
            r3.reset()
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r15.verticalRun
            r3.reset()
            goto L_0x0224
        L_0x023e:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r2.container
            r5 = 0
            r3.measured = r5
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r3 = r3.horizontalRun
            r3.reset()
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r3.verticalRun
            r3.reset()
            r2.mNeedRedoMeasures = r5
        L_0x0251:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r2.mContainer
            r2.basicMeasureWidgets(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r2.container
            java.util.Objects.requireNonNull(r3)
            r3.f15mX = r5
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r2.container
            java.util.Objects.requireNonNull(r3)
            r3.f16mY = r5
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r3 = r2.container
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = r3.getDimensionBehaviour(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r5 = r2.container
            r15 = 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = r5.getDimensionBehaviour(r15)
            boolean r15 = r2.mNeedBuildGraph
            if (r15 == 0) goto L_0x0278
            r2.buildGraph()
        L_0x0278:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r15 = r2.container
            int r15 = r15.getX()
            r16 = r7
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r7 = r2.container
            int r7 = r7.getY()
            r20 = r9
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r9 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r9 = r9.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.start
            r9.resolve(r15)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r9 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r9 = r9.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r9.start
            r9.resolve(r7)
            r2.measureWidgets()
            r9 = r18
            if (r3 == r9) goto L_0x02b0
            if (r5 != r9) goto L_0x02a4
            goto L_0x02b0
        L_0x02a4:
            r23 = r8
            r18 = r11
            r22 = r12
            r12 = r17
            r17 = r0
            goto L_0x0325
        L_0x02b0:
            r27 = r13
            if (r13 == 0) goto L_0x02ce
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r13 = r2.mRuns
            java.util.Iterator r13 = r13.iterator()
        L_0x02ba:
            boolean r18 = r13.hasNext()
            if (r18 == 0) goto L_0x02ce
            java.lang.Object r18 = r13.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r18 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r18
            boolean r18 = r18.supportsWrapComputation()
            if (r18 != 0) goto L_0x02ba
            r13 = 0
            goto L_0x02d0
        L_0x02ce:
            r13 = r27
        L_0x02d0:
            if (r13 == 0) goto L_0x02fb
            if (r3 != r9) goto L_0x02fb
            r18 = r11
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r11 = r2.container
            r22 = r12
            r12 = r17
            r11.setHorizontalDimensionBehaviour(r12)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r11 = r2.container
            r17 = r0
            r23 = r8
            r0 = 0
            int r8 = r2.computeWrap(r11, r0)
            r11.setWidth(r8)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r8 = r0.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r8 = r8.dimension
            int r0 = r0.getWidth()
            r8.resolve(r0)
            goto L_0x0305
        L_0x02fb:
            r23 = r8
            r18 = r11
            r22 = r12
            r12 = r17
            r17 = r0
        L_0x0305:
            if (r13 == 0) goto L_0x0325
            if (r5 != r9) goto L_0x0325
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r2.container
            r0.setVerticalDimensionBehaviour(r12)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r2.container
            r8 = 1
            int r11 = r2.computeWrap(r0, r8)
            r0.setHeight(r11)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r8 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r8 = r8.dimension
            int r0 = r0.getHeight()
            r8.resolve(r0)
        L_0x0325:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r2.container
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r0.mListDimensionBehaviors
            r11 = 0
            r13 = r8[r11]
            if (r13 == r12) goto L_0x0335
            r8 = r8[r11]
            if (r8 != r14) goto L_0x0333
            goto L_0x0335
        L_0x0333:
            r0 = 0
            goto L_0x0379
        L_0x0335:
            int r0 = r0.getWidth()
            int r0 = r0 + r15
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r8 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r8 = r8.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r8 = r8.end
            r8.resolve(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r8 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r8 = r8.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r8 = r8.dimension
            int r0 = r0 - r15
            r8.resolve(r0)
            r2.measureWidgets()
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r2.container
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r0.mListDimensionBehaviors
            r11 = 1
            r13 = r8[r11]
            if (r13 == r12) goto L_0x035d
            r8 = r8[r11]
            if (r8 != r14) goto L_0x0375
        L_0x035d:
            int r0 = r0.getHeight()
            int r0 = r0 + r7
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r8 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r8 = r8.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r8 = r8.end
            r8.resolve(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r8 = r2.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r8 = r8.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r8 = r8.dimension
            int r0 = r0 - r7
            r8.resolve(r0)
        L_0x0375:
            r2.measureWidgets()
            r0 = 1
        L_0x0379:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r7 = r2.mRuns
            java.util.Iterator r7 = r7.iterator()
        L_0x037f:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x039a
            java.lang.Object r8 = r7.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r8 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r8
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r12 = r2.container
            if (r11 != r12) goto L_0x0396
            boolean r11 = r8.resolved
            if (r11 != 0) goto L_0x0396
            goto L_0x037f
        L_0x0396:
            r8.applyToWidget()
            goto L_0x037f
        L_0x039a:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r7 = r2.mRuns
            java.util.Iterator r7 = r7.iterator()
        L_0x03a0:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x03d7
            java.lang.Object r8 = r7.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r8 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r8
            if (r0 != 0) goto L_0x03b5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r8.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r12 = r2.container
            if (r11 != r12) goto L_0x03b5
            goto L_0x03a0
        L_0x03b5:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r11 = r8.start
            boolean r11 = r11.resolved
            if (r11 != 0) goto L_0x03bc
            goto L_0x03d5
        L_0x03bc:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r11 = r8.end
            boolean r11 = r11.resolved
            if (r11 != 0) goto L_0x03c7
            boolean r11 = r8 instanceof androidx.constraintlayout.solver.widgets.analyzer.GuidelineReference
            if (r11 != 0) goto L_0x03c7
            goto L_0x03d5
        L_0x03c7:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r11 = r8.dimension
            boolean r11 = r11.resolved
            if (r11 != 0) goto L_0x03a0
            boolean r11 = r8 instanceof androidx.constraintlayout.solver.widgets.analyzer.ChainRun
            if (r11 != 0) goto L_0x03a0
            boolean r8 = r8 instanceof androidx.constraintlayout.solver.widgets.analyzer.GuidelineReference
            if (r8 != 0) goto L_0x03a0
        L_0x03d5:
            r0 = 0
            goto L_0x03d8
        L_0x03d7:
            r0 = 1
        L_0x03d8:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r7 = r2.container
            r7.setHorizontalDimensionBehaviour(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r2.container
            r2.setVerticalDimensionBehaviour(r5)
            r3 = r0
            r0 = 1073741824(0x40000000, float:2.0)
            r2 = 2
            goto L_0x0492
        L_0x03e8:
            r17 = r0
            r16 = r7
            r23 = r8
            r20 = r9
            r22 = r12
            r9 = r18
            r18 = r11
            androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph r0 = r1.mDependencyGraph
            java.util.Objects.requireNonNull(r0)
            boolean r2 = r0.mNeedBuildGraph
            if (r2 == 0) goto L_0x044e
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.container
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r2 = r2.mChildren
            java.util.Iterator r2 = r2.iterator()
        L_0x0407:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x042d
            java.lang.Object r3 = r2.next()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r3
            r5 = 0
            r3.measured = r5
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r7 = r3.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r8 = r7.dimension
            r8.resolved = r5
            r7.resolved = r5
            r7.reset()
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r3.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r7 = r3.dimension
            r7.resolved = r5
            r3.resolved = r5
            r3.reset()
            goto L_0x0407
        L_0x042d:
            r5 = 0
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.container
            r2.measured = r5
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r2 = r2.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r2.dimension
            r3.resolved = r5
            r2.resolved = r5
            r2.reset()
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r2.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r2.dimension
            r3.resolved = r5
            r2.resolved = r5
            r2.reset()
            r0.buildGraph()
            goto L_0x044f
        L_0x044e:
            r5 = 0
        L_0x044f:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.mContainer
            r0.basicMeasureWidgets(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.container
            java.util.Objects.requireNonNull(r2)
            r2.f15mX = r5
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.container
            java.util.Objects.requireNonNull(r2)
            r2.f16mY = r5
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = r0.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r2 = r2.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.start
            r2.resolve(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r0.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.start
            r0.resolve(r5)
            r0 = 1073741824(0x40000000, float:2.0)
            if (r4 != r0) goto L_0x0483
            boolean r2 = r1.directMeasureWithOrientation(r13, r5)
            r3 = 1
            r19 = r2 & 1
            r2 = r3
            r5 = r19
            goto L_0x0486
        L_0x0483:
            r3 = 1
            r5 = r3
            r2 = 0
        L_0x0486:
            if (r6 != r0) goto L_0x0491
            boolean r7 = r1.directMeasureWithOrientation(r13, r3)
            r3 = r5 & r7
            int r2 = r2 + 1
            goto L_0x0492
        L_0x0491:
            r3 = r5
        L_0x0492:
            if (r3 == 0) goto L_0x04d9
            if (r4 != r0) goto L_0x0498
            r4 = 1
            goto L_0x0499
        L_0x0498:
            r4 = 0
        L_0x0499:
            if (r6 != r0) goto L_0x049d
            r0 = 1
            goto L_0x049e
        L_0x049d:
            r0 = 0
        L_0x049e:
            r1.updateFromRuns(r4, r0)
            goto L_0x04d9
        L_0x04a2:
            r17 = r0
            r16 = r7
            r23 = r8
            r20 = r9
            r22 = r12
            r9 = r18
            r18 = r11
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r1.horizontalRun
            r0.clear()
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r1.verticalRun
            r0.clear()
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r0 = r1.mChildren
            java.util.Iterator r0 = r0.iterator()
        L_0x04c0:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x04d7
            java.lang.Object r2 = r0.next()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r2
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r3 = r2.horizontalRun
            r3.clear()
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r2.verticalRun
            r2.clear()
            goto L_0x04c0
        L_0x04d7:
            r2 = 0
            r3 = 0
        L_0x04d9:
            if (r3 == 0) goto L_0x04de
            r0 = 2
            if (r2 == r0) goto L_0x07c0
        L_0x04de:
            r0 = 8
            if (r10 <= 0) goto L_0x05b3
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r2 = r1.mChildren
            int r2 = r2.size()
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r3 = r1.mMeasurer
            r4 = 0
        L_0x04eb:
            if (r4 >= r2) goto L_0x053c
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r5 = r1.mChildren
            java.lang.Object r5 = r5.get(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r5
            boolean r6 = r5 instanceof androidx.constraintlayout.solver.widgets.Guideline
            if (r6 == 0) goto L_0x04fa
            goto L_0x050a
        L_0x04fa:
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r6 = r5.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            boolean r6 = r6.resolved
            if (r6 == 0) goto L_0x050f
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r6 = r5.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            boolean r6 = r6.resolved
            if (r6 == 0) goto L_0x050f
        L_0x050a:
            r6 = r17
            r11 = r23
            goto L_0x0535
        L_0x050f:
            r6 = 0
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = r5.getDimensionBehaviour(r6)
            r6 = 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r8 = r5.getDimensionBehaviour(r6)
            r11 = r23
            if (r7 != r11) goto L_0x0529
            int r7 = r5.mMatchConstraintDefaultWidth
            if (r7 == r6) goto L_0x0529
            if (r8 != r11) goto L_0x0529
            int r7 = r5.mMatchConstraintDefaultHeight
            if (r7 == r6) goto L_0x0529
            r6 = 1
            goto L_0x052a
        L_0x0529:
            r6 = 0
        L_0x052a:
            if (r6 == 0) goto L_0x052f
            r6 = r17
            goto L_0x0535
        L_0x052f:
            r6 = r17
            r7 = 0
            r6.measure(r3, r5, r7)
        L_0x0535:
            int r4 = r4 + 1
            r17 = r6
            r23 = r11
            goto L_0x04eb
        L_0x053c:
            r6 = r17
            androidx.constraintlayout.widget.ConstraintLayout$Measurer r3 = (androidx.constraintlayout.widget.ConstraintLayout.Measurer) r3
            java.util.Objects.requireNonNull(r3)
            androidx.constraintlayout.widget.ConstraintLayout r2 = r3.layout
            int r2 = r2.getChildCount()
            r15 = 0
        L_0x054a:
            if (r15 >= r2) goto L_0x0596
            androidx.constraintlayout.widget.ConstraintLayout r4 = r3.layout
            android.view.View r4 = r4.getChildAt(r15)
            boolean r5 = r4 instanceof androidx.constraintlayout.widget.Placeholder
            if (r5 == 0) goto L_0x0593
            androidx.constraintlayout.widget.Placeholder r4 = (androidx.constraintlayout.widget.Placeholder) r4
            java.util.Objects.requireNonNull(r4)
            android.view.View r5 = r4.mContent
            if (r5 != 0) goto L_0x0560
            goto L_0x0593
        L_0x0560:
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r5 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r5
            android.view.View r4 = r4.mContent
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r4 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r4
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r4.widget
            java.util.Objects.requireNonNull(r7)
            r8 = 0
            r7.mVisibility = r8
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r5.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r4.widget
            int r8 = r8.getWidth()
            r7.setWidth(r8)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r4.widget
            int r7 = r7.getHeight()
            r5.setHeight(r7)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r4.widget
            java.util.Objects.requireNonNull(r4)
            r4.mVisibility = r0
        L_0x0593:
            int r15 = r15 + 1
            goto L_0x054a
        L_0x0596:
            androidx.constraintlayout.widget.ConstraintLayout r2 = r3.layout
            java.util.ArrayList<androidx.constraintlayout.widget.ConstraintHelper> r2 = r2.mConstraintHelpers
            int r2 = r2.size()
            if (r2 <= 0) goto L_0x05b5
            r15 = 0
        L_0x05a1:
            if (r15 >= r2) goto L_0x05b5
            androidx.constraintlayout.widget.ConstraintLayout r4 = r3.layout
            java.util.ArrayList<androidx.constraintlayout.widget.ConstraintHelper> r4 = r4.mConstraintHelpers
            java.lang.Object r4 = r4.get(r15)
            androidx.constraintlayout.widget.ConstraintHelper r4 = (androidx.constraintlayout.widget.ConstraintHelper) r4
            java.util.Objects.requireNonNull(r4)
            int r15 = r15 + 1
            goto L_0x05a1
        L_0x05b3:
            r6 = r17
        L_0x05b5:
            int r2 = r1.mOptimizationLevel
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r3 = r6.mVariableDimensionsWidgets
            int r3 = r3.size()
            if (r10 <= 0) goto L_0x05c7
            r4 = r18
            r5 = r22
            r6.solveLinearSystem(r1, r4, r5)
            goto L_0x05cb
        L_0x05c7:
            r4 = r18
            r5 = r22
        L_0x05cb:
            if (r3 <= 0) goto L_0x07bd
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r7 = r1.mListDimensionBehaviors
            r15 = 0
            r8 = r7[r15]
            if (r8 != r9) goto L_0x05d6
            r8 = 1
            goto L_0x05d7
        L_0x05d6:
            r8 = r15
        L_0x05d7:
            r10 = 1
            r7 = r7[r10]
            if (r7 != r9) goto L_0x05de
            r7 = 1
            goto L_0x05df
        L_0x05de:
            r7 = r15
        L_0x05df:
            int r9 = r26.getWidth()
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r10 = r6.constraintWidgetContainer
            java.util.Objects.requireNonNull(r10)
            int r10 = r10.mMinWidth
            int r9 = java.lang.Math.max(r9, r10)
            int r10 = r26.getHeight()
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r11 = r6.constraintWidgetContainer
            java.util.Objects.requireNonNull(r11)
            int r11 = r11.mMinHeight
            int r10 = java.lang.Math.max(r10, r11)
            r11 = r15
            r12 = r11
        L_0x05ff:
            if (r11 >= r3) goto L_0x06a7
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r13 = r6.mVariableDimensionsWidgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r13
            boolean r14 = r13 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout
            if (r14 != 0) goto L_0x0617
            r17 = r2
            r14 = r16
            r0 = r20
            r15 = r21
            goto L_0x0698
        L_0x0617:
            int r14 = r13.getWidth()
            int r15 = r13.getHeight()
            r17 = r2
            r0 = r20
            r2 = 1
            boolean r18 = r6.measure(r0, r13, r2)
            r2 = r12 | r18
            int r12 = r13.getWidth()
            r28 = r2
            int r2 = r13.getHeight()
            if (r12 == r14) goto L_0x0660
            r13.setWidth(r12)
            if (r8 == 0) goto L_0x065c
            int r12 = r13.getX()
            int r14 = r13.mWidth
            int r12 = r12 + r14
            if (r12 <= r9) goto L_0x065c
            int r12 = r13.getX()
            int r14 = r13.mWidth
            int r12 = r12 + r14
            r14 = r16
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r16 = r13.getAnchor(r14)
            int r16 = r16.getMargin()
            int r12 = r16 + r12
            int r9 = java.lang.Math.max(r9, r12)
            goto L_0x065e
        L_0x065c:
            r14 = r16
        L_0x065e:
            r12 = 1
            goto L_0x0664
        L_0x0660:
            r14 = r16
            r12 = r28
        L_0x0664:
            if (r2 == r15) goto L_0x0690
            r13.setHeight(r2)
            if (r7 == 0) goto L_0x068c
            int r2 = r13.getY()
            int r12 = r13.mHeight
            int r2 = r2 + r12
            if (r2 <= r10) goto L_0x068c
            int r2 = r13.getY()
            int r12 = r13.mHeight
            int r2 = r2 + r12
            r15 = r21
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r13.getAnchor(r15)
            int r12 = r12.getMargin()
            int r12 = r12 + r2
            int r2 = java.lang.Math.max(r10, r12)
            r10 = r2
            goto L_0x068e
        L_0x068c:
            r15 = r21
        L_0x068e:
            r12 = 1
            goto L_0x0692
        L_0x0690:
            r15 = r21
        L_0x0692:
            androidx.constraintlayout.solver.widgets.VirtualLayout r13 = (androidx.constraintlayout.solver.widgets.VirtualLayout) r13
            boolean r2 = r13.mNeedsCallFromSolver
            r2 = r2 | r12
            r12 = r2
        L_0x0698:
            int r11 = r11 + 1
            r20 = r0
            r16 = r14
            r21 = r15
            r2 = r17
            r0 = 8
            r15 = 0
            goto L_0x05ff
        L_0x06a7:
            r17 = r2
            r14 = r16
            r0 = r20
            r15 = r21
            r2 = 0
            r11 = 2
        L_0x06b1:
            if (r2 >= r11) goto L_0x0797
            r13 = r12
            r12 = 0
        L_0x06b5:
            if (r12 >= r3) goto L_0x0778
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r11 = r6.mVariableDimensionsWidgets
            java.lang.Object r11 = r11.get(r12)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r11
            r28 = r3
            boolean r3 = r11 instanceof androidx.constraintlayout.solver.widgets.Helper
            if (r3 == 0) goto L_0x06c9
            boolean r3 = r11 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout
            if (r3 == 0) goto L_0x06cd
        L_0x06c9:
            boolean r3 = r11 instanceof androidx.constraintlayout.solver.widgets.Guideline
            if (r3 == 0) goto L_0x06d2
        L_0x06cd:
            r16 = r2
            r2 = 8
            goto L_0x06f3
        L_0x06d2:
            java.util.Objects.requireNonNull(r11)
            int r3 = r11.mVisibility
            r16 = r2
            r2 = 8
            if (r3 != r2) goto L_0x06de
            goto L_0x06f3
        L_0x06de:
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r3 = r11.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            boolean r3 = r3.resolved
            if (r3 == 0) goto L_0x06ef
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r11.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            boolean r3 = r3.resolved
            if (r3 == 0) goto L_0x06ef
            goto L_0x06f3
        L_0x06ef:
            boolean r3 = r11 instanceof androidx.constraintlayout.solver.widgets.VirtualLayout
            if (r3 == 0) goto L_0x06f7
        L_0x06f3:
            r18 = r4
            goto L_0x076b
        L_0x06f7:
            int r3 = r11.getWidth()
            int r2 = r11.getHeight()
            int r1 = r11.mBaselineDistance
            r18 = r4
            r4 = 1
            boolean r19 = r6.measure(r0, r11, r4)
            r19 = r13 | r19
            int r13 = r11.getWidth()
            int r4 = r11.getHeight()
            if (r13 == r3) goto L_0x0738
            r11.setWidth(r13)
            if (r8 == 0) goto L_0x0736
            int r3 = r11.getX()
            int r13 = r11.mWidth
            int r3 = r3 + r13
            if (r3 <= r9) goto L_0x0736
            int r3 = r11.getX()
            int r13 = r11.mWidth
            int r3 = r3 + r13
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r13 = r11.getAnchor(r14)
            int r13 = r13.getMargin()
            int r13 = r13 + r3
            int r9 = java.lang.Math.max(r9, r13)
        L_0x0736:
            r19 = 1
        L_0x0738:
            if (r4 == r2) goto L_0x075f
            r11.setHeight(r4)
            if (r7 == 0) goto L_0x075d
            int r2 = r11.getY()
            int r3 = r11.mHeight
            int r2 = r2 + r3
            if (r2 <= r10) goto L_0x075d
            int r2 = r11.getY()
            int r3 = r11.mHeight
            int r2 = r2 + r3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r11.getAnchor(r15)
            int r3 = r3.getMargin()
            int r3 = r3 + r2
            int r2 = java.lang.Math.max(r10, r3)
            r10 = r2
        L_0x075d:
            r19 = 1
        L_0x075f:
            boolean r2 = r11.hasBaseline
            if (r2 == 0) goto L_0x0769
            int r2 = r11.mBaselineDistance
            if (r1 == r2) goto L_0x0769
            r13 = 1
            goto L_0x076b
        L_0x0769:
            r13 = r19
        L_0x076b:
            int r12 = r12 + 1
            r1 = r26
            r3 = r28
            r2 = r16
            r4 = r18
            r11 = 2
            goto L_0x06b5
        L_0x0778:
            r16 = r2
            r28 = r3
            r18 = r4
            if (r13 == 0) goto L_0x0789
            r1 = r26
            r2 = r18
            r6.solveLinearSystem(r1, r2, r5)
            r12 = 0
            goto L_0x078e
        L_0x0789:
            r1 = r26
            r2 = r18
            r12 = r13
        L_0x078e:
            int r3 = r16 + 1
            r4 = r2
            r2 = r3
            r11 = 2
            r3 = r28
            goto L_0x06b1
        L_0x0797:
            r2 = r4
            if (r12 == 0) goto L_0x07ba
            r6.solveLinearSystem(r1, r2, r5)
            int r0 = r26.getWidth()
            if (r0 >= r9) goto L_0x07a8
            r1.setWidth(r9)
            r15 = 1
            goto L_0x07a9
        L_0x07a8:
            r15 = 0
        L_0x07a9:
            int r0 = r26.getHeight()
            if (r0 >= r10) goto L_0x07b4
            r1.setHeight(r10)
            r9 = 1
            goto L_0x07b5
        L_0x07b4:
            r9 = r15
        L_0x07b5:
            if (r9 == 0) goto L_0x07ba
            r6.solveLinearSystem(r1, r2, r5)
        L_0x07ba:
            r0 = r17
            goto L_0x07be
        L_0x07bd:
            r0 = r2
        L_0x07be:
            r1.mOptimizationLevel = r0
        L_0x07c0:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.resolveSystem(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, int, int, int):void");
    }

    public final void setDesignInformation(String str, Integer num) {
        if ((str instanceof String) && (num instanceof Integer)) {
            if (this.mDesignIds == null) {
                this.mDesignIds = new HashMap<>();
            }
            int indexOf = str.indexOf("/");
            if (indexOf != -1) {
                str = str.substring(indexOf + 1);
            }
            this.mDesignIds.put(str, Integer.valueOf(num.intValue()));
        }
    }

    public final void setId(int i) {
        this.mChildrenByIds.remove(getId());
        super.setId(i);
        this.mChildrenByIds.put(getId(), this);
    }

    public final int getPaddingWidth() {
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingEnd = getPaddingEnd() + getPaddingStart();
        if (paddingEnd > 0) {
            return paddingEnd;
        }
        return paddingRight;
    }

    public final boolean isRtl() {
        boolean z;
        if ((getContext().getApplicationInfo().flags & 4194304) != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z || 1 != getLayoutDirection()) {
            return false;
        }
        return true;
    }

    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View view;
        int childCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            ConstraintWidget constraintWidget = layoutParams.widget;
            if ((childAt.getVisibility() != 8 || layoutParams.isGuideline || layoutParams.isHelper || isInEditMode) && !layoutParams.isInPlaceholder) {
                int x = constraintWidget.getX();
                int y = constraintWidget.getY();
                int width = constraintWidget.getWidth() + x;
                int height = constraintWidget.getHeight() + y;
                childAt.layout(x, y, width, height);
                if ((childAt instanceof Placeholder) && (view = ((Placeholder) childAt).mContent) != null) {
                    view.setVisibility(0);
                    view.layout(x, y, width, height);
                }
            }
        }
        int size = this.mConstraintHelpers.size();
        if (size > 0) {
            for (int i6 = 0; i6 < size; i6++) {
                this.mConstraintHelpers.get(i6).updatePostLayout();
            }
        }
    }

    public void onViewAdded(View view) {
        super.onViewAdded(view);
        ConstraintWidget viewWidget = getViewWidget(view);
        if ((view instanceof Guideline) && !(viewWidget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            Guideline guideline = new Guideline();
            layoutParams.widget = guideline;
            layoutParams.isGuideline = true;
            guideline.setOrientation(layoutParams.orientation);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper constraintHelper = (ConstraintHelper) view;
            constraintHelper.validateParams();
            ((LayoutParams) view.getLayoutParams()).isHelper = true;
            if (!this.mConstraintHelpers.contains(constraintHelper)) {
                this.mConstraintHelpers.add(constraintHelper);
            }
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = true;
    }

    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.mChildrenByIds.remove(view.getId());
        ConstraintWidget viewWidget = getViewWidget(view);
        ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutWidget;
        Objects.requireNonNull(constraintWidgetContainer);
        constraintWidgetContainer.mChildren.remove(viewWidget);
        Objects.requireNonNull(viewWidget);
        viewWidget.mParent = null;
        this.mConstraintHelpers.remove(view);
        this.mDirtyHierarchy = true;
    }

    public final void resolveMeasuredDimension(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int resolveSizeAndState = View.resolveSizeAndState(getPaddingWidth() + i3, i, 0);
        int min = Math.min(this.mMaxWidth, resolveSizeAndState & 16777215);
        int min2 = Math.min(this.mMaxHeight, View.resolveSizeAndState(i4 + paddingBottom, i2, 0) & 16777215);
        if (z) {
            min |= 16777216;
        }
        if (z2) {
            min2 |= 16777216;
        }
        setMeasuredDimension(min, min2);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(attributeSet, i, 0);
    }

    @TargetApi(21)
    public ConstraintLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init(attributeSet, i, i2);
    }

    public final boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void removeView(View view) {
        super.removeView(view);
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
    }
}
