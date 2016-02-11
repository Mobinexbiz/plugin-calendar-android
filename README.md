Smartface Calendar Plugin Android
===================

Smartface Calendar Plugin for Android is one of the first plugins of Smartface Plugin Project. This project is based on Caldroid project. It provides month based view. It can be used as a view or as a dialog. It supports swipe to change month and full localization. Calendar plugin works with Android API 16 and above.

<img src="http://imgur.com/ifJonYx.png">

Setup
=====

To start using Calendar plugin you need to follow below steps:

 - Download plugin file.
 - Copy plugin file to Smartface plugin folder.
 - Select Calendar plugin in Smartface project settings.
 
If you want to rebuild the plugin you can use `buildplugin.bat` file in project root folder. Be sure that `CLI` is installed and in your `PATH`.

To use Calendar with auto complete support (Optional)

 - Copy Calendar auto complete Javascript file to your project folder.
 - Load auto complete file in global.

You can look following guides to understand how can you pass these steps:

 - Adding a plugin to Smartface projects
 - Adding auto complete support for plugins
 - Installing CLI and adding it to PATH.

Features
========

####Can be embedded or shown as dialog

You can show SmartfaceCalendar as a dialog in your activity with below code:

``` Javascript
var calendar= new SmartfaceCalendar("Landroid/app/Activity;");
calendar.showAsDialog();
```

You can also embed caldroid fragment as a child in your application.

``` Javascript
var calendar= new SmartfaceCalendar("Landroid/app/Activity;");
Pages.Page1.add(calendar);
```

####Can set year and month

Month value should be between 1 - 12

``` Javascript
calendar.setYear(2000);
calendar.setMonth(1);
```

####Can set start day of the week

Start day value should be one of the Java Calendar day values: Sunday to Saturday (1-7)

``` Javascript
calendar.setStartDayOfWeek(2); //Calendar.MONDAY
```

####Can set locale

User can set locale of the calendar. If user don't set locale default locale will be used.

``` Javascript
calendar.setLocale("tr_TR");
```

####Can change layout parameters of calendar dynamically

You can pass layout parameters with constructor

``` Javascript
var calendar= new SmartfaceCalendar(
	"Landroid/app/Activity;", 
	1000, 1000, 0, 0);
```

or you can use default size. In this case Calendar will use `MATCH_PARENT` for width, `WRAP_CONTENT` for height and `0` as top and left parameters.

``` Javascript
var calendar= new SmartfaceCalendar("Landroid/app/Activity;");
```

or you can change each one separately

``` Javascript
calendar.setCalendarTop(700);
calendar.setCalendarLeft(100);
calendar.setCalendarWidth(1000);
calendar.setCalendarHeight(1000);
```

####Can change previous and next month button images

You should copy the images you want to use under `\resources\Images\android` folder in your project. You should provide required images for every screen dpi supported. You do not need to use file extensions. Give only file name itself.

You should call `setNextImage` and `setPreviousImage` before adding calendar view or showing calendar dialog.

``` Javascript
calendar.setNextImage("right");
calendar.setPreviousImage("left");
```

####Can enable or disable swipe to change month

``` Javascript
calendar.setSwipeEnabled(false);
```

####Can anable or disable touch

``` Javascript
calendar.setTouchEnabled(false);
```

####Can go to today programmatically

``` Javascript
calendar.goToday();
```

####Can set title of the calendar dialog

``` Javascript
calendar.setTitle("Select Date");
```

##Date methods

Dates functions of calendar accepts dates as `String` or `long`. When using dates as String user should always provide the format of the date string.

####Can set minimum and maximum date

User can set minimum and maximum date. Dates before minimum date and dates after maximum date will be disabled.

``` Javascript
calendar.setMinDate("01.01.2000", dateFormat);
calendar.setMaxDate("01.01.2001", dateFormat);
```

####Can disable dates

User can disable dates between two dates

``` Javascript
calendar.setDisabledFromDate("01.01.2000", dateFormat);
calendar.setDisabledToDate("01.01.2001", dateFormat);
```

or can disable array of dates

``` Javascript
calendar.setDisabledDates(datesArray, dateFormat);
```

####Can select dates

User can select dates between two dates

``` Javascript
calendar.setSelectedFromDate("01.01.2000", dateFormat);
calendar.setSelectedToDate("01.01.2001", dateFormat);
calendar.setSelectedDates("01.01.2000", "01.01.2001", dateFormat);
```

##Events

SmartfaceCalendar supports following events to notify user:

####onTouch
``` Javascript
calendar.setOnTouch(function (e) {
	alert("onTouch");
	log("onTouch");
});
```

####onTouchEnd
``` Javascript
calendar.setOnTouchEnd(function (e) {
	alert("onTouchEnd");
	log("onTouchEnd");
});
```

####onShow
``` Javascript
calendar.setOnShow(function (e) {
	alert("onShow");
	log("onShow");
});
```

####onHide
``` Javascript
calendar.setOnHide(function (e) {
	alert("onHide");
	log("onHide");
});
```

####onDateSelected
``` Javascript
calendar.setOnDateSelected(function (e) {
	alert("Date selected: " + e.date);
	log("Date selected: " + e.date);
});
```

####onLongPressed
``` Javascript
calendar.setOnLongPressed(function (e) {
	alert("Long pressed on date : " + e.date);
	log("Long pressed on date : " + e.date);
});
```

####onMonthChanged
``` Javascript
calendar.setOnMonthChanged(function (e) {
	alert("Month changed : " + e.oldMonth + " → " +  + e.newMonth);
	log("Month changed : " + e.oldMonth + " → " +  + e.newMonth);
});
```

Release Notes
========

Read the [RELEASE-NOTES.md](https://github.com/SmartfaceIO/plugin-calendar-android/blob/master/RELEASE-NOTES.md) file.
