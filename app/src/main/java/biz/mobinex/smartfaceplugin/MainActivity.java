package biz.mobinex.smartfaceplugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewGroup viewGroup = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);

        SmartfaceCalendar smartfaceCalendar =
                new SmartfaceCalendar(this,
                        AbsoluteLayout.LayoutParams.MATCH_PARENT,
                        AbsoluteLayout.LayoutParams.WRAP_CONTENT,
                        0, toolbar.getLayoutParams().height);

        java.util.Calendar javaCalendar = java.util.Calendar.getInstance();
        javaCalendar.set(java.util.Calendar.YEAR, 2016);
        javaCalendar.set(java.util.Calendar.MONTH, Calendar.JANUARY);
        javaCalendar.set(Calendar.DATE, 1);

        Date minDate = javaCalendar.getTime();

        javaCalendar.set(Calendar.DATE, 31);

        Date maxDate = javaCalendar.getTime();

        //smartfaceCalendar.setMinDate(minDate.toString(), "EEE MMM dd HH:mm:ss zzz yyyy");
        //smartfaceCalendar.setMaxDate(maxDate.toString(), "EEE MMM dd HH:mm:ss zzz yyyy");

        smartfaceCalendar.setMinDate(minDate.getTime());
        smartfaceCalendar.setMaxDate(maxDate.getTime());

        javaCalendar.set(java.util.Calendar.YEAR, 2016);
        javaCalendar.set(java.util.Calendar.MONTH, java.util.Calendar.JANUARY);
        javaCalendar.set(Calendar.DATE, 1);

        Date fromDate = javaCalendar.getTime();

        javaCalendar.set(Calendar.DATE, 10);

        Date toDate = javaCalendar.getTime();

        //smartfaceCalendar.setDisabledFromDate(fromDate.toString());
        //smartfaceCalendar.setDisabledToDate(toDate.toString());

        //smartfaceCalendar.setFillColor("#66799E");

        viewGroup.addView(smartfaceCalendar);
    }
}
