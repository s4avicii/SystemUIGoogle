package com.google.android.material.datepicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.internal.ViewUtils;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Objects;

final class MaterialCalendarGridView extends GridView {
    public final Calendar dayCompute;
    public final boolean nestedScrollable;

    public MaterialCalendarGridView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void onDraw(Canvas canvas) {
        boolean z;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        boolean z2;
        boolean z3;
        MaterialCalendarGridView materialCalendarGridView = this;
        super.onDraw(canvas);
        MonthAdapter adapter = getAdapter();
        DateSelector<?> dateSelector = adapter.dateSelector;
        CalendarStyle calendarStyle = adapter.calendarStyle;
        int max = Math.max(adapter.firstPositionInMonth(), getFirstVisiblePosition());
        int min = Math.min(adapter.lastPositionInMonth(), getLastVisiblePosition());
        Long item = adapter.getItem(max);
        Long item2 = adapter.getItem(min);
        Iterator it = dateSelector.getSelectedRanges().iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            F f = pair.first;
            if (f == null) {
                materialCalendarGridView = this;
            } else if (pair.second != null) {
                long longValue = ((Long) f).longValue();
                long longValue2 = ((Long) pair.second).longValue();
                Long valueOf = Long.valueOf(longValue);
                Long valueOf2 = Long.valueOf(longValue2);
                if (item == null || item2 == null || valueOf == null || valueOf2 == null || valueOf.longValue() > item2.longValue() || valueOf2.longValue() < item.longValue()) {
                    z = true;
                } else {
                    z = false;
                }
                if (!z) {
                    boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
                    if (longValue < item.longValue()) {
                        if (max % adapter.month.daysInWeek == 0) {
                            z3 = true;
                        } else {
                            z3 = false;
                        }
                        if (z3) {
                            i2 = 0;
                        } else if (!isLayoutRtl) {
                            i2 = materialCalendarGridView.getChildAtPosition(max - 1).getRight();
                        } else {
                            i2 = materialCalendarGridView.getChildAtPosition(max - 1).getLeft();
                        }
                        i = max;
                    } else {
                        materialCalendarGridView.dayCompute.setTimeInMillis(longValue);
                        i = adapter.firstPositionInMonth() + (materialCalendarGridView.dayCompute.get(5) - 1);
                        View childAtPosition = materialCalendarGridView.getChildAtPosition(i);
                        i2 = (childAtPosition.getWidth() / 2) + childAtPosition.getLeft();
                    }
                    if (longValue2 > item2.longValue()) {
                        if ((min + 1) % adapter.month.daysInWeek == 0) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        if (z2) {
                            i4 = getWidth();
                        } else if (!isLayoutRtl) {
                            i4 = materialCalendarGridView.getChildAtPosition(min).getRight();
                        } else {
                            i4 = materialCalendarGridView.getChildAtPosition(min).getLeft();
                        }
                        i3 = min;
                    } else {
                        materialCalendarGridView.dayCompute.setTimeInMillis(longValue2);
                        i3 = adapter.firstPositionInMonth() + (materialCalendarGridView.dayCompute.get(5) - 1);
                        View childAtPosition2 = materialCalendarGridView.getChildAtPosition(i3);
                        i4 = (childAtPosition2.getWidth() / 2) + childAtPosition2.getLeft();
                    }
                    int itemId = (int) adapter.getItemId(i);
                    int i9 = max;
                    int i10 = min;
                    int itemId2 = (int) adapter.getItemId(i3);
                    while (itemId <= itemId2) {
                        int numColumns = getNumColumns() * itemId;
                        int numColumns2 = (getNumColumns() + numColumns) - 1;
                        View childAtPosition3 = materialCalendarGridView.getChildAtPosition(numColumns);
                        int top = childAtPosition3.getTop();
                        CalendarItemStyle calendarItemStyle = calendarStyle.day;
                        Objects.requireNonNull(calendarItemStyle);
                        int i11 = top + calendarItemStyle.insets.top;
                        int bottom = childAtPosition3.getBottom();
                        MonthAdapter monthAdapter = adapter;
                        CalendarItemStyle calendarItemStyle2 = calendarStyle.day;
                        Objects.requireNonNull(calendarItemStyle2);
                        int i12 = bottom - calendarItemStyle2.insets.bottom;
                        if (!isLayoutRtl) {
                            if (numColumns > i) {
                                i6 = 0;
                            } else {
                                i6 = i2;
                            }
                            if (i3 > numColumns2) {
                                i5 = getWidth();
                            } else {
                                i5 = i4;
                            }
                        } else {
                            if (i3 > numColumns2) {
                                i7 = 0;
                            } else {
                                i7 = i4;
                            }
                            if (numColumns > i) {
                                i8 = getWidth();
                            } else {
                                i8 = i2;
                            }
                            int i13 = i7;
                            i5 = i8;
                            i6 = i13;
                        }
                        canvas.drawRect((float) i6, (float) i11, (float) i5, (float) i12, calendarStyle.rangeFill);
                        itemId++;
                        materialCalendarGridView = this;
                        it = it;
                        adapter = monthAdapter;
                    }
                    materialCalendarGridView = this;
                    max = i9;
                    min = i10;
                }
            }
        }
    }

    public final void onFocusChanged(boolean z, int i, Rect rect) {
        if (!z) {
            super.onFocusChanged(false, i, rect);
        } else if (i == 33) {
            setSelection(getAdapter().lastPositionInMonth());
        } else if (i == 130) {
            setSelection(getAdapter().firstPositionInMonth());
        } else {
            super.onFocusChanged(true, i, rect);
        }
    }

    public final void onMeasure(int i, int i2) {
        if (this.nestedScrollable) {
            super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(16777215, Integer.MIN_VALUE));
            getLayoutParams().height = getMeasuredHeight();
            return;
        }
        super.onMeasure(i, i2);
    }

    public final void setAdapter(ListAdapter listAdapter) {
        if (listAdapter instanceof MonthAdapter) {
            super.setAdapter(listAdapter);
        } else {
            throw new IllegalArgumentException(String.format("%1$s must have its Adapter set to a %2$s", new Object[]{MaterialCalendarGridView.class.getCanonicalName(), MonthAdapter.class.getCanonicalName()}));
        }
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dayCompute = UtcDates.getUtcCalendarOf((Calendar) null);
        if (MaterialDatePicker.isFullscreen(getContext())) {
            setNextFocusLeftId(C1777R.C1779id.cancel_button);
            setNextFocusRightId(C1777R.C1779id.confirm_button);
        }
        this.nestedScrollable = MaterialDatePicker.readMaterialCalendarStyleBoolean(getContext(), C1777R.attr.nestedScrollable);
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() {
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo((AccessibilityNodeInfoCompat.CollectionInfoCompat) null);
            }
        });
    }

    public final MonthAdapter getAdapter() {
        return (MonthAdapter) super.getAdapter();
    }

    public final View getChildAtPosition(int i) {
        return getChildAt(i - getFirstVisiblePosition());
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        getAdapter().notifyDataSetChanged();
    }

    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!super.onKeyDown(i, keyEvent)) {
            return false;
        }
        if (getSelectedItemPosition() == -1 || getSelectedItemPosition() >= getAdapter().firstPositionInMonth()) {
            return true;
        }
        if (19 != i) {
            return false;
        }
        setSelection(getAdapter().firstPositionInMonth());
        return true;
    }

    public final void setSelection(int i) {
        if (i < getAdapter().firstPositionInMonth()) {
            super.setSelection(getAdapter().firstPositionInMonth());
        } else {
            super.setSelection(i);
        }
    }
}
