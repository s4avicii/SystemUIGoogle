package com.google.android.material.datepicker;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.core.util.Pair;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public final class MaterialCalendar<S> extends PickerFragment<S> {
    public static final Object MONTHS_VIEW_GROUP_TAG = "MONTHS_VIEW_GROUP_TAG";
    public static final Object NAVIGATION_NEXT_TAG = "NAVIGATION_NEXT_TAG";
    public static final Object NAVIGATION_PREV_TAG = "NAVIGATION_PREV_TAG";
    public static final Object SELECTOR_TOGGLE_TAG = "SELECTOR_TOGGLE_TAG";
    public CalendarConstraints calendarConstraints;
    public CalendarSelector calendarSelector;
    public CalendarStyle calendarStyle;
    public Month current;
    public DateSelector<S> dateSelector;
    public View dayFrame;
    public RecyclerView recyclerView;
    public int themeResId;
    public View yearFrame;
    public RecyclerView yearSelector;

    public enum CalendarSelector {
        DAY,
        YEAR
    }

    public interface OnDayClickListener {
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final int i;
        int i2;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getContext(), this.themeResId);
        this.calendarStyle = new CalendarStyle(contextThemeWrapper);
        LayoutInflater cloneInContext = layoutInflater.cloneInContext(contextThemeWrapper);
        CalendarConstraints calendarConstraints2 = this.calendarConstraints;
        Objects.requireNonNull(calendarConstraints2);
        Month month = calendarConstraints2.start;
        if (MaterialDatePicker.isFullscreen(contextThemeWrapper)) {
            i2 = C1777R.layout.mtrl_calendar_vertical;
            i = 1;
        } else {
            i2 = C1777R.layout.mtrl_calendar_horizontal;
            i = 0;
        }
        View inflate = cloneInContext.inflate(i2, viewGroup, false);
        Resources resources = requireContext().getResources();
        int dimensionPixelOffset = resources.getDimensionPixelOffset(C1777R.dimen.mtrl_calendar_navigation_bottom_padding) + resources.getDimensionPixelOffset(C1777R.dimen.mtrl_calendar_navigation_top_padding) + resources.getDimensionPixelSize(C1777R.dimen.mtrl_calendar_navigation_height);
        int dimensionPixelSize = resources.getDimensionPixelSize(C1777R.dimen.mtrl_calendar_days_of_week_height);
        int i3 = MonthAdapter.MAXIMUM_WEEKS;
        inflate.setMinimumHeight(dimensionPixelOffset + dimensionPixelSize + (resources.getDimensionPixelOffset(C1777R.dimen.mtrl_calendar_month_vertical_padding) * (i3 - 1)) + (resources.getDimensionPixelSize(C1777R.dimen.mtrl_calendar_day_height) * i3) + resources.getDimensionPixelOffset(C1777R.dimen.mtrl_calendar_bottom_padding));
        GridView gridView = (GridView) inflate.findViewById(C1777R.C1779id.mtrl_calendar_days_of_week);
        ViewCompat.setAccessibilityDelegate(gridView, new AccessibilityDelegateCompat() {
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCollectionInfo((AccessibilityNodeInfoCompat.CollectionInfoCompat) null);
            }
        });
        gridView.setAdapter(new DaysOfWeekAdapter());
        gridView.setNumColumns(month.daysInWeek);
        gridView.setEnabled(false);
        this.recyclerView = (RecyclerView) inflate.findViewById(C1777R.C1779id.mtrl_calendar_months);
        getContext();
        this.recyclerView.setLayoutManager(new SmoothCalendarLayoutManager(i) {
            public final void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
                if (i == 0) {
                    iArr[0] = MaterialCalendar.this.recyclerView.getWidth();
                    iArr[1] = MaterialCalendar.this.recyclerView.getWidth();
                    return;
                }
                iArr[0] = MaterialCalendar.this.recyclerView.getHeight();
                iArr[1] = MaterialCalendar.this.recyclerView.getHeight();
            }
        });
        this.recyclerView.setTag(MONTHS_VIEW_GROUP_TAG);
        final MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(contextThemeWrapper, this.dateSelector, this.calendarConstraints, new OnDayClickListener() {
        });
        this.recyclerView.setAdapter(monthsPagerAdapter);
        int integer = contextThemeWrapper.getResources().getInteger(C1777R.integer.mtrl_calendar_year_selector_span);
        RecyclerView recyclerView2 = (RecyclerView) inflate.findViewById(C1777R.C1779id.mtrl_calendar_year_selector_frame);
        this.yearSelector = recyclerView2;
        if (recyclerView2 != null) {
            recyclerView2.mHasFixedSize = true;
            recyclerView2.setLayoutManager(new GridLayoutManager(integer, 0));
            this.yearSelector.setAdapter(new YearGridAdapter(this));
            this.yearSelector.addItemDecoration(new RecyclerView.ItemDecoration() {
                public final Calendar endItem = UtcDates.getUtcCalendarOf((Calendar) null);
                public final Calendar startItem = UtcDates.getUtcCalendarOf((Calendar) null);

                public final void onDraw(Canvas canvas, RecyclerView recyclerView) {
                    int i;
                    int i2;
                    RecyclerView recyclerView2 = recyclerView;
                    Objects.requireNonNull(recyclerView);
                    RecyclerView.Adapter adapter = recyclerView2.mAdapter;
                    if (adapter instanceof YearGridAdapter) {
                        RecyclerView.LayoutManager layoutManager = recyclerView2.mLayout;
                        if (layoutManager instanceof GridLayoutManager) {
                            YearGridAdapter yearGridAdapter = (YearGridAdapter) adapter;
                            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                            for (Pair pair : MaterialCalendar.this.dateSelector.getSelectedRanges()) {
                                F f = pair.first;
                                if (!(f == null || pair.second == null)) {
                                    this.startItem.setTimeInMillis(((Long) f).longValue());
                                    this.endItem.setTimeInMillis(((Long) pair.second).longValue());
                                    int i3 = this.startItem.get(1);
                                    Objects.requireNonNull(yearGridAdapter);
                                    MaterialCalendar<?> materialCalendar = yearGridAdapter.materialCalendar;
                                    Objects.requireNonNull(materialCalendar);
                                    CalendarConstraints calendarConstraints = materialCalendar.calendarConstraints;
                                    Objects.requireNonNull(calendarConstraints);
                                    int i4 = i3 - calendarConstraints.start.year;
                                    int i5 = this.endItem.get(1);
                                    MaterialCalendar<?> materialCalendar2 = yearGridAdapter.materialCalendar;
                                    Objects.requireNonNull(materialCalendar2);
                                    CalendarConstraints calendarConstraints2 = materialCalendar2.calendarConstraints;
                                    Objects.requireNonNull(calendarConstraints2);
                                    int i6 = i5 - calendarConstraints2.start.year;
                                    View findViewByPosition = gridLayoutManager.findViewByPosition(i4);
                                    View findViewByPosition2 = gridLayoutManager.findViewByPosition(i6);
                                    int i7 = gridLayoutManager.mSpanCount;
                                    int i8 = i4 / i7;
                                    int i9 = i6 / i7;
                                    for (int i10 = i8; i10 <= i9; i10++) {
                                        View findViewByPosition3 = gridLayoutManager.findViewByPosition(gridLayoutManager.mSpanCount * i10);
                                        if (findViewByPosition3 != null) {
                                            int top = findViewByPosition3.getTop();
                                            CalendarItemStyle calendarItemStyle = MaterialCalendar.this.calendarStyle.year;
                                            Objects.requireNonNull(calendarItemStyle);
                                            int i11 = top + calendarItemStyle.insets.top;
                                            int bottom = findViewByPosition3.getBottom();
                                            CalendarItemStyle calendarItemStyle2 = MaterialCalendar.this.calendarStyle.year;
                                            Objects.requireNonNull(calendarItemStyle2);
                                            int i12 = bottom - calendarItemStyle2.insets.bottom;
                                            if (i10 == i8) {
                                                i = (findViewByPosition.getWidth() / 2) + findViewByPosition.getLeft();
                                            } else {
                                                i = 0;
                                            }
                                            if (i10 == i9) {
                                                i2 = (findViewByPosition2.getWidth() / 2) + findViewByPosition2.getLeft();
                                            } else {
                                                i2 = recyclerView.getWidth();
                                            }
                                            canvas.drawRect((float) i, (float) i11, (float) i2, (float) i12, MaterialCalendar.this.calendarStyle.rangeFill);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }
        if (inflate.findViewById(C1777R.C1779id.month_navigation_fragment_toggle) != null) {
            final MaterialButton materialButton = (MaterialButton) inflate.findViewById(C1777R.C1779id.month_navigation_fragment_toggle);
            materialButton.setTag(SELECTOR_TOGGLE_TAG);
            ViewCompat.setAccessibilityDelegate(materialButton, new AccessibilityDelegateCompat() {
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                    String str;
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                    if (MaterialCalendar.this.dayFrame.getVisibility() == 0) {
                        MaterialCalendar materialCalendar = MaterialCalendar.this;
                        Objects.requireNonNull(materialCalendar);
                        str = materialCalendar.requireContext().getResources().getString(C1777R.string.mtrl_picker_toggle_to_year_selection);
                    } else {
                        MaterialCalendar materialCalendar2 = MaterialCalendar.this;
                        Objects.requireNonNull(materialCalendar2);
                        str = materialCalendar2.requireContext().getResources().getString(C1777R.string.mtrl_picker_toggle_to_day_selection);
                    }
                    accessibilityNodeInfoCompat.mInfo.setHintText(str);
                }
            });
            MaterialButton materialButton2 = (MaterialButton) inflate.findViewById(C1777R.C1779id.month_navigation_previous);
            materialButton2.setTag(NAVIGATION_PREV_TAG);
            MaterialButton materialButton3 = (MaterialButton) inflate.findViewById(C1777R.C1779id.month_navigation_next);
            materialButton3.setTag(NAVIGATION_NEXT_TAG);
            this.yearFrame = inflate.findViewById(C1777R.C1779id.mtrl_calendar_year_selector_frame);
            this.dayFrame = inflate.findViewById(C1777R.C1779id.mtrl_calendar_day_selector_frame);
            setSelector(CalendarSelector.DAY);
            materialButton.setText(this.current.getLongName(inflate.getContext()));
            this.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
                    if (i == 0) {
                        recyclerView.announceForAccessibility(materialButton.getText());
                    }
                }

                public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    int i3;
                    if (i < 0) {
                        MaterialCalendar materialCalendar = MaterialCalendar.this;
                        Objects.requireNonNull(materialCalendar);
                        RecyclerView recyclerView2 = materialCalendar.recyclerView;
                        Objects.requireNonNull(recyclerView2);
                        i3 = ((LinearLayoutManager) recyclerView2.mLayout).findFirstVisibleItemPosition();
                    } else {
                        MaterialCalendar materialCalendar2 = MaterialCalendar.this;
                        Objects.requireNonNull(materialCalendar2);
                        RecyclerView recyclerView3 = materialCalendar2.recyclerView;
                        Objects.requireNonNull(recyclerView3);
                        i3 = ((LinearLayoutManager) recyclerView3.mLayout).findLastVisibleItemPosition();
                    }
                    MaterialCalendar materialCalendar3 = MaterialCalendar.this;
                    MonthsPagerAdapter monthsPagerAdapter = monthsPagerAdapter;
                    Objects.requireNonNull(monthsPagerAdapter);
                    CalendarConstraints calendarConstraints = monthsPagerAdapter.calendarConstraints;
                    Objects.requireNonNull(calendarConstraints);
                    Month month = calendarConstraints.start;
                    Objects.requireNonNull(month);
                    Calendar dayCopy = UtcDates.getDayCopy(month.firstOfMonth);
                    dayCopy.add(2, i3);
                    materialCalendar3.current = new Month(dayCopy);
                    MaterialButton materialButton = materialButton;
                    MonthsPagerAdapter monthsPagerAdapter2 = monthsPagerAdapter;
                    Objects.requireNonNull(monthsPagerAdapter2);
                    CalendarConstraints calendarConstraints2 = monthsPagerAdapter2.calendarConstraints;
                    Objects.requireNonNull(calendarConstraints2);
                    Month month2 = calendarConstraints2.start;
                    Objects.requireNonNull(month2);
                    Calendar dayCopy2 = UtcDates.getDayCopy(month2.firstOfMonth);
                    dayCopy2.add(2, i3);
                    materialButton.setText(new Month(dayCopy2).getLongName(monthsPagerAdapter2.context));
                }
            });
            materialButton.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    Objects.requireNonNull(materialCalendar);
                    CalendarSelector calendarSelector = CalendarSelector.DAY;
                    CalendarSelector calendarSelector2 = materialCalendar.calendarSelector;
                    CalendarSelector calendarSelector3 = CalendarSelector.YEAR;
                    if (calendarSelector2 == calendarSelector3) {
                        materialCalendar.setSelector(calendarSelector);
                    } else if (calendarSelector2 == calendarSelector) {
                        materialCalendar.setSelector(calendarSelector3);
                    }
                }
            });
            materialButton3.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    Objects.requireNonNull(materialCalendar);
                    RecyclerView recyclerView = materialCalendar.recyclerView;
                    Objects.requireNonNull(recyclerView);
                    int findFirstVisibleItemPosition = ((LinearLayoutManager) recyclerView.mLayout).findFirstVisibleItemPosition() + 1;
                    RecyclerView recyclerView2 = MaterialCalendar.this.recyclerView;
                    Objects.requireNonNull(recyclerView2);
                    if (findFirstVisibleItemPosition < recyclerView2.mAdapter.getItemCount()) {
                        MaterialCalendar materialCalendar2 = MaterialCalendar.this;
                        MonthsPagerAdapter monthsPagerAdapter = monthsPagerAdapter;
                        Objects.requireNonNull(monthsPagerAdapter);
                        CalendarConstraints calendarConstraints = monthsPagerAdapter.calendarConstraints;
                        Objects.requireNonNull(calendarConstraints);
                        Month month = calendarConstraints.start;
                        Objects.requireNonNull(month);
                        Calendar dayCopy = UtcDates.getDayCopy(month.firstOfMonth);
                        dayCopy.add(2, findFirstVisibleItemPosition);
                        materialCalendar2.setCurrentMonth(new Month(dayCopy));
                    }
                }
            });
            materialButton2.setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    MaterialCalendar materialCalendar = MaterialCalendar.this;
                    Objects.requireNonNull(materialCalendar);
                    RecyclerView recyclerView = materialCalendar.recyclerView;
                    Objects.requireNonNull(recyclerView);
                    int findLastVisibleItemPosition = ((LinearLayoutManager) recyclerView.mLayout).findLastVisibleItemPosition() - 1;
                    if (findLastVisibleItemPosition >= 0) {
                        MaterialCalendar materialCalendar2 = MaterialCalendar.this;
                        MonthsPagerAdapter monthsPagerAdapter = monthsPagerAdapter;
                        Objects.requireNonNull(monthsPagerAdapter);
                        CalendarConstraints calendarConstraints = monthsPagerAdapter.calendarConstraints;
                        Objects.requireNonNull(calendarConstraints);
                        Month month = calendarConstraints.start;
                        Objects.requireNonNull(month);
                        Calendar dayCopy = UtcDates.getDayCopy(month.firstOfMonth);
                        dayCopy.add(2, findLastVisibleItemPosition);
                        materialCalendar2.setCurrentMonth(new Month(dayCopy));
                    }
                }
            });
        }
        if (!MaterialDatePicker.isFullscreen(contextThemeWrapper)) {
            new PagerSnapHelper().attachToRecyclerView(this.recyclerView);
        }
        RecyclerView recyclerView3 = this.recyclerView;
        Month month2 = this.current;
        CalendarConstraints calendarConstraints3 = monthsPagerAdapter.calendarConstraints;
        Objects.requireNonNull(calendarConstraints3);
        Month month3 = calendarConstraints3.start;
        Objects.requireNonNull(month3);
        if (month3.firstOfMonth instanceof GregorianCalendar) {
            recyclerView3.scrollToPosition((month2.month - month3.month) + ((month2.year - month3.year) * 12));
            return inflate;
        }
        throw new IllegalArgumentException("Only Gregorian calendars are supported.");
    }

    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("THEME_RES_ID_KEY", this.themeResId);
        bundle.putParcelable("GRID_SELECTOR_KEY", this.dateSelector);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.calendarConstraints);
        bundle.putParcelable("CURRENT_MONTH_KEY", this.current);
    }

    public final void setCurrentMonth(Month month) {
        boolean z;
        RecyclerView recyclerView2 = this.recyclerView;
        Objects.requireNonNull(recyclerView2);
        MonthsPagerAdapter monthsPagerAdapter = (MonthsPagerAdapter) recyclerView2.mAdapter;
        Objects.requireNonNull(monthsPagerAdapter);
        CalendarConstraints calendarConstraints2 = monthsPagerAdapter.calendarConstraints;
        Objects.requireNonNull(calendarConstraints2);
        Month month2 = calendarConstraints2.start;
        Objects.requireNonNull(month2);
        if (month2.firstOfMonth instanceof GregorianCalendar) {
            final int i = (month.month - month2.month) + ((month.year - month2.year) * 12);
            Month month3 = this.current;
            CalendarConstraints calendarConstraints3 = monthsPagerAdapter.calendarConstraints;
            Objects.requireNonNull(calendarConstraints3);
            Month month4 = calendarConstraints3.start;
            Objects.requireNonNull(month4);
            if (month4.firstOfMonth instanceof GregorianCalendar) {
                int i2 = i - ((month3.month - month4.month) + ((month3.year - month4.year) * 12));
                boolean z2 = true;
                if (Math.abs(i2) > 3) {
                    z = true;
                } else {
                    z = false;
                }
                if (i2 <= 0) {
                    z2 = false;
                }
                this.current = month;
                if (z && z2) {
                    this.recyclerView.scrollToPosition(i - 3);
                    this.recyclerView.post(new Runnable() {
                        public final void run() {
                            MaterialCalendar.this.recyclerView.smoothScrollToPosition(i);
                        }
                    });
                } else if (z) {
                    this.recyclerView.scrollToPosition(i + 3);
                    this.recyclerView.post(new Runnable() {
                        public final void run() {
                            MaterialCalendar.this.recyclerView.smoothScrollToPosition(i);
                        }
                    });
                } else {
                    this.recyclerView.post(new Runnable() {
                        public final void run() {
                            MaterialCalendar.this.recyclerView.smoothScrollToPosition(i);
                        }
                    });
                }
            } else {
                throw new IllegalArgumentException("Only Gregorian calendars are supported.");
            }
        } else {
            throw new IllegalArgumentException("Only Gregorian calendars are supported.");
        }
    }

    public final void setSelector(CalendarSelector calendarSelector2) {
        this.calendarSelector = calendarSelector2;
        if (calendarSelector2 == CalendarSelector.YEAR) {
            RecyclerView recyclerView2 = this.yearSelector;
            Objects.requireNonNull(recyclerView2);
            RecyclerView.LayoutManager layoutManager = recyclerView2.mLayout;
            RecyclerView recyclerView3 = this.yearSelector;
            Objects.requireNonNull(recyclerView3);
            YearGridAdapter yearGridAdapter = (YearGridAdapter) recyclerView3.mAdapter;
            int i = this.current.year;
            Objects.requireNonNull(yearGridAdapter);
            MaterialCalendar<?> materialCalendar = yearGridAdapter.materialCalendar;
            Objects.requireNonNull(materialCalendar);
            CalendarConstraints calendarConstraints2 = materialCalendar.calendarConstraints;
            Objects.requireNonNull(calendarConstraints2);
            layoutManager.scrollToPosition(i - calendarConstraints2.start.year);
            this.yearFrame.setVisibility(0);
            this.dayFrame.setVisibility(8);
        } else if (calendarSelector2 == CalendarSelector.DAY) {
            this.yearFrame.setVisibility(8);
            this.dayFrame.setVisibility(0);
            setCurrentMonth(this.current);
        }
    }

    public final boolean addOnSelectionChangedListener(MaterialDatePicker.C20063 r1) {
        return super.addOnSelectionChangedListener(r1);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            bundle = this.mArguments;
        }
        this.themeResId = bundle.getInt("THEME_RES_ID_KEY");
        this.dateSelector = (DateSelector) bundle.getParcelable("GRID_SELECTOR_KEY");
        this.calendarConstraints = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
        this.current = (Month) bundle.getParcelable("CURRENT_MONTH_KEY");
    }
}
