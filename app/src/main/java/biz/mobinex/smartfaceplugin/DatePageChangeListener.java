package biz.mobinex.smartfaceplugin;

import android.support.v4.view.ViewPager;

import com.antonyt.infiniteviewpager.InfiniteViewPager;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.ArrayList;

import hirondelle.date4j.DateTime;

/**
 * DatePageChangeListener refresh the date grid views when user swipe the calendar
 *
 * @author thomasdao
 */
public class DatePageChangeListener implements ViewPager.OnPageChangeListener {
    private int currentPage = InfiniteViewPager.OFFSET;
    private DateTime currentDateTime;
    private ArrayList<CaldroidGridAdapter> caldroidGridAdapters;
    private MobinexCalendarView mobinexCalendarView;

    public DatePageChangeListener(MobinexCalendarView mobinexCalendarView) {
        this.mobinexCalendarView = mobinexCalendarView;
    }

    /**
     * Return currentPage of the dateViewPager
     *
     * @return
     */
    public int getCurrentPage() {
        return currentPage;
    }


    /**
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }


    /**
     * Return currentDateTime of the selected page
     *
     * @return
     */
    public DateTime getCurrentDateTime() {
        return currentDateTime;
    }


    /**
     * @param dateTime
     */
    public void setCurrentDateTime(DateTime dateTime) {
        this.currentDateTime = dateTime;
        mobinexCalendarView.setCalendarDateTime(currentDateTime);
    }


    /**
     * Return 4 adapters
     *
     * @return
     */
    public ArrayList<CaldroidGridAdapter> getCaldroidGridAdapters() {
        return caldroidGridAdapters;
    }


    /**
     * @param caldroidGridAdapters
     */
    public void setCaldroidGridAdapters(ArrayList<CaldroidGridAdapter> caldroidGridAdapters) {
        this.caldroidGridAdapters = caldroidGridAdapters;
    }


    /**
     * Return virtual next position
     *
     * @param position
     * @return
     */
    private int getNext(int position) {
        return (position + 1) % CaldroidFragment.NUMBER_OF_PAGES;
    }


    /**
     * Return virtual previous position
     *
     * @param position
     * @return
     */
    private int getPrevious(int position) {
        return (position + 3) % CaldroidFragment.NUMBER_OF_PAGES;
    }


    /**
     * Return virtual current position
     *
     * @param position
     * @return
     */
    public int getCurrent(int position) {
        return position % CaldroidFragment.NUMBER_OF_PAGES;
    }


    /**
     * @param position
     */
    @Override
    public void onPageScrollStateChanged(int position) {
    }


    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }


    /**
     * @param position
     */
    public void refreshAdapters(int position) {
        // Get adapters to refresh
        CaldroidGridAdapter currentAdapter = caldroidGridAdapters.get(getCurrent(position));
        CaldroidGridAdapter prevAdapter = caldroidGridAdapters.get(getPrevious(position));
        CaldroidGridAdapter nextAdapter = caldroidGridAdapters.get(getNext(position));

        if (position == currentPage) {
            // Refresh current adapter
            currentAdapter.setAdapterDateTime(currentDateTime);
            currentAdapter.notifyDataSetChanged();

            // Refresh previous adapter
            prevAdapter.setAdapterDateTime(currentDateTime.minus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.LastDay));
            prevAdapter.notifyDataSetChanged();

            // Refresh next adapter
            nextAdapter.setAdapterDateTime(currentDateTime.plus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.LastDay));
            nextAdapter.notifyDataSetChanged();
        }
        // Detect if swipe right or swipe left
        // Swipe right
        else if (position > currentPage) {
            // Update current date time to next month
            currentDateTime = currentDateTime.plus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.LastDay);

            // Refresh the adapter of next gridview
            nextAdapter.setAdapterDateTime(currentDateTime.plus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.LastDay));
            nextAdapter.notifyDataSetChanged();

        }
        // Swipe left
        else {
            // Update current date time to previous month
            currentDateTime = currentDateTime.minus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.LastDay);

            // Refresh the adapter of previous gridview
            prevAdapter.setAdapterDateTime(currentDateTime.minus(0, 1, 0, 0, 0, 0, 0, DateTime.DayOverflow.LastDay));
            prevAdapter.notifyDataSetChanged();
        }

        // Update current page
        currentPage = position;
    }


    /**
     * Refresh the fragments
     */
    @Override
    public void onPageSelected(int position) {
        refreshAdapters(position);

        // Update current date time of the selected page
        mobinexCalendarView.setCalendarDateTime(currentDateTime);

        // Update all the dates inside current month
        CaldroidGridAdapter currentAdapter = caldroidGridAdapters.get(position % CaldroidFragment.NUMBER_OF_PAGES);

        // Refresh dateInMonthsList
        mobinexCalendarView.dateInMonthsList.clear();
        mobinexCalendarView.dateInMonthsList.addAll(currentAdapter.getDatetimeList());
    }
}
