
package biz.mobinex.smartfaceplugin;


import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.smartface.plugin.SMFJSObject;

/**
 * Caldroid wrapper.
 * Created by olcay on 4.01.2016.
 */
public class CalendarWrapper {

    public static final String DATE_TEMPLATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Log tag.
     */
    public static final String TAG = "CalendarWrapper";

    /**
     * SMFJ object reference.
     * Will be initialized by Smartface.
     */
    public static SMFJSObject smfJSClass = null;

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
     * Y value of the calendar View.
     */
    private int top;

    /**
     * X value of the calendar View.
     */
    private int left;

    /**
     * Setter for calendar view width.
     */
    private int width;

    /**
     * Calendar height.
     */
    private int height;

    /**
     * Touch enable value of calendar view.
     */
    private boolean touchEnabled;

    /**
     * Swipe enable value of calendar view.
     */
    private boolean swipeEnabled;

    /**
     * Reference to instance.
     * To use in async methods.
     */
    private CalendarWrapper calendarWrapper = this;

    /**
     * Caldroid instance.
     * This is the calender itself.
     */
    private CaldroidFragment caldroidFragment;

    /**
     * Title of the calendar dialog.
     */
    private String title;

    /**
     * To get SupportFragmentManager.
     */
    private AppCompatActivity activity;

    /**
     * Support fragment manager.
     */
    private FragmentManager fragmentManager;

    /**
     * Calendar instance.
     */
    private Calendar calendar;

    /**
     * Month.
     */
    private int month;

    /**
     * Year.
     */
    private int year;

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
     * Calendar on touch listener.
     */
    private View.OnTouchListener calendarOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return onCalendarTouched(event);
        }
    };
    /**
     * On show listener for calendar dialog.
     */
    private DialogInterface.OnShowListener onShowListener = new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialog) {
            onCalendarShow();
        }
    };
    /**
     * On dismiss listener for calendar dialog.
     */
    private DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {
        @Override
        public void onDismiss(DialogInterface dialog) {
            onCalendarHide();
        }
    };
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
            calendarWrapper.onChangeMonth(month, year);
        }

        @Override
        public void onLongClickDate(Date date, View view) {
            calendarWrapper.onLongClickDate(date);
        }

        @Override
        public void onCaldroidViewCreated() {
            onCalendarViewCreated();
        }
    };


    /**
     * Default constructor.
     */
    public CalendarWrapper(AppCompatActivity activity) {
        this.activity = activity;
        calendar = Calendar.getInstance(Locale.getDefault());
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        fragmentManager = activity.getSupportFragmentManager();
        caldroidFragment = new CaldroidFragment();
    }


    /**
     * Default constructor.
     */
    public CalendarWrapper(AppCompatActivity activity, String localization) {
        this.activity = activity;
        this.localization = localization;
        calendar = Calendar.getInstance(new Locale(localization));
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        fragmentManager = activity.getSupportFragmentManager();

        Bundle args = new Bundle();
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, firstDayOfWeek);

        caldroidFragment = new CaldroidFragment();
        caldroidFragment.setArguments(args);
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
     * Getter for calendar dialog title.
     *
     * @return Returns calendar dialog title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for calendar dialog title.
     *
     * @param title Calendar dialog title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for activity.
     *
     * @return Returns the activity which this calendar added.
     */
    public AppCompatActivity getActivity() {
        return activity;
    }

    /**
     * Setter for activity.
     *
     * @param activity The activity this calendar will be added.
     */
    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    /**
     * Getter for month.
     *
     * @return Returns month of the calendar.
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
     * Gives Y position of the calendar view.
     *
     * @return Returns Y position of the calendar view.
     */
    public int getTop() {
        return top;
    }

    /**
     * Sets Y position of the calendar view.
     *
     * @param top Y position of the calendar view.
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     * Getter for name of the previous month button image.
     *
     * @return Returns name of the previous month button image.
     */
    public int getLeft() {
        return left;
    }

    /**
     * X value of the calendar View.
     *
     * @param left Returns X position of the calendar view.
     */
    public void setLeft(int left) {
        this.left = left;
    }

    /**
     * Getter for calendar view width.
     *
     * @return Returns calendar view width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter for calendar view width.
     *
     * @param width Calendar view width.
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter for height of the calendar.
     *
     * @return Returns height of the calendar.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter for height of the calendar.
     *
     * @param height Height of the calendar.
     */
    public void setHeight(int height) {
        this.height = height;
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
     * Getter for swipe enable value of calendar view.
     *
     * @return Returns swipe enable value.
     */
    public boolean isSwipeEnabled() {
        return swipeEnabled;
    }

    /**
     * Setter for calendar swipe enable value.
     *
     * @param swipeEnabled Swipe enable value.
     */
    public void setSwipeEnabled(boolean swipeEnabled) {
        this.swipeEnabled = swipeEnabled;
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
        Calendar calendar = Calendar.getInstance(new Locale(localization));
        caldroidFragment.setCalendarDate(calendar.getTime());
        Bundle args = caldroidFragment.getArguments();
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        caldroidFragment.setArguments(args);
        caldroidFragment.refreshView();
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
     * Sets start date of the calendar.
     *
     * @param minDate Start date of the calendar.
     */
    public void setMinDate(String minDate) {
        if (minDate == null) return;
        this.minDate = minDate;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TEMPLATE, Locale.getDefault());
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
     * Sets end date of the calendar.
     *
     * @param maxDate End date of the calendar.
     */
    public void setMaxDate(String maxDate) {
        if (maxDate == null) return;
        this.maxDate = maxDate;
        SimpleDateFormat format = new SimpleDateFormat(DATE_TEMPLATE, Locale.getDefault());
        try {
            caldroidFragment.setMaxDate(format.parse(maxDate));
            caldroidFragment.refreshView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows calendar as a dialog.
     */
    public void showCalendarDialog() {
        caldroidFragment.setCaldroidListener(listener);
        caldroidFragment.show(fragmentManager, TAG);
    }


    /**
     * Getter for fill color of the calendar background.
     *
     * @return Returns background color of the calendar.
     */
    public String getFillColor() {
        return fillColor;
    }


    /**
     * Setter for fill color of the calendar background.
     *
     * @param fillColor Background color of the calendar.
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
        try {
            if (nextImage.contains(".")) {
                nextImage = nextImage.substring(0, nextImage.indexOf("."));
            }
            Resources resources = activity.getResources();
            String packageName = activity.getPackageName();
            int drawableId = resources.getIdentifier(nextImage, "drawable", packageName);
            caldroidFragment.getRightArrowButton().setBackgroundResource(drawableId);
            caldroidFragment.refreshView();
        } catch (Exception e) {
            Log.e(TAG, "setNextImage - Failure to get drawable id.");
            e.printStackTrace();
        }
    }


    /**
     * @return Returns left arrow resource name.
     */
    public String getPreviousImage() {
        return previousImage;
    }


    /**
     * Sets left arrow image name.
     *
     * @param previousImage Image name.
     */
    public void setPreviousImage(String previousImage) {
        this.previousImage = previousImage;
        try {
            if (previousImage.contains(".")) {
                previousImage = previousImage.substring(0, previousImage.indexOf("."));
            }
            Resources resources = activity.getResources();
            String packageName = activity.getPackageName();
            int drawableId = resources.getIdentifier(previousImage, "drawable", packageName);
            caldroidFragment.getLeftArrowButton().setBackgroundResource(drawableId);
            caldroidFragment.refreshView();
        } catch (Exception e) {
            Log.e(TAG, "setPreviousImage - Failure to get drawable id.");
            e.printStackTrace();
        }
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
                dateJson.put("newMonth", month);
                dateJson.put("oldMonth", calendarWrapper.month);
                calendarWrapper.month = month;
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
     * Called when calendar view crated event.
     */
    private void onCalendarViewCreated() {
        caldroidFragment.getDialog().setOnShowListener(onShowListener);
        caldroidFragment.getDialog().setOnDismissListener(onDismissListener);
        View view = caldroidFragment.getView();

        if (view != null) {
            view.setOnTouchListener(calendarOnTouchListener);
            view.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {

                    if (visibility == View.VISIBLE) {
                        onCalendarShow();
                    } else {
                        onCalendarHide();
                    }
                }
            });
        }
    }


    /**
     * Called when touch event happens on calendar view.
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
     * Called when calendar view is visible.
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
     * Called when calendar view is not visible.
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
    private void goToday() {
        Calendar calendar = Calendar.getInstance(new Locale(localization));
        caldroidFragment.setCalendarDate(calendar.getTime());
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
}
