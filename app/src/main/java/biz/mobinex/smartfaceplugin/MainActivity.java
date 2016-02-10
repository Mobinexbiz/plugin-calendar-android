package biz.mobinex.smartfaceplugin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private SmartfaceCalendar smartfaceCalendar;
    private LinearLayout topGroup;
    private ViewGroup viewGroup;
    private MainActivity mainActivity = this;
    private boolean isCalendarAdded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topGroup = (LinearLayout) findViewById(R.id.top_group);
        viewGroup = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);

        findViewById(R.id.test_set_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartfaceCalendar.setCalendarTop(topGroup.getHeight() + 100);
            }
        });

        findViewById(R.id.test_set_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartfaceCalendar.setCalendarLeft(100);
            }
        });

        findViewById(R.id.test_set_width).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartfaceCalendar.setCalendarWidth(1000);
            }
        });

        findViewById(R.id.test_set_height).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartfaceCalendar.setCalendarHeight(1000);
            }
        });

        findViewById(R.id.test_go_today).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartfaceCalendar.goToday();
            }
        });

        findViewById(R.id.test_toggle_swipe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartfaceCalendar.setSwipeEnabled(!smartfaceCalendar.isSwipeEnabled());
            }
        });

        findViewById(R.id.test_toggle_touch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smartfaceCalendar.setTouchEnabled(!smartfaceCalendar.isTouchEnabled());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        topGroup.post(new Runnable() {
            @Override
            public void run() {

                if (!isCalendarAdded) {
                    smartfaceCalendar = new SmartfaceCalendar(mainActivity);
                    smartfaceCalendar.setTheme(R.style.CaldroidDefaultDark);
                    viewGroup.addView(smartfaceCalendar, 1);
                    isCalendarAdded = true;
                }
            }
        });
    }
}
