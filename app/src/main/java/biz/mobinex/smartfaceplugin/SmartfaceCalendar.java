package biz.mobinex.smartfaceplugin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import caldroid.CaldroidFragment;
import caldroid.CaldroidListener;
import io.smartface.android.AndroidUI.WithGeometry;
import io.smartface.plugin.SMFJSObject;

/**
 * Calendar plugin for Smartface.
 * Based on Caldroid project.
 *
 * @author Olcay Ertaş.
 * @version 1.0
 * @see "https://github.com/roomorama/Caldroid"
 * @since 22.01.2016
 */
public class SmartfaceCalendar extends AbsoluteLayout implements WithGeometry {

    /**
     * Date template for parsing date.
     */
    public static final String DATE_TEMPLATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Log tag.
     */
    public static final String TAG = "SmartfaceCalendar";

    /**
     * Key for arguments
     */
    public static final String KEY_NEW_MONTH = "newMonth";

    /**
     * Key for arguments
     */
    public static final String KEY_OLD_MONTH = "oldMonth";

    /**
     * SMFJ object reference.
     * Will be initialized by Smartface.
     */
    public static SMFJSObject smfJSClass = null;

    // Event Listeners //

    /**
     * Javascript on touch callback.
     */
    private SMFJSObject onTouch;

    /**
     * Javascript on touch end callback.
     */
    private SMFJSObject onTouchEnd;

    /**
     * Javascript on show callback.
     */
    private SMFJSObject onShow;

    /**
     * Javascript on hide callback.
     */
    private SMFJSObject onHide;

    /**
     * Touch enable value of javaCalendar view.
     */
    private boolean touchEnabled = true;

    /**
     * Swipe enable value of javaCalendar view.
     */
    private boolean swipeEnabled = true;

    /**
     * Selected dates beginning.
     * Must be in ISO format.
     * Default is null;
     */
    public String selectedFromDate;

    /**
     * Selected dates ending.
     * Must be in ISO format.
     * Default is null;
     */
    public String selectedToDate;

    /**
     * Selected dates array.
     * Must be a JSON array string.
     * Default is null;
     */
    public List<String> selectedDates;

    /**
     * Disabled dates beginning.
     * Must be in ISO format.
     * Default is null;
     */
    public String disabledFromDate;

    /**
     * Disabled dates ending.
     * Must be in ISO format.
     * Default is null;
     */
    public String disabledToDate;

    /**
     * Disabled dates array.
     * Default is null;
     */
    public String[] disabledDates;

    /**
     * Javascript callback for on date selected callback.
     * Used in Caldroid listener.
     */
    private SMFJSObject onDateSelected = null;

    /**
     * Javascript callback for on long pressed callback.
     */
    private SMFJSObject onLongPressed;

    /**
     * Javascript callback for on month changed callback.
     */
    private SMFJSObject onMonthChanged;

    /**
     * Reference to instance.
     * To use in async methods.
     */
    private SmartfaceCalendar smartfaceCalendar = this;

    /**
     * Caldroid instance.
     * This is the calender itself.
     */
    private CaldroidFragment caldroidFragment;

    /**
     * Title of the javaCalendar dialog.
     */
    private String title;

    /**
     * To get SupportFragmentManager.
     */
    private AppCompatActivity activity;

    /**
     * Month.
     */
    private int month;

    /**
     * Year.
     */
    private int year;

    /**
     * Calendar width.
     */
    private int width;

    /**
     * Calendar height
     */
    private int height;

    /**
     *
     */
    private int calendarTop;

    /**
     *
     */
    private int calendarLeft;

    /**
     * Caldroid listener.
     */
    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {
            onDateSelected(date);
        }

        @Override
        public void onChangeMonth(int month, int year) {
            smartfaceCalendar.onChangeMonth(month, year);
            //caldroidFragment.refreshView();
        }

        @Override
        public void onLongClickDate(Date date, View view) {
            smartfaceCalendar.onLongClickDate(date);
        }

        @Override
        public void onCaldroidViewCreated() {
            try {
                if (previousImage.contains(".")) {
                    previousImage = previousImage.substring(0, previousImage.indexOf("."));
                }
                Resources resources = appCompatActivity.getResources();
                String packageName = appCompatActivity.getPackageName();
                int drawableId = resources.getIdentifier(previousImage, "drawable", packageName);
                caldroidFragment.getLeftArrowButton().setBackgroundResource(drawableId);
                if (nextImage.contains(".")) {
                    nextImage = nextImage.substring(0, nextImage.indexOf("."));
                }
                drawableId = resources.getIdentifier(nextImage, "drawable", packageName);
                caldroidFragment.getRightArrowButton().setBackgroundResource(drawableId);
                caldroidFragment.refreshView();
            } catch (Exception e) {
                Log.e(TAG, "setPreviousImage - Failure to get drawable id.");
                e.printStackTrace();
            }
        }
    };

    /**
     * Background color. Default is white.
     */
    private String fillColor = "#FFFFFFFF";

    /**
     * Right arrow.
     */
    private String nextImage;
    /**
     * Left arrow.
     */
    private String previousImage;

    /**
     * Locale of the calender.
     */
    private String localization;

    /**
     * Minimum date to show.
     */
    private String minDate;

    /**
     * Maximum date to show.
     */
    private String maxDate;

    /**
     * Calendar params
     */
    private Bundle arguments = new Bundle();

    /**
     * Container actiivity
     */
    private AppCompatActivity appCompatActivity;

    /**
     * Constructor for use in Smartface.
     *
     * @param appCompatActivity Activity.
     * @param width             Calendar width.
     * @param height            Calendar height.
     * @param calendarLeft      Calendar calendarLeft position.
     * @param calendarTop       Calendar calendarTop position.
     */
    public SmartfaceCalendar(AppCompatActivity appCompatActivity, int width, int height, int calendarLeft, int calendarTop) {
        super(appCompatActivity.getApplicationContext());
        this.appCompatActivity = appCompatActivity;
        setId(R.id.caldroid_container);
        this.width = width;
        this.height = height;
        this.calendarTop = calendarTop;
        this.calendarLeft = calendarLeft;
        LayoutParams params = new LayoutParams(width, height, calendarLeft, calendarTop);
        setLayoutParams(params);
        configureOnShowAndOnHideCallbacks();
    }

    /**
     * Constructor for use in Smartface.
     *
     * @param appCompatActivity Parent activity.
     */
    public SmartfaceCalendar(AppCompatActivity appCompatActivity) {
        super(appCompatActivity.getApplicationContext());
        this.appCompatActivity = appCompatActivity;
        setBackgroundColor(Color.BLACK);
        setId(R.id.caldroid_container);
        LayoutParams params = new LayoutParams(
                AbsoluteLayout.LayoutParams.MATCH_PARENT,
                AbsoluteLayout.LayoutParams.WRAP_CONTENT, 0, 0);
        setLayoutParams(params);
        configureOnShowAndOnHideCallbacks();
    }

    /**
     * Configures onShow and onHide callbacks
     */
    private void configureOnShowAndOnHideCallbacks() {
        setOnSystemUiVisibilityChangeListener(new OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                try {
                    if (visibility == VISIBLE) {
                        if (onShow != null) onShow.callAsFunction(onShow, null);
                    } else {
                        if (onHide != null) onHide.callAsFunction(onShow, null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sets calendar theme.
     *
     * @param theme Theme resource id.
     */
    public void setTheme(int theme) {
        arguments.putInt(CaldroidFragment.THEME_RESOURCE, theme);
    }

    /**
     * Creates Caldroid instanse and sets layout params when this view attached to any window.
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int firstDayOfWeek = java.util.Calendar.getInstance().getFirstDayOfWeek();
        arguments.putInt(CaldroidFragment.START_DAY_OF_WEEK, firstDayOfWeek);
        caldroidFragment = new CaldroidFragment();
        caldroidFragment.setArguments(arguments);
        caldroidFragment.setCaldroidListener(listener);
        FragmentManager fm = appCompatActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.caldroid_container, caldroidFragment).commitAllowingStateLoss();
    }

    /**
     * Default constructor.
     *
     * @param context Context.
     */
    private SmartfaceCalendar(Context context) {
        super(context);
    }

    /**
     * Constructor with attributes.
     *
     * @param context Context.
     * @param attrs   Attributes.
     */
    private SmartfaceCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor with attributes and style.
     *
     * @param context      Context.
     * @param attrs        Attributes.
     * @param defStyleAttr Style.
     */
    private SmartfaceCalendar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Gives dates between two dates.
     *
     * @param startDate Start date.
     * @param endDate   End date.
     * @return Returns the list of dates between two dates.
     */
    private static ArrayList<Date> getDaysBetweenDates(Date startDate, Date endDate) {
        ArrayList<Date> dates = new ArrayList<>();
        java.util.Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        while (calendar.getTime().before(endDate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(java.util.Calendar.DATE, 1);
        }
        return dates;
    }

    /**
     * Setter for calendarTop.
     *
     * @param calendarTop Top coordinate of calendar.
     */
    public void setCalendarTop(int calendarTop) {
        this.calendarTop = calendarTop;
        LayoutParams params = new LayoutParams(width, height, calendarLeft, calendarTop);
        setLayoutParams(params);
        caldroidFragment.refreshView();
    }

    /**
     * Setter for calendarLeft.
     *
     * @param calendarLeft Left coordinate of calendar.
     */
    public void setCalendarLeft(int calendarLeft) {
        this.calendarLeft = calendarLeft;
        LayoutParams params = new LayoutParams(width, height, calendarLeft, calendarTop);
        setLayoutParams(params);
        caldroidFragment.refreshView();
    }

    /**
     * Setter for calendar width.
     *
     * @param width Width of calendar.
     */
    public void setCalendarWidth(int width) {
        this.width = width;
        LayoutParams params = new LayoutParams(width, height, calendarLeft, calendarTop);
        setLayoutParams(params);
        caldroidFragment.refreshView();
    }

    /**
     * Setter for calendar height.
     *
     * @param height Calendar height.
     */
    public void setCalendarHeight(int height) {
        this.height = height;
        LayoutParams params = new LayoutParams(width, height, calendarLeft, calendarTop);
        setLayoutParams(params);
        caldroidFragment.refreshView();
    }

    /**
     * Getter for on date selected listener.
     *
     * @return Returns on date selected listener.
     */
    public SMFJSObject getOnDateSelected() {
        return onDateSelected;
    }

    /**
     * Setter for on date selected listener.
     *
     * @param onDateSelected On date selected listener.
     */
    public void setOnDateSelected(SMFJSObject onDateSelected) {
        this.onDateSelected = onDateSelected;
    }

    /**
     * Getter for on date long pressed listener.
     *
     * @return Returns on date long pressed listener.
     */
    private SMFJSObject getOnLongPressed() {
        return onLongPressed;
    }

    /**
     * Setter for on date long pressed.
     *
     * @param onLongPressed On date long pressed listener.
     */
    public void setOnLongPressed(SMFJSObject onLongPressed) {
        this.onLongPressed = onLongPressed;
    }

    /**
     * Getter for on month changed listener.
     *
     * @return Returns on month changed listener.
     */
    public SMFJSObject getOnMonthChanged() {
        return onMonthChanged;
    }

    /**
     * Setter for on month changed listener.
     *
     * @param onMonthChanged On month changed listener.
     */
    public void setOnMonthChanged(SMFJSObject onMonthChanged) {
        this.onMonthChanged = onMonthChanged;
    }

    /**
     * Getter for on touch listener.
     *
     * @return Returns on touch listener.
     */
    public SMFJSObject getOnTouch() {
        return onTouch;
    }

    /**
     * Setter for on touch listener.
     *
     * @param onTouch On touch listener.
     */
    public void setOnTouch(SMFJSObject onTouch) {
        this.onTouch = onTouch;
    }

    /**
     * Getter for on touch end listener.
     *
     * @return Returns on touch end listener.
     */
    public SMFJSObject getOnTouchEnd() {
        return onTouchEnd;
    }

    /**
     * Setter for on touch end listener.
     *
     * @param onTouchEnd On touch end listener.
     */
    public void setOnTouchEnd(SMFJSObject onTouchEnd) {
        this.onTouchEnd = onTouchEnd;
    }

    /**
     * Getter for on show listener.
     *
     * @return Returns on show listener.
     */
    public SMFJSObject getOnShow() {
        return onShow;
    }

    /**
     * Setter for on show listener.
     *
     * @param onShow On show listener.
     */
    public void setOnShow(SMFJSObject onShow) {
        this.onShow = onShow;
    }

    /**
     * Getter for on hide listener.
     *
     * @return On hide listener.
     */
    public SMFJSObject getOnHide() {
        return onHide;
    }

    /**
     * Setter for on hide listener.
     *
     * @param onHide On hide listener.
     */
    public void setOnHide(SMFJSObject onHide) {
        this.onHide = onHide;
    }

    /**
     * Getter for swipe enable value of javaCalendar view.
     *
     * @return Returns swipe enable value.
     */
    public boolean isSwipeEnabled() {
        return swipeEnabled;
    }

    /**
     * Setter for javaCalendar swipe enable value.
     *
     * @param swipeEnabled Swipe enable value.
     */
    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
        Bundle arguments = caldroidFragment.getArguments();
        if (arguments == null) arguments = new Bundle();
        arguments.putBoolean(CaldroidFragment.ENABLE_SWIPE, swipeEnabled);
        arguments.putInt(CaldroidFragment.MONTH, caldroidFragment.getMonth());
        arguments.putInt(CaldroidFragment.YEAR, caldroidFragment.getYear());
        FragmentManager fm = appCompatActivity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(caldroidFragment).commitAllowingStateLoss();
        caldroidFragment = new CaldroidFragment();
        caldroidFragment.setArguments(arguments);
        caldroidFragment.setCaldroidListener(listener);
        ft = fm.beginTransaction();
        ft.replace(R.id.caldroid_container, caldroidFragment).commitAllowingStateLoss();
    }

    /**
     * Getter for javaCalendar dialog title.
     *
     * @return Returns javaCalendar dialog title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for javaCalendar dialog title.
     *
     * @param title Calendar dialog title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for month.
     *
     * @return Returns month of the javaCalendar.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Setter for month.
     *
     * @param month Month.
     */
    public void setMonth(int month) {
        this.month = month;
        Bundle args = caldroidFragment.getArguments();
        args.putInt(CaldroidFragment.MONTH, month);
        caldroidFragment.setArguments(args);
        caldroidFragment.refreshView();
    }

    /**
     * Getter for year.
     *
     * @return Returns year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Setter for year.
     *
     * @param year Year.
     */
    public void setYear(int year) {
        this.year = year;
        Bundle args = caldroidFragment.getArguments();
        args.putInt(CaldroidFragment.YEAR, year);
        caldroidFragment.setArguments(args);
        caldroidFragment.refreshView();

    }

    /**
     * Getter for fill color of the javaCalendar background.
     *
     * @return Returns background color of the javaCalendar.
     */
    public String getFillColor() {
        return fillColor;
    }

    /**
     * Setter for fill color of the javaCalendar background.
     *
     * @param fillColor Background color of the javaCalendar.
     */
    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
        View calendarView = caldroidFragment.getView();

        if (calendarView != null)
            calendarView.setBackgroundColor(Color.parseColor(fillColor));

        caldroidFragment.refreshView();
    }

    /**
     * Getter for name of the next month button image.
     *
     * @return Name of the next month button image.
     */
    public String getNextImage() {
        return nextImage;
    }

    /**
     * Sets right arrow image name.
     *
     * @param nextImage Image name.
     */
    public void setNextImage(String nextImage) {
        this.nextImage = nextImage;
    }

    /**
     * Getter for selected dates beginning.
     *
     * @return Returns beginning date of the selected dates as ISO date string.
     */
    public String getSelectedFromDate() {
        return selectedFromDate;
    }

    /**
     * Setter for beginning of the selected dates.
     *
     * @param selectedFromDate Beginning of the selected dates as ISO date string.
     * @param format           Date format to parse selected date. Must be same format with selectedToDate.
     */
    public void setSelectedFromDate(String selectedFromDate, String format) {
        this.selectedFromDate = selectedFromDate;

        if (selectedToDate != null) {
            setSelectedDates(selectedFromDate, selectedToDate, format);
        }
    }

    /**
     * Getter for selected dates ending.
     *
     * @return Returns selected dates ending as ISO date string.
     */
    public String getSelectedToDate() {
        return selectedToDate;
    }

    /**
     * Setter for selected dates ending.
     *
     * @param selectedToDate Selected dates ending as ISO date string.
     * @param format         Date format to parse selected date. Must be same format with selectedFromDate.
     */
    public void setSelectedToDate(String selectedToDate, String format) {
        this.selectedToDate = selectedToDate;

        if (selectedFromDate != null) {
            setSelectedDates(selectedFromDate, selectedFromDate, format);
        }
    }

    /**
     * Getter for selected dates.
     *
     * @return Returns selected dates as JSON array string.
     * All dates in array are ISO date strings.
     */
    public String[] getSelectedDates() {
        return (String[]) selectedDates.toArray();
    }

    /**
     * Setter for selected dates.
     *
     * @param fromDate Beginning of the selected dates.
     * @param toDate   Selected dates ending.
     */
    public void setSelectedDates(String fromDate, String toDate, String format) {
        this.selectedFromDate = fromDate;
        this.selectedToDate = toDate;
        SimpleDateFormat dateFormat =
                new SimpleDateFormat(
                        format, Locale.getDefault());
        try {
            caldroidFragment.setSelectedDates(
                    dateFormat.parse(fromDate),
                    dateFormat.parse(toDate));
            caldroidFragment.refreshView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for disabled dates start date.
     *
     * @return Returns start date of disabled dates.
     */
    public String getDisabledFromDate() {
        return disabledFromDate;
    }

    /**
     * Setter for start date of disabled dates.
     *
     * @param disabledFromDate Start date of disabled dates.
     */
    public void setDisabledFromDate(String disabledFromDate, String format) {
        this.disabledFromDate = disabledFromDate;

        if (disabledToDate != null) {
            setDisabledDates(format);
        }
    }

    /**
     * Getter for ending date of disabled dates.
     *
     * @return Returns ending date of disabled dates.
     */
    public String getDisabledToDate() {
        return disabledToDate;
    }

    /**
     * Setter for ending date of disabled dates.
     *
     * @param disabledToDate Ending date of disabled dates.
     */
    public void setDisabledToDate(String disabledToDate, String format) {
        this.disabledToDate = disabledToDate;

        if (disabledFromDate != null) {
            setDisabledDates(format);
        }
    }

    /**
     * Sets disabled dates between disabledFromDate and disabledToDate.
     */
    private void setDisabledDates(String format) {
        SimpleDateFormat isoDateFormat =
                new SimpleDateFormat(
                        format,
                        Locale.getDefault());
        try {
            Date fromDate = isoDateFormat.parse(disabledFromDate);
            Date toDate = isoDateFormat.parse(disabledToDate);
            caldroidFragment.setDisableDates(getDaysBetweenDates(fromDate, toDate));
            caldroidFragment.refreshView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for disabled dates.
     *
     * @return Returns disabled dates as String[].
     */
    public String[] getDisabledDates() {
        return disabledDates;
    }

    /**
     * Setter for disabled dates.
     *
     * @param disabledDates Disabled dates array.
     */
    public void setDisabledDates(String[] disabledDates, String format) {
        this.disabledDates = disabledDates;
        ArrayList<String> datesAsString = new ArrayList<>();
        datesAsString.addAll(Arrays.asList(disabledDates));

        SimpleDateFormat isoDateFormat =
                new SimpleDateFormat(
                        format, Locale.getDefault());

        ArrayList<Date> dates = new ArrayList<>();

        for (String dateString : datesAsString) {
            try {
                dates.add(isoDateFormat.parse(dateString));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        caldroidFragment.setDisableDates(dates);
    }

    /**
     * Sets absolute layout params.
     *
     * @param width  Calendar width.
     * @param height Calendar height.
     * @param x      Calendar calendarLeft position.
     * @param y      Calendar calendarTop position.
     */
    public void setLayoutParams(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.calendarLeft = x;
        this.calendarTop = y;
        LayoutParams params = new LayoutParams(width, height, x, y);
        super.setLayoutParams(params);
    }

    /**
     * Getter for activity.
     *
     * @return Returns the activity which this javaCalendar added.
     */
    public AppCompatActivity getActivity() {
        return activity;
    }

    /**
     * Setter for activity.
     *
     * @param activity The activity this javaCalendar will be added.
     */
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Checks if touch enabled.
     *
     * @return Returns true if touch enabled else returns false.
     */
    public boolean isTouchEnabled() {
        return touchEnabled;
    }

    /**
     * Sets touch enabled status.
     *
     * @param touchEnabled Touch enabled value.
     */
    public void setTouchEnabled(boolean touchEnabled) {
        this.touchEnabled = touchEnabled;
    }

    /**
     * Enable or disable touch based on touchEnabled value.
     *
     * @param ev Motion event.
     * @return Returns negate of touchEnabled.
     */
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return !touchEnabled;
    }

    /**
     * Getter for localization.
     *
     * @return Returns localization string.
     */
    public String getLocalization() {
        return localization;
    }

    /**
     * Setter for localization.
     *
     * @param localization Localization string.
     */
    public void setLocalization(String localization) {
        this.localization = localization;
        if (caldroidFragment != null)
            caldroidFragment.setLocale(localization);
    }

    /**
     * Getter for minimum date.
     *
     * @return Returns minimum date.
     */
    public String getMinDate() {
        return minDate;
    }

    /**
     * Sets start date of the javaCalendar.
     *
     * @param minDateAsMilliSeconds Start date of the javaCalendar as milli seconds.
     */
    public void setMinDate(long minDateAsMilliSeconds) {
        Date minDate = new Date(minDateAsMilliSeconds);
        this.minDate = minDate.toString();
        caldroidFragment.setMinDate(minDate);
        caldroidFragment.refreshView();
    }

    /**
     * Sets start date of the javaCalendar.
     *
     * @param minDate Start date of the javaCalendar.
     */
    public void setMinDate(String minDate, String template) {
        if (minDate == null) return;
        this.minDate = minDate;
        /*"EEE MMM dd HH:mm:ss zzz yyyy"*/
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.getDefault());
        try {
            caldroidFragment.setMinDate(format.parse(minDate));
            caldroidFragment.refreshView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter for maximum date.
     *
     * @return Maximum date.
     */
    public String getMaxDate() {
        return maxDate;
    }

    /**
     * Sets end date of the javaCalendar.
     *
     * @param maxDateMilliSeconds End date of the javaCalendar as milli seconds.
     */
    public void setMaxDate(long maxDateMilliSeconds) {
        Date maxDate = new Date(maxDateMilliSeconds);
        this.maxDate = maxDate.toString();
        caldroidFragment.setMaxDate(maxDate);
        caldroidFragment.refreshView();
    }

    /**
     * Sets end date of the javaCalendar.
     *
     * @param maxDate End date of the javaCalendar.
     */
    public void setMaxDate(String maxDate, String template) {
        if (maxDate == null) return;
        this.maxDate = maxDate;
        SimpleDateFormat format = new SimpleDateFormat(template, Locale.getDefault());
        try {
            caldroidFragment.setMaxDate(format.parse(maxDate));
            caldroidFragment.refreshView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows javaCalendar as a dialog.
     */
    public void showCalendarDialog() {
        caldroidFragment.setCaldroidListener(listener);
        caldroidFragment.show(activity.getSupportFragmentManager(), TAG);
    }

    /**
     * @return Returns calendarLeft arrow resource name.
     */
    public String getPreviousImage() {
        return previousImage;
    }

    /**
     * Sets calendarLeft arrow image name.
     *
     * @param previousImage Image name.
     */
    public void setPreviousImage(String previousImage) {
        this.previousImage = previousImage;
    }

    /**
     * Called when onDateSelected event happens.
     *
     * @param date Selected date.
     */
    private void onDateSelected(Date date) {
        try {
            if (onDateSelected != null) {
                JSONObject dateJson = new JSONObject();
                dateJson.put("date", date);
                SMFJSObject[] arguments = new SMFJSObject[1];
                arguments[0] = new SMFJSObject(dateJson);
                Log.w(TAG, "CaldroidListener - onDateSelected : " + date.toString());
                onDateSelected.callAsFunction(null, arguments);
            } else {
                Log.w(TAG, "CaldroidListener: onDateSelected listener is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when month changed.
     *
     * @param month Month.
     * @param year  Year.
     */
    private void onChangeMonth(int month, int year) {
        try {
            if (onMonthChanged != null) {
                JSONObject dateJson = new JSONObject();
                dateJson.put(KEY_NEW_MONTH, month);
                dateJson.put(KEY_OLD_MONTH, smartfaceCalendar.month);
                smartfaceCalendar.month = month;
                SMFJSObject[] arguments = new SMFJSObject[1];
                arguments[0] = new SMFJSObject(dateJson);
                Log.w(TAG, String.format(Locale.getDefault(), "CaldroidListener - onLongClickDate : %d - %d", month, year));
                onMonthChanged.callAsFunction(null, arguments);
            } else {
                Log.w(TAG, "CaldroidListener: onChangeMonth listener is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when long clicked on a date.
     *
     * @param date Long clicked date.
     */
    private void onLongClickDate(Date date) {
        try {
            if (onLongPressed != null) {
                JSONObject dateJson = new JSONObject();
                dateJson.put("date", date);
                SMFJSObject[] arguments = new SMFJSObject[1];
                arguments[0] = new SMFJSObject(dateJson);
                Log.w(TAG, "CaldroidListener - onLongClickDate : " + date.toString());
                onLongPressed.callAsFunction(null, arguments);
            } else {
                Log.w(TAG, "CaldroidListener: onLongClickDate listener is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when action down happens.
     */
    private void onActionDown() {
        try {
            if (onTouch != null) {
                Log.w(TAG, "CaldroidListener - onTouch");
                onTouch.callAsFunction(null, null);
            } else {
                Log.w(TAG, "CaldroidListener: onTouch listener is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when action up happens.
     */
    private void onActionUp() {
        try {
            if (onTouchEnd != null) {
                Log.w(TAG, "CaldroidListener - onTouchEnd");
                onTouchEnd.callAsFunction(null, null);
            } else {
                Log.w(TAG, "CaldroidListener: onTouchEnd listener is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when touch event happens on javaCalendar view.
     *
     * @param event Motion event.
     * @return Returns true if event is action down or action up else returns false.
     */
    private boolean onCalendarTouched(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                onActionDown();
                return true;
            case MotionEvent.ACTION_UP:
                onActionUp();
                return true;
        }

        return false;
    }

    /**
     * Called when javaCalendar view is visible.
     */
    private void onCalendarShow() {
        try {
            if (onShow != null) {
                Log.w(TAG, "CaldroidListener - onShow");
                onShow.callAsFunction(null, null);
            } else {
                Log.w(TAG, "CaldroidListener: onShow listener is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when javaCalendar view is not visible.
     */
    private void onCalendarHide() {
        try {
            if (onHide != null) {
                Log.w(TAG, "CaldroidListener - onHide");
                onHide.callAsFunction(null, null);
            } else {
                Log.w(TAG, "CaldroidListener: onHide listener is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes displayed month and selects today.
     */
    public void goToday() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        caldroidFragment.moveToDate(calendar.getTime());
    }

    /**
     * Sets start day of the week.
     *
     * @param startDayOfWeek Start day of week. Can be any day of Calendar class (Calendar.MONDAY).
     */
    public void setStartDayOfWeek(int startDayOfWeek) {
        Bundle args = new Bundle();
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, startDayOfWeek);
        caldroidFragment.setArguments(args);
    }

    // With geometry methods. Do not implement.
    public void setPosition__N(int left, int top, int width, int height, int right, int bottom) {
    }

    public void setVisible__N(boolean visible, boolean enabled, boolean showOnTop) {
    }

    public void setElevation__N(float elevation) {
    }

    public void resetZ__N(float alpha) {
    }
}
